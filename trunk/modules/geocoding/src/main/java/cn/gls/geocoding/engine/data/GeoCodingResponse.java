package cn.gls.geocoding.engine.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.gls.Response;
import cn.gls.data.Result;

/**
 * @ClassName GeoCodingResponse.java
 * @Description 中文地理编码返回结果
 * @CreateDate 2012-5-25
 * @Version 1.0
 * @Update 2012-5-29
 * @author "Daniel Zhang"
 * 
 */
public class GeoCodingResponse extends Response {
	/*** 原始访问字符串 */
	private transient volatile String o_request_str;
	/*** 是否含有该字符串 */
	private volatile boolean existFlag = false;
	/** 这个属性是指所有地理编码服务返回的个数 */
	private int size = 0;

	/** 无参构造函数 **/
	public GeoCodingResponse() {
		super();
	}

	public GeoCodingResponse(GeoCodingRequest request) {
		this(request, null);
	}

	public GeoCodingResponse(GeoCodingRequest request,
			List<GeoCodingResult> results) {
		this.request = request;
		if (results == null)
			results = new ArrayList<GeoCodingResult>();
		for (Iterator<GeoCodingResult> it = results.iterator(); it.hasNext();)
			this.results.add(it.next());
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getO_request_str() {
		return o_request_str;
	}

	public void setO_request_str(String o_request_str) {
		this.o_request_str = o_request_str;
	}

	public boolean isexistFlag() {
		return existFlag;
	}

	public void setexistFlag(boolean existFlag) {
		this.existFlag = existFlag;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator<Result> it = results.iterator(); it.hasNext();) {
			Result result = it.next();
			sb.append(result.toString());
		}
		return "GeoCodingResponse{size=" + size + ",results=" + sb.toString()
				+ "}";
	}
}
