package cn.gls.database.util;

import java.util.ArrayList;
import java.util.List;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.NameImpl;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;

/**
 * @date 2012-7-30
 * @author "Daniel Zhang"
 * @update 2012-7-30
 * @description 与文件shp相互作用的工具类
 */
public class ShpUtils {
    public static List<String> getColumnNames(String filePath) {
        if (filePath == null || "".equalsIgnoreCase(filePath))
            return null;
        List<String> columns = new ArrayList<String>();
        SimpleFeatureType featureType = cn.gls.database.shp.util.ShpUtils
                .getSimpleFeatureTypeByShp(filePath);
        List<AttributeDescriptor> attributes = featureType
                .getAttributeDescriptors();
        for (AttributeDescriptor attribute : attributes) {
            if (!(attribute instanceof GeometryDescriptor)) {
                columns.add(attribute.getName().toString());
            }
        }
        return columns;
    }

    public static Object[][] getTableData(String filePath, List<String> columns) {
        FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = cn.gls.database.shp.util.ShpUtils
                .readShpfile(filePath);
        return getTableData(featureCollection, columns);
    }

    public static Object[][] getTableData(
            FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection,
            List<String> columns) {
        FeatureIterator<SimpleFeature> featureIterator = featureCollection
                .features();
        int s = featureCollection.size();
        Object[][] tables = new Object[s][columns.size()];
        int j = 0;
        while (featureIterator.hasNext()) {
            SimpleFeature feature = featureIterator.next();
            Object[] objs = new Object[columns.size()];
            for (int k = 0; k < columns.size(); k++) {
                objs[k] = feature.getAttribute(new NameImpl(columns.get(k)));
            }
            tables[j] = objs;
            j++;

        }
        featureIterator.close();
        return tables;

    }
}
