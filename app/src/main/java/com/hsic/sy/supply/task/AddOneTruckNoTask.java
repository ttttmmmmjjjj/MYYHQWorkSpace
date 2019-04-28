package com.hsic.sy.supply.task;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.AddOneTruckNoListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;

/**
 * ��������
 * @author tmj
 *
 */
public class AddOneTruckNoTask extends AsyncTask<String, Void, HsicMessage>{
	private Context context;
	private ProgressDialog dialog;
	AddOneTruckNoListener l;
	WebServiceCallHelper wb;
	GetBasicInfo getBasicInfo;//20181019
	public AddOneTruckNoTask(Context context,AddOneTruckNoListener l){
		this.context = context;
		dialog = new ProgressDialog(context);
		this.l=l;
		getBasicInfo=new GetBasicInfo(context);
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("新增车次中...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.AddOneTruckNo(getBasicInfo.getDeviceID(), arg0[0]);
//		Log.e("==", JSONUtils.toJsonWithGson(hsicMess));
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.AddOneTruckNoListenerEnd(result);
	}

}
