package com.hsic.sy.supply.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.view.DispatchListView;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ListViewActivity extends  Activity {
    private DispatchListView mListView = null;
    private List<String> mDatas = null;
    private ArrayAdapter mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mListView = (DispatchListView) findViewById(R.id.id_listview);
        mDatas = new ArrayList<String>(Arrays.asList("北京市", "天津市", "上海市", "重庆市", "安徽省", "福建省",
                "甘肃省", "广东省", "贵州省", "海南省", "河北省","河南省","黑龙江省","湖北省","湖南省","吉林省"));
        mAdapter = new ArrayAdapter(ListViewActivity.this,android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
        //设置列表项Item删除按钮的点击监听事件
        mListView.setDelButtonClickListener(new DispatchListView.DelButtonClickListener() {
            @Override
            public void onDelClick(int position) {
                Toast.makeText(ListViewActivity.this, "删除：" + position + " : " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                //从列表中移除当前项
                mAdapter.remove(mAdapter.getItem(position));
            }
        });
        //设置列表项Item点击监听事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(ListViewActivity.this, "您点击的是第 " + arg2 + " 项: " + mAdapter.getItem(arg2), Toast.LENGTH_SHORT).show();
            }
        });
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
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
	

}
