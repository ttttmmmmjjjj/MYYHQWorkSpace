package com.hsic.fxqpmanager;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.adapter.MenuGridAdapter;
import com.hsic.bean.Count;
import com.hsic.bean.Sale;
import com.hsic.bll.GetBasicInfo;
import com.hsic.db.DeliveryDB;
import com.hsic.dialog.CountDialog;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.task.UpLoadHistoryTask;
import com.hsic.tmj.gridview.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 统计
 */
public class CountActivity extends AppCompatActivity {
    private MyGridView gridview;
    private TextView station, info;
    GetBasicInfo getBasicInfo;
    TextView stationName, operator;
    String EmployeeID,StationID;
    Button btn_countD,btn_countR,btn_countS,btn_countDoorSale,btn_finish,btn_unfinish;
    DeliveryDB deliveryDB;
    List<Sale> sales;
    Count count;
    List<Count> mDataCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
//        stationName = this.findViewById(R.id.station);
//        operator = this.findViewById(R.id.info);
        getBasicInfo = new GetBasicInfo(this);
        deliveryDB=new DeliveryDB(this);
        EmployeeID=getBasicInfo.getOperationID();//登录员工号
        StationID=getBasicInfo.getStationID();
        innitView();
    }
    private void innitView() {
        gridview = (MyGridView) findViewById(R.id.gridview);
        String[] img_text = {"订单补打","退单管理","配送统计", "整改统计", "安检统计", "门售统计" };
        int[] imgs = {
                R.drawable.search, R.drawable.i,R.drawable.c,
                R.drawable.gas, R.drawable.order,
                R.drawable.truck, R.drawable.count
                };
        gridview.setAdapter(new MenuGridAdapter(this, img_text, imgs));
        info = (TextView) this.findViewById(R.id.info);
        info.setText(getBasicInfo.getOperationName());
        station = (TextView) this.findViewById(R.id.station);
        station.setText(getBasicInfo.getStationName());
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch ((int) i) {
                    case 0:
                        Intent login =new Intent(CountActivity.this,DeliveryFinishActivity.class);
                        startActivity(login);
                        break;

                    case 1:
                        Intent login3 =new Intent(CountActivity.this,ReturnOrderActivity.class);
                        startActivity(login3);
                        break;
                    case 2:
                        sales=new ArrayList<>();
                        sales=deliveryDB.SaleFinishCount(EmployeeID,StationID);
                        count=new Count();
                        count.setName("配送数量");
                        count.setValue(""+sales.size());
                        mDataCount=new ArrayList<>();
                        mDataCount.add(count);
                        new CountDialog(CountActivity.this,R.style.dialog,mDataCount,new CountDialog.OnCloseListener(){

                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                            }
                        }).setTitle("配送数量说明").show();
                        break;
                    case 3:
                        sales=new ArrayList<>();
                        sales=deliveryDB.RectifyFinishCount(EmployeeID,StationID);
                        count=new Count();
                        count.setName("整改数量");
                        count.setValue(""+sales.size());
                        List<Count> mDataCount;
                        mDataCount=new ArrayList<>();
                        mDataCount.add(count);
                        new CountDialog(CountActivity.this,R.style.dialog,mDataCount,new CountDialog.OnCloseListener(){

                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                            }
                        }).setTitle("整改数量说明").show();
                        break;
                    case 4:
                        sales=new ArrayList<>();
                        sales=deliveryDB.SearchFinishCount(EmployeeID,StationID);
                        count=new Count();
                        count.setName("安检数量");
                        count.setValue(""+sales.size());
                        mDataCount=new ArrayList<>();
                        mDataCount.add(count);
                        new CountDialog(CountActivity.this,R.style.dialog,mDataCount,new CountDialog.OnCloseListener(){

                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                            }
                        }).setTitle("安检数量说明").show();

                        break;
                    case 5:

                        break;



                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
