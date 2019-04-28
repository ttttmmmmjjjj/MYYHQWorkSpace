package com.hsic.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2019/2/26.
 */

public class Sale {
    public int ID; // 销售单编号

    public List<SaleDetail> sale_detail_info;

    public String SaleID; // 销售单号

    public String CustomerID; // 所属客户号

    public String Station; // 所属站点号

    public int AddressID;// 地址编号 CustomerAddressInfo中的ID

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

    public int CreateType;// 生成方式 0：配送员工添加，1：门售员工添加，2：电话添加，3：微信添加

    public String CreateTime; // 生成时间

    public String PS;// 备注

    public int Match;// 是否符合计划 0：符合，1：不符合

    public int iState;// 状态标识位 0：未审核，2：未分单，3：已分单，4：已完成，9：作废

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

    public int OtherQPNum; /// 收到非本站钢瓶数

    public BigDecimal OtherQPPirce; // 抵扣价格

    public String BackEmployeeID;// 退单人

    public String BackInfo;// 退单原因

    public String UserType;// 员工类型

    public String StationName;// STATION名称
    public String CreateManID;// EmployeeInfo中的UserID
    public int UrgeGasInfoStatus;// 0：催单，1：不催单
    /// 0：不可以，1：可以
    public int BackCanFei;
    /// 0：不可以，1：可以
    public int BackCanZhuan;
    /// 0：不可以，1：可以
    public int BackCanFen;
    /// 0：不可以，1：可以
    public int BackCanTui;
    /// 0：合格，1：不予接装，2：停止供气
    /// </summary>
    public int InspectionStatus;
    /// 巡检不合格项
    public String InspectionItem;
    /// GPS_J
    public double GPS_J;
    /// GPS_W
    public double GPS_W;
    // QPEXPirce
    public BigDecimal QPEXPirce;
    //支付的方式
    private int PayType;
    public String SaleAddress;
    public String Remark;
    public String ReceiveByInput;//手输回收瓶标识
    public String DeliverByInput;//手输发出瓶标识
    private String telphone;//来电电话

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<SaleDetail> getSale_detail_info() {
        return sale_detail_info;
    }

    public void setSale_detail_info(List<SaleDetail> sale_detail_info) {
        this.sale_detail_info = sale_detail_info;
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

    public int getAddressID() {
        return AddressID;
    }

    public void setAddressID(int addressID) {
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

    public int getCreateType() {
        return CreateType;
    }

    public void setCreateType(int createType) {
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

    public int getMatch() {
        return Match;
    }

    public void setMatch(int match) {
        Match = match;
    }

    public int getiState() {
        return iState;
    }

    public void setiState(int iState) {
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

    public int getOtherQPNum() {
        return OtherQPNum;
    }

    public void setOtherQPNum(int otherQPNum) {
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

    public int getUrgeGasInfoStatus() {
        return UrgeGasInfoStatus;
    }

    public void setUrgeGasInfoStatus(int urgeGasInfoStatus) {
        UrgeGasInfoStatus = urgeGasInfoStatus;
    }

    public int getBackCanFei() {
        return BackCanFei;
    }

    public void setBackCanFei(int backCanFei) {
        BackCanFei = backCanFei;
    }

    public int getBackCanZhuan() {
        return BackCanZhuan;
    }

    public void setBackCanZhuan(int backCanZhuan) {
        BackCanZhuan = backCanZhuan;
    }

    public int getBackCanFen() {
        return BackCanFen;
    }

    public void setBackCanFen(int backCanFen) {
        BackCanFen = backCanFen;
    }

    public int getBackCanTui() {
        return BackCanTui;
    }

    public void setBackCanTui(int backCanTui) {
        BackCanTui = backCanTui;
    }

    public int getInspectionStatus() {
        return InspectionStatus;
    }

    public void setInspectionStatus(int inspectionStatus) {
        InspectionStatus = inspectionStatus;
    }

    public String getInspectionItem() {
        return InspectionItem;
    }

    public void setInspectionItem(String inspectionItem) {
        InspectionItem = inspectionItem;
    }

    public void setGPS_J(double GPS_J) {
        this.GPS_J = GPS_J;
    }

    public void setGPS_W(double GPS_W) {
        this.GPS_W = GPS_W;
    }

    public double getGPS_J() {
        return GPS_J;
    }

    public double getGPS_W() {
        return GPS_W;
    }

    public BigDecimal getQPEXPirce() {
        return QPEXPirce;
    }

    public void setQPEXPirce(BigDecimal QPEXPirce) {
        this.QPEXPirce = QPEXPirce;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int payType) {
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
