package cn.gls.database;


import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;


/**
 * @ClassName: IOperateGeoData.java
 * @Description  对空间数据的操作
 * @Date  2012-6-13
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-13
 */
public interface IOperateGeoData {
	FeatureCollection<SimpleFeatureType,SimpleFeature> readShpfile(String filepath);
        
}
