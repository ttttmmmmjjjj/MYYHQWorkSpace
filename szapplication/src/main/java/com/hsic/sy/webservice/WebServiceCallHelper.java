package com.hsic.sy.webservice;

import android.content.Context;
import android.util.Log;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.utils.HsicMessage;
import com.hsic.sy.utils.NetWorkHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WebServiceCallHelper {
    private Context mContext;

    public WebServiceCallHelper(Context context) {
        super();
        mContext = context;
    }

    public HsicMessage NetTest(String propertyValue) {
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
                Locale.getDefault());
        String time = simpleDateFormat.format(date);//��ȡѲ������
        HsicMessage hsicMess = new HsicMessage();
        hsicMess.setRespCode(8);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);
        try {
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespMsg("网络连接失败");
                hsicMess.setRespCode(-1);
                return hsicMess;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            hsicMess.setRespMsg("network check error!");
            hsicMess.setRespCode(-2);
            return hsicMess;
        }
        GetData orderdeclData = new GetData(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> RunTime = new HashMap<String, Object>();
        RunTime.put("propertyName", "RunTime");// 参数名称
        RunTime.put("propertyValue", time);
        propertyList.add(RunTime);
        Log.e("propertyList",JSONUtils.toJsonWithGson(propertyList));
        try {
            Object object = orderdeclData.recevieData("netTest", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess.setRespCode(0);
                hsicMess.setRespMsg(object.toString());
//                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
//                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;

    }

}
