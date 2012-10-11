package cn.gls.geocoding.context.support;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.gls.geocoding.context.GeoCodingConfigContext;
import cn.gls.geocoding.context.GeoCodingContext;

/**
 * 类名 GeoCodingContextUtil.java 说明 中文地理编码上下文环境的工具类 创建日期 2012-5-23 作者 张德品 版权 1.0
 * 更新时间 2012-05-27
 */
public class GeoCodingContextUtil {
	/** 产生上下文环境的类 */
	public static GeoCodingContext getGeoCodingContext(ServletContext sc) {
		return getGeoCodingContext(sc,
				GeoCodingConfigContext.ROOT_WEB_GeoCoding_CONTEXT_ATTRIBUTE);
	}

	public static GeoCodingContext GeoCodingContext() {
		HttpServletRequest Httprequest = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		sc = Httprequest.getSession().getServletContext();
		return getGeoCodingContext(sc);
	}

	/** 根据ServletContext的属性名来获取上下文环境的类 */
	public static GeoCodingContext getGeoCodingContext(ServletContext sc,
			String attribute) {
		Assert.notNull(sc, "ServletContext必须不能为空");
		Object obj = sc.getAttribute(attribute);
		if (obj == null)
			return null;
		if (!(obj instanceof GeoCodingContext))
			throw new IllegalStateException("这个对象不是GeoCodingContext类");
		return (GeoCodingContext) obj;
	}

	private static ServletContext sc;

	public static Object getBean(String beanName) {
		HttpServletRequest Httprequest = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		sc = Httprequest.getSession().getServletContext();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		return context.getBean(beanName);
	}

	public static boolean containBean(String beanName) {
		HttpServletRequest Httprequest = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		sc = Httprequest.getSession().getServletContext();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		return context.containsBean(beanName);
	}

}
