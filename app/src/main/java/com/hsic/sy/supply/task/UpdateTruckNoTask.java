package com.hsic.sy.supply.task;


import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.UpdateTruckNoListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UpdateTruckNoTask extends AsyncTask<String, Void, HsicMessage>{
	private Context context;
	private ProgressDialog dialog;
	WebServiceCallHelper wb;
	UpdateTruckNoListener l;
	GetBasicInfo getBasicInfo;//20181019
	public UpdateTruckNoTask(Context context,UpdateTruckNoListener l){
		this.context = context;
		getBasicInfo=new GetBasicInfo(context);
		dialog = new ProgressDialog(context);
		this.l=l;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("操作进行中...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.UpdateTruckNo(getBasicInfo.getDeviceID(), params[1]);
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.UpdateTruckNoListenerEnd(result);
	}

}
