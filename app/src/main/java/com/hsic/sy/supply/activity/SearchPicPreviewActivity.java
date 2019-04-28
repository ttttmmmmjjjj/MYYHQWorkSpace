package com.hsic.sy.supply.activity;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.adapter.ImageAdapter;
import com.hsic.sy.bll.DeviceIDInfo;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.PathUtil;
import com.hsic.sy.supplystationmanager.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class SearchPicPreviewActivity extends Activity{
	private ArrayList<String> mList;
	private GridView gridView;
	private ImageAdapter imageAdapter;
	String userid="",deviceid="",id="";
	GetBasicInfo getBasicInfo;//20181019
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		getBasicInfo=new GetBasicInfo(SearchPicPreviewActivity.this);
		gridView = (GridView) findViewById(R.id.gridView1);// �ҵ��ؼ�
		deviceid= getBasicInfo.getDeviceID();
		try{
			Bundle bundle = this.getIntent().getExtras();
			userid=bundle.getString("userid");
			id=bundle.getString("id");
		}catch(Exception ex){
			ex.toString();
		}
		initData();
		imageAdapter = new ImageAdapter(this, mList);//
		gridView.setAdapter(imageAdapter);//
		gridView.setOnItemClickListener(new OnItemClickListener() {// ����¼�

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent in = new Intent(SearchPicPreviewActivity.this,SearchBigPreViewActivity.class);
				in.putExtra("pathName", mList.get(arg2));
				in.putStringArrayListExtra("List", mList);
				startActivityForResult(in, 1);// �����ͼԤ��ҳ��
				SearchPicPreviewActivity.this.finish();
			}
		});
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		mList = new ArrayList<String>();
		String photoesPattern =id+"d"+deviceid+ "u" + userid;
		String filePath= PathUtil.getImagePath();
		File file = new File(filePath);
		String[] paths = file.list();
//		Log.e("aa", JSONUtils.toJsonWithGson(paths));
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
