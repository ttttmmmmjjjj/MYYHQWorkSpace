package com.hsic.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hsic.fxqpmanager.MainActivity;
import com.hsic.fxqpmanager.SaleListActivity;
import com.hsic.utils.SystemUtils;

/**
 * Created by Administrator on 2019/4/2.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(SystemUtils.isAppRunning(context, "com.hsic.fxqpmanager")){
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent detailIntent = new Intent(context, SaleListActivity.class);
            Intent[] intents = {mainIntent, detailIntent};
            context.startActivities(intents);
        }
    }
}
