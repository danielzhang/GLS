/*
 * Created by JFormDesigner on Sun Jul 29 13:58:07 CST 2012
 */

package cn.gls.database.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.component.JRIATextField;
import cn.gls.database.data.DataType;
import cn.gls.database.postgis.standard.PostGISOperator;
import cn.gls.database.shp.util.ShpUtils;
import cn.gls.database.util.FeaturePreProcessing;
import cn.gls.database.util.UIUtils;

/**
 * @author daniel zhang
 */
public class AddressImportFrame extends JFrame {
    public AddressImportFrame() {
        initComponents();
    }

    private String filePath;

    private String quyufilePath;

    private FeaturePreProcessing featureProcessing;
    private FeatureCollection<SimpleFeatureType,SimpleFeature> featureCollection;
    private FeatureCollection<SimpleFeatureType,SimpleFeature> countyFeatureCollection;

    public FeatureCollection<SimpleFeatureType, SimpleFeature> getFeatureCollection() {
		return featureCollection;
	}

	public void setFeatureCollection(
			FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection) {
		this.featureCollection = featureCollection;
	}

	private void button1ActionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(new File("D:\\"));
        fileChooser.addChoosableFileFilter(UIUtils.filter);
        int restatus = fileChooser.showOpenDialog(this);

        if (restatus == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getPath();
            textField1.setText(fileChooser.getSelectedFile().getPath());

            // 设置每一个字段的字段名
            // 首先获得Shpfile
            SimpleFeatureType featureType = ShpUtils
                    .getSimpleFeatureTypeByShp(textField1.getText());
            List<AttributeDescriptor> attributes = featureType
                    .getAttributeDescriptors();
            fields.clear();
            for (AttributeDescriptor attribute : attributes) {
               
                if (attribute instanceof GeometryDescriptor)
                    continue;
                fields.add(attribute.getName().toString());
            }

        } else {
            JOptionPane.showMessageDialog(null, "请选择正确的文件格式", "Tile",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<String> fields = new ArrayList<String>();

    public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	private void rIATextField1FocusGained(FocusEvent e) {
        rIATextField1.setHistory(fields);
    }

    private void rIATextField2FocusGained(FocusEvent e) {
        rIATextField2.setHistory(fields);
    }

    private void rIATextField3FocusGained(FocusEvent e) {
        rIATextField3.setHistory(fields);
    }

    private void rIATextField4FocusGained(FocusEvent e) {
        rIATextField4.setHistory(fields);
    }

    private void rIATextField5FocusGained(FocusEvent e) {
        rIATextField5.setHistory(fields);
    }

    private void rIATextField6FocusGained(FocusEvent e) {
        rIATextField6.setHistory(fields);
    }

    private void rIATextField7FocusGained(FocusEvent e) {
        rIATextField7.setHistory(fields);
    }

    private void button2ActionPerformed(ActionEvent e) {
        // 导入数据
        if (rIATextField1.getText() == null && rIATextField2.getText() == null
                && rIATextField3.getText() == null
                && rIATextField4.getText() == null
                && rIATextField5.getText() == null
                && rIATextField6.getText() == null
                && rIATextField7.getText() == null) {
            JOptionPane.showMessageDialog(null, "请选择相对应的字段名", "Tile",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Map<String, String> fieldMap = new HashMap<String, String>();
        String dbTableName = null;
        int importType = DataType.CITY_TYPE;
        if (rIATextField1.getText() != null) {
            fieldMap.put("city_name", rIATextField1.getText());
            dbTableName = "cityTableName";
        }
        if (rIATextField2.getText() != null) {
            fieldMap.put("county_name", rIATextField2.getText());
            if (importType < DataType.POLITICAL_TYPE)
                importType = DataType.POLITICAL_TYPE;
            dbTableName = "politicalTableName";
        }
        if (rIATextField3.getText() != null) {
            fieldMap.put("town_name", rIATextField3.getText());
            if (importType < DataType.POLITICAL_TYPE)
                importType = DataType.POLITICAL_TYPE;
            dbTableName = "politicalTableName";
        }
        if (rIATextField4.getText() != null) {
            fieldMap.put("village_name", rIATextField4.getText());
            if (importType < DataType.POLITICAL_TYPE)
                importType = DataType.POLITICAL_TYPE;
            dbTableName = "politicalTableName";
        }
        if (rIATextField5.getText() != null) {
            fieldMap.put("street_name", rIATextField5.getText());
            if (importType < DataType.STREET_TYPE)
                importType = DataType.STREET_TYPE;
            dbTableName = "streetTableName";
        }
        if (rIATextField6.getText() != null) {
            fieldMap.put("comummitis_name", rIATextField6.getText());
            if (importType < DataType.ADDRESS_TYPE)
                importType = DataType.ADDRESS_TYPE;
            dbTableName = "poiTableName";
        }
        if (rIATextField7.getText() != null) {
            fieldMap.put("building_name", rIATextField7.getText());
            if (importType < DataType.ADDRESS_TYPE)
                importType = DataType.ADDRESS_TYPE;
            dbTableName = "poiTableName";
        }
        if (rIATextField8.getText() != null) {
            fieldMap.put("dbType", rIATextField8.getText());

        }
        if (rIATextField9.getText() != null) {
            fieldMap.put("poi_name", rIATextField9.getText());
            if (importType < DataType.ADDRESS_TYPE)
                importType = DataType.ADDRESS_TYPE;
            dbTableName = "poiTableName";
        }
        // 去导入数据
        initDataBaseEnv();
        PostGISOperator postGISOperator = new PostGISOperator(importType);
        FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = preProcessingData(importType);
        String dbType = fieldMap.get("dbType") == null ? null : fieldMap
                .get("dbType");
        sCityName = textField2.getText();
        postGISOperator.insert(featureCollection, fieldMap, dbTableName,
                dbType, sCityName);
    }

    private String sCityName;

    private LoadApplicationContext loadApplicationContext;

    private void initDataBaseEnv() {
        loadApplicationContext = LoadApplicationContext.getInstance();
        featureProcessing = (FeaturePreProcessing) LoadApplicationContext
                .getInstance().getBean("featureProcessing");
    }

    private FeatureCollection<SimpleFeatureType, SimpleFeature> preProcessingData(
            int type) {
        FeatureCollection<SimpleFeatureType, SimpleFeature> featureaCollection = null;
        switch (type) {
            case DataType.CITY_TYPE:
                featureaCollection = ShpUtils.readShpfile(filePath);
                break;

            case DataType.POLITICAL_TYPE:
                if (quyufilePath != null)
                    featureaCollection = preprocessingData(filePath,
                            quyufilePath, rIATextField2.getText(), sCityName);
                break;
            case DataType.STREET_TYPE:
                String filedName=rIATextField8.getText();
                featureaCollection=  unionFeature(filePath,filedName,quyufilePath,"Name");
                break;
            case DataType.ADDRESS_TYPE:
                
        }
        return featureaCollection;
    }

    private FeatureCollection<SimpleFeatureType, SimpleFeature> preprocessingData(
            String oShpName, String countyShpName, String countyFieldName,
            String cityName) {
        FeatureCollection<SimpleFeatureType, SimpleFeature> processingFeature = featureProcessing
                .preDATAProcessing(ShpUtils.readShpfile(oShpName),
                        ShpUtils.readShpfile(countyShpName), countyFieldName,
                        cityName);
        return processingFeature;
    }

    /**
     * @Param AddressImportFrame
     * @Description 合并要素
     * @return FeatureCollection<SimpleFeatureType,SimpleFeature>
     * @param shpName
     * @param fieldName
     * @param countyshpName
     * @param countyFieldName
     * @return
     */
    private FeatureCollection<SimpleFeatureType, SimpleFeature> unionFeature(
            String shpName, String fieldName, String countyshpName,
            String countyFieldName) {
        FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = ShpUtils
                .getFeatureSourceByShp(shpName);
        FeatureSource<SimpleFeatureType, SimpleFeature> countyFeatureSource = null;
        if (countyshpName != null)
            countyFeatureSource = ShpUtils.getFeatureSourceByShp(countyshpName);
        return featureProcessing.unionSimpleFeatures(featureSource, fieldName,
                countyFeatureSource, countyFieldName);

    }

    /**
     * @Param AddressImportFrame
     * @Description 取消按钮激发事件
     * @return void
     * @param e
     */
    private void button3ActionPerformed(ActionEvent e) {
        addressFrame.setVisible(false);
    }

    private void rIATextField9FocusGained(FocusEvent e) {
        rIATextField9.setHistory(fields);
    }

    private void rIATextField8FocusGained(FocusEvent e) {
        rIATextField8.setHistory(fields);
    }

    /**
     * @Param AddressImportFrame
     * @Description 选择区域文件
     * @return void
     * @param e
     */
    private void button4ActionPerformed(ActionEvent e) {
    	  JFileChooser fileChooser = new JFileChooser(new File("D:\\"));
          FileFilter fileFilter = new FileFilter() {

              @Override
              public String getDescription() {
                  // TODO Auto-generated method stub
                  return ".shp";
              }

              @Override
              public boolean accept(File f) {
                  String fileName = f.getName();

                  return fileName.endsWith(".shp");
              }
          };

          fileChooser.addChoosableFileFilter(fileFilter);

          int restatus = fileChooser.showOpenDialog(this);

          if (restatus == JFileChooser.APPROVE_OPTION) {
              quyufilePath = fileChooser.getSelectedFile().getPath();
              textField2.setText(fileChooser.getSelectedFile().getPath());
          } else {
              JOptionPane.showMessageDialog(null, "请选择正确的文件格式", "Tile",
                      JOptionPane.ERROR_MESSAGE);
          }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY
        // //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - daniel zhang
		addressFrame = new JFrame();
		label1 = new JLabel();
		textField1 = new JTextField();
		button1 = new JButton();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		button2 = new JButton();
		button3 = new JButton();
		progressBar1 = new JProgressBar();
		rIATextField1 = new JRIATextField();
		rIATextField2 = new JRIATextField();
		rIATextField3 = new JRIATextField();
		rIATextField4 = new JRIATextField();
		rIATextField5 = new JRIATextField();
		rIATextField6 = new JRIATextField();
		rIATextField7 = new JRIATextField();
		label9 = new JLabel();
		rIATextField8 = new JRIATextField();
		label10 = new JLabel();
		rIATextField9 = new JRIATextField();
		label11 = new JLabel();
		textField2 = new JTextField();
		label12 = new JLabel();
		textField3 = new JTextField();
		button4 = new JButton();

		//======== addressFrame ========
		{
			addressFrame.setMinimumSize(new Dimension(400, 550));
			addressFrame.setTitle("GLS\u5730\u5740\u5bfc\u5165");
			Container addressFrameContentPane = addressFrame.getContentPane();

			//---- label1 ----
			label1.setText("*\u9009\u62e9\u6587\u4ef6\uff1a");
			label1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 22));

			//---- button1 ----
			button1.setText("...");
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			//---- label2 ----
			label2.setText("\u57ce\u5e02\u5b57\u6bb5\uff1a");
			label2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- label3 ----
			label3.setText("\u533a\u53bf\u5b57\u6bb5\uff1a");
			label3.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- label4 ----
			label4.setText("\u4e61\u9547\u5b57\u6bb5\uff1a");
			label4.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- label5 ----
			label5.setText("\u6751\u5e84\u5b57\u6bb5\uff1a");
			label5.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- label6 ----
			label6.setText("\u9053\u8def\u8857\u9053\u5b57\u6bb5\uff1a");
			label6.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- label7 ----
			label7.setText("\u5c0f\u533a\u5b57\u6bb5\uff1a");
			label7.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- label8 ----
			label8.setText("\u5927\u53a6\u5b57\u6bb5\uff1a");
			label8.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- button2 ----
			button2.setText("\u786e\u5b9a");
			button2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button2ActionPerformed(e);
				}
			});

			//---- button3 ----
			button3.setText("\u53d6\u6d88");
			button3.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));
			button3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button3ActionPerformed(e);
				}
			});

			//---- rIATextField1 ----
			rIATextField1.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField1FocusGained(e);
				}
			});

			//---- rIATextField2 ----
			rIATextField2.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField2FocusGained(e);
				}
			});

			//---- rIATextField3 ----
			rIATextField3.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField3FocusGained(e);
				}
			});

			//---- rIATextField4 ----
			rIATextField4.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField4FocusGained(e);
				}
			});

			//---- rIATextField5 ----
			rIATextField5.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField5FocusGained(e);
				}
			});

			//---- rIATextField6 ----
			rIATextField6.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField6FocusGained(e);
				}
			});

			//---- rIATextField7 ----
			rIATextField7.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField7FocusGained(e);
				}
			});

			//---- label9 ----
			label9.setText("\u6570\u636e\u7c7b\u578b\uff1a");
			label9.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 20));

			//---- rIATextField8 ----
			rIATextField8.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField8FocusGained(e);
				}
			});

			//---- label10 ----
			label10.setText("POI\u5b57\u6bb5\uff1a");
			label10.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- rIATextField9 ----
			rIATextField9.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					rIATextField9FocusGained(e);
				}
			});

			//---- label11 ----
			label11.setText("\u57ce\u5e02\u540d\u79f0\uff1a");
			label11.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- label12 ----
			label12.setText("\u9009\u62e9\u533a\u57df\u6587\u4ef6");
			label12.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 18));

			//---- button4 ----
			button4.setText("...");
			button4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button4ActionPerformed(e);
				}
			});

			GroupLayout addressFrameContentPaneLayout = new GroupLayout(addressFrameContentPane);
			addressFrameContentPane.setLayout(addressFrameContentPaneLayout);
			addressFrameContentPaneLayout.setHorizontalGroup(
				addressFrameContentPaneLayout.createParallelGroup()
					.add(addressFrameContentPaneLayout.createSequentialGroup()
						.add(addressFrameContentPaneLayout.createParallelGroup()
							.add(addressFrameContentPaneLayout.createSequentialGroup()
								.addContainerGap()
								.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.TRAILING)
									.add(button3)
									.add(progressBar1, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)))
							.add(addressFrameContentPaneLayout.createSequentialGroup()
								.add(31, 31, 31)
								.add(addressFrameContentPaneLayout.createParallelGroup()
									.add(label9, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
									.add(addressFrameContentPaneLayout.createSequentialGroup()
										.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.TRAILING, false)
											.add(GroupLayout.LEADING, addressFrameContentPaneLayout.createSequentialGroup()
												.add(addressFrameContentPaneLayout.createParallelGroup()
													.add(label11)
													.add(label10, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
												.add(45, 45, 45)
												.add(addressFrameContentPaneLayout.createParallelGroup()
													.add(GroupLayout.TRAILING, rIATextField8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.add(addressFrameContentPaneLayout.createSequentialGroup()
														.add(button2)
														.add(0, 0, Short.MAX_VALUE))
													.add(textField2)
													.add(GroupLayout.TRAILING, rIATextField9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
											.add(GroupLayout.LEADING, addressFrameContentPaneLayout.createSequentialGroup()
												.add(label8, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(LayoutStyle.UNRELATED)
												.add(rIATextField7, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)))
										.add(26, 26, 26)
										.add(addressFrameContentPaneLayout.createParallelGroup()
											.add(button1)
											.add(button4)))
									.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.TRAILING, false)
										.add(GroupLayout.LEADING, addressFrameContentPaneLayout.createSequentialGroup()
											.add(label7)
											.add(50, 50, 50)
											.add(rIATextField6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.add(GroupLayout.LEADING, addressFrameContentPaneLayout.createSequentialGroup()
											.add(addressFrameContentPaneLayout.createParallelGroup()
												.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.TRAILING, false)
													.add(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.add(label12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.add(GroupLayout.LEADING, label2)
													.add(GroupLayout.LEADING, label3, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
													.add(GroupLayout.LEADING, label4, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
													.add(label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.add(label6, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
											.add(addressFrameContentPaneLayout.createParallelGroup()
												.add(addressFrameContentPaneLayout.createSequentialGroup()
													.add(12, 12, 12)
													.add(addressFrameContentPaneLayout.createParallelGroup()
														.add(rIATextField2, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
														.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.LEADING, false)
															.add(textField1, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
															.add(textField3, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
															.add(rIATextField1, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
														.add(rIATextField3, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
														.add(rIATextField4, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)))
												.add(GroupLayout.TRAILING, addressFrameContentPaneLayout.createSequentialGroup()
													.add(14, 14, 14)
													.add(rIATextField5, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))))))))
						.addContainerGap(60, Short.MAX_VALUE))
			);
			addressFrameContentPaneLayout.setVerticalGroup(
				addressFrameContentPaneLayout.createParallelGroup()
					.add(addressFrameContentPaneLayout.createSequentialGroup()
						.add(29, 29, 29)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.TRAILING)
							.add(button1)
							.add(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.add(43, 43, 43)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label12)
							.add(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(button4))
						.add(30, 30, 30)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label2)
							.add(rIATextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(28, 28, 28)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label3)
							.add(rIATextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(30, 30, 30)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label4, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.add(rIATextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label5)
							.add(rIATextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(23, 23, 23)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label6, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.add(rIATextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(30, 30, 30)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label7)
							.add(rIATextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(27, 27, 27)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label8)
							.add(rIATextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(23, 23, 23)
						.add(addressFrameContentPaneLayout.createParallelGroup()
							.add(GroupLayout.TRAILING, label10)
							.add(GroupLayout.TRAILING, rIATextField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(18, 18, 18)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label11)
							.add(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(26, 26, 26)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label9)
							.add(rIATextField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(32, 32, 32)
						.add(addressFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(button2)
							.add(button3))
						.add(18, 18, 18)
						.add(progressBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
			);
			addressFrame.pack();
			addressFrame.setLocationRelativeTo(null);
		}
        // //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY
    // //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - daniel zhang
	public JFrame addressFrame;
	private JLabel label1;
	public JTextField textField1;
	private JButton button1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JButton button2;
	private JButton button3;
	private JProgressBar progressBar1;
	private JRIATextField rIATextField1;
	private JRIATextField rIATextField2;
	private JRIATextField rIATextField3;
	private JRIATextField rIATextField4;
	private JRIATextField rIATextField5;
	private JRIATextField rIATextField6;
	private JRIATextField rIATextField7;
	private JLabel label9;
	private JRIATextField rIATextField8;
	private JLabel label10;
	private JRIATextField rIATextField9;
	private JLabel label11;
	private JTextField textField2;
	private JLabel label12;
	public JTextField textField3;
	private JButton button4;
    // JFormDesigner - End of variables declaration //GEN-END:variables

    public static void main(String[] args) {
        AddressImportFrame frame = new AddressImportFrame();
        frame.addressFrame.setVisible(true);
    }
}
