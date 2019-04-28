package com.hsic.sy.supply.activity;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bll.PathUtil;
import com.hsic.sy.bll.PrintUtils;
import com.hsic.sy.bll.TimeUtils;
import com.hsic.sy.picture.PictureHelper;
import com.hsic.sy.search.task.UpLoadTask;
import com.hsic.sy.supplystationmanager.R;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.search.bean.ApplicationHelper;
import com.hsic.sy.search.bean.LastUserInspection;
import com.hsic.sy.search.bean.UserRectifyInfo;
import com.hsic.sy.search.db.TB_UserRectifyInfo;
import com.hsic.sy.search.db.UpDataUserRectifyInfo;
import com.hsic.sy.search.listener.UploadTaskListener;

public class SerachActivity  extends Activity implements UploadTaskListener {
	private TabHost mTabHost;
	private List<CheckBox> StopSupply = new ArrayList<CheckBox>();
	private List<CheckBox> UnInstall = new ArrayList<CheckBox>();
	private CheckBox StopSupplyType1, StopSupplyType2, StopSupplyType3,
			StopSupplyType4, StopSupplyType5, StopSupplyType6, StopSupplyType7,StopSupplyType8,
			UnInstallType1, UnInstallType3,UnInstallType2, UnInstallType4,UnInstallType5,
			UnInstallType6,UnInstallType7,
			UnInstallType9,  UnInstallType11, UnInstallType12,upDate, repair, stop, result;// S:停止供气；N:不予接装
	public static String imagePath;
	UserRectifyInfo userRectifyInfo;
	ApplicationHelper applicationHelper;
	TB_UserRectifyInfo userRectifyInfo_TB;
	UpDataUserRectifyInfo UpData;
	String RectifyMan = "", userid = "",DeviceId = "", id="",inspecstatus = "";
	LastUserInspection lastUserInspection;
	GetBasicInfo getBasicInfo;//20181019
	StringBuffer lastInspection=new StringBuffer();
	StringBuffer rectifyInfo;
	Map<String, String> basicInfo ;
	String RectifyName="",UserName="",DeliverAddress="",Telphone="",RectifyDate="",Remark="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.serarch);
		mTabHost = (TabHost) findViewById(R.id.tabhost);
		mTabHost.setup();
		final TabSpec mTabSpec = mTabHost.newTabSpec("stop");
		TabSpec mTabSpec2 = mTabHost.newTabSpec("UNstall");
		mTabSpec.setIndicator("",
				getResources().getDrawable(R.drawable.stop_tab_seletcor));
		mTabSpec.setContent(R.id.page1);
		mTabHost.addTab(mTabSpec);

		mTabSpec2.setIndicator("",
				getResources().getDrawable(R.drawable.u_tab_selector));
		mTabSpec2.setContent(R.id.page2);
		mTabHost.addTab(mTabSpec2);
		initCheckBox();

		applicationHelper = (ApplicationHelper) getApplication();
		userRectifyInfo = applicationHelper.getUseRectifyInfo();
		getBasicInfo=new GetBasicInfo(SerachActivity.this);
		lastUserInspection = new LastUserInspection();
		try {
			RectifyMan = applicationHelper.getUseRectifyInfo().getRectifyMan();
			userid = applicationHelper.getUseRectifyInfo().getUserid();
		} catch (Exception ex) {
			ex.toString();
		}
		userRectifyInfo_TB = new TB_UserRectifyInfo(this);
		UpData = new UpDataUserRectifyInfo(this);
		Map<String, String> xj = new HashMap<String, String>();
		xj = userRectifyInfo_TB.GetLastSearch(userid, RectifyMan);
		setCheckBoxStatus(xj);
		DeviceId = getBasicInfo.getDeviceID();
		id=userRectifyInfo_TB.getID(userid, RectifyMan);
		// android 7.0系统解决拍照的问题
		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		builder.detectFileUriExposure();
		basicInfo=userRectifyInfo_TB.GetRectifyInfo(userid, RectifyMan);
		RectifyName=basicInfo.get("empname");
		UserName=basicInfo.get("username");
		DeliverAddress=basicInfo.get("deliveraddress");
		RectifyDate=basicInfo.get("InspectionDate");
		Remark=basicInfo.get("Remark");
	}
	/**
	 * 拍照事件
	 *
	 * @param view
	 */
	public void takePhoto(View view) {
		takePhoto(DeviceId, userid);
	}

	/**
	 * 预览事件
	 *
	 * @param view
	 */
	public void preView(View view) {
		preview(DeviceId, userid);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 1) {
				getImagePath(DeviceId, userid);
			}
		}
	}

	public void takePhoto(String deviceId, String userid) {
		imagePath = Environment.getExternalStorageDirectory() + "/photoes/"
				+ deviceId + "s" + ".jpg";
		File file = new File(imagePath);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File fileDir = new File(Environment.getExternalStorageDirectory(),
				"/photoes/");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)); // 保存图片的位置
		startActivityForResult(intent, 1);
	}

	public void getImagePath(String deviceId, String user) {
		File file = new File(imagePath);
		if (file != null && file.exists()) {
			String path = PathUtil.getImagePath();
			File file1 = new File(path);
			if (!file1.exists()) {
				file1.mkdirs();
			}
			Date date = new Date();
			String pattern = "yyyyMMddHHmmss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					Locale.getDefault());
			String format = sdf.format(date);
			File file2 = new File(file1.getPath(), id+"d"+ deviceId +"u" + user + "_"
					+ format + ".jpg");
			PictureHelper.compressPicture(file.getAbsolutePath(),
					file2.getAbsolutePath(), 720, 1280);
			if (file.exists()) {
				file.delete();
			}
			File fileDir = new File(Environment.getExternalStorageDirectory(),
					"/photoes/");
			if (fileDir.exists()) {
				fileDir.delete();
			}
		}
	}

	public void preview(String deviceId, String userid) {
		int flag = 0;
		String filePath = PathUtil.getImagePath();
		File file = new File(filePath);
		String[] paths = file.list();
//		Log.e("bb", JSONUtils.toJsonWithGson(paths));
		if (paths != null && paths.length > 0) {
			for (int i = 0; i < paths.length; i++) {
				String path = paths[i];
				if (path.contains(id+"d"+ DeviceId +"u" + userid)) {
					flag++;
				}
			}
			if (flag == 0) {
				Toast.makeText(getApplicationContext(), "请先拍照后在预览",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				Intent intent = new Intent(this, SearchPicPreviewActivity.class);
				intent.putExtra("userid", userid);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		} else {
			Toast.makeText(getApplicationContext(), "请先拍照后在预览",
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 保存巡检状态事件
	 *
	 * @param view
	 */
	boolean save = false;

	public void save(View view) {
		Date date = new Date();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
				Locale.getDefault());
		String inspectedDate = simpleDateFormat.format(date);// 获取巡检日期
		int photoesCount = pictureCounts(DeviceId, userid);
		saveState(DeviceId);// 保存巡检状态
		String temp = "";
		temp = userRectifyInfo.getInspectionStatus();

		if (temp != null) {
			if (photoesCount <= 0) {
				Toast.makeText(getApplicationContext(), "请拍照",
						Toast.LENGTH_SHORT).show();
				return;
			}
		}
		if (userRectifyInfo.getInspectionStatus().equals("2")) {
			userRectifyInfo.setStopYY(stop());
		}

		userRectifyInfo.setInspectionDate(inspectedDate);
		applicationHelper.setUseRectifyInfo(userRectifyInfo);
		applicationHelper.setLastUserInspection(lastUserInspection);
		// 插入数据库
		boolean isInspected = userRectifyInfo_TB
				.IsInspected(userid, RectifyMan);
		if (isInspected) {
			Toast.makeText(getApplicationContext(), "订单已保存成功!",
					Toast.LENGTH_SHORT).show();
			applicationHelper.setLastUserInspection(lastUserInspection);// 上次巡检信息
			/**
			 * dayin
			 */
			getRectifyResult();
			new PrintCodeTask(SerachActivity.this).execute();
		} else {
			save = UpData.updata(applicationHelper, userid, RectifyMan);
			if (save) {
				Toast.makeText(getApplicationContext(), "订单保存成功!",
						Toast.LENGTH_SHORT).show();
				// 跳转到打印页面
				applicationHelper.setLastUserInspection(lastUserInspection);// 上次巡检信息
			}
			/**
			 * dayin
			 */
			getRectifyResult();
			new PrintCodeTask(SerachActivity.this).execute();
		}

	}

	public void up(View view) {
		boolean isInspect = userRectifyInfo_TB.IsInspected(userid, RectifyMan);
		if (isInspect) {
			UpLoadTask up = new UpLoadTask(this, this);
			up.execute(userid, RectifyMan, DeviceId);

		}else{
			Toast.makeText(getApplicationContext(), "无可提交的订单",
					Toast.LENGTH_SHORT).show();
		}
	}

	public String stop() {
		StringBuffer sb = new StringBuffer();
		int j = 1;
		for (CheckBox box : StopSupply) {
			if (box.isChecked()) {
				if (sb.toString().length() == 0) {
					sb.append(getStopitem(j));
				} else {
					sb.append("," + getStopitem(j));
				}
			}
			j++;
		}
		return sb.toString();

	}

	/**
	 * 保存巡检项同时把巡检项插入数据库
	 */
	private void saveState(String DeviceId){
		boolean UNStatus = false;
		boolean StopStatus = false;
		if(UnInstallType1.isChecked()){
			UNStatus = true;
			userRectifyInfo.setUnInstallType1("1");
		}
		if(UnInstallType2.isChecked()){
			UNStatus = true;
			userRectifyInfo.setUnInstallType2("1");
		}
		if(UnInstallType3.isChecked()){
			UNStatus = true;
			userRectifyInfo.setUnInstallType3("1");
		}
		if(UnInstallType4.isChecked()){
			userRectifyInfo.setUnInstallType4("1");
			UNStatus = true;
		}
		if(UnInstallType5.isChecked()){
			UNStatus = true;
			userRectifyInfo.setUnInstallType5("1");
		}
		if(UnInstallType6.isChecked()){
			userRectifyInfo.setUnInstallType6("1");
			UNStatus = true;
		}
		if(UnInstallType7.isChecked()){
			UNStatus = true;
			userRectifyInfo.setUnInstallType7("1");
		}
		if(UnInstallType9.isChecked()){
			userRectifyInfo.setUnInstallType9("1");
			UNStatus = true;
		}
		if(UnInstallType11.isChecked()){
			userRectifyInfo.setUnInstallType11("1");
			UNStatus = true;
		}
		if(UnInstallType12.isChecked()){
			userRectifyInfo.setUnInstallType12("1");
			UNStatus = true;
		}
		if(StopSupplyType1.isChecked()){
			userRectifyInfo.setStopSupplyType1("1");
			StopStatus = true;
		}
		if(StopSupplyType2.isChecked()){
			userRectifyInfo.setStopSupplyType2("1");
			StopStatus = true;
		}
		if(StopSupplyType3.isChecked()){
			userRectifyInfo.setStopSupplyType3("1");
			StopStatus = true;
		}
		if(StopSupplyType4.isChecked()){
			userRectifyInfo.setStopSupplyType4("1");
			StopStatus = true;
		}
		if(StopSupplyType5.isChecked()){
			userRectifyInfo.setStopSupplyType5("1");
			StopStatus = true;
		}
		if(StopSupplyType6.isChecked()){
			userRectifyInfo.setStopSupplyType6("1");
			StopStatus = true;
		}
		if(StopSupplyType7.isChecked()){
			userRectifyInfo.setStopSupplyType7("1");
			StopStatus = true;
		}
		if(StopSupplyType8.isChecked()){
			userRectifyInfo.setStopSupplyType8("1");
			StopStatus = true;
		}
		String OperationResult="";
		if (upDate.isChecked()) {
			OperationResult += getResources().getString(R.string.update)+",";
		}
		if (repair.isChecked()) {
			OperationResult += getResources().getString(R.string.repair)+",";
		}
		if (stop.isChecked()) {
			OperationResult += getResources().getString(R.string.stop)+",";
		}
		if (result.isChecked()) {
			OperationResult += getResources().getString(R.string.result)+",";
		}
		if(!OperationResult.equals("")){
			OperationResult=OperationResult.substring(0, OperationResult.length()-1);
		}
		userRectifyInfo.setOperationResult(OperationResult);
		String relationID = "";
		relationID =id+ "d"+DeviceId +"u" + userid;
		userRectifyInfo.setRelationID(relationID);
		if (StopStatus) {
			userRectifyInfo.setInspectionStatus("2");
			userRectifyInfo.setIsInspected("1");// 本地标识：做过巡检
			userRectifyInfo.setUnInstallType1("0");
			userRectifyInfo.setUnInstallType2("0");
			userRectifyInfo.setUnInstallType3("0");
			userRectifyInfo.setUnInstallType4("0");
			userRectifyInfo.setUnInstallType5("0");
			userRectifyInfo.setUnInstallType6("0");
			userRectifyInfo.setUnInstallType7("0");
			userRectifyInfo.setUnInstallType9("0");
			userRectifyInfo.setUnInstallType11("0");
			userRectifyInfo.setUnInstallType12("0");
			applicationHelper.setUseRectifyInfo(userRectifyInfo);

		} else if (UNStatus) {
			userRectifyInfo.setInspectionStatus("1");
			userRectifyInfo.setIsInspected("1");
			userRectifyInfo.setStopYY("");
			userRectifyInfo.setStopSupplyType1("0");
			userRectifyInfo.setStopSupplyType2("0");
			userRectifyInfo.setStopSupplyType3("0");
			userRectifyInfo.setStopSupplyType4("0");
			userRectifyInfo.setStopSupplyType5("0");
			userRectifyInfo.setStopSupplyType6("0");
			userRectifyInfo.setStopSupplyType7("0");
			userRectifyInfo.setStopSupplyType8("0");
			applicationHelper.setUseRectifyInfo(userRectifyInfo);
		} else {
			userRectifyInfo.setInspectionStatus("0");
			userRectifyInfo.setIsInspected("1");
			userRectifyInfo.setStopYY("");
			userRectifyInfo.setStopSupplyType1("0");
			userRectifyInfo.setStopSupplyType2("0");
			userRectifyInfo.setStopSupplyType3("0");
			userRectifyInfo.setStopSupplyType4("0");
			userRectifyInfo.setStopSupplyType5("0");
			userRectifyInfo.setStopSupplyType6("0");
			userRectifyInfo.setStopSupplyType7("0");
			userRectifyInfo.setStopSupplyType8("0");
			userRectifyInfo.setUnInstallType1("0");
			userRectifyInfo.setUnInstallType2("0");
			userRectifyInfo.setUnInstallType3("0");
			userRectifyInfo.setUnInstallType4("0");
			userRectifyInfo.setUnInstallType5("0");
			userRectifyInfo.setUnInstallType6("0");
			userRectifyInfo.setUnInstallType7("0");
			userRectifyInfo.setUnInstallType9("0");
			userRectifyInfo.setUnInstallType11("0");
			userRectifyInfo.setUnInstallType12("0");
			applicationHelper.setUseRectifyInfo(userRectifyInfo);
		}

	}

	/**
	 * 照片数量
	 *
	 * @return
	 */
	private int pictureCounts(String deviceId, String userid) {
		ArrayList<String> mList = new ArrayList<String>();
		String photoesPattern = id+ "d"+DeviceId +"u" + userid;;
		String filePath = PathUtil.getImagePath();
		File file = new File(filePath);
		String[] paths = file.list();
		if (paths != null && paths.length > 0) {
			for (int i = 0; i < paths.length; i++) {
				String path = filePath + paths[i];
				if (path.contains(photoesPattern)) {
					mList.add(path);
				}
			}

		}
		return mList.size();
	}
	public void initCheckBox() {
		StopSupplyType1 = (CheckBox) this.findViewById(R.id.basement);
		StopSupplyType2 = (CheckBox) this.findViewById(R.id.half_basement);
		StopSupplyType3 = (CheckBox) this.findViewById(R.id.Highrise);
		StopSupplyType4 = (CheckBox) this.findViewById(R.id.crowd_places);
		StopSupplyType5 = (CheckBox) this.findViewById(R.id.Airtight);
		StopSupplyType6 = (CheckBox) this.findViewById(R.id.bedroom);
		StopSupplyType7 = (CheckBox) this.findViewById(R.id.two_gas);
		StopSupplyType8 = (CheckBox) this.findViewById(R.id.Others);

		UnInstallType1 = (CheckBox) this.findViewById(R.id.no_safe_EXTRCTOR);
		UnInstallType2 = (CheckBox) this.findViewById(R.id.UnInstallType2);
		UnInstallType3 = (CheckBox) this.findViewById(R.id.no_safe_gas_cooker);
		UnInstallType4 = (CheckBox) this.findViewById(R.id.no_match);
		UnInstallType5 = (CheckBox) this.findViewById(R.id.UnInstallType5);
		UnInstallType6 = (CheckBox) this.findViewById(R.id.no_rubber);
		UnInstallType7 = (CheckBox) this.findViewById(R.id.UnInstallType7);
		UnInstallType9 = (CheckBox) this.findViewById(R.id.leak);
		UnInstallType11 = (CheckBox) this.findViewById(R.id.have_fire);
		UnInstallType12 = (CheckBox) this.findViewById(R.id.other);
		StopSupply.add(StopSupplyType1);
		StopSupply.add(StopSupplyType2);
		StopSupply.add(StopSupplyType3);
		StopSupply.add(StopSupplyType4);
		StopSupply.add(StopSupplyType5);
		StopSupply.add(StopSupplyType6);
		StopSupply.add(StopSupplyType7);
		StopSupply.add(StopSupplyType8);


		UnInstall.add(UnInstallType1);
		UnInstall.add(UnInstallType2);
		UnInstall.add(UnInstallType3);
		UnInstall.add(UnInstallType4);
		UnInstall.add(UnInstallType5);
		UnInstall.add(UnInstallType6);
		UnInstall.add(UnInstallType7);
		UnInstall.add(UnInstallType9);
		UnInstall.add(UnInstallType11);
		UnInstall.add(UnInstallType12);

		upDate = (CheckBox) this.findViewById(R.id.update);
		repair = (CheckBox) this.findViewById(R.id.repair);
		stop = (CheckBox) this.findViewById(R.id.stop);
		result = (CheckBox) this.findViewById(R.id.result);

	}
	/**
	 * 根据巡检表，设置复选框状态
	 *
	 * @param xj
	 */
	private void setCheckBoxStatus(Map<String, String> xj) {
		if (xj != null) {
			int i = xj.size();
			if (xj.containsKey("InspectionStatus")) {
				if (xj.get("InspectionStatus").equals("1")) {
					lastUserInspection.setInspectionStatus("1");
					mTabHost.setCurrentTab(1);
					setUnCheckBoxStatus(xj);

				} else if (xj.get("InspectionStatus").equals("2")) {
					lastUserInspection.setInspectionStatus("2");
					mTabHost.setCurrentTab(0);
					setSCheckBoxStatus(xj);
				} else {
					lastUserInspection.setInspectionStatus("0");
				}
			}

		}

	}

	/**
	 * 设置严重隐患控件状态
	 *
	 * @param xj
	 */
	private void setSCheckBoxStatus(Map<String, String> xj) {
		if (xj.containsKey("StopSupplyType1")) {
			if (xj.get("StopSupplyType1").equals("1")) {
				StopSupplyType1.setChecked(true);
				lastUserInspection.setStopSupplyType1("1");
				lastInspection.append(getResources().getString(R.string.StopSupplyType1)
						+ "\n");
			} else {
				StopSupplyType1.setChecked(false);
				lastUserInspection.setStopSupplyType1("0");
			}
		}
		if (xj.containsKey("StopSupplyType2")) {
			if (xj.get("StopSupplyType2").equals("1")) {
				StopSupplyType2.setChecked(true);
				lastUserInspection.setStopSupplyType2("1");
				lastInspection.append(getResources().getString(R.string.StopSupplyType2)
						+ "\n");
			} else {
				StopSupplyType2.setChecked(false);
				lastUserInspection.setStopSupplyType2("0");
			}
		}
		if (xj.containsKey("StopSupplyType3")) {
			if (xj.get("StopSupplyType3").equals("1")) {
				StopSupplyType3.setChecked(true);
				lastUserInspection.setStopSupplyType3("1");
				lastInspection.append(getResources().getString(R.string.StopSupplyType3)
						+ "\n");
			} else {
				StopSupplyType3.setChecked(false);
				lastUserInspection.setStopSupplyType3("0");
			}
		}
		if (xj.containsKey("StopSupplyType4")) {
			if (xj.get("StopSupplyType4").equals("1")) {
				StopSupplyType4.setChecked(true);
				lastUserInspection.setStopSupplyType4("1");
				lastInspection.append(getResources().getString(R.string.StopSupplyType4)
						+ "\n");
			} else {
				StopSupplyType4.setChecked(false);
				lastUserInspection.setStopSupplyType4("0");
			}
		}
		if (xj.containsKey("StopSupplyType5")) {
			if (xj.get("StopSupplyType5").equals("1")) {
				StopSupplyType5.setChecked(true);
				lastUserInspection.setStopSupplyType5("1");
				lastInspection.append(getResources().getString(R.string.StopSupplyType5)
						+ "\n");
			} else {
				StopSupplyType5.setChecked(false);
				lastUserInspection.setStopSupplyType5("0");
			}
		}
		if (xj.containsKey("StopSupplyType6")) {
			if (xj.get("StopSupplyType6").equals("1")) {
				StopSupplyType6.setChecked(true);
				lastUserInspection.setStopSupplyType6("1");
				lastInspection.append(getResources().getString(R.string.StopSupplyType6)
						+ "\n");
			} else {
				StopSupplyType6.setChecked(false);
				lastUserInspection.setStopSupplyType6("0");
			}
		}
		if (xj.containsKey("StopSupplyType7")) {
			if (xj.get("StopSupplyType7").equals("1")) {
				StopSupplyType7.setChecked(true);
				lastUserInspection.setStopSupplyType7("1");
				lastInspection.append(getResources().getString(R.string.StopSupplyType7)
						+ "\n");
			} else {
				StopSupplyType7.setChecked(false);
				lastUserInspection.setStopSupplyType7("0");
			}
		}
		if (xj.containsKey("StopSupplyType8")) {
			if (xj.get("StopSupplyType8").equals("1")) {
				lastUserInspection.setStopSupplyType8("1");
				lastInspection.append(getResources().getString(R.string.StopSupplyType8)
						+ "\n");
			} else {
				lastUserInspection.setStopSupplyType8("0");
			}
		}
	}

	/**
	 * 设置一般隐患控件状态
	 *
	 * @param xj
	 */
	private void setUnCheckBoxStatus(Map<String, String> xj) {
		lastUserInspection.setInspectionStatus("1");
		if (xj.containsKey("UnInstallType1")) {
			if (xj.get("UnInstallType1").equals("1")) {
				UnInstallType1.setChecked(true);
				lastUserInspection.setUnInstallType1("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType1)
						+ "\n");
			} else {
				UnInstallType1.setChecked(false);
				lastUserInspection.setUnInstallType1("0");
			}
		}
		if (xj.containsKey("UnInstallType2")) {
			if (xj.get("UnInstallType2").equals("1")) {
				lastUserInspection.setUnInstallType2("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType2)
						+ "\n");
			} else {
				lastUserInspection.setUnInstallType2("0");
			}
		}
		if (xj.containsKey("UnInstallType3")) {
			if (xj.get("UnInstallType3").equals("1")) {
				UnInstallType3.setChecked(true);
				lastUserInspection.setUnInstallType3("1");lastInspection.append(getResources().getString(R.string.UnInstallType3)
						+ "\n");

			} else {
				UnInstallType3.setChecked(false);
				lastUserInspection.setUnInstallType3("0");
			}
		}
		if (xj.containsKey("UnInstallType4")) {
			if (xj.get("UnInstallType4").equals("1")) {
				UnInstallType4.setChecked(true);
				lastUserInspection.setUnInstallType4("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType4)
						+ "\n");
			} else {
				UnInstallType4.setChecked(false);
				lastUserInspection.setUnInstallType4("0");
			}
		}
		if (xj.containsKey("UnInstallType5")) {
			if (xj.get("UnInstallType5").equals("1")) {
				lastUserInspection.setUnInstallType5("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType5)
						+ "\n");
			} else {
				lastUserInspection.setUnInstallType5("0");
			}
		}
		if (xj.containsKey("UnInstallType6")) {
			if (xj.get("UnInstallType6").equals("1")) {
				UnInstallType6.setChecked(true);
				lastUserInspection.setUnInstallType6("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType6)
						+ "\n");
			} else {
				UnInstallType6.setChecked(false);
				lastUserInspection.setUnInstallType6("0");
			}
		}
		if (xj.containsKey("UnInstallType7")) {
			if (xj.get("UnInstallType7").equals("1")) {
				lastUserInspection.setUnInstallType7("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType7)
						+ "\n");
			} else {
				lastUserInspection.setUnInstallType7("0");
			}
		}
		if (xj.containsKey("UnInstallType8")) {
			if (xj.get("UnInstallType8").equals("1")) {
				lastUserInspection.setUnInstallType8("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType8)
						+ "\n");
			} else {;
				lastUserInspection.setUnInstallType8("0");
			}
		}
		if (xj.containsKey("UnInstallType9")) {
			if (xj.get("UnInstallType9").equals("1")) {
				UnInstallType9.setChecked(true);
				lastUserInspection.setUnInstallType9("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType9)
						+ "\n");
			} else {
				UnInstallType9.setChecked(false);
				lastUserInspection.setUnInstallType9("0");
			}
		}
		if (xj.containsKey("UnInstallType10")) {
			if (xj.get("UnInstallType10").equals("1")) {
				lastUserInspection.setUnInstallType10("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType10)
						+ "\n");
			} else {
				lastUserInspection.setUnInstallType10("0");
			}
		}
		if (xj.containsKey("UnInstallType11")) {
			if (xj.get("UnInstallType11").equals("1")) {
				UnInstallType11.setChecked(true);
				lastUserInspection.setUnInstallType11("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType11)
						+ "\n");
			} else {
				UnInstallType11.setChecked(false);
				lastUserInspection.setUnInstallType11("0");
			}
		}
		if (xj.containsKey("UnInstallType12")) {
			if (xj.get("UnInstallType12").equals("1")) {
				UnInstallType12.setChecked(true);
				lastUserInspection.setUnInstallType12("1");
				lastInspection.append(getResources().getString(R.string.UnInstallType12)
						+ "\n");
			} else {
				UnInstallType12.setChecked(false);
				lastUserInspection.setUnInstallType12("0");
			}
		}
	}

	/**
	 * 获取严重隐患具体名称（用于历史记录）
	 *
	 * @param i
	 * @return
	 */
	private String getStopitem(int i) {
		StringBuffer item = new StringBuffer();
		switch (i) {
			case 1:
				item.append(getResources().getString(R.string.StopSupplyType1)
						+ ",");
				break;
			case 2:
				item.append(getResources().getString(R.string.StopSupplyType2)
						+ ",");
				break;
			case 3:
				item.append(getResources().getString(R.string.StopSupplyType3)
						+ ",");
				break;
			case 4:
				item.append(getResources().getString(R.string.StopSupplyType4)
						+ ",");
				break;
			case 5:
				item.append(getResources().getString(R.string.StopSupplyType5)
						+ ",");
				break;
			case 6:
				item.append(getResources().getString(R.string.StopSupplyType6)
						+ ",");
				break;
			case 7:
				item.append(getResources().getString(R.string.StopSupplyType7)
						+ ",");
				break;
			case 8:
				item.append(getResources().getString(R.string.StopSupplyType8)
						+ ",");
				break;
			default:
				break;
		}
		return item.toString();
	}

	/**
	 * 获取一般隐患具体名称（用于历史记录）
	 *
	 *
	 * @return
	 */
	private String getRectifyResult() {
		rectifyInfo=new StringBuffer();
		if(userRectifyInfo.getInspectionStatus().equals("2")){
			if(StopSupplyType1.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.StopSupplyType1)+"\n");
			}
			if(StopSupplyType2.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.StopSupplyType2)+"\n");
			}
			if(StopSupplyType3.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.StopSupplyType3)+"\n");
			}
			if(StopSupplyType4.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.StopSupplyType4)+"\n");
			}

			if(StopSupplyType5.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.StopSupplyType5)+"\n");
			}
			if(StopSupplyType6.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.StopSupplyType6)+"\n");
			}
			if(StopSupplyType7.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.StopSupplyType7)+"\n");
			}
			if(StopSupplyType8.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.StopSupplyType8));
			}
		}else if(userRectifyInfo.getInspectionStatus().equals("1")){
			if(UnInstallType1.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType1)+"\n");
			}
			if(UnInstallType2.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType2)+"\n");
			}
			if(UnInstallType3.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType3)+"\n");
			}
			if(UnInstallType4.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType4)+"\n");
			}
			if(UnInstallType5.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType5)+"\n");
			}
			if(UnInstallType6.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType6)+"\n");
			}
			if(UnInstallType7.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType7)+"\n");
			}
			if(UnInstallType9.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType9)+"\n");
			}
			if(UnInstallType11.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType11)+"\n");
			}
			if(UnInstallType12.isChecked()){
				rectifyInfo.append(getResources().getString(R.string.UnInstallType12));
			}
		}
		return rectifyInfo.toString();
	}
	@Override
	public void UploadTaskListenerEnd(Boolean num) {
		// TODO Auto-generated method stub
		if (num) {
			this.finish();

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
	/**
	 *打印
	 */
	public class PrintCodeTask extends AsyncTask<Void, Void, Void> {
		private byte[] saleID;
		private Context context = null;
		private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		private boolean isConnection = false;
		private BluetoothDevice device = null;
		private BluetoothSocket bluetoothSocket = null;
		private OutputStream outputStream;
		private  final UUID uuid = UUID
				.fromString("00001101-0000-1000-8000-00805F9B34FB");
		SharedPreferences deviceSetting;
		String bluetoothadd = "";// 蓝牙MAC
		private ProgressDialog dialog;
		GetBasicInfo basicInfo;
		public PrintCodeTask(Context context) {
			this.context = context;
			deviceSetting = context.getSharedPreferences("DeviceSetting", 0);
			bluetoothadd = deviceSetting.getString("BlueToothAdd", "");// 蓝牙MAC
			dialog = new ProgressDialog(context);
			basicInfo = new GetBasicInfo(context);


		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("正在打印信息");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... voids) {
			try {
				int pCount = 3;
//                int pCount = 1;
//                pCount = Integer.parseInt("1");
				//测试(最新测试)
				String Intret = connectBT();
				PrintUtils.setOutputStream(outputStream);
				for(int a=0;a<pCount;a++){
					PrintUtils.selectCommand(PrintUtils.RESET);
					PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
					PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
					PrintUtils.selectCommand(PrintUtils.BOLD);
					PrintUtils.printText("\n");
					PrintUtils.printText( "\n");//公司
					PrintUtils.printText("整改收据\n");
					PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
//                    PrintUtils.selectCommand(PrintUtils.NORMAL);
//                    PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
					PrintUtils.selectCommand(PrintUtils.NORMAL);
					PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
//					PrintUtils.printText(PrintUtils.printTwoData("整改单", Sale.getSaleID() + "\n"));
					PrintUtils.printText(PrintUtils.printTwoData("整改用户", UserName + "\n"));
					PrintUtils.printText( DeliverAddress+ "\n");
					PrintUtils.printText("--------------------------------\n");
					if(!lastInspection.toString().equals("")){
						PrintUtils.printText("上次巡检不合格项:"+ "\n");
						PrintUtils.printText(lastInspection.toString() );
					}
					PrintUtils.printText("--------------------------------\n");
					if(!rectifyInfo.toString().equals("")){
						PrintUtils.printText("本次安检结果:未整改" + "\n");
						PrintUtils.printText("本次巡检不合格项:" + "\n");
						PrintUtils.printText(rectifyInfo.toString() + "\n");
					}else{
						PrintUtils.printText("本次安检结果:已整改" + "\n");
					}
					PrintUtils.printText("--------------------------------\n");
					if(Remark!=null){
						if(!Remark.equals("")){
							PrintUtils.printText(PrintUtils.printTwoData("整改人:",RectifyName+ "\n"));
							PrintUtils.printText(PrintUtils.printTwoData("整改日期:",userRectifyInfo.getInspectionDate()+ "\n"));
							PrintUtils.printText(PrintUtils.printTwoData("备注:",Remark+ "\n"));
						}else{
							PrintUtils.printText(PrintUtils.printTwoData("整改人:",RectifyName+ "\n"));
							PrintUtils.printText(PrintUtils.printTwoData("整改日期:",userRectifyInfo.getInspectionDate()+ "\n"));
						}
					}else{
						PrintUtils.printText(PrintUtils.printTwoData("整改人:",RectifyName+ "\n"));
						PrintUtils.printText(PrintUtils.printTwoData("整改日期:",userRectifyInfo.getInspectionDate()+ "\n"));
					}

					PrintUtils.printText("\n\n\n\n\n");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				ex.toString();
				close();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			dialog.setCancelable(true);
			dialog.dismiss();
			close();
		}
		public void printCode(byte[] saleID, boolean charactor) {
			try {
				outputStream.write(saleID, 0, saleID.length);
				outputStream.flush();
			} catch (Exception ex) {

			}
		}

		public String connectBT() {
			String log = "connectBT()";
			// 先检查该设备是否支持蓝牙
			if (bluetoothAdapter == null) {
				return "1";// 该设备没有蓝牙功能
			} else {
				// 检查蓝牙是否打开
				boolean b = bluetoothAdapter.isEnabled();
				if (!bluetoothAdapter.isEnabled()) {
					// 若没打开，先打开蓝牙
					bluetoothAdapter.enable();
					System.out.print("蓝牙未打开");
					return "2";// 蓝牙未打开，程序强制打开蓝牙
				} else {
					try {
						this.device = bluetoothAdapter
								.getRemoteDevice(bluetoothadd);
						if (!this.isConnection) {
							bluetoothSocket = this.device
									.createRfcommSocketToServiceRecord(uuid);
							bluetoothSocket.connect();
							outputStream = bluetoothSocket.getOutputStream();
							this.isConnection = true;
						}
					} catch (Exception ex) {
						System.out.print("远程获取设备出现异常" + ex.toString());
						return "3";// 获取设备出现异常
					}
				}
				return "0";// 连接成功
			}

		}

		private void close() {
			try {
				if (bluetoothSocket != null) {
					bluetoothSocket.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
