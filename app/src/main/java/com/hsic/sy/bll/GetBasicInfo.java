package com.hsic.sy.bll;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/8/17.
 */

public class GetBasicInfo {
	private Context context = null;
	SharedPreferences deviceSetting;

	public GetBasicInfo(Context context) {
		this.context = context;
		deviceSetting = context.getSharedPreferences("DeviceSetting", 0);
	}

	public String getStationID() {
		String ret = "";
		ret = deviceSetting.getString("StationID", "");// վ��ID
		return ret;
	}

	public String getDeviceID() {
		String ret = "";
		ret = deviceSetting.getString("DeviceID", "");// �豸���
		return ret;
	}
	public String getDeviceType() {
		String ret = "";
		ret = deviceSetting.getString("DeviceType", "");// �豸���
		return ret;
	}
	public String getStationName() {
		String ret = "";
		ret = deviceSetting.getString("StationName", "");// վ������
		return ret;
	}

	public String getOperationID() {
		String ret = "";
		ret = deviceSetting.getString("OperationID", "");// ����ԱID
		return ret;
	}

	public String getOperationName() {
		String ret = "";
		ret = deviceSetting.getString("OperationName", "");// ��������
		return ret;
	}

	public String getBlueToothAdd() {
		String ret = "";
		ret = deviceSetting.getString("BlueToothAdd", "");// ����MAC��ַ
		return ret;
	}

	public String getStuffTagID() {
		String ret = "";
		ret = deviceSetting.getString("StuffTagID", "");// Ա��tagID��uid��
		return ret;
	}

	public String getRectifyMan() {
		String ret = "";
		ret = deviceSetting.getString("RectifyMan", "");// ������Ա
		return ret;
	}
	public String getLoginMode() {
		String ret = "";
		ret = deviceSetting.getString("LoginMode", "");// ������Ա
		return ret;
	}
	public String getLoginStuffMode() {
		String ret = "";
		ret = deviceSetting.getString("LoginStuffMode", "");// ������Ա
		return ret;
	}
}
