package com.hsic.bean;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerAddressInfo {
	// �ͻ���Ϣע���ַ��
	public String CustomerID;//
	public String Address;//
	public String FloorType;//
	public String DistanceType;//
	public String LastQPApply;//
	public String iState;//
	public String Logtime;//
	public String ID;//
	public String AreaCode;
	public String BelongPeisong;
	public String BelongPeisongType;
	public String Station;

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getFloorType() {
		return FloorType;
	}

	public void setFloorType(String floorType) {
		FloorType = floorType;
	}

	public String getDistanceType() {
		return DistanceType;
	}

	public void setDistanceType(String distanceType) {
		DistanceType = distanceType;
	}

	public String getLastQPApply() {
		return LastQPApply;
	}

	public void setLastQPApply(String lastQPApply) {
		LastQPApply = lastQPApply;
	}

	public String getiState() {
		return iState;
	}

	public void setiState(String iState) {
		this.iState = iState;
	}

	public String getLogtime() {
		return Logtime;
	}

	public void setLogtime(String logtime) {
		Logtime = logtime;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getAreaCode() {
		return AreaCode;
	}

	public void setAreaCode(String areaCode) {
		AreaCode = areaCode;
	}

	public String getBelongPeisong() {
		return BelongPeisong;
	}

	public void setBelongPeisong(String belongPeisong) {
		BelongPeisong = belongPeisong;
	}

	public String getBelongPeisongType() {
		return BelongPeisongType;
	}

	public void setBelongPeisongType(String belongPeisongType) {
		BelongPeisongType = belongPeisongType;
	}

	public String getStation() {
		return Station;
	}

	public void setStation(String station) {
		Station = station;
	}
}
