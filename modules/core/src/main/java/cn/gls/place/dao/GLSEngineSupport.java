package cn.gls.place.dao;



import cn.gls.GLSDBSupport;
import cn.gls.context.GLSContext;



/**
 * 类名      GeoCodingSqlMapClientDaoSupport.java
 * 说明   支撑数据库访问的类
 * 创建日期 2012-5-24
 * 作者   "daniel zhang"
 * 更新时间   2012-7-4
 * SVN 版本  V1.0
 */
public class GLSEngineSupport extends GLSDBSupport {
	
	/** 上下文环境 */
	protected GLSContext context;

	public GLSEngineSupport(GLSContext context) {
		super();
		this.context = context;
	}
	/***
	 * 初始化引擎
	 */
	public void initEngine() {

	}

}
