package com.hsic.sy.supply.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.Employee;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.Secuser;
import com.hsic.sy.bll.ActivityUtil;
import com.hsic.sy.bll.SaveBasicInfo;
import com.hsic.sy.constant.Constant;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.supply.listener.EmployeeLoginListener;
import com.hsic.sy.supply.listener.StationLoginListener;
import com.hsic.sy.supply.task.EmployeeLoginTask;
import com.hsic.sy.supply.task.StationLoginTask;
import com.hsic.sy.supplystationmanager.R;

import java.util.Timer;

/**
 * �����¼
 * @author Administrator
 *
 */
public class LoginByInputActivity extends Activity implements StationLoginListener,
		EmployeeLoginListener,
		com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
	private  EditText edt_stuffid,edt_password;
	private Button login_in;
	private String stuffid,password;
	SaveBasicInfo savaBasicInfo;//
	private RadioGroup rd_group;
	private RadioButton rd_stationMode, rd_stuffMode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		savaBasicInfo=new SaveBasicInfo(LoginByInputActivity.this);
		rd_group = (RadioGroup) findViewById(R.id.rd_group);
		rd_stationMode = (RadioButton) findViewById(R.id.rd_stationMode);
		rd_stuffMode = (RadioButton) findViewById(R.id.rd_stuffMode);
		rd_stationMode.setChecked(true);
		stuffid="";
		password="";
		Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
		toolbar.inflateMenu(R.menu.login_toolbar_menu);
		toolbar.setTitle("登录");
		toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				int menuItemId = item.getItemId();
				if(menuItemId==R.id.action_settings){
					TurnSetting();
				}
				return false;
			}
		});
		edt_stuffid=(EditText) this.findViewById(R.id.edt_stuffid);
		edt_password=(EditText) this.findViewById(R.id.edt_password);
		login_in=(Button) this.findViewById(R.id.signup_Btna);
		login_in.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stuffid="";
				password="";
				stuffid=edt_stuffid.getText().toString();
				stuffid=stuffid.toUpperCase();
				password=edt_password.getText().toString();
				if(TextUtils.isEmpty(stuffid)){
					shownMess("输入员工号!");
					return ;
				}
				savaBasicInfo.saveRectifyMan(stuffid);
				if(rd_stationMode.isChecked()){
					if(TextUtils.isEmpty(password)){
						shownMess("输入密码!");
						return ;
					}
					//登录
					savaBasicInfo.saveLoginStuffMode(Constant.LoginByStation);
					savaBasicInfo.saveLoginMode("2");//手输登录
					Secuser secuser=new Secuser();
					secuser.setPwd(password);
					secuser.setUserno(stuffid);
					String request0=JSONUtils.toJsonWithGson(secuser);
					HsicMessage EmpID = new HsicMessage();
					EmpID.setRespMsg(request0);
					String request=JSONUtils.toJsonWithGson(EmpID);
					task(request);
				}else{
					savaBasicInfo.saveLoginStuffMode(Constant.LoginByStuff);
					savaBasicInfo.saveOperationID(stuffid);
					Employee secuser=new Employee();
					secuser.setEmpid(stuffid);
					String request0=JSONUtils.toJsonWithGson(secuser);
					HsicMessage EmpID = new HsicMessage();
					EmpID.setRespMsg(request0);
					String request=JSONUtils.toJsonWithGson(EmpID);
					task2(request);
				}


			}
			
		});
	}
	private void TurnSetting() {
		ActivityUtil.JumpToAdvConfig(this);
	}
	private void task(String requestdata){
		new StationLoginTask(LoginByInputActivity.this,LoginByInputActivity.this).execute(requestdata);
	}
	private void task2(String requestdata){
		new EmployeeLoginTask(LoginByInputActivity.this,LoginByInputActivity.this).execute(requestdata);
	}
	@Override
	public void StationLoginListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if(tag.getRespCode()==0){
			Employee secuser=new Employee();
			secuser=JSONUtils.toObjectWithGson(tag.getRespMsg(),Employee.class);
			savaBasicInfo.saveStationID(secuser.getStationid());
			savaBasicInfo.saveOperationID(secuser.getEmpid());
			savaBasicInfo.saveStuffTagID(secuser.getCardcode());
			Intent ok = new Intent(LoginByInputActivity.this,
					LoginOkActivity.class);
			startActivity(ok);
			this.finish();
		}else{
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, this, AlertView.Style.Alert, this)
					.show();
		}
	}
	private void shownMess(String Mess){
		new AlertView("提示", Mess, null, new String[] { "确定" },
				null, this, AlertView.Style.Alert, this)
				.show();
	}
	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void EmployeeLoginListenerEnd(HsicMessage tag) {
		if(tag.getRespCode()==0){
			Employee secuser=new Employee();
			secuser=JSONUtils.toObjectWithGson(tag.getRespMsg(),Employee.class);
			savaBasicInfo.saveStationName(secuser.getStationName());
			savaBasicInfo.saveStationID(secuser.getStationid());
			savaBasicInfo.saveOperationID(secuser.getEmpid());
			savaBasicInfo.saveStuffTagID(secuser.getCardcode());
			Intent ok = new Intent(LoginByInputActivity.this,
					LoginOkActivity.class);
			startActivity(ok);
			this.finish();
		}else{
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, this, AlertView.Style.Alert, this)
					.show();
		}
	}
}
