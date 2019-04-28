package com.hsic.sy.search.task;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.picture.UpLoadPicture;
import com.hsic.sy.search.db.UpLoad;
import com.hsic.sy.search.listener.UploadTaskListener;

public class UpLoadTask extends AsyncTask<String, Void, HsicMessage>{
	private Context context;
	ProgressDialog progressDialog;
	private UploadTaskListener listener;
	public UpLoadTask(Context context,UploadTaskListener listener){
		this.context=context;
		progressDialog = new ProgressDialog(context);
		this.listener=listener;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("正在上传");
		progressDialog.show();
	}


	@Override
	protected HsicMessage doInBackground(String... params) {
		// TODO Auto-generated method stub
		int ret1 = 0;
		int ret2 = 0;
		int flag = 0;// 更新UI界面所需的返回结果
		HsicMessage pic=new HsicMessage();
		if (!isNetworkAvailable(context)) {
			flag = 10;// 网络异常
		} else {
			UpLoad upLoad=new UpLoad(context);
			HsicMessage upInspection=upLoad.UserRectifyInfo(context, params[0], params[1], params[2]);
			Log.e("upInspection", JSONUtils.toJsonWithGson(upInspection));
			if(upInspection.getRespCode()==0){
				flag=4;
				pic.setRespCode(4);
			}
			UpLoadPicture upPicture=new UpLoadPicture();
			pic=upPicture.upPicture(context,  params[2]);
		}
		return pic;
	}
	@Override
	protected void onPostExecute(HsicMessage result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progressDialog.dismiss();
		if(result.getRespCode()==4){
			Toast.makeText(context, "本次订单上传成功", Toast.LENGTH_SHORT)
					.show();
		}
		if(listener!=null){
			listener.UploadTaskListenerEnd(true);
		}
	}

	private boolean isNetworkAvailable(Context con) {
		ConnectivityManager cm = (ConnectivityManager) con
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		if (netinfo.isConnected()) {
			return true;
		}
		return false;
	}
}
