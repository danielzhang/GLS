package cn.gls.assist;


import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.gls.context.GLSContext;
import cn.gls.data.Place;
import cn.gls.place.dao.GLSEngineSupport;
import cn.gls.place.dao.IPlaceDao;

/**
 * @ClassName PinYinEngine.java
 * @Createdate 2012-5-24
 * @Description 拼音引擎
 * @Version 1.0
 * @Update 2012-5-27
 * @author "Daniel Zhang"
 */
public class PinYinEngine extends GLSEngineSupport {

private static final Log log = LogFactory.getLog(PinYinEngine.class);
/**地名词访问接口*/
private IPlaceDao placeDao;
/**
 * 构建拼音引擎
 * @param context
 * @param placeDao
 */
public PinYinEngine(GLSContext context, IPlaceDao placeDao) {
    super(context);
    this.placeDao = placeDao;
}
/**
 * 
 *@Param PinYinEngine
 *@Description 通过拼音来查找地名词
 *@return List<Place>
 * @param pinyin
 * @param citycode
 * @return
 */
public List<Place> getPlacesByPinyin(Place pinyin, int citycode) {
    List<Place> places=placeDao.pinyin2Places(pinyin.getName(), citycode);
        if(places!=null){
        	for(Place place:places){
        		place.setSuffix(pinyin.getSuffix());
        	}
        }
    return places;
}



}
