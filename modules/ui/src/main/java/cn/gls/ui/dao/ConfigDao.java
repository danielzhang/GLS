package cn.gls.ui.dao;

import cn.gls.ui.entity.Config;
import cn.gls.ui.frame.MainFrame;



public class ConfigDao {
	private Config config;
	private static ConfigDao configDao;

	public static ConfigDao instance() {
		if (configDao == null)
			configDao = new ConfigDao();
		return configDao;
	}

	public ConfigDao() {
		config = new Config(MainFrame.instance().getProperties());
	}

	public Config getConfig() {
		return config;
	}
	

	public void setConfig(Config config) {
		this.config = config;
	}
}
