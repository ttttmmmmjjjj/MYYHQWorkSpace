package com.hsic.fxqpmanager;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsic.bean.EmployeeInfo;
import com.hsic.bean.HsicMessage;
import com.hsic.bll.ActivityUtil;
import com.hsic.bll.GPrinterCommand;
import com.hsic.bll.GetBasicInfo;
import com.hsic.bll.PrintPic;
import com.hsic.bll.SaveBasicInfo;
import com.hsic.constant.Constant;
import com.hsic.db.DeliveryDB;
import com.hsic.listener.ImplLogin;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.task.LoginTask;
import com.hsic.utils.MD5Util;
import com.hsic.utils.PathUtil;
import com.hsic.utils.PrintUtils;
import com.hsic.version.VersionAsyncTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class LoginActivity extends AppCompatActivity implements ImplLogin,
        com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
    private EditText edt_user, edt_pass;
    private Button btn_Login;
    private CheckBox remeberUserAndPass;//是否记住密码
    TextView Version;
    SaveBasicInfo saveBasicInfo;
    GetBasicInfo getBasicInfo;
    String version;
    Build bd = new Build();
    private String DB_Version;
    DeliveryDB insertData;
    SharedPreferences deviceSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        saveBasicInfo = new SaveBasicInfo(LoginActivity.this);
        getBasicInfo = new GetBasicInfo(LoginActivity.this);
        insertData = new DeliveryDB(LoginActivity.this);
        innitView();
        setDeviceID();
        DB_Version = getBasicInfo.getDBVersion();
        if (DB_Version.equals("")) {
            DB_Version = getResources().getString(R.string.data_version);//数据库版本
            saveBasicInfo.saveDBVersion(DB_Version);
        }
        deviceSetting = getSharedPreferences("DeviceSetting", 0);
        String bluetooth = deviceSetting.getString("BlueToothAdd", "");
        if (bluetooth.equals("")) {
            Intent i = new Intent(LoginActivity.this, AdvConfigActivity.class);
            this.startActivity(i);

        }
        checkVersion();
    }

    private void innitView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TurnSetting();
                //弹出基本设置
            }
        });
        edt_user = this.findViewById(R.id.edt_stuffid);
        edt_user.setText("");
        edt_pass = this.findViewById(R.id.edt_password);
        edt_pass.setText("");
        Version = this.findViewById(R.id.Version);
        String version = getLocalVersionName(LoginActivity.this);
        Version.setText("应用版本：" + version);
        remeberUserAndPass = this.findViewById(R.id.userAndPass);
        if (getBasicInfo.getIsChecked()) {
            remeberUserAndPass.setChecked(true);
        } else {
            remeberUserAndPass.setChecked(false);
        }
        if (remeberUserAndPass.isChecked()) {
            //初始化登录账户及密码
            edt_user.setText(getBasicInfo.getAccount());
            edt_pass.setText(getBasicInfo.getPass());
        }
        btn_Login = this.findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_Login.setEnabled(false);
                String account = edt_user.getText().toString();
                String pass = edt_pass.getText().toString();
                if (TextUtils.isEmpty(account)) {
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    return;
                }
                try {
                    pass = MD5Util.getFileMD5String(pass);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (checkNetworkState()) {
                    new LoginTask(LoginActivity.this, LoginActivity.this).execute(account, pass);
                } else {
                    //没有网络状态
                    String userID = (edt_user.getText().toString()).toUpperCase();
                    if (userID.equals(getBasicInfo.getOperationID())) {
                        Intent login = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(login);
                        LoginActivity.this.finish();
                    } else {
                        new AlertView("提示", "由于该用户无最近登陆记录，无网络状态登陆不支持", null, new String[]{"确定"},
                                null, LoginActivity.this, AlertView.Style.Alert, LoginActivity.this)
                                .show();
                    }

                }
            }
        });
    }

    private void TurnSetting() {
        ActivityUtil.JumpToAdvConfig(this);
    }

    /**
     * 获取设备基本信息
     */
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
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            version = packInfo.versionName;
            saveBasicInfo.saveDeviceID(deviceN0);//
            saveBasicInfo.saveDeviceType(bd.MODEL);
            Constant.DeviceType = bd.MODEL;
            Constant.DeviceID = deviceN0;
        } catch (SecurityException ex) {
            ex.toString();
            saveBasicInfo.saveDeviceID("00000000");//
            Constant.DeviceID = "00000000";
        } catch (PackageManager.NameNotFoundException ex2) {
            ex2.toString();
            version = "0.0.0";
            saveBasicInfo.saveDeviceID("00000000");//
            Constant.DeviceID = "00000000";
        }
    }

    @Override
    public void LoginTaskEnd(HsicMessage tag) {
        if (tag.getRespCode() == 0) {
            EmployeeInfo employeeInfo = (EmployeeInfo) JSONUtils.toObjectWithGson(
                    tag.getRespMsg(), EmployeeInfo.class);
            saveBasicInfo.saveOperationID(employeeInfo.getUserID().toUpperCase());
            saveBasicInfo.saveOperationName(employeeInfo.getUserName());
            saveBasicInfo.saveStationName(employeeInfo.getStationName());
            saveBasicInfo.saveStationID(employeeInfo.getStation());
            if (remeberUserAndPass.isChecked()) {
                saveBasicInfo.saveIsChecked(true);
                saveBasicInfo.saveAccount(edt_user.getText().toString());
                saveBasicInfo.savePass(edt_pass.getText().toString());

            }
            /**
             * 根据数据库版本决定是否需要更新本地数据库
             */
            if (insertData.InsertConfigInfo()) {
                saveBasicInfo.saveDBVersion("");
            }
            Intent login = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(login);
            this.finish();

        } else {
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }
        btn_Login.setEnabled(true);
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {

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
    private void checkVersion() {
        boolean net = false;
        net = checkNetworkState();
        if (net) {
            VersionAsyncTask vat = new VersionAsyncTask(LoginActivity.this, this);
            vat.execute();
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
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * ceshi
     *
     * @param
     * @return
     */
    public String isWrite() {
        boolean ret = false;
        int flag = 0;
        String path = "";
        String filePath = PathUtil.getImagePath();
        File file = new File(filePath);
        String[] paths = file.list();
        if (paths != null && paths.length > 0) {
            for (int i = 0; i < paths.length; i++) {
                Log.e("paths[i]", JSONUtils.toJsonWithGson(filePath+paths[i]));
                String tmp = paths[i];
                if (tmp.contains("sign")) {
                    path = tmp;
                }
            }
        }
        return path;
    }

    public static byte[] getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }    // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data));
        return data;
    }



}
