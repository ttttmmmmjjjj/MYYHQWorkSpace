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
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hsic.adapter.SaleListAdapter;
import com.hsic.bean.Sale;
import com.hsic.bean.SaleDetail;
import com.hsic.bll.GetBasicInfo;
import com.hsic.db.DeliveryDB;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.utils.PrintUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

public class DeliveryFinishActivity extends AppCompatActivity {
    DeliveryDB deliveryDB;
    Sale sale;
    GetBasicInfo getBasicInfo;
    private String saleID;
    List<Map<String,String>> finish;
    List<SaleDetail> details;
    ListView listView;
    SaleListAdapter saleListAdapter;
    TextView txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_finish);
        txt_title=this.findViewById(R.id.txt_title);
        deliveryDB=new DeliveryDB(this);
        getBasicInfo=new GetBasicInfo(this);
        listView=this.findViewById(R.id.lv_saleInfo);
        txt_title.setText("订单补打");
        sale=new Sale();
        details=new ArrayList<>();
        finish=new ArrayList<Map<String, String>>() ;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(finish.size()>0){
                    Map<String, String> data =  (Map<String, String>)adapterView.getItemAtPosition(position);
                    String UserID=data.get("UserID");
                    String SaleID=data.get("SaleID");
                    saleID=SaleID;
                    sale=deliveryDB.GetPrint(getBasicInfo.getOperationID(),saleID);
                    details=deliveryDB.GetSaleDetailByP(getBasicInfo.getOperationID(),getBasicInfo.getStationID(),saleID);
                    msg=new StringBuilder();
                    msg.append(("---------------------------------------------------------------\n")) ;
                    msg.append(PrintUtils.printTwoData("供应站点", getBasicInfo.getStationName() + "\n"));
                    msg.append(PrintUtils.printTwoData("收据单", SaleID + "\n"));
                    msg.append(PrintUtils.printTwoData("送气号", UserID + "\n"));
                    msg.append(PrintUtils.printTwoData("姓名", sale.getUserName() + "\n"));
                    msg.append(PrintUtils.printTwoData("客户类型", sale.getRemark() + "\n"));
                    msg.append(PrintUtils.printTwoData("联系电话", sale.getTelphone()+ "\n"));
                    msg.append("上海奉贤燃气股份有限公司" + "\n");//公司
                    msg.append(("---------------------------------------------------------------\n")) ;
                    msg.append("回收瓶：" + "\n");
                    msg.append(sale.getReceiveQP() + "\n");
                    msg.append("配送瓶：" + "\n");
                    msg.append(sale.getSendQP() + "\n");
                    msg.append(("---------------------------------------------------------------\n")) ;
                    int size=details.size();
                    for (int i = 0; i < size; i++) {
                        msg.append(PrintUtils.printThreeData(details.get(i).getQPName(), ""+details.get(i).getSendNum(), details.get(i).getQPPrice() + "\n"));
                    }
                    msg.append(PrintUtils.printTwoData("交易金额",sale.getRealPirce()+"\n"));
                    DeliveryFinishActivity.SaleCodeDialog t = new DeliveryFinishActivity.SaleCodeDialog(DeliveryFinishActivity.this, msg.toString());
                    t.shown();
                }else{

                }
//                listview.setSelectionFromTop(scrolledX, scrolledY);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            /**
             * 滚动状态改变时调用
             */

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    try {
                        scrolledX = view.getFirstVisiblePosition();
                        Log.i("scroll X", String.valueOf(scrolledX));
                        scrolledY = view.getChildAt(0).getTop();
                        Log.i("scroll Y", String.valueOf(scrolledY));
                    } catch (Exception e) {
                    }
                }
            }

        });

        finish=deliveryDB.GetSaleInfoFinish(getBasicInfo.getOperationID(),getBasicInfo.getStationID());
        saleListAdapter=new SaleListAdapter(this,finish);
        listView.setAdapter(saleListAdapter);
    }
    StringBuilder msg = new StringBuilder();
    private int scrolledX = 0;
    private int scrolledY = 0;
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
                    PrintUtils.printText(PrintUtils.printTwoData("收据单", sale.getSaleID() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("送气号", sale.getCustomerID() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("供应站点", getBasicInfo.getStationName() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("姓名",sale.getUserName() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("客户类型", sale.getRemark() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("联系电话", sale.getTelphone() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("来电电话", sale.getPS() + "\n"));
//                    PrintUtils.printText(PrintUtils.printTwoData("联系电话", CustomerInfo.getTelphone() + "\n"));
                    PrintUtils.printText(sale.getSaleAddress() + "\n");//客户地址
                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.selectCommand(PrintUtils.BOLD);
                    PrintUtils.printText(PrintUtils.printThreeData("项目", "数量", "金额\n"));
                    int size = details.size();
                    for (int i = 0; i < size; i++) {

                        PrintUtils.printText(PrintUtils.printThreeData(details.get(i).getQPName(), ""+details.get(i).getSendNum(), details.get(i).getQPPrice() + "\n"));
                    }

                    PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.printText(PrintUtils.printTwoData("合计", sale.getRealPirce() + "\n"));
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
