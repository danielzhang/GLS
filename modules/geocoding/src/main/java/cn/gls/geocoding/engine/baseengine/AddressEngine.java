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
import cn.gls.geocoding.engine.data.GeoCodingResult;
import cn.gls.geocoding.engine.util.ClassificationUtil;

public class AddressEngine implements IBaseEngine {

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
		Document doc = context.getClassDoc();
		float c_score = ClassificationUtil.getScore("Address", doc);

		if (c_score < score) {
			response.setMessage(context.getProperties().getProperty(
					"Message.OVER_SCORE_LIMIT"));
		} else {
			List<GeoCodingResult> results = geoCodingDao
					.getGeoCodingResultsToAddress(places,  context);
			response.setMessage(context.getProperties().getProperty(
					"Message.OK"));
			if (response.getResults() == null)
				response.setResults(new ArrayList<Result>());
			for (Iterator<GeoCodingResult> it = results.iterator(); it
					.hasNext();) {
				GeoCodingResult result = it.next();
				result.setScore(c_score);
			}
			response.setSize(response.getResults().size());
		}
		return response;
	}
}
