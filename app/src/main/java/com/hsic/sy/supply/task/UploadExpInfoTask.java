package com.hsic.sy.supply.task;


import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.picture.UpLoadPicture;
import com.hsic.sy.supply.listener.UploadExpInfoListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UploadExpInfoTask extends AsyncTask<String , Void, HsicMessage>{
	private Context context;
	private ProgressDialog dialog;
	WebServiceCallHelper wb;
	UploadExpInfoListener l;
	GetBasicInfo getBasicInfo;//20181019
	public UploadExpInfoTask(Context context,UploadExpInfoListener l) {
		this.context = context;
		getBasicInfo=new GetBasicInfo(context);
		dialog = new ProgressDialog(context);
		wb=new WebServiceCallHelper(context);
		this.l=l;

	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("正在上传记录");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		HsicMessage hsicMess=new HsicMessage();
		try{
			hsicMess=wb.uploadExpInfo(params[0], params[1]);
			UpLoadPicture upPicture=new UpLoadPicture();
			upPicture.UpEcpPicture(context,  params[0]);//上传灶位勘探照片
		}catch(Exception ex){
			ex.toString();
			hsicMess.setRespCode(1);
			hsicMess.setRespMsg(ex.toString());
		}
		return hsicMess;
	}

	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.dismiss();
		l.UploadExpInfoListenerEnd(result);
	}

}
