package cn.gls.geocoding.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import cn.gls.geocoding.context.GeoCodingConfigContext;
import cn.gls.geocoding.context.GeoCodingContextLoader;

/**
 * @ClassName GeoCodingContextListener.java
 * @Description GeoCodingContext监听器。在这里是一切web服务的入口
 * @Version 1.0
 * @Update 2012-5-27
 * @author "Daniel Zhang"
 * 
 */
public class GeoCodingContextListener extends GeoCodingContextLoader implements
		ServletContextListener {
//
//	private static final Log log = LogFactory
//			.getLog(GeoCodingContextListener.class);

	private GeoCodingContextLoader geoCodingContextLoader;

	private ServletContext servletContext;

	/***
	 * 获得geoCoding的contextLoade
	 * 
	 * @return GeoCodingContextLoader
	 */
	public GeoCodingContextLoader getGeoCodingContextLoader() {
		return geoCodingContextLoader;
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("开始回收垃圾内存了");
	}

	/** 限制不能直接产生这个对像 */
	public GeoCodingContextListener() {
		super();
	}

	/** 加载地理编码所需的文档和词库 */
	@Override
	public void create() {
		super.create();

	}

	/*
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */

	public void contextDestroyed(ServletContextEvent arg0) {
		if (geoCodingContextLoader != null)
			geoCodingContextLoader.closeGeoCodingContext();
		if (servletContext
				.getAttribute(GeoCodingConfigContext.ROOT_WEB_GeoCoding_CONTEXT_ATTRIBUTE) != null)
			servletContext
					.removeAttribute(GeoCodingConfigContext.ROOT_WEB_GeoCoding_CONTEXT_ATTRIBUTE);
	}

	/**
	 * 监听取消时，应调用消除词库加载
	 */
	public void destroy() {
		super.destory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */

	public void contextInitialized(ServletContextEvent arg0) {
		if (geoCodingContextLoader == null)
			this.geoCodingContextLoader = this;
		servletContext = arg0.getServletContext();
		this.geoCodingContextLoader.create();
		servletContext.setAttribute(
				GeoCodingConfigContext.ROOT_WEB_GeoCoding_CONTEXT_ATTRIBUTE,
				geoCodingContext);

	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
