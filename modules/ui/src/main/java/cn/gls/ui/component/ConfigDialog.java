/*
 * Created by JFormDesigner on Tue Aug 14 16:27:21 CST 2012
 */

package cn.gls.ui.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import cn.gls.database.util.DBUtils;
import cn.gls.ui.frame.MainFrame;

/**
 * 
 * @date 2012-8-14
 * @author "Daniel Zhang"
 * @update 2012-8-14
 * @description 设置连接的数据库
 * 
 */
public class ConfigDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5608416724913402202L;
	private static ConfigDialog configDialog;

	public static ConfigDialog instance() {
		if (configDialog == null)
			configDialog = new ConfigDialog();
		return configDialog;
	}

	public ConfigDialog() {
		super(MainFrame.instance(), "链接数据库配置", true);
		initComponents();
	}

	private void testLinkDB(ActionEvent e) {
		if (dbs.getSelectedItem() != null && textField1.getText() != null&&!"".equalsIgnoreCase(textField1.getText())
				&& textField2.getText() != null&&!"".equalsIgnoreCase(textField2.getText()) && textField3.getText() != null
				&&!"".equalsIgnoreCase( textField3.getText())&& textField4.getText() != null&&!"".equalsIgnoreCase( textField4.getText()) && textField5.getText() != null&&!"".equalsIgnoreCase( textField5.getText())) {
			boolean flag = DBUtils.varificationLinkDB(
					(String) dbs.getSelectedItem(), textField1.getText(),
					textField2.getText(), textField3.getText(),
					textField5.getText(), textField4.getText());
			if (flag)
				JOptionPane.showMessageDialog(null, "测试成功,可以连接数据库");
			else
				JOptionPane.showMessageDialog(null, "测试失败,不能到达数据库");
		}

	}

	private void changeConfigDB(ActionEvent e) {
		if (dbs.getSelectedItem() != null && textField1.getText() != null&&!"".equalsIgnoreCase(textField1.getText())
				&& textField2.getText() != null&&!"".equalsIgnoreCase(textField2.getText()) && textField3.getText() != null
				&&!"".equalsIgnoreCase( textField3.getText())&& textField4.getText() != null&&!"".equalsIgnoreCase( textField4.getText()) && textField5.getText() != null&&!"".equalsIgnoreCase( textField5.getText())) {
			boolean flag = DBUtils.changeConfigDB(
					(String) dbs.getSelectedItem(), textField1.getText(),
					textField2.getText(), textField3.getText(),
					textField5.getText(), textField4.getText());
			if (flag){
				JOptionPane.showMessageDialog(null, "更新数据库配置成功");
				this.setVisible(!flag);
			}
			else {
				JOptionPane.showMessageDialog(null, "更新数据库配置失败");
			}
		}
	}

	private void initComponents() {
		setLayout(null);
		setSize(400, 420);
		setLocationRelativeTo(null);
		configDialog = this;
		label1 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		textField2 = new JTextField();
		label3 = new JLabel();
		textField3 = new JTextField();
		label4 = new JLabel();
		textField4 = new JTextField();
		label5 = new JLabel();
		textField5 = new JTextField();
		button1 = new JButton();
		button2 = new JButton();
		dbs = new JComboBox();
		label0 = new JLabel();
		dbs.addItem("PostgreSQL");
		dbs.addItem("Oracle");

		// ======== this ========
		Container contentPane = getContentPane();
		// -----------label0---------
		label0.setText("选择数据库类型：");
		// ---- label1 ----
		label1.setText("\u6570\u636e\u5e93\u5730\u5740\uff1a");

		// ---- label2 ----
		label2.setText("\u6570\u636e\u5e93\u540d\u79f0\uff1a");

		// ---- label3 ----
		label3.setText("\u6570\u636e\u5e93\u7528\u6237\u540d\uff1a");

		// ---- label4 ----
		label4.setText("\u6570\u636e\u5e93\u7aef\u53e3\uff1a");

		// ---- label5 ----
		label5.setText("\u6570\u636e\u5e93\u7528\u6237\u5bc6\u7801\uff1a");

		// ---- button1 ----
		button1.setText("\u6d4b\u8bd5\u8fde\u63a5");
		button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				testLinkDB(e);

			}
		});

		// ---- button2 ----
		button2.setText("\u786e\u5b9a\u8fde\u63a5");
		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				changeConfigDB(e);

			}
		});

		// ------

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout
				.setHorizontalGroup(contentPaneLayout
						.createParallelGroup()
						.add(contentPaneLayout
								.createSequentialGroup()
								.add(55, 55, 55)
								.add(contentPaneLayout
										.createParallelGroup()
										.add(contentPaneLayout
												.createParallelGroup(
														GroupLayout.TRAILING,
														false)
												.add(label0,
														GroupLayout.DEFAULT_SIZE,
														131, Short.MAX_VALUE)
												.add(label3,
														GroupLayout.DEFAULT_SIZE,
														131, Short.MAX_VALUE)
												.add(GroupLayout.LEADING,
														label1,
														GroupLayout.DEFAULT_SIZE,
														131, Short.MAX_VALUE)
												.add(GroupLayout.LEADING,
														label2,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.add(label4,
														GroupLayout.DEFAULT_SIZE,
														131, Short.MAX_VALUE))
										.add(label5,
												GroupLayout.PREFERRED_SIZE,
												153, GroupLayout.PREFERRED_SIZE))
								.add(27, 27, 27)
								.add(contentPaneLayout
										.createParallelGroup(
												GroupLayout.LEADING, false)
										.add(dbs)
										.add(textField1)
										.add(textField2)
										.add(textField4)
										.add(textField3)
										.add(textField5,
												GroupLayout.PREFERRED_SIZE,
												176, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(146, Short.MAX_VALUE))
						.add(contentPaneLayout
								.createSequentialGroup()
								.add(111, 111, 111)
								.add(button1)
								.addPreferredGap(LayoutStyle.RELATED, 119,
										Short.MAX_VALUE).add(button2)
								.add(113, 113, 113)));
		contentPaneLayout.setVerticalGroup(contentPaneLayout
				.createParallelGroup()
				.add(contentPaneLayout
						.createSequentialGroup()
						.add(67, 67, 67)
						.add(contentPaneLayout
								.createParallelGroup(GroupLayout.BASELINE)
								.add(contentPaneLayout
										.createSequentialGroup()
										.add(dbs, GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
										.add(30, 30, 30)
										.add(textField1,
												GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
										.add(30, 30, 30)
										.add(textField2,
												GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
										.add(30, 30, 30)
										.add(textField4,
												GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
										.add(30, 30, 30)
										.add(textField3,
												GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
										.add(30,30,30)
										.add(textField5,
												GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE))
								.add(contentPaneLayout.createSequentialGroup()
										.add(label0, GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
												.add(30, 30, 30)
										.add(label1, GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
												.add(30,30,30)
										.add(label2, GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
												.add(30,30,30)
										.add(label4, GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
												.add(30,30,30)
										.add(label3, GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)
												.add(30,30,30)
										.add(label5, GroupLayout.PREFERRED_SIZE,
												45,
												GroupLayout.PREFERRED_SIZE)))
						.add(61, 61, 61)
						.add(contentPaneLayout
								.createParallelGroup(GroupLayout.BASELINE)
								.add(button1).add(button2))
						.addContainerGap(81, Short.MAX_VALUE)));
		pack();
		setLocationRelativeTo(null);
	}

	private JComboBox dbs;
	private JLabel label0;
	private JLabel label1;
	private JTextField textField1;
	private JLabel label2;
	private JTextField textField2;
	private JLabel label3;
	private JTextField textField3;
	private JLabel label4;
	private JTextField textField4;
	private JLabel label5;
	private JTextField textField5;
	private JButton button1;
	private JButton button2;

	public void open() {
		setVisible(true);
	}
}
