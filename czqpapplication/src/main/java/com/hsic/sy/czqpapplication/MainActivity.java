package com.hsic.sy.czqpapplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btn_query,btn_nfc,btn_bfconly;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_memu);
		btn_query=(Button) this.findViewById(R.id.btn_query);
		btn_nfc=(Button) this.findViewById(R.id.btn_nfc);
		btn_bfconly=(Button) this.findViewById(R.id.btn_bfconly);
		btn_query.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,InputActivity.class);
				startActivity(i);
			}
		});
		btn_nfc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,NFCActivity.class);
				startActivity(i);
			}
		});
		btn_bfconly.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,NFCOnlyActivity.class);
				startActivity(i);
			}
		});
	}

}
