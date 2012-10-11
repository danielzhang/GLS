package cn.gls.seg.operator;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.gls.seg.ComplexSeg;
import cn.gls.seg.Sentence;
import cn.gls.seg.dic.Dictionary;
import cn.gls.util.StringUtils;


/**
 * @ClassName: Complex.java
 * @Description  这个是分词引擎的主题类，主要是把地址名词分割成多个地名词,用最简单的方法把这件事情给办了，地址的特点： 下一步改进的空间是根据城市进行分词。
 * @Date   2011-11-4
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-7-4
 */
public class Complex {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @TODO
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {

		Complex complex = new Complex();
		Dictionary dictionary = Dictionary.getInstance();
		long s = System.currentTimeMillis();
		List<String> strs = complex.segWords("沪松公路５１８８号大创钢市Ｅ幢１６号", dictionary);
		
		int l = strs.size();
		StringBuilder sBuilder = new StringBuilder("");
		for (int i = 0; i < l; i++) {
			sBuilder.append(strs.get(i));
			sBuilder.append("|");
		}
		 System.out.println(sBuilder.toString());
		System.out.println("完全分完词所需时间:" + (System.currentTimeMillis() - s));
	}

	/*
	 * @说明：他就是用来处理分词后的编码的，除去编码他啥也干不了。
	 */
	public List<String> handleSufix(List<String> strs) {

		// 开一个多好啊，
		List<String> result = new ArrayList<String>();

		SuffixObject suffixObject = readSuffix();

		StringBuilder sufix = new StringBuilder();

		for (int i = 0; i < strs.size(); i++) {
			String temp = strs.get(i);
			// 要是都是数字那怎么办啊。。。判断
			boolean flag = false;
			for (int j = 0; j < temp.length(); j++) {
				Character character = temp.charAt(j);
				if (Character.isDigit(character))
					flag = true;
				else {
					flag = false;
					break;
				}
			}
			if (!flag && suffixObject.suffix.indexOf(temp) >= 0)
				flag = true;
			// 地名词，所有的非数字，
			if (temp.length() > 1 && !flag) {
				result.add(temp);
				if (!"".equalsIgnoreCase(sufix.toString())) {
					result.add(sufix.toString());
					sufix.delete(0, sufix.length());
				}
				continue;
			} else {
				boolean isarea = suffixObject.chineseera.indexOf(temp) >= 0;
				boolean isdigit = Character.isDigit(temp.charAt(0)) || flag
						|| Character.isLowerCase(temp.charAt(0))
						|| Character.isUpperCase(temp.charAt(0));
				boolean issuffix = suffixObject.suffix.indexOf(temp) >= 0;
				boolean isspecial = suffixObject.specialmark.indexOf(temp) >= 0;
				// 这两个判断是否包含了所有的情况。
				if (!isarea && !isdigit && !issuffix && !isspecial) {
					result.add(temp);
					continue;
				}
				if (isarea && i != strs.size() - 1) {
					String tString = strs.get(i + 1);
					isarea = Character.isDigit(tString.charAt(0))
							|| Character.isLowerCase(tString.charAt(0))
							|| Character.isUpperCase(tString.charAt(0));
				}
				if (isarea || isdigit || issuffix || isspecial) {
					sufix.append(temp);
				}
				if (issuffix || i == strs.size() - 1) {
					// 如果是结束标示符，那么还要查看下一个字符。
					if (!"".equalsIgnoreCase(sufix.toString())) {
						result.add(sufix.toString());
						sufix.delete(0, sufix.length());
					}

				}
			}
		}
		return result;
	}

	/**
	 * 这个函数是判断这个字符串是字或词是字母或数字的
	 * 
	 */

//	private boolean isLetterOrDigit(String temp) {
//		Pattern pattern = Pattern.compile("[A-Z]");
//		Pattern q = Pattern.compile("[a-z]");
//		Pattern r = Pattern.compile("[0-9]");
//		Matcher m1 = pattern.matcher(temp);
//		Matcher m2 = q.matcher(temp);
//		Matcher m3 = r.matcher(temp);
//		if (m1.lookingAt() || m2.lookingAt() || m3.lookingAt())
//			return true;
//		else {
//			return false;
//		}
//	}
/**
 * 
 * @param str
 * @param dic
 * @return
 */
	public List<String> segWords(String str, Dictionary dic) {
		char[] text = str.trim().toCharArray();
		int l = text.length;
		for (int i = 0; i < l; i++) {
			text[i] = StringUtils.regularize(text[i]);
		}
		ComplexSeg seg = new ComplexSeg();

		return handleSufix(seg.seg(new Sentence(text, 0), dic));
	}


/**
 * 
 * @param str
 * @param newCharset
 * @return
 * @throws UnsupportedEncodingException
 */
	public String changeCharset(String str, String newCharset)
			throws UnsupportedEncodingException {
		if (str != null) {
			// 锟斤拷默锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟街凤拷
			byte[] bs = str.getBytes();
			// 锟斤拷锟铰碉拷锟街凤拷锟斤拷锟斤拷锟斤拷锟街凤拷
			return new String(bs, newCharset);
		}
		return null;
	}

/**
 * 
 * @param str
 * @param oldCharset
 * @param newCharset
 * @return
 * @throws UnsupportedEncodingException
 */
	public String changeCharset(String str, String oldCharset, String newCharset)
			throws UnsupportedEncodingException {
		if (str != null) {
			// 锟矫旧碉拷锟街凤拷锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷芑锟斤拷锟斤拷锟届常锟斤拷
			byte[] bs = str.getBytes(oldCharset);
			// 锟斤拷锟铰碉拷锟街凤拷锟斤拷锟斤拷锟斤拷锟街凤拷
			return new String(bs, newCharset);
		}
		return null;
	}

	private SuffixObject readSuffix() {
		SuffixObject suffixObject = new SuffixObject("", "", "", "");
		String filepath = "suffix.xml";
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(Complex.class.getResourceAsStream(filepath));
			Element rootElement = doc.getRootElement();
			suffixObject.suffix = rootElement.element("suffixStr").getText()
					.toString();
			suffixObject.chineseera = rootElement.element("chineseera")
					.getText().toString();
			suffixObject.orientation = rootElement.element("orientation")
					.getText().toString();
			suffixObject.specialmark = rootElement.element("specificmark")
					.getText().toString();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return suffixObject;
	}

	/*
	 * 锟斤拷缀
	 */
	public class SuffixObject {
		public String chineseera;
		public String suffix;
		public String orientation;
		public String specialmark;

		public SuffixObject(String chineseera, String suffix,
				String orientation, String specialmark) {
			super();
			this.chineseera = chineseera;
			this.suffix = suffix;
			this.orientation = orientation;
			this.specialmark = specialmark;
		}
	}

	/*
	 * 锟斤拷锟斤拷锟脚讹拷锟斤拷
	 */
	public class SuffixCode {
		public String prefix;
		public String content;
		public String suffix;

		public SuffixCode() {
			super();

		}
	}
}
