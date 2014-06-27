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
import cn.gls.geocoding.engine.data.GeoCodingType;
import cn.gls.geocoding.engine.util.ClassificationUtil;

public class PoliticalEngine implements IBaseEngine{

    public GeoCodingResponse getResponse(List<Place> places, float score,
            int road_level, 
            GeoCodingResponse response, IGeoCodingDao geoCodingDao,
            GeoCodingContext context) {
        Document doc=context.getClassDoc();
        float p_score=ClassificationUtil.getScore("political", doc);
        if(p_score<score){
                //构建空的response
                response.setMessage(context.getProperties().getProperty("Message.OVER_SCORE_LIMIT"));
        }
        else{
                List<GeoCodingResult> results=geoCodingDao.getGeoCodingResultsToPolitical(places,context);
                if(response.getResults()==null)
                        response.setResults(new ArrayList<Result>());
                for(Iterator<GeoCodingResult> it=results.iterator();it.hasNext();){
                        GeoCodingResult result=it.next();
                       response.getResults().add(result);
                }
                response.setMessage(context.getProperties().getProperty("Message.OK"));
                response.setSize(response.getResults().size());
        }
              return response;
      }


}
