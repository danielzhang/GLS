package cn.gls.ui.frame;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

public class LoadingFrame extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8236850107130802140L;
	private static LoadingFrame loadingFrame;

	public static LoadingFrame instance() {
		if (loadingFrame == null)
			loadingFrame = new LoadingFrame();
		return loadingFrame;
	}

	public LoadingFrame() {
		super("GLS空间定位服务");
		setSize(300, 300);
		loadingFrame = this;
		setUndecorated(true);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		AWTUtilities.setWindowOpaque(this, false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(this.getClass().getResource(
				"/img/icon.png")).getImage());
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		ImageIcon bg = new ImageIcon(this.getClass().getResource(
				"/img/loading.png"));
		g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
	}


	public void run() {
		instance();
	}
}
