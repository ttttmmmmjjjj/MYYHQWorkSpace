package task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import bean.HsicMessage;
import listener.GetQPSInfoTaskListener;
import webservice.WebServiceClient;
import webservice.WebServiceHelper;

public class GetQPSInfoTask extends AsyncTask<String, Void, HsicMessage> {
	private Context context;
	private ProgressDialog dialog;
	GetQPSInfoTaskListener l;
	WebServiceHelper wb;
	private String DWCode;
	private String UserRegioncode;
	 WebServiceClient client;
	 boolean ret=false;
	public GetQPSInfoTask(Context context,GetQPSInfoTaskListener l,String DWCode, String UserRegioncode){
		this.l=l;
		this.context=context;
		dialog = new ProgressDialog(context);
		this.DWCode = DWCode;
		this.UserRegioncode = UserRegioncode;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("���ڼ�����ƿ��ת��Ϣ...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		 wb=new WebServiceHelper(context);
		 HsicMessage hsicMess=new HsicMessage();	
		 if (DWCode.equals("311414")) {
				String url = "QP=" + UserRegioncode + "&" + "CQDW=" + DWCode;
				hsicMess=client.doPost(url, "");
				ret=true;
		 }else{
			 hsicMess=wb.GetSaleInfo(params[0], params[1]);
		 }
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.GetQPSInfoTaskListenerEnd(result,ret);
	}

}
