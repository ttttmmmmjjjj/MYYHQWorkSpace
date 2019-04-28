package com.hsic.sy.supply.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.adapter.UserListAdapter;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.bll.MyLog;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.search.bean.ApplicationHelper;
import com.hsic.sy.search.bean.UserRectifyInfo;
import com.hsic.sy.search.db.InsertData;
import com.hsic.sy.search.db.TB_UserRectifyInfo;
import com.hsic.sy.search.listener.UploadTaskListener;
import com.hsic.sy.search.web.WebServiceCallHelper;
import com.hsic.sy.supplystationmanager.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchLoginOk extends Activity implements OnClickListener,
		UploadTaskListener, com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
	UserListAdapter userlist;
	TextView  RectifyName, deviceId, taskConts, finish;
	TB_UserRectifyInfo userRectifyInfo_TB;
	String RectifyManID = "", userid = "", empid = "", devideID = "";
	ApplicationHelper applicationHelper;
	UserRectifyInfo userRectifyInfo;
	Button  warning;
	GetBasicInfo getBasicInfo;// 20181019
	boolean isTurn = false;
	private ListView list;
	private TextView SearchDate, StationName, OperationName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_loginok);
		list = (ListView) this.findViewById(R.id.listView1);
		StationName = (TextView) this.findViewById(R.id.stationName);
		OperationName = (TextView) this.findViewById(R.id.operationName);
//		RectifyName = (TextView) this.findViewById(R.id.stationName);
		taskConts = (TextView) this.findViewById(R.id.taskCounts);
		finish = (TextView) this.findViewById(R.id.finish);
		userRectifyInfo_TB = new TB_UserRectifyInfo(SearchLoginOk.this);
		applicationHelper = (ApplicationHelper) getApplication();
		userRectifyInfo = new UserRectifyInfo();
		getBasicInfo = new GetBasicInfo(SearchLoginOk.this);
		devideID = getBasicInfo.getDeviceID();// 设备编号
		StationName.setText("");
		StationName.setText(getBasicInfo.getStationName());
//		Log.e("===",""+getBasicInfo.getStationName());
		RectifyManID = getBasicInfo.getRectifyMan();

		empid=RectifyManID;
		userRectifyInfo.setRectifyMan(RectifyManID);// 员工号
		applicationHelper.setUseRectifyInfo(userRectifyInfo);
		DownLoadRectityTask downLoad = new DownLoadRectityTask(getContext(),
				devideID, RectifyManID);
		downLoad.execute();

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				TextView userId = (TextView) arg1.findViewById(R.id.userid);
				TextView username = (TextView) arg1.findViewById(R.id.username);
				TextView add = (TextView) arg1.findViewById(R.id.address);
				String userID = "",name = "",address = "";
				userID = userId.getText().toString();
				name = username.getText().toString();
				address = add.getText().toString();
				userid = userID;
				// 以saleid和userid为查询条件，查询出所有的订单用户信息
				try {
					String detail = "";
					detail += "送气编号" + userID + "\n";
					detail += "用户:" + name + "\n";
					detail += "地址:" + address + "\n";
					shownInfo(detail);
				} catch (Exception ex) {
					MyLog.e("3", ex.toString());
				}

			}

		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		String RectifyMan = "";
		RectifyMan = getBasicInfo.getRectifyMan();
		if (!RectifyMan.equals("")) {
			innitData();
			innitList();
		}
	}

	/**
	 * 初始化数据
	 */
	String RectifyManName = "";
	private void innitData() {
		RectifyManName = userRectifyInfo_TB.getRectifyMan(empid);
//		RectifyName.setText(RectifyManName);

	}

	/**
	 * 初始化任务List
	 */
	private void innitList() {
		List<Map<String, String>> rectifyList = new ArrayList<Map<String, String>>();
		rectifyList = userRectifyInfo_TB.GetRectifyTaskCounts(empid);
		List<Map<String, String>> rectifyFinishList = new ArrayList<Map<String, String>>();
		rectifyFinishList = userRectifyInfo_TB.GetRectifyFinishCounts(empid);
		List<Map<String, String>> uploadList = new ArrayList<Map<String, String>>();
		uploadList = userRectifyInfo_TB.GetRectifyUploadCounts(empid);
		int rectifySize = rectifyList.size();
		int rectifyFinishSize = rectifyFinishList.size();
		int upload = uploadList.size();
		OperationName.setText(RectifyManName);
		taskConts.setText("总任务数:"+String.valueOf(rectifySize));// 总任务数
		finish.setText("已完成/上传:"+String.valueOf(rectifyFinishSize)+"/"+String.valueOf(upload));// 已巡检
		List<Map<String, String>> rectifyInfo = new ArrayList<Map<String, String>>();
		rectifyInfo = userRectifyInfo_TB.GetRectifyBaseInfo(empid);
		userlist = new UserListAdapter(this, rectifyInfo);
		userlist.notifyDataSetChanged();
		list.setAdapter(userlist);
	}

	private Context getContext() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//			case R.id.button1:
//				 String counts = "";
//				 counts = taskConts.getText().toString();
//				 if(!counts.equals("")){
//				 List<Map<String, String>> rectifyList = new ArrayList<Map<String,
//				 String>>();
//				 rectifyList = userRectifyInfo_TB.GetRectifyBaseInfo(empid);
//				 if (rectifyList.size() > 0) {
//				 Intent i=new Intent(this,SerachActivity.class);
//				 startActivity(i);
//				 } else {
//				 Toast.makeText(getBaseContext(), "无任务可做", Toast.LENGTH_LONG)
//				 .show();
//				 }
//				 }else{
//				 Toast.makeText(getBaseContext(), "无任务可做", Toast.LENGTH_LONG)
//				 .show();
//				 }

//				break;
			default:
				break;
		}
	}

	@Override
	public void UploadTaskListenerEnd(Boolean num) {
		// TODO Auto-generated method stub
		if (num) {
			innitData();
			innitList();
		}
	}

	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub
		// this.finish();
	}

	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub
		if (isTurn) {
			isTurn = false;
			Intent i = new Intent(SearchLoginOk.this, SerachActivity.class);
			i.putExtra("userid", userid);
			i.putExtra("RectifyMan", RectifyManID);
			userRectifyInfo.setUserid(userid);
			applicationHelper.setUseRectifyInfo(userRectifyInfo);
			startActivityForResult(i, 1);
		} else {
			this.finish();
		}

	}

	private void shownInfo(String detail) {
		isTurn = true;
		new AlertView("用户详细信息", detail, null, new String[] { "整改" }, null,
				getContext(), AlertView.Style.Alert, SearchLoginOk.this).show();
	}

	public class DownLoadRectityTask extends
			AsyncTask<Void, Integer, HsicMessage> {
		String DeviceID = "", empid = "";
		private Context context;
		private ProgressDialog dialog;

		public DownLoadRectityTask(Context context, String DeviceID,
								   String empid) {
			this.context = context;
			dialog = new ProgressDialog(context);
			this.DeviceID = DeviceID;
			this.empid = empid;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("正在下载数据.......");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected HsicMessage doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int ret = 0;
			HsicMessage mess = new HsicMessage();
			WebServiceCallHelper wsHelper = new WebServiceCallHelper(context);
			mess = wsHelper.DownLoadInfo(DeviceID, empid);

			try {
				if (mess.getRespCode() == 0) {
					 MyLog.e("下载数据结果",
					 JSONUtils.toJsonWithGson(mess.getRespMsg()));
					Type typeOfT = new TypeToken<List<UserRectifyInfo>>() {
					}.getType();
					List<UserRectifyInfo> UserRectifyInfo_LIST = new ArrayList<UserRectifyInfo>();
					UserRectifyInfo_LIST = JSONUtils.toListWithGson(
							mess.getRespMsg(), typeOfT);
					// 此处做数据整理,哪些该插入本地,哪些本地数据该做删除,一旦没有给我任何数据,删除本地未完成的整改用户数据
					String userID = "";
					if (UserRectifyInfo_LIST.size() > 0) {
						for (int n = 0; n < UserRectifyInfo_LIST.size(); n++) {
							userID += UserRectifyInfo_LIST.get(n).getUserid()
									+ ",";
						}
					}

					InsertData insert = new InsertData(context);
					insert.IsertIntoDB(UserRectifyInfo_LIST);
					// 反向遍历
					List<String> list = new ArrayList<String>();
					list = userRectifyInfo_TB.getUserAllID(empid);// 本地数据中所有的userID
					// MyLog.e("本地", JSONUtils.toJsonWithGson(list));
					String userid = "";
					userid = userID.substring(0, userID.length() - 1);
					if (list.size() > 0) {
						for (int h = 0; h < list.size(); h++) {
							// MyLog.e("后台", userid);
							if (!userid.contains(list.get(h))) {
								userRectifyInfo_TB.deleteUser(empid,
										list.get(h));
							}
						}
					}

				}
				if (mess.getRespCode() == 1) {
					if (mess.getRespMsg().equals("未找到整改单")) {
						// 此处删除本地未完成的订单数据
						// String userid="";
						// userid=userRectifyInfo_TB.getUserID(empid);
						// if(!userid.equals("")){
						// userRectifyInfo_TB.deleteUser( empid,userid);
						// }
					} else {
						MyLog.e("下载失败", "调用webservice失败");
					}

				}

			} catch (Exception ex) {
				ex.printStackTrace();
				MyLog.e("信息同步 下载出现异常", ex.toString());
				return mess;

			}
			return mess;
		}

		@Override
		protected void onPostExecute(HsicMessage result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.setCancelable(true);
			dialog.dismiss();
			int resultCode = result.getRespCode();
			switch (resultCode) {
				case 0:
					// 更新UI界面
					innitData();
					innitList();
					break;
				case 1:
					new AlertView("提示", result.getRespMsg(), null,
							new String[] { "确定" }, null, getContext(),
							AlertView.Style.Alert, SearchLoginOk.this).show();
					break;
				case 3:
					Toast.makeText(getContext(), result.getRespMsg(),
							Toast.LENGTH_LONG).show();
					break;
				case 4:
					Toast.makeText(getContext(), result.getRespMsg(),
							Toast.LENGTH_LONG).show();
					break;
				case 5:
					Toast.makeText(getContext(), result.getRespMsg(),
							Toast.LENGTH_LONG).show();
					break;
				case 6:
					Toast.makeText(getContext(), result.getRespMsg(),
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
			}

		}
	}
	// 是否退出程序
	private static Boolean isExit = false;
	// 定时触发器
	private static Timer tExit = null;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}
