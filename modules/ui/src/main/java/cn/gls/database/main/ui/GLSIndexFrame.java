/*
 * Created by JFormDesigner on Mon Jul 30 09:10:37 CST 2012
 */

package cn.gls.database.main.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jdesktop.layout.GroupLayout;

/**
 * @author daniel zhang
 */
public class GLSIndexFrame {
	public GLSIndexFrame() {
		initComponents();
	}

	/**
	 * 查看数据原文件
	 * 
	 * @param e
	 */
	private void menuItem5ActionPerformed(ActionEvent e) {

	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - daniel zhang
		GLSFrame = new JFrame();
		menuBar1 = new JMenuBar();
		placeImport = new JMenu();
		menuItem1 = new JMenuItem();
		menuItem3 = new JMenuItem();
		menuItem4 = new JMenuItem();
		menu1 = new JMenu();
		menuItem2 = new JMenuItem();
		menu2 = new JMenu();
		menuItem5 = new JMenuItem();
		menu3 = new JMenu();
		menuItem6 = new JMenuItem();
		label1 = new JLabel();

		// ======== GLSFrame ========
		{
			Container GLSFrameContentPane = GLSFrame.getContentPane();

			// ======== menuBar1 ========
			{

				// ======== placeImport ========
				{
					placeImport.setText("\u5730\u540d\u8bcd\u5bfc\u5165");

					// ---- menuItem1 ----
					menuItem1.setText("\u6807\u51c6\u8bcd\u5bfc\u5165");
					placeImport.add(menuItem1);

					// ---- menuItem3 ----
					menuItem3.setText("\u7236\u5b50\u7c7b\u5bfc\u5165");
					placeImport.add(menuItem3);

					// ---- menuItem4 ----
					menuItem4.setText("\u62fc\u97f3\u8bcd\u5bfc\u5165");
					placeImport.add(menuItem4);
				}
				menuBar1.add(placeImport);

				// ======== menu1 ========
				{
					menu1.setText("\u5730\u5740\u5bfc\u5165");

					// ---- menuItem2 ----
					menuItem2.setText("\u6807\u51c6\u5730\u5740\u5bfc\u5165");
					menu1.add(menuItem2);
				}
				menuBar1.add(menu1);

				// ======== menu2 ========
				{
					menu2.setText("\u67e5\u770b");

					// ---- menuItem5 ----
					menuItem5.setText("\u67e5\u770b\u539f\u6587\u4ef6");
					menuItem5.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							menuItem5ActionPerformed(e);
						}
					});
					menu2.add(menuItem5);
				}
				menuBar1.add(menu2);

				// ======== menu3 ========
				{
					menu3.setText("\u6e05\u7406");

					// ---- menuItem6 ----
					menuItem6.setText("\u6e05\u7406\u6570\u636e");
					menu3.add(menuItem6);
				}
				menuBar1.add(menu3);
			}
			GLSFrame.setJMenuBar(menuBar1);

			// ---- label1 ----
			label1.setHorizontalAlignment(SwingConstants.LEFT);
			label1.setText("\u8f6f\u4ef6\u8bf4\u660e");
			label1.setVerticalAlignment(SwingConstants.TOP);

			GroupLayout GLSFrameContentPaneLayout = new GroupLayout(
					GLSFrameContentPane);
			GLSFrameContentPane.setLayout(GLSFrameContentPaneLayout);
			GLSFrameContentPaneLayout
					.setHorizontalGroup(GLSFrameContentPaneLayout
							.createParallelGroup().add(
									GLSFrameContentPaneLayout
											.createSequentialGroup()
											.add(32, 32, 32)
											.add(label1,
													GroupLayout.DEFAULT_SIZE,
													560, Short.MAX_VALUE)
											.addContainerGap()));
			GLSFrameContentPaneLayout
					.setVerticalGroup(GLSFrameContentPaneLayout
							.createParallelGroup().add(
									GLSFrameContentPaneLayout
											.createSequentialGroup()
											.addContainerGap()
											.add(label1,
													GroupLayout.PREFERRED_SIZE,
													514,
													GroupLayout.PREFERRED_SIZE)
											.addContainerGap(134,
													Short.MAX_VALUE)));
			GLSFrame.pack();
			GLSFrame.setLocationRelativeTo(GLSFrame.getOwner());
		}
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - daniel zhang
	private JFrame GLSFrame;
	private JMenuBar menuBar1;
	private JMenu placeImport;
	private JMenuItem menuItem1;
	private JMenuItem menuItem3;
	private JMenuItem menuItem4;
	private JMenu menu1;
	private JMenuItem menuItem2;
	private JMenu menu2;
	private JMenuItem menuItem5;
	private JMenu menu3;
	private JMenuItem menuItem6;
	private JLabel label1;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
