package com.hsic.sy.czqpapplication;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import db.QueryDB;
import spinerpop.AbstractSpinerAdapter;
import spinerpop.CompanyModel;
import spinerpop.SpinerPopWindow;

public class InputActivity extends Activity implements
		AbstractSpinerAdapter.IOnItemSelectListener {
	private SpinerPopWindow mSpinerPopWindow;
	// private List<CompanyModel> nameList = new ArrayList<CompanyModel>();
	private List<String> nameList = new ArrayList<String>();
	private EditText edt_company, edt_userReginCode;
	CompanyModel companyModel;
	QueryDB queryDB;
	private Button btn_query;
	RelativeLayout ll_lowwarn1;
	private TextView tv_currentCompany;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		edt_company = (EditText) this.findViewById(R.id.edt_company);
		edt_userReginCode = (EditText) this
				.findViewById(R.id.edt_userReginCode);
		edt_userReginCode.setText("");
		btn_query = (Button) this.findViewById(R.id.btn_query);
		ll_lowwarn1 = (RelativeLayout) this.findViewById(R.id.ll_lowwarn1);
		edt_company.setFocusable(false);
		tv_currentCompany=(TextView) this.findViewById(R.id.tv_currentCompany);
		String[] CN=getResources().getStringArray(R.array.CN);
		for(int i=0;i<CN.length;i++){
			nameList.add(CN[i]);
		}
		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.refreshData(nameList, 0);
		mSpinerPopWindow.setItemListener(this);

		edt_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(edt_company.getWindowToken(), 0);
				mSpinerPopWindow.refreshData(nameList, 0);
				showSpinWindow();
			}
		});
		btn_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userRegioncode = edt_userReginCode.getText().toString();
				if (companyCode.equals("")) {
					Toast.makeText(getBaseContext(), "�����뵥λ����",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (userRegioncode.equals("")) {
					Toast.makeText(getBaseContext(), "������Ԥ����",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					if (userRegioncode.length() != 8) {
						Toast.makeText(getBaseContext(), "Ԥ�������Ϊ8λ",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(edt_userReginCode.getWindowToken(),
						0);
				mSpinerPopWindow.refreshData(nameList, 0);
				Intent i = new Intent(InputActivity.this, NFCActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("DWCode", companyCode);
				bundle.putString("UserRegioncode", userRegioncode);
				bundle.putString("CompanyName", companyName);
				i.putExtras(bundle);
				startActivity(i);
//				InputActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.finish();
			return super.onKeyDown(keyCode, event);
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void showSpinWindow() {
		mSpinerPopWindow.setWidth(edt_company.getWidth());
		mSpinerPopWindow.showAsDropDown(edt_company, 0, 0);
	}

	String companyCode = "";
	String companyName = "";
	String userRegioncode = "";

	@Override
	public void onItemClick(int pos) {
		// TODO Auto-generated method stub
		// Log.e("���", "λ��"+pos);
		if (pos >= 0 && pos <= nameList.size()) {
			String[] CompanyName=getResources().getStringArray(R.array.companyName);
			String[] CompanyCode=getResources().getStringArray(R.array.companyCode);
			String company =CompanyName[pos];
			companyName = company;
			companyModel = new CompanyModel();
			companyCode=CompanyCode[pos];
//			companyCode = "31" + queryDB.getCZDWName(company);
			companyModel.setCode("31" + companyCode);
			companyCode="31" + companyCode;
			edt_company.setText(company);
			 Log.e("��λName", company);
			 Log.e("��λCode", companyCode);
			mSpinerPopWindow.dismiss();
			edt_userReginCode.setEnabled(true);
			tv_currentCompany.setText("��ǰ��λ"+":"+company);
		}
	}

}
