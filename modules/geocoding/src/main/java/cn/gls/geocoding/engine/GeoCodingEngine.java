package cn.gls.geocoding.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.gls.data.Place;
import cn.gls.data.SegAddress;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.context.support.GeoCodingContextUtil;
import cn.gls.geocoding.engine.batch.BatchGeoCodingEngine;
import cn.gls.geocoding.engine.dao.IGeoCodingAttributeDao;
import cn.gls.geocoding.engine.data.GeoCodingRequest;
import cn.gls.geocoding.engine.data.GeoCodingRequestParameter;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.seg.AutoSegWord;
import cn.gls.seg.ISegWord;
import cn.gls.seg.SegAddressEngine;
import cn.gls.seg.StyleSegWord;
import cn.gls.util.IPUtil;

/**
 * @ClassName: GeoCodingEngine.java
 * @Description 说明 中文地理编码的门面设计
 * @Date 2012-5-24
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-13
 */
public class GeoCodingEngine implements IGeoCodingEngine {

	protected static final Log log = LogFactory.getLog(GeoCodingEngine.class);

	protected static final Boolean isDebug = log.isDebugEnabled();

	/** 地理编码的上下文环境 */
	protected GeoCodingContext context;

	/** 基础地理编码引擎 */
	private BaseEngine baseEngine;

	/** 访问日志记录引擎 **/

	private BAlanalysisEngine baEngine;

	private List<Place> places=null;

	/**** PlaceDaoImpl实例化 **/

	protected IGeoCodingAttributeDao placeDao = (IGeoCodingAttributeDao) GeoCodingContextUtil
			.getBean("geoCodingAttributeDao");

	public void setContext(GeoCodingContext context) {
		this.context = context;
	}

	/**
	 * 实例化GeoCodingContext
	 * 
	 * @param context
	 */
	public GeoCodingEngine(GeoCodingContext context) {
		this.context = context;
		initEngine();
		places = new ArrayList<Place>();
	}

	/**
	 * @Param GeoCodingEngine
	 * @Description 初始化各种引擎
	 * @return void
	 */
	private void initEngine() {
		baseEngine = new BaseEngine(context);
		baEngine = new BAlanalysisEngine(context);
	}

	/**
	 * (non-Javadoc) 需要知道查询字符串----q,分数----s，当前页面-----c，页面数量----p
	 * 
	 * @see cn.gis.geocoding.engine.IGeoCodingEngine#getGeoCodingResults(cn.gls.geocoding
	 *      .Request)
	 */
	public GeoCodingResponse getGeoCodingResults(GeoCodingRequest request) {
		// 定义Responese
		GeoCodingResponse response = new GeoCodingResponse(request);
		// 获得请求参数
		GeoCodingRequestParameter gParam = request.getGbsRequestParameter();
		// 获得返回对象
		return getResponse(gParam, response);

	}

	/**
	 * 根据参数名来获得返回对象
	 * 
	 * @param gParam
	 * @param response
	 * @return
	 */
	public GeoCodingResponse getResponse(GeoCodingRequestParameter gParam,
			GeoCodingResponse response) {
		// 开始分词
		ISegWord segWord = null;
		float l = System.currentTimeMillis();
		if (gParam.getStyle() == 0)
			segWord = new AutoSegWord();
		if (gParam.getStyle() == 1)
			segWord = new StyleSegWord();
		SegAddress segAddress = segWord.seg4Address(gParam);
		long s = System.currentTimeMillis();
		System.out.println("分词需要的时间:" + (s - l));
		// 标准化
		log.info("开始对地址词进行标准化");

		// 首先判断所在城市
		// 如果有city参数，则需要先求出citycode
		int citycode = 0;
		if (gParam.getCity() != null)
			citycode = placeDao.getCityCodeByName(gParam.getCity());
		if (citycode == 0)
			citycode = getCityCode(segAddress.getPlacesList(),
					gParam.getClientIp());
		SegAddressEngine segEngine = new SegAddressEngine(segWord, context,
				placeDao);
	 places = segEngine.standardPlace(
				segAddress.getPlacesList(), citycode);
		// 转移到BaseEngine
		response = baseEngine.getGeoCodingResponse(places, gParam, response);
		// 开辟新线程，记录请求的对象及相应的结果.
		 RecordInfoThread recordThread = new RecordInfoThread(response,
		 places);
		 recordThread.run();
		return response;
	}

	/***
	 * 由地名词来判别城市级别 首先如果是正确的地名词,再进行判断是否是城市级别的地名词。这个要求优先级最高
	 * 
	 * @param wds
	 * @return
	 */
	private int getCityCodeByAddress(List<Place> places) {
		int citycode = 0;
		for (Place place : places) {

			String placeName = place.getName().trim();
			// 进行判断
			if (place.getPlaceLevel() == 0) {
				citycode = placeDao.getCityCodeByPlaceName(placeName, 3);
				if (citycode != 0)
					break;
			}
		}
		if(citycode==0){
//			int f=0;
			List<Integer> cityCodes=new ArrayList<Integer>();
			 for(Place p:places){
				 cityCodes.addAll(placeDao.getCityCodeByPlaceName(p.getName()));
			 }
				//判断最多的places
				int count=0;
				for(Integer i:cityCodes){
					 int c=Collections.frequency(cityCodes, i);
					 if(c>count)
					 {
						 count=c;
						 citycode=i;
					 }
				}
		}
		return citycode;
	}

	/***
	 * 根据ip地址来获得citycode
	 * 
	 * @param clientip
	 * @return
	 */
	private int getCityCodeByIP(String clientip) {
		if(clientip==null||clientip.indexOf("//")<0)
			return 0;
		// 判断citycode
		String ip = clientip.substring(clientip.indexOf("//") + 2,
				clientip.length());
		log.info("客户端访问的ip为" + ip);
		String city = IPUtil.getCity(context.getIpMap(), ip);
		return placeDao.getCityCodeByName(city);

	}

	/***
	 * 获得城市编码
	 * 
	 * @param 分词词组
	 * @param 客户端ip
	 * @return 城市编码
	 */
	private int getCityCode(List<Place> places, String clientip) {
		int citycode = getCityCodeByAddress(places);
		//通过地名词寻找cityCode
		//citycode=getCityCodeByPlace();
		if (citycode == 0)
			getCityCodeByIP(clientip);
		
		return citycode;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cn.gis.geocoding.engine.IGeoCodingEngine#getGeoCodingBatchResults(cn.
	 *      gis.geocoding.Request)
	 */
	public List<GeoCodingResponse> getGeoCodingBatchResults(
			GeoCodingRequest request) {
//		GeoCodingResponse response = new GeoCodingResponse(request);
		GeoCodingRequestParameter gParam = request.getGbsRequestParameter();

		BatchGeoCodingEngine batchEngine = new BatchGeoCodingEngine(context);
		return batchEngine.getResultes(gParam);

	}

	private class RecordInfoThread implements Runnable {

		private GeoCodingResponse response;
		public List<Place> places;

		public RecordInfoThread(GeoCodingResponse response, List<Place> places) {
			this.response = response;
			this.places = places;
		}

		public void run() {
			// 记录地址
			recordAddress();
			// 记录地名词
			recordPlaces();

		}

		private int recordAddress() {
			return baEngine.recordInfo(response);
		}

		private void recordPlaces() {
			baEngine.recordPlaces(places);
		}
	}
}
