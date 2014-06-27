package cn.gls.geocoding.service;

import java.io.IOException;
import java.util.List;

import cn.gls.Request;
import cn.gls.Response;
import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.engine.GeoCodingEngine;
import cn.gls.geocoding.engine.IGeoCodingEngine;
import cn.gls.geocoding.engine.data.GeoCodingRequest;
import cn.gls.geocoding.engine.data.GeoCodingResponse;

/**
 * 地理编码服务接口
 * 
 * @author 张德品
 * @date 2011-4-19
 * @说明
 */
public class GeoCodingService implements IGeoCodingService {

/** 中文地理编码引擎 **/
private IGeoCodingEngine geoCodingEngine;

public void setGeoCodingEngine(GeoCodingEngine geoCodingEngine) {
    this.geoCodingEngine = geoCodingEngine;
}

/**
 * 中文地理编码的构造函数
 */
public GeoCodingService(GeoCodingContext context) {
    geoCodingEngine = new GeoCodingEngine(context);
}

/**
 * 执行单一地理编码服务
 */
public GeoCodingResponse getResponse(Request request) throws IOException {
    return (GeoCodingResponse) geoCodingEngine
            .getGeoCodingResults((GeoCodingRequest) request);
}
/**
 * 批量地理編碼
 */
public List<GeoCodingResponse> getBatchResponse(GeoCodingRequest request) {

    return null;
}

}
