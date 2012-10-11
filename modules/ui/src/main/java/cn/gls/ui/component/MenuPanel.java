package cn.gls.ui.component;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import cn.gls.ui.listener.MenuListener;



public class MenuPanel extends JPanel {
	/**
	 * MenuPanel
	 */
	private static final long serialVersionUID = -2703153422697822701L;
	private static MenuPanel menuPanel;

	public static MenuPanel instance() {
		if (menuPanel == null)
			menuPanel = new MenuPanel();
		return menuPanel;
	}

	public MenuPanel() {
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 15));
		setPreferredSize(new Dimension(200, 600));

		ImageButton placeImport,fatherAndSonImport,pinyinImport,addressImport,cleanData,lookData;
		placeImport = new ImageButton("menu", "地名导入");
		fatherAndSonImport = new ImageButton("menu", "父子类导入");
		pinyinImport = new ImageButton("menu", "拼音导入");
		addressImport = new ImageButton("menu", "地址导入");
		cleanData = new ImageButton("menu", "清理数据");
		lookData = new ImageButton("menu", "查看原数据");

		placeImport.setPreferredSize(new Dimension(180, 50));
		fatherAndSonImport.setPreferredSize(new Dimension(180, 50));
		pinyinImport.setPreferredSize(new Dimension(180, 50));
		addressImport.setPreferredSize(new Dimension(180, 50));
		cleanData.setPreferredSize(new Dimension(180, 50));
		lookData.setPreferredSize(new Dimension(180, 50));

		MenuListener menuListener = new MenuListener(placeImport,
				fatherAndSonImport, pinyinImport, addressImport, cleanData, lookData);
		placeImport.addActionListener(menuListener);
		fatherAndSonImport.addActionListener(menuListener);
		pinyinImport.addActionListener(menuListener);
		addressImport.addActionListener(menuListener);
		cleanData.addActionListener(menuListener);
		lookData.addActionListener(menuListener);
		
		add(lookData);
		add(cleanData);
		add(placeImport);
		add(fatherAndSonImport);
		add(pinyinImport);
		add(addressImport);
				
	}
}