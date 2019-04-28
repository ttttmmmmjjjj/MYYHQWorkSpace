package com.hsic.sy.supply.task;


import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.db.UserRegionCodeDB;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UpLoadUserRegionByDB extends AsyncTask<String, Void, HsicMessage>{
	private Context context;
	private ProgressDialog dialog;
	UserRegionCodeDB UserRegionCode_TB;
	WebServiceCallHelper wb;
	String data,stationID,OperationTag;
	public UpLoadUserRegionByDB(Context context,ProgressDialog dialog){
		this.context = context;
		this.dialog=dialog;
		UserRegionCode_TB=new UserRegionCodeDB(context);
		wb=new WebServiceCallHelper(context);
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("正在上传信息...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		HsicMessage hsicMess=new HsicMessage();
		data=arg0[0];
		OperationTag=arg0[1];//未成功上传数据标识
		stationID=arg0[2];
		try{
			hsicMess=wb.UpLoadData(data);
		}catch(Exception ex){

		}
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		if(result.getRespCode()==0){
			//删除
			UserRegionCode_TB.DeleteDB(data, stationID,GetDate.getToday(),OperationTag);
			UserRegionCode_TB.InserDB(data,stationID, GetDate.getToday(),"0");
		}else if(result.getRespCode()==1){
			//删除
			UserRegionCode_TB.DeleteDB(data, stationID,GetDate.getToday(),OperationTag);
			UserRegionCode_TB.InserDB(data, stationID, GetDate.getToday(),"1");
		}else if(result.getRespCode()==2){
			//删除
			UserRegionCode_TB.DeleteDB(data, stationID,GetDate.getToday(),OperationTag);
			UserRegionCode_TB.InserDB(data, stationID, GetDate.getToday(),"2");
		}else if(result.getRespCode()==3){
			//删除
			UserRegionCode_TB.DeleteDB(data, stationID,GetDate.getToday(),OperationTag);
			UserRegionCode_TB.InserDB(data,stationID, GetDate.getToday(),"3");
		}else if(result.getRespCode()==4){
			//删除
			UserRegionCode_TB.DeleteDB(data, stationID,GetDate.getToday(),OperationTag);
			UserRegionCode_TB.InserDB(data, stationID, GetDate.getToday(),"4");
		}else if(result.getRespCode()==5){
			//删除
			UserRegionCode_TB.DeleteDB(data, stationID,GetDate.getToday(),OperationTag);
			UserRegionCode_TB.InserDB(data, stationID, GetDate.getToday(),"4");
		}else{

		}
	}
}
