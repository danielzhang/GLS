package cn.gls.context;

import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;

import cn.gls.data.ServiceType;
import cn.gls.seg.dic.Dictionary;
import cn.gls.util.IPUtil.IPInfo;

/**
 * @ClassName GBSConfigContext.java
 * @Description 实现GBSContext的接口 关键这个类是使用静态还是单例--先实现单例
 * @Version 1.0
 * @Update 2012-5-27
 * @author "Daniel Zhang"
 */
public class GLSConfigContext implements GLSContext {
/*** 服务类型 */
private ServiceType serviceType;

/** 加载ip文件属性 */
private Map<Integer, Map<Long, IPInfo>> ipMap;


/** placeStyle文档 **/
private Document placeStyle;

/** 词库 */
private Dictionary dictionary;

private Properties defaultProperties;



private static final GLSConfigContext instance = new GLSConfigContext();




/** 构造方法私有。不能被实现 **/
protected GLSConfigContext() {

}

/** 实现单例实例化 */
public  static synchronized GLSConfigContext getInstance() {
    return instance;
}

/** 获得服务类型 **/
public ServiceType getServiceType() {

    return serviceType;
}

/** 获得词库 */
public Dictionary getDictionary() {

    return dictionary;
}

/** 获得ip地址 */
public Map<Integer, Map<Long, IPInfo>> getIpMap() {

    return ipMap;
}

/** 获得地址样式 */
public Document getPlaceStyle() {

    return placeStyle;
}
public void setServiceType(ServiceType serviceType) {
    this.serviceType = serviceType;
}

public void setIpMap(Map<Integer, Map<Long, IPInfo>> ipMap) {
    this.ipMap = ipMap;
}

public void setPlaceStyle(Document placeStyle) {
    this.placeStyle = placeStyle;
}

public void setDictionary(Dictionary dictionary) {
    this.dictionary = dictionary;
}
public Properties getDefaultProperties() {
	return defaultProperties;
}

public void setDefaultProperties(Properties defaultProperties) {
	this.defaultProperties = defaultProperties;
}

}
