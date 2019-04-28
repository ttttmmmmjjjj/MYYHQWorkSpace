package com.hsic.fxqpmanager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.adapter.MenuGridAdapter;
import com.hsic.bll.GetBasicInfo;
import com.hsic.db.DeleteData;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.task.UpLoadHistoryTask;
import com.hsic.tmj.gridview.MyGridView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements  com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
    private MyGridView gridview;
    private TextView station, info;
    GetBasicInfo getBasicInfo;
    AlertView choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBasicInfo = new GetBasicInfo(MainActivity.this);
        innitView();
        choice = new AlertView("请选择用户类型", null, null, null,
                new String[]{"用户未发卡", "用户已发卡"},
                this, AlertView.Style.ActionSheet, this);
        /**
         * 删除历史数据
         */
        DeleteData deleteData = new DeleteData(this);
        deleteData.delete(getBasicInfo.getOperationID(), getBasicInfo.getStationID());

    }
    private void innitView() {
        gridview = (MyGridView) findViewById(R.id.gridview);
        String[] img_text = {"在线门售", "上门配送", "安全检查", "隐患整改", "其他业务", "数据上传"};
        int[] imgs = {R.drawable.xd, R.drawable.cc,
                R.drawable.h, R.drawable.c,
                R.drawable.count, R.drawable.setting};
        gridview.setAdapter(new MenuGridAdapter(this, img_text, imgs));
        info = (TextView) this.findViewById(R.id.info);
        info.setText(getBasicInfo.getOperationName());
        station = (TextView) this.findViewById(R.id.station);
        station.setText(getBasicInfo.getStationName());
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch ((int) i) {
                    case 0:
//                        Intent doorSale = new Intent(MainActivity.this, DoorSaleMainActivity.class);
//                        startActivity(doorSale);
                        shownDialog("无权限使用!");
                        break;
                    case 1:
                        Intent saleList = new Intent(MainActivity.this, SaleListActivity.class);
                        startActivity(saleList);//
                        break;
                    case 2:
                        //安检
//                        Intent search=new Intent(MainActivity.this,UserLoginByReadActivity.class);//有卡无卡
//                        search.putExtra("LR","2");//上门配送时用户登录
//                        startActivity(search);
                        if(checkNetworkState()){
                            choice.show();
                        }else{
                            new AlertView("提示", "安检只支持在线状态", null, new String[]{"确定"},
                                    null, MainActivity.this, AlertView.Style.Alert, MainActivity.this)
                                    .show();
                        }

                        break;
                    case 3:
                        Intent rectifty = new Intent(MainActivity.this, RectiftyListActivity.class);
                        startActivity(rectifty);
                        break;
                    case 4:
                        Intent count = new Intent(MainActivity.this, CountActivity.class);
                        startActivity(count);
                        break;
                    case 5:
                        new UpLoadHistoryTask(MainActivity.this).execute();
                        break;


                }
            }
        });
    }
    // 是否退出程序
    private static Boolean isExit = false;
    // 定时触发器
    private static Timer tExit = null;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(choice.isShowing()){
                choice.dismiss();
                return true;
            }else{
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
                    finish();
                    System.exit(0);
                }
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        if (o == choice) {
            Intent i;
            switch (position) {
                case 0:
                    //跳进
                    choice.dismiss();
                    i = new Intent(MainActivity.this, UserLoginNoCardActivity.class);
                    i.putExtra("LR", "2");//上门配送时用户登录
                    startActivity(i);
                    break;
                case 1:
                    i = new Intent(MainActivity.this, UserLoginByReadActivity.class);
                    i.putExtra("LR", "2");//上门配送时用户登录
                    startActivity(i);
                    choice.dismiss();
                    break;
            }
        }
    }

    private void shownDialog(String text) {
        new AlertView("提示", text, null, new String[]{"确定"},
                null, this, AlertView.Style.Alert, this)
                .show();

    }
    /**
     * 检测网络是否连接
     *
     * @return
     */
    private boolean checkNetworkState() {
        boolean flag = false;
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }
}
