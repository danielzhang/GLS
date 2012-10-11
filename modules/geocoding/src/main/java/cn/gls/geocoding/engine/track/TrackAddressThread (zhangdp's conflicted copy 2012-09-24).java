package cn.gls.geocoding.engine.track;

import java.util.List;


import cn.gls.data.Place;
import cn.gls.geocoding.engine.BAlanalysisEngine;
import cn.gls.geocoding.engine.data.GeoCodingRequestParameter;
import cn.gls.geocoding.engine.data.GeoCodingResponse;
/**
 * @ClassName: TrackAddressThread.java
 * @Description  记录用户行为，可以把这些记录放到数据库中，以供用户的行为分析。
 * @Date  2011-7-26
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-5-29
 */
public class TrackAddressThread extends Thread {
	/**行为分析引擎*/
	private BAlanalysisEngine bEngine;
	/**请求参数*/
	private GeoCodingRequestParameter gParam;
	/**相应结果*/
    private GeoCodingResponse response;
     /**分析后的地名词*/
    private List<Place> places;
	public TrackAddressThread(BAlanalysisEngine bEngine,GeoCodingResponse response,List<Place> places,GeoCodingRequestParameter gParam) {
		super();
		this.bEngine = bEngine;
        this.gParam=gParam;
        this.response=response;
        this.places=places;
	}

	@Override
	public void run() {
		synchronized (this) {

		}
	}
}
