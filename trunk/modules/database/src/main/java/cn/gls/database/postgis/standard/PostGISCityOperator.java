package cn.gls.database.postgis.standard;

import java.util.Map;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.database.data.DataType;

/**
 * @ClassName: PostGISCityOperator.java
 * @Description  TODO
 * @Date  2012-6-28
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-28
 */
public class PostGISCityOperator extends PostGISBaseOperator {

	/**
	 *@Title:PostGISCityOperator
	 *@Description:TODO
	 *@param type
	 *@return:
	 * @throws
	 */
	public PostGISCityOperator() {
		super();
		type=DataType.CITY_TYPE;
	}
	
	@Override
	public int insert(
			FeatureCollection<SimpleFeatureType, SimpleFeature> fcollection,
			Map<String, String> fieldMap, String dbTableName,String dataType, String sCityName) {        
		return super.insert(fcollection, fieldMap, dbTableName,dataType, sCityName);
	}
/**
 * @descriptor:
 * 必须映射的字段name字段
 * 选择行映射的字段：a_type，city_code、province_name字段。
 */
	@Override
	protected SimpleFeature mapingAttributeToTableField(
	        SimpleFeature resultFeature, SimpleFeature feature,
	        Map<String, String> fieldMap) {
	    // 不管是不是标准词都要判断一下
	    ////////////必须映射name字段。这个就是城市名称字段
            String st_name = (String) getSqlMapClientTemplate().queryForObject(
                    "assist.selectStandardCityName",
                    feature.getAttribute(fieldMap.get("name")).toString());
            if (st_name != null)
                resultFeature.setAttribute("name", st_name);
            ///////////可选填字段
            if (!fieldMap.containsKey("province_name"))
                resultFeature.setAttribute("province_name",
                        selectProvinceByCity(resultFeature.getAttribute("name")
                                .toString()));
            if (!fieldMap.containsKey("a_type")){
            	if(dataType==null)
                resultFeature.setAttribute("a_type", "市行政区");
            	else
            		resultFeature.setAttribute("a_type", dataType);
            }
            if (!fieldMap.containsKey("city_code")) {
                cityCode = selectCityCodeByCityName((String) resultFeature
                        .getAttribute("name"));
                if (cityCode != 0)
                    resultFeature.setAttribute("city_code",
                            String.valueOf(cityCode));
            }
            if (!fieldMap.containsKey("st_address")) {
                stAddress = (resultFeature.getAttribute("province_name") != null && (!((String) resultFeature
                        .getAttribute("province_name"))
                        .equalsIgnoreCase((String) resultFeature
                                .getAttribute("name")))) ? (String) resultFeature
                        .getAttribute("province_name")
                        + (String) resultFeature.getAttribute("name")
                        : (String) resultFeature.getAttribute("name");
                resultFeature.setAttribute("st_address", stAddress);
            }
            resultFeature.setDefaultGeometry(feature.getDefaultGeometry());
	    return resultFeature;
	}

}
