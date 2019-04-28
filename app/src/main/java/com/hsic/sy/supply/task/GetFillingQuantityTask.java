package com.hsic.sy.supply.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hsic.sy.bean.FillMessage;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.GetFillingQuantityListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import com.hsic.sy.supply.web.WebServiceClient;

public class GetFillingQuantityTask extends AsyncTask<String, Void, FillMessage> {
	GetFillingQuantityListener l;
	private Context context;
	private ProgressDialog dialog;
	WebServiceClient wb;
	GetBasicInfo getBasicInfo;//20181019
	public GetFillingQuantityTask (Context context,GetFillingQuantityListener l){
		this.context = context;
		dialog = new ProgressDialog(context);
		this.l=l;
		getBasicInfo=new GetBasicInfo(context);

	}
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("查询统计中...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected FillMessage doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		FillMessage hsicMess=new FillMessage();
		hsicMess=wb.doPost("");
		return hsicMess;
	}

	@Override
	protected void onPostExecute(FillMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.GetFillingQuantityListenerEnd(result);
	}
}
