package cn.gls.database.postgis.standard;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.data.DataType;
import cn.gls.database.shp.util.ShpUtils;
import cn.gls.database.util.FeaturePreProcessing;

/**
 * @ClassName: ReadGeoDataTest.java
 * @Description 空间数据的读取测试类.
 * @Date 2012-6-14
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-14
 */
public class PostGISDataTest extends
		AbstractDependencyInjectionSpringContextTests {
	private PostGISOperator postGISOperator;

	private FeaturePreProcessing featureProcessing;

	public void setDatabaseOperator(PostGISOperator postGISOperator) {
		this.postGISOperator = postGISOperator;
	}

	public PostGISDataTest() {
		super();
		LoadApplicationContext loadApplicationContext = LoadApplicationContext
				.getInstance();
		postGISOperator = new PostGISOperator(DataType.PROVINCE_TYPE);
		featureProcessing = (FeaturePreProcessing) LoadApplicationContext
				.getInstance().getBean("featureProcessing");
		// System.out.println(LoadApplicationContext.getInstance().getBean("sqlMapClient"));
	}

	private FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = null;

	private Map<String, String> fieldMap = null;

	private String dbTableName = null;

	private String sCityName = null;
	
	private String dbType=null;

	public void testInsertTable() {
		postGISOperator.setType(DataType.ADDRESS_TYPE);
		//insertProvince();
		//insertCity();
	//	insertPolitical();
	//	 insertStreet();
		insertPOI();
		int count = postGISOperator.insert(featureCollection, fieldMap,
				dbTableName,dbType, sCityName);
		System.out.println(count);
	}

	private void insertCity() {
		fieldMap = new HashMap<String, String>();
		fieldMap.put("name", "NAME");
		fieldMap.put("city_code", "ADCODE99");
		featureCollection = ShpUtils
				.readShpfile("src/test/resources/shp/citys.shp");
		dbTableName = "cityTableName";
	}

	private void insertProvince() {

		fieldMap = new HashMap<String, String>();
		
		fieldMap.put("name", "NAME");
		// fieldMap.put("province_code", "ADCODE99");
		
	    unionFeature("src/test/resources/shp/国界与省界4p.shp", "NAME",null,null);
		dbTableName = "provinceTableName";
	}

	
	private void insertPolitical() {
		fieldMap = new HashMap<String, String>();
		fieldMap.put("village_name", "Name");
//		featureCollection = ShpUtils
//				.readShpfile("src/test/resources/shp/cun.shp");		
		//预处理
//		featureCollection=featureProcessing.preDATAProcessing(ShpUtils
//			.readShpfile("src/test/resources/shp/cun.shp"), ShpUtils.readShpfile("src/test/resources/shp/shanghaiquxia.shp"), "Name","上海市");
		//首先要进行预处理，就是添加前几个字段。
		featureCollection=preprocessingData("src/test/resources/shp/cun.shp", "src/test/resources/shp/shanghaiquxia.shp", "Name","上海市");
		dbTableName = "politicalTableName";
		dbType="行政地名";
		sCityName = "上海市";
	}

	private void insertStreet() {
		fieldMap = new HashMap<String, String>();
		fieldMap.put("street_name", "Name");
		fieldMap.put("county_name", "county_name");
	//	fieldMap.put("name", "village_name");
		long c = System.currentTimeMillis();		
		unionFeature("src/test/resources/shp/daolu.shp","Name","src/test/resources/shp/shanghaiquxia.shp","Name");
		sCityName="上海市";
		dbType="主要道路";
	//	System.out.println("合并要素耗时："+(System.currentTimeMillis() - c));
		dbTableName = "streetTableName";
	}
	
	private void insertPOI(){
		fieldMap=new HashMap<String, String>();
	//	fieldMap.put("building_name", "Name");
		fieldMap.put("poi_name", "Name");
		fieldMap.put("address", "Address");
		
		long c = System.currentTimeMillis();
		sCityName="上海市";
		dbTableName="poiTableName";
		//在导入之前必须先预处理一下。
	        featureCollection=	featureProcessing.preDATAProcessing(ShpUtils.readShpfile("src/test/resources/shp/gouwu.shp"), ShpUtils.readShpfile("src/test/resources/shp/shanghaiquxia.shp"), "Name", "上海市");	 
		dbType="购物服务";
	}

	private void unionFeature(String shpName,String fieldName,String countyshpName,String countyFieldName) {
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = ShpUtils
				.getFeatureSourceByShp(shpName);
		FeatureSource<SimpleFeatureType, SimpleFeature> countyFeatureSource=null;
		if(countyshpName!=null)
        countyFeatureSource=ShpUtils.getFeatureSourceByShp(countyshpName);
		featureCollection = featureProcessing.unionSimpleFeatures(
				featureSource, fieldName,countyFeatureSource,countyFieldName);
		System.out.println("合并feature后的数量："+featureCollection.size());
	}
	
	private FeatureCollection<SimpleFeatureType, SimpleFeature> preprocessingData(String oShpName,String countyShpName,String countyFieldName,String cityName){
		 FeatureCollection<SimpleFeatureType, SimpleFeature> processingFeature=featureProcessing.preDATAProcessing(ShpUtils.readShpfile(oShpName),ShpUtils.readShpfile(countyShpName), countyFieldName, cityName);
		 return processingFeature;
	}
	

	public void insertShptoPostGIS() {
		try {
			long t = System.currentTimeMillis();
			PostGISDataOperator readGeoData = (PostGISDataOperator) LoadApplicationContext
					.getInstance().getBean("postgisOperator");
			readGeoData.insertShp(
					"src/test/resources/shp/Insurancecompany.shp",
					"Insurancecompany");
			System.out.println((System.currentTimeMillis() - t));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
