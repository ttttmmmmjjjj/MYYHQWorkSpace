package com.hsic.fxqpmanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.bean.HsicMessage;
import com.hsic.bean.UserInfoDoorSale;
import com.hsic.bll.GetBasicInfo;
import com.hsic.bll.SaleCode;
import com.hsic.dialog.SaleCodeDialog;
import com.hsic.listener.ImplFinishOrderTask;
import com.hsic.listener.ImplGetOrderBySaleIdTask;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.dialoglibrary.OnItemClickListener;
import com.hsic.task.FinishOrderTask;
import com.hsic.task.GetOrderBySaleIdTask;
import com.hsic.utils.DataUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * 上门提货
 */
public class PickGoodsActivity extends AppCompatActivity implements OnDismissListener, OnItemClickListener,
        ImplGetOrderBySaleIdTask,
        ImplFinishOrderTask {
    private TextView txt_stationName,txt_saleID,txt_userID,txt_UserName,txt_userAddress,
            txt_goodsInfo,txt_totalPrice,txt_emptyID,
            txt_fullID,txt_operationTime,txt_payMode,txt_operator,txt_PhoneNum;
    private Button btn_scanFull,btn_finish;
    private SearchView sv_saleID;
    private String saleID;
    UserInfoDoorSale userInfoDoorSale;
    private String upGoodsType;//1.气瓶，2.配件
    int maxGoodsCount=0;
    private String goodsName,goodsCode;
    private String requestStr,deviceIDStr;
    Toolbar toolbar;
    GetBasicInfo basicInfo;
    private boolean scantag=false;//获取订单号通过扫描一维码
    private String FullNO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_goods);
        basicInfo=new GetBasicInfo(PickGoodsActivity.this);
        deviceIDStr= basicInfo.getDeviceID();
        sv_saleID=this.findViewById(R.id.sv_saleID);
        sv_saleID.setIconifiedByDefault(true);
        sv_saleID.setFocusable(true);
        sv_saleID.setIconified(false);
        sv_saleID.requestFocusFromTouch();
        /**
         * 设置提示框字体颜色
         * SearchView去掉默认的下划线
         */
        if(sv_saleID == null) { return;}
        int id = sv_saleID.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView =  sv_saleID.findViewById(id);
        textView.setTextColor(Color.BLACK);//字体颜色
        textView.setTextSize(18);//字体、提示字体大小
        textView.setHintTextColor(Color.GRAY);//提示字体颜色
        if (sv_saleID != null) {
            try {        //--拿到字节码
                Class<?> argClass = sv_saleID.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(sv_saleID);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        txt_PhoneNum=this.findViewById(R.id.txt_PhoneNum);
        txt_PhoneNum.setText("");
        txt_stationName=this.findViewById(R.id.txt_stationName);
        txt_stationName.setText("");
        txt_saleID=this.findViewById(R.id.txt_saleID);
        txt_saleID.setText("");
        txt_userID=this.findViewById(R.id.txt_userID);
        txt_userID.setText("");
        txt_UserName=this.findViewById(R.id.txt_UserName);
        txt_UserName.setText("");
        txt_userAddress=this.findViewById(R.id.txt_userAddress);
        txt_userAddress.setText("");
        txt_goodsInfo=this.findViewById(R.id.txt_goodsInfo);
        txt_goodsInfo.setText("");
        txt_totalPrice=this.findViewById(R.id.txt_totalPrice);
        txt_totalPrice.setText("");
        txt_emptyID=this.findViewById(R.id.txt_emptyID);
        txt_emptyID.setText("");
        txt_fullID=this.findViewById(R.id.txt_fullID);
        txt_fullID.setText("");
        txt_operationTime=this.findViewById(R.id.txt_operationTime);
        txt_operationTime.setText("");
        txt_payMode=this.findViewById(R.id.txt_payMode);
        txt_payMode.setText("");
        txt_operator=this.findViewById(R.id.txt_operator);
        txt_operator.setText("");

        btn_scanFull=this.findViewById(R.id.btn_scanFull);
        btn_finish=this.findViewById(R.id.btn_finish);
        sv_saleID.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                saleID=sv_saleID.getQuery().toString();
                if(TextUtils.isEmpty(saleID)){
                    Toast.makeText(getBaseContext(),"查询条件不能为空",Toast.LENGTH_SHORT).show();
                }
                if(scantag){
                    saleID=sv_saleID.getQuery().toString();
                    scantag=false;
                }else{
                    if(saleID.length()<16){
                        saleID=basicInfo.getStationID()+ DataUtils.getDate()+saleID;
                    }else{
                        saleID=sv_saleID.getQuery().toString();
                    }

                }
                userInfoDoorSale=new UserInfoDoorSale();
                userInfoDoorSale.setSaleID(saleID);
                requestStr= JSONUtils.toJsonWithGson(userInfoDoorSale);
                new GetOrderBySaleIdTask(PickGoodsActivity.this,PickGoodsActivity.this,deviceIDStr,requestStr).execute();
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        btn_scanFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=txt_userID.getText().toString();
                if(TextUtils.isEmpty(userid)){
                    Toast.makeText(getBaseContext(),"请先查询订单信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent scan=new Intent(PickGoodsActivity.this,ScanQPTagActivity.class);
                scan.putExtra("QPNO",FullNO);
                startActivityForResult(scan,1);
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=txt_userID.getText().toString();
                if(TextUtils.isEmpty(userid)){
                    Toast.makeText(getBaseContext(),"请先查询订单信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                String fullNo=(userInfoDoorSale.getFullNO()==null? "" :userInfoDoorSale.getFullNO());
                if(TextUtils.isEmpty(fullNo)){
                    Toast.makeText(getBaseContext(),"请先扫满瓶",Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断满瓶数量
                int scanCount=0;
                if(fullNo.contains(",")){
                    String[] size=fullNo.split(",");
                    scanCount=size.length;
                }else{
                    scanCount=1;
                }
                if(scanCount!=maxGoodsCount){
                    Toast.makeText(getBaseContext(),"满瓶数量不够",Toast.LENGTH_SHORT).show();
                    return;
                }
                userInfoDoorSale.setFullNO(fullNo);
                requestStr= JSONUtils.toJsonWithGson(userInfoDoorSale);
                new FinishOrderTask(PickGoodsActivity.this,PickGoodsActivity.this,deviceIDStr,requestStr).execute();
            }
        });
        userInfoDoorSale=new UserInfoDoorSale();
        FullNO="";
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.pickgoods_toolbar_menu);
        toolbar.setTitle(basicInfo.getStationName());
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if(menuItemId==R.id.action_search){
                    Intent s=new Intent(PickGoodsActivity.this,ScanQRCodeActivity.class);
                    s.putExtra("RequestMode","sale");
                    startActivityForResult(s,5);
                }
                return false;
            }
        });
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
//
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Log.e("00004","="+4);
//                int menuItemId = item.getItemId();
//                Log.e("menuItemId","="+menuItemId);
//                if(menuItemId==R.id.action_search){
//                    Intent s=new Intent(PickGoodsActivity.this,ScanQRCodeActivity.class);
//                    s.putExtra("RequestMode","sale");
//                    startActivityForResult(s,5);
//                }
//
//                return false;
//            }
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            txt_fullID.setVisibility(View.VISIBLE);
            Bundle b=data.getExtras(); //data为B中回传的Intent
            String userRegionCode=b.getString("userReginCode");
            userInfoDoorSale.setFullNO(userRegionCode);
            txt_fullID.setText("满瓶号:"+userRegionCode);
            FullNO=userRegionCode;
        }
        if(requestCode==5){
            scantag=true;
            Bundle b=data.getExtras(); //data为B中回传的Intent
            String saleID=b.getString("SaleID");
            sv_saleID.setQuery(saleID,true);
        }
    }
    // 是否退出程序
    private static Boolean isExit = false;
    // 定时触发器
    private static Timer tExit = null;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (isExit == false) {
//                isExit = true;
//                if (tExit != null) {
//                    tExit.cancel(); // 将原任务从队列中移除
//                }
//                // 重新实例一个定时器
//                tExit = new Timer();
//                TimerTask task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        isExit = false;
//                    }
//                };
//                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                // 延时两秒触发task任务
//                tExit.schedule(task, 2000);
//            } else {
//                finish();
//                System.exit(0);
//            }
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void GetOrderBySaleIdTaskEnd(HsicMessage tag) {
        try{
            if(tag.getRespCode()==0){
                maxGoodsCount=0;
                userInfoDoorSale=new UserInfoDoorSale();
                List<UserInfoDoorSale.GoodsInfo> goodsInfo_List2=new ArrayList<UserInfoDoorSale.GoodsInfo>();
                userInfoDoorSale=JSONUtils.toObjectWithGson(tag.getRespMsg(),UserInfoDoorSale.class);
                /**
                 * 首先判断该订单状态
                 */
                String  saleStatus=userInfoDoorSale.getSaleStatus();
                if(saleStatus.equals("2")){
                    shownDialog("该订单已销单");
                    return ;
                }else if(saleStatus.equals("3")){
                    shownDialog("该订单已作废");
                    return;
                }
                goodsInfo_List2=userInfoDoorSale.getGoodsInfo();
                int size=goodsInfo_List2.size();
                StringBuffer goodsInfo=new StringBuffer();
                for(int i=0;i<size;i++){
                    String goodsCountStr=goodsInfo_List2.get(i).getGoodsCount();
                    int temp=Integer.parseInt(goodsCountStr);
                    maxGoodsCount=maxGoodsCount+temp;
                    goodsInfo.append(goodsInfo_List2.get(i).getGoodsName()+"/"+goodsInfo_List2.get(i).getGoodsCount()+
                            "个"+",");
                }
                String tempGoods=(goodsInfo.substring(0,goodsInfo.length()-1)).toLowerCase();
                txt_stationName.setText("用户站点:"+userInfoDoorSale.getStationName());
                txt_saleID.setText("订单编号:"+userInfoDoorSale.getSaleID());
                txt_userID.setText("用户编号:"+userInfoDoorSale.getUserID());
                txt_UserName.setText("用户姓名:"+userInfoDoorSale.getUserName());
                txt_userAddress.setText("用户地址:"+userInfoDoorSale.getAddress());
                txt_PhoneNum.setText("电话:"+userInfoDoorSale.getPhoneNumber());
                txt_goodsInfo.setText("商品信息:"+tempGoods);
                txt_totalPrice.setText("总价:"+userInfoDoorSale.getTotalPrice());
                txt_emptyID.setText("空瓶号:"+userInfoDoorSale.getEmptyNO());
                txt_fullID.setText("满瓶号:");
                txt_operationTime.setText("下单时间:"+userInfoDoorSale.getOperationTime());
                if(userInfoDoorSale.getPayMode().toUpperCase().equals("C")){
                    txt_payMode.setText("支付方式:"+"现金");
                }
                txt_operator.setText("操作人:"+basicInfo.getOperationName());

            }else{
                shownDialog(tag.getRespMsg());
            }
        }catch(Exception ex){
            ex.toString();
            ex.printStackTrace();
            shownDialog( ex.toString());
        }


    }

    @Override
    public void GetImplFinishOrderTaskEnd(HsicMessage tag) {
//        Log.e("FinishOrderTask",JSONUtils.toJsonWithGson(tag));
        try{
            if(tag.getRespCode()==0) {
                FullNO="";
                List<UserInfoDoorSale.GoodsInfo> goodsInfo_List2=new ArrayList<UserInfoDoorSale.GoodsInfo>();
                userInfoDoorSale = new UserInfoDoorSale();
                userInfoDoorSale = JSONUtils.toObjectWithGson(tag.getRespMsg(), UserInfoDoorSale.class);
                goodsInfo_List2=userInfoDoorSale.getGoodsInfo();
                int size=goodsInfo_List2.size();
                StringBuffer goodsInfo=new StringBuffer();
                for(int i=0;i<size;i++){
                    goodsInfo.append(goodsInfo_List2.get(i).getGoodsName()+"\t\t\t\t"+goodsInfo_List2.get(i).getGoodsCount()+
                            "个"+",");
                }
                //显示订单信息并且打印
                StringBuilder msg=new StringBuilder();
                msg.append("\n\n");
                msg.append("用户站点:"+userInfoDoorSale.getStationName()+"\n");
                msg.append("订单编号:"+userInfoDoorSale.getSaleID()+"\n");
                msg.append("用户编号:"+userInfoDoorSale.getUserID()+"\n");
                msg.append("用户姓名:"+userInfoDoorSale.getUserName()+"\n");
                msg.append("电话:"+userInfoDoorSale.getPhoneNumber()+"\n");
                msg.append("用户地址:"+userInfoDoorSale.getAddress()+"\n");
                String goodsTemp=(goodsInfo.substring(0,goodsInfo.length()-1)).toString();
                msg.append("商品详情:"+"\n");
                if(goodsTemp.contains(",")){
                    String[] g=goodsTemp.split(",");
                    int i=g.length;
                    for(int j=0;j<i;j++){
                        msg.append("\t\t\t\t\t\t\t\t"+g[j]+"\n");
                    }
                }else{

                    msg.append("\t\t\t\t\t\t\t\t"+goodsTemp+"\n");
                }
                msg.append("空瓶:"+userInfoDoorSale.getEmptyNO()+"\n");
                msg.append("满瓶:"+userInfoDoorSale.getFullNO()+"\n");
                msg.append("总价:"+userInfoDoorSale.getTotalPrice()+"\n");
                msg.append("完成时间:"+userInfoDoorSale.getOperationTime()+"\n");
                msg.append("操作人:"+basicInfo.getOperationName()+"\n");
                msg.append("操作站点:"+basicInfo.getStationName()+"\n");
                SaleCode sleCode=new SaleCode(userInfoDoorSale.getSaleID());
                byte[] s=sleCode.getSaleCode();
                SaleCodeDialog t=new SaleCodeDialog(PickGoodsActivity.this,msg.toString(),s,userInfoDoorSale);
//                SaleInfoDialogByPrint s=new SaleInfoDialogByPrint(PickGoodsActivity.this,msg.toString());
                t.shown();
            }else{
                shownDialog(tag.getRespMsg());
            }

        }catch(Exception ex){
            ex.toString();
        }
    }

    private void shownDialog(String msg){
        new AlertView("提示", msg, null, new String[] { "确定" },
                null,this, AlertView.Style.Alert, this)
                .show();
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
    }

}
