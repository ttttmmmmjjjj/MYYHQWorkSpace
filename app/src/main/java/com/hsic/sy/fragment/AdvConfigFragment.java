package com.hsic.sy.fragment;


import java.net.NetworkInterface;
import java.util.Enumeration;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.Log;
import android.view.KeyEvent;

import com.hsic.sy.bll.DeviceIDInfo;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.supplystationmanager.SetBlueToothPrinter;

public class AdvConfigFragment extends PreferenceFragment {
	GetBasicInfo basicInfo;
	private SharedPreferences mPreferences;
	private static String StationServer;
	private static String GPServer;
	private static String bluetooth;
	private static String dbversion;
	private static String deviceid;
	private static String version;
	private static String devicetype;
	private static String printCount;
	private static String StationPort;
	private static String GPPort;
	SharedPreferences device;
	private static String  FTPServer;
	private static String  FTPPort;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.config);
		device = getActivity().getSharedPreferences("DeviceSetting", 0);
		bluetooth = device.getString("BlueToothAdd", "");
//
		mPreferences = getPreferenceScreen().getSharedPreferences();
//
//		defValuedeviceid=getActivity().getResources().getString(
//				R.string.device_no);
//
		StationServer = getActivity().getResources()
				.getString(R.string.station_default);
		FTPServer=getActivity().getResources()
				.getString(R.string.ftp_default);
		FTPPort=getActivity().getResources()
				.getString(R.string.ftpport_default);
		GPServer = getActivity().getResources()
				.getString(R.string.gp_default);
		dbversion = getActivity().getResources().getString(R.string.db_default);
		version = getActivity().getResources().getString(
				R.string.version_default);
//		devicetype = device.getString("DeviceType", "");
//		printCount=getActivity().getResources().getString(
//				R.string.p_default);//20161017
		StationPort=getActivity().getResources().getString(
				R.string.stationport_default);//端口号
		GPPort=getActivity().getResources().getString(
				R.string.gpport_default);//端口号
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		super.getActivity().onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.getActivity().finish();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		String deviceid= DeviceIDInfo.getDeviceID();

		findPreference("deviceid").setSummary(deviceid);
		findPreference("StationServer").setSummary(// ws
				mPreferences.getString("StationServer", StationServer));
		findPreference("GPServer").setSummary(// ws
				mPreferences.getString("GPServer", GPServer));
		findPreference("version").setSummary(// ws
				mPreferences.getString("version", version));
		findPreference("dbversion").setSummary(// ws
				mPreferences.getString("dbversion", dbversion));
//		findPreference("devicetype").setSummary(// ws
//				mPreferences.getString("devicetype", devicetype));
//		findPreference("printCount").setSummary(// ws
//				mPreferences.getString("printCount", printCount));//20161017
		findPreference("StationPort").setSummary(// ws
				mPreferences.getString("StationPort", StationPort));//端口号
		findPreference("GPPort").setSummary(// ws
				mPreferences.getString("GPPort", GPPort));//端口号
		findPreference("FTPServer").setSummary(// ws
				mPreferences.getString("FTPServer", FTPServer));//端口号
		findPreference("FTPPort").setSummary(// ws
				mPreferences.getString("FTPPort", FTPPort));//端口号
		String verString = "";
		try {
			PackageInfo packageinfo = getActivity().getPackageManager()
					.getPackageInfo("hsic.sy.supplystationmanager", 0);
			verString = "V " + packageinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		findPreference("version").setSummary(String.valueOf(verString));
		if (!bluetooth.equals("")) {
			Log.e("ConfigFragment", bluetooth);
			findPreference("bluetooth").setSummary(bluetooth);
		}
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
										 Preference preference) {
		// TODO Auto-generated method stub
		if (preference.getKey().equals("bluetooth")) {
			// 在这里进行蓝牙搜索操作
//			Log.e("在这里发出蓝牙广播", "1");
			Intent i = new Intent(getActivity(), SetBlueToothPrinter.class);
			startActivity(i);

		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	public String getMac(Context context) {
		try {
			for (Enumeration<NetworkInterface> e = NetworkInterface
					.getNetworkInterfaces(); e.hasMoreElements();) {
				NetworkInterface item = e.nextElement();
				byte[] mac = item.getHardwareAddress();
				if (mac != null && mac.length > 0) {
					return new String(mac);
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(
						mOnSharedPreferenceChangeListener);
		bluetooth = device.getString("BlueToothAdd", "");
		if (!bluetooth.equals("")) {
			Log.e("ConfigFragment", bluetooth);
			findPreference("bluetooth").setSummary(bluetooth);
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(
						mOnSharedPreferenceChangeListener);
	}

	private OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener = new OnSharedPreferenceChangeListener() {

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			Preference pref = findPreference(key);
			if (pref instanceof EditTextPreference) {
				EditTextPreference etp = (EditTextPreference) pref;
				pref.setSummary(etp.getText());
			}
			if (pref.getKey().equals("bluetooth")) {
			}
		}
	};

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		getActivity().finish();

	}
}
