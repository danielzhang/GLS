package cn.gls.ui.component;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import cn.gls.ui.listener.OptionListener;


public class OptionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1613590806361203632L;
	private static OptionPanel optionPanel;
	
	static public OptionPanel instance(){
		if(optionPanel == null)
			optionPanel = new OptionPanel();
		return optionPanel;
	}

	public OptionPanel() {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10));
		setSize(1366, 150);
		setOpaque(false);
//		optionPanel = this;

		ImageButton jbtRefresh = new ImageButton("refresh");
		ImageButton jbtConfig = new ImageButton("config");
		ImageButton jbtLock = new ImageButton("lock");
		ImageButton jbtExit = new ImageButton("exit");
		ImageButton jbtFullscreen=new ImageButton("fullscreen");
		ImageButton jbtMinimize=new ImageButton("minimize");
		ImageButton dbConfig=new ImageButton("db");
		ImageButton home=new ImageButton("home");
		
		jbtMinimize.setToolTipText(" 最小化 ");
        jbtFullscreen.setToolTipText(" 全屏  ");
		jbtRefresh.setToolTipText("　刷新　");
		jbtConfig.setToolTipText("　设置　");
		jbtLock.setToolTipText("　注销　");
		jbtExit.setToolTipText("　退出　");
		dbConfig.setToolTipText("数据库");
		home.setToolTipText("主页");
		
		Dimension dimension=new Dimension(80,80);

        jbtFullscreen.setPreferredSize(dimension);
		jbtRefresh.setPreferredSize(dimension);
		jbtConfig.setPreferredSize(dimension);
		jbtLock.setPreferredSize(dimension);
		jbtExit.setPreferredSize(dimension);
		jbtMinimize.setPreferredSize(dimension);
        dbConfig.setPreferredSize(dimension);
        home.setPreferredSize(dimension);
		
		
		OptionListener optionListener = new OptionListener( jbtRefresh, jbtConfig, jbtLock, jbtExit,jbtFullscreen,jbtMinimize,dbConfig,home);		
        jbtFullscreen.addActionListener(optionListener);
		jbtRefresh.addActionListener(optionListener);
		jbtConfig.addActionListener(optionListener);
		jbtLock.addActionListener(optionListener);
		jbtExit.addActionListener(optionListener);
		jbtMinimize.addActionListener(optionListener);
        dbConfig.addActionListener(optionListener);
        home.addActionListener(optionListener);
        
        add(home);
        add(dbConfig);
		add(jbtFullscreen);
		add(jbtMinimize);
		add(jbtRefresh);
		add(jbtConfig);
		add(jbtLock);
		add(jbtExit);
	}
}
