package com.hsic.sy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hsic.sy.bll.MyLog;

/**
 * �汾3
 * ��¼��ˮ��
 * @author Administrator
 *
 */
public class UserRegionCodeDataBaseHelper extends SQLiteOpenHelper{
	private static UserRegionCodeDataBaseHelper mInstance = null;
	/** ���ݿ����� **/
	public static final String DATABASE_NAME = "UserRegionCode.db";
	/** ���ݿ�汾�� **/
	private static final int DATABASE_VERSION = 7;
	/** ���ݿ�SQL��� ���һ���� **/
	private static final String T_B_UserRegionCode = "create table if not exists T_B_UserRegionCode "
			+"(SerialNum TEXT,UserRegionCode TEXT,StationCode TEXT,Date TEXT,OperationTag TEXT)";
	private static final String T_B_SerialNum = "create table if not exists T_B_SerialNum "
			+"(SerialNum TEXT,StationCode TEXT,Date TEXT,OperationTag TEXT)";
	public UserRegionCodeDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/** ����ģʽ **/
	public static synchronized UserRegionCodeDataBaseHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new UserRegionCodeDataBaseHelper(context);
		}
		return mInstance;
	}

	private void creatTables(SQLiteDatabase db) {
		/** ����������ӱ� **/	
		db.execSQL(T_B_UserRegionCode);
		db.execSQL(T_B_SerialNum);
	}

	private void deleteTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS T_B_UserRegionCode");
		db.execSQL("DROP TABLE IF EXISTS T_B_SerialNum");
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
