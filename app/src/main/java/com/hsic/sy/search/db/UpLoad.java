package com.hsic.sy.search.db;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.MyLog;
import com.hsic.sy.search.bean.UserXJInfo;
import com.hsic.sy.search.web.WebServiceCallHelper;

public class UpLoad {
	private SQLiteDatabase mDatabase = null;
	TB_UserRectifyInfo userRectifyInfo_TB;
	public UpLoad(Context context){
		mDatabase = SearchDataBaseHelper.getInstance(context).getReadableDatabase();
	}
	public HsicMessage UserRectifyInfo(Context context, String userid, String RectifyMan, String DeviceId){
		UserXJInfo userXJInfo =new UserXJInfo();
		HsicMessage hsicMess = new HsicMessage();
		hsicMess.setRespCode(10);
		Map<String, String> map = new HashMap<String, String>();
		String[] selectionArgs0 = { userid ,RectifyMan};
		String sql = "select InspectionStatus,InspectionDate,StopSupplyType1,StopSupplyType2,StopSupplyType3,StopSupplyType4," +
				" StopSupplyType5,StopSupplyType6,StopSupplyType7,StopSupplyType8,UnInstallType1,UnInstallType2,UnInstallType3," +
				" UnInstallType4,UnInstallType5,UnInstallType6,UnInstallType7,UnInstallType8,UnInstallType9,UnInstallType10," +
				" UnInstallType11,UnInstallType12,relationID,Last_InspectionStatus,stationid  from  T_B_UserRectifyInfo where userid=? and RectifyMan=?";
		
		try {			
			Cursor query = mDatabase.rawQuery(sql, selectionArgs0);
			if (query.moveToFirst()) {
				userXJInfo.setInspectionMan(RectifyMan);
				userXJInfo.setUserid(userid);
				userXJInfo.setInspectionStatus(query.getString(0));
				userXJInfo.setInspectionDate(query.getString(1));
				userXJInfo.setStopSupplyType1(query.getString(2));
				userXJInfo.setStopSupplyType2(query.getString(3));
				userXJInfo.setStopSupplyType3(query.getString(4));
				userXJInfo.setStopSupplyType4(query.getString(5));
				userXJInfo.setStopSupplyType5(query.getString(6));
				userXJInfo.setStopSupplyType6(query.getString(7));
				userXJInfo.setStopSupplyType7(query.getString(8));
				userXJInfo.setStopSupplyType8(query.getString(9));
				userXJInfo.setUnInstallType1(query.getString(10));
				userXJInfo.setUnInstallType2(query.getString(11));
				userXJInfo.setUnInstallType3(query.getString(12));
				userXJInfo.setUnInstallType4(query.getString(13));
				userXJInfo.setUnInstallType5(query.getString(14));
				userXJInfo.setUnInstallType6(query.getString(15));
				userXJInfo.setUnInstallType7(query.getString(16));
				userXJInfo.setUnInstallType8(query.getString(17));
				userXJInfo.setUnInstallType9(query.getString(18));
				userXJInfo.setUnInstallType10(query.getString(19));
				userXJInfo.setUnInstallType11(query.getString(20));
				userXJInfo.setUnInstallType12(query.getString(21));
				userXJInfo.setAttachID(query.getString(22));
				userXJInfo.setLast_InspectionStatus(query.getString(23));
				userXJInfo.setStationcode(query.getString(24));
				String data = "";// �ϴ����������
				data = JSONUtils.toJsonWithGson(userXJInfo);
				hsicMess.setRespMsg(data);
				String requestData = JSONUtils.toJsonWithGson(hsicMess);
				WebServiceCallHelper web = new WebServiceCallHelper(context);
				String[] selection = { "DeviceID", "RequestData" };
				String[] selectionArgs = { DeviceId, requestData };
				String methodName = "";
				methodName = "UpUserRectifyInfo";// ��������
				hsicMess = web.uploadInfo(selection, methodName, selectionArgs);
				int i = hsicMess.getRespCode();// ����ִ�н��
				if (i == 0) {
					// ���±����ֶ�
					boolean ret;
					userRectifyInfo_TB=new TB_UserRectifyInfo(context);
					ret=userRectifyInfo_TB.upIsInspected( userid, RectifyMan);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			MyLog.e("��ѯ���Ķ��������쳣", ex.toString());
		}
		return hsicMess;
	}
}
