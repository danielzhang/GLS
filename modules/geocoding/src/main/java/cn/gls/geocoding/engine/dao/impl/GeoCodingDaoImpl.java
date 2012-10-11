package cn.gls.geocoding.engine.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.gls.GLSDBSupport;
import cn.gls.data.Place;
import cn.gls.data.StandardAddress;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.dao.IGeoCodingDao;
import cn.gls.geocoding.engine.data.GeoCodingResult;
import cn.gls.geocoding.engine.data.Geometry;
import cn.gls.geocoding.engine.data.LocationType;
import cn.gls.geocoding.engine.data.ViewPort;
import cn.gls.geocoding.engine.util.ClassificationUtil;
import cn.gls.geocoding.engine.util.GeometryUtil;
import cn.gls.geocoding.engine.util.PlaceAddressUtil;
import cn.gls.util.PlaceUtil;
import cn.gls.util.StringUtils;

/**
 * @ClassName GeoCodingDaoImpl.java
 * @Createdate 2012-5-27
 * @Description 中文地理编码引擎接口
 * @Version 1.0
 * @Update 2012-6-5
 * @author "Daniel Zhang"
 */
public class GeoCodingDaoImpl extends GLSDBSupport implements IGeoCodingDao {
	/**
	 * @Title:GeoCodingDaoImpl
	 * @Description:TODO
	 * @param:
	 * @return:
	 * @throws
	 */
	private GeoCodingDaoImpl() {
	}

	private static final GeoCodingDaoImpl instance = new GeoCodingDaoImpl();

	/***
	 * 关于得到中文地理编码的实例
	 * 
	 * @return
	 */
	public static synchronized GeoCodingDaoImpl getInstance() {
		return instance;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.geocoding.engine.dao.IGeoCodingDao#getGeoCodingResultsToCity(cn.gbs.geocoding.engine.data.GeoCodingAddress,
	 *      int, int, float)
	 */
	@SuppressWarnings("unchecked")
	public List<GeoCodingResult> getGeoCodingResultsToCity(List<Place> places,
			GeoCodingContext context) {
		List<GeoCodingResult> results = null;
		StandardAddress address = PlaceAddressUtil.getAddressByPlaces(places);

		results = (List<GeoCodingResult>) getSqlMapClientTemplate()
				.queryForList("geocoding.selectCityByAddress", address);
		if (results != null) {
			for (GeoCodingResult result : results) {
				result.setScore(ClassificationUtil.getEngineScore(
						address.getAddr_type(), context.getClassDoc()));
				com.vividsolutions.jts.geom.Geometry geometry = GeometryUtil
						.convertGeometry(result.getAddress().getGeoText());
				ViewPort vp = new ViewPort(GeometryUtil.convertExtent(geometry));
				cn.gls.geometry.data.Geometry geom = GeometryUtil
						.convertGeometry(geometry);
				Geometry geoCodinggeometry = new Geometry(geom);
				geoCodinggeometry.setLocation(geom);
				geoCodinggeometry.setLocation_type(LocationType.ROOFTOP);
				geoCodinggeometry.setPartial_match(true);
				geoCodinggeometry.setViewport(vp);
				result.setGeometry(geoCodinggeometry);
			}
			return results;
		} else
			return null;
	}

	/**
	 * 查询区域结果
	 */
	@SuppressWarnings("unchecked")
	public List<GeoCodingResult> getGeoCodingResultsToPolitical(
			List<Place> places, GeoCodingContext context) {
		List<GeoCodingResult> results = null;
		StandardAddress address = PlaceAddressUtil.getAddressByPlaces(places);

		results = (List<GeoCodingResult>) getSqlMapClientTemplate()
				.queryForList("geocoding.selectPoliticalByAddress", address);
		if (results != null) {
			for (GeoCodingResult result : results) {
				String aType = result.getAddress().getAddr_type();
				result.setScore(ClassificationUtil.getEngineScore(aType,
						context.getClassDoc()));
				com.vividsolutions.jts.geom.Geometry geometry = GeometryUtil
						.convertGeometry(result.getAddress().getGeoText());
				ViewPort vp = new ViewPort(GeometryUtil.convertExtent(geometry));
				cn.gls.geometry.data.Geometry geom = GeometryUtil
						.convertGeometry(geometry);
				Geometry geoCodinggeometry = new Geometry(geom);
				geoCodinggeometry.setLocation(geom);
				geoCodinggeometry.setLocation_type(LocationType.ROOFTOP);
				geoCodinggeometry.setPartial_match(true);
				geoCodinggeometry.setViewport(vp);
				result.setGeometry(geoCodinggeometry);
			}
			return results;
		} else
			return null;
	}

	/**
	 * 查询街道
	 */
	@SuppressWarnings("unchecked")
	public List<GeoCodingResult> getGeoCodingResultsToStreet(
			List<Place> places, GeoCodingContext context) {
		List<GeoCodingResult> results = null;
		StandardAddress address = PlaceAddressUtil.getAddressByPlaces(places);
		results = getSqlMapClientTemplate().queryForList(
				"geocoding.selectStreetByAddress", address);
		int length = results.size();
		List<GeoCodingResult> gresults = null;
		gresults = length <= 0 ? null : new ArrayList<GeoCodingResult>();
		if (results != null) {
			for (int i = 0; i < length; i++) {
				GeoCodingResult result = new GeoCodingResult();
				result.setAddress(results.get(i).getAddress());
//				String aType = result.getAddress().getAddr_type();
				result.setScore(ClassificationUtil.getEngineScore("路",
						context.getClassDoc()));
				com.vividsolutions.jts.geom.Geometry geometry = GeometryUtil
						.convertGeometry(result.getAddress().getGeoText());
				ViewPort vp = new ViewPort(GeometryUtil.convertExtent(geometry));
				cn.gls.geometry.data.Geometry geom = GeometryUtil
						.convertGeometry(geometry);
				Geometry geoCodinggeometry = new Geometry(geom);
				geoCodinggeometry.setLocation(geom);
				geoCodinggeometry.setLocation_type(LocationType.ROOFTOP);
				geoCodinggeometry.setPartial_match(true);
				geoCodinggeometry.setViewport(vp);
				result.setGeometry(geoCodinggeometry);
				gresults.add(result);
			}
			
		} 
			return gresults;
	}

	/**
	 * 交叉路口查询
	 */
	@SuppressWarnings("unchecked")
	public List<GeoCodingResult> getGeoCodingResultsToIntersection(
			List<Place> places, GeoCodingContext context) {
		List<GeoCodingResult> results = null;
		StandardAddress address = PlaceAddressUtil.getAddressByPlaces(places);
		results = getSqlMapClientTemplate().queryForList(
				"geocoding.selectIntersectionByAddress", address);
		int length = results.size();
		if (length <= 1)
			return null;
		Map<StandardAddress, Object> maps = new HashMap<StandardAddress, Object>();
		String preAddress = null;
		String aType = null;
		for (int i = 0; i < length; i++) {
			if (preAddress == null) {
				preAddress = results.get(i).getAddress().getSt_address()
						.split(results.get(i).getAddress().getStreet())[0];
			}
			maps.put(
					results.get(i).getAddress(),
					GeometryUtil.convertGeometry(results.get(i).getAddress()
							.getGeoText()));
			if (aType == null)
				aType = results.get(i).getAddress().getAddr_type();
		}
		List<GeoCodingResult> gresults = null;
		Map<StandardAddress, com.vividsolutions.jts.geom.Geometry> geometryResult = GeometryUtil
				.intersection(maps);
		if (geometryResult != null) {
			Iterator<Map.Entry<StandardAddress, com.vividsolutions.jts.geom.Geometry>> iterator = geometryResult
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<StandardAddress, com.vividsolutions.jts.geom.Geometry> gEntry = iterator
						.next();
				GeoCodingResult result = new GeoCodingResult();
				result.setScore(ClassificationUtil.getEngineScore("交叉口",
						context.getClassDoc()));
				result.setAddress(gEntry.getKey());
				ViewPort vp = new ViewPort(GeometryUtil.convertExtent(gEntry
						.getValue()));
				cn.gls.geometry.data.Geometry geom = GeometryUtil
						.convertGeometry(gEntry.getValue());
				Geometry geoCodinggeometry = new Geometry(geom);
				geoCodinggeometry.setLocation(geom);
				geoCodinggeometry.setLocation_type(LocationType.ROOFTOP);
				geoCodinggeometry.setPartial_match(true);
				geoCodinggeometry.setViewport(vp);
				result.setGeometry(geoCodinggeometry);
				if (gresults == null)
					gresults = new ArrayList<GeoCodingResult>();
				gresults.add(result);
			}
		}

		return gresults;
	}

	/**
	 * 地址匹配引擎的核心。
	 */
	@SuppressWarnings("unchecked")
	public List<GeoCodingResult> getGeoCodingResultsToAddress(
			List<Place> places, GeoCodingContext context) {
		List<Integer> grades = new ArrayList<Integer>();
		// 要得到最高级别。
		int level = 0;
		for (Place place : places) {
			if (place.getPlaceLevel() > 10)
				continue;
			if (grades.size()== 0) {
				grades.add(0, place.getPlaceLevel());

			} else if (grades.get(0) < place.getPlaceLevel()) {

				grades.set(0, place.getPlaceLevel());
				if (place.getSuffix() != null)
					grades.add(1, 10 + place.getPlaceLevel());
			}
		}
		level = grades.get(0);
		List<GeoCodingResult> results = null;
		StandardAddress address = PlaceAddressUtil.getAddressByPlaces(places);
		for (int i = 1; i < 5; i++) {
			List<List<Integer>> tags = ClassificationUtil.gettags(String.valueOf(i),
					context.getClassDoc());
			int length = tags.size();
			for (int j = 0; j < length; j++) {
		
				List<Integer> tag = tags.get(j);
				if (grades.containsAll(tag)) {
					if (results == null)
						results = new ArrayList<GeoCodingResult>();
					results.addAll(getSqlMapClientTemplate().queryForList(
							"geocoding.selectAddress" + i, address));
				}
			}
		}
//		Map<Integer, Integer> map = ClassificationUtil.getWeightMap(context
//				.getClassDoc());
		// float orlg = PlaceAddressUtil.getLg(map, address);
		int digit = 0;
		String keyWord = null;
//		Integer w = 0;
		// 从大到小
		if (address.getBuilding_suffix() != null) {
//			w = map.get(20);
			digit = StringUtils.checkoutDigit(address.getBuilding_suffix());
			if (digit == 0)
				keyWord = StringUtils.getKeyWordInSuffix(address
						.getBuilding_suffix());
		}
		if (digit == 0 && keyWord == null) {
			if (address.getCommunitis_suffix() != null) {
//				w = map.get(19);
				level = 9;
				digit = StringUtils.checkoutDigit(address
						.getCommunitis_suffix());
				if (digit == 0)
					keyWord = StringUtils.getKeyWordInSuffix(address
							.getCommunitis_suffix());
			}
		}
		if (digit == 0 && keyWord == null) {
			if (address.getStreet_suffix() != null) {
//				w = map.get(18);
				level = 8;
				digit = StringUtils.checkoutDigit(address.getStreet_suffix());
				if (digit == 0)
					keyWord = StringUtils.getKeyWordInSuffix(address
							.getStreet_suffix());
			}
		}
		if (digit == 0 && keyWord == null) {
			if (address.getVillage_suffix() != null)
//				w = map.get(16);
			level = 6;
			digit = StringUtils.checkoutDigit(address.getVillage_suffix());
			if (digit == 0)
				keyWord = StringUtils.getKeyWordInSuffix(address
						.getVillage_suffix());
		}

		// 对结果进行逐一的评分,对于这个结果，在于优而不在于多。
		Map<Object, GeoCodingResult> difs = new HashMap<Object, GeoCodingResult>();
		List<GeoCodingResult> geoCodingResults = new ArrayList<GeoCodingResult>();
		// 标示是否找到最合适的地址。如果已经找到则break;
		boolean flag = false;
		for (Iterator<GeoCodingResult> resultIterator = results.iterator(); resultIterator
				.hasNext();) {
			if (flag)
				break;
			GeoCodingResult result = resultIterator.next();

			StandardAddress stAddress = result.getAddress();
			int d = 0;
			String k = null;
			switch (level) {
			case 10:
				d = StringUtils.checkoutDigit(stAddress.getBuilding_suffix());
				if(d==0)
				k = StringUtils.getKeyWordInSuffix(stAddress
						.getBuilding_suffix());
				break;
			case 9:
				d = StringUtils.checkoutDigit(stAddress.getCommunitis_suffix());
				if(d==0)
				k = StringUtils.getKeyWordInSuffix(stAddress
						.getCommunitis_suffix());
				break;
			case 8:
				d = StringUtils.checkoutDigit(stAddress.getStreet_suffix());
				if(d==0)
				k = StringUtils
						.getKeyWordInSuffix(stAddress.getStreet_suffix());
				break;
			case 6:
				d = StringUtils.checkoutDigit(stAddress.getVillage_suffix());
				if(d==0)
				k = StringUtils
						.getKeyWordInSuffix(stAddress.getVillage_suffix());
				break;
			}
			if (digit != 0 || keyWord != null) {
				if (d != 0 && digit != 0) {
					difs.put(d, result);
				} else if (d == 0 && digit == 0) {
					if (keyWord.equalsIgnoreCase(k)) {

						geoCodingResults.add(result);

						flag = true;
					} else
						difs.put(k, result);
				}
			} else {
				// 当请求没有后缀时
				if (d != 0)
					difs.put(d, result);
				else if (k != null)
					difs.put(k, result);
				else if (d == 0 && k == null) {
					geoCodingResults.add(result);
					flag = true;
				}
			}
		}

		/**
		 * 1. 要求按照规则进行排序并返回了。 2. 理论上100分之米90分，500米之内80分，1500米之内60分。 3.
		 * 首先是门牌号是数字的。
		 * 
		 */

		if (geoCodingResults.size() != 1) {
			Iterator<Map.Entry<Object, GeoCodingResult>> gIterator = difs
					.entrySet().iterator();
			while (gIterator.hasNext()) {
				Map.Entry<Object, GeoCodingResult> mEntry=gIterator.next();
				Object key = mEntry.getKey();
				GeoCodingResult result = mEntry.getValue();
				if ((PlaceUtil.isDigital(key.toString()) && Integer.valueOf(key
						.toString()) == digit)
						|| key.toString().equalsIgnoreCase(keyWord)) {
					geoCodingResults.clear();
					geoCodingResults.add(0, result);
					break;
				}
			}
				// 如果不是这样就要评分了。
				if (digit != 0 && geoCodingResults.size() != 1) {
					TreeMap<Object, GeoCodingResult> difsTree = new TreeMap<Object, GeoCodingResult>(
							difs);
					double aveDis = PlaceAddressUtil.aveDistance(difsTree);
					results = PlaceAddressUtil
							.getScore(difsTree, aveDis, digit);

				} else if(keyWord!=null&&geoCodingResults.size()!=1) {
					// 对于后缀不是数字门牌号的，则要求分数一直，统一90分
					Iterator<Map.Entry<Object, GeoCodingResult>> rIterator = difs
							.entrySet().iterator();
					results.clear();
					while (rIterator.hasNext()) {
						Map.Entry<Object, GeoCodingResult> rEntry = rIterator
								.next();
						GeoCodingResult tresult = rEntry.getValue();
						com.vividsolutions.jts.geom.Geometry geometry = GeometryUtil
								.convertGeometry(tresult.getAddress()
										.getGeoText());
						cn.gls.geometry.data.Geometry geom = GeometryUtil
								.convertGeometry(geometry);
						tresult.setGeometry(new Geometry(geom));
						tresult.setScore(90f);
						results.add(tresult);
					}
				}
			}
	
		if (geoCodingResults.size() == 1) {
			GeoCodingResult result = geoCodingResults.get(0);
			com.vividsolutions.jts.geom.Geometry geometry = GeometryUtil
					.convertGeometry(result.getAddress().getGeoText());
			cn.gls.geometry.data.Geometry geom = GeometryUtil
					.convertGeometry(geometry);
			result.setGeometry(new Geometry(geom));
			result.setScore(100f);
			return geoCodingResults;
		}
		Collections.sort(results,Collections.reverseOrder());
		return results;
	}

}
