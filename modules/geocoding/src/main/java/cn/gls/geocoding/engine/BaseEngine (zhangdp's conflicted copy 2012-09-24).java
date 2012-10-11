package cn.gls.geocoding.engine;

import java.util.Collections;
import java.util.List;

import cn.gls.data.Place;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.context.support.GeoCodingContextUtil;
import cn.gls.geocoding.engine.baseengine.BaseEngineContext;
import cn.gls.geocoding.engine.dao.IGeoCodingDao;
import cn.gls.geocoding.engine.dao.impl.GeoCodingDaoImpl;
import cn.gls.geocoding.engine.data.GeoCodingRequestParameter;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.geocoding.engine.util.ClassificationUtil;
import cn.gls.place.dao.GLSEngineSupport;

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: BaseEngine.java
 * @Description 基础地理编码类，这里是一个策略模式的使用
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-27
 */
public class BaseEngine extends GLSEngineSupport {

	protected static final Log logger = LogFactory.getLog(BaseEngine.class);

	protected static final Boolean isDebug = logger.isDebugEnabled();

	private IGeoCodingDao geoCodingDao = (IGeoCodingDao) GeoCodingContextUtil
			.getBean("geoCodingDao");

	/***
	 * 构造基础地理引擎
	 * 
	 * @param context
	 */
	public BaseEngine(GeoCodingContext context) {
		super(context);
		this.context = context;
	}

	/**
	 * @Title:BaseEngine
	 * @Description:空对象构建BaseEngine。
	 * @param:
	 * @return:
	 * @throws
	 */
	public BaseEngine() {
		super(null);
	}

	// private String standard_address;

	/***
	 * 中文地理编码
	 * 
	 * @param places
	 * @param gParam
	 * @return
	 */
	public GeoCodingResponse getGeoCodingResponse(List<Place> places,
			GeoCodingRequestParameter gParam, GeoCodingResponse response) {
		// 对Places进行解析
		int addr_level = 0;
		int road_level = 0;
		Collections.sort(places);
		StringBuilder sb = new StringBuilder();
		for (Iterator<Place> it = places.iterator(); it.hasNext();) {
			Place place = it.next();
			if (addr_level < place.getPlaceLevel())
				addr_level = place.getPlaceLevel();
			if (place.getSuffix() != null)
				addr_level = 9;
			if (place.getPlaceLevel() == 8 && place.getSuffix() == null)
				road_level++;
			sb.append(place.getName());
		}
		if (isDebug)
			logger.debug("编码地址标准化后的地址为：" + sb.toString());
		return getResults(places, gParam.getS(), addr_level, road_level,
				response);
	}

	/**
	 * @param address
	 *            :中文地理编码地址。
	 * @param score
	 *            :显示最低分数
	 * @param addnum
	 *            :返回的查询结果数
	 * @param terms
	 *            : 含有等级的标准地址词
	 * @param addr_level
	 *            :最高地址等级
	 * @param road_level
	 *            :含有道路级别的地址词的个数
	 * @return :返回符合条件的查询结果
	 * @TODO :基础引擎的查询
	 */
	private GeoCodingResponse getResults(List<Place> places, float score,
			int addr_level, int road_level, GeoCodingResponse response) {
		// 得到显示分数
		if (score == 0)
			score = ClassificationUtil.getScore(((GeoCodingContext) context)
					.getClassDoc());
		if (isDebug) {
			logger.debug("该地址的最高级别是" + addr_level);
			logger.debug("要显示的分数为" + score);
		}
		// 定义策略环境变量
		BaseEngineContext baseContext = new BaseEngineContext();
		// 设置策略
		baseContext.setEngine(addr_level);

		return baseContext.getResponse(places, score, road_level, response,
				geoCodingDao, (GeoCodingContext) context);
	}

	public void log(String message) {
		logger.info(message);
	}

	public void log(Exception ex, String message) {
		logger.info(message, ex);
	}

	public void log(String message, Throwable ex) {
		logger.info(message, ex);
	}

}
