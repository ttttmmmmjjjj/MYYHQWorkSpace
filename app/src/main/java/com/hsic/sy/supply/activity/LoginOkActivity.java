package com.hsic.sy.supply.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.adapter.MenuGridAdapter;
import com.hsic.sy.adapter.UserExplorationAdapter;
import com.hsic.sy.bean.Employee;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.UserExploration;
import com.hsic.sy.bll.ActivityUtil;
import com.hsic.sy.bll.DeviceIDInfo;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.bll.SaveBasicInfo;
import com.hsic.sy.constant.Constant;
import com.hsic.sy.db.UserRegionCodeDB;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.doorsale.Activity.MainActivity;
import com.hsic.sy.search.bean.ApplicationHelper;
import com.hsic.sy.supply.listener.GetEmpInfoListener;
import com.hsic.sy.supply.listener.GetEmpInfo_newListener;
import com.hsic.sy.supply.listener.GetExpListListener;
import com.hsic.sy.supply.task.GetEmpInfoTask;
import com.hsic.sy.supply.task.GetEmpInfo_newTask;
import com.hsic.sy.supply.task.GetExpListTask;
import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.view.MyGridView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/***
 * 20181022 GetEmpInfo_new ������ʽΪ:List<Employee>
 * 20181019
 * �Ż������豸������Ϣ
 */
public class LoginOkActivity extends Activity implements GetEmpInfo_newListener,
        GetEmpInfoListener, GetExpListListener, View.OnClickListener,
        com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
    private MyGridView gridview;
    private Context mContext;
    private TextView station;
    private TextView info;
    private LinearLayout Setting;
    private String StuffID;
    private String StuffCardTagID;
    ApplicationHelper applicationHelper;
    boolean close = false;
    List<UserExploration> mDatas;
    UserRegionCodeDB userRegionCodeDB;
    String DeviceID;
    GetBasicInfo getBasicInfo;//20181019
    SaveBasicInfo saveBasicInfo;
    String loginMode;
    private Button btn_gpManager, btn_doorSale, btn_truckManager, btn_saleManager,
            btn_zwkt, btn_userRectify, btn_Count, btn_infoSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginok);
        applicationHelper = (ApplicationHelper) getApplication();
        getBasicInfo = new GetBasicInfo(LoginOkActivity.this);
        saveBasicInfo = new SaveBasicInfo(LoginOkActivity.this);
        StuffID = getBasicInfo.getOperationID();
        StuffCardTagID = getBasicInfo.getStuffTagID();
        DeviceID = getBasicInfo.getDeviceID();
        loginMode = getBasicInfo.getLoginStuffMode();
        initView();
        userRegionCodeDB = new UserRegionCodeDB(getBaseContext());
        userRegionCodeDB.DeteHistoryData(GetDate.getToday());
        userRegionCodeDB.DeleteHistorySerialNum(GetDate.getToday());
        if (!loginMode.equals(Constant.LoginByStuff)) {
            String loginMode = getBasicInfo.getLoginMode();
            if (loginMode.equals("1")) {
                GetEmpInfo_newTask task = new GetEmpInfo_newTask(this, this);
                task.execute(DeviceID, StuffCardTagID, StuffID);
            } else {
                String stationid = getBasicInfo.getStationID();
                Employee e = new Employee();
                e.setStationid(stationid);
                HsicMessage hsicMess = new HsicMessage();
                hsicMess.setRespMsg(JSONUtils.toJsonWithGson(e));
                String responsedata = JSONUtils.toJsonWithGson(hsicMess);
                GetEmpInfoTask task = new GetEmpInfoTask(this, this);
                task.execute(DeviceID, responsedata);
            }
        }


    }

    private void initView() {
        gridview = (MyGridView) findViewById(R.id.gridview);
        String[] img_text = { "钢瓶管理", "门售", "车次管理", "订单管理", "灶位勘探","用户整改","查询统计","设置"};
        int[] imgs = { R.drawable.gas, R.drawable.xd2,
                R.drawable.cc, R.drawable.i,
                R.drawable.c, R.drawable.h,
                R.drawable.count, R.drawable.setting};
        if (loginMode.equals(Constant.LoginByStuff)) {
            int[] imgs2  = { R.drawable.gas2, R.drawable.xd2,
                    R.drawable.cc2, R.drawable.i2,
                    R.drawable.c2, R.drawable.h,
                    R.drawable.count2, R.drawable.setting};
            gridview.setAdapter(new MenuGridAdapter(this,img_text,imgs2));
        }else{
            gridview.setAdapter(new MenuGridAdapter(this,img_text,imgs));
        }




        info = (TextView) this.findViewById(R.id.info);
        station = (TextView) this.findViewById(R.id.station);
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch ((int) id) {
                    case 0://���ι���

                        if (loginMode.equals(Constant.LoginByStuff)) {
                            shownDialog("无权限使用!");
                        } else {
                            Intent gp = new Intent(LoginOkActivity.this, GPManagerActivity.class);
                            startActivity(gp);
                        }
                        break;
                    case 1:
//                        if (loginMode.equals(Constant.LoginByStuff)) {
//                            shownDialog("无权限使用!");
//                        } else {
//                            Intent ms = new Intent(LoginOkActivity.this, MainActivity.class);
//                            startActivity(ms);
//                        }
                        shownDialog("无权限使用!");
                        break;
                    case 2:
                        if (loginMode.equals(Constant.LoginByStuff)) {
                            shownDialog("无权限使用!");
                        } else {
                            Intent cc = new Intent(LoginOkActivity.this, TruckManager.class);
                            startActivity(cc);
                        }
                        break;
                    case 3:
                        if (loginMode.equals(Constant.LoginByStuff)) {
                            shownDialog("无权限使用!");
                        } else {
                            Intent yh = new Intent(LoginOkActivity.this, UserManagerActivity.class);
                            startActivity(yh);
                        }
                        break;
                    case 4:
                        if (loginMode.equals(Constant.LoginByStuff)) {
                            shownDialog("无权限使用!");
                        } else {
                            GetExpListTask();
                        }

                        break;
                    case 5:
//                        if (loginMode.equals(Constant.LoginByStuff)) {
//                            shownDialog("无权限使用!");
//                        } else {
//                            Intent t = new Intent(LoginOkActivity.this, SearchLoginOk.class);
//                            startActivity(t);
//                        }
                        Intent zg = new Intent(LoginOkActivity.this, SearchLoginOk.class);
                        startActivity(zg);
                        break;
                    case 6:

                        if (loginMode.equals(Constant.LoginByStuff)) {
                            shownDialog("无权限使用!");
                        } else {
                            Intent t = new Intent(LoginOkActivity.this, QueryMainActivity.class);
                            startActivity(t);
                        }

                        break;
                    case 7:
                        //设置
                        ActivityUtil.JumpToAdvConfig(LoginOkActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 3) {
                GetExpListTask();
            }
        }

    }

    private void GetExpListTask() {
        GetExpListTask task = new GetExpListTask(this, this);
        task.execute(DeviceIDInfo.getDeviceID(), StuffID);
    }

    private void shownDialog(String text) {
        new AlertView("提示", text, null, new String[]{"确定"},
                null, this, AlertView.Style.Alert, this)
                .show();
    }

    @Override
    public void onDismiss(Object o) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(Object o, int position) {
        // TODO Auto-generated method stub
        if (close) {
            close = false;
            this.finish();
        }

    }

    @Override
    public void GetEmpInfo_newListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub
        if (tag.getRespCode() == 0) {
            Type typeOfT = new TypeToken<List<Employee>>() {
            }.getType();
            List<Employee> Employee_List = new ArrayList<Employee>();

            Employee_List = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
            Employee employee = new Employee();
            employee = Employee_List.get(0);
            station.setText(employee.getStationName());
            info.setText(employee.getEmpname());
//			applicationHelper.setEmployee(employee);
//			/**
//			 * 测试
//			 */
//			saveBasicInfo.saveStationID("1104");
            saveBasicInfo.saveStationID(employee.getStationid());
            saveBasicInfo.saveStationName(employee.getStationName());
            saveBasicInfo.saveOperationName(employee.getEmpname());
        } else {
            close = true;
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }
    }

    @Override
    public void GetExpListListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub
        if (tag.getRespCode() == 0) {
            Type typeOfT = new TypeToken<List<UserExploration>>() {
            }.getType();
            List<UserExploration> Truck_LIST = new ArrayList<UserExploration>();
            Truck_LIST = JSONUtils.toListWithGson(
                    tag.getRespMsg(), typeOfT);

            int size = 0;
            size = Truck_LIST.size();
            mDatas = new ArrayList<UserExploration>();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    mDatas.add(Truck_LIST.get(i));
                }
                ShowDialog();
            }

        } else {
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }
    }

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public void ShowDialog() {
        Context context = LoginOkActivity.this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.formcommonlist, null);
        ListView myListView = (ListView) layout.findViewById(R.id.formcustomspinner_list);
        TextView Lable = (TextView) layout.findViewById(R.id.label);
        Lable.setText("请选择用户");
        UserExplorationAdapter adapter = new UserExplorationAdapter(context, mDatas);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int positon, long id) {
                applicationHelper.setUserExploration(mDatas.get(positon));
                Intent m = new Intent(LoginOkActivity.this, ZWKTActivity.class);
                startActivityForResult(m, 1);
                alertDialog.dismiss();
            }
        });
        builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void GetEmpInfoListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        if (tag.getRespCode() == 0) {
            Type typeOfT = new TypeToken<List<Employee>>() {
            }.getType();
            List<Employee> Employee_List = new ArrayList<Employee>();

            Employee_List = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
            Employee employee = new Employee();
            employee = Employee_List.get(0);
            station.setText(employee.getStationName());
            info.setText(employee.getEmpname());
            saveBasicInfo.saveStationID(employee.getStationid());
            saveBasicInfo.saveStationName(employee.getStationName());
            saveBasicInfo.saveOperationName(employee.getEmpname());
        } else {
            close = true;
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }
    }

    // 是否退出程序
    private static Boolean isExit = false;
    // 定时触发器
    private static Timer tExit = null;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                if (tExit != null) {
                    tExit.cancel(); // 将原任务从队列中移除
                }
                // 重新实例一个定时器
                tExit = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                };
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 延时两秒触发task任务
                tExit.schedule(task, 2000);
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gpManager:
                Intent gpManager = new Intent(LoginOkActivity.this, GPManagerActivity.class);
                startActivity(gpManager);
                break;
            case R.id.btn_doorSale:
                Intent doorSale = new Intent(LoginOkActivity.this, MainActivity.class);
                startActivity(doorSale);
                break;
            case R.id.btn_truckManager:
                Intent truckManager = new Intent(LoginOkActivity.this, TruckManager.class);
                startActivity(truckManager);
                break;
            case R.id.btn_saleManager:
                Intent saleManager = new Intent(LoginOkActivity.this, UserManagerActivity.class);
                startActivity(saleManager);
                break;
            case R.id.btn_zwkt:
                GetExpListTask();
                break;
            case R.id.btn_userRectify:
                Intent userRectify = new Intent(LoginOkActivity.this, SearchLoginOk.class);
                startActivity(userRectify);
                break;
            case R.id.btn_Count:
                Intent Count = new Intent(LoginOkActivity.this, QueryMainActivity.class);
                startActivity(Count);
                break;
            case R.id.btn_infoSetting:
                break;

        }
    }
}
