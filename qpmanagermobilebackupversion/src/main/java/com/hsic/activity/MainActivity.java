package com.hsic.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.adapter.SaleListAdapter;
import com.hsic.bean.ApplicationHelper;
import com.hsic.bean.CustomerInfo;
import com.hsic.bean.HsicMessage;
import com.hsic.bean.Sale;
import com.hsic.bean.SaleAll;
import com.hsic.bean.SaleDetail;
import com.hsic.bll.GetBasicInfo;
import com.hsic.listener.ImplSearchAssignSale;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.task.SearchAssignSaleTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImplSearchAssignSale,
        com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
    private TextView mTvPeiSongCount, mTvVisitDate, mTvChePai, mTvZhanDian,
            mTvSheBei, mTvTaskCount, mTvWanCheng, mTvQiPingCount;
    private Button mBtnShangMenPeiSong;
    ListView listview;
    Sale sale;
    SaleAll saleAll;
    SaleDetail saleDetail;
    CustomerInfo customerInfo;
    ApplicationHelper applicationHelper;
    String StaffID;
    GetBasicInfo getBasicInfo;
    SaleListAdapter saleListAdapter;
    List<SaleAll> SaleAll_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationHelper = (ApplicationHelper) getApplication();
        getBasicInfo = new GetBasicInfo(MainActivity.this);
        sale = new Sale();
        saleAll = new SaleAll();
        saleDetail = new SaleDetail();
        customerInfo = new CustomerInfo();
        StaffID = "";
        innitview();
        mTvSheBei.setText("设备号：" + getBasicInfo.getDeviceID());//设备编号
        Intent i = getIntent();
        StaffID = i.getStringExtra("staffID");
        mTvChePai.setText("车牌号：" + StaffID);//车牌
        applicationHelper.setStaffID(StaffID);
        /**
         * 获取订单信息
         */
        Sale sale = new Sale();
        sale.setEmployeeID(StaffID);
        String request = JSONUtils.toJsonWithGson(sale);
        HsicMessage hsic = new HsicMessage();
        hsic.setRespMsg(request);
        String requestData = JSONUtils.toJsonWithGson(hsic);
        SearchAssignSaleTask task = new SearchAssignSaleTask(MainActivity.this, MainActivity.this);
        task.execute(requestData);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void innitview() {
        listview = (ListView) findViewById(R.id.listview);
        mTvPeiSongCount = (TextView) findViewById(R.id.peingsongcount);
        mTvPeiSongCount.setText("");
        mTvVisitDate = (TextView) findViewById(R.id.visitdate);
        mTvVisitDate.setText("");
        mTvVisitDate.setVisibility(View.GONE);
        mTvChePai = (TextView) findViewById(R.id.chepai);
        mTvChePai.setText("");
        mTvZhanDian = (TextView) findViewById(R.id.zhandianID);
        mTvZhanDian.setText("");
        mTvZhanDian.setVisibility(View.GONE);
        mTvSheBei = (TextView) findViewById(R.id.bianhao);
        mTvSheBei.setText("");
        mTvTaskCount = (TextView) findViewById(R.id.taskcount);
        mTvTaskCount.setText("");
        mTvWanCheng = (TextView) findViewById(R.id.wancheng);
        mTvWanCheng.setText("");
        mTvQiPingCount = (TextView) findViewById(R.id.qipingcount);
        mTvQiPingCount.setText("");
        mTvQiPingCount.setVisibility(View.GONE);
        mBtnShangMenPeiSong = (Button) findViewById(R.id.shangmenpeisong);
        mBtnShangMenPeiSong.setVisibility(View.GONE);
        mBtnShangMenPeiSong.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Intent i=new Intent(MainActivity.this,DeliveryActivity.class);
//                startActivity(i);
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SaleAll saleAll = new SaleAll();
                saleAll = (SaleAll) adapterView.getItemAtPosition(position);
//                Log.e("===", JSONUtils.toJsonWithGson((SaleAll) adapterView.getItemAtPosition(position)));
                Intent d=new Intent(MainActivity.this,DeliveryActivity.class);
                d.putExtra("Sale",saleAll);
                startActivityForResult(d,1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if (resultCode == 8) {
                //订单销单
                /**
                 * 获取订单信息
                 */
                Sale sale = new Sale();
                sale.setEmployeeID(StaffID);
                String request = JSONUtils.toJsonWithGson(sale);
                HsicMessage hsic = new HsicMessage();
                hsic.setRespMsg(request);
                String requestData = JSONUtils.toJsonWithGson(hsic);
                SearchAssignSaleTask task = new SearchAssignSaleTask(MainActivity.this, MainActivity.this);
                task.execute(requestData);
            }
        }

    }

    @Override
    public void SearchAssignSaleTaskEnd(HsicMessage tag) {
        if (tag.getRespCode() == 0) {
            SaleAll_List = new ArrayList<SaleAll>();
            SaleAll_List = JSONUtils.toListWithGson(tag.getRespMsg(), new TypeToken<List<SaleAll>>() {
            }.getType());//数据来源
            saleListAdapter = new SaleListAdapter(MainActivity.this, SaleAll_List);
            listview.setAdapter(saleListAdapter);
            mTvTaskCount.setText("" + SaleAll_List.size());
        } else {
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
        this.finish();
    }
}
