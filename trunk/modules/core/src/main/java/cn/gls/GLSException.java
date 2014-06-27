package cn.gls;
/**
 * @ClassName: GISServiceException.java
 * @Description  
 * @Date  2012-5-25
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-5-25
 */
public class GLSException extends RuntimeException {

	/**@Field serialVersionUID:TODO*/
	private static final long serialVersionUID = 3L;

	public GLSException(String message) {
		super(message);
	}

	public GLSException() {
		super();

	}

	public GLSException(String message, Throwable cause) {
		super(message, cause);
	}

	public GLSException(Throwable cause) {
		super(cause);

	}

	/**
	 * 返回详细信息
	 */

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
