package com.hsic.sy.supply.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.hsic.sy.supplystationmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class KTBigPreViewActivity extends Activity {
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
		Intent in = new Intent(this, KTPicViewActivity.class);
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
			Intent in = new Intent(this, KTPicViewActivity.class);
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
			finish();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}
