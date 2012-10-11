package cn.gls.geocoding.engine.data;

import cn.gls.geometry.data.Point;

/*
 * 类名      Location.java
 * 说明  某一位置信息
 * 创建日期 2012-5-25
 * 作者 Administrator
 * 版权  ***
 * 更新时间  $Date$
 * 标签   $Name$
 * SVN 版本  $Revision$
 * 最后更新者 $Author$
 */
public class Location {
	/** 点坐标 ***/
	Point point;

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
}
