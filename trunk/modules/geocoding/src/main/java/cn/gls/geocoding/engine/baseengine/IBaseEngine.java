package cn.gls.geocoding.engine.baseengine;

import java.util.List;

import cn.gls.data.Place;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.dao.IGeoCodingDao;
import cn.gls.geocoding.engine.dao.impl.GeoCodingDaoImpl;
import cn.gls.geocoding.engine.data.GeoCodingResponse;

/**
 * @ClassName: IBaseEngine.java
 * @Description 当级别不同时，进入不同的实现基础引擎。这个类是策略抽象类。
 * @Date 2012-5-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-30
 */
public interface IBaseEngine {
	/**地理编码访问数据库接口**/
	 IGeoCodingDao geoCodingDao = GeoCodingDaoImpl.getInstance();

	/***
	 * 地理编码不同级别的实现接口
	 * @param address 中文地理编码的地址类
	 * @param score 结果的分数限制
	 * @param road_level 含有多少条道路，这个对道路引擎有关。
	 * @param pagesize 每页的个数
	 * @param currentpage 当前页面
	 * @param response  返回结果
	 * @return
	 */
	GeoCodingResponse getResponse(List<Place> places,
			float score, int road_level, GeoCodingResponse response,IGeoCodingDao geoCodingDao,GeoCodingContext context);
}
