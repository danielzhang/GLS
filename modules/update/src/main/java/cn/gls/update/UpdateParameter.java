package cn.gls.update;

import com.vividsolutions.jts.geom.Geometry;



/**
 * 
 * @date 2012-8-29
 * @author "Daniel Zhang"
 * @update 2012-8-29
 * @description ���²���
 *
 */
public class UpdateParameter {
  //��ַ
	private String address;
  //����
  private String type;
  //��������
  private Geometry geometry;
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public Geometry getGeometry() {
	return geometry;
}
public void setGeometry(Geometry geometry) {
	this.geometry = geometry;
}
  
}
