package com.hsic.sy.supply.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.sy.supplystationmanager.R;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.adapter.TagAdapter;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.QPInfo;
import com.hsic.sy.bll.DataParse;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.GetDate;
import com.hsic.sy.db.UserRegionCodeDB;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.nfc.GetTag;
import com.hsic.sy.supply.listener.PostDataListener;
import com.hsic.sy.supply.listener.UploadListener;
import com.hsic.sy.supply.task.UpLoadTaskByBottle;
import com.hsic.sy.supply.task.UpLoadUserRegionByDB;

public class ReadTag extends Activity implements OnClickListener, UploadListener,
        PostDataListener,
        com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
    private TextView Operation,OperationMan;
    NfcAdapter mAdapter;
    private static PendingIntent mPendingIntent;
    private static IntentFilter[] mFilters;
    private static String[][] mTechLists;
    ListView listview;
    QPInfo qpInfo;
    List<QPInfo> qpInfo_List;
    String operation="";
    private Button UpLoad;
    String POS="9866";
    String Year="";//年月日
    int serial=0;////流水号[递增]
    String SerialNum="0000";//流水号[递增]
    String LogOutTag="ff";//注销标识
    String UID="";//气瓶UID
    String Link="03";//环节
    String Time="";//时分秒
    String YFCode="";//预付码
    String FullMedia="";//充装介质
    String GH="";//瓶号
    String StationID="";//站点编号
    String SendDate="";//配送日期
    String s1="0000";
    String s2="000700";
    String QPType="";//气瓶类型
    String s3="00";
    String s4="03";
    String StuffUID="bb8f4800306623e0";//员工ID
    DataParse dataParse;
    GetDate getDate;
    List<String> UpLoad_LIST;
    String operationTag="";//操作环节标识
    String upload="";//当前需要上传的数据
    int [] Result=new int[5];
    private TextView All,UploadSucess,CF,DateError,Others;
    Vibrator vibrator; //震动
    private AlertView mAlertView;//避免创建重复View，先创建View，然后需要的时候show出来
    UserRegionCodeDB UserRegionCode_TB;
    GetBasicInfo getBasicInfo;//20181019
    private ProgressDialog dialog;// 显示等待时间的进度对话框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive);
        listview=(ListView) this.findViewById(R.id.listView1);
        Operation=(TextView) this.findViewById(R.id.operation);
        OperationMan=(TextView) this.findViewById(R.id.operationMan);
        All=(TextView) this.findViewById(R.id.all);
        UploadSucess=(TextView) this.findViewById(R.id.uploadsucess);
        CF=(TextView) this.findViewById(R.id.cf);
        DateError=(TextView) this.findViewById(R.id.dateerroe);
        Others=(TextView) this.findViewById(R.id.other);
        UpLoad=(Button) this.findViewById(R.id.upload);
        UpLoad.setOnClickListener(this);
        getBasicInfo=new GetBasicInfo(ReadTag.this);
        UserRegionCode_TB=new UserRegionCodeDB(getContext());

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        dialog = new ProgressDialog(getContext());

        operationTag();
        Operation.setText(operationTag);
        String stuffName=getBasicInfo.getOperationName();
        if(stuffName==null){
            stuffName="";
        }
        StationID=getBasicInfo.getStationID();
        OperationMan.setText(getBasicInfo.getStationName());
        Link="0"+operation;
        StuffUID=getBasicInfo.getStuffTagID();
        setStuffCardID();
        POS=getBasicInfo.getDeviceID().substring(4, 8);

        setNFC();
        UpLoad_LIST=new ArrayList<String>();//上传数据列表
        qpInfo_List= new ArrayList<QPInfo>();
        setList();

        mAlertView=new AlertView("提示", "服务调用失败!", null, new String[] { "确定" },
                null, getContext(), AlertView.Style.Alert, this);



    }
    private void setStuffCardID(){
        String[] temp_uid = new String[8];
        temp_uid[0] = StuffUID.substring(0, 2);
        temp_uid[1] = StuffUID.substring(2, 4);
        temp_uid[2] = StuffUID.substring(4, 6);
        temp_uid[3] = StuffUID.substring(6, 8);
        temp_uid[4] = StuffUID.substring(8, 10);
        temp_uid[5] = StuffUID.substring(10, 12);
        temp_uid[6] = StuffUID.substring(12, 14);
        temp_uid[7] = StuffUID.substring(14, 16);

        StuffUID = temp_uid[7] + temp_uid[6] + temp_uid[5]
                + temp_uid[4] + temp_uid[3] + temp_uid[2]
                + temp_uid[1] + temp_uid[0];
    }
    private void operationTag(){
        Bundle bundle = this.getIntent().getExtras();
        operation=bundle.getString("operationTag");
        int tag=Integer.parseInt(operation);

        switch(tag){
            case 1:
                operationTag="灌装收瓶";
                break;
            case 2:
                operationTag="灌装发瓶";
                break;
            case 3:
                operationTag="客户收瓶 ";
                break;
            case 4:
                operationTag="客户发瓶";
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        List<String> UpLoadData=new ArrayList<String>();
        UpLoadData=UserRegionCode_TB.GetUserRegionDB(StationID,GetDate.getToday());	//查询未上传数据
        int size=UpLoadData.size();
        if(size>0){
            for(int i=0;i<size;i++){
                String temp=UpLoadData.get(i);
                String[] data=temp.split(",");
                UpLoadUserRegionByDB task=new UpLoadUserRegionByDB(this,dialog);
                task.execute(data[0],data[1],StationID);//data[0]:上传数据，data[1]:Web调用结果
            }
        }else{
            Toast.makeText(ReadTag.this,"无要上传的数据",Toast.LENGTH_SHORT).show();
        }
    }
    private Context  getContext(){
        return this;
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }
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

    // NFC模块
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
            } catch (MalformedMimeTypeException e) {
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
    @Override
    public void onNewIntent(Intent intent) {
        resolveIntent(intent);
    }

    void resolveIntent(Intent intent) {
        try{
            String action = intent.getAction();
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
                GetTag getTag=new GetTag();
                dataParse=new DataParse();
                String[] GetData=new String[2];
                GetData=getTag.getTag(intent,0,16,getBasicInfo.getDeviceType());
                UID=GetData[0];
                String ret=GetData[1];
                if(ret.length()>=16){
                    String company=ret.substring(18, 24);
                    if(company.equals("311211")){
                        getQPData(ret);
                    }else{
                        Toast.makeText(ReadTag.this,"非气瓶标签",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }catch(Exception ex){
            ex.toString();
        }

    }

    private String addZero(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左(前)补0
            // sb.append(str).append("0");//右(后)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }
    /**
     * 获取时间
     * @param ReadData
     */
    private void SortData(String ReadData){
        Date date = new Date();
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        String time = sdf.format(date);//时分秒

        getDate=new GetDate();
        if(ReadData.length()>=128){
            /**
             * 获取流水号
             */
            String serial_str=UserRegionCode_TB.GetSerialNum(Link, StationID, GetDate.getToday());
            if(serial_str==null||serial_str.equals("")){
                serial_str="1";
                serial=Integer.parseInt(serial_str);
                UserRegionCode_TB.InserSerialNum(String.valueOf(serial), StationID, GetDate.getToday(), Link);
            }else{
                serial=Integer.parseInt(serial_str);
                serial=serial+1;
                UserRegionCode_TB.UpdateSerialNum(String.valueOf(serial), StationID, GetDate.getToday(), Link);
            }
            SerialNum=addZero(String.valueOf(serial),4);
            Log.e("流水号", JSONUtils.toJsonWithGson(SerialNum));
            /**
             * 更新流水号
             */
            Year=getDate.GetYMD();
            Time=time.substring(8, 14);
            YFCode=ReadData.substring(16, 32);
            FullMedia=ReadData.substring(32, 36);
            String tempGH=ReadData.substring(36, 60);
            GH=dataParse.toStringHex2(tempGH);
            SendDate=getDate.GetDate();
            QPType="10";
            upload=POS+Year+SerialNum+LogOutTag+UID+Link+Time+
                    YFCode+FullMedia+GH+StationID+SendDate+"0000"+"000700"+QPType+"03"+StuffUID;
        }
    }

    // 是否退出程序
    private static Boolean isExit = false;
    // 定时触发器
    private static Timer tExit = null;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    /**
     * 获取气瓶信息
     * @param readdata 读取到的数据
     */

    private void  getQPData(String readdata){

        String tag=readdata.substring(24, 32);
        if(!isNumeric(tag)) return;//字符串不是整数 跳出
        vibrator.vibrate(1000);

        String  yfCode=tag;
        if(qpInfo_List !=null && qpInfo_List.size()>0){
            for (int i = 0; i < qpInfo_List.size(); i++) {
                if(qpInfo_List.get(i).getQPNumber().equals(yfCode)){
                    return ;
                }

            }
        }
        SortData(readdata);
        qpInfo=new QPInfo();
        qpInfo.setQPNumber(tag);
        qpInfo.setUploadData(upload);
        qpInfo.setColorTag("0");
        qpInfo_List.add(qpInfo);
        setList();
        int size=qpInfo_List.size();
        All.setText("总扫描数:"+size);
        up=new UpLoadTaskByBottle(this,this,dialog);
        up.execute(upload);
//		Log.e("上传数据",upload);
    }
    UpLoadTaskByBottle up;
    /**
     * 设置ListView数据
     */
    private void setList(){
        List<QPInfo> list = new ArrayList<QPInfo>();
        for(int i=qpInfo_List.size()-1;i>=0;i--){
            list.add(qpInfo_List.get(i));
        }
        listview.setAdapter(new TagAdapter(this, list));
    }
    private void showDiag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                ReadTag.this);
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
    @Override
    public void onDismiss(Object o) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onItemClick(Object o, int position) {
        // TODO Auto-generated method stub

    }
    @Override
    public void PostDataListenerListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub

    }
    /**
     * 对上传结果做处理
     * @param tag
     * @param size
     * @param resultTag:上传结果
     */
    private void ResetData(HsicMessage tag,int size,String resultTag,String ColorTag){
//		Log.e("上传数据结果",tag.getRespMsg()+"--"+resultTag);
        UserRegionCode_TB.InserDB(tag.getRespMsg(), StationID,GetDate.getToday(),resultTag);
        for(int i=0;i<size;i++){
            if(tag.getRespMsg().equals(qpInfo_List.get(i).getUploadData())){
                qpInfo=new QPInfo();
                qpInfo.setQPNumber(qpInfo_List.get(i).getQPNumber());
                qpInfo.setUploadData(qpInfo_List.get(i).getUploadData());
                qpInfo.setColorTag(ColorTag);
                qpInfo_List.remove(i);
                setList();
                qpInfo_List.add(qpInfo);
                setList();
            }
        }
    }
    @Override
    public void UploadListenerEnd(HsicMessage tag) {
        // TODO Auto-generated method stub
        int size=qpInfo_List.size();
        switch(tag.getRespCode()){
            case 0://服务调用成功
                dialog.dismiss();
                ResetData(tag,size,"0","1");
                Result[0]=	Result[0]+1;
                break;
            case 1://扫描日期不对
                dialog.dismiss();
                ResetData(tag,size,"1","0");
                Result[1]=	Result[1]+1;
                break;
            case 2://重复扫描
                dialog.dismiss();
                ResetData(tag,size,"2","0");
                Result[2]=	Result[2]+1;
                break;
            case 3://参数不正确
                dialog.dismiss();
                ResetData(tag,size,"3","0");
                Result[3]=	Result[3]+1;
                break;
            case 4://网络异常
                dialog.dismiss();
                ResetData(tag,size,"4","0");
                Result[3]=	Result[3]+1;
                break;
            case 5://服务调用失败
                dialog.dismiss();
                mAlertView.show();
                ResetData(tag,size,"4","0");
                Result[3]=	Result[3]+1;
                break;
            default:
                break;
        }

        All.setText("总扫描数:"+size);
        UploadSucess.setText("上传成功:"+Result[0]);
        CF.setText("重复扫描:"+Result[2]);
        DateError.setText("日期不对:"+Result[1]);
        Others.setText("其他:"+Result[3]);
    }
    /**
     * 是否全为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    /**
     * 判断网络状态
     */
    public static final int getNetWorkConnectionType(Context context){
        final ConnectivityManager connectivityManager=(ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifiNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobileNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifiNetworkInfo!=null &&wifiNetworkInfo.isAvailable())
        {
            return ConnectivityManager.TYPE_WIFI;
        }
        else if(mobileNetworkInfo!=null &&mobileNetworkInfo.isAvailable())
        {
            return ConnectivityManager.TYPE_MOBILE;
        }
        else {
            return -1;
        }
    }

}
