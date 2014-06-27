package cn.gls.ui.listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import cn.gls.ui.component.MainPanel;
import cn.gls.ui.frame.MainFrame;

public class DBListener implements ActionListener{

	private JButton createTable,createIndex;
	
	
	
	public DBListener(JButton createTable, JButton createIndex) {
		super();
		this.createTable = createTable;
		this.createIndex = createIndex;
	}



	public void actionPerformed(ActionEvent e) {
		MainPanel mainPanel=null;
	  if(e.getSource()==createTable){
		mainPanel=  MainPanel.instance().showCreateTable();
	  }
	  
	  if(e.getSource()==createIndex){
		 mainPanel= MainPanel.instance().showCreateIndex();
	  }
	  MainFrame.instance().getContentPane().remove(MainFrame.instance().getContentPane().getComponent(2));
		MainFrame.instance().getContentPane().add(mainPanel, BorderLayout.CENTER);
		//这个很重要
		MainFrame.instance().setVisible(true);
	}

}
