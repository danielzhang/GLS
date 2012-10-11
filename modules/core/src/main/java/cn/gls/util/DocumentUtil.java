package cn.gls.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.gls.geometry.data.Line;
import cn.gls.geometry.data.LinearRing;
import cn.gls.geometry.data.Point;
import cn.gls.geometry.data.Polygon;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.Fields;


/**
 * @date 2012-5-23
 * @author "Daniel Zhang"
 * @update 2012-7-16
 * @description TODO
 *
 */
public class DocumentUtil {
	/**
	 * Document转化为Object类 先把Document转化成XML然后再把XML转化成Java Object
	 * 
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	public static Object document2Object(Document doc)
			throws TransformerException {
		Source xmlSource = new DOMSource(doc);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Result result = new StreamResult();
		transformer.transform(xmlSource, result);
		return result;

	}

	/**
	 * object类转化为Document对象
	 * 
	 * @param obj
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Document object2Document(Object obj, Document document)
			throws ParserConfigurationException, SAXException, IOException {
		Class<?> clazz = obj.getClass();
		XStream xstream = new XStream();
		xstream.alias(clazz.getSimpleName(), clazz);
		simpleXML(xstream);
//	Field[] fields=	clazz.getDeclaredFields();
//	 for(Field field:fields){
//		 System.out.println("genericType"+field.getGenericType());
//		 System.out.println("class"+field.getClass());
//		 System.out.println("declarClass"+field.getDeclaringClass());
//	 }
		String xmlobj = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ xstream.toXML(obj);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		// 从DOM工厂中获得DOM解析器
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		// 把要解析的xml文档读入DOM解析器

		Document document1 = (DocumentImpl) dbBuilder.parse(new InputSource(
				new StringReader(xmlobj)));
		int length = document1.getChildNodes().getLength();
		for (int i = 0; i < length; i++) {
			document.appendChild(document.importNode(document1.getChildNodes()
					.item(i), true));
		}
		return document;
	}
	
	private static void simpleXML(XStream xstream){
		 xstream.alias(Point.class.getSimpleName(), Point.class);
		 xstream.alias(LinearRing.class.getSimpleName(),LinearRing.class);
		 xstream.alias(Polygon.class.getSimpleName(), Polygon.class);
		 xstream.alias(Line.class.getSimpleName(), Line.class);
	}
}
