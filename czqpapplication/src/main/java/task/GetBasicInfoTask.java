package task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import bean.HsicMessage;
import listener.GetBasicInfoTaskListener;
import webservice.NetWorkHelper;
import webservice.WebServiceClient;
import webservice.WebServiceHelper;

public class GetBasicInfoTask extends AsyncTask<String, Void, HsicMessage> {
	private Context context;
	private ProgressDialog dialog;
	GetBasicInfoTaskListener l;
	WebServiceHelper wb;
	WebServiceClient client;
	private String DWCode;
	private String UserRegioncode;

	public GetBasicInfoTask(Context context, GetBasicInfoTaskListener l,
			String DWCode, String UserRegioncode) {
		this.l = l;
		this.context = context;
		dialog = new ProgressDialog(context);
		this.DWCode = DWCode;
		this.UserRegioncode = UserRegioncode;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("���ڼ�����ƿ������Ϣ...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		HsicMessage hsicMess = new HsicMessage();
		NetWorkHelper mHelper = new NetWorkHelper(context);// �ж������Ƿ����
		try {
			if (!mHelper.checkNetworkStatus()) {
				hsicMess.setRespMsg("���鱾������");
				hsicMess.setRespCode(-2);
				return hsicMess;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			hsicMess.setRespMsg("������ʧ��");
			hsicMess.setRespCode(-3);
			return hsicMess;
		}
		wb = new WebServiceHelper(context);
		hsicMess = wb.GetBasicInfo(params[0]);
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		l.GetBasinInfoTaskListenerEnd(result);
	}

}
