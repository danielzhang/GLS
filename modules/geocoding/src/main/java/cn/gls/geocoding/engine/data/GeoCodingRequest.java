package cn.gls.geocoding.engine.data;

import cn.gls.Request;

/**
 * @ClassName: GeoCodingRequest.java
 * @Description  相比Request主要添加了一些地理编码服务的限制参数包括两种：
 * 一种是请求参数，一种内部表示参数
 * @Date  2012-5-29
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-5-29
 */
public class GeoCodingRequest extends Request {

	/** 该服务是否过期 */
	private volatile boolean isExpire;
	/** 批量地理编码限制 */
	private volatile int batchLimit = 20;

	public boolean isExpire() {
		return isExpire;
	}

	public void setExpire(boolean isExpire) {
		this.isExpire = isExpire;
	}

	public int getBatchLimit() {
		return batchLimit;
	}

	public void setBatchLimit(int batchLimit) {
		this.batchLimit = batchLimit;
	}
	@Override
	public GeoCodingRequestParameter getGbsRequestParameter() {
		return (GeoCodingRequestParameter)gbsRequestParameter;
	}
	
	public void setGbsRequestParameter(GeoCodingRequestParameter gbsRequestParameter) {
		this.gbsRequestParameter = gbsRequestParameter;
	}
}
