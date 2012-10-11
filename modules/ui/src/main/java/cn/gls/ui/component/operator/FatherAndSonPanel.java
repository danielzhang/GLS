package cn.gls.ui.component.operator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;

import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import org.geotools.feature.FeatureCollection;
import org.jdesktop.layout.GroupLayout;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.data.FatherAndSon;
import cn.gls.database.LoadApplicationContext;
import cn.gls.database.postgis.operator.assist.FatherAndSonTableProcessing;
import cn.gls.database.shp.util.ShpUtils;
import cn.gls.ui.component.ImageButton;
import cn.gls.ui.component.MainPanel;
import cn.gls.ui.dao.ConfigDao;
import cn.gls.util.UIUtils;

/**
 * 
 * @date 2012-8-13
 * @author "Daniel Zhang"
 * @update 2012-8-13
 * @description TODO
 * 
 */
public class FatherAndSonPanel extends MainPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4243599511676684651L;

	public FatherAndSonPanel() {
		initComponents();
	}

	private FeatureCollection<SimpleFeatureType, SimpleFeature> sonFeatureCollection;
	private FeatureCollection<SimpleFeatureType, SimpleFeature> fatherFeatureCollection;

	private void chooserSonFile(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();
		int l = fileChooser.showOpenDialog(this);
		if (l == JFileChooser.APPROVE_OPTION) {
			textField1.setText(fileChooser.getSelectedFile().getPath());
			// 开始选择子文件数据
			sonFeatureCollection = ShpUtils.readShpfile(textField1.getText());
			// 所有的列名放到List中去
			java.util.List<String> columns = cn.gls.util.ShpUtils
					.getColumnNames(textField1.getText());
			int s = columns.size();
			for (int i = 0; i < s; i++) {
				combox1.addItem(columns.get(i));
			}
		}

	}

	private void chooserFatherFile(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();
		int l = fileChooser.showOpenDialog(this);
		if (l == JFileChooser.APPROVE_OPTION) {
			textField2.setText(fileChooser.getSelectedFile().getPath());
			// 开始选择子文件数据
			fatherFeatureCollection = ShpUtils
					.readShpfile(textField2.getText());
			java.util.List<String> columns = cn.gls.util.ShpUtils
					.getColumnNames(textField2.getText());
			int s = columns.size();
			for (int i = 0; i < s; i++) {
				combox2.addItem(columns.get(i));
			}
		}
	}

	private FatherAndSonTableProcessing fatherAndSonOperator;

	// 产生关系
	private void createRelation(ActionEvent e) {
            thread=new FatherAndSonThread(1);
            thread.start();
	}

	private void importFatherAndSonToDB(ActionEvent e) {
		thread = new FatherAndSonThread(0);
		thread.start();

	}

	private FatherAndSonThread thread;

	/**
	 * 取消按钮
	 * 
	 * @param e
	 */
	@SuppressWarnings("deprecation")
	private void cancelOperator(ActionEvent e) {
		if(sonFeatureCollection!=null)
		sonFeatureCollection.clear();
		if(fatherFeatureCollection!=null)
		fatherFeatureCollection.clear();
		table1.setModel(new DefaultTableModel());
		textField1.setText(null);
		textField2.setText(null);
		combox1.removeAll();
		combox2.removeAll();
		cityField.setText(null);
		if (thread != null)
			thread.stop();

	}

	private void initComponents() {

		label1 = new JLabel();
		textField1 = new JTextField();
		button1 = new ImageButton("chooser");
		label2 = new JLabel();
		textField2 = new JTextField();
		button2 = new ImageButton("chooser");
		button3 = new ImageButton("relation");
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		button4 = new ImageButton("importstart");
		button5 = new ImageButton("cancel");
		label3 = new JLabel();
		combox1 = new JComboBox();
		combox2 = new JComboBox();
		cityField = new JTextField();
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		// ======== this ========
		// ---- label1 ----
		label1.setText("\u9009\u62e9\u5b50\u6587\u4ef6\uff1a");
		label1.setFont(font);

		// ---- button1 ----
		button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				chooserSonFile(e);
			}
		});
		// -------button2-----
		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				chooserFatherFile(e);
			}
		});
		// -------button3------
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				createRelation(e);
			}
		});
		// ------button4-----
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importFatherAndSonToDB(e);
			}
		});
		// -----------button5--------
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelOperator(e);
			}
		});
		// ---- label2 ----
		label2.setText("\u9009\u62e9\u7236\u6587\u4ef6:");
		label2.setFont(font);
		// ======== scrollPane1 ========
		{
			scrollPane1.setViewportView(table1);
		}
		label3.setText("<html>" + "使用说明："
				+ ConfigDao.instance().getConfig().getFatherAndSonDescribe()
				+ "</html>");
		label3.setFont(descFont);
		label3.setBorder(BorderFactory.createLineBorder(Color.red));
		label3.setVerticalAlignment(SwingConstants.TOP);

		// ------textField-----
		textField1.setFont(textFont);
		textField2.setFont(textFont);
		cityField.setFont(textFont);

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup()
				.add(layout
						.createSequentialGroup()
						.add((int) (width * 0.035), (int) (width * 0.035),
								(int) (width * 0.035))
						.add(layout
								.createParallelGroup(GroupLayout.LEADING)
								.add(layout
										.createSequentialGroup()
										.add(layout
												.createParallelGroup()
												.add(label1,
														GroupLayout.PREFERRED_SIZE,
														(int) (width * 0.12),
														GroupLayout.PREFERRED_SIZE)
												.add(label2,
														GroupLayout.PREFERRED_SIZE,
														(int) (width * 0.12),
														GroupLayout.PREFERRED_SIZE))
										.add((int) (width * 0.02),
												(int) (width * 0.02),
												(int) (width * 0.02))
										.add(layout
												.createParallelGroup()
												.add(textField1,
														GroupLayout.PREFERRED_SIZE,
														(int) (width * 0.2),
														GroupLayout.PREFERRED_SIZE)
												.add(textField2,
														GroupLayout.PREFERRED_SIZE,
														(int) (width * 0.2),
														GroupLayout.PREFERRED_SIZE))
										.add((int) (width * 0.04),
												(int) (width * 0.04),
												(int) (width * 0.04))
										.add(layout
												.createParallelGroup()
												.add(button1,
														GroupLayout.PREFERRED_SIZE,
														110,
														GroupLayout.PREFERRED_SIZE)
												.add(button2,
														GroupLayout.PREFERRED_SIZE,
														110,
														GroupLayout.PREFERRED_SIZE))
										.add((int) (width * 0.04),
												(int) (width * 0.04),
												(int) (width * 0.04))
										.add(layout
												.createParallelGroup()
												.add(combox1,
														GroupLayout.PREFERRED_SIZE,
														110,
														GroupLayout.PREFERRED_SIZE)
												.add(combox2,
														GroupLayout.PREFERRED_SIZE,
														110,
														GroupLayout.PREFERRED_SIZE))
										.add((int) (width * 0.04),
												(int) (width * 0.04),
												(int) (width * 0.04))
										.add(layout
												.createParallelGroup()
												.add(cityField,
														GroupLayout.PREFERRED_SIZE,
														110,
														GroupLayout.PREFERRED_SIZE)
												.add(button3,
														GroupLayout.PREFERRED_SIZE,
														110,
														GroupLayout.PREFERRED_SIZE))))
						.add(layout.createParallelGroup().add(label3,
								GroupLayout.PREFERRED_SIZE,
								(int) (width * 0.25),
								GroupLayout.PREFERRED_SIZE)))
				.add(GroupLayout.CENTER,
						layout.createSequentialGroup().add(scrollPane1,
								GroupLayout.DEFAULT_SIZE, (int) (width * 0.9),
								Short.MAX_VALUE))
				.add(GroupLayout.TRAILING,
						layout.createSequentialGroup()
								.add(progressBar, GroupLayout.PREFERRED_SIZE,
										(int) (width * 0.5),
										GroupLayout.PREFERRED_SIZE)
								.add(button4, GroupLayout.PREFERRED_SIZE, 110,
										GroupLayout.PREFERRED_SIZE)
								.add((int) (width * 0.1), (int) (width * 0.1),
										(int) (width * 0.1))
								.add(button5, GroupLayout.PREFERRED_SIZE, 110,
										GroupLayout.PREFERRED_SIZE)
								.add((int) (width * 0.12),
										(int) (width * 0.12),
										(int) (width * 0.12))));
		layout.setVerticalGroup(layout
				.createParallelGroup()
				.add(layout
						.createSequentialGroup()
						.add((int) (height * .045), (int) (height * .045),
								(int) (height * .045))
						.add(layout
								.createParallelGroup(GroupLayout.BASELINE,
										false)
								.add(label1, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.add(textField1, GroupLayout.PREFERRED_SIZE,
										45, GroupLayout.PREFERRED_SIZE)
								.add(button1, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.add(combox1, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.add(cityField, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE))
						.add((int) (height * 0.03), (int) (height * 0.03),
								(int) (height * 0.03))
						.add(layout
								.createParallelGroup()
								.add(label2, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.add(textField2, GroupLayout.PREFERRED_SIZE,
										45, GroupLayout.PREFERRED_SIZE)
								.add(button2, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.add(combox2, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.add(button3, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE))
						.add((int) (height * 0.03), (int) (height * 0.03),
								(int) (height * 0.03))
						.add(scrollPane1, GroupLayout.DEFAULT_SIZE,
								(int) (height * 0.55), Short.MAX_VALUE)
						.add((int) (height * 0.05), (int) (height * 0.05),
								(int) (height * 0.05))
						.add(layout
								.createParallelGroup(GroupLayout.BASELINE)
								.add(progressBar, GroupLayout.PREFERRED_SIZE,
										45, GroupLayout.PREFERRED_SIZE)
								.add(button4, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.add(button5, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE))
						.add(51, 51, 51))
				.add(layout
						.createParallelGroup()
						.add((int) (height * .045), (int) (height * .045),
								(int) (height * .045))
						.add(label3, GroupLayout.PREFERRED_SIZE,
								(int) (height * 0.18), (int) (height * 0.2)))

		);
	}

	private JProgressBar progressBar;
	private JTextField cityField;
	private JComboBox combox1;
	private JComboBox combox2;
	private JLabel label1;
	private JTextField textField1;
	private JButton button1;
	private JLabel label2;
	private JTextField textField2;
	private JButton button2;
	private JButton button3;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton button4;
	private JButton button5;
	private JLabel label3;

	public class FatherAndSonThread extends Thread {
		int flag = 0;

		public FatherAndSonThread(int flag) {
			super();
			this.flag = flag;
		}

		@Override
		public void run() {
            if(flag==0)
			
			importFatherAndSon();
            else
            	createRelation();
		}
		@SuppressWarnings("unchecked")
		private void importFatherAndSon() {
			if (table1.getRowCount() <= 0) {
				JOptionPane.showMessageDialog(null, "您还没有产生两个数据文件的父子关系");
				return;
			}
			progressBar.setVisible(true);
			progressBar.setIndeterminate(true);
			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			int length = model.getRowCount();
			Set<FatherAndSon> fatherAndSons = new HashSet<FatherAndSon>();			
			Vector<Object> vector = model.getDataVector();
			for (int i = 0; i < length; i++) {
				Vector<Object> obj =(Vector<Object>) vector.get(i);
				FatherAndSon fs = new FatherAndSon((String)obj.elementAt(1), (String)obj.elementAt(0), (Integer)obj.elementAt(2));
				fatherAndSons.add(fs);
			}
			
			if(fatherAndSonOperator==null)
				fatherAndSonOperator= (FatherAndSonTableProcessing) LoadApplicationContext.getInstance().getBean("fatherAndSonOperator");
			int count = fatherAndSonOperator.insertFatherAndSon(fatherAndSons);
			progressBar.setVisible(false);
			JOptionPane.showMessageDialog(null, "导入父子关系结束,共导入" + count + "个");
		}

		private void createRelation() {
			if (textField1.getText() == null || textField2.getText() == null
					|| sonFeatureCollection == null
					|| fatherFeatureCollection == null
					|| cityField.getText() == null || cityField.getText() == "") {
				JOptionPane.showMessageDialog(null, "请选择适当的条件再产生父子关系");
				return;
			}
			// 产生关系
			LoadApplicationContext loadApplicationContext = LoadApplicationContext
					.getInstance();
			fatherAndSonOperator = (FatherAndSonTableProcessing) loadApplicationContext
					.getBean("fatherAndSonOperator");
			progressBar.setVisible(true);
			progressBar.setIndeterminate(true);
			Set<FatherAndSon> fatherAndSons = fatherAndSonOperator
					.judgeFatherAndSon(fatherFeatureCollection,sonFeatureCollection, (String)combox2.getSelectedItem(), (String)combox1.getSelectedItem(), null, cityField.getText(),
							null);
			String[] columns = { "sonName",  "fatherName", "cityCode" };
			DefaultTableModel model = new DefaultTableModel(
					cn.gls.util.ShpUtils.getTableData(fatherAndSons.toArray(),FatherAndSon.class), columns);
			table1.setModel(model);
			scrollPane1.setViewportView(table1);
			progressBar.setVisible(false);
			JOptionPane.showMessageDialog(null, "产生关系列数为："+fatherAndSons.size());
		}
	}

}
