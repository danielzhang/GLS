package cn.gls.geometry.data;

import java.util.List;

/**
 * 
 * @date 2012-7-16
 * @author "Daniel Zhang"
 * @update 2012-7-16
 * @description TODO
 *
 */
public class Extent extends Geometry {
	
 private Point southwest;

private Point northeast;

public Extent(Point southwest, Point northeast) {
	super();
	this.southwest = southwest;
	this.northeast = northeast;
}

public Point getSouthwest() {
	return southwest;
}

public void setSouthwest(Point southwest) {
	this.southwest = southwest;
}

public Point getNortheast() {
	return northeast;
}

public void setNortheast(Point northeast) {
	this.northeast = northeast;
}


 
}
