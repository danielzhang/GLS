package cn.gbs.geocoding.engine.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import junit.framework.TestCase;

public class JavaTest extends TestCase {
   public void testJava(){
	   String all="北京市东城区东直门南大街";
       String street="东直门南大街";
     try {
    	 System.out.println(Charset.defaultCharset().name());
		System.out.println(new String(all.getBytes(),"UTF-8"));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
