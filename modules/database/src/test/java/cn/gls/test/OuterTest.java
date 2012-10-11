package cn.gls.test;

/**
 * @ClassName: Outer.java
 * @Description  TODO
 * @Date  2012-6-28
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-28
 */
public class OuterTest {

	 public   class Inner{ 
	        public void seeOuter(){ 
	            System.out.println(this); 
	            System.out.println(OuterTest.this); 
	        } 
	    } 
	public static void main(String[] args) {
		OuterTest outer=new OuterTest();
		 outer.new Inner().seeOuter();
	}
}
