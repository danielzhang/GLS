package cn.gls.data;

/**
 * @ClassName: PinyinPlace.java
 * @Description 拼音地名词对象
 * @Date 2012-5-31
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-31
 */
public class PinyinPlace extends Object {
	/*** id号 */
	int pid;



	/** 地名词 **/
	String name;
	/** 拼音 **/
	String pName;

	/** 城市代码 */
	int cityCode;

	/** 是否标准地名词 **/
	boolean isStandard=true;
	

	public PinyinPlace(String name, String pName, int cityCode) {
		super();
		this.name = name;
		this.pName = pName;
		this.cityCode = cityCode;
	}

	/**
	 * pinyin地名词的构造函数
	 * 
	 * @Title:PinyinPlace
	 * @Description:TODO
	 * @param: @param name
	 * @param: @param cityCode
	 * @return:
	 * @throws
	 */
	public PinyinPlace(String name, int cityCode) {
		this(name, null, cityCode);
	}

	@Override
	public int hashCode(){
		return this.name.hashCode()+this.pName.hashCode()+this.cityCode;
	}
	@Override
	public boolean equals(Object pinyin) {
		if (this == pinyin)
			return true;
		if (!(pinyin instanceof PinyinPlace))
			return false;
		else {
			PinyinPlace other = (PinyinPlace) pinyin;
			return (this.name.equals(other.getName())
					&& this.pName.equals(other.getpName()) && this.cityCode == other
						.getCityCode());
		}
	}
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public void setStandard(boolean isStandard) {
		this.isStandard = isStandard;
	}

	public boolean isStandard() {
		return isStandard;
	}

}
