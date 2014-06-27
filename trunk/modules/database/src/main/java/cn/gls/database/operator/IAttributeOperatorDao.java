package cn.gls.database.operator;

import java.util.List;

import cn.gls.database.postgis.standard.PostGISAttributeOperator.PlaceNode;

/**
 * @ClassName: IAttributeUpdateDao.java
 * @Description  与空间操作无关的属性信息的查询与操作。
 * @Date  2012-6-20
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-20
 */
public interface IAttributeOperatorDao {
	/**
	 * 
	 * @param cityName
	 * @return
	 */
    String selectProvinceByCity(String cityName);
    /**
     * 
     * @param cityName
     * @return
     */
    int selectCityCodeByCityName(String cityName);
    /**
     * 
     * @param cityCode
     * @return
     */
    String selectCityNameByCityCode(int cityCode);
    /**
     * 通过子类来查询父类，也许有很多父类。这个是大致粗略来求父类
     * @param sonName
     * @param cityCode
     * @return
     */
    List<String> selectFatherBySon(String sonName,int cityCode);
    /**
     * 选择出所有的父级地名词名称。
     * @param sonName
     * @param cityCode
     * @return
     */
   PlaceNode selectAllFathersBySon(String sonName, int cityCode);
    
    
}
