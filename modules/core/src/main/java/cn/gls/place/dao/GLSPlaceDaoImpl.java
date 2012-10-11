package cn.gls.place.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.gls.GLSDBSupport;
import cn.gls.data.PinyinPlace;
import cn.gls.data.Place;
import cn.gls.data.TycPlace;


/**
 * @ClassName GLSPlaceDaoImpl.java
 * @Createdate 2012-5-24
 * @Description 说明 数据库中的Place与辅助对象的交互
 * @Version 1.0
 * @Update 2012-7-10 
 * @author "Daniel Zhang"
 *
 */
public class GLSPlaceDaoImpl extends GLSDBSupport implements IPlaceDao {

//	private GLSPlaceDaoImpl() {
//		// 不能够产生私有对象
//	}
//	private static final GLSPlaceDaoImpl instance = new GLSPlaceDaoImpl();
//
//	public synchronized static GLSPlaceDaoImpl getInstance() {
//		return instance;
//	}

	/**
	 * 用拼音转化为标准词
	 */
	public List<Place> pinyin2Places(String pinyin, int citycode) {
		// 首先获得拼音词。
		List<PinyinPlace> pinyinplaces = getSqlMapClientTemplate()
				.queryForList("place.selectPinyinsBypinyinName",
						new PinyinPlace(pinyin, citycode));
		List<Place> places = new ArrayList<Place>();
		// 判断是否为标准词
		for (Iterator<PinyinPlace> pit = pinyinplaces.iterator(); pit.hasNext();) {
			PinyinPlace pinyinplace = pit.next();
			if (pinyinplace.isStandard()) {
				places.add(getPlaceByStandardPlaceName(pinyinplace.getpName(),
						citycode));
			} else {
				places.add(tyc2Place(pinyinplace.getpName(), citycode));
			}
		}
		return places;
	}

	public GLSPlaceDaoImpl() {
		super();
	}

	public List<String> place2Pinyin(String place, int citycode) {

		return null;
	}

	public List<String> place2Tyc(String place, int citycode) {

		return (List<String>) getSqlMapClientTemplate().queryForObject(
				"place.selectTycByStandardPlaceName",
				new TycPlace(citycode, place));

	}

	public String tyc2PlaceName(String tyc, int citycode) {

		return (String) getSqlMapClientTemplate().queryForObject(
				"place.selectTycByPlaceName", new TycPlace(tyc, citycode));
	}

	public List<Place> getPlacesByStandardName(String name) {

		return getSqlMapClientTemplate().queryForList(
				"place.selectPlacesByStandardName", new Place(name, 0, 0));
	}

	/**
	 * 通过地名词的名词来得到地名词通常情况不用判别
	 */
	public List<Place> getPlacesByName(String name, int cityCode) {

		// 首先判断是否是同义词。
		Map<String,Object> p=new HashMap<String, Object>();
		p.put("placeName", name);
		p.put("cityCode",cityCode);
        List<Place> places=getSqlMapClientTemplate().queryForList("place.selectPlaceByName",p);
             if(places!=null&&places.size()>0)
		return places;
             else
            	 return null;
	}

//	public int getCityCodeByName(String name) {
//
//		return (Integer) getSqlMapClientTemplate().queryForObject(
//				"place.selectCityCodeByName", name);
//	}
//
//	public int getCityCodeByPlaceName(String name, int place_level) {
//
//		return (Integer) getSqlMapClientTemplate().queryForObject(
//				"place.selectCityCodeByPlaceName",
//				new Place(name, place_level, 0));
//	}
/**
 * 由错词来得到地名词。
 */
	public List<Place> pinyinPlacesByError(String error, int citycode) {

		return null;
	}

	public Place getPlaceByStandardPlaceName(String name, int citycode) {

		return (Place) getSqlMapClientTemplate().queryForObject(
				"place.selectPlaceByStandardPlaceName",
				new Place(name, 0, citycode));
	}

	public Place tyc2Place(String tyc, int citycode) {

		return (Place) getSqlMapClientTemplate().queryForObject(
				"place.selectPlaceByTycPlaceName", new TycPlace(tyc, citycode));
	}

}
