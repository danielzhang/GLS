package cn.gls.seg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.gls.context.GLSConfigContext;
import cn.gls.context.GLSContext;
import cn.gls.data.GLSRequestParameter;
import cn.gls.data.PinyinPlace;
import cn.gls.data.Place;
import cn.gls.data.SegAddress;
import cn.gls.seg.dic.Dictionary;
import cn.gls.seg.operator.Complex;
import cn.gls.util.PlaceUtil;

/**
 * @ClassName AutoSegWord.java
 * @CreateDate 2012-5-24
 * @Description 主要是对字符串进行分词，最后结果是得到分词后的SegAddress
 * @Version 1.0
 * @Update 2012-7-9
 * @author "Daniel Zhang"
 */
public class AutoSegWord implements ISegWord {

	protected static final Log log = LogFactory.getLog(AutoSegWord.class);

	protected static final Boolean isDebug = log.isDebugEnabled();

	// /** 分词中要有上下文环境 */
	// private GBSContext context;

	public AutoSegWord() {
		super();
	}

	public SegAddress seg4Address(GLSRequestParameter gParam) {
		return getAddress(gParam.getQ());
	}

	private GLSContext context = GLSConfigContext.getInstance();

	/**
	 * @param address
	 *            输入地址字符串
	 * @return : 地址分词数组
	 * @TODO : 中文地址分词函数，并判定是否含有拼音或者错误地址词,并且记录地址词
	 */
	private SegAddress getAddress(String address) {

		SegAddress dfeature = new SegAddress();
		List<Place> placesList = dfeature.getPlacesList();
		log.info("开始分词");
		Pattern pattern = Pattern.compile(context.getDefaultProperties()
				.getProperty("spaceCharacter"));
		// ///////// 去空格
		String[] sentences = pattern.split(address);
		List<String> queryMap = new ArrayList<String>();
		int sl = sentences.length;
		for (int i = 0; i < sl; i++) {
			if (isDebug)
				log.debug("组成地址串之一为:" + sentences[i]);
			queryMap.add(sentences[i]);
		}
		// /////////////开始 分词

		for (Iterator<String> iterator = queryMap.iterator(); iterator
				.hasNext();) {
			String queryTemp = iterator.next();

			if ("".equalsIgnoreCase(queryTemp))
				continue;
			List<String> segTerms;
			try {
				segTerms = segWords(queryTemp, context.getDictionary());
				if (isDebug)
					log.debug("分出词的个数为：" + segTerms.size());
				boolean wrongFlag = false; // 判断是否是错词的标示符
				StringBuilder linkCharacter = new StringBuilder(); // 连接符
				int tl = segTerms.size();
				for (int j = 0; j < tl; j++) {
					// 判断是否含有拼音，及是否有错误地址词
					// /////////////////////////////////////////////////////////////////////////////////////////////////////
					// 首先判断是否为门牌号..这是很有技术难度的，怎么判断是编号呢。///////////////
					final String temp = segTerms.get(j);
					if (PlaceUtil.isHouseNumber(temp)) {
						if (isDebug)
							log.debug(temp + "是门牌号");
						if (!"".equalsIgnoreCase(linkCharacter.toString())
								&& linkCharacter.length() > 1) {
							// 导入到错误的词中
							Place place = new Place();
							place.setPlaceLevel(13);
							place.setAllName(linkCharacter.toString());
							placesList.add(place);
							linkCharacter.delete(0, linkCharacter.length());
						}
						// 寻找最前面的那个Place
						if (placesList.size() == 0)
							continue;
						Place place = placesList.get(placesList.size() - 1);
						dfeature.setSuffix(true);
						place.setSuffix(place.getSuffix() != null ? place
								.getSuffix() + temp : temp);
					} else {
						// 判断是否是拼音
						boolean ispinyin = false;
						int l = temp.length();
						for (int c = 0; c < l; c++) {
							Character tempchar = temp.charAt(c);
							if ((String.valueOf(tempchar)).getBytes() == null
									|| (String.valueOf(tempchar)).getBytes().length >= 2
									|| (String.valueOf(tempchar)).getBytes().length <= 0) {
								ispinyin = false;
								break;
							} else {
								ispinyin = true;
							}
						}
						if (isDebug)
							log.debug(temp + "是否为拼音" + ispinyin);
						if (ispinyin) {
							wrongFlag = false;
							linkCharacter.delete(0, linkCharacter.length());
							dfeature.setPinyin(true);
							Place place = new Place();
							place.setAllName(temp);
							place.setPinyin(new PinyinPlace(temp, 0));
							place.setPlaceLevel(12);
							placesList.add(place);
						}
						// 判断是否含有错误的地址词，标准：只要含有一个字的就是错词, 如果是错词则要连接起来
						if (temp.length() == 1) {
							linkCharacter.append(temp);
							wrongFlag = true;
							if (isDebug)
								log.debug(temp + " 是错词");
							dfeature.setMisprint(true);
						} else {
							wrongFlag = false;
						}
						if (!wrongFlag) {
							if (isDebug)
								log.debug(temp + " 不是错词");
							if (!(linkCharacter.length() == 0)) {
								Place place = new Place();
								if (linkCharacter.length() >= 2) {
									place.setAllName(linkCharacter.toString());
									dfeature.setMisprint(true);
									place.setPlaceLevel(13);
									placesList.add(place);
								}
								linkCharacter.delete(0, linkCharacter.length());
							} else {
								Place place = new Place();
								place.setAllName(temp);
								place.setPlaceLevel(0);
								placesList.add(place);
							}

						}
						if (wrongFlag = true && j == tl - 1
								&& !(linkCharacter.length() == 0)) {
							if (linkCharacter.length() >= 2) {
								Place place = new Place();
								place.setAllName(linkCharacter.toString());
								dfeature.setMisprint(true);
								place.setPlaceLevel(13);
								placesList.add(place);

							}

						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		log.info("分词结束");
		return dfeature;
	}

	/**
	 * 调用分词引擎
	 * 
	 * @param input
	 *            输入的地址
	 * @param dic
	 *            词库
	 * @return 分解好的地名词
	 * @throws IOException
	 */
	private List<String> segWords(String input, Dictionary dic)
			throws IOException {
		Complex complex = new Complex();
		return complex.segWords(input, dic);
	}
}
