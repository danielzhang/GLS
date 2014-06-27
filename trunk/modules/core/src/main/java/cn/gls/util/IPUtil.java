package cn.gls.util;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


/**
 * @author 张德品
 * @date 2011-11-29
 * @说明
 */
public class IPUtil {
	// 把文件读到Map中看一下查询速度
	private static final Logger log = Logger.getLogger(IPUtil.class.getName());

	public static Map<Integer, Map<Long, IPInfo>> readToMap(String path) {
		Map<Integer, Map<Long, IPInfo>> ipMap = new ConcurrentHashMap<Integer, Map<Long, IPInfo>>();
		BufferedReader buffer = null;
		// File file=null;
		// try {
		InputStream input = IPUtil.class.getClassLoader().getResourceAsStream(
				path);
//          File file=new File(IPUtil.class.getClassLoader().getResource(path).getFile());
		// String filePath=new
		// String(IPUtil.class.getClass().getResource(path).getFile().getBytes(),"UTF-8");
		// file = new File(filePath);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		try {
			buffer = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			String s = buffer.readLine();
			long t = System.currentTimeMillis();
			String[] info;
			IPInfo info2;
			String tkey;
			while (s != null) {
				// // 进行处理
				info = s.split("\\s+");
				int length = info[0].length();
				if (length < 7)
					tkey = "0";
				else
					tkey = info[0].substring(0, length - 6);
				info2 = new IPInfo(Long.valueOf(info[0]),
						Long.valueOf(info[1]), info[2]);
				if (ipMap.containsKey(Integer.valueOf(tkey))) {
					ipMap.get(Integer.valueOf(tkey)).put(Long.valueOf(info[0]),
							info2);
				} else {
					Map<Long, IPInfo> map = new ConcurrentHashMap<Long, IPInfo>();
					map.put(Long.valueOf(info[0]), info2);
					ipMap.put(Integer.valueOf(tkey), map);
				}
				// 写入文件

				s = buffer.readLine();
			}
			buffer.close();

			log.info("加载ip文件需要的时间为=" + (System.currentTimeMillis() - t));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (buffer != null)
					buffer.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ipMap;
	}

	/**
	 * 这个函数的主要作用是把Map做为数据结构，运用Map的搜索来扩展
	 * 
	 * @param ipMap
	 * @param ip
	 * @return
	 */
	public static String getCity(Map<Integer, Map<Long, IPInfo>> ipMap,
			String ip) {
		String city = "北京市";
		Long ipLong = ip2longFor4(ip);
		// 取前几位
		String s = ipLong.toString();
		Integer key;
		int l = s.length();
		if (l < 7) {
			key = 0;
		}
		key = Integer.valueOf(s.substring(0, l - 6));
		// System.out.println(ipMap.size());
		// 寻找key所在的Map
		if (ipMap.containsKey(key)) {
			Map<Long, IPInfo> info = ipMap.get(key);
			Iterator<Map.Entry<Long, IPInfo>> iterator = info.entrySet()
					.iterator();

			while (iterator.hasNext()) {

				Map.Entry<Long, IPInfo> info2 = iterator.next();

				if (info2.getKey() > ipLong)
					continue;
				else {
					IPInfo ipInfo = info2.getValue();
					if (ipInfo.ipEnd < ipLong)
						continue;
					else {
						city = ipInfo.cityString;
						break;
					}
				}
			}
		} else {
			// key-1来判断|||
			if (key != 0) {
				Map<Long, IPInfo> info = ipMap.get(key - 1);
				Iterator<Map.Entry<Long, IPInfo>> iterator = info.entrySet()
						.iterator();
				while (iterator.hasNext()) {
					Map.Entry<Long, IPInfo> info2 = iterator.next();
					if (info2.getKey() > ipLong)
						continue;
					else {
						IPInfo ipInfo = info2.getValue();
						if (ipInfo.ipEnd < ipLong)
							continue;
						else {
							city = ipInfo.cityString;
							break;
						}
					}
				}
			}

		}

		return city;
	}

	private static long ip2longFor4(String strIp) {

		String[] ipStrList = strIp.split("\\.");
		long ipLong = 0L;
		int i = 3;
		int cI = 0;
		String ip;
		for (int j = 0; j < ipStrList.length; j++) {
			ip = ipStrList[j];

			cI = i << 3;
			i--;
			ipLong += Long.valueOf(ip).longValue() << cI;
		}
		return ipLong;
	}

	public static class IPInfo {
		public Long ipStart;
		public Long ipEnd;
		public String cityString;

		public IPInfo(Long ipStart, Long ipEnd, String cityString) {
			this.ipStart = ipStart;
			this.ipEnd = ipEnd;
			this.cityString = cityString;
		}
	}
}
