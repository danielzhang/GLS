package cn.gls.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SegAddress.java----待分词的地址
 * @Description 
 *              地址分完词后的状态,关于等级的划分。0表示还没有确定等级，1-中国，2-省级行政区，3-市级行政区，4-区、县级行政区，5-
 *              乡、镇级行政区，6-村级行政区，16-表示乡村级行政区内的编号。8--道路级别,18--道路编号.9-小区级别
 *              ,19-小区级别的编号。10-大楼/大厦。20--大楼大厦的编号。11-POI点,兴趣点。12-拼音。13-错别字。7-交叉口
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-9
 */
public class SegAddress {
	/** 原始地址 **/
	private String q;
	/*** 含有几条道路 */
	private int roadNum = 0;
	/** 是否含有拼音 */
	private boolean pinyin = false;
	/** 是否含有错误的地址词 **/
	private boolean misprint = false;
	/** 表示城市代号 */
	private int cityCode;
	/** 是否含有地名门牌号 */
	private boolean suffix = false;
	/**
	 * 地名词的存储,之所以用Map是因为可以判断哪一个词是拼音，哪一个是错别字 0-11----正常，13----拼音，14---错词，12--门牌号
	 * 应该是一个有序的集合。。。
	 */
	private List<Place> placesList = new ArrayList<Place>();


	public List<Place> getPlacesList() {
		return placesList;
	}

	public void setPlacesList(List<Place> placesList) {
		this.placesList = placesList;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public boolean isSuffix() {
		return suffix;
	}

	public void setSuffix(boolean suffix) {
		this.suffix = suffix;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
	public int getRoadNum() {
		return roadNum;
	}

	public void setRoadNum(int roadNum) {
		this.roadNum = roadNum;
	}

	public boolean isPinyin() {
		return pinyin;
	}

	public void setPinyin(boolean pinyin) {
		this.pinyin = pinyin;
	}

	public boolean isMisprint() {
		return misprint;
	}

	public void setMisprint(boolean misprint) {
		this.misprint = misprint;
	}



}
