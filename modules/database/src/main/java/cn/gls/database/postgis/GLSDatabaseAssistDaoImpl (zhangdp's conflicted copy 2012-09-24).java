package cn.gls.database.postgis;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import cn.gls.GLSDBSupport;
import cn.gls.data.FatherAndSon;
import cn.gls.data.PinyinPlace;
import cn.gls.data.Place;
import cn.gls.data.TycPlace;
import cn.gls.database.operator.assist.IGLSDatabaseAssistDao;

/**
 * @ClassName: GLSDatabaseAssistDaoImpl.java
 * @Description 辅助表的实现类
 * @Date 2012-7-10
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-10
 */
public class GLSDatabaseAssistDaoImpl extends GLSDBSupport implements
		IGLSDatabaseAssistDao {


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
		  return  (Integer)  this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					 executor.startBatch();
					 int count=0;
					 for (FatherAndSon fatherSon : fatherAndSons) {
							sqlMapClient.insert("assist.insertFatherAndSon", fatherSon);
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
	public int insertGradeData(final Set<Place> places) {
		try {
			resetSeq(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  return  (Integer)  this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					 executor.startBatch();
					 int count=0;
						for (Place place : places){
							sqlMapClient.insert("assist.inserGradeDataToTable", place);
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
	 * @see cn.gls.database.operator.assist.IGLSDatabaseAssistDao#insertPinyin(java.util.Set)
	 */
	public int insertPinyin(final Set<PinyinPlace> places) {
		try {
			resetSeq(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  return  (Integer)  this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			 executor.startBatch();
			 long l=System.currentTimeMillis();
			 int count=0;
			 for (PinyinPlace place : places) {
					executor.insert("assist.insertPinyinPlace", place);
					count++;
				}
			     executor.executeBatch();
			   System.out.println("耗費："+(System.currentTimeMillis()-l));
			
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

}
