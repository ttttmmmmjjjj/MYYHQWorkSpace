package com.hsic.sy.supply.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.adapter.DragDelListAdaper;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import android.widget.AdapterView.OnItemClickListener;

import com.hsic.sy.dragdellistview.DragDelListView;
import com.hsic.sy.supply.listener.GetTruckAllRecordsListener;
import com.hsic.sy.supply.task.GetTruckAllRecordsTask;
import com.hsic.sy.supplystationmanager.R;

/**
 * ����
 * 
 * @author Administrator
 * 
 */
public class TruckCanselFragment extends Fragment implements
		GetTruckAllRecordsListener,com.hsic.sy.dialoglibrary. OnItemClickListener,
		OnDismissListener {
	private ArrayAdapter mAdapter = null;
	private DragDelListView mListView;
	private View view;
	String StationID;
	GetBasicInfo getBasicInfo;// 20181019

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_listview, container, false);
		mListView = (DragDelListView) view.findViewById(R.id.id_listview);

		// 设置列表项Item点击监听事件
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
			}

		});
		getBasicInfo = new GetBasicInfo(getActivity());
		StationID = getBasicInfo.getStationID();
		ExecuteTask();
		return view;
	}

	private void ExecuteTask() {
		TruckInfo truckInfo = new TruckInfo();
		HsicMessage hsicMess = new HsicMessage();
		truckInfo.setStartTime(GetDate.getToday());
		truckInfo.setEndTime(GetDate.getToday());
		truckInfo.setStationid(StationID);
		hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truckInfo));
		String responsedata = JSONUtils.toJsonWithGson(hsicMess);
		GetTruckAllRecordsTask task = new GetTruckAllRecordsTask(getActivity(),
				this);
		task.execute("", responsedata);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			ExecuteTask();
		}
	}

	@Override
	public void GetTruckAllRecordsListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		String ret = tag.getRespMsg();
		if (tag.getRespCode() == 0) {
			// 获取数据
			List<TruckInfo> mDatas;
			Type typeOfT = new TypeToken<List<TruckInfo>>() {
			}.getType();
			List<TruckInfo> Truck_LIST = new ArrayList<TruckInfo>();
			Truck_LIST = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
			int size = 0;
			size = Truck_LIST.size();
			mDatas = new ArrayList<TruckInfo>();
			if (size > 0) {
				for (int a = 0; a < size; a++) {
					if (Truck_LIST.get(a).getState().equals("0")) {
						TruckInfo temp = Truck_LIST.get(a);
						mDatas.add(temp);
					}
				}
			}
			// 将数据添加到ListView
			// ListView设置数据
			mListView.setAdapter(new DragDelListAdaper(getActivity(), mDatas,
					"4"));

		} else {
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, view.getContext(), AlertView.Style.Alert, this)
					.show();
		}

	}

	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub

	}

}
