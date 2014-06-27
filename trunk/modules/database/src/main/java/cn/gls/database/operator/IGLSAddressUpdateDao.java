package cn.gls.database.operator;

import java.util.List;

import cn.gls.data.StandardAddress;



/**
 * @ClassName: IGeoCodingUpdateDao.java
 * @Description 数据更新数据访问接口
 * @Date 2012-5-29
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-30
 */
public interface IGLSAddressUpdateDao {
	/** 添加标准地址到更新表*/
	void addRecords2UpdateTable(List<StandardAddress> addresses);

	/** 更新标准地址在更新表*/
	void updateRecordsInUpdateTable(List<StandardAddress> addresses);

	/** 刪除更新表中的记录*/
	void deleteRecordsInUpdateTable(List<StandardAddress> addresses);

	/** 验证地址在更新表*/
	List<StandardAddress> verifyRecordsInUpdateTable(
			List<StandardAddress> addresses);

	/** 把过验证的地址导入到标准表**/
	void addRecords2StandardTable(List<StandardAddress> addresses);
}
