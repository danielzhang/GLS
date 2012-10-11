package cn.gls.seg;

import java.util.ArrayList;
import java.util.List;
import cn.gls.seg.dic.Dictionary;

/**
 * @author 张德品
 * @date 2011-9-7
 * @说明 使用最大正向匹配法做为一个分词器
 */
public class ComplexSeg {
	// private int maxLength = 20;

	// int offset;// 目前最右边的字符在句子中的位置，可以看做一个指针。

	public List<String> seg(Sentence sen, Dictionary dic) {
		List<String> terms = new ArrayList<String>();
		int maxLength = sen.getText().length;
		sen.setOffset(maxLength);
		char[] w1 = new char[0];
		char[] senchild = new char[0];
		// 如果不为空则使用正向最大匹配法继续，否则使用最大逆向匹配法
		while (sen.getOffset() - w1.length != 0) {
			sen.setStartOffset(sen.getStartOffset() + w1.length);
			sen.setOffset(sen.getOffset() - w1.length);
			senchild = sen.getText(sen.getStartOffset(), maxLength);
			if (senchild.length == 1)
				w1 = senchild;
			else
				w1 = dic.maxMatch(senchild, sen.getOffset());
			terms.add(String.valueOf(w1));
			// System.out.println("w1的长度" + w1.length);
			// System.out.println("指针相差距离:" + sen.getOffset());
			// rStringBuilder.append(String.valueOf(w1) + spacemark);
			// System.out.println("两个相差：" + (sen.getOffset() - w1.length));
		}

		return terms;
	}

}
