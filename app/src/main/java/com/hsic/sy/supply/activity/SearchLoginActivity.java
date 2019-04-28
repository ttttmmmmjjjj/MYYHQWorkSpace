package com.hsic.sy.supply.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hsic.sy.bll.SaveBasicInfo;
import com.hsic.sy.supplystationmanager.R;

import java.util.Timer;
import java.util.TimerTask;

public class SearchLoginActivity extends Activity implements OnClickListener {
	private Button LoginOk;
	private EditText empid;
	String StuffID = "";
	SaveBasicInfo saveBasicInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_login);
		LoginOk = (Button) this.findViewById(R.id.login_in);
		LoginOk.setOnClickListener(this);
		empid = (EditText) this.findViewById(R.id.stuffId);
		saveBasicInfo=new SaveBasicInfo(SearchLoginActivity.this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_in:
			StuffID = empid.getText().toString();
			if (!StuffID.equals("")) {
				saveBasicInfo.saveRectifyMan(StuffID);
				Intent i = new Intent(this, SearchLoginOk.class);
//				Bundle bundle = new Bundle();
//				bundle.putString("empid", StuffID);
//				i.putExtras(bundle);
				startActivity(i);
				this.finish();
			} else {
				Toast.makeText(getApplicationContext(), "������Ա����",
						Toast.LENGTH_SHORT).show();
			}

			break;
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
