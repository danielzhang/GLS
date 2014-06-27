package cn.gls.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import cn.gls.context.GLSContext;
import cn.gls.data.Place;
import cn.gls.data.StandardAddress;
import cn.gls.database.operator.IGLSAddressUpdateDao;



/**
 * @ClassName: GeoCodingUpdateEngine.java
 * @Description 数据更新引擎
 * @Date 2012-5-26
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-29
 */
public class GLSUpdateEngine {
//
//	private static final Log log = LogFactory
//			.getLog(GLSUpdateEngine.class);
	/** 中文地理编码上下文环境 */
	private GLSContext context;
	/** 中文地理编码更新接口 */
	private IGLSAddressUpdateDao gUpdateDao;

	public GLSUpdateEngine(GLSContext context) {
		super();
	}

	/**
	 * 用户增加POI点
	 * 
	 * @param inputAddress
	 * @return
	 * @TODO
	 */
	public boolean addAddress2UpdateTable(StandardAddress inputAddress) {
		boolean flag = false;

		return flag;
	}

	/**
	 * 插入到词库中
	 * 
	 * @param word
	 * @TODO
	 */
	private void writeWord2Dictionary(String word) {

	}

	/**
	 * @param addresses
	 * @return 验证后的地址 验证地址，并更改地址在数据库中的状态
	 */
	public List<StandardAddress> verifyAddressInUpdateTable(List<StandardAddress> addresses) {
		List<StandardAddress> saddress = new ArrayList<StandardAddress>();
		return saddress;
	}

	/**
	 * 
	 * @param words
	 *            标准词录入
	 * @param province
	 *            省市
	 * @return
	 * @Description:插入地名词到等级表中
	 */
	public synchronized boolean addWords2GradeTable(
			Map<String, Integer> words, String citycode) {
		int length = words.size();
		boolean flag = false;

		return flag;

	}

	/**
	 * @param words
	 *            录入同义词
	 * @param type
	 * @return
	 * @Description：添加同义词到同义词表中
	 */
	public synchronized boolean addThesaurus2Table(
			Map<String, String> words, String citycode) {
		int length = words.size();
		boolean flag = false;
		return flag;
	}

	/**
	 * 批量标准地址的录入
	 * 
	 * @param address
	 * @return
	 * @TODO
	 */
	public synchronized String addAddresses2StandardTable(List<StandardAddress> addresses) {
		int length = addresses.size();
		String flag = "fail";

		return flag;
	}

	/**
	 * 获得地址词的级别
	 * 
	 * @param address
	 * @return
	 * @TODO
	 */
	private Place getPlace(Map<Integer, String> address) {
		if (address.size() == 0)
			return null;
		Place place = new Place();
		
		return place;
	}

	/**
	 * 查询未批准的POI点
	 * 
	 * @param city
	 * @param type
	 * @param num
	 * @return
	 * 
	 */
	public List<StandardAddress> selectUnexcusedAddress(String city, String type,
			Integer num) {
		List<StandardAddress> inputaddress = new ArrayList<StandardAddress>();
		return inputaddress;
	}

	/**
	 * 
	 * 删除更新表中的地址
	 * 
	 * @param inputaddresses
	 * @return
	 * @TODO
	 */
	public String deleteUpdateTableAddress(List<StandardAddress> inputaddresses) {
		String flag = "fail";
		
		return flag;

	}

	/**
	 * 更新新增POI点信息
	 * @param inputaddresses
	 * @return
	 * @TODO
	 */
	public synchronized String updateAddressInUpdateTable(List<StandardAddress> inputaddresses) {
		String flag = "fail";
		return flag;
	}

}
