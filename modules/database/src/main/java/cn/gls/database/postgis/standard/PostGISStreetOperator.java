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
 * @ClassName: PostGISStreetOperator.java
 * @Description TODO
 * @Date 2012-6-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public class PostGISStreetOperator extends PostGISBaseOperator implements
		IStandardTableOperator {

	public PostGISStreetOperator() {
		super();
		type = DataType.STREET_TYPE;
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
 * @Desciptor:必选字段name 字段。路名。
 * 原始必有字段：province_name,city_name,county_name.
 * 其他为可以选字段:city_code,a_type,town_name,village_name
 */
	@Override
	protected SimpleFeature mapingAttributeToTableField(
			SimpleFeature resultFeature, SimpleFeature feature,
			Map<String, String> fieldMap) {
		List<String> strs = new ArrayList<String>();
		sonName = (String) feature.getAttribute(fieldMap.get("street_name"));
		/////////////////////////////////////////下面是必填的字段
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
		// System.out.println(sonName);
		/////////////////////////////////////////////////////////////////////////// 寻找父类
		List<String> fatherNames = selectFatherBySon(sonName,
				(Integer) resultFeature.getAttribute("city_code"));
		String fatherName = fatherNames != null && fatherNames.size() > 0 ? selectFatherBySon(
				sonName, (Integer) resultFeature.getAttribute("city_code"))
				.get(0) : null;
		if (fatherName != null) {
			if (!fieldMap.containsKey("village_name")) {
				String[] villageSuffixs = databaseProperties.getProperty(
						"villageSuffix").split("、");
				boolean iscontainVillage = false;
				for (String vstr : villageSuffixs) {
					if (fatherName.contains(vstr)) {
						iscontainVillage = true;
						break;
					}
				}
				if (iscontainVillage)
					resultFeature.setAttribute("village_name", fatherName);

			}
			if (!fieldMap.containsKey("town_name")) {
				String[] townSuffixs = databaseProperties.getProperty(
						"townSuffix").split("、");
				boolean iscontaintown = false;
				for (String vstr : townSuffixs) {
					if (fatherName.contains(vstr)) {
						iscontaintown = true;
						break;
					}
				}
				if (iscontaintown)
					resultFeature.setAttribute("town_name", fatherName);
			}
			// 确定是否还有county_name的可能
			else if (!fieldMap.containsKey("county_name")
					&& (resultFeature.getAttribute("county_name") == null)) {
				List<String> ffatherNames = selectFatherBySon(fatherName,
						(Integer) resultFeature.getAttribute("city_code"));
				if (ffatherNames != null && ffatherNames.size() > 0) {
					String ffatherName = ffatherNames.get(0);
					if (!ffatherName
							.equalsIgnoreCase(selectCityNameByCityCode((Integer) resultFeature
									.getAttribute("city_code")))) {
						resultFeature.setAttribute("county_name", ffatherName);
					}
				}
			}
		}
		////选填的字段
		if (!fieldMap.containsKey("a_type")) {
			resultFeature.setAttribute("a_type", this.dataType);
		}
		
		if (!((String) resultFeature.getAttribute("province_name"))
				.equalsIgnoreCase((String) resultFeature
						.getAttribute("city_name")))
			strs.add((String) resultFeature.getAttribute("province_name"));

		strs.add((String) resultFeature.getAttribute("city_name"));
		strs.add((String) resultFeature.getAttribute("county_name"));
		strs.add((String)resultFeature.getAttribute("town_name"));
		strs.add((String) resultFeature.getAttribute("village_name"));
		strs.add(sonName);
		resultFeature
				.setAttribute("st_address", StringUtils.mergeObjects(strs));
		resultFeature.setDefaultGeometry(feature.getDefaultGeometry());
		return resultFeature;
	}

}
