package cn.gls.geocoding.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.context.support.GeoCodingContextUtil;
import cn.gls.geocoding.engine.data.GeoCodingRequest;
import cn.gls.geocoding.engine.data.GeoCodingRequestParameter;
import cn.gls.geocoding.service.GeoCodingService;
import cn.gls.geocoding.service.IGeoCodingService;
import cn.gls.util.DocumentUtil;

/**
 * @ClassName: GeoCodingServerResource.java
 * @Description 地理编码服务的rest接口
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-29
 */
public class GeoCodingServerResource extends ServerResource {

	private static Log log = LogFactory.getLog(GeoCodingServerResource.class);
	/** 中文地理编码的相关请求 */
	private GeoCodingRequest geoRequest;

	private String f = "XML";

	public void setgeoRequest(GeoCodingRequest geoRequest) {
		this.geoRequest = geoRequest;
	}

	public void setF(String f) {
		this.f = f;
	}

	/**
	 * @说明:获得客户端的参数
	 */
	@Override
	protected void doInit() throws ResourceException {

		// 判断ip或者refer
		Form form1 = (Form) getRequest().getAttributes().get(
				"org.restlet.http.headers");
		Map<String, String> map = form1.getValuesMap();

		Iterator<Map.Entry<String, String>> iterator = map.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			if ("Referer".equalsIgnoreCase(entry.getKey())) {
				if (geoRequest.getGbsRequestParameter() == null) {

					GeoCodingRequestParameter gParameter = new GeoCodingRequestParameter();
					gParameter.setClientIp(entry.getValue());
				} else
					geoRequest.getGbsRequestParameter().setClientIp(
							entry.getValue());
				break;
			}
		}

		if (getMethod() == Method.GET) {
			// geoRequest.setMethod(Method.GET);
			Form form = getRequest().getResourceRef().getQueryAsForm();
			getParameter(form);
		} else if (getMethod() == Method.POST) {
			// geoRequest.setMethod(Method.POST);
		}

	}

	/**
	 * @说明：获得请求参数
	 * @param form
	 */
	private void getParameter(Form form) {
		if (geoRequest.getGbsRequestParameter() == null)
			geoRequest.setGbsRequestParameter(new GeoCodingRequestParameter());
		for (org.restlet.data.Parameter parameter : form) {
			try {
				if ("q".equalsIgnoreCase(parameter.getName())) {
					geoRequest.getGbsRequestParameter().setQ(
							URLDecoder.decode(parameter.getValue(), "UTF-8"));
					continue;
				}
				if ("f".equalsIgnoreCase(parameter.getName())) {
					f = URLDecoder.decode(parameter.getValue(), "UTF-8");
					continue;
				}
				if ("qs".equalsIgnoreCase(parameter.getName())) {
					geoRequest.getGbsRequestParameter().setBatch(true);
					geoRequest.getGbsRequestParameter().setQ(
							URLDecoder.decode(parameter.getValue(), "UTF-8"));
					continue;
				}
				if ("s".equalsIgnoreCase(parameter.getName())) {
					geoRequest.getGbsRequestParameter().setS(
							Float.valueOf(parameter.getValue()));
					continue;
				}

//				if ("p".equalsIgnoreCase(parameter.getName())) {
//					geoRequest.getGbsRequestParameter().setP(
//							Integer.valueOf(parameter.getValue()));
//					continue;
//				}
//				if ("c".equalsIgnoreCase(parameter.getName())) {
//					geoRequest.getGbsRequestParameter().setC(
//							Integer.valueOf(parameter.getValue()));
//					continue;
//				}
				if ("sl".equalsIgnoreCase(parameter.getName())) {
					geoRequest.getGbsRequestParameter().setStyle(
							Integer.valueOf(parameter.getValue()));
					continue;
				} else {

				}

			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
	}

	private Representation getGISRepresentation() {
		Representation representation = null;
		try {

			// Response response = new GeoCodingResponse(geoRequest);
		
			Object response;
			HttpServletRequest Httprequest = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			
			ServletContext sc = Httprequest.getSession().getServletContext();
			GeoCodingContext geoCodingContext = GeoCodingContextUtil
					.getGeoCodingContext(sc);

			IGeoCodingService geocoding = new GeoCodingService(geoCodingContext);
			if (geoRequest.getGbsRequestParameter().isBatch())
				response = geocoding.getBatchResponse(geoRequest);
			else
				response = geocoding.getResponse(geoRequest);

			if ("xml".equalsIgnoreCase(f)) {
				representation = new DomRepresentation(MediaType.TEXT_XML);
				Document document = ((DomRepresentation) representation)
						.getDocument();
			
		    	DocumentUtil.object2Document(response,document);
//		    	
//		    	System.out.println(document.getChildNodes().getLength());
//		    	document.normalizeDocument();
//		    	System.out.println(((DomRepresentation) representation)
//						.getDocument().getChildNodes().getLength());
//		    	System.out.println(((DomRepresentation) representation).getDocument()==document);
		    	document.normalizeDocument();
		//      System.out.println(doc.getElementsByTagName("GeoCodingResponse").item(0).getLocalName());
			} else {
				representation = new JsonRepresentation(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return representation;
	}

	/**
	 * 通过get请求来得到GIS服务的返回结果
	 */
	@Override
	@Get("xml|json")
	public Representation get() {
		return getGISRepresentation();
	}

	/**
	 * post请求来得到GIS服务的返回结果
	 */
	@Override
	@Post("xml|json")
	public Representation post(Representation entity) {
		Form form = new Form(entity);
		getParameter(form);
		return getGISRepresentation();
	}

}
