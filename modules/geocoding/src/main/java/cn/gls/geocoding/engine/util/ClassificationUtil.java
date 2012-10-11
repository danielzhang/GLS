package cn.gls.geocoding.engine.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.gls.data.Parameter;



/**
 * @ClassName GeoCodingUtil.java
 * @Createdate 2012-6-5
 * @Description 中文地理编码的工具类
 * @Version 1.0
 * @Update 2012-6-5 
 * @author "Daniel Zhang"
 *
 */
public class ClassificationUtil {
	/***
	 * 通过参数的Name来得到Values值
	 * 
	 * @param parameters
	 * @param name
	 */
	public static String getParamterValue(List<Parameter> parameters,
			String name) {
		String value = null;
		for (Iterator<Parameter> it = parameters.iterator(); it.hasNext();) {
			Parameter parameter = it.next();
			if (name.equalsIgnoreCase(parameter.getName())) {
				value = parameter.getName();
				break;
			}
		}
		return value;
	}

	/***
	 * 获得最高分数
	 * 
	 * @param doc
	 * @return
	 */
	public static int getScore(Document doc) {
		Element element = doc.getRootElement();
		return Integer.valueOf(element.element("SCORE").getText().toString());
	}

	/***
	 * 获得特定级别的分数值
	 * 
	 * @param input
	 * @param address
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static float getScore(String type, Document doc) {
		float score = 0;
		Element element = doc.getRootElement();
		for (
		Iterator<Element> k = element.elementIterator("ENGINE"); k
				.hasNext();) {
			Element node = k.next();
			if (type.equalsIgnoreCase(node.attribute("engine_selector")
					.getValue())) {
				for (Iterator<Element> levels = node.elementIterator("LEVEL"); levels
						.hasNext();) {
					Element level = levels.next();
					score= (Float.valueOf(level
							.attribute("grade").getValue().toString()));					
				}
				break;
			}
		}
		return score;
	}
	
	/***
	 * 获得特定级别的分数值
	 * 
	 * @param input
	 * @param address
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static float getEngineScore(String addrType, Document doc) {
		float score = 0;
		Element element = doc.getRootElement();
		for (Iterator<Element> k = element.elementIterator("ENGINE"); k
				.hasNext();) {
			Element node = k.next();
			if (addrType.equalsIgnoreCase(node.attribute("engine_name")
					.getValue())) {
				for (Iterator<Element> levels = node.elementIterator("LEVEL"); levels
						.hasNext();) {
					Element level = levels.next();
					score= (Float.valueOf(level
							.attribute("grade").getValue().toString()));					
				}
				break;
			}
		}
		return score;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map<Integer, Integer> getWeightMap(Document doc){
		Map<Integer, Integer> maps=new HashMap<Integer, Integer>();
		Element element=doc.getRootElement();
		for(Iterator<Element> eIterator=element.elementIterator("ENGINE");eIterator.hasNext();){
			Element node=eIterator.next();
			if("address".equalsIgnoreCase(node.attribute("engine_selector").getValue())){
			  for(Iterator<Element> weightIterator=node.elementIterator("weight");weightIterator.hasNext();){
				   Element weightElement=weightIterator.next();
				   for(Iterator<Element> levelIterator=weightElement.elementIterator("level");levelIterator.hasNext();){
					   Element level=levelIterator.next();
					   Integer key=Integer.valueOf(level.attribute("tag").getValue());
					   Integer value=Integer.valueOf(level.attributeValue("value").toString());
					   maps.put(key, value);   
				   }
			  }
			}
		}
		return maps;
	}
	@SuppressWarnings("unchecked")
	public static List<List<Integer>> gettags(String grade, Document doc) {
		Element element = doc.getRootElement();
		List<List<Integer>> tags = new ArrayList<List<Integer>>();
		for (Iterator<Element> k = element.elementIterator("ENGINE"); k
				.hasNext();) {
			Element node = k.next();
			if ("Address".equalsIgnoreCase(node.attribute("engine_selector")
					.getValue().toString())) {
				for (Iterator<Element> levels = node.elementIterator("LEVEL"); levels
						.hasNext();) {
					Element level = levels.next();
					if (grade.equalsIgnoreCase(level.attribute("level_index")
							.getValue().toString())) {
						// 锟矫碉拷一锟斤拷锟斤拷锟斤拷

						// List<Element> esituations = level.elements();
						for (Iterator<Element> situation = level
								.elementIterator("LEVEL_SITUATION"); situation
								.hasNext();) {
							List<Integer> tag = new ArrayList<Integer>();
							Element stion = situation.next();
							for (Iterator<Element> tagelement = stion
									.elementIterator("LEVEL_ELEMENT"); tagelement
									.hasNext();) {
								Element telement = tagelement.next();
								tag.add(Integer
										.valueOf(telement.attribute("tag")
												.getValue().toString()));
							}
							tags.add(tag);
						}
					}

				}
			}

		}
		return tags;
	}

}
