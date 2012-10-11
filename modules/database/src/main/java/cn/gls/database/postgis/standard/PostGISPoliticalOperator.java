package cn.gls.database.postgis.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.database.data.DataType;
import cn.gls.database.operator.IStandardTableOperator;
import cn.gls.util.StringUtils;

/**
 * @ClassName: PostGISPoliticalOperator.java
 * @Description 与行政区表的操作
 * @Date 2012-6-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public class PostGISPoliticalOperator extends PostGISBaseOperator implements
		IStandardTableOperator {

	public PostGISPoliticalOperator() {
		super();
		type = DataType.POLITICAL_TYPE;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.IStandardTableOperator#insert(org.geotools.feature.FeatureCollection,
	 *      java.util.Map, java.lang.String, java.lang.String)
	 */
	@Override
	public int insert(
			FeatureCollection<SimpleFeatureType, SimpleFeature> fcollection,
			Map<String, String> fieldMap, String dbTableName, String dataType,
			String sCityName) {

		return super.insert(fcollection, fieldMap, dbTableName, dataType,
				sCityName);
	}

	/**
	 * @descriptor:映射可选字段：village_name,town_name二者可选。
	 *   原始必有字段：province_name,city_name，county_name
	 *                 
	 */
	@Override
	protected SimpleFeature mapingAttributeToTableField(
			SimpleFeature resultFeature, SimpleFeature feature,
			Map<String, String> fieldMap) {
		
		List<String> strs = new ArrayList<String>();
         ///////////////////////////////////////下面是必须的字段
		if (!fieldMap.containsKey("county_name")) {
			resultFeature.setAttribute("county_name",
					feature.getAttribute("county_name"));
		}
		if (!fieldMap.containsKey("city_name")) {
			if (sCityName != null)
				resultFeature.setAttribute("city_name", sCityName);
		}
		if (!fieldMap.containsKey("city_code")) {
			if (sCityName != null) {
				cityCode = selectCityCodeByCityName(sCityName);
				resultFeature.setAttribute("city_code", cityCode);
			}
		}
		if (!fieldMap.containsKey("province_name")) {
			String provinceName = selectProvinceByCity(resultFeature
					.getAttribute("city_name").toString());
			resultFeature.setAttribute("province_name", provinceName);
		}
		// //////////////////////////////////首先如果有村级行政区，但是没有镇级别的行政区。这就要去父子级里寻找了。
		if (fieldMap.containsKey("village_name")
				&& !fieldMap.containsKey("town_name")) {
			List<String> fathers = selectFatherBySon(
					(String) resultFeature.getAttribute("village_name"),
					(Integer) resultFeature.getAttribute("city_code"));
			if (fathers != null
					&& !fathers.contains((String) resultFeature
							.getAttribute("county_name"))) {
				// 默认取其第一个父类
				resultFeature.setAttribute("town_name", fathers.get(0));
			}
		}
        ///选填的字段
		if (!fieldMap.containsKey("a_type")) {
			if (dataType == null) {
				if (resultFeature.getAttribute("village_name") != null)
					resultFeature.setAttribute("a_type", "乡村行政区划");
				else
					resultFeature.setAttribute("a_type", "区县行政区划");
			} else
				resultFeature.setAttribute("a_type", dataType);
		}

		if (((String) resultFeature.getAttribute("province_name"))
				.equalsIgnoreCase((String) resultFeature
						.getAttribute("city_name")))
			strs.add((String) resultFeature.getAttribute("province_name"));
		strs.add((String) resultFeature.getAttribute("city_name"));
		strs.add((String) resultFeature.getAttribute("county_name"));
		strs.add((String) resultFeature.getAttribute("town_name"));
		strs.add((String) resultFeature.getAttribute("village_name"));
		resultFeature
				.setAttribute("st_address", StringUtils.mergeObjects(strs));
		resultFeature.setDefaultGeometry(feature.getDefaultGeometry());
		return resultFeature;
	}
}
