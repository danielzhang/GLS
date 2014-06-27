package cn.gls.ui;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;

import cn.gls.ui.dao.ConfigDao;
import cn.gls.ui.frame.LoadingFrame;
import cn.gls.ui.frame.LoginFrame;
import cn.gls.ui.frame.MainFrame;


public class Main {

	public static void main(String[] args) {
		new Thread(new LoadingFrame()).start();
		// 设置默认字体
		Font font = ConfigDao.instance().getConfig().getFont();
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, font);
		}
		new Thread(new LoginFrame()).start();
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
		new Thread(MainFrame.instance()).start();
	}
}
