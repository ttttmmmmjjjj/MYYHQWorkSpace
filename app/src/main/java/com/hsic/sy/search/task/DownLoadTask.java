package com.hsic.sy.search.task;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.MyLog;
import com.hsic.sy.search.bean.UserRectifyInfo;
import com.hsic.sy.search.db.InsertData;
import com.hsic.sy.search.db.TB_UserRectifyInfo;
import com.hsic.sy.search.listener.DownLoadTaskListener;
import com.hsic.sy.search.web.WebServiceCallHelper;

public class DownLoadTask extends AsyncTask<Void, Void, Integer> {
	private Context context;
	private ProgressDialog dialog;
	String DeviceID="",empid="";
	private DownLoadTaskListener listener;
	TB_UserRectifyInfo userRectifyInfo_TB;

	public DownLoadTask(Context context,DownLoadTaskListener l,String DeviceID,String empid) {
		this.context = context;
		dialog = new ProgressDialog(context);
		this.DeviceID=DeviceID;
		this.empid=empid;
		listener=l;
		userRectifyInfo_TB=new TB_UserRectifyInfo(context);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("正在下载信息");
		dialog.setCancelable(false);
		dialog.show();
	}


	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.setCancelable(true);
		dialog.dismiss();
		if (result==1) {
			if(listener!=null){
				listener.DownLoadTaskListenerEnd(true);
			}
			Toast.makeText(context, "信息下载同步完成", Toast.LENGTH_LONG).show();
		} else  if(result==-1){
			Toast.makeText(context, "信息下载同步失败,网络异常", Toast.LENGTH_LONG)
					.show();
		}else if(result==-2){
			Toast.makeText(context, "信息下载同步失败,服务调用失败", Toast.LENGTH_LONG)
					.show();
		}else if(result==-3){
			Toast.makeText(context, "信息下载同步失败,服务调用异常", Toast.LENGTH_LONG)
					.show();
		}else if(result==-4){
			Toast.makeText(context, "调用webservice失败", Toast.LENGTH_LONG)
					.show();
		}else if(result==-5){
			Toast.makeText(context, "信息同步下载出现异常", Toast.LENGTH_LONG)
					.show();
		}else if(result==-6){
			Toast.makeText(context, "未找到整改单!", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		int ret=0;
		HsicMessage mess = new HsicMessage();
		WebServiceCallHelper wsHelper = new WebServiceCallHelper(context);
		mess = wsHelper.DownLoadInfo(DeviceID, empid);
		MyLog.e("下载数据结果", JSONUtils.toJsonWithGson(mess.getRespMsg()));
		try {
			if (mess.getRespCode() == 3 || mess.getRespCode() == 4) {
				MyLog.e("信息同步 下载出现异常", "网络异常");
				ret=-1;
				return ret;
			}
			if (mess.getRespCode() == 5) {
				MyLog.e("信息同步 下载出现异常", "服务调用失败");
				ret=-2;
				return ret;
			}
			if (mess.getRespCode() == 6) {
				MyLog.e("信息同步 下载出现异常", "服务调用异常");
				ret=-3;
				return ret;
			}
			if (mess.getRespCode() == 0) {
				Type typeOfT = new TypeToken<List<UserRectifyInfo>>(){}.getType();
				List<UserRectifyInfo> UserRectifyInfo_LIST=new ArrayList<UserRectifyInfo>();
				UserRectifyInfo_LIST = JSONUtils.toListWithGson(mess.getRespMsg(), typeOfT);
				String userID="";
				if(UserRectifyInfo_LIST.size()>0){
					for(int n=0;n<UserRectifyInfo_LIST.size();n++){
						userID+=UserRectifyInfo_LIST.get(n).getUserid()+",";
					}
				}

				InsertData insert=new InsertData(context);
				insert.IsertIntoDB(UserRectifyInfo_LIST);
				//反向遍历
				List<String> list=new ArrayList<String>();
				list=userRectifyInfo_TB.getUserAllID(empid);
				String userid="";
				userid=userID.substring(0, userID.length() - 1);
				if(list.size()>0){
					for(int h=0;h<list.size();h++){
						if (!userid.contains(list.get(h))) {
							userRectifyInfo_TB.deleteUser( empid,list.get(h));
						}
					}
				}
				ret=1;
				return ret;
			}
			if(mess.getRespCode() == 1){
				if(mess.getRespMsg().equals("未找到整改单")){
					ret=-6;
				}else{
					MyLog.e("下载失败", "调用webservice失败");
					ret=-4;
				}

				return ret;
			}


		} catch (Exception ex) {
			ex.printStackTrace();
			MyLog.e("信息同步 下载出现异常", ex.toString());
			ret=-5;
			return ret;

		}
		return ret;
	}
}