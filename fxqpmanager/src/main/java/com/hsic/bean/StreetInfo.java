package com.hsic.bean;

/**
 * Created by Administrator on 2019/2/25.
 */

public class StreetInfo {
    /// 流水号
    public int ID;
    /// 区域代码
    public String AreaCode;
    /// 区名称
    public String QuName;
    /// 街道名称
    public String JieName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getQuName() {
        return QuName;
    }

    public void setQuName(String quName) {
        QuName = quName;
    }

    public String getJieName() {
        return JieName;
    }

    public void setJieName(String jieName) {
        JieName = jieName;
    }
}
