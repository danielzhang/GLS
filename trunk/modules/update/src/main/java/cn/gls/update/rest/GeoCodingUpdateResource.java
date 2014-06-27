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
 * @description ���ĵ���������ݸ���,��Ҫ�ṩһЩ�ӿ�
 * 
 */
public class GeoCodingUpdateResource extends ServerResource {
	/** ���ݸ��²��� */
	private UpdateParameter uParameter;

	@Override
	protected void doInit() {
		// �����ĸ���������ַ�����ͣ���γ������
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
