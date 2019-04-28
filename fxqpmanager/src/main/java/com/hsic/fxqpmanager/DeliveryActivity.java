package com.hsic.fxqpmanager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.bdlocation.BDLocationUtils;
import com.hsic.bdlocation.Const;
import com.hsic.bean.CustomerInfo;
import com.hsic.bean.HsicMessage;
import com.hsic.bean.Sale;
import com.hsic.bean.SaleDetail;
import com.hsic.bean.ScanHistory;
import com.hsic.bean.UserReginCode;
import com.hsic.bean.UserXJInfo;
import com.hsic.bll.GetBasicInfo;
import com.hsic.db.DeliveryDB;
import com.hsic.listener.ImplUploadSale;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.dialoglibrary.OnItemClickListener;
import com.hsic.task.UploadSaleTask;
import com.hsic.utils.PrintUtils;
import com.hsic.utils.TimeUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 配送
 */
public class DeliveryActivity extends AppCompatActivity implements OnDismissListener, OnItemClickListener,
        ImplUploadSale {
    List<UserReginCode> EmptyList;//暂存已扫描到的空瓶
    List<UserReginCode> FullList;//暂存已扫描到的满瓶
    AlertView IsSunmitSale,tagDialog;//确认提交订单对话框
    List<Map<String, String>> saleInfo;//根据用户编号查询订单相关信息
    private TextView txt_userName, txt_cardID, txt_phone, txt_goods, txt_address, txt_sendQP,
            txt_receiverQP,txt_SaleID,txt_price,txt_userType,txt_callingPhone;
    private Button btn_readQP, btn_search, btn_print, btn_submit, btn_checkLast, btn_checkNext;
    private String[] QPType = {"满瓶", "空瓶"};//选择扫描的气瓶类型
    private String GoodsCount;//气瓶数量
    private List<com.hsic.bean.SaleDetail> SaleDetail_LIST;
    private String ReceiveByInput;//手输回收瓶标识
    private String DeliverByInput;//手输发出瓶标识
    private String StationID, SaleID, UserID, OperationID, DeviceID, IsNew;
    private GetBasicInfo getBasicInfo;
    private DeliveryDB dbData;
    private AlertDialog alertDialog1; //信息框
    List<ScanHistory> scanHistories;
    Sale sale;
    StringBuilder msg = new StringBuilder();
    String userName="",phone="",goods="",address="",CustomerCardID="",userType="",AllPrice="",callingTphone="";
    private boolean isSave=false;
    List<SaleDetail> details;
//    BDLocationUtils bdLocationUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        innitView();
        getBasicInfo = new GetBasicInfo(this);
        StationID = getBasicInfo.getStationID();
        OperationID = getBasicInfo.getOperationID();
        DeviceID = getBasicInfo.getDeviceID();
        dbData = new DeliveryDB(this);
        saleInfo = new ArrayList<Map<String, String>>();
        GoodsCount = "";
        Intent i = getIntent();
        UserID = i.getStringExtra("UserID");
        SaleID=i.getStringExtra("SaleID");
        EmptyList = new ArrayList<UserReginCode>();
        FullList = new ArrayList<UserReginCode>();
        details=new ArrayList<>();
        IsSunmitSale = new AlertView("提示", "确认提交订单", "取消", new String[]{"确定"},
                null, this, AlertView.Style.Alert, this);//
        tagDialog=  new AlertView("提示", "请提交订单", null, new String[]{"确定"},
                null, this, AlertView.Style.Alert, this);
        /**
         * 根据用户编号查询用户订单信息
         */
        if(!SaleID.equals("")){
            saleInfo = dbData.GetSaleInfoBySaleID(OperationID, StationID, SaleID);
        }else{
            saleInfo = dbData.GetSaleInfoByUserID(OperationID, StationID, UserID);
        }

        setViewData();
//        bdLocationUtils=new BDLocationUtils(this);//百度地图定位
//        bdLocationUtils.doLocation();
//        bdLocationUtils.mLocationClient.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("DeliveryActivity", "———onStart(): TaskId: " + getTaskId() +",  hashCode: " + hashCode()+" time:" +System.currentTimeMillis());

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.e("DeliveryActivity", "———onPause(): TaskId: " + getTaskId() +",  hashCode: " + hashCode()+System.currentTimeMillis());


    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("DeliveryActivity", "———onStop(): TaskId: " + getTaskId() +",  hashCode: " + hashCode()+" time:" +System.currentTimeMillis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DeliveryActivity", "———onDestroy(): TaskId: " + getTaskId() +",  hashCode: " + hashCode()+" time:" +System.currentTimeMillis());
//        bdLocationUtils.mLocationClient.stop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
        //若是该订单已做  提示提交该订单
            if(dbData.isDone(OperationID,StationID,SaleID)){
                tagDialog.show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void innitView() {
        txt_SaleID=this.findViewById(R.id.txt_saleID);
        txt_SaleID.setText("订单：");
        txt_userName = this.findViewById(R.id.txt_userName);
        txt_userName.setText("姓名：");
        txt_cardID = this.findViewById(R.id.txt_cardID);
        txt_cardID.setText("编号：");
        txt_phone = this.findViewById(R.id.txt_phone);
        txt_phone.setText("联系电话：");
        txt_callingPhone=this.findViewById(R.id.txt_callingPhone);
        txt_callingPhone.setText("来电电话：");
        txt_goods = this.findViewById(R.id.txt_goods);
        txt_goods.setText("商品：");
        txt_address = this.findViewById(R.id.txt_address);
        txt_address.setText("地址：");
        txt_price=this.findViewById(R.id.txt_price);
        txt_price.setText("交易金额：");
        txt_userType=this.findViewById(R.id.txt_userType);
        txt_userType.setText("客户类型：");
        txt_receiverQP = this.findViewById(R.id.txt_gpinfo2);
        txt_receiverQP.setText("");
        txt_sendQP = this.findViewById(R.id.txt_gpinfo4);
        txt_sendQP.setText("");
        btn_readQP = this.findViewById(R.id.btn_readQP);
        btn_search = this.findViewById(R.id.btn_search);
        btn_print = this.findViewById(R.id.btn_print);
        btn_submit = this.findViewById(R.id.btn_submit);
        btn_submit.setEnabled(false);
        btn_readQP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先弹出扫空瓶还是满瓶
                showList();//选择
            }
        });
        /**
         * 巡检
         */
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(DeliveryActivity.this, InspectionActivity.class);
                search.putExtra("SaleID",SaleID);
                search.putExtra("UserID",UserID);
                search.putExtra("UserName",userName);
                search.putExtra("Address",address);
                startActivity(search);
            }
        });
        /**
         * 保存打印
         */
        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交订单
                //校验瓶子数量
                int goodsCount = 0;
                String FullNo = "";
                FullNo = txt_sendQP.getText().toString();
                if (!TextUtils.isEmpty(FullNo)) {
                    int count = 0;
                    if (FullNo.contains(",")) {
                        String[] tmp = FullNo.split(",");
                        count = tmp.length;
                    } else {
                        count = 1;
                    }
                    //校验满瓶数量
                    goodsCount = Integer.parseInt(GoodsCount);
                    if (count != goodsCount) {
                        Toast.makeText(DeliveryActivity.this, "请核对满瓶数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(DeliveryActivity.this, "满瓶数量不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String EmptyNo = "";
                EmptyNo = txt_receiverQP.getText().toString();
                if (!IsNew.equals("0")) {
                    //非新用户必须有归还瓶
                    if (TextUtils.isEmpty(EmptyNo)) {
                        Toast.makeText(DeliveryActivity.this, "空瓶数量不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                /**
                 * 同一钢瓶瓶不能既是满瓶又是空瓶
                 */
               if(goodsCount>1){
                   if(!EmptyNo.equals("")){
                       if(EmptyNo.contains(",")){
                           String[] tmp = EmptyNo.split(",");
                           int size=tmp.length;
                           for(int a=0;a<size;a++){
                               if(FullNo.contains(tmp[a])){
                                   Toast.makeText(DeliveryActivity.this, "同一钢瓶瓶不能既是满瓶又是空瓶", Toast.LENGTH_SHORT).show();
                                   return;
                               }
                           }
                       }else{
                          if(FullNo.contains(EmptyNo)){
                              Toast.makeText(DeliveryActivity.this, "同一钢瓶瓶不能既是满瓶又是空瓶", Toast.LENGTH_SHORT).show();
                              return;
                          }

                       }
                   }
               }else {
                   if(!EmptyNo.equals("")){
                       if( EmptyNo.equals(FullNo)){
                           Toast.makeText(DeliveryActivity.this, "同一钢瓶瓶不能既是满瓶又是空瓶", Toast.LENGTH_SHORT).show();
                           return;
                       }

                   }
               }
                sale=new Sale();
                sale.setFinishTime(TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));
                sale.setSendQP(FullNo);
                sale.setReceiveQP(EmptyNo);
                sale.setReceiveByInput(ReceiveByInput);
                sale.setDeliverByInput(DeliverByInput);
                sale.setGPS_J(Const.LONGITUDE);
                sale.setGPS_W(Const.LATITUDE);
                SortSaleDetail();
                msg=new StringBuilder();
                msg.append(("---------------------------------------------------------------\n")) ;
                msg.append(PrintUtils.printTwoData("供应站点", getBasicInfo.getStationName() + "\n"));
                msg.append(PrintUtils.printTwoData("收据单", SaleID + "\n"));
                msg.append(PrintUtils.printTwoData("送气号", UserID + "\n"));
                msg.append(PrintUtils.printTwoData("姓名", userName + "\n"));
                msg.append(PrintUtils.printTwoData("客户类型", userType + "\n"));
                msg.append(PrintUtils.printTwoData("联系电话", phone+ "\n"));
                msg.append("上海奉贤燃气股份有限公司" + "\n");//公司
                msg.append(("---------------------------------------------------------------\n")) ;
                msg.append("回收瓶：" + "\n");
                msg.append(EmptyNo + "\n");
                msg.append("配送瓶：" + "\n");
                msg.append(FullNo + "\n");
                msg.append(("---------------------------------------------------------------\n")) ;
                if(!isSave){
                    IsSunmitSale.show();
                }else{
                    btn_submit.setEnabled(true);
                    Toast.makeText(DeliveryActivity.this, "订单保存成功", Toast.LENGTH_SHORT).show();
                    details=dbData.GetSaleDetailByP(OperationID,StationID,SaleID);
                    int size=details.size();
                    for (int i = 0; i < size; i++) {
                        msg.append(PrintUtils.printThreeData(details.get(i).getQPName(), ""+details.get(i).getSendNum(), details.get(i).getQPPrice() + "\n"));
                    }
                    msg.append(PrintUtils.printTwoData("交易金额",AllPrice+"\n"));
                    SaleCodeDialog t = new SaleCodeDialog(DeliveryActivity.this, msg.toString());
                    t.shown();
                }

            }
        });
        /**
         * 上传提交
         */
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 上传订单信息
                 */
                if(checkNetworkState()){
                    new UploadSaleTask(DeliveryActivity.this,DeliveryActivity.this).execute(SaleID,UserID);
                }else{
                    Toast.makeText(DeliveryActivity.this, "无网络", Toast.LENGTH_SHORT).show();
                    DeliveryActivity.this.finish();
                }

            }
        });
        btn_checkLast = this.findViewById(R.id.checklook);
        btn_checkNext = this.findViewById(R.id.checklook1);
        /**
         * 查看回收瓶
         */
        btn_checkLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmptyNO = "";
                EmptyNO = txt_receiverQP.getText().toString();
                Intent e = new Intent(DeliveryActivity.this, ReadQPActivity.class);
                e.putExtra("Operation", "E");
                e.putExtra("QPNO", EmptyNO);
                e.putExtra("SaleID", SaleID);
                e.putExtra("QPCount", GoodsCount);
                Bundle bundle = new Bundle();
                bundle.putSerializable("EmptyList", (Serializable) EmptyList);
                e.putExtra("bundle", bundle);
                startActivityForResult(e, 2);
            }
        });
        /**
         * 查看交付瓶
         */
        btn_checkNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullNO = "";
                FullNO = txt_sendQP.getText().toString();
                Intent f = new Intent(DeliveryActivity.this, ReadQPActivity.class);
                f.putExtra("Operation", "F");
                f.putExtra("SaleID", SaleID);
                f.putExtra("QPNO", FullNO);
                f.putExtra("QPCount", GoodsCount);
                Bundle bundle = new Bundle();
                bundle.putSerializable("FullList", (Serializable) FullList);
                f.putExtra("bundle", bundle);
                startActivityForResult(f, 1);
            }
        });
    }

    /**
     * 初始化控件信息
     */
    private void setViewData() {
        if(saleInfo.size()>0){
            SaleID=saleInfo.get(0).get("SaleID");
            userName=saleInfo.get(0).get("CustomerName");
            CustomerCardID=saleInfo.get(0).get("CustomerCardID");
            goods=saleInfo.get(0).get("GoodsInfo");
            phone=saleInfo.get(0).get("Telephone");
            callingTphone=saleInfo.get(0).get("CallingTelephone");
            address=saleInfo.get(0).get("Address");
            userType=saleInfo.get(0).get("CustomerTypeName");
            AllPrice=saleInfo.get(0).get("AllPrice");
            txt_SaleID.setText("订单号："+SaleID);
            txt_cardID.setText("送气号：" + UserID);
            IsNew = saleInfo.get(0).get("IsNew");
            if (IsNew.equals("0")) {
                Spanned strA = Html.fromHtml("姓名：" +userName + "["+"<font color=#ff0000>" + "新用户" + "</font>"+"]");
                txt_userName.setText(strA);
            } else {
                txt_userName.setText("姓名：" + userName);
            }
            txt_userType.setText("客户类型："+userType);
            txt_phone.setText("联系电话：" + phone);
            txt_callingPhone.setText("来电电话："+callingTphone);
            txt_address.setText("地址：" + address);
            txt_goods.setText("商品：" + goods);
            txt_price.setText("交易金额："+AllPrice);

            GoodsCount = saleInfo.get(0).get("GoodsCount");
        }else{
            new AlertView("提示","该用户无可配送的订单", null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //满瓶
            DeliverByInput = "";
            FullList = new ArrayList<UserReginCode>();
            Bundle b = data.getExtras(); //data为B中回传的Intent
            Bundle bundle = data.getBundleExtra("bundle");
            String userRegionCode = b.getString("userReginCode");
            DeliverByInput = b.getString("DeliverByInput");
            FullList = (ArrayList<UserReginCode>) bundle.getSerializable("FullList");
            txt_sendQP.setText(userRegionCode);
            Log.e("DeliverByInput",JSONUtils.toJsonWithGson(DeliverByInput));
        }
        if (requestCode == 2) {
            //空瓶
            ReceiveByInput = "";
            EmptyList = new ArrayList<UserReginCode>();
            Bundle b = data.getExtras(); //data为B中回传的Intent
            Bundle bundle = data.getBundleExtra("bundle");
            EmptyList = (ArrayList<UserReginCode>) bundle.getSerializable("EmptyList");
            String userRegionCode = b.getString("userReginCode");
            ReceiveByInput = b.getString("ReceiveByInput");
            txt_receiverQP.setText(userRegionCode);
            Log.e("ReceiveByInput",JSONUtils.toJsonWithGson(ReceiveByInput));
        }
    }

    /**
     * 选择扫描气瓶类型
     */
    public void showList() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("请选择扫描气瓶类型");
        alertBuilder.setItems(QPType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (QPType[i].equals("满瓶")) {
                    String FullNO = "";
                    FullNO = txt_sendQP.getText().toString();
                    Intent f = new Intent(DeliveryActivity.this, ReadQPActivity.class);
                    f.putExtra("Operation", "F");
                    f.putExtra("SaleID", SaleID);
                    f.putExtra("QPNO", FullNO);
                    f.putExtra("QPCount", GoodsCount);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FullList", (Serializable) FullList);
                    f.putExtra("bundle", bundle);
                    startActivityForResult(f, 1);
                }
                if (QPType[i].equals("空瓶")) {
                    String EmptyNO = "";
                    EmptyNO = txt_receiverQP.getText().toString();
                    Intent e = new Intent(DeliveryActivity.this, ReadQPActivity.class);
                    e.putExtra("Operation", "E");
                    e.putExtra("QPNO", EmptyNO);
                    e.putExtra("SaleID", SaleID);
                    e.putExtra("QPCount", GoodsCount);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EmptyList", (Serializable) EmptyList);
                    e.putExtra("bundle", bundle);
                    startActivityForResult(e, 2);
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }

    @Override
    public void onDismiss(Object o) {
        if (o == IsSunmitSale) {
            IsSunmitSale.dismiss();
        }else if(o==tagDialog){
            tagDialog.dismiss();
        }else{
            DeliveryActivity.this.finish();
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        try {
            if (o == IsSunmitSale) {
                if(position==0){
                    //保存订单数据到本地数据库
                    isSave=dbData.upDataSaleInfo(OperationID,StationID,SaleID,sale,SaleDetail_LIST,scanHistories);
                    if(isSave){
                        btn_submit.setEnabled(true);
                        Toast.makeText(DeliveryActivity.this, "订单保存成功", Toast.LENGTH_SHORT).show();
                        details=dbData.GetSaleDetailByP(OperationID,StationID,SaleID);
                        int size=details.size();
                        for (int i = 0; i < size; i++) {
                            msg.append(PrintUtils.printThreeData(details.get(i).getQPName(), ""+details.get(i).getSendNum(), details.get(i).getQPPrice() + "\n"));
                        }
                        msg.append(PrintUtils.printTwoData("交易金额",AllPrice+"\n"));
                        SaleCodeDialog t = new SaleCodeDialog(DeliveryActivity.this, msg.toString());
                        t.shown();
                    }
                }
                IsSunmitSale.dismiss();
            }else if(o==tagDialog){
                tagDialog.dismiss();
            }else{
                DeliveryActivity.this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 整理交易detail表详情[保存订单信息时使用]
     */
    private void SortSaleDetail() {
        SaleDetail_LIST = new ArrayList<com.hsic.bean.SaleDetail>();
        List<String> strings=new ArrayList<>();
        strings=dbData.GetSaleDetail(OperationID,StationID,SaleID);
        int typeSize=strings.size();
        int fullSize = FullList.size();
        int  EmptySize = EmptyList.size();
        if(typeSize>0){
            for(int a=0;a<typeSize;a++){
                String type=strings.get(a);
                String typeName="";
                int count=0;
                SaleDetail saleDetail = new SaleDetail();
                scanHistories=new ArrayList<ScanHistory>();
                /**
                 * 设置ScanHistory
                 */
                Log.e("FullList1",JSONUtils.toJsonWithGson(FullList));
                Log.e("EmptyList1",JSONUtils.toJsonWithGson(EmptyList));
                for (int f = 0; f < fullSize; f++) {
                    ScanHistory sh = new ScanHistory();
                    sh.setSaleID(SaleID);
                    sh.setUseRegCode(FullList.get(f).getUserRegionCode());
                    sh.setTypeFlag("09");//
                    sh.setQPType(FullList.get(f).getQpType());
                    scanHistories.add(sh);
                    if(type.equals(FullList.get(f).getQpType())){
                        typeName=FullList.get(f).getQpName();
                        count=count+1;
                    }
                }
                /**
                 * 设置saleDetail
                 */
                saleDetail.setQPType(type);
                saleDetail.setQPName(typeName);
                saleDetail.setSendNum(count);
                count=0;
                for (int e = 0; e < EmptySize; e++) {
                    ScanHistory sh = new ScanHistory();
                    sh.setCustomerID(UserID);
                    sh.setSaleID(SaleID);
                    sh.setUseRegCode(EmptyList.get(e).getUserRegionCode());
                    sh.setTypeFlag("01");
                    sh.setQPType(EmptyList.get(e).getQpType());
                    scanHistories.add(sh);
                    if(type.equals(EmptyList.get(e).getQpType())){
                        count=count+1;
                    }
                }
                /**
                 * 设置saleDetail
                 */
                saleDetail.setReceiveNum(count);
                SaleDetail_LIST.add(saleDetail);
            }
        }


    }

    @Override
    public void UpLoadSaleTaskEnd(HsicMessage tag) {
        new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                null, this, AlertView.Style.Alert, this)
                .show();
    }

    /**
     * 打印交易信息
     */
    public class PrintCodeTask extends AsyncTask<Void, Void, Void> {
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
        public PrintCodeTask(Context context) {
            this.context = context;
            deviceSetting = context.getSharedPreferences("DeviceSetting", 0);
            bluetoothadd = deviceSetting.getString("BlueToothAdd", "");// 蓝牙MAC
            dialog = new ProgressDialog(context);
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
                int pCount = 2;
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
                    PrintUtils.printText("上海奉贤燃气股份有限公司" + "\n");//公司
                    PrintUtils.printText("--------------------------------\n");
//                    PrintUtils.printText(companyInfo.getString("Company","") + "\n");//公司
                    PrintUtils.printText("销售收据\n");
                    PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
//                    PrintUtils.selectCommand(PrintUtils.NORMAL);
//                    PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
                    PrintUtils.selectCommand(PrintUtils.NORMAL);
                    PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
                    PrintUtils.printText(PrintUtils.printTwoData("收据单", SaleID + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("送气号", UserID + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("供应站点", getBasicInfo.getStationName() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("姓名",userName + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("客户类型", userType + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("联系电话", phone + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("来电电话", callingTphone + "\n"));
//                    PrintUtils.printText(PrintUtils.printTwoData("联系电话", CustomerInfo.getTelphone() + "\n"));
                    PrintUtils.printText(address + "\n");//客户地址
                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.selectCommand(PrintUtils.BOLD);
                    PrintUtils.printText(PrintUtils.printThreeData("项目", "数量", "金额\n"));
                    int size = details.size();
                    for (int i = 0; i < size; i++) {

                        PrintUtils.printText(PrintUtils.printThreeData(details.get(i).getQPName(), ""+details.get(i).getSendNum(), details.get(i).getQPPrice() + "\n"));
                    }

                    PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.printText(PrintUtils.printTwoData("合计", AllPrice + "\n"));
                    String EmptyNo = "";
                    EmptyNo = sale.getReceiveQP();
                    if (!EmptyNo.equals("")) {
                        PrintUtils.printText("空瓶\n");
                        if (EmptyNo.contains(",")) {
                            String[] tmp = EmptyNo.split(",");
                            int length = tmp.length;
                            for (int i = 0; i < length; i++) {
                                PrintUtils.printText(PrintUtils.printTwoData("", tmp[i] + "\n"));
                            }
                        } else {
                            PrintUtils.printText(PrintUtils.printTwoData("", EmptyNo + "\n"));
                        }
                    }
                    String FullNo = "";
                    FullNo = sale.getSendQP();
                    if (!FullNo.equals("")) {
                        PrintUtils.printText("满瓶\n");
                        if (FullNo.contains(",")) {
                            String[] tmp = FullNo.split(",");
                            int length = tmp.length;
                            for (int i = 0; i < length; i++) {
                                PrintUtils.printText(PrintUtils.printTwoData("", tmp[i] + "\n"));
                            }
                        } else {
                            PrintUtils.printText(PrintUtils.printTwoData("", FullNo + "\n"));
                        }
                    }

                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.printText(PrintUtils.printTwoData("配送日期", sale.getFinishTime() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("送瓶员", getBasicInfo.getOperationName() + "\n"));
                    PrintUtils.printText("--------------------------------\n");//一般隐患
                    PrintUtils.printText("--------------------------------\n");//用户签名
                    PrintUtils.printText(PrintUtils.printTwoData("用户签名", "" + "\n"));
                    PrintUtils.printText("\n\n");//一般隐患
                    PrintUtils.printText("--------------------------------\n");//用户签名
//                    PrintUtils.printText(PrintUtils.printTwoData("应急投诉：67183737", "" + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("送气热线：67183737", "" + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("如需发票，一次收据换取", "" + "\n"));
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
    /***
     *交易信息详情
     */
    public class SaleCodeDialog {
        private Context context;
        private Dialog mGoodsDialog;//
        private LinearLayout root;
        private TextView info;
        private String msg;
        private Button btn_sure,btn_cansel;
        public SaleCodeDialog(final Context context, String msg){
            this.context=context;
            this.msg=msg;
            mGoodsDialog = new Dialog(context, R.style.my_dialog);
            root = (LinearLayout) LayoutInflater.from(context).inflate(
                    R.layout.sale_info_dialog_layout, null);
            info= root.findViewById(R.id.saleinto);
            info.setText("");
            btn_sure=root.findViewById(R.id.btn_print);
            btn_cansel= root.findViewById(R.id.btn_cansel);
            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dimiss();
                    new PrintCodeTask(context).execute();

                }
            });
            btn_cansel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGoodsDialog.dismiss();
                }
            });
            mGoodsDialog.setContentView(root);
            Window dialogWindow = mGoodsDialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
            WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = 450; // 新位置Y坐标
            lp.width = (int) context.getResources().getDisplayMetrics().widthPixels; // 宽度
            root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
            lp.alpha = 9f; // 透明度
            dialogWindow.setAttributes(lp);
            mGoodsDialog.setOnKeyListener(new DialogInterface.OnKeyListener(){

                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == KeyEvent.KEYCODE_BACK) {
                        mGoodsDialog.dismiss();
                    }
                    return false;
                }
            });
        }
        public  void shown(){
            info.setText(msg);
            mGoodsDialog.show();
        }
        public void dimiss(){
            mGoodsDialog.dismiss();
        }
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
}
