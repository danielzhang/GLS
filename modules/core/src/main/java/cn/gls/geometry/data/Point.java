package cn.gls.geometry.data;

import java.io.Serializable;

/**
 * @ClassName: Point.java
 * @Description 点几何要素
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-16
 */
public final class Point  extends Geometry implements Serializable {
	/***/
	private static final long serialVersionUID = 1L;

	/** 纬度坐标 */
	double lat;

	/** 经度坐标 **/
	double lng;

	/**
	 * 构造函数,由经纬度来构建Point对象 *
	 */
	public Point(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}


}
