package cn.gls.ui.db.thread;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import cn.gls.database.operator.ICreateTableAndIndex;

/**
 * 
 * @date 2012-8-21
 * @author "Daniel Zhang"
 * @update 2012-8-21
 * @description 与db的联系
 * 
 */
public class DBThread extends Thread {
	
	private String flag;
	private ICreateTableAndIndex tableIndex;
	private JProgressBar progressBar;
	private JButton button;
	public DBThread(String flag,ICreateTableAndIndex tableIndex,JProgressBar progressBar,JButton button) {
		super();
		this.progressBar=progressBar;
		this.tableIndex=tableIndex;
		this.flag = flag;
		this.button=button;
	}

	@Override
	public void run() {
	   progressBar.setVisible(true);
	   progressBar.setIndeterminate(true);
       if("createPlace".equalsIgnoreCase(flag))
    	   tableIndex.createPlaceTable();      
       else if("createPinyin".equalsIgnoreCase(flag))
    	   tableIndex.createPinyinTable();
       else if("createFatherAndSon".equalsIgnoreCase(flag))
    	   tableIndex.createFatherAndSonTable();
       else if("createTyc".equalsIgnoreCase(flag))
    	   tableIndex.createTycTable();
       else if("createProvince".equalsIgnoreCase(flag))
    	   tableIndex.createProvinceTable();
       else if("createCity".equalsIgnoreCase(flag))
    	   tableIndex.createCityTable();
       else if("createPolitical".equalsIgnoreCase(flag))
    	   tableIndex.createPoliticalTable();
       else if("createStreet".equalsIgnoreCase(flag))
    	   tableIndex.createStreetTable();
       else if("createAddress".equalsIgnoreCase(flag))
    	   tableIndex.createAddressTable();
       else if("createPlaceIndex".equalsIgnoreCase(flag))
    	   tableIndex.createPlaceIndex();
       else if("createPinyinIndex".equalsIgnoreCase(flag))
    	   tableIndex.createPinyinIndex();
       else if("createFatherAndSonIndex".equalsIgnoreCase(flag))
    	   tableIndex.createFatherAndSonIndex();
       else if("createTycIndex".equalsIgnoreCase(flag))
    	   tableIndex.createTycIndex();
       else if("createProvinceIndex".equalsIgnoreCase(flag))
    	   tableIndex.createProvinceIndex();
       else if("createCityIndex".equalsIgnoreCase(flag))
    	   tableIndex.createCityIndex();
       else if("createPoliticalIndex".equalsIgnoreCase(flag))
    	   tableIndex.createPoliticalIndex();
       else if("createStreetIndex".equalsIgnoreCase(flag))
    	   tableIndex.createStreetIndex();
       else if("createAddressIndex".equalsIgnoreCase(flag))
    	   tableIndex.createAddressIndex();
       progressBar.setVisible(false);
       button.setEnabled(false);
	}
}
