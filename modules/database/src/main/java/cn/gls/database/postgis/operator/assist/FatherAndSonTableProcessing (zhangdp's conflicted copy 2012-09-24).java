package cn.gls.database.postgis.operator.assist;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.GLSDBSupport;
import cn.gls.data.FatherAndSon;
import cn.gls.database.operator.IAttributeOperatorDao;
import cn.gls.database.operator.assist.IGLSDatabaseAssistDao;
import cn.gls.util.StringUtils;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @ClassName: FatherAndSonProcessing.java
 * @Description 子父级关系处理类
 * @Date 2012-6-26
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-10
 */
public class FatherAndSonTableProcessing extends GLSDBSupport{

	private IAttributeOperatorDao attributeOperator;

	public void setAttributeOperator(IAttributeOperatorDao attributeOperator) {
		this.attributeOperator = attributeOperator;
	}
	     private IGLSDatabaseAssistDao databaseAssistDao;
	     public void setDatabaseAssistDao(IGLSDatabaseAssistDao databaseAssistDao) {
	         this.databaseAssistDao = databaseAssistDao;
	     }

	/**
	 * 生成父子关系-这个当子类集合中不含有父类字段时。
	 * 
	 * @Param FatherAndSonProcessing
	 * @Description 生产父子关系类
	 * @return int
	 * @param fatherCollection
	 * @param sonFeatureCollection
	 * @param fatherNameField
	 * @param sonNameField
	 * @param cityCodeField
	 * @param cityCode
	 * @return
	 */
	public Set<FatherAndSon> judgeFatherAndSon(
			FeatureCollection<SimpleFeatureType, SimpleFeature> fatherCollection,
			FeatureCollection<SimpleFeatureType, SimpleFeature> sonFeatureCollection,
			String fatherNameField, String sonNameField, String cityCodeField,
			String cityName, String cityFieldName) {
		Iterator<SimpleFeature> sonSimpleFeatureIterator = sonFeatureCollection
				.iterator();
		Set<FatherAndSon> fahterAndSonSet = new HashSet<FatherAndSon>();

		boolean ismoreCity = false;
		int cityCode = 0;
		if (fatherCollection.getSchema().getDescriptor(cityCodeField) == null) {
			if (cityName == null || "".equalsIgnoreCase(cityName)) {
				if (fatherCollection.getSchema().getDescriptor(cityFieldName) != null) {
					ismoreCity = true;
				}
			}
			cityCode = attributeOperator.selectCityCodeByCityName(cityName);

		} else
			ismoreCity = true;
		while (sonSimpleFeatureIterator.hasNext()) {
			SimpleFeature sonFeature = sonSimpleFeatureIterator.next();
			Geometry sonGeometry = (Geometry) sonFeature.getDefaultGeometry();
			if (sonFeature.getAttribute(sonNameField) == null
					|| "".equalsIgnoreCase((String) sonFeature
							.getAttribute(sonNameField)))
				continue;
			FeatureIterator<SimpleFeature> fatherFeatureIterator = fatherCollection
					.features();
			while (fatherFeatureIterator.hasNext()) {
				SimpleFeature fatherFeature = fatherFeatureIterator.next();
				if (fatherFeature.getAttribute(fatherNameField) == null
						|| "".equalsIgnoreCase((String) fatherFeature
								.getAttribute(fatherNameField)))
					continue;
				Geometry fatherGeometry = (Geometry) fatherFeature
						.getDefaultGeometry();
				if (fatherGeometry.contains(sonGeometry)) {

					String sonName = (String) sonFeature
							.getAttribute(sonNameField);
					String fatherName = (String) fatherFeature
							.getAttribute(fatherNameField);
					if (ismoreCity) {
						if (fatherFeature.getAttribute(cityCodeField) != null)
							cityCode = (Integer) fatherFeature
									.getAttribute(cityCodeField);
						else {
							cityCode = attributeOperator
									.selectCityCodeByCityName((String) fatherFeature
											.getAttribute(cityFieldName));
						}
					}
					FatherAndSon fatherAndSon = new FatherAndSon(fatherName,
							sonName, cityCode);

					fahterAndSonSet.add(fatherAndSon);
				} else
					continue;
			}
			fatherFeatureIterator.close();

		}

		return fahterAndSonSet;
	}

	/**
	 * 产生父子类,如果知道子类集合中含有父类的字段，则指定父类字段。
	 * @param sonfeatureCollection
	 * @param fatherFeatureCollection
	 * @param fatherField
	 * @param sonFieldName
	 * @param fatherFieldName
	 * @param cityName
	 * @param cityCodeFieldName
	 * @param cityFieldName
	 * @return
	 */
	public Set<FatherAndSon> judgeFatherAndSon(
			FeatureCollection<SimpleFeatureType, SimpleFeature> sonfeatureCollection,
			FeatureCollection<SimpleFeatureType, SimpleFeature> fatherFeatureCollection,
			String fatherField, String sonFieldName, String fatherFieldName,
			String cityName, String cityCodeFieldName, String cityFieldName) {

		FeatureIterator<SimpleFeature> featureIterator = sonfeatureCollection
				.features();
		Set<FatherAndSon> fatherAndSon = new HashSet<FatherAndSon>();
		int cityCode = 0;
		if (cityName != null && !"".equalsIgnoreCase(cityName))
			cityCode = attributeOperator.selectCityCodeByCityName(cityName);
		
		while (featureIterator.hasNext()) {
			SimpleFeature feature = featureIterator.next();
			String sonName = (String) feature.getAttribute(sonFieldName);
			sonName = StringUtils.getPlaceName(sonName);
			String fatherName = (String) feature.getAttribute(fatherFieldName);
			fatherName = StringUtils.getPlaceName(fatherName);
			if (sonName == null || fatherName == null
					|| "".equalsIgnoreCase(sonName)
					|| "".equalsIgnoreCase(fatherName)) {
				SimpleFeature resultFeature = affirmFatherFeature(feature,
						fatherFeatureCollection);
				if(resultFeature!=null)
				fatherName = StringUtils.getPlaceName((String) resultFeature
						.getAttribute(fatherField));
			}
           
		//	System.out.println(sonName + "---------" + fatherName);
			int cityCodeTemp = 0;
			if (cityCode == 0 && cityCodeFieldName != null) {
				cityCodeTemp = (Integer) feature
						.getAttribute(cityCodeFieldName);
			}
			if (cityCode == 0 && cityCodeTemp == 0 && cityFieldName != null) {
				cityCodeTemp = (Integer) attributeOperator
						.selectCityCodeByCityName((String) feature
								.getAttribute(cityFieldName));
			}
			fatherAndSon.add(new FatherAndSon(fatherName, sonName,
					cityCode == 0 ? cityCodeTemp : cityCode));

		}
		featureIterator.close();
		return fatherAndSon;
	}

	/**
	 * 判断是否父子类
	 * 
	 * @param feature
	 * @param fatherFeatureCollection
	 * @return
	 */
	private SimpleFeature affirmFatherFeature(
			SimpleFeature feature,
			FeatureCollection<SimpleFeatureType, SimpleFeature> fatherFeatureCollection) {
		FeatureIterator<SimpleFeature> featureIterator = fatherFeatureCollection
				.features();
		Geometry sonGeometry = (Geometry) feature.getDefaultGeometry();
		SimpleFeature resultFeature = null;
		while (featureIterator.hasNext()) {
			SimpleFeature tempFeature = featureIterator.next();
			Geometry fatherGeometry = (Geometry) tempFeature
					.getDefaultGeometry();
			if (fatherGeometry.contains(sonGeometry)) {
				resultFeature = tempFeature;
				break;
			}
		}
		featureIterator.close();
		return resultFeature;
	}
	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.assist.IFatherAndSonOperator#insertFatherAndSon(java
	 *      .util.Set)
	 */
	public int insertFatherAndSon(Set<FatherAndSon> fatherAndSons) {
                     return databaseAssistDao.insertFatherAndSon(fatherAndSons);
	}
	
}
