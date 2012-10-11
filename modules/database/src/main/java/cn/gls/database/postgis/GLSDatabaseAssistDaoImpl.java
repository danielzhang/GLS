package cn.gls.database.postgis;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import cn.gls.GLSDBSupport;
import cn.gls.data.FatherAndSon;
import cn.gls.data.PinyinPlace;
import cn.gls.data.Place;
import cn.gls.data.TycPlace;
import cn.gls.database.operator.ICreateTableAndIndex;
import cn.gls.database.operator.assist.IGLSDatabaseAssistDao;
import cn.gls.database.postgis.operator.assist.GradeTableProcessing;

/**
 * @ClassName: GLSDatabaseAssistDaoImpl.java
 * @Description 辅助表的实现类
 * @Date 2012-7-10
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-8-21
 */
public class GLSDatabaseAssistDaoImpl extends GLSDBSupport implements
		IGLSDatabaseAssistDao, ICreateTableAndIndex {

	public GLSDatabaseAssistDaoImpl() {
		super();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.assist.IGLSDatabaseAssistDao#insertFatherAndSon(java.util.Set)
	 */
	public int insertFatherAndSon(final Set<FatherAndSon> fatherAndSons) {
		try {
			resetSeq(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						int count = 0;
						for (FatherAndSon fatherSon : fatherAndSons) {
							sqlMapClient.insert("assist.insertFatherAndSon",
									fatherSon);
							count++;
						}
						executor.executeBatch();
						return count;
					}
				});

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.assist.IGLSDatabaseAssistDao#insertGradeData(java.util.Set)
	 */
	public int insertGradeData(final Set<Place> places,
			final GradeTableProcessing gradeProcessing) {
		try {
			resetSeq(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback<Object>() {
					public Object doInSqlMapClient(final SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						int count = 0;
						final int s = places.size();
						String placesName = "";
						for (Place place : places) {
							sqlMapClient.insert("assist.inserGradeDataToTable",
									place);
							count++;
							gradeProcessing.setPlacesName("导入数据:" + placesName
									+ place.getName() + "\n", "");
							placesName += "导入数据：" + place.getName() + "\n";
							if (s == count) {
								gradeProcessing.setPlacesName(placesName + "\n"
										+ "完成地名词的导入", "");
							}
						}
						executor.executeBatch();
						return count;
					}
				});

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.assist.IGLSDatabaseAssistDao#insertPinyin(java.util.Set)
	 */
	public int insertPinyin(final Set<PinyinPlace> places) {
		try {
			resetSeq(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(final SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						final long l = System.currentTimeMillis();
						int count = 0;
						for (final PinyinPlace place : places) {
							executor.insert("assist.insertPinyinPlace", place);
							count++;
						}
						executor.executeBatch();
						System.out.println("耗費："
								+ (System.currentTimeMillis() - l));

						return count;
					}
				});

	}

	private void resetSeq(int type) throws SQLException {
		switch (type) {
		case 1:
			int maxPinyinSeq = sqlMapClient
					.queryForObject("assist.selectPinyinMaxSeq") != null ? (Integer) sqlMapClient
					.queryForObject("assist.selectPinyinMaxSeq") : 1;
			sqlMapClient.queryForObject("assist.setPinyinSeq", maxPinyinSeq);
			break;
		case 2:
			int maxGid = sqlMapClient
					.queryForObject("assist.selectMaxGradeSeq") != null ? (Integer) sqlMapClient
					.queryForObject("assist.selectMaxGradeSeq") : 1;
			sqlMapClient.queryForList("assist.setGradeSeq", maxGid);
			break;
		case 3:
			int maxFatherAndSonId = sqlMapClient
					.queryForObject("assist.selectFatherAndSonMaxSeq") != null ? (Integer) getSqlMapClientTemplate()
					.queryForObject("assist.selectFatherAndSonMaxSeq") : 1;
			sqlMapClient.queryForObject("assist.selectFatherAndSonMaxSeq",
					maxFatherAndSonId);
			break;

		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gls.database.operator.assist.IGLSDatabaseAssistDao#insertThesaurusData(java.util.Set)
	 */
	public int insertThesaurusData(Set<TycPlace> places) {
		return 0;
	}

	public Set<PinyinPlace> applyPinyinPlace(Set<PinyinPlace> places) {
		try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for (PinyinPlace pinyinPlace : places) {
				PinyinPlace place = (PinyinPlace) sqlMapClient.queryForObject(
						"assist.selectPinyinPlaceByPlace", pinyinPlace);
				if (place != null)
					places.remove(pinyinPlace);
			}
			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return places;
	}

	public Set<FatherAndSon> applyFatherAndSon(Set<FatherAndSon> fatherAndSons) {
		try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for (FatherAndSon fatherAndSon : fatherAndSons) {
				FatherAndSon place = (FatherAndSon) sqlMapClient
						.queryForObject("assist.selectFatherAndSon",
								fatherAndSon);
				if (place != null)
					fatherAndSons.remove(fatherAndSon);
			}
			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fatherAndSons;
	}

	public Set<Place> applyPlace(Set<Place> places) {
		try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for (Place place : places) {
				@SuppressWarnings("unchecked")
				List<Integer> fids = sqlMapClient.queryForList(
						"assist.selectPlaces", place);
				if (fids != null && fids.size() != 0)
					places.remove(place);
			}
			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public Set<TycPlace> applyTycPlace(Set<TycPlace> tycPlaces) {

		return null;
	}

	public int existPlaceTable() {
		Object obj = null;
		try {
			obj = sqlMapClient.queryForObject("create.existTable", "p_place");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createPlaceTable() {
		// 产生表结构，产生序列
		try {
			if (existPlaceTable() == 0) {
				sqlMapClient.update("create.createPlaceSeq", null);
				sqlMapClient.update("create.createPlaceTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existPinyinTable() {
		Object obj = null;
		try {

			obj = sqlMapClient.queryForObject("create.existTable", "p_pinyin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createPinyinTable() {
		// 产生表结构，产生序列
		try {
			if (existPinyinTable() == 0) {
				sqlMapClient.update("create.createPinyinSeq", null);
				sqlMapClient.update("create.createPinyinTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existTycTable() {
		Object obj = null;
		try {
			obj = sqlMapClient.queryForObject("create.existTable",
					"p_thesaurus");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createTycTable() {
		// 产生表结构，产生序列
		try {
			if (existTycTable() == 0) {
				sqlMapClient.update("create.createTycSeq", null);
				sqlMapClient.update("create.createTycTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existFatherAndSonTable() {
		Object obj = null;
		try {
			obj = sqlMapClient.queryForObject("create.existTable",
					"p_father_son");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createFatherAndSonTable() {
		// 产生表结构，产生序列
		try {
			if (existFatherAndSonTable() == 0) {
				sqlMapClient.update("create.createFatherAndSonSeq", null);
				sqlMapClient.update("create.createFatherAndSonTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existProvinceTable() {
		Object obj = null;
		try {
			obj = sqlMapClient.queryForObject("create.existTable",
					"st_a_province");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createProvinceTable() {
		// 产生表结构，产生序列
		try {
			if (existProvinceTable() == 0) {
				sqlMapClient.update("create.createProvinceSeq", null);
				sqlMapClient.update("create.createProvinceTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existCityTable() {
		Object obj = null;
		try {
			obj = sqlMapClient.queryForObject("create.existTable", "st_a_city");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createCityTable() {
		// 产生表结构，产生序列
		try {
			if (existCityTable() == 0) {
				sqlMapClient.update("create.createCitySeq", null);
				sqlMapClient.update("create.createCityTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existPoliticalTable() {
		Object obj = null;
		try {
			obj = sqlMapClient.queryForObject("create.existTable",
					"st_a_political");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createPoliticalTable() {
		// 产生表结构，产生序列
		try {
			if (existPoliticalTable() == 0) {
				sqlMapClient.update("create.createPoliticalSeq", null);
				sqlMapClient.update("create.createPoliticalTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existStreetTable() {
		Object obj = null;
		try {
			obj = sqlMapClient.queryForObject("create.existTable",
					"st_a_street");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createStreetTable() {
		// 产生表结构，产生序列
		try {
			if (existStreetTable() == 0) {
				sqlMapClient.update("create.createStreetSeq", null);
				sqlMapClient.update("create.createStreetTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existAddressTable() {
		Object obj = null;
		try {
			obj = sqlMapClient.queryForObject("create.existTable",
					"st_a_address");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (obj == null)
			return 0;
		else
			return 1;
	}

	public void createAddressTable() {
		// 产生表结构，产生序列
		try {
			if (existAddressTable() == 0) {
				sqlMapClient.update("create.createAddressSeq", null);
				sqlMapClient.update("create.createAddressTable", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createPlaceIndex() {
		try {
			if (existPlaceIndex() == 0)
				sqlMapClient.update("create.createPlaceIndex", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createPinyinIndex() {
		try {
			if (existPinyinIndex() == 0)
				sqlMapClient.update("create.createPinyinIndex", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createTycIndex() {
		try {
			if (existTycIndex() == 0)
				sqlMapClient.update("create.createTycIndex", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createFatherAndSonIndex() {
		try {
			if (existFatherAndSonIndex() == 0) {
				sqlMapClient.update("create.createSonIndex", null);
				sqlMapClient.update("create.createFatherIndex", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void createProvinceIndex() {
		try {
			if (existProvinceIndex() == 0) {
				sqlMapClient.update("create.createProvinceCodeIndex", null);
				sqlMapClient.update("create.createProcinceNameIndex", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createCityIndex() {
		try {
			if (existCityIndex() == 0) {
				sqlMapClient.update("create.createCityCodeIndex", null);
				sqlMapClient.update("create.createCityNameIndex", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createPoliticalIndex() {
		try {
			if (existPoliticalIndex() == 0) {
				sqlMapClient.update("create.createVillageIndex", null);
				sqlMapClient.update("create.createTownIndex", null);
				sqlMapClient.update("create.createCountyIndex", null);
				sqlMapClient.update("create.createCityIndex", null);
				sqlMapClient.update("create.createProvinceIndex", null);
				sqlMapClient
						.update("create.createPoliticalCityCodeIndex", null);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void createStreetIndex() {
		try {
			
			if (existStreetIndex() == 0) {
				sqlMapClient.update("create.createStreetProvinceIndex", null);
				sqlMapClient.update("create.createStreetCityIndex", null);
				sqlMapClient.update("create.createStreetCityCodeIndex", null);
				sqlMapClient.update("create.createStreetCountyIndex", null);
				sqlMapClient.update("create.createStreetTownIndex", null);
				sqlMapClient.update("create.createStreetVillageIndex", null);
				sqlMapClient.update("create.createStreetStreetIndex", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createAddressIndex() {
		try {
			
			if (existAddressIndex() == 0) {
				sqlMapClient.update("create.createAddressProvinceIndex", null);
				sqlMapClient.update("create.createAddressCityIndex", null);
				sqlMapClient.update("create.createAddressCityCodeIndex", null);
				sqlMapClient.update("create.createAddressCountyIndex", null);
				sqlMapClient.update("create.createAddressTownIndex", null);
				sqlMapClient.update("create.createAddressVillageIndex", null);
				sqlMapClient.update("create.createAddressStreetIndex", null);
				sqlMapClient.update("create.createAddressComminutiesIndex",
						null);
				sqlMapClient.update("create.createAddressBuildingIndex", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int existPlaceIndex() {
		int flag = 0;
		try {
			Object obj = sqlMapClient.queryForObject("create.existTableIndex","index_grade_name");
			if (obj != null)
				flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int existPinyinIndex() {
		int flag = 0;
		try {
			Object obj = sqlMapClient.queryForObject("create.existTableIndex","index_pinyin_name");
			if (obj != null)
				flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int existTycIndex() {
		int flag = 0;
		try {
			Object obj = sqlMapClient.queryForObject("create.existTableIndex","index_thesaurus_name");
			if (obj != null)
				flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int existFatherAndSonIndex() {
		int flag = 0;
		try {
			Object obj = sqlMapClient.queryForObject("create.existTableIndex","index_son_name");
			if (obj != null) {
				flag = 1;
				return flag;
			}
			obj = sqlMapClient.queryForObject("create.existTableIndex","index_father_name");
			if (obj != null)
				flag = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int existProvinceIndex() {
		int flag = 0;
		try {
			if (sqlMapClient.queryForObject("create.existTableIndex","index_province_code") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_province_name") != null)
				flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int existCityIndex() {
		int flag = 0;
		try {
			if (sqlMapClient.queryForObject("create.existTableIndex","index_city_code") != null
					|| sqlMapClient.queryForObject("create.existTableIndex","index_city_name") != null)
				flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int existPoliticalIndex() {
		int flag = 0;
		try {
			if (sqlMapClient.queryForObject("create.existTableIndex","index_political_village") != null
					|| sqlMapClient.queryForObject("create.existTableIndex","index_political_town") != null
					|| sqlMapClient.queryForObject("create.existTableIndex","index_political_county") != null
					|| sqlMapClient.queryForObject("create.existTableIndex","index_political_city") != null
					|| sqlMapClient.queryForObject("create.existTableIndex","index_political_province") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_political_city_code") != null)
				flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int existStreetIndex() {
		int flag = 0;
		try {
			
			if (sqlMapClient.queryForObject("create.existTableIndex","index_street_province") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_street_city") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_street_city_code") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_street_county") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_street_town") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_street_village") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_street_street") != null)
				flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int existAddressIndex() {
		int flag = 0;
		try {
			if (sqlMapClient.queryForObject("create.existTableIndex","index_address_province") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_address_city") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_address_city_code") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_address_county") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_address_town") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_address_village") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_address_street") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_address_community") != null
					|| sqlMapClient
							.queryForObject("create.existTableIndex","index_address_building") != null)
				flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
