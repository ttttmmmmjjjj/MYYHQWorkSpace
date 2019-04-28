package com.hsic.sy.supply.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.sy.adapter.QueryFullBottleAdapter;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supplystationmanager.R;

/***
 * 20181019
 * �Ż������豸������Ϣ
 */
public class QueryFullBottleActivity extends Activity{
	String StationID;
	private ListView FullBottle;
	private List<String> data_List;
	List<TruckInfo> mDatas;
	String UseRegCode;
	String liscense;
	TextView Title;
	GetBasicInfo getBasicInfo;//20181019
	private QueryFullBottleAdapter mAdapter = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullbottle);
		FullBottle=(ListView)this.findViewById(R.id.fullbottle_list);
		Title=(TextView) this.findViewById(R.id.textView1);
		getBasicInfo=new GetBasicInfo(QueryFullBottleActivity.this);
		StationID=getBasicInfo.getStationID();
		data_List=new  ArrayList<String>();
		Bundle bundle = this.getIntent().getExtras();
		String[] output=bundle.getStringArray("UseRegCode");
		UseRegCode=output[1];
		liscense=output[0];
		Title.setText("满瓶信息"+"["+liscense+"]");
		String[] fullBottle=UseRegCode.split(",");
		for(int i=0;i<fullBottle.length;i++){
			String temp=fullBottle[i];
			data_List.add(temp.substring(0, 8));
		}
		FullBottle.setAdapter(new QueryFullBottleAdapter(getBaseContext(), data_List));
	}
	// 是否退出程序
	private static Boolean isExit = false;
	// 定时触发器
	private static Timer tExit = null;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	

}
