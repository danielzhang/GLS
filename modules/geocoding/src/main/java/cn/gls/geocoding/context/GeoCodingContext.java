package cn.gls.geocoding.context;

import java.util.Properties;
import org.dom4j.Document;

import cn.gls.context.GLSContext;

/**
 * @ClassName GeoCodingContext.java
 * @Description GeoCoding上下文环境
 * @date 2012-5-25
 * @Version 1.0
 * @Update 2012-5-30
 * @author "Daniel Zhang"
 * 
 */
public interface GeoCodingContext extends GLSContext {
	/**获得引擎文档*/
	Document getEngineDoc();
    /**获得分级文档*/
	Document getClassDoc();
   /**获得属性配置文件**/
	Properties getProperties();
}
