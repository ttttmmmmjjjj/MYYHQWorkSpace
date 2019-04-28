package com.hsic.sy.supply.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.Employee;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.bean.Truck_Info;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.SpinnerOption;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.supply.listener.AddOneTruckNoListener;
import com.hsic.sy.supply.listener.GetEmpNewListListener;
import com.hsic.sy.supply.listener.GetStationInfoListener;
import com.hsic.sy.supply.listener.GetTruck_infoListener;
import com.hsic.sy.supply.task.AddOneTruckNoTask;
import com.hsic.sy.supply.task.GetEmpNewListTask;
import com.hsic.sy.supply.task.GetStationInfoTask;
import com.hsic.sy.supply.task.GetTruck_infoTask;
import com.hsic.sy.supplystationmanager.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * 20181206 新增获取所有站点接口
 * 20181017 ���������ύ���� ��������
 * 
 * @author tmj
 * 
 */
public class TruckHomeFragment extends Fragment implements
		GetTruck_infoListener, AddOneTruckNoListener, OnClickListener,
		com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener,
		GetEmpNewListListener,GetStationInfoListener {
	private Spinner License, StuffID, DriverID, DeviceID, Sation, StuffName;
	private List<String> data_list;
	private ArrayAdapter<SpinnerOption> License_Adapter, Sation_Adapter,
			DriverID_Adapter, StuffID_Adapter, StuffName_Adapter;
	private View view;
	private Button Save;
	ArrayList<SpinnerOption> StationInfo_LIST;
	ArrayList<SpinnerOption> TruckInfo_LIST;
	ArrayList<SpinnerOption> Driver_LIST,Stuff_LIST;
	SpinnerOption PresentSelected;
	boolean isFinish = false;
	String StationID;
	GetBasicInfo getBasicInfo;// 20181019

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.truck_tab0, container, false);
		License = (Spinner) view.findViewById(R.id.truckNo);
		DriverID = (Spinner) view.findViewById(R.id.truckDriver);
		StuffID = (Spinner) view.findViewById(R.id.truckStuff);
		// StuffName = (Spinner) view.findViewById(R.id.stuffName);
		Sation = (Spinner) view.findViewById(R.id.station);

		Save = (Button) view.findViewById(R.id.save);
		Save.setOnClickListener(this);
		getBasicInfo = new GetBasicInfo(getActivity());
		StationID = getBasicInfo.getStationID();
		ExecuteTask();
		return view;
	}

	private void ExecuteTask() {
		Employee employee = new Employee();
		HsicMessage hsicMess = new HsicMessage();
		employee.setStationid(StationID);
		hsicMess.setRespMsg(JSONUtils.toJsonWithGson(employee));
		String responsedata = JSONUtils.toJsonWithGson(hsicMess);
		GetEmpNewListTask task = new GetEmpNewListTask(getActivity(), this);
		task.execute("", responsedata);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{
			switch (v.getId()) {

				case R.id.save:
					String driverID = "",
							SupPointCode="",//加瓶站点
							stuffID = "",
							truckId = "", // 车次
							license = "";// 车牌
					truckId = ((SpinnerOption) License.getSelectedItem()).getValue();
					license = ((SpinnerOption) License.getSelectedItem()).getText();
					PresentSelected = new SpinnerOption(truckId,
							((SpinnerOption) License.getSelectedItem()).getText());
//				if (truckId.equals("")) {
//					isFinish = false;
//					new AlertView("提示", "已无可新增的车!", null, new String[] { "确定" },
//							null, view.getContext(), AlertView.Style.Alert, this)
//							.show();
//					return;
//				}
					driverID = ((SpinnerOption) DriverID.getSelectedItem()).getValue();
					String temp = ((SpinnerOption) DriverID.getSelectedItem())
							.getText();
					if (driverID.equals("")) {
						isFinish = true;
						new AlertView("提示", "请选择驾驶员!", null, new String[] { "确定" },
								null, view.getContext(), AlertView.Style.Alert, this)
								.show();
						return;
					}
					if(Driver_LIST!=null){
						if(Driver_LIST.size()==0){
							new AlertView("提示", "无可操作的驾驶员!", null, new String[] { "确定" },
									null, view.getContext(), AlertView.Style.Alert, this)
									.show();
							return;
						}
					}else{
						new AlertView("提示", "无可操作的驾驶员!", null, new String[] { "确定" },
								null, view.getContext(), AlertView.Style.Alert, this)
								.show();
						return;
					}
					if(Stuff_LIST!=null){
						if(Stuff_LIST.size()==0){
							new AlertView("提示", "无可操作的业务员!", null, new String[] { "确定" },
									null, view.getContext(), AlertView.Style.Alert, this)
									.show();
							return;
						}
					}else{
						new AlertView("提示", "无可操作的业务员!", null, new String[] { "确定" },
								null, view.getContext(), AlertView.Style.Alert, this)
								.show();
						return;
					}
					stuffID = ((SpinnerOption) StuffID.getSelectedItem()).getValue();
					if (stuffID.equals("")) {
						isFinish = true;
						new AlertView("提示", "请选择员工!", null, new String[] { "确定" },
								null, view.getContext(), AlertView.Style.Alert, this)
								.show();
						return;
					}
					SupPointCode = ((SpinnerOption) Sation.getSelectedItem()).getValue();
					if (SupPointCode.equals("")) {
						isFinish = true;
						new AlertView("提示", "请选择加瓶站点!", null, new String[] { "确定" },
								null, view.getContext(), AlertView.Style.Alert, this)
								.show();
						return;
					}
					TruckInfo truckInfo = new TruckInfo();
					truckInfo.setState("0");
					truckInfo.setTruckID(truckId);
					truckInfo.setDriver(driverID);
					truckInfo.setSaleMan(stuffID);
					truckInfo.setLicense(license);
					truckInfo.setStationid(StationID);
					truckInfo.setGetQPStationid(SupPointCode);// 加瓶站点
					HsicMessage hsicMess = new HsicMessage();
					hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truckInfo));
					String responsedata = JSONUtils.toJsonWithGson(hsicMess);
					AddOneTruckNoTask add = new AddOneTruckNoTask(getActivity(), this);
					add.execute(responsedata);
					break;
				default:
					break;
			}
		}catch (Exception ex){

		}

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
	public void GetTruck_infoListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub

		if (tag.getRespCode() == 0) {
			// 获取数据
			Type typeOfT = new TypeToken<List<Truck_Info>>() {
			}.getType();
			List<Truck_Info> TruckInfo_LIST = new ArrayList<Truck_Info>();
			TruckInfo_LIST = JSONUtils
					.toListWithGson(tag.getRespMsg(), typeOfT);
			int size = TruckInfo_LIST.size();
			this.TruckInfo_LIST = new ArrayList<SpinnerOption>();
			for (int i = 0; i < size; i++) {

				String license_Temp = TruckInfo_LIST.get(i).getLicense();
				String Truck_id_Temp = TruckInfo_LIST.get(i).getTruck_id();// 车次不需要，加瓶站点需要
				SpinnerOption c_TruckInfo = new SpinnerOption(Truck_id_Temp,
						license_Temp);
				this.TruckInfo_LIST.add(c_TruckInfo);
			}
			/**
			 * 获取所有站点信息
			 */
			GetStationInfoTask task=new GetStationInfoTask(getActivity(),this);
			task.execute();
//			setData();
		} else {
			// isFinish=false;
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
		if (!isFinish) {
			// getActivity().finish();
			isFinish = false;
		}
	}

	@Override
	public void AddOneTruckNoListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if (tag.getRespCode() == 0) {
			// 重新设置
			isFinish = true;
			int h = TruckInfo_LIST.size();
			for (int a = 0; a < h; a++) {
				if (PresentSelected.getText().equals(
						TruckInfo_LIST.get(a).getText())) {
					TruckInfo_LIST.remove(a);
					break;
				}
			}

			License_Adapter = new ArrayAdapter<SpinnerOption>(
					view.getContext(), android.R.layout.simple_spinner_item,
					TruckInfo_LIST);
			License_Adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			License.setAdapter(License_Adapter);
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, view.getContext(), AlertView.Style.Alert, this)
					.show();
		} else {
			isFinish = false;
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, view.getContext(), AlertView.Style.Alert, this)
					.show();
		}

	}

	@Override
	public void GetEmpNewListListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if (tag.getRespCode() == 0) {
			Type typeOfT = new TypeToken<List<Employee>>() {
			}.getType();
			List<Employee> Employee_LIST = new ArrayList<Employee>();
			Employee_LIST = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
			int size = Employee_LIST.size();
			Driver_LIST = new ArrayList<SpinnerOption>();
			Stuff_LIST=new ArrayList<SpinnerOption>();
			for (int i = 0; i < size; i++) {
				if(Employee_LIST.get(i).getIdtype().equals("1")){
					//驾驶员
					String driverId_Temp = Employee_LIST.get(i).getEmpid();
					String driverName_Temp = Employee_LIST.get(i).getEmpname();
					SpinnerOption c_Driver = new SpinnerOption(driverId_Temp,
							driverName_Temp);
//					Driver_LIST = removeDuplicate(Driver_LIST, driverId_Temp);
					Driver_LIST.add(c_Driver);
				}else if(Employee_LIST.get(i).getIdtype().equals("2")){
					//业务员
					String driverId_Temp = Employee_LIST.get(i).getEmpid();
					String driverName_Temp = Employee_LIST.get(i).getEmpname();
					SpinnerOption c_Driver = new SpinnerOption(driverId_Temp,
							driverName_Temp);
//					Stuff_LIST = removeDuplicate(Stuff_LIST, driverId_Temp);
					Stuff_LIST.add(c_Driver);
				}else{
					//0 既是驾驶员又是业务员
					String driverId_Temp = Employee_LIST.get(i).getEmpid();
					String driverName_Temp = Employee_LIST.get(i).getEmpname();
					SpinnerOption c_Driver = new SpinnerOption(driverId_Temp,
							driverName_Temp);
//					Driver_LIST = removeDuplicate(Driver_LIST, driverId_Temp);
					Driver_LIST.add(c_Driver);
//					Stuff_LIST = removeDuplicate(Stuff_LIST, driverId_Temp);
					Stuff_LIST.add(c_Driver);
				}

			}

			Truck_Info truck_Info = new Truck_Info();
			HsicMessage hsicMess = new HsicMessage();
			truck_Info.setStationid(StationID);
			hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truck_Info));
			String responsedata = JSONUtils.toJsonWithGson(hsicMess);
			GetTruck_infoTask getTruck_infoTask = new GetTruck_infoTask(
					getActivity(), this);
			getTruck_infoTask.execute("", responsedata);
		} else {
			isFinish = false;
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, view.getContext(), AlertView.Style.Alert, this)
					.show();
		}
	}

	private void setData() {
		if (TruckInfo_LIST != null) {
			// 车牌号
			License_Adapter = new ArrayAdapter<SpinnerOption>(
					view.getContext(), android.R.layout.simple_spinner_item,
					TruckInfo_LIST);
			License_Adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			License.setAdapter(License_Adapter);
		}
		if (StationInfo_LIST != null) {
			// 站点
			Sation_Adapter = new ArrayAdapter<SpinnerOption>(view.getContext(),
					android.R.layout.simple_spinner_item, StationInfo_LIST);
			Sation_Adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			Sation.setAdapter(Sation_Adapter);
		}
		if (Stuff_LIST != null) {
			// 业务员
			StuffID_Adapter = new ArrayAdapter<SpinnerOption>(
					view.getContext(), android.R.layout.simple_spinner_item,
					Stuff_LIST);
			StuffID_Adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			StuffID.setAdapter(StuffID_Adapter);
		}
		if (Driver_LIST != null) {
			// 驾驶员
			DriverID_Adapter = new ArrayAdapter<SpinnerOption>(
					view.getContext(), android.R.layout.simple_spinner_item,
					Driver_LIST);
			DriverID_Adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			DriverID.setAdapter(DriverID_Adapter);
		}

		// if (Driver_LIST != null) {
		// // 员工姓名
		// StuffName_Adapter = new
		// ArrayAdapter<SpinnerOption>(view.getContext(),
		// android.R.layout.simple_spinner_item, Driver_LIST);
		// StuffName_Adapter
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// StuffName.setAdapter(StuffName_Adapter);
		// }

	}

	/**
	 * 数组去重
	 *
	 * @param list
	 * @return
	 */
	public static ArrayList<SpinnerOption> removeDuplicate(
			ArrayList<SpinnerOption> list, String str) {
		ArrayList<SpinnerOption> listTemp = new ArrayList<SpinnerOption>();
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).getValue().contains(str)) {
				listTemp.add(list.get(i));
			}
		}
		return listTemp;
	}

	@Override
	public void GetStationInfoListenerEnd(HsicMessage tag) {
		Log.e("GetStationInfo",JSONUtils.toJsonWithGson(tag));
		if(tag.getRespCode()==0){
			Type typeOfT = new TypeToken<List<Truck_Info>>() {
			}.getType();
			List<Truck_Info> Truck_LIST = new ArrayList<Truck_Info>();
			Truck_LIST = JSONUtils.toListWithGson(
					tag.getRespMsg(), typeOfT);
			int size = 0;
			size = Truck_LIST.size();
			StationInfo_LIST = new ArrayList<SpinnerOption>();
			for(int i=0;i<size;i++){
				String staionid_Temp = Truck_LIST.get(i).getStationid();
				String station_Name = Truck_LIST.get(i).getStationname();
				SpinnerOption c_StationInfo = new SpinnerOption(staionid_Temp,
						station_Name);
				StationInfo_LIST.add(c_StationInfo);

			}
//			Log.e("StationInfo_LIST",JSONUtils.toJsonWithGson(StationInfo_LIST));
			setData();
		}else{
			isFinish = false;
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, view.getContext(), AlertView.Style.Alert, this)
					.show();
		}
	}
}
