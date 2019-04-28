package com.hsic.sy.supply.task;


import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.PostDataListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class PostDataTask extends AsyncTask<String, Void, HsicMessage> {
	private Context context;
	private ProgressDialog dialog;
	WebServiceCallHelper wb;
	PostDataListener l;

	public PostDataTask(Context context, PostDataListener l) {
		this.context = context;
		dialog = new ProgressDialog(context);
		this.l = l;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("上传数据中...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.UpLoadData(params[0]);
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.PostDataListenerListenerEnd(result);
	}

}
