package cn.gls.seg;


import java.util.List;
import cn.gls.data.Place;


/**
 * @ClassName: SegAddressUtil.java
 * @Description  分词引擎
 * @Date  2012-7-4
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-7-4
 */
public interface ISegAddressEngine {
	
	/**
	 * 根据一条地址记录与城市代码来分词及制定地名词数组。
	 * @param address
	 * @param cityCode
	 * @return
	 */
	   List<Place> standardPlace(String address,int cityCode);
	
	


}
