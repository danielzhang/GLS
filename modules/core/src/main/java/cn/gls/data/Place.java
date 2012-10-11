package cn.gls.data;

import java.util.List;

import cn.gls.util.StringUtils;

/**
 * @ClassName: Place.java
 * @Description 这个类主要是用于定义地名词的结构与属性的.
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-9
 */
public final class Place implements Comparable<Place> {
	/**地名词的全称*/
	private String allName;
	
	/** 地名名称 */
	private String name;

	/** 地名词级别,默认为0表示暂无级别注意不能为16,18,19,20因为严格意义上说这些都不是地名词 */
	private transient int placeLevel = 0;

	/** 城市代码 */
	private transient int cityCode;

	/** 地名词类型 */
	private String placeType;

	/** 地名词的拼音 */
	private transient PinyinPlace pinyin;

	/** 同义词 */
	private transient List<TycPlace> placeTycs;
	
	/**后缀*/
	private String suffix;
	
	/** 该地名词是否在数据库中存在 */
	private transient boolean isExist;

	/** 地名词搜索次数 **/
	private transient int searchTime;
    
	
	
	
	/**
	 * 标准地名词有下面三个参数确定唯一性。
	 * 
	 * @param name
	 * @param placeLevel
	 * @param cityCode
	 */
	public Place(String allName, int placeLevel, int cityCode) {
		super();
	
		this.allName = allName;	
		this.name=this.suffix==null?allName:StringUtils.removeSuffix(allName, suffix);
		this.placeLevel = placeLevel;
		this.cityCode = cityCode;
	}

	public Place(String name) {
		super();
		this.name = name;
	}

	/**
	 * 当返回地名词时，用户就关心这三个属性
	 * 
	 * @Title:Place
	 * @Description:TODO
	 * @param name
	 * @param placeLevel
	 * @param placeType
	 * @return:
	 * @throws
	 */
	public Place(String allName, int placeLevel, String placeType) {
		super();
		this.name=this.suffix==null?allName:StringUtils.removeSuffix(allName, suffix);
		this.allName = allName;
		this.placeLevel = placeLevel;
		this.placeType = placeType;
	}
    
	public Place() {
		super();
	}

	@Override
	public int hashCode() {
		return this.allName.hashCode() + this.placeLevel + this.cityCode;
	}

	@Override
	public boolean equals(Object place) {

		if (this == place)
			return true;
		if (!(place instanceof Place))
			return false;
		else {
			Place other = (Place) place;
			return (this.allName.equalsIgnoreCase(other.getAllName())
					&& this.placeLevel == other.getPlaceLevel() && this.cityCode == other
						.getCityCode());
		}
	}

	public int getPlaceLevel() {
		return placeLevel;
	}

	public void setPlaceLevel(int placeLevel) {
		this.placeLevel = placeLevel;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public List<TycPlace> getPlaceTycs() {
		return placeTycs;
	}

	public void setPlaceTycs(List<TycPlace> placeTycs) {
		this.placeTycs = placeTycs;
	}



	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	public int getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(int searchTime) {
		this.searchTime = searchTime;
	}

	public PinyinPlace getPinyin() {
		return pinyin;
	}

	public void setPinyin(PinyinPlace pinyin) {
		this.pinyin = pinyin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.allName=this.suffix==null?name:name+suffix;
	}
	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
		this.name=this.suffix==null?allName:StringUtils.removeSuffix(allName, suffix);
	}
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
		this.allName=suffix!=null?name+suffix:name;
	}

	@Override
	public String toString() {
		return "Place{Allname=" + allName + ",placeLevel=" + placeLevel
				+ ",cityCode=" + cityCode + ",placeType=" + placeType + "}";
	}

	/**
	 * 按照地名级别进行排序
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Place o) {
		return ((Integer) this.getPlaceLevel()).compareTo(o.getPlaceLevel());
	}
}
