package cn.gls.database;

import cn.gls.GLSException;
/**
 * 
 * @ClassName GLSDataBaseException.java
 * @Createdate 2012-6-28
 * @Description 拋出database异常
 * @Version 1.0
 * @Update 2012-6-28 
 * @author "Daniel Zhang"
 *
 */
public class GLSDataBaseException extends GLSException {
public GLSDataBaseException(String message) {
    super(message);
}

public GLSDataBaseException() {
    super();

}

public GLSDataBaseException(String message, Throwable cause) {
    super(message, cause);
}

public GLSDataBaseException(Throwable cause) {
    super(cause);

}
}
