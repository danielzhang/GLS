package cn.gls.database.postgis.standard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.gls.GLSDBSupport;
import cn.gls.database.operator.IAttributeOperatorDao;

/**
 * @ClassName: PostGISAttributeOperator.java
 * @Description 一些属性查询的相关类
 * @Date 2012-6-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public class PostGISAttributeOperator extends GLSDBSupport implements
		IAttributeOperatorDao {

	public PostGISAttributeOperator() {
		super();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.IAttributeOperatorDao#selectProvinceByCity(java.lang.String)
	 */
	public String selectProvinceByCity(String cityName) {
		String st_name = (String) getSqlMapClientTemplate().queryForObject(
				"assist.selectStandardCityName", cityName);
		if (st_name != null)
			return (String) getSqlMapClientTemplate().queryForObject(
					"city.selectProvinceByCity", st_name);
		else
			return (String) getSqlMapClientTemplate().queryForObject(
					"city.selectProvinceByCity", cityName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.postgis.operator.IStandardDatabaseOperatorDao#selectCityCodeByCityName(java.lang.String)
	 */
	public int selectCityCodeByCityName(String cityName) {
		 if(cityName==null||"".equalsIgnoreCase(cityName))
			 return 0;
		String st_name = (String) getSqlMapClientTemplate().queryForObject(
				"assist.selectStandardCityName", cityName);
		Object obj = null;
		if (st_name != null) {
			obj = getSqlMapClientTemplate().queryForObject(
					"city.selectCityCodeByCityName", st_name);
		} else {
			obj = getSqlMapClientTemplate().queryForObject(
					"city.selectCityCodeByCityName", cityName);
		}
		if (obj != null)
			return (Integer) obj;
		else
			return 0;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.IAttributeOperatorDao#selectCityNameByCityCode(int)
	 */
	public String selectCityNameByCityCode(int cityCode) {
		return (String) getSqlMapClientTemplate().queryForObject(
				"city.selectCityNameByCityCode", cityCode);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.IAttributeOperatorDao#selectFatherBySon(java.lang.String,
	 *      int)
	 */
	public List<String> selectFatherBySon(String sonName, int cityCode) {
		Map<String, Object> smap = new HashMap<String, Object>();
		smap.put("sonName", sonName);
		smap.put("cityCode", cityCode);
		@SuppressWarnings("unchecked")
		List<String> fathers = (List<String>) getSqlMapClientTemplate()
				.queryForList("assist.selectFatherNameBySonName", smap);
		return fathers;
	}
      /**
       * 此方法暂时不用。因为算法错误。
       */
	public PlaceNode selectAllFathersBySon(final String sonName, int cityCode) {

		PlaceNode head = new PlaceNode("");
		String fatherName = null;
		List<String> sonNames = new ArrayList<String>();
		List<String> sons=new ArrayList<String>() {
			{
				add(sonName);
			}
		};
		PlaceNode fatherNode=new PlaceNode("");
		while (!"市".equalsIgnoreCase(fatherName.substring(
				fatherName.length() - 1, fatherName.length()))) {
		      sonNames=sons;
			  sons.clear();
			      
			for (String son : sonNames) {
				   PlaceNode sonNode=fatherNode.get(son);

				   List<String> fathers = selectFatherBySon(son, cityCode);
				sons.addAll(fathers);
				if (fathers.size() > 0)
					fatherName = fathers.get(0);
				PlaceNode node=sonNode;
				for (String father : fathers) {
					       PlaceNode p= node.get(father);
					       if(p==null)
					       {
					    	   p=new PlaceNode(father);
					    	   node.born(father, p);
					       }
					       node=p;
					// PlaceNode node=new PlaceNode(father);
					//placeNode.nodeMap.put(father, node);
				}
			}
		}
		return head;
	}

	/**
	 * 
	 * @param provinceName
	 * @return
	 */
	public Integer selectProvinceCodeByProvinceName(String provinceName) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"city.selectProvinceCodeByProvinceName", provinceName);
	}

	public class PlaceNode {

		String name;
		Map<String, PlaceNode> nodeMap;
		private boolean isLeaf;

		public boolean isLeaf() {
			return isLeaf;
		}

		public void born(String k, PlaceNode node) {
			nodeMap.put(k, node);
		}

		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}
          public PlaceNode get(String name){
        	return  nodeMap.get(name);
          }
		public PlaceNode(String k) {
			name = k;
			this.nodeMap = new HashMap<String, PostGISAttributeOperator.PlaceNode>();
		}
	}

}
