package com.hsic.sy.search.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hsic.sy.bll.MyLog;
import com.hsic.sy.search.bean.UserRectifyInfo;

import java.util.List;

public class InsertData {
	private SQLiteDatabase mDatabase = null;

	public InsertData(Context context) {
		mDatabase = SearchDataBaseHelper.getInstance(context).getReadableDatabase();
	}

	public void IsertIntoDB(List<UserRectifyInfo> UserRectifyInfo_LIST) {
		mDatabase.beginTransaction();
		try {
			//�Ѻ�̨Ϊ��׼
			for (int i = 0; i < UserRectifyInfo_LIST.size(); i++) {
				String uid = UserRectifyInfo_LIST.get(i).getUserid();
				String RectifyMan = UserRectifyInfo_LIST.get(i).getRectifyMan();
				if (!isExist(uid, RectifyMan)) {
					//���ز�����
					ContentValues cValue = new ContentValues();
					cValue.put("deliveraddress", UserRectifyInfo_LIST.get(i)
							.getDeliveraddress());
					cValue.put("ex_userid", UserRectifyInfo_LIST.get(i)
							.getEx_userid());
					cValue.put("userstat", UserRectifyInfo_LIST.get(i)
							.getUserstat());
					cValue.put("userid", UserRectifyInfo_LIST.get(i)
							.getUserid());
					cValue.put("RectifyStatus", UserRectifyInfo_LIST.get(i)
							.getRectifyStatus());
					cValue.put("RectifyMan", UserRectifyInfo_LIST.get(i)
							.getRectifyMan());
					cValue.put("userCardStatus", UserRectifyInfo_LIST.get(i)
							.getUserCardStatus());
					cValue.put("userCardID", UserRectifyInfo_LIST.get(i)
							.getUserCardID());
					cValue.put("username", UserRectifyInfo_LIST.get(i)
							.getUsername());
					cValue.put("telephone", UserRectifyInfo_LIST.get(i)
							.getTelephone());
					cValue.put("handphone", UserRectifyInfo_LIST.get(i)
							.getHandphone());
					cValue.put("handphone2", UserRectifyInfo_LIST.get(i)
							.getHandphone2());
					cValue.put("handphone3", UserRectifyInfo_LIST.get(i)
							.getHandphone3());
					cValue.put("usertype", UserRectifyInfo_LIST.get(i)
							.getUsertype());
					cValue.put("custom_type", UserRectifyInfo_LIST.get(i)
							.getCustom_type());
					cValue.put("extendValue1", UserRectifyInfo_LIST.get(i)
							.getExtendValue1());
					cValue.put("insurance", UserRectifyInfo_LIST.get(i)
							.getInsurance());
					cValue.put("fireno2", UserRectifyInfo_LIST.get(i)
							.getFireno2());
					cValue.put("InspectionStatus", UserRectifyInfo_LIST.get(i)
							.getInspectionStatus());
					cValue.put("Last_InspectionStatus", UserRectifyInfo_LIST.get(i)
							.getInspectionStatus());
					cValue.put("InspectionDate", UserRectifyInfo_LIST.get(i)
							.getInspectionDate());
					cValue.put("IsBackup", UserRectifyInfo_LIST.get(i)
							.getIsBackup());
					cValue.put("id", UserRectifyInfo_LIST.get(i).getId());
					cValue.put("empname", UserRectifyInfo_LIST.get(i)
							.getEmpname());
					cValue.put("StopSupplyType1", UserRectifyInfo_LIST.get(i)
							.getStopSupplyType1());
					cValue.put("StopSupplyType2", UserRectifyInfo_LIST.get(i)
							.getStopSupplyType2());
					cValue.put("StopSupplyType3", UserRectifyInfo_LIST.get(i)
							.getStopSupplyType3());
					cValue.put("StopSupplyType4", UserRectifyInfo_LIST.get(i)
							.getStopSupplyType4());
					cValue.put("StopSupplyType5", UserRectifyInfo_LIST.get(i)
							.getStopSupplyType5());
					cValue.put("StopSupplyType6", UserRectifyInfo_LIST.get(i)
							.getStopSupplyType6());
					cValue.put("StopSupplyType7", UserRectifyInfo_LIST.get(i)
							.getStopSupplyType7());
					cValue.put("StopSupplyType8", UserRectifyInfo_LIST.get(i)
							.getStopSupplyType8());
					cValue.put("UnInstallType1", UserRectifyInfo_LIST.get(i)
							.getUnInstallType1());
					cValue.put("UnInstallType2", UserRectifyInfo_LIST.get(i)
							.getUnInstallType2());
					cValue.put("UnInstallType3", UserRectifyInfo_LIST.get(i)
							.getUnInstallType3());
					cValue.put("UnInstallType4", UserRectifyInfo_LIST.get(i)
							.getUnInstallType4());
					cValue.put("UnInstallType5", UserRectifyInfo_LIST.get(i)
							.getUnInstallType5());
					cValue.put("UnInstallType6", UserRectifyInfo_LIST.get(i)
							.getUnInstallType6());
					cValue.put("UnInstallType7", UserRectifyInfo_LIST.get(i)
							.getUnInstallType7());
					cValue.put("UnInstallType8", UserRectifyInfo_LIST.get(i)
							.getUnInstallType8());
					cValue.put("UnInstallType9", UserRectifyInfo_LIST.get(i)
							.getUnInstallType9());
					cValue.put("UnInstallType10", UserRectifyInfo_LIST.get(i)
							.getUnInstallType10());
					cValue.put("UnInstallType11", UserRectifyInfo_LIST.get(i)
							.getUnInstallType11());
					cValue.put("UnInstallType12", UserRectifyInfo_LIST.get(i)
							.getUnInstallType12());
					cValue.put("IsInspected", "0");
					cValue.put("stationid", UserRectifyInfo_LIST.get(i).getStationid());
					long ret = mDatabase.insert("T_B_UserRectifyInfo", null,
							cValue);
					if (ret == -1) {
						MyLog.e("T_B_UserRectifyInfo����ʧ�ܣ�saleid", "");
					}
				} else {
					//���ش���
					if (!isInspect(uid, RectifyMan)) {
						//�����
						//�Ƿ����ϴ�
						if(isUpLoad(uid, RectifyMan)){
							ContentValues cValue = new ContentValues();
							cValue.put("deliveraddress", UserRectifyInfo_LIST.get(i)
									.getDeliveraddress());
							cValue.put("ex_userid", UserRectifyInfo_LIST.get(i)
									.getEx_userid());
							cValue.put("userstat", UserRectifyInfo_LIST.get(i)
									.getUserstat());
							cValue.put("userid", UserRectifyInfo_LIST.get(i)
									.getUserid());
							cValue.put("RectifyStatus", UserRectifyInfo_LIST.get(i)
									.getRectifyStatus());
							cValue.put("RectifyMan", UserRectifyInfo_LIST.get(i)
									.getRectifyMan());
							cValue.put("userCardStatus", UserRectifyInfo_LIST.get(i)
									.getUserCardStatus());
							cValue.put("userCardID", UserRectifyInfo_LIST.get(i)
									.getUserCardID());
							cValue.put("username", UserRectifyInfo_LIST.get(i)
									.getUsername());
							cValue.put("telephone", UserRectifyInfo_LIST.get(i)
									.getTelephone());
							cValue.put("handphone", UserRectifyInfo_LIST.get(i)
									.getHandphone());
							cValue.put("handphone2", UserRectifyInfo_LIST.get(i)
									.getHandphone2());
							cValue.put("handphone3", UserRectifyInfo_LIST.get(i)
									.getHandphone3());
							cValue.put("usertype", UserRectifyInfo_LIST.get(i)
									.getUsertype());
							cValue.put("custom_type", UserRectifyInfo_LIST.get(i)
									.getCustom_type());
							cValue.put("extendValue1", UserRectifyInfo_LIST.get(i)
									.getExtendValue1());
							cValue.put("insurance", UserRectifyInfo_LIST.get(i)
									.getInsurance());
							cValue.put("fireno2", UserRectifyInfo_LIST.get(i)
									.getFireno2());
							cValue.put("InspectionStatus", UserRectifyInfo_LIST.get(i)
									.getInspectionStatus());
							cValue.put("Last_InspectionStatus", UserRectifyInfo_LIST.get(i)
									.getInspectionStatus());
							cValue.put("InspectionDate", UserRectifyInfo_LIST.get(i)
									.getInspectionDate());
							cValue.put("IsBackup", UserRectifyInfo_LIST.get(i)
									.getIsBackup());
							cValue.put("id", UserRectifyInfo_LIST.get(i).getId());
							cValue.put("empname", UserRectifyInfo_LIST.get(i)
									.getEmpname());
							cValue.put("StopSupplyType1", UserRectifyInfo_LIST.get(i)
									.getStopSupplyType1());
							cValue.put("StopSupplyType2", UserRectifyInfo_LIST.get(i)
									.getStopSupplyType2());
							cValue.put("StopSupplyType3", UserRectifyInfo_LIST.get(i)
									.getStopSupplyType3());
							cValue.put("StopSupplyType4", UserRectifyInfo_LIST.get(i)
									.getStopSupplyType4());
							cValue.put("StopSupplyType5", UserRectifyInfo_LIST.get(i)
									.getStopSupplyType5());
							cValue.put("StopSupplyType6", UserRectifyInfo_LIST.get(i)
									.getStopSupplyType6());
							cValue.put("StopSupplyType7", UserRectifyInfo_LIST.get(i)
									.getStopSupplyType7());
							cValue.put("StopSupplyType8", UserRectifyInfo_LIST.get(i)
									.getStopSupplyType8());
							cValue.put("UnInstallType1", UserRectifyInfo_LIST.get(i)
									.getUnInstallType1());
							cValue.put("UnInstallType2", UserRectifyInfo_LIST.get(i)
									.getUnInstallType2());
							cValue.put("UnInstallType3", UserRectifyInfo_LIST.get(i)
									.getUnInstallType3());
							cValue.put("UnInstallType4", UserRectifyInfo_LIST.get(i)
									.getUnInstallType4());
							cValue.put("UnInstallType5", UserRectifyInfo_LIST.get(i)
									.getUnInstallType5());
							cValue.put("UnInstallType6", UserRectifyInfo_LIST.get(i)
									.getUnInstallType6());
							cValue.put("UnInstallType7", UserRectifyInfo_LIST.get(i)
									.getUnInstallType7());
							cValue.put("UnInstallType8", UserRectifyInfo_LIST.get(i)
									.getUnInstallType8());
							cValue.put("UnInstallType9", UserRectifyInfo_LIST.get(i)
									.getUnInstallType9());
							cValue.put("UnInstallType10", UserRectifyInfo_LIST.get(i)
									.getUnInstallType10());
							cValue.put("UnInstallType11", UserRectifyInfo_LIST.get(i)
									.getUnInstallType11());
							cValue.put("UnInstallType12", UserRectifyInfo_LIST.get(i)
									.getUnInstallType12());
							cValue.put("IsInspected", "0");
							cValue.put("stationid", UserRectifyInfo_LIST.get(i).getStationid());
							long ret = mDatabase.insert("T_B_UserRectifyInfo", null,
									cValue);
							if (ret == -1) {
								MyLog.e("T_B_UserRectifyInfo����ʧ�ܣ�saleid", "");
							}
						}
						
					}else{
						//��δ��ɣ�����
						String userid=UserRectifyInfo_LIST.get(i)
								.getUserid();
						String whereClauseBys = "userid=? and RectifyMan=?";
						String[] whereArgsBys = { userid, RectifyMan };
						ContentValues cValue = new ContentValues();
						cValue.put("StopSupplyType1",
								UserRectifyInfo_LIST.get(i)
										.getStopSupplyType1());
						cValue.put("StopSupplyType2",
								UserRectifyInfo_LIST.get(i)
										.getStopSupplyType2());
						cValue.put("StopSupplyType3",
								UserRectifyInfo_LIST.get(i)
										.getStopSupplyType3());
						cValue.put("StopSupplyType4",
								UserRectifyInfo_LIST.get(i)
										.getStopSupplyType4());
						cValue.put("StopSupplyType5",
								UserRectifyInfo_LIST.get(i)
										.getStopSupplyType5());
						cValue.put("StopSupplyType6",
								UserRectifyInfo_LIST.get(i)
										.getStopSupplyType6());
						cValue.put("StopSupplyType7",
								UserRectifyInfo_LIST.get(i)
										.getStopSupplyType7());
						cValue.put("StopSupplyType8",
								UserRectifyInfo_LIST.get(i)
										.getStopSupplyType8());
						cValue.put("UnInstallType1", UserRectifyInfo_LIST
								.get(i).getUnInstallType1());
						cValue.put("UnInstallType2", UserRectifyInfo_LIST
								.get(i).getUnInstallType2());
						cValue.put("UnInstallType3", UserRectifyInfo_LIST
								.get(i).getUnInstallType3());
						cValue.put("UnInstallType4", UserRectifyInfo_LIST
								.get(i).getUnInstallType4());
						cValue.put("UnInstallType5", UserRectifyInfo_LIST
								.get(i).getUnInstallType5());
						cValue.put("UnInstallType6", UserRectifyInfo_LIST
								.get(i).getUnInstallType6());
						cValue.put("UnInstallType7", UserRectifyInfo_LIST
								.get(i).getUnInstallType7());
						cValue.put("UnInstallType8", UserRectifyInfo_LIST
								.get(i).getUnInstallType8());
						cValue.put("UnInstallType9", UserRectifyInfo_LIST
								.get(i).getUnInstallType9());
						cValue.put("UnInstallType10",
								UserRectifyInfo_LIST.get(i)
										.getUnInstallType10());
						cValue.put("UnInstallType11",
								UserRectifyInfo_LIST.get(i)
										.getUnInstallType11());
						cValue.put("UnInstallType12",
								UserRectifyInfo_LIST.get(i)
										.getUnInstallType12());
						int h = mDatabase.update("T_B_UserRectifyInfo", cValue,
								whereClauseBys, whereArgsBys);
					}
				}
			}
			mDatabase.setTransactionSuccessful();

		} catch (Exception ex) {
			MyLog.e("���Ĳ������ݳ����쳣", ex.toString());
		} finally {
			mDatabase.endTransaction();

		}
	}

	public boolean isExist(String userid, String RectifyMan) {
		boolean ret = false;
		String[] selectionArgs = { userid, RectifyMan };
		String sql = "select userid from T_B_UserRectifyInfo where userid=? and RectifyMan=?";

		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ret = false;
		}

		return ret;
	}

	public boolean isInspect(String userid, String RectifyMan) {
		boolean ret = false;
		String[] selectionArgs = { userid, RectifyMan };
		String sql = "select userid from T_B_UserRectifyInfo where userid=? and RectifyMan=? and IsInspected=0 ";

		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ret = false;
		}
		return ret;
	}	
	public boolean isUpLoad(String userid, String RectifyMan) {
		boolean ret = false;
		String[] selectionArgs = { userid, RectifyMan };
		String sql = "select userid from T_B_UserRectifyInfo where userid=? and RectifyMan=? and IsInspected=2 ";

		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ret = false;
		}
		return ret;
	}
}
