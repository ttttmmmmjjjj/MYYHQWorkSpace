package com.hsic.sy.supply.task;


import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.InsertTruckSaleRelationListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * ���۵��󶨳���
 * @author tmj
 *
 */
public class InsertTruckSaleRelationTask extends AsyncTask<String, Void, HsicMessage>{
	private Context context;
	private ProgressDialog dialog;
	InsertTruckSaleRelationListener l;
	WebServiceCallHelper wb;
	GetBasicInfo getBasicInfo;//20181019
	public InsertTruckSaleRelationTask(Context context,InsertTruckSaleRelationListener l){
		this.context = context;
		getBasicInfo=new GetBasicInfo(context);
		dialog = new ProgressDialog(context);
		this.l=l;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("销售单绑定车次中...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.InsertTruckSaleRelation(getBasicInfo.getDeviceID(), params[1]);
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.InsertTruckSaleRelationListenerEnd(result);
	}

}
