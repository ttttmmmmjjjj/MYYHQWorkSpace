package com.hsic.activity;
import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.bean.ApplicationHelper;

/**
 * 20180507 
 * ��Ƭɾ���ɹ���ɾ����Ƭ��Ӧ�������Ϣ
 * @author Administrator
 *
 */
public class BigPreViewActivity extends Activity {
	private ImageView mImageview;
	private Bitmap decodeFile;
	String path="";
	String fileName="";
	private ApplicationHelper applicationHelper;
	ArrayList<String>	mList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bigpreview);
		mImageview = (ImageView) findViewById(R.id.bigpreview);
		applicationHelper = (ApplicationHelper) getApplication();
//		Log.e("truckNoId", truckNoId);
//		Log.e("saleid", saleid);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = getIntent();
		path = intent.getStringExtra("pathName");
		mList=intent.getStringArrayListExtra("List");
		fileName=path.substring(path.lastIndexOf("/")+1);
		Log.e("fileName", fileName);
		// decodeFile = BitmapFactory.decodeFile(path);
		Bitmap big = big(path);
		mImageview.setImageBitmap(big);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (decodeFile != null && !decodeFile.isRecycled()) {
			// ���ղ�����Ϊnull
			decodeFile.recycle();
			decodeFile = null;

		}

		System.gc();
	}

	/**
	 * 
	 * @param path
	 *            ��Ƭ·��
	 * @return ����bitmap
	 */
	public Bitmap big(String path) {
			
		// Display d=getWindowManager().getDefaultDisplay();
		// int height = d.getHeight();
		// int width = d.getWidth();
		// Log.i("width", String.valueOf(width));
		// Log.i("height", String.valueOf(height));
		// int bmpwidth=bitmap.getWidth();
		// int bmpheight=bitmap.getHeight();
		// Log.i("bmpwidth", String.valueOf(bmpwidth));
		// Log.i("bmpheight", String.valueOf(bmpheight));
		// float scaleWidth=width/bmpheight;
		// float scaleHeight=height/bmpwidth;
		// Matrix matrix = new Matrix();
		// matrix.postScale(scaleWidth, scaleHeight); // ���Ϳ�Ŵ���С�ı���
		// Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
		// bitmap.getWidth(),
		// bitmap.getHeight(), matrix, true);
		Bitmap resizeBmp = BitmapFactory.decodeFile(path);
		return resizeBmp;
	}

	/**
	 * 
	 * @param v
	 *            ����Ԥ������
	 */
	public void click(View v) {
		Intent in = new Intent(this, PreviewActivity.class);
		startActivity(in);
		this.finish();
	}

	/**
	 * 
	 * @param v
	 * ����Ԥ����������ǽ��밲ȫѲ��ҳ��
	 */
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
			Intent in = new Intent(this, PreviewActivity.class);
			startActivity(in);
			this.finish();
		}
//		ApplicationHelper application=(ApplicationHelper) getApplication();
//		UserXJInfo userXJInfo=new UserXJInfo();
//		userXJInfo=application.getUserXJInfo();
//		String imagePath=userXJInfo.getImagepath();
//		List <String> list=new ArrayList<String>();
//		if(imagePath.contains(",")){
//			String [] pathArrayU=imagePath.split(",");
//			if(pathArrayU!=null&&pathArrayU.length>0){
//				for(int i=0;i<pathArrayU.length;i++){
//					list.add(pathArrayU[i]);
//				}
//			}	
//		}else{
//			list.add(imagePath);
//		}
//		File file = new File(path);
//		if (file.exists()) {
//			if(list.contains(fileName)){
//				list.remove(fileName);
//			}
//			file.delete();
//		}
//		StringBuffer stringBuffer=new StringBuffer();
//		if(list.size()<=0){
//			userXJInfo.setImagepath("");
//			application.setUserXJInfo(userXJInfo);
//			this.finish();
//		}else{
//			for(int i=0;i<list.size();i++){
//				if(stringBuffer.toString().length()==0){
//					stringBuffer.append(list.get(i));
//				}else{
//					stringBuffer.append(","+list.get(i));
//				}
//			}
//			userXJInfo.setImagepath(stringBuffer.toString());
//			Intent in = new Intent(this, PreviewActivity.class);
//			in.putExtra("delete", "1");
//			startActivity(in);
//			this.finish();
//		}

	}
}
