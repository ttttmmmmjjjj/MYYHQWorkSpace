package com.hsic.sy.supply.task;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.StationLoginListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class StationLoginTask extends AsyncTask<String, Void, HsicMessage>{
	StationLoginListener l;
	private Context context;
	private ProgressDialog dialog;
	WebServiceCallHelper wb;
	GetBasicInfo getBasicInfo;//20181019
	public StationLoginTask(Context context, StationLoginListener l){
		this.context = context;
		dialog = new ProgressDialog(context);
		this.l=l;
		getBasicInfo=new GetBasicInfo(context);

	}
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("登录中...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.EmployeeLogin(getBasicInfo.getDeviceID(), arg0[0]);
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.StationLoginListenerEnd(result);
	}
}
