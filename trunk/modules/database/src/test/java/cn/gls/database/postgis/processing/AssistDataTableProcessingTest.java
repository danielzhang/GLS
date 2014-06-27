package cn.gls.database.postgis.processing;

import java.util.Set;

import junit.framework.TestCase;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.data.FatherAndSon;
import cn.gls.data.PinyinPlace;
import cn.gls.data.Place;
import cn.gls.database.LoadApplicationContext;
import cn.gls.database.postgis.operator.assist.FatherAndSonTableProcessing;
import cn.gls.database.postgis.operator.assist.GradeTableProcessing;
import cn.gls.database.postgis.operator.assist.PinyinTableProcessing;
import cn.gls.database.postgis.operator.assist.ThesaurusTableProcessing;
import cn.gls.database.shp.util.ShpUtils;
import cn.gls.database.util.FeaturePreProcessing;

/**
 * @ClassName: DataTableProcessingTest.java
 * @Description 主要是处理辅助引擎的哪些表的操作。例如子父类，省与市关系表，同义词表
 * @Date 2012-6-20
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-20
 */
public class AssistDataTableProcessingTest extends TestCase {
	private ThesaurusTableProcessing dataProcessing;
	private FatherAndSonTableProcessing fatherAndSonOperator;
	private GradeTableProcessing gradeProcessing;
	private FeaturePreProcessing featureProcessing;
	private PinyinTableProcessing pinyinProdcessing;

	public void setFatherAndSonOperator(
			FatherAndSonTableProcessing fatherAndSonOperator) {
		this.fatherAndSonOperator = fatherAndSonOperator;
	}

	public AssistDataTableProcessingTest() {
		super();
		LoadApplicationContext loadApplicationContext = LoadApplicationContext
				.getInstance();
		dataProcessing = (ThesaurusTableProcessing) loadApplicationContext
				.getBean("dataProcessing");
		fatherAndSonOperator = (FatherAndSonTableProcessing) loadApplicationContext
				.getBean("fatherAndSonOperator");
		gradeProcessing = (GradeTableProcessing) loadApplicationContext
				.getBean("gradeTableProcessing");
		featureProcessing = (FeaturePreProcessing) LoadApplicationContext
				.getInstance().getBean("featureProcessing");
		pinyinProdcessing = (PinyinTableProcessing) LoadApplicationContext
				.getInstance().getBean("pinyinProcessing");
	}

	public void setDataProcessing(ThesaurusTableProcessing dataProcessing) {
		this.dataProcessing = dataProcessing;
	}

	/**
	 * 测试用例
	 * 
	 * @Param AssistDataTableProcessingTest
	 * @Description TODO
	 * @return void
	 */
	public void testAssistTable() {

//		int count= insertFatherAndSon("src/test/resources/shp/shanghaiquxia.shp","src/test/resources/shp/gouwu.shp","Name",
//		 "Name", null,"上海市",null);
//		int count = insertGradeToTable("src/test/resources/shp/gouwu.shp",
//				"Name", 11, "购物服务", null, "上海市", null, false,true);

		// insertGradeToTable("src/test/resources/shp/citys.shp", "NAME",3,
		// "城市", null, null, "NAME");
		 int count= insertPinyin("src/test/resources/shp/gouwu.shp", "Name",
		 "Name_PY", null,"上海市", null);

		// 插入地名等级表
		// int count= insertGradeToTable("src/test/resources/shp/xiezilou.shp",
		// "Name", 10, "写字楼", null,"上海市",null);
		System.out.println("处理要素个数" + count);
	}

	/**
	 * 插入st_province_city表中,这个表就此确定了，一般情况不会发生变化
	 */
	public void insertProvinceName() {
		// int count = dataProcessing.insertProvinceCity();
		// System.out.println(count);
	}

	/**
	 * 插入同义词
	 */
	public void insertThesaurus() {
		// int count = dataProcessing.insertThesaurusCity();
		// System.out.println(count);
	}

	/**
	 * 插入子父级
	 */
	public int insertFatherAndSon(String fatherShp, String sonShp,
			String fatherFieldName, String sonFieldName, String cityCodeField,
			String cityName, String cityFieldName) {
		// Set<FatherAndSon> fatherAndSons =
		// fatherAndSonOperator.judgeFatherAndSon(ShpUtils
		// .readShpfile(fatherShp),
		// ShpUtils.readShpfile(sonShp),fatherFieldName,sonFieldName,cityCodeField,cityName,cityFieldName);
		Set<FatherAndSon> fatherAndSons = fatherAndSonOperator
				.judgeFatherAndSon(ShpUtils.readShpfile(sonShp),
						ShpUtils.readShpfile(fatherShp), fatherFieldName,
						sonFieldName, "Address", cityName, cityCodeField,
						cityFieldName);
		int count = fatherAndSonOperator.insertFatherAndSon(fatherAndSons);
		return count;
		// System.out.println(count);
	}

	/**
	 * 插入等级地名词
	 * 
	 * @Param AssistDataTableProcessingTest
	 * @Description TODO
	 * @return int
	 * @param shpName
	 * @param fieldName
	 * @param placeLevel
	 * @param placeType
	 * @param cityName
	 * @param cityFieldName
	 * @return
	 */
	private int insertGradeToTable(String shpName, String fieldName,
			int placeLevel, String placeType, String cityCodeFieldName,
			String cityName, String cityFieldName, boolean isunion,boolean isRemoveSuffix) {
		FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = null;
		if (isunion) {
			featureCollection = featureProcessing.unionSimpleFeatures(
					ShpUtils.getFeatureSourceByShp(shpName), fieldName, null,
					null);

		} else {
			featureCollection = ShpUtils.readShpfile(shpName);
		}
		Set<Place> places = gradeProcessing.clearPlace(featureCollection,
				fieldName, placeLevel, placeType, cityCodeFieldName, cityName,
				cityFieldName,isRemoveSuffix);
		int count = gradeProcessing.insertGradeData(places);
		// System.out.println(count);
		return count;
	}

	/**
	 * 
	 * @param shpName
	 * @param fieldName
	 * @param pinyinFieldName
	 * @param cityCodeFieldName
	 * @param cityName
	 * @param cityFieldName
	 * @return
	 */
	private int insertPinyin(String shpName, String fieldName,
			String pinyinFieldName, String cityCodeFieldName, String cityName,
			String cityFieldName) {
		// FeatureCollection<SimpleFeatureType,SimpleFeature>
		// featureCollection=featureProcessing.unionSimpleFeatures(ShpUtils.getFeatureSourceByShp(shpName),
		// fieldName, null, null);
		Set<PinyinPlace> places = pinyinProdcessing.insertPinyinToTable(
				ShpUtils.readShpfile(shpName), fieldName, pinyinFieldName,
				cityCodeFieldName, cityName, cityFieldName);
		int count = pinyinProdcessing.insertPinyin(places);
		// System.out.println(count);
		return count;
	}

}
