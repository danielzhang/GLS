package cn.gls.data;

/**
 * @ClassName GLSRequestParameter.java
 * @Description GLS请求参数
 * @Version 1.0
 * @Update 2012-7-3
 * @author "Daniel Zhang"
 *
 */
public class GLSRequestParameter {

/**请求参数绝大部分是地址串*/
protected String q;

public GLSRequestParameter() {
    super();
    // TODO Auto-generated constructor stub
}

public GLSRequestParameter(String q) {
	super();
	this.q = q;
}

public String getQ() {
        return q;
}

public void setQ(String q) {
        this.q = q;
}
}
