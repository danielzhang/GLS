package cn.gls.assist;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.gls.context.GLSContext;
import cn.gls.data.Place;

import cn.gls.place.dao.GLSEngineSupport;
import cn.gls.place.dao.IPlaceDao;

/**
 * @ClassName ThesaurusEngine.java
 * @Description 同义词引擎开发
 * @createdate 2011-2-21
 * @Version 1.0
 * @Update 2012-5-27
 * @author "Daniel Zhang"
 */
public class ThesaurusEngine extends GLSEngineSupport {

	private static final Log log = LogFactory.getLog(ThesaurusEngine.class);

	private IPlaceDao placeDao;

	/**
	 * @param 含有同义词的地址词数组
	 * @Description：同义词地址转化为标准地址词及其级别，首先判断是否为同义词，如果不是直接查询完后返回，如果是则需要查找到标准词后返回。
	 * @param str
	 *            同义词
	 * @param citycode
	 *            城市代码
	 * @return 标准词
	 */
	public List<Place> getPlacesByName(Place place, int citycode) {
		List<Place> places = new ArrayList<Place>();
		// 首先判断是否为同义词,要去标准词库表中先去查找
		if (place.getPlaceLevel() == 0) {
			places = placeDao.getPlacesByName(place.getName(), citycode);
			if (places == null || places.size() == 0) {
				// 查找标准词
				String standardStr = placeDao.tyc2PlaceName(place.getName(),
						citycode);
				// 寻找地名词
				List<Place> thesaurusPlaces = placeDao.getPlacesByName(
						standardStr, citycode);
				if (thesaurusPlaces != null)
					places.addAll(thesaurusPlaces);
			}
			else {
				for (Place placeItem : places) {
					placeItem.setSuffix(place.getSuffix());
				}
			}
		}
		else
			places.add(place);
		return places;
	}

	public ThesaurusEngine(GLSContext context, IPlaceDao placeDao) {
		super(context);
		this.placeDao = placeDao;
	}

}
