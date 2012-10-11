package cn.gls.database.postgis.util;

import java.io.IOException;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.database.LoadApplicationContext;

/**
 * @ClassName: PostGISUtil.java
 * @Description 与postgis操作关系的相关工具类
 * @Date 2012-6-26
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-26
 */
public class PostGISUtils {

	private  DataStore pgdataStore;

	private  Map<String, String> params;
	private static PostGISUtils instance = new PostGISUtils();

	@SuppressWarnings("unchecked")
	public PostGISUtils() {
		params = (Map<String, String>) LoadApplicationContext.getInstance()
				.getBean("parameterMap");
		initDataStore();
	}

	public static synchronized  PostGISUtils getInstance() {
		return instance;
	}

	/**
	 * 初始化dataStore中。
	 */
	private  void initDataStore() {
		try {
			if (pgdataStore == null)
				pgdataStore = DataStoreFinder.getDataStore(params);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description  从postgis里读取要素，获得FeatureCollection对象。
	 * @param tableName 表的名称 
	 */
	public  FeatureCollection<SimpleFeatureType, SimpleFeature> readFeatureByPostGIS(
			String tableName) {
		try {
			FeatureSource<SimpleFeatureType, SimpleFeature> fsource = pgdataStore
					.getFeatureSource(tableName);
			FeatureCollection<SimpleFeatureType, SimpleFeature> fcollections = fsource
					.getFeatures();
			return fcollections;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
         /**
          * 
          *@Param PostGISUtils
          *@Description 获得postgis的featureStore对象.
          *@return FeatureStore<SimpleFeatureType,SimpleFeature>
          * @param tableName
          * @return
          * @throws IOException
          */
	@SuppressWarnings("unchecked")
	public  FeatureStore<SimpleFeatureType, SimpleFeature> getFeatureStore(
			String tableName) throws IOException {
		return (FeatureStore<SimpleFeatureType, SimpleFeature>) pgdataStore
				.getFeatureSource(tableName);
	}
	
}
