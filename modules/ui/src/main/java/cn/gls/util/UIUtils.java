package cn.gls.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;

import cn.gls.data.Place;


/**
 * 
 * @date 2012-7-31
 * @author "Daniel Zhang"
 * @update 2012-7-31
 * @description 界面工具类
 * 
 */
public class UIUtils {
	//文件过滤器
	public static FileFilter filter = new FileFilter() {
		@Override
		public String getDescription() {
			return ".shp";
		}

		@Override
		public boolean accept(File f) {
			String fileName = f.getName();
			return fileName.endsWith(".shp");
		}
	};
	
	public static JFileChooser getFileChooser(){
		JFileChooser fileChooser=new JFileChooser("D:\\Dropbox\\Public\\gls\\modules\\database\\src\\test\\resources\\shp");
	   fileChooser.setFileFilter(filter);
	   return fileChooser;
	}
	
	public static int getLevel(String placeName){
		
		if(placeName.equalsIgnoreCase("城市"))
			return 3;
		if(placeName.equalsIgnoreCase("区县"))
			return 4;
		if(placeName.equalsIgnoreCase("乡镇"))
			return 5;
		if(placeName.equalsIgnoreCase("村庄"))
			return 6;
		if(placeName.equalsIgnoreCase("街道"))
			return 8;
		if(placeName.equalsIgnoreCase("社区"))
			return 9;
		if(placeName.equalsIgnoreCase("大厦"))
			return 10;
		else
			return 11;
	}
	public static Object[][] getTables(Set<Place> places){
		int s=places.size();
		 Object[][] pdatas=new Object[s][4];
		 int j=0;
		for(Place place:places){
			Object[] obj=new Object[4];
			obj[0]=place.getName();
			obj[1]=place.getPlaceLevel();
			obj[2]=place.getCityCode();
			obj[3]=place.getPlaceType();
			pdatas[j]=obj;
			j++;
		}
		return pdatas;
	}
	public static List<String> getColumnsList(FeatureCollection<SimpleFeatureType,SimpleFeature> features){
		List<String> columns = new ArrayList<String>();
		 List<AttributeDescriptor> attributes = features.getSchema()
	                .getAttributeDescriptors();
	        for (AttributeDescriptor attribute : attributes) {
	            if (!(attribute instanceof GeometryDescriptor)) {
	                columns.add(attribute.getName().toString());
	            }
	        }
	        return columns;
	}
	public static Object[] getColumns(FeatureCollection<SimpleFeatureType,SimpleFeature> features){
              
	        return getColumnsList(features).toArray();
	}
	public static Object[][] getTables(FeatureCollection<SimpleFeatureType,SimpleFeature> features){
		SimpleFeatureType featureType= features.getSchema();
		List<String> columns = new ArrayList<String>();
		 List<AttributeDescriptor> attributes = featureType
	                .getAttributeDescriptors();
	        for (AttributeDescriptor attribute : attributes) {
	            if (!(attribute instanceof GeometryDescriptor)) {
	                columns.add(attribute.getName().toString());
	            }
	        }
	     FeatureIterator<SimpleFeature> featureIterator=features.features();
	     Object[][] datas=new Object[features.size()][columns.size()];
	     int j=0;
	     while(featureIterator.hasNext()){
	    	 SimpleFeature feature=featureIterator.next();
	    	 Object[] obj=new Object[columns.size()];
	    	 int c=columns.size();
	    	 for(int i=0;i<c;i++){
	    		 obj[i]=feature.getAttribute(columns.get(i));
	    	 }
	    	 datas[j]=obj;
	    	 j++;
	     }
	     return datas;
	}
	 @SuppressWarnings("unchecked")
	public static Set<Place> getPlaces(DefaultTableModel model){
	  
	Vector<Object> vectors=	model.getDataVector();
	   Set<Place> places=new HashSet<Place>();
	    for(Object obj:vectors){
	    	Vector<Object> v=(Vector<Object>)obj;
	    	Object[] ps=v.toArray();
	    	Place p=new Place((String)ps[0],(Integer)ps[1],(Integer) ps[2]);
	    	p.setPlaceType((String)ps[3]);
	    	places.add(p);
	    	
	    }
	    return places;
	}
	public static void clearTable(JTable table){
		table.setModel(new DefaultTableModel());
	}
	
}
