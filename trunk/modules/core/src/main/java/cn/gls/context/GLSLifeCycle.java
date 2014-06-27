package cn.gls.context;


/**
 * 类名      GISLifeCycle.java
 * 说明  GIS 服务的生命周期
 * 创建日期 2012-5-22
 * 作者  张德品
 * 版权  ***
 * 更新时间  $Date$
 * 标签   $Name$
 * SVN 版本  $Revision$
 * 最后更新者 $Author$
 */
public interface GLSLifeCycle {
 void create();

void init();

void gisService();

void destory();
}
