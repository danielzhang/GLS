package cn.gls.geocoding.engine.data;




/**
 * @ClassName: Geometry.java
 * @Description 返回查询的几何结果.
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-16
 */
public class Geometry {
/**
 * 包含地址解析生成的纬度值和经度值。对于常规地址查询，此字段通常是最重要的。 / Location location; /**
 * 存储有关指定位置的附加数据。当前支持以下值： "ROOFTOP" 表示传回的结果是一个精确的地址解析值，我们可获得精确到街道地址的位置信息。
 * "RANGE_INTERPOLATED"
 * 表示返回的结果是一个近似值（通常表示某条道路上的地址），该地址处于两个精确点（如十字路口）之间。当无法对街道地址进行精确的地址解析时
 * ，通常会返回近似结果。 "GEOMETRIC_CENTER" 表示返回的结果是折线（如街道）或多边形（区域）等内容的几何中心。 "APPROXIMATE"
 * 表示返回的结果是一个近似值。
 * 
 **/
LocationType location_type=LocationType.ROOFTOP;
/**
 * 表示地址解析器未传回原始请求的完全匹配项，但与请求地址的一部分匹配。您可能希望检查原始请求的拼写是否正确和/或检查地址是否完整。
 * 当街道地址不在请求中传递的地区中时，往往会出现部分匹配。如果是false表示标准匹配，true表示部分匹配或有拼音或地址查询错误。
 */
boolean partial_match;

cn.gls.geometry.data.Geometry location;

/**
 * 包含用于显示传回结果的建议可视区域，并被指定为两个纬度/经度值，分别定义可视区域边框的 southwest 和 northeast
 * 角。通常，该可视区域用于在将结果显示给用户时作为结果的框架。
 */
ViewPort viewport;

public Geometry(LocationType location_type, ViewPort viewport, cn.gls.geometry.data.Geometry geo,boolean partial_match) {
    super();
    this.location_type = location_type;
    this.viewport = viewport;
    this.location = geo;
    this.partial_match=partial_match;
}
/**
 * 只有一个精确点组成
 * @param location
 */
public Geometry(cn.gls.geometry.data.Geometry geo) {
    this(LocationType.APPROXIMATE,null,geo,false);
}


public LocationType getLocation_type() {
    return location_type;
}

public void setLocation_type(LocationType location_type) {
    this.location_type = location_type;
}

public ViewPort getViewport() {
    return viewport;
}
public cn.gls.geometry.data.Geometry getLocation() {
    return location;
}
public void setLocation(cn.gls.geometry.data.Geometry location) {
    this.location = location;
}
public void setViewport(ViewPort viewport) {
    this.viewport = viewport;
}

public boolean isPartial_match() {
    return partial_match;
}

public void setPartial_match(boolean partial_match) {
    this.partial_match = partial_match;
}

}
