package cn.gls.database.postgis.standard;

import java.util.Map;

import org.geotools.feature.FeatureCollection;

import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;


import cn.gls.database.data.DataType;
import cn.gls.database.operator.IStandardTableOperator;

/**
 * @ClassName: PostGISProvinceOperator.java
 * @Description TODO
 * @Date 2012-6-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public class PostGISProvinceOperator extends PostGISBaseOperator implements
		IStandardTableOperator {
	
	public PostGISProvinceOperator() {
		super();
		type = DataType.PROVINCE_TYPE;
	}

	// 唯一标示
	/**
	 * 插入省的数据 (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.IStandardTableOperator#insert(org.geotools.feature.FeatureCollection,
	 *      java.util.Map, java.lang.String, java.lang.String)
	 */


	public int insert(
			FeatureCollection<SimpleFeatureType, SimpleFeature> fcollection,
			Map<String, String> fieldMap, String dbTableName,String dataType, String sCityName) {
		
		return super.insert(fcollection, fieldMap, dbTableName,dataType, sCityName);
	}

	/**
	 * 映射特殊的属性到列中
	 * @descriptor:必选字段为name；对应于province_name
	 * @param resultFeature
	 * @param feature
	 * @param fieldMap
	 */
	@Override
	protected SimpleFeature mapingAttributeToTableField(
			SimpleFeature resultFeature, SimpleFeature feature,
			Map<String, String> fieldMap) {
		if (!fieldMap.containsKey("province_code"))
			resultFeature.setAttribute("province_code",
					selectProvinceCodeByProvinceName((String) feature
							.getAttribute(fieldMap.get("name"))));
		
//		System.out.println(feature.getAttribute(fieldMap.get("name"))
//				+ "------" + resultFeature.getAttribute("province_code"));
		if (!fieldMap.containsKey("a_type")){
			if(dataType==null)
			resultFeature.setAttribute("a_type", "省行政区");
			else
				resultFeature.setAttribute("a_type", dataType);			
		}
		if (!fieldMap.containsKey("st_address"))
			resultFeature.setAttribute("st_address",
					(String) feature.getAttribute(fieldMap.get("name")));
		resultFeature.setDefaultGeometry(feature.getDefaultGeometry());
		return resultFeature;
	}

}
