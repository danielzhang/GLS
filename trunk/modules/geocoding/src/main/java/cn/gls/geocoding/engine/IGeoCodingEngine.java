package cn.gls.geocoding.engine;



import java.util.List;


import cn.gls.geocoding.engine.data.GeoCodingRequest;
import cn.gls.geocoding.engine.data.GeoCodingResponse;


/**
 * 类名      IGeoCodingEngine.java
 * 说明      使用门面模式。使用辅助引擎加入地理编码引擎
 * 创建日期 2012-5-24
 * 作者 "Daniel Zhang"
 * 版权  V1.0
 * 更新时间  2012-5-27
 */
public interface IGeoCodingEngine {

	/*** 地理编码服务 */
	GeoCodingResponse getGeoCodingResults(GeoCodingRequest request);

	/** 批量地理编码服务 **/
	List<GeoCodingResponse> getGeoCodingBatchResults(GeoCodingRequest request);

}
