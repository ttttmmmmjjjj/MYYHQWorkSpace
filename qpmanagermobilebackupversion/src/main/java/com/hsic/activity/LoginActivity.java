package com.hsic.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.bean.EmployeeInfo;
import com.hsic.bean.HsicMessage;
import com.hsic.bean.LoginInfo;
import com.hsic.bll.ActivityUtil;
import com.hsic.bll.DeviceIDInfo;
import com.hsic.bll.GetBasicInfo;
import com.hsic.bll.SaveBasicInfo;
import com.hsic.constant.Constant;
import com.hsic.listener.ImplLogin;
import com.hsic.picture.UpLoadPIC;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.task.LoginTask;
import com.hsic.version.VersionAsyncTask;

public class LoginActivity extends AppCompatActivity implements ImplLogin ,  com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
    SaveBasicInfo savaBasicInfo;//
    Build bd = new Build();
    private String version;
    private EditText edt_staffID,edt_password;
    private Button btn_loginIn;
    private SharedPreferences companyInfo;
    GetBasicInfo getBasicInfo;
    SharedPreferences deviceSetting;
    private TextView txt_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
        toolbar.setTitle("登录");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TurnSetting();
            }
        });
        savaBasicInfo = new SaveBasicInfo(LoginActivity.this);
        setDeviceID();
        edt_staffID=this.findViewById(R.id.edt_staffid);
        edt_password=this.findViewById(R.id.edt_password);
        btn_loginIn=this.findViewById(R.id.btn_loginIn);
        txt_version=this.findViewById(R.id.txt_version);
        String version=getLocalVersionName(LoginActivity.this);
        txt_version.setText("应用版本："+version);
        btn_loginIn.setText("登录");
        btn_loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String staffID=edt_staffID.getText().toString();
                if(!TextUtils.isEmpty(staffID)){
                    EmployeeInfo ei = new EmployeeInfo();
                    ei.setUserID(staffID);
                    String request = JSONUtils.toJsonWithGson(ei);
                    // 建立服务通信类
                    HsicMessage mHsicMessage = new HsicMessage();
                    // 给通信类设置值
                    mHsicMessage.setRespMsg(request);
                    // 把服务通信类转换成字符串
                    String requestData = JSONUtils.toJsonWithGson(mHsicMessage);
                    LoginTask task=new LoginTask(LoginActivity.this,LoginActivity.this);
                    task.execute(requestData);
                }

            }
        });
        companyInfo = getSharedPreferences("CompanyInfo", 0);// 公司选择器
        /**
         * 设置公司，默认为上液
         */
        String temp_company = companyInfo.getString("CompanyName", "");
        if(temp_company.equals("")){
            //强制设置
            new AlertView("请选择公司", null, null, null,
                    new String[]{"上液备用(测试)", "上液备用(正式)"},
                    this, AlertView.Style.ActionSheet, this).show();
        }else{
            savaBasicInfo.saveCompany(temp_company);
        }
        getBasicInfo=new GetBasicInfo(LoginActivity.this);
        deviceSetting = getSharedPreferences("DeviceSetting", 0);
        String bluetooth = deviceSetting.getString("BlueToothAdd", "");
        if (bluetooth.equals("")) {
            Intent i = new Intent(LoginActivity.this, AdvConfigActivity.class);
            this.startActivity(i);

        }
        checkVersion();
//        new TestPIC(LoginActivity.this).execute();
    }
    /**
     * 检测网络是否连接
     *
     * @return
     */
    private boolean checkNetworkState() {
        boolean flag = false;
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }
    /**
     * 检查版本更新
     */
    private void  checkVersion(){
        boolean net = false;
        net = checkNetworkState();
        if (net) {
            VersionAsyncTask vat = new VersionAsyncTask(LoginActivity.this, this);
            vat.execute();
        }
    }
    private void setDeviceID() {
        try {
            TelephonyManager tm = (TelephonyManager) this
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String deviceN0 = "";
            deviceN0 = tm.getDeviceId();
            int l = deviceN0.length();
            deviceN0 = deviceN0.substring(l - 8, l);
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
            version = packInfo.versionName;
            savaBasicInfo.saveDeviceID(deviceN0);// �����ֳֻ����
            savaBasicInfo.saveDeviceType(bd.MODEL);
            DeviceIDInfo.setDeviceID(deviceN0);
            Constant.DeviceType= bd.MODEL;
        } catch (SecurityException ex) {
            ex.toString();
            savaBasicInfo.saveDeviceID("00000000");// �����ֳֻ����
            DeviceIDInfo.setDeviceID("00000000");
        }catch(PackageManager.NameNotFoundException ex2){
            ex2.toString();
            version="0.0.0";
            savaBasicInfo.saveDeviceID("00000000");// �����ֳֻ����
            DeviceIDInfo.setDeviceID("00000000");
        }
    }
    private void TurnSetting() {
        ActivityUtil.JumpToAdvConfig(this);
    }

    @Override
    public void LoginTaskEnd(HsicMessage tag) {
        if(tag.getRespCode()==0){
            EmployeeInfo EmployeeInfo;// 登录人员信息
            EmployeeInfo = (EmployeeInfo) JSONUtils.toObjectWithGson(
                    tag.getRespMsg(), EmployeeInfo.class);
            int UserType= EmployeeInfo.getUserType();
            if(UserType==0){
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                i.putExtra("staffID",EmployeeInfo.getUserID());
                startActivity(i);
                LoginActivity.this.finish();
            }else{
                Intent i=new Intent(LoginActivity.this,DistrubutionOrderActivity.class);
                i.putExtra("staffID",EmployeeInfo.getUserID());
                startActivity(i);
                LoginActivity.this.finish();
            }

        }else{
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        switch(position){
            case 0:
                SharedPreferences.Editor mEditor1 = companyInfo.edit();
                mEditor1.putString("CompanyName", "SY_T");
                mEditor1.putString("Company","上海液化石油气经营有限公司");
                mEditor1.commit();
                break;
            case 1:
                SharedPreferences.Editor mEditor = companyInfo.edit();
                mEditor.putString("CompanyName", "SY_N");
                mEditor.putString("Company","上海液化石油气经营有限公司");
                mEditor.commit();
                break;
//            case 2:
//                SharedPreferences.Editor mEditor2 = companyInfo.edit();
//                mEditor2.putString("CompanyName", "CM");
//                mEditor2.putString("Company","上海燃气崇明有限公司");
//                mEditor2.commit();
//                break;
//            case 3:
//                SharedPreferences.Editor mEditor3 = companyInfo.edit();
//                mEditor3.putString("CompanyName", "HS");
//                mEditor3.putString("Company","上海液化石油气经营有限公司");
//                mEditor3.commit();
//                break;
        }
    }
    /**
     * 上传照片
     */
    class TestPIC extends AsyncTask<Void, Void, Void>{
        private ProgressDialog dialog;
        private Context context = null;
        public TestPIC(Context context) {
            this.context = context;
            dialog = new ProgressDialog(context);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("上传照片中");
            dialog.setCancelable(false);
            dialog.show();
        }
        protected Void doInBackground(Void... voids) {
            UpLoadPIC upPIC = new UpLoadPIC();
            upPIC.upPicture(LoginActivity.this, getBasicInfo.getDeviceID());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.setCancelable(true);
            dialog.dismiss();
        }
    }
    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d("TAG", "当前版本名称：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
