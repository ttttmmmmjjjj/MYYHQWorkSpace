package com.hsic.sy.doorsale.bean;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/8/6.
 */

public class WebServiceClient {
    String url="http://open-api.haoyunqi.com.cn/order/getOrderInfo";
    private WebServiceClient(){
        throw new IllegalStateException("");
    }
    private static final String CHARSET_NAME="UTF-8";
    /**
     * POST请求
     *
     * @param url    请求地址
     * @param params XML字符串
     * @return null为调用失败
     */
    public static String doPost(String url, String params) {
        try {
            url="http://open-api.haoyunqi.com.cn/order/getOrderInfo?accessToken=nzdlmjy12tatywy5zi00nzjmlwex&QP=085&CQDW=33709";
            params="{'accessToken':"+"'nzdlmjy12tatywy5zi00nzjmlwex'"+","+"QP:"+"'085'"+","+"CQDW:"+"'33709'}";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 超时时间30秒
            int timeout = 30 * 1000;
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.connect();
            if (params != null) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), CHARSET_NAME);
                out.write(params);
                out.flush();
                out.close();
            }
            InputStreamReader r = new InputStreamReader(conn.getInputStream(), CHARSET_NAME);
            BufferedReader reader = new BufferedReader(r);
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Log.e("服务调用结果",sb.toString());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

