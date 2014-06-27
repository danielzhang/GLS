package cn.gls.ui.component.operator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.operator.ICreateTableAndIndex;
import cn.gls.ui.component.*;
import cn.gls.ui.dao.ConfigDao;
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
public class CreateIndexPanel extends MainPanel {
	private static final long serialVersionUID = 2317856201209407317L;

	public CreateIndexPanel() {
		initComponents();
		init();

	}

	private ICreateTableAndIndex tableIndex;

	private void init() {
		tableIndex = (ICreateTableAndIndex) LoadApplicationContext
				.getInstance().getBean("databaseAssistDao");
		if (tableIndex.existAddressIndex() != 0)
			button9.setEnabled(false);
		if (tableIndex.existStreetIndex() != 0)
			button8.setEnabled(false);
		if (tableIndex.existPoliticalIndex() != 0)
			button7.setEnabled(false);
		if (tableIndex.existCityIndex() != 0)
			button6.setEnabled(false);
		if (tableIndex.existProvinceIndex() != 0)
			button5.setEnabled(false);
		if (tableIndex.existTycIndex() != 0)
			button3.setEnabled(false);
		if (tableIndex.existPinyinIndex() != 0)
			button2.setEnabled(false);
		if (tableIndex.existFatherAndSonIndex() != 0)
			button4.setEnabled(false);
		if (tableIndex.existPlaceIndex() != 0)
			button1.setEnabled(false);
	}

	private DBThread thread;

	private void createPlaceIndex(ActionEvent e) {
		thread = new DBThread("createPlaceIndex", tableIndex, progressBar,
				(JButton) e.getSource());
		thread.start();
	}

	private void createPinyinIndex(ActionEvent e) {
		thread = new DBThread("createPinyinIndex", tableIndex, progressBar,
				(JButton) e.getSource());
		thread.start();
	}

	private void createTycIndex(ActionEvent e) {

		thread = new DBThread("createTycIndex", tableIndex, progressBar,
				(JButton) e.getSource());
		thread.start();
	}

	private void createFatherAndSonIndex(ActionEvent e) {
		thread = new DBThread("createFatherAndSonIndex", tableIndex,
				progressBar, (JButton) e.getSource());
		thread.start();
	}

	private void createProvinceIndex(ActionEvent e) {
		thread = new DBThread("createProvinceIndex", tableIndex, progressBar,
				(JButton) e.getSource());
		thread.start();
	}

	private void createCityIndex(ActionEvent e) {
		thread = new DBThread("createCityIndex", tableIndex, progressBar,
				(JButton) e.getSource());
		thread.start();
	}

	private void createPoliticalIndex(ActionEvent e) {

		thread = new DBThread("createPoliticalIndex", tableIndex, progressBar,
				(JButton) e.getSource());
		thread.start();
	}

	private void createStreetIndex(ActionEvent e) {
		thread = new DBThread("createStreetIndex", tableIndex, progressBar,
				(JButton) e.getSource());
		thread.start();
	}

	private void createAddressIndex(ActionEvent e) {
		thread = new DBThread("createAddressIndex", tableIndex, progressBar,
				(JButton) e.getSource());
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
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		// ======== this ========

		// ---- button1 ----
		button1.setText("\u5730\u540d\u8bcd\u7d22\u5f15");
		button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				createPlaceIndex(e);
			}
		});

		// ---- button2 ----
		button2.setText("\u62fc\u97f3\u8bcd\u7d22\u5f15");
		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				createPinyinIndex(e);
			}
		});

		// ---- button3 ----
		button3.setText("\u540c\u4e49\u8bcd\u7d22\u5f15");
		button3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				createTycIndex(e);
			}
		});

		// ---- button4 ----
		button4.setText("\u7236\u5b50\u7c7b\u7d22\u5f15");
		button4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				createFatherAndSonIndex(e);
			}
		});

		// ---- button5 ----
		button5.setText("\u7701\u7ea7\u5730\u5740\u7d22\u5f15");
		button5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				createProvinceIndex(e);
			}
		});

		// ---- button6 ----
		button6.setText("\u5e02\u7ea7\u5730\u5740\u7d22\u5f15");
		button6.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createCityIndex(e);
			}
		});

		// ---- button7 ----
		button7.setText("\u884c\u653f\u533a\u5730\u5740\u7d22\u5f15");
		button7.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				createPoliticalIndex(e);
			}
		});
		// ---- button8 ----
		button8.setText("\u9053\u8def\u5730\u5740\u7d22\u5f15");
		button8.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createStreetIndex(e);
			}
		});

		// ---- button9 ----
		button9.setText("\u5efa\u7b51\u7269\u5730\u5740\u7d22\u5f15");
		button9.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createAddressIndex(e);
			}
		});

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup()
				.add(layout
						.createSequentialGroup()
						.add(65, 65, 65)
						.add(layout
								.createParallelGroup(GroupLayout.LEADING, false)
								.add(button1, GroupLayout.DEFAULT_SIZE, 173,
										Short.MAX_VALUE)
								.add(button3, GroupLayout.DEFAULT_SIZE, 173,
										Short.MAX_VALUE)
								.add(button5, GroupLayout.DEFAULT_SIZE, 173,
										Short.MAX_VALUE)
								.add(button7, GroupLayout.DEFAULT_SIZE, 173,
										Short.MAX_VALUE)
								.add(button9, GroupLayout.DEFAULT_SIZE, 173,
										Short.MAX_VALUE))
						.add(137, 137, 137)
						.add(layout
								.createParallelGroup(GroupLayout.LEADING, false)
								.add(button2, GroupLayout.DEFAULT_SIZE, 187,
										Short.MAX_VALUE)
								.add(button4, GroupLayout.DEFAULT_SIZE, 187,
										Short.MAX_VALUE)
								.add(button6, GroupLayout.DEFAULT_SIZE, 187,
										Short.MAX_VALUE)
								.add(button8, GroupLayout.DEFAULT_SIZE, 187,
										Short.MAX_VALUE))
						.addContainerGap(153, Short.MAX_VALUE))
				.add(layout.createParallelGroup(GroupLayout.LEADING, false)
						.add(progressBar, GroupLayout.DEFAULT_SIZE,
								(int) (width * 0.7), Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup().add(
				layout.createSequentialGroup()
						.add(59, 59, 59)
						.add(layout.createParallelGroup(GroupLayout.TRAILING)
								.add(button1).add(button2))
						.add(70, 70, 70)
						.add(layout.createParallelGroup(GroupLayout.TRAILING)
								.add(button3).add(button4))
						.add(73, 73, 73)
						.add(layout.createParallelGroup(GroupLayout.TRAILING)
								.add(button5).add(button6))
						.add(79, 79, 79)
						.add(layout.createParallelGroup(GroupLayout.TRAILING)
								.add(button7).add(button8)).add(74, 74, 74)
						.add(button9)
						.add(80,80,80)
						.add(progressBar,60,60,60)
						.addContainerGap(175, Short.MAX_VALUE)));

	}

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
