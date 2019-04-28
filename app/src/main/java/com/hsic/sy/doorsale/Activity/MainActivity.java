package com.hsic.sy.doorsale.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.hsic.sy.adapter.DoorsaleGridAdapter;
import com.hsic.sy.adapter.GPManagerGridAdapter;
import com.hsic.sy.bll.Count;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.dialoglibrary.OnItemClickListener;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.doorsale.bean.UserInfoDoorSale;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.doorsale.lisenter.ImplGetSaleCountTask;
import com.hsic.sy.doorsale.task.GetSaleCountTask;
import com.hsic.sy.supply.activity.AddFullQPActivity;
import com.hsic.sy.supply.activity.GPManagerActivity;
import com.hsic.sy.supply.activity.ReadTag;
import com.hsic.sy.supplystationmanager.R;
import com.hsic.sy.view.CommomDialog;
import com.hsic.sy.view.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ImplGetSaleCountTask,
        OnDismissListener, com.hsic.sy.dialoglibrary.OnItemClickListener {
//    private Button btn_placeOrder,btn_pickGoods,btn_canselOrder,btn_count,btn_print,btn_exchangegoods;
    UserInfoDoorSale userInfoDoorSale;
    GetBasicInfo basicInfo;
    Toolbar toolbar;
    private MyGridView gridview;
    private List<Count> mDataCount;
    Count count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        basicInfo=new GetBasicInfo(MainActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(basicInfo.getStationName());
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        gridview = (MyGridView) findViewById(R.id.gridview);
        gridview.setAdapter(new DoorsaleGridAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                switch ((int) id) {
                    case 0://���ι���
                        Intent p=new Intent(MainActivity.this,PlaceOrderActivity.class);
                        startActivity(p);
//					shownDialog("��δ����!");
                        break;
                    case 1:
                        Intent pi=new Intent(MainActivity.this,PickGoodsActivity.class);
                        startActivity(pi);
//					shownDialog("��δ����!");
                        break;
                    case 2:
                        Intent c=new Intent(MainActivity.this,CanselOrderActivity.class);
                        startActivity(c);
                        break;
                    case 3:
                        new GetSaleCountTask(MainActivity.this,MainActivity.this).execute();
//					shownDialog("��δ����!");
                        break;
                    case 4:
//					shownDialog(
                        Intent dp=new Intent(MainActivity.this,DelayPrintActivity.class);
                        startActivity(dp);
                        break;
                    case 5:
                        Intent ex=new Intent(MainActivity.this,ExchangeGoodsActivity.class);
                        startActivity(ex);
//					shownDialog("��δ����!");
                        break;
                }
            }
        });
//
//        btn_placeOrder=this.findViewById(R.id.btn_placeOrder);
//        btn_pickGoods=this.findViewById(R.id.btn_pickGoods);
//        btn_canselOrder=this.findViewById(R.id.btn_canselOrder);
//        btn_count=this.findViewById(R.id.btn_count);
//        btn_print=this.findViewById(R.id.btn_print);
//        btn_exchangegoods=this.findViewById(R.id.btn_exchangegoods);
//        userInfoDoorSale=new UserInfoDoorSale();
//        btn_canselOrder.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                Intent c=new Intent(MainActivity.this,CanselOrderActivity.class);
//                startActivity(c);
//            }
//        });
//        btn_placeOrder.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent p=new Intent(MainActivity.this,PlaceOrderActivity.class);
//                startActivity(p);
//            }
//        });
//        btn_pickGoods.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent p=new Intent(MainActivity.this,PickGoodsActivity.class);
//                startActivity(p);
//            }
//        });
//        btn_print.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent dp=new Intent(MainActivity.this,DelayPrintActivity.class);
//                startActivity(dp);
//            }
//        });
//        btn_count.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new GetSaleCountTask(MainActivity.this,MainActivity.this).execute();
//            }
//        });
//
//        btn_exchangegoods.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                Intent ex=new Intent(MainActivity.this,ExchangeGoodsActivity.class);
//                startActivity(ex);
//            }
//        });
    }

    @Override
    public void ImplGetSaleCount(HsicMessage tag) {
        try{
            if(tag.getRespCode()==0){
                shownDialog(tag.getRespMsg()+"单");
                mDataCount=new ArrayList<>();
                count=new Count();
                count.setName("当天销售单数:");
                count.setValue(tag.getRespMsg()+"单");
                new CommomDialog(this,R.style.dialog,mDataCount,new CommomDialog.OnCloseListener(){

                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                    }
                }).setTitle("当天销售统计").show();
            }else{
                shownDialog(tag.getRespMsg());
            }
        }catch (Exception ex){
            ex.toString();
        }
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {

    }
    private void shownDialog(String msg){
        new AlertView("当天销售量", msg, null, new String[] { "确定" },
                null,this, AlertView.Style.Alert, this)
                .show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
