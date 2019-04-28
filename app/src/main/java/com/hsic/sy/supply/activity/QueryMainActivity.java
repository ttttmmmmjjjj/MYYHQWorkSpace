package com.hsic.sy.supply.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.adapter.GPManagerGridAdapter;
import com.hsic.sy.adapter.QueryGridAdapter;
import com.hsic.sy.bean.DeliveryQPNOCount;
import com.hsic.sy.bean.FillMessage;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.bll.Count;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.db.UserRegionCodeDB;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.supply.listener.DeliveryQPNOCountListener;
import com.hsic.sy.supply.listener.GetFillingQuantityListener;
import com.hsic.sy.supply.listener.GetTruckAllRecordsListener;
import com.hsic.sy.supply.listener.SupplyQPNOCountListener;
import com.hsic.sy.supply.task.DeliveryQPNOCountTask;
import com.hsic.sy.supply.task.GetFillingQuantityTask;
import com.hsic.sy.supply.task.GetTruckAllRecordsTask;
import com.hsic.sy.supply.task.SupplyQPNOCountTask;
import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.view.CommomDialog;
import com.hsic.sy.view.MyGridView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/***
 * 20181019 �Ż������豸������Ϣ
 */
public class QueryMainActivity extends Activity implements
		com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener,
		GetTruckAllRecordsListener, DeliveryQPNOCountListener,
		SupplyQPNOCountListener,GetFillingQuantityListener {
	private TextView HeadLine;
	UserRegionCodeDB userRegionCodeDB;
	String StationCode;
	String StationID;
	List<TruckInfo> mDatas;
	GetBasicInfo getBasicInfo;// 20181019
	private MyGridView gridview;
	private List<Count> mDataCount;
	Count count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qp_manager);
		getBasicInfo = new GetBasicInfo(QueryMainActivity.this);
		StationID = getBasicInfo.getStationID();
		StationCode = getBasicInfo.getStationID();
		InnitView();
		userRegionCodeDB = new UserRegionCodeDB(getBaseContext());
	}

	private void InnitView() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
		toolbar.setTitle("查询统计");
		toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		gridview = (MyGridView) findViewById(R.id.gridview);
		gridview.setAdapter(new QueryGridAdapter(this));
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				switch ((int) id) {
					case 0://���ι���
						Intent i = new Intent(QueryMainActivity.this,
								QueryTruckNoActivity.class);
						startActivity(i);
						break;
					case 1:
						QueryResult();
						break;
					case 2:
						ExecuteTask();
						break;
					case 3:
						FillingTask();
						break;
					case 4:
					new DeliveryQPNOCountTask(QueryMainActivity.this,
						QueryMainActivity.this).execute(StationID);
						break;
					case 5:
						new SupplyQPNOCountTask(QueryMainActivity.this,
								QueryMainActivity.this).execute(StationID);
						break;
				}
			}
		});

	}

	private void ExecuteTask() {
		TruckInfo truckInfo = new TruckInfo();
		HsicMessage hsicMess = new HsicMessage();
		truckInfo.setStartTime(GetDate.getToday());
		truckInfo.setEndTime(GetDate.getToday());
		truckInfo.setStationid(StationID);
		hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truckInfo));
		String responsedata = JSONUtils.toJsonWithGson(hsicMess);
		GetTruckAllRecordsTask task = new GetTruckAllRecordsTask(this, this);
		task.execute("", responsedata);
	}
	private void FillingTask() {
		GetFillingQuantityTask task=new GetFillingQuantityTask(this,this);
		task.execute("");
	}

	private void QueryResult() {
		int s = userRegionCodeDB.GetUploadSucess("0", StationCode,
				GetDate.getToday());// �ɹ�
		int d = userRegionCodeDB.GetUploadSucess("1", StationCode,
				GetDate.getToday());// //ɨ�����ڲ���
		int c = userRegionCodeDB.GetUploadSucess("2", StationCode,
				GetDate.getToday());// //�ظ�ɨ��
		int e = userRegionCodeDB.GetUploadSucess("3", StationCode,
				GetDate.getToday());// ��������ȷ

		int f = userRegionCodeDB.GetUploadSucess("4", StationCode,
				GetDate.getToday());// ʧ��
		mDataCount=new ArrayList<>();
		count=new Count();
		count.setName("总共扫描记录:");
		count.setValue(String.valueOf(s + d + c + e + f)+ "条");
		mDataCount.add(count);
		count=new Count();
		count.setName("上传成功记录:");
		count.setValue(String.valueOf(s) + "条");
		mDataCount.add(count);
		count=new Count();
		count.setName("上传失败记录:");
		count.setValue(String.valueOf(f) + "条");
		mDataCount.add(count);
		count=new Count();
		count.setName("扫描日期不对:");
		count.setValue(String.valueOf(d) + "条");
		mDataCount.add(count);
		count=new Count();
		count.setName("重复扫描记录:");
		count.setValue(String.valueOf(c) + "条");
		mDataCount.add(count);
		count=new Count();
		count.setName("参数有误记录:");
		count.setValue(String.valueOf(e) + "条");
		mDataCount.add(count);
		new CommomDialog(this,R.style.dialog,mDataCount,new CommomDialog.OnCloseListener(){

			@Override
			public void onClick(Dialog dialog, boolean confirm) {
				dialog.dismiss();
			}
		}).setTitle("上传结果说明").show();
	}

	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void GetTruckAllRecordsListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if (tag.getRespCode() == 0) {
			Type typeOfT = new TypeToken<List<TruckInfo>>() {
			}.getType();
			List<TruckInfo> Truck_LIST = new ArrayList<TruckInfo>();
			Truck_LIST = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
			int size = 0;
			size = Truck_LIST.size();
			mDatas = new ArrayList<TruckInfo>();
			if (size > 0) {
				for (int a = 0; a < size; a++) {
					TruckInfo temp = Truck_LIST.get(a);
					mDatas.add(temp);
				}
			}
			ShowDialog();
		} else {
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, this, AlertView.Style.Alert, this).show();
		}
	}

	private AlertDialog.Builder builder;
	private AlertDialog alertDialog;

	public void ShowDialog() {
		Context context = QueryMainActivity.this;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.formcommonlist, null);
		ListView myListView = (ListView) layout
				.findViewById(R.id.formcustomspinner_list);
		MyAdapter adapter = new MyAdapter(context, mDatas);
		myListView.setAdapter(adapter);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int positon, long id) {
				String truck = mDatas.get(positon).getLicense();
				String UseRegCode = mDatas.get(positon).getUseRegCode();
				String[] input = new String[2];
				if (UseRegCode != null) {
					if (!UseRegCode.equals("")) {
						Intent i = new Intent(QueryMainActivity.this,
								QueryFullBottleActivity.class);
						input[0] = truck;
						input[1] = UseRegCode;
						Bundle bundle = new Bundle();
						bundle.putStringArray("UseRegCode", input);
						i.putExtras(bundle);
						startActivity(i);
						alertDialog.dismiss();
					} else {
						alertDialog.dismiss();
						tips();
					}

				} else {
					alertDialog.dismiss();
					tips();
				}

			}
		});
		builder = new AlertDialog.Builder(context);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
	}

	private void tips() {
		new AlertView("提示", "该车未加满瓶!", null, new String[] { "确定" }, null, this,
				AlertView.Style.Alert, this).show();
	}

	// �Զ����������
	class MyAdapter extends BaseAdapter {
		private List<TruckInfo> mlist;
		private Context mContext;

		public MyAdapter(Context context, List<TruckInfo> list) {
			this.mContext = context;
			mlist = new ArrayList<TruckInfo>();
			this.mlist = list;
		}

		@Override
		public int getCount() {
			return mlist.size();
		}

		@Override
		public Object getItem(int position) {
			return mlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Person person = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.rtu_item, null);
				person = new Person();
				person.name = (TextView) convertView.findViewById(R.id.tv_name);
				convertView.setTag(person);
			} else {
				person = (Person) convertView.getTag();
			}
			person.name.setText(mlist.get(position).getLicense());
			return convertView;
		}

		class Person {
			TextView name;
		}
	}

	@Override
	public void SupplyQPNOCountListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if(tag.getRespCode()==0){
			String[] s=new String[2];
			if(tag.getRespMsg().contains(",")){
				s=tag.getRespMsg().split(",");
			}else{
				s[0]=tag.getRespMsg();
				s[1]="";
			}
			mDataCount=new ArrayList<>();
			count=new Count();
			count.setName("满瓶数:");
			count.setValue(s[0]);
			mDataCount.add(count);
			count=new Count();
			count.setName("空瓶数:");
			count.setValue(s[1]);
			mDataCount.add(count);
			new CommomDialog(this,R.style.dialog,mDataCount,new CommomDialog.OnCloseListener(){

				@Override
				public void onClick(Dialog dialog, boolean confirm) {
					dialog.dismiss();
				}
			}).setTitle("供应站点库存统计").show();
		}else{
			new AlertView("供应站点库存统计失败", tag.getRespMsg(), null, new String[] { "确定" }, null,
					this, AlertView.Style.Alert, this).show();
		}
	}

	@Override
	public void DeliveryQPNOCountListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if(tag.getRespCode()==0){
			Type typeOfT = new TypeToken<List<DeliveryQPNOCount> >() {
			}.getType();
			List<DeliveryQPNOCount> DeliveryQPNOCount_List=new ArrayList<DeliveryQPNOCount>();
			DeliveryQPNOCount_List = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
			StringBuffer message=new StringBuffer();
			if(DeliveryQPNOCount_List!=null&& DeliveryQPNOCount_List.size()>0){
				mDataCount=new ArrayList<>();
				int size=DeliveryQPNOCount_List.size();
				for(int i=0;i<size;i++){
					count=new Count();
					count.setName(DeliveryQPNOCount_List.get(i).getLicense());
					count.setValue("");
					mDataCount.add(count);
					count=new Count();
					count.setName("订单总数:");
					count.setValue(DeliveryQPNOCount_List.get(i).getSaleCounts());
					mDataCount.add(count);
					count=new Count();
					count.setName("钢瓶总数:");
					count.setValue(DeliveryQPNOCount_List.get(i).getGpCounts());
					mDataCount.add(count);
					count=new Count();
					count.setName("销售金额:");
					count.setValue(DeliveryQPNOCount_List.get(i).getSaleCounts());
					mDataCount.add(count);
				}
				new CommomDialog(this,R.style.dialog,mDataCount,new CommomDialog.OnCloseListener(){

					@Override
					public void onClick(Dialog dialog, boolean confirm) {
						dialog.dismiss();
					}
				}).setTitle("供应站配送数据统计").show();
			}else{
				new AlertView("供应站配送数据统计", "无数据", null, new String[] { "确定" }, null,
						this, AlertView.Style.Alert, this).show();
			}

		}else{
			new AlertView("配送统计失败", tag.getRespMsg(), null, new String[] { "确定" }, null,
					this, AlertView.Style.Alert, this).show();
		}
	}

	@Override
	public void GetFillingQuantityListenerEnd(FillMessage tag) {
		// TODO Auto-generated method stub
		if(tag.getResult()==0){
			mDataCount=new ArrayList<>();
			count=new Count();
			count.setName("50KG:");
			count.setValue(tag.getBigNum());
			mDataCount.add(count);
			count=new Count();
			count.setName("15KG:");
			count.setValue(tag.getSmallNum());
			mDataCount.add(count);
			new CommomDialog(this,R.style.dialog,mDataCount,new CommomDialog.OnCloseListener(){

				@Override
				public void onClick(Dialog dialog, boolean confirm) {
					dialog.dismiss();
				}
			}).setTitle("钢瓶充装数据统计").show();

		}else{
			new AlertView("充装查询统计失败", tag.getMessage(), null, new String[] { "确定" }, null,
					this, AlertView.Style.Alert, this).show();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}