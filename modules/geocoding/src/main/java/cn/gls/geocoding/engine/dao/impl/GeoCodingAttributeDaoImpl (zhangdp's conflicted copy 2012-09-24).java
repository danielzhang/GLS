package cn.gls.geocoding.engine.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gls.geocoding.engine.dao.IGeoCodingAttributeDao;
import cn.gls.place.dao.GLSPlaceDaoImpl;

public class GeoCodingAttributeDaoImpl extends GLSPlaceDaoImpl implements
		IGeoCodingAttributeDao {
	protected GeoCodingAttributeDaoImpl() {
		super();
	}

	// private static final GeoCodingAttributeDaoImpl instance = new
	// GeoCodingAttributeDaoImpl();
	//
	// public synchronized static GeoCodingAttributeDaoImpl getInstance() {
	// return instance;
	// }
	/**
     * 
     */
	public int getCityCodeByName(String cityName) {
		if (cityName == null || "".equalsIgnoreCase(cityName))
			return 0;

		Integer cityCode = (Integer) getSqlMapClientTemplate().queryForObject(
				"place.selectCityCodeByCityName", cityName);
		if (cityCode == null)
			return 0;
		return cityCode;
	}

	public int getCityCodeByPlaceName(String placeName, int placeLevel) {
		if (placeName == null || "".equalsIgnoreCase(placeName))
			return 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pName", placeName);
		map.put("placeLevel", placeLevel);
		Integer cityCode = (Integer) getSqlMapClientTemplate().queryForObject(
				"place.selectCityCodeByPlaceName", map);
		if (cityCode == null)
			cityCode = (Integer) getSqlMapClientTemplate().queryForObject(
					"place.selectCityCodeByTycName", map);
		if (cityCode == null)
			return 0;
		return cityCode;

	}

	public List<Integer> getCityCodeByPlaceName(String placeName) {
		if (placeName == null || "".equalsIgnoreCase(placeName))
		return null;
		List<Integer> cityCodes=getSqlMapClientTemplate().queryForList("place.selectCityCodeByName", placeName);
		if(cityCodes==null){
			cityCodes=getSqlMapClientTemplate().queryForList("place.selectCityCodesByTycName", placeName);
		}
		return cityCodes;
	}
}
