package com.hsic.sy.supply.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.hsic.sy.bll.GetDate;
import com.hsic.sy.bll.PathUtil;
import com.hsic.sy.search.bean.ApplicationHelper;
import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.adapter.ImageAdapter;
import com.hsic.sy.bll.GetBasicInfo;


public class KTPicViewActivity extends Activity{
	private ArrayList<String> mList;
	private GridView gridView;
	private ImageAdapter imageAdapter;
	String userid="",deviceid="";
	GetBasicInfo getBasicInfo;//20181019
	private ApplicationHelper application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		gridView = (GridView) findViewById(R.id.gridView1);// 
		getBasicInfo=new GetBasicInfo(KTPicViewActivity.this);
		application=(ApplicationHelper) getApplication();
		deviceid=getBasicInfo.getDeviceID();
		userid=application.getUserExploration().getUserid();
		initData();
		imageAdapter = new ImageAdapter(this, mList);// 
		gridView.setAdapter(imageAdapter);// 
		
		
		gridView.setOnItemClickListener(new OnItemClickListener() {// 

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent in = new Intent(KTPicViewActivity.this,KTBigPreViewActivity.class);
				in.putExtra("pathName", mList.get(arg2));
				in.putStringArrayListExtra("List", mList);
				startActivityForResult(in, 1);//
				KTPicViewActivity.this.finish();
			}
		});
	}

	private void initData() {
		mList = new ArrayList<String>();
		String photoesPattern ="zwkt"+ GetDate.GetDate()+"d"+deviceid +"stuff"+getBasicInfo.getOperationID()+"u" + userid;
		String filePath= PathUtil.getExpInfoImagePath();
		File file = new File(filePath);
		String[] paths = file.list();
		if (paths != null && paths.length > 0) {
			for (int i = 0; i < paths.length; i++) {
				String path = filePath+ paths[i];
				if (path.contains(photoesPattern)) {
					mList.add(path);
				}
			}

		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
