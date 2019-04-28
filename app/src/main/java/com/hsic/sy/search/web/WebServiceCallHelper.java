package com.hsic.sy.search.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.MyLog;
import com.hsic.sy.bll.NetWorkHelper;
import com.hsic.sy.supply.web.GetDataByStation;

public class WebServiceCallHelper {
	private Context mContext;

	public WebServiceCallHelper(Context context) {
		super();
		mContext = context;
	}
	/**
	 * 下载整改信息
	 * @param DeviceID 设备名称
	 * @param empid    员工号
	 * @return
	 */
	public HsicMessage DownLoadInfo(String DeviceID, String empid) {
		HsicMessage mess = new HsicMessage();
		mess.setRespCode(8);
		NetWorkHelper mHelper = new NetWorkHelper(mContext);
		try {
			if (!mHelper.checkNetworkStatus()) {
				mess.setRespCode(3);
				mess.setRespMsg("网络连接失败");
				return mess;
			}
		} catch (Exception e1) {
			MyLog.e("校验网络出现异常", e1.toString());
			e1.printStackTrace();
			mess.setRespCode(4);
			mess.setRespMsg("network check error!");
			return mess;
		}
		GetData orderdeclData = new GetData(mContext);

		// 参数类表
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map.put("propertyName", "DeviceID");// 参数名称
		map.put("propertyValue", DeviceID);
		map2.put("propertyName", "empid");// 参数名称
		map2.put("propertyValue", empid);
		propertyList.add(map);
		propertyList.add(map2);
		try {
			Object object = orderdeclData.recevieData("DownloadRectifyInfo",propertyList, null, null, true);// 方法名：UpCJData

			if (object.toString().equals("false")) {
				mess.setRespCode(5);
				mess.setRespMsg("服务调用失败");
			} else {
				mess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}
		} catch (Exception e) {
			MyLog.e("信息下载调用WebService出现异常", e.toString());
			e.printStackTrace();
			mess.setRespCode(6);
			mess.setRespMsg("接口异常：上传信息!");
		}
		return mess;
	}
	public HsicMessage SearchLogin(String DeviceID, String empid) {
		HsicMessage mess = new HsicMessage();
		mess.setRespCode(8);
		NetWorkHelper mHelper = new NetWorkHelper(mContext);
		try {
			if (!mHelper.checkNetworkStatus()) {
				mess.setRespCode(3);
				mess.setRespMsg("网络连接失败");
				return mess;
			}
		} catch (Exception e1) {
			MyLog.e("校验网络出现异常", e1.toString());
			e1.printStackTrace();
			mess.setRespCode(4);
			mess.setRespMsg("network check error!");
			return mess;
		}
		GetData orderdeclData = new GetData(mContext);

		// 参数类表
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map.put("propertyName", "DeviceID");// 参数名称
		map.put("propertyValue", DeviceID);
		map2.put("propertyName", "empid");// 参数名称
		map2.put("propertyValue", empid);
		propertyList.add(map);
		propertyList.add(map2);
		try {
			Object object = orderdeclData.recevieData("DownloadRectifyInfo",propertyList, null, null, true);// 方法名：UpCJData

			if (object.toString().equals("false")) {
				mess.setRespCode(5);
				mess.setRespMsg("服务调用失败");
			} else {
				mess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}
		} catch (Exception e) {
			MyLog.e("信息下载调用WebService出现异常", e.toString());
			e.printStackTrace();
			mess.setRespCode(6);
			mess.setRespMsg("接口异常：上传信息!");
		}
		return mess;
	}
	public HsicMessage GetUserRegionCode(String DeviceID,String TruckNoID) {
		HsicMessage mess = new HsicMessage();
		mess.setRespCode(8);
		NetWorkHelper mHelper = new NetWorkHelper(mContext);
		try {
			if (!mHelper.checkNetworkStatus()) {
				mess.setRespCode(3);
				mess.setRespMsg("网络连接失败");
				return mess;
			}
		} catch (Exception e1) {
			MyLog.e("校验网络出现异常", e1.toString());
			e1.printStackTrace();
			mess.setRespCode(4);
			mess.setRespMsg("network check error!");
			return mess;
		}
		GetData orderdeclData = new GetData(mContext);

		// 参数类表
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map1.put("propertyName", "DeviceID");// 参数名称
		map1.put("propertyValue", DeviceID);
		map2.put("propertyName", "TruckNoID");// 参数名称
		map2.put("propertyValue", TruckNoID);
		propertyList.add(map1);
		propertyList.add(map2);
		try {
			Object object = orderdeclData.recevieData("GetQPByTruckID",propertyList, null, null, true);// 方法名：UpCJData

			if (object.toString().equals("false")) {
				mess.setRespCode(5);
				mess.setRespMsg("服务调用失败");
			} else {
				mess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}
		} catch (Exception e) {
			MyLog.e("获取满屏信息调用WebService出现异常", e.toString());
			e.printStackTrace();
			mess.setRespCode(6);
			mess.setRespMsg("接口异常：上传信息!");
		}
		return mess;
	}
	/**
	 *
	 * @param argsName
	 * @param methodName
	 * @param data
	 * @return 上传信息
	 */


	public HsicMessage uploadInfo(String[] argsName, String methodName,
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
		GetData orderdeclData = new GetData(mContext);
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < data.length; i++) {
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("propertyName", argsName[i]);// 参数名称
			map1.put("propertyValue", data[i]);
			propertyList.add(map1);
		}
		try {
			Object object = orderdeclData.recevieData(methodName, propertyList,
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
	 * uploadFileReletionInfo
	 * @param argsName
	 * @param methodName
	 * @param data
	 * @return
	 */
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
			Object object = orderdeclData.recevieData(methodName, propertyList,
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
	 *
	 * @param argsName
	 * @param methodName
	 * @param data
	 * @return 获取手机信息
	 */

	public HsicMessage getDeviceIdInfo(String[] argsName, String methodName,
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
		GetData orderdeclData = new GetData(mContext);
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < data.length; i++) {
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("propertyName", argsName[i]);// 参数名称
			map1.put("propertyValue", data[i]);
			propertyList.add(map1);
		}
		try {
			Object object = orderdeclData.recevieData(methodName, propertyList,
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
	 * 获取订单状态
	 * @return
	 */
	public HsicMessage getTelStatus(String DeviceID, String TruckNoID,
									String saleID){
		HsicMessage mess = new HsicMessage();
		NetWorkHelper mHelper = new NetWorkHelper(mContext);
		try{
			if (!mHelper.checkNetworkStatus()) {
				mess.setRespMsg("网络连接失败");
				mess.setRespCode(-1);
				return mess;
			}
			GetData orderdeclData = new GetData(mContext);
			// 参数类表
			List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			HashMap<String, Object> map3 = new HashMap<String, Object>();
			map1.put("propertyName", "DeviceID");// 参数名称
			map1.put("propertyValue", DeviceID);
			map2.put("propertyName", "TruckNoID");// 参数名称
			map2.put("propertyValue", TruckNoID);
			map3.put("propertyName", "SaleID");// 参数名称
			map3.put("propertyValue", saleID);
			propertyList.add(map1);
			propertyList.add(map2);
			propertyList.add(map3);
			Object object = orderdeclData.recevieData("getSaleidState", propertyList,
					null, null, true);

			if (object.toString().equals("false")) {
				mess.setRespCode(-2);
				mess.setRespMsg("服务调用失败");
				return mess;
			} else {
				mess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}
		}catch(Exception ex){
			MyLog.e("获取订单状态出现异常", ex.toString());
			ex.toString();
		}
		return  mess;
	}
}
