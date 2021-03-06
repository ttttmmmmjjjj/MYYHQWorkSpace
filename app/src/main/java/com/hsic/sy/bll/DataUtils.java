package com.hsic.sy.bll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/8/24.
 */

public class DataUtils {
    public static String getCurrentTime(){
        String inspectedDate="2000-01-01 00:00:00";
        try{
            Date date = new Date();
            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
                    Locale.getDefault());
            inspectedDate = simpleDateFormat.format(date);//获取巡检日期
        }catch(Exception ex){
            ex.toString();
        }
        return inspectedDate;
    }
    public static String getCurrentDate(){
        String inspectedDate="2000-01-01";
        try{
            Date date = new Date();
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
                    Locale.getDefault());
            inspectedDate = simpleDateFormat.format(date);//获取巡检日期
        }catch(Exception ex){
            ex.toString();
        }
        return inspectedDate;
    }
    public static String getDate(){
        String inspectedDate="20000101";
        try{
            Date date = new Date();
            String pattern = "yyyyMMdd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
                    Locale.getDefault());
            inspectedDate = simpleDateFormat.format(date);//获取巡检日期
        }catch(Exception ex){
            ex.toString();
        }
        return inspectedDate;
    }
}
