package webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.hsic.qpmanager.util.json.JSONUtils;

import bean.HsicMessage;
import bean.RecodeMessage;

public class WebServiceClient {
	static String URLStr="http://open-api.haoyunqi.com.cn/order/getOrderInfo";
    private WebServiceClient(){
        throw new IllegalStateException("");
    }
    private static final String CHARSET_NAME="UTF-8";
    /**
     * POST����
     *
     * @param url    �����ַ��QP=085&CQDW=33709��
     * @param params XML�ַ���
     * @return nullΪ����ʧ��
     */
    public static HsicMessage doPost(String url, String params) {
    	HsicMessage mess = new HsicMessage();//����HsicMessage
        try {
        	URLStr="http://open-api.haoyunqi.com.cn/order/getOrderInfo?accessToken=nzdlmjy12tatywy5zi00nzjmlwex"+"&"+url;
        	
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
            RecodeMessage recodeMess=new RecodeMessage();
            Log.e("http������ý��0",sb.toString());
            //������ý��
            String con="δ�ҵ���ƿ�ŵ�������Ϣ";
            String s=sb.toString();
            if(s.contains(con)){
            	mess.setRespCode(1);
                mess.setRespMsg("δ�ҵ���ƿ�ŵ�������Ϣ");
            }else{
            	 recodeMess = (RecodeMessage) JSONUtils.toObjectWithGson(sb.toString(), RecodeMessage.class);
                 Log.e("http������ý��0",JSONUtils.toJsonWithGson(recodeMess));
                 String code=recodeMess.getRespCode();
                 int Code=Integer.parseInt(code);
                 if(Code==0){
                 	mess.setRespCode(0);
                     mess.setRespMsg(JSONUtils.toJsonWithGson(recodeMess.getRespMsg()));
                 }else{
                 	mess.setRespCode(1);
                     mess.setRespMsg("δ�ҵ���ƿ�ŵ�������Ϣ");
                 }
                 
            }
            
           
            Log.e("http������ý��1",JSONUtils.toJsonWithGson(mess));
            return mess;
        } catch (IOException e) {
            e.printStackTrace();
            mess.setRespCode(-1);
            mess.setRespMsg("�����쳣:"+e.toString());
            return mess;
        }
    }
  
}

