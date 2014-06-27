package cn.gls.test;

/**
 * @ClassName: ParentTest.java
 * @Description  TODO
 * @Date  2012-6-28
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-28
 */
public class ParentTest {
protected static int type=0;
       protected int init(){
    	   int count=0;
    	for(int i=1;i<2;i++){
    		count=i*createInteger()+type;
    	}
    	   return count;
       }
       protected int createInteger(){
    	   return 0;
       }
}
