package cn.gls.util;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * @ClassName XMLParse.java
 * @Description 读取XML文件
 * @Version 1.0
 * @Update 2012-5-27
 * @author "Daniel Zhang"
 * 
 */
public class XMLParse {
	/**
	 * 读取file文件为Document
	 * 
	 * @param filepath
	 * @return
	 */
	public static Document readXML(String filepath) {
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			if(filepath!=null&&!"".equalsIgnoreCase(filepath))
			doc = reader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
}
