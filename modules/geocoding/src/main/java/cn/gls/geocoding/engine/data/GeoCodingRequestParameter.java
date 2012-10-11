package cn.gls.geocoding.engine.data;

import cn.gls.data.GLSRequestParameter;

/**
 * @ClassName: GeoCodingRequestParameter.java
 * @Description  用户请求参数对象
 * @Date  2011-11-1
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-5-29
 */
public class GeoCodingRequestParameter extends GLSRequestParameter {
	/** 中文地理编码希望使用什么样的分词方式，1：根据词库进行分词；2，根据后缀进行分词 */
	private int style = 0;
	/*** 请求结果分数在多少以上 */
	private float s = 60;
//	/** 当前页 */
//	private int c = 1;
//	/** 请求每一页的结果数 */
//	private int p = 1;
	/** 是否批量运行 */
	private boolean isBatch = false;

	// 判断请求的城市
	private String city;

	// 请求的客户端IP，根据这个可以判断城市。
	private String clientIp;

	public GeoCodingRequestParameter() {
        super();
        // TODO Auto-generated constructor stub
    }

    /***
	 * 
	 * @Title:GeoCodingRequestParameter
	 * @Description:TODO
	 * @param:
	 * @return:
	 * @throws
	 */
	public GeoCodingRequestParameter(String q) {
		super(q);
	}

	/**
	 * 
	 * @Title:GeoCodingRequestParameter
	 * @Description:TODO
	 * @param: @param q
	 * @param: @param s
	 * @param: @param clientIp
	 * @return:
	 * @throws
	 */
	public GeoCodingRequestParameter(String q, float s, String clientIp){
	        super(q);
		this.q = q;
		this.s = s;
		this.clientIp = clientIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public float getS() {
		return s;
	}

	public void setS(float s) {
		this.s = s;
	}

//	public int getC() {
//		return c;
//	}
//
//	public void setC(int c) {
//		this.c = c;
//	}
//
//	public int getP() {
//		return p;
//	}
//
//	public void setP(int p) {
//		this.p = p;
//	}

	public boolean isBatch() {
		return isBatch;
	}

	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}

}
