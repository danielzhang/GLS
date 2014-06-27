package cn.gls.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.gls.data.Place;

import com.vividsolutions.jts.geom.Geometry;
/**
 * 
 * @date 2012-7-26
 * @author "Daniel Zhang"
 * @update 2012-7-26
 * @description TODO
 *
 */
public class StringUtils {
	public static String mergeObjects(List<String> strs) {
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			if (str != null || "".equalsIgnoreCase(str))
				sb.append(str);
		}
		if (sb.length() != 0)
			return sb.toString();
		else
			return null;
	}
	
	public static boolean equals(List<String> ori,List<String> goal){
	             	boolean flag=false;
	             	if(ori==null||goal==null)
	             		return flag;
	             	if(ori.size()!=goal.size())
	             		return flag;
	             	for(int i=0;i<ori.size();i++){
	             		if(!ori.get(i).equals(goal.get(i))){
	             			flag=true;
	             			break;
	             		}
	             	}
	             	return flag;
	}

	public static Class getClass(String clazz) {
		if ("Integer".equalsIgnoreCase(clazz))
			return Integer.class;
		if ("String".equalsIgnoreCase(clazz))
			return String.class;
		if ("Double".equalsIgnoreCase(clazz))
			return Double.class;
		if ("Geometry".equalsIgnoreCase(clazz))
			return Geometry.class;
		else
			return null;
	}

	public static int selectIntegerFromString(String name) {
		name = ToDBC(name);
		char[] chars = name.toCharArray();
		int d = -1;
		String digest = "";
		for (Character c : chars) {
			if (Character.isDigit(c)) {
				digest += c;
			} else if (isDigit(c) > 0) {
				if (isDigit(c) % 10 == 0) {
					continue;
				} else
					digest += isDigit(c);

			} else
				continue;
		}
		if (!"".equalsIgnoreCase(digest))
			d = Integer.valueOf(digest);
		return d;
	}

	/**
	 * 半角转全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {

		if (input != null && !"".equalsIgnoreCase(input)) {
			char c[] = input.trim().toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] == '\u3000') {
					c[i] = ' ';
				} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
					c[i] = (char) (c[i] - 65248);

				}
			}
			return new String(c);
		} else
			return "";
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private static int isDigit(Character c) {
		int count = -1;
		switch (c) {
		case '零':
			count = 0;
			break;

		case '一':
			count = 1;
			break;
		case '二':
			count = 2;
			break;
		case '三':
			count = 3;
			break;
		case '四':
			count = 4;
			break;
		case '五':
			count = 5;
			break;
		case '六':
			count = 6;
			break;
		case '七':
			count = 7;
			break;
		case '八':
			count = 8;
		case '九':
			count = 9;
			break;
		case '十':
			count = 10;
			break;

		}
		return count;
	}

	/**
	 * 获得简单的地名词
	 * 
	 * @param name
	 * @return
	 */
	public static String getPlaceName(String name) {

		name = ToDBC(name);
		char[] names = name.toCharArray();
		int s = names.length;
		int index = name.length();
		for (int i = 2; i < s; i++) {
			if (isLetterOrDigit(names[i])
					|| specialCharacter.contains(names[i])) {
				if (!specialCharacter.contains(names[i]) && i < s - 1
						&& suffixCharacter.contains(names[i + 1]))
					continue;
				index = i;
				break;
			}
		}
		return name.substring(0, index);
	}

	/**
	 * 判断是否数字或字母
	 * 
	 * @param temp
	 * @return
	 */
	public static boolean isLetterOrDigit(Character temp) {
		Pattern pattern = Pattern.compile("[A-Z]");
		Pattern q = Pattern.compile("[a-z]");
		Pattern r = Pattern.compile("[0-9]");
		Matcher m1 = pattern.matcher(temp.toString());
		Matcher m2 = q.matcher(temp.toString());
		Matcher m3 = r.matcher(temp.toString());
		if (m1.lookingAt() || m2.lookingAt() || m3.lookingAt())
			return true;
		else {
			return false;
		}
	}

	private static final List<Character> specialCharacter = Arrays.asList('(',
			')', '#', '（', '）', '<', '>', '《', '》');
	private static final List<Character> upperDigit = Arrays.asList('一', '二',
			'三', '四', '五', '六', '七', '八', '九');
	private static final List<Character> suffixCharacter = Arrays.asList('市',
			'镇', '县', '村', '路');
	private static final List<Character> keywordCharacter=Arrays.asList('A','B','C','D','E','F','G');
	private static final  Map<Character, Integer> digitMap = new HashMap<Character, Integer>() {
		{
			put('一', 1);
			put('二', 2);
			put('三', 3);
			put('四', 4);
			put('五', 5);
			put('六', 6);
			put('七', 7);
			put('八', 8);
			put('九', 9);
			put('十', 10);
		}
	};
	/**
	 * 
	 * @param allStr
	 * @param suffix
	 * @return
	 */
	public static String removeSuffix(String allStr, String suffix) {
		if (allStr == null || suffix == null)
			return allStr;
		int index = allStr.indexOf(suffix);
		if (index > 0)
			return allStr.substring(0, index);
		else
			return allStr;
	}

	/**
	 * 大写转小写
	 * 
	 * @param input
	 * @return
	 */

	public static char regularize(char input) {
		if (input == 12288) {
			input = (char) 32;

		} else if (input > 65280 && input < 65375) {
			input = (char) (input - 65248);

		} else if (input >= 'a' && input <= 'z') {
			input -= 32;
		}
		return input;
	}

	public static String changeCase(String name) {
		name = ToDBC(name);
		// char[] tempCharacter=name.trim().toCharArray();
		// name.to
		return name.toLowerCase();
	}

	/**
	 * 判断是否有数字，如果有数字则放回数字，否则返回0；
	 * 
	 * @param suffix
	 * @return
	 */
	public static int checkoutDigit(String suffix) {
		if(suffix==null)
			return 0;
		StringBuilder flag = new StringBuilder();
		char[] cs = suffix.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			char temp = cs[i];
			if(flag.length()!=0&&!Character.isDigit(temp))
				break;
			if (Character.isDigit(temp)) {
				flag.append(temp);
			} else if(upperDigit.contains(cs[i])){
				flag.append(digitMap.get(temp));
			}else
				continue;
		}
		if (flag == null)
			flag = new StringBuilder("0");
		return Integer.valueOf(flag.toString());
	}
	/**
	 * 取出后缀的关键字
	 */
	public static String getKeyWordInSuffix(String suffix){
		if(suffix==null)
			return null;
		String keyWord=null;
		char[] cs=suffix.toCharArray();
		for(int i=0;i<cs.length;i++){
			char c=cs[i];
			if(keywordCharacter.contains(c)){
				keyWord=String.valueOf(c);
				break;
			}
		}
		return keyWord;
	}

public static String getResultString(List<Place> places){
	 StringBuffer stBuffer=new StringBuffer();
	 if(places==null||places.size()==0)
		 return "";
	 else{
		 for(Place p:places){
			 stBuffer.append(p.getAllName());			 
		 }
		 return new String(stBuffer);
	 }
}

	// private static List<Character>
	// upperDigit=Arrays.asList('一','二','三','四','五','六','七','八','九','十');

	public static void main(String[] args) {
		// System.out.println(specialCharacter.contains("（"));
		// String name = changeCase("AdsCds");
		// String name="上海市浦东新区浦三路小学（南门）";
		// String
		// name1=name.substring(name.indexOf("dddddd")+"dddddd".length(),name.length());

		System.out.println(checkoutDigit("2015号"));
	}
}
