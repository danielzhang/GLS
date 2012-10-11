package cn.gls.database;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gls.database.postgis.standard.PostGISDataOperator;
import cn.gls.util.XMLParse;

/**
 * @ClassName: LoadApplicationContext.java
 * @Description 加载xml文件的
 * @Date 2012-6-19
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-19
 */
public class LoadApplicationContext implements ApplicationContextAware {

	private ApplicationContext appContext;

	private Document document;

	public Document getDocument() {
		return document;
	}

	private static LoadApplicationContext instance = new LoadApplicationContext();

	private LoadApplicationContext() {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(
					"classpath:conf/applicationContext.xml");
			try {
				document = new SAXReader().read(Thread.currentThread()
						.getContextClassLoader()
						.getResourceAsStream("conf/tables.xml"));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static LoadApplicationContext getInstance() {
		return instance;
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
	}

	public Object getBean(String beanName) {

		return appContext.getBean(beanName);
	}

	public boolean containBean(String beanName) {
		return appContext.containsBean(beanName);
	}

}
