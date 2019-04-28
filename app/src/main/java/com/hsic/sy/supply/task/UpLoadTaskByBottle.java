package com.hsic.sy.supply.task;

import java.util.ArrayList;
import java.util.List;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.UploadListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UpLoadTaskByBottle extends AsyncTask<String , Void, HsicMessage> {
	private Context context;
	private ProgressDialog dialog;
	WebServiceCallHelper wb;
	UploadListener l;
	List<String> remove;
	int j=0,m=0;//已上传成功的标识（总上传成功数）
	public UpLoadTaskByBottle(Context context,UploadListener l,ProgressDialog dialog) {
		this.context = context;
		wb=new WebServiceCallHelper(context);
		this.l=l;
		this.dialog=dialog;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("正在上传记录");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		HsicMessage hsicMess=new HsicMessage();
		String Ret="";
		try{
			hsicMess=wb.UpLoadData( params[0]);
			if(hsicMess.getRespCode()==0){
				//服务调用成功
				if(hsicMess.getRespMsg().equals("0")){
					hsicMess.setRespCode(0);
					hsicMess.setRespMsg(params[0]);
				}
				if(hsicMess.getRespMsg().equals("1")){
					//扫描日期不对
					hsicMess.setRespCode(1);
					hsicMess.setRespMsg(params[0]);
				}
				if(hsicMess.getRespMsg().equals("2")){
					//重复扫描
					hsicMess.setRespCode(2);
					hsicMess.setRespMsg(params[0]);
				}
				if(hsicMess.getRespMsg().equals("3")){
					//参数不正确
					hsicMess.setRespCode(3);
					hsicMess.setRespMsg(params[0]);
				}
				if(hsicMess.getRespMsg().equals("4")){
					//网络异常
					hsicMess.setRespCode(4);
					hsicMess.setRespMsg(params[0]);
				}
			}else{
				//服务调用失败
				hsicMess.setRespCode(5);
				hsicMess.setRespMsg(params[0]);
			}
		}catch(Exception ex){
			hsicMess.setRespCode(4);
			hsicMess.setRespMsg( params[0]);
		}
		// TODO Auto-generated method stub
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
//		dialog.setCancelable(true);
//		dialog.dismiss();
		l.UploadListenerEnd(result);
	}
}
