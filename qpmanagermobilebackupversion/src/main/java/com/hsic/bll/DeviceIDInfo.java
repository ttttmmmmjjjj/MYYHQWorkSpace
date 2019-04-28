package com.hsic.bll;

import android.content.Context;
import android.preference.PreferenceManager;

public class DeviceIDInfo {
	/**
	 * ��ȡ�豸���
	 */
	private static String deviceID="";

	/**
	 * ��ȡ�Ƿ�У���豸��״̬
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getDeviceidCheckState(Context context) {
		boolean ret = false;

		ret = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean("checkbox_key", false);

		return ret;
	}

	/**
	 * ��ȡ�Ƿ��ϴ�gps��״̬
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getGpsSwitchState(Context context) {

		boolean ret = false;

		ret = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean("checkbox_gps_key", false);

		return ret;
	}

	public static String getDeviceID() {
		return deviceID;
	}

	public static void setDeviceID(String deviceID) {
		DeviceIDInfo.deviceID = deviceID;
	}
}
