package com.hsic.bean;

import android.app.Application;

/**
 * Created by Administrator on 2019/1/7.
 */

public class ApplicationHelper extends Application {
    String staffID;
    Sale sale;
    UserXJInfo userXJInfo;
    SaleAll saleAll;

    public SaleAll getSaleAll() {
        return saleAll;
    }

    public void setSaleAll(SaleAll saleAll) {
        this.saleAll = saleAll;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public Sale getSale() {
        return sale;
    }

    public UserXJInfo getUserXJInfo() {
        return userXJInfo;
    }

    public void setUserXJInfo(UserXJInfo userXJInfo) {
        this.userXJInfo = userXJInfo;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
