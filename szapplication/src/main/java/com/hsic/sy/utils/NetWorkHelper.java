package com.hsic.sy.utils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2018/8/8.
 */

public class NetWorkHelper {
    private Context mContext;

    private static NetWorkHelper mInstance = null;

    public NetWorkHelper(Context context) {
        super();
        mContext = context;
    }

    public static synchronized NetWorkHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetWorkHelper(context);
        }
        return mInstance;
    }

    /*
     * 检查网络状态
     */
    public boolean checkNetworkStatus() throws NetworkErrorException {
        boolean result = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isWifiAvail = ni.isAvailable();
            boolean isWifiConn = ni.isConnected();
            if (isWifiAvail && isWifiConn) {
                result = true;
            } else {
                ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                boolean isMobileAvail = ni.isAvailable();
                boolean isMobileConn = ni.isConnected();
                if (isMobileAvail && isMobileConn) {
                    result = true;
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
