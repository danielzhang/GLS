package cn.gls.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import cn.gls.ui.component.operator.AddressImportPanel;
import cn.gls.ui.component.operator.CleanDataPanel;
import cn.gls.ui.component.operator.CreateIndexPanel;
import cn.gls.ui.component.operator.CreateTablePanel;
import cn.gls.ui.component.operator.FatherAndSonPanel;
import cn.gls.ui.component.operator.LookDataPanel;
import cn.gls.ui.component.operator.PinyinPanel;
import cn.gls.ui.component.operator.PlaceImportPanel;
import cn.gls.ui.dao.ConfigDao;

/**
 * 
 * @date 2012-8-6
 * @author "Daniel Zhang"
 * @update 2012-8-6
 * @description 各个Panel的模块,工厂模式
 * 
 */

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 4090036947302273308L;
	private static MainPanel instance;

	private String operatorType = "lookData";

	public String getOperatorType() {
		return operatorType;
	}

	private void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	private JScrollPane jscrolPane;

	protected static final Font font = ConfigDao.instance().getConfig()
			.getFont();
	protected static final Font textFont = ConfigDao.instance().getConfig()
			.getTextFont();
	protected static final Font descFont = ConfigDao.instance().getConfig()
			.getDescFont();
	protected int width = (int) ConfigDao.instance().getConfig()
			.getMainPanelDimension().getWidth();
	protected int height = (int) ConfigDao.instance().getConfig()
			.getMainPanelDimension().getHeight();

	public static MainPanel instance() {
		if (instance == null) {
			instance = new LookDataPanel();
		}
		return instance;
	}

	private void createPlaceImportPanel(String filePath,
			FeatureCollection<SimpleFeatureType, SimpleFeature> features) {
		instance = new PlaceImportPanel(filePath, features);
		instance.setVisible(true);
		instance.setOperatorType("placeImport");
	}

	private void createLookDataPanel() {
		instance = new LookDataPanel();
		instance.setVisible(true);
		instance.setOperatorType("lookData");
	}

	private void createCleanDataPanel(String filePath) {
		instance = new CleanDataPanel(filePath);
		instance.setVisible(true);
		instance.setOperatorType("cleanData");
	}

	private void createFatherAndSonPanel() {

		instance = new FatherAndSonPanel();
		instance.setVisible(true);
		instance.setOperatorType("fatherAndSon");
	}

	private void createPinyinPanel() {
		instance = new PinyinPanel();
		instance.setVisible(true);
		instance.setOperatorType("pinyinImport");
	}

	private void createAddressImportPanel() {
		instance = new AddressImportPanel();
		instance.setVisible(true);
		instance.setOperatorType("addressImport");
	}

	private void createTable() {
		instance = new CreateTablePanel();
		instance.setVisible(true);
		instance.setOperatorType("createTableType");
	}

	private void createTableIndex() {
		instance = new CreateIndexPanel();
		instance.setVisible(true);
		instance.setOperatorType("createIndexType");
	}

	public MainPanel() {
		Dimension dimension = ConfigDao.instance().getConfig()
				.getMainPanelDimension();
		setPreferredSize(dimension);
		revalidate();
		// 设置背景色
		setBackground(new Color(255, 200, 100));
		jscrolPane = new JScrollPane();
		jscrolPane.setBorder(null);
		jscrolPane.setOpaque(false);
		jscrolPane.getViewport().setOpaque(false);
		add(jscrolPane);
	}

	@Override
	public void paint(Graphics g) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(
				"/img/scrollpane.png"));
		Image img = icon.getImage();
		g.drawImage(img, jscrolPane.getX(), jscrolPane.getY(),
				jscrolPane.getWidth(), jscrolPane.getHeight(), this);
		super.paint(g);
	}

	public MainPanel showPlaceImport(String filePath,
			FeatureCollection<SimpleFeatureType, SimpleFeature> features) {
		createPlaceImportPanel(filePath, features);
		return instance;
	}

	public MainPanel showlookData() {
		createLookDataPanel();
		return instance;
	}

	public MainPanel showCreateTable() {
		createTable();
		return instance;
	}

	public MainPanel showCreateIndex() {
		createTableIndex();
		return instance;
	}

	public MainPanel showcleanData(String filePath) {
		createCleanDataPanel(filePath);
		return instance;
	}

	public MainPanel showfatherAndSonImport() {
		createFatherAndSonPanel();
		return instance;
	}

	public MainPanel showpinyinImport() {
		createPinyinPanel();
		return instance;
	}

	public MainPanel showaddressImport() {
		createAddressImportPanel();
		return instance;
	}

	public void refresh() {
		if (operatorType.equals("placeImport")) {
			showPlaceImport(null, null);
		} else if (operatorType.equals("lookData")) {
			showlookData();
		} else if (operatorType.equals("cleanData")) {
			showcleanData(null);
		} else if (operatorType.equals("fatherAndSon")) {
			showfatherAndSonImport();
		} else if (operatorType.equals("pinyinImport")) {
			showpinyinImport();
		} else if (operatorType.equalsIgnoreCase("addressImport")) {
			showaddressImport();
		} else if ("createTable".equalsIgnoreCase(operatorType)) {
			createTable();
		} else if ("createIndex".equalsIgnoreCase(operatorType)) {
			createTableIndex();
		}
	}
}
