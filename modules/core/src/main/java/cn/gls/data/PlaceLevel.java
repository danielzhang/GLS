package cn.gls.data;

import java.io.Serializable;
/**
 * @ClassName: PlaceLevel.java
 * @Description  TODO
 * @Date  2012-5-25
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-5-25
 */
public final class PlaceLevel extends Object implements Serializable {
	/***/
	private static final long serialVersionUID = 2L;
    
	/**国家级别 */
	public final static int STATE = 1;
	
	/**省级别 */
	public final static int PROVINCE=2;
	/**市级别 */
	public final static int CITY=3;
	/**区级别 */
	public final static int COUNTY=4;
	/** 乡、镇级别 */
	public final static int TOWN=5;
	/** 村级别 */
	public final static int VILLAGE=6;
	
	/**村后缀 **/
	public final static int VILLAGE_SUFFIX=16;
	
	/** 街道 */
	public final static int STREET=8;
	
	/**街道后缀**/
	public final static int STREET_SUFFIX=18;
	
	/** 小区 ,学校 */
	public final static int COMMUNITY=9;
	
	/**小区、学校后缀***/
	 public final static int COMMUNITY_SUFFIX=19;
	/**建筑物  */
	public final static int BUILDING=10;
	
	/**建筑物后缀*/
	public final static int BUILDING_SUFFIX=20;
	
	/**POI兴趣点*/
    public final static int POI=11;
    
    /**拼音*/
    public final static int PINYIN=12;
    
    /**错字*/
    public final static int MISPRINT=13;

}
