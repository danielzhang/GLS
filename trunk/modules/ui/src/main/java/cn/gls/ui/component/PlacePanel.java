package cn.gls.ui.component;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import cn.gls.data.Place;
import cn.gls.database.LoadApplicationContext;
import org.geotools.data.FeatureSource;
import org.geotools.data.collection.CollectionFeatureSource;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.layout.GroupLayout;
import cn.gls.database.postgis.operator.assist.GradeTableProcessing;
import cn.gls.database.util.FeaturePreProcessing;
import cn.gls.ui.dao.ConfigDao;
import cn.gls.util.UIUtils;
import org.jdesktop.layout.LayoutStyle;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * 
 * @date 2012-8-9
 * @author "Daniel Zhang"
 * @update 2012-8-9
 * @description TODO
 * 
 */
public class PlacePanel {

	private PlacePanel(String filePath) {
		super();
		initComponents();
		init(filePath);
	}

	private PlacePanel() {
		this(null);
	}

	private static PlacePanel placePanel;

	public static PlacePanel instance(String filePath) {
		if (placePanel == null)
			placePanel = new PlacePanel(filePath);
		return placePanel;
	}

	private FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection;
	private FeatureCollection<SimpleFeatureType, SimpleFeature> countyCollection;

	public void setFeatureCollection(
			FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection) {
		this.featureCollection = featureCollection;
	}

	public void init(String filePath) {
		if (filePath == null || "".equalsIgnoreCase(filePath))
			return;
		this.filePath = filePath;
		textField1.setText(filePath);
		// 获得所有列
		panel1.setBackground(new Color(255, 200, 100));
		// 为Table1赋值
		if (featureCollection == null) {
			featureCollection = cn.gls.database.shp.util.ShpUtils
					.readShpfile(filePath);
		}
		fileds = UIUtils.getColumnsList(featureCollection);
		Object[][] tables = cn.gls.util.ShpUtils.getTableData(
				featureCollection, fileds);
		DefaultTableModel model = new DefaultTableModel(tables,
				fileds.toArray());
		table1.setModel(model);
		scroollPane.setViewportView(table1);
	}

	private String filePath;

	private PlaceThread thread;
	/** 屏幕所使用的字体 */
	private Font font = ConfigDao.instance().getConfig().getFont();
	private Font textFont = ConfigDao.instance().getConfig().getTextFont();
	private static final String placeImportdescribe = ConfigDao.instance()
			.getConfig().getPlaceImportDescribe();

	private int width = (int) ConfigDao.instance().getConfig()
			.getMainPanelDimension().getWidth();
	private int height = (int) ConfigDao.instance().getConfig()
			.getMainPanelDimension().getHeight();

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private void button1ActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();
		int restatus = fileChooser.showOpenDialog(panel1);
		if (restatus == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getPath();
			init(filePath);
		}
	}

	private void rIATextField1FocusGained(FocusEvent e) {

		rIATextField1.setHistory(fileds);
	}

	private void rIATextField2FocusGained(FocusEvent e) {
		rIATextField2.setHistory(fileds);
	}

	private FeaturePreProcessing featureProcessing;
	private LoadApplicationContext appContext = null;

	/**
	 * 首先预处理地名词，点击“确定” 按钮会发生这个。
	 */
	private void placeSureActionPerformed(ActionEvent e) {
		thread = new PlaceThread(0);
		thread.start();
	}

	/**
	 * 导入地名词
	 * 
	 * @param e
	 */
	private void button2ActionPerformed(ActionEvent e) {
		thread = new PlaceThread(1);
		thread.start();
	}

	/**
	 * 获得地名词
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
	private Set<Place> getPlaces(String shpName, String fieldName,
			int placeLevel, String placeType, String cityCodeFieldName,
			String cityName, String cityFieldName, boolean isunion,
			boolean isRemoveSuffix) {

		if (!shpName.endsWith(".shp")) {
			JOptionPane.showMessageDialog(null, "您选择的文件格式不正确，请选择.shp文件");
			return null;
		}
		if (isunion) {
			// 搞个Table替换。
			if (countyCollection == null && countyPath != null) {
				countyCollection = cn.gls.database.shp.util.ShpUtils
						.readShpfile(countyPath);
			}
			FeatureSource<SimpleFeatureType, SimpleFeature> featureSource;
			if (featureCollection == null) {
				featureSource = cn.gls.database.shp.util.ShpUtils
						.getFeatureSourceByShp(shpName);
			} else
				featureSource = new CollectionFeatureSource(
						(SimpleFeatureCollection) featureCollection);
			if (featureProcessing == null)
				featureProcessing = (FeaturePreProcessing) LoadApplicationContext
						.getInstance().getBean("featureProcessing");
			featureCollection = featureProcessing.unionSimpleFeatures(featureSource, fieldName,countyCollection==null?null:new CollectionFeatureSource((SimpleFeatureCollection) countyCollection),
					(String) comboBox.getSelectedItem());
		} else {
			if (featureCollection == null)
				featureCollection = cn.gls.database.shp.util.ShpUtils
						.readShpfile(shpName);
		}
		if (gradeTableProcessing == null)
			gradeTableProcessing = (GradeTableProcessing) appContext
					.getBean("gradeTableProcessing");
		Set<Place> places = gradeTableProcessing.clearPlace(featureCollection,
				fieldName, placeLevel, placeType, cityCodeFieldName, cityName,
				cityFieldName, isRemoveSuffix);
		return places;
	}

	private void checkBox2ActionPerformed(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			checkBox2.setSelected(true);
			checkBox1.setSelected(false);
		} else {
			checkBox2.setSelected(false);
			checkBox1.setSelected(true);
		}

	}

	private void checkBox4ActionPerformed(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			checkBox4.setSelected(true);
			checkBox3.setSelected(false);
		} else {
			checkBox4.setSelected(false);
			checkBox3.setSelected(true);
		}
	}

	private void checkBox1ActionPerformed(ItemEvent e) {
		if (ItemEvent.SELECTED == e.getStateChange()) {
			checkBox1.setSelected(true);
			checkBox2.setSelected(false);

		} else {
			checkBox1.setSelected(false);
			checkBox2.setSelected(true);
		}
	}

	private void checkBox3ActionPerformed(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			checkBox3.setSelected(true);
			checkBox4.setSelected(false);

		} else {
			checkBox3.setSelected(false);
			checkBox4.setSelected(true);
		}

	}

	private void rIATextField3ActionPerformed(ActionEvent e) {
		rIATextField3.setHistory(fileds);
	}

	/**
	 * 点击取消按钮后，要求清除一切与shp文件相关的东西
	 * 
	 * @param e
	 */
	@SuppressWarnings("deprecation")
	private void button3ActionPerformed(ActionEvent e) {
		textField1.setText(null);
		rIATextField1.setText(null);
		rIATextField2.setText(null);
		rIATextField3.setText(null);
		rIATextField4.setText(null);
		textField2.setText(null);
		countyField.setText(null);
		comboBox.removeAllItems();
		table1.setModel(new DefaultTableModel());
		textArea1.setText(null);
		if (thread != null) {
			thread.stop();
		}
	}

	/**
	 * 地名词的类型
	 * 
	 * @param e
	 */
	private void rIATextField4FocusGained(FocusEvent e) {
		List<String> fs = new ArrayList<String>();
		fs.add("城市");
		fs.add("区县");
		fs.add("乡镇");
		fs.add("村庄");
		fs.add("街道");
		fs.add("社区");
		fs.add("大厦");
		fs.add("兴趣点");
		rIATextField4.setHistory(fs);
	}

	private void rIATextField3FocusGained(FocusEvent e) {
		rIATextField3.setHistory(fileds);
	}

	private String countyPath;
	private List<String> countyFields;

	private void countyFeatureCollection(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();
		int r = fileChooser.showOpenDialog(panel1);
		if (r == JFileChooser.APPROVE_OPTION) {
			countyPath = fileChooser.getSelectedFile().getPath();
			countyField.setText(countyPath);
			countyCollection = cn.gls.database.shp.util.ShpUtils
					.readShpfile(countyPath);
			countyFields = new ArrayList<String>();
			countyFields = UIUtils.getColumnsList(countyCollection);
			// comboBox.set
			for (int i = 0; i < countyFields.size(); i++) {
				comboBox.addItem(countyFields.get(i));
			}
		}
	}

	private List<String> fileds = new ArrayList<String>();

	private void initComponents() {
		textArea1 = new JTextArea();
		panel1 = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		button1 = new ImageButton("chooser");
		label2 = new JLabel();
		rIATextField1 = new JRIATextField();
		label3 = new JLabel();
		rIATextField4 = new JRIATextField();
		label4 = new JLabel();
		rIATextField2 = new JRIATextField();
		label5 = new JLabel();
		rIATextField3 = new JRIATextField();
		label8 = new JLabel();
		textField2 = new JTextField();
		label6 = new JLabel();
		checkBox1 = new JCheckBox();
		checkBox2 = new JCheckBox();
		label7 = new JLabel();
		scroollPane = new JScrollPane();
		checkBox3 = new JCheckBox();
		checkBox4 = new JCheckBox();
		button2 = new ImageButton("importstart");
		button3 = new ImageButton("cancel");
		table1 = new JTable();
		panel1.setBackground(new Color(255, 200, 100));
		label9 = new JLabel();
		textArea1.setFont(textFont);
		textField1.setFont(textFont);
		textField2.setFont(textFont);
		rIATextField1.setFont(textFont);
		rIATextField2.setFont(textFont);
		rIATextField3.setFont(textFont);
		rIATextField4.setFont(textFont);
		sureButton = new ImageButton("ensure");
		label9 = new JLabel();
		countyLabel = new JLabel();
		countyField = new JTextField();
		countyButton = new ImageButton("chooser");
		countyLabel.setText("选择区域文件:");
		countyLabel.setFont(font);
		countyField.setFont(textFont);
		comboBox = new JComboBox();
		textScrollPanel=new JScrollPane();
		textScrollPanel.setViewportView(textArea1);
		// ======== panel1 ========
		{

			// ---- label1 ----
			label1.setText("*\u9009\u62e9\u6587\u4ef6\uff1a");
			label1.setFont(font);
			label1.setHorizontalAlignment(SwingConstants.CENTER);

			// ---- button1 ----
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			// ---- label2 ----
			label2.setText("*\u5730\u540d\u5b57\u6bb5\uff1a");
			label2.setFont(font);
			label2.setHorizontalAlignment(SwingConstants.CENTER);

			// ---- rIATextField1 ----
			rIATextField1.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField1FocusGained(e);
				}
			});

			// ---- label3 ----
			label3.setText("*\u5730\u540d\u7ea7\u522b\uff1a");
			label3.setFont(font);
			label3.setHorizontalAlignment(SwingConstants.CENTER);

			// ---- rIATextField4 ----
			rIATextField4.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField4FocusGained(e);
				}
			});

			// ---- label4 ----
			label4.setText("*\u5730\u540d\u7c7b\u578b\uff1a");
			label4.setHorizontalAlignment(SwingConstants.CENTER);
			label4.setFont(font);

			// ---- rIATextField2 ----
			rIATextField2.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField2FocusGained(e);
				}
			});
			// --------table-----
			table1.setFont(font);
			table1.setBackground(new Color(255, 200, 100));
			scroollPane.setViewportView(table1);
			scroollPane.getViewport().setBackground(new Color(62, 146, 128));
			// ---- label5 ----
			label5.setText("\u57ce\u5e02\u5b57\u6bb5\u540d\uff1a");
			label5.setFont(font);
			label5.setHorizontalAlignment(SwingConstants.CENTER);

			// ---- rIATextField3 ----
			rIATextField3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rIATextField3ActionPerformed(e);
				}
			});
			rIATextField3.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField3FocusGained(e);
				}
			});

			// ---- label8 ----
			label8.setText("\u57ce\u5e02\u540d\u79f0\uff1a");
			label8.setFont(font);
			label8.setHorizontalAlignment(SwingConstants.CENTER);
			// ---- textField2 ----
			textField2.setFont(font);

			// ---- label6 ----
			label6.setText("\u5408\u5e76\u5730\u540d\uff1a");
			label6.setFont(font);
			label6.setHorizontalAlignment(SwingConstants.CENTER);

			// ---- checkBox1 ----
			checkBox1.setText("\u662f");
			checkBox1.setFont(font);
			checkBox1.setSelected(true);
			checkBox1.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					checkBox1ActionPerformed(e);
				}
			});

			// ---- checkBox2 ----
			checkBox2.setText("\u5426");
			checkBox2.setFont(font);
			checkBox2.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					checkBox2ActionPerformed(e);
				}

			});

			// ---- label7 ----
			label7.setText("\u5220\u9664\u540e\u7f00\uff1a");
			label7.setFont(font);
			label7.setHorizontalAlignment(SwingConstants.CENTER);

			// ---- checkBox3 ----
			checkBox3.setText("\u662f");
			checkBox3.setFont(font);
			checkBox3.setSelected(true);
			checkBox3.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub

					checkBox3ActionPerformed(e);
				}
			});

			// ---- checkBox4 ----
			checkBox4.setText("\u5426");
			checkBox4.setFont(font);
			checkBox4.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					checkBox4ActionPerformed(e);
				}
			});

			// ---- button2 ----

			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button2ActionPerformed(e);
				}
			});

			// ---- button3 ----

			button3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button3ActionPerformed(e);
				}
			});

			// ------sureButton--------
			sureButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					placeSureActionPerformed(e);
				}
			});
			// ----------Label----------------
			label9.setText("<html>" + "使用说明：" + placeImportdescribe + "</html>");
			label9.setFont(textFont);
			label9.setVerticalAlignment(SwingConstants.TOP);
			label9.setVerticalTextPosition(SwingConstants.TOP);
			label9.setBorder(BorderFactory.createLineBorder(Color.red));
			// -----------------countyButton---------------
			countyButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					countyFeatureCollection(e);
				}
			});
			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout
					.setHorizontalGroup(panel1Layout
							.createParallelGroup()

							.add(panel1Layout
									.createSequentialGroup()
									.add(panel1Layout
											.createParallelGroup()
											.add(panel1Layout
													.createSequentialGroup()
													.addContainerGap()
													.add(panel1Layout
															.createParallelGroup()
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(label1,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.12),
																			GroupLayout.PREFERRED_SIZE)
																	.add(18,
																			18,
																			18)
																	.add(textField1,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.2),
																			GroupLayout.PREFERRED_SIZE)
																	.add(18,
																			18,
																			18)
																	.add(button1,
																			GroupLayout.PREFERRED_SIZE,
																			110,
																			GroupLayout.PREFERRED_SIZE))
															.add(panel1Layout
																	.createSequentialGroup()

																	.add(countyLabel,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.12),
																			GroupLayout.PREFERRED_SIZE)
																	.add(18,
																			18,
																			18)
																	.add(countyField,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.2),
																			GroupLayout.PREFERRED_SIZE)
																	.add(18,
																			18,
																			18)
																	.add(countyButton,
																			GroupLayout.PREFERRED_SIZE,
																			110,
																			GroupLayout.PREFERRED_SIZE)
																	.add(18,
																			18,
																			18)
																	.add(comboBox,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.1),
																			GroupLayout.PREFERRED_SIZE))

															.add(panel1Layout
																	.createSequentialGroup()
																	.add(label4,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.12),
																			GroupLayout.PREFERRED_SIZE)
																	.add(18,
																			18,
																			18)
																	.add(rIATextField2,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.2),
																			GroupLayout.PREFERRED_SIZE))
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(panel1Layout
																			.createParallelGroup()
																			.add(panel1Layout
																					.createSequentialGroup()
																					.add(label6,
																							GroupLayout.PREFERRED_SIZE,
																							(int) (width * 0.12),
																							GroupLayout.PREFERRED_SIZE)
																					.add(34,
																							34,
																							34)
																					.add(checkBox1))
																			.add(panel1Layout
																					.createSequentialGroup()
																					.add(label7,
																							GroupLayout.PREFERRED_SIZE,
																							(int) (width * 0.12),
																							GroupLayout.PREFERRED_SIZE)
																					.add(34,
																							34,
																							34)
																					.add(checkBox3)))
																	.add(152,
																			152,
																			152)
																	.add(panel1Layout
																			.createParallelGroup()
																			.add(checkBox4)
																			.add(checkBox2)))
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(label2,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.12),
																			GroupLayout.PREFERRED_SIZE)
																	.add(18,
																			18,
																			18)
																	.add(panel1Layout
																			.createParallelGroup()
																			.add(rIATextField4,
																					GroupLayout.PREFERRED_SIZE,
																					(int) (width * 0.2),
																					GroupLayout.PREFERRED_SIZE)
																			.add(rIATextField1,
																					GroupLayout.PREFERRED_SIZE,
																					(int) (width * 0.2),
																					GroupLayout.PREFERRED_SIZE)))
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(label3,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.12),
																			GroupLayout.PREFERRED_SIZE)
																	.add(0,
																			0,
																			Short.MAX_VALUE))
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(panel1Layout
																			.createParallelGroup()
																			.add(label5,
																					GroupLayout.PREFERRED_SIZE,
																					(int) (width * 0.12),
																					GroupLayout.PREFERRED_SIZE)
																			.add(label8,
																					GroupLayout.PREFERRED_SIZE,
																					(int) (width * 0.12),
																					GroupLayout.PREFERRED_SIZE))
																	.add(18,
																			18,
																			18)
																	.add(panel1Layout
																			.createParallelGroup()
																			.add(textField2,
																					GroupLayout.PREFERRED_SIZE,
																					(int) (width * 0.2),
																					GroupLayout.PREFERRED_SIZE)
																			.add(rIATextField3,
																					GroupLayout.PREFERRED_SIZE,
																					(int) (width * 0.2),
																					GroupLayout.PREFERRED_SIZE))
																	.add(panel1Layout
																			.createParallelGroup()
																			.add(label9,
																					(int) (width * 0.15),
																					(int) (width * 0.3),
																					(int) (width * 0.45))))))
											.add(panel1Layout
													.createSequentialGroup()
													.add(22, 22, 22)
											// .add(progressBar1,
											// GroupLayout.PREFERRED_SIZE,
											// (int) (width * 0.5),
											// GroupLayout.PREFERRED_SIZE)
											)
											.add(panel1Layout
													.createSequentialGroup()
													.add((int) (width * 0.13),
															(int) (width * 0.13),
															(int) (width * 0.13))
													.add(sureButton,
															GroupLayout.PREFERRED_SIZE,
															110,
															GroupLayout.PREFERRED_SIZE)

													.add((int) (width * 0.04),
															(int) (width * 0.05),
															(int) (width * 0.05))
													.add(button2,
															GroupLayout.PREFERRED_SIZE,
															110,
															GroupLayout.PREFERRED_SIZE)
													.add((int) (width * 0.04),
															(int) (width * 0.05),
															(int) (width * 0.05))
													.add(button3,
															GroupLayout.PREFERRED_SIZE,
															110,
															GroupLayout.PREFERRED_SIZE))
											.add(GroupLayout.LEADING,
													panel1Layout
															.createSequentialGroup()
															.add(10, 10, 10)
															.add(textScrollPanel,
																	(int) (width * 0.3),
																	(int) (width * 0.45),
																	(int) (width * 0.55))))
									.addContainerGap(48, Short.MAX_VALUE)));
			panel1Layout
					.setVerticalGroup(panel1Layout
							.createParallelGroup()
							.add(panel1Layout
									.createSequentialGroup()
									.addContainerGap()
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(textField1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(button1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup()
											.add(countyLabel,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(countyField,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(countyButton,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(comboBox,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup()
											.add(label2,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)

									)
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label3,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField4,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label4,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField2,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)

									)
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label5,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField3,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label8,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(textField2,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)

									)
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label6,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(checkBox1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(checkBox2,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE))

									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label7,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(checkBox3,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE)
											.add(checkBox4,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.05),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.03),
											(int) (height * 0.03),
											(int) (height * 0.03))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(button2,
													GroupLayout.PREFERRED_SIZE,
													45,
													GroupLayout.PREFERRED_SIZE)
											.add(sureButton,
													GroupLayout.PREFERRED_SIZE,
													45,
													GroupLayout.PREFERRED_SIZE)
											.add(button3,
													GroupLayout.PREFERRED_SIZE,
													45,
													GroupLayout.PREFERRED_SIZE))
									.add(10, 10, 10)
									.add(panel1Layout.createParallelGroup(
											GroupLayout.TRAILING).add(
													textScrollPanel, (int) (height * 0.13),
											(int) (height * 0.45),
											(int) (height * 0.7)))
									.addPreferredGap(LayoutStyle.RELATED, 45,
											Short.MAX_VALUE).add(63, 63, 63))
							.add(panel1Layout
									.createSequentialGroup()
									.add((int) (height * 0.2),
											(int) (height * 0.2),
											(int) (height * 0.2))
									.add(label9, (int) (height * 0.25),
											(int) (height * 0.3),
											(int) (height * 0.35))));
		}
	}

	private JComboBox comboBox;
	private JLabel countyLabel;
	private JTextField countyField;
	private JButton countyButton;
	private JButton sureButton;
	private GradeTableProcessing gradeTableProcessing;
	private JLabel label1;
	private JTextField textField1;
	private JButton button1;
	private JLabel label2;
	private JRIATextField rIATextField1;
	private JLabel label3;
	private JRIATextField rIATextField4;
	private JLabel label4;
	private JRIATextField rIATextField2;
	private JLabel label5;
	private JRIATextField rIATextField3;
	private JLabel label8;
	private JTextField textField2;
	private JLabel label6;
	private JCheckBox checkBox1;
	private JCheckBox checkBox2;
	private JLabel label7;
	private JCheckBox checkBox3;
	private JCheckBox checkBox4;
	private JButton button2;
	private JButton button3;
	private BindingGroup bindingGroup;
	public JScrollPane scroollPane;
	public JPanel panel1;
	private JTable table1;
	private JTextArea textArea1;
	private JScrollPane textScrollPanel;
	private JLabel label9;
	private Set<Place> places;

	public class PlaceThread extends Thread {
		int flag = 0;

		public PlaceThread(int flag) {
			super();
			this.flag = flag;
		}

		@Override
		public void run() {
			if (flag == 0) {
				// 开始导入地名词
				if (filePath == null || rIATextField1.getText() == null
						|| "".equalsIgnoreCase(rIATextField1.getText())
						|| "".equalsIgnoreCase(rIATextField2.getText())
						|| "".equalsIgnoreCase(rIATextField4.getText())
						|| rIATextField2.getText() == null
						|| rIATextField4.getText() == null) {
					JOptionPane.showMessageDialog(null, "你必须填写必要的信息！");
					return;
				}
				if ((textField2.getText() == null || ""
						.equalsIgnoreCase(textField2.getText()))
						&& (rIATextField3.getText() == null || ""
								.equalsIgnoreCase(rIATextField3.getText()))) {
					JOptionPane.showMessageDialog(null, "城市字段或城市名必选其一");
					return;
				}
				if (appContext == null)
					appContext = LoadApplicationContext.getInstance();
				featureProcessing = (FeaturePreProcessing) appContext
						.getBean("featureProcessing");
				int level = UIUtils.getLevel(rIATextField4.getText());
				String isunionstr = checkBox1.isSelected() == true ? checkBox1
						.getText() : checkBox2.getText();
				boolean isunion = true;
				if (!"是".equalsIgnoreCase(isunionstr))
					isunion = false;
				String removeStr = checkBox3.isSelected() == true ? checkBox3
						.getText() : checkBox4.getText();
				boolean removesuffix = true;
				if (!"是".equalsIgnoreCase(removeStr))
					removesuffix = false;
				places = getPlaces(filePath, (String) rIATextField1.getText(),
						level, rIATextField2.getText(), null,
						textField2.getText(), rIATextField3.getText(), isunion,
						removesuffix);
				// 为Table添加数据
				String[] columns = { "name","placeLevel","cityCode","placeType"};
				Object[][] placesData = UIUtils.getTables(places);
				DefaultTableModel model=new DefaultTableModel(placesData, columns);
				UIUtils.clearTable(table1);
				table1.setModel(model);
				table1.getModel().addTableModelListener(
						new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								changeTable(e);
							}
						});
			} else {
				// 把地名词导入到数据库
				DefaultTableModel model = (DefaultTableModel) table1.getModel();
				boolean f = model.getColumnName(0).equalsIgnoreCase("name")
						&& model.getColumnName(1)
								.equalsIgnoreCase("placeLevel")
						&& model.getColumnName(2).equalsIgnoreCase("cityCode")
						&& model.getColumnName(3).equalsIgnoreCase("placeType");
				if (!f) {
					JOptionPane.showMessageDialog(null,
							"你还没有进行对应选择生成地名词，请按要求操作。");
					return;
				}
				places = UIUtils.getPlaces(model);
				if (model.getRowCount() < 1)
					return;
				if (gradeTableProcessing == null) {
					appContext = LoadApplicationContext.getInstance();
					gradeTableProcessing = (GradeTableProcessing) appContext
							.getBean("gradeTableProcessing");

				}
				bindingGroup = new BindingGroup();
				bindingGroup.addBinding(Bindings.createAutoBinding(
						UpdateStrategy.READ_WRITE, gradeTableProcessing,
						BeanProperty.create("placesName"), textArea1,
						BeanProperty.create("text")));
				bindingGroup.bind();
				int count = gradeTableProcessing.insertGradeData(places);
				JOptionPane.showMessageDialog(panel1, "导入地名词" + count + "个");
			}
		}

		private void changeTable(TableModelEvent e) {
			// if()
		}
	}
}
