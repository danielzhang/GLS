package cn.gls.database.operator;

import cn.gls.database.GLSDataBaseException;

/**
 * @ClassName: GLSDataOperatorException.java
 * @Description  TODO
 * @Date  2012-7-3
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-7-3
 */
public class GLSDataOperatorException extends GLSDataBaseException {

	public GLSDataOperatorException() {
		super();
		
	}

	public GLSDataOperatorException(String message, Throwable cause) {
		super(message, cause);

	}

	public GLSDataOperatorException(String message) {
		super(message);

	}

	public GLSDataOperatorException(Throwable cause) {
		super(cause);

	}
             
}
