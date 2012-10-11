package cn.gls.database.operator;

import java.util.Map;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * @ClassName: IPostGISStandardTableOperator.java
 * @Description  对标准地址表的操作类
 * @Date  2012-6-28
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-28
 */
public interface IStandardTableOperator {
	/**
	 * 真实的插入算法接口
	 * @param fcollection 要插入的要素
	 * @param fieldMap    插入的要素属性与标准的表结构的列的对应关系
	 * @param dbTableName 标准的表名称
	 * @param sCityName   城市名称
	 * @param  dataType   插入的要素类型
	 * @return
	 */
    int insert(FeatureCollection<SimpleFeatureType, SimpleFeature> fcollection, Map<String, String> fieldMap, 
            String dbTableName, String dataType, String sCityName);
}
