package com.hsic.sy.szapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.service.PollingService;
import com.hsic.sy.task.Impltask;
import com.hsic.sy.task.InsertTask;
import com.hsic.sy.utils.AlarmReceiver;
import com.hsic.sy.utils.HsicMessage;
import com.hsic.sy.utils.PollingUtils;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements Impltask {
    private Button btn_start,btn_stop;
    private EditText edt_time;
    AlarmReceiver receiver;
    long intervalMillis=20000;
    AlarmManager am;
    PendingIntent sender;
    Calendar calendar;
    Intent intent;
    IntentFilter itf;
    private TextView txt_info;
    int count=0,f=0,s=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_info=this.findViewById(R.id.txt_info);
        txt_info.setText("");
        txt_info.setText("共调用服务:"+count+" 次，成功:"+s+" 次，失败:"+f+"次");
        btn_start=this.findViewById(R.id.btn_start);
        btn_start.setBackgroundResource(R.drawable.bg_btn_selector);
        btn_stop=this.findViewById(R.id.btn_stop);
        edt_time=this.findViewById(R.id.edt_time);
        edt_time.setText("");
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
        toolbar.setTitle("网络测试");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=0;
                f=0;s=0;
                txt_info.setText("共调用服务:"+count+" 次，成功:"+s+" 次，失败:"+f+"次");
                String time=edt_time.getText().toString();
                if(!TextUtils.isEmpty(time)){
                    intervalMillis=Long.parseLong(time)*1000;
                }else{
                    intervalMillis=20000;
                }
                btn_start.setBackgroundColor(Color.GRAY);
                btn_start.setEnabled(false);
                new InsertTask(LoginActivity.this, LoginActivity.this).execute();
                am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intervalMillis,sender);
                receiver = new AlarmReceiver(LoginActivity.this);
                itf = new IntentFilter();
                itf.addAction("intent_alarm_log");
                registerReceiver(receiver, itf);
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_start.setBackgroundResource(R.drawable.bg_btn_selector);
                btn_start.setEnabled(true);
                if (receiver != null) {
                    try {
                        am.cancel(sender);
                        unregisterReceiver(receiver);
                        receiver = null;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.SECOND, 1);
        intent =new Intent("intent_alarm_log");
        sender = PendingIntent.getBroadcast(LoginActivity.this,0,intent,0);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    // 是否退出程序
    private static Boolean isExit = false;
    // 定时触发器
    private static Timer tExit = null;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                if (tExit != null) {
                    tExit.cancel(); // 将原任务从队列中移除
                }
                // 重新实例一个定时器
                tExit = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                };
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 延时两秒触发task任务
                tExit.schedule(task, 2000);
            } else {
                if (receiver != null) {
                    try {
                        am.cancel(sender);
                        unregisterReceiver(receiver);
                        receiver = null;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void ImpltaskEnd(HsicMessage tag) {
        count++;
        if(tag.getRespCode()==0){
            s++;
        }else{
            f++;
        }
        txt_info.setText("共调用服务:"+count+" 次，成功:"+s+" 次，失败:"+f+"次");
    }
}
