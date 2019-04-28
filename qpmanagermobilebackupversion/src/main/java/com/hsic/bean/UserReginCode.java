package com.hsic.bean;

/**
 * Created by Administrator on 2018/8/13.
 */

public class UserReginCode {
    private String userRegionCode;
    private String qpType;
    private String qpName;

    public String getQpName() {
        return qpName;
    }

    public void setQpName(String qpName) {
        this.qpName = qpName;
    }

    public String getQpType() {
        return qpType;
    }

    public void setQpType(String qpType) {
        this.qpType = qpType;
    }

    public String getUserRegionCode() {
        return userRegionCode;
    }

    public void setUserRegionCode(String userRegionCode) {
        this.userRegionCode = userRegionCode;
    }
}
