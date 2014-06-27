package cn.gls;

import cn.gls.data.GLSRequestParameter;
import cn.gls.data.ServiceType;

/**
 * @ClassName: Request.java
 * @Description 服务的请求参数,在此还可以设置服务的许多限制
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-29
 */
public class Request {
ServiceType serviceType;


 protected GLSRequestParameter gbsRequestParameter;

public Request() {
    super();
}
/**利用ServiceType,Parameter進行初始化*/
public Request(ServiceType serviceType,GLSRequestParameter gbsRequestParamter) {
    super();
    this.serviceType = serviceType;
    this.gbsRequestParameter=gbsRequestParamter;
}

/**利用Parameter進行初始化*/
public Request(GLSRequestParameter gbsRequestParamter){
   this(ServiceType.GEOCODING,gbsRequestParamter);
}
public static Request getCurrent() {
    return (Response.getCurrent() == null) ? null : Response.getCurrent()
            .getRequest();
}

public ServiceType getServiceType() {
    return serviceType;
}

public void setServiceType(ServiceType serviceType) {
    this.serviceType = serviceType;
}
public GLSRequestParameter getGbsRequestParameter() {
	return gbsRequestParameter;
}
public void setGbsRequestParameter(GLSRequestParameter gbsRequestParameter) {
	this.gbsRequestParameter = gbsRequestParameter;
}


}
