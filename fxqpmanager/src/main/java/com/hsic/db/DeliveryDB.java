package com.hsic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.hsic.bean.FileRelationInfo;
import com.hsic.bean.HsicMessage;
import com.hsic.bean.Sale;
import com.hsic.bean.SaleAll;
import com.hsic.bean.SaleDetail;
import com.hsic.bean.ScanHistory;
import com.hsic.bean.UserXJInfo;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.tmj.wheelview.QPType;
import com.hsic.utils.TimeUtils;
import com.hsic.web.WebServiceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tmj on 2019/2/26.
 */

public class DeliveryDB {
    private StringBuffer sql_insert;
    private SQLiteDatabase mDatabase = null;
    public DeliveryDB(Context context){
        mDatabase=DataBaseHelper.getInstance(context).getReadableDatabase();
    }

    /**
     * 将交易信息及用户相关信息插入到本地数据库
     */
    public void InsertInfo(){
    }
    /**
     * 基础配置信息 包括 街道信息
     * 插入街道信息
     */
    public boolean InsertConfigInfo(){
        boolean ret=false;
        try{
            mDatabase.beginTransaction();
        }catch(Exception ex){

        }finally {
            mDatabase.endTransaction();
        }
        return ret;
    }

    /***
     * 插入巡检配置信息
     */
    public void InsertSearchInfo(){

    }

    /**
     * 插入销售相关信息
     */
    public void InsertSaleInfo(List<SaleAll> saleAllList,String EmployeeID,String StationID){
        long start=System.currentTimeMillis();
        try{
            List<Sale> sales=new ArrayList<Sale>();
            List<UserXJInfo> userXJInfos=new ArrayList<UserXJInfo>();
            List<SaleDetail> saleDetails=new ArrayList<SaleDetail>();
            sql_insert = new StringBuffer();
            sql_insert.append("INSERT INTO T_B_SALE(SaleID,UserID,StationID,StationName,Match,IState," +
                    "Remark,Address,AssignTime,EmployeeID,AllPrice,InsertTime,Telephone,RealPriceInfo)");
            sql_insert.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)");
            int size=saleAllList.size();
            StringBuffer saleID_Web=new StringBuffer();
            for(int i=0;i<size;i++){
                String SaleID=saleAllList.get(i).getSale().getSaleID();
                saleID_Web.append(SaleID+",");
                if(!isExist(EmployeeID,StationID,SaleID)){
                    sales.add(saleAllList.get(i).getSale());
                    userXJInfos.add(saleAllList.get(i).getUserXJInfo());
                    List<SaleDetail> tmp=new ArrayList<SaleDetail>();
                    tmp=saleAllList.get(i).getSale().getSale_detail_info();
                    int s=tmp.size();
                    for(int j=0;j<s;j++){
                        saleDetails.add(tmp.get(j));
                    }
                }
            }
            for(Sale sale:sales){
                SQLiteStatement statement=    mDatabase.compileStatement(sql_insert.toString());
                statement.bindString(1, sale.getSaleID());
                statement.bindString(2, sale.getCustomerID());
                statement.bindString(3, sale.getStation());
                statement.bindString(4, "");
                statement.bindLong(5, sale.getMatch());
                statement.bindLong(6, sale.getiState());
                statement.bindString(7, sale.getRemark()!=null?sale.getRemark():"");
                statement.bindString(8, sale.getSaleAddress());
                statement.bindString(9, sale.getCreateTime());
                statement.bindString(10, sale.getEmployeeID());
                statement.bindString(11, sale.getPlanPirce());
                statement.bindString(12, TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));
                statement.bindString(13, sale.getTelphone());//来电电话
                statement.bindString(14, sale.getPlanPirceInfo());//来电电话
                statement.executeInsert();
            }
            sql_insert = new StringBuffer();
            sql_insert.append("INSERT INTO T_B_CUSTOMERINFO(SaleID,UserID,StationID,CustomerType,IsNew,CustomerCardID,UserCardStatus," +
                    "StopSupplyType1,StopSupplyType2,StopSupplyType3,StopSupplyType4,StopSupplyType5,StopSupplyType6,StopSupplyType7,StopSupplyType8,UnInstallType1,UnInstallType2," +
                    "UnInstallType3,UnInstallType4,UnInstallType5,UnInstallType6,UnInstallType7,UnInstallType8,UnInstallType8,UnInstallType10,UnInstallType11,UnInstallType12" +
                    ",EmployeeID,CustomerName,IState,CustomerTypeName,InsertTime,Telephone,Address,TagID)");
            sql_insert.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)");
            for(UserXJInfo userXJInfo:userXJInfos){
                SQLiteStatement statement=    mDatabase.compileStatement(sql_insert.toString());
                statement.bindString(1, userXJInfo.getSaleid());
                statement.bindString(2, userXJInfo.getUserid());
                statement.bindString(3, userXJInfo.getStationcode());
                statement.bindString(4, userXJInfo.getCustomerType());
                statement.bindLong(5, userXJInfo.getIsNew());
                statement.bindString(6, userXJInfo.getCustomerCardID());
                statement.bindString(7, userXJInfo.getUserCardStatus());
                statement.bindString(8, userXJInfo.getStopSupplyType1());
                statement.bindString(9, userXJInfo.getStopSupplyType2());
                statement.bindString(10, userXJInfo.getStopSupplyType3());
                statement.bindString(11, userXJInfo.getStopSupplyType4());
                statement.bindString(12, userXJInfo.getStopSupplyType5());
                statement.bindString(13, userXJInfo.getStopSupplyType6());
                statement.bindString(14, userXJInfo.getStopSupplyType7());
                statement.bindString(15, userXJInfo.getStopSupplyType8());
                statement.bindString(16, userXJInfo.getUnInstallType1());
                statement.bindString(17, userXJInfo.getUnInstallType2());
                statement.bindString(18, userXJInfo.getUnInstallType3());
                statement.bindString(19, userXJInfo.getUnInstallType4());
                statement.bindString(20, userXJInfo.getUnInstallType5());
                statement.bindString(21, userXJInfo.getUnInstallType6());
                statement.bindString(22, userXJInfo.getUnInstallType7());
                statement.bindString(23, userXJInfo.getUnInstallType8());
                statement.bindString(24, userXJInfo.getUnInstallType9());
                statement.bindString(25, userXJInfo.getUnInstallType10());
                statement.bindString(26, userXJInfo.getUnInstallType11());
                statement.bindString(27, userXJInfo.getUnInstallType12());
                statement.bindString(28, EmployeeID);
                statement.bindString(29, userXJInfo.getUsername());
                statement.bindString(30, "3");
                statement.bindString(31, userXJInfo.getCustomerTypeName());
                statement.bindString(32,  TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));
                statement.bindString(33,  userXJInfo.getTelephone());
                statement.bindString(34,  userXJInfo.getDeliveraddress());
                statement.bindString(35,  userXJInfo.getUserCardID());
                statement.executeInsert();
            }
            sql_insert = new StringBuffer();
            sql_insert.append("INSERT INTO T_B_SALEDETAIL(SaleID,QPType,QPName,PlanSendNum,PlanReceiveNum,QTPrice,EmployeeID,StationID,IsEx,InsertTime" +
                    ")");
            sql_insert.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, ?,?,?)");
            for(SaleDetail saleDetail:saleDetails){
                SQLiteStatement statement=    mDatabase.compileStatement(sql_insert.toString());
                statement.bindString(1, saleDetail.getSaleID());
                statement.bindString(2, saleDetail.getQPType());
                statement.bindString(3, saleDetail.getQPName());
                statement.bindLong(4, saleDetail.getPlanSendNum());
                statement.bindLong(5, saleDetail.getPlanReceiveNum());
                statement.bindString(6, saleDetail.getRealQTPrice());
                statement.bindString(7, EmployeeID);
                statement.bindString(8, StationID);
                statement.bindLong(9, saleDetail.getIsEx());
                statement.bindString(10,TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));
                statement.executeInsert();
            }
            String saleID=saleID_Web.toString();
            int l=saleID.length();
            saleID=saleID.substring(0,l);
            //本地订单和后台对比
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            data=saleID(EmployeeID,StationID);
            for (int h = 0; h < data.size(); h++) {
                String tmp=data.get(h).get("SaleID");
                if (!saleID.contains(tmp)) {
                    upDateSaleStatus(EmployeeID,StationID,tmp);
                }
            }
        }
        catch(Exception ex){
            Log.e("数据插入出现异常",ex.toString());
        }finally {
        }
        long end = System.currentTimeMillis();
    }

    /**
     * 删除本地无效订单及相关信息
     * @param saleAllList
     * @param EmployeeID
     */
    public void deleteCanselSale(List<SaleAll> saleAllList,String EmployeeID,String StationID){
        long start=System.currentTimeMillis();
        try{
            int size=saleAllList.size();
            List<Sale> sales=new ArrayList<Sale>();
            for(int i=0;i<size;i++){
                String SaleID=saleAllList.get(i).getSale().getSaleID();
                if(isExist(EmployeeID,StationID,SaleID)){
                    sales.add(saleAllList.get(i).getSale());
                }

            }
            if(sales.size()>0){
                for(Sale sale:sales){
                    String SaleID=sale.getSaleID();
                    /**
                     * 删除该订单对应的所有相关信息
                     */
                    String whereClause_t="SaleID=? and EmployeeID=?";
                    String[] whereArgs_t={SaleID,EmployeeID};
                    mDatabase.delete("T_B_SALE", whereClause_t, whereArgs_t);
                    mDatabase.delete("T_B_SALEDETAIL", whereClause_t, whereArgs_t);
                    mDatabase.delete("T_B_CUSTOMERINFO", whereClause_t, whereArgs_t);
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        Log.e("删除异常订单","="+(end -start));
    }

    /**
     * 获取订单信息[未完成]
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Map<String, String>>GetSaleInfo(String EmployeeID,String StationID){
        long start=System.currentTimeMillis();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String[] selectionArgs = { EmployeeID};
        String sql="select a.SaleID,a.UserID,b.CustomerName,b.Telephone,a.Address,b.CustomerCardID,b.CustomerType,a.Telephone from T_B_SALE a left join T_B_CUSTOMERINFO b on a.SaleID=b.SaleID and a.EmployeeID=b.EmployeeID and a.StationID=b.StationID" +
                " where a.EmployeeID=? and a.IState='3' order by a.AssignTime desc";
        String sqld = "select PlanSendNum,QPName from T_B_SALEDETAIL where SaleID=? and EmployeeID=? and StationID=? and IsEx='0'";
        try{
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while (query.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("SaleID",query.getString(0));
                String SaleID=query.getString(0);//订单号
                map.put("UserID",query.getString(1));
                map.put("CustomerName",query.getString(2));
                map.put("Telephone",query.getString(3));
                map.put("Address",query.getString(4));
                map.put("CustomerCardID",query.getString(5));
                map.put("CustomerType",query.getString(6));
                map.put("CallingTelephone",query.getString(7));
                StringBuffer goodsInfo=new StringBuffer();
                Cursor rawQuery = mDatabase.rawQuery(sqld,
                        new String[] {SaleID, EmployeeID,StationID });
                while (rawQuery.moveToNext()) {
                    goodsInfo.append(rawQuery.getString(1)+"/"+rawQuery.getString(0)+"，");
                }
                String tmp=goodsInfo.toString();
                tmp=tmp.substring(0,tmp.length()-1);
                map.put("goodsInfo", tmp);
                list.add(map);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        return list;
    }

    /**
     * 获取打印信息
     * @param EmployeeID
     * @param SaleID
     * @return
     */
    public Sale GetPrint(String EmployeeID,String SaleID){
        Sale sale=new Sale();
        List<SaleDetail> saleDetailList=new ArrayList<SaleDetail>();
    try{
        String[] selectionArgs = { EmployeeID,SaleID};
        String sql="select a.SaleID,a.UserID,b.CustomerName,b.Telephone,a.Address,b.CustomerCardID,a.SendQP,a.ReceiveQP,a.FinishTime,b.CustomerTypeName," +
                " a.AllPrice,a.Address,a.Telephone from T_B_SALE a left join T_B_CUSTOMERINFO b on a.SaleID=b.SaleID and a.EmployeeID=b.EmployeeID and a.StationID=b.StationID" +
                " where a.EmployeeID=? and a.SaleID=? and (a.IState='4'or  a.IState='5')order by a.AssignTime desc";
        Cursor query = mDatabase.rawQuery(sql, selectionArgs);
        if(query.moveToFirst()){
            sale.setSaleID(query.getString(0));
            sale.setCustomerID(query.getString(1));
            sale.setUserName(query.getString(2));
            sale.setTelphone(query.getString(3));//联系电话
            sale.setAddress(query.getString(4));
            sale.setSendQP(query.getString(6));
            sale.setReceiveQP(query.getString(7));
            sale.setFinishTime(query.getString(8));
            sale.setRemark(query.getString(9));
            sale.setRealPirce(query.getString(10));
            sale.setSaleAddress(query.getString(11));
            sale.setPS(query.getString(12));//来电电话
        }
    }catch(Exception ex){
        ex.toString();
    }
    return sale;
}
    /**
     * 已完成订单
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Map<String, String>>GetSaleInfoFinish(String EmployeeID,String StationID){
        long start=System.currentTimeMillis();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String[] selectionArgs = { EmployeeID};
        String sql="select a.SaleID,a.UserID,b.CustomerName,b.Telephone,a.Address,b.CustomerCardID,b.CustomerType,a.Telephone from T_B_SALE a left join T_B_CUSTOMERINFO b on a.SaleID=b.SaleID and a.EmployeeID=b.EmployeeID and a.StationID=b.StationID" +
                " where a.EmployeeID=? and (a.IState='4'or  a.IState='5')order by a.FinishTime desc";
        String sqld = "select PlanSendNum,QPName from T_B_SALEDETAIL where SaleID=? and EmployeeID=? and StationID=? and IsEx='0'";
        try{
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while (query.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("SaleID",query.getString(0));
                String SaleID=query.getString(0);//订单号
                map.put("UserID",query.getString(1));
                map.put("CustomerName",query.getString(2));
                map.put("Telephone",query.getString(3));
                map.put("Address",query.getString(4));
                map.put("CustomerCardID",query.getString(5));
                map.put("CustomerType",query.getString(6));
                map.put("CallingTelephone",query.getString(7));
                StringBuffer goodsInfo=new StringBuffer();
                Cursor rawQuery = mDatabase.rawQuery(sqld,
                        new String[] {SaleID, EmployeeID,StationID });
                while (rawQuery.moveToNext()) {
                    goodsInfo.append(rawQuery.getString(1)+"/"+rawQuery.getString(0)+"，");
                }
                String tmp=goodsInfo.toString();
                tmp=tmp.substring(0,tmp.length()-1);
                map.put("goodsInfo", tmp);
                list.add(map);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        return list;
    }

    /**
     * 看订单是否存在
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     * @return
     */
    public boolean isExist(String EmployeeID,String StationID,String SaleID){
        boolean result=false;
        try {
            String whereClause = "EmployeeID=? and StationID=? and SaleID=?";
            String[] whereArgs = new String[] { EmployeeID, StationID,SaleID };
            Cursor cursor = mDatabase.query("T_B_SALE", null, whereClause,
                    whereArgs, null, null, null);
            if (cursor.moveToFirst()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询该订单是否已完成
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     * @return
     */
    public boolean isDone(String EmployeeID,String StationID,String SaleID){
        boolean result=false;
        try {
            String whereClause = "EmployeeID=? and StationID=? and SaleID=? and IState='4'";
            String[] whereArgs = new String[] { EmployeeID, StationID,SaleID };
            Cursor cursor = mDatabase.query("T_B_SALE", null, whereClause,
                    whereArgs, null, null, null);
            if (cursor.moveToFirst()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 该用户是否存在可配送的订单
     * @param EmployeeID
     * @param StationID
     * @param UserID
     * @return
     */
    public boolean userIsExist(String EmployeeID,String StationID,String UserID){
        boolean result=false;
        try {
            String whereClause = "EmployeeID=? and StationID=? and  UserID=? and IState='3'";
            String[] whereArgs = new String[] { EmployeeID, StationID,UserID };
            Cursor cursor = mDatabase.query("T_B_SALE", null, whereClause,
                    whereArgs, null, null, null);
            if (cursor.moveToFirst()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询该用户是否有需要整改的订单
     * @param EmployeeID
     * @param StationID
     * @param UserID
     * @return
     */
    public boolean userIsRectify(String EmployeeID,String StationID,String UserID){
        boolean result=false;
        try {
            String whereClause = "EmployeeID=? and StationID=? and  UserID=? and IsInspected='0'";
            String[] whereArgs = new String[] { EmployeeID, StationID,UserID };
            Cursor cursor = mDatabase.query("T_B_UserRectifyInfo", null, whereClause,
                    whereArgs, null, null, null);
            if (cursor.moveToFirst()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 该用户是否已发卡
     * @param EmployeeID
     * @param StationID
     * @param TagID
     * @return
     */
    public boolean IsMakeCard(String EmployeeID,String StationID,String TagID){
        boolean result=false;
        try {
            String whereClause = "EmployeeID=? and StationID=? and  TagID=? ";
            String[] whereArgs = new String[] { EmployeeID, StationID,TagID };
            Cursor cursor = mDatabase.query("T_B_CUSTOMERINFO", null, whereClause,
                    whereArgs, null, null, null);
            if (cursor.moveToFirst()) {
                String tagID=cursor.getString(0);
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 根据用户号获取订单相关信息
     * @param EmployeeID
     * @param StationID
     * @param UserID
     * @return
     */
    public List<Map<String, String>>GetSaleInfoByUserID(String EmployeeID,String StationID,String UserID){
        long start=System.currentTimeMillis();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String[] selectionArgs = { StationID,EmployeeID,UserID };
        String sql="select a.SaleID,a.UserID,b.CustomerName,b.Telephone,a.Address,b.IsNew,b.CustomerCardID,b.CustomerTypeName,a.AllPrice,a.Telephone" +
                " from  T_B_SALE a  " +
                " left join T_B_CUSTOMERINFO b on a.SaleID=b.SaleID and a.EmployeeID=b.EmployeeID and a.StationID=b.StationID" +
                " where  a.StationID=? and a.EmployeeID=? and a.IState='3'and a.UserID=?";

        String sqld = "select PlanSendNum,QPName from T_B_SALEDETAIL where SaleID=? and EmployeeID=? and StationID=? and IsEx='0'";
        try{
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while (query.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("SaleID",query.getString(0));
                String SaleID=query.getString(0);//订单号
                map.put("UserID",query.getString(1));
                map.put("CustomerName",query.getString(2));
                map.put("Telephone",query.getString(3));
                map.put("Address",query.getString(4));
                map.put("IsNew",query.getString(5));
                map.put("CustomerCardID",query.getString(6));
                map.put("CustomerTypeName",query.getString(7));
                map.put("AllPrice",query.getString(8));
                map.put("CallingTelephone",query.getString(9));
                StringBuffer goodsInfo=new StringBuffer();
                int GoodsCount=0;
                Cursor rawQuery = mDatabase.rawQuery(sqld,
                        new String[] {SaleID, EmployeeID,StationID });
                while (rawQuery.moveToNext()) {
                    String c=rawQuery.getString(0);
                    int tmp=Integer.parseInt(c);
                    GoodsCount=GoodsCount+tmp;
                    goodsInfo.append(rawQuery.getString(1)+"/"+rawQuery.getString(0)+"，");
                }
                String tmp=goodsInfo.toString();
                tmp=tmp.substring(0,tmp.length()-1);
                map.put("GoodsInfo", tmp);
                String GoodsCountStr=String.valueOf(GoodsCount);
                map.put("GoodsCount", GoodsCountStr);
                list.add(map);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        return list;
    }
    /**
     * 根据订单号获取订单相关信息
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     * @return
     */
    public List<Map<String, String>>GetSaleInfoBySaleID(String EmployeeID,String StationID,String SaleID){
        long start=System.currentTimeMillis();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String[] selectionArgs = { StationID,EmployeeID,SaleID };
        String sql="select a.SaleID,a.UserID,b.CustomerName,b.Telephone,a.Address,b.IsNew,b.CustomerCardID,b.CustomerTypeName,a.AllPrice,a.Telephone" +
                " from  T_B_SALE a  " +
                " left join T_B_CUSTOMERINFO b on a.SaleID=b.SaleID and a.EmployeeID=b.EmployeeID and a.StationID=b.StationID" +
                " where  a.StationID=? and a.EmployeeID=? and a.IState='3'and a.SaleID=?";

        String sqld = "select PlanSendNum,QPName from T_B_SALEDETAIL where SaleID=? and EmployeeID=? and StationID=? and IsEx='0'";
        try{
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while (query.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("SaleID",query.getString(0));
                map.put("UserID",query.getString(1));
                map.put("CustomerName",query.getString(2));
                map.put("Telephone",query.getString(3));
                map.put("Address",query.getString(4));
                map.put("IsNew",query.getString(5));
                map.put("CustomerCardID",query.getString(6));
                map.put("CustomerTypeName",query.getString(7));
                map.put("AllPrice",query.getString(8));
                map.put("CallingTelephone",query.getString(9));
                StringBuffer goodsInfo=new StringBuffer();
                int GoodsCount=0;
                Cursor rawQuery = mDatabase.rawQuery(sqld,
                        new String[] {SaleID, EmployeeID,StationID });
                while (rawQuery.moveToNext()) {
                    String c=rawQuery.getString(0);
                    int tmp=Integer.parseInt(c);
                    GoodsCount=GoodsCount+tmp;
                    goodsInfo.append(rawQuery.getString(1)+"/"+rawQuery.getString(0)+"，");
                }
                String tmp=goodsInfo.toString();
                tmp=tmp.substring(0,tmp.length()-1);
                map.put("GoodsInfo", tmp);
                String GoodsCountStr=String.valueOf(GoodsCount);
                map.put("GoodsCount", GoodsCountStr);
                list.add(map);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        Log.e("GetSaleInfoByUserID","="+(end -start));
        return list;
    }
    /**
     * 获取商品类型
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     * @return
     */
    public  List<QPType> GetGoodsType(String EmployeeID,String StationID,String SaleID ){
        List<QPType> saleDetails=new ArrayList<QPType>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID,SaleID };
            String sql="select  QPType,QPName,PlanSendNum  from T_B_SALEDETAIL where EmployeeID=? and StationID=? and SaleID=? and IsEx='0'";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while (query.moveToNext()) {
                QPType qpType=new QPType();
                qpType.setQPType(query.getString(0));
                qpType.setQPName(query.getString(1));
                qpType.setQPNum(query.getString(2));
                saleDetails.add(qpType);
            }
        }catch(Exception Ex){
            Ex.printStackTrace();
        }
     return saleDetails;
    }
    public  List<String> GetSaleDetail(String EmployeeID,String StationID,String SaleID ){
        List<String> s=new ArrayList<String>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID,SaleID };
            String sql="select  QPType,QPName,PlanSendNum,PlanReceiveNum  from T_B_SALEDETAIL where EmployeeID=? and StationID=? and SaleID=? and IsEx='0'";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while (query.moveToNext()) {
                s.add(query.getString(0));
            }
        }catch(Exception Ex){
            Ex.printStackTrace();
        }
        return s;
    }

    /**
     * 保存订单数据到本地数据库
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     * @param sale
     * @param saleDetails
     * @param scanHistories
     * @return
     */
    public boolean upDataSaleInfo(String EmployeeID, String StationID, String SaleID, Sale sale, List<SaleDetail> saleDetails,
                                  List<ScanHistory> scanHistories){
        boolean ret=false;
        try{
            String whereClauseBys = "EmployeeID=? and StationID=? and SaleID=? and IState='3'";
            String[] whereArgsBys = { EmployeeID,StationID, SaleID};
            ContentValues valuesBys = new ContentValues();
            valuesBys.put("SendQP",sale.getSendQP());
            valuesBys.put("ReceiveQP",sale.getReceiveQP());
            valuesBys.put("DeliveryByInput",sale.getDeliverByInput());
            valuesBys.put("ReceiveByInput",sale.getReceiveByInput());
            valuesBys.put("GPS_J",sale.getGPS_J());
            valuesBys.put("GPS_W",sale.getGPS_W());
            valuesBys.put("FinishTime", TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));
            valuesBys.put("IState","4");

            int i = mDatabase.update("T_B_SALE", valuesBys,
                    whereClauseBys, whereArgsBys);
            if(i>0){
                ret=true;
            }else{
                ret=false;
            }
            int size=saleDetails.size();
            for(int a=0;a<size;a++){
                whereClauseBys = "EmployeeID=? and StationID=? and SaleID=? ";
                valuesBys = new ContentValues();
                valuesBys.put("SendNum",saleDetails.get(a).getSendNum());
                valuesBys.put("ReceiveNum",saleDetails.get(a).getReceiveNum());
                i=0;
                i = mDatabase.update("T_B_SALEDETAIL", valuesBys,
                        whereClauseBys, whereArgsBys);
                if(i>0){
                    ret=true;
                }else{
                    ret=false;
                }
            }
            size=scanHistories.size();
            for(int b=0;b<size;b++){
                valuesBys = new ContentValues();
                valuesBys.put("EmployeeID",EmployeeID);
                valuesBys.put("StationID",StationID);
                valuesBys.put("UserID",scanHistories.get(b).getCustomerID());
                valuesBys.put("SaleID",scanHistories.get(b).getSaleID());
                valuesBys.put("UseRegCode",scanHistories.get(b).getUseRegCode());
                valuesBys.put("TypeFlag",scanHistories.get(b).getTypeFlag());
                valuesBys.put("QPType",scanHistories.get(b).getQPType());
                valuesBys.put("InsertTime",TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));
                String saleid=scanHistories.get(b).getSaleID();
                if(isExistScan(EmployeeID,StationID,saleid)){
                    //更新
                    i = mDatabase.update("T_B_SCANHISTORY", valuesBys,
                            whereClauseBys, whereArgsBys);
                    if(i >0){
                        ret=true;
                    }else{
                        ret=false;
                    }
                }else{
                    long  h = mDatabase.insert("T_B_SCANHISTORY",null,valuesBys);
                    if(h>0){
                        ret=true;
                    }else{
                        ret=false;
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            Log.e("更新订单异常",ex.toString());
        }
        return ret;

    }
    public boolean isExistScan(String EmployeeID,String StationID,String SaleID){
        boolean result=false;
        try {
            String whereClause = "EmployeeID=? and StationID=? and SaleID=?";
            String[] whereArgs = new String[] { EmployeeID, StationID,SaleID };
            Cursor cursor = mDatabase.query("T_B_SCANHISTORY", null, whereClause,
                    whereArgs, null, null, null);
            if (cursor.moveToFirst()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 整理上传销售信息
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     */
    public HsicMessage upLoadSaleInfo(Context context,String  DeviceId,String EmployeeID, String StationID, String SaleID,String CustomID){
        HsicMessage hsicMess = new HsicMessage();
        hsicMess.setRespCode(-1);
        SaleAll saleAll=new SaleAll();
        Sale sale=new Sale();
        List<ScanHistory> scanHistories=new ArrayList<ScanHistory>();
        List<SaleDetail> saleDetails=new ArrayList<SaleDetail>();
        try {
            String[] selectionArgs = new String[] { EmployeeID, StationID,SaleID };
            String sql=" select SendQP,ReceiveQP,DeliveryByInput,ReceiveByInput,GPS_J,GPS_W,AllPrice,RealPriceInfo from T_B_SALE where EmployeeID=? and StationID=? and SaleID=?";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            if(query.moveToFirst()){
                sale.setSaleID(SaleID);
                sale.setStation(StationID);
                sale.setCustomerID(CustomID);
                sale.setSendQP(query.getString(0));
                sale.setReceiveQP(query.getString(1));
                sale.setDeliverByInput(query.getString(2));
                sale.setReceiveByInput(query.getString(3));
                sale.setGPS_J(query.getDouble(4));
                sale.setGPS_W(query.getDouble(5));
                sale.setRealPirce(query.getString(6));
                sale.setRealPirceInfo(query.getString(7));
                sale.setFinishTime(TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));
            }
            /**
             * saledetail
             */
            sql="select SendNum,ReceiveNum,QPType,QPName,QTPrice from T_B_SALEDETAIL where EmployeeID=? and StationID=? and SaleID=?";
            query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                SaleDetail saleDetail=new SaleDetail();
                saleDetail.setSaleID(SaleID);
                saleDetail.setSendNum(query.getInt(0));
                saleDetail.setReceiveNum(query.getInt(1));
                saleDetail.setQPType(query.getString(2));
                saleDetail.setQPName(query.getString(3));
                saleDetail.setRealQTPrice(query.getString(4));
                saleDetails.add(saleDetail);
            }
            /**
             * scanhistory
             */
            sql="select QPType,TypeFlag,UseRegCode from T_B_SCANHISTORY where EmployeeID=? and StationID=? and SaleID=?";
            query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                ScanHistory scanHistory=new ScanHistory();
                scanHistory.setQPType(query.getString(0));
                scanHistory.setTypeFlag(query.getString(1));
                scanHistory.setUseRegCode(query.getString(2));
                scanHistories.add(scanHistory);
            }
            sale.setSale_detail_info(saleDetails);
            saleAll.setSale(sale);
            saleAll.setScanHistory(scanHistories);
            String data = "";// 上传传输的数据
            data = JSONUtils.toJsonWithGson(saleAll);
            hsicMess.setRespMsg(data);
            String requestData = JSONUtils.toJsonWithGson(hsicMess);
            WebServiceHelper web = new WebServiceHelper(context);
            String[] selection = { "DeviceID", "RequestData" };
            selectionArgs = new String[]{ DeviceId, requestData };
            String methodName = "";
            methodName = "UpdateSaleInfo";// 方法名称
            hsicMess = web.uploadInfo(selection, methodName, selectionArgs);
            if(hsicMess.getRespCode()==0){
                upDateSaleStatus(EmployeeID,StationID,SaleID);
            }
        }catch (Exception ex){
            hsicMess.setRespCode(-2);
            hsicMess.setRespMsg("上传销售信息时接口异常");
            ex.printStackTrace();
        }
        Log.e("saleAll",JSONUtils.toJsonWithGson(saleAll));
        return hsicMess;
    }

    /**
     *打印
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     * @return
     */
    public List<SaleDetail> GetSaleDetailByP(String EmployeeID, String StationID, String SaleID){
    List<SaleDetail> details=new ArrayList<>();
    try{
        String[] selectionArgs = new String[] { EmployeeID, StationID,SaleID };
        String sql="select SendNum,QPType,QPName,QTPrice from T_B_SALEDETAIL where EmployeeID=? and StationID=? and SaleID=?";
       Cursor query = mDatabase.rawQuery(sql, selectionArgs);
        while(query.moveToNext()){
            SaleDetail saleDetail=new SaleDetail();
            saleDetail.setSaleID(SaleID);
            saleDetail.setSendNum(query.getInt(0));
            saleDetail.setQPType(query.getString(1));
            saleDetail.setQPName(query.getString(2));
            saleDetail.setQPPrice(query.getString(3));
            details.add(saleDetail);
        }

    }catch(Exception ex){
        ex.toString();
        ex.printStackTrace();
    }
    return details;
}
    /**
     * 更新本地订单状态
     */
    public int upDateSaleStatus(String EmployeeID, String StationID, String SaleID){
        int ret=-1;
        ContentValues values = new ContentValues();
        String whereClause = "EmployeeID=? and StationID=? and SaleID=?";
        String[] whereArgs={EmployeeID,StationID,SaleID};
        values.put("IState","5");
        try{
            ret=mDatabase.update("T_B_SALE",values,whereClause,whereArgs);
        }catch (Exception ex){
            ex.printStackTrace();
            ret=-1;
        }
        return ret;
    }
    /**
     * 退单成功 8
     */
    public int ChargeBackStatus(String EmployeeID, String StationID, String SaleID){
        int ret=-1;
        ContentValues values = new ContentValues();
        String whereClause = "EmployeeID=? and StationID=? and SaleID=?";
        String[] whereArgs={EmployeeID,StationID,SaleID};
        values.put("IState","8");
        try{
            ret=mDatabase.update("T_B_SALE",values,whereClause,whereArgs);
        }catch (Exception ex){
            ex.printStackTrace();
            ret=-1;
        }
        return ret;
    }
    /**
     * 将巡检信息保存到本地
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     * @param userXJInfo
     * @return
     */
    public boolean upDateInspectionInfo(String EmployeeID, String StationID, String SaleID,UserXJInfo userXJInfo){
        boolean ret=false;
        try{
            /**
             * 更新巡检信息
             */
            String whereClauseBys = "EmployeeID=? and StationID=? and SaleID=? ";
            String[] whereArgsBys = { EmployeeID,StationID, SaleID};
            ContentValues valuesBys = new ContentValues();
            valuesBys.put("RelationID",userXJInfo.getAttachID());
            valuesBys.put("IsInspected","1");
            valuesBys.put("InspectionStatus",userXJInfo.getInspectionStatus());
            valuesBys.put("InspectionDate",userXJInfo.getInspectionDate());
            valuesBys.put("InspectionMan",userXJInfo.getInspectionMan());
            valuesBys.put("StopSupplyType1",userXJInfo.getStopSupplyType1());
            valuesBys.put("StopSupplyType2",userXJInfo.getStopSupplyType2());
            valuesBys.put("StopSupplyType3",userXJInfo.getStopSupplyType3());
            valuesBys.put("StopSupplyType4",userXJInfo.getStopSupplyType4());
            valuesBys.put("StopSupplyType5",userXJInfo.getStopSupplyType5());
            valuesBys.put("StopSupplyType6",userXJInfo.getStopSupplyType6());
            valuesBys.put("StopSupplyType7",userXJInfo.getStopSupplyType7());
            valuesBys.put("StopSupplyType8",userXJInfo.getStopSupplyType8());
            valuesBys.put("UnInstallType1",userXJInfo.getUnInstallType1());
            valuesBys.put("UnInstallType2",userXJInfo.getUnInstallType2());
            valuesBys.put("UnInstallType3",userXJInfo.getUnInstallType3());
            valuesBys.put("UnInstallType4",userXJInfo.getUnInstallType4());
            valuesBys.put("UnInstallType5",userXJInfo.getUnInstallType5());
            valuesBys.put("UnInstallType6",userXJInfo.getUnInstallType6());
            valuesBys.put("UnInstallType7",userXJInfo.getUnInstallType7());
            valuesBys.put("UnInstallType8",userXJInfo.getUnInstallType8());
            valuesBys.put("UnInstallType9",userXJInfo.getUnInstallType9());
            valuesBys.put("UnInstallType10",userXJInfo.getUnInstallType10());
            valuesBys.put("UnInstallType11",userXJInfo.getUnInstallType11());
            valuesBys.put("UnInstallType12",userXJInfo.getUnInstallType12());
            int i = mDatabase.update("T_B_CUSTOMERINFO", valuesBys,
                    whereClauseBys, whereArgsBys);
            if (i>0) {
                ret=true;
            }
            /**
             * 插入关联表信息
             */
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * 上传巡检信息
     * @param EmployeeID
     * @param StationID
     * @param SaleID
     * @param UserID
     */
    public HsicMessage upLoadXJInfo(Context context,String DeviceId,String EmployeeID, String StationID, String SaleID,String UserID){
        HsicMessage hsicMess = new HsicMessage();
        WebServiceHelper web = new WebServiceHelper(context);
        try{
            hsicMess.setRespCode(10);
            UserXJInfo userXJInfo = new UserXJInfo();
            String[] selections = { EmployeeID, StationID,SaleID};
            String sql="select RelationID,InspectionStatus,InspectionDate,InspectionMan,StopSupplyType1,StopSupplyType2,StopSupplyType3," +
                    "StopSupplyType4,StopSupplyType5,StopSupplyType6,StopSupplyType7,StopSupplyType8,UnInstallType1,UnInstallType2,UnInstallType3," +
                    "UnInstallType4,UnInstallType5,UnInstallType6,UnInstallType7,UnInstallType8,UnInstallType9,UnInstallType10,UnInstallType11," +
                    "UnInstallType12 from T_B_CUSTOMERINFO where EmployeeID=? and StationID=? and SaleID=?";
            Cursor query = mDatabase.rawQuery(sql, selections);
            if(query.moveToNext()){
                userXJInfo.setAttachID(query.getString(0));
                userXJInfo.setInspectionStatus(query.getString(1));
                userXJInfo.setInspectionDate(query.getString(2));
                userXJInfo.setInspectionMan(query.getString(3));
                userXJInfo.setStopSupplyType1(query.getString(4));
                userXJInfo.setStopSupplyType2(query.getString(5));
                userXJInfo.setStopSupplyType3(query.getString(6));
                userXJInfo.setStopSupplyType4(query.getString(7));
                userXJInfo.setStopSupplyType5(query.getString(8));
                userXJInfo.setStopSupplyType6(query.getString(9));
                userXJInfo.setStopSupplyType7(query.getString(10));
                userXJInfo.setStopSupplyType8(query.getString(11));
                userXJInfo.setUnInstallType1(query.getString(12));
                userXJInfo.setUnInstallType2(query.getString(13));
                userXJInfo.setUnInstallType3(query.getString(14));
                userXJInfo.setUnInstallType4(query.getString(15));
                userXJInfo.setUnInstallType5(query.getString(16));
                userXJInfo.setUnInstallType6(query.getString(17));
                userXJInfo.setUnInstallType7(query.getString(18));
                userXJInfo.setUnInstallType8(query.getString(19));
                userXJInfo.setUnInstallType9(query.getString(20));
                userXJInfo.setUnInstallType10(query.getString(21));
                userXJInfo.setUnInstallType11(query.getString(22));
                userXJInfo.setUnInstallType12(query.getString(23));
                userXJInfo.setStationcode(StationID);
                userXJInfo.setSaleid(SaleID);
                userXJInfo.setUserid(UserID);
                userXJInfo.setIsBackup("0");
                userXJInfo.setRefuseInspection("0");
                userXJInfo.setErrorNature("0");
                userXJInfo.setErrorAddress("0");
            }
            String data = "";// 上传传输的数据
            data = JSONUtils.toJsonWithGson(userXJInfo);
            hsicMess.setRespMsg(data);
            String requestData = JSONUtils.toJsonWithGson(hsicMess);
            String[] selection = { "DeviceID", "RequestData" };
            String[] selectionArgs = { DeviceId, requestData };
            String methodName = "";
            methodName = "UpSaleInspectionInfo";// 方法名称
            hsicMess = web.uploadInfo(selection, methodName, selectionArgs);
            int i = hsicMess.getRespCode();// 方法执行结果
            if(i ==0){
                upDateXJStatus(EmployeeID,StationID,SaleID);
            }
        }catch(Exception ex){
            ex.toString();
            ex.printStackTrace();
        }
        return hsicMess;
    }
    /**
     * 更新本地订单状态
     */
    public int upDateXJStatus(String EmployeeID, String StationID, String SaleID){
        int ret=-1;
        ContentValues values = new ContentValues();
        String whereClause = "EmployeeID=? and StationID=? and SaleID=?";
        String[] whereArgs={EmployeeID,StationID,SaleID};
        values.put("IsInspected","2");
        try{
            ret=mDatabase.update("T_B_CUSTOMERINFO",values,whereClause,whereArgs);
        }catch (Exception ex){
            ex.printStackTrace();
            ret=-1;
        }
        return ret;
    }
    /**
     * 将照片基本信息插入到数据表中  巡检
     * @param
     * @param SaleID
     * @param
     * @param RelationID
     */
    public void InsertXJAssociation(String EmployeeID, String SaleID, String ImageName, String RelationID, String FileName){
        try{
            ContentValues cValue = new ContentValues();
            cValue.put("EmployeeID", EmployeeID);
            cValue.put("SaleID", SaleID);
            cValue.put("ImageName", ImageName);
            cValue.put("RelationID", RelationID);
            cValue.put("FileName", FileName);
            cValue.put("IsUpload", "0");
            cValue.put("InsertTime",TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));

            long ret = mDatabase.insert("T_B_XJ_Association", null, cValue);
        }catch(Exception ex){
            ex.toString();

        }
    }
    public void DeleteXJAssociation(String Path,String EmployeeID,String SaleID){
        try{
            String whereClause_t="ImageName=? and EmployeeID=? and SaleID=?";
            String[] whereArgs_t={Path,EmployeeID,SaleID};
            long ret=mDatabase.delete("T_B_XJ_Association", whereClause_t, whereArgs_t);
        }catch(Exception ex){
            Log.e("删除照片异常",ex.toString());
        }
    }
    /**
     * 查询该车次该订单下所有的照片关联信息 巡检
     *
     */
    public List<FileRelationInfo> GetXJFileRelationInfo(String EmployeeID,String SaleID){
        List<FileRelationInfo> FileRelationInfo_LIST=new ArrayList<FileRelationInfo>();
        try{
            String[] selectionArgs = { EmployeeID, SaleID};
            String sql = "select RelationID,ImageName,FileName  from  T_B_XJ_Association where EmployeeID=? and SaleID=? and IsUpload='0'";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                FileRelationInfo f=new FileRelationInfo();
                f.setRelationID(query.getString(0));
                String FilePath=query.getString(2)+"/"+query.getString(1);
                f.setFilePath(FilePath);
                f.setImageName(query.getString(1));
                f.setTruckNoId(EmployeeID);
                f.setSaleID(SaleID);
                FileRelationInfo_LIST.add(f);
            }

        }catch(Exception ex){

        }
        return FileRelationInfo_LIST;
    }
    public int UpDateXJAssociation(String EmployeeID,String SaleID,String Path){
        int ret=-1;
        ContentValues values = new ContentValues();
        String whereClause = "EmployeeID=? and SaleID=? and ImageName=?";
        String[] whereArgs={EmployeeID,SaleID,Path};
        values.put("IsUpLoad","1");
        try{
            ret=mDatabase.update("T_B_XJ_Association",values,whereClause,whereArgs);
        }catch (Exception ex){
            ex.printStackTrace();
            ret=-1;
        }
        return ret;
    }

    /**
     * 上传附件关联信息
     * @param FileRelationInfo_LIST
     * @param deviceid
     * @param context
     * @return
     */
    public HsicMessage UpLoadA(List<FileRelationInfo> FileRelationInfo_LIST, String deviceid,Context context) {
        HsicMessage hsicMessage = new HsicMessage();
        hsicMessage.setRespCode(10);
        try {
            List<FileRelationInfo> upLoadSuc=new ArrayList<FileRelationInfo>();//暂存已经上传成功的附件信息
            int size=0;
            size=FileRelationInfo_LIST.size();
            if(size>0){
                for(int i=0;i<size;i++){
                    List<FileRelationInfo> list = new ArrayList<FileRelationInfo>();//上传附件信息
                    FileRelationInfo fri = FileRelationInfo_LIST.get(i);
                    fri.setFilePath(fri.getFilePath());
                    fri.setRelationID(fri.getRelationID());
                    String ImageName=fri.getImageName();
                    String TruckNoId=fri.getTruckNoId();
                    String SaleID=fri.getSaleID();
                    list.add(fri);
                    String respMsg = JSONUtils.toJsonWithGson(list);
                    hsicMessage.setRespMsg(respMsg);
                    WebServiceHelper web = new WebServiceHelper(context);
                    String requestData = JSONUtils.toJsonWithGson(hsicMessage);// web接口参数
                    String[] webRe = { "DeviceID", "RequestData" };
                    String webMethod = "UpFileReletionInfo";
                    String[] value = { deviceid, requestData };
                    hsicMessage = web.uploadInfo(webRe, webMethod, value);
                    if(hsicMessage.getRespCode()==0){
                        //关联表上传成功更新本地上传字段
                        FileRelationInfo f=new FileRelationInfo();
                        f.setTruckNoId(TruckNoId);
                        f.setSaleID(SaleID);
                        f.setImageName(ImageName);
                        upLoadSuc.add(f);

                    }
                }
            }
            //批量更新上传成功以后的关联表本地字段
            int size_up=upLoadSuc.size();
            for(int m=0;m<size_up;m++){
                String ImageName=upLoadSuc.get(m).getImageName();
                String TruckNoId=upLoadSuc.get(m).getTruckNoId();
                String SaleID=upLoadSuc.get(m).getSaleID();
                UpDateXJAssociation(TruckNoId, SaleID, ImageName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            hsicMessage.setRespCode(5);
            hsicMessage.setRespMsg("调用借口异常");
        }
        return hsicMessage;
    }

    /**
     * 查询本地订单
     * @return
     */
    public List<Map<String, String>> saleID(String EmployeeID,String StationID) {
        List<Map<String, String>> saleID = new ArrayList<Map<String, String>>();
        String[] selectionArgs = { EmployeeID, StationID};
        try {
            String sql = "select SaleID from T_B_SALE where EmployeeID=? and StationID=? and IState='3'";
            Cursor cursor = mDatabase.rawQuery(sql, selectionArgs);
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("SaleID", cursor.getString(0));
                saleID.add(map);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return saleID;
    }

    /**
     * 获取已经做完未上传的订单[IState:4]
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Sale> UpHistorySale(String EmployeeID,String StationID){
        List<Sale> sales=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID from T_B_SALE where EmployeeID=? and StationID=? and IState='4'";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                Sale sale=new Sale();
                sale.setSaleID(query.getString(0));
                sale.setCustomerID(query.getString(1));
                sales.add(sale);
            }
        }catch(Exception  ex){

        }
        return sales;
    }
    public List<Sale> UpHistoryXJ(String EmployeeID,String StationID){
        List<Sale> sales=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID from T_B_CUSTOMERINFO where EmployeeID=? and StationID=? and IsInspected='1'";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                Sale sale=new Sale();
                sale.setSaleID(query.getString(0));
                sale.setCustomerID(query.getString(1));
                sales.add(sale);
            }
        }catch(Exception  ex){

        }
        return sales;
    }
    /**
     * 上传历史订单
     * @param context
     * @param DeviceId
     * @param EmployeeID
     * @param StationID
     */
    public void UpHistorySale(Context context,String  DeviceId,String EmployeeID, String StationID){
        List<Sale> sales=new ArrayList<>();
        sales=UpHistorySale(EmployeeID,StationID);
        int size=sales.size();
        if(size>0){
            for(int i=0;i<size;i++){
                String SaleID;String CustomID;
                SaleID= sales.get(i).getSaleID();
                CustomID=sales.get(i).getCustomerID();
                upLoadSaleInfo(context,DeviceId,EmployeeID,StationID,SaleID,CustomID);
                /**
                 *上传巡检关联表历史信息
                 */
                List<FileRelationInfo> fileRelationInfos=new ArrayList<>();
                fileRelationInfos=GetXJFileRelationInfo(EmployeeID,SaleID);
                size=fileRelationInfos.size();
                if(size>0){
                    UpLoadA(fileRelationInfos,DeviceId,context);
                }
            }

        }
    }

    /**
     * 上传巡检历史信息
     * @param context
     * @param DeviceId
     * @param EmployeeID
     * @param StationID
     */
    public void UpHistoryXJ(Context context,String  DeviceId,String EmployeeID, String StationID){
        List<Sale> sales=new ArrayList<>();
        sales=UpHistoryXJ(EmployeeID,StationID);
        int size=sales.size();
        if(size>0){
            for(int i=0;i<size;i++){
                String SaleID;String CustomID;
                SaleID= sales.get(i).getSaleID();
                CustomID=sales.get(i).getCustomerID();
                upLoadXJInfo(context,DeviceId,EmployeeID,StationID,SaleID,CustomID);
            }

        }
    }

    /**
     *本地所有订单
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Sale> AllSale(String EmployeeID,String StationID){
        List<Sale> sales=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID from T_B_SALE where EmployeeID=? and StationID=? and (IState='3'or IState='4' or IState='5')";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                Sale sale=new Sale();
                sale.setSaleID(query.getString(0));
                sale.setCustomerID(query.getString(1));
                sales.add(sale);
            }
        }catch(Exception  ex){

        }
        return sales;
    }

    /**
     * 已上传
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Sale> uploadList(String EmployeeID,String StationID){
        List<Sale> sales=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID from T_B_SALE where EmployeeID=? and StationID=? and IState='5'";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                Sale sale=new Sale();
                sale.setSaleID(query.getString(0));
                sale.setCustomerID(query.getString(1));
                sales.add(sale);
            }
        }catch(Exception  ex){

        }
        return sales;
    }
    /**
     * 已完成[完成+上传]
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Sale> finishList(String EmployeeID,String StationID){
        List<Sale> sales=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID from T_B_SALE where EmployeeID=? and StationID=? and (IState='4'or IState='5')";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                Sale sale=new Sale();
                sale.setSaleID(query.getString(0));
                sale.setCustomerID(query.getString(1));
                sales.add(sale);
            }
        }catch(Exception  ex){
            ex.toString();
        }
        return sales;
    }

    /**
     *
     * @param EmployeeID
     * @param StationID
     * @param customCardID
     */
    public List<UserXJInfo> getInfoByCustom(String EmployeeID,String StationID,String customCardID){
        List<UserXJInfo> userXJInfos=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID,customCardID };
            String sql=" select SaleID,UserID,CustomerName,Telephone,Address,CustomerTypeName from T_B_CUSTOMERINFO where EmployeeID=? and StationID=? and CustomerCardID=?";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while (query.moveToNext()){
                UserXJInfo userXJInfo=new UserXJInfo();
                userXJInfo.setSaleid(query.getString(0));
                userXJInfo.setUserid(query.getString(1));
                userXJInfo.setUsername(query.getString(2));
                userXJInfo.setTelephone(query.getString(3));
                userXJInfo.setDeliveraddress(query.getString(4));
                userXJInfo.setCustomerTypeName(query.getString(5));
                userXJInfos.add(userXJInfo);
            }
        }catch(Exception ex){

        }
        return userXJInfos;
    }
    public List<UserXJInfo> getInfoByAddress(String EmployeeID,String StationID,String Address ){
        List<UserXJInfo> userXJInfos=new ArrayList<>();
//        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID,CustomerName,Telephone,Address,CustomerTypeName from T_B_CUSTOMERINFO where EmployeeID=? and StationID=? and " +
                    "Address like  '%"+Address+"%' ";
            Log.e("sql",sql);
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
           while(query.moveToNext()){
                UserXJInfo userXJInfo=new UserXJInfo();
                userXJInfo.setSaleid(query.getString(0));
                userXJInfo.setUserid(query.getString(1));
                userXJInfo.setUsername(query.getString(2));
                userXJInfo.setTelephone(query.getString(3));
                userXJInfo.setDeliveraddress(query.getString(4));
                userXJInfo.setCustomerTypeName(query.getString(5));
                userXJInfos.add(userXJInfo);
            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
        return userXJInfos;
    }
    public List<UserXJInfo> getInfoByTelephone(String EmployeeID,String StationID,String Telephone){
        List<UserXJInfo> userXJInfos=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID,CustomerName,Telephone,Address,CustomerTypeName from T_B_CUSTOMERINFO where EmployeeID=? and StationID=? and" +
                    " Telephone like  '%"+Telephone+"%'";
            Log.e("sql",sql);
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                UserXJInfo userXJInfo=new UserXJInfo();
                userXJInfo.setSaleid(query.getString(0));
                userXJInfo.setUserid(query.getString(1));
                userXJInfo.setUsername(query.getString(2));
                userXJInfo.setTelephone(query.getString(3));
                userXJInfo.setDeliveraddress(query.getString(4));
                userXJInfo.setCustomerTypeName(query.getString(5));
                userXJInfos.add(userXJInfo);
            }
        }catch(Exception ex){

        }
        return userXJInfos;
    }
    public List<UserXJInfo> getInfoByUserName(String EmployeeID,String StationID,String userName){
        List<UserXJInfo> userXJInfos=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID,CustomerName,Telephone,Address,CustomerTypeName from T_B_CUSTOMERINFO where EmployeeID=? and StationID=? and" +
                    " CustomerName  like  '%"+userName+"%'";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while (query.moveToNext()){
                UserXJInfo userXJInfo=new UserXJInfo();
                userXJInfo.setSaleid(query.getString(0));
                userXJInfo.setUserid(query.getString(1));
                userXJInfo.setUsername(query.getString(2));
                userXJInfo.setTelephone(query.getString(3));
                userXJInfo.setDeliveraddress(query.getString(4));
                userXJInfo.setCustomerTypeName(query.getString(5));
                userXJInfos.add(userXJInfo);
            }
        }catch(Exception ex){

        }
        return userXJInfos;
    }

    /***
     * 统计已经完成配送的订单数量
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Sale> SaleFinishCount(String EmployeeID,String StationID){
        List<Sale> sales=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select SaleID,UserID from T_B_SALE where EmployeeID=? and StationID=? and (IState='4' or IState='5')";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                Sale sale=new Sale();
                sale.setSaleID(query.getString(0));
                sale.setCustomerID(query.getString(1));
                sales.add(sale);
            }
        }catch(Exception  ex){

        }
        return sales;
    }
    /***
     * 统计已经完成整改订单数量
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Sale> RectifyFinishCount(String EmployeeID,String StationID){
        List<Sale> sales=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select UserID from T_B_UserRectifyInfo where EmployeeID=? and StationID=? and (IsInspected='1' or IsInspected='2')";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                Sale sale=new Sale();
                sale.setCustomerID(query.getString(0));
                sales.add(sale);
            }
        }catch(Exception  ex){

        }
        return sales;
    }
    /***
     * 统计已经完成安检订单数量
     * @param EmployeeID
     * @param StationID
     * @return
     */
    public List<Sale> SearchFinishCount(String EmployeeID,String StationID){
        List<Sale> sales=new ArrayList<>();
        try{
            String[] selectionArgs = new String[] { EmployeeID, StationID };
            String sql=" select UserID from T_B_AJINFO where EmployeeID=? and StationID=? and (IsInspected='1' or IsInspected='2')";
            Cursor query = mDatabase.rawQuery(sql, selectionArgs);
            while(query.moveToNext()){
                Sale sale=new Sale();
                sale.setCustomerID(query.getString(0));
                sales.add(sale);
            }
        }catch(Exception  ex){

        }
        return sales;
    }
}
