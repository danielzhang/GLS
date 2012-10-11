package cn.gls.database.postgis.processing;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.shp.util.ShpUtils;
import cn.gls.database.util.FeaturePreProcessing;

import junit.framework.TestCase;

/**
 * @ClassName: FeatureUtilsTest.java
 * @Description 对feature的测试
 * @Date 2012-6-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public class FeatureUtilsTest extends TestCase {
	private FeaturePreProcessing featureProcessing;

	public FeatureUtilsTest() {
		super();
		LoadApplicationContext loadApplicationContext = LoadApplicationContext
				.getInstance();
		featureProcessing = (FeaturePreProcessing) loadApplicationContext
				.getBean("featureProcessing");
	}
	public void unionFeatures() {
		long c = System.currentTimeMillis();
		FeatureCollection<SimpleFeatureType, SimpleFeature> resultFeatures = featureProcessing
				.unionSimpleFeatures(
						ShpUtils.getFeatureSourceByShp("src/test/resources/shp/guodao.shp"),
						"Name",ShpUtils.getFeatureSourceByShp("src/test/resources/shp/shanghaiquxia.shp"),"Name");
		System.out.println(System.currentTimeMillis() - c);
		System.out.println(resultFeatures.size());
	}
	public void testFeature(){
	    unionFeatures();
	}
}
