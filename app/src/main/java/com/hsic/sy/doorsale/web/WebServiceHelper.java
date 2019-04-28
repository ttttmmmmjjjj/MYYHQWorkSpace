package com.hsic.sy.doorsale.web;

import android.content.Context;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.NetWorkHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/8.
 */

public class WebServiceHelper {
    private Context mContext;
    public WebServiceHelper(Context context) {//实现构造函数
        super();
        mContext = context;
    }

    /**
     *员工登录
     * @param requestData
     * @param DeviceID
     * @return
     */
    public HsicMessage EmployeeLogin(String requestData,String DeviceID,String vertion){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }
        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "RequestData");// 参数名称
        map2.put("propertyValue",requestData);
        HashMap<String, Object> map3= new HashMap<String, Object>();
        map3.put("propertyName", "vertion");// 参数名称
        map3.put("propertyValue",vertion);
        propertyList.add(map1);
        propertyList.add(map2);
        propertyList.add(map3);
//
        try {
            Object object = getData.recevieData("EmployeeLogin", propertyList,true);//添加参数调用recevieData方法返回结果
//            Log.e("调用结果", JSONUtils.toJsonWithGson(object));
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }


        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }
    /**
     * 获取用户基本信息
     * @param requestData
     * @param DeviceID
     * @param StationId
     * @return
     */
    public HsicMessage getBasicUserInfo(String requestData,String DeviceID,String StationId){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }
        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "RequestData");// 参数名称
        map2.put("propertyValue",requestData);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("propertyName", "StationId");// 参数名称
        map3.put("propertyValue",StationId);
        propertyList.add(map1);
        propertyList.add(map2);
        propertyList.add(map3);
        try {
            Object object = getData.recevieData("getBasicUserInfo", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }

    /**
     * 下单
     * @param requestData
     * @param DeviceID
     * @return
     */
    public HsicMessage subscriberOrder(String requestData,String DeviceID,String StationId){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }
        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "RequestData");// 参数名称
        map2.put("propertyValue",requestData);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("propertyName", "StationId");// 参数名称
        map3.put("propertyValue",StationId);
        propertyList.add(map1);
        propertyList.add(map2);
        propertyList.add(map3);
        try {
            Object object = getData.recevieData("subscriberOrder", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }

    /***
     * 根据订单号查询订单
     * @param requestData
     * @param DeviceID
     * @return
     */
    public HsicMessage getOrderBySaleId(String requestData,String DeviceID,String StationId){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }
        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "RequestData");// 参数名称
        map2.put("propertyValue",requestData);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("propertyName", "StationId");// 参数名称
        map3.put("propertyValue",StationId);
        propertyList.add(map1);
        propertyList.add(map2);
        propertyList.add(map3);
        try {
            Object object = getData.recevieData("getOrderBySaleId", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }
//            Log.e("调用结果", object.toString());
        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }

    /**
     * 提货
     * @param requestData
     * @param DeviceID
     * @return
     */
    public HsicMessage finishOrder(String requestData,String DeviceID,String StationId){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }
        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "RequestData");// 参数名称
        map2.put("propertyValue",requestData);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("propertyName", "StationId");// 参数名称
        map3.put("propertyValue",StationId);
        propertyList.add(map1);
        propertyList.add(map2);
        propertyList.add(map3);
        try {
            Object object = getData.recevieData("finishOrder", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }
//            /Log.e("调用结果", object.toString());
        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }

    /**
     * 订单作废
     * @param DeviceID
     * @param StationID
     * @param saleID
     * @return
     */
    public HsicMessage cancelOrder(String DeviceID,String StationID, String RequestData){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }
        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "StationID");// 参数名称
        map2.put("propertyValue",StationID);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("propertyName", "RequestData");// 参数名称
        map3.put("propertyValue",RequestData);

        propertyList.add(map1);
        propertyList.add(map2);
        propertyList.add(map3);
        try {
            Object object = getData.recevieData("cancelOrder", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }
//            Log.e("调用结果", object.toString());
        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }

    /**
     * 获取该站点，该操作人员底下订单
     * @param DeviceID
     * @param StationID 站点
     * @param operationID  操作员
     * @param saleStatus  1:下单 ，2:提货 ，3:作废 4：补打
     * @return
     */
    public HsicMessage getAllSale(String DeviceID, String StationID, String operationID, String saleStatus){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }

        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "StationID");// 参数名称
        map2.put("propertyValue",StationID);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("propertyName", "operationID");// 参数名称
        map3.put("propertyValue",operationID);
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put("propertyName", "saleStatus");// 参数名称
        map4.put("propertyValue",saleStatus);
        propertyList.add(map1);
        propertyList.add(map2);
        propertyList.add(map3);
        propertyList.add(map4);
        try {
            Object object = getData.recevieData("getAllSale", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }
//            Log.e("调用结果", object.toString());
        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }

    /**
     * 获取设备信息
     * @param DeviceID
     * @param StationID
     * @return
     */
    public HsicMessage getDevice(String DeviceID, String StationID){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }

        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "StationID");// 参数名称
        map2.put("propertyValue",StationID);
        propertyList.add(map1);
        propertyList.add(map2);
        try {
            Object object = getData.recevieData("getDevice", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }

    /**
     * 换货
     * @param DeviceID
     * @param StationID
     * @param saleID 订单号
     * @param lastFullID  上次满瓶号
     * @param currentFullID 本次满瓶号
     * @return
     */
    public HsicMessage exchangeGoods(String DeviceID, String StationID,String saleID,
                                     String lastFullID,String currentFullID){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }

        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "StationID");// 参数名称
        map2.put("propertyValue",StationID);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("propertyName", "saleID");// 参数名称
        map3.put("propertyValue",saleID);
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put("propertyName", "lastFullID");// 参数名称
        map4.put("propertyValue",lastFullID);
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        map5.put("propertyName", "currentFullID");// 参数名称
        map5.put("propertyValue",currentFullID);
        propertyList.add(map1);
        propertyList.add(map2);
        propertyList.add(map3);
        propertyList.add(map4);
        propertyList.add(map5);
        try {
            Object object = getData.recevieData("exchangeGoods", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }
    public HsicMessage getSaleCount(String DeviceID, String StationID){
        HsicMessage hsicMess=new HsicMessage();
        hsicMess.setRespCode(-1);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用
        try{
            if (!mHelper.checkNetworkStatus()) {
                hsicMess.setRespCode(3);
                hsicMess.setRespMsg("网络连接失败");
                return hsicMess;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            ex.toString();
            hsicMess.setRespMsg("获取网络状态异常"+ex.toString());
        }

        GetData getData=new GetData(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("propertyName", "DeviceID");// 参数名称
        map1.put("propertyValue",DeviceID);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("propertyName", "StationID");// 参数名称
        map2.put("propertyValue",StationID);
        propertyList.add(map1);
        propertyList.add(map2);

        try {
            Object object = getData.recevieData("getSaleCount", propertyList,true);//添加参数调用recevieData方法返回结果
            if(object.toString().equals("false")){
                hsicMess.setRespCode(1);
                hsicMess.setRespMsg("调用失败");
            }else{
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(-4);
            hsicMess.setRespMsg("调用异常");
        }
        return hsicMess;
    }

}
