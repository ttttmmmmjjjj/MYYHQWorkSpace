package com.hsic.sy.supply.web;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.FillMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceClient {
	static String URLStr="http://open-api.haoyunqi.com.cn/order/getOrderInfo";
    private WebServiceClient(){
        throw new IllegalStateException("");
    }
    private static final String CHARSET_NAME="UTF-8";
    /**
     * POST����
     *
     * @param
     * @param params XML�ַ���
     * @return nullΪ����ʧ��
     */
    public static FillMessage doPost(String params) {
    	FillMessage fillMessage;
    	fillMessage=new FillMessage();
        try {
        	URLStr="http://10.123.16.81/SHJJWQPWeb/ThirdInterface.ashx?method=getFillingQuantity";       	
            params="{'accessToken':"+"'nzdlmjy12tatywy5zi00nzjmlwex'"+","+"QP:"+"'085'"+","+"CQDW:"+"'33709'}";
            HttpURLConnection conn = (HttpURLConnection) new URL(URLStr).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ʱʱ��30��
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
//            String json=JSONUtils.toJsonWithGson(sb);     
//            mess.setRespMsg(json);
           
            fillMessage = (FillMessage) JSONUtils.toObjectWithGson(sb.toString(), FillMessage.class);
            return fillMessage;
        } catch (IOException e) {
            e.printStackTrace();
            fillMessage.setResult(-1);
            fillMessage.setMessage("调用异常:"+e.toString());
            return fillMessage;
        }
    }
  
}

