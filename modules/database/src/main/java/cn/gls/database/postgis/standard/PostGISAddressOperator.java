package cn.gls.database.postgis.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.data.Place;

import cn.gls.database.data.DataType;

import cn.gls.place.dao.IPlaceDao;

import cn.gls.seg.ISegAddressEngine;
import cn.gls.util.StringUtils;

/**
 * 
 * @ClassName PostGISPOIOperator.java
 * @Createdate 2012-7-1
 * @Description postgis的poi导入
 * @Version 1.0
 * @Update 2012-7-1 
 * @author "Daniel Zhang"
 *
 */
/**
 * 
 * @ClassName PostGISPOIOperator.java
 * @Createdate 2012-7-1
 * @Description TODO
 * @Version 1.0
 * @Update 2012-7-1
 * @author "Daniel Zhang"
 * 
 */
public class PostGISAddressOperator extends PostGISBaseOperator {
	private ISegAddressEngine segAddressEngine;
	private IPlaceDao placeDao;

	public void setPlaceDao(IPlaceDao placeDao) {
		this.placeDao = placeDao;
	}

	public PostGISAddressOperator() {
		super();
		type = DataType.ADDRESS_TYPE;
	}

	public void setSegAddressEngine(ISegAddressEngine segAddressEngine) {
		this.segAddressEngine = segAddressEngine;
	}

	@Override
	public int insert(
			FeatureCollection<SimpleFeatureType, SimpleFeature> fcollection,
			Map<String, String> fieldMap, String dbTableName, String dataType,
			String sCityName) {

		return super.insert(fcollection, fieldMap, dbTableName, dataType,
				sCityName);
	}

	/**
	 * 外部必映射字段address。 其中street_name,community_name,building_name,poi_name必选其一。
	 * a_type,city_code 是可选的。
	 * 
	 * feature 必含字段为province_name,city_name,county_name,.....
	 * 
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.postgis.standard.PostGISBaseOperator#mapingAttributeToTableField(org.opengis.feature.simple.SimpleFeature,
	 *      org.opengis.feature.simple.SimpleFeature, java.util.Map)
	 */
	@Override
	protected SimpleFeature mapingAttributeToTableField(
			SimpleFeature resultFeature, SimpleFeature feature,
			Map<String, String> fieldMap) {
		// ///////////////////////////////////////////////////////////////////
		// 首先处理现有字段
		if (fieldMap.containsKey("community_all")) {
			String communityAll = (String) resultFeature
					.getAttribute("community_all");
			String communityName = StringUtils.getPlaceName(communityAll);
			if (!communityAll.equalsIgnoreCase(communityName)) {
				String communitySuffix = communityAll
						.substring(communityAll.indexOf(communityName)
								+ communityName.length(), communityAll.length());
				resultFeature.setAttribute("community_suffix", communitySuffix);
			}
			resultFeature.setAttribute("community_name", communityName);
		}
		if (fieldMap.containsKey("building_all")) {
			String buildingAll = (String) resultFeature
					.getAttribute("building_all");
			String buildingName = StringUtils.getPlaceName(buildingAll);
			if (!buildingAll.equalsIgnoreCase(buildingName)) {
				String buildingSuffix = buildingAll.substring(
						buildingAll.indexOf(buildingName)
								+ buildingName.length(), buildingAll.length());
				resultFeature.setAttribute("building_suffix", buildingSuffix);
			}
			resultFeature.setAttribute("building_name", buildingName);
		}
		// 总的字符串
		List<String> strs = new ArrayList<String>();
		// 定义最大级别
		int maxLevel = 4;

		// sonName = (String) feature.getAttribute(fieldMap.get("name"));
		// //////////////////////////////////////feature必含字段-----------------------------------地址最低为省+市+区县
		if (!fieldMap.containsKey("province_name"))
			resultFeature.setAttribute("province_name",
					feature.getAttribute("province_name"));
		if (!fieldMap.containsKey("city_name"))
			resultFeature.setAttribute("city_name",
					feature.getAttribute("city_name"));
		if (!fieldMap.containsKey("county_name"))
			resultFeature.setAttribute("county_name",
					feature.getAttribute("county_name"));

		String cityName = (String) resultFeature.getAttribute("city_name");
		if (cityName == null && sCityName == null) {
			resultFeature.setAttribute("city_code", 0);
			return resultFeature;
			// throw new GLSPOIDataOperatorException("这条POI信息无效。");
		}
		int cityCode = selectCityCodeByCityName(cityName);
		// // 获得name的等级
		// List<Place> namePlaces = placeDao.getPlacesByName(
		// sonName, cityCode);
		// // 默认情况为第一个地名词为其级别
		// if (namePlaces!=null&&namePlaces.size() > 0) {
		// int nameLevel = namePlaces.get(0).getPlaceLevel();
		// switch (nameLevel) {
		// case 9:
		// resultFeature.setAttribute("community_name", sonName);
		// resultFeature.setAttribute("community_all", sonName);
		// if (maxLevel < 9)
		// maxLevel = 9;
		// break;
		// case 10:
		// resultFeature.setAttribute("landmark_name", sonName);
		// resultFeature.setAttribute("landmark_all", sonName);
		// if (maxLevel < 10)
		// maxLevel = 10;
		// break;
		// }
		// }

		if (!fieldMap.containsKey("city_code"))
			resultFeature.setAttribute("city_code", cityCode);

		// //////////////////////////////必映射字段/////////////////////////////////////////////////////////
		resultFeature.setDefaultGeometry(feature.getDefaultGeometry());
		String address = (String) resultFeature.getAttribute("address");
		if (!fieldMap.containsKey("a_type")) {
			resultFeature.setAttribute("a_type", this.dataType);
		}
		// 当address为空时
		// if (address == null || "".equalsIgnoreCase(address))
		// return resultFeature;
		if (address != null && !"".equalsIgnoreCase(address)) {
			address = StringUtils.ToDBC(address);
			// System.out.println(address);

			resultFeature.setAttribute("address", address);

			// address非空
			List<Place> places = segAddressEngine.standardPlace(address,
					cityCode);
			if(places!=null&&places.size()>0)
			// 要从村开始。
			for (Place place : places) {
				int placeLevel = place.getPlaceLevel();
				switch (placeLevel) {
				case 5:
					resultFeature.setAttribute("town_name", place.getName());
					if(maxLevel<5)
					{
						maxLevel=5;
						sonName=place.getName();
					}
					break;
				case 6:
					resultFeature.setAttribute("village_name", place.getName());
					resultFeature.setAttribute("village_suffix",
							place.getSuffix());
					resultFeature.setAttribute("village_all",
							place.getAllName());
					if (maxLevel < 6) {
						maxLevel = 6;
						sonName = place.getName();
					}

					break;
				case 8:
					resultFeature.setAttribute("street_name", place.getName());
					resultFeature.setAttribute("street_suffix",
							place.getSuffix());
					resultFeature
							.setAttribute("street_all", place.getAllName());
					if (maxLevel < 8) {
						maxLevel = 8;
						sonName = place.getName();
					}
					break;
				case 9:
					resultFeature.setAttribute("community_name",
							place.getName());
					resultFeature.setAttribute("community_suffix",
							place.getSuffix());
					resultFeature.setAttribute("community_all",
							place.getAllName());
					if (maxLevel < 9) {
						maxLevel = 9;
						sonName = place.getName();
					}
					break;
				case 10:
					resultFeature
							.setAttribute("building_name", place.getName());
					resultFeature.setAttribute("building_suffix",
							place.getSuffix());
					resultFeature.setAttribute("building_all",
							place.getAllName());
					if (maxLevel < 10) {
						maxLevel = 10;
						sonName = place.getName();
					}
					break;
				case 11:
					resultFeature.setAttribute("poi_name", place.getAllName());
					if (maxLevel < 11) {
						maxLevel = 11;
						sonName = place.getName();
					}
					break;
				}
			}
			// ///////////////////////////////////////////////////////////////////////////////////////////////
			// 从这里开始要求通过子父类来搞了。这里主要处理的是5,6,8,(9,10)的关系
			// 寻找父类
			List<String> fatherNames = selectFatherBySon(sonName,
					(Integer) resultFeature.getAttribute("city_code"));
			if (fatherNames != null) {
				switch (maxLevel) {
				case 10:
					// 如果是10的话，可能的情况为9,8,6
					// 判断父类级别
					for (String fatherName : fatherNames) {
						List<Place> fatherPlaces = placeDao.getPlacesByName(
								fatherName, cityCode);
						if(fatherPlaces!=null)
						for (Place place : fatherPlaces) {
							switch (place.getPlaceLevel()) {
							case 9:
								resultFeature.setAttribute("community_name",
										place.getName());
								resultFeature.setAttribute("community_all",
										place.getAllName());
								break;
							case 8:
								resultFeature.setAttribute("street_name",
										place.getName());
								resultFeature.setAttribute("street_all",
										place.getAllName());
								break;
							case 6:
								resultFeature.setAttribute("village_name",
										place.getName());
								resultFeature.setAttribute("village_all",
										place.getAllName());
								break;
							case 5:
								resultFeature.setAttribute("town_name", place.getName());
								break;
							}
						}
					}

					break;
				case 9:
					// 判断父类级别
					for (String fatherName : fatherNames) {
						List<Place> fatherPlaces = placeDao.getPlacesByName(
								fatherName, cityCode);
						if(fatherPlaces!=null)
						for (Place place : fatherPlaces) {
							switch (place.getPlaceLevel()) {

							case 8:
								resultFeature.setAttribute("street_name",
										place.getName());
								resultFeature.setAttribute("street_all",
										place.getAllName());
								break;
							case 6:
								resultFeature.setAttribute("village_name",
										place.getName());
								resultFeature.setAttribute("village_all",
										place.getAllName());
								break;
							case 5:
								resultFeature.setAttribute("town_name", place.getName());
								break;
							}
						}
					}
					break;
				case 8:
					// 判断父类级别
					for (String fatherName : fatherNames) {
						List<Place> fatherPlaces = placeDao.getPlacesByName(
								fatherName, cityCode);
						if(fatherPlaces!=null)
						for (Place place : fatherPlaces) {
							switch (place.getPlaceLevel()) {
							case 6:
								resultFeature.setAttribute("village_name",
										place.getName());
								resultFeature.setAttribute("village_all",
										place.getAllName());
								break;
							case 5:
								resultFeature.setAttribute("town_name", place.getName());
								break;
							}
						}
					}
					break;
				}
			}
		}
		// 父子关系完成后要把完整地址给编辑出来了。
		if (!((String) resultFeature.getAttribute("province_name"))
				.equalsIgnoreCase((String) resultFeature
						.getAttribute("city_name")))
			strs.add((String) resultFeature.getAttribute("province_name"));
		strs.add((String) resultFeature.getAttribute("city_name"));
		strs.add((String) resultFeature.getAttribute("county_name"));
		strs.add((String) resultFeature.getAttribute("town_name"));
		strs.add((String) resultFeature.getAttribute("village_all"));
		strs.add((String) resultFeature.getAttribute("street_all"));
		strs.add((String) resultFeature.getAttribute("community_all"));
		strs.add((String) resultFeature.getAttribute("building_all"));
		// strs.add((String) resultFeature.getAttribute("poi_name"));
		resultFeature
				.setAttribute("st_address", StringUtils.mergeObjects(strs));
		return resultFeature;
	}

//	private SimpleFeature mappingExistingField(SimpleFeature resultFeature,
//			SimpleFeature feature, Map<String, String> fieldMap, int maxLevel) {
//
//		return resultFeature;
//	}

}
