package com.hsic.fxqpmanager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class SetBlueToothActivity extends AppCompatActivity {
    private Button print, list;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private BluetoothDevice device = null;
    private ListView lv;
    private static BluetoothSocket bluetoothSocket = null;
    private static OutputStream outputStream;
    private static    final UUID uuid = UUID
            .fromString("000001101-0000-1000-8000-00805F9B34FB");
    private boolean isConnection = false;
    SharedPreferences mSharedPreferences;
    String add="";
    String imagePath="";
    boolean conn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_blue_tooth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting);
        toolbar.setTitle("已配对蓝牙打印机");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        list = (Button) findViewById(R.id.search);
        print = (Button) findViewById(R.id.print);
        lv = (ListView) findViewById(R.id.unbondDevices);
        BA = BluetoothAdapter.getDefaultAdapter();
        lv.setOnItemClickListener(new ItemClickEvent());
        mSharedPreferences = getSharedPreferences("DeviceSetting", 0);
        list(lv);
        add = mSharedPreferences.getString("BlueToothAdd", "");
        if(add != null && !add.equals("")){
            Toast.makeText(getApplicationContext(), "正在连接蓝牙",
                    Toast.LENGTH_SHORT).show();
            conn = connectBT(add);
            Toast.makeText(getApplicationContext(), "蓝牙连接已打开",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void list(View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, getData());
        lv.setAdapter(adapter);

    }

    public void print(View view) {
        boolean ret = false;
        try{
            add = mSharedPreferences.getString("BlueToothAdd", "");
            if (add != null && !add.equals("")) {
                boolean temp = connectBT(add);
                if (conn) {
                    ret = printData(add);
                    if (ret) {

                    } else {
                        Toast.makeText(getApplicationContext(), "蓝牙打印机通讯有问题",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                }
            }else{
                ret = printData(add);
                if (ret) {

                } else {
                    Toast.makeText(getApplicationContext(), "蓝牙打印机通讯有问题",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception ex){
            ex.toString();
        }


    }



    private ArrayList<String> getData() {
        pairedDevices = BA.getBondedDevices();
        ArrayList<String> list1 = new ArrayList<String>();
        for (BluetoothDevice bt : pairedDevices) {
            String temp=bt.getName() + "-" + bt.getAddress();
            if(list1.size()>0){
                for(int i=0;i<list1.size();i++){
                    if(list1.get(i).contains(temp)){
                        continue;
                    }else{
                        list1.add(temp);
                    }
                }
            }else{
                list1.add(temp);
            }


        }
        return list1;
    }

    private final class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
            String text = lv.getItemAtPosition(arg2).toString();
            String[] add = text.split("-");
            int length=add.length;
            String temp = add[length-1];

            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.putString("BlueToothAdd", temp);
            mEditor.commit();
            // Toast.makeText(getApplicationContext(), temp, 1).show();
        }
    }

    /**
     * 打印
     *
     * @return
     */
    public boolean printData(String add) {
        boolean ret = false;
        String Data = add + "\n\n\n";
        try {
            byte[] data = Data.getBytes("gbk");
//			//20160927
//			imagePath=Environment.getExternalStorageDirectory() + "/handwriting/1474536666901.jpg";
//			byte[] image=imageToByteArray(imagePath);
//			if(image!=null){
//				outputStream.write(image, 0, image.length);
//			}

            outputStream.write(data, 0, data.length);
            isConnection=false;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 连接蓝牙
     *
     * @param bluetoothadd
     * @return
     */
    public boolean connectBT(String bluetoothadd) {
        String log = "connectBT()";
        try {
            this.device = BA.getRemoteDevice(bluetoothadd);
            if (!this.isConnection) {
                bluetoothSocket = this.device
                        .createRfcommSocketToServiceRecord(uuid);
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                if (outputStream != null) {
                }
                this.isConnection = true;
            }
        } catch (Exception ex) {
            System.out.print("远程获取设备出现异常" + ex);
            return false;// ��ȡ�豸�����쳣
        }
        return true;
    }
    /**
     * 201609 ���� ��ӡǩ����Ƭ
     * @param imgPath
     * @return
     */


    public static byte[] imageToByteArray(String imgPath) {
        BufferedInputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(imgPath));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int size = 0;
            byte[] temp = new byte[1024];
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();


        if(bluetoothSocket!=null){
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            bluetoothSocket=null;
        }
        if(outputStream!=null){
            try {
                outputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            outputStream=null;
        }
    }
}
