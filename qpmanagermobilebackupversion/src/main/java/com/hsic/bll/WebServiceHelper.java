package com.hsic.bll;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.util.Log;

import com.hsic.bean.HsicMessage;
import com.hsic.qpmanager.util.json.JSONUtils;

public class WebServiceHelper {

	private Context mContext;

	public WebServiceHelper(Context context) {//ʵ�ֹ��캯��
		super();
		mContext = context;
	}
	public HsicMessage EmployeeLoginNew_ALLInfo(String DeviceID,String RequestData) {
		HsicMessage hsicMess = new HsicMessage();//����HsicMessage
		hsicMess.setRespCode(-1);//����RespCode����
		NetWorkHelper mHelper = new NetWorkHelper(mContext);
		try {
			if (!mHelper.checkNetworkStatus()) {
				hsicMess.setRespMsg("请检查网络");
				hsicMess.setRespCode(-2);
				return hsicMess;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			hsicMess.setRespMsg("网络异常");
			hsicMess.setRespCode(-3);
			return hsicMess;
		}
		GetWebResult orderdeclData = new GetWebResult(mContext);
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
		HashMap<String, Object> hp_DeviceID = new HashMap<String, Object>();
		hp_DeviceID.put("propertyName", "DeviceID");// 参数名称
		hp_DeviceID.put("propertyValue",DeviceID);
		propertyList.add(hp_DeviceID);
		HashMap<String, Object> hp_RequestData = new HashMap<String, Object>();
		hp_RequestData.put("propertyName", "RequestData");// 参数名称
		hp_RequestData.put("propertyValue",RequestData);
		propertyList.add(hp_RequestData);
		try {
			Object object = orderdeclData.recevieData("EmployeeLoginNew", propertyList,true);
			if (object.toString().equals("false")) {
				hsicMess.setRespCode(5);
				hsicMess.setRespMsg("服务调用失败");
			} else {

				hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			hsicMess.setRespCode(-4);
			hsicMess.setRespMsg("服务调用异常!");
		}
		return hsicMess;
	}
	/*
	下载巡检信息
	 */
	public HsicMessage InspectionInfo(String DeviceID,String RequestData) {
		//			参数的含义，其一是参数的名字，
		//			其二是调用webService的方法名字
		//			其三是调用webService的方法要传入的参数

		HsicMessage hsicMess = new HsicMessage();//创建HsicMessage

		hsicMess.setRespCode(-1);//设置RespCode属性

		NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用

		try {

			if (!mHelper.checkNetworkStatus()) {

				hsicMess.setRespMsg("网络连接失败");

				hsicMess.setRespCode(-2);

				return hsicMess;
			}
		} catch (Exception e1) {

			e1.printStackTrace();

			hsicMess.setRespMsg("网络检查失败");

			hsicMess.setRespCode(-3);

			return hsicMess;
		}

		GetWebResult orderdeclData = new GetWebResult(mContext);
		//在调用webService时的基本参数设置，这个是在构建GetData对象时完成的
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
		HashMap<String, Object> hp_DeviceID = new HashMap<String, Object>();
		hp_DeviceID.put("propertyName", "DeviceID");// 参数名称
		hp_DeviceID.put("propertyValue",DeviceID);
		propertyList.add(hp_DeviceID);
		HashMap<String, Object> hp_RequestData = new HashMap<String, Object>();
		hp_RequestData.put("propertyName", "RequestData");// 参数名称
		hp_RequestData.put("propertyValue",RequestData);
		propertyList.add(hp_RequestData);
		try {
			Object object = orderdeclData.recevieData("UpSaleInspectionInfo_new", propertyList,true);//添加参数调用recevieData方法返回结果
			if (object.toString().equals("false")) {
				hsicMess.setRespCode(5);
				hsicMess.setRespMsg("服务调用失败");
			} else {
				hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}

		} catch (Exception e) {
			e.printStackTrace();

			hsicMess.setRespCode(-4);

			hsicMess.setRespMsg("接口异常：上传信息!");
		}
		return hsicMess;
	}
	/*
	配送订单时:获取订单信息
	 */
	public HsicMessage SearchAssignSale(String DeviceID,String RequestData) {
		//			参数的含义，其一是参数的名字，
		//			其二是调用webService的方法名字
		//			其三是调用webService的方法要传入的参数

		HsicMessage hsicMess = new HsicMessage();//创建HsicMessage
		hsicMess.setRespCode(-1);//设置RespCode属性
		NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用

		try {

			if (!mHelper.checkNetworkStatus()) {
				hsicMess.setRespMsg("网络连接失败");
				hsicMess.setRespCode(-2);
				return hsicMess;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			hsicMess.setRespMsg("网络检查失败");
			hsicMess.setRespCode(-3);
			return hsicMess;
		}

		GetWebResult orderdeclData = new GetWebResult(mContext);
		//在调用webService时的基本参数设置，这个是在构建GetData对象时完成的
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
		HashMap<String, Object> hp_DeviceID = new HashMap<String, Object>();
		hp_DeviceID.put("propertyName", "DeviceID");// 参数名称
		hp_DeviceID.put("propertyValue",DeviceID);
		propertyList.add(hp_DeviceID);
		HashMap<String, Object> hp_RequestData = new HashMap<String, Object>();
		hp_RequestData.put("propertyName", "RequestData");// 参数名称
		hp_RequestData.put("propertyValue",RequestData);
		propertyList.add(hp_RequestData);
		try {
			Object object = orderdeclData.recevieData("SearchAssignSale", propertyList,true);//添加参数调用recevieData方法返回结果

			if (object.toString().equals("false")) {
				hsicMess.setRespCode(5);
				hsicMess.setRespMsg("服务调用失败");
			} else {
				hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}
//			Log.e("hsicMess",JSONUtils.toJsonWithGson(hsicMess));
		} catch (Exception e) {
			e.printStackTrace();
			hsicMess.setRespCode(-4);
			hsicMess.setRespMsg("接口异常：SearchAssignSale!");
		}
		return hsicMess;
	}
	/*
	分配订单:获取订单信息
	 */
	public HsicMessage SearchSale(String DeviceID,String RequestData) {
		//			参数的含义，其一是参数的名字，
		//			其二是调用webService的方法名字
		//			其三是调用webService的方法要传入的参数

		HsicMessage hsicMess = new HsicMessage();//创建HsicMessage

		hsicMess.setRespCode(-1);//设置RespCode属性

		NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用

		try {

			if (!mHelper.checkNetworkStatus()) {

				hsicMess.setRespMsg("网络连接失败");

				hsicMess.setRespCode(-2);

				return hsicMess;
			}
		} catch (Exception e1) {

			e1.printStackTrace();

			hsicMess.setRespMsg("网络检查失败");

			hsicMess.setRespCode(-3);

			return hsicMess;
		}

		GetWebResult orderdeclData = new GetWebResult(mContext);
		//在调用webService时的基本参数设置，这个是在构建GetData对象时完成的
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
		HashMap<String, Object> hp_DeviceID = new HashMap<String, Object>();
		hp_DeviceID.put("propertyName", "DeviceID");// 参数名称
		hp_DeviceID.put("propertyValue",DeviceID);
		propertyList.add(hp_DeviceID);
		HashMap<String, Object> hp_RequestData = new HashMap<String, Object>();
		hp_RequestData.put("propertyName", "RequestData");// 参数名称
		hp_RequestData.put("propertyValue",RequestData);
		propertyList.add(hp_RequestData);
		try {
			Object object = orderdeclData.recevieData("SearchSale", propertyList,true);//添加参数调用recevieData方法返回结果
			if (object.toString().equals("false")) {
				hsicMess.setRespCode(5);
				hsicMess.setRespMsg("服务调用失败");
			} else {
				hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}

		} catch (Exception e) {
			e.printStackTrace();

			hsicMess.setRespCode(-4);

			hsicMess.setRespMsg("接口异常：上传信息!");
		}
		return hsicMess;
	}
	/*
	分单时:更新分单数据
	 */
	public HsicMessage UpdateSale(String DeviceID,String RequestData) {
		//			参数的含义，其一是参数的名字，
		//			其二是调用webService的方法名字
		//			其三是调用webService的方法要传入的参数

		HsicMessage hsicMess = new HsicMessage();//创建HsicMessage

		hsicMess.setRespCode(-1);//设置RespCode属性

		NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用

		try {

			if (!mHelper.checkNetworkStatus()) {

				hsicMess.setRespMsg("网络连接失败");

				hsicMess.setRespCode(-2);

				return hsicMess;
			}
		} catch (Exception e1) {

			e1.printStackTrace();

			hsicMess.setRespMsg("网络检查失败");

			hsicMess.setRespCode(-3);

			return hsicMess;
		}

		GetWebResult orderdeclData = new GetWebResult(mContext);
		//在调用webService时的基本参数设置，这个是在构建GetData对象时完成的
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
		HashMap<String, Object> hp_DeviceID = new HashMap<String, Object>();
		hp_DeviceID.put("propertyName", "DeviceID");// 参数名称
		hp_DeviceID.put("propertyValue",DeviceID);
		propertyList.add(hp_DeviceID);
		HashMap<String, Object> hp_RequestData = new HashMap<String, Object>();
		hp_RequestData.put("propertyName", "RequestData");// 参数名称
		hp_RequestData.put("propertyValue",RequestData);
		propertyList.add(hp_RequestData);

		try {
			Object object = orderdeclData.recevieData("UpdateSale", propertyList,true);//添加参数调用recevieData方法返回结果
			if (object.toString().equals("false")) {
				hsicMess.setRespCode(5);
				hsicMess.setRespMsg("服务调用失败");
			} else {
				hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}

		} catch (Exception e) {
			e.printStackTrace();

			hsicMess.setRespCode(-4);

			hsicMess.setRespMsg("接口异常：上传信息!");
		}
		return hsicMess;
	}
	/*
	上传巡检信息
	 */
	public HsicMessage UpUserInspection(String DeviceID,String RequestData) {
		//			参数的含义，其一是参数的名字，
		//			其二是调用webService的方法名字
		//			其三是调用webService的方法要传入的参数

		HsicMessage hsicMess = new HsicMessage();//创建HsicMessage
		hsicMess.setRespCode(-1);//设置RespCode属性
		NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用

		try {
			if (!mHelper.checkNetworkStatus()) {
				hsicMess.setRespMsg("网络连接失败");
				hsicMess.setRespCode(-2);
				return hsicMess;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			hsicMess.setRespMsg("网络检查失败");
			hsicMess.setRespCode(-3);
			return hsicMess;
		}
		GetWebResult orderdeclData = new GetWebResult(mContext);
		//在调用webService时的基本参数设置，这个是在构建GetData对象时完成的
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
		HashMap<String, Object> hp_DeviceID = new HashMap<String, Object>();
		hp_DeviceID.put("propertyName", "DeviceID");// 参数名称
		hp_DeviceID.put("propertyValue",DeviceID);
		propertyList.add(hp_DeviceID);
		HashMap<String, Object> hp_RequestData = new HashMap<String, Object>();
		hp_RequestData.put("propertyName", "RequestData");// 参数名称
		hp_RequestData.put("propertyValue",RequestData);
		propertyList.add(hp_RequestData);
		try {
			Object object = orderdeclData.recevieData("UpSaleInspectionInfo_new", propertyList,true);//添加参数调用recevieData方法返回结果
			hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
			hsicMess.setRespCode(-4);
			hsicMess.setRespMsg("接口异常：上传信息!");
		}
		return hsicMess;
	}
	/*
	分单时:更新分单数据
	 */
	public HsicMessage UpdateSaleInfo(String DeviceID,String RequestData) {
		//			参数的含义，其一是参数的名字，
		//			其二是调用webService的方法名字
		//			其三是调用webService的方法要传入的参数

		HsicMessage hsicMess = new HsicMessage();//创建HsicMessage
		hsicMess.setRespCode(-1);//设置RespCode属性
		NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用

		try {
			if (!mHelper.checkNetworkStatus()) {
				hsicMess.setRespMsg("网络连接失败");
				hsicMess.setRespCode(-2);
				return hsicMess;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			hsicMess.setRespMsg("网络检查失败");
			hsicMess.setRespCode(-3);
			return hsicMess;
		}
		GetWebResult orderdeclData = new GetWebResult(mContext);
		//在调用webService时的基本参数设置，这个是在构建GetData对象时完成的
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
		HashMap<String, Object> hp_DeviceID = new HashMap<String, Object>();
		hp_DeviceID.put("propertyName", "DeviceID");// 参数名称
		hp_DeviceID.put("propertyValue",DeviceID);
		propertyList.add(hp_DeviceID);
		HashMap<String, Object> hp_RequestData = new HashMap<String, Object>();
		hp_RequestData.put("propertyName", "RequestData");// 参数名称
		hp_RequestData.put("propertyValue",RequestData);
		propertyList.add(hp_RequestData);
		try {
			Object object = orderdeclData.recevieData("UpdateSaleInfo", propertyList,true);//添加参数调用recevieData方法返回结果
			if (object.toString().equals("false")) {
				hsicMess.setRespCode(5);
				hsicMess.setRespMsg("服务调用失败");
			} else {
				hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			hsicMess.setRespCode(-4);
			hsicMess.setRespMsg("接口异常：上传信息!");
		}
		return hsicMess;
	}

	/*
	获取站点下员工
	 */
	public HsicMessage SearchEmployee(String DeviceID,String RequestData) {
		//			参数的含义，其一是参数的名字，
		//			其二是调用webService的方法名字
		//			其三是调用webService的方法要传入的参数

		HsicMessage hsicMess = new HsicMessage();//创建HsicMessage
		hsicMess.setRespCode(-1);//设置RespCode属性
		NetWorkHelper mHelper = new NetWorkHelper(mContext);//判断网络是否可用

		try {
			if (!mHelper.checkNetworkStatus()) {
				hsicMess.setRespMsg("网络连接失败");
				hsicMess.setRespCode(-2);
				return hsicMess;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			hsicMess.setRespMsg("网络检查失败");
			hsicMess.setRespCode(-3);
			return hsicMess;
		}
		GetWebResult orderdeclData = new GetWebResult(mContext);
		//在调用webService时的基本参数设置，这个是在构建GetData对象时完成的
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//准备参数
		HashMap<String, Object> hp_DeviceID = new HashMap<String, Object>();
		hp_DeviceID.put("propertyName", "DeviceID");// 参数名称
		hp_DeviceID.put("propertyValue",DeviceID);
		propertyList.add(hp_DeviceID);
		HashMap<String, Object> hp_RequestData = new HashMap<String, Object>();
		hp_RequestData.put("propertyName", "RequestData");// 参数名称
		hp_RequestData.put("propertyValue",RequestData);
		propertyList.add(hp_RequestData);
		try {
			Object object = orderdeclData.recevieData("SearchEmployee", propertyList,true);//添加参数调用recevieData方法返回结果
			if (object.toString().equals("false")) {
				hsicMess.setRespCode(5);
				hsicMess.setRespMsg("服务调用失败");
			} else {
				hsicMess = (HsicMessage) JSONUtils.toObjectWithGson(
						object.toString(), HsicMessage.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			hsicMess.setRespCode(-4);
			hsicMess.setRespMsg("接口异常：上传信息!");
		}
		return hsicMess;
	}

	/**
	 * 上传FTP
	 * @param argsName
	 * @param methodName
	 * @param data
	 * @return
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
		GetWebResult orderdeclData = new GetWebResult(mContext);
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < data.length; i++) {
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("propertyName", argsName[i]);// 参数名称
			map1.put("propertyValue", data[i]);
			propertyList.add(map1);
		}
		try {
			Object object = orderdeclData.recevieData(methodName, propertyList, true);// 方法名：UpCJData

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
}