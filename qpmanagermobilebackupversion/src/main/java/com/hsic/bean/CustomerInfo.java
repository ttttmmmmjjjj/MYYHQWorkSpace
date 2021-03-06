package com.hsic.bean;

import java.io.Serializable;
import java.util.List;

public class CustomerInfo implements Serializable {
	public int ID; // 客户编号
	public String CustomerID;// 客户号,主键
	public String CustomerCardID; // 用户卡号
	public String Station;// 所属站点号
	public String Telphone;// 手机号
	public String CustomerName;// 客户姓名
	public String IdentityID;// 身份证号
	public String OtherTel;// 其他联系方式
	public String Password;// 密码
	public String CheckMan;// 审核人
	public String CheckTime;// 审核时间
	public int iState; // 状态标识位 0：未审核，1：已审核，8：待复审，9：停用
	public String InfoTime;// 信息修改日期
	public String Logtime;// 注册日期
	public String CustomerType;// 用户类型
	public List<CustomerAddressInfo> CustomerAddressInfo;// 用户地址
	public String CustomerTypeName;// 用户类型描述
	public String BelongPeisong;/// EmployeeInfo中的UserID
	/// 默认0。0：新用户，1：老用户
	public String IsNew;
	/// 代销户 1代销户
	public String DelUser;
	// 操作人的id
	public String OperatorID;
	// EmployeeInfo中的UserType
	public String BelongPeisongType;
	/// EmployeeInfo中的Station
	public String BelongStation;
	private String userCardID;
	private String userCardStatus;

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getCustomerCardID() {
		return CustomerCardID;
	}

	public void setCustomerCardID(String customerCardID) {
		CustomerCardID = customerCardID;
	}

	public String getStation() {
		return Station;
	}

	public void setStation(String station) {
		Station = station;
	}

	public String getTelphone() {
		return Telphone;
	}

	public void setTelphone(String telphone) {
		Telphone = telphone;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getIdentityID() {
		return IdentityID;
	}

	public void setIdentityID(String identityID) {
		IdentityID = identityID;
	}

	public String getOtherTel() {
		return OtherTel;
	}

	public void setOtherTel(String otherTel) {
		OtherTel = otherTel;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getCheckMan() {
		return CheckMan;
	}

	public void setCheckMan(String checkMan) {
		CheckMan = checkMan;
	}

	public String getCheckTime() {
		return CheckTime;
	}

	public void setCheckTime(String checkTime) {
		CheckTime = checkTime;
	}

	public int getiState() {
		return iState;
	}

	public void setiState(int iState) {
		this.iState = iState;
	}

	public String getInfoTime() {
		return InfoTime;
	}

	public void setInfoTime(String infoTime) {
		InfoTime = infoTime;
	}

	public String getLogtime() {
		return Logtime;
	}

	public void setLogtime(String logtime) {
		Logtime = logtime;
	}

	public String getCustomerType() {
		return CustomerType;
	}

	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}

	public List<com.hsic.bean.CustomerAddressInfo> getCustomerAddressInfo() {
		return CustomerAddressInfo;
	}

	public void setCustomerAddressInfo(List<com.hsic.bean.CustomerAddressInfo> customerAddressInfo) {
		CustomerAddressInfo = customerAddressInfo;
	}

	public String getCustomerTypeName() {
		return CustomerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		CustomerTypeName = customerTypeName;
	}

	public String getBelongPeisong() {
		return BelongPeisong;
	}

	public void setBelongPeisong(String belongPeisong) {
		BelongPeisong = belongPeisong;
	}

	public String getIsNew() {
		return IsNew;
	}

	public void setIsNew(String isNew) {
		IsNew = isNew;
	}

	public String getDelUser() {
		return DelUser;
	}

	public void setDelUser(String delUser) {
		DelUser = delUser;
	}

	public String getOperatorID() {
		return OperatorID;
	}

	public void setOperatorID(String operatorID) {
		OperatorID = operatorID;
	}

	public String getBelongPeisongType() {
		return BelongPeisongType;
	}

	public void setBelongPeisongType(String belongPeisongType) {
		BelongPeisongType = belongPeisongType;
	}

	public String getBelongStation() {
		return BelongStation;
	}

	public void setBelongStation(String belongStation) {
		BelongStation = belongStation;
	}

	public String getUserCardID() {
		return userCardID;
	}

	public void setUserCardID(String userCardID) {
		this.userCardID = userCardID;
	}

	public String getUserCardStatus() {
		return userCardStatus;
	}

	public void setUserCardStatus(String userCardStatus) {
		this.userCardStatus = userCardStatus;
	}
}
