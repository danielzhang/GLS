package cn.gls.ui.component;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;


import cn.gls.ui.listener.DBListener;
import cn.gls.ui.listener.MenuListener;

public class DBMenuPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5580869770860377224L;
	private static DBMenuPanel menuPanel;

	public static DBMenuPanel instance() {
		if (menuPanel == null)
			menuPanel = new DBMenuPanel();
		return menuPanel;
	}

	public DBMenuPanel() {
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 15));
		setPreferredSize(new Dimension(200, 600));
		ImageButton createTable,createIndex;
		createTable = new ImageButton("menu", "创建数据表");
		createIndex = new ImageButton("menu", "创建索引");
//		pinyinImport = new ImageButton("menu", "");
//		addressImport = new ImageButton("menu", "");
//		cleanData = new ImageButton("menu", "清理数据");
//		lookData = new ImageButton("menu", "查看原数据");

		createTable.setPreferredSize(new Dimension(180, 50));
		createIndex.setPreferredSize(new Dimension(180, 50));
//		pinyinImport.setPreferredSize(new Dimension(180, 50));
//		addressImport.setPreferredSize(new Dimension(180, 50));
//		cleanData.setPreferredSize(new Dimension(180, 50));
//		lookData.setPreferredSize(new Dimension(180, 50));

		DBListener dbListener = new DBListener(createTable,
				createIndex);
		createTable.addActionListener(dbListener);
		createIndex.addActionListener(dbListener);
//		pinyinImport.addActionListener(menuListener);
//		addressImport.addActionListener(menuListener);
//		cleanData.addActionListener(menuListener);
//		lookData.addActionListener(menuListener);
		
//		add(lookData);
//		add(cleanData);
		add(createTable);
		add(createIndex);
//		add(pinyinImport);
//		add(addressImport);
				
	}
}
