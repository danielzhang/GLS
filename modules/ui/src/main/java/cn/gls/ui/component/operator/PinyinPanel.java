package cn.gls.ui.component.operator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cn.gls.data.PinyinPlace;
import cn.gls.database.LoadApplicationContext;
import cn.gls.database.postgis.operator.assist.PinyinTableProcessing;
import cn.gls.ui.component.*;

import org.geotools.feature.FeatureCollection;
import org.jdesktop.layout.GroupLayout;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import cn.gls.ui.component.MainPanel;
import cn.gls.ui.dao.ConfigDao;
import cn.gls.util.ShpUtils;
import cn.gls.util.UIUtils;
/**
 * 
 * @date 2012-8-14
 * @author "Daniel Zhang"
 * @update 2012-8-14
 * @description 拼音词的导入与转化。
 *
 */
public class PinyinPanel extends MainPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9006564598545823732L;

	public PinyinPanel() {
		initComponents();
	}

	private static final String pinyinDescribe = ConfigDao.instance()
			.getConfig().getPinyinDescribe();
	private PinyinTableProcessing pinyinProdcessing;
	private FeatureCollection<SimpleFeatureType, SimpleFeature> pinyinFeatureCollection;

	private void chooserPinyin(ActionEvent e) {
		JFileChooser fileChooser = UIUtils.getFileChooser();
		int l = fileChooser.showOpenDialog(this);
		if (l == JFileChooser.APPROVE_OPTION) {
			textField1.setText(fileChooser.getSelectedFile().getPath());
			pinyinFeatureCollection = cn.gls.database.shp.util.ShpUtils
					.readShpfile(textField1.getText());
			java.util.List<String> columns = cn.gls.util.ShpUtils
					.getColumnNames(textField1.getText());
			int s = columns.size();
			for (int i = 0; i < s; i++) {
				comboBox.addItem(columns.get(i));
				comboBox2.addItem(columns.get(i));
			}
			// 为table添加
			Object[][] tables = cn.gls.util.ShpUtils.getTableData(
					pinyinFeatureCollection, columns);

			DefaultTableModel model = new DefaultTableModel(tables,
					columns.toArray());
			table1.setModel(model);
		}
	}

	/**
	 * 清洗拼音词
	 * 
	 * @param e
	 */
	private void cleanPinyin(ActionEvent e) {
		thread = new PinyinThread(1);
		thread.start();
	}

	/**
	 * 开始导入到数据库中
	 * 
	 * @param e
	 */

	private void importPinyin(ActionEvent e) {
		thread = new PinyinThread(0);
		thread.start();
	}

	/**
	 * 取消操作
	 * 
	 * @param e
	 */
	private void cancelPinyin(ActionEvent e) {
		comboBox.removeAllItems();
		comboBox2.removeAllItems();
		if (pinyinFeatureCollection != null)
			pinyinFeatureCollection.clear();
		textField1.setText(null);
		table1.setModel(new DefaultTableModel());
		if (thread != null)
			thread.interrupt();
	}

	private void initComponents() {

		label1 = new JLabel();
		textField1 = new JTextField();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		button1 = new ImageButton("importstart");
		button2 = new ImageButton("cancel");
		button3 = new ImageButton("chooser");
		button4 = new ImageButton("clean");
		label2 = new JLabel();
		comboBox = new JComboBox();
		comboBox2 = new JComboBox();
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		cityField = new JTextField();

		// ======== this ========
		// ---- label1 ----
		label1.setText("\u9009\u62e9\u6587\u4ef6:");
		label1.setFont(font);

		label2.setFont(descFont);
		label2.setBorder(BorderFactory.createLineBorder(Color.red));
		label2.setText("<html>" + "使用说明：" + pinyinDescribe + "</html>");
		label2.setVerticalAlignment(SwingConstants.TOP);

		// ==========textField,combobox==============
		textField1.setFont(textFont);
		cityField.setFont(textFont);
		comboBox.setFont(textFont);
		comboBox2.setFont(textFont);

		// --------button3---------
		button3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				chooserPinyin(e);
			}
		});
		button4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				cleanPinyin(e);
			}
		});
		button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				importPinyin(e);
			}
		});

		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cancelPinyin(e);
			}
		});
		// ======== scrollPane1 ========
		{
			scrollPane1.setViewportView(table1);
		}

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup()
				.add(layout
						.createSequentialGroup()
						.add(layout
								.createParallelGroup()
								.add(layout
										.createSequentialGroup()
										.add((int) (width * 0.035),
												(int) (width * 0.035),
												(int) (width * 0.035))
										.add(layout
												.createParallelGroup()
												.add(layout
														.createSequentialGroup()
														.add(label1,
																GroupLayout.PREFERRED_SIZE,
																(int) (width * 0.08),
																GroupLayout.PREFERRED_SIZE)
														.add((int) (width * 0.01),
																(int) (width * 0.01),
																(int) (width * 0.01))
														.add(textField1,
																GroupLayout.PREFERRED_SIZE,
																(int) (width * 0.18),
																GroupLayout.PREFERRED_SIZE)
														.add((int) (width * 0.01),
																(int) (width * 0.01),
																(int) (width * 0.01))
														.add(button3,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE)
														.add((int) (width * 0.01),
																(int) (width * 0.01),
																(int) (width * 0.01))
														.add(comboBox,
																(int) (width * 0.08),
																(int) (width * 0.08),
																(int) (width * 0.08))
														.add((int) (width * 0.01),
																(int) (width * 0.01),
																(int) (width * 0.01))
														.add(comboBox2,
																(int) (width * 0.08),
																(int) (width * 0.08),
																(int) (width * 0.08))
														.add((int) (width * 0.01),
																(int) (width * 0.01),
																(int) (width * 0.01))
														.add(cityField,
																(int) (width * 0.07),
																(int) (width * 0.07),
																(int) (width * 0.07))
														.add((int) (width * 0.01),
																(int) (width * 0.01),
																(int) (width * 0.01))
														.add(button4,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE)
														.add((int) (width * 0.005),
																(int) (width * 0.005),
																(int) (width * 0.005))
														.add(label2,
																(int) (width * 0.23),
																(int) (width * 0.23),
																(int) (width * 0.23)))
												.add(GroupLayout.LEADING,
														scrollPane1,
														GroupLayout.DEFAULT_SIZE,
														(int) (width * 0.9),
														Short.MAX_VALUE)))
								.add(GroupLayout.TRAILING,
										layout.createSequentialGroup()
												.add((int) (width * 0.05))
												.add(progressBar,
														GroupLayout.PREFERRED_SIZE,
														(int) (width * 0.55),
														GroupLayout.PREFERRED_SIZE)
												.add((int) (width * 0.1))
												.add(button1,
														GroupLayout.PREFERRED_SIZE,
														110,
														GroupLayout.PREFERRED_SIZE)
												.add((int) (width * 0.1),
														(int) (width * 0.1),
														(int) (width * 0.1))
												.add(button2,
														GroupLayout.PREFERRED_SIZE,
														110,
														GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(31, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup()
				.add(layout
						.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(GroupLayout.CENTER)
								.add(layout
										.createParallelGroup(
												GroupLayout.BASELINE)
										.add(label1,
												GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE)
										.add(textField1,
												GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE))
								.add(layout
										.createParallelGroup(
												GroupLayout.LEADING, false)
										.add(button3, GroupLayout.DEFAULT_SIZE,
												45, Short.MAX_VALUE)
										.add(comboBox, 45, 45, 45)
										.add(comboBox2, 45, 45, 45)
										.add(cityField, 45, 45, 45)
										.add(button4, GroupLayout.DEFAULT_SIZE,
												45, Short.MAX_VALUE))
								.add(layout.createParallelGroup().add(label2,
										GroupLayout.DEFAULT_SIZE,
										(int) (height * 0.25), Short.MAX_VALUE)))
						.add((int) (width * 0.015), (int) (width * 0.015),
								(int) (width * 0.015))
						.add(scrollPane1, GroupLayout.DEFAULT_SIZE,
								(int) (height * 0.75),
								Short.MAX_VALUE)
						.add((int) (height * 0.03), (int) (height * 0.03),
								(int) (height * 0.03))
						.add(layout
								.createParallelGroup(GroupLayout.BASELINE)
								.add(progressBar, GroupLayout.PREFERRED_SIZE,
										45, GroupLayout.PREFERRED_SIZE)
								.add(button1, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.add(button2, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(72, Short.MAX_VALUE)));

	}

	private JComboBox comboBox2;
	private JComboBox comboBox;
	private JLabel label1;
	private JTextField textField1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JLabel label2;
	private JProgressBar progressBar;
	private PinyinThread thread;
	private JTextField cityField;

	public class PinyinThread extends Thread {
		int flag = 0;

		public PinyinThread(int flag) {
			super();
			this.flag = flag;
		}

		@Override
		public void run() {
			if (flag == 0)
				importPinyin();
			else
				cleanPinyin();
		}
		@SuppressWarnings("unchecked")
		private void importPinyin() {
			if (table1.getRowCount() < 1)
				return;
			progressBar.setVisible(true);
			progressBar.setIndeterminate(true);
			DefaultTableModel model = (DefaultTableModel) table1.getModel();
		
			Vector<Object> datas = model.getDataVector();
			Set<PinyinPlace> pinyinPlaces = new HashSet<PinyinPlace>();
			int l=datas.size();
			for (int i=0;i<l;i++) {
				Vector<Object> elementVector=(Vector<Object>)datas.elementAt(i);
				PinyinPlace place = new PinyinPlace((String)elementVector.elementAt(0),(String)elementVector.elementAt(1),(Integer)elementVector.elementAt(2));
				pinyinPlaces.add(place);
			}
			if (pinyinProdcessing == null)
				pinyinProdcessing = (PinyinTableProcessing) LoadApplicationContext
						.getInstance().getBean("pinyinProcessing");
			int count = pinyinProdcessing.insertPinyin(pinyinPlaces);
			progressBar.setVisible(false);
			JOptionPane.showMessageDialog(null, "成功导入拼音词" + count + "个");
		}

		private void cleanPinyin() {
			// 针对Pinyin进行清洗
			if (comboBox.getSelectedItem() == null
					|| comboBox2.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(null, "请选择适当的拼音文件进行清理");
				return;
			}
			if (pinyinFeatureCollection == null) {
				JOptionPane.showMessageDialog(null, "拼音属性集合为空");
				return;
			}
			progressBar.setVisible(true);
			progressBar.setIndeterminate(true);
			if (pinyinProdcessing == null)
				pinyinProdcessing = (PinyinTableProcessing) LoadApplicationContext
						.getInstance().getBean("pinyinProcessing");
			Set<PinyinPlace> pinyinPlaces = pinyinProdcessing
					.cleanPinyin(pinyinFeatureCollection,(String) comboBox
							.getSelectedItem(),(String) comboBox2
							.getSelectedItem(), cityField.getText());
			String[] columns = { "name", "pName", "cityCode" };
			Object[][] datas = ShpUtils.getTableData(pinyinPlaces.toArray(),
					PinyinPlace.class);
			DefaultTableModel model = new DefaultTableModel(datas, columns);
			table1.setModel(model);
			progressBar.setVisible(false);
		}

	}

}
