package com.hsic.sy.search.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TB_UserRectifyInfo {
	private static final String TABLE_NAME = "T_B_UserRectifyInfo";
	private SQLiteDatabase mDatabase = null;

	public TB_UserRectifyInfo(Context context) {
		mDatabase = SearchDataBaseHelper.getInstance(context).getReadableDatabase();
	}
	public boolean idExist(String userid,String RectifyMan){
		boolean ret=false ;
		String[] selectionArgs = { userid,RectifyMan };
		String sql = "select userid from T_B_UserRectifyInfo where userid=? and RectifyMan=?";
		
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = true;			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ret=false;
		}

		return ret;
	}
	/**
	 * ��ȡ��Ѳ����Ա����δ��ɵĶ���
	 * @param RectifyMan
	 * @return
	 */
	public String getUserID(String RectifyMan){
		String ret="";
		String[] selectionArgs={RectifyMan};
		String sql=" select userid from T_B_UserRectifyInfo where RectifyMan=? and IsInspected='0'";
		try{
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret =query.getString(0);			
			}
		}catch(Exception ex){
			ex.toString();
			ret="";
		}
		return ret;
	}
	/**
	 * ɾ��ָ������
	 * @param RectifyMan
	 * @param userid
	 */
	public void deleteUser(String RectifyMan,String userid){
		String[] selectionArgs={RectifyMan,userid};
		String sql="delete T_B_UserRectifyInfo where RectifyMan=? and userid=?";
		try{
			 mDatabase.delete("T_B_UserRectifyInfo", "RectifyMan=? and userid=?", selectionArgs);
		}catch(Exception ex){
			ex.toString();
		}
	}
	/**
	 * ��ȡ���ݿ�洢�ĸÿ���TagID
	 * @param userid
	 * @param RectifyMan
	 * @return
	 */
	public String getTagID(String userid,String RectifyMan){
		String ret="";
		String[] selectionArgs = { userid,RectifyMan };
		String sql = "select userCardID from T_B_UserRectifyInfo where userid=? and RectifyMan=? and IsInspected='0'";
		
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret =query.getString(0);			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ret="";
		}
		return ret;
	}
	/***
	 * ��ȡ����id
	 * @param userid
	 * @param RectifyMan
	 * @return
	 */
	public String getID(String userid,String RectifyMan){
		String ret="";
		String[] selectionArgs = { userid,RectifyMan };
		String sql = "select id from T_B_UserRectifyInfo where userid=? and RectifyMan=? and IsInspected='0'";
		
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret =query.getString(0);			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ret="";
		}
		return ret;
	}
	/**
	 * ��ȡ�ÿ��Ŀ�״̬
	 * @param userid
	 * @param RectifyMan
	 * @return
	 */
	public String getCardStatus(String userid,String RectifyMan){
		String ret="";
		String[] selectionArgs = { userid,RectifyMan };
		String sql = "select userCardStatus from T_B_UserRectifyInfo where userid=? and RectifyMan=?";
		
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret =query.getString(0);			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ret="";
		}
		return ret;
	}
	/**
	 * ��ȡ����Ա
	 * @return
	 */
	public String getRectifyMan(String RectifyMan){
		String ret="";
		String[] selectionArgs = { RectifyMan };
		String sql = "select empname from T_B_UserRectifyInfo where RectifyMan=?";
		
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				ret = query.getString(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
		
	}
	/**
	 * ������������
	 * @param RectifyMan
	 * @return
	 */
	public List<Map<String, String>> GetRectifyTaskCounts(String RectifyMan) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] selectionArgs = { RectifyMan };
		String sql = "select userid from  T_B_UserRectifyInfo where RectifyMan=? and (IsInspected='0' or IsInspected='1' or IsInspected='2')";
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			while (query.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("userid", query.getString(0));
				list.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	/**
	 * ��ȡ���Ļ�����Ϣ
	 * @param RectifyMan
	 * @return
	 */
	public 	List<Map<String, String>> GetRectifyBaseInfo(String RectifyMan){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] selectionArgs = { RectifyMan };
		String sql = "select userid,username,deliveraddress,telephone,handphone,handphone3 from  T_B_UserRectifyInfo where RectifyMan=? and IsInspected='0'";
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			while (query.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("userid", query.getString(0));
				map.put("username", query.getString(1));
				map.put("deliveraddress", query.getString(2));
				map.put("telephone", query.getString(3));
				map.put("handphone", query.getString(4));
				map.put("handphone3", query.getString(5));			
				list.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	/**
	 * ��ȡ����������Ϣ
	 * @param userid
	 * @param RectifyMan
	 * @return
	 */
	public Map<String, String> GetRectifyInfo(String userid,String RectifyMan){
		Map<String, String> map = new HashMap<String, String>();
		String[] selectionArgs = { userid ,RectifyMan};
		String sql = "select InspectionStatus,InspectionDate,StopSupplyType1,StopSupplyType2,StopSupplyType3,StopSupplyType4," +
				" StopSupplyType5,StopSupplyType6,StopSupplyType7,StopSupplyType8,UnInstallType1,UnInstallType2,UnInstallType3," +
				" UnInstallType4,UnInstallType5,UnInstallType6,UnInstallType7,UnInstallType8,UnInstallType9,UnInstallType10," +
				" UnInstallType11,UnInstallType12,empname,username,deliveraddress from  T_B_UserRectifyInfo where userid=? and RectifyMan=?";
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				map.put("InspectionStatus", query.getString(0));
				map.put("InspectionDate", query.getString(1));
				map.put("StopSupplyType1", query.getString(2));
				map.put("StopSupplyType2", query.getString(3));
				map.put("StopSupplyType3", query.getString(4));
				map.put("StopSupplyType4", query.getString(5));
				map.put("StopSupplyType5", query.getString(6));
				map.put("StopSupplyType6", query.getString(7));
				map.put("StopSupplyType7", query.getString(8));
				map.put("StopSupplyType8", query.getString(9));
				map.put("UnInstallType1", query.getString(10));
				map.put("UnInstallType2", query.getString(11));
				map.put("UnInstallType3", query.getString(12));
				map.put("UnInstallType4", query.getString(13));
				map.put("UnInstallType5", query.getString(14));
				map.put("UnInstallType6", query.getString(15));
				map.put("UnInstallType7", query.getString(16));
				map.put("UnInstallType8", query.getString(17));
				map.put("UnInstallType9", query.getString(18));
				map.put("UnInstallType10", query.getString(19));
				map.put("UnInstallType11", query.getString(20));
				map.put("UnInstallType12", query.getString(21));	
				map.put("empname", query.getString(22));	
				map.put("username", query.getString(23));	
				map.put("deliveraddress", query.getString(24));		
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	/**
	 * ��ȡ�����ϴ�������Ϣ
	 * @param userid
	 * @param RectifyMan
	 * @return
	 */
	public Map<String, String> GetLastSearch(String userid,String RectifyMan){
		Map<String, String> map = new HashMap<String, String>();
		String[] selectionArgs = { userid ,RectifyMan};
		String sql = "select InspectionStatus,InspectionDate,StopSupplyType1,StopSupplyType2,StopSupplyType3,StopSupplyType4," +
				" StopSupplyType5,StopSupplyType6,StopSupplyType7,StopSupplyType8,UnInstallType1,UnInstallType2,UnInstallType3," +
				" UnInstallType4,UnInstallType5,UnInstallType6,UnInstallType7,UnInstallType8,UnInstallType9,UnInstallType10," +
				" UnInstallType11,UnInstallType12,relationID from  T_B_UserRectifyInfo where userid=? and RectifyMan=? and IsInspected=0";
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			if (query.moveToFirst()) {
				map.put("InspectionStatus", query.getString(0));
				map.put("InspectionDate", query.getString(1));
				map.put("StopSupplyType1", query.getString(2));
				map.put("StopSupplyType2", query.getString(3));
				map.put("StopSupplyType3", query.getString(4));
				map.put("StopSupplyType4", query.getString(5));
				map.put("StopSupplyType5", query.getString(6));
				map.put("StopSupplyType6", query.getString(7));
				map.put("StopSupplyType7", query.getString(8));
				map.put("StopSupplyType8", query.getString(9));
				map.put("UnInstallType1", query.getString(10));
				map.put("UnInstallType2", query.getString(11));
				map.put("UnInstallType3", query.getString(12));
				map.put("UnInstallType4", query.getString(13));
				map.put("UnInstallType5", query.getString(14));
				map.put("UnInstallType6", query.getString(15));
				map.put("UnInstallType7", query.getString(16));
				map.put("UnInstallType8", query.getString(17));
				map.put("UnInstallType9", query.getString(18));
				map.put("UnInstallType10", query.getString(19));
				map.put("UnInstallType11", query.getString(20));
				map.put("UnInstallType12", query.getString(21));			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	/**
	 * 
	 * ������
	 * @param RectifyMan
	 * @return
	 */
	public List<Map<String, String>> GetRectifyFinishCounts(String RectifyMan) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] selectionArgs = { RectifyMan };
		String sql = "select userid from  T_B_UserRectifyInfo where RectifyMan=? and (IsInspected=1 or IsInspected=2)";
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			while (query.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("userid", query.getString(0));
				list.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	/**
	 * 
	 * ���ϴ�
	 * @param RectifyMan
	 * @return
	 */
	public List<Map<String, String>> GetRectifyUploadCounts(String RectifyMan) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] selectionArgs = { RectifyMan };
		String sql = "select userid from  T_B_UserRectifyInfo where RectifyMan=? and IsInspected=2";
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			while (query.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("userid", query.getString(0));
				list.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	/**
	 * ���±����ֶ�Ϊ���ϴ�
	 * @param
	 * @param userid
	 * @param RectifyMan
	 * @return
	 */
	public boolean upIsInspected( String userid,
			String RectifyMan) {
		boolean ret=false;
		try{
			String whereClauseBys = "userid=? and RectifyMan=?";
			String[] whereArgsBys = { userid, RectifyMan };
			ContentValues valuesByu = new ContentValues();
			valuesByu.put("IsInspected", "2");
			int h = mDatabase.update("T_B_UserRectifyInfo", valuesByu,
					whereClauseBys, whereArgsBys);
			ret=true;
		}catch(Exception ex){
			ex.printStackTrace();
			ex.toString();
		}
		return ret;
	}
	public boolean IsInspected( String userid,
			String RectifyMan) {
		boolean ret = false;
		String[] selectionArgs = { userid, RectifyMan };
		String sql = "select userid from T_B_UserRectifyInfo where userid=? and RectifyMan=? and IsInspected=1 ";

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
	public boolean IsUpload( String userid,
			String RectifyMan) {
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
	/**
	 * ɾ������ǰ��Ѳ�쵥
	 * @return
	 */
	public List<String> FinishDate() {
		List<String> RectifyMan = new ArrayList<String>();
		try {
			String sql = "select RectifyMan from T_B_UserRectifyInfo where date(RectifyDate)< date('now','-2 day')";
			Cursor cursor = mDatabase.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				RectifyMan.add(cursor.getString(0));
			}
		} catch (Exception ex) {
			ex.toString();
		}
		return RectifyMan;
	}
	/**
	 * ��ѯ����ɵ��û�
	 * @param RectifyMan
	 * @return
	 */
	public List<String> finishedUser(String RectifyMan){
		List<String> userid = new ArrayList<String>();
		String[] selectionArgs = {  RectifyMan };
		String sql = "select userid from T_B_UserRectifyInfo where RectifyMan=? and IsInspected=1 ";
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			while (query.moveToNext()) {
				userid.add(query.getString(0));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return userid;
	}
	public List<Map<String, String>> getFinished(String RectifyMan) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] selectionArgs = { RectifyMan };
		String sql="select empname,userid,InspectionDate,InspectionStatus from T_B_UserRectifyInfo where RectifyMan=? and (IsInspected=1 or IsInspected=2) ";
		int i = 1;
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			while (query.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("RectifyMan", query.getString(0));
				map.put("userid", query.getString(1));
				map.put("InspectionDate", query.getString(2));
				
				if(query.getString(3).equals("0")){
					map.put("InspectionStatus", "����ͨ��");
				}else{
					map.put("InspectionStatus", "����δͨ��");
				}
				map.put("RowID", String.valueOf(i));
				list.add(map);
				i++;
			}
			}catch(Exception ex){
			}
		return list;
	}
	public List<Map<String, String>> test(String RectifyMan) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] selectionArgs = { RectifyMan };
		String sql="select empname,userid,InspectionDate,InspectionStatus from T_B_UserRectifyInfo where RectifyMan=?  ";
		int i = 1;
		try {
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			while (query.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("RectifyMan", query.getString(0));
				map.put("userid", query.getString(1));
				map.put("InspectionDate", query.getString(2));
				map.put("InspectionStatus", query.getString(3));
				map.put("RowID", String.valueOf(i));
				list.add(map);
				i++;
			}
			}catch(Exception ex){
			}
		return list;
	}
	public List<String> getUserAllID(String RectifyMan){
		List<String> list=new ArrayList<String>();
		String[] selectionArgs = {RectifyMan };
		String sql = "select userid from T_B_UserRectifyInfo where RectifyMan=? ";
		try{
			Cursor query = mDatabase.rawQuery(sql, selectionArgs);
			while (query.moveToNext()) {
				list.add(query.getString(0));
			}
		}catch(Exception ex){
			
		}
		return list;
	}
}
