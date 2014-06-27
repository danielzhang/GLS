package cn.gls.seg.dic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.jar.JarFile;
import java.util.logging.Logger;

import cn.gls.util.IPUtil;

/**
 * @author 张德品
 * @date 2011-9-6
 * @说明 单例模式
 */
public class Dictionary {
	private static final Logger log = Logger.getLogger(Dictionary.class
			.getName());

	private File dicPath; // 词库目录
	private volatile Map<Character, CharNode> dict;
	private volatile Map<Character, Object> unit; // 单个字的单位
	/** 不要直接使用, 通过 {@link #getDefalutPath()} 使用 */
	private static File defalutPath = null;
	private static final ConcurrentHashMap<File, Dictionary> dics = new ConcurrentHashMap<File, Dictionary>();

	/**
	 * 从默认目录加载词库文件.
	 * <p/>
	 * 查找默认目录顺序:
	 * <ol>
	 * <li>从系统属性mmseg.dic.path指定的目录中加载</li>
	 * <li>从classpath/data目录</li>
	 * <li>从user.dir/data目录</li>
	 * </ol>
	 * 
	 * @see #getDefalutPath()
	 */
	public static Dictionary getInstance() {
		File path = null;
		try {
			path = getDefalutPath();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return getInstance(path);
	}

	/**
	 * @param path
	 *            词典的目录
	 */
	public static Dictionary getInstance(String path) {
		return getInstance(new File(path));
	}

	/**
	 * 当 words.dic 是从 jar 里加载时, 可能 defalut 不存在
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static File getDefalutPath() throws UnsupportedEncodingException {
		if (defalutPath == null) {
		String	defPath=null;
			if(System.getProperty("datas")!=null)
			 defPath = new String(System.getProperty("datas").getBytes(),
					"UTF-8");
			log.info("寻找词典目录=" + defPath);
			if (defPath == null) {
				
				URL url = Dictionary.class.getResource("/data");
				if (url != null) {
					defPath = new String(url.getFile().getBytes(), "UTF-8");
					log.info("寻找到路径为=" + defPath);
				} else {
					defPath = new String(System.getProperty("user.dir")
							.getBytes(), "UTF-8") + File.separator + "data";

					// defPath
					// =Dictionary.class.getResource("/").getPath().toString();
					log.info("look up in user.dir=" + defPath);
				}
			}
			defalutPath = new File(defPath);
			if (!defalutPath.exists()) {
				log.warning("defalut dic path=" + defalutPath + " not exist");
			}
		}
		return defalutPath;
	}

	/**
	 * @param path
	 *            词典的目录
	 */
	public static Dictionary getInstance(File path) {
		Dictionary dic = dics.get(path);
		if (dic == null) {
			dic = new Dictionary(path);
			dics.put(path, dic);
		}
		return dic;
	}

	/**
	 * 销毁, 释放资源. 此后此对像不再可用.
	 */
	void destroy() {
		clear(dicPath);

		dicPath = null;
		dict = null;
		unit = null;
	}

	public void unload() {
		destroy();
	}

	/**
	 * @see Dictionary#clear(File)
	 */
	public static Dictionary clear(String path) {
		return clear(new File(path));
	}

	/**
	 * 从单例缓存中去除
	 * 
	 * @param path
	 * @return 没有返回 null
	 */
	public static Dictionary clear(File path) {
		return dics.remove(path);
	}

	/**
	 * 词典的目录
	 */
	private Dictionary(File path) {
		init(path);
	}

	private void init(File path) {
		dicPath = path;
		reload(); // 加载词典
	}

	/**
	 * 全新加载词库，没有成功加载会回滚。
	 * <P/>
	 * 注意：重新加载时，务必有两倍的词库树结构的内存，默认词库是 50M/个 左右。否则抛出 OOM。
	 * 
	 * @return 是否成功加载
	 */
	public synchronized boolean reload() {
		try {
			dict = loadDic(dicPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private Map<Character, CharNode> loadDic(File path)
			throws FileNotFoundException {
		// InputStream charsIn = null;
		final Map<Character, CharNode> dic = new ConcurrentHashMap<Character, CharNode>();
		long ss = System.currentTimeMillis();
		// File charsFile = new File(path, "chars.dic");
		// if (charsFile.exists()) {
		// charsIn = new FileInputStream(charsFile);
		// } else { // 从 jar 里加载
		// charsIn = this.getClass().getResourceAsStream("/data/chars.dic");
		// charsFile = new File(this.getClass().getResource("/data/chars.dic")
		// .getFile()); // only for log
		// }

		// int lineNum = 0;
		// long s = System.currentTimeMillis();
		// long ss = s;
		// try {
		// lineNum = load(charsIn, new FileLoading() { // 单个字的
		// public void row(String line, int n) {
		// if (line.length() < 1) {
		// return;
		// }
		// String[] w = line.split(" ");
		// CharNode cn = new CharNode();
		// switch (w.length) {
		// case 2:
		// dic.put(w[0].charAt(0), cn);
		// }
		// }
		// });
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// log.info("chars loaded time=" + (System.currentTimeMillis() - s)
		// + "ms, line=" + lineNum + ", on file=" + charsFile);
		// 加载真正的词典库
		InputStream wordsDicIn = this.getClass().getClassLoader().getResourceAsStream("data/places.dic");
		if(wordsDicIn!=null){
			File wordsDic = new File(this.getClass().getClassLoader().getResource("data/places.dic").getFile());
			loadWord(wordsDicIn, dic, wordsDic);
		}
		File[] words = listWordsFiles(); // 只要 wordXXX.dic的文件
		if (words != null) { // 扩展词库目录
			for (File wordsFile : words) {
				loadWord(new FileInputStream(wordsFile), dic, wordsFile);
			}
		}

		log.info("加载所有词库所需要的时间为=" + (System.currentTimeMillis() - ss) + "ms");
		return dic;
	}

	private void loadWord(InputStream is, Map<Character, CharNode> dic,
			File wordsFile) {
		long s = System.currentTimeMillis();
		int lineNum = 0;
		try {
			lineNum = load(is, new WordsFileLoading(dic));
		} catch (IOException e) {

			e.printStackTrace();
		} // 正常的词库
		log.info("words词库加载时间为=" + (System.currentTimeMillis() - s)
				+ "ms, line=" + lineNum + ", on file=" + wordsFile);
	}

	/**
	 * 加载 wordsXXX.dic 文件类。
	 * 
	 * @author chenlb 2009-10-15 下午02:12:55
	 */
	private static class WordsFileLoading implements FileLoading {
		final Map<Character, CharNode> dic;

		/**
		 * @param dic
		 *            加载的词，保存在此结构中。
		 */
		public WordsFileLoading(Map<Character, CharNode> dic) {
			this.dic = dic;
		}

		public void row(String line, int n) {
			if (line.length() < 2) {
				return;
			}
			CharNode cn = dic.get(line.charAt(0));
			if (cn == null) {
				cn = new CharNode();
				dic.put(line.charAt(0), cn);
			}
			cn.add(line.toCharArray());
		}
	}

	/**
	 * 只要 wordXXX.dic的文件
	 * 
	 * @return
	 */
	protected File[] listWordsFiles() {
		
		FilenameFilter fileFilter=new  FilenameFilter() {

			public boolean accept(File dir, String name) {

				return name.startsWith("words") && name.endsWith(".dic");
			}
		};
		
		return dicPath.listFiles(fileFilter);
	}

	/**
	 * 加载词文件的模板
	 * 
	 * @return 文件总行数
	 */
	public static int load(InputStream fin, FileLoading loading)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(fin), "UTF-8"));
		String line = null;
		int n = 0;

		while ((line = br.readLine()) != null) {

			if (line == null || line.startsWith("#")) {
				continue;
			}
			n++;
			// 插入的是key=字，value=n第几个插入的
			loading.row(line, n);
		}
		return n;
	}

	public static interface FileLoading {
		/**
		 * @param line
		 *            读出的一行
		 * @param n
		 *            当前第几行
		 * @author chenlb 2009-3-3 下午09:55:54
		 */
		void row(String line, int n);
	}

	public void addWord(String word) {
		if (word.length() < 2) {
			return;
		}
		CharNode cn = dict.get(word.charAt(0));
		if (cn == null) {
			cn = new CharNode();
			dict.put(word.charAt(0), cn);
		}
		cn.add(word.toCharArray());
	}

	// 最大匹配
	public char[] maxMatch(char[] words, int offset) {

		CharNode node = dict.get(words[0]);

		if (node == null) {
			// 判断是否type为2，如果是则一直循环，否则跳出
			int type = Character.getType(words[0]);
			StringBuilder s = new StringBuilder("");
			if (type == 5) {
				s.append(words[0]);
				return s.toString().toCharArray();
			}
			int i = 0;
			while (type != 5&& i < words.length) {
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
			return node.maxMatch(words, offset);
		}

	}
}
