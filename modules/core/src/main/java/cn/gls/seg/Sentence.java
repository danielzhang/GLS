package cn.gls.seg;

/**
 * 
 * @date 2012-5-26
 * @author "Daniel Zhang"
 * @update 2012-7-26
 * @description TODO
 *
 */
public class Sentence {

	private char[] text;
	private int startOffset = 0;

	private int offset;

	public Sentence() {
		text = new char[0];
	}

	public Sentence(char[] text, int startOffset) {
		reinit(text, startOffset);
	}

	public void reinit(char[] text, int startOffset) {
		this.text = text;
		this.startOffset = startOffset;
		offset = 0;
	}

	/*
	 * 获得特定点的字符串。。。。
	 */
	public char[] getText(int startOffset, int offset) {
		char[] rchar = new char[offset - startOffset];

		for (int i = startOffset; i < offset; i++) {
			rchar[i - startOffset] = text[i];
		}
		return rchar;
	}

	public char[] getText() {
		return text;
	}

	/** 句子开始处理的偏移位置 */
	public int getOffset() {
		return offset;
	}

	/** 句子开始处理的偏移位置 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void addOffset(int inc) {
		offset += inc;
	}

	/** 句子处理完成 */
	public boolean isFinish() {
		return offset - startOffset == 1;
	}

	/** 句子在文本中的偏移位置 */
	public int getStartOffset() {
		return startOffset;
	}

	/** 句子在文本中的偏移位置 */
	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}
}
