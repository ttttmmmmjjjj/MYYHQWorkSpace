package com.hsic.sy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hsic.sy.task.Impltask;
import com.hsic.sy.task.InsertTask;

/**
 * Created by Administrator on 2018/12/20.
 */

public class AlarmReceiver extends BroadcastReceiver {
    Impltask l;

    public AlarmReceiver(Impltask l) {
        this.l = l;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == "intent_alarm_log") {
            new InsertTask(context, l).execute();
        }
    }

}
