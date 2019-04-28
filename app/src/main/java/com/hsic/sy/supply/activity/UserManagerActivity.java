package com.hsic.sy.supply.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.hsic.sy.bean.SaleDetail;
import com.hsic.sy.supplystationmanager.R;
import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.adapter.SortAdapter;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.Sale;
import com.hsic.sy.bean.SortModel;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.bll.CharacterParser;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.bll.PinyinComparator;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.search.bean.ApplicationHelper;
import com.hsic.sy.supply.listener.GetTruckNoRecordsListener;
import com.hsic.sy.supply.listener.InsertTruckSaleRelationListener;
import com.hsic.sy.supply.listener.SearchAssignSaleListener;
import com.hsic.sy.supply.task.GetTruckNoRecordsTask;
import com.hsic.sy.supply.task.InsertTruckSaleRelationTask;
import com.hsic.sy.supply.task.SearchAssignSaleTask;
import com.hsic.sy.supplystationmanager.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UserManagerActivity extends Activity implements SearchAssignSaleListener,
		com.hsic.sy.dialoglibrary.OnItemClickListener,
		OnDismissListener,GetTruckNoRecordsListener,InsertTruckSaleRelationListener {
	private ListView sortListView;
	// private SideBar sideBar;
	Button btn;
	TextView UserName, UserID,UserType,SaleDate,IfSend,PayMode,SendMode,
			CardID,LastQP,Add,Mark,goodsInfo,saleid;
	LinearLayout Info;
	DrawerLayout d;
	List<TruckInfo> mDatas;
	private String SaleID="",truck_id ="";
	/**
	 * 显示字母的TextView
	 */
	private TextView dialog;
	private SortAdapter adapter;
//	private ClearEditText mClearEditText;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	String StationID;
	ApplicationHelper applicationHelper;
	GetBasicInfo getBasicInfo;//20181019

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_manager);
		initViews();
		Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
        toolbar.setTitle("订单管理");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

		applicationHelper = (ApplicationHelper)getApplication();
		getBasicInfo=new GetBasicInfo(UserManagerActivity.this);
		StationID=getBasicInfo.getStationID();
		Sale sale=new Sale();
		sale.setStationcode(StationID);
		HsicMessage hsicMess=new HsicMessage();
		hsicMess.setRespMsg(JSONUtils.toJsonWithGson(sale));
		String responsedata=JSONUtils.toJsonWithGson(hsicMess);
		SearchAssignSaleTask task=new SearchAssignSaleTask(this,this);
		task.execute("",responsedata);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	private void initViews() {
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		// sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		// sideBar.setTextView(dialog);
		btn = (Button) this.findViewById(R.id.chouti_sure);
		UserName = (TextView) this.findViewById(R.id.username);
		UserID = (TextView) this.findViewById(R.id.userid);
		UserType=(TextView)this.findViewById(R.id.usertype);
		SaleDate=(TextView)this.findViewById(R.id.senddate);
		IfSend=(TextView)this.findViewById(R.id.ifsend);
		PayMode=(TextView)this.findViewById(R.id.paymode);
		SendMode=(TextView)this.findViewById(R.id.sendtype);
		CardID=(TextView)this.findViewById(R.id.cardid);
		goodsInfo=this.findViewById(R.id.goodsInfo);
		LastQP=(TextView)this.findViewById(R.id.lastnum);
		Add=(TextView)this.findViewById(R.id.address);
		Mark=(TextView)this.findViewById(R.id.marke);
		Info = (LinearLayout) this.findViewById(R.id.layoutDrawKK);
		Info.setOnClickListener(null);
		d = (DrawerLayout) this.findViewById(R.id.mDrawlayout);
		saleid=this.findViewById(R.id.saleid);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				d.closeDrawer(Gravity.RIGHT);
				//查询车次
				GetTruck();
			}

		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				d.openDrawer(Gravity.RIGHT);
				//				String userID = addZero((String.valueOf(position + 1)), 8);
				UserID.setText("送气编号:"+((SortModel) adapter.getItem(position))
						.getUserID());
				UserName.setText("用户姓名:"+((SortModel) adapter.getItem(position))
						.getName());
				UserType.setText("客户类别:"+((SortModel) adapter.getItem(position))
						.getUserTyep());
				SaleDate.setText("交易日期:"+((SortModel) adapter.getItem(position))
						.getSaleDate());
				String IfSeng_Temp=((SortModel) adapter.getItem(position))
						.getIfSend();
				if(IfSeng_Temp.equals("Y")){
					IfSeng_Temp="是";
				}else{
					IfSeng_Temp="否";
				}
				String PayMode_Temp=((SortModel) adapter.getItem(position))
						.getPayMode();
				if(PayMode_Temp.equals("C")){
					PayMode_Temp="现金";
				}
				if(PayMode_Temp.equals("Z")){
					PayMode_Temp="支付宝";
				}
				if(PayMode_Temp.equals("W")){
					PayMode_Temp="微信";
				}
				IfSend.setText("是否送气:"+IfSeng_Temp);
				PayMode.setText("付款方式:"+PayMode_Temp);
				SendMode.setText("送气方式:"+((SortModel) adapter.getItem(position))
						.getSendMode());
				CardID.setText("身份证号:"+((SortModel) adapter.getItem(position))
						.getCardId());
				LastQP.setText("上次气瓶:"+((SortModel) adapter.getItem(position))
						.getLastQP());
				Add.setText("送气地址:"+((SortModel) adapter.getItem(position))
						.getAdd());
				Mark.setText("信息备注:"+((SortModel) adapter.getItem(position))
						.getMark());
				SaleID=((SortModel) adapter.getItem(position)).getSaleID();
				saleid.setText("订单编号:"+SaleID);
				goodsInfo.setText("商品信息:"+((SortModel) adapter.getItem(position))
						.getGoodsInfo());

			}
		});

//		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
//
//		// 根据输入框输入值的改变来过滤搜索
//		mClearEditText.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//				filterData(s.toString());
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//			}
//		});
	}
	private void GetTruck(){
		TruckInfo truckInfo=new TruckInfo();
		HsicMessage hsicMess=new HsicMessage();
		truckInfo.setStartTime(GetDate.getToday());
		truckInfo.setEndTime(GetDate.getToday());
		truckInfo.setStationid(StationID);
		hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truckInfo));
		String responsedata=JSONUtils.toJsonWithGson(hsicMess);
		GetTruckNoRecordsTask task=new GetTruckNoRecordsTask(this,this);
		task.execute("",responsedata);
	}
	/**
	 * 为ListView填充数据
	 *
	 * @param
	 * @return
	 */
	private List<SortModel> filledData(List<Sale> sale) {
		List<SortModel> mSortList = new ArrayList<SortModel>();
		for (int i = 0; i <sale.size() ; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(sale.get(i).getUsername());
			sortModel.setUserID(sale.get(i).getUserid());
			sortModel.setAdd(sale.get(i).getDeliveraddress());
			sortModel.setIfSend(sale.get(i).getDeliveryn());
			sortModel.setLastQP(sale.get(i).getBottleno());
			sortModel.setPayMode(sale.get(i).getPaymode());
			sortModel.setSaleDate(sale.get(i).getDeliver_time());
			sortModel.setCardId(sale.get(i).getCardid());
			sortModel.setMark(sale.get(i).getComment());
			sortModel.setUserTyep(sale.get(i).getAccept());
			sortModel.setSendMode(sale.get(i).getDeliver_method());
			sortModel.setSaleID(sale.get(i).getSaleid());
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(sale.get(i).getUsername());
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
//			if (sortString.matches("[A-Z]")) {
//				sortModel.setSortLetters(sortString.toUpperCase());
//			} else {
//				sortModel.setSortLetters("#");
//			}
			/**
			 * 商品信息
			 */
			List<SaleDetail> saleDetailList=new ArrayList<>();
			saleDetailList=sale.get(i).getSaledetail();
			StringBuffer detailStr=new StringBuffer();
			int detail=saleDetailList.size();
			for(int d=0;d<detail;d++){
				detailStr.append(saleDetailList.get(d).getGoodsname()+"/"+saleDetailList.get(d).getGoodscount()+",");
			}
			if(!detailStr.toString().equals("")){
				String temp=detailStr.toString();
				int l=temp.length();
				sortModel.setGoodsInfo(temp.substring(0,l-1));
			}else{
				sortModel.setGoodsInfo("");
			}

			mSortList.add(sortModel);
		}

		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 *
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.toUpperCase().indexOf(
						filterStr.toString().toUpperCase()) != -1
						|| characterParser.getSelling(name).toUpperCase()
						.startsWith(filterStr.toString().toUpperCase())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	private String addZero(String str, int strLength) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < strLength) {
			sb = new StringBuffer();
			sb.append("0").append(str);// 左(前)补0
			// sb.append(str).append("0");//右(后)补0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}

	@Override
	public void SearchAssignSaleListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if(tag.getRespCode()==0){
			//设置适配器数据
			Type typeOfT = new TypeToken<List<Sale>>() {
			}.getType();
			List<Sale> Sale_LIST = new ArrayList<Sale>();
			Sale_LIST = JSONUtils
					.toListWithGson(tag.getRespMsg(), typeOfT);
			SourceDateList = filledData(Sale_LIST);
			// 根据a-z进行排序源数据
//			Collections.sort(SourceDateList, pinyinComparator);
			adapter = new SortAdapter(this, SourceDateList);
			sortListView.setAdapter(adapter);
		}else{
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, this, AlertView.Style.Alert, this).show();
		}
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
	public void InsertTruckSaleRelationListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if(tag.getRespCode()==0){
			for(int i=0;i<SourceDateList.size();i++){
				if(SourceDateList.get(i).getSaleID().endsWith(SaleID)){
					SourceDateList.remove(i);
//					Collections.sort(SourceDateList, pinyinComparator);
					adapter = new SortAdapter(this, SourceDateList);
					sortListView.setAdapter(adapter);
				}
			}
			Toast.makeText(UserManagerActivity.this, "订单绑定成功!", Toast.LENGTH_SHORT).show();
		}else{
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, this, AlertView.Style.Alert, this).show();
		}
	}
	@Override
	public void GetTruckNoRecordsListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if(tag.getRespCode()==0){
			//获取数据
			Type typeOfT = new TypeToken<List<TruckInfo>>() {
			}.getType();
			List<TruckInfo> Truck_LIST = new ArrayList<TruckInfo>();
			Truck_LIST = JSONUtils.toListWithGson(
					tag.getRespMsg(), typeOfT);

			int size=0;
			size=Truck_LIST.size();
			mDatas=new ArrayList<TruckInfo>();
			if(size>0){
				for(int a=0;a<size;a++){
					if(Truck_LIST.get(a).getState().equals("1")){
						TruckInfo temp=Truck_LIST.get(a);
						mDatas.add(temp);
					}
				}
			}
			ShowDialog();
		}else{
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, this, AlertView.Style.Alert, this).show();
		}
	}
	private AlertDialog.Builder builder;
	private AlertDialog alertDialog;
	public void ShowDialog() {
		Context context = UserManagerActivity.this;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.formcommonlist, null);
		ListView myListView = (ListView) layout.findViewById(R.id.formcustomspinner_list);
		MyAdapter adapter = new MyAdapter(context, mDatas);
		myListView.setAdapter(adapter);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int positon, long id) {
				String TruckNoID="";
				TruckNoID=mDatas.get(positon).getTruckNoID();
				truck_id=mDatas.get(positon).getTruckID();
				TruckInfo truck=new TruckInfo();
				Sale sale=new Sale();
				sale.setSaleid(SaleID);
				sale.setTruck_id(truck_id);
				List<Sale> Sale_LIST=new ArrayList<Sale>();
				Sale_LIST.add(sale);
				truck.setSale_tmp(Sale_LIST);
				truck.setTruckNoID(TruckNoID);
				truck.setSaleMan(getBasicInfo.getOperationID());
				truck.setStationid(StationID);
				HsicMessage hsicMess=new HsicMessage();
				hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truck));
				String responsedata=JSONUtils.toJsonWithGson(hsicMess);
				InsertTask(responsedata);
				if (alertDialog != null) {
					alertDialog.dismiss();
				}
			}
		});
		builder = new AlertDialog.Builder(context);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
	}
	private void InsertTask(String responsedata){
		InsertTruckSaleRelationTask task=new InsertTruckSaleRelationTask(this,this);
		task.execute("",responsedata);
	}
	//自定义的适配器
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
				convertView = inflater.inflate(R.layout.rtu_item,null);
				person = new Person();
				person.name = (TextView)convertView.findViewById(R.id.tv_name);
				convertView.setTag(person);
			}else{
				person = (Person)convertView.getTag();
			}
			person.name.setText(mlist.get(position).getLicense());
			return convertView;
		}
		class Person{
			TextView name;
		}
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
