/*
 * Created by JFormDesigner on Mon Jul 30 11:23:33 CST 2012
 */

package cn.gls.database.ui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Vector;
import javax.swing.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.filechooser.FileFilter;

import org.geotools.feature.FeatureCollection;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.shp.util.ShpUtils;
import cn.gls.database.util.FeaturePreProcessing;
import cn.gls.database.util.FeatureUtils;
import cn.gls.database.util.UIUtils;

/**
 * @date 2012-7-30
 * @author "Daniel Zhang"
 * @update 2012-7-30
 * @description TODO
 */
public class CleanShpFrame extends JFrame {
	public CleanShpFrame() {
		initComponents();
	}

	// 文件目录
	private String filePath;
	// 区域文件
	private String quyufilePath;
	// 最终的featureCollection
	private FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection;

	/**
	 * 选择文件
	 * 
	 * @param e
	 */
	private void button1ActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(UIUtils.filter);
		int returnval = fileChooser.showOpenDialog(this);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			//
			filePath = fileChooser.getSelectedFile().getPath();
			textField1.setText(filePath);
			// 所有的列名放到List中去
			java.util.List<String> columns = cn.gls.database.util.ShpUtils
					.getColumnNames(filePath);
			int l = columns.size();
			for (int i = 0; i < l; i++) {
				comboBox1.addItem(columns.get(i));
			}
		}
	}

	/**
	 * 清空所有空值项
	 * 
	 * @param e
	 */
	private void button4ActionPerformed(ActionEvent e) {
		if (comboBox1.getSelectedItem() == null || textField1.getText() == null) {
			JOptionPane.showMessageDialog(null, "请选择要求清除的要素文件!");
			return;
		}
		//
		// 首先查看是否有选择的字段名称。
		FeatureCollection<SimpleFeatureType, SimpleFeature> ofeatureCollection = ShpUtils
				.readShpfile(filePath);

		featureCollection = FeatureUtils.clearSimpleFeatures(
				ofeatureCollection, comboBox1.getSelectedItem().toString());
		java.util.List<String> columns = cn.gls.database.util.ShpUtils
				.getColumnNames(filePath);
		Object[][] tables = cn.gls.database.util.ShpUtils.getTableData(
				featureCollection, columns);
		if (table1 != null)
			table1.removeAll();
		table1 = new JTable(tables, columns.toArray());
		scrollPane1.setViewportView(table1);
	}

	/**
	 * @Param CleanShpFrame
	 * @Description 刪除重复的项
	 * @return void
	 * @param e
	 */
	private void button5ActionPerformed(ActionEvent e) {
		if (comboBox1.getSelectedItem() == null || textField1.getText() == null) {
			JOptionPane.showMessageDialog(null, "请选择要求合并的要素文件！");
			return;
		}
		if (comboBox2.getSelectedItem() == null || textField2.getText() == null) {
			JOptionPane.showMessageDialog(null, "请选择要求合并的区域条件！");
			return;
		}

		LoadApplicationContext applicationContext = LoadApplicationContext
				.getInstance();
		FeaturePreProcessing processing = (FeaturePreProcessing) applicationContext
				.getBean("featureProcessing");
		featureCollection = processing.unionSimpleFeatures(ShpUtils
				.getFeatureSourceByShp(filePath), comboBox1.getSelectedItem()
				.toString(), ShpUtils.getFeatureSourceByShp(quyufilePath),
				comboBox2.getSelectedItem().toString());
		java.util.List<String> columns = cn.gls.database.util.ShpUtils
				.getColumnNames(filePath);
		Object[][] tables = cn.gls.database.util.ShpUtils.getTableData(
				featureCollection, columns);
		if (table1 != null)
			table1.removeAll();
		table1 = new JTable(tables, columns.toArray());
		scrollPane1.setViewportView(table1);
	}

	/**
	 * @Description 选择区域文件
	 * @return void
	 * @param e
	 */
	private void button6ActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(UIUtils.filter);
		int returnval = fileChooser.showOpenDialog(this);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			quyufilePath = fileChooser.getSelectedFile().getPath();
			textField2.setText(quyufilePath);
			java.util.List<String> columns = cn.gls.database.util.ShpUtils
					.getColumnNames(quyufilePath);
			int l = columns.size();
			for (int i = 0; i < l; i++) {
				comboBox2.addItem(columns.get(i));
			}
		}
	}

	/**
	 * 进入数据导入界面
	 * 
	 * @param e
	 */
	private void button2ActionPerformed(ActionEvent e) {
		AddressImportFrame addressFrame = new AddressImportFrame();
		addressFrame.addressFrame.setVisible(true);
		// 传值。
		if (featureCollection != null)
			addressFrame.setFeatureCollection(featureCollection);
		// 填充fields
		List<String> fields = cn.gls.database.util.ShpUtils
				.getColumnNames(filePath);
		addressFrame.setFields(fields);
		addressFrame.textField1.setText(filePath);
		addressFrame.textField3.setText(quyufilePath);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - daniel zhang
		cleanFrame = new JFrame();
		label1 = new JLabel();
		textField1 = new JTextField();
		button1 = new JButton();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		progressBar1 = new JProgressBar();
		button4 = new JButton();
		button5 = new JButton();
		label2 = new JLabel();
		textField2 = new JTextField();
		button6 = new JButton();
		comboBox1 = new JComboBox();
		comboBox2 = new JComboBox();
		button2 = new JButton();

		// ======== cleanFrame ========
		{
			cleanFrame.setTitle("\u6570\u636e\u6e05\u7406");
			Container cleanFrameContentPane = cleanFrame.getContentPane();

			// ---- label1 ----
			label1.setText("\u9009\u62e9\u6587\u4ef6\uff1a");
			label1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));

			// ---- button1 ----
			button1.setText("...");
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
					button1ActionPerformed(e);
				}
			});

			// ======== scrollPane1 ========
			{

				// ---- table1 ----
				table1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));
				scrollPane1.setViewportView(table1);
			}

			// ---- button4 ----
			button4.setText("\u6e05\u9664\u7a7a\u503c\u9879");
			button4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
					button1ActionPerformed(e);
					button4ActionPerformed(e);
				}
			});

			// ---- button5 ----
			button5.setText("\u5408\u5e76\u91cd\u590d\u9879");
			button5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
					button1ActionPerformed(e);
					button5ActionPerformed(e);
				}
			});

			// ---- label2 ----
			label2.setText("\u9009\u62e9\u533a\u57df\u6587\u4ef6\uff1a");
			label2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			// ---- button6 ----
			button6.setText("...");
			button6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
					button1ActionPerformed(e);
					button6ActionPerformed(e);
				}
			});

			// ---- button2 ----
			button2.setText("\u6570\u636e\u5bfc\u5165");
			button2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 22));
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button2ActionPerformed(e);
				}
			});

			GroupLayout cleanFrameContentPaneLayout = new GroupLayout(
					cleanFrameContentPane);
			cleanFrameContentPane.setLayout(cleanFrameContentPaneLayout);
			cleanFrameContentPaneLayout
					.setHorizontalGroup(cleanFrameContentPaneLayout
							.createParallelGroup()
							.add(cleanFrameContentPaneLayout
									.createSequentialGroup()
									.add(cleanFrameContentPaneLayout
											.createParallelGroup()
											.add(cleanFrameContentPaneLayout
													.createSequentialGroup()
													.add(23, 23, 23)
													.add(progressBar1,
															GroupLayout.DEFAULT_SIZE,
															GroupLayout.DEFAULT_SIZE,
															Short.MAX_VALUE))
											.add(cleanFrameContentPaneLayout
													.createSequentialGroup()
													.addContainerGap()
													.add(cleanFrameContentPaneLayout
															.createParallelGroup(
																	GroupLayout.TRAILING,
																	false)
															.add(cleanFrameContentPaneLayout
																	.createSequentialGroup()
																	.add(label1)
																	.addPreferredGap(
																			LayoutStyle.UNRELATED)
																	.add(textField1,
																			GroupLayout.PREFERRED_SIZE,
																			239,
																			GroupLayout.PREFERRED_SIZE)
																	.add(18,
																			18,
																			18)
																	.add(comboBox1,
																			GroupLayout.PREFERRED_SIZE,
																			GroupLayout.DEFAULT_SIZE,
																			GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																			LayoutStyle.UNRELATED)
																	.add(button1))
															.add(cleanFrameContentPaneLayout
																	.createSequentialGroup()
																	.add(label2)
																	.addPreferredGap(
																			LayoutStyle.RELATED)
																	.add(textField2,
																			GroupLayout.DEFAULT_SIZE,
																			237,
																			Short.MAX_VALUE)
																	.add(18,
																			18,
																			18)
																	.add(comboBox2,
																			GroupLayout.PREFERRED_SIZE,
																			GroupLayout.DEFAULT_SIZE,
																			GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																			LayoutStyle.UNRELATED)
																	.add(button6)))
													.addPreferredGap(
															LayoutStyle.RELATED)
													.add(cleanFrameContentPaneLayout
															.createParallelGroup()
															.add(button5)
															.add(button4)))
											.add(scrollPane1,
													GroupLayout.DEFAULT_SIZE,
													699, Short.MAX_VALUE))
									.addContainerGap())
							.add(GroupLayout.TRAILING,
									cleanFrameContentPaneLayout
											.createSequentialGroup()
											.add(0, 552, Short.MAX_VALUE)
											.add(button2,
													GroupLayout.PREFERRED_SIZE,
													131,
													GroupLayout.PREFERRED_SIZE)
											.add(31, 31, 31)));
			cleanFrameContentPaneLayout
					.setVerticalGroup(cleanFrameContentPaneLayout
							.createParallelGroup()
							.add(cleanFrameContentPaneLayout
									.createSequentialGroup()
									.add(35, 35, 35)
									.add(cleanFrameContentPaneLayout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label1)
											.add(button1)
											.add(button4)
											.add(comboBox1,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.add(textField1,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.add(18, 18, 18)
									.add(cleanFrameContentPaneLayout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label2)
											.add(button5)
											.add(button6)
											.add(comboBox2,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.add(textField2,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.RELATED)
									.add(scrollPane1,
											GroupLayout.PREFERRED_SIZE, 691,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.RELATED, 17,
											Short.MAX_VALUE)
									.add(button2)
									.add(27, 27, 27)
									.add(progressBar1,
											GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addContainerGap()));
			cleanFrame.pack();
			cleanFrame.setLocationRelativeTo(null);
		}
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - daniel zhang
	public JFrame cleanFrame;
	private JLabel label1;
	private JTextField textField1;
	private JButton button1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JProgressBar progressBar1;
	private JButton button4;
	private JButton button5;
	private JLabel label2;
	private JTextField textField2;
	private JButton button6;
	private JComboBox comboBox1;
	private JComboBox comboBox2;
	private JButton button2;

	// JFormDesigner - End of variables declaration //GEN-END:variables

	public static void main(String[] args) {
		CleanShpFrame cleanFrame = new CleanShpFrame();
		cleanFrame.cleanFrame.setVisible(true);
	}
}
