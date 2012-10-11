package cn.gls.database.operator.assist;


import java.util.Set;

import cn.gls.data.FatherAndSon;
import cn.gls.data.PinyinPlace;
import cn.gls.data.Place;
import cn.gls.data.TycPlace;

/**
 * @ClassName: IAssistOperator.java
 * @Description 对辅助表的操作，对辅助表的查询，删除，插入等等操作。
 * @Date 2012-7-10
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-10
 */
public interface IGLSDatabaseAssistDao {
	/**
	 * 插入子父类
	 * @Description 批量插入父子类
	 * @param fatherAndSons
	 * @return
	 */
	int insertFatherAndSon(Set<FatherAndSon> fatherAndSons);

	/**
	 * 
	 * @Param IGradePlaceOperator
	 * @Description 插入地名词等级
	 * @return int
	 * @param featureCollection
	 * @param fieldName
	 * @param placeLevel
	 * @param placeType
	 * @param cityName
	 * @param cityFieldName
	 * @return
	 */
	int insertGradeData(Set<Place> places);

	/**
	 * 
	 * @Param IPinyinOperator
	 * @Description 插入pinyin词
	 * @return int
	 * @param places
	 * @return
	 */
	public int insertPinyin(Set<PinyinPlace> places);

	/**
	 * 
	 * @param places
	 * @return
	 */
	int insertThesaurusData(Set<TycPlace> places);

	/**
	 * 
	 * @param place
	 * @param sqlClient
	 * @return
	 */
	Set<PinyinPlace> applyPinyinPlace(Set<PinyinPlace> places);

	/**
	 * 
	 * @param fatherAndSon
	 * @param sqlClient
	 * @return
	 */
	Set<FatherAndSon> applyFatherAndSon(Set<FatherAndSon> fatherAndSons);

	/**
	 * 
	 * @param place
	 * @param sqlClient
	 * @return
	 */
	Set<Place> applyPlace(Set<Place> places);
	
	Set<TycPlace> applyTycPlace(Set<TycPlace> tycPlaces);
}
