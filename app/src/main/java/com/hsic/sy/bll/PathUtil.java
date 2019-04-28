package com.hsic.sy.bll;

import android.os.Environment;

public class PathUtil {
	public static String getDefaultPath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SupplyManager/";
		return ret;
	}
	public static String getImagePath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SupplyManager/QPManagerMobileRectify/Image/";
		return ret;
	}
	public static String getExpInfoImagePath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SupplyManager/QPManagerExpInfo/Image/";
		return ret;
	}
	public static String getLogPath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SupplyManager/Log/";
		return ret;
	}
	public static String getYLogPath() {
		String ret = "";
		ret=Environment.getExternalStorageDirectory().getAbsolutePath()+"/QPManagerMobileRectify/Log/MyLog/";
		return ret;
	}
}
