package com.hsic.sy.supply.task;


import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.GetExpListListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetExpListTask extends AsyncTask<String, Void, HsicMessage>{
	private Context context;
	private ProgressDialog dialog;
	GetExpListListener l;
	WebServiceCallHelper wb;
	GetBasicInfo getBasicInfo;//20181019
	public GetExpListTask(Context context,GetExpListListener l){
		this.context = context;
		dialog = new ProgressDialog(context);
		getBasicInfo=new GetBasicInfo(context);
		this.l=l;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("加载信息...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.GetExpList( getBasicInfo.getDeviceID(), arg0[1]);
//		
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.GetExpListListenerEnd(result);
	}

}
