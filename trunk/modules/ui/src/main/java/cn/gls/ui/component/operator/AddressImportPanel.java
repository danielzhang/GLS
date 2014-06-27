package cn.gls.ui.component.operator;

import java.awt.ComponentOrientation;
import java.awt.Dimension;


import javax.swing.*;
import org.jdesktop.layout.GroupLayout;

import cn.gls.ui.component.AddressPanel;
import cn.gls.ui.component.MainPanel;


/**
 * @author daniel zhang
 */
public class AddressImportPanel extends MainPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036317186136587012L;

	public AddressImportPanel() {
		super();
		initComponents();
	}

	@SuppressWarnings("deprecation")
	private void initComponents() {
		AddressPanel address = AddressPanel.instance();
		splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false ,address.panel1,address.scrollPane);
        splitPane1.setBackground(this.getBackground());
		// ======== this ========
		splitPane1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		splitPane1.disable();
		{
			splitPane1.setAlignmentX(0.5F);
		}
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup().add(splitPane1,
						 GroupLayout.DEFAULT_SIZE, (int) width,
						 Short.MAX_VALUE)

		);
		layout.setVerticalGroup(layout.createParallelGroup().add(splitPane1,
				GroupLayout.DEFAULT_SIZE, (int) height,
				 Short.MAX_VALUE));

		Dimension dimension1 = new Dimension((int) (width * 0.5),
				(int)(height));
//		Dimension dimension2=new Dimension((int)(width*0.5),(int)(height));
		address.panel1.setPreferredSize(dimension1);
		address.panel1.setMinimumSize(dimension1);
		address.panel1.setMaximumSize(dimension1);
		address.scrollPane.setMinimumSize(dimension1);
		address.scrollPane.setMaximumSize(dimension1);
		address.scrollPane.setPreferredSize(dimension1);
	
	}

	private JSplitPane splitPane1;

}
