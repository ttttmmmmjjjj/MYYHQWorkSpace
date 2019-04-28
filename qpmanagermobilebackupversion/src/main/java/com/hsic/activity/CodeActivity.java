package com.hsic.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.bll.Code;

public class CodeActivity extends Activity implements OnClickListener{
	private ImageView iv_showCode;  
	private EditText et_phoneCode; 
	//��������֤��  
	private String realCode; 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code);
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay();
		WindowManager.LayoutParams p = getWindow().getAttributes();
		p.height = (int) (d.getHeight() * 0.3);
		p.width = (int) (d.getWidth() * 1);
		getWindow().setAttributes(p);

		et_phoneCode = (EditText) findViewById(R.id.et_phoneCodes); 
		Button but_toSetCode = (Button) findViewById(R.id.but_forgetpass_toSetCodes);  
		but_toSetCode.setOnClickListener(this);  
		iv_showCode = (ImageView) findViewById(R.id.iv_showCode);  
		//����֤����ͼƬ����ʽ��ʾ����  
		iv_showCode.setImageBitmap(Code.getInstance().createBitmap());  
		realCode = Code.getInstance().getCode().toLowerCase();
		iv_showCode.setOnClickListener(this); 
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			int RESULT_CODE = 13;
			Intent intent = new Intent();
			intent.putExtra("result", 3);
			setResult(RESULT_CODE, intent);
			this.finish(); 
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {  

		case R.id.but_forgetpass_toSetCodes:  
			String phoneCode = et_phoneCode.getText().toString().toLowerCase();  
			String msg = "���ɵ���֤�룺"+realCode+"�������֤��:"+phoneCode;  
			if (phoneCode.equals(realCode)) { 
				//���÷�����ر�ҳ��
				int RESULT_CODE = 12;
				Intent intent = new Intent();
				intent.putExtra("result", 3);
				setResult(RESULT_CODE, intent);
				this.finish();  
			} else {  
				Toast.makeText(CodeActivity.this, phoneCode + "��֤�����", Toast.LENGTH_SHORT).show();  
			}  
			break;  
		}  
	}




}
