package com.hsic.sy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SupplyDataBaseHelper extends SQLiteOpenHelper{
	
	private static SupplyDataBaseHelper mInstance = null;
	/** ���ݿ����� **/
	public static final String DATABASE_NAME = "SupplyStationManager.db";
	/** ���ݿ�汾�� **/
	private static final int DATABASE_VERSION = 1;
	private static final String T_B_FULLINPUT_CREATE = "create table if not exists T_B_FullInput "
			+ "(POSID TEXT, DATE TEXT,SERIALNUM TEXT,LOGOUTTAG TEXT,UID TEXT ,TIME TEXT,YFCODE TEXT,BOTTLEID TEXT," +
			" DISPATCHDATE TEXT,QPTYPE TEXT,STUFFID TEXT)";
	private static final String T_B_FULLOUTPUT_CREATE = "create table if not exists T_B_FullOutput "
			+ "(POSID TEXT, DATE TEXT,SERIALNUM TEXT,LOGOUTTAG TEXT,UID TEXT ,TIME TEXT,YFCODE TEXT,BOTTLEID TEXT," +
			" DISPATCHDATE TEXT,QPTYPE TEXT,STUFFID TEXT)";
	private static final String T_B_EMPTYINPUT_CREATE = "create table if not exists T_B_EmptyInput "
			+ "(POSID TEXT, DATE TEXT,SERIALNUM TEXT,LOGOUTTAG TEXT,UID TEXT ,TIME TEXT,YFCODE TEXT,BOTTLEID TEXT," +
			" DISPATCHDATE TEXT,QPTYPE TEXT,STUFFID TEXT)";
	private static final String T_B_EMPTYOUTPUT_CREATE = "create table if not exists T_B_EmptyOutput "
			+ "(POSID TEXT, DATE TEXT,SERIALNUM TEXT,LOGOUTTAG TEXT,UID TEXT ,TIME TEXT,YFCODE TEXT,BOTTLEID TEXT," +
			" DISPATCHDATE TEXT,QPTYPE TEXT,STUFFID TEXT)";
	public SupplyDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	/** ����ģʽ **/
	public static synchronized SupplyDataBaseHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SupplyDataBaseHelper(context);
		}
		return mInstance;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 creatTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (newVersion > oldVersion) {
			deleteTables(db);			
			creatTables(db);
		}
	}
	private void creatTables(SQLiteDatabase db) {
		db.execSQL(T_B_FULLINPUT_CREATE);
		db.execSQL(T_B_FULLOUTPUT_CREATE);
		db.execSQL(T_B_EMPTYINPUT_CREATE);
		db.execSQL(T_B_EMPTYOUTPUT_CREATE);
	}
	private void deleteTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS T_B_FullInput");
		db.execSQL("DROP TABLE IF EXISTS T_B_FullOutput");
		db.execSQL("DROP TABLE IF EXISTS T_B_EmptyInput");
		db.execSQL("DROP TABLE IF EXISTS T_B_EmptyOutput");
	}
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(DATABASE_NAME);
	}
}
