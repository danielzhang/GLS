package cn.gls.database.postgis.operator.assist;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.gls.GLSDBSupport;
import cn.gls.data.TycPlace;
import cn.gls.data.place.CityDTO;
import cn.gls.database.operator.assist.IGLSDatabaseAssistDao;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @ClassName: DataTableProcessing.java
 * @Description 同义词的处理
 * @Date 2012-6-20
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-20
 */
public class ThesaurusTableProcessing extends GLSDBSupport {

     private IGLSDatabaseAssistDao databaseAssistDao;
    public void setDatabaseAssistDao(IGLSDatabaseAssistDao databaseAssistDao) {
        this.databaseAssistDao = databaseAssistDao;
    }

    private int insertProvinceCity() {
        List<Map<String, Object>> provinceCitys = selectProvinceCity();
        int count = 0;
        SqlMapClient sqlClient = getSqlMapClient();
        try {
            sqlClient.startTransaction();
            sqlClient.startBatch();

            for (Map<String, Object> city : provinceCitys) {
                CityDTO cityDTO = new CityDTO();
                cityDTO.setProvinceName(((String) sqlClient.queryForObject(
                        "city.selectProvinceName",
                        (Integer) city.get("provinceCode"))));
                cityDTO.setCityCode((Integer) city.get("cityCode"));
                sqlClient.update("city.updateProvinceCity", cityDTO);
            }
            count = sqlClient.executeBatch();
            sqlClient.commitTransaction();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                sqlClient.endTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return count;
        }

    }

    private int insertCity() {
        List<Map<String, Object>> provinceCitys = selectProvinceCity();
        int count = 0;
        SqlMapClient sqlClient = getSqlMapClient();
        try {
            sqlClient.startTransaction();
            sqlClient.startBatch();
            for (Map<String, Object> city : provinceCitys) {
                CityDTO cityDTO = new CityDTO();
                cityDTO.setaType("城市");
                cityDTO.setCityCode((Integer) city.get("cityCode"));
                cityDTO.setName((String) city.get("cityName"));
                cityDTO.setProvinceName((String) city.get("provinceName"));
                cityDTO.setStAddress((String) city.get("provinceName")
                        + (String) city.get("cityName"));
                // cityDTO.setZipCode()
                sqlClient.insert("city.insertCitys", cityDTO);
            }
            count = sqlClient.executeBatch();
            sqlClient.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sqlClient.endTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    private int insertThesaurusCity() {
        List<CityDTO> citys = getSqlMapClientTemplate().queryForList(
                "city.selectCitys");
        int count = 0;
        SqlMapClient sqlClient = getSqlMapClient();
        try {
            sqlClient.startTransaction();
            sqlClient.startBatch();
            for (CityDTO city : citys) {
                String st_name = city.getName();
                if (st_name.substring(st_name.length() - 1, st_name.length())
                        .equalsIgnoreCase("市")) {
                    String name = st_name.substring(0, st_name.length() - 1);
                    TycPlace tycplace = new TycPlace(city.getCityCode(),
                            st_name);
                    tycplace.setName(name);
                    sqlClient.insert("city.insertTycs", tycplace);
                } else
                    continue;
            }
            count = sqlClient.executeBatch();
            sqlClient.commitTransaction();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                sqlClient.endTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return count;
        }

    }

    private List<Map<String, Object>> selectProvinceCity() {
        return getSqlMapClientTemplate()
                .queryForList("city.selectProvinceCity");
    }

    /**
     * 插入同义词 (non-Javadoc)
     * 
     * @see cn.gls.database.operator.assist.IThesaurusOperator#insertThesaurusData(java.util.Set)
     */
    public int insertThesaurusData(Set<TycPlace> places) {
        SqlMapClient sqlClient = getSqlMapClient();
        int count = 0;
        try {
            sqlClient.startTransaction();
            sqlClient.startBatch();
            for (TycPlace place : places) {
                if (sqlClient.queryForObject("assist.selectTycPlace", place) != null)
                    sqlClient.insert("assist.insertTycPlace", place);
            }
            count = sqlClient.executeBatch();
            sqlClient.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                sqlClient.endTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;
    }

}
