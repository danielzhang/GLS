package cn.gls.database.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 
 * @date 2012-7-31
 * @author "Daniel Zhang"
 * @update 2012-7-31
 * @description 界面工具类
 * 
 */
public class UIUtils {
	//文件过滤器
	public static FileFilter filter = new FileFilter() {
		@Override
		public String getDescription() {
			return ".shp";
		}

		@Override
		public boolean accept(File f) {
			String fileName = f.getName();
			return fileName.endsWith(".shp");
		}
	};
}
