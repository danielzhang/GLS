package cn.gls.place.dao;

import java.util.List;

import cn.gls.data.Place;

/**
 * 类名 IPlaceDao.java 说明 对地名词的一些列操作 创建日期 2012-5-24 作者 "Daniel Zhang" 版权 V1.0 更新时间
 * 2012-5-31
 */
public interface IPlaceDao {
/** 拼音转化为地名词 */
List<Place> pinyin2Places(String pinyin, int citycode);

/** 地名词转拼音 */
List<String> place2Pinyin(String place, int citycode);

/** 标准地名词转化为同义词 */
List<String> place2Tyc(String place, int citycode);

/** 同义词转标准词名称 **/
String tyc2PlaceName(String tyc, int citycode);

Place tyc2Place(String tyc, int citycode);

/** 由名称来得到标准地名词 */
List<Place> getPlacesByStandardName(String name);

/** 由名称来得到地名词 **/
List<Place> getPlacesByName(String name, int citycode);



/** 错误的词到拼音 **/
List<Place> pinyinPlacesByError(String error, int citycode);

/** 由标准地名词来得到地名词 */
Place getPlaceByStandardPlaceName(String name, int citycode);

}
