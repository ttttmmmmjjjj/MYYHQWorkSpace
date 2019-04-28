package com.hsic.sy.supply.activity;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.Sale;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.InfoDialog;
import com.hsic.sy.search.bean.ApplicationHelper;
import com.hsic.sy.supply.task.GetTruckAllRecordsTask;
import com.hsic.sy.supplystationmanager.R;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.reflect.TypeToken;
import com.hsic.sy.bean.SaleDetail;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.supply.listener.GetTruckAllRecordsListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TruckNoDetailActivity extends Activity implements
		GetTruckAllRecordsListener,
		com.hsic.sy.dialoglibrary.OnItemClickListener,
		OnDismissListener {
	String TruckNoID;
	String StationID;
	ApplicationHelper applicationHelper;
	List<Map<String, Object>> sale_Items;
	List<Map<String, Object>> saleDetail_Items;
	GridView gv_sale, gv_saledetail;
	List<SaleDetail> SaleDetailInfo_LIST;
	String license;
	TextView title;
	String Address;
	GetBasicInfo getBasicInfo;//20181019
	boolean dialogIsClose=true;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_truckno_detail);
		applicationHelper = (ApplicationHelper) getApplication();
		getBasicInfo=new GetBasicInfo(TruckNoDetailActivity.this);
		StationID=getBasicInfo.getStationID();
		gv_sale = (GridView) this.findViewById(R.id.gv_sale);
		gv_saledetail = (GridView) this.findViewById(R.id.gv_saledetail);
		title = (TextView) this.findViewById(R.id.headline);
		Bundle bundle = this.getIntent().getExtras();
		String[] TruckInfo = bundle.getStringArray("TruckInfo");
		TruckNoID = TruckInfo[0];
		license = TruckInfo[1];
		ExecuteTask();

		sale_Items = new ArrayList<Map<String, Object>>();
		saleDetail_Items = new ArrayList<Map<String, Object>>();
		setGvTitle();
		SetGVDetailTitle();
		gv_sale.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				try{
					if (arg2 == 0) {
						saleDetail_Items = new ArrayList<Map<String, Object>>();
						saleDetail_Items.add(title2);
						return;
					}
					saleDetail_Items = new ArrayList<Map<String, Object>>();
					saleDetail_Items.add(title2);
					Sale sale = JSONUtils.toObjectWithGson(JSONUtils
									.toJsonWithGson(gv_sale.getItemAtPosition(arg2)),
							Sale.class);
					String saleid = sale.getSaleid();
					if(saleid==null){
						return;
					}
					Address = sale.getDeliveraddress();
					int size = SaleDetailInfo_LIST.size();
					for (int t = 0; t < size; t++) {
						if (saleid.equals(SaleDetailInfo_LIST.get(t).getSaleid())) {
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("saleid",SaleDetailInfo_LIST.get(t).getSaleid());
							item.put("goodsname", SaleDetailInfo_LIST.get(t)
									.getGoodsname());
							item.put("goodsprice", SaleDetailInfo_LIST.get(t)
									.getGoodsprice());
							item.put("goodscount", SaleDetailInfo_LIST.get(t)
									.getGoodscount());
							item.put("decreaseprice_deliver", SaleDetailInfo_LIST
									.get(t).getDeliverprice());
							item.put("deliverprice", SaleDetailInfo_LIST.get(t)
									.getDecreaseprice_deliver());
							item.put("decreaseprice_goods", SaleDetailInfo_LIST
									.get(t).getDecreaseprice_goods());
							item.put("totalprice", SaleDetailInfo_LIST.get(t)
									.getTotalprice());

							item.put("bottleno", SaleDetailInfo_LIST.get(t)
									.getBottleno());
							item.put("ReceiveBottle", SaleDetailInfo_LIST.get(t)
									.getReceiveBottle());
							item.put("Match", SaleDetailInfo_LIST.get(t).getMatch());
							item.put("ReceiveBottleByHand", SaleDetailInfo_LIST
									.get(t).getReceiveBottleByHand());
//							Log.e("+"+t,JSONUtils.toJsonWithGson(SaleDetailInfo_LIST.get(t)));
							saleDetail_Items.add(item);
							break;
						}
					}
				}catch (Exception ex){
					ex.printStackTrace();
				}
				// 设置适配器
				SetGVDetailAdapter();
			}

		});
		gv_saledetail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				SaleDetail saleDetail = new SaleDetail();
				if (arg2 == 0) {
					return;
				}
				saleDetail = JSONUtils.toObjectWithGson(JSONUtils
								.toJsonWithGson(gv_saledetail.getItemAtPosition(arg2)),
						SaleDetail.class);
				String msg = "用户地址:" + Address + "\n" + "满瓶电子标签号:"
						+ saleDetail.getBottleno() + "\n" + "空瓶电子标签号:"
						+ saleDetail.getReceiveBottle() + "\n" + "手输标签号:"
						+ saleDetail.getReceiveBottleByHand() + "\n";
				ShnownJY(msg);

			}

		});
		title.setText("订单信息" + "[" + license + "]");
	}

	private void ShnownJY(String msg) {
		dialogIsClose=false;
		new InfoDialog("提示", msg, null, new String[] { "确定" }, null, this,
				InfoDialog.Style.Alert, this).show();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		;
	}

	private void ExecuteTask() {
		TruckInfo truckInfo = new TruckInfo();
		HsicMessage hsicMess = new HsicMessage();
		truckInfo.setStartTime(GetDate.getToday());
		truckInfo.setEndTime(GetDate.getToday());
		truckInfo.setTruckNoID(TruckNoID);
		truckInfo.setStationid(StationID);
		hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truckInfo));
		String responsedata = JSONUtils.toJsonWithGson(hsicMess);
		GetTruckAllRecordsTask task = new GetTruckAllRecordsTask(this, this);
		task.execute("", responsedata);
	}

	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub
		if(dialogIsClose){
			dialogIsClose=false;
			this.finish();
		}

	}

	@Override
	public void GetTruckAllRecordsListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		try{
			if (tag.getRespCode() == 0) {
				// 获取数据
				List<TruckInfo> mDatas;
				Type typeOfT = new TypeToken<List<TruckInfo>>() {
				}.getType();
				List<TruckInfo> Truck_LIST = new ArrayList<TruckInfo>();
				Truck_LIST = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
				SaleDetailInfo_LIST = new ArrayList<SaleDetail>();
				int size = Truck_LIST.size();
				// 将数据添加到ListView
				// ListView设置数据
				for (int a = 0; a < size; a++) {
					if (Truck_LIST.get(a).getTruckNoID().equals(TruckNoID)) {
						List<Sale> Sale_tmp = new ArrayList<Sale>();
						Sale_tmp = Truck_LIST.get(a).getSale_tmp();//车次底下所有订单
						int saleSize = Sale_tmp.size();
						if (saleSize >0) {
							for (int m = 0; m < saleSize; m++) {
								Map<String, Object> item = new HashMap<String, Object>();
								item.put("RowId", +m + 1);
								String temp=Sale_tmp.get(m).getSaleid();
								item.put("saleidSub", temp.substring(temp.length()-4));
								item.put("saleid", Sale_tmp.get(m).getSaleid());
								item.put("transdate", GetDate.getToday());
								item.put("userid", Sale_tmp.get(m).getUserid());
								item.put("username", Sale_tmp.get(m).getUsername());
								item.put("deliveraddress", Sale_tmp.get(m)
										.getDeliveraddress());
								item.put("telephone", Sale_tmp.get(m)
										.getHandphone());
								item.put("handphone", Sale_tmp.get(m)
										.getHandphone());
								item.put("paymode", Sale_tmp.get(m).getPaymode());
								item.put("revokeyn", Sale_tmp.get(m).getRevokeyn());
								item.put("goodscount", Sale_tmp.get(m)
										.getGoodscount());
								item.put("totalprice", Sale_tmp.get(m)
										.getTotalprice());
								item.put("comment", Sale_tmp.get(m).getComment());
								List<SaleDetail> TEMP=new ArrayList<SaleDetail>();
								TEMP=Sale_tmp.get(m).getSaledetail();
								int sizeTEPM=TEMP.size();
								for(int b=0;b<sizeTEPM;b++){
									SaleDetailInfo_LIST.add(TEMP.get(b));
								}
//
								sale_Items.add(item);
							}
						}
					}
				}
				// 设置 适配器
				SetGVAdapter();
			} else {
				new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
						null, this, AlertView.Style.Alert, this).show();

			}
		}catch(Exception ex){

		}

	}
	Map<String, Object> title2;
	private void SetGVDetailTitle() {
		title2 = new HashMap<String, Object>();
		title2.put("goodsname", "商品名称");
		title2.put("goodsprice", "商品价格");
		title2.put("goodscount", "数量");
		title2.put("decreaseprice_deliver", "送货单价");
		title2.put("deliverprice", "运费差价");
		title2.put("decreaseprice_goods", "商品差价");
		title2.put("totalprice", "总价");
		title2.put("bottleno", "满瓶电子标签");
		title2.put("ReceiveBottle", "空瓶电子标签");
		title2.put("Match", "是否匹配");
		title2.put("ReceiveBottleByHand", "手输标签号");
		saleDetail_Items.add(title2);
	}
	Map<String, Object> saletTitle;
	private void setGvTitle() {
		saletTitle = new HashMap<String, Object>();
		saletTitle.put("RowId", "编号");
		saletTitle.put("saleidSub", "订单号");
		saletTitle.put("transdate", "交易日期");
		saletTitle.put("userid", "送气号");
		saletTitle.put("username", "用户名");
		saletTitle.put("deliveraddress", "用户地址");
		saletTitle.put("telephone", "电话");
		saletTitle.put("handphone", "手机号");
		saletTitle.put("paymode", "付款方式");
		saletTitle.put("revokeyn", "状态");
		saletTitle.put("goodscount", "数量");
		saletTitle.put("totalprice", "金额");
		saletTitle.put("comment", "备注");
		sale_Items.add(saletTitle);

	}

	private void SetGVAdapter() {
		SimpleAdapter sa = new SimpleAdapter(TruckNoDetailActivity.this, // 上下文环境
				sale_Items, // 数据源
				R.layout.item_truckno_sale, // 内容布局
				new String[] { "RowId", "saleidSub", "transdate", "userid",
						"username", "deliveraddress", "telephone", "handphone",
						"paymode", "revokeyn", "goodscount", "totalprice",
						"comment" }, // 数据源的arrayName
				new int[] { R.id.rowId, R.id.saleId, R.id.saleDate,
						R.id.userid, R.id.username, R.id.address,
						R.id.telephone, R.id.phone, R.id.paymode, R.id.state,
						R.id.num, R.id.price, R.id.remark } // 装载数据的控件
		);
		gv_sale.setAdapter(sa); // 与gridview绑定
	}

	private void SetGVDetailAdapter() {
		SimpleAdapter sa = new SimpleAdapter(TruckNoDetailActivity.this, // 上下文环境
				saleDetail_Items, // 数据源
				R.layout.item_turckno_saledetail, // 内容布局
				new String[] { "goodsname", "goodsprice", "goodscount",
						"decreaseprice_deliver", "deliverprice",
						"decreaseprice_goods", "totalprice", "bottleno",
						"ReceiveBottle", "Match", "ReceiveBottleByHand" }, // 数据源的arrayName
				new int[] { R.id.goodsName, R.id.price, R.id.goodsCounts,
						R.id.goodsPrice, R.id.transPrice, R.id.dPrice,
						R.id.totalPrice, R.id.fullUserRegionCode,
						R.id.emptyUserRegionCode, R.id.isMatch,
						R.id.InputUserRegionCode } // 装载数据的控件
		);
		gv_saledetail.setAdapter(sa); // 与gridview绑定
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
