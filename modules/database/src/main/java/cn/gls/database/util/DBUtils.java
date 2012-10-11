package cn.gls.database.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.gls.database.DynamicLoadBean;
import cn.gls.database.LoadApplicationContext;
import cn.gls.util.SafeProperties;

/**
 * 
 * @date 2012-8-14
 * @author "Daniel Zhang"
 * @update 2012-8-14
 * @description 與數據庫的操作。
 * 
 */
public class DBUtils {
	public static boolean varificationLinkDB(String dbType, String dbAddress,
			String dbName, String userName, String userPassword, String port) {
		String driver = null;
		String url = null;
		if ("oracle".equalsIgnoreCase(dbType)) {
			driver = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + dbAddress + ":" + port + ":" + dbName;
		} else if ("PostgreSQL".equalsIgnoreCase(dbType)) {
			driver = "org.postgresql.Driver";
			url = " jdbc:postgresql://" + dbAddress + ":" + port + "/" + dbName
					+ "";
		}
		if (setDriver(driver))
			return setConnection(url, userName, userPassword);
		else
			return false;
	}

	private static boolean setConnection(String url, String userName,
			String userPWD) {
		try {
			Connection m_conn = DriverManager.getConnection(url, userName,
					userPWD);
			Statement m_stmt = m_conn.createStatement();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean setDriver(String driver) {
		try {
			Class.forName(driver);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean changeConfigDB(String dbType, String dbAddress,
			String dbName, String userName, String userPassword, String port) {

		boolean flag = false;
		flag = varificationLinkDB(dbType, dbAddress, dbName, userName,
				userPassword, port);
		if (!flag) {
			return flag;
		}
		// 操作db.properties
		/** 属性配置文件的位置 */
		String DEFAULT_DB_PATH = "conf/database.properties";
		String driver = null;
		String url = null;
		if ("oracle".equalsIgnoreCase(dbType)) {
			driver = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + dbAddress + ":" + port + ":" + dbName;
		} else if ("PostgreSQL".equalsIgnoreCase(dbType)) {
			driver = "org.postgresql.Driver";
			url = "jdbc:postgresql://" + dbAddress + ":" + port + "/" + dbName
					+ "";
		}
		Properties databaseProperties = new SafeProperties();
		try {
			InputStream in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(DEFAULT_DB_PATH);
			databaseProperties.load(in);

		} catch (IOException e) {
			throw new IllegalStateException(
					"Could not load 'standardTable.properties': "
							+ e.getMessage());
		}
		//
		// String[] targetVars = {
		// "jdbc.driverClassName",
		// "jdbc.url",
		// "jdbc.username",
		// "jdbc.password","dbtype","host","port","schema","database","user","passwd"};
		databaseProperties.setProperty("jdbc.driverClassName", driver);
		databaseProperties.setProperty("jdbc.url", url);
		databaseProperties.setProperty("jdbc.username", userName);
		databaseProperties.setProperty("jdbc.password", userPassword);

		databaseProperties.setProperty("dbtype", dbType);
		databaseProperties.setProperty("host", dbAddress);
		databaseProperties.setProperty("port", port);
		databaseProperties.setProperty("schema", "public");
		databaseProperties.setProperty("database", dbName);
		databaseProperties.setProperty("user", userName);
		databaseProperties.setProperty("passwd", userPassword);

		try {
			File file = new File(Thread.currentThread().getContextClassLoader()
					.getResource(DEFAULT_DB_PATH).getFile());
			OutputStream out = new FileOutputStream(file);
			databaseProperties.store(out, "Update  DB Config");
			out.flush();
			out.close();
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;

	}

	private static Map<String, String> getTraFileValue(Properties prop,
			String[] keys) {
		HashMap<String, String> env = new HashMap<String, String>();
		for (String key : keys) {
			env.put(key, getTraFileValue(prop, key));
		}
		return env;
	}

	private static String getTraFileValue(Properties prop, String key) {
		return prop.getProperty(key);
	}

	public static void main(String[] args) {
		boolean flag = changeConfigDB("PostgreSQL", "192.168.120.247",
				"geoq_dev", "geoq", "geoq", "5432");
		System.out.println(flag);
	}
	public static void loadAddressTable(){
		DynamicLoadBean dynamicLoadBean = (DynamicLoadBean) LoadApplicationContext
				.getInstance().getBean("dynamicLoadBean");
		dynamicLoadBean.loadBean("classpath:conf/addressTable.xml");
	}
}
