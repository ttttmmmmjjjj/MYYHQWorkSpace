package com.hsic.sy.supply.task;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.GetTruckAllRecordsListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * ��ȡĳһ��ʱ�������г�����Ϣ
 * @author Administrator
 *
 */
public class GetTruckAllRecordsTask extends AsyncTask<String, Void, HsicMessage>{
	private Context context;
	private ProgressDialog dialog;
	GetTruckAllRecordsListener l;
	WebServiceCallHelper wb;
	GetBasicInfo getBasicInfo;//20181019
	public GetTruckAllRecordsTask(Context context,GetTruckAllRecordsListener l){
		this.context = context;
		dialog = new ProgressDialog(context);
		getBasicInfo=new GetBasicInfo(context);
		this.l=l;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("正在下载车次信息...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.GetTruckAllRecords(getBasicInfo.getDeviceID(), params[1]);
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.GetTruckAllRecordsListenerEnd(result);
	}

}
