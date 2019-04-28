package com.hsic.sy.czqpapplication;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
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
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import bean.HsicMessage;
import bean.QPSale;
import bean.QPSale_;
import bean.QPXX;
import bean.QPXXALL;
import bll.VeDate;
import db.QueryDB;
import listener.GetBasicInfoTaskListener;
import listener.GetQPSInfoTaskListener;
import nfc.GetTag;
import task.GetBasicInfoTask;
import task.GetQPSInfoTask;

public class NFCActivity extends Activity implements GetBasicInfoTaskListener,
		GetQPSInfoTaskListener {
	/**
	 * ��ƿ��������
	 */
	private TextView tv_userregincode, tv_gh, tv_company, tv_qptype, tv_media,
	tv_manufacture, tv_madeDate, tv_nextCheckDate, tv_checkResult;
	/**
	 * ������װ��Ϣ
	 */
	private TextView tv_receiveDate, tv_receiveFromStation, tv_fullDate,
	tv_fullWeight, tv_sendDate, tv_sendToStation;
	/**
	 * ��ƿ������Ϣ
	 */
	private TextView tv_f_custormName, tv_f_custormType, tv_f_custormCountry,
	tv_f_custormAddress, tv_f_supplyStation, tv_f_truckNO,
	tv_f_saleMan, tv_f_saleID, tv_f_saleDate, tv_f_saleFinishDate,
	tv_f_goods, tv_f_saleGPS, tv_f_fullNumber, tv_f_emptyNumber,
	tv_f_searchResult;
	/**
	 * ��ƿ������Ϣ
	 */
	private TextView tv_e_custormName, tv_e_custormType, tv_e_custormCountry,
	tv_e_custormAddress, tv_e_supplyStation, tv_e_truckNO,
	tv_e_saleMan, tv_e_saleID, tv_e_saleDate, tv_e_saleFinishDate,
	tv_e_goods, tv_e_saleGPS, tv_e_fullNumber, tv_e_emptyNumber,
	tv_e_searchResult;
	VeDate veDate;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		innitView();
		setNFC();
		veDate=new VeDate();
		try {
			//����
			Bundle bundle = this.getIntent().getExtras();
			if (bundle != null) {
				String DWCode = bundle.getString("DWCode");
				String UserRegioncode = bundle.getString("UserRegioncode");
				String CompanyName = bundle.getString("CompanyName");
				CQDWName = CompanyName;
//				Log.e("===", "03" + DWCode + UserRegioncode);
				GetBasicInfoTask basicTask = new GetBasicInfoTask(this, this,DWCode,UserRegioncode);
				basicTask.execute("03" + DWCode + UserRegioncode);
				if (DWCode.equals("311910") || DWCode.equals("311607")
						|| DWCode.equals("311707") || DWCode.equals("312010")
						|| DWCode.equals("312005") || DWCode.equals("311211")||DWCode.equals("311414")) {
					InitPSView();
					GetQPSInfoTask saleTask = new GetQPSInfoTask(NFCActivity.this, NFCActivity.this,DWCode,UserRegioncode);
					saleTask.execute(DWCode, UserRegioncode);
				} else {
					InitPSView();
				}
			}

		} catch (Exception ex) {

		}

	}

	private void innitView() {
		// ��ƿ��������
		tv_userregincode = (TextView) this.findViewById(R.id.tv_userregincode);
		tv_gh = (TextView) this.findViewById(R.id.tv_gh);
		tv_company = (TextView) this.findViewById(R.id.tv_company);
		tv_qptype = (TextView) this.findViewById(R.id.tv_qptype);
		tv_media = (TextView) this.findViewById(R.id.tv_media);
		tv_manufacture = (TextView) this.findViewById(R.id.tv_manufacture);
		tv_madeDate = (TextView) this.findViewById(R.id.tv_madeDate);
		tv_nextCheckDate = (TextView) this.findViewById(R.id.tv_nextCheckDate);
		tv_checkResult = (TextView) this.findViewById(R.id.tv_checkResult);

		// ������װ��Ϣ
		tv_receiveDate = (TextView) this.findViewById(R.id.tv_receiveDate);
		tv_receiveFromStation = (TextView) this
				.findViewById(R.id.tv_receiveFromStation);
		// tv_fullDate=(TextView) this.findViewById(R.id.tv_fullDate);
		// tv_fullWeight=(TextView) this.findViewById(R.id.tv_fullWeight);
		tv_sendDate = (TextView) this.findViewById(R.id.tv_sendDate);
		tv_sendToStation = (TextView) this.findViewById(R.id.tv_sendToStation);
		InitBasicView();
		// ��ƿ������Ϣ
		tv_f_custormName = (TextView) this.findViewById(R.id.tv_f_custormName);
		tv_f_custormType = (TextView) this.findViewById(R.id.tv_f_custormType);
		tv_f_custormCountry = (TextView) this
				.findViewById(R.id.tv_f_custormCountry);
		tv_f_custormAddress = (TextView) this
				.findViewById(R.id.tv_f_custormAddress);
		tv_f_supplyStation = (TextView) this
				.findViewById(R.id.tv_f_supplyStation);
		tv_f_truckNO = (TextView) this.findViewById(R.id.tv_f_truckNO);
		tv_f_saleMan = (TextView) this.findViewById(R.id.tv_f_saleMan);
		tv_f_saleID = (TextView) this.findViewById(R.id.tv_f_saleID);
		tv_f_saleDate = (TextView) this.findViewById(R.id.tv_f_saleDate);
		tv_f_saleFinishDate = (TextView) this
				.findViewById(R.id.tv_f_saleFinishDate);
		tv_f_goods = (TextView) this.findViewById(R.id.tv_f_goods);
		tv_f_saleGPS = (TextView) this.findViewById(R.id.tv_f_saleGPS);
		tv_f_fullNumber = (TextView) this.findViewById(R.id.tv_f_fullNumber);
		tv_f_emptyNumber = (TextView) this.findViewById(R.id.tv_f_emptyNumber);
		tv_f_searchResult = (TextView) this
				.findViewById(R.id.tv_f_searchResult);

		// ��ƿ������Ϣ
		tv_e_custormName = (TextView) this.findViewById(R.id.tv_e_custormName);
		tv_e_custormType = (TextView) this.findViewById(R.id.tv_e_custormType);
		tv_e_custormCountry = (TextView) this
				.findViewById(R.id.tv_e_custormCountry);
		tv_e_custormAddress = (TextView) this
				.findViewById(R.id.tv_e_custormAddress);
		tv_e_supplyStation = (TextView) this
				.findViewById(R.id.tv_e_supplyStation);
		tv_e_truckNO = (TextView) this.findViewById(R.id.tv_e_truckNO);
		tv_e_saleMan = (TextView) this.findViewById(R.id.tv_e_saleMan);
		tv_e_saleID = (TextView) this.findViewById(R.id.tv_e_saleID);
		tv_e_saleDate = (TextView) this.findViewById(R.id.tv_e_saleDate);
		tv_e_saleFinishDate = (TextView) this
				.findViewById(R.id.tv_e_saleFinishDate);
		tv_e_goods = (TextView) this.findViewById(R.id.tv_e_goods);
		tv_e_saleGPS = (TextView) this.findViewById(R.id.tv_e_saleGPS);
		tv_e_fullNumber = (TextView) this.findViewById(R.id.tv_e_fullNumber);
		tv_e_emptyNumber = (TextView) this.findViewById(R.id.tv_e_emptyNumber);
		tv_e_searchResult = (TextView) this
				.findViewById(R.id.tv_e_searchResult);

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
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.finish();
			return super.onKeyDown(keyCode, event);
		} else {
			return super.onKeyDown(keyCode, event);
		}
		// TODO Auto-generated method stub

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
				GetData = getTag.getTag(intent, 0, 4);
				if (GetData != null) {
					if (GetData.length >= 2) {
						sortData(GetData);
					} else {
						// ��ȡ����
					}
				}

			}
		} catch (Exception ex) {
			ex.toString();
		}

	}

	private String UserRegionCode, GH, CQDW, CQDWName, CheckDate, Media,
	NextCheckDate, MadeDate, GPType;

	private void sortData(String[] GetData) {
		QueryDB query = new QueryDB(this);
		String data = GetData[1];
		GPType = data.substring(16, 18);
		CQDW = data.substring(18, 24); // ��λ����
		CQDWName = query.getCZDW(CQDW.substring(2, 6));
		UserRegionCode = data.substring(24, 32);// ��ƿԤ����
		GetBasicInfoTask basicTask = new GetBasicInfoTask(this, this,CQDW,UserRegionCode);
		basicTask.execute(GPType + CQDW + UserRegionCode);
		if (CQDW.equals("311910") || CQDW.equals("311607")
				|| CQDW.equals("311707") || CQDW.equals("312010")
				|| CQDW.equals("312005") || CQDW.equals("311211")||CQDW.equals("311414")) {
			InitPSView();
			GetQPSInfoTask saleTask = new GetQPSInfoTask(this, this,CQDW,UserRegionCode);
			saleTask.execute(CQDW, UserRegionCode);
		} else {
			InitPSView();
		}

	}

	private void InitBasicView() {
		tv_userregincode.setText("");
		tv_gh.setText("");
		tv_company.setText("");
		tv_qptype.setText("");
		tv_media.setText("");
		tv_manufacture.setText("");
		tv_madeDate.setText("");
		tv_nextCheckDate.setText("");
		tv_checkResult.setText("");
		tv_receiveDate.setText("");
		tv_receiveFromStation.setText("");
		// tv_fullDate.setText("2018��05��23��");
		// tv_fullWeight.setText("14.5KG");
		tv_sendDate.setText("");
		tv_sendToStation.setText("");
	}

	private void InitPSView() {
		tv_f_custormName.setText("");
		tv_f_custormType.setText("");
		tv_f_custormCountry.setText("");
		tv_f_custormAddress.setText("");
		tv_f_supplyStation.setText("");
		tv_f_truckNO.setText("");
		tv_f_saleMan.setText("");
		tv_f_saleID.setText("");
		tv_f_saleDate.setText("");
		tv_f_saleFinishDate.setText("");
		tv_f_goods.setText("");
		tv_f_saleGPS.setText("");
		tv_f_fullNumber.setText("");
		tv_f_emptyNumber.setText("");
		tv_f_searchResult.setText("");

		tv_e_custormName.setText("");
		tv_e_custormType.setText("");
		tv_e_custormCountry.setText("");
		tv_e_custormAddress.setText("");
		tv_e_supplyStation.setText("");
		tv_e_truckNO.setText("");
		tv_e_saleMan.setText("");
		tv_e_saleID.setText("");
		tv_e_saleDate.setText("");
		tv_e_saleFinishDate.setText("");
		tv_e_goods.setText("");
		tv_e_saleGPS.setText("");
		tv_e_fullNumber.setText("");
		tv_e_emptyNumber.setText("");
		tv_e_searchResult.setText("");
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


	@Override
	public void GetBasinInfoTaskListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if (tag.getRespCode() == 0) {
			QPXXALL qpALL = JSONUtils.toObjectWithGson(tag.getRespMsg(),
					QPXXALL.class);
			QPXX qpXX = qpALL.getQPXX();
			tv_userregincode.setText(qpXX.getQPDJCODE());
			tv_gh.setText(qpXX.getGPNO());
			tv_company.setText(CQDWName);// ������˾
			tv_qptype.setText(qpXX.getName());
			tv_media.setText(qpXX.getCZJZ());
			tv_manufacture.setText(qpXX.getMadeName());
			tv_madeDate.setText(qpXX.getMadedate());
			tv_nextCheckDate.setText(qpXX.getJCRQ());
			//�ж��Ƿ񳬹��´μ�������
			String nextCheckDate=(qpXX.getJCRQ()).substring(0, 10);
			//			Log.e("====", nextCheckDate);
			if(veDate.CompareDate2(nextCheckDate)){
				tv_nextCheckDate.setTextColor(Color.RED);
			}else{
				tv_nextCheckDate.setTextColor(Color.BLACK);
			}
			tv_checkResult.setText("�ϸ�");
			tv_receiveDate.setText(qpALL.getReceiveQP().getRECEIVEDATE());
			tv_receiveFromStation.setText(qpALL.getReceiveQP()
					.getCUSTOMERNAME());
			// tv_fullDate.setText(qpALL.getFull().getFullDate());
			// tv_fullWeight.setText("14.5KG");
			tv_sendDate.setText(qpALL.getSendQP().getSENDDATE());
			tv_sendToStation.setText(qpALL.getSendQP().getTAGCUSTOMERNAME());
			// Log.e("�ɹ�", JSONUtils.toJsonWithGson(tag));
		} else {
			InitBasicView();
			Toast.makeText(getBaseContext(), tag.getRespMsg(),
					Toast.LENGTH_SHORT).show();

		}
	}

	@Override
	public void GetQPSInfoTaskListenerEnd(HsicMessage tag, boolean ret) {
		// TODO Auto-generated method stub		
		try{
			if (tag.getRespCode() == 0) {
				if(ret){			
					List<QPSale_> mDatas;
					Type typeOfT = new TypeToken<List<QPSale_>>() {
					}.getType();
					List<QPSale_> QPSale_LIST = new ArrayList<QPSale_>();
					QPSale_LIST = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
					int size = QPSale_LIST.size();
					if (size > 0) {
						for (int i = 0; i < size; i++) {
							if (QPSale_LIST.get(i).getSaleType().equals("8")) {
								tv_f_custormName.setText(QPSale_LIST.get(i)
										.getCustomerName());
								tv_f_custormType.setText(QPSale_LIST.get(i)
										.getCustomerTypeName());
								tv_f_custormCountry.setText(QPSale_LIST.get(i)
										.getAreaStreet());
								tv_f_custormAddress.setText(QPSale_LIST.get(i)
										.getAddress());
								tv_f_supplyStation.setText(QPSale_LIST.get(i)
										.getStationName());
								tv_f_truckNO.setText(QPSale_LIST.get(i)
										.getPlateNumber());
								tv_f_saleMan.setText(QPSale_LIST.get(i)
										.getEmployeeName());
								tv_f_saleID.setText(QPSale_LIST.get(i).getSaleID());
								tv_f_saleDate.setText(QPSale_LIST.get(i).getFinishTime());
								tv_f_saleFinishDate.setText(QPSale_LIST.get(i)
										.getFinishTime());
								tv_f_goods.setText(QPSale_LIST.get(i).getGoodsName()
										+ "/" + QPSale_LIST.get(i).getGoodsNum());
								tv_f_saleGPS.setText(QPSale_LIST.get(i).getgPS_J()
										+ "/" + QPSale_LIST.get(i).getgPS_W());
								tv_f_fullNumber.setText(QPSale_LIST.get(i).getSendQP());
								tv_f_emptyNumber.setText(QPSale_LIST.get(i)
										.getReceiveQP());
								tv_f_searchResult.setText(QPSale_LIST.get(i)
										.getInspectionStatus());
							} else {
								tv_e_custormName.setText(QPSale_LIST.get(i)
										.getCustomerName());
								tv_e_custormType.setText(QPSale_LIST.get(i)
										.getCustomerTypeName());
								tv_e_custormCountry.setText(QPSale_LIST.get(i)
										.getAreaStreet());
								tv_e_custormAddress.setText(QPSale_LIST.get(i)
										.getAddress());
								tv_e_supplyStation.setText(QPSale_LIST.get(i)
										.getStationName());
								tv_e_truckNO.setText(QPSale_LIST.get(i)
										.getPlateNumber());
								tv_e_saleMan.setText(QPSale_LIST.get(i)
										.getEmployeeName());
								tv_e_saleID.setText(QPSale_LIST.get(i).getSaleID());
								tv_e_saleDate.setText(QPSale_LIST.get(i).getFinishTime());
								tv_e_saleFinishDate.setText(QPSale_LIST.get(i)
										.getFinishTime());
								tv_e_goods.setText(QPSale_LIST.get(i).getGoodsName()
										+ "/" + QPSale_LIST.get(i).getGoodsNum());
								tv_e_saleGPS.setText(QPSale_LIST.get(i).getgPS_J()
										+ "/" + QPSale_LIST.get(i).getgPS_W());
								tv_e_fullNumber.setText(QPSale_LIST.get(i).getSendQP());
								tv_e_emptyNumber.setText(QPSale_LIST.get(i)
										.getReceiveQP());
								tv_e_searchResult.setText(QPSale_LIST.get(i)
										.getInspectionStatus());
							}
						}
					}
				}else{		
					List<QPSale> mDatas;
					Type typeOfT = new TypeToken<List<QPSale>>() {
					}.getType();
					List<QPSale> QPSale_LIST = new ArrayList<QPSale>();
					QPSale_LIST = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
					int size = QPSale_LIST.size();
					if (size > 0) {
						for (int i = 0; i < size; i++) {
							if (QPSale_LIST.get(i).getSaleType().equals("8")) {
								tv_f_custormName.setText(QPSale_LIST.get(i)
										.getCustomerName());
								tv_f_custormType.setText(QPSale_LIST.get(i)
										.getCustomerTypeName());
								tv_f_custormCountry.setText(QPSale_LIST.get(i)
										.getAreaStreet());
								tv_f_custormAddress.setText(QPSale_LIST.get(i)
										.getAddress());
								tv_f_supplyStation.setText(QPSale_LIST.get(i)
										.getStationName());
								tv_f_truckNO.setText(QPSale_LIST.get(i)
										.getPlateNumber());
								tv_f_saleMan.setText(QPSale_LIST.get(i)
										.getEmployeeName());
								tv_f_saleID.setText(QPSale_LIST.get(i).getSaleID());
								tv_f_saleDate.setText(QPSale_LIST.get(i).getLogtime());
								tv_f_saleFinishDate.setText(QPSale_LIST.get(i)
										.getFinishTime());
								tv_f_goods.setText(QPSale_LIST.get(i).getGoodsName()
										+ "/" + QPSale_LIST.get(i).getGoodsNum());
								tv_f_saleGPS.setText(QPSale_LIST.get(i).getGPS_J()
										+ "/" + QPSale_LIST.get(i).getGPS_W());
								tv_f_fullNumber.setText(QPSale_LIST.get(i).getSendQP());
								tv_f_emptyNumber.setText(QPSale_LIST.get(i)
										.getReceiveQP());
								tv_f_searchResult.setText(QPSale_LIST.get(i)
										.getInspectionStatus());
							} else {
								tv_e_custormName.setText(QPSale_LIST.get(i)
										.getCustomerName());
								tv_e_custormType.setText(QPSale_LIST.get(i)
										.getCustomerTypeName());
								tv_e_custormCountry.setText(QPSale_LIST.get(i)
										.getAreaStreet());
								tv_e_custormAddress.setText(QPSale_LIST.get(i)
										.getAddress());
								tv_e_supplyStation.setText(QPSale_LIST.get(i)
										.getStationName());
								tv_e_truckNO.setText(QPSale_LIST.get(i)
										.getPlateNumber());
								tv_e_saleMan.setText(QPSale_LIST.get(i)
										.getEmployeeName());
								tv_e_saleID.setText(QPSale_LIST.get(i).getSaleID());
								tv_e_saleDate.setText(QPSale_LIST.get(i).getLogtime());
								tv_e_saleFinishDate.setText(QPSale_LIST.get(i)
										.getFinishTime());
								tv_e_goods.setText(QPSale_LIST.get(i).getGoodsName()
										+ "/" + QPSale_LIST.get(i).getGoodsNum());
								tv_e_saleGPS.setText(QPSale_LIST.get(i).getGPS_J()
										+ "/" + QPSale_LIST.get(i).getGPS_W());
								tv_e_fullNumber.setText(QPSale_LIST.get(i).getSendQP());
								tv_e_emptyNumber.setText(QPSale_LIST.get(i)
										.getReceiveQP());
								tv_e_searchResult.setText(QPSale_LIST.get(i)
										.getInspectionStatus());
							}
						}
					}

				} 
			}else{
				InitPSView();
				Toast.makeText(getBaseContext(), tag.getRespMsg(),
						Toast.LENGTH_SHORT).show();
			}	
		}catch(Exception ex ){
			ex.toString();
		}
		
	}

}
