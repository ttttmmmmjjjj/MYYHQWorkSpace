package com.hsic.sy.supply.task;
import android.app.ProgressDialog;
import android.content.Context;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.GetTruckNoRecordsListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import android.os.AsyncTask;
/**
 * վ�����Ӷ���������ѯ
 * @author tmj
 *
 */
public class GetTruckNoRecordsTask extends AsyncTask<String, Void, HsicMessage>{
	private Context context;
	private ProgressDialog dialog;
	GetTruckNoRecordsListener l;
	WebServiceCallHelper wb;
	GetBasicInfo getBasicInfo;//20181019
	public GetTruckNoRecordsTask(Context context,GetTruckNoRecordsListener l){
		this.context = context;
		getBasicInfo=new GetBasicInfo(context);
		dialog = new ProgressDialog(context);
		this.l=l;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("正在加载订单信息...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.GetTruckNoRecords(getBasicInfo.getDeviceID(), params[1]);
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.GetTruckNoRecordsListenerEnd(result);
	}

}
