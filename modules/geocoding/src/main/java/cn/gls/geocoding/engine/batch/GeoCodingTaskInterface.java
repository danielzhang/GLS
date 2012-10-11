
package cn.gls.geocoding.engine.batch;

import cn.gls.geocoding.engine.data.GeoCodingResponse;


/**
 * @ClassName GeoCodingTaskInterface.java
 * @Createdate 2011-7-26
 * @Description 地理编码任务接口
 * @Version 1.0
 * @Update 2012-5-27 
 * @author "Daniel Zhang"
 *
 */
public interface GeoCodingTaskInterface {
	GeoCodingResponse execute();
}
