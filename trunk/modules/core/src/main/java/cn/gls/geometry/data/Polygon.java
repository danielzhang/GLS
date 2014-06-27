package cn.gls.geometry.data;

import java.util.List;

/**
 * 
 * @date 2012-7-16
 * @author "Daniel Zhang"
 * @update 2012-7-16
 * @description 多边形
 *
 */
public class Polygon extends Geometry{
  List<LinearRing> linearRings;

public List<LinearRing> getLinearRings() {
	return linearRings;
}

public void setLinearRings(List<LinearRing> linearRings) {
	this.linearRings = linearRings;
}

public Polygon(List<LinearRing> linearRings) {
	super();
	this.linearRings = linearRings;
}
  
}
