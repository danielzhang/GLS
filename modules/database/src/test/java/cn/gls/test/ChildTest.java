package cn.gls.test;

/**
 * @ClassName: ChildTest.java
 * @Description  TODO
 * @Date  2012-6-28
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-28
 */
public class ChildTest extends ParentTest{

@Override
public int init(){
         type=100;
	return super.init();
 }
 @Override
protected int createInteger(){
	 return 10;
 }
 public static void main(String[] args) {
	ChildTest c=new ChildTest();
	System.out.println(c.init());
}
}
