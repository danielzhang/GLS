/**
 *2011-4-19
 *TODO
 *
 *
 */
package cn.gls.geocoding.service;

import cn.gls.geocoding.engine.data.GeoCodingResponse;
import cn.gls.service.IGLSService;

public interface IReGeoCodingService extends IGLSService {
	GeoCodingResponse getResponse(float lat, float lon, Integer dis);

	GeoCodingResponse getResponse(float minlat, float maxlat, float minlon,
			float maxlon);
}
