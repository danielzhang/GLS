/**
 *2011-9-6
 *TODO
 *
 *
 */
package cn.gls.seg.dic;

import java.util.HashSet;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张德品
 * @date 2011-9-6
 * @说明 真正的存储对象
 */
public class CharNode {
	Map<Character, Set<String>> nodesMap;/* 最后一个字符。。。。 */
	int type;

	public CharNode() {

	}

	public void add(char[] charArray) {
		if (charArray.length <= 1)
			return;
		else {
			Character endCharacter = charArray[1];
			if (nodesMap == null)
				nodesMap = new ConcurrentHashMap<Character, Set<String>>();
			Set<String> nodes = nodesMap.get(endCharacter);
			if (nodes == null)
				nodesMap.put(endCharacter, initSet(String.valueOf(charArray)));
			else {
				nodes.add(String.valueOf(charArray));
			}
		}
	}

	public char[] maxMatch(char[] words, int offset) {

		char[] ts = getMaxMatch(words, offset);
		while (ts == null) {
			offset = offset - 1;
			if (offset == 1) {
				ts = new char[1];
				ts[0] = words[0];
			} else
				ts = getMaxMatch(words, offset);
		}
		return ts;
	}

	private char[] getMaxMatch(char[] words, int offset) {
		Set<String> sets = this.nodesMap.get(words[1]);
		if (sets == null) {
			int type = Character.getType(words[0]);
			StringBuilder s = new StringBuilder("");
			if (type == 5) {
				s.append(words[0]);
				return s.toString().toCharArray();
			}
			int i = 0;
			while (type != 5 && i < words.length) {
				s.append(words[i]);
				i++;
				if (i < offset) {
					int temp = type;
					type = Character.getType(words[i]);
					if (temp != type)
						break;
				}
			}
			return s.toString().toCharArray();
		} else {
			char[] cs = new char[offset];
			// 我感觉要在这里判断一下了<首先到这一步已经通过了第一二个字符相同情况的验证。>。。。。。。。。。。。。
			boolean isdigit = true;
			int type = 5;
			for (int j = 0; j < offset; j++) {
				cs[j] = words[j];
				if (isdigit) {
					int temp = type;
					type = Character.getType(cs[j]);
					if (j != 0 && (type == 5 || temp != type))
						isdigit = false;
				}
			}
			if (isdigit)
				return cs;
			else {
				if (!sets.contains(String.valueOf(cs)) && offset != 1) {
					return null;
				} else
					return cs;
			}
		}
	}

	private void clear(Character charnode) {
		if (nodesMap == null)
			return;
		if (nodesMap.get(charnode).size() != 0)
			nodesMap.get(charnode).clear();
	}

	private HashSet<String> initSet(String str) {
		HashSet sets = new HashSet<String>();
		sets.add(str);
		return sets;
	}

	// public class Node {
	// String text;
	// int length;
	//
	// public Node(String text, int length) {
	// super();
	// this.text = text;
	// this.length = length;
	// }
	//
	// }
}
