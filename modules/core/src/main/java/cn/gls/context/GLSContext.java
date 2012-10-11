package cn.gls.context;


import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;

import cn.gls.data.ServiceType;
import cn.gls.seg.dic.Dictionary;
import cn.gls.util.IPUtil.IPInfo;

/**
 * @ClassName: GLSContext.java
 * @Description  这个接口是上下文环境，能够得到词库，分类配置document,引擎文件等等
 * @Date   2012-5-22
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-7-12
 */
public interface GLSContext {

	/** 得到服务类型 */
	ServiceType getServiceType();
	/**获得词库*/
	Dictionary getDictionary();
	/***获得ip地址库*/
	Map<Integer, Map<Long, IPInfo>> getIpMap();
	/**加载地址格式**/
	Document getPlaceStyle();
	
    Properties getDefaultProperties();

}
