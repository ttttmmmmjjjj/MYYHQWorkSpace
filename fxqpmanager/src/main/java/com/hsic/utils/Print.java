package com.hsic.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2018/8/16.
 */

public class Print {
    private Context context = null;
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
    public Print(Context context){
        this.context=context;
        deviceSetting = context.getSharedPreferences("DeviceSetting", 0);
        bluetoothadd = deviceSetting.getString("BlueToothAdd", "");// 蓝牙MAC
    }
    /**
     * 事件响应方法
     */

    public String printData(byte[] data, boolean charactor) {
        try {
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
            return "0";
        } catch (IOException e) {
            System.out.println("输出流" + e);
            return "1";
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
    public void finish(){
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
    public String printData2(String Data, boolean charactor) {
        try {
            byte[] data = Data.getBytes("gbk");
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
            return "0";
        } catch (IOException e) {
            System.out.println("输出流" + e);
            return "1";
        }
    }
}
