package cn.gls.data.place;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @ClassName: CityDTO.java
 * @Description  与表st_a_city对应
 * @Date  2012-6-21
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-21
 */
public class CityDTO {
    private String name;
    private String provinceName;
    private int cityCode;
    private String stAddress;
    private String aType;
    private int zipCode;
    private Geometry geom;
    
	public Geometry getGeom() {
		return geom;
	}
	public void setGeom(Geometry geom) {
		this.geom = geom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public String getStAddress() {
		return stAddress;
	}
	public void setStAddress(String stAddress) {
		this.stAddress = stAddress;
	}
	public String getaType() {
		return aType;
	}
	public void setaType(String aType) {
		this.aType = aType;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
}
