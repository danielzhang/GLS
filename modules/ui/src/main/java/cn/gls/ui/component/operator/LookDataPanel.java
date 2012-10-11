package cn.gls.ui.component.operator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import cn.gls.ui.component.ImageButton;
import cn.gls.ui.component.MainPanel;
import cn.gls.ui.frame.MainFrame;
import cn.gls.util.ShpUtils;
import cn.gls.util.UIUtils;

/**
 * 
 * @date 2012-7-31
 * @author "Daniel Zhang"
 * @update 2012-7-31
 * @description 查看文件数据
 *
 */
public class LookDataPanel extends MainPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6479464113360603566L;

	public LookDataPanel() {
	     super();
		initComponents();
	}
	String filePath;

	/**
	 * 查看原文件
	 * 
	 * @param e
	 */
	private void button1ActionPerformed(ActionEvent e) {
         JFileChooser fileChooser=UIUtils.getFileChooser();
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
		//填充清理的选项
	MainPanel mainPanel=	MainPanel.instance().showcleanData(filePath);
	MainFrame.instance().getContentPane().remove(MainFrame.instance().getContentPane().getComponent(2));
	MainFrame.instance().getContentPane().add(mainPanel, BorderLayout.CENTER);
	//这个很重要
	MainFrame.instance().setVisible(true);
	}
/**
 * 初始化组件
 */
	private void initComponents() {
	
		panel1 = this;
		label1 = new JLabel();
		button1 = new ImageButton("chooser");
		textField1 = new JTextField();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		button3 = new ImageButton("clean");
		button2 = new ImageButton("cancel");
 
		//======== panel1 ========
		{

			panel1.setEnabled(false);
			panel1.setFont(font);
			panel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panel1.setComponentPopupMenu(null);
           panel1.setPreferredSize(super.getPreferredSize());
			//---- label1 ----
			label1.setText("\u9009\u62e9\u6587\u4ef6:");
			label1.setFont(font);
            textField1.setFont(textFont);
			//---- button1 ----
			
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			//======== scrollPane1 ========
			{
				table1.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
				table1.setBackground(Color.lightGray);				
				scrollPane1.setViewportView(table1);
			    panel1.revalidate();
			}

			//---- button3 ----
			button3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button3ActionPerformed(e);
				}
			});

			//---- button2 ----
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button2ActionPerformed(e);
				}
			});
			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.add(panel1Layout.createSequentialGroup()
						.add((int)(width*0.035), (int)(width*0.035), (int)(width*0.035))
						.add(panel1Layout.createParallelGroup()
							.add(panel1Layout.createSequentialGroup()
								.add(label1,GroupLayout.PREFERRED_SIZE,
										(int) (width * 0.13),
										GroupLayout.PREFERRED_SIZE)
								.add((int)(width*0.025), (int)(width*0.025), (int)(width*0.025))
								.add(textField1, GroupLayout.PREFERRED_SIZE, (int)(width*0.33), GroupLayout.PREFERRED_SIZE)
									.add((int)(width*0.025), (int)(width*0.025), (int)(width*0.025))
								.add(button1,GroupLayout.PREFERRED_SIZE,110,GroupLayout.PREFERRED_SIZE))
							.add(scrollPane1, GroupLayout.DEFAULT_SIZE, (int)(width*0.87),  Short.MAX_VALUE))
						.addContainerGap())
					.add(GroupLayout.TRAILING,panel1Layout.createSequentialGroup()
				        .add((int)(width*0.48),(int)(width*0.48),(int)(width*0.48))
						.add(button3,GroupLayout.PREFERRED_SIZE,110,GroupLayout.PREFERRED_SIZE)
						.add((int)(width*0.1), (int)(width*0.1), (int)(width*0.1))
						.add(button2,GroupLayout.PREFERRED_SIZE,110,GroupLayout.PREFERRED_SIZE)
						)
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.add(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.add(panel1Layout.createParallelGroup(GroupLayout.CENTER)
							.add(label1, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
							.add(textField1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.add(button1, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.RELATED)
						.add(scrollPane1, GroupLayout.DEFAULT_SIZE,(int)(height*0.8),Short.MAX_VALUE)
						.add((int)(width*0.015), (int)(width*0.015), (int)(width*0.015))
						.add(panel1Layout.createParallelGroup()
							.add(button2, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
							.add(button3, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
						.add((int)(width*0.015), (int)(width*0.015), (int)(width*0.015)))
			);
		}
	
	}


	private JLabel label1;
	private ImageButton button1;
	private JTextField textField1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private ImageButton button3;
	private ImageButton button2;
	private LookDataPanel panel1;
//	private 
	public static void main(String[] args) {
		LookDataPanel checkFrame = new LookDataPanel();
		checkFrame.setVisible(true);
	}
}
