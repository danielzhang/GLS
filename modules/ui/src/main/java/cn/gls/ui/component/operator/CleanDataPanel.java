package cn.gls.ui.component.operator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.geotools.data.memory.MemoryFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.shp.util.ShpUtils;
import cn.gls.database.util.FeaturePreProcessing;
import cn.gls.database.util.FeatureUtils;
import cn.gls.ui.component.ImageButton;
import cn.gls.ui.component.MainPanel;
import cn.gls.ui.dao.ConfigDao;
import cn.gls.ui.frame.MainFrame;
import cn.gls.util.UIUtils;

/**
 * @date 2012-7-30
 * @author "Daniel Zhang"
 * @update 2012-7-30
 * @description 清理数据
 */
public class CleanDataPanel extends MainPanel {

	private static final long serialVersionUID = 1L;

	public CleanDataPanel(String filePath) {
		super();
		this.filePath = filePath;
		initComponents();
		init(filePath);
	}

	// 字体大小
	private static final Font descFont = ConfigDao.instance().getConfig()
			.getDescFont();
	private static final String cleanDescribe = ConfigDao.instance()
			.getConfig().getCleanDescribe();

	public CleanDataPanel() {
		this(null);
	}

	/**
	 * 初始化函数
	 * 
	 * @param filePath
	 */
	public void init(String filePath) {
		if (filePath == null || "".equalsIgnoreCase(filePath))
			return;
		textField1.setText(filePath);
		// 所有的列名放到List中去
		java.util.List<String> columns = cn.gls.util.ShpUtils
				.getColumnNames(filePath);
		int l = columns.size();
		for (int i = 0; i < l; i++) {
			comboBox1.addItem(columns.get(i));
		}
		// 填充Table
		featureCollection = ShpUtils.readShpfile(filePath);
		Object[][] tables = cn.gls.util.ShpUtils.getTableData(
				featureCollection, columns);
		DefaultTableModel model=new DefaultTableModel(tables, columns.toArray());
		table1.setModel(model);
		scrollPane1.setViewportView(table1);
		progressBar1.setVisible(false);
	}

	// 文件目录
	private String filePath;
	// 区域文件
	private String quyufilePath;
	// 最终的featureCollection
	private FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 选择需要导入的文件
	 * 
	 * @param e
	 */
	private void button1ActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();

		int returnval = fileChooser.showOpenDialog(null);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			//
			filePath = fileChooser.getSelectedFile().getPath();
			init(filePath);
		}
	}

	/**
	 * 清空所有空值项
	 * 
	 * @param e
	 */
	private void button4ActionPerformed(ActionEvent e) {
		thread = new CleanThread(1);
		thread.start();
	}

	private CleanThread thread;

	/**
	 * @Param CleanShpFrame
	 * @Description 刪除重复的项
	 * @return void
	 * @param e
	 */
	private void button5ActionPerformed(ActionEvent e) {

		thread = new CleanThread(0);
		thread.start();
	}

	/**
	 * @Description 选择区域文件
	 * @return void
	 * @param e
	 */
	private void button6ActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();
		int returnval = fileChooser.showOpenDialog(null);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			quyufilePath = fileChooser.getSelectedFile().getPath();
			textField2.setText(quyufilePath);
			java.util.List<String> columns = cn.gls.util.ShpUtils
					.getColumnNames(quyufilePath);
			int l = columns.size();
			for (int i = 0; i < l; i++) {
				comboBox2.addItem(columns.get(i));
			}
		}
	}

	/**
	 * 进入地名导入界面
	 * 
	 * @param e
	 */
	private void button2ActionPerformed(ActionEvent e) {
		MainPanel mainPanel = MainPanel.instance().showPlaceImport(filePath,featureCollection);
		MainFrame.instance().getContentPane()
				.remove(MainFrame.instance().getContentPane().getComponent(2));
		MainFrame.instance().getContentPane()
				.add(mainPanel, BorderLayout.CENTER);
		// 这个很重要
		MainFrame.instance().setVisible(true);
	}

	/**
	 * 取消按钮
	 * 
	 * @param e
	 */
	@SuppressWarnings("deprecation")
	private void button3ActionPerformed(ActionEvent e) {
		textField1.setText("");
		textField2.setText("");
		comboBox1.removeAllItems();
		comboBox2.removeAllItems();
		if (featureCollection != null)
			featureCollection.clear();
		if (table1.getRowCount() != 0)
			UIUtils.clearTable(table1);
		if (thread != null)
			thread.stop();
	}

	private void initComponents() {

		panel1 = this;
		label1 = new JLabel();
		textField1 = new JTextField();
		button1 = new ImageButton("chooser");
		comboBox1 = new JComboBox();
		label2 = new JLabel();
		textField2 = new JTextField();
		button6 = new ImageButton("chooser");
		comboBox2 = new JComboBox();
		button4 = new ImageButton("cleannull");
		button5 = new ImageButton("chongfu");
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		button3 = new ImageButton("cancel");
		button2 = new ImageButton("dataimport");
		progressBar1 = new JProgressBar();
		label3 = new JLabel();
		progressBar1.setVisible(false);

		// ======== panel1 ========
		{
			// ---- label1 ----
			label1.setText("\u9009\u62e9\u6587\u4ef6\uff1a");
			label1.setFont(font);

			// ---- button1 ----
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			// ---- label2 ----
			label2.setText("\u9009\u62e9\u533a\u57df\u6587\u4ef6\uff1a");
			label2.setFont(font);

			// ---- button6 ----

			button6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button6ActionPerformed(e);
				}
			});

			// ---- button4 ----

			button4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button4ActionPerformed(e);
				}
			});

			// ---- button5 ----

			button5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button5ActionPerformed(e);
				}
			});
			// ----TextField1----
			textField1.setFont(textFont);
			textField2.setFont(textFont);
			// ======== scrollPane1 ========
			{

				// ---- table1 ----
				table1.setFont(font);
				table1.setBackground(new Color(255, 200, 100));
				scrollPane1.setViewportView(table1);
				scrollPane1.setBackground(new Color(255, 200, 100));
			}

			// ---- button3 ----
			button3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button3ActionPerformed(e);
				}
			});

			// ---- button2 ----
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button2ActionPerformed(e);
				}
			});
			// --------TextField1-----

			// ---- label3 ----
			label3.setText("<html>" + "\u4f7f\u7528\u8bf4\u660e\uff1a"
					+ cleanDescribe + "</html>");
			label3.setVerticalTextPosition(SwingConstants.TOP);
			label3.setFont(descFont);
			label3.setBorder(BorderFactory.createLineBorder(Color.red));
			label3.setVerticalAlignment(SwingConstants.TOP);

			label3.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setAutocreateContainerGaps(true);
			panel1Layout.setAutocreateGaps(true);
			panel1Layout
					.setHorizontalGroup(panel1Layout
							.createParallelGroup()
							.add(panel1Layout
									.createSequentialGroup()
									.add(panel1Layout
											.createParallelGroup()
											.add(panel1Layout
													.createSequentialGroup()
													.add((int) (width * 0.035),
															(int) (width * 0.035),
															(int) (width * 0.035))
													.add(label2,
															GroupLayout.PREFERRED_SIZE,
															(int) (width * 0.14),
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
															LayoutStyle.RELATED)
													.add(textField2,
															GroupLayout.PREFERRED_SIZE,
															(int) (width * 0.2),
															GroupLayout.PREFERRED_SIZE)
													.add((int) (width * 0.025),
															(int) (width * 0.025),
															(int) (width * 0.025))
													.add(button6,
															GroupLayout.PREFERRED_SIZE,
															110,
															GroupLayout.PREFERRED_SIZE)
													.add((int) (width * 0.025),
															(int) (width * 0.025),
															(int) (width * 0.025))
													.add(comboBox2,
															GroupLayout.PREFERRED_SIZE,
															(int) (width * 0.1),
															GroupLayout.PREFERRED_SIZE)
													.add((int) (width * 0.025),
															(int) (width * 0.025),
															(int) (width * 0.025))
													.add(button5,
															GroupLayout.PREFERRED_SIZE,
															110,
															GroupLayout.PREFERRED_SIZE)
													.add(0, 0, Short.MAX_VALUE))
											.add(panel1Layout
													.createSequentialGroup()
													.add((int) (width * 0.035),
															(int) (width * 0.035),
															(int) (width * 0.035))
													.add(label1,
															GroupLayout.PREFERRED_SIZE,
															(int) (width * 0.14),
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
															LayoutStyle.RELATED)
													.add(textField1,
															GroupLayout.PREFERRED_SIZE,
															(int) (width * 0.2),
															GroupLayout.PREFERRED_SIZE)
													.add((int) (width * 0.025),
															(int) (width * 0.025),
															(int) (width * 0.025))
													.add(button1,
															GroupLayout.PREFERRED_SIZE,
															110,
															GroupLayout.PREFERRED_SIZE)
													.add((int) (width * 0.025),
															(int) (width * 0.025),
															(int) (width * 0.025))
													.add(comboBox1,
															GroupLayout.PREFERRED_SIZE,
															(int) (width * 0.1),
															GroupLayout.PREFERRED_SIZE)
													.add((int) (width * 0.025),
															(int) (width * 0.025),
															(int) (width * 0.025))
													.add(button4,
															GroupLayout.PREFERRED_SIZE,
															110,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
															LayoutStyle.UNRELATED)
													.add(label3,
															0,
															(int) (width * 0.3),
															(int) (width * 0.4))
													.addPreferredGap(
															LayoutStyle.RELATED,
															26, Short.MAX_VALUE))
											.add(panel1Layout
													.createSequentialGroup()
													.add(panel1Layout
															.createParallelGroup()
															.add(GroupLayout.CENTER,
																	scrollPane1,GroupLayout.DEFAULT_SIZE,(int)(width*0.87),Short.MAX_VALUE)
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(100,
																			(int) (width * 0.6),
																			(int) (width * 0.7))
																	.add(button2,
																			GroupLayout.PREFERRED_SIZE,
																			110,
																			GroupLayout.PREFERRED_SIZE)
																	.add((int) (width * 0.1),
																			(int) (width * 0.1),
																			(int) (width * 0.1))
																	.add(button3,
																			GroupLayout.PREFERRED_SIZE,
																			110,
																			GroupLayout.PREFERRED_SIZE)
																	.add((int) (width * 0.025),
																			(int) (width * 0.025),
																			(int) (width * 0.025)))
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(progressBar1,
																			GroupLayout.DEFAULT_SIZE,
																			(int) (width * 0.5),
																			(int) (width * 0.55)))

													))).addContainerGap()));
			panel1Layout
					.setVerticalGroup(panel1Layout
							.createParallelGroup()
							.add(panel1Layout
									.createSequentialGroup()
									.add(panel1Layout
											.createParallelGroup()
											.add(panel1Layout
													.createSequentialGroup()
													.add((int) (height * .045),
															(int) (height * .045),
															(int) (height * .045))
													.add(panel1Layout
															.createParallelGroup(
																	GroupLayout.LEADING)
															.add(label1,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)
															.add(textField1,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)
															.add(comboBox1,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)
															.add(button1,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)
															.add(button4,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE))
													.add((int) (height * .045),
															(int) (height * .045),
															(int) (height * .045))
													.add(panel1Layout
															.createParallelGroup(
																	GroupLayout.BASELINE)
															.add(label2,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)
															.add(textField2,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)
															.add(button6,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)
															.add(comboBox2,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)
															.add(button5,
																	GroupLayout.PREFERRED_SIZE,
																	45,
																	GroupLayout.PREFERRED_SIZE)))
											.add(GroupLayout.TRAILING,
													panel1Layout
															.createSequentialGroup()
															.addContainerGap(
																	(int) (height * 0.01),
																	(int) (height * 0.03))
															.add(label3,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (height * 0.23),
																	GroupLayout.PREFERRED_SIZE)))
									.add(30, 30, 30)
									.add(scrollPane1,
											GroupLayout.DEFAULT_SIZE,
											(int) (height * 0.55),
											Short.MAX_VALUE)
									.add(30, 30, 30)
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(button2,
													GroupLayout.PREFERRED_SIZE,
													45,
													GroupLayout.PREFERRED_SIZE)
											.add(button3,
													GroupLayout.PREFERRED_SIZE,
													45,
													GroupLayout.PREFERRED_SIZE)
											.add(progressBar1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE))

							));
		}

	}

	private JLabel label1;
	private JTextField textField1;
	private ImageButton button1;
	private JComboBox comboBox1;
	private JLabel label2;
	private JTextField textField2;
	private ImageButton button6;
	private JComboBox comboBox2;
	private ImageButton button4;
	private ImageButton button5;
	private JScrollPane scrollPane1;
	private JTable table1;
	private ImageButton button3;
	private ImageButton button2;
	private JProgressBar progressBar1;
	private JLabel label3;

	private CleanDataPanel panel1;

	public class CleanThread extends Thread {
		private int flag = 0;

		public CleanThread(int flag) {
			super();
			this.flag = flag;
		}

		@Override
		public void run() {
			if (flag == 0) {
				if (comboBox1.getSelectedItem() == null
						|| textField1.getText() == null) {
					JOptionPane.showMessageDialog(null, "请选择要求合并的要素文件！");
					return;
				}
				if (comboBox2.getSelectedItem() == null
						|| textField2.getText() == null) {
					JOptionPane.showMessageDialog(null, "请选择要求合并的区域条件！");
					return;
				}
				progressBar1.setVisible(true);
				progressBar1.setIndeterminate(true);
				LoadApplicationContext applicationContext = LoadApplicationContext
						.getInstance();
				FeaturePreProcessing processing = (FeaturePreProcessing) applicationContext
						.getBean("featureProcessing");
				featureCollection = processing.unionSimpleFeatures(ShpUtils
						.getFeatureSourceByShp(filePath), comboBox1
						.getSelectedItem().toString(), ShpUtils
						.getFeatureSourceByShp(quyufilePath), comboBox2
						.getSelectedItem().toString());
				java.util.List<String> columns = cn.gls.util.ShpUtils
						.getColumnNames(filePath);
				Object[][] tables = cn.gls.util.ShpUtils.getTableData(
						featureCollection, columns);
				DefaultTableModel model=new DefaultTableModel(tables, columns.toArray());
				table1.setModel(model);
				table1.getModel().addTableModelListener(new TableModelListener() {
					
					public void tableChanged(TableModelEvent e) {
						changeTable(e);
					}
				});
				scrollPane1.setViewportView(table1);
				progressBar1.setIndeterminate(false);
				progressBar1.setVisible(false);
				JOptionPane.showMessageDialog(null, "合并重复项完成。");
			} else {
				if (comboBox1.getSelectedItem() == null
						|| textField1.getText() == null) {
					JOptionPane.showMessageDialog(null, "请选择要求清除的要素文件!");
					return;
				}
				//
				// 首先查看是否有选择的字段名称。
				FeatureCollection<SimpleFeatureType, SimpleFeature> ofeatureCollection = ShpUtils
						.readShpfile(filePath);
				featureCollection = FeatureUtils.clearSimpleFeatures(
						ofeatureCollection, (String)comboBox1.getSelectedItem());
				java.util.List<String> columns = cn.gls.util.ShpUtils
						.getColumnNames(filePath);
				Object[][] tables = cn.gls.util.ShpUtils.getTableData(
						featureCollection, columns);
				DefaultTableModel model=new DefaultTableModel(tables, columns.toArray());
				table1.setModel(model);
				scrollPane1.setViewportView(table1);
			}
		}
		private void changeTable(TableModelEvent e){
			if(featureCollection==null&&!(featureCollection instanceof MemoryFeatureCollection)){
				return;
			}
			int col = e.getColumn();
			int row = e.getFirstRow();

			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			model.getColumnName(col);
			Object[] os = (Object[]) model.getDataVector().toArray();
			@SuppressWarnings("unchecked")
			Object[] changeFeature = ((Vector<Object>) os[row]).toArray();
			String featureName = (String) changeFeature[col];
			String fieldName = (String) UIUtils.getColumns(featureCollection)[col];
			Object[] objs = ((MemoryFeatureCollection) featureCollection).toArray();
			SimpleFeature feature = (SimpleFeature) objs[row];
			feature.setAttribute(fieldName, featureName);
		}
	}

}
