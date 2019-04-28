package com.hsic.sy.supply.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.adapter.GridViewAdapter;
import com.hsic.sy.adapter.RegionCodeAdapter;
import com.hsic.sy.adapter.ViewPagerAdapter;
import com.hsic.sy.bean.Employee;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.TmpScan;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.supply.listener.GetAllEmpNewListListener;
import com.hsic.sy.supply.task.GetAllEmpNewListTask;
import com.hsic.sy.supply.task.GetTruckNoUseregcodeTask;
import com.hsic.sy.supply.task.GetTrucknoListTask;
import com.hsic.sy.supply.task.UpdateTruckNoTask;
import com.hsic.sy.supplystationmanager.R;
import com.google.gson.reflect.TypeToken;
import com.hsic.sy.adapter.Model;
import com.hsic.sy.bean.RegionCodeBean;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.supply.listener.GetTruckNoUseregcodeListener;
import com.hsic.sy.supply.listener.GetTrucknoListListener;
import com.hsic.sy.supply.listener.UpdateTruckNoListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddFullQPActivity extends Activity implements GetTruckNoUseregcodeListener,
        com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener, GetTrucknoListListener, UpdateTruckNoListener, GetAllEmpNewListListener {
    private String[][] titles;
    private ViewPager mPager;
    private List<View> mPagerList;
    private List<Model> ScanSutff;
    private LinearLayout mLlDot;
    private LayoutInflater inflater;
    private List<RegionCodeBean> RegionCode_List;
    private List<RegionCodeBean> RegionCode_Data;
    private ListView RegionCode_LIST;
    private Button AddQP;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 4;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;
    private String StationID;
    List<TruckInfo> mDatas;
    String useRegCode,SupPointCode,SupPointCodeName;//加瓶站点
    List<RegionCodeBean> UploadList;
    GetBasicInfo getBasicInfo;//20181019
    private String TruckNoID,TruckID;//加瓶车次信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_addfullqp);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mLlDot = (LinearLayout) findViewById(R.id.ll_dot);
        RegionCode_LIST = (ListView) this.findViewById(R.id.listView1);
        AddQP = (Button) this.findViewById(R.id.btn_addqp);
        getBasicInfo = new GetBasicInfo(AddFullQPActivity.this);
        StationID = getBasicInfo.getStationID();
        SupPointCode="";SupPointCodeName="";
        TruckNoID="";
        TruckID="";
        //初始化数据源
        getScanStuff();

        RegionCode_LIST.setOnItemClickListener(mOnItemClickListener);
        AddQP.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (RegionCode_Data != null && RegionCode_Data.size() > 0) {
                    if (isEixst()) {
                        //开始整理上传数据
                       SortUploadData(RegionCode_Data);
                        //直接显示车次对话框
                        TruckInfo truckInfo = new TruckInfo();
                        truckInfo.setSaleMan(getBasicInfo.getOperationID());
                        truckInfo.setTruckNoID(TruckNoID);
                        truckInfo.setUseRegCode(useRegCode);
                        truckInfo.setTruckID(TruckID);
                        HsicMessage hsicMess = new HsicMessage();
                        hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truckInfo));
                        String responsedata = JSONUtils.toJsonWithGson(hsicMess);
                        task(responsedata);
                    } else {
                        shownDialog("请选择气瓶!");
                    }
                } else {
                    shownDialog("无可供加瓶的气瓶!");
                }
            }
        });

    }

    private boolean isEixst() {
        boolean ret = false;
        int size = RegionCode_Data.size();
        for (int i = 0; i < size; i++) {
            if (RegionCode_Data.get(i).isChecked) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    private Context getContext() {
        return this;
    }

    private void shownDialog(String text) {
        new AlertView("提示", text, null, new String[]{"确定"},
                null, this, AlertView.Style.Alert, this)
                .show();
    }

    private List<RegionCodeBean> SortUploadData(List<RegionCodeBean> RegionCode_Data) {
        UploadList = new ArrayList<RegionCodeBean>();
        useRegCode = "";
        for (int i = 0; i < RegionCode_Data.size(); i++) {
            if (RegionCode_Data.get(i).isChecked) {
                useRegCode += RegionCode_Data.get(i).getMsg() + "|B16" + ",";
                UploadList.add(RegionCode_Data.get(i));
            }
        }
        useRegCode = useRegCode.substring(0, useRegCode.length() - 1);
        return UploadList;
    }

    private final AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
            if (RegionCode_Data.get(position).isChecked) {
                RegionCodeBean regionCode = new RegionCodeBean();
                regionCode.setChecked(false);
                regionCode.setMsg(RegionCode_Data.get(position).getMsg());
                regionCode.setEmpid(RegionCode_Data.get(position).getEmpid());
                regionCode.setEmpname(RegionCode_Data.get(position).getEmpname());
                RegionCode_Data.remove(position);
                RegionCode_Data.add(position, regionCode);
                RegionCode_LIST.setAdapter(new RegionCodeAdapter(getBaseContext(), RegionCode_Data));
            } else {
                RegionCodeBean regionCode = new RegionCodeBean();
                regionCode.setChecked(true);
                regionCode.setMsg(RegionCode_Data.get(position).getMsg());
                regionCode.setEmpid(RegionCode_Data.get(position).getEmpid());
                regionCode.setEmpname(RegionCode_Data.get(position).getEmpname());
                RegionCode_Data.remove(position);
                RegionCode_Data.add(position, regionCode);
                RegionCode_LIST.setAdapter(new RegionCodeAdapter(getBaseContext(), RegionCode_Data));
            }


        }
    };

    /**
     * 根据选中的员工设置List列表
     */
    private void setScanStuff() {
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(ScanSutff.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview_copy_mt, mPager, false);
            gridView.setAdapter(new GridViewAdapter(this, ScanSutff, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    //全部 整个List列表的数据都选择
                    try {
                        RegionCode_Data = new ArrayList<RegionCodeBean>();
                        for (int i = 0; i < RegionCode_List.size(); i++) {
                            RegionCode_Data.add(RegionCode_List.get(i));
                        }

                        if (RegionCode_Data.size() > 0) {
                            if (ScanSutff.get(pos).getId().equals("000000")) {
                                List<RegionCodeBean> RegionCode_LIST_Temp;
                                RegionCode_LIST_Temp = new ArrayList<RegionCodeBean>();

                                int size = RegionCode_Data.size();
                                for (int a = 0; a < size; a++) {
                                    RegionCodeBean regionCode = new RegionCodeBean();
                                    regionCode.setChecked(true);
                                    regionCode.setMsg(RegionCode_Data.get(a).getMsg());
                                    regionCode.setEmpid(RegionCode_Data.get(a).getEmpid());
                                    regionCode.setEmpname(RegionCode_Data.get(a).getEmpname());
                                    RegionCode_LIST_Temp.add(regionCode);
                                }
                                RegionCode_Data = new ArrayList<RegionCodeBean>();
                                int tempsize = RegionCode_LIST_Temp.size();
                                for (int i = 0; i < tempsize; i++) {
                                    RegionCode_Data.add(i, RegionCode_LIST_Temp.get(i));
                                }
                                RegionCode_LIST.setAdapter(new RegionCodeAdapter(getBaseContext(), RegionCode_Data));
                            } else {
                                List<RegionCodeBean> RegionCode_LIST_Temp;
                                RegionCode_LIST_Temp = new ArrayList<RegionCodeBean>();
                                int size = RegionCode_Data.size();
                                for (int a = 0; a < size; a++) {
                                    if (ScanSutff.get(pos).getId().equals(RegionCode_Data.get(a).getEmpid())) {
                                        RegionCodeBean regionCode = new RegionCodeBean();
                                        regionCode.setChecked(true);
                                        regionCode.setMsg(RegionCode_Data.get(a).getMsg());
                                        regionCode.setEmpid(RegionCode_Data.get(a).getEmpid());
                                        regionCode.setEmpname(RegionCode_Data.get(a).getEmpname());
                                        RegionCode_LIST_Temp.add(regionCode);

                                    }

                                }
                                //重新设置Adapter
                                RegionCode_Data = new ArrayList<RegionCodeBean>();
                                int tempsize = RegionCode_LIST_Temp.size();
                                if (tempsize > 0) {
                                    for (int i = 0; i < tempsize; i++) {
                                        RegionCode_Data.add(RegionCode_LIST_Temp.get(i));
                                    }
                                    RegionCode_LIST.setAdapter(new RegionCodeAdapter(getBaseContext(), RegionCode_Data));
                                } else {
                                    RegionCode_LIST.setAdapter(new RegionCodeAdapter(getBaseContext(), RegionCode_Data));
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.toString();
                    }

                }
            });
        }
        //设置适配器
        mPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();

    }

    /**
     * 获取可加瓶的车辆
     */
    private void GetTrucknoList() {
        TruckInfo truck = new TruckInfo();
        truck.setStationid(StationID);
        HsicMessage hsicMess = new HsicMessage();
        hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truck));
        String responsedata = JSONUtils.toJsonWithGson(hsicMess);
        GetTrucknoListTask task = new GetTrucknoListTask(this, this);
        task.execute("", responsedata);
    }

    /**
     * 获取气瓶
     */
    private void getRegionCode(String SupPointCode ) {
        TmpScan tmpScan = new TmpScan();
        tmpScan.setSupPointCode(SupPointCode);
        HsicMessage hsicMess = new HsicMessage();
        hsicMess.setRespMsg(JSONUtils.toJsonWithGson(tmpScan));
        String responsedata = JSONUtils.toJsonWithGson(hsicMess);
        GetTruckNoUseregcodeTask task = new GetTruckNoUseregcodeTask(this, this);
        task.execute("", responsedata);
    }

    /**
     * 加瓶员工信息
     */
    private void getScanStuff() {
        Employee employee = new Employee();
        HsicMessage hsicMess = new HsicMessage();
        employee.setStationid(StationID);
        hsicMess.setRespMsg(JSONUtils.toJsonWithGson(employee));
        String responsedata = JSONUtils.toJsonWithGson(hsicMess);
        GetAllEmpNewListTask task = new GetAllEmpNewListTask(this, this);
        task.execute("", responsedata);
    }

    @Override
    public void GetTruckNoUseregcodeListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub
        RegionCode_List = new ArrayList<RegionCodeBean>();
        RegionCode_Data = new ArrayList<RegionCodeBean>();
        if (tag.getRespCode() == 0) {

            Type typeOfT = new TypeToken<List<TmpScan>>() {
            }.getType();
            List<TmpScan> TmpScan_LIST = new ArrayList<TmpScan>();
            TmpScan_LIST = JSONUtils
                    .toListWithGson(tag.getRespMsg(), typeOfT);
            int size = TmpScan_LIST.size();
//            if(size<=0){
//                new AlertView("提示", "该车次所在的加瓶站点+"+"("+SupPointCode+")"+"下无可添加的满瓶", null, new String[]{"确定"},
//                        null, this, AlertView.Style.Alert, this)
//                        .show();
//                return;
//            }
            for (int i = 0; i < size; i++) {
                RegionCodeBean regionCode = new RegionCodeBean();
                regionCode.setMsg(TmpScan_LIST.get(i).getUseRegCode());
                regionCode.setChecked(false);
                regionCode.setEmpid(TmpScan_LIST.get(i).getEmpid());
                regionCode.setEmpname(TmpScan_LIST.get(i).getEmpname());
                RegionCode_List.add(regionCode);
            }
            for (int i = 0; i < RegionCode_List.size(); i++) {
                RegionCode_Data.add(RegionCode_List.get(i));
            }
        }else{
            new AlertView("提示",tag.getRespMsg() , null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }
        RegionCode_LIST.setAdapter(new RegionCodeAdapter(this, RegionCode_Data));
    }

    /**
     * 初始化数据源
     */
    private void initDatas() {
        ScanSutff = new ArrayList<Model>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            ScanSutff.add(new Model(titles[i][0], imageId, titles[i][1]));
        }
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void GetAllEmpNewListListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub
        if (tag.getRespCode() == 0) {
            Type typeOfT = new TypeToken<List<Employee>>() {
            }.getType();
            List<Employee> Employee_LIST = new ArrayList<Employee>();
            Employee_LIST = JSONUtils
                    .toListWithGson(tag.getRespMsg(), typeOfT);

            int size = Employee_LIST.size();
            titles = new String[size + 1][size + 1];
            titles[0][0] = "全部";
            titles[0][1] = "000000";
            for (int i = 1; i < size; i++) {
                titles[i][0] = Employee_LIST.get(i).getEmpname();
            }
            for (int i = 1; i < size; i++) {
                titles[i][1] = Employee_LIST.get(i).getEmpid();
            }
            initDatas();
            inflater = LayoutInflater.from(this);
            setScanStuff();//设置扫描人员数据
            GetTrucknoList();//获取车次
        } else {
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
            finish();
        }
    }
    @Override
    public void onDismiss(Object o) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(Object o, int position) {
        // TODO Auto-generated method stub
        finish();
    }

    @Override
    public void GetTrucknoListListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub
        if (tag.getRespCode() == 0) {
            Type typeOfT = new TypeToken<List<TruckInfo>>() {
            }.getType();
            List<TruckInfo> Truck_LIST = new ArrayList<TruckInfo>();
            Truck_LIST = JSONUtils.toListWithGson(
                    tag.getRespMsg(), typeOfT);

            int size = 0;
            size = Truck_LIST.size();
            mDatas = new ArrayList<TruckInfo>();
            if (size > 0) {
                for (int a = 0; a < size; a++) {
                    if (Truck_LIST.get(a).getState().equals("1") || Truck_LIST.get(a).getState().equals("0")) {
                        TruckInfo temp = Truck_LIST.get(a);
                        mDatas.add(temp);
                    }
                }
            }
            ShowDialog();
        } else {
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();

        }
    }

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public void ShowDialog() {
        Context context = AddFullQPActivity.this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.formcommonlist, null);
        ListView myListView = (ListView) layout.findViewById(R.id.formcustomspinner_list);
        TextView Lable = (TextView) layout.findViewById(R.id.label);
        Lable.setText("请选择加瓶车次");
        MyAdapter adapter = new MyAdapter(context, mDatas);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int positon, long id) {
                getRegionCode(mDatas.get(positon).getGetQPStationid());//获取气瓶
                TruckNoID=mDatas.get(positon).getTruckNoID();
                TruckID=mDatas.get(positon).getTruckID();
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

    private void task(String responsedata) {
        UpdateTruckNoTask up = new UpdateTruckNoTask(this, this);
        up.execute("", responsedata);
    }

    @Override
    public void UpdateTruckNoListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub
        if (tag.getRespCode() == 0) {
            //清理list表
            for (int i = 0; i < UploadList.size(); i++) {
                RegionCode_Data.remove(UploadList.get(i));
            }
            RegionCode_LIST.setAdapter(new RegionCodeAdapter(this, RegionCode_Data));
            new AlertView("提示", "添加满瓶成功", null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();

        } else {
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }

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
