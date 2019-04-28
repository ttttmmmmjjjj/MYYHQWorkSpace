package com.hsic.bean;

/**
 * Created by Administrator on 2019/2/25.
 */

public class StationInfo {
    public String Station ;// 站点号
    public String StationName; // 站点简称
    public String StationFullName;// 站点全称
    public String Address ;// 站点地址
    public int iState ;// 状态标识位 1：正使用，9：停用

    public String getStation() {
        return Station;
    }

    public void setStation(String station) {
        Station = station;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getStationFullName() {
        return StationFullName;
    }

    public void setStationFullName(String stationFullName) {
        StationFullName = stationFullName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getiState() {
        return iState;
    }

    public void setiState(int iState) {
        this.iState = iState;
    }
}
