package com.hsic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.bean.ApplicationHelper;
import com.hsic.bean.CustomerInfo;
import com.hsic.bean.HsicMessage;
import com.hsic.bean.Sale;
import com.hsic.bean.SaleAll;
import com.hsic.bean.SaleDetail;
import com.hsic.bean.UserXJInfo;
import com.hsic.bll.GetBasicInfo;
import com.hsic.bll.PrintUtils;
import com.hsic.bll.TimeUtils;
import com.hsic.bll.ViewUtil;
import com.hsic.listener.ImplPrint;
import com.hsic.listener.ImplUpUserInspection;
import com.hsic.listener.ImplUpdateSaleInfo;
import com.hsic.picture.UpLoadPIC;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.dialoglibrary.OnItemClickListener;
import com.hsic.task.UpUserInspectionTask;
import com.hsic.task.UpdateSaleInfoTask;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity implements View.OnClickListener, OnDismissListener, OnItemClickListener,
        ImplUpdateSaleInfo, ImplUpUserInspection {
    TextView userinfo, present, lastgp, nextgp, blow, userinfo1, cook_txt;
    Button print, readTag, submit, security, presentButton, checkLook,
            checknextLook;
    private RelativeLayout rl;
    ApplicationHelper applicationHelper;
    String DM;
    UserXJInfo userXJInfo;
    private String stopYY;//停止交易标识
    public com.hsic.bean.Sale Sale;//
    public List<com.hsic.bean.SaleDetail> SaleDetail;
    public List<com.hsic.bean.SaleDetail> SaleDetail_LIST;
    public com.hsic.bean.CustomerInfo CustomerInfo;//
    String isNew;
    SaleAll SaleAll;
    GetBasicInfo getBasicInfo;
    private SharedPreferences companyInfo;
    private String GoodsCount;
    private String[] QPType = {"满瓶", "空瓶"};;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        innitView();
        getBasicInfo = new GetBasicInfo(DeliveryActivity.this);
        applicationHelper = (ApplicationHelper) getApplication();
        userXJInfo = new UserXJInfo();
        applicationHelper.setUserXJInfo(userXJInfo);
        IsSunmitSale = new AlertView("提示", "确认提交订单", "取消", new String[]{"确定"},
                null, this, AlertView.Style.Alert, this);
        Intent intent = getIntent();
        SaleAll = (SaleAll) intent.getSerializableExtra("Sale");
        Sale = new Sale();
        Sale = SaleAll.getSale();
        Log.e("Sale",JSONUtils.toJsonWithGson(Sale));
        SaleDetail= new ArrayList<SaleDetail>();
        SaleDetail = SaleAll.getSaleDetail();
        SaleDetail_LIST=new ArrayList<SaleDetail>();
        CustomerInfo = new CustomerInfo();
        CustomerInfo = SaleAll.getCustomerInfo();
//        Log.e("CustomerInfo",JSONUtils.toJsonWithGson(CustomerInfo));
        GoodsCount = "";
        setData();
        companyInfo = getSharedPreferences("CompanyInfo", 0);// 公司选择器
    }

    private void innitView() {
        blow = (TextView) this.findViewById(R.id.blow);
        rl = (RelativeLayout) this.findViewById(R.id.above);
        userinfo = (TextView) this.findViewById(R.id.txt_userinfo);
        userinfo1 = (TextView) this.findViewById(R.id.txt_userinfo1);
        present = (TextView) this.findViewById(R.id.present);
        present.setVisibility(View.GONE);
        lastgp = (TextView) this.findViewById(R.id.txt_gpinfo2);
        nextgp = (TextView) this.findViewById(R.id.txt_gpinfo4);
        presentButton = (Button) this.findViewById(R.id.presentbotton);
        presentButton.setVisibility(View.GONE);
        print = (Button) this.findViewById(R.id.button6);
        readTag = (Button) this.findViewById(R.id.btn_qpr);
        checkLook = (Button) this.findViewById(R.id.checklook);
//        checkLook.setVisibility(View.GONE);
        checknextLook = (Button) this.findViewById(R.id.checklook1);
//        checknextLook.setVisibility(View.GONE);
        submit = (Button) this.findViewById(R.id.btn_qps);
        security = (Button) findViewById(R.id.button4);
        presentButton.setOnClickListener(this);
        print.setOnClickListener(this);
        print.setEnabled(false);
        readTag.setOnClickListener(this);
        checkLook.setOnClickListener(this);
        checknextLook.setOnClickListener(this);
        submit.setOnClickListener(this);
        security.setOnClickListener(this);
        security.setEnabled(true);
        readTag.setEnabled(true);
        submit.setEnabled(true);// 提交按钮
//        print.setEnabled(false);
        userinfo1.setText("");

    }

    private void setData() {
        userinfo.append("送气号：" + Sale.getCustomerID() + "\n");
        userinfo.append("订单号：" + Sale.getSaleID());
        String newUser = "";
        isNew = CustomerInfo.getIsNew();
        if (isNew != null && isNew.equals("0")) {
            userinfo1.append("姓名：" + CustomerInfo.getCustomerName() + newUser + " "
                    + CustomerInfo.getCustomerTypeName() + "\n");
            ViewUtil.setWordsColor(userinfo1, newUser,
                    CustomerInfo.getCustomerTypeName());// 设置属性

        } else {
            userinfo1.append("姓名：" + CustomerInfo.getCustomerName() + " " + CustomerInfo.getCustomerTypeName()
                    + "\n");
            ForegroundColorSpan blueSpan = new ForegroundColorSpan(
                    Color.BLUE);
            ViewUtil.setTextViewColor(blueSpan, userinfo1,
                    CustomerInfo.getCustomerTypeName());// 设置属性
        }
        userinfo1.append("地址：" + Sale.getSaleAddress() + "\n");
        userinfo1.append("来电电话：" + Sale.getTelphone() + "\n");
        userinfo1.append("联系电话：" + CustomerInfo.getTelphone() + "\n");
        /**
         * 商品详情类型
         */
        int size = SaleDetail.size();
//        QPType = new String[size];//气瓶类型数组
        StringBuffer goodsInfo = new StringBuffer();
        int count = 0;
        for (int j = 0; j < size; j++) {
//            QPType[j] = SaleDetail.get(j).getQPName();
            String tmp = SaleDetail.get(j).getPlanSendNum();
            goodsInfo.append(SaleDetail.get(j).getQPName() + "/" + SaleDetail.get(j).getPlanSendNum() + "，");
            count = count + Integer.parseInt(tmp);
        }
//        /**
//         * 去除气瓶类型数组重复值
//         */
//        List<String> list = new ArrayList<String>();
//        for (int i = 0; i < QPType.length; i++) {
//            if (!list.contains(QPType[i])) {
//                list.add(QPType[i]);
//            }
//        }
//        QPType = list.toArray(new String[list.size()]);
        GoodsCount = String.valueOf(count);
        String tmp = goodsInfo.toString();
        tmp = tmp.substring(0, tmp.length() - 1);
        userinfo1.append("商品：" + tmp + "\n");
        userinfo1.append("交易金额：" + Sale.getPlanPirce() + "\n");
    }

    private AlertDialog alertDialog1; //信息框

    /**
     * 选择扫描气瓶类型
     */
    public void showList() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("请选择扫描气瓶类型");
        alertBuilder.setItems(QPType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(DeliveryActivity.this, QPType[i], Toast.LENGTH_SHORT).show();
                if(QPType[i].equals("满瓶")){
                    String FullNO = "";
                    FullNO = nextgp.getText().toString();
                    Intent f = new Intent(DeliveryActivity.this, ScanQPTagActivity.class);
                    f.putExtra("Operation", "F");
                    f.putExtra("QPNO", FullNO);
//                    f.putExtra("QPType", ScanQPType);
//                    f.putExtra("QPName", ScanQPName);
                    f.putExtra("QPCount",GoodsCount);
//                    f.putExtra("SaleDetail",(Serializable)SaleDetail_FLIST);
                    startActivityForResult(f, 1);
                };
                if(QPType[i].equals("空瓶")){
                    String EmptyNO = "";
                    EmptyNO = lastgp.getText().toString();
                    Intent e = new Intent(DeliveryActivity.this, ScanQPTagActivity.class);
                    e.putExtra("Operation", "E");
                    e.putExtra("QPNO", EmptyNO);
//                    e.putExtra("QPType", ScanQPType);
//                    e.putExtra("QPName", ScanQPName);
                    e.putExtra("QPCount",GoodsCount);
                    startActivityForResult(e, 2);
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }
    AlertView  IsSunmitSale;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.presentbotton:
//                presentButton();
                break;
            case R.id.button6:
                print();
                break;
            case R.id.btn_qpr:
//                if (QPType.length == 1) {
//                    ScanQPName=QPType[0];
//                    ScanQPType = getQPType(QPType[0]);
//                    showSingleAlertDialog();
//                } else {
//                    showList();
//                }
                showList();
//                readtag();
                break;
            case R.id.checklook:
                String EmptyNO = "";
                EmptyNO = lastgp.getText().toString();
                Intent e = new Intent(this, ScanQPTagActivity.class);
                e.putExtra("Operation", "E");
                e.putExtra("QPNO", EmptyNO);
                e.putExtra("QPCount",GoodsCount);
                startActivityForResult(e, 2);
                break;
            case R.id.checklook1:
                String FullNO = "";
                FullNO = nextgp.getText().toString();
                Intent f = new Intent(this, ScanQPTagActivity.class);
                f.putExtra("Operation", "F");
                f.putExtra("QPNO", FullNO);
                f.putExtra("QPCount",GoodsCount);
                startActivityForResult(f, 1);
                break;
            case R.id.btn_qps:
                submit();
                break;
            case R.id.button4:
                security();
                break;

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //满瓶
            Bundle b = data.getExtras(); //data为B中回传的Intent
            String userRegionCode = b.getString("userReginCode");
            nextgp.setText(userRegionCode);
        }
        if (requestCode == 2) {
            //空瓶
            Bundle b = data.getExtras(); //data为B中回传的Intent
            String userRegionCode = b.getString("userReginCode");
            lastgp.setText(userRegionCode);
        }
        if (requestCode == 7) {
            //提交订单之前进行拍照
            if (resultCode == Activity.RESULT_OK) {
//                getImagePath(saleid, userid);
            }

        }
        if (resultCode == 0) {
//            String stop = "";
//            stop = applicationHelper.getUserXJInfo().getStopYY();
//            if (stop != null && !stop.equals("")) {
//                // 20170510 交易继续
//                stopYY = ViewUtil.reflashUiStop(stop, blow, rl);
////                telSaleInfo.setQxjyyy(stopYY);
//                /**
//                 * 20180123 强制初始化
//                 */
//                lastgp.setText("");
//                nextgp.setText("");
////                telSaleInfo.setReceiveBottleByHand("");// 用之前清理
////                telSaleInfo.setSendBottle("");// 满瓶
////                telSaleInfo.setReceiveBottle("");// 空瓶
//            } else {
//                blow.setVisibility(View.GONE);
//                rl.setVisibility(View.VISIBLE);
//                blow.setText("");
//            }
        }
        if (resultCode == 5) {
            // 手输还瓶
//            CheckTagCounts(data, "bottleInfo", getApplicationContext());
            String tempL = "", tempN = "", temp1 = "", temp2 = "";
            tempL = lastgp.getText().toString();//上次归还瓶
            tempN = nextgp.getText().toString();//本次交付瓶
//            temp1 = applicationHelper.getSmE();
//            temp2 = applicationHelper.getSmF();
            String[] n = temp1.split(",");
            String[] m = temp2.split(",");
            if (tempL.length() > 1) {
                for (int i = 0; i < n.length; i++) {
                    if (!tempL.contains(n[i])) {
                        lastgp.append("," + n[i]);
                    }
                }

            } else {
                lastgp.append(temp1);
            }
            if (tempN.length() > 1) {
                for (int i = 0; i < m.length; i++) {
                    if (!tempN.contains(m[i])) {
                        nextgp.append("," + m[i]);
                    }
                }

            } else {
                nextgp.append(temp2);
            }
        }

    }

    /**
     * 跳转到安全巡检页面
     */
    public void security() {
        Intent intent = new Intent(DeliveryActivity.this,
                SearchActivity.class);
        userXJInfo=new UserXJInfo();
        userXJInfo.setSaleid(Sale.getSaleID());
        userXJInfo.setUserid(Sale.getCustomerID());
        applicationHelper.setUserXJInfo(userXJInfo);
        startActivityForResult(intent, 6);
    }

    @Override
    public void onDismiss(Object o) {
        if (o == IsSunmitSale) {
            IsSunmitSale.dismiss();
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        try {
            if (o == IsSunmitSale) {
                IsSunmitSale.dismiss();
                //上传销售信息
                HsicMessage mHsicMessage = new HsicMessage();// 创建通信类
                String request = JSONUtils.toJsonWithGson(SaleAll);// 将信息转换成json
                mHsicMessage.setRespMsg(request);// 给通信类设置信息
                String requestData = JSONUtils.toJsonWithGson(mHsicMessage);// 将通信类转换成json
                UpdateSaleInfoTask task = new UpdateSaleInfoTask(DeliveryActivity.this, DeliveryActivity.this);
                task.execute(requestData);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 打印交易信息
     */
    private void print() {
        //校验瓶子数量
        String FullNo = "";
        FullNo = nextgp.getText().toString();
        if (!TextUtils.isEmpty(FullNo)) {
            int count = 0;
            if (FullNo.contains(",")) {
                String[] tmp = FullNo.split(",");
                count = tmp.length;
            } else {
                count = 1;
            }
            //判断满瓶的数量

        } else {
            Toast.makeText(this, "瓶数量不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //非新用户 空瓶也判断数量
        String EmptyNo = "";
        if (isNew != null && !isNew.equals("0")) {
            EmptyNo = lastgp.getText().toString();
            if (!TextUtils.isEmpty(EmptyNo)) {
                int count = 0;
                if (EmptyNo.contains(",")) {
                    String[] tmp = EmptyNo.split(",");
                    count = tmp.length;
                } else {
                    count = 1;
                }
                //判断空瓶的数量
            } else {
                Toast.makeText(this, "空瓶数量不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // //显示订单信息并且打印成功
        StringBuilder msg = new StringBuilder();
        msg.append(PrintUtils.printTwoData("收据单", Sale.getSaleID() + "\n"));
        msg.append(PrintUtils.printTwoData("送气号", Sale.getCustomerID() + "\n"));
        msg.append(companyInfo.getString("Company", "") + "\n");//公司
        if(Sale.getStation()!=null){
            msg.append(PrintUtils.printTwoData("供应站点", Sale.getStation() + "\n"));
        }else{
            msg.append(PrintUtils.printTwoData("供应站点", Sale.getStationName() + "\n"));
        }

        msg.append(PrintUtils.printTwoData("姓名", CustomerInfo.getCustomerName() + "\n"));
        msg.append(PrintUtils.printTwoData("客户类型", CustomerInfo.getCustomerTypeName() + "\n"));
        msg.append(PrintUtils.printTwoData("联系电话", Sale.getTelphone() + "\n"));
        msg.append(Sale.getSaleAddress() + "\n");//客户地址
//        msg.append("--------------------------------\n");
        msg.append(PrintUtils.printThreeData("项目", "数量", "金额\n"));
        int size = SaleDetail.size();
        for (int i = 0; i < size; i++) {
            msg.append(PrintUtils.printThreeData(SaleDetail.get(i).getQPName(), SaleDetail.get(i).getPlanSendNum(), SaleDetail.get(i).getRealQTPrice() + "\n"));
            msg.append(PrintUtils.printThreeData("运费", SaleDetail.get(i).getPlanSendNum(), SaleDetail.get(i).getQPPrice() + "\n"));
        }
//        msg.append("--------------------------------\n");
        msg.append(PrintUtils.printTwoData("合计", Sale.getPlanPirce() + "\n"));

        EmptyNo = lastgp.getText().toString();
        if (!EmptyNo.equals("")) {
            msg.append("空瓶\n");
            if (EmptyNo.contains(",")) {
                String[] tmp = EmptyNo.split(",");
                int length = tmp.length;
                for (int i = 0; i < length; i++) {
                    msg.append(PrintUtils.printTwoData("", tmp[i] + "\n"));
                }
            } else {
                msg.append(PrintUtils.printTwoData("", EmptyNo + "\n"));
            }
        }
        FullNo = nextgp.getText().toString();
        if (!FullNo.equals("")) {
            msg.append("满瓶\n");
            if (FullNo.contains(",")) {
                String[] tmp = FullNo.split(",");
                int length = tmp.length;
                for (int i = 0; i < length; i++) {
                    msg.append(PrintUtils.printTwoData("", tmp[i] + "\n"));
                }
            } else {
                msg.append(PrintUtils.printTwoData("", FullNo + "\n"));
            }
        }
//        if(Sale.getEmployeeID()!=null){
//            msg.append(PrintUtils.printTwoData("操作员", Sale.getEmployeeID() + "\n"));
//        }else{
//            msg.append(PrintUtils.printTwoData("操作员", "" + "\n"));
//        }
        msg.append(PrintUtils.printTwoData("车牌号", applicationHelper.getStaffID() + "\n"));
        msg.append(PrintUtils.printTwoData("日期", Sale.getFinishTime() + "\n"));
//        if(Sale.getEmployeeID()!=null){
//            msg.append(PrintUtils.printTwoData("送瓶员", Sale.getEmployeeID() + "\n"));
//        }else{
//            msg.append(PrintUtils.printTwoData("送瓶员", "" + "\n"));
//        }

        SaleCodeDialog t = new SaleCodeDialog(DeliveryActivity.this, msg.toString());
        t.shown();
    }

    //提交交易数据
    private void submit() {
        //校验瓶子数量
        String FullNo = "";
        FullNo = nextgp.getText().toString();
        if (!TextUtils.isEmpty(FullNo)) {
            int count = 0;
            if (FullNo.contains(",")) {
                String[] tmp = FullNo.split(",");
                count = tmp.length;
            } else {
                count = 1;
            }
            //校验满瓶数量
            int goodsCount = 0;
            goodsCount = Integer.parseInt(GoodsCount);
            if (count != goodsCount) {
                Toast.makeText(this, "请核对商品数量", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "瓶数量不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //非新用户 空瓶也判断数量
        String EmptyNo = "";
//        if (isNew != null && !isNew.equals("0")) {
            EmptyNo = lastgp.getText().toString();
            if (!TextUtils.isEmpty(EmptyNo)) {
                int count = 0;
                if (EmptyNo.contains(",")) {
                    String[] tmp = EmptyNo.split(",");
                    count = tmp.length;
                } else {
                    count = 1;
                }
                //判断空瓶的数量
                int goodsCount = 0;
                goodsCount = Integer.parseInt(GoodsCount);
                if (count != goodsCount) {
                    Toast.makeText(this, "请核对商品数量", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(this, "空瓶数量不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
//        }

        String InspectionStatus = applicationHelper.getUserXJInfo().getInspectionStatus();
        if (InspectionStatus == null) {
            Toast.makeText(this, "必须巡检", Toast.LENGTH_SHORT).show();
            return;
        }
        Sale.setFinishTime(TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));
        SortSaleDetail();
        Sale.setReceiveQP(EmptyNo);
        Sale.setSendQP(FullNo);
        SaleAll.setSaleDetail(SaleDetail_LIST);
        SaleAll.setSale(Sale);
//        applicationHelper.setSaleAll(SaleAll);
//        applicationHelper.setSale(Sale);
        IsSunmitSale.show();
    }

    /**
     * 销单成功
     *
     * @param tag
     */
    @Override
    public void UpdateSaleInfoTaskEnd(HsicMessage tag) {
        if (tag.getRespCode() == 0) {
            //上传成功
            if(IsSunmitSale!=null){
                IsSunmitSale.dismiss();
            }

            Toast.makeText(this, "订单信息上传成功", Toast.LENGTH_SHORT).show();
            userXJInfo = applicationHelper.getUserXJInfo();
            String data = JSONUtils.toJsonWithGson(userXJInfo);//
            HsicMessage hm = new HsicMessage();
            hm.setRespMsg(data);
            String requestData = JSONUtils.toJsonWithGson(hm);
            UpUserInspectionTask task = new UpUserInspectionTask(DeliveryActivity.this, DeliveryActivity.this);
            task.execute(requestData);
        } else {
            Toast.makeText(this, tag.getRespMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 巡检信息上传
     *
     * @param tag
     */
    @Override
    public void UpUserInspectionTaskEnd(HsicMessage tag) {
        if (tag.getRespCode() == 0) {
            submit.setEnabled(false);
            print.setEnabled(true);
            readTag.setEnabled(false);
            checkLook.setEnabled(false);
            checknextLook.setEnabled(false);
            security.setEnabled(false);
            Toast.makeText(this, "巡检信息上传成功", Toast.LENGTH_SHORT).show();
            new UploadPIC(DeliveryActivity.this).execute();
        } else {
            //上传巡检信息
            Toast.makeText(this, tag.getRespMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (IsSunmitSale != null && IsSunmitSale.isShowing()) {
                IsSunmitSale.dismiss();
                return false;
            }
            new AlertDialog.Builder(this)
                    .setTitle("确定退出程序么")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })

                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent();
                            intent.putExtra("result", 3);
                            setResult(8, intent);
                            finish();//

                        }

                    }).show();

        }
        return super.onKeyDown(keyCode, event);

    }

    /***
     * 整理交易detail表详情
     */
    private void SortSaleDetail() {
        SaleDetail = SaleAll.getSaleDetail();
        int size = SaleDetail.size();
        SaleDetail_LIST=new ArrayList<SaleDetail>();
        for (int i = 0; i < size; i++) {
            SaleDetail saleDetail=new SaleDetail();
            saleDetail.setSendNum(SaleDetail.get(i).getPlanSendNum());
            saleDetail.setQPType(SaleDetail.get(i).getQPType());
            saleDetail.setQPName(SaleDetail.get(i).getQPName());
//            SaleDetail_LIST.add(saleDetail);
//            saleDetail=new SaleDetail();
            saleDetail.setReceiveNum(SaleDetail.get(i).getPlanSendNum());
//            saleDetail.setQPType(SaleDetail.get(i).getQPType());
//            saleDetail.setQPName(SaleDetail.get(i).getQPName());
            SaleDetail_LIST.add(saleDetail);
        }
    }
    /***
     *交易信息详情
     */
    public class SaleCodeDialog {
        private Context context;
        private Dialog mGoodsDialog,SaleInfoDialog;//
        private LinearLayout root;
        private TextView info;
        private String msg;
        private Button btn_sure,btn_cansel;
        private byte[] saleID;
        ImplPrint l;
        List<com.hsic.bean.SaleDetail> SaleDetail;
        Sale sale;
        UserXJInfo userXJInfo;
        CustomerInfo customerInfo;
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
                    String msg=info.getText().toString();
//                mGoodsDialog.dismiss();
                    new PrintCodeTask(context,msg).execute();

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
     *打印
     */
    public class PrintCodeTask extends AsyncTask<Void, Void, Void> {
        private byte[] saleID;
        private Context context = null;
        private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        private boolean isConnection = false;
        private BluetoothDevice device = null;
        private  BluetoothSocket bluetoothSocket = null;
        private OutputStream outputStream;
        private  final UUID uuid = UUID
                .fromString("00001101-0000-1000-8000-00805F9B34FB");
        SharedPreferences deviceSetting;
        String bluetoothadd = "";// 蓝牙MAC
        private ProgressDialog dialog;
        GetBasicInfo basicInfo;
        ImplPrint l;
        public PrintCodeTask(Context context,String msg) {
            this.context = context;
            deviceSetting = context.getSharedPreferences("DeviceSetting", 0);
            bluetoothadd = deviceSetting.getString("BlueToothAdd", "");// 蓝牙MAC
            dialog = new ProgressDialog(context);
            basicInfo = new GetBasicInfo(context);


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
                int pCount = 3;
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
                    PrintUtils.printText("\n");
                    PrintUtils.printText(companyInfo.getString("Company","") + "\n");//公司
                    PrintUtils.printText("销售收据\n");
                    PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
//                    PrintUtils.selectCommand(PrintUtils.NORMAL);
//                    PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
                    PrintUtils.selectCommand(PrintUtils.NORMAL);
                    PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
                    PrintUtils.printText(PrintUtils.printTwoData("收据单", Sale.getSaleID() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("送气号", Sale.getCustomerID() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("供应站点", Sale.getStationName() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("姓名", CustomerInfo.getCustomerName() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("客户类型", CustomerInfo.getCustomerTypeName() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("来电电话", Sale.getTelphone() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("联系电话", CustomerInfo.getTelphone() + "\n"));
                    PrintUtils.printText(Sale.getSaleAddress() + "\n");//客户地址
                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.selectCommand(PrintUtils.BOLD);
                    PrintUtils.printText(PrintUtils.printThreeData("项目", "数量", "金额\n"));
                    int size = SaleDetail.size();

                    for (int i = 0; i < size; i++) {

                        PrintUtils.printText(PrintUtils.printThreeData(SaleDetail.get(i).getQPName(), SaleDetail.get(i).getPlanSendNum(), SaleDetail.get(i).getRealQTPrice() + "\n"));
                        PrintUtils.printText(PrintUtils.printThreeData("运费", SaleDetail.get(i).getPlanSendNum(), SaleDetail.get(i).getQPPrice() + "\n"));
                    }

                    PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.printText(PrintUtils.printTwoData("合计", Sale.getPlanPirce() + "\n"));
                    String EmptyNo = "";
                    EmptyNo = Sale.getReceiveQP();
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
                    FullNo = Sale.getSendQP();
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
//
                    PrintUtils.printText(PrintUtils.printTwoData("车牌号", applicationHelper.getStaffID() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("日期", TimeUtils.getTime("yyyy-MM-dd HH:mm:ss") + "\n"));
//                    PrintUtils.printText(PrintUtils.printTwoData("送瓶员", "" + "\n"));
                    PrintUtils.printText("--------------------------------\n");//一般隐患
                    String InspectionStatus = "";
                    userXJInfo=applicationHelper.getUserXJInfo();
                    InspectionStatus = (userXJInfo.getInspectionStatus() == null) ? "" : userXJInfo.getInspectionStatus();
                    if (!InspectionStatus.equals("")) {
                        if (InspectionStatus.equals("0")) {
                            PrintUtils.printText("钢瓶已检查接装到位,可正常使用\n");//一般隐患
                        } else if (InspectionStatus.equals("1")) {
                            PrintUtils.printText("一般隐患内容\n");//一般隐患
                            PrintUtils.printText(userXJInfo.getPrintInfo() + "\n");//一般隐患
                            PrintUtils.printText("安检员签字\n");//一般隐患
                        } else {
                            PrintUtils.printText("严重隐患内容\n");//一般隐患
                            PrintUtils.printText(userXJInfo.getPrintInfo() + "\n");//一般隐患
                            PrintUtils.printText("\n\n");//一般隐患
                            PrintUtils.printText("安检员签字\n");//一般隐患
                            PrintUtils.printText("\n\n");//一般隐患
                        }
                    }
                    PrintUtils.printText("--------------------------------\n");//用户签名
                    PrintUtils.printText(PrintUtils.printTwoData("用户签名", "" + "\n"));
                    PrintUtils.printText("\n\n");//一般隐患
                    PrintUtils.printText("--------------------------------\n");//用户签名
                    PrintUtils.printText(PrintUtils.printTwoData("应急投诉：962777", "" + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("送气热线：962777", "" + "\n"));
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
        public void printCode(byte[] saleID, boolean charactor) {
            try {
                outputStream.write(saleID, 0, saleID.length);
                outputStream.flush();
            } catch (Exception ex) {

            }
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

    /**
     * 上传照片
     */
    class UploadPIC extends AsyncTask<Void, Void, Void>{
        private ProgressDialog dialog;
        private Context context = null;
        public UploadPIC(Context context) {
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
            upPIC.upPicture(DeliveryActivity.this, getBasicInfo.getDeviceID());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.setCancelable(true);
            dialog.dismiss();
        }
    }
}
