package com.hsic.fxqpmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hsic.adapter.DragDelListAdaper;
import com.hsic.bean.UserReginCode;
import com.hsic.bll.GetBasicInfo;
import com.hsic.nfc.GetTag;
import com.hsic.sy.dragdellistview.DragDelListView;

import java.util.ArrayList;
import java.util.List;

public class ScanQPTagActivity extends AppCompatActivity {
    List<UserReginCode> UserReginCode_List;
    private Button btn_Sure,btn_Cansel,btn_input;
    private Dialog mGoodsDialog;//
    private LinearLayout root;
    private EditText edt_qpTag;
    private DragDelListView mListView;
    GetBasicInfo basicInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qptag);
        mListView=this.findViewById(R.id.listView01);
        btn_Sure=this.findViewById(R.id.btn_sure);
        btn_Cansel=this.findViewById(R.id.btn_cansel);
        btn_input=this.findViewById(R.id.btn_input);
        InnitGoodsDialog();
        setNFC();
        basicInfo=new GetBasicInfo(ScanQPTagActivity.this);
        UserReginCode_List=new ArrayList<UserReginCode>();
        btn_Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userReginCodeStr="";
                StringBuffer userReginCodeStrBuff=new StringBuffer();
                int size=UserReginCode_List.size();
                if(size>0){
                    for (int i=0;i<size;i++){
                        userReginCodeStrBuff.append(UserReginCode_List.get(i).getUserRegionCode()+",");
                    }
                    userReginCodeStr=userReginCodeStrBuff.substring(0,userReginCodeStrBuff.length()-1);
                    Intent data = new Intent();
                    data.putExtra("userReginCode",userReginCodeStr);
                    setResult(5,data);
                    ScanQPTagActivity.this.finish();
                }else{
                    Toast.makeText(getBaseContext(),"请先扫描标签",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_Cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("userReginCode","");
                setResult(5,data);
                ScanQPTagActivity.this.finish();
            }
        });
        btn_input.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mGoodsDialog.show();
            }
        });
        mGoodsDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mGoodsDialog.dismiss();
                }
                return false;
            }

        });
        /**
         *设置标签号 缓存
         */
        Intent intent = getIntent();
        if(intent!=null){
            String tag = intent.getStringExtra("QPNO");
            if(!tag.equals("")){
                if(tag.contains(",")){
                    String[] temp=tag.split(",");
                    int size=temp.length;
                    for(int i=0;i<size;i++){
                        SortData(temp[i]);
                    }
                }else{
                    SortData(tag);
                }
            }
        }
    }
    private void InnitGoodsDialog(){
        mGoodsDialog = new Dialog(this, R.style.my_dialog);
        root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.qp_tag_input_layout, null);
        root.findViewById(R.id.btn_cansel).setOnClickListener(btnListener);
        root.findViewById(R.id.btn_sure).setOnClickListener(btnListener);
        edt_qpTag = (EditText) root.findViewById(R.id.edt_brand);
        edt_qpTag.setText("");
        mGoodsDialog.setContentView(root);
        Window dialogWindow = mGoodsDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 450; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
//        toolbar.inflateMenu(R.menu.login_toolbar_menu);
//        toolbar.setTitle("登录");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int menuItemId = item.getItemId();
//                if(menuItemId==R.id.action_settings){
//                    Intent i = new Intent(LoginActivity.this, AdvConfigActivity.class);
//                    LoginActivity.this.startActivity(i);
//                }
//
//                return false;
//            }
//        });
    }
    private View.OnClickListener btnListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_cansel:
                    mGoodsDialog.dismiss();
                    break;
                case R.id.btn_sure://保存数据
                    String readData=edt_qpTag.getText().toString();
                    if(!TextUtils.isEmpty(readData)){
                        if(readData.length()<8){
                            Toast.makeText(getBaseContext(),"瓶号必须为8位",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SortData(readData);
                    }else{
                        Toast.makeText(getBaseContext(),"请输入气瓶号",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mGoodsDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        try {
            mAdapter.disableForegroundDispatch(this);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                showDiag();
            }
        }

        try {
            mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
                    mTechLists);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            String userReginCodeStr="";
            StringBuffer userReginCodeStrBuff=new StringBuffer();
            int size=UserReginCode_List.size();
            if(size>0){
                for (int i=0;i<size;i++){
                    userReginCodeStrBuff.append(UserReginCode_List.get(i).getUserRegionCode()+",");
                }
                userReginCodeStr=userReginCodeStrBuff.substring(0,userReginCodeStrBuff.length()-1);
                Intent data = new Intent();
                data.putExtra("userReginCode",userReginCodeStr);
                setResult(5,data);
                ScanQPTagActivity.this.finish();
            }else{
                Intent data = new Intent();
                data.putExtra("userReginCode","");
                setResult(5,data);
                ScanQPTagActivity.this.finish();
//                Toast.makeText(getBaseContext(),"请先扫描标签",Toast.LENGTH_SHORT).show();
            }
            this.finish();
            return super.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
        // TODO Auto-generated method stub

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        resolveIntent(intent);
    }

    NfcAdapter mAdapter;
    private static PendingIntent mPendingIntent;
    private static IntentFilter[] mFilters;
    private static String[][] mTechLists;

    // NFC忙篓隆氓聺聴
    private void setNFC() {
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                showDiag();
            }
            mPendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass())
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            // Setup an intent filter for all MIME based dispatches
            IntentFilter ndef = new IntentFilter(
                    NfcAdapter.ACTION_TECH_DISCOVERED);

            try {
                ndef.addDataType("*/*");
            } catch (IntentFilter.MalformedMimeTypeException e) {
                throw new RuntimeException("fail", e);
            }
            mFilters = new IntentFilter[] { ndef, };

            // Setup a tech list for all NfcF tags
            mTechLists = new String[][] {
                    new String[] { MifareClassic.class.getName() },
                    new String[] { NfcA.class.getName() },
                    new String[] { NfcB.class.getName() },
                    new String[] { NfcF.class.getName() },
                    new String[] { NfcV.class.getName() },
                    new String[] { Ndef.class.getName() },
                    new String[] { NdefFormatable.class.getName() },
                    new String[] { MifareUltralight.class.getName() },
                    new String[] { IsoDep.class.getName() } };

            Intent intent = getIntent();
            resolveIntent(intent);
        }
    }

    void resolveIntent(Intent intent) {
        try {
            String action = intent.getAction();
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
                GetTag getTag = new GetTag();
                String[] ret=new String[2];
                String readData="";
                ret=getTag.getTag(intent,3,1,basicInfo.getDeviceType());
                readData = ret[1];
//                Log.e("标签号","="+readData);
                if (readData != null) {
                    if (!readData.equals("")) {
                        SortData(readData);
                    } else {
                        // 读取数据
                    }
                }

            }
        } catch (Exception ex) {
            ex.toString();
        }

    }
    private void SortData(String readData){
        UserReginCode userReginCode=new UserReginCode();
        int size=UserReginCode_List.size();
        if(size>0){
            for(int i=0;i<size;i++){
                if(readData.equals(UserReginCode_List.get(i).getUserRegionCode())){
                    return;
                }
            }
        }
        userReginCode.setUserRegionCode(readData);
        UserReginCode_List.add(userReginCode);
        mListView.setAdapter( new DragDelListAdaper(getBaseContext(),UserReginCode_List));
    }
    private void showDiag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("NFC设备未打开，是否去打开？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
