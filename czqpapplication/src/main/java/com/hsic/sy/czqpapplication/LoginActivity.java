package com.hsic.sy.czqpapplication;


import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.List;

public class LoginActivity extends Activity {
private Button Login;
RelativeLayout Setting;
private EditText edt_id,edt_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Login=(Button) this.findViewById(R.id.btn_login);
		edt_id=(EditText) this.findViewById(R.id.edt_id);
		edt_password=(EditText) this.findViewById(R.id.edt_password);
		Setting = (RelativeLayout) this.findViewById(R.id.set);
		Setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						AdvConfigActivity.class);
				startActivity(intent);
			}
		});
		Login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i=new Intent(LoginActivity.this,MainActivity.class);
//				startActivity(i);
				boolean isHave=
				hasApplication(LoginActivity.this,"com.hsic.tmj.myyhqworkspace");
				if(!isHave){
					return ;
				}
				ComponentName componetName = new ComponentName(
						//这个是另外一个应用程序的包名
						"com.hsic.tmj.myyhqworkspace",
						//这个参数是要启动的Activity
						"com.hsic.tmj.myyhqworkspace.LoginActivity");
//        Intent intent= new Intent("chroya.foo");
				Intent intent= new Intent();
				//我们给他添加一个参数表示从apk1传过去的
				Bundle bundle = new Bundle();
				bundle.putString("arge1", "这是跳转过来的！来自apk1");
				intent.putExtras(bundle);
				intent.setComponent(componetName);
				startActivity(intent);
				
			}
		});
	}

	/**
	 * 判断是否安装该应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean hasApplication(Context context, String packageName){
		PackageManager packageManager = context.getPackageManager();

		//获取系统中安装的应用包的信息
		List<PackageInfo> listPackageInfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < listPackageInfo.size(); i++) {
			if(listPackageInfo.get(i).packageName.equalsIgnoreCase(packageName)){
				return true;
			}
		}
		return false;

	}


}
