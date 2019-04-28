package com.hsic.sy.db;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserRegionCodeDB {
	private SQLiteDatabase mDatabase = null;
	public UserRegionCodeDB(Context context){
		mDatabase = UserRegionCodeDataBaseHelper.getInstance(context).getReadableDatabase();
	}
	public boolean isExist(String UserRegionCode,String StationCode,String Date){
		boolean ret=false;
		String[] selectionArgs = { UserRegionCode,StationCode,Date };
		String sql = "select UserRegionCode from T_B_UserRegionCode where UserRegionCode=? and StationCode=? and Date=?";

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
	/**
	 * ��������
	 */
	public void InserDB(String UserRegionCode,String StationCode,String Date,String OperationTag){
		try{
			ContentValues cValue = new ContentValues();
			cValue.put("UserRegionCode", UserRegionCode);
			cValue.put("StationCode", StationCode);
			cValue.put("Date", Date);
			cValue.put("OperationTag", OperationTag);
			long ret = mDatabase.insert("T_B_UserRegionCode", null,
					cValue);
			if (ret == -1) {
			}
		}catch(Exception ex){
			ex.toString();
			Log.e("�������ݳ����쳣", ex.toString());
		}
	}
	/**
	 * ������ˮ��
	 * @param SerialNum
	 * @param StationCode
	 * @param Date
	 * @param OperationTag
	 */
	public void InserSerialNum(String SerialNum,String StationCode,String Date,String OperationTag){
		/**
		 * ��ɾ��Ȼ���ٲ���
		 */
		try{
			ContentValues cValue = new ContentValues();
			cValue.put("SerialNum", SerialNum);
			cValue.put("StationCode", StationCode);
			cValue.put("Date", Date);
			cValue.put("OperationTag", OperationTag);
			long ret = mDatabase.insert("T_B_SerialNum", null,
					cValue);
			if (ret == -1) {
			}
		}catch(Exception ex){
			ex.toString();
			Log.e("�������ݳ����쳣", ex.toString());
		}
	}
	/**
	 * ������ˮ�ֶ�
	 * @param SerialNum
	 * @param StationCode
	 * @param Date
	 * @param OperationTag
	 */
	public void UpdateSerialNum(String SerialNum,String StationCode,String Date,String OperationTag){
		/**
		 * ��ɾ��Ȼ���ٲ���
		 */

		try{
			String whereClause_t=" StationCode=?  and Date=? and OperationTag=? ";
			String[] whereArgs_t={StationCode,Date,OperationTag};
			ContentValues cValue = new ContentValues();
			cValue.put("SerialNum", SerialNum);
			int i=mDatabase.update("T_B_SerialNum", cValue,whereClause_t, whereArgs_t);
		}catch(Exception ex){
			ex.toString();
			Log.e("������ˮ�����ݳ����쳣", ex.toString());
		}
	}
	/**
	 * ��ѯ����
	 */
	public List<String> GetUserRegionDB(String StationCode,String Date){
		List<String> data=new ArrayList<String>();
		try{
			String[] selectionArgs = { StationCode,Date };
			String sql = "select UserRegionCode,OperationTag from  T_B_UserRegionCode where StationCode=? and OperationTag='4' and Date=? ";
			try {
				Cursor query = mDatabase.rawQuery(sql, selectionArgs);
				while (query.moveToNext()) {
					String UserRegionCode=query.getString(0);
					String OperationTag=query.getString(1);
					data.add(UserRegionCode+","+OperationTag);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				Log.e("��ѯ���Ķ��������쳣", ex.toString());
			}
		}catch(Exception ex){
			ex.toString();
		}
		return data;
	}
	/**
	 * ɾ������
	 */
	public void DeleteDB(String UserRegionCode,String StationCode,String Date,String OperationTag){
		try{
			String whereClause_t="UserRegionCode=? and StationCode=?  and Date=? and OperationTag=? ";
			String[] whereArgs_t={UserRegionCode,StationCode,Date,OperationTag};
			int i=mDatabase.delete("T_B_UserRegionCode", whereClause_t, whereArgs_t);
//			MyLog.e("���ҵ���:", i+"��");
		}catch(Exception ex){
			
			ex.toString();
		}
	}
	/**
	 * ɾ����ˮ��
	 * @param
	 * @param StationCode
	 * @param Date
	 * @param OperationTag
	 */
	public void DeleteSerialNum(String StationCode,String Date,String OperationTag){
		try{
			String whereClause_t=" StationCode=?  and Date=? and OperationTag=? ";
			String[] whereArgs_t={StationCode,Date,OperationTag};
			int i=mDatabase.delete("T_B_SerialNum", whereClause_t, whereArgs_t);
//			MyLog.e("�����ˮ��:", i+"��");
		}catch(Exception ex){			
			ex.toString();
		}
	}
	/**
	 * ɾ������ǰ����ƿ����
	 * @return
	 */
	public void DeteHistoryData(String Date) {
		try {
			String sql = "select UserRegionCode,StationCode,Date,OperationTag  from T_B_UserRegionCode where date(Date)< date('now','-2 day')";
			Cursor cursor = mDatabase.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				DeleteDB(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));			
			}
		} catch (Exception ex) {
			ex.toString();
		}

	}
	/**
	 * ɾ����ˮ�ű�
	 * @param Date
	 */
	public void DeleteHistorySerialNum(String Date){
		try {
			String sql = "select StationCode,Date,OperationTag  from T_B_SerialNum where date(Date)< date('now','-2 day')";
			Cursor cursor = mDatabase.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				DeleteSerialNum(cursor.getString(0),cursor.getString(1),cursor.getString(2));
			}
		} catch (Exception ex) {
			ex.toString();
		}
	}
	/**
	 * �����ϴ��ɹ�
	 * @param OperationTag 0:�ϴ��ɹ�,1:ɨ�����ڲ���,2:�ظ�ɨ��,3:��������ȷ,4:�ϴ�ʧ��
	 * @param StationCode
	 * @param Date
	 */
	public int  GetUploadSucess(String OperationTag,String StationCode,String Date ){
		int size=0;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] whereArgs_t={OperationTag,StationCode,Date};
		String sql = "select UserRegionCode from  T_B_UserRegionCode where OperationTag=? and StationCode=?  and Date=?";
		try {
			Cursor query = mDatabase.rawQuery(sql, whereArgs_t);
			while (query.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("UserRegionCode", query.getString(0));
				list.add(map);
			}
			if(list!=null){
				size=list.size();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return size;
	}
	/***
	 * ��ѯ������ˮ��
	 */
	public String GetSerialNum(String OperationTag,String StationCode,String Date){
		String SerialNum="";
		try{
			String[] whereArgs_t={OperationTag,StationCode,Date};
			String sql = "select SerialNum from  T_B_SerialNum where OperationTag=? and StationCode=?  and Date=?" +
					" order by  SerialNum desc";					
			Cursor query = mDatabase.rawQuery(sql, whereArgs_t);
			if(query.moveToFirst()){
				SerialNum=query.getString(0);	
			}			
		}catch(Exception ex){
		}
		return SerialNum;
	}

}
