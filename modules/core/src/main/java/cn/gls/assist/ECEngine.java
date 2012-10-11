package cn.gls.assist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.gls.context.GLSContext;
import cn.gls.data.Place;
import cn.gls.data.TycPlace;

import cn.gls.place.dao.GLSEngineSupport;
import cn.gls.place.dao.IPlaceDao;

/**
 * @ClassName ECEngine.java
 * @Createdate 2011-2-21
 * @Description  纠错引擎
 * @Version 1.0
 * @Update 2012-5-31 
 * @author "Daniel Zhang"
 *
 */
public class ECEngine extends GLSEngineSupport {
/**地名词访问接口*/
private IPlaceDao placeDao;

/**日志类*/
private static final Log log = LogFactory.getLog(ECEngine.class);

public ECEngine() {
    super(null);
}
public ECEngine(GLSContext context, IPlaceDao placeDao) {
    super(context);
    this.placeDao = placeDao;
}


/**
 * @Param ECEngine
 * @Description 错误词首先通过拼音来获得正确的地名词，然后通过正确的地名词转换为标准的。
 * @return List<Place>
 * @param error
 * @param citycode
 * @return
 */
public List<Place> getPlacesByErrorPlaceName(Place misprint, int citycode) {
    List<Place> places = new ArrayList<Place>();
    //首先地名词转化为拼音
    places=placeDao.pinyinPlacesByError(misprint.getName(), citycode);
    if(places==null)
    	return null;
    //把拼音转化为地名词或标准地名
    for(Iterator<Place> it=places.iterator();it.hasNext();){
    //	判断是否是标准地名词，如果是则不变，否则要通过同义词转化为标准词。
    	Place place=it.next();
    	if(place.getPinyin().isStandard()){
    		place.setName(place.getPinyin().getpName());
    	
    	}
    	else{
    		//把同义词转化为标准地名词
    		ArrayList<TycPlace> tplace=new ArrayList<TycPlace>();
    		tplace.add(new TycPlace(place.getPinyin().getpName(), citycode));
    	    place.setPlaceTycs(tplace);
    	    //查找标准词
    	    place.setName(placeDao.tyc2PlaceName(place.getPinyin().getpName(), citycode));   
    	    
    	}
    	place.setSuffix(misprint.getSuffix());
    }
    return places;
}

/**
 * @param :含有纠错地址词的数组
 * @说明：地址词的标准化
 */
public Map<String, Integer> getStandard(Map<String, Integer> strs,
        String citycode) {
    return strs;
}

}
