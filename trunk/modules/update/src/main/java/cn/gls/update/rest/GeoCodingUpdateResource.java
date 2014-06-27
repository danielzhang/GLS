package cn.gls.update.rest;

import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import cn.gls.update.UpdateParameter;

/**
 * 
 * @date 2012-8-29
 * @author "Daniel Zhang"
 * @update 2012-8-29
 * @description 中文地理编码数据更新,主要提供一些接口
 * 
 */
public class GeoCodingUpdateResource extends ServerResource {
	/** 数据更新参数 */
	private UpdateParameter uParameter;

	@Override
	protected void doInit() {
		// 定义四个参数，地址，类型，经纬度坐标
		Form form = null;
		if (getMethod() == Method.GET) {
			form = getRequest().getResourceRef().getQueryAsForm();
		} else {
			form = getRequest().getEntityAsForm();
		}
		getParameter(form);
	}

	/****/
	private void getParameter(Form form) {
		if (form != null) {
			uParameter = new UpdateParameter();
			for (Parameter parameter : form) {
				if ("address".equalsIgnoreCase(parameter.getFirst())) {
					uParameter.setAddress(parameter.getValue());
				}
				if ("type".equalsIgnoreCase(parameter.getFirst())) {
					uParameter.setType(parameter.getValue());
				}
                if("lat".equalsIgnoreCase(parameter.getFirst())){
                	if(uParameter.getGeometry()==null)
                	{
                		//Geometry geom=new Point(coordinate, precisionModel, SRID)
                	}
                }
			}
		}
	}

	@Get
	@Post("xml|json")
	public void updateSingleData() {

	}

	@Post("xml|json")
	public void updateMultiData() {

	}
}
