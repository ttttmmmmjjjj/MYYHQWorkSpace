package com.hsic.sy.bll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class GetDate {
static Date date = new Date();
static String pattern = "yyyy-MM-dd HH:mm:ss";
static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
		Locale.getDefault());
static String time = simpleDateFormat.format(date);//��ȡѲ������
public  static String   GetYMD(){
	String ret="";
	ret=time.substring(2, 4)+time.substring(5, 7)+time.substring(8, 10);
//	Log.e("GetYMD", ret);
	return ret;
}
public static String GetDate(){
	String ret="";
//	Log.e("GetDate", time);
	ret=time.substring(0, 4)+time.substring(5, 7)+time.substring(8, 10);
//	Log.e("GetDate", ret);
	return ret;
}
public static String GetTime(){
	String ret="";
//	Log.e("GetTime", time);
	ret=time.substring(11, 13)+time.substring(14, 16)+time.substring(17, 19);
//	Log.e("GetTime", ret);
	return ret;
}
public static String getToday(){
	String today="";
	Date date = new Date();
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
			Locale.getDefault());
	today=simpleDateFormat.format(date);
	return today;
}
}
