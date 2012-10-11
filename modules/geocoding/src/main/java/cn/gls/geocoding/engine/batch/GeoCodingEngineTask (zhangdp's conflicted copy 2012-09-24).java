package cn.gls.geocoding.engine.batch;

import cn.gls.Request;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.context.support.GeoCodingContextUtil;
import cn.gls.geocoding.engine.GeoCodingEngine;
import cn.gls.geocoding.engine.data.GeoCodingRequestParameter;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.geocoding.engine.data.GeoCodingResult;

/**
 * @ClassName GeoCodingEngineTask.java
 * @Createdate 2011-7-26
 * @Description 主要是实现服务器端真实的功能类，这个类的主题功能是任务类，是把队列中的请求通过中文地理编码引擎来实现。
 * @Version 1.0
 * @Update 2012-5-29
 * @author "Daniel Zhang"
 * 
 */
public class GeoCodingEngineTask implements GeoCodingTaskInterface {

	/*** GeoCoding的请求参数 */
	private GeoCodingRequestParameter gParam;
	/** GeoCoding的响应对象 **/
	private GeoCodingResponse response;

	private BatchGeoCodingEngine bEngine;

	/**
	 * 中文地理编码任务。
	 * 
	 * @Title:GeoCodingEngineTask
	 * @Description:TODO
	 * @param: @param bEngine
	 * @param: @param str
	 * @param: @param score
	 * @return:
	 * @throws
	 */
	public GeoCodingEngineTask(GeoCodingRequestParameter gParam,
			GeoCodingResponse response, BatchGeoCodingEngine bEngine) {
		super();
		this.gParam = gParam;
		this.response = response;
		this.bEngine = bEngine;
	}

	/**
	 * 执行任务。
	 * 
	 * @see com.esrichina.tm.engineInterface.GeoCodingTaskInterface#execute(java.
	 *      lang.String, int, int, int)
	 */
	public GeoCodingResponse execute() {
		// 构建GeoCodingEngine
		GeoCodingEngine gEngine = new GeoCodingEngine(bEngine.getContext());
		response = gEngine.getResponse(gParam, response);
		bEngine.batchAddresses++;
		// 如果没有匹配的则需要在其中添加默认的response
		if (response.getResults().size() == 0) {
			response.setMessage("无匹配项");

		}

		bEngine.responses.add(response);

		if (bEngine.batchAddresses == bEngine.sum) {
			synchronized (bEngine) {
				bEngine.notifyAll();
			}
		}

		return response;
	}

}
