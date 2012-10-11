package cn.gls.geometry.data;

import java.util.List;

/**
 * 
 * @date 2012-7-16
 * @author "Daniel Zhang"
 * @update 2012-7-16
 * @description 闭环线
 *
 */
public class LinearRing extends Geometry{
List<Point> points;

public List<Point> getPoints() {
	return points;
}

public void setPoints(List<Point> points) {
	this.points = points;
}

public LinearRing(List<Point> points) {
	super();
	this.points = points;
}

}
