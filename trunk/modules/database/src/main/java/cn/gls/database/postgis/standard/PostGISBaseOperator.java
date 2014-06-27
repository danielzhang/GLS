package cn.gls.database.postgis.standard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.dom4j.Document;
import org.dom4j.Element;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureStore;
import org.geotools.data.Transaction;
import org.geotools.data.memory.MemoryFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.NameImpl;
import org.geotools.feature.simple.SimpleFeatureImpl;
import org.geotools.feature.simple.SimpleFeatureTypeImpl;
import org.geotools.feature.type.AttributeDescriptorImpl;
import org.geotools.feature.type.AttributeTypeImpl;
import org.geotools.feature.type.GeometryDescriptorImpl;
import org.geotools.filter.identity.FeatureIdImpl;
import org.geotools.util.SimpleInternationalString;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.feature.type.GeometryType;
import org.opengis.feature.type.Name;
import org.opengis.filter.identity.FeatureId;
import cn.gls.context.GLSContextLoader;
import cn.gls.database.DynamicLoadBean;
import cn.gls.database.LoadApplicationContext;
import cn.gls.database.data.DataType;
import cn.gls.database.operator.IStandardTableOperator;
import cn.gls.database.postgis.util.PostGISUtils;
import cn.gls.util.StringUtils;

/**
 * @ClassName: PostGISBaseOperator.java
 * @Description 基本的初始化类
 * @Date 2012-6-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public class PostGISBaseOperator extends PostGISAttributeOperator implements
		IStandardTableOperator {
    
	private String addressName="";
	
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		firePropertyChange("addressName",this.addressName,addressName);
		this.addressName = addressName;
	}

	
	/** 属性配置文件的位置 */
	private static final String DEFAULT_STANDARD_PATH = "conf/standardTable.properties";

	protected static Properties databaseProperties = new Properties();

	static {
		try {
			InputStream in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(DEFAULT_STANDARD_PATH);
			databaseProperties.load(in);

		} catch (IOException e) {
			throw new IllegalStateException(
					"Could not load 'standardTable.properties': "
							+ e.getMessage());
		}
	}

	protected static int type = 0;

	protected String sonName = null;

	protected int cityCode;

	protected String stAddress = null;

	protected String sCityName = null;

	protected String dataType = null;
	
   protected PropertyChangeSupport changeSupport;
	
	public PropertyChangeSupport getChangeSupport() {
		return changeSupport;
	}

	public void setChangeSupport(PropertyChangeSupport changeSupport) {
		this.changeSupport = changeSupport;
	}
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public void firePropertyChange(String propertyName, Object old,
			Object newObj) {
		changeSupport.firePropertyChange(propertyName, old, newObj);
	}
	
	public PostGISBaseOperator() {
		super();
	}

	public int insert(
			FeatureCollection<SimpleFeatureType, SimpleFeature> fcollection,
			Map<String, String> fieldMap, String dbTableName, String dataType,
			String sCityName) {
		if (fcollection == null || fcollection.isEmpty())
			return 0;
		FeatureIterator<SimpleFeature> featureIterator = fcollection.features();
		FeatureCollection<SimpleFeatureType, SimpleFeature> mfcollections = null;
		SimpleFeatureType simplefeatureType = null;
		int i = 0;
		this.dataType = dataType;
		this.sCityName = sCityName;
		// 重置seq的值
		resetSeq(type);
		while (featureIterator.hasNext()) {
			SimpleFeature feature = featureIterator.next();
			if (simplefeatureType == null) {
				simplefeatureType = createFeatureType(feature.getFeatureType(),
						type);
				mfcollections = new MemoryFeatureCollection(simplefeatureType);
			}
            
			FeatureId featureId = new FeatureIdImpl(
					databaseProperties.getProperty(dbTableName) + i++);
			SimpleFeature resultFeature = new SimpleFeatureImpl(
					new Object[simplefeatureType.getAttributeCount()],
					simplefeatureType, featureId, false);
			mapingDefaultField(resultFeature, feature, fieldMap);
			mapingAttributeToTableField(resultFeature, feature, fieldMap);
			setAddressName(addressName+"\n"+(String)resultFeature.getAttribute("st_address"));
			mfcollections.add(resultFeature);
		}
		setAddressName(addressName+"\n"+"完成导入数据");
		featureIterator.close();
		return insertFeatures(dbTableName, mfcollections);

	}

	/**
	 * 向不同的标准表中插入要素
	 * 
	 * @param dbTableName
	 * @param featureCollection
	 * @return
	 */
	protected int insertFeatures(
			String dbTableName,
			FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection) {
		int count = 0;
		FeatureStore<SimpleFeatureType, SimpleFeature> fstore = null;
		try {
			fstore = PostGISUtils.getInstance().getFeatureStore(
					databaseProperties.getProperty(dbTableName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Transaction t = new DefaultTransaction("add");
		fstore.setTransaction(t);
		
		try {
			fstore.addFeatures(featureCollection);
			count = featureCollection.size();
			featureCollection.clear();
			t.commit();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				t.rollback();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		} finally {
			try {
				t.close();
				if (DataType.ADDRESS_TYPE == type) {
					DynamicLoadBean dynamicLoadBean = (DynamicLoadBean) LoadApplicationContext
							.getInstance().getBean("dynamicLoadBean");
//					GLSContextLoader glsContext = (GLSContextLoader) LoadApplicationContext
//							.getInstance().getBean("glsContextLoader");
					GLSContextLoader.getInstance().destory();
					dynamicLoadBean.unloadBean();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return count;

	}

	/**
	 * 映射属性与表列相对应的值
	 * 
	 * @param resultFeature
	 * @param feature
	 * @param fieldMap
	 * @return
	 */
	protected SimpleFeature mapingDefaultField(SimpleFeature resultFeature,
			SimpleFeature feature, Map<String, String> fieldMap) {
		Iterator<Entry<String, String>> fieldName = fieldMap.entrySet()
				.iterator();
		while (fieldName.hasNext()) {
			Entry<String, String> entry = fieldName.next();
			String fieldKey = entry.getKey();
			String fieldValue = entry.getValue();
			resultFeature.setAttribute(fieldKey,
					feature.getAttribute(fieldValue));
		}
		return resultFeature;
	}

	/**
	 * 特殊映射
	 * 
	 * @param resultFeature
	 * @param feature
	 * @param fieldMap
	 * @return
	 */
	protected SimpleFeature mapingAttributeToTableField(
			SimpleFeature resultFeature, SimpleFeature feature,
			Map<String, String> fieldMap) {
		return resultFeature;
	}

	/**
	 * 根据表结构构建SimpleFeatureType
	 * 
	 * @param featureType
	 * @param type
	 * @return
	 */
	protected SimpleFeatureType createFeatureType(
			SimpleFeatureType featureType, int type) {
		SimpleFeatureType sfeatureType = null;
		Document document = LoadApplicationContext.getInstance().getDocument();
		Element rootElement = document.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> elements = rootElement.elements();
		Iterator<Element> eiterator = elements.iterator();
		List<AttributeDescriptor> attributeDescriptors = new ArrayList<AttributeDescriptor>();
		while (eiterator.hasNext()) {
			Element element = eiterator.next();
			if (type == (Integer.valueOf(element.attribute("type").getValue()))) {
				@SuppressWarnings("unchecked")
				List<Element> attributeElement = element.elements();
				for (int i = 1; i < attributeElement.size(); i++) {

					Element e = attributeElement.get(i);

					Name name = new NameImpl(e.attributeValue("name"));
					if ("geoColumn".equalsIgnoreCase(e.getName())) {
						GeometryType gType = featureType
								.getGeometryDescriptor().getType();
						GeometryDescriptor gdescriptor = new GeometryDescriptorImpl(
								gType, name, 0, 1, false, null);
						attributeDescriptors.add(gdescriptor);
						continue;
					}
					AttributeType atype = new AttributeTypeImpl(name,
							StringUtils.getClass(e.element("bindingType")
									.getText()), false, false, null, null,
							new SimpleInternationalString(e.element(
									"descriptor").getText()));
					attributeDescriptors.add(new AttributeDescriptorImpl(atype,
							name, 0, 1, false, e.element("defaultValue")
									.getData()));

				}
				sfeatureType = new SimpleFeatureTypeImpl(new NameImpl(
						element.attributeValue("name")), attributeDescriptors,
						featureType.getGeometryDescriptor(), false, null, null,
						new SimpleInternationalString((String) element.element(
								"descriptor").getData()));
			} else
				continue;
		}

		return sfeatureType;
	}

	/**
	 * 重置表序列
	 * 
	 * @Param DataBaseOperator
	 * @Description TODO
	 * @return void
	 * @param type
	 */
	protected void resetSeq(int type) {
		switch (type) {
		case DataType.PROVINCE_TYPE:
			getSqlMapClientTemplate().queryForObject("assist.setProvinceSeq",
					maxSeq(type));
			break;
		case DataType.CITY_TYPE:
			getSqlMapClientTemplate().queryForObject("assist.setCitySeq",
					maxSeq(type));
			break;
		case DataType.POLITICAL_TYPE:
			getSqlMapClientTemplate().queryForObject("assist.setPoliticalSeq",
					maxSeq(type));
			break;
		case DataType.STREET_TYPE:
			getSqlMapClientTemplate().queryForList("assist.setStreetSeq",
					maxSeq(type));
			break;
		case DataType.ADDRESS_TYPE:
			getSqlMapClientTemplate().queryForList("assist.setAddressSeq",
					maxSeq(type));
			break;
		}
	}

	protected int maxSeq(int type) {
		Integer seq = 1;
		switch (type) {
		case DataType.PROVINCE_TYPE:
		    seq = (Integer) getSqlMapClientTemplate().queryForObject(
					"assist.getProvinceMaxSeq");
			break;
		case DataType.CITY_TYPE:
			seq = (Integer) getSqlMapClientTemplate().queryForObject(
					"assist.getCityMaxSeq");
			break;
		case DataType.POLITICAL_TYPE:
			seq = (Integer) getSqlMapClientTemplate().queryForObject(
					"assist.getPoliticalMaxSeq");
			break;
		case DataType.STREET_TYPE:
			seq = (Integer) getSqlMapClientTemplate().queryForObject(
					"assist.getStreetMaxSeq");
			break;
		case DataType.ADDRESS_TYPE:
			seq = (Integer) getSqlMapClientTemplate().queryForObject(
					"assist.getAddressMaxSeq");
			break;
		}
		if(seq==null)
			seq=1;
		return seq;
	}
}
