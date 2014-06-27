
package cn.gls.ui.component.operator;
import java.awt.*;
import javax.swing.*;
import org.geotools.feature.FeatureCollection;
import org.jdesktop.layout.GroupLayout;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.ui.component.MainPanel;
import cn.gls.ui.component.PlacePanel;


/**
 * 
 * @date 2012-8-8
 * @author "Daniel Zhang"
 * @update 2012-8-8
 * @description TODO
 *
 */
public class PlaceImportPanel extends MainPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String filePath;
    private FeatureCollection<SimpleFeatureType, SimpleFeature> features;
	public PlaceImportPanel(String filePath,FeatureCollection<SimpleFeatureType, SimpleFeature> features) {
		super();
		this.filePath=filePath;
		this.features=features;
		init();
		initComponents();
	}
	private void init(){
//		table=new JTable();
	}
	
	@SuppressWarnings("unused")
	private PlaceImportPanel(){
		this(null,null);
	}

	@SuppressWarnings("deprecation")
	private void initComponents() {		
		PlacePanel place=PlacePanel.instance(filePath);
		if(features!=null)
	       place.setFeatureCollection(features);
		splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,place.panel1,place.scroollPane);
		//======== this ========
	   setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
       splitPane1.disable();

		//======== splitPane1 ========
		{
			splitPane1.setAlignmentX(0.5F);
		}
		//-----PlacePanel-------
	
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.add(splitPane1, GroupLayout.DEFAULT_SIZE, (int)width, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.add(splitPane1, GroupLayout.DEFAULT_SIZE, (int)height, Short.MAX_VALUE)
		);
		Dimension dimension1 = new Dimension((int) (width * 0.6),
				(int) (height));
		Dimension dimension2=new Dimension((int)(width*0.4),(int)(height));
		place.panel1.setPreferredSize(dimension1);
		place.panel1.setMinimumSize(dimension1);
		place.panel1.setMaximumSize(dimension1);
		place.scroollPane.setMinimumSize(dimension2);
		place.scroollPane.setMaximumSize(dimension2);
		place.scroollPane.setPreferredSize(dimension2);
	}

	private JSplitPane splitPane1;

}
