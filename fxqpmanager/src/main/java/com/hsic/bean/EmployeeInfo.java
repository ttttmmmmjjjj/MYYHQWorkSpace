package com.hsic.bean;

/**
 * Created by Administrator on 2019/2/25.
 */

public class EmployeeInfo {
    public int ID;// 员工编号
    public String UserID;// 账号
    public String Password;// 密码 MD5加密
    public String UserName;// 用户姓名
    public String UserCardID;// 员工卡号 暂无用，可为空
    public String Telphone;// 联系方式
    public String Station;// 所属站点
    public int UserType;// 员工类型 0：配送员工，1：门售员工，2：站点管理员，9：超级管理员
    public String CheckMan;// 审核人
    public String CheckTime;// 审核时间
    public int iState; // 状态标识位 0：未审核，1：已审核，7：审核不通过，9：停用
    public String PrePassword;// 用户的原密
    public String StationName;
    //新增查询日期字段
    public String SearchDate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserCardID() {
        return UserCardID;
    }

    public void setUserCardID(String userCardID) {
        UserCardID = userCardID;
    }

    public String getTelphone() {
        return Telphone;
    }

    public void setTelphone(String telphone) {
        Telphone = telphone;
    }

    public String getStation() {
        return Station;
    }

    public void setStation(String station) {
        Station = station;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
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

    public String getPrePassword() {
        return PrePassword;
    }

    public void setPrePassword(String prePassword) {
        PrePassword = prePassword;
    }

    public String getSearchDate() {
        return SearchDate;
    }

    public void setSearchDate(String searchDate) {
        SearchDate = searchDate;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }
}
