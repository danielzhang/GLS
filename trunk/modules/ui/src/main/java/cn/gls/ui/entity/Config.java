package cn.gls.ui.entity;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.util.Properties;

import javax.swing.ImageIcon;

import cn.gls.ui.component.MenuPanel;
import cn.gls.ui.component.OptionPanel;
import cn.gls.ui.frame.MainFrame;

/**
 * @Description:主要的GLS配置参数
 * @author "Daniel Zhang"
 * 
 */
public class Config {

	private Properties uiProperties;

	public Config(Properties properties) {
		uiProperties = properties;
	}

	public Font getTextFont() {
		return new Font(uiProperties.getProperty("textFontName"),
				Integer.valueOf(uiProperties.getProperty("textFontStyle")),
				Integer.valueOf(uiProperties.getProperty("textFontSize")));
	}

	public String getName() {
		return uiProperties.getProperty("name");
	}

	public Font getFont() {
		return new Font(uiProperties.getProperty("fontName"),
				Integer.valueOf(uiProperties.getProperty("fontStyle")),
				Integer.valueOf(uiProperties.getProperty("fontSize")));
	}
	public Font getDescFont() {
		return new Font(uiProperties.getProperty("descFontName"),
				Integer.valueOf(uiProperties.getProperty("descFontStyle")),
				Integer.valueOf(uiProperties.getProperty("descFontSize")));
	}

	public Font getMenuFont() {
		return new Font(uiProperties.getProperty("menuFontName"),
				Integer.valueOf(uiProperties.getProperty("menuFontStyle")),
				Integer.valueOf(uiProperties.getProperty("menuFontSize")));
	}
	public String getBackground() {
		return uiProperties.getProperty("background");
	}

	public ImageIcon getBackgroundImage() {
		File file = new File(getBackground());
		ImageIcon backgroundImage = null;
		if (file.exists()) {
			backgroundImage = new ImageIcon(getBackground());
		} else
			backgroundImage = new ImageIcon(this.getClass().getResource(
					uiProperties.getProperty("backgroundImage")));
		return backgroundImage;
	}

	public String getCopyright() {
		return uiProperties.getProperty("copyright");
	}



	public String getCleanDescribe() {
		return uiProperties.getProperty("cleanDescribe");
	}

	public String getAddressImportDescribe() {
		return uiProperties.getProperty("addressImportDescribe");
	}

	public String getPlaceImportDescribe() {
		return uiProperties.getProperty("placeImportDescribe");
	}
    
	public String getFatherAndSonDescribe(){
		return uiProperties.getProperty("fatherAndSonDescribe");
	}
	
	public String getPinyinDescribe(){
		return uiProperties.getProperty("pinyinDescribe");
	}

	/**
	 * 获得主Panel的尺寸大小
	 */
	public Dimension getMainPanelDimension() {
		int menuWidth, optionHeight;
		menuWidth = (int) MenuPanel.instance().getPreferredSize().getWidth();
		optionHeight = (int) OptionPanel.instance().getPreferredSize()
				.getHeight();
		return new Dimension((int) getMainFrameDimension().getWidth()
				- menuWidth, (int) getMainFrameDimension().getHeight()
				- optionHeight);
	}
	/**
	 * 获得MainFrame的大小
	 */
	public Dimension getMainFrameDimension() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		float r = 0.8f;
		if (MainFrame.instance().getExtendedState() == Frame.MAXIMIZED_BOTH)
			r = 1.0f;

		return new Dimension((int) (screenSize.getWidth() * r),
				(int) (screenSize.getHeight() * r));
	}
}
