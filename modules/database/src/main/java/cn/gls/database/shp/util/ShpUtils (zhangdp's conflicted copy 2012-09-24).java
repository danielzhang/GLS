package cn.gls.database.shp.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * @ClassName: ShpUtil.java
 * @Description 与shp文件相关的操作类
 * @Date 2012-6-26
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-26
 */
public class ShpUtils {
	/**
	 * 读取shp文件
	 * 
	 */
	public static FeatureCollection<SimpleFeatureType, SimpleFeature> readShpfile(
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
	public static FeatureSource<SimpleFeatureType, SimpleFeature> getFeatureSourceByShp(
			String filepath) {
		if (filepath == null || "".equals(filepath))
			return null;
		// URL url=ShpUtils.class.getClassLoader().getResource(filepath);
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

	public static SimpleFeatureType getSimpleFeatureTypeByShp(String filePath) {
		File file = new File(filePath);
		ShapefileDataStore shapeDataStore = null;
		try {
			shapeDataStore = new ShapefileDataStore(file.toURL());
			return shapeDataStore.getSchema();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
