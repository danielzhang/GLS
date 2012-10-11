package cn.gls.database.operator;

import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * 
 * @ClassName IShpFileProcessing.java
 * @Createdate 2012-6-27
 * @Description 对shp文件的处理,清洗,对poi的处理类
 * @Version 1.0
 * @Update 2012-6-27
 * @author "Daniel Zhang"
 * 
 */
public interface IFeaturePreProcessing {
	/**
	 * 合并Name相同的
	 * 
	 * @Param IShpFileProcessing
	 * @Description TODO
	 * @return FeatureCollection<SimpleFeatureType,SimpleFeature>
	 * @param ofeatures
	 * @return
	 */
	FeatureCollection<SimpleFeatureType, SimpleFeature> unionSimpleFeatures(
			FeatureSource<SimpleFeatureType, SimpleFeature> ofeatures,
			String fieldName,
			FeatureSource<SimpleFeatureType, SimpleFeature> countyFeatureSource,
			String countyFieldName);

//	/**
//	 * 
//	 * @Param IShpFileProcessing
//	 * @Description TODO
//	 * @return FeatureCollection<SimpleFeatureType,SimpleFeature>
//	 * @param ofeatures
//	 * @param  根据某一个字段进行清洗
//	 * @return
//	 */
//	FeatureCollection<SimpleFeatureType, SimpleFeature> clearSimpleFeatures(
//			FeatureCollection<SimpleFeatureType, SimpleFeature> ofeatures,String fieldName);
	
	/**
	 * 对poi导入前的预处理，这是必须经过的步骤。
	 * @param poiFeatures
	 * @param countyFeatures
	 * @param cityName
	 * @return
	 */
	public FeatureCollection<SimpleFeatureType, SimpleFeature> preDATAProcessing(
			FeatureCollection<SimpleFeatureType, SimpleFeature> ofeature,
			FeatureCollection<SimpleFeatureType, SimpleFeature> countyFeatures,String countyFieldName,
			String cityName);
	  
}
