package cn.gls.database.data;
/**
 * 
 * @ClassName DataType.java
 * @Createdate 2012-6-28
 * @Description 数据表的类型
 * @Version 1.0
 * @Update 2012-6-28 
 * @author "Daniel Zhang"
 *
 */
public class DataType {
public static final int PROVINCE_TYPE=1;
public static final int CITY_TYPE=2;
public static final int POLITICAL_TYPE=3;
public static final int STREET_TYPE=4;
public static final int ADDRESS_TYPE=5;
public static int getMax(){
    return ADDRESS_TYPE;
}
public static int getMin(){
    return PROVINCE_TYPE;
}
}
