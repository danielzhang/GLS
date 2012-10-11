package cn.gls.geometry.data;

import java.util.List;
/**
 * 
 * @date 2012-7-16
 * @author "Daniel Zhang"
 * @update 2012-7-16
 * @description 单一的线
 *
 */
public class Line extends Geometry{
	public List<Point> points;

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Line(List<Point> points) {
		super();
		this.points = points;
	}

}
