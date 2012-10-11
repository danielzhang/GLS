package cn.gls.geocoding.engine.data;

import cn.gls.data.GLSStatus;

public class GeoCodingResponseStatus extends GLSStatus {
     public static final String ZERO_RESULTS="zero_results";
     public static final String OVER_QUERY_LIMIT="over_query_limit";
     public static final String REQUEST_DENIED="request_denied";
     public static final String INVALID_REQUEST="invalid_request";
     public static final String SERVER_QUERY_ERROR="server_query_error";
     
}
