package cn.gls.ui.listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import cn.gls.ui.component.MainPanel;
import cn.gls.ui.frame.MainFrame;


public class MenuListener implements ActionListener {

	private JButton placeImport, fatherAndSonImport, pinyinImport, addressImport,
			cleanData, lookData;

	public MenuListener(JButton placeImport, JButton fatherAndSonImport,
			JButton pinyinImport, JButton addressImport, JButton cleanData,
			JButton lookData) {
		this.placeImport = placeImport;
		this.fatherAndSonImport = fatherAndSonImport;
		this.pinyinImport = pinyinImport;
		this.addressImport = addressImport;
		this.cleanData = cleanData;
		this.lookData = lookData;
	}


	public void actionPerformed(ActionEvent e) {
		MainPanel mainPanel=null;
		if (e.getSource() == placeImport) {
			mainPanel=MainPanel.instance().showPlaceImport(null,null);
		} else if (e.getSource() == fatherAndSonImport) {
			mainPanel=MainPanel.instance().showfatherAndSonImport();
		} else if (e.getSource() == pinyinImport) {
			mainPanel=MainPanel.instance().showpinyinImport();
		} else if (e.getSource() == addressImport) {
			mainPanel=MainPanel.instance().showaddressImport();
		} else if (e.getSource() == cleanData) {
			mainPanel=MainPanel.instance().showcleanData(null);
		} else if (e.getSource() == lookData) {
			mainPanel=MainPanel.instance().showlookData();
		}
		MainFrame.instance().getContentPane().remove(MainFrame.instance().getContentPane().getComponent(2));
		MainFrame.instance().getContentPane().add(mainPanel, BorderLayout.CENTER);
		//这个很重要
		MainFrame.instance().setVisible(true);
	}

}
