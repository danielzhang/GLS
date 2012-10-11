package cn.gls.geocoding.engine.baseengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;

import cn.gls.data.Place;
import cn.gls.data.Result;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.dao.IGeoCodingDao;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.geocoding.engine.data.GeoCodingResponseStatus;
import cn.gls.geocoding.engine.data.GeoCodingResult;
import cn.gls.geocoding.engine.util.ClassificationUtil;

/**
 * @ClassName: CityEngine.java
 * @Description 城市引擎对象， 具体对象策略类
 * @Date 2012-5-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-30
 */
public class CityEngine implements IBaseEngine {

	/**
	 * 城市级别，都是固定的分数值 (non-Javadoc)
	 * 
	 * @see cn.gls.geocoding.engine.baseengine.IBaseEngine#getResponse(cn.gbs.geocoding.engine.data.GeoCodingAddress,
	 *      float, int, int, int,
	 *      cn.gls.geocoding.engine.data.GeoCodingResponse,
	 *      cn.gls.geocoding.engine.dao.IGeoCodingDao)
	 */
	public GeoCodingResponse getResponse(List<Place> places, float score,
			int road_level,
			GeoCodingResponse response, IGeoCodingDao geoCodingDao,
			GeoCodingContext context) {
		/** 获得城市级别的分数值 */
		Document doc = context.getClassDoc();
		float city_score = ClassificationUtil.getScore("city", doc);

		if (city_score < score) {
			response.setMessage(context.getProperties().getProperty("Message.OVER_SCORE_LIMIT"));
		} else {
			List<GeoCodingResult> results = geoCodingDao
					.getGeoCodingResultsToCity(places,context);
			response.setMessage(context.getProperties().getProperty("Message.OK"));
			if (response.getResults()==null)
				response.setResults(new ArrayList<Result>());
			for (Iterator<GeoCodingResult> it = results.iterator(); it
					.hasNext();) {
				GeoCodingResult result = it.next();
				response.getResults().add(result);
			}
			response.setStatus(GeoCodingResponseStatus.OK);
			response.setSize(results.size());
		}
		return response;
	}

}
