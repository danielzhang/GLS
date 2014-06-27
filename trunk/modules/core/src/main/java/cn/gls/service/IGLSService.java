package cn.gls.service;

import java.io.IOException;

import cn.gls.Request;
import cn.gls.Response;

/**
 * @ClassName IGBSService.java
 * @Description 所有的GBS服务都来此类
 * @Version 1.0
 * @Update 2012-5-27 
 * @author "Daniel Zhang"
 *
 */
public interface IGLSService {
	/**
	 * 通过请求或得返回结果
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	Response getResponse(Request request) throws IOException;
}
