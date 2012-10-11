package cn.gls.geometry.data;

public class MultiPoint extends Geometry {
  Point[] points;

public Point[] getPoints() {
	return points;
}

public void setPoints(Point[] points) {
	this.points = points;
}

public MultiPoint(Point[] points) {
	super();
	this.points = points;
}
  
}
