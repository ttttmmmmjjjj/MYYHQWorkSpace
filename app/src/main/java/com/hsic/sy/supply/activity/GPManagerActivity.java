package com.hsic.sy.supply.activity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.hsic.sy.adapter.GPManagerGridAdapter;
import com.hsic.sy.dialoglibrary.OnItemClickListener;
import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.view.MyGridView;

import java.util.Timer;
import java.util.TimerTask;

public class GPManagerActivity extends Activity implements  com.hsic.sy.dialoglibrary.OnItemClickListener, com.hsic.sy.dialoglibrary.OnDismissListener{
//	private Button FullBottle_Input,FullBottle_Output;//满瓶出入库
//	private Button OtherOperation;
//	private Button EmptyBottle_Input,EmptyBottle_Output;//空瓶出入库
	private TextView station,operationMan;
	private MyGridView gridview;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qp_manager);
		InnitView();
	}
	private void InnitView(){
		Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
		toolbar.setTitle("钢瓶管理");
		toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		gridview = (MyGridView) findViewById(R.id.gridview);
		gridview.setAdapter(new GPManagerGridAdapter(this));
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				switch ((int) id) {
					case 0://���ι���
						Intent i=new Intent(GPManagerActivity.this,ReadTag.class);
						//用Bundle携带数据
						Bundle bundle=new Bundle();
						bundle.putString("operationTag", "1");
						i.putExtras(bundle);
						startActivity(i);
						break;
					case 1:
						Intent i1=new Intent(GPManagerActivity.this,ReadTag.class);
						//用Bundle携带数据
						Bundle bundle1=new Bundle();
						bundle1.putString("operationTag", "2");
						i1.putExtras(bundle1);
						startActivity(i1);
						break;
					case 2:
						Intent i2=new Intent(GPManagerActivity.this,ReadTag.class);
						//用Bundle携带数据
						Bundle bundle2=new Bundle();
						bundle2.putString("operationTag", "3");
						i2.putExtras(bundle2);
						startActivity(i2);
						break;
					case 3:
						Intent i3=new Intent(GPManagerActivity.this,ReadTag.class);
						//用Bundle携带数据
						Bundle bundle3=new Bundle();
						bundle3.putString("operationTag", "4");
						i3.putExtras(bundle3);
						startActivity(i3);
						break;
					case 4:
						//满瓶出库调拨
						Intent i4=new Intent(GPManagerActivity.this,TransferSationActivity.class);
						//用Bundle携带数据
						startActivity(i4);
						break;
					case 5:
						Intent i5=new Intent(GPManagerActivity.this,AddFullQPActivity.class);
						//用Bundle携带数据
						startActivity(i5);
						break;
				}
			}
		});

	}
	private void shownDialog(String text){
		new AlertView("提示", text, null, new String[] { "确定" },
				null,this, AlertView.Style.Alert, this)
				.show();
	}
	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub
		finish();
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
