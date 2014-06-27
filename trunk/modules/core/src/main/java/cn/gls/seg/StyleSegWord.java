package cn.gls.seg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import cn.gls.context.GLSConfigContext;
import cn.gls.context.GLSContextLoader;
import cn.gls.data.GLSRequestParameter;
import cn.gls.data.Place;
import cn.gls.data.SegAddress;
import cn.gls.util.PlaceUtil;

/**
 * @ClassName StyleSegWord.java
 * @Description 这个类主要是进行地名词的分词
 * @Version 1.0
 * @Update 2012-7-9
 * @author "Daniel Zhang"
 * 
 */
public class StyleSegWord implements ISegWord {
	public StyleSegWord() {
		super();
	}

	private SegAddress dFeature = new SegAddress();

	private SegAddress getAddress(String address) {
		Pattern pattern = Pattern.compile(GLSConfigContext.getInstance()
				.getDefaultProperties().getProperty("spaceCharacter"));
		// 去空格
		String[] sentences = pattern.split(address);
		List<String> queryMap = new ArrayList<String>();
		int sl = sentences.length;
		for (int i = 0; i < sl; i++) {
			queryMap.add(sentences[i]);
		}
		// 分词
		int ql = queryMap.size();

		List<Place> placesList = new ArrayList<Place>();
		for (int i = 0; i < ql; i++) {
			placesList.addAll(segWords(queryMap.get(i)));
		}
		dFeature.setQ(address);
		dFeature.setPlacesList(placesList);
		return dFeature;

	}

	// 进行分词啊,替代中文分词引擎进行分词。
	private List<Place> segWords(String queryStr) {
//		if (queryStr.equalsIgnoreCase("川沙路4088号(近王桥站)")) {
//			System.out.println(queryStr);
//		}
		// 这个分词是用关键字截取来分词的
		List<Place> places = new ArrayList<Place>();
		Map<Integer, List<String>> styles = readStyle();

		Iterator iterator = styles.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, List<String>> iEntry = (Map.Entry<Integer, List<String>>) iterator
					.next();
			// 开始了关键字分词阶段从第一级别开始
			List<String> keywords = iEntry.getValue();
			for (int i = 0; i < keywords.size(); i++) {
				String temp = keywords.get(i);
				int index = queryStr.indexOf(temp);
				if (index > 0) {
					String word = queryStr.substring(0, index + temp.length());
					// 判断是哪一种类型
					if (PlaceUtil.ispinyin(word)) {
						Place place = new Place();
						place.setAllName(word);
						place.setPlaceLevel(12);
						dFeature.setPinyin(true);
						places.add(place);
					} else if (iEntry.getKey() == 18) {
						if (places.size() == 0)
							continue;
						Place place = places.get(places.size() - 1);
						place.setSuffix(word);
						dFeature.setSuffix(true);
					} else {
						if (word.length() > 1) {
							Place place = new Place();
							place.setAllName(word);
							place.setPlaceLevel(iEntry.getKey());
							places.add(place);
						}
					}
					queryStr = queryStr.substring(index + temp.length(),
							queryStr.length());
					break;
				}
			}

		}

		// 如果queryStr不能于0那么这个要放到2里面
		if (queryStr.length() > 1) {

			if (PlaceUtil.isHouseNumber(queryStr)) {
				// errorMap.put(queryStr, 3);
				// words.add(errorMap);
				if (places.size() != 0) {
					Place place = places.get(places.size() - 1);
					place.setSuffix(place.getSuffix() == null ? queryStr
							: place.getSuffix() + queryStr);
					dFeature.setSuffix(true);
				}

			} else {
				Place place = new Place();
				place.setAllName(queryStr);
				place.setPlaceLevel(13);
				places.add(place);
				dFeature.setMisprint(true);
			}
		}

		return places;
	}

	// 进行读取style文件
	private Map<Integer, List<String>> readStyle() {
		Map<Integer, List<String>> styles = new TreeMap<Integer, List<String>>();
		styles.put(1, Arrays.asList(PlaceUtil.COUNTRY.split("、")));
		styles.put(2, Arrays.asList(PlaceUtil.PROVINCE.split("、")));
		styles.put(3, Arrays.asList(PlaceUtil.CITY.split("、")));
		styles.put(4, Arrays.asList(PlaceUtil.COUNTY.split("、")));
		styles.put(5, Arrays.asList(PlaceUtil.TOWN.split("、")));
		styles.put(6, Arrays.asList(PlaceUtil.VILLAGE.split("、")));
		styles.put(8, Arrays.asList(PlaceUtil.STREET.split("、")));
		styles.put(18, Arrays.asList(PlaceUtil.PLACE_SUFFIX.split("、")));
		styles.put(9, Arrays.asList(PlaceUtil.COMMUNITY.split("、")));
		styles.put(10, Arrays.asList(PlaceUtil.BUILDING.split("、")));

		return styles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gis.geocoding.seg.ISegWord#seg4Address(cn.gis.geocoding.engine.data
	 * .GeoCodingParameter)
	 */
	public SegAddress seg4Address(GLSRequestParameter gParam) {
		return getAddress(gParam.getQ());
	}
	 public static void main(String[] args) {
	 StyleSegWord segWord=new StyleSegWord();
	 SegAddress address= segWord.seg4Address(new
	 GLSRequestParameter("川沙路4088号(近王桥站)"));
	 
	 }

}
