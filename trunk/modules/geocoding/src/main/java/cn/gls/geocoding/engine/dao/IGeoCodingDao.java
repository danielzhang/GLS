package cn.gls.geocoding.engine.dao;

import java.util.List;


import cn.gls.data.Place;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.data.GeoCodingResult;

/**
 * 类名      IGeoCodingDao.java
 * 说明   中文地理编码的访问接口
 * 创建日期 2012-5-25
 * 作者 "Daniel Zhang"
 * 版权  ***
 * 更新时间  2012-7-23
 * 标签   $Name$
 * SVN 版本  v0.1
 * 最后更新者 "Daniel Zhang"
 */
public interface IGeoCodingDao {
	/** 通过地址来获得城市结果 ****/
	List<GeoCodingResult> getGeoCodingResultsToCity(List<Place> places,GeoCodingContext context);

	/** 通过地址来获得区域结果 ***/
	List<GeoCodingResult> getGeoCodingResultsToPolitical(List<Place> places,GeoCodingContext context);

	/** 通过地址获得街道结果 *****/
	List<GeoCodingResult> getGeoCodingResultsToStreet(List<Place> places,GeoCodingContext context);

	/** 通过地址获得交叉口结果 ***/
	List<GeoCodingResult> getGeoCodingResultsToIntersection(
	        List<Place> places,GeoCodingContext context);

	/**** 通过地址获得地理编码 ***/
	List<GeoCodingResult> getGeoCodingResultsToAddress(List<Place> places,GeoCodingContext context);
}
