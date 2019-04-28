package com.hsic.sy.search.task;

import android.content.Context;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.search.bean.FileRelationInfo;
import com.hsic.sy.search.web.WebServiceCallHelper;

import java.util.ArrayList;
import java.util.List;

public class UpLoadData {
	/**
	 * �ϴ���ʷ������
	 * 
	 * @param deviceid
	 * @param
	 * @param context
	 * @return
	 */
	public HsicMessage UpLoadA(String path, String relationId, String deviceid,
			Context context) {
		HsicMessage hsicMessage = new HsicMessage();
		hsicMessage.setRespCode(10);
		try {
			List<FileRelationInfo> list = new ArrayList<FileRelationInfo>();
			FileRelationInfo fri = new FileRelationInfo();
			fri.setFilePath(path);
			fri.setRelationID(relationId);
			list.add(fri);
			String respMsg = JSONUtils.toJsonWithGson(list);
			hsicMessage.setRespMsg(respMsg);
			WebServiceCallHelper web = new WebServiceCallHelper(context);
			String requestData = JSONUtils.toJsonWithGson(hsicMessage);// web接口参数
			String[] webRe = { "DeviceID", "RequestData" };
			String webMethod = "UpFileReletionInfo";
			String[] value = { deviceid, requestData };
			hsicMessage = web.uploadInfo(webRe, webMethod, value);
		} catch (Exception ex) {
			ex.printStackTrace();
			hsicMessage.setRespCode(5);
			hsicMessage.setRespMsg("调用借口异常");
		}
		return hsicMessage;
	}
	/**
	 * 上传灶位勘探照片关联信息
	 * @param path
	 * @param relationId
	 * @param deviceid
	 * @param context
	 * @return
	 */
	public HsicMessage UpFileReletionInfo(String path, String relationId, String deviceid,
										  Context context) {
		HsicMessage hsicMessage = new HsicMessage();
		hsicMessage.setRespCode(10);
		try {
			List<FileRelationInfo> list = new ArrayList<FileRelationInfo>();
			FileRelationInfo fri = new FileRelationInfo();
			fri.setFilePath(path);
			fri.setRelationID(relationId);
			list.add(fri);
//			Log.e("relationId", JSONUtils.toJsonWithGson(relationId));
			String respMsg = JSONUtils.toJsonWithGson(list);
			hsicMessage.setRespMsg(respMsg);
			WebServiceCallHelper web = new WebServiceCallHelper(context);
			String requestData = JSONUtils.toJsonWithGson(hsicMessage);// web接口参数
			String[] webRe = { "DeviceID", "RequestData" };
			String webMethod = "UpFileReletionInfo";
			String[] value = { deviceid, requestData };
			hsicMessage = web.uploadFileReletionInfo(webRe, webMethod, value);
		} catch (Exception ex) {
			ex.printStackTrace();
			hsicMessage.setRespCode(5);
			hsicMessage.setRespMsg("调用借口异常");
		}
		return hsicMessage;
	}
	
}

