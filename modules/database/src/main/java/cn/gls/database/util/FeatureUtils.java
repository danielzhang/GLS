package cn.gls.database.util;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * 
 * @ClassName FeatureUtils.java
 * @Createdate 2012-7-30
 * @Description 关于feature操作的工具类
 * @Version 1.0
 * @Update 2012-7-30 
 * @author "Daniel Zhang"
 *
 */
public class FeatureUtils {

/**
 * @descriptor:按照某一个列进行清洗。大部分是通过name相同或不为null来判断。
 * @param ofeature:原始要素集合
 * @param fieldName:原始要素的列名称.
 */
public static FeatureCollection<SimpleFeatureType, SimpleFeature> clearSimpleFeatures(
        FeatureCollection<SimpleFeatureType, SimpleFeature> ofeatures,
        String fieldName) {
    FeatureIterator<SimpleFeature> featureIterator = ofeatures.features();

    FeatureCollection<SimpleFeatureType, SimpleFeature> keepFeatureCollection = FeatureCollections
            .newCollection();

    while (featureIterator.hasNext()) {
        SimpleFeature feature = featureIterator.next();
        if (feature.getAttribute(fieldName) == null
                || "".equalsIgnoreCase((String) feature.getAttribute(fieldName))) {
         
            continue;

        }
        keepFeatureCollection.add(feature);
    }
    featureIterator.close();
    return keepFeatureCollection;
}
}
