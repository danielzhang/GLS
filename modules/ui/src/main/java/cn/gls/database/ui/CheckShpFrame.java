/*
 * Created by JFormDesigner on Mon Jul 30 09:37:42 CST 2012
 */

package cn.gls.database.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import cn.gls.database.util.ShpUtils;
import cn.gls.database.util.UIUtils;

/**
 * 
 * @date 2012-7-31
 * @author "Daniel Zhang"
 * @update 2012-7-31
 * @description TODO
 *
 */
public class CheckShpFrame extends JFrame {
	

	public CheckShpFrame() {
		initComponents();
	}

	String filePath;

	/**
	 * 查看原文件
	 * 
	 * @param e
	 */
	private void button1ActionPerformed(ActionEvent e) {
         JFileChooser fileChooser=new JFileChooser();
		 fileChooser.setFileFilter(UIUtils.filter);
		 int returnVal = fileChooser.showOpenDialog(this);
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
			// 读取文件属性。
			textField1.setText(fileChooser.getSelectedFile().getPath());
			filePath = textField1.getText();
			// 为table赋值
			java.util.List<String> columns = ShpUtils.getColumnNames(filePath);
			Object[][] tables = ShpUtils.getTableData(filePath, columns);
			// table1.set
			table1 = new JTable(tables, columns.toArray());
			scrollPane1.setViewportView(table1);
		}
	}

	/**
	 * 取消选中的文件属性
	 * 
	 * @param e
	 */
	private void button2ActionPerformed(ActionEvent e) {

		table1.setModel(new DefaultTableModel());
		filePath = null;
		textField1.setText(null);
	}

	/**
	 * 进入清理页面
	 * 
	 * @param e
	 */
	private void button3ActionPerformed(ActionEvent e) {
		CleanShpFrame cleanFrame = new CleanShpFrame();
		cleanFrame.cleanFrame.setVisible(true);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - daniel zhang
		label1 = new JLabel();
		textField1 = new JTextField();
		button1 = new JButton();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		button2 = new JButton();
		button3 = new JButton();
		progressBar1 = new JProgressBar();

		// ======== this ========
		setTitle("\u67e5\u770bshp\u6587\u4ef6\u5c5e\u6027");
		Container contentPane = getContentPane();

		// ---- label1 ----
		label1.setText("\u9009\u62e9\u6587\u4ef6\uff1a");

		// ---- button1 ----
		button1.setText("...");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1ActionPerformed(e);
			}
		});

		// ======== scrollPane1 ========
		{
			scrollPane1.setViewportView(table1);
		}

		// ---- button2 ----
		button2.setText("\u53d6\u6d88");
		button2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 22));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button2ActionPerformed(e);
			}
		});

		// ---- button3 ----
		button3.setText("\u6e05\u7406");
		button3.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 22));
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button3ActionPerformed(e);
			}
		});

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout
				.setHorizontalGroup(contentPaneLayout
						.createParallelGroup()
						.add(contentPaneLayout
								.createSequentialGroup()
								.addContainerGap()
								.add(contentPaneLayout
										.createParallelGroup()
										.add(contentPaneLayout
												.createSequentialGroup()
												.add(contentPaneLayout
														.createParallelGroup()
														.add(contentPaneLayout
																.createSequentialGroup()
																.add(label1)
																.add(29, 29, 29)
																.add(textField1,
																		GroupLayout.PREFERRED_SIZE,
																		258,
																		GroupLayout.PREFERRED_SIZE)
																.add(18, 18, 18)
																.add(button1)
																.add(0,
																		26,
																		Short.MAX_VALUE))
														.add(scrollPane1,
																GroupLayout.DEFAULT_SIZE,
																482,
																Short.MAX_VALUE)
														.add(progressBar1,
																GroupLayout.DEFAULT_SIZE,
																482,
																Short.MAX_VALUE))
												.addContainerGap())
										.add(GroupLayout.TRAILING,
												contentPaneLayout
														.createSequentialGroup()
														.add(0, 193,
																Short.MAX_VALUE)
														.add(button3,
																GroupLayout.PREFERRED_SIZE,
																88,
																GroupLayout.PREFERRED_SIZE)
														.add(74, 74, 74)
														.add(button2)
														.add(63, 63, 63)))));
		contentPaneLayout.setVerticalGroup(contentPaneLayout
				.createParallelGroup().add(
						contentPaneLayout
								.createSequentialGroup()
								.add(38, 38, 38)
								.add(contentPaneLayout
										.createParallelGroup(
												GroupLayout.BASELINE)
										.add(label1,
												GroupLayout.PREFERRED_SIZE, 32,
												GroupLayout.PREFERRED_SIZE)
										.add(textField1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.add(button1))
								.addPreferredGap(LayoutStyle.RELATED)
								.add(scrollPane1, GroupLayout.PREFERRED_SIZE,
										653, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.UNRELATED)
								.add(contentPaneLayout
										.createParallelGroup(
												GroupLayout.BASELINE)
										.add(button2)
										.add(button3, GroupLayout.DEFAULT_SIZE,
												37, Short.MAX_VALUE))
								.addPreferredGap(LayoutStyle.RELATED)
								.add(progressBar1, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.add(14, 14, 14)));
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - daniel zhang
	private JLabel label1;
	private JTextField textField1;
	private JButton button1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton button2;
	private JButton button3;
	private JProgressBar progressBar1;

	// JFormDesigner - End of variables declaration //GEN-END:variables

	public static void main(String[] args) {
		CheckShpFrame checkFrame = new CheckShpFrame();
		checkFrame.setVisible(true);
	}
}
