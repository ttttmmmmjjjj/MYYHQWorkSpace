package com.hsic.bll;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2019/2/25.
 */

public class GetBasicInfo {
    private Context context = null;
    SharedPreferences deviceSetting;

    public GetBasicInfo(Context context) {
        this.context = context;
        deviceSetting = context.getSharedPreferences("DeviceSetting", 0);
    }

    public String getStationID() {
        String ret = "";
        ret = deviceSetting.getString("StationID", "");// վ��ID
        return ret;
    }

    public String getDeviceID() {
        String ret = "";
        ret = deviceSetting.getString("DeviceID", "");// �豸���
        return ret;
    }
    public String getDeviceType() {
        String ret = "";
        ret = deviceSetting.getString("DeviceType", "");// �豸���
        return ret;
    }
    public String getStationName() {
        String ret = "";
        ret = deviceSetting.getString("StationName", "");// վ������
        return ret;
    }

    public String getOperationID() {
        String ret = "";
        ret = deviceSetting.getString("OperationID", "");// ����ԱID
        return ret;
    }

    public String getOperationName() {
        String ret = "";
        ret = deviceSetting.getString("OperationName", "");// ��������
        return ret;
    }

    public String getBlueToothAdd() {
        String ret = "";
        ret = deviceSetting.getString("BlueToothAdd", "");// ����MAC��ַ
        return ret;
    }

    public String getStuffTagID() {
        String ret = "";
        ret = deviceSetting.getString("StaffTagID", "");// Ա��tagID��uid��
        return ret;
    }

    public String getRectifyMan() {
        String ret = "";
        ret = deviceSetting.getString("RectifyMan", "");// ������Ա
        return ret;
    }
    public String getLoginMode() {
        String ret = "";
        ret = deviceSetting.getString("LoginMode", "");// ������Ա
        return ret;
    }
    public String getLoginStuffMode() {
        String ret = "";
        ret = deviceSetting.getString("LoginStaffMode", "");// ������Ա
        return ret;
    }
    public String getCompany() {
        String ret = "";
        ret = deviceSetting.getString("Company", "");// ������Ա
        return ret;
    }

    /**
     * 获取登录账户
     * @return
     */
    public String getAccount() {
        String ret = "";
        ret = deviceSetting.getString("Account", "");// ������Ա
        return ret;
    }

    /**
     * 获取登录密码
     * @return
     */
    public String getPass() {
        String ret = "";
        ret = deviceSetting.getString("PassWord", "");// ������Ա
        return ret;
    }

    /***
     * 手持机数据库版本
     * @return
     */
    public String getDBVersion() {
        String ret = "";
        ret = deviceSetting.getString("DBVersion", "");// ������Ա
        return ret;
    }

    /**
     *登录是否记住密码勾选框
     * @return
     */
    public boolean getIsChecked(){
        boolean ret = false;
        ret = deviceSetting.getBoolean("IsChecked", false);// ������Ա
        return ret;
    }
}
