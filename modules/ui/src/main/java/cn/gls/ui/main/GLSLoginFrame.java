
package cn.gls.ui.main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

/**
 * 
 * @author "Daniel Zhang"
 * 
 */
public class GLSLoginFrame {
	public GLSLoginFrame() {
		initComponents();
		init();
	}

	private Map<String, String> users = new HashMap<String, String>() {
		{
			put("admin", "zhangdp");
		}
	};

	private void init() {
		jlpic = new JLabel();
		ImageIcon icon = new ImageIcon();
		BufferedImage image = null;
		try {
			String path = "/img/login.png";
			File file = new File(GLSLoginFrame.class.getResource(path)
					.getFile());
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		icon.setImage(image);
		jlpic.setBounds(0, 0, this.loginFrame.getWidth(),
				this.loginFrame.getHeight());
		jlpic.setHorizontalAlignment(0);
		jlpic.setIcon(icon);
		this.loginFrame.add(jlpic);
		this.loginFrame.pack();
		// this.loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * 登陆
	 * 
	 * @param e
	 */
	private void button1ActionPerformed(ActionEvent e) {
		if (!users.containsKey(textField1.getText())) {
			JOptionPane.showMessageDialog(null, "用户不存在，请重新输入。");
			return;
		} else {
			String userName = textField1.getText();
			@SuppressWarnings("deprecation")
			String password = passwordField1.getText();
			if (password.equalsIgnoreCase(users.get(userName))) {
				// 进入主页面
			} else {
				// 提示密码错误
				JOptionPane.showMessageDialog(null, "输入密码错误。");
				return;

			}
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - daniel zhang
		loginFrame = new JFrame();
		textField1 = new JTextField();
		button1 = new JButton();
		label1 = new JLabel();
		label2 = new JLabel();
		passwordField1 = new JPasswordField();
		button2 = new JButton();

		//======== loginFrame ========
		{
			loginFrame.setTitle("GLS2012");
			loginFrame.setIconImage(new ImageIcon("D:\\Dropbox\\Photos\\\u7167\u7247\\IMG_8608_picself_1.jpg").getImage());
			loginFrame.setBackground(new Color(255, 0, 153));
			
			loginFrame.setForeground(Color.darkGray);
			loginFrame.setResizable(false);
			loginFrame.setFont(new Font("Dialog", Font.PLAIN, 16));

			loginFrame.setAlwaysOnTop(true);
			Container loginFrameContentPane = loginFrame.getContentPane();

			//---- textField1 ----
			textField1.setBackground(new Color(230, 228, 227));
			textField1.setHorizontalAlignment(SwingConstants.CENTER);

			//---- button1 ----
			button1.setText("\u767b\u9646");
			button1.setBackground(new Color(236, 229, 224));
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			//---- label1 ----
			label1.setText("text");
			label1.setIcon(new ImageIcon(getClass().getResource("/img/touxiang.png")));

			//---- label2 ----
			label2.setText("GLS   2012");
			label2.setVerticalAlignment(SwingConstants.TOP);
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			label2.setForeground(Color.blue);
			label2.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 20));

			//---- passwordField1 ----
			passwordField1.setBackground(new Color(230, 228, 227));

			//---- button2 ----
			button2.setText("\u53d6\u6d88");

			GroupLayout loginFrameContentPaneLayout = new GroupLayout(loginFrameContentPane);
			loginFrameContentPane.setLayout(loginFrameContentPaneLayout);
			loginFrameContentPaneLayout.setHorizontalGroup(
				loginFrameContentPaneLayout.createParallelGroup()
					.add(loginFrameContentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.add(loginFrameContentPaneLayout.createParallelGroup()
							.add(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.add(loginFrameContentPaneLayout.createSequentialGroup()
								.add(17, 17, 17)
								.add(loginFrameContentPaneLayout.createParallelGroup(GroupLayout.LEADING, false)
									.add(loginFrameContentPaneLayout.createSequentialGroup()
										.add(label1, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
										.add(52, 52, 52)
										.add(button1)
										.addPreferredGap(LayoutStyle.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.add(button2)
										.add(33, 33, 33))
									.add(passwordField1, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
									.add(textField1, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
								.add(0, 16, Short.MAX_VALUE)))
						.addContainerGap())
			);
			loginFrameContentPaneLayout.setVerticalGroup(
				loginFrameContentPaneLayout.createParallelGroup()
					.add(loginFrameContentPaneLayout.createSequentialGroup()
						.add(label2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED, 57, Short.MAX_VALUE)
						.add(textField1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.add(28, 28, 28)
						.add(passwordField1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.add(53, 53, 53)
						.add(loginFrameContentPaneLayout.createParallelGroup()
							.add(label1, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.add(loginFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
								.add(button1)
								.add(button2)))
						.add(23, 23, 23))
			);
			loginFrame.setSize(420, 380);
			loginFrame.setLocationRelativeTo(loginFrame.getOwner());
		}
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - daniel zhang
	private JFrame loginFrame;
	private JTextField textField1;
	private JButton button1;
	private JLabel label1;
	private JLabel label2;
	private JPasswordField passwordField1;
	private JButton button2;
	// JFormDesigner - End of variables declaration //GEN-END:variables
	private JLabel jlpic;

	public static void main(String[] args) {
		GLSLoginFrame frame = new GLSLoginFrame();
		frame.loginFrame.setVisible(true);
	}
}
