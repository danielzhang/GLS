package cn.gls.ui.component.operator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.operator.ICreateTableAndIndex;
import cn.gls.ui.component.*;
import cn.gls.ui.db.thread.DBThread;

import org.jdesktop.layout.GroupLayout;
/**
 * 
 * @date 2012-8-21
 * @author "Daniel Zhang"
 * @update 2012-8-21
 * @description TODO
 *
 */
public class CreateTablePanel extends MainPanel {
	public CreateTablePanel() {
		initComponents();
		init();
	}
	/**
	 * 去数据库查询，有哪些表存在
	 */
	private void init(){
		tableIndex=(ICreateTableAndIndex) LoadApplicationContext.getInstance().getBean("databaseAssistDao");
		//判断哪些表已经创建
	int address=	tableIndex.existAddressTable();
	int city=	tableIndex.existCityTable();
	int fs=	tableIndex.existFatherAndSonTable();
	int pinyin=	tableIndex.existPinyinTable();
	int place=	tableIndex.existPlaceTable();
	int political=	tableIndex.existPoliticalTable();
	int province=	tableIndex.existProvinceTable();
	int street=	tableIndex.existStreetTable();
	int tyc=	tableIndex.existTycTable();

	if(address!=0)
		button9.setEnabled(false);
	if(street!=0)
		button8.setEnabled(false);
	if(political!=0)
		button7.setEnabled(false);
	if(city!=0)
		button6.setEnabled(false);
	if(province!=0)
		button5.setEnabled(false);
	if(tyc!=0)
		button4.setEnabled(false);
	if(pinyin!=0)
		button3.setEnabled(false);
	if(fs!=0)
		button2.setEnabled(false);
	if(place!=0)
		button1.setEnabled(false);
	}
	private ICreateTableAndIndex tableIndex;
	private void createPlace(ActionEvent e){
		thread=new DBThread("createPlace",tableIndex, progressBar,(JButton)e.getSource());
		thread.start();
		
	}
	private void createfs(ActionEvent e){
		thread=new DBThread("createFatherAndSon", tableIndex, progressBar,(JButton)e.getSource());
		thread.start();
	}
	private void createPinyin(ActionEvent e){
		thread=new DBThread("createPinyin",tableIndex,progressBar,(JButton)e.getSource());
		thread.start();
	}
	private void creatTyc(ActionEvent e){
		thread=new DBThread("createTyc", tableIndex, progressBar,(JButton)e.getSource());
		thread.start();
	}
	private void createProvince(ActionEvent e){
		thread=new DBThread("createProvince", tableIndex, progressBar,(JButton)e.getSource());
		thread.start();
	}
	private void createCity(ActionEvent e){
		thread=new DBThread("createCity", tableIndex, progressBar,(JButton)e.getSource());
		thread.start();
	}
	private void createPolitical(ActionEvent e){
		thread=new DBThread("createPolitical", tableIndex, progressBar,(JButton)e.getSource());
		thread.start();
	}
	private void createStreet(ActionEvent e){
		thread=new DBThread("createStreet", tableIndex, progressBar,(JButton)e.getSource());
		thread.start();
	}
	private void createAddress(ActionEvent e){
		thread=new DBThread("createAddress",tableIndex,progressBar,(JButton)e.getSource());
		thread.start();
	}
	
	
	private void initComponents() {
		
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		button4 = new JButton();
		button5 = new JButton();
		button6 = new JButton();
		button7 = new JButton();
		button8 = new JButton();
		button9 = new JButton();
		progressBar=new JProgressBar();
		progressBar.setVisible(false);
		
		
		//======== this ========


		//---- button1 ----
		button1.setText("\u5730\u540d\u8bcd\u8868");
        button1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				createPlace(e);
			}
		});
		
		//---- button2 ----
		button2.setText("\u7236\u5b50\u7c7b\u8868");
		
		button2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				createfs(e);
				
			}
		});

		//---- button3 ----
		button3.setText("\u62fc\u97f3\u8868");
		button3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				createPinyin(e);
				
			}
		});

		//---- button4 ----
		button4.setText("\u540c\u4e49\u8bcd\u8868");
		button4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				creatTyc(e);
			}
		});

		//---- button5 ----
		button5.setText("\u7701\u7ea7\u5730\u5740\u8868");
		button5.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			       createProvince(e);
			}
		});

		//---- button6 ----
		button6.setText("\u5e02\u7ea7\u5730\u5740\u8868");
		button6.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			createCity(e);
			}
		});

		//---- button7 ----
		button7.setText("\u884c\u653f\u533a\u57df\u5730\u7ea7\u8868");
         button7.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			   createPolitical(e);
			}
		});
		//---- button8 ----
		button8.setText("\u9053\u8def\u7ea7\u5730\u5740\u8868");
		button8.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				createStreet(e);
			}
		});

		//---- button9 ----
		button9.setText("\u5efa\u7b51\u7269\u5730\u5740\u8868");
          button9.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				createAddress(e);
			}
		});
		
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.add(layout.createSequentialGroup()
					.add(73, 73, 73)
					.add(layout.createParallelGroup()
						.add(button9, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
						.add(layout.createSequentialGroup()
							.add(layout.createParallelGroup()
								.add(button1, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.add(button3, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.add(button5, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.add(button7))
							.add(174, 174, 174)
							.add(layout.createParallelGroup()
								.add(button8, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.add(button6, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.add(button4, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.add(button2, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)))
							.add(progressBar, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)	
							)
					.addContainerGap(83, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.add(layout.createSequentialGroup()
					.add(42, 42, 42)
					.add(layout.createParallelGroup(GroupLayout.BASELINE)
						.add(button1)
						.add(button2))
					.add(78, 78, 78)
					.add(layout.createParallelGroup(GroupLayout.BASELINE)
						.add(button3)
						.add(button4))
					.add(74, 74, 74)
					.add(layout.createParallelGroup(GroupLayout.BASELINE)
						.add(button5)
						.add(button6))
					.add(71, 71, 71)
					.add(layout.createParallelGroup(GroupLayout.BASELINE)
						.add(button7)
						.add(button8))
					.add(72, 72, 72)
					.add(button9)
					.add(72,72,72)
					.add(progressBar,60,60,60)
					.addContainerGap(108, Short.MAX_VALUE))
		);
	}
	
	private DBThread thread;
	private JProgressBar progressBar;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button9;

}
