package com.hsic.bll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
/**
 * 20180507
 * ����getFilePath
 * @author Administrator
 *
 */
public class PathUtil {
	
	public static String getDefaultPath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/QPManagerMobileBak/";
		return ret;
	}
	public static String getImagePath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/QPManagerMobileBak/Image/";
		return ret;
	}
	public static String getLogPath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/QPManagerMobileBak/Log/";
		return ret;
	}
	public static String getYLogPath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/QPManagerMobileBak/Log/MyLog/";
		return ret;
	}
	/**
	 * ��ȡ��Ƭ·��
	 * @param
	 * @return
	 */
	public static String getFilePath(){
		String ret = "";
		try{
			Date date = new Date();
			String pattern = "yyyyMMdd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
					Locale.getDefault());
			String FileName = simpleDateFormat.format(date);
			ret=FileName;
		}catch(Exception ex){
			ex.toString();
		}
			
		return ret;
	}
}
