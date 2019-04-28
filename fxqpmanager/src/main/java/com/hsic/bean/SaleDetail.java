package com.hsic.bean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/2/26.
 */

public class SaleDetail {
    public String SaleID;// 销售单号

    public String QPType;// 气瓶种类

    public int PlanSendNum;// 预发气瓶

    public int PlanReceiveNum; // 预收气瓶

    public int ReceiveNum;// 实际收瓶数量（本场瓶）

    public String QPPrice;//气瓶单价

    public int LeaseNum ;//租瓶数

    public String LeaseID; // 租瓶单号

    public int SendNum;// 实际发瓶数量

    public int OtherQPNum;// 收到非本站钢瓶数

    public String Logtime;// 生成日期

    public String QPName;// 气瓶名称

    public String RealQTPrice; /// 气体实际单价
    /// 状态标识位 0：不是附件，1：是附件
    public int IsEx;

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

    public int getPlanSendNum() {
        return PlanSendNum;
    }

    public void setPlanSendNum(int planSendNum) {
        PlanSendNum = planSendNum;
    }

    public int getPlanReceiveNum() {
        return PlanReceiveNum;
    }

    public void setPlanReceiveNum(int planReceiveNum) {
        PlanReceiveNum = planReceiveNum;
    }

    public int getReceiveNum() {
        return ReceiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        ReceiveNum = receiveNum;
    }

    public String getQPPrice() {
        return QPPrice;
    }

    public void setQPPrice(String QPPrice) {
        this.QPPrice = QPPrice;
    }

    public int getLeaseNum() {
        return LeaseNum;
    }

    public void setLeaseNum(int leaseNum) {
        LeaseNum = leaseNum;
    }

    public String getLeaseID() {
        return LeaseID;
    }

    public void setLeaseID(String leaseID) {
        LeaseID = leaseID;
    }

    public int getSendNum() {
        return SendNum;
    }

    public void setSendNum(int sendNum) {
        SendNum = sendNum;
    }

    public int getOtherQPNum() {
        return OtherQPNum;
    }

    public void setOtherQPNum(int otherQPNum) {
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

    public int getIsEx() {
        return IsEx;
    }

    public void setIsEx(int isEx) {
        IsEx = isEx;
    }
}
