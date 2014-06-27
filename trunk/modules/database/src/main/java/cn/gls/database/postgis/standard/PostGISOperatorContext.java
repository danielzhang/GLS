package cn.gls.database.postgis.standard;

import java.util.HashMap;
import java.util.Map;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.database.DynamicLoadBean;
import cn.gls.database.LoadApplicationContext;
import cn.gls.database.data.DataType;
import cn.gls.database.operator.IStandardTableOperator;

/**
 * @ClassName: PostGISOperatorContext.java
 * @Description 关于postgis上下文环境类
 * @Date 2012-6-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public class PostGISOperatorContext {
	private IStandardTableOperator postgisOperator;

	private static final Map<Integer, PostGISBaseOperator> mapper = new HashMap<Integer, PostGISBaseOperator>() {
		{
			put(DataType.PROVINCE_TYPE,
					(PostGISBaseOperator) LoadApplicationContext.getInstance()
							.getBean("provinceOperator"));
			put(DataType.CITY_TYPE,
					(PostGISBaseOperator) LoadApplicationContext.getInstance()
							.getBean("cityOperator"));
			put(DataType.POLITICAL_TYPE,
					(PostGISBaseOperator) LoadApplicationContext.getInstance()
							.getBean("politicalOperator"));
			put(DataType.STREET_TYPE,
					(PostGISBaseOperator) LoadApplicationContext.getInstance()
							.getBean("streetOperator"));
			// put(DataType.POI_TYPE,(PostGISPOIOperator)LoadApplicationContext.getInstance().getBean("poiOperator"));
		}
	};

	public void setOperator(int type) {
		
		if (type == DataType.ADDRESS_TYPE&&!LoadApplicationContext.getInstance().containBean("addressOperator")) {
			DynamicLoadBean dynamicLoadBean = (DynamicLoadBean) LoadApplicationContext
					.getInstance().getBean("dynamicLoadBean");
//			GLSContextLoader contextLoader=GLSContextLoader.getInstance();
			dynamicLoadBean.loadBean("classpath:conf/addressTable.xml");
			postgisOperator = (PostGISAddressOperator) LoadApplicationContext
					.getInstance().getBean("addressOperator");
		} else
			postgisOperator = mapper.get(type);
	}

	/**
	 * 真正的计算方法
	 * 
	 * @param fcollection
	 * @param fieldMap
	 * @param dbTableName
	 * @param sCityName
	 * @return
	 */
	public int insert(
			FeatureCollection<SimpleFeatureType, SimpleFeature> fcollection,
			Map<String, String> fieldMap, String dbTableName, String dataType,
			String sCityName) {
		return postgisOperator.insert(fcollection, fieldMap, dbTableName,
				dataType, sCityName);
	}
}
