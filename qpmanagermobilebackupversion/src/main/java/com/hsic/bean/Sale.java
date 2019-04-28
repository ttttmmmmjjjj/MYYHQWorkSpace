package com.hsic.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Sale implements Serializable {

	public String ID; // 销售单编号

	public SaleDetail SaleDetial;

	public String SaleID; // 销售单号

	public String CustomerID; // 所属客户号

	public String Station; // 所属站点号

	public String AddressID;// 地址编号 CustomerAddressInfo中的ID

	public String PlanPirce; // 应收款

	public String PlanPirceInfo;// 应收款说明

	public String RealPirce;// 实收款

	public String RealPirceInfo; // 实收款说明

	public String ReceiveQP;// 收瓶号

	public String SendQP; // 发瓶号

	public String FinishTime;// 完成时间

	public String EmployeeID;// 被分单人

	public String ManagerID;// 分单人

	public String AssignTime;// 分单时间

	public String CreateType;// 生成方式 0：配送员工添加，1：门售员工添加，2：电话添加，3：微信添加

	public String CreateTime; // 生成时间

	public String PS;// 备注

	public String Match;// 是否符合计划 0：符合，1：不符合

	public String iState;// 状态标识位 0：未审核，2：未分单，3：已分单，4：已完成，9：作废

	public String address;// 用户地址（本地）

	public String userName;// 用户名字（本地）

	public String phone;// 用户手机号（本地）

	public String qpCount;// 用户销售单瓶数（本地）

	public String FloorType;// 楼层类型

	public BigDecimal FloorPrice;// 楼层费
	;
	public String DistanceType;// 配送距离
	;
	public BigDecimal DistancePrice;// 配送费

	public String OtherQP;// 非本站钢瓶号

	public String OtherQPNum; /// 收到非本站钢瓶数

	public BigDecimal OtherQPPirce; // 抵扣价格

	public String BackEmployeeID;// 退单人

	public String BackInfo;// 退单原因

	public String UserType;// 员工类型

	public String StationName;// STATION名称
	public String CreateManID;// EmployeeInfo中的UserID
	public String UrgeGasInfoStatus;// 0：催单，1：不催单
	/// 0：不可以，1：可以
	public String BackCanFei;
	/// 0：不可以，1：可以
	public String BackCanZhuan;
	/// 0：不可以，1：可以
	public String BackCanFen;
	/// 0：不可以，1：可以
	public String BackCanTui;
	/// 0：合格，1：不予接装，2：停止供气
	/// </summary>
	public String InspectionStatus;
	/// 巡检不合格项
	public String InspectionItem;
	/// GPS_J
	public String GPS_J;
	/// GPS_W
	public String GPS_W;
	// QPEXPirce
	public String QPEXPirce;
	//支付的方式
	private String PayType;
	public String SaleAddress;
	public String Remark;
	public String ReceiveByInput;//手输回收瓶标识
	public String DeliverByInput;//手输发出瓶标识
	private String telphone;//来电电话

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public SaleDetail getSaleDetial() {
		return SaleDetial;
	}

	public void setSaleDetial(SaleDetail saleDetial) {
		SaleDetial = saleDetial;
	}

	public String getSaleID() {
		return SaleID;
	}

	public void setSaleID(String saleID) {
		SaleID = saleID;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getStation() {
		return Station;
	}

	public void setStation(String station) {
		Station = station;
	}

	public String getAddressID() {
		return AddressID;
	}

	public void setAddressID(String addressID) {
		AddressID = addressID;
	}

	public String getPlanPirce() {
		return PlanPirce;
	}

	public void setPlanPirce(String planPirce) {
		PlanPirce = planPirce;
	}

	public String getPlanPirceInfo() {
		return PlanPirceInfo;
	}

	public void setPlanPirceInfo(String planPirceInfo) {
		PlanPirceInfo = planPirceInfo;
	}

	public String getRealPirce() {
		return RealPirce;
	}

	public void setRealPirce(String realPirce) {
		RealPirce = realPirce;
	}

	public String getRealPirceInfo() {
		return RealPirceInfo;
	}

	public void setRealPirceInfo(String realPirceInfo) {
		RealPirceInfo = realPirceInfo;
	}

	public String getReceiveQP() {
		return ReceiveQP;
	}

	public void setReceiveQP(String receiveQP) {
		ReceiveQP = receiveQP;
	}

	public String getSendQP() {
		return SendQP;
	}

	public void setSendQP(String sendQP) {
		SendQP = sendQP;
	}

	public String getFinishTime() {
		return FinishTime;
	}

	public void setFinishTime(String finishTime) {
		FinishTime = finishTime;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}

	public String getManagerID() {
		return ManagerID;
	}

	public void setManagerID(String managerID) {
		ManagerID = managerID;
	}

	public String getAssignTime() {
		return AssignTime;
	}

	public void setAssignTime(String assignTime) {
		AssignTime = assignTime;
	}

	public String getCreateType() {
		return CreateType;
	}

	public void setCreateType(String createType) {
		CreateType = createType;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getPS() {
		return PS;
	}

	public void setPS(String PS) {
		this.PS = PS;
	}

	public String getMatch() {
		return Match;
	}

	public void setMatch(String match) {
		Match = match;
	}

	public String getiState() {
		return iState;
	}

	public void setiState(String iState) {
		this.iState = iState;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQpCount() {
		return qpCount;
	}

	public void setQpCount(String qpCount) {
		this.qpCount = qpCount;
	}

	public String getFloorType() {
		return FloorType;
	}

	public void setFloorType(String floorType) {
		FloorType = floorType;
	}

	public BigDecimal getFloorPrice() {
		return FloorPrice;
	}

	public void setFloorPrice(BigDecimal floorPrice) {
		FloorPrice = floorPrice;
	}

	public String getDistanceType() {
		return DistanceType;
	}

	public void setDistanceType(String distanceType) {
		DistanceType = distanceType;
	}

	public BigDecimal getDistancePrice() {
		return DistancePrice;
	}

	public void setDistancePrice(BigDecimal distancePrice) {
		DistancePrice = distancePrice;
	}

	public String getOtherQP() {
		return OtherQP;
	}

	public void setOtherQP(String otherQP) {
		OtherQP = otherQP;
	}

	public String getOtherQPNum() {
		return OtherQPNum;
	}

	public void setOtherQPNum(String otherQPNum) {
		OtherQPNum = otherQPNum;
	}

	public BigDecimal getOtherQPPirce() {
		return OtherQPPirce;
	}

	public void setOtherQPPirce(BigDecimal otherQPPirce) {
		OtherQPPirce = otherQPPirce;
	}

	public String getBackEmployeeID() {
		return BackEmployeeID;
	}

	public void setBackEmployeeID(String backEmployeeID) {
		BackEmployeeID = backEmployeeID;
	}

	public String getBackInfo() {
		return BackInfo;
	}

	public void setBackInfo(String backInfo) {
		BackInfo = backInfo;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public String getStationName() {
		return StationName;
	}

	public void setStationName(String stationName) {
		StationName = stationName;
	}

	public String getCreateManID() {
		return CreateManID;
	}

	public void setCreateManID(String createManID) {
		CreateManID = createManID;
	}

	public String getUrgeGasInfoStatus() {
		return UrgeGasInfoStatus;
	}

	public void setUrgeGasInfoStatus(String urgeGasInfoStatus) {
		UrgeGasInfoStatus = urgeGasInfoStatus;
	}

	public String getBackCanFei() {
		return BackCanFei;
	}

	public void setBackCanFei(String backCanFei) {
		BackCanFei = backCanFei;
	}

	public String getBackCanZhuan() {
		return BackCanZhuan;
	}

	public void setBackCanZhuan(String backCanZhuan) {
		BackCanZhuan = backCanZhuan;
	}

	public String getBackCanFen() {
		return BackCanFen;
	}

	public void setBackCanFen(String backCanFen) {
		BackCanFen = backCanFen;
	}

	public String getBackCanTui() {
		return BackCanTui;
	}

	public void setBackCanTui(String backCanTui) {
		BackCanTui = backCanTui;
	}

	public String getInspectionStatus() {
		return InspectionStatus;
	}

	public void setInspectionStatus(String inspectionStatus) {
		InspectionStatus = inspectionStatus;
	}

	public String getInspectionItem() {
		return InspectionItem;
	}

	public void setInspectionItem(String inspectionItem) {
		InspectionItem = inspectionItem;
	}

	public String getGPS_J() {
		return GPS_J;
	}

	public void setGPS_J(String GPS_J) {
		this.GPS_J = GPS_J;
	}

	public String getGPS_W() {
		return GPS_W;
	}

	public void setGPS_W(String GPS_W) {
		this.GPS_W = GPS_W;
	}

	public String getQPEXPirce() {
		return QPEXPirce;
	}

	public void setQPEXPirce(String QPEXPirce) {
		this.QPEXPirce = QPEXPirce;
	}

	public String getPayType() {
		return PayType;
	}

	public void setPayType(String payType) {
		PayType = payType;
	}

	public String getSaleAddress() {
		return SaleAddress;
	}

	public void setSaleAddress(String saleAddress) {
		SaleAddress = saleAddress;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getReceiveByInput() {
		return ReceiveByInput;
	}

	public void setReceiveByInput(String receiveByInput) {
		ReceiveByInput = receiveByInput;
	}

	public String getDeliverByInput() {
		return DeliverByInput;
	}

	public void setDeliverByInput(String deliverByInput) {
		DeliverByInput = deliverByInput;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
}
