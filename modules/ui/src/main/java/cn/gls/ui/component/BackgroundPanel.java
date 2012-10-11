package cn.gls.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import cn.gls.ui.dao.ConfigDao;
import cn.gls.ui.frame.MainFrame;

/**
 * 
 * @author "Daniel Zhang"
 * 
 */
public class BackgroundPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5419654428856923445L;

	public BackgroundPanel() {
		// // Dimension screenSize =
		// Toolkit.getDefaultToolkit().getScreenSize();
		// setBounds(0, 0, (int) screenSize.getWidth(),
		// (int) screenSize.getHeight());
		init();
	}

	private void init() {
		ImageIcon imageIcon = ConfigDao.instance().getConfig()
				.getBackgroundImage();
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension winSize = Toolkit.getDefaultToolkit().getScreenSize(); // 屏幕分辨率
		setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
		setLocation((winSize.width -(int)screenSize.getWidth()) / 2,
				(winSize.height -(int)screenSize.getHeight()) / 2);
	}

	public void paint(Graphics g) {
		super.paint(g);
		 Dimension bg = ConfigDao.instance().getConfig().getMainFrameDimension();
		ImageIcon imageIcon = ConfigDao.instance().getConfig()
				.getBackgroundImage();
		String copyright = ConfigDao.instance().getConfig().getCopyright();
		g.drawImage(imageIcon.getImage(), 0, 0, (int)bg.getWidth(),
				(int)bg.getHeight(), this);
		g.setFont(new Font("黑体", Font.PLAIN, 16));
		g.setColor(Color.GRAY);
		g.drawString(copyright, 10,(int)bg.getHeight()- 10);
		MainFrame.instance().repaint();
	}
}
