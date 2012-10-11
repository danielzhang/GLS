package cn.gls.ui.listener;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import cn.gls.ui.component.ConfigDialog;
import cn.gls.ui.component.DBMenuPanel;
import cn.gls.ui.component.MainPanel;
import cn.gls.ui.component.MenuPanel;
import cn.gls.ui.frame.LoginFrame;
import cn.gls.ui.frame.MainFrame;

public class OptionListener implements ActionListener {

	JButton jbtRefresh;
	JButton jbtConfig;
	JButton jbtLock;
	JButton jbtExit;
	JButton jbtFullscreen;
	JButton jbtMinimize;
	JButton dbConfig;
	JButton home;

	public OptionListener(JButton jbtRefresh, JButton jbtConfig,
			JButton jbtLock, JButton jbtExit, JButton jbtFullscreen,
			JButton jbtMinimize,JButton dbConfig,JButton home) {
		super();
		this.jbtMinimize = jbtMinimize;
		this.jbtRefresh = jbtRefresh;
		this.jbtConfig = jbtConfig;
		this.jbtLock = jbtLock;
		this.jbtExit = jbtExit;
		this.jbtFullscreen = jbtFullscreen;
		this.dbConfig=dbConfig;
		this.home=home;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtRefresh) {
			MainPanel.instance().refresh();
			MainFrame
			.instance()
			.getContentPane()
			.remove(MainFrame.instance().getContentPane()
					.getComponent(2));
	         MainFrame.instance().getContentPane()
			.add(MainPanel.instance(), BorderLayout.CENTER);
			MainFrame.instance().setVisible(true);
		} else if (e.getSource() == jbtConfig) {
			ConfigDialog.instance().open();
		} else if (e.getSource() == jbtLock) {
			LoginFrame.instance().open();
			MainFrame.instance().dispose();
		} else if (e.getSource() == jbtExit) {
			System.exit(0);
		} else if (e.getSource() == jbtFullscreen) {
			if (MainFrame.instance().getExtendedState() == Frame.MAXIMIZED_BOTH) {
				MainFrame.instance().setExtendedState(Frame.NORMAL);
			} else {
				MainFrame.instance().setExtendedState(Frame.MAXIMIZED_BOTH);
			}
			MainFrame
					.instance()
					.getContentPane()
					.remove(MainFrame.instance().getContentPane()
							.getComponent(2));
			MainFrame.instance().getContentPane()
					.add(MainPanel.instance(), BorderLayout.CENTER);
			// 这个很重要
			MainFrame.instance().setVisible(true);
		} else if (e.getSource() == jbtMinimize) {
			if (MainFrame.instance().getExtendedState() == Frame.ICONIFIED) {
				MainFrame.instance().setExtendedState(Frame.NORMAL);
			} else {
				MainFrame.instance().setExtendedState(Frame.ICONIFIED);
			}
		}
		else if(e.getSource()==dbConfig){
		//	更新MainFrame
			MainFrame.instance().getContentPane().remove(MainFrame.instance().getContentPane().getComponent(1));
			MainFrame.instance().getContentPane().add(DBMenuPanel.instance(),BorderLayout.WEST);
			MainPanel.instance().showCreateTable();
			MainFrame.instance().getContentPane().remove(MainFrame.instance().getContentPane().getComponent(1));
			MainFrame.instance().getContentPane().add(MainPanel.instance(),BorderLayout.CENTER);
			MainFrame.instance().pack();
			//这个很重要
			MainFrame.instance().setVisible(true);
		}
		else if(e.getSource()==home){
			//	更新MainFrame
			MainFrame.instance().getContentPane().remove(MainFrame.instance().getContentPane().getComponent(1));
			MainFrame.instance().getContentPane().add(MenuPanel.instance(),BorderLayout.WEST);
			MainPanel.instance().showlookData();
			MainFrame.instance().getContentPane().remove(MainFrame.instance().getContentPane().getComponent(1));
			MainFrame.instance().getContentPane().add(MainPanel.instance(),BorderLayout.CENTER);
			MainFrame.instance().pack();
			//这个很重要
			MainFrame.instance().setVisible(true);
		}

	}
}
