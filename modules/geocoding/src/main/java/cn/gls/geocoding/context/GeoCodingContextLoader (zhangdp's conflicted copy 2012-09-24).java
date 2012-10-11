package cn.gls.geocoding.context;

import java.io.IOException;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import cn.gls.context.GLSContextLoader;
import cn.gls.util.XMLParse;

/**
 * 类名 GeoCodingContextLoader.java 说明 description of the class 创建日期 2012-5-22 作者
 * "Daniel Zhang"
 */
public class GeoCodingContextLoader extends GLSContextLoader {

/** 分级文件 */
private static final String CLASSIFICATION = "classification";

/** 引擎文件 */
private static final String ENGINE = "engine";



/** GeoCoding 上下文环境 */

protected static  GeoCodingConfigContext geoCodingContext = GeoCodingConfigContext.getInstance();

public  GeoCodingConfigContext getGeoCodingContext() {
	return geoCodingContext;
}

/**
 * 通过Context来产生ContextLoader
 * 
 * @param geoCodingContext
 */
public GeoCodingContextLoader() {
    super();
}

@Override
public void create() {
    super.create(geoCodingContext);
    // 加载引擎文档
    loadEngine(geoCodingContext.getProperties().getProperty(ENGINE));
    // 加载分类文档
    loadClassification(geoCodingContext.getProperties().getProperty(CLASSIFICATION));
   // geoCodingContext.setDictionary(configContext.getDictionary());
     //geoCodingContext.set
}

/** 销毁GeoCoding的文档类 */
@Override
public void destory() {
    super.destory();
    unGeoCodingDocument();
}

private void unGeoCodingDocument() {
    geoCodingContext.setClassDoc(null);
    geoCodingContext.setEngineDoc(null);
}

private void loadEngine(String path) {
    if (geoCodingContext.getEngineDoc() == null)
        geoCodingContext.setEngineDoc(XMLParse.readXML(path));
}

private void loadClassification(String path) {
    if (geoCodingContext.getClassDoc() == null)
        geoCodingContext.setClassDoc(XMLParse.readXML(path));
}

/**
 * 重新刷新GeoCoding的载入
 * 
 * @param gc
 * @param sc
 */
protected void RefreshGeoCodingContext() {

}

/**
 * 关闭GeoCoding的上下文环境
 * 
 * @param sc
 */
public void closeGeoCodingContext() {
    destory();
}
}
