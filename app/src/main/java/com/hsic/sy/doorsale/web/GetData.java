package com.hsic.sy.doorsale.web;

import android.content.Context;
import android.preference.PreferenceManager;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/8.
 */

public class GetData {
    private String nameSapceStr="http://tempuri.org/";
    private String headerStr="http://";
    private String end="/SHLPGMTWs/SHLPGMT.asmx";
    private String nameSpace;
    private String endPoint;//
    private String ip,port;
    private int overTime;
    private Context context;
    public GetData(Context context){
        this.nameSpace = nameSapceStr;
        ip="192.168.1.58";
        port="83";
        ip = PreferenceManager.getDefaultSharedPreferences(context).getString(
                "StationServer", "192.168.1.58");
        String portDefaultString = "83";// 端口号默认值
        port = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("StationPort", portDefaultString);
        this.endPoint = headerStr + ip+":"+port + end;//配置地址
        String outTime="60";

    }
    public Object recevieData(String methodName, List<Map<String, Object>> propertyList, Boolean isSimpleRet) {
        // 返回的查询结果
        String soapAction = nameSpace + methodName;
        // 指定WebService的命名空间和调用的方法名
        SoapObject request = new SoapObject(this.nameSpace, methodName);

        // 设置需要返回请求对象的参数
        for (Map<String, Object> map : propertyList) {

            request.addProperty(map.get("propertyName").toString(),

                    map.get("propertyValue"));

        }
        // 设置soap的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        // 设置是否调用的是dotNet开发的
        envelope.dotNet = true;

        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transport = new HttpTransportSE(endPoint,
                overTime * 1000);
        // web service请求
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // 得到返回结果
        try {
            if (!isSimpleRet) {

                SoapObject o = (SoapObject) envelope.bodyIn;

                if (o != null) {

                    return o;
                }
            } else {
                return envelope.getResponse();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public KvmSerializable parseToObject(SoapObject soapObject,
                                         Class objectClass) throws InstantiationException,
            IllegalAccessException {

        KvmSerializable result = (KvmSerializable) objectClass.newInstance();

        int numOfAttr = result.getPropertyCount();

        for (int i = 0; i < numOfAttr; i++) {
            PropertyInfo info = new PropertyInfo();
            result.getPropertyInfo(i, null, info);
            // 处理property不存在的情况
            try {
                Object object = soapObject.getProperty(info.name);
                result.setProperty(i, object);
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }
}
