package com.hsic.sy.supply.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hsic.sy.supplystationmanager.R;
public class SearchBigPreViewActivity extends Activity{
	private ImageView mImageview;
	private Bitmap decodeFile;
	String path="";
	String fileName="";
	ArrayList<String>	mList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bigpreview);
		mImageview = (ImageView) findViewById(R.id.bigpreview);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = getIntent();
		path = intent.getStringExtra("pathName");
		mList=intent.getStringArrayListExtra("List");
		fileName=path.substring(path.lastIndexOf("/")+1);
		// decodeFile = BitmapFactory.decodeFile(path);
		Bitmap big = big(path);
		mImageview.setImageBitmap(big);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (decodeFile != null && !decodeFile.isRecycled()) {
			decodeFile.recycle();
			decodeFile = null;

		}

		System.gc();
	}

	/**
	 * 
	 * @param path
	 *            ????????????????
	 * @return ????????????bitmap
	 */
	public Bitmap big(String path) {
		Bitmap resizeBmp = BitmapFactory.decodeFile(path);
		return resizeBmp;
	}


	public void click(View v) {
		Intent in = new Intent(this, SearchPicPreviewActivity.class);
		startActivity(in);
		this.finish();
	}

	public void click1(View v) {
		if(mList.size()==1){
			File file=new File(path);
			if(file.exists()){
				file.delete();
			}
			this.finish();
		}else{
			File file=new File(path);
			if(file.exists()){
				file.delete();
			}
			Intent in = new Intent(this, SearchPicPreviewActivity.class);
			startActivity(in);
			this.finish();
		}
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
