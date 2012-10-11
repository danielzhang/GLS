package cn.gls.data.place;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @ClassName: ProvinceDTO.java
 * @Description 省的dto与st_a_province对应
 * @Date 2012-6-21
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-21
 */
public class ProvinceDTO {
	private String name;
	private Integer provinceCode;
	private String stAddress;
	private Integer zipCode;
	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	private Geometry geom;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getStAddress() {
		return stAddress;
	}

	public void setStAddress(String stAddress) {
		this.stAddress = stAddress;
	}

	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}
}
