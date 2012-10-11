package cn.gls.geocoding.engine.baseengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.gls.data.Place;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.dao.IGeoCodingDao;
import cn.gls.geocoding.engine.data.GeoCodingResponse;

/**
 * @ClassName: BaseEngineContext.java
 * @Description  基础引擎策略模式的环境变量
 * @Date  2012-5-28
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-5-28
 */
public class BaseEngineContext {
	/**定义一个基础引擎的抽象策略接口类*/
     IBaseEngine baseEngine;
     /**这个是指各个基础引擎的策略配置。要根据Addr_level判断*/
     private static final Map<Integer, Class> mapper=new HashMap<Integer, Class>(){{
    	 put(3,CityEngine.class);
    	 put(4,PoliticalEngine.class);
    	 put(5,PoliticalEngine.class);
    	 put(6,PoliticalEngine.class);
    	 put(8,StreetEngine.class);
    	 put(9,AddressEngine.class);
    	 put(10,AddressEngine.class);
    	 put(11,AddressEngine.class);
     }};
     /**
      * 这个方法用于指定策略。
      * @param addr_level 地址类型
      */
     public void setEngine(int addr_level){
    	  Class clazz=mapper.get(addr_level);
    	  try {
			baseEngine=(IBaseEngine)Class.forName(clazz.getName()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
     }
     /**
      * 执行地理编码真正的引擎算法
      * @param address
      * @param score
      * @param road_level
      * @param pagesize
      * @param currentpage
      * @param response
      * @return
      */
     public GeoCodingResponse getResponse(List<Place> places,
 			float score, int road_level,  GeoCodingResponse response,IGeoCodingDao geoCodingDao,GeoCodingContext context){
    	return  baseEngine.getResponse(places, score, road_level, response,geoCodingDao,context);
     }
}
