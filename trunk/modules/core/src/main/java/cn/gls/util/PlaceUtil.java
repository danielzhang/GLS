package cn.gls.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.gls.context.GLSConfigContext;

/**
 * 类名 PlaceUtil.java 说明 关于地名的工具类，主要和地址相关，与服务引擎等无关 创建日期 2012-5-24 作者 张德品 版权 ***
 * 更新时间 2012-5-27
 */
public class PlaceUtil {
	public final static String COUNTRY = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("country");
	public final static String PROVINCE = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("province");

	public final static String CITY = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("city");

	public final static String COUNTY = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("county");

	public final static String TOWN = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("town");

	public final static String VILLAGE = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("village");
	public final static String VILLAGE_SUFFIX = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("villageSuffix");
	public final static String STREET = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("street");
	public final static String STREET_SUFFIX = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("streetSuffix");
	public final static String COMMUNITY = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("community");
	public final static String COMMUNITY_SUFFIX = GLSConfigContext
			.getInstance().getPlaceStyle().getRootElement()
			.elementText("communitySuffix");
	public final static String BUILDING = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("building");
	public final static String BUILDING_SUFFIX = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("buildingSuffix");

	public final static String PLACE_SUFFIX = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("suffix");

	public final static String ORIENTATION_SUFFIX = GLSConfigContext
			.getInstance().getPlaceStyle().getRootElement()
			.elementText("orientation");

	public final static String CHINESEERA_SUFFIX = GLSConfigContext
			.getInstance().getPlaceStyle().getRootElement()
			.elementText("chineseera");
	public final static String DIGIT = GLSConfigContext.getInstance()
			.getPlaceStyle().getRootElement().elementText("digit");

	/**
	 * @这个是判断是否是门牌号的函数 关于判断非门牌号的词的方法统计： 1.不应该是数字 2.不包括特殊的后缀名称。例如“号，座”等等..
	 *                 3.方位词，“东南西北” 4."甲乙丙丁" 规则1：如果是前缀开始且以后缀结束 规则2：如果是数字开始且以后缀结束
	 */
	public static boolean isHouseNumber(String str) {
		boolean flag = false;
		flag = isDigital(str);
		if (flag)
			return flag;
		if (str != null && !"".equalsIgnoreCase(str)) {
			Character t = str.charAt(0);
			// 要去判断前缀
			if (CHINESEERA_SUFFIX.indexOf(String.valueOf(t)) >= 0) {

				Character t0 = str.charAt(str.length() - 1);
				// 判断后缀
				if (PLACE_SUFFIX.indexOf(String.valueOf(t0)) >= 0) {

					if (str.length() > 2) {
						// 判断中间有数字
						for (int i = 1; i < str.length() - 1; i++) {
							Character c = str.charAt(i);
							if (Character.isDigit(c))
								flag = true;
						}
					}
				}
			} else if (Character.isDigit(t)) {
				Character t0 = str.charAt(str.length() - 1);
				if (PLACE_SUFFIX.indexOf(String.valueOf(t0)) >= 0) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param tmp
	 * @return
	 */
	public static boolean isDigital(String tmp) {
		int l = tmp.length();
		boolean flag = false;
		for (int i = 0; i < l; ++i) {
			char c = tmp.charAt(i);
			if (Character.isDigit(c) || specialCharacter.contains(c)) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}
		return flag;
	}

	private static List<Character> specialCharacter = Arrays.asList('-','#');

	/**
	 * 字符串是否为拼音
	 * 
	 * @param str
	 * @return
	 */
	public static boolean ispinyin(String str) {
		boolean ispinyin = false;
		int l = str.length();
		for (int c = 0; c < l; c++) {
			Character tempchar = str.charAt(c);
			if ((String.valueOf(tempchar)).getBytes() == null
					|| (String.valueOf(tempchar)).getBytes().length >= 2
					|| (String.valueOf(tempchar)).getBytes().length <= 0) {
				ispinyin = false;
				break;
			} else {
				ispinyin = true;
			}
		}
		return ispinyin;
	}

	public static boolean checkoutDigit(Character c) {
		String[] digits = DIGIT.split("、");
		return Arrays.asList(digits).contains(c);
	}

	public static boolean checkoutPrefix(Character c) {
		String[] prefixs = CHINESEERA_SUFFIX.split("、");
		return Arrays.asList(prefixs).contains(c);
	}
	public static void main(String[] args) {
	
	}
}
