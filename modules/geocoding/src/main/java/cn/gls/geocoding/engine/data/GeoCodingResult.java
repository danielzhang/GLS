package cn.gls.geocoding.engine.data;

import java.util.List;

import cn.gls.data.Place;
import cn.gls.data.Result;
import cn.gls.data.StandardAddress;

/**
 * @ClassName: GeoCodingResult.java
 * @Description 此类主要是描述地理编码服务的返回结果的结构
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-6
 */
public class GeoCodingResult extends Result implements
		Comparable<GeoCodingResult> {

	/** 结果的地址信息 */
	StandardAddress address;
	/** 结果的得分 */
	Float score = 0.0f;
	/***
	 * 返回几何对象
	 */
	Geometry geometry;

	/**
	 * @Title:GeoCodingResult
	 * @Description:构建函数
	 * @param: @param address
	 * @param: @param score
	 * @param: @param places
	 * @return:
	 * @throws
	 */
	public GeoCodingResult(StandardAddress address, Float score,
			List<Place> places) {
		super();
		this.address = address;
		this.score = score;
	}

	/**
	 * @Title:GeoCodingResult
	 * @Description:TODO
	 * @param: @param address
	 * @param: @param score
	 * @return:
	 * @throws
	 */
	public GeoCodingResult(StandardAddress address, Float score) {
		this(address, score, null);
	}

	/**
	 * @Title:GeoCodingResult
	 * @Description:TODO
	 * @param: @param address
	 * @return:
	 * @throws
	 */
	public GeoCodingResult(StandardAddress address) {
		this(address, 0f, null);
	}

	public GeoCodingResult() {
		super();
	}

	public StandardAddress getAddress() {
		return address;
	}

	public void setAddress(StandardAddress address) {
		this.address = address;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public int compareTo(GeoCodingResult o) {

		return this.getScore().compareTo(o.getScore());
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	@Override
	public String toString() {
		return "GeoCodingResult{score=" + score + ",StandardAddress="
				+ address.toString() + "}";
	}
}
