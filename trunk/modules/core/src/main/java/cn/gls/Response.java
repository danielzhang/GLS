package cn.gls;

import java.util.List;

import cn.gls.data.GLSStatus;
import cn.gls.data.Result;
import cn.gls.data.ServiceType;

/**
 * @ClassName: Response.java
 * @Description 该类是用于返回服务结果的父类,主要是描述返回状态的类
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-25
 */
public class Response {
	private static final ThreadLocal<Response> CURRENT = new ThreadLocal<Response>();

	/** 服务类型 */
	private volatile ServiceType serviceType;

	/** 返回的信息 */
	private volatile String message;

	/** 相关联的服务请求 */
	protected transient volatile Request request;

	/** 返回状态 */
	private volatile String status = GLSStatus.OK;

	protected List<Result> results;

	public static Response getCurrent() {
		return CURRENT.get();
	}

	public static void setCurrent(Response response) {
		CURRENT.set(response);
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	/** 返回结果的无参构造函数 **/
	public Response() {
		this.serviceType = ServiceType.GEOCODING;
		this.status = GLSStatus.OK;
		this.message = "";
	}

	public Response(Request request) {
		this(request, null);
	}

	public Response(List<Result> results) {
		this(null, results);
	}

	public Response(Request request, List<Result> results) {
		super();
		this.serviceType = ServiceType.GEOCODING;
		this.status = GLSStatus.OK;
		this.message = "";
		this.request = request;
		this.results = results;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
}
