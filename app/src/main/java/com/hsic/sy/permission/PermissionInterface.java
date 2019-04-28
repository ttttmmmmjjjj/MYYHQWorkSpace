package com.hsic.sy.permission;

import com.hsic.sy.bean.HsicMessage;

/**
 * Created by Administrator on 2019/3/12.
 */

public interface PermissionInterface {
    public void requestPermissionsSuccess();
    public void requestPermissionsFail();
    public int getPermissionsRequestCode();
    public String[] getPermissions();
}
