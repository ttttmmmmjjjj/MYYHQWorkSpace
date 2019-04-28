package com.hsic.activity;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.bean.ApplicationHelper;
import com.hsic.bll.GetBasicInfo;
import com.hsic.bll.PathUtil;
import com.hsic.picture.ImageAdapter;


public class PreviewActivity extends Activity {
	private ArrayList<String> mList;
	private GridView gridView;
	private ImageAdapter imageAdapter;
	private ApplicationHelper application;
	String saleId="",deviceid="",photoesPattern="";
	GetBasicInfo getBasicInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		application = (ApplicationHelper) getApplication();
		getBasicInfo=new GetBasicInfo(PreviewActivity.this);
		deviceid=getBasicInfo.getDeviceID();
		saleId=application.getUserXJInfo().getSaleid();
//		TruckNoId=application.getTelSaleInfo().getTruckNoID();
		initData();
		gridView = (GridView) findViewById(R.id.gridView1);// �ҵ��ؼ�
		imageAdapter = new ImageAdapter(this, mList);// ����������
		gridView.setAdapter(imageAdapter);// ����������
		gridView.setOnItemClickListener(new OnItemClickListener() {// ����¼�
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent in = new Intent(PreviewActivity.this,BigPreViewActivity.class);
				in.putExtra("pathName", mList.get(position));
				in.putStringArrayListExtra("List", mList);
				startActivityForResult(in, 1);// �����ͼԤ��ҳ��
				PreviewActivity.this.finish();
			}
		});
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		mList = new ArrayList<String>();
		String photoesPattern =deviceid+ "s" + saleId ;
		String filePath= PathUtil.getImagePath();
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
}
