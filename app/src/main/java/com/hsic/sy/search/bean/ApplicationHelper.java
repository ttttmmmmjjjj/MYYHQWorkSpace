package com.hsic.sy.search.bean;

import android.app.Application;

import com.hsic.sy.bean.UserExploration;

public class ApplicationHelper extends Application{
	public String DeviceXH;//�豸�ͺ�
	public String BMac;//������ַ
	public String DeviceID;//�豸���
	public String HistoryXJ;
	public UserRectifyInfo useRectifyInfo;
	public LastUserInspection lastUserInspection;
	/*public String StuffTagID;
	hsic.sy.qp.bean.Employee Employee;*/
	public UserExploration userExploration;

	public String getDeviceXH() {
		return DeviceXH;
	}
	public void setDeviceXH(String deviceXH) {
		DeviceXH = deviceXH;
	}
	public String getBMac() {
		return BMac;
	}
	public void setBMac(String bMac) {
		BMac = bMac;
	}
	public String getDeviceID() {
		return DeviceID;
	}
	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}
	public String getHistoryXJ() {
		return HistoryXJ;
	}
	public void setHistoryXJ(String historyXJ) {
		HistoryXJ = historyXJ;
	}
	public UserRectifyInfo getUseRectifyInfo() {
		return useRectifyInfo;
	}
	public void setUseRectifyInfo(UserRectifyInfo useRectifyInfo) {
		this.useRectifyInfo = useRectifyInfo;
	}
	public LastUserInspection getLastUserInspection() {
		return lastUserInspection;
	}
	public void setLastUserInspection(LastUserInspection lastUserInspection) {
		this.lastUserInspection = lastUserInspection;
	}
	public UserExploration getUserExploration() {
		return userExploration;
	}
	public void setUserExploration(UserExploration userExploration) {
		this.userExploration = userExploration;
	}


}
