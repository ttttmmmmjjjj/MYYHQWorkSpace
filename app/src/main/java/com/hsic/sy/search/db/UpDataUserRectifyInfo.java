package com.hsic.sy.search.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hsic.sy.search.bean.ApplicationHelper;

public class UpDataUserRectifyInfo {
	String OperationDate;
	private static final String TABLE_NAME = "T_B_UserRectifyInfo";
	private SQLiteDatabase mDatabase = null;

	public UpDataUserRectifyInfo(Context context) {
		mDatabase = SearchDataBaseHelper.getInstance(context).getReadableDatabase();
	}

	public boolean updata(ApplicationHelper applicationHelper, String userid,
						  String RectifyMan) {
		boolean ret = false;
		Date dt = new Date();
		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
		OperationDate = matter1.format(dt);
		Date date = new Date();
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String rectifyDate = pattern.format(date);
		mDatabase.beginTransaction();
		try {
			String whereClauseBys = "userid=? and RectifyMan=?";
			String[] whereArgsBys = { userid, RectifyMan };
			// user��
			String IsInspected = "";
			IsInspected = applicationHelper.getUseRectifyInfo()
					.getIsInspected();
			if (IsInspected != null && IsInspected.equals("1")) {
				ContentValues valuesByu = new ContentValues();
				valuesByu.put("RelationID", applicationHelper
						.getUseRectifyInfo().getRelationID());
				valuesByu.put("UserCardStatus", applicationHelper
						.getUseRectifyInfo().getUserCardStatus());
				valuesByu.put("InspectionStatus", applicationHelper
						.getUseRectifyInfo().getInspectionStatus());
				valuesByu
						.put("StopSupplyType1",
								applicationHelper.getUseRectifyInfo()
										.getStopSupplyType1() != null ? applicationHelper
										.getUseRectifyInfo()
										.getStopSupplyType1() : "0");
				valuesByu
						.put("StopSupplyType2",
								applicationHelper.getUseRectifyInfo()
										.getStopSupplyType2() != null ? applicationHelper
										.getUseRectifyInfo()
										.getStopSupplyType2() : "0");
				valuesByu
						.put("StopSupplyType3",
								applicationHelper.getUseRectifyInfo()
										.getStopSupplyType3() != null ? applicationHelper
										.getUseRectifyInfo()
										.getStopSupplyType3() : "0");
				valuesByu
						.put("StopSupplyType4",
								applicationHelper.getUseRectifyInfo()
										.getStopSupplyType4() != null ? applicationHelper
										.getUseRectifyInfo()
										.getStopSupplyType4() : "0");
				valuesByu
						.put("StopSupplyType5",
								applicationHelper.getUseRectifyInfo()
										.getStopSupplyType5() != null ? applicationHelper
										.getUseRectifyInfo()
										.getStopSupplyType5() : "0");
				valuesByu
						.put("StopSupplyType6",
								applicationHelper.getUseRectifyInfo()
										.getStopSupplyType6() != null ? applicationHelper
										.getUseRectifyInfo()
										.getStopSupplyType6() : "0");
				valuesByu
						.put("StopSupplyType7",
								applicationHelper.getUseRectifyInfo()
										.getStopSupplyType7() != null ? applicationHelper
										.getUseRectifyInfo()
										.getStopSupplyType7() : "0");
				valuesByu
						.put("StopSupplyType8",
								applicationHelper.getUseRectifyInfo()
										.getStopSupplyType8() != null ? applicationHelper
										.getUseRectifyInfo()
										.getStopSupplyType8() : "0");

				valuesByu
						.put("UnInstallType1",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType1() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType1() : "0");
				valuesByu
						.put("UnInstallType2",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType2() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType2() : "0");
				valuesByu
						.put("UnInstallType3",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType3() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType3() : "0");
				valuesByu
						.put("UnInstallType4",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType4() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType4() : "0");
				valuesByu
						.put("UnInstallType5",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType5() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType5() : "0");
				valuesByu
						.put("UnInstallType6",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType6() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType6() : "0");
				valuesByu
						.put("UnInstallType7",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType7() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType7() : "0");
				valuesByu
						.put("UnInstallType8",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType8() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType8() : "0");
				valuesByu
						.put("UnInstallType9",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType9() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType9() : "0");
				valuesByu
						.put("UnInstallType10",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType10() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType10() : "0");
				valuesByu
						.put("UnInstallType11",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType11() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType11() : "0");
				valuesByu
						.put("UnInstallType12",
								applicationHelper.getUseRectifyInfo()
										.getUnInstallType12() != null ? applicationHelper
										.getUseRectifyInfo()
										.getUnInstallType12() : "0");
				valuesByu.put("RectifyDate", OperationDate);
				valuesByu.put("InspectionDate", rectifyDate);
				valuesByu.put("IsInspected", "1");
				valuesByu.put("ISBACKUP", applicationHelper.getUseRectifyInfo()
						.getIsBackup());
				valuesByu.put("RectifyStatus", applicationHelper
						.getUseRectifyInfo().getRectifyStatus());
				valuesByu.put("OperationResult", applicationHelper
						.getUseRectifyInfo().getOperationResult());

				int h = mDatabase.update("T_B_UserRectifyInfo", valuesByu,
						whereClauseBys, whereArgsBys);
				mDatabase.setTransactionSuccessful();
				ret=true;
			}
		} catch (Exception ex) {
			ex.toString();
			ret=false;
		}finally{
			mDatabase.endTransaction();
		}
		return ret;
	}
}
