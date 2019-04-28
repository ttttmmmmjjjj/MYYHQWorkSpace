package com.hsic.sy.search.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hsic.sy.bll.MyLog;

public class SearchDataBaseHelper extends SQLiteOpenHelper{
	private static SearchDataBaseHelper mInstance = null;
	/** ���ݿ����� **/
	public static final String DATABASE_NAME = "SYQPRectify.db";
	/** ���ݿ�汾�� **/
	private static final int DATABASE_VERSION = 5;

	/** ���ݿ�SQL��� ���һ���� **/
	private static final String T_B_USERJINFO_CREATE = "create table if not exists T_B_UserRectifyInfo"
			+"(deliveraddress TEXT,userstat TEXT,ex_userid TEXT,userid TEXT,RectifyStatus TEXT,RectifyMan TEXT," 
			+" userCardStatus TEXT,userCardID TEXT,username TEXT,telephone TEXT,handphone TEXT,handphone2 TEXT,"
			+" handphone3 TEXT,usertype TEXT,custom_type TEXT,extendValue1 TEXT,insurance TEXT,fireno2 TEXT," 
			+" InspectionStatus TEXT,Last_InspectionStatus TEXT,InspectionDate TEXT,IsBackup TEXT,id TEXT,empname TEXT,"
			+ " StopSupplyType1 TEXT,StopSupplyType2 TEXT,StopSupplyType3 TEXT,StopSupplyType4 TEXT,"
			+ " StopSupplyType5 TEXT,StopSupplyType6 TEXT,StopSupplyType7 TEXT,StopSupplyType8 TEXT,"
			+ " UnInstallType1 TEXT,UnInstallType2 TEXT,UnInstallType3 TEXT,UnInstallType4 TEXT,"
			+ " UnInstallType5 TEXT,UnInstallType6 TEXT,UnInstallType7 TEXT,UnInstallType8 TEXT,"
			+ " UnInstallType9 TEXT,UnInstallType10 TEXT,UnInstallType11 TEXT,UnInstallType12 TEXT,"
			+ " relationID TEXT,IsInspected TEXT,stationid  TEXT,RectifyDate TEXT,OperationResult TEXT)";
	private static final String T_B_GPSINFO_TABLE = "create table if not exists T_B_gpsInfo"
			+ "(GPS_Date TEXT,GPS_J TEXT,GPS_W TEXT)";
	private static final String T_B_DEVICEINFO_TABLE = "create table if not exists T_B_deviceInfo"
			+ "(deviceid TEXT,macaddress TEXT,telephone TEXT,blueMac TEXT,insertDate TEXT)";

	public SearchDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/** ����ģʽ **/
	public static synchronized SearchDataBaseHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SearchDataBaseHelper(context);
		}
		return mInstance;
	}

	private void creatTables(SQLiteDatabase db) {
		/** ����������ӱ� **/	
		db.execSQL(T_B_USERJINFO_CREATE);
		db.execSQL(T_B_GPSINFO_TABLE);
		db.execSQL(T_B_DEVICEINFO_TABLE);
	}

	private void deleteTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS T_B_UserRectifyInfo");
		db.execSQL("DROP TABLE IF EXISTS T_B_gpsInfo");
		db.execSQL("DROP TABLE IF EXISTS T_B_deviceInfo");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		creatTables(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		/** �����õ���ǰ���ݿ�İ汾��Ϣ ��֮ǰ���ݿ�İ汾��Ϣ �����������ݿ� **/
		MyLog.i("old=" + oldVersion, "new=" + newVersion);
		if (newVersion > oldVersion) {
			deleteTables(db);
			MyLog.i("old1=" + oldVersion, "new1=" + newVersion);
			creatTables(db);
		}
	}

	/**
	 * ɾ�����ݿ�
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(DATABASE_NAME);
	}

}
