package cn.gls.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JTable;

import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.NameImpl;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;

import cn.gls.data.FatherAndSon;
import cn.gls.data.PinyinPlace;

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
    public static String[] getColumnNames(Class<?> clazz){
    	Field[] fields=clazz.getDeclaredFields();
    	String[] columns=new String[fields.length];
    	int i=0;
    	for(Field field:fields){
    		columns[i]=field.getName();
    		i++;
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
    public static Object[][] getTableData(Object[] places,Class<?> clazz){
    	if(places==null||clazz==null)
    		return null;
    	int s=places.length;
         Object[][] tables=null;
         if(clazz.equals(FatherAndSon.class)){
    	 tables=new Object[s][3];
    	int k=0;
    	for(Object fatherAndSon : places){
    		 Object[] objs=new Object[3];
    		 objs[0]=((FatherAndSon)fatherAndSon).getSonName();
    		 objs[1]=((FatherAndSon)fatherAndSon).getFatherName();
    		 objs[2]=((FatherAndSon)fatherAndSon).getCityCode();
    		 tables[k]=objs;
    		 k++;
    	}
         }
         else if(clazz.equals(PinyinPlace.class)){
        		int k=0;
        		 tables=new Object[s][3];
            	for(Object place:places){
            		Object[] objs=new Object[3];
            		objs[0]=((PinyinPlace)place).getName();
            		objs[1]=((PinyinPlace)place).getpName();
            		objs[2]=((PinyinPlace)place).getCityCode();
            		tables[k]=objs;
            		k++;
            	}
         }
    	return tables;
    }
  
    
    public FeatureSource<SimpleFeatureType, SimpleFeature> changeTableToFeatureSource(JTable table){
    	FeatureSource<SimpleFeatureType, SimpleFeature> featureSources=null;
    	
    	return featureSources;
    }
}
