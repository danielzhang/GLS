package cn.gls;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @ClassName: GISDBSupport.java
 * @Description ibatis与spring结合的基类
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-5-25
 */
public class GLSDBSupport extends SqlMapClientDaoSupport {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;
	@PostConstruct
	public void initSqlMapClient() {
		
		super.setSqlMapClient(sqlMapClient);
	}
}
