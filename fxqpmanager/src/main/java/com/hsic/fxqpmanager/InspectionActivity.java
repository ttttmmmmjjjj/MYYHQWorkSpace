package com.hsic.fxqpmanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.Toast;

import com.hsic.bean.HsicMessage;
import com.hsic.bean.UserXJInfo;
import com.hsic.bll.GetBasicInfo;
import com.hsic.bll.PrintPic;
import com.hsic.db.DeliveryDB;
import com.hsic.dialog.WritePadDialog;
import com.hsic.listener.DialogListener;
import com.hsic.listener.ImplUpLoadInspection;
import com.hsic.permission.PermissionHelper;
import com.hsic.permission.PermissionInterface;
import com.hsic.permission.PermissionPageUtils;
import com.hsic.permission.PermissionUtil;
import com.hsic.picture.PictureHelper;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.task.UpLoadInspectionTask;
import com.hsic.utils.GetOperationTime;
import com.hsic.utils.PathUtil;
import com.hsic.utils.PrintUtils;
import com.hsic.utils.TimeUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 巡检最新流程：打印签字，拍照，预览，上传
 */
public class InspectionActivity extends AppCompatActivity implements ImplUpLoadInspection,
        com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener{
    UserXJInfo userXJInfo;
    private TabHost mTabHost;
    private CheckBox StopSupplyType1, StopSupplyType2, StopSupplyType3,
            StopSupplyType4, StopSupplyType5, StopSupplyType6, StopSupplyType7,StopSupplyType8,
            UnInstallType1, UnInstallType2, UnInstallType3, UnInstallType4,
            UnInstallType5,UnInstallType6, UnInstallType7,
            UnInstallType9,  UnInstallType11, UnInstallType12;// S:停止供气；N:不予接装
    private List<CheckBox> StopSupply = new ArrayList<CheckBox>();
    private List<CheckBox> UnInstall = new ArrayList<CheckBox>();
    public static String filePath;
    String userId = "",saleId = "",deviceid = "";
    GetBasicInfo getBasicInfo;
    private Bitmap mSignBitmap;
    StringBuffer searchInfo;//巡检信息
    StringBuffer stopYY;
    String relationID;//
    DeliveryDB dbData;
    int picCount;
    Button btn_UpLoad;
    boolean isUpdate=false;String RectifyName = "", UserName = "", DeliverAddress = "", Telphone = "", RectifyDate = "", Remark = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inspection);
        userXJInfo=new UserXJInfo();
        userXJInfo.setInspectionStatus("0");
        getBasicInfo=new GetBasicInfo(InspectionActivity.this);
        deviceid=getBasicInfo.getDeviceID();
        Intent i=getIntent();
        saleId=i.getStringExtra("SaleID");
        userId=i.getStringExtra("UserID");
        UserName=i.getStringExtra("UserName");
        DeliverAddress=i.getStringExtra("Address");
        mTabHost = (TabHost) findViewById(R.id.tabhost);
        mTabHost.setup();
        final TabHost.TabSpec mTabSpec = mTabHost.newTabSpec("stop");
        TabHost.TabSpec mTabSpec2 = mTabHost.newTabSpec("UNstall");
        mTabSpec2.setIndicator("",
                getResources().getDrawable(R.drawable.u_tab_selector));
        mTabSpec2.setContent(R.id.page2);
        mTabHost.addTab(mTabSpec2);//默认显示一般隐患

        mTabSpec.setIndicator("",
                getResources().getDrawable(R.drawable.stop_tab_seletcor));
        mTabSpec.setContent(R.id.page1);
        mTabHost.addTab(mTabSpec);
        initCheckBox();
        relationID=deviceid + "e"+getBasicInfo.getOperationID()+"s" + saleId;
        dbData=new DeliveryDB(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }
    /**
     * 初始化checkBox
     */
    public void initCheckBox() {
        StopSupplyType1 = (CheckBox) this.findViewById(R.id.basement);
        StopSupplyType2 = (CheckBox) this.findViewById(R.id.half_basement);
        StopSupplyType3 = (CheckBox) this.findViewById(R.id.Highrise);
        StopSupplyType4 = (CheckBox) this.findViewById(R.id.crowd_places);
        StopSupplyType5 = (CheckBox) this.findViewById(R.id.Airtight);
        StopSupplyType6 = (CheckBox) this.findViewById(R.id.bedroom);
        StopSupplyType7 = (CheckBox) this.findViewById(R.id.two_gas);
        StopSupplyType8 = (CheckBox) this.findViewById(R.id.Others);

        UnInstallType1 = (CheckBox) this.findViewById(R.id.no_safe_EXTRCTOR);
        UnInstallType2 = (CheckBox) this.findViewById(R.id.UnInstallType2);
        UnInstallType3 = (CheckBox) this.findViewById(R.id.no_safe_gas_cooker);
        UnInstallType4 = (CheckBox) this.findViewById(R.id.no_match);
        UnInstallType5 = (CheckBox) this.findViewById(R.id.UnInstallType5);
        UnInstallType6 = (CheckBox) this.findViewById(R.id.no_rubber);
        UnInstallType7 = (CheckBox) this.findViewById(R.id.UnInstallType7);
        UnInstallType9 = (CheckBox) this.findViewById(R.id.leak);
        UnInstallType11 = (CheckBox) this.findViewById(R.id.have_fire);
        UnInstallType12 = (CheckBox) this.findViewById(R.id.other);

        StopSupply.add(StopSupplyType1);
        StopSupply.add(StopSupplyType2);
        StopSupply.add(StopSupplyType3);
        StopSupply.add(StopSupplyType4);
        StopSupply.add(StopSupplyType5);
        StopSupply.add(StopSupplyType6);
        StopSupply.add(StopSupplyType7);
        StopSupply.add(StopSupplyType8);


        UnInstall.add(UnInstallType1);
        UnInstall.add(UnInstallType2);
        UnInstall.add(UnInstallType3);
        UnInstall.add(UnInstallType4);
        UnInstall.add(UnInstallType5);
        UnInstall.add(UnInstallType6);
        UnInstall.add(UnInstallType7);
        UnInstall.add(UnInstallType9);
        UnInstall.add(UnInstallType11);
        UnInstall.add(UnInstallType12);
        btn_UpLoad=this.findViewById(R.id.upload);
        btn_UpLoad.setEnabled(false);//

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        } else {
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    /***
     * 上传巡检信息
     * @param view
     */
    public void upload(View view){
        //上传巡检信息
        picCount=0;
        isWrite();//检查照片数量
        Log.e("picCount","="+picCount);
        if(picCount>0){
            //巡检完成请拍照
            if(checkNetworkState()){
                new UpLoadInspectionTask(InspectionActivity.this,InspectionActivity.this).execute(saleId,userId);
            }else{
                Toast.makeText(InspectionActivity.this, "无网络", Toast.LENGTH_SHORT).show();
                InspectionActivity.this.finish();
            }
        }else{
            Toast.makeText(getApplicationContext(), "请先拍照",
                    Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 拍照保存页面
     *
     * @param view
     */
    public void click1(View view) {
        takePhotoes(userId, saleId);
    }

    /**
     * 进入预览页面
     *
     * @param view
     */
    public void click2(View view) {
        preview();
    }

    /**
     * 打印签字
     * @param view
     */
    public void click3(View view) {
        try{
            String inspectedDate = GetOperationTime.getCurrentTime();//获取巡检日期
            saveState();//保存巡检状态
            getRectifyResult();//
            userXJInfo.setIsBackup("0");
            userXJInfo.setErrorAddress("0");
            userXJInfo.setErrorNature("0");
            userXJInfo.setRefuseInspection("0");
            userXJInfo.setNoAlarm("0");
            userXJInfo.setInspectionMan(getBasicInfo.getOperationID());
            userXJInfo.setInspectionDate(inspectedDate);
            isUpdate=dbData.upDateInspectionInfo(getBasicInfo.getOperationID(),getBasicInfo.getStationID(),saleId,userXJInfo);
            update();
//            if(picCount>0){
//                if(isWrite()){
//                    //是否已拍照
//                    isUpdate=dbData.upDateInspectionInfo(getBasicInfo.getOperationID(),getBasicInfo.getStationID(),saleId,userXJInfo);
//                    update();
//                }else{
//                    //签字确认
//                    WritePadDialog wpd = new WritePadDialog(InspectionActivity.this,
//                            new DialogListener() {
//                                @Override
//                                public void refreshActivity(Object object) {
//                                    mSignBitmap = (Bitmap) object;
//                                    createSignFile();
//                                    //签字成功，保存巡检信息,巡检信息关联表到本地
//                                    update();
//                                }
//                            });
//                    wpd.show();
//                }
//            }else{
//                //巡检完成请拍照
//                Toast.makeText(getApplicationContext(), "巡检完成请拍照",
//                        Toast.LENGTH_SHORT).show();
//            }


        }catch(Exception ex){
            ex.toString();

        }
    }

    /**
     * 更新本地数据库巡检想信息 并打印
     */
    private void update(){
        if(isUpdate){
            btn_UpLoad.setEnabled(true);
            new PrintCodeTask(InspectionActivity.this).execute();//直接打印
        }
    }
    /**
     * 创建签字文件名
     * @return
     */
    private String createSignFile() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String _path = null;
        try {
            String sign_dir = PathUtil.getImagePath();
            File f = new File(sign_dir);
            if (!f.exists()) {
                f.mkdirs();
            }
            String format=TimeUtils.getTime("yyyyMMddHHmmss");
            _path = sign_dir + relationID+"_" +  format+ "_"
                    + userId +"xj"+"sign" + ".jpg";//签字照片名
            mSignBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] photoBytes = baos.toByteArray();
            FileOutputStream fo = new FileOutputStream(_path);
            if (photoBytes != null) {
                fo.write(photoBytes);
                //将签字照片文件保存到本地
                String ImageFileName=relationID+"_"+ format + "_" + userId +"xj"+"sign" +".jpg";
                String FileName=PathUtil.getFilePath();
                dbData.InsertXJAssociation(getBasicInfo.getOperationID(), saleId, ImageFileName, relationID,FileName);//将照片信息插入到数据表中
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String time=TimeUtils.getTime("yyyyMMddHHmmss");
                getImagePath(filePath,deviceid,getBasicInfo.getOperationID(),saleId, userId,time);

            }
        }

    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    @SuppressLint("SimpleDateFormat")
    public void takePhotoes(String user, String checkSaleid) {
        filePath = Environment.getExternalStorageDirectory() + "/photoes/"
                + deviceid + "s" + ".jpg";
        File file = new File(filePath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fileDir = new File(Environment.getExternalStorageDirectory(),
                "/photoes/");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)); // 保存图片的位置
        startActivityForResult(intent, 1);
    }

    /**
     * 判断是否签字
     * @return
     */
    public boolean isWrite(){
        boolean ret=false;
        String filePath = PathUtil.getImagePath();
        File file = new File(filePath);
        String[] paths = file.list();
        if (paths != null && paths.length > 0) {
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (path.contains(relationID)) {
                    picCount++;//照片数量
                }
            }
        } else {
            ret=false;
            Toast.makeText(getApplicationContext(), "请先拍照",
                    Toast.LENGTH_SHORT).show();
        }
        return ret;
    }
    public void preview() {
        int flag = 0;
        String filePath = PathUtil.getImagePath();
        File file = new File(filePath);
        String[] paths = file.list();
        if (paths != null && paths.length > 0) {
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (path.contains(relationID)) {
                    flag++;
                }
            }
            if (flag == 0) {
                Toast.makeText(getApplicationContext(), "请先拍照后在预览",
                        Toast.LENGTH_SHORT).show();
                return;
            } else {
                Intent intent = new Intent(this, InsepctionPreviewActivity.class);
                intent.putExtra("SaleID",saleId);
                intent.putExtra("UserID",userId);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "请先拍照后在预览",
                    Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 保存巡检项同时把巡检项插入数据库
     */
    private void saveState(){
        boolean UNStatus = false;
        boolean StopStatus = false;
        searchInfo=new StringBuffer();
        if(UnInstallType1.isChecked()){
            UNStatus = true;
            userXJInfo.setUnInstallType1("1");
        }else{
            userXJInfo.setUnInstallType1("0");
        }
        if(UnInstallType2.isChecked()){
            UNStatus = true;
            userXJInfo.setUnInstallType2("1");
        }else{
            userXJInfo.setUnInstallType2("0");
        }
        if(UnInstallType3.isChecked()){
            UNStatus = true;
            userXJInfo.setUnInstallType3("1");
        }else{
            userXJInfo.setUnInstallType3("0");
        }
        if(UnInstallType4.isChecked()){
            userXJInfo.setUnInstallType4("1");
            UNStatus = true;
        }else{
            userXJInfo.setUnInstallType4("0");
        }
        if(UnInstallType5.isChecked()){
            UNStatus = true;
            userXJInfo.setUnInstallType5("1");
        }else{
            userXJInfo.setUnInstallType5("0");
        }
        if(UnInstallType6.isChecked()){
            userXJInfo.setUnInstallType6("1");
            UNStatus = true;
        }else{
            userXJInfo.setUnInstallType6("0");
        }
        if(UnInstallType7.isChecked()){
            UNStatus = true;
            userXJInfo.setUnInstallType7("1");
        }else{
            userXJInfo.setUnInstallType7("0");
        }
        userXJInfo.setUnInstallType8("0");
        if(UnInstallType9.isChecked()){
            userXJInfo.setUnInstallType9("1");
            UNStatus = true;
        }else{
            userXJInfo.setUnInstallType9("0");
        }
        userXJInfo.setUnInstallType10("0");
        if(UnInstallType11.isChecked()){
            userXJInfo.setUnInstallType11("1");
            UNStatus = true;
        }else{
            userXJInfo.setUnInstallType11("0");
        }
        if(UnInstallType12.isChecked()){
            userXJInfo.setUnInstallType12("1");
            UNStatus = true;
        }else{
            userXJInfo.setUnInstallType12("0");
        }
        if(StopSupplyType1.isChecked()){
            userXJInfo.setStopSupplyType1("1");
            StopStatus = true;
        }else{
            userXJInfo.setStopSupplyType1("0");
        }
        if(StopSupplyType2.isChecked()){
            userXJInfo.setStopSupplyType2("1");
            StopStatus = true;
        }else{
            userXJInfo.setStopSupplyType2("0");
        }
        if(StopSupplyType3.isChecked()){
            userXJInfo.setStopSupplyType3("1");
            StopStatus = true;
        }else{
            userXJInfo.setStopSupplyType3("0");
        }
        if(StopSupplyType4.isChecked()){
            userXJInfo.setStopSupplyType4("1");
            StopStatus = true;
        }else{
            userXJInfo.setStopSupplyType4("0");
        }
        if(StopSupplyType5.isChecked()){
            userXJInfo.setStopSupplyType5("1");
            StopStatus = true;
        }else{
            userXJInfo.setStopSupplyType5("0");
        }

        if(StopSupplyType6.isChecked()){
            userXJInfo.setStopSupplyType6("1");
            StopStatus = true;
        }else{
            userXJInfo.setStopSupplyType6("0");
        }
        if(StopSupplyType7.isChecked()){
            userXJInfo.setStopSupplyType7("1");
            StopStatus = true;
        }else{
            userXJInfo.setStopSupplyType7("0");
        }

        if(StopSupplyType8.isChecked()){
            userXJInfo.setStopSupplyType8("1");
            StopStatus = true;
        }else{
            userXJInfo.setStopSupplyType8("0");
        }
        userXJInfo.setAttachID(relationID);
//        userXJInfo.setInspectionMan();
        if (StopStatus) {
            userXJInfo.setInspectionStatus("2");
            userXJInfo.setIsInspected("1");// 本地标识：做过巡检
            return;

        } else if (UNStatus) {
            userXJInfo.setInspectionStatus("1");
            userXJInfo.setIsInspected("1");
//            userXJInfo.setStopYY("");
            userXJInfo.setStopSupplyType1("0");
            userXJInfo.setStopSupplyType2("0");
            userXJInfo.setStopSupplyType3("0");
            userXJInfo.setStopSupplyType4("0");
            userXJInfo.setStopSupplyType5("0");
            userXJInfo.setStopSupplyType6("0");
            userXJInfo.setStopSupplyType7("0");
            userXJInfo.setStopSupplyType8("0");
            return;
        } else {
            userXJInfo.setInspectionStatus("0");
            userXJInfo.setIsInspected("1");
//            userXJInfo.setStopYY("");
            userXJInfo.setStopSupplyType1("0");
            userXJInfo.setStopSupplyType2("0");
            userXJInfo.setStopSupplyType3("0");
            userXJInfo.setStopSupplyType4("0");
            userXJInfo.setStopSupplyType5("0");
            userXJInfo.setStopSupplyType6("0");
            userXJInfo.setStopSupplyType7("0");
            userXJInfo.setStopSupplyType8("0");
            userXJInfo.setUnInstallType1("0");
            userXJInfo.setUnInstallType2("0");
            userXJInfo.setUnInstallType3("0");
            userXJInfo.setUnInstallType4("0");
            userXJInfo.setUnInstallType5("0");
            userXJInfo.setUnInstallType6("0");
            userXJInfo.setUnInstallType7("0");
            userXJInfo.setUnInstallType9("0");
            userXJInfo.setUnInstallType11("0");
            userXJInfo.setUnInstallType12("0");
            return;
        }
    }
    /*

     */
    public void  getImagePath(String filePath,String deviceid,String employee, String checkSaleid, String user,String format) {
        if (checkSaleid != null) {
            File file = new File(filePath);
            if (file != null && file.exists()) {
                String path = PathUtil.getImagePath();
                File file1 = new File(path);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                String ImageFileName=relationID+"_" +format + "_" + user + ".jpg";
                File file2;
                file2 = new File(file1.getPath(), ImageFileName);
                PictureHelper.compressPicture(file.getAbsolutePath(),
                        file2.getAbsolutePath(), 720, 1280);
                if (file.exists()) {
                    file.delete();
                }
                File fileDir = new File(
                        Environment.getExternalStorageDirectory(), "/photoes/");
                if (fileDir.exists()) {
                    fileDir.delete();
                }
                String FileName=PathUtil.getFilePath();
                dbData.InsertXJAssociation(employee, checkSaleid, ImageFileName, relationID,FileName);//将照片信息插入到数据表中
            }
        }
    }
    private String getRectifyResult() {
        stopYY=new StringBuffer();
        searchInfo = new StringBuffer();
        if (StopSupplyType1.isChecked()) {
            stopYY.append(getResources().getString(R.string.StopSupplyType1)+",");
            searchInfo.append(getResources().getString(R.string.StopSupplyType1)+"\n");
        }
        if (StopSupplyType2.isChecked()) {
            stopYY.append(getResources().getString(R.string.StopSupplyType2)+",");
            searchInfo.append(getResources().getString(R.string.StopSupplyType2)+"\n");
        }
        if (StopSupplyType3.isChecked()) {
            stopYY.append(getResources().getString(R.string.StopSupplyType3)+",");
            searchInfo.append(getResources().getString(R.string.StopSupplyType3)+"\n");
        }
        if (StopSupplyType4.isChecked()) {
            stopYY.append(getResources().getString(R.string.StopSupplyType4)+",");
            searchInfo.append(getResources().getString(R.string.StopSupplyType4)+"\n");
        }

        if (StopSupplyType5.isChecked()) {
            stopYY.append(getResources().getString(R.string.StopSupplyType5)+",");
            searchInfo.append(getResources().getString(R.string.StopSupplyType5)+"\n");
        }
        if (StopSupplyType6.isChecked()) {
            stopYY.append(getResources().getString(R.string.StopSupplyType6)+",");
            searchInfo.append(getResources().getString(R.string.StopSupplyType6)+"\n");
        }
        if (StopSupplyType7.isChecked()) {
            stopYY.append(getResources().getString(R.string.StopSupplyType7)+",");
            searchInfo.append(getResources().getString(R.string.StopSupplyType7)+"\n");
        }
        if (StopSupplyType8.isChecked()) {
            stopYY.append(getResources().getString(R.string.StopSupplyType8));
            searchInfo.append(getResources().getString(R.string.StopSupplyType8)+"\n");
        }
        if (UnInstallType1.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType1)+"\n");
        }
        if (UnInstallType2.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType2)+"\n");
        }
        if (UnInstallType3.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType3)+"\n");
        }
        if (UnInstallType4.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType4)+"\n");
        }
        if (UnInstallType5.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType5)+"\n");
        }
        if (UnInstallType6.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType6)+"\n");
        }
        if (UnInstallType7.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType7)+"\n");
        }
        if (UnInstallType9.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType9)+"\n");
        }
        if (UnInstallType11.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType11)+"\n");
        }
        if (UnInstallType12.isChecked()) {
            searchInfo.append(getResources().getString(R.string.UnInstallType12));
        }
        return searchInfo.toString();
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        this.finish();
    }

    /**
     * 打印巡检信息
     */
    public class PrintCodeTask extends AsyncTask<Void, Void, Void> {
        private Context context = null;
        private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        private boolean isConnection = false;
        private BluetoothDevice device = null;
        private BluetoothSocket bluetoothSocket = null;
        private OutputStream outputStream;
        private final UUID uuid = UUID
                .fromString("00001101-0000-1000-8000-00805F9B34FB");
        SharedPreferences deviceSetting;
        String bluetoothadd = "";// 蓝牙MAC
        private ProgressDialog dialog;
        GetBasicInfo basicInfo;

        public PrintCodeTask(Context context) {
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
                int pCount = 2;
                String Intret = connectBT();
                PrintUtils.setOutputStream(outputStream);
                for (int a = 0; a < pCount; a++) {
                    PrintUtils.selectCommand(PrintUtils.RESET);
                    PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
                    PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
                    PrintUtils.selectCommand(PrintUtils.BOLD);
                    PrintUtils.printText("\n");
                    PrintUtils.printText("上海奉贤燃气股份有限公司" + "\n");//公司
                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.printText("巡检收据\n");
                    PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
                    PrintUtils.selectCommand(PrintUtils.NORMAL);
                    PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
//					PrintUtils.printText(PrintUtils.printTwoData("巡检单", saleId + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("巡检单", userId + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("巡检用户", UserName + "\n"));
                    PrintUtils.printText(DeliverAddress + "\n");
                    PrintUtils.printText("--------------------------------\n");
                    if (userXJInfo.getInspectionStatus().equals("1")) {
                        PrintUtils.printText("本次巡检结果:一般隐患" + "\n");
                        PrintUtils.printText("本次巡检不合格项:" + "\n");
                        PrintUtils.printText(searchInfo.toString() + "\n");
                    } else if(userXJInfo.getInspectionStatus().equals("2")){
                        PrintUtils.printText("本次巡检结果:严重隐患" + "\n");
                        PrintUtils.printText("本次巡检不合格项:" + "\n");
                        PrintUtils.printText(searchInfo.toString() + "\n");
                    }else{
                        PrintUtils.printText("本次巡检结果:合格" + "\n");
                    }
                    PrintUtils.printText("--------------------------------\n");
                    PrintUtils.printText(PrintUtils.printTwoData("巡检人:", getBasicInfo.getOperationName() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("巡检日期:", userXJInfo.getInspectionDate() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("用户签字:",   "\n\n"));
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
    @Override
    public void UpLoadInspectionTaskEnd(HsicMessage tag) {
        if(tag.getRespCode()==0){
            new AlertView("提示", "巡检信息上传成功", null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }else{
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
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
