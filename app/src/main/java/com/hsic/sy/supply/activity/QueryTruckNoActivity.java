package com.hsic.sy.supply.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.bll.DeviceIDInfo;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.bll.SpinnerOption;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.supply.listener.GetTruckAllRecordsListener;
import com.hsic.sy.supply.task.GetTruckAllRecordsTask;
import com.hsic.sy.supplystationmanager.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QueryTruckNoActivity extends Activity implements
		GetTruckAllRecordsListener, com.hsic.sy.dialoglibrary.OnItemClickListener,
		OnDismissListener {
	String StationID;
	List<Map<String, Object>> items;
	private Spinner TruckState;
	ArrayList<SpinnerOption> TruckState_LST;
	GridView gv;
	Button Query;
	private ArrayAdapter<SpinnerOption> License_Adapter;
	String index;
	GetBasicInfo getBasicInfo;//20181019
	TextView stationid,deviceid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_truckno);
		getBasicInfo=new GetBasicInfo(QueryTruckNoActivity.this);
		StationID=getBasicInfo.getStationID();
		TruckState = (Spinner) this.findViewById(R.id.truckState);
		stationid=this.findViewById(R.id.stationid);
		deviceid=this.findViewById(R.id.deviceid);
		stationid.setText(getBasicInfo.getStationName());
		deviceid.setText("设备号:"+getBasicInfo.getDeviceID());
		setSpinner();
		TruckState.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				index = ((SpinnerOption) TruckState.getSelectedItem())
						.getText();
				ExecuteTask();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
//		Query = (Button) this.findViewById(R.id.btn_query);
//		Query.setVisibility(View.GONE);
		// ExecuteTask();
		gv = (GridView) findViewById(R.id.mygridview);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					return;
				}
				TruckInfo truckInfo = JSONUtils.toObjectWithGson(
						JSONUtils.toJsonWithGson(gv.getItemAtPosition(arg2)),
						TruckInfo.class);

				String[] truck = new String[2];
				String TruckNoID = truckInfo.getTruckNoID();
				Intent i = new Intent(QueryTruckNoActivity.this,
						TruckNoDetailActivity.class);
				// 用Bundle携带数据
				truck[0] = TruckNoID;
				truck[1] = truckInfo.getLicense();

				Bundle bundle = new Bundle();
				bundle.putStringArray("TruckInfo", truck);
				i.putExtras(bundle);
				startActivity(i);
//				StringBuffer message = new StringBuffer();
//				message.append("车次号:"+TruckNoID+"\n");
//				message.append("车牌号:"+truckInfo.getLicense()+"\n");
//				message.append("车牌号:"+truckInfo.getLicense()+"\n");


			}

		});
//		Query.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				ExecuteTask();
//			}
//
//		});

	}
//private void setDetail(String message){
//	new AlertView("订单详细信息", message, null, new String[] { "确定" }, null,
//			this, AlertView.Style.Msg, this).show();
//}
	private void setSpinner() {
		TruckState_LST = new ArrayList<SpinnerOption>();
		String tag = "5";
		String tagName = "全部";
		SpinnerOption spinner = new SpinnerOption(tag, tagName);
		TruckState_LST.add(spinner);
		String tag0 = "0";
		String tagName0 = "未发车";

		SpinnerOption spinner0 = new SpinnerOption(tag0, tagName0);
		TruckState_LST.add(spinner0);
		String tag1 = "1";
		String tagName1 = "已发车";
		SpinnerOption spinner1 = new SpinnerOption(tag1, tagName1);
		TruckState_LST.add(spinner1);
		String tag2 = "2";
		String tagName2 = "已结束";
		SpinnerOption spinner2 = new SpinnerOption(tag2, tagName2);
		TruckState_LST.add(spinner2);
		String tag3 = "3";
		String tagName3 = "强制结束";
		SpinnerOption spinner3 = new SpinnerOption(tag3, tagName3);
		TruckState_LST.add(spinner3);
		String tag4 = "4";
		String tagName4 = "作废";
		SpinnerOption spinner4 = new SpinnerOption(tag4, tagName4);
		TruckState_LST.add(spinner4);
		License_Adapter = new ArrayAdapter<SpinnerOption>(this,
				android.R.layout.simple_spinner_item, TruckState_LST);
		License_Adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		TruckState.setAdapter(License_Adapter);
	}

	private void setGV() {
		items = new ArrayList<Map<String, Object>>();
		Map<String, Object> title = new HashMap<String, Object>();
		title.put("RowId", "编号");
		title.put("stationid", "始发站点");
		title.put("TruckNoIDSub", "车次");
		title.put("license", "车牌号");
		title.put("SaleMan", "业务员");
		title.put("driver", "驾驶员");
		title.put("HandsetNo", "手持机编号");
		title.put("bottleNum", "满瓶数");
		title.put("State", "状态");
		// title.put("CreateTime", "创建时间");
		// title.put("SendTime", "发车时间");
		// title.put("UserRegionCode", "电子标签");
		items.add(title);
	}

	private void ExecuteTask() {
		TruckInfo truckInfo = new TruckInfo();
		HsicMessage hsicMess = new HsicMessage();
//		truckInfo.setStartTime("2017-01-01");
//		truckInfo.setEndTime("2017-12-14");
		truckInfo.setStartTime(GetDate.getToday());
		truckInfo.setEndTime(GetDate.getToday());
		truckInfo.setStationid(StationID);
		hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truckInfo));
		String responsedata = JSONUtils.toJsonWithGson(hsicMess);
		GetTruckAllRecordsTask task = new GetTruckAllRecordsTask(QueryTruckNoActivity.this, QueryTruckNoActivity.this);
		task.execute("", responsedata);
	}

	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub
		this.finish();
	}

	@Override
	public void GetTruckAllRecordsListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		String ret = tag.getRespMsg();
		if (tag.getRespCode() == 0) {
			setGV();
			// 获取数据
			List<TruckInfo> mDatas;
			Type typeOfT = new TypeToken<List<TruckInfo>>() {
			}.getType();
			List<TruckInfo> Truck_LIST = new ArrayList<TruckInfo>();
			Truck_LIST = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
			int size = Truck_LIST.size();
			List<TruckInfo> Truck_LIST_New = new ArrayList<TruckInfo>();
			// 将数据添加到ListView
			// ListView设置数据
			for (int a = 0; a < size; a++) {
				if (index.equals("全部")) {
					TruckInfo truck=new TruckInfo();
					truck= Truck_LIST.get(a);
					Truck_LIST_New.add(truck);
					continue;
				}
				if (index.equals("未发车")) {
					if (Truck_LIST.get(a).getState().equals("0")) {
						TruckInfo truck=new TruckInfo();
						truck= Truck_LIST.get(a);
						truck.setState("未发车");
						Truck_LIST_New.add(truck);
						continue;
					}

				}
				if (index.equals("已发车")) {
					if (Truck_LIST.get(a).getState().equals("1")) {
						TruckInfo truck=new TruckInfo();
						truck= Truck_LIST.get(a);
						truck.setState("已发车");
						Truck_LIST_New.add(truck);
						continue;
					}

				}
				if (index.equals("已结束")) {
					if (Truck_LIST.get(a).getState().equals("2")) {
						TruckInfo truck=new TruckInfo();
						truck= Truck_LIST.get(a);
						truck.setState("已结束");
						Truck_LIST_New.add(truck);
						continue;
					}

				}
				if (index.equals("强制结束")) {

					if (Truck_LIST.get(a).getState().equals("3")) {
						TruckInfo truck=new TruckInfo();
						truck= Truck_LIST.get(a);
						truck.setState("强制结束");
						Truck_LIST_New.add(truck);
						continue;

					}

				}
				if (index.equals("作废")) {
					if (Truck_LIST.get(a).getState().equals("4")) {
						TruckInfo truck=new TruckInfo();
						truck= Truck_LIST.get(a);
						truck.setState("作废");
						Truck_LIST_New.add(truck);
						continue;
					}

				}
			}
			int newSize=Truck_LIST_New.size();
			for(int b=0;b<newSize;b++){
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("RowId", newSize-b );
				item.put("stationid", Truck_LIST_New.get(b).getStationname());
				String TruckNoID_Temp=Truck_LIST_New.get(b).getTruckNoID();
				item.put("TruckNoID", Truck_LIST_New.get(b).getTruckNoID());
				item.put("TruckNoIDSub", TruckNoID_Temp.substring(TruckNoID_Temp.length()-4));
				item.put("license", Truck_LIST_New.get(b).getLicense());
				item.put("SaleMan", Truck_LIST_New.get(b).getSaleMan());
				item.put("driver", Truck_LIST_New.get(b).getDriver());
				String HandsetNo_Temp=getBasicInfo.getDeviceID();
				item.put("HandsetNo", HandsetNo_Temp.substring(HandsetNo_Temp.length()-4));
				item.put("bottleNum", Truck_LIST_New.get(b).getBottleNum());
				String stateTemp=Truck_LIST_New.get(b).getState();
//				Log.e("11111","="+stateTemp);
				if(stateTemp.equals("0")||stateTemp.equals("1")||stateTemp.equals("2")||
						stateTemp.equals("3")||stateTemp.equals("4")){
					item.put("State", getStateDic(stateTemp));
				}else{
					item.put("State", stateTemp);
				}

				items.add(item);
			}

			SimpleAdapter sa = new SimpleAdapter(QueryTruckNoActivity.this, // 上下文环境
					items, // 数据源
					R.layout.item_truckno, // 内容布局

					new String[] { "RowId", "stationid", "TruckNoIDSub",
							"license", "SaleMan", "driver", "HandsetNo",
							"bottleNum", "State", }, // 数据源的arrayName
					new int[] { R.id.text_item0, R.id.text_station,
							R.id.text_item1, R.id.text_item2, R.id.text_item3,
							R.id.text_item4, R.id.text_item5, R.id.text_item6,
							R.id.text_item7 } // 装载数据的控件
			);
			gv.setAdapter(sa); // 与gridview绑定

		}else{
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, this, AlertView.Style.Alert, this).show();
		}
	}
	private String getStateDic(String sate ){
		String ret="";
		if(sate.equals("0")){
			ret="未发车";
		}else if(sate.equals("1")){
			ret="已发车";
		}else if(sate.equals("2")){
			ret="已结束";
		}else if(sate.equals("3")){
			ret="强制结束";
		}else if(sate.equals("4")){
			ret="作废";
		}else{
			ret="";
		}
		return ret;
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


