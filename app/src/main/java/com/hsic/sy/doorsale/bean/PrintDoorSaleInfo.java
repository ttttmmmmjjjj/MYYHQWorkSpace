package com.hsic.sy.doorsale.bean;

/**
 * Created by Administrator on 2018/8/17.
 */

public class PrintDoorSaleInfo {
    private String saleId;
    private String userId;
    private String userName;
    private String phoneNum;
    private String address;
    private String[][] GoodsInfo;
    private String emptyNo;
    private String funnNo;
    private String totalPrice;
    private String operation;
    private String operationTime;

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[][] getGoodsInfo() {
        return GoodsInfo;
    }

    public void setGoodsInfo(String[][] goodsInfo) {
        GoodsInfo = goodsInfo;
    }

    public String getEmptyNo() {
        return emptyNo;
    }

    public void setEmptyNo(String emptyNo) {
        this.emptyNo = emptyNo;
    }

    public String getFunnNo() {
        return funnNo;
    }

    public void setFunnNo(String funnNo) {
        this.funnNo = funnNo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }
}
