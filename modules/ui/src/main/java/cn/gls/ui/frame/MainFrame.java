package cn.gls.ui.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import cn.gls.ui.component.BackgroundPanel;
import cn.gls.ui.component.JLDFrame;
import cn.gls.ui.component.MainPanel;
import cn.gls.ui.component.MenuPanel;
import cn.gls.ui.component.OptionPanel;
import cn.gls.ui.dao.ConfigDao;

/**
 * 
 * @date 2012-8-10
 * @author "Daniel Zhang"
 * @update 2012-8-10
 * @description 主题界面
 * 
 */
public class MainFrame extends JLDFrame implements Runnable {
	private static final long serialVersionUID = -5988513125942516733L;
	private static MainFrame mainFrame;

	public static MainFrame instance() {
		if (mainFrame == null)
			mainFrame = new MainFrame();
		return mainFrame;
	}

	public MainFrame() {
		super();
		mainFrame = this;
		setTitle("GLS系统");
		setUndecorated(true);
		Dimension dimension = ConfigDao.instance().getConfig()
				.getMainFrameDimension();
		setMinimumSize(dimension);
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.add(new OptionPanel(), BorderLayout.NORTH);
		container.add(new MenuPanel(), BorderLayout.WEST);
		container.add(MainPanel.instance(), BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(this.getClass().getResource("/img/icon.png"))
				.getImage());
		((JPanel) this.getContentPane()).setOpaque(false);
		BackgroundPanel background = new BackgroundPanel();
		getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		setLocationRelativeTo(null);
	}

	/** 属性配置文件的位置 */
	private static final String DEFAULT_STANDARD_PATH = "conf/ui.properties";
	private static Properties uiProperties = new Properties();
	static {
		try {
			InputStream in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(DEFAULT_STANDARD_PATH);
			uiProperties.load(in);
		} catch (IOException e) {
			throw new IllegalStateException("Could not load 'ui.properties': "
					+ e.getMessage());
		}
	}

	public Properties getProperties() {
		return uiProperties;
	}

	public void refresh() {
		// mainFrame = new MainFrame();
	}

	public void open() {
		setVisible(true);
		LoginFrame.instance().setVisible(false);
		LoginFrame.getLoginDialog().setVisible(false);
	}

	public void run() {
		instance();
	}

	public static void main(String[] args) {
		MainFrame frame = MainFrame.instance();
		frame.open();
	}
}
