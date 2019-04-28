package com.hsic.sy.supply.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.hsic.sy.baidulocation.BDLocationUtils;
import com.hsic.sy.baidulocation.Const;
import com.hsic.sy.bean.Employee;
import com.hsic.sy.bll.ActivityUtil;
import com.hsic.sy.bll.DeviceIDInfo;
import com.hsic.sy.bll.SaveBasicInfo;
import com.hsic.sy.constant.Constant;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.nfc.GetTag;
import com.hsic.sy.search.bean.ApplicationHelper;
import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.version.VersionAsyncTask;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/***
 * �Ż������豸������Ϣ
 */
public class LoginActivity extends Activity implements OnClickListener, com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener
{
	private Button Login_In;
	String PhoneModel = "";// �豸�ͺ�
	private LinearLayout Setting;
	NfcAdapter mAdapter;
	private static PendingIntent mPendingIntent;
	private static IntentFilter[] mFilters;
	private static String[][] mTechLists;
	ApplicationHelper applicationHelper;
	Employee employee;
	SaveBasicInfo savaBasicInfo;//
	Build bd = new Build();
	private String version;
	SharedPreferences mSharedPreferences;
	String add="";
	private TextView txt_version;
	BDLocationUtils bdLocationUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_readstuff);
		applicationHelper = (ApplicationHelper) getApplication();
		employee = new Employee();
		savaBasicInfo = new SaveBasicInfo(LoginActivity.this);
		mSharedPreferences = getSharedPreferences("DeviceSetting", 0);
		innit();
		PhoneModel = Build.MODEL;
		setNFC();
		setDeviceID();
		add = mSharedPreferences.getString("BlueToothAdd", "");
		if (add != null && add.equals("")) {
			TurnSetting();
		}
		checkVersion();//检查版本是否需要更新
//		if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
//			if(ActivityCompat.lacksPermission(LoginActivity.this,ActivityCompat.permissionsREAD)){
//
//			}
//		}

	}

	private void innit() {
		Login_In = (Button) this.findViewById(R.id.login_in);
		Login_In.setOnClickListener(this);
		Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
        toolbar.inflateMenu(R.menu.login_toolbar_menu);
        toolbar.setTitle("登录");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if(menuItemId==R.id.action_settings){
                   TurnSetting();
                }
                return false;
            }
        });
		txt_version=this.findViewById(R.id.txt_version);
		String version=getLocalVersionName(LoginActivity.this);
		txt_version.setText("应用版本："+version);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			mAdapter.disableForegroundDispatch(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mAdapter != null) {
			if (!mAdapter.isEnabled()) {
				showDiag();
			}
		}

		try {
			mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
					mTechLists);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void TurnSetting() {
		ActivityUtil.JumpToAdvConfig(this);
	}

	private void setDeviceID() {
		try {
//			applicationHelper.setDeviceID("tmj");
			TelephonyManager tm = (TelephonyManager) this
					.getSystemService(Context.TELEPHONY_SERVICE);
			String deviceN0 = "";
			deviceN0 = tm.getDeviceId();
			int l = deviceN0.length();
			deviceN0 = deviceN0.substring(l - 8, l);
//			Log.e("deviceN0","+"+deviceN0);
			Const.DeviceType= bd.MODEL;
			Const.DeviceID=deviceN0;
			PackageManager packageManager = getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
			version = packInfo.versionName;
			Const.APPVsersion=version;
			savaBasicInfo.saveDeviceID(deviceN0);// �����ֳֻ����
			savaBasicInfo.saveDeviceType(bd.MODEL);
			DeviceIDInfo.setDeviceID(deviceN0);
		} catch (SecurityException ex) {
			Const.DeviceID="00000000";
			ex.toString();
			savaBasicInfo.saveDeviceID("00000000");// �����ֳֻ����
			DeviceIDInfo.setDeviceID("00000000");
		}catch(PackageManager.NameNotFoundException ex2){
			ex2.toString();
			version="0.0.0";
			Const.APPVsersion=version;
			savaBasicInfo.saveDeviceID("00000000");// �����ֳֻ����
			DeviceIDInfo.setDeviceID("00000000");
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_in:
			Intent i = new Intent(LoginActivity.this,
					LoginByInputActivity.class);
			startActivity(i);
			break;

		}
	}
	/**
	 * 检测网络是否连接
	 *
	 * @return
	 */
	private boolean checkNetworkState() {
		boolean flag = false;
		// 得到网络连接信息
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// 去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		return flag;
	}
	/**
	 * 检查版本更新
	 */
	private void  checkVersion(){
		boolean net = false;
		net = checkNetworkState();
		if (net) {
			VersionAsyncTask vat = new VersionAsyncTask(LoginActivity.this, this);
			vat.execute();
		}
	}
	// NFCģ��
	private void setNFC() {
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mAdapter != null) {
			if (!mAdapter.isEnabled()) {
				showDiag();
			}
			mPendingIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, getClass())
			.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
			// Setup an intent filter for all MIME based dispatches
			IntentFilter ndef = new IntentFilter(
					NfcAdapter.ACTION_TECH_DISCOVERED);

			try {
				ndef.addDataType("*/*");
			} catch (MalformedMimeTypeException e) {
				throw new RuntimeException("fail", e);
			}
			mFilters = new IntentFilter[] { ndef, };

			// Setup a tech list for all NfcF tags
			mTechLists = new String[][] {
					new String[] { MifareClassic.class.getName() },
					new String[] { NfcA.class.getName() },
					new String[] { NfcB.class.getName() },
					new String[] { NfcF.class.getName() },
					new String[] { NfcV.class.getName() },
					new String[] { Ndef.class.getName() },
					new String[] { NdefFormatable.class.getName() },
					new String[] { MifareUltralight.class.getName() },
					new String[] { IsoDep.class.getName() } };

			Intent intent = getIntent();
			resolveIntent(intent);
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		resolveIntent(intent);
	}

	void resolveIntent(Intent intent) {
		try {
			String action = intent.getAction();
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
				GetTag getTag = new GetTag();
				String[] GetData = new String[3];
				GetData = getTag.GetStuffID(intent,bd.MODEL);
				String ret = GetData[1];
				String hsictag = ret.substring(0, 8);
				hsictag = toStringHex2(hsictag).toUpperCase();
				if (hsictag.equals("HSIC")) {
					String stuffID = GetData[1].substring(10, 22);
					stuffID = toStringHex2(stuffID);
					stuffID = stuffID.replaceAll("[^0-9a-zA-Z-\\-]", "");

					employee.setEmpid(stuffID);// Ա����

					String stuffTagID = GetData[0];
					stuffTagID = stuffTagID.toUpperCase();// Ա����TAGID
					/**
					 * ����վ����
					 */
					String[] tempArray = GetData[2].toUpperCase().split("3B");
					String StationID = tempArray[1];

					String stationID = toStringHex2(StationID.trim())
							.substring(0, 4);
					if (stationID.length() >= 4) {
						savaBasicInfo.saveLoginMode("1");//������¼
						employee.setStationid(stationID);
						savaBasicInfo.saveOperationID(stuffID);
						savaBasicInfo.saveStuffTagID(stuffTagID);
						savaBasicInfo.saveStationID(stationID);
						savaBasicInfo.saveLoginStuffMode(Constant.LoginByRead);
						Intent ok = new Intent(LoginActivity.this,
								LoginOkActivity.class);
						startActivity(ok);
					}
				} else {
					Toast.makeText(LoginActivity.this, "非员工卡!",
							Toast.LENGTH_SHORT).show();
				}

			}
		} catch (Exception ex) {
			ex.toString();
		}

	}

	private void showDiag() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				LoginActivity.this);
		builder.setTitle("提示");
		builder.setMessage("NFC设备未打开，是否去打开？");
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
				startActivity(intent);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * 16����תASCII
	 * 
	 * @param s
	 * @return
	 */
	public static String toStringHex2(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "ASCII");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 16����תGBK
	 * 
	 * @param s
	 * @return
	 */
	public static String hexToStringGBK(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		try {
			s = new String(baKeyword, "GBK");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
			return "";
		}
		return s;
	}
	// 是否退出程序
	private static Boolean isExit = false;
	// 定时触发器
	private static Timer tExit = null;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				if (tExit != null) {
					tExit.cancel(); // 将原任务从队列中移除
				}
				// 重新实例一个定时器
				tExit = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						isExit = false;
					}
				};
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				// 延时两秒触发task任务
				tExit.schedule(task, 2000);
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onDismiss(Object o) {

	}

	@Override
	public void onItemClick(Object o, int position) {

	}
	/**
	 * 获取本地软件版本号名称
	 */
	public static String getLocalVersionName(Context ctx) {
		String localVersion = "";
		try {
			PackageInfo packageInfo = ctx.getApplicationContext()
					.getPackageManager()
					.getPackageInfo(ctx.getPackageName(), 0);
			localVersion = packageInfo.versionName;
			Log.d("TAG", "当前版本名称：" + localVersion);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return localVersion;
	}
}
