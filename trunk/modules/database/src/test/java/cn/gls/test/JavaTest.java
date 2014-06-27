package cn.gls.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.gls.data.PinyinPlace;



public class JavaTest {

/**
 *@Param JavaTest
 *@Description TODO
 *@return void
 * @param args
 */
public static void main(String[] args) {
//   Collection<Integer> collections=Arrays.asList(1,1,2,3,1,2,3,4,5,3,4,6,5,2);
//         Iterator<Integer> tIterator=collections.iterator();
//         List<Integer> objs=new ArrayList<Integer>();
//         Set<Integer> os=new HashSet<Integer>();
//         while(tIterator.hasNext()){
//             Integer item=tIterator.next();
//             if(os.contains(item))
//               continue;
//             else
//                 os.add(item);
//             Iterator<Integer> wtIterator=collections.iterator();
//             
//             while(wtIterator.hasNext()){
//                 Integer witem=wtIterator.next();
//                 if(item==witem){
//                     objs.add(witem);
//                     //collections.remove(witem);                 
//                     continue;
//                 }
//             else
//                 continue;
//         }
//         }
//         System.out.println(objs.size());
             PinyinPlace place =new PinyinPlace("意大利老船长", "YiDaLi LaoChuanZhang",3100021);
             PinyinPlace place2 =new PinyinPlace("意大利老船长", "YiDaLi LaoChuanZhang",3100021);
          System.out.println( place.equals(place2));  
          System.out.println(place.hashCode()==place2.hashCode());
             HashSet<PinyinPlace> sets=new HashSet<PinyinPlace>();
             sets.add(place2);
             sets.add(place);
             System.out.println(sets.size());
}
public static int  selectIntegerFromString(String name){
	 name=ToDBC(name);
	 char[] chars=name.toCharArray();
	 int d=-1;
	 String digest="";
	 for(Character c:chars){
		  if(Character.isDigit(c)){
			   digest+=c;
		  }
		  else if(isDigit(c)>0)
		  {
			  if(isDigit(c)%10==0){
				  continue;
			  }
			  else
				  digest+=isDigit(c);

				  
		  }
		  else
			  continue;
	 }
	 if(!"".equalsIgnoreCase(digest))
	 d=Integer.valueOf(digest);
	 return d;
}
private static int isDigit(Character c){
	int count=-1;
	switch(c){
	case '零':
		count=0;
		break;
	   
	case '一':
		count=1;
		break;
	case '二':
		count=2;
		break;
	case '三':
		count=3;
		break;
	case '四':
		count=4;
		break;
	case '五':
		count=5;
		break;
	case '六':
		count=6;
		break;
	case '七':
		count=7;
		break;
	case '八':
		count=8;
		break;
	case '九':
		count=9;
		break;
	case '十':
		count=10;
		break;
	case '百':
		count=100;
		break;
	case '千':
		count=1000;
		break;
	case '万':
		count=10000;
		break;
		
	}
	return count;
}
/**
 * 
 * @param input
 * @return
 */
public static String ToDBC(String input) {   
    
	  
    char c[] = input.toCharArray();   
    for (int i = 0; i < c.length; i++) {   
      if (c[i] == '\u3000') {   
         c[i] = ' ';   
       } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {   
         c[i] = (char) (c[i] - 65248);   

       }   
     }
    return new String(c);
}  
/**
 * 
 * @param input
 * @return
 */
public static String ToSBC(String input){
    char c[] = input.toCharArray();   
    for (int i = 0; i < c.length; i++) {   
      if (c[i] == ' ') {   
         c[i] = '\u3000';                 //采用十六进制,相当于十进制的12288   


       } else if (c[i] < '\177') {    //采用八进制,相当于十进制的127   
         c[i] = (char) (c[i] + 65248);   

       }   
     }   
    return new String(c);   
}
}
