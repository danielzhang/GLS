package cn.gls.data;

/**
 * @ClassName: ThesaurusPlace.java
 * @Description 同义词地名词
 * @Date 2012-5-31
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-31
 */
public class TycPlace {
	/** 同义词id号 */
	Integer tid;

	/** 城市代码 */
	Integer cityCode;

	/** 同义词的名称 */
	String name;

	/** 标准词 */
	String stName;

	/** 同义词地名词构建函数 */
	public TycPlace(String name, int cityCode) {
		this(cityCode, name, null);
	}

	public TycPlace(Integer cityCode, String name, String stName) {
		super();
		this.cityCode = cityCode;
		this.name = name;
		this.stName = stName;
	}

	public TycPlace(Integer cityCode, String stName) {
		this(cityCode, null, stName);
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + this.stName.hashCode()
				+ this.cityCode.hashCode();
	}

	@Override
	public boolean equals(Object place) {
		if (this == place)
			return true;
		if (!(place instanceof TycPlace))
			return false;
		else {
			TycPlace other = (TycPlace) place;
			return (this.name.equalsIgnoreCase(other.getName())
					&& this.cityCode == other.getCityCode() && this.stName
						.equalsIgnoreCase(other.getStName()));
		}
	}

}
