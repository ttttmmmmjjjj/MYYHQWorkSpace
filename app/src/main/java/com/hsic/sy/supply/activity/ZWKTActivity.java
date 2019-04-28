package com.hsic.sy.supply.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.bll.PathUtil;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.picture.PictureHelper;
import com.hsic.sy.supply.task.UploadExpInfoTask;
import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.bean.UserExploration;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.search.bean.ApplicationHelper;
import com.hsic.sy.supply.listener.UploadExpInfoListener;

/**
 * ��Ƭ��������:
 * @author Administrator
 *
 */
public class ZWKTActivity extends Activity implements
		com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener, UploadExpInfoListener {
	private String StuffID;
	ApplicationHelper applicationHelper;
	String DeviceID;
	String custom_type;
	UserExploration userExploration;
	TextView CustomType;
	/**
	 * 商用勘探项目
	 */
	private CheckBox c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;
	private TextView txt_c1, txt_c2, txt_c3, txt_c4, txt_c5, txt_c6, txt_c7,
			txt_c8, txt_c9, txt_c10, txt_c11;
	/**
	 * 民用勘探项目
	 */
	private CheckBox r1, r2, r3, r4, r5, r6, r7;
	private TextView txt_r1, txt_r2, txt_r3, txt_r4, txt_r5, txt_r6, txt_r7;
	private Button picture, view, submit;
	String userid = "";
	GetBasicInfo getBasicInfo;//20181019
	private boolean dialogISClose=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		applicationHelper = (ApplicationHelper) getApplication();
		getBasicInfo=new GetBasicInfo(ZWKTActivity.this);
		StuffID=getBasicInfo.getOperationID();
		DeviceID = getBasicInfo.getDeviceID();
		custom_type = applicationHelper.getUserExploration().getCustom_type();
		userid = applicationHelper.getUserExploration().getUserid();
		setLayout(custom_type);
		// android 7.0系统解决拍照的问题
		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		builder.detectFileUriExposure();

	}

	private void setLayout(String type) {
		if (type.equals("CT03") || type.equals("CT04")) {
			// 商用
			setContentView(R.layout.activity_commecial);
			CustomType=(TextView) this.findViewById(R.id.txt_customtype);
			CustomType.setText("商业用户"+"（"+userid+"）");
			c1 = (CheckBox) this.findViewById(R.id.c1);
			c2 = (CheckBox) this.findViewById(R.id.c2);
			c3 = (CheckBox) this.findViewById(R.id.c3);
			c4 = (CheckBox) this.findViewById(R.id.c4);
			c5 = (CheckBox) this.findViewById(R.id.c5);
			c6 = (CheckBox) this.findViewById(R.id.c6);
			c7 = (CheckBox) this.findViewById(R.id.c7);
			c8 = (CheckBox) this.findViewById(R.id.c8);
			c9 = (CheckBox) this.findViewById(R.id.c9);
			c10 = (CheckBox) this.findViewById(R.id.c10);
			c11 = (CheckBox) this.findViewById(R.id.c11);
			txt_c1 = (TextView) this.findViewById(R.id.txt_c1);
			txt_c2 = (TextView) this.findViewById(R.id.txt_c2);
			txt_c3 = (TextView) this.findViewById(R.id.txt_c3);
			txt_c4 = (TextView) this.findViewById(R.id.txt_c4);
			txt_c5 = (TextView) this.findViewById(R.id.txt_c5);
			txt_c6 = (TextView) this.findViewById(R.id.txt_c6);
			txt_c7 = (TextView) this.findViewById(R.id.txt_c7);
			txt_c8 = (TextView) this.findViewById(R.id.txt_c8);
			txt_c9 = (TextView) this.findViewById(R.id.txt_c9);
			txt_c10 = (TextView) this.findViewById(R.id.txt_c10);
			txt_c11 = (TextView) this.findViewById(R.id.txt_c11);

			picture = (Button) this.findViewById(R.id.picture);
			view = (Button) this.findViewById(R.id.view);
			submit = (Button) this.findViewById(R.id.submit);

			txt_c1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c1_d));
				}
			});
			txt_c2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c2_d));
				}
			});
			txt_c3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c3_d));
				}
			});
			txt_c4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c4_d));
				}
			});
			txt_c5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c5_d));
				}
			});
			txt_c6.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c6_d));
				}
			});
			txt_c7.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c7_d));
				}
			});
			txt_c8.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c8_d));
				}
			});
			txt_c9.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c9_d));
				}
			});
			txt_c10.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c10_d));
				}
			});
			txt_c11.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.c11_d));
				}
			});

			picture.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					takePhoto(DeviceID, userid);
				}
			});
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					preview(DeviceID, userid);
				}
			});
			submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int photoesCount = pictureCounts(DeviceID, userid);
					if (photoesCount <= 0) {
						Toast.makeText(getApplicationContext(), "请拍照",
								Toast.LENGTH_SHORT).show();
						return;
					}
					saveData();
					task();
				}
			});

		} else {
			// 民用
			setContentView(R.layout.activity_kitchen_exploration);
			CustomType=(TextView) this.findViewById(R.id.txt_customtype);
			CustomType.setText("居民用户"+"（"+userid+"）");
			r1 = (CheckBox) this.findViewById(R.id.r1);
			r2 = (CheckBox) this.findViewById(R.id.r2);
			r3 = (CheckBox) this.findViewById(R.id.r3);
			r4 = (CheckBox) this.findViewById(R.id.r4);
			r5 = (CheckBox) this.findViewById(R.id.r5);
			r6 = (CheckBox) this.findViewById(R.id.r6);
			r7 = (CheckBox) this.findViewById(R.id.r7);
			txt_r1 = (TextView) this.findViewById(R.id.txt_r1);
			txt_r2 = (TextView) this.findViewById(R.id.txt_r2);
			txt_r3 = (TextView) this.findViewById(R.id.txt_r3);
			txt_r4 = (TextView) this.findViewById(R.id.txt_r4);
			txt_r5 = (TextView) this.findViewById(R.id.txt_r5);
			txt_r6 = (TextView) this.findViewById(R.id.txt_r6);
			txt_r7 = (TextView) this.findViewById(R.id.txt_r7);

			picture = (Button) this.findViewById(R.id.picture);
			view = (Button) this.findViewById(R.id.view);
			submit = (Button) this.findViewById(R.id.submit);

			txt_r1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.r1_d));
				}
			});
			txt_r2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.r2_d));
				}
			});
			txt_r3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.r3_d));
				}
			});
			txt_r4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.r4_d));
				}
			});
			txt_r5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.r5_d));
				}
			});
			txt_r6.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.r6_d));
				}
			});
			txt_r7.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shownDetailItem(getResources().getString(R.string.r7_d));
				}
			});

			picture.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					takePhoto(DeviceID, userid);
				}
			});
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					preview(DeviceID, userid);
				}
			});
			submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// 上传
					int photoesCount = pictureCounts(DeviceID, userid);
					if (photoesCount <= 0) {
						Toast.makeText(getApplicationContext(), "请拍照",
								Toast.LENGTH_SHORT).show();
						return;
					}
					saveData();
					task();
				}
			});
		}

	}

	private void task() {
		HsicMessage hsicMess = new HsicMessage();
		hsicMess.setRespMsg(JSONUtils.toJsonWithGson(userExploration));
		String responsedata = JSONUtils.toJsonWithGson(hsicMess);
		UploadExpInfoTask upload = new UploadExpInfoTask(this, this);
		upload.execute(DeviceID, responsedata);
	}
	/**
	 * 照片数量
	 *
	 * @return
	 */
	private int pictureCounts(String deviceId, String userid) {
		ArrayList<String> mList = new ArrayList<String>();
		String photoesPattern = "d"+DeviceID +"stuff"+getBasicInfo.getOperationID()+"u" + userid;;
		String filePath = PathUtil.getExpInfoImagePath();
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
	private void saveData() {
		userExploration=new UserExploration();
		boolean isQ=true;
		if (custom_type.equals("CT03") || custom_type.equals("CT04")) {
			// 商用
			if (c1.isChecked()) {
				isQ=false;
				userExploration.setExplorationType8("1");
			} else {
				userExploration.setExplorationType8("0");
			}
			if (c2.isChecked()) {
				isQ=false;
				userExploration.setExplorationType9("1");
			} else {
				userExploration.setExplorationType9("0");
			}
			if (c3.isChecked()) {
				isQ=false;
				userExploration.setExplorationType10("1");
			} else {
				userExploration.setExplorationType10("0");
			}
			if (c4.isChecked()) {
				isQ=false;
				userExploration.setExplorationType11("1");
			} else {
				userExploration.setExplorationType11("0");
			}
			if (c5.isChecked()) {
				isQ=false;
				userExploration.setExplorationType12("1");
			} else {
				userExploration.setExplorationType12("0");
			}
			if (c6.isChecked()) {
				isQ=false;
				userExploration.setExplorationType13("1");
			} else {
				userExploration.setExplorationType13("0");
			}
			if (c7.isChecked()) {
				isQ=false;
				userExploration.setExplorationType14("1");
			} else {
				userExploration.setExplorationType14("0");
			}
			if (c8.isChecked()) {
				isQ=false;
				userExploration.setExplorationType15("1");
			} else {
				userExploration.setExplorationType15("0");
			}
			if (c9.isChecked()) {
				isQ=false;
				userExploration.setExplorationType16("1");
			} else {
				userExploration.setExplorationType16("0");
			}
			if (c10.isChecked()) {
				isQ=false;
				userExploration.setExplorationType17("1");
			} else {
				userExploration.setExplorationType17("0");
			}
			if (c11.isChecked()) {
				isQ=false;
				userExploration.setExplorationType18("1");
			} else {
				userExploration.setExplorationType18("0");
			}
			userExploration.setExplorationType1("0");
			userExploration.setExplorationType2("0");
			userExploration.setExplorationType3("0");
			userExploration.setExplorationType4("0");
			userExploration.setExplorationType5("0");
			userExploration.setExplorationType6("0");
			userExploration.setExplorationType7("0");

		} else {
			if (r1.isChecked()) {
				isQ=false;
				userExploration.setExplorationType1("1");
			} else {
				userExploration.setExplorationType1("0");
			}
			if (r2.isChecked()) {
				isQ=false;
				userExploration.setExplorationType2("1");
			} else {
				userExploration.setExplorationType2("0");
			}
			if (r3.isChecked()) {
				isQ=false;
				userExploration.setExplorationType3("1");
			} else {
				userExploration.setExplorationType3("0");
			}
			if (r4.isChecked()) {
				isQ=false;
				userExploration.setExplorationType4("1");
			} else {
				userExploration.setExplorationType4("0");
			}
			if (r5.isChecked()) {
				isQ=false;
				userExploration.setExplorationType5("1");
			} else {
				userExploration.setExplorationType5("0");
			}
			if (r6.isChecked()) {
				isQ=false;
				userExploration.setExplorationType6("1");
			} else {
				userExploration.setExplorationType6("0");
			}
			if (r7.isChecked()) {
				isQ=false;
				userExploration.setExplorationType7("1");
			} else {
				userExploration.setExplorationType7("0");
			}
			userExploration.setExplorationType8("0");
			userExploration.setExplorationType9("0");
			userExploration.setExplorationType10("0");
			userExploration.setExplorationType11("0");
			userExploration.setExplorationType12("0");
			userExploration.setExplorationType13("0");
			userExploration.setExplorationType14("0");
			userExploration.setExplorationType15("0");
			userExploration.setExplorationType16("0");
			userExploration.setExplorationType17("0");
			userExploration.setExplorationType18("0");
		}
		userExploration.setUserid(userid);
		userExploration.setExplorationDate(GetDate.getToday());
		if(isQ){
			userExploration.setExplorationStatus("0");
		}else{
			userExploration.setExplorationStatus("1");
		}

		String relationID = "";
		relationID ="d"+DeviceID +"stuff"+getBasicInfo.getOperationID()+"u" + userid;
		userExploration.setExplorationMan(StuffID);
		userExploration.setAttachID(relationID);
	}

	public static String imagePath;

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

	/**
	 * 存储照片
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 1) {
				getImagePath(DeviceID, userid);
			}
		}
	}

	/**
	 * 将照片压缩存入本地
	 *
	 * @param deviceId
	 * @param user
	 */
	public void getImagePath(String deviceId, String user) {
		File file = new File(imagePath);
		if (file != null && file.exists()) {
			String path = PathUtil.getExpInfoImagePath();
			File file1 = new File(path);
			if (!file1.exists()) {
				file1.mkdirs();
			}
			Date date = new Date();
			String pattern = "yyyyMMddHHmmss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					Locale.getDefault());
			String format = sdf.format(date);
			File file2 = new File(file1.getPath(), "zwkt"+GetDate.GetDate()+"d"+deviceId +"stuff"+StuffID+"u" + userid +
					"_"
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

	/**
	 * 预览照片
	 *
	 * @param deviceId
	 * @param userid
	 */
	public void preview(String deviceId, String userid) {
		int flag = 0;
		String filePath = PathUtil.getExpInfoImagePath();
		File file = new File(filePath);
		String[] paths = file.list();
		if (paths != null && paths.length > 0) {
			for (int i = 0; i < paths.length; i++) {
				String path = paths[i];
				if (path.contains( "zwkt"+GetDate.GetDate()+"d"+deviceId +"stuff"+StuffID+"u" + userid)) {
					flag++;
				}
			}
			if (flag == 0) {
				Toast.makeText(getApplicationContext(), "请先拍照后在预览",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				Intent intent = new Intent(this, KTPicViewActivity.class);
				intent.putExtra("userid", userid);
				startActivity(intent);
			}
		} else {
			Toast.makeText(getApplicationContext(), "请先拍照后在预览",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void shownDetailItem(String message) {
		new AlertView("详细", message, null, new String[] { "确定" }, null, this,
				AlertView.Style.Alert, this).show();
	}

	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub
		if(dialogISClose){
			dialogISClose=false;
			this.finish();

		}
	}

	/**
	 * 灶位信息上传接口
	 */
	@Override
	public void UploadExpInfoListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub
		if(tag.getRespCode()==0){
			//向上个页面发送执行结果完成的信号
			Intent intent = new Intent();
			intent.putExtra("result", 3);
			setResult(1, intent);
			dialogISClose=true;
			new AlertView("提示", "灶位信息上传成功", null, new String[] { "确定" },
					null,this, AlertView.Style.Alert, this)
					.show();

		}else{
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null,this, AlertView.Style.Alert, this);
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
