package com.hsic.sy.doorsale.task;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.hsic.sy.bll.PrintUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2018/8/15.
 */

public class PrintTask extends AsyncTask<Void, Void, String> {
    private Context context = null;
    private String msg;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
    private boolean isConnection = false;
    private BluetoothDevice device = null;
    private static BluetoothSocket bluetoothSocket = null;
    private static OutputStream outputStream;
    private static final UUID uuid = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    SharedPreferences deviceSetting;
    String bluetoothadd = "";// 蓝牙MAC

    private ProgressDialog dialog;

    public PrintTask(Context context, String msg) {
        this.context = context;
        this.msg = msg;
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
    protected String doInBackground(Void... voids) {
        String s = "";
        try {
            int pCount = 0;
            pCount = Integer.parseInt("1");
            //测试(最新测试)
            String Intret = connectBT();
            if (Intret.equals("0")) {
            } else {
                return "-1";
            }
//            printMsg(msg, false);
            PrintUtils.setOutputStream(outputStream);
            PrintUtils.selectCommand(PrintUtils.RESET);
            PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
            PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
            PrintUtils.printText("美食餐厅\n\n");
            PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
            PrintUtils.printText("桌号：1号桌\n\n");
            PrintUtils.selectCommand(PrintUtils.NORMAL);
            PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
            PrintUtils.printText(PrintUtils.printTwoData("订单编号", "201507161515\n"));
            PrintUtils.printText(PrintUtils.printTwoData("点菜时间", "2016-02-16 10:46\n"));
            PrintUtils.printText(PrintUtils.printTwoData("上菜时间", "2016-02-16 11:46\n"));
            PrintUtils.printText(PrintUtils.printTwoData("人数：2人", "收银员：张三\n"));

            PrintUtils.printText("--------------------------------\n");
            PrintUtils.selectCommand(PrintUtils.BOLD);
            PrintUtils.printText(PrintUtils.printThreeData("项目", "数量", "金额\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
            PrintUtils.printText(PrintUtils.printThreeData("面", "1", "0.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("米饭", "1", "6.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("铁板烧", "1", "26.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("一个测试", "1", "226.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("牛肉面啊啊", "1", "2226.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("牛肉面啊啊啊牛肉面啊啊啊", "888", "98886.00\n"));

            PrintUtils.printText("--------------------------------\n");
            PrintUtils.printText(PrintUtils.printTwoData("合计", "53.50\n"));
            PrintUtils.printText(PrintUtils.printTwoData("抹零", "3.50\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.printText(PrintUtils.printTwoData("应收", "50.00\n"));
            PrintUtils.printText("--------------------------------\n");

            PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
            PrintUtils.printText("备注：不要辣、不要香菜");
            PrintUtils.printText("\n\n\n\n\n");
        } catch (Exception ex) {
            ex.toString();
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.setCancelable(true);
        dialog.dismiss();
        finish();
    }

    private void printMsg(String Data, boolean charactor) {
        try {
            String msg = "订单条形码\n" + Data+"\n\n";
            byte[] data = msg.getBytes("gbk");
            if (charactor) {
                outputStream.write(0x1c);
                outputStream.write(0x21);
                outputStream.write(4);
            } else {
                outputStream.write(0x1c);
                outputStream.write(0x21);
                outputStream.write(2);
            }
            outputStream.write(data, 0, data.length);
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

    private void finish() {
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
