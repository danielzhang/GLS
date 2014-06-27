package cn.gls.database.postgis.standard;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * @ClassName: BaseServiceTest.java
 * @Description  
 * @Date  2012-6-14
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-14
 */
public class BaseServiceTest extends
		AbstractDependencyInjectionSpringContextTests {
	// 指定Spring配置文件的位置
	@Override
	protected String[] getConfigLocations() {
		return new String[] { "/conf/applicationContext.xml" };
	}
}
