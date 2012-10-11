package cn.gls.database.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.geotools.data.FeatureSource;
import org.geotools.data.memory.MemoryFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.NameImpl;
import org.geotools.feature.simple.SimpleFeatureImpl;
import org.geotools.feature.simple.SimpleFeatureTypeImpl;
import org.geotools.feature.type.AttributeDescriptorImpl;
import org.geotools.feature.type.AttributeTypeImpl;
import org.geotools.filter.AttributeExpressionImpl;
import org.geotools.filter.SortByImpl;
import org.geotools.geometry.jts.FactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.expression.PropertyName;
import org.opengis.filter.sort.SortBy;
import org.opengis.filter.sort.SortOrder;

import cn.gls.database.operator.IAttributeOperatorDao;
import cn.gls.database.operator.IFeaturePreProcessing;
import cn.gls.threads.GLSTaskInterface;
import cn.gls.threads.GLSTaskQueue;
import cn.gls.threads.GLSThreadGroup;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;

public class FeaturePreProcessing implements IFeaturePreProcessing {

private FeatureCollection<SimpleFeatureType, SimpleFeature> simpleFeatures = null;

private int taskCount = 0;

private IAttributeOperatorDao attributeOperatorDao;

public void setAttributeOperatorDao(IAttributeOperatorDao attributeOperatorDao) {
    this.attributeOperatorDao = attributeOperatorDao;
}

private FeaturePreProcessing outer = this;

/**
 * 合并要素,基本上是线性要素比如国道，道路，街道,主要针对Street类要素。
 */
public synchronized FeatureCollection<SimpleFeatureType, SimpleFeature> unionSimpleFeatures(
        FeatureSource<SimpleFeatureType, SimpleFeature> ofeature,
        String fieldName,
        FeatureSource<SimpleFeatureType, SimpleFeature> countyFeatureSource,
        String countyFieldName) {
    // 首先对其进行排序
    FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = null;
    // 清洗数据
    try {
        featureCollection = FeatureUtils.clearSimpleFeatures(ofeature.getFeatures(),
                fieldName);
        System.out.println("清洗后要素个数---" + featureCollection.size());
    } catch (IOException e2) {
        e2.printStackTrace();
    }
    PropertyName propertyName = new AttributeExpressionImpl(new NameImpl(
            fieldName));
    SortBy sortBy = new SortByImpl(propertyName, SortOrder.ASCENDING);
    FeatureCollection<SimpleFeatureType, SimpleFeature> sortFeatureCollection = null;
    FeatureCollection<SimpleFeatureType, SimpleFeature> countyFeatureCollection = null;
    try {

        if (countyFeatureSource != null)
            countyFeatureCollection = countyFeatureSource.getFeatures();

        MemoryFeatureCollection memoryFeatureCollection = changeFeatureCollectionToMemoryFeatureCollection(featureCollection);
        sortFeatureCollection = memoryFeatureCollection.sort(sortBy);
    } catch (IOException e1) {
        e1.printStackTrace();
    }
    SimpleFeatureType schema = sortFeatureCollection.getSchema();
    simpleFeatures = new MemoryFeatureCollection(schema);
    Map<SimpleFeature, List<Geometry>> simpleFeatureMap = new HashMap<SimpleFeature, List<Geometry>>();
    FeatureIterator<SimpleFeature> featureIterator = sortFeatureCollection
            .features();
    SimpleFeature lastSimpleFeature = null;
    Geometry countyGeometry = null;
    while (featureIterator.hasNext()) {
        SimpleFeature simpleFeature = featureIterator.next();
        String fieldValue = ((String) simpleFeature.getAttribute(fieldName))
                .trim();
        if (fieldValue == null || "".equalsIgnoreCase(fieldValue))
            continue;
        if (lastSimpleFeature != null
                && fieldValue.equalsIgnoreCase(((String) lastSimpleFeature
                        .getAttribute(fieldName)).trim())
                && (countyFeatureCollection == null || (countyGeometry != null && countyGeometry
                        .contains((Geometry) lastSimpleFeature
                                .getDefaultGeometry())))) {
            // 相同
            simpleFeatureMap.get(lastSimpleFeature).add(
                    (Geometry) simpleFeature.getDefaultGeometry());
        } else {

            List<Geometry> geometrys = new ArrayList<Geometry>();
            geometrys.add((Geometry) simpleFeature.getDefaultGeometry());
            lastSimpleFeature = simpleFeature;
            // 如果存在区域对象，则需要判定出这个geometry在那个Geometry中。
            if (countyFeatureCollection != null) {
                SimpleFeature fatherFeature = getfatherFeature(
                        (Geometry) simpleFeature.getDefaultGeometry(),
                        countyFeatureCollection);
                countyGeometry = (Geometry) fatherFeature.getDefaultGeometry();
                if (countyFieldName != null) {
                    String countyValue = (String) fatherFeature
                            .getAttribute(countyFieldName);
                    lastSimpleFeature = changeFeatureToMemoryFeature(
                            simpleFeature,
                            createSimpleFeatureType(simpleFeature
                                    .getFeatureType()), countyValue);
                }
            }
            simpleFeatureMap.put(lastSimpleFeature, geometrys);
        }
    }
    featureIterator.close();
    // 合并

    int cpus = Runtime.getRuntime().availableProcessors();
    int s = simpleFeatureMap.size();
    // if(s==featureCollection.size())

    GLSTaskQueue taskQueue = new GLSTaskQueue();
    GLSThreadGroup group = new GLSThreadGroup(taskQueue);
    Iterator<Map.Entry<SimpleFeature, List<Geometry>>> mapIterator = simpleFeatureMap
            .entrySet().iterator();
    taskCount = s;
    while (mapIterator.hasNext()) {
        Entry<SimpleFeature, List<Geometry>> mapEntry = mapIterator.next();
        CombineGeometryTask task = new CombineGeometryTask(mapEntry.getValue(),
                mapEntry.getKey());
        taskQueue.putTask(task);
    }
    for (int i = 0; i < cpus; i++) {
        group.addGLSThread();
    }
    try {
        synchronized (this) {
            this.wait();
        }

    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return simpleFeatures;
}


/**
 * @ClassName FeaturePreProcessing.java
 * @Createdate 2012-7-9
 * @Description 图形合并类，通过名称相同或其他规则来合并各要素的方法。
 * @Version 1.0
 * @Update 2012-7-9
 * @author "Daniel Zhang"
 */

public class CombineGeometryTask implements GLSTaskInterface {
private List<Geometry> geometryCollection = null;

private SimpleFeature feature = null;

public CombineGeometryTask(List<Geometry> geometryCollection,
        SimpleFeature feature) {
    super();
    this.geometryCollection = geometryCollection;
    this.feature = feature;
}

/**
 * 多个geometry合并成一个geometry
 * 
 * @Param FeatureProcessing
 * @Description TODO
 * @return Geometry
 * @param geometryCollection
 * @return
 */
public Object execute() {

    if (geometryCollection.size() > 1) {
        GeometryFactory factory = FactoryFinder.getGeometryFactory(null);
        Object[] objs = this.geometryCollection.toArray();
        Geometry[] geometrys = new Geometry[objs.length];
        for (int i = 0; i < objs.length; i++) {
            Geometry geomtry = (Geometry) objs[i];
            geometrys[i] = geomtry;
        }
        GeometryCollection geometryCollections = new GeometryCollection(
                geometrys, factory);
        // if(!((Geometry)feature.getDefaultGeometry()).getClass().isAssignableFrom(Point.class))
        Geometry geometry = geometryCollections.union();

        feature.setDefaultGeometry(geometry);
        // System.out.println(feature.getAttribute("Name"));
    }

    if (simpleFeatures != null)
        simpleFeatures.add(feature);
  //  System.out.println(simpleFeatures.size());
    if (simpleFeatures.size() == taskCount) {

        synchronized (outer) {
            outer.notifyAll();
        }
    }
    return feature;
}
}

/**
 * 由老的simpleFeature来获得新的内存Simplefeature.
 * 
 * @param feature
 * @param newsimpleFeatureType
 * @param countyValue
 * @return
 */
private SimpleFeature changeFeatureToMemoryFeature(SimpleFeature feature,
        SimpleFeatureType newsimpleFeatureType, String countyValue) {
    SimpleFeature memoryFeature = new SimpleFeatureImpl(
            new Object[newsimpleFeatureType.getAttributeCount()],
            newsimpleFeatureType, feature.getIdentifier(), false);
    int count = feature.getAttributeCount();
    for (int i = 0; i < count; i++) {
        memoryFeature.setAttribute(i, feature.getAttribute(i));
    }
    memoryFeature.setAttribute("county_name", countyValue);
    return memoryFeature;
}

/**
 * @Param FeaturePreProcessing
 * @Description 把featureCollection转化为MemoryFeatureCollection
 * @return MemoryFeatureCollection
 * @param featureCollection
 * @return
 */
private MemoryFeatureCollection changeFeatureCollectionToMemoryFeatureCollection(
        FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection) {

    MemoryFeatureCollection memoryFeatureCollection = new MemoryFeatureCollection(
            createSimpleFeatureType(featureCollection.getSchema()));
    FeatureIterator<SimpleFeature> featureIterator = featureCollection
            .features();
    while (featureIterator.hasNext()) {
        memoryFeatureCollection.add(featureIterator.next());
    }
    featureIterator.close();
    return memoryFeatureCollection;
}

/**
 * 产生新的SimpleFeatureType.
 * 
 * @param simpleFeatureType
 * @return
 */
private SimpleFeatureType createSimpleFeatureType(
        SimpleFeatureType simpleFeatureType) {
    List<AttributeDescriptor> attributeDescriptors = simpleFeatureType
            .getAttributeDescriptors();
    List<AttributeDescriptor> newattributeDescriptors = new ArrayList<AttributeDescriptor>();
    for (AttributeDescriptor attributeDescriptor : attributeDescriptors) {
        newattributeDescriptors.add(attributeDescriptor);
    }
    if (simpleFeatureType.getDescriptor("county_name") == null) {
        AttributeDescriptor countyAttributeDescriptor = new AttributeDescriptorImpl(
                new AttributeTypeImpl(new NameImpl("county_name"),
                        String.class, false, false, null, null, null),
                new NameImpl("county_name"), 0, 1, false, null);
        newattributeDescriptors.add(countyAttributeDescriptor);

    }
    if (simpleFeatureType.getDescriptor("city_name") == null) {
        AttributeDescriptor cityAttributeDescriptor = new AttributeDescriptorImpl(
                new AttributeTypeImpl(new NameImpl("city_name"), String.class,
                        false, false, null, null, null), new NameImpl(
                        "city_name"), 0, 1, false, null);
        newattributeDescriptors.add(cityAttributeDescriptor);
    }
    if (simpleFeatureType.getDescriptor("province_name") == null) {
        AttributeDescriptor provinceAttributeDescriptor = new AttributeDescriptorImpl(
                new AttributeTypeImpl(new NameImpl("province_name"),
                        String.class, false, false, null, null, null),
                new NameImpl("province_name"), 0, 1, false, null);
        newattributeDescriptors.add(provinceAttributeDescriptor);
    }
    SimpleFeatureType newSimpleFeature = new SimpleFeatureTypeImpl(
            simpleFeatureType.getName(), newattributeDescriptors,
            simpleFeatureType.getGeometryDescriptor(),
            simpleFeatureType.isAbstract(),
            simpleFeatureType.getRestrictions(), simpleFeatureType.getSuper(),
            simpleFeatureType.getDescription());
    return newSimpleFeature;

}

/**
 * @desciptor:获得父类要素
 * @param geometry
 * @param fatherFeatureCollection
 * @return
 */
private SimpleFeature getfatherFeature(
        Geometry geometry,
        FeatureCollection<SimpleFeatureType, SimpleFeature> fatherFeatureCollection) {
    SimpleFeature fatherFeature = null;
    FeatureIterator<SimpleFeature> featureIterator = fatherFeatureCollection
            .features();
    while (featureIterator.hasNext()) {
        SimpleFeature feature = featureIterator.next();

        if (((Geometry) feature.getDefaultGeometry()).contains(geometry)) {
            fatherFeature = feature;
            break;
        }
    }
    featureIterator.close();
    // 如果没有包含关系的话那么一定要搞到相交的父要素

    if (fatherFeature == null) {
        FeatureIterator<SimpleFeature> featureIterator2 = fatherFeatureCollection
                .features();
        while (featureIterator2.hasNext()) {
            SimpleFeature feature = featureIterator2.next();
            Geometry fatherGeometry = (Geometry) feature.getDefaultGeometry();
            if (fatherGeometry.getEnvelope().contains(geometry.getCentroid())) {
                fatherFeature = feature;
                break;
            }

        }
    }

    return fatherFeature;
}
private String provinceName;
private String cityName;
private String countyName;
/**
 * 作用：为每一个feature添加属性字段province_name,city_name,county_name (non-Javadoc)
 * 
 * @see cn.gls.database.util.IShpFileProcessing#prePOIDATAProcessing(org.geotools.feature.FeatureCollection,
 *      org.geotools.feature.FeatureCollection, java.lang.String)
 */
public FeatureCollection<SimpleFeatureType, SimpleFeature> preDATAProcessing(
        FeatureCollection<SimpleFeatureType, SimpleFeature> oFeatures,
        FeatureCollection<SimpleFeatureType, SimpleFeature> countyFeatures,
        String countyFieldName, String cityName) {
	this.cityName=cityName;
    // 构造memoryFeatureCollection
    SimpleFeatureType poiFeatureType = createSimpleFeatureType(oFeatures
            .getSchema());
    MemoryFeatureCollection memoryFeatureCollection = new MemoryFeatureCollection(
            poiFeatureType);
    FeatureIterator<SimpleFeature> featureIterator = oFeatures.features();
    // boolean cityValid=false;

    while (featureIterator.hasNext()) {
        SimpleFeature feature = featureIterator.next();
        Geometry poiGeometry = (Geometry) feature.getDefaultGeometry();
        // 下面就是确定county的值
//        String countyValue = null;
        FeatureIterator<SimpleFeature> countyFeatureIterator = countyFeatures
                .features();
        while (countyFeatureIterator.hasNext()) {
            SimpleFeature countyFeature = countyFeatureIterator.next();
            Geometry countyGeometry = (Geometry) countyFeature
                    .getDefaultGeometry();
            if (countyGeometry.contains(poiGeometry)) {
                this.countyName = (String) countyFeature
                        .getAttribute(countyFieldName);
                break;
            }
        }
        countyFeatureIterator.close();
        // 生成新的SimpleFeature
        memoryFeatureCollection.add(changeFeatureToMemoryFeature(feature,
                poiFeatureType, this.countyName, this.cityName));
    }
    featureIterator.close();
    return memoryFeatureCollection;
}

/**
 * 由老的feature来获得新的内存Feature
 * 
 * @param ofeature
 * @param simpleFeatureType
 * @param countyValue
 * @param cityName
 * @return
 */
private SimpleFeature changeFeatureToMemoryFeature(SimpleFeature feature,
        SimpleFeatureType newsimpleFeatureType, String countyValue,
        String cityName) {
    // 通过cityname来得到province
	if(this.cityName.equalsIgnoreCase(cityName)&&provinceName==null)
    this.provinceName = attributeOperatorDao.selectProvinceByCity(cityName);
    SimpleFeature memoryFeature = new SimpleFeatureImpl(
            new Object[newsimpleFeatureType.getAttributeCount()],
            newsimpleFeatureType, feature.getIdentifier(), false);
    int count = feature.getAttributeCount();
    for (int i = 0; i < count; i++) {
        memoryFeature.setAttribute(i, feature.getAttribute(i));
    }
    memoryFeature.setAttribute("county_name", countyValue);
		memoryFeature.setAttribute("city_name", cityName);
    memoryFeature.setAttribute("province_name", provinceName);
    return memoryFeature;

}



}
