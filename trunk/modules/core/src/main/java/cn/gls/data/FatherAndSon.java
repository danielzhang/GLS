package cn.gls.data;

import java.io.Serializable;

/**
 * @ClassName: FatherAndSon.java
 * @Description 子父类
 * @Date 2012-6-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-25
 */
public class FatherAndSon implements Serializable {
	private String fatherName;

	private int fatherCode;

	private int sonCode;

	private String sonName;

	private int cityCode;
	

	public FatherAndSon(String fatherName, String sonName, int cityCode) {
		super();
		this.fatherName = fatherName;
		this.sonName = sonName;
		this.cityCode = cityCode;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public int getFatherCode() {
		return fatherCode;
	}

	public void setFatherCode(int fatherCode) {
		this.fatherCode = fatherCode;
	}

	public int getSonCode() {
		return sonCode;
	}

	public void setSonCode(int sonCode) {
		this.sonCode = sonCode;
	}

	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Override
	public int hashCode() {
		return this.fatherName.hashCode() + this.sonName.hashCode()
				+ this.cityCode;
	}

	@Override
	public boolean equals(Object fatherAndSon) {
		if (this == fatherAndSon)
			return true;
		if (!(fatherAndSon instanceof FatherAndSon))
			return false;
		else {
			FatherAndSon other = (FatherAndSon) fatherAndSon;
			if (this.cityCode == other.getCityCode()
					&& this.sonName.equalsIgnoreCase(other.getSonName())
					&& this.fatherName.equalsIgnoreCase(other.getFatherName()))
				return true;
			else
				return false;
		}
	}

}
