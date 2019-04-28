package com.hsic.sy.supply.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.Envelope;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.NetWorkHelper;

public class WebServiceCallHelper {
    private Context mContext;

    public WebServiceCallHelper(Context context) {
        super();
        mContext = context;
    }

    public HsicMessage UpLoadData(String propertyValue) {
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
        GetDataByGP orderdeclData = new GetDataByGP(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "json");// 参数名称
        map.put("propertyValue", propertyValue);
        propertyList.add(map);
        try {
            Envelope envelope = new Envelope();
            Object object = orderdeclData.recevieData("PostData", propertyList,
                    null, null, true);// 方法名：UpCJData

            envelope = (Envelope) JSONUtils.toObjectWithGson(
                    JSONUtils.toJsonWithGson(object), Envelope.class);
            if (envelope.getValue().equals("0")) {
                hsicMess.setRespCode(0);
                hsicMess.setRespMsg(envelope.getValue());
            } else if (envelope.getValue().equals("1")) {
                hsicMess.setRespCode(0);
                hsicMess.setRespMsg(envelope.getValue());
            } else if (envelope.getValue().equals("2")) {
                hsicMess.setRespCode(0);
                hsicMess.setRespMsg(envelope.getValue());
            } else if (envelope.getValue().equals("3")) {
                hsicMess.setRespCode(0);
                hsicMess.setRespMsg(envelope.getValue());
            } else if (envelope.getValue().equals("4")) {
                hsicMess.setRespCode(0);
                hsicMess.setRespMsg(envelope.getValue());
            } else {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg(object.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;

    }

    /**
     * 站点可分配订单查询
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage SearchAssignSale(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("SearchAssignSale", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;

    }

    /**
     * 站点可新增车次查询
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage GetTruck_info(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("GetTruck_info", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 站点可添加订单车辆查询
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage GetTruckNoRecords(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("GetTruckNoRecords", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 站点可添加满瓶查询
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage GetTruckNoUseregcode(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("GetTruckNoUseregcode", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 站点可用员工查询
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage GetEmpNewList(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("GetEmpNewList", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 站点所有员工查询
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage GetAllEmpNewList(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("GetAllEmpNewList", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 获取某一段时间内所有的车次
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage GetTruckAllRecords(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("GetTruckAllRecords", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 新增车次
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage AddOneTruckNo(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("AddOneTruckNo", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 销售单绑定至车次
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage InsertTruckSaleRelation(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("InsertTruckSaleRelation", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 获得能够在本站点加瓶的车辆信息
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage GetTrucknoList(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("GetTrucknoList", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 更新车次状态
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage UpdateTruckNo(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("UpdateTruckNo", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 根据员工查询站点信息
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage GetEmpInfo(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "RequestData");// 参数名称
        map.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(map);
        propertyList.add(device);
//				Log.e("===", JSONUtils.toJsonWithGson(propertyList));
        try {
            Object object = orderdeclData.recevieData("GetEmpInfo", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }
//			hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
//					object.toString(), HsicMessage.class);

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    public HsicMessage GetEmpInfo_new(String DeviceID, String cardcode, String empid) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> CardCode = new HashMap<String, Object>();
        CardCode.put("propertyName", "cardcode");// 参数名称
        CardCode.put("propertyValue", cardcode);

        HashMap<String, Object> EmpID = new HashMap<String, Object>();
        EmpID.put("propertyName", "empid");// 参数名称
        EmpID.put("propertyValue", empid);

        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);

        propertyList.add(CardCode);
        propertyList.add(device);
        propertyList.add(EmpID);

        try {
            Object object = orderdeclData.recevieData("GetEmpInfo_new", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

//			hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
//					object.toString(), HsicMessage.class);

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 踏勘灶位信息查询
     *
     * @param DeviceID
     * @param
     * @param empid
     * @return
     */
    public HsicMessage GetExpList(String DeviceID, String empid) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> CardCode = new HashMap<String, Object>();
        CardCode.put("propertyName", "empid");// 参数名称
        CardCode.put("propertyValue", empid);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);

        propertyList.add(CardCode);
        propertyList.add(device);

        try {
            Object object = orderdeclData.recevieData("GetExpList", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 上传踏勘灶位信息
     */
    public HsicMessage uploadExpInfo(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> CardCode = new HashMap<String, Object>();
        CardCode.put("propertyName", "RequestData");// 参数名称
        CardCode.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);

        propertyList.add(CardCode);
        propertyList.add(device);

        try {
            Object object = orderdeclData.recevieData("uploadExpInfo", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }
//			hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
//					object.toString(), HsicMessage.class);

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /***
     * 上传附件关联信息
     */
    public HsicMessage UpFileReletionInfo(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> CardCode = new HashMap<String, Object>();
        CardCode.put("propertyName", "RequestData");// 参数名称
        CardCode.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);

        propertyList.add(CardCode);
        propertyList.add(device);

        try {
            Object object = orderdeclData.recevieData("UpFileReletionInfo", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /*
     * 站点登录（根据账号密码）
     */
    public HsicMessage EmployeeLogin(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> CardCode = new HashMap<String, Object>();
        CardCode.put("propertyName", "RequestData");// 参数名称
        CardCode.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);

        propertyList.add(CardCode);
        propertyList.add(device);

        try {
            Object object = orderdeclData.recevieData("EmployeeLogin", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 供应站点库存统计
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage SupplyQPNOCount(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> CardCode = new HashMap<String, Object>();
        CardCode.put("propertyName", "stationID");// 参数名称
        CardCode.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);

        propertyList.add(CardCode);
        propertyList.add(device);

        try {
            Object object = orderdeclData.recevieData("SupplyQPNOCount", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 钢瓶配送统计
     *
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage DeliveryQPNOCount(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> CardCode = new HashMap<String, Object>();
        CardCode.put("propertyName", "stationID");// 参数名称
        CardCode.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);

        propertyList.add(CardCode);
        propertyList.add(device);

        try {
            Object object = orderdeclData.recevieData("DeliveryQPNOCount", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    public HsicMessage uploadFileReletionInfo(String[] argsName, String methodName,
                                              String[] data) {
        HsicMessage mess = new HsicMessage();
        mess.setRespCode(8);
        NetWorkHelper mHelper = new NetWorkHelper(mContext);
        try {
            if (!mHelper.checkNetworkStatus()) {
                mess.setRespMsg("网络连接失败");
                mess.setRespCode(3);
                return mess;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            mess.setRespMsg("network check error!");
            mess.setRespCode(4);
            return mess;
        }
        GetDataByStation orderdeclData = new GetDataByStation(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < data.length; i++) {
            HashMap<String, Object> map1 = new HashMap<String, Object>();
            map1.put("propertyName", argsName[i]);// 参数名称
            map1.put("propertyValue", data[i]);
            propertyList.add(map1);
        }
        try {
            Object object = orderdeclData.recevieData("UpFileReletionInfo", propertyList,
                    null, null, true);// 方法名：UpCJData

            if (object.toString().equals("false")) {
                mess.setRespCode(5);
                mess.setRespMsg("服务调用失败");
            } else {
                mess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mess.setRespCode(6);
            mess.setRespMsg("接口异常：上传信息!");
        }
        return mess;
    }

    /**
     * 获取所有站点信息
     */
    public HsicMessage GetStationInfo(String DeviceID) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(device);

        try {
            Object object = orderdeclData.recevieData("GetStationInfo", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 钢瓶跨站点调拨执行操作
     * @param DeviceID
     * @param currentStationId
     * @param aimStationId
     * @param userRegioncode
     * @param Type
     * @param Operation
     * @return
     */
    public HsicMessage TransferByStation(String DeviceID, String currentStationId, String aimStationId,
                                         String userRegioncode, String Type, String Operation) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);
        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);
        propertyList.add(device);

        HashMap<String, Object> currentstationId = new HashMap<String, Object>();
        currentstationId.put("propertyName", "currentStationId");// 参数名称
        currentstationId.put("propertyValue", currentStationId);
        propertyList.add(currentstationId);

        HashMap<String, Object> aimstationId = new HashMap<String, Object>();
        aimstationId.put("propertyName", "aimStationId");// 参数名称
        aimstationId.put("propertyValue", aimStationId);
        propertyList.add(aimstationId);

        HashMap<String, Object> userregioncode = new HashMap<String, Object>();
        userregioncode.put("propertyName", "userRegioncode");// 参数名称
        userregioncode.put("propertyValue", userRegioncode);
        propertyList.add(userregioncode);

        HashMap<String, Object> type = new HashMap<String, Object>();
        type.put("propertyName", "Type");// 参数名称
        type.put("propertyValue", Type);
        propertyList.add(type);

        HashMap<String, Object> operation = new HashMap<String, Object>();
        operation.put("propertyName", "Operation");// 参数名称
        operation.put("propertyValue", Operation);
        propertyList.add(operation);
        try {
            Object object = orderdeclData.recevieData("TransferByStation", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }

    /**
     * 员工登录
     * @param DeviceID
     * @param RequestData
     * @return
     */
    public HsicMessage Login(String DeviceID, String RequestData) {
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
        GetDataByStation orderdeclData = new GetDataByStation(mContext);

        List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();

        HashMap<String, Object> CardCode = new HashMap<String, Object>();
        CardCode.put("propertyName", "RequestData");// 参数名称
        CardCode.put("propertyValue", RequestData);
        HashMap<String, Object> device = new HashMap<String, Object>();
        device.put("propertyName", "DeviceID");// 参数名称
        device.put("propertyValue", DeviceID);

        propertyList.add(CardCode);
        propertyList.add(device);
        try {
            Object object = orderdeclData.recevieData("Login", propertyList,
                    null, null, true);// 方法名：UpCJData
            if (object.toString().equals("false")) {
                hsicMess.setRespCode(5);
                hsicMess.setRespMsg("服务调用失败");
            } else {
                hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
                        object.toString(), HsicMessage.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hsicMess.setRespCode(6);
            hsicMess.setRespMsg("接口异常!");
        }
        return hsicMess;
    }
}
