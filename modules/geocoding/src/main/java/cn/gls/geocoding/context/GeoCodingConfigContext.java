package cn.gls.geocoding.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.dom4j.Document;
import cn.gls.context.GLSConfigContext;

/**
 * @ClassName GeoCodingContext.java
 * @Description 中文地理的上下文配置类。
 * @Version 1.0
 * @Update 2012-5-27
 * @author "Daniel Zhang"
 */
public class GeoCodingConfigContext extends GLSConfigContext implements GeoCodingContext{
/** 分类的文档 */
private  Document classDoc;


/** 引擎相关文档 */
private  Document engineDoc;


/** 定义一个properties文件 */
public static  Properties geoCodingStrategies=new Properties();

/** 属性配置文件的位置 */
private static final String DEFAULT_STRATEGIES_PATH = "GeoCodingContext.properties";
static {
    try {
    	InputStream input=GeoCodingConfigContext.class.getResourceAsStream(DEFAULT_STRATEGIES_PATH);
    	geoCodingStrategies.load(input);
    } catch (IOException e) {
        throw new IllegalStateException(
                "Could not load 'GeoCodingContext.properties': "
                        + e.getMessage());
    }
}

private static final GeoCodingConfigContext instance = new GeoCodingConfigContext();

public static final String ROOT_WEB_GeoCoding_CONTEXT_ATTRIBUTE="GeoCodingContext.ROOT";
public GeoCodingConfigContext() {
    super();
}
/**GeoCodingConfig单例模式*/
public static synchronized GeoCodingConfigContext getInstance() {
    return instance;
}

/** 获得引擎文档 **/
public  Document getEngineDoc() {
    return engineDoc;
}

/*** 获得分级文档 */
public  Document getClassDoc() {
    return classDoc;
}
/**设置分级文档*/
public  void setClassDoc(Document classDoc) {
    this.classDoc = classDoc;
}
/**设置引擎文档*/
public  void setEngineDoc(Document engineDoc) {
    this.engineDoc = engineDoc;
}
/* (non-Javadoc)
 * @see cn.gbs.geocoding.context.GeoCodingContext#getProperties()
 */
public Properties getProperties() {
	
	return geoCodingStrategies;
}
}
