package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QueryDB {
	
	private SQLiteDatabase mDatabase = null;
	public QueryDB(Context context) {
		mDatabase = DataBaseHelper.getInstance(context).getReadableDatabase();
	}
	public Boolean CZDWisExsit(){
		Boolean ret=false;
		try{
			String sql=" select NAME from T_B_CZDW ";
			Cursor query = mDatabase.rawQuery(sql, null);
			if (query.moveToFirst()) {
				ret=true;		
			}
		}catch(Exception ex){
			ret=false;
		}
		return ret;
	}
	public Boolean MediaisExsit(){
		Boolean ret=false;
		try{
			String sql=" select NAME from T_B_MediaInfo ";
			Cursor query = mDatabase.rawQuery(sql, null);
			if (query.moveToFirst()) {
				ret=true;		
			}
		}catch(Exception ex){
			ret=false;
		}
		return ret;
	}
	public Boolean GPLXisExsit(){
		Boolean ret=false;
		try{
			String sql=" select NAME from T_B_GPLX ";
			Cursor query = mDatabase.rawQuery(sql, null);
			if (query.moveToFirst()) {
				ret=true;		
			}
		}catch(Exception ex){
			ret=false;
		}
		return ret;
	}
	/**
	 * ��ȡ��Ȩ��λ
	 * @param No
	 * @return
	 */
	public String getCZDW(String No){
		String ret="";
		try{
			String[] selectionArgs = { No };
			String sql=" select NAME from T_B_CZDW where NO=?";
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = query.getString(0);			
			}
			else{
				ret="δ֪";
			}
		}catch(Exception ex){
			ret="";
			Log.e("getCZDW",ex.toString());
		}
		return ret;
		
	}
	public String getCZDWName(String Name){
		String ret="";
		try{
			String[] selectionArgs = { Name };
			String sql=" select NO from T_B_CZDW where NAME=?";
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = query.getString(0);			
			}
			else{
				ret="δ֪";
			}
		}catch(Exception ex){
			ret="";
			Log.e("getCZDW",ex.toString());
		}
		return ret;
		
	}
	/**
	 * ��ȡ��װ����
	 * @param No
	 * @return
	 */
	public String getMediaInfo(String No){
		String ret="";
		try{
			String[] selectionArgs = { No };
			String sql=" select NAME from T_B_MediaInfo where NO=?";
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = query.getString(0);			
			}
			else{
				ret="δ֪";
			}
		}catch(Exception ex){
			ret="";
			Log.e("getMediaInfo",ex.toString());
		}
		return ret;
	}
	/**
	 * ��ѯ��ƿ����
	 * @param No
	 * @return
	 */
	public String getGPLX(String No){
		String ret="";
		try{
			String[] selectionArgs = { No };
			String sql=" select NAME from T_B_GPLX where NO=?";
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = query.getString(0);			
			}else{
				ret="δ֪";
			}
		}catch(Exception ex){
			ret="";
			Log.e("getGPLX",ex.toString());
		}
		return ret;
	}
}

