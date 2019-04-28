package com.hsic.bean;


import java.util.List;

/**
 * Created by Administrator on 2019/2/26.
 */

public class SaleAll {
    public Sale Sale;// 销售信息
    public UserXJInfo UserXJInfo;//用户相关信息
    public List<ScanHistory> ScanHistory; // 发瓶收瓶列表
    public com.hsic.bean.Sale getSale() {
        return Sale;
    }

    public void setSale(com.hsic.bean.Sale sale) {
        Sale = sale;
    }

    public com.hsic.bean.UserXJInfo getUserXJInfo() {
        return UserXJInfo;
    }

    public void setUserXJInfo(com.hsic.bean.UserXJInfo userXJInfo) {
        UserXJInfo = userXJInfo;
    }

    public List<com.hsic.bean.ScanHistory> getScanHistory() {
        return ScanHistory;
    }

    public void setScanHistory(List<com.hsic.bean.ScanHistory> scanHistory) {
        ScanHistory = scanHistory;
    }
}
