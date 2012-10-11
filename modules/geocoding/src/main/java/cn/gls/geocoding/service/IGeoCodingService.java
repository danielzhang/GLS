package cn.gls.geocoding.service;


import java.util.List;

import cn.gls.geocoding.engine.data.GeoCodingRequest;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.service.IGLSService;

/**
 * @ClassName IGeoCodingService.java
 * @Createdate 2011-5-20
 * @Description 中文地理编码的服务接口
 * @Version 1.0
 * @Update 2012-5-27 
 * @author "Daniel Zhang"
 *
 */
public interface IGeoCodingService extends IGLSService {
	/**
	 * 批量地理编码通过请求及参数来获得反应
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	List<GeoCodingResponse> getBatchResponse(GeoCodingRequest request);

}
