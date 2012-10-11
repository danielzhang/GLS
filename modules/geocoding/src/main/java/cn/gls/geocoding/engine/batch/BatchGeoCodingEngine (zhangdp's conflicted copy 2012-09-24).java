package cn.gls.geocoding.engine.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.data.GeoCodingRequest;
import cn.gls.geocoding.engine.data.GeoCodingRequestParameter;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.geocoding.engine.util.ClassificationUtil;

/**
 * @ClassName: BatchGeoCodingEngine.java
 * @Description 批量地理编码引擎,针对批量地理编码的返回结果
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-29
 */
public class BatchGeoCodingEngine {
	/** 中文地理编码引擎上下文环境 */
	private GeoCodingContext context;
	public GeoCodingContext getContext() {
		return context;
	}

	/****/
	public  int batchAddresses = 0;
	/**响应对象*/
	public  List<GeoCodingResponse> responses = new ArrayList<GeoCodingResponse>();
	
	public  int sum=0;



	public synchronized List<GeoCodingResponse> getResultes(
			GeoCodingRequestParameter gParam) {

		GeoCodingTaskQueue queue = new GeoCodingTaskQueue();
		GeoCodingThreadGroup group = new GeoCodingThreadGroup(queue);
		List<String> strs = Arrays.asList(gParam.getQ().split("/n"));
		sum = strs.size();
		batchAddresses = 0;
		for (int i = 0; i < sum; i++) {
			GeoCodingRequestParameter gp = new GeoCodingRequestParameter(
					strs.get(i), gParam.getS(), gParam.getClientIp());
			// 构造一个线程任务类
			GeoCodingEngineTask task = new GeoCodingEngineTask(gParam,
					new GeoCodingResponse(),this);
			queue.putTask(task);
		}
		int cpus = Runtime.getRuntime().availableProcessors();
		for (int j = 0; j < cpus; j++) {
			group.addGeoCodingEngineThread();
		}
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return responses;
	}
	
	/**
	 * 获得中文地理编码的上下文环境
	 *@Title:BatchGeoCodingEngine
	 *@Description:TODO
	 *@param: @param context
	 *@return:
	 * @throws
	 */
	public BatchGeoCodingEngine(GeoCodingContext context) {
		super();
		this.context = context;
	}
}
