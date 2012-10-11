package cn.gls.geocoding.engine;

import java.util.List;
import java.util.Map;

import cn.gls.data.Place;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.dao.IHistroyDao;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.place.dao.GLSEngineSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @ClassName: BAlanalysisEngine.java
 * @Description 记录用户访问地址及次数或者其他信息
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-27
 */
public class BAlanalysisEngine extends GLSEngineSupport {

/**
 * @param context
 */
public BAlanalysisEngine(GeoCodingContext context) {
    super(context);

}

private IHistroyDao histroyDao;

/**
 * @param sqlClient
 * @param or_address
 * @param standStr
 * @param flag
 * @记录当前用户搜索的地址,时间，是否存在，原始地址及次数。
 */
public synchronized int recordInfo(GeoCodingResponse response) {
    return histroyDao.insertRecordToStandardTable(response);
}

/**
 * @param sqlClient
 * @param words
 * @记录地址词
 */
public synchronized void recordPlaces(List<Place> places) {
    histroyDao.insertRecordPlaceToStandardTable(places);
}
}
