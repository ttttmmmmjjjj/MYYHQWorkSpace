package com.hsic.bean;

import java.io.Serializable;

public class SaleDetail implements Serializable{
	public int ID;
	public String SaleID;
	public String QPType;
	public String PlanSendNum;
	public String PlanReceiveNum;
	public String ReceiveNum;//
    public String QPPrice;
    public String LeaseNum ;
    public String LeaseID;
	public String SendNum;//
	public String OtherQPNum;
	public String Logtime;
    public String QPName;
    public String RealQTPrice;
    public String  IsEx;

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getSaleID() {
		return SaleID;
	}

	public void setSaleID(String saleID) {
		SaleID = saleID;
	}

	public String getQPType() {
		return QPType;
	}

	public void setQPType(String QPType) {
		this.QPType = QPType;
	}

	public String getPlanSendNum() {
		return PlanSendNum;
	}

	public void setPlanSendNum(String planSendNum) {
		PlanSendNum = planSendNum;
	}

	public String getPlanReceiveNum() {
		return PlanReceiveNum;
	}

	public void setPlanReceiveNum(String planReceiveNum) {
		PlanReceiveNum = planReceiveNum;
	}

	public String getReceiveNum() {
		return ReceiveNum;
	}

	public void setReceiveNum(String receiveNum) {
		ReceiveNum = receiveNum;
	}

	public String getQPPrice() {
		return QPPrice;
	}

	public void setQPPrice(String QPPrice) {
		this.QPPrice = QPPrice;
	}

	public String getLeaseNum() {
		return LeaseNum;
	}

	public void setLeaseNum(String leaseNum) {
		LeaseNum = leaseNum;
	}

	public String getLeaseID() {
		return LeaseID;
	}

	public void setLeaseID(String leaseID) {
		LeaseID = leaseID;
	}

	public String getSendNum() {
		return SendNum;
	}

	public void setSendNum(String sendNum) {
		SendNum = sendNum;
	}

	public String getOtherQPNum() {
		return OtherQPNum;
	}

	public void setOtherQPNum(String otherQPNum) {
		OtherQPNum = otherQPNum;
	}

	public String getLogtime() {
		return Logtime;
	}

	public void setLogtime(String logtime) {
		Logtime = logtime;
	}

	public String getQPName() {
		return QPName;
	}

	public void setQPName(String QPName) {
		this.QPName = QPName;
	}

	public String getRealQTPrice() {
		return RealQTPrice;
	}

	public void setRealQTPrice(String realQTPrice) {
		RealQTPrice = realQTPrice;
	}

	public String getIsEx() {
		return IsEx;
	}

	public void setIsEx(String isEx) {
		IsEx = isEx;
	}
}
