package cn.gls.geocoding.engine.dao;

import java.util.List;

import cn.gls.place.dao.IPlaceDao;

/**
 * 
 * @ClassName IGeoCodingAttributeDao.java
 * @Createdate 2012-7-11
 * @Description 主要是与辅助表的查询操作等等。
 * @Version 1.0
 * @Update 2012-7-11 
 * @author "Daniel Zhang"
 *
 */
public interface IGeoCodingAttributeDao extends IPlaceDao {
  /*** 通过城市名称来得到城市code **/
  int getCityCodeByName(String cityName);
  
  /*** 地名词的名字获得citycode,地名级别为2 **/
  int getCityCodeByPlaceName(String placeName, int placeLevel);
  
  List<Integer> getCityCodeByPlaceName(String placeName);
  
}
