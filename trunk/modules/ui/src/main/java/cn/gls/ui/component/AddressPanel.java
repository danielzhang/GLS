package cn.gls.ui.component;

import java.awt.Color;
import java.awt.TextArea;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.geotools.data.FeatureSource;
import org.geotools.data.memory.MemoryFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.layout.GroupLayout;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.data.DataType;
import cn.gls.database.postgis.standard.PostGISAddressOperator;
import cn.gls.database.postgis.standard.PostGISBaseOperator;
import cn.gls.database.postgis.standard.PostGISCityOperator;
import cn.gls.database.postgis.standard.PostGISOperator;
import cn.gls.database.postgis.standard.PostGISPoliticalOperator;
import cn.gls.database.postgis.standard.PostGISStreetOperator;
import cn.gls.database.shp.util.ShpUtils;
import cn.gls.database.util.DBUtils;
import cn.gls.database.util.FeaturePreProcessing;
import cn.gls.ui.dao.ConfigDao;
import cn.gls.util.UIUtils;

/**
 * 
 * @date 2012-8-9
 * @author "Daniel Zhang"
 * @update 2012-8-9
 * @description TODO
 * 
 */
public class AddressPanel {
	public AddressPanel() {
		super();
		initComponents();

	}

	private static AddressPanel addressPanel;

	public static AddressPanel instance() {
		if (addressPanel == null)
			addressPanel = new AddressPanel();
		return addressPanel;
	}

	private String filePath;

	private String quyufilePath;

	private Font font = ConfigDao.instance().getConfig().getFont();

	private Font textFont = ConfigDao.instance().getConfig().getTextFont();

	private int width = (int) ConfigDao.instance().getConfig()
			.getMainPanelDimension().getWidth();
	private int height = (int) ConfigDao.instance().getConfig()
			.getMainPanelDimension().getHeight();
	private static final String addressImportDescribe = ConfigDao.instance()
			.getConfig().getAddressImportDescribe();

	private FeaturePreProcessing featureProcessing;
	private FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection;

	private String sCityName;

	private LoadApplicationContext loadApplicationContext = null;

	private PostGISBaseOperator operator;

	private AddressThread thread;

	private void tableChange(TableModelEvent e) {
		if (featureCollection == null
				|| !(featureCollection instanceof MemoryFeatureCollection)) {
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

	private void initDataBaseEnv() {
		loadApplicationContext = LoadApplicationContext.getInstance();
		featureProcessing = (FeaturePreProcessing) loadApplicationContext
				.getBean("featureProcessing");
	}

	/**
	 * 选择文件,填充table用选择的文件
	 * 
	 * @param e
	 */
	private void button1ActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();
		int restatus = fileChooser.showOpenDialog(panel1);

		if (restatus == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getPath();
			textField1.setText(fileChooser.getSelectedFile().getPath());

			// 设置每一个字段的字段名
			// 首先获得Shpfile
			SimpleFeatureType featureType = ShpUtils
					.getSimpleFeatureTypeByShp(textField1.getText());
			List<AttributeDescriptor> attributes = featureType
					.getAttributeDescriptors();
			fields.clear();
			for (AttributeDescriptor attribute : attributes) {

				if (attribute instanceof GeometryDescriptor)
					continue;
				fields.add(attribute.getName().toString());
			}
			// 填充table
			Object[][] datas = cn.gls.util.ShpUtils.getTableData(
					ShpUtils.readShpfile(filePath), fields);
			table1.setModel(new DefaultTableModel(datas, fields.toArray()));
		} else {
			JOptionPane.showMessageDialog(null, "请选择正确的文件格式", "Tile",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private List<String> fields = new ArrayList<String>();

	private void rIATextField1FocusGained(FocusEvent e) {
		rIATextField1.setHistory(fields);
	}

	private void rIATextField2FocusGained(FocusEvent e) {
		rIATextField2.setHistory(fields);
	}

	private void rIATextField3FocusGained(FocusEvent e) {
		rIATextField3.setHistory(fields);
	}

	private void rIATextField4FocusGained(FocusEvent e) {
		rIATextField4.setHistory(fields);
	}

	private void rIATextField5FocusGained(FocusEvent e) {
		rIATextField5.setHistory(fields);
	}

	private void rIATextField6FocusGained(FocusEvent e) {
		rIATextField6.setHistory(fields);
	}

	private void rIATextField7FocusGained(FocusEvent e) {
		rIATextField7.setHistory(fields);
	}

	/**
	 * 开始导入地址数据
	 * 
	 * @param e
	 */
	private void button2ActionPerformed(ActionEvent e) {

		// 首先产生新的feature
		if (thread == null) {
			thread = new AddressThread();
		}
		thread.start();
	}
	/**
	 * 生成地址操作
	 * 
	 * @param e
	 */
	private void createAddress(ActionEvent e) {
		if (rIATextField1.getText() == null
				&& !"".equalsIgnoreCase(rIATextField1.getText())
				&& rIATextField2.getText() == null
				&& !"".equalsIgnoreCase(rIATextField2.getText())
				&& rIATextField3.getText() == null
				&& !"".equalsIgnoreCase(rIATextField3.getText())
				&& !"".equalsIgnoreCase(rIATextField4.getText())
				&& !"".equalsIgnoreCase(rIATextField5.getText())
				&& rIATextField4.getText() == null
				&& rIATextField5.getText() == null
				&& rIATextField6.getText() == null
				&& !"".equalsIgnoreCase(rIATextField6.getText())
				&& rIATextField7.getText() == null
				&& !"".equalsIgnoreCase(rIATextField7.getText())) {
			JOptionPane.showMessageDialog(null, "请选择相对应的字段名", "Tile",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		int importType = DataType.CITY_TYPE;

		if (!"".equalsIgnoreCase(rIATextField2.getText())) {

			if (importType < DataType.POLITICAL_TYPE)
				importType = DataType.POLITICAL_TYPE;

		}
		if (!"".equalsIgnoreCase(rIATextField3.getText())) {

			if (importType < DataType.POLITICAL_TYPE)
				importType = DataType.POLITICAL_TYPE;

		}
		if (!"".equalsIgnoreCase(rIATextField4.getText())) {

			if (importType < DataType.POLITICAL_TYPE)
				importType = DataType.POLITICAL_TYPE;

		}
		if (!"".equalsIgnoreCase(rIATextField5.getText())) {

			if (importType < DataType.STREET_TYPE)
				importType = DataType.STREET_TYPE;

		} else {
			importType = DataType.ADDRESS_TYPE;
		}
		//添加绑定
		if (featureProcessing == null) {
			initDataBaseEnv();
		}
		

		// 去导入数据
		featureCollection = preProcessingData(importType);
		//
		Object[][] datas = UIUtils.getTables(featureCollection);
		Object[] columns = UIUtils.getColumns(featureCollection);
		table1.setModel(new DefaultTableModel(datas, columns));
		table1.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				System.out.println("------------------------------发生改变");
				tableChange(e);

			}
		});
	}

	/**
	 * 
	 * @param oShpName
	 *            文件名称
	 * @param countyShpName
	 *            区域文件名称
	 * @param countyFieldName
	 *            区域字段名
	 * @param cityName
	 *            城市名称
	 * @return
	 */
	private FeatureCollection<SimpleFeatureType, SimpleFeature> preprocessingData(
			String oShpName, String countyShpName, String countyFieldName,
			String cityName) {
	
		FeatureCollection<SimpleFeatureType, SimpleFeature> processingFeature = featureProcessing
				.preDATAProcessing(ShpUtils.readShpfile(oShpName),
						ShpUtils.readShpfile(countyShpName), countyFieldName,
						cityName);
		return processingFeature;
	}

	private FeatureCollection<SimpleFeatureType, SimpleFeature> preProcessingData(
			int type) {
		FeatureCollection<SimpleFeatureType, SimpleFeature> featureaCollection = null;
		switch (type) {
		case DataType.CITY_TYPE:
			featureaCollection = ShpUtils.readShpfile(filePath);
			break;
		case DataType.POLITICAL_TYPE:
			if (quyufilePath != null)
				featureaCollection = preprocessingData(filePath, quyufilePath,
						(String)combobox.getSelectedItem(), sCityName);
			break;
		case DataType.STREET_TYPE:
			String filedName = rIATextField5.getText();
			featureaCollection = unionFeature(filePath, filedName,
					quyufilePath, (String)combobox.getSelectedItem());
			break;
		case DataType.ADDRESS_TYPE:
			if (quyufilePath != null)
				featureaCollection = preprocessingData(filePath, quyufilePath,
						(String)combobox.getSelectedItem(), sCityName);
			break;
		}
		return featureaCollection;
	}

	/**
	 * @Param AddressImportFrame
	 * @Description 合并要素
	 * @return FeatureCollection<SimpleFeatureType,SimpleFeature>
	 * @param shpName
	 * @param fieldName
	 * @param countyshpName
	 * @param countyFieldName
	 * @return
	 */
	private FeatureCollection<SimpleFeatureType, SimpleFeature> unionFeature(
			String shpName, String fieldName, String countyshpName,
			String countyFieldName) {
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = ShpUtils
				.getFeatureSourceByShp(shpName);
		FeatureSource<SimpleFeatureType, SimpleFeature> countyFeatureSource = null;
		if (countyshpName != null)
			countyFeatureSource = ShpUtils.getFeatureSourceByShp(countyshpName);
		if (featureProcessing == null) {
			loadApplicationContext = LoadApplicationContext.getInstance();
			featureProcessing = (FeaturePreProcessing) loadApplicationContext
					.getBean("featureProcessing");
		}
		return featureProcessing.unionSimpleFeatures(featureSource, fieldName,
				countyFeatureSource, countyFieldName);
	}

	/**
	 * @Param AddressImportFrame
	 * @Description 取消按钮激发事件
	 * @return void
	 * @param e
	 */
	private void button3ActionPerformed(ActionEvent e) {
		textArea1.setText("");
		textField1.setText("");
		textField2.setText("");
		textField3.setText("");
		rIATextField1.setText("");
		rIATextField2.setText("");
		rIATextField3.setText("");
		rIATextField4.setText("");
		rIATextField5.setText("");
		rIATextField6.setText("");
		rIATextField7.setText("");
		rIATextField8.setText("");
		rIATextField9.setText("");
		rIATextField1.setHistory(null);
		rIATextField2.setHistory(null);
		rIATextField3.setHistory(null);
		rIATextField4.setHistory(null);
		rIATextField5.setHistory(null);
		rIATextField6.setHistory(null);
		rIATextField7.setHistory(null);
		rIATextField8.setHistory(null);
		rIATextField9.setHistory(null);
		combobox.removeAllItems();
		
		fields.clear();
		table1.setModel(new DefaultTableModel());
		if (thread != null) {
			thread.interrupt();
		}
	}

	private void rIATextField9FocusGained(FocusEvent e) {
		rIATextField9.setHistory(fields);
	}

	private void rIATextField8FocusGained(FocusEvent e) {
		rIATextField8.setHistory(fields);
	}
     private List<String> countyFields=new ArrayList<String>();
	/**
	 * @Param AddressImportFrame
	 * @Description 选择区域文件
	 * @return void
	 * @param e
	 */
	private void button4ActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();
		int restatus = fileChooser.showOpenDialog(panel1);

		if (restatus == JFileChooser.APPROVE_OPTION) {
			quyufilePath = fileChooser.getSelectedFile().getPath();
			textField2.setText(fileChooser.getSelectedFile().getPath());
			countyFields=cn.gls.util.ShpUtils.getColumnNames(quyufilePath);
			combobox.removeAllItems();
			for(int i=0;i<countyFields.size();i++){
				combobox.addItem(countyFields.get(i));
			}
		} else {
			JOptionPane.showMessageDialog(null, "请选择正确的文件格式", "Tile",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 初始化Address控件
	 */
	private void initComponents() {

		panel1 = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		button1 = new ImageButton("chooser");
		label12 = new JLabel();
		textField3 = new JTextField();
		button4 = new ImageButton("chooser");
		label2 = new JLabel();
		rIATextField1 = new JRIATextField();
		label3 = new JLabel();
		rIATextField2 = new JRIATextField();
		label4 = new JLabel();
		rIATextField3 = new JRIATextField();
		label5 = new JLabel();
		rIATextField4 = new JRIATextField();
		label6 = new JLabel();
		rIATextField5 = new JRIATextField();
		label7 = new JLabel();
		rIATextField6 = new JRIATextField();
		label8 = new JLabel();
		rIATextField7 = new JRIATextField();
		label10 = new JLabel();
		rIATextField9 = new JRIATextField();
		label11 = new JLabel();
		textField2 = new JTextField();
		label9 = new JLabel();
		rIATextField8 = new JRIATextField();
		button2 = new ImageButton("importstart");
		button3 = new ImageButton("cancel");
		sureButton = new ImageButton("ensure");
//		progressBar1 = new JProgressBar();
		table1 = new JTable();
		textArea1 = new TextArea();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table1);
		combobox=new JComboBox();
	     combobox.setFont(textFont);
		label13 = new JLabel();
		{
			textField1.setFont(textFont);
			textField2.setFont(textFont);
			textField3.setFont(textFont);
			rIATextField1.setFont(textFont);
			rIATextField2.setFont(textFont);
			rIATextField3.setFont(textFont);
			rIATextField4.setFont(textFont);
			rIATextField5.setFont(textFont);
			rIATextField6.setFont(textFont);
			rIATextField7.setFont(textFont);
			rIATextField8.setFont(textFont);
			rIATextField9.setFont(textFont);
		}
		bindingGroup = new BindingGroup();
//		rIATextField9.setText("r9");
//		rIATextField8.setText("r8");
		
		
		// ======== panel1 ========
		{
			// =========设置大小=====

			// ---- label1 ----
			label1.setText("*\u9009\u62e9\u6587\u4ef6:");
			label1.setFont(font);

			// ---- button1 ----

			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			// ---- label12 ----
			label12.setText("\u9009\u62e9\u533a\u57df\u6587\u4ef6:");
			label12.setFont(font);

			// ---- button4 ----

			button4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button4ActionPerformed(e);
				}
			});

			// ---- label2 ----
			label2.setText("\u57ce\u5e02\u5b57\u6bb5\uff1a");
			label2.setFont(font);

			// ---- rIATextField1 ----
			rIATextField1.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField1FocusGained(e);
				}
			});

			// ---- label3 ----
			label3.setText("\u533a\u53bf\u5b57\u6bb5\uff1a");
			label3.setFont(font);

			// ---- rIATextField2 ----
			rIATextField2.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField2FocusGained(e);
				}
			});

			// ---- label4 ----
			label4.setText("\u4e61\u9547\u5b57\u6bb5\uff1a");
			label4.setFont(font);

			// ---- rIATextField3 ----
			rIATextField3.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField3FocusGained(e);
				}
			});

			// ---- label5 ----
			label5.setText("\u6751\u5e84\u5b57\u6bb5\uff1a");
			label5.setFont(font);

			// ---- rIATextField4 ----
			rIATextField4.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField4FocusGained(e);
				}
			});

			// ---- label6 ----
			label6.setText("\u9053\u8def\u8857\u9053\u5b57\u6bb5\uff1a");
			label6.setFont(font);

			// ---- rIATextField5 ----
			rIATextField5.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField5FocusGained(e);
				}
			});

			// ---- label7 ----
			label7.setText("\u5c0f\u533a\u5b57\u6bb5\uff1a");
			label7.setFont(font);

			// ---- rIATextField6 ----
			rIATextField6.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField6FocusGained(e);
				}
			});

			// ---- label8 ----
			label8.setText("\u5927\u53a6\u5b57\u6bb5\uff1a");
			label8.setFont(font);

			// ---- rIATextField7 ----
			rIATextField7.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField7FocusGained(e);
				}
			});

			// ---- label10 ----
			label10.setText("POI\u5b57\u6bb5\uff1a");
			label10.setFont(font);

			// ---- rIATextField9 ----
			rIATextField9.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField9FocusGained(e);
				}
			});

			// ---- label11 ----
			label11.setText("\u57ce\u5e02\u540d\u79f0\uff1a");
			label11.setFont(font);

			// ---- label9 ----
			label9.setText("\u6570\u636e\u7c7b\u578b\uff1a");
			label9.setFont(font);

			// ---- rIATextField8 ----
			rIATextField8.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField8FocusGained(e);
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
			// -----sureButton-------------
			sureButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					createAddress(e);

				}
			});
			// --------textArea-------
			textArea1.setFont(font);

			// ----------label13------
			label13.setText("<html>" + "使用说明:" + addressImportDescribe
					+ "</html>");
			label13.setVerticalTextPosition(SwingConstants.TOP);
			label13.setFont(textFont);
			label13.setBorder(BorderFactory.createLineBorder(Color.red));
			label13.setVerticalAlignment(SwingConstants.TOP);

//			progressBar1.setVisible(false);
			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout
					.setHorizontalGroup(panel1Layout
							.createParallelGroup()
							.add(panel1Layout
									.createSequentialGroup()
									.add((int) (width * 0.03),
											(int) (width * 0.03),
											(int) (width * 0.03))
									.add(panel1Layout
											.createParallelGroup()
											.add(panel1Layout
													.createSequentialGroup()
													.add(panel1Layout
															.createParallelGroup()
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(label1,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.1),
																			GroupLayout.PREFERRED_SIZE)
																	.add((int) (width * 0.03),
																			(int) (width * 0.03),
																			(int) (width * 0.03))
																	.add(textField1,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.16),
																			GroupLayout.PREFERRED_SIZE))
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(label2,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.1),
																			GroupLayout.PREFERRED_SIZE)
																	.add((int) (width * 0.03),
																			(int) (width * 0.03),
																			(int) (width * 0.03))
																	.add(rIATextField1,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.16),
																			GroupLayout.PREFERRED_SIZE))
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(panel1Layout
																			.createParallelGroup(
																					GroupLayout.LEADING)
																			.add(label3,
																					GroupLayout.PREFERRED_SIZE,
																					(int) (width * 0.1),
																					GroupLayout.PREFERRED_SIZE)
																			.add(panel1Layout
																					.createParallelGroup(
																							GroupLayout.LEADING,
																							false)
																					.add(label5,
																							GroupLayout.DEFAULT_SIZE,
																							(int) (width * 0.1),
																							Short.MAX_VALUE)
																					.add(label4,
																							GroupLayout.PREFERRED_SIZE,
																							(int) (width * 0.1),
																							GroupLayout.PREFERRED_SIZE)))
																	.add(panel1Layout
																			.createParallelGroup()
																			.add(panel1Layout
																					.createSequentialGroup()
																					.add((int) (width * 0.03),
																							(int) (width * 0.03),
																							(int) (width * 0.03))
																					.add(rIATextField4,
																							GroupLayout.PREFERRED_SIZE,
																							(int) (width * 0.16),
																							GroupLayout.PREFERRED_SIZE))
																			.add(panel1Layout
																					.createSequentialGroup()
																					.add((int) (width * 0.03),
																							(int) (width * 0.03),
																							(int) (width * 0.03))
																					.add(panel1Layout
																							.createParallelGroup(
																									GroupLayout.LEADING)
																							.add(rIATextField3,
																									GroupLayout.PREFERRED_SIZE,
																									(int) (width * 0.16),
																									GroupLayout.PREFERRED_SIZE)
																							.add(rIATextField2,
																									GroupLayout.PREFERRED_SIZE,
																									(int) (width * 0.16),
																									GroupLayout.PREFERRED_SIZE)))))
															.add(panel1Layout
																	.createSequentialGroup()
																	.add(label12,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.1),
																			GroupLayout.PREFERRED_SIZE)
																	.add((int) (width * 0.03),
																			(int) (width * 0.03),
																			(int) (width * 0.03))
																	.add(textField2,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.16),
																			GroupLayout.PREFERRED_SIZE)))
													.add((int) (width * 0.03),
															(int) (width * 0.03),
															(int) (width * 0.03))
													.add(panel1Layout
															.createParallelGroup()
															.add(button1,
																	GroupLayout.PREFERRED_SIZE,
																	110,
																	GroupLayout.PREFERRED_SIZE)
															.add(button4,
																	GroupLayout.PREFERRED_SIZE,
																	110,
																	GroupLayout.PREFERRED_SIZE)
															)
														.add(combobox,
																	GroupLayout.PREFERRED_SIZE,
																	80,
																	GroupLayout.PREFERRED_SIZE))
											.add(panel1Layout
													.createSequentialGroup()
													.add(panel1Layout
															.createParallelGroup()
															.add(label6,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.1),
																	GroupLayout.PREFERRED_SIZE)
															.add(label7,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.1),
																	GroupLayout.PREFERRED_SIZE)
															.add(label8,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.1),
																	GroupLayout.PREFERRED_SIZE)
															.add(label10,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.1),
																	GroupLayout.PREFERRED_SIZE)
															.add(label9,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.1),
																	GroupLayout.PREFERRED_SIZE)
															.add(label11,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.1),
																	GroupLayout.PREFERRED_SIZE))
													.add((int) (width * 0.03),
															(int) (width * 0.03),
															(int) (width * 0.03))
													.add(panel1Layout
															.createParallelGroup()
															.add(rIATextField9,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.16),
																	GroupLayout.PREFERRED_SIZE)
															.add(panel1Layout
																	.createParallelGroup(
																			GroupLayout.LEADING)
																	.add(rIATextField7,
																			GroupLayout.PREFERRED_SIZE,
																			(int) (width * 0.16),
																			GroupLayout.PREFERRED_SIZE))
															.add(textField3,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.16),
																	GroupLayout.PREFERRED_SIZE)
															.add(rIATextField8,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.16),
																	GroupLayout.PREFERRED_SIZE)
															.add(rIATextField6,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.16),
																	GroupLayout.PREFERRED_SIZE)
															.add(rIATextField5,
																	GroupLayout.PREFERRED_SIZE,
																	(int) (width * 0.16),
																	GroupLayout.PREFERRED_SIZE))
													.add(panel1Layout
															.createSequentialGroup()
															.add(10, 10, 10)
															.add(label13,
																	(int) (width * 0.1),
																	(int) (width * 0.1),
																	(int) (width * 0.15))))))
							.add(panel1Layout
									.createSequentialGroup()
									.add((int) (width * 0.05),
											(int) (width * 0.05),
											(int) (width * 0.05))
									.add(sureButton,
											GroupLayout.PREFERRED_SIZE, 110,
											GroupLayout.PREFERRED_SIZE)
									.add((int) (width * 0.035),
											(int) (width * 0.035),
											(int) (width * 0.035))
									.add(button2, GroupLayout.PREFERRED_SIZE,
											110, GroupLayout.PREFERRED_SIZE)
									.add((int) (width * 0.035),
											(int) (width * 0.035),
											(int) (width * 0.035))
									.add(button3, GroupLayout.PREFERRED_SIZE,
											110, GroupLayout.PREFERRED_SIZE))
							.add(GroupLayout.LEADING,
									panel1Layout
											.createSequentialGroup()
											.addContainerGap()
											.add(textArea1,
													GroupLayout.PREFERRED_SIZE,
													(int) (width * 0.5),
													GroupLayout.PREFERRED_SIZE)));
			panel1Layout
					.setVerticalGroup(panel1Layout
							.createParallelGroup()
							.add(panel1Layout
									.createSequentialGroup()
									.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(button1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(textField1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label12,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(button4,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(combobox,GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(textField2,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup()
											.add(label2,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField1,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label3,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField2,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label4,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField3,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.01)
											)
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label5,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField4,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label6,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField5,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label7,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField6,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
									.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label8,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField7,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
								.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label10,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField9,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
							.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(label9,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(rIATextField8,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											)
								.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(textField3,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE)
											.add(label11,
													GroupLayout.PREFERRED_SIZE,
													(int) (height * 0.04),
													GroupLayout.PREFERRED_SIZE))
							.add((int) (height * 0.01))
									.add(panel1Layout
											.createParallelGroup(
													GroupLayout.BASELINE)
											.add(sureButton,
													GroupLayout.PREFERRED_SIZE,
													45,
													GroupLayout.PREFERRED_SIZE)
											.add(button2,
													GroupLayout.PREFERRED_SIZE,
													45,
													GroupLayout.PREFERRED_SIZE)
											.add(button3,
													GroupLayout.PREFERRED_SIZE,
													45,
													GroupLayout.PREFERRED_SIZE))
								.add((int) (height * 0.01))
									.add(textArea1,GroupLayout.DEFAULT_SIZE,
											(int) (height * 0.2),
											Short.MAX_VALUE)
//									.add(progressBar1,
//											GroupLayout.PREFERRED_SIZE, 40,
//											GroupLayout.PREFERRED_SIZE)
									.addContainerGap(5, Short.MAX_VALUE))
							.add(panel1Layout
									.createSequentialGroup()
									.add((int) (height * 0.12),
											(int) (height * 0.12),
											(int) (height * 0.12))
									.add(label13,(int) (height * 0.48),(int) (height * 0.48),
											(int) (height * 0.48))));
		}

	}
    private JComboBox combobox;
	private JLabel label1;
	private JTextField textField1;
	private JButton button1;
	private JLabel label12;
	private JTextField textField3;
	private JButton button4;
	private JLabel label2;
	private JRIATextField rIATextField1;
	private JLabel label3;
	private JRIATextField rIATextField2;
	private JLabel label4;
	private JRIATextField rIATextField3;
	private JLabel label5;
	private JRIATextField rIATextField4;
	private JLabel label6;
	private JRIATextField rIATextField5;
	private JLabel label7;
	private JRIATextField rIATextField6;
	private JLabel label8;
	private JRIATextField rIATextField7;
	private JLabel label10;
	private JRIATextField rIATextField9;
	private JLabel label11;
	private JTextField textField2;
	private JLabel label9;
	private JRIATextField rIATextField8;
	private JButton button2;
	private JButton button3;
//	private JProgressBar progressBar1;
	private TextArea textArea1;
	private BindingGroup bindingGroup;
	// 使用说明
	private JLabel label13;
	public JPanel panel1;
	private JTable table1;
	private JButton sureButton;
	public JScrollPane scrollPane;

	public class AddressThread extends Thread {
		@Override
		public void run() {
			// 获取新的featureCollection
			if (((DefaultTableModel) table1.getModel()).getRowCount() < 1
					|| featureCollection == null) {
				JOptionPane.showMessageDialog(null, "先进行数据查看与处理");
				return;
			}
			initDataBaseEnv();
			Map<String, String> fieldMap = new HashMap<String, String>();
			String dbTableName = null;
			String dbType = null;
			int flag = 0;
			int importType = DataType.CITY_TYPE;
			if (!rIATextField1.getText().equalsIgnoreCase("")) {
				fieldMap.put("city_name", rIATextField1.getText());
				dbTableName = "cityTableName";

			}
			if (!"".equalsIgnoreCase(rIATextField2.getText())) {
				fieldMap.put("county_name", rIATextField2.getText());
				if (importType < DataType.POLITICAL_TYPE)
					importType = DataType.POLITICAL_TYPE;
				dbTableName = "politicalTableName";
				if (flag < 1)
					flag = 1;
			}
			if (!"".equalsIgnoreCase(rIATextField3.getText())) {
				fieldMap.put("town_name", rIATextField3.getText());
				if (importType < DataType.POLITICAL_TYPE)
					importType = DataType.POLITICAL_TYPE;
				dbTableName = "politicalTableName";
				if (flag < 1)
					flag = 1;

			}
			if (!"".equalsIgnoreCase(rIATextField4.getText())) {
				fieldMap.put("village_name", rIATextField4.getText());
				if (importType < DataType.POLITICAL_TYPE)
					importType = DataType.POLITICAL_TYPE;
				dbTableName = "politicalTableName";
				if (flag < 1)
					flag = 1;
			}
			if (!"".equalsIgnoreCase(rIATextField5.getText())) {
				fieldMap.put("street_name", rIATextField5.getText());
				if (importType < DataType.STREET_TYPE)
					importType = DataType.STREET_TYPE;
				dbTableName = "streetTableName";
				if (flag < 2)
					flag = 2;
			}
			if (!"".equalsIgnoreCase(rIATextField6.getText())) {
				fieldMap.put("comummitis_name", rIATextField6.getText());
				if (importType < DataType.ADDRESS_TYPE)
					importType = DataType.ADDRESS_TYPE;
				dbTableName = "poiTableName";
				if (flag < 3)
					flag = 3;
			}
			if (!"".equalsIgnoreCase(rIATextField7.getText())) {
				fieldMap.put("building_name", rIATextField7.getText());
				if (importType < DataType.ADDRESS_TYPE)
					importType = DataType.ADDRESS_TYPE;
				dbTableName = "poiTableName";
				if (flag < 3)
					flag = 3;
			}
			if (!"".equalsIgnoreCase(rIATextField8.getText())) {
				String t = rIATextField8.getText();
				if (fields.contains(t))
					fieldMap.put("a_type", rIATextField8.getText());
				//
				dbType = t;
			}
			if (!"".equalsIgnoreCase(rIATextField9.getText())) {
				fieldMap.put("poi_name", rIATextField9.getText());
				if (importType < DataType.ADDRESS_TYPE)
					importType = DataType.ADDRESS_TYPE;
				dbTableName = "poiTableName";
				if (flag < 3)
					flag = 3;
			}
			
			if (flag == 0) {
				operator = (PostGISCityOperator) loadApplicationContext
						.getBean("cityOperator");
			} else if (flag == 1) {
				operator = (PostGISPoliticalOperator) loadApplicationContext
						.getBean("politicalOperator");
			} else if (flag == 2) {
				operator = (PostGISStreetOperator) loadApplicationContext
						.getBean("streetOperator");
			} else {
             DBUtils.loadAddressTable();
				operator = (PostGISAddressOperator) loadApplicationContext
						.getBean("addressOperator");
			}
			PostGISOperator postGISOperator = new PostGISOperator(importType);
			sCityName = textField3.getText();
			if ("".equalsIgnoreCase(sCityName) || sCityName == null) {
				int option = JOptionPane.showConfirmDialog(null,
						"城市名称为空值，是否继续。");
				if (!(option == JOptionPane.YES_OPTION))
					return;
			}
//			bindingGroup.unbind();
			bindingGroup.addBinding(Bindings.createAutoBinding(
					UpdateStrategy.READ_WRITE, operator,
					BeanProperty.create("addressName"), textArea1,
					BeanProperty.create("text")));
			bindingGroup.bind();
			textArea1.setCaretPosition(textArea1.getText().length());
			// featureCollection进行更改，
			int count = postGISOperator.insert(featureCollection, fieldMap,
					dbTableName, dbType, sCityName.replace(" ", ""));
			JOptionPane.showMessageDialog(null, "正确导入地址数：" + count + "个");
		}
	}

}
