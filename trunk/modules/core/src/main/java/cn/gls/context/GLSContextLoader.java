package cn.gls.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.gls.seg.dic.Dictionary;
import cn.gls.util.IPUtil;
import cn.gls.util.XMLParse;

/**
 * @ClassName GBSWebContext.java
 * @Description GBS的Web上下文环境
 * @Version 1.0
 * @Update 2012-5-26
 * @author "Daniel Zhang"
 */

public class GLSContextLoader implements GLSLifeCycle {

	/** 地址样式列表 */
	private static final String PLACE_STYLE = "placeStyle";

	private static final String IP_ADDRESS = "ipaddressConfig";
	/** 定义一个properties文件 */
	private static final Properties defaultStrategies = new Properties();

	/** 属性配置文件的位置 */
	private static final String DEFAULT_STRATEGIES_PATH = "GLSContextLoader.proterties";

	static {
		try {
			InputStream in = GLSContextLoader.class
					.getResourceAsStream(DEFAULT_STRATEGIES_PATH);
			defaultStrategies.load(in);
		} catch (IOException e) {
			throw new IllegalStateException(
					"Could not load 'GLSContextLoader.properties': "
							+ e.getMessage());
		}
	}

	/** GLS配置上下文加载 */
	protected static GLSConfigContext configContext=GLSConfigContext.getInstance() ;
	//
	// public static GLSConfigContext getConfigContext() {
	// return configContext;
	// }

	private static final GLSContextLoader instance = new GLSContextLoader();

	public static synchronized GLSContextLoader getInstance() {
		return instance;
	}

	protected GLSContextLoader() {
		super();
		 create();
	}
	/*
	 * (non-Javadoc) 主要实现加载词库的作用
	 * 
	 * @see
	 * cn.gis.geocoding.context.GISServiceContext#loadDictionary(cn.gis.geocoding
	 * .seg.dic.Dictionary)
	 */
	private void loadDictionary() {
		if (configContext.getDictionary() == null)
			configContext.setDictionary(Dictionary.getInstance());
	}

	/**
	 * 加载ip地址类
	 * 
	 * @param sc
	 * @return
	 */
	private void loadIPDictionary() {
		if (configContext.getIpMap() == null)
			configContext.setIpMap(IPUtil.readToMap(defaultStrategies
					.getProperty((IP_ADDRESS))));
	}

	/***
	 * @Description 加载样式表
	 * @param path
	 * @return
	 */

	private void loadPlaceStyle(String path) {
		if (configContext.getPlaceStyle() == null)
			configContext.setPlaceStyle(XMLParse.readXML(path));
	}

	private void loadProperties() {
		if (configContext.getDefaultProperties() == null)
			configContext.setDefaultProperties(defaultStrategies);
	}

	/***** 产生Context对象主要作用是加载词库和ip文件及样式表 */
	public void create(GLSConfigContext configContext) {
		this.configContext=configContext;
		String placeStyle = defaultStrategies.getProperty(PLACE_STYLE);
		String ipPath = defaultStrategies.getProperty(IP_ADDRESS);
		// 加载词库
		loadDictionary();
		// 加载ip地址
		loadIPDictionary();
		// 加载地理编码的格式文件
		loadPlaceStyle(placeStyle);
		// 加载Key/Value文件
		loadProperties();
	}

	public void init() {
		// TODO Auto-generated method stub
	}

	public void gisService() {

	}

	/** 卸载词库，ip库，和地址样式表 **/
	public void destory() {
		if (configContext.getDictionary() != null)
			configContext.getDictionary().unload();
		configContext.setIpMap(null);
		configContext.setPlaceStyle(null);
		configContext.setDefaultProperties(null);

	}

	public void create() {
		create(configContext);
	}

}
