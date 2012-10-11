package cn.gls.database.postgis.operator.assist;

import java.util.HashSet;
import java.util.Set;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.GLSDBSupport;
import cn.gls.data.Place;
import cn.gls.database.operator.IAttributeOperatorDao;
import cn.gls.database.operator.assist.IGLSDatabaseAssistDao;
import cn.gls.util.StringUtils;

/**
 * @ClassName GradeTableProcessing.java
 * @Createdate 2012-7-1
 * @Description 等级表的处理
 * @Version 1.0
 * @Update 2012-7-10
 * @author "Daniel Zhang"
 */
public class GradeTableProcessing extends GLSDBSupport{
    private IAttributeOperatorDao attributeOperator;

    public void setAttributeOperator(IAttributeOperatorDao attributeOperator) {
        this.attributeOperator = attributeOperator;
    }
    private IGLSDatabaseAssistDao databaseAssistDao;
   public void setDatabaseAssistDao(IGLSDatabaseAssistDao databaseAssistDao) {
       this.databaseAssistDao = databaseAssistDao;
   }

   
   
   
    /**
     * 由属性来生成唯一的Place，通常是生成等级表时有用。
     * 
     * @param featureCollection
     * @param fieldName
     * @param placeLevel
     * @param cityName
     * @param cityFieldName
     * @return
     */
    public synchronized Set<Place> unionPlace(
            FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection,
            String fieldName, int placeLevel, String cityName,
            String cityFieldName) {
        FeatureIterator<SimpleFeature> featureIterator = featureCollection
                .features();
        Set<Place> places = new HashSet<Place>();
        int cityCode = 0;
        if (cityName != null && !"".equalsIgnoreCase(cityName))
            cityCode = attributeOperator.selectCityCodeByCityName(cityName);
        while (featureIterator.hasNext()) {
            SimpleFeature feature = featureIterator.next();
            String name = (String) feature.getAttribute(fieldName);
            if (cityCode == 0 && cityFieldName != null) {
                cityCode = attributeOperator
                        .selectCityCodeByCityName((String) feature
                                .getAttribute(cityFieldName));
            }

            places.add(new Place(name, placeLevel, cityCode));
        }
        return places;
    }

    /**
     * @Param GradeTableProcessing
     * @Description 导入等级地名词
     * @return int
     * @param featureCollection
     * @param fieldName
     * @param placeLevel
     * @param cityName
     * @param cityFieldName
     * @param placeType  类型字段
     * @return
     */
    public Set<Place> clearPlace(
            FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection,
            String fieldName, int placeLevel, String placeType,
            String cityCodeFieldName, String cityName, String cityFieldName,boolean isRemoveSuffix) {
        FeatureIterator<SimpleFeature> featureIterator = featureCollection
                .features();
        Set<Place> places = new HashSet<Place>();
        boolean isCityCodeField = featureCollection.getSchema().getDescriptor(
                cityCodeFieldName) != null;
        int cityCode = 0;
        if (cityName != null&&!"".equalsIgnoreCase(cityName))
            cityCode = attributeOperator.selectCityCodeByCityName(cityName);
        while (featureIterator.hasNext()) {
            SimpleFeature feature = featureIterator.next();
            String name = (String) feature.getAttribute(fieldName);
            if (name == null || "".equalsIgnoreCase(name))
                continue;
            if(isRemoveSuffix)
            name=getPlaceName(name);
            Place place = null;
            if (cityCode==0&&isCityCodeField)
                cityCode = (Integer) feature.getAttribute(cityCodeFieldName);
            else if (cityCode==0&&!isCityCodeField&&feature.getFeatureType().getDescriptor(cityFieldName) != null)
                    cityCode = attributeOperator
                            .selectCityCodeByCityName(cityName);
            // 换成allName了。
            place = new Place(name, placeLevel, cityCode);
            place.setPlaceType(feature.getAttribute(placeType).toString());
            // 首先要查询一下
            places.add(place);
        }
        featureIterator.close();
        return places;
    }



    /**
     * 真正的实现插入标准表数据
     */
    public int insertGradeData(Set<Place> places) {
        return databaseAssistDao.insertGradeData(places);
    }
    
    public String getPlaceName(String name){
    	return StringUtils.getPlaceName(name);
    }
}
