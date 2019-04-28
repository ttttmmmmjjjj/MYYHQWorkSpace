package com.hsic.sy.supply.task;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.SearchAssignSaleListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;

/**
 * վ���¿ɷ������۵���Ϣ
 * @author tmj
 *
 */
public class SearchAssignSaleTask extends AsyncTask<String, Void, HsicMessage> {
	private Context context;
	private ProgressDialog dialog;
	private SearchAssignSaleListener l;
	private WebServiceCallHelper wb;
	GetBasicInfo getBasicInfo;//20181019
	public SearchAssignSaleTask(Context context,SearchAssignSaleListener l){
		this.context = context;
		getBasicInfo=new GetBasicInfo(context);
		dialog = new ProgressDialog(context);
		this.l=l;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("正在下载订单...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		wb=new WebServiceCallHelper(context);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess=wb.SearchAssignSale(getBasicInfo.getDeviceID(), params[1]);
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.SearchAssignSaleListenerEnd(result);
	}

}
