/*
 * Created by JFormDesigner on Tue Jul 31 11:14:48 CST 2012
 */

package cn.gls.database.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import cn.gls.data.Place;
import cn.gls.database.LoadApplicationContext;
import cn.gls.database.component.*;

import org.geotools.feature.FeatureCollection;
import org.jdesktop.layout.GroupLayout;

import cn.gls.database.postgis.operator.assist.GradeTableProcessing;
import cn.gls.database.util.FeaturePreProcessing;
import cn.gls.database.util.ShpUtils;
import cn.gls.database.util.UIUtils;

import org.jdesktop.layout.LayoutStyle;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * @author daniel zhang
 */
public class PlaceImport extends JFrame {
	public PlaceImport() {
		initComponents();
	}

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private void button1ActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(UIUtils.filter);
		int restatus = fileChooser.showOpenDialog(this);
		if (restatus == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getPath();
			textField1.setText(filePath);
		}
		// 获得所有列
		fileds = ShpUtils.getColumnNames(filePath);
	}

	private void rIATextField1FocusGained(FocusEvent e) {
		rIATextField1.setHistory(fileds);
	}

	private void rIATextField2FocusGained(FocusEvent e) {
		rIATextField2.setHistory(fileds);
	}
	private GradeTableProcessing gradeProcessing;
	private FeaturePreProcessing featureProcessing;
	/**
	 * 导入地名词
	 * 
	 * @param e
	 */
	private void button2ActionPerformed(ActionEvent e) {
		//开始导入地名词
		if(filePath==null||rIATextField1.getText()==null||rIATextField2.getText()==null||textField3.getText()==null){
			JOptionPane.showMessageDialog(null, "你必须填写必要的信息！");
			return ;
		}
		if(textField2.getText()==null&&rIATextField3.getText()==null){
			JOptionPane.showMessageDialog(null,"城市字段或城市名必选其一");
			return;
		}
		LoadApplicationContext appContext=LoadApplicationContext.getInstance();
		gradeProcessing = (GradeTableProcessing) appContext
				.getBean("gradeTableProcessing");
		featureProcessing = (FeaturePreProcessing) appContext.getBean("featureProcessing");
		int level=Integer.valueOf(textField3.getText());
		String isunionstr=checkBox1.isSelected()==true?checkBox1.getText():checkBox2.getText();
		boolean isunion=true;
		if(!"是".equalsIgnoreCase(isunionstr))
			isunion=false;
		String removeStr=checkBox3.isSelected()==true?checkBox3.getText():checkBox4.getText();
		boolean removesuffix=true;
		if(!"是".equalsIgnoreCase(removeStr))
			removesuffix=false;
	     int count=	insertGradeToTable(filePath,rIATextField1.getText().toString(),level,rIATextField2.getText(),null,textField2.getText(),rIATextField3.getText(),isunion,removesuffix);
         JOptionPane.showMessageDialog(null, "成功导入要素数："+count);
	}
	
	
	/**
	 * 插入等级地名词
	 * 
	 * @Param AssistDataTableProcessingTest
	 * @Description TODO
	 * @return int
	 * @param shpName
	 * @param fieldName
	 * @param placeLevel
	 * @param placeType
	 * @param cityName
	 * @param cityFieldName
	 * @return
	 */
	private int insertGradeToTable(String shpName, String fieldName,
			int placeLevel, String placeType, String cityCodeFieldName,
			String cityName, String cityFieldName, boolean isunion,boolean isRemoveSuffix) {
		FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = null;
		if (isunion) {
			featureCollection = featureProcessing.unionSimpleFeatures(
					cn.gls.database.shp.util.ShpUtils.getFeatureSourceByShp(shpName), fieldName, null,
					null);
		} else {
			featureCollection = cn.gls.database.shp.util.ShpUtils.readShpfile(shpName);
		}
		Set<Place> places = gradeProcessing.clearPlace(featureCollection,
				fieldName, placeLevel, placeType, cityCodeFieldName, cityName,
				cityFieldName,isRemoveSuffix);
		int count = gradeProcessing.insertGradeData(places);
	
		return count;
	}
	
	

	private void textField3FocusGained(FocusEvent e) {
		// TODO add your code here
	}

	private void checkBox2ActionPerformed(ActionEvent e) {
		if(!checkBox2.isSelected()){
			checkBox2.setSelected(true);
			checkBox1.setSelected(false);
		}
	
	}

	private void checkBox4ActionPerformed(ActionEvent e) {
		if(!checkBox4.isSelected()){
			checkBox4.setSelected(true);
			checkBox3.setSelected(false);
		}
	}

	private void checkBox1ActionPerformed(ActionEvent e) {
		if(!checkBox1.isSelected())
		{
			checkBox1.setSelected(true);
			checkBox2.setSelected(false);
		}
	}

	private void checkBox3ActionPerformed(ActionEvent e) {
		if(!checkBox3.isSelected()){
			checkBox3.setSelected(true);
			checkBox4.setSelected(false);
		}
	}

	private void rIATextField3ActionPerformed(ActionEvent e) {
		rIATextField3.setHistory(fileds);
	}

	private List<String> fileds = new ArrayList<String>();

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - daniel zhang
		GLSPlace = new JFrame();
		title = new JLabel();
		label1 = new JLabel();
		textField1 = new JTextField();
		button1 = new JButton();
		label2 = new JLabel();
		label3 = new JLabel();
		textField3 = new JTextField();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		checkBox1 = new JCheckBox();
		checkBox2 = new JCheckBox();
		label7 = new JLabel();
		checkBox3 = new JCheckBox();
		checkBox4 = new JCheckBox();
		button2 = new JButton();
		button3 = new JButton();
		rIATextField1 = new JRIATextField();
		rIATextField2 = new JRIATextField();
		progressBar1 = new JProgressBar();
		label8 = new JLabel();
		textField2 = new JTextField();
		rIATextField3 = new JRIATextField();

		//======== GLSPlace ========
		{
			GLSPlace.setMinimumSize(new Dimension(400, 500));
			GLSPlace.setBackground(Color.lightGray);
			GLSPlace.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 26));
			GLSPlace.setTitle("GLS");
			GLSPlace.setResizable(false);
			Container GLSPlaceContentPane = GLSPlace.getContentPane();

			//---- title ----
			title.setText("\u5730\u540d\u8bcd\u5bfc\u5165");
			title.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 26));
			title.setHorizontalAlignment(SwingConstants.CENTER);

			//---- label1 ----
			label1.setText("*\u9009\u62e9\u6587\u4ef6\uff1a");
			label1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			label1.setHorizontalAlignment(SwingConstants.CENTER);

			//---- button1 ----
			button1.setText("....");
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			//---- label2 ----
			label2.setText("*\u5730\u540d\u5b57\u6bb5\uff1a");
			label2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			label2.setHorizontalAlignment(SwingConstants.CENTER);

			//---- label3 ----
			label3.setText("*\u5730\u540d\u7ea7\u522b\uff1a");
			label3.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			label3.setHorizontalAlignment(SwingConstants.CENTER);

			//---- textField3 ----
			textField3.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					textField3FocusGained(e);
				}
			});

			//---- label4 ----
			label4.setText("*\u5730\u540d\u7c7b\u578b\uff1a");
			label4.setHorizontalAlignment(SwingConstants.CENTER);
			label4.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));

			//---- label5 ----
			label5.setText("\u57ce\u5e02\u5b57\u6bb5\u540d\uff1a");
			label5.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			label5.setHorizontalAlignment(SwingConstants.LEFT);

			//---- label6 ----
			label6.setText("\u5408\u5e76\u5730\u540d\uff1a");
			label6.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			label6.setHorizontalAlignment(SwingConstants.LEFT);

			//---- checkBox1 ----
			checkBox1.setText("\u662f");
			checkBox1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			checkBox1.setSelected(true);
			checkBox1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkBox1ActionPerformed(e);
				}
			});

			//---- checkBox2 ----
			checkBox2.setText("\u5426");
			checkBox2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			checkBox2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkBox2ActionPerformed(e);
				}
			});

			//---- label7 ----
			label7.setText("\u5220\u9664\u540e\u7f00\uff1a");
			label7.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));

			//---- checkBox3 ----
			checkBox3.setText("\u662f");
			checkBox3.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			checkBox3.setSelected(true);
			checkBox3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkBox3ActionPerformed(e);
				}
			});

			//---- checkBox4 ----
			checkBox4.setText("\u5426");
			checkBox4.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));
			checkBox4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkBox4ActionPerformed(e);
				}
			});

			//---- button2 ----
			button2.setText("\u786e\u5b9a");
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button2ActionPerformed(e);
				}
			});

			//---- button3 ----
			button3.setText("\u53d6\u6d88");

			//---- rIATextField1 ----
			rIATextField1.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField1FocusGained(e);
				}
			});

			//---- rIATextField2 ----
			rIATextField2.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField2FocusGained(e);
				}
			});

			//---- label8 ----
			label8.setText("\u57ce\u5e02\u540d\u79f0\uff1a");
			label8.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 24));

			//---- rIATextField3 ----
			rIATextField3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rIATextField3ActionPerformed(e);
				}
			});

			GroupLayout GLSPlaceContentPaneLayout = new GroupLayout(GLSPlaceContentPane);
			GLSPlaceContentPane.setLayout(GLSPlaceContentPaneLayout);
			GLSPlaceContentPaneLayout.setHorizontalGroup(
				GLSPlaceContentPaneLayout.createParallelGroup()
					.add(GLSPlaceContentPaneLayout.createSequentialGroup()
						.add(GLSPlaceContentPaneLayout.createParallelGroup()
							.add(GLSPlaceContentPaneLayout.createSequentialGroup()
								.add(21, 21, 21)
								.add(GLSPlaceContentPaneLayout.createParallelGroup()
									.add(label1, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
									.add(label2, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
								.add(65, 65, 65)
								.add(GLSPlaceContentPaneLayout.createParallelGroup()
									.add(textField1, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE)
									.add(rIATextField1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(LayoutStyle.RELATED, 51, Short.MAX_VALUE)
								.add(button1))
							.add(GLSPlaceContentPaneLayout.createSequentialGroup()
								.add(GLSPlaceContentPaneLayout.createParallelGroup()
									.add(GLSPlaceContentPaneLayout.createSequentialGroup()
										.add(121, 121, 121)
										.add(title, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE))
									.add(GLSPlaceContentPaneLayout.createSequentialGroup()
										.add(21, 21, 21)
										.add(label3, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
										.add(65, 65, 65)
										.add(textField3, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE))
									.add(GLSPlaceContentPaneLayout.createSequentialGroup()
										.add(GLSPlaceContentPaneLayout.createParallelGroup()
											.add(GLSPlaceContentPaneLayout.createSequentialGroup()
												.add(21, 21, 21)
												.add(label4, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
											.add(GLSPlaceContentPaneLayout.createSequentialGroup()
												.add(29, 29, 29)
												.add(GLSPlaceContentPaneLayout.createParallelGroup()
													.add(label5)
													.add(label8, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))))
										.add(57, 57, 57)
										.add(GLSPlaceContentPaneLayout.createParallelGroup(GroupLayout.LEADING, false)
											.add(rIATextField2, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
											.add(textField2, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
											.add(rIATextField3, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))))
								.add(0, 122, Short.MAX_VALUE)))
						.add(22, 22, 22))
					.add(GroupLayout.TRAILING, GLSPlaceContentPaneLayout.createSequentialGroup()
						.add(0, 381, Short.MAX_VALUE)
						.add(button2, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.add(64, 64, 64)
						.add(button3, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.add(30, 30, 30))
					.add(GLSPlaceContentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.add(GLSPlaceContentPaneLayout.createParallelGroup()
							.add(progressBar1, GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
							.add(GLSPlaceContentPaneLayout.createSequentialGroup()
								.add(GLSPlaceContentPaneLayout.createParallelGroup()
									.add(GLSPlaceContentPaneLayout.createSequentialGroup()
										.add(label6, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
										.add(83, 83, 83)
										.add(checkBox1)
										.add(55, 55, 55)
										.add(checkBox2))
									.add(GLSPlaceContentPaneLayout.createSequentialGroup()
										.add(label7, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
										.add(83, 83, 83)
										.add(checkBox3)
										.add(55, 55, 55)
										.add(checkBox4)))
								.add(0, 347, Short.MAX_VALUE)))
						.addContainerGap())
			);
			GLSPlaceContentPaneLayout.setVerticalGroup(
				GLSPlaceContentPaneLayout.createParallelGroup()
					.add(GLSPlaceContentPaneLayout.createSequentialGroup()
						.add(title)
						.add(47, 47, 47)
						.add(GLSPlaceContentPaneLayout.createParallelGroup()
							.add(label1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.add(GLSPlaceContentPaneLayout.createSequentialGroup()
								.add(4, 4, 4)
								.add(textField1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
							.add(GLSPlaceContentPaneLayout.createSequentialGroup()
								.add(6, 6, 6)
								.add(button1)))
						.add(36, 36, 36)
						.add(GLSPlaceContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label2)
							.add(rIATextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(63, 63, 63)
						.add(GLSPlaceContentPaneLayout.createParallelGroup()
							.add(label3)
							.add(GLSPlaceContentPaneLayout.createSequentialGroup()
								.add(4, 4, 4)
								.add(textField3, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
						.add(59, 59, 59)
						.add(GLSPlaceContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label4)
							.add(rIATextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(57, 57, 57)
						.add(GLSPlaceContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label5)
							.add(rIATextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(65, 65, 65)
						.add(GLSPlaceContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label8)
							.add(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(83, 83, 83)
						.add(GLSPlaceContentPaneLayout.createParallelGroup()
							.add(label6, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.add(checkBox1)
							.add(checkBox2))
						.add(31, 31, 31)
						.add(GLSPlaceContentPaneLayout.createParallelGroup()
							.add(label7, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.add(checkBox3)
							.add(checkBox4))
						.add(18, 18, 18)
						.add(GLSPlaceContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(button2)
							.add(button3))
						.add(47, 47, 47)
						.add(progressBar1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(164, Short.MAX_VALUE))
			);
			GLSPlace.pack();
			GLSPlace.setLocationRelativeTo(GLSPlace.getOwner());
		}
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - daniel zhang
	private JFrame GLSPlace;
	private JLabel title;
	private JLabel label1;
	private JTextField textField1;
	private JButton button1;
	private JLabel label2;
	private JLabel label3;
	private JTextField textField3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JCheckBox checkBox1;
	private JCheckBox checkBox2;
	private JLabel label7;
	private JCheckBox checkBox3;
	private JCheckBox checkBox4;
	private JButton button2;
	private JButton button3;
	private JRIATextField rIATextField1;
	private JRIATextField rIATextField2;
	private JProgressBar progressBar1;
	private JLabel label8;
	private JTextField textField2;
	private JRIATextField rIATextField3;
	// JFormDesigner - End of variables declaration //GEN-END:variables

	public static void main(String[] args) {
		PlaceImport place = new PlaceImport();
		place.GLSPlace.setVisible(true);
	}
}
