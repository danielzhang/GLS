package cn.gls.database.postgis.standard;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.Iterator;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;

import org.geotools.data.Transaction;
import org.geotools.data.memory.MemoryFeatureCollection;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureImpl;

import org.geotools.jdbc.JDBCDataStore;
import org.geotools.jdbc.SQLDialect;


import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;



/**
 * @ClassName: ReadGeoData.java
 * @Description 读取shpfile文件,注意，这时的地名词全部默认为标准地名词
 * @Date 2012-6-13
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-13
 */
public class PostGISDataOperator {

	private static final Logger logger = Logger.getLogger(PostGISDataOperator.class);
	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.IOperateGeoData#readShpfile(java.lang.String)
	 */
	public FeatureCollection<SimpleFeatureType, SimpleFeature> readShpfile(
			String filepath) {
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = getFeatureSourceByShp(filepath);

		FeatureCollection<SimpleFeatureType, SimpleFeature> features = null;
		try {
			features = featureSource.getFeatures();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return features;

	}

	/**
	 * 获得shp的featureSource
	 * 
	 * @param filepath
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private FeatureSource<SimpleFeatureType, SimpleFeature> getFeatureSourceByShp(
			String filepath) {
		if (filepath == null || "".equals(filepath))
			return null;
		File file = new File(filepath);
		ShapefileDataStore shapeDataStore = null;
		try {
			shapeDataStore = new ShapefileDataStore(file.toURL());
			String typeName = shapeDataStore.getTypeNames()[0];
			FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = null;
			shapeDataStore.setStringCharset(Charset.forName("GBK"));
			featureSource = shapeDataStore.getFeatureSource(typeName);
			return featureSource;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从shp里读取要素
	 * 
	 * @param tableName
	 * @param spatialFieldName
	 */
	public FeatureCollection<SimpleFeatureType, SimpleFeature> readFeatureByShp(
			String tableName) {
		try {
			FeatureSource<SimpleFeatureType, SimpleFeature> fsource =getFeatureSourceByShp(tableName);
			FeatureCollection<SimpleFeatureType, SimpleFeature> fcollections = fsource
					.getFeatures();
			return fcollections;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 根据某图层与列名来获得Place,如果citycode已存在的话cityName就默认无用。
	 * 
	 * @param place_level
	 * @param features
	 * @param fieldName
	 * @param cityCode
	 * @param fieldCity
	 * @return
	 * @throws UnsupportedEncodingException
	 */
//	public List<Place> getPlaces(int place_level,
//			FeatureCollection<SimpleFeatureType, SimpleFeature> features,
//			String fieldName, String cityName, int cityCode, String fieldCity,
//			String charSet) throws UnsupportedEncodingException {
//		int length = features.size();
//		if (length <= 0)
//			return null;
//		List<Place> places = new ArrayList<Place>();
//		// 确定cityCode是否为空
//		if (cityName != null && !"".equalsIgnoreCase(cityName)) {
//			cityCode = PostGISOperator.getInstance().selectCityCodeByCityName(
//					cityName);
//		}
//		FeatureIterator<SimpleFeature> featureIterator = features.features();
//		while (featureIterator.hasNext()) {
//			SimpleFeature feature = featureIterator.next();
//			String fieldValue = new String(feature.getAttribute(fieldName)
//					.toString().getBytes(charSet), "GBK");
//			;
//			if (cityCode != 0)
//				places.add(new Place(fieldValue, place_level, cityCode));
//			else {
//				String cityValue = feature.getAttribute(fieldCity).toString();
//				places.add(new Place(fieldValue, place_level, PostGISOperator
//						.getInstance().selectCityCodeByCityName(cityValue)));
//			}
//		}
//		featureIterator.close();
//		return places;
//	}

	/**
	 * 标准化地址。
	 * 
	 * @param features
	 * @param citycode
	 * @param cityName
	 * @param fieldAddress
	 * @param charSet
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	// public List<PoliticalAddress> getProvinceAddress(
	// FeatureCollection<SimpleFeatureType, SimpleFeature> features,
	// String fieldAddress, String charSet) throws UnsupportedEncodingException
	// {
	// int length = features.size();
	// if (length <= 0)
	// return null;
	// List<PoliticalAddress> paddresses=null;
	// FeatureIterator<SimpleFeature> featureIterator = features.features();
	// while (featureIterator.hasNext()) {
	// SimpleFeature feature = featureIterator.next();
	// String fieldValue = new
	// String(feature.getAttribute(fieldAddress).toString().getBytes(charSet),"GBK");
	// Geometry geometry=(Geometry) feature.getDefaultGeometry();
	// //行政区划为面要素。
	//
	// }
	// }
	public PostGISDataOperator() {
		super();
	}

	private DataStore pgdataStore;

	/**
	 * 初始化dataStore中。
	 */
	@SuppressWarnings("unused")
	private void initDataStore() {
		try {
			if (pgdataStore == null)
				pgdataStore = DataStoreFinder.getDataStore(params);
			if (pgdataStore != null)
				logger.info("POSTGIS链接成功！");
			else
				logger.info("POSTGIS链接错误!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把shp文件插入到postgis中。
	 * 
	 * @param file
	 * @param tableName
	 * @throws IOException
	 * @throws SQLException
	 */
	public synchronized void insertShp(String file, String tableName)
			throws IOException, SQLException {
		// 首先或得SimpleFeatureType;
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = getFeatureSourceByShp(file);
		SimpleFeatureType type1 = featureSource.getSchema();
		((JDBCDataStore)pgdataStore).getDataSource().getConnection().setAutoCommit(false);
		try {
		
			pgdataStore.createSchema(type1);
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			FeatureSource<SimpleFeatureType, SimpleFeature> fsource = pgdataStore
					.getFeatureSource(tableName);
			if (fsource instanceof FeatureStore<?, ?>) {
				Transaction t = new DefaultTransaction("create");
				FeatureStore<SimpleFeatureType, SimpleFeature> fstore = (FeatureStore<SimpleFeatureType, SimpleFeature>) fsource;
				fstore.setTransaction(t);
				try {
					fstore.addFeatures(featureSource.getFeatures());
					t.commit();
				} catch (IOException e) {
					e.printStackTrace();
					try {
						t.rollback();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} finally {
					t.close();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public FeatureStore<SimpleFeatureType,SimpleFeature> getFeatureStore(String tableName) throws IOException{
		return (FeatureStore<SimpleFeatureType, SimpleFeature>) pgdataStore
				.getFeatureSource(tableName);
	}

	
	/**
	 * 向postgis中添加数据
	 * 
	 * @param tableName
	 * @param fieldMaps
	 * @return
	 */
	public int addShpToPostGIS(String tableName, Map<String, String> fieldMaps,FeatureCollection<SimpleFeatureType, SimpleFeature> fcollections) {
		MemoryFeatureCollection mfcollections = null;
		Transaction t = new DefaultTransaction("add");
		try {
			FeatureSource<SimpleFeatureType, SimpleFeature> fsource = pgdataStore
					.getFeatureSource(tableName);
			mfcollections = new MemoryFeatureCollection(fsource.getSchema());
			if (fsource instanceof FeatureStore<?, ?>) {
				FeatureStore<SimpleFeatureType, SimpleFeature> fstore = (FeatureStore<SimpleFeatureType, SimpleFeature>) fsource;
				fstore.setTransaction(t);
				FeatureIterator<SimpleFeature> fit = fcollections.features();
				while (fit.hasNext()) {
					SimpleFeature feature = fit.next();
					Iterator<Entry<String, String>> fmit = fieldMaps.entrySet()
							.iterator();
					SimpleFeature resultFeature = new SimpleFeatureImpl(null,
							feature.getFeatureType(), feature.getIdentifier());
					while (fmit.hasNext()) {
						Entry<String, String> fentry = fmit.next();
						String dbfield = fentry.getKey();
						String shpfield = fentry.getValue();
						resultFeature.setAttribute(dbfield,
								feature.getAttribute(shpfield));
					}
					resultFeature.setDefaultGeometry(feature
							.getDefaultGeometry());
				}
				fstore.addFeatures(mfcollections);
				fit.close();
				t.commit();
			}

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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mfcollections.size();
	}

	/**
	 * 从postgis里读取要素
	 * 
	 * @param tableName
	 * @param spatialFieldName
	 */
	public FeatureCollection<SimpleFeatureType, SimpleFeature> readFeatureByPostGIS(
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
	private Map<String, String> params;

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	private Connection connection;

	public Connection getConnection() throws IOException,
			SQLException {
		if (connection == null || connection.isClosed()) {
			pgdataStore = pgdataStore == null ? DataStoreFinder
					.getDataStore(params) : pgdataStore;
			connection = ((JDBCDataStore) pgdataStore).getDataSource().getConnection();
		}
		return connection;
	}
	public SQLDialect getSQLDialect() throws IOException{
		pgdataStore = pgdataStore == null ? DataStoreFinder
				.getDataStore(params) : pgdataStore;
		return 		((JDBCDataStore)pgdataStore).getSQLDialect();
	}
}
