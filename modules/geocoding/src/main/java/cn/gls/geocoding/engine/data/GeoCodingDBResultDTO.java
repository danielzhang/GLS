package cn.gls.geocoding.engine.data;

import cn.gls.data.StandardAddress;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @ClassName: GeoCodingDBResult.java
 * @Description 数据库对应的对象
 * @Date 2012-6-5
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-13
 */
public class GeoCodingDBResultDTO {
	/***标准地址*/
	StandardAddress address;
	/**精确的点坐标**/
	Geometry geometry;
	/***四至范围*/
	ViewPort viewport;
	
	public StandardAddress getAddress() {
		return address;
	}
	public void setAddress(StandardAddress address) {
		this.address = address;
	}

    public Geometry getGeometry() {
        return geometry;
    }
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    public ViewPort getViewport() {
		return viewport;
	}
	public void setViewport(ViewPort viewport) {
		this.viewport = viewport;
	}

}
