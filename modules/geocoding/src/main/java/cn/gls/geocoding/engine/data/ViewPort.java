package cn.gls.geocoding.engine.data;

import cn.gls.geometry.data.Extent;

/**
 * @author 张德品
 * @date 2012-5-25
 * @说明 四至矩形
 */
public class ViewPort {
private Extent extent;

public Extent getExtent() {
	return extent;
}

public void setExtent(Extent extent) {
	this.extent = extent;
}

public ViewPort(Extent extent) {
	super();
	this.extent = extent;
}

}
