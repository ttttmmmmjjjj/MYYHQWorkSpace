package com.hsic.sy.czqpapplication;

import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import bll.VeDate;
import db.QueryDB;
import nfc.GetTag;

public class NFCOnlyActivity extends Activity {
	/**
	 * ��ƿ��������
	 */
	private TextView tv_userregincode, tv_gh, tv_company, tv_qptype, tv_media,
			tv_manufacture, tv_madeDate, tv_nextCheckDate, tv_checkResult;
	VeDate veDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfconly);
		setNFC();
		Innit();
		veDate=new VeDate();
	}

	private void Innit() {
		tv_userregincode = (TextView) this.findViewById(R.id.tv_userregincode);
		tv_gh = (TextView) this.findViewById(R.id.tv_gh);
		tv_company = (TextView) this.findViewById(R.id.tv_company);
		tv_qptype = (TextView) this.findViewById(R.id.tv_qptype);
		tv_media = (TextView) this.findViewById(R.id.tv_media);
		tv_manufacture = (TextView) this.findViewById(R.id.tv_manufacture);// ��������
		tv_madeDate = (TextView) this.findViewById(R.id.tv_madeDate);
		tv_nextCheckDate = (TextView) this.findViewById(R.id.tv_nextCheckDate);
		tv_userregincode.setText("");
		tv_gh.setText("");
		tv_company.setText("");
		tv_qptype.setText("");
		tv_media.setText("");
		tv_manufacture.setText("");
		tv_madeDate.setText("");
		tv_nextCheckDate.setText("");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			mAdapter.disableForegroundDispatch(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mAdapter != null) {
			if (!mAdapter.isEnabled()) {
				showDiag();
			}
		}

		try {
			mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
					mTechLists);
		} catch (Exception e) {
			// TODO: handle exception
		}

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

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		resolveIntent(intent);
	}

	NfcAdapter mAdapter;
	private static PendingIntent mPendingIntent;
	private static IntentFilter[] mFilters;
	private static String[][] mTechLists;

	// NFCæ¨¡å
	private void setNFC() {
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mAdapter != null) {
			if (!mAdapter.isEnabled()) {
				showDiag();
			}
			mPendingIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, getClass())
							.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
			// Setup an intent filter for all MIME based dispatches
			IntentFilter ndef = new IntentFilter(
					NfcAdapter.ACTION_TECH_DISCOVERED);

			try {
				ndef.addDataType("*/*");
			} catch (MalformedMimeTypeException e) {
				throw new RuntimeException("fail", e);
			}
			mFilters = new IntentFilter[] { ndef, };

			// Setup a tech list for all NfcF tags
			mTechLists = new String[][] {
					new String[] { MifareClassic.class.getName() },
					new String[] { NfcA.class.getName() },
					new String[] { NfcB.class.getName() },
					new String[] { NfcF.class.getName() },
					new String[] { NfcV.class.getName() },
					new String[] { Ndef.class.getName() },
					new String[] { NdefFormatable.class.getName() },
					new String[] { MifareUltralight.class.getName() },
					new String[] { IsoDep.class.getName() } };

			Intent intent = getIntent();
			resolveIntent(intent);
		}
	}

	void resolveIntent(Intent intent) {
		try {
			String action = intent.getAction();
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
				GetTag getTag = new GetTag();
				String[] GetData = new String[2];
				GetData = getTag.getTag2(intent,"",21);
				if (GetData.length >= 2) {
					sortData(GetData);
				} else {

				}
			}
		} catch (Exception ex) {
			ex.toString();
		}

	}

	private String UserRegionCode, GH, CQDW, CheckDate, Media, NextCheckDate,
			MadeDate, GPType;

	private void sortData(String[] GetData) {
		QueryDB query = new QueryDB(this);
		String data = GetData[1];
		GPType = data.substring(16, 18);
		GPType = query.getGPLX(GPType);
		CQDW = data.substring(18, 24);
		CQDW = query.getCZDW(CQDW.substring(2, 6));
		UserRegionCode = data.substring(24, 32);
		Media = data.substring(32, 36);
		String media_temp = String.valueOf(Integer.parseInt(Media, 16));
		Media = query.getMediaInfo(media_temp);
		GH = data.substring(36, 60);
		String GH_temp = toStringHex2(GH);
		GH = GH_temp;
		MadeDate = data.substring(322, 330);
		MadeDate = DateString(MadeDate);
		CheckDate = data.substring(330, 338);
		CheckDate = DateString(CheckDate);
		NextCheckDate = data.substring(348, 356);
		NextCheckDate = DateString(NextCheckDate);

		Log.e("====", NextCheckDate);
		if(veDate.CompareDate(NextCheckDate)){
			tv_nextCheckDate.setTextColor(Color.RED);
		}
		tv_userregincode.setText(UserRegionCode);
		tv_gh.setText(GH);
		tv_company.setText(CQDW);
		tv_qptype.setText(GPType);
		tv_media.setText(Media);
		tv_madeDate.setText(MadeDate);
		tv_manufacture.setText(CheckDate);
		tv_nextCheckDate.setText(NextCheckDate);
	}

	private String DateString(String date) {
		String ret = "";
		ret = date.substring(0, 4) + "/" + date.substring(4, 6) + "/"
				+ date.substring(6, 8);
		return ret;
	}

	public static String toStringHex2(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "ASCII");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	private void showDiag() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��ʾ");
		builder.setMessage("NFC�豸δ�򿪣��Ƿ�ȥ�򿪣�");
		builder.setPositiveButton("��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
				startActivity(intent);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
