package cn.gls.geocoding.engine.baseengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import cn.gls.data.Place;
import cn.gls.data.Result;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.dao.IGeoCodingDao;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.geocoding.engine.data.GeoCodingResult;
import cn.gls.geocoding.engine.util.ClassificationUtil;

public class StreetEngine implements IBaseEngine {

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.geocoding.engine.baseengine.IBaseEngine#getResponse(cn.gbs.geocoding.engine.data.GeoCodingAddress,
	 *      float, int, int, int,
	 *      cn.gls.geocoding.engine.data.GeoCodingResponse,
	 *      cn.gls.geocoding.engine.dao.IGeoCodingDao,
	 *      cn.gls.geocoding.context.GeoCodingContext)
	 */
	public GeoCodingResponse getResponse(List<Place> places, float score,
			int road_level, 
			GeoCodingResponse response, IGeoCodingDao geoCodingDao,
			GeoCodingContext context) {
		float s_score = ClassificationUtil.getScore("street",
				context.getClassDoc());
		if (s_score < score)
			response.setMessage(context.getProperties().getProperty(
					"Message.OVER_SCORE_LIMIT"));
		switch (road_level) {
		case 1:
			// 道路引擎。
			List<GeoCodingResult> gresults = geoCodingDao
					.getGeoCodingResultsToStreet(places, context);
			if (response.getResults() == null)
				response.setResults(new ArrayList<Result>());
			if (gresults != null) {
				for (Iterator<GeoCodingResult> git = gresults.iterator(); git
						.hasNext();) {
					GeoCodingResult result = git.next();
					response.getResults().add(result);
				}
			}
			response.setMessage(context.getProperties().getProperty(
					"Message.OK"));
			response.setSize(response.getResults().size());
			break;
		case 2:
			// 道路交叉口
			List<GeoCodingResult> iresults = geoCodingDao
					.getGeoCodingResultsToIntersection(places,context);
			if (response.getResults() == null)
				response.setResults(new ArrayList<Result>());
			if (iresults != null) {
				for (Iterator<GeoCodingResult> git = iresults.iterator(); git
						.hasNext();) {
					GeoCodingResult result = git.next();
					response.getResults().add(result);
				}
			}
			response.setMessage(context.getProperties().getProperty(
					"Message.OK"));
			response.setSize(response.getResults().size());
			break;
		}
		return response;
	}

}
