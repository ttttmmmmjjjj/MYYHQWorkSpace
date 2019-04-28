package com.hsic.fxqpmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.Toast;

import com.hsic.bean.HsicMessage;
import com.hsic.bean.LastUserInspection;
import com.hsic.bean.UserRectifyInfo;
import com.hsic.bll.GetBasicInfo;
import com.hsic.db.RectifyDB;
import com.hsic.dialog.WritePadDialog;
import com.hsic.listener.DialogListener;
import com.hsic.listener.ImplUploadRectify;
import com.hsic.picture.PictureHelper;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.task.UpLoadRectifyTask;
import com.hsic.utils.GetOperationTime;
import com.hsic.utils.PathUtil;
import com.hsic.utils.PrintUtils;
import com.hsic.utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class RectiftyActivity extends AppCompatActivity implements ImplUploadRectify , com.hsic.sy.dialoglibrary.OnItemClickListener, OnDismissListener {
    private TabHost mTabHost;
    private List<CheckBox> StopSupply = new ArrayList<CheckBox>();
    private List<CheckBox> UnInstall = new ArrayList<CheckBox>();
    private CheckBox StopSupplyType1, StopSupplyType2, StopSupplyType3,
            StopSupplyType4, StopSupplyType5, StopSupplyType6, StopSupplyType7, StopSupplyType8,
            UnInstallType1, UnInstallType3, UnInstallType2, UnInstallType4, UnInstallType5,
            UnInstallType6, UnInstallType7,
            UnInstallType9, UnInstallType11, UnInstallType12, upDate, repair, stop, result;// S:停止供气；N:不予接装
    StringBuffer lastInspection = new StringBuffer();
    StringBuffer rectifyInfo;
    LastUserInspection lastUserInspection;
    UserRectifyInfo userRectifyInfo;
    GetBasicInfo getBasicInfo;//20181019
    String RectifyName = "", UserName = "", DeliverAddress = "", Telphone = "", RectifyDate = "", Remark = "";
    String deviceid,id,userID;
    String relationID,emplyeeID,stationID;//
    RectifyDB rectifyDB;
    public static String filePath;
    boolean isUpdate=false;
    int picCount=0;
    Button btn_UpLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectifty);
        mTabHost = (TabHost) findViewById(R.id.tabhost);
        mTabHost.setup();
        final TabHost.TabSpec mTabSpec = mTabHost.newTabSpec("stop");
        TabHost.TabSpec mTabSpec2 = mTabHost.newTabSpec("UNstall");
        mTabSpec.setIndicator("",
                getResources().getDrawable(R.drawable.stop_tab_seletcor));
        mTabSpec.setContent(R.id.page1);
        mTabHost.addTab(mTabSpec);

        mTabSpec2.setIndicator("",
                getResources().getDrawable(R.drawable.u_tab_selector));
        mTabSpec2.setContent(R.id.page2);
        mTabHost.addTab(mTabSpec2);
        initCheckBox();
        Intent i=getIntent();
        userID=i.getStringExtra("UserID");
        getBasicInfo = new GetBasicInfo(RectiftyActivity.this);
        rectifyDB=new RectifyDB(RectiftyActivity.this);
        userRectifyInfo=rectifyDB.GetRectifyInfoByUserID(getBasicInfo.getOperationID(),getBasicInfo.getStationID(),userID);
        id=userRectifyInfo.getId();
        lastUserInspection = new LastUserInspection();
        setData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
    private void setData(){
        deviceid=getBasicInfo.getDeviceID();
        emplyeeID=getBasicInfo.getOperationID();
        stationID=getBasicInfo.getStationID();
        id=userRectifyInfo.getId();
        userID=userRectifyInfo.getUserid();
        UserName=userRectifyInfo.getUsername();
        DeliverAddress=userRectifyInfo.getDeliveraddress();
        Telphone=userRectifyInfo.getTelephone();
        relationID=deviceid + "e"+emplyeeID+"id" + id;
        if(userRectifyInfo.getLast_InspectionStatus().equals("2")){
            lastUserInspection.setInspectionStatus("2");
            if (userRectifyInfo.getStopSupplyType1().equals("1")) {
                StopSupplyType1.setChecked(true);
                lastUserInspection.setStopSupplyType1("1");
                lastInspection.append(getResources().getString(R.string.StopSupplyType1)
                        + "\n");
            } else {
                StopSupplyType1.setChecked(false);
//                lastUserInspection.setStopSupplyType1("0");
            }
            if (userRectifyInfo.getStopSupplyType2().equals("1")) {
                StopSupplyType2.setChecked(true);
                lastUserInspection.setStopSupplyType2("1");
                lastInspection.append(getResources().getString(R.string.StopSupplyType2)
                        + "\n");
            } else {
                StopSupplyType2.setChecked(false);
                lastUserInspection.setStopSupplyType2("0");
            }
            if (userRectifyInfo.getStopSupplyType3().equals("1")) {
                StopSupplyType3.setChecked(true);
                lastUserInspection.setStopSupplyType3("1");
                lastInspection.append(getResources().getString(R.string.StopSupplyType3)
                        + "\n");
            } else {
                StopSupplyType3.setChecked(false);
                lastUserInspection.setStopSupplyType3("0");
            }
            if (userRectifyInfo.getStopSupplyType4().equals("1")) {
                StopSupplyType4.setChecked(true);
                lastUserInspection.setStopSupplyType4("1");
                lastInspection.append(getResources().getString(R.string.StopSupplyType4)
                        + "\n");
            } else {
                StopSupplyType4.setChecked(false);
                lastUserInspection.setStopSupplyType4("0");
            }
            if (userRectifyInfo.getStopSupplyType5().equals("1")) {
                StopSupplyType5.setChecked(true);
                lastUserInspection.setStopSupplyType5("1");
                lastInspection.append(getResources().getString(R.string.StopSupplyType5)
                        + "\n");
            } else {
                StopSupplyType5.setChecked(false);
                lastUserInspection.setStopSupplyType5("0");
            }
            if (userRectifyInfo.getStopSupplyType6().equals("1")) {
                StopSupplyType6.setChecked(true);
                lastUserInspection.setStopSupplyType6("1");
                lastInspection.append(getResources().getString(R.string.StopSupplyType6)
                        + "\n");
            } else {
                StopSupplyType6.setChecked(false);
                lastUserInspection.setStopSupplyType6("0");
            }
            if (userRectifyInfo.getStopSupplyType7().equals("1")) {
                StopSupplyType7.setChecked(true);
                lastUserInspection.setStopSupplyType7("1");
                lastInspection.append(getResources().getString(R.string.StopSupplyType7)
                        + "\n");
            } else {
                StopSupplyType7.setChecked(false);
                lastUserInspection.setStopSupplyType7("0");
            }
            if (userRectifyInfo.getStopSupplyType8().equals("1")) {
                lastUserInspection.setStopSupplyType8("1");
                lastInspection.append(getResources().getString(R.string.StopSupplyType8)
                        + "\n");
            } else {
                lastUserInspection.setStopSupplyType8("0");
            }
        }else if(userRectifyInfo.getLast_InspectionStatus().equals("1")){
            lastUserInspection.setInspectionStatus("1");
            if (userRectifyInfo.getUnInstallType1().equals("1")) {
                UnInstallType1.setChecked(true);
                lastUserInspection.setUnInstallType1("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType1)
                        + "\n");
            } else {
                UnInstallType1.setChecked(false);
                lastUserInspection.setUnInstallType1("0");
            }
            if (userRectifyInfo.getUnInstallType2().equals("1")) {
                lastUserInspection.setUnInstallType2("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType2)
                        + "\n");
            } else {
                lastUserInspection.setUnInstallType2("0");
            }
            if (userRectifyInfo.getUnInstallType3().equals("1")) {
                UnInstallType3.setChecked(true);
                lastUserInspection.setUnInstallType3("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType3)
                        + "\n");

            } else {
                UnInstallType3.setChecked(false);
                lastUserInspection.setUnInstallType3("0");
            }
            if (userRectifyInfo.getUnInstallType4().equals("1")) {
                UnInstallType4.setChecked(true);
                lastUserInspection.setUnInstallType4("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType4)
                        + "\n");
            } else {
                UnInstallType4.setChecked(false);
                lastUserInspection.setUnInstallType4("0");
            }
            if (userRectifyInfo.getUnInstallType5().equals("1")) {
                lastUserInspection.setUnInstallType5("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType5)
                        + "\n");
            } else {
                lastUserInspection.setUnInstallType5("0");
            }
            if (userRectifyInfo.getUnInstallType6().equals("1")) {
                UnInstallType6.setChecked(true);
                lastUserInspection.setUnInstallType6("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType6)
                        + "\n");
            } else {
                UnInstallType6.setChecked(false);
                lastUserInspection.setUnInstallType6("0");
            }
            if (userRectifyInfo.getUnInstallType7().equals("1")) {
                lastUserInspection.setUnInstallType7("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType7)
                        + "\n");
            } else {
                lastUserInspection.setUnInstallType7("0");
            }
            if (userRectifyInfo.getUnInstallType8().equals("1")) {
                lastUserInspection.setUnInstallType8("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType8)
                        + "\n");
            } else {
                ;
                lastUserInspection.setUnInstallType8("0");
            }
            if (userRectifyInfo.getUnInstallType9().equals("1")) {
                UnInstallType9.setChecked(true);
                lastUserInspection.setUnInstallType9("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType9)
                        + "\n");
            } else {
                UnInstallType9.setChecked(false);
                lastUserInspection.setUnInstallType9("0");
            }
            if (userRectifyInfo.getUnInstallType10().equals("1")) {
                lastUserInspection.setUnInstallType10("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType10)
                        + "\n");
            } else {
                lastUserInspection.setUnInstallType10("0");
            }
            if (userRectifyInfo.getUnInstallType11().equals("1")) {
                UnInstallType11.setChecked(true);
                lastUserInspection.setUnInstallType11("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType11)
                        + "\n");
            } else {
                UnInstallType11.setChecked(false);
                lastUserInspection.setUnInstallType11("0");
            }
            if (userRectifyInfo.getUnInstallType12().equals("1")) {
                UnInstallType12.setChecked(true);
                lastUserInspection.setUnInstallType12("1");
                lastInspection.append(getResources().getString(R.string.UnInstallType12)
                        + "\n");
            } else {
                UnInstallType12.setChecked(false);
                lastUserInspection.setUnInstallType12("0");
            }
        }



    }
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

        upDate = (CheckBox) this.findViewById(R.id.update);
        repair = (CheckBox) this.findViewById(R.id.repair);
        stop = (CheckBox) this.findViewById(R.id.stop);
        result = (CheckBox) this.findViewById(R.id.result);
        btn_UpLoad=this.findViewById(R.id.upload);
        btn_UpLoad.setEnabled(false);//

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                String time= TimeUtils.getTime("yyyyMMddHHmmss");
                getImagePath(filePath,deviceid,getBasicInfo.getOperationID(),userID,time);
            }
        }
    }
    /**
     * 拍照事件
     *
     * @param view
     */
    public void takePhoto(View view) {
        takePhoto(deviceid, userID);
    }
    /***
     * 上传巡检信息
     * @param view
     */
    public void upload(View view){
        //上传巡检信息
        new UpLoadRectifyTask(RectiftyActivity.this,RectiftyActivity.this).execute(userID);
    }

    /**
     * 进入预览页面
     *
     * @param view
     */
    public void preview(View view) {
        preview();
    }

    /**
     * 打印签字
     * @param view
     */
    public void save(View view) {
        try{
            String inspectedDate = GetOperationTime.getCurrentTime();//获取巡检日期
            saveState();//保存巡检状态
            getRectifyResult();//
            userRectifyInfo.setInspectionDate(inspectedDate);
            picCount=0;
            isWrite();//检查照片数量
            if(picCount>0){
                    //是否已拍照
                isUpdate=rectifyDB.updateRectifyInfo(getBasicInfo.getOperationID(),getBasicInfo.getStationID(),userRectifyInfo);
                if(isUpdate){
                    btn_UpLoad.setEnabled(true);//
                    Toast.makeText(RectiftyActivity.this, "订单保存成功", Toast.LENGTH_SHORT).show();
                    new RectiftyActivity.PrintCodeTask(RectiftyActivity.this).execute();//直接打印
                }
            }else{
                //巡检完成请拍照
                Toast.makeText(getApplicationContext(), "巡检完成请拍照",
                        Toast.LENGTH_SHORT).show();
            }


        }catch(Exception ex){
            ex.toString();

        }
    }
    /**
     * 判断是否签字
     * @return
     */
    public boolean isWrite(){
        boolean ret=false;
        int flag = 0;
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
//            if (flag == 0) {
//                Toast.makeText(getApplicationContext(), "请先签字确认",
//                        Toast.LENGTH_SHORT).show();
//                return false;
//            } else {
//                return true;
//            }
        } else {
            ret=false;
            Toast.makeText(getApplicationContext(), "请先拍照确认",
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
                Intent intent = new Intent(this, RectifyPreviewActivity.class);
                intent.putExtra("ID",id);
                intent.putExtra("UserID",userID);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "请先拍照后在预览",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void takePhoto(String deviceId, String userid) {
        filePath = Environment.getExternalStorageDirectory() + "/photoes/"
                + deviceId + "s" + ".jpg";
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
    /*

    */
    public void  getImagePath(String filePath,String deviceid,String employee, String user,String format) {
        if (user != null) {
            File file = new File(filePath);
            if (file != null && file.exists()) {
                String path = PathUtil.getImagePath();
                File file1 = new File(path);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                String ImageFileName=relationID+"_" +format + "_" + "u" +userID+ ".jpg";
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
                rectifyDB.InsertRectifyAssociation(employee, userID, ImageFileName, relationID,FileName);//将照片信息插入到数据表中
            }
        }
    }

    /**
     * 保存巡检项同时把巡检项插入数据库
     */
    private void saveState(){
        boolean UNStatus = false;
        boolean StopStatus = false;
        if(UnInstallType1.isChecked()){
            UNStatus = true;
            userRectifyInfo.setUnInstallType1("1");
        }
        if(UnInstallType2.isChecked()){
            UNStatus = true;
            userRectifyInfo.setUnInstallType2("1");
        }
        if(UnInstallType3.isChecked()){
            UNStatus = true;
            userRectifyInfo.setUnInstallType3("1");
        }
        if(UnInstallType4.isChecked()){
            userRectifyInfo.setUnInstallType4("1");
            UNStatus = true;
        }
        if(UnInstallType5.isChecked()){
            UNStatus = true;
            userRectifyInfo.setUnInstallType5("1");
        }
        if(UnInstallType6.isChecked()){
            userRectifyInfo.setUnInstallType6("1");
            UNStatus = true;
        }
        if(UnInstallType7.isChecked()){
            UNStatus = true;
            userRectifyInfo.setUnInstallType7("1");
        }
        if(UnInstallType9.isChecked()){
            userRectifyInfo.setUnInstallType9("1");
            UNStatus = true;
        }
        if(UnInstallType11.isChecked()){
            userRectifyInfo.setUnInstallType11("1");
            UNStatus = true;
        }
        if(UnInstallType12.isChecked()){
            userRectifyInfo.setUnInstallType12("1");
            UNStatus = true;
        }
        if(StopSupplyType1.isChecked()){
            userRectifyInfo.setStopSupplyType1("1");
            StopStatus = true;
        }
        if(StopSupplyType2.isChecked()){
            userRectifyInfo.setStopSupplyType2("1");
            StopStatus = true;
        }
        if(StopSupplyType3.isChecked()){
            userRectifyInfo.setStopSupplyType3("1");
            StopStatus = true;
        }
        if(StopSupplyType4.isChecked()){
            userRectifyInfo.setStopSupplyType4("1");
            StopStatus = true;
        }
        if(StopSupplyType5.isChecked()){
            userRectifyInfo.setStopSupplyType5("1");
            StopStatus = true;
        }
        if(StopSupplyType6.isChecked()){
            userRectifyInfo.setStopSupplyType6("1");
            StopStatus = true;
        }
        if(StopSupplyType7.isChecked()){
            userRectifyInfo.setStopSupplyType7("1");
            StopStatus = true;
        }
        if(StopSupplyType8.isChecked()){
            userRectifyInfo.setStopSupplyType8("1");
            StopStatus = true;
        }
        String OperationResult="";
        if (upDate.isChecked()) {
            OperationResult += getResources().getString(R.string.update)+",";
        }
        if (repair.isChecked()) {
            OperationResult += getResources().getString(R.string.repair)+",";
        }
        if (stop.isChecked()) {
            OperationResult += getResources().getString(R.string.stop)+",";
        }
        if (result.isChecked()) {
            OperationResult += getResources().getString(R.string.result)+",";
        }
        if(!OperationResult.equals("")){
            OperationResult=OperationResult.substring(0, OperationResult.length()-1);
        }
        userRectifyInfo.setOperationResult(OperationResult);
        userRectifyInfo.setRelationID(relationID);
        if (StopStatus) {
            userRectifyInfo.setInspectionStatus("2");
            userRectifyInfo.setIsInspected("1");// 本地标识：做过巡检
            userRectifyInfo.setUnInstallType1("0");
            userRectifyInfo.setUnInstallType2("0");
            userRectifyInfo.setUnInstallType3("0");
            userRectifyInfo.setUnInstallType4("0");
            userRectifyInfo.setUnInstallType5("0");
            userRectifyInfo.setUnInstallType6("0");
            userRectifyInfo.setUnInstallType7("0");
            userRectifyInfo.setUnInstallType9("0");
            userRectifyInfo.setUnInstallType11("0");
            userRectifyInfo.setUnInstallType12("0");

        } else if (UNStatus) {
            userRectifyInfo.setInspectionStatus("1");
            userRectifyInfo.setIsInspected("1");
            userRectifyInfo.setStopYY("");
            userRectifyInfo.setStopSupplyType1("0");
            userRectifyInfo.setStopSupplyType2("0");
            userRectifyInfo.setStopSupplyType3("0");
            userRectifyInfo.setStopSupplyType4("0");
            userRectifyInfo.setStopSupplyType5("0");
            userRectifyInfo.setStopSupplyType6("0");
            userRectifyInfo.setStopSupplyType7("0");
            userRectifyInfo.setStopSupplyType8("0");
        } else {
            userRectifyInfo.setInspectionStatus("0");
            userRectifyInfo.setIsInspected("1");
            userRectifyInfo.setStopYY("");
            userRectifyInfo.setStopSupplyType1("0");
            userRectifyInfo.setStopSupplyType2("0");
            userRectifyInfo.setStopSupplyType3("0");
            userRectifyInfo.setStopSupplyType4("0");
            userRectifyInfo.setStopSupplyType5("0");
            userRectifyInfo.setStopSupplyType6("0");
            userRectifyInfo.setStopSupplyType7("0");
            userRectifyInfo.setStopSupplyType8("0");
            userRectifyInfo.setUnInstallType1("0");
            userRectifyInfo.setUnInstallType2("0");
            userRectifyInfo.setUnInstallType3("0");
            userRectifyInfo.setUnInstallType4("0");
            userRectifyInfo.setUnInstallType5("0");
            userRectifyInfo.setUnInstallType6("0");
            userRectifyInfo.setUnInstallType7("0");
            userRectifyInfo.setUnInstallType9("0");
            userRectifyInfo.setUnInstallType11("0");
            userRectifyInfo.setUnInstallType12("0");
        }

    }
    /**
     * 获取一般隐患具体名称（用于历史记录）
     *
     * @return
     */
    private String getRectifyResult() {
        rectifyInfo=new StringBuffer();
        if(userRectifyInfo.getInspectionStatus().equals("2")){
            if(StopSupplyType1.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.StopSupplyType1)+"\n");
            }
            if(StopSupplyType2.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.StopSupplyType2)+"\n");
            }
            if(StopSupplyType3.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.StopSupplyType3)+"\n");
            }
            if(StopSupplyType4.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.StopSupplyType4)+"\n");
            }

            if(StopSupplyType5.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.StopSupplyType5)+"\n");
            }
            if(StopSupplyType6.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.StopSupplyType6)+"\n");
            }
            if(StopSupplyType7.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.StopSupplyType7)+"\n");
            }
            if(StopSupplyType8.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.StopSupplyType8));
            }
        }else if(userRectifyInfo.getInspectionStatus().equals("1")){
            if(UnInstallType1.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType1)+"\n");
            }
            if(UnInstallType2.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType2)+"\n");
            }
            if(UnInstallType3.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType3)+"\n");
            }
            if(UnInstallType4.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType4)+"\n");
            }
            if(UnInstallType5.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType5)+"\n");
            }
            if(UnInstallType6.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType6)+"\n");
            }
            if(UnInstallType7.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType7)+"\n");
            }
            if(UnInstallType9.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType9)+"\n");
            }
            if(UnInstallType11.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType11)+"\n");
            }
            if(UnInstallType12.isChecked()){
                rectifyInfo.append(getResources().getString(R.string.UnInstallType12));
            }
        }
        return rectifyInfo.toString();
    }

    @Override
    public void UploadRectifyTaskEnd(HsicMessage tag) {
        if(tag.getRespCode()==0){
            new AlertView("提示", "整改信息上传成功", null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }else{
            new AlertView("提示", tag.getRespMsg(), null, new String[]{"确定"},
                    null, this, AlertView.Style.Alert, this)
                    .show();
        }

    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        RectiftyActivity.this.finish();
    }

    /**
     * 打印
     */
    public class PrintCodeTask extends AsyncTask<Void, Void, Void> {
        private byte[] saleID;
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
                int pCount = 3;
//                int pCount = 1;
//                pCount = Integer.parseInt("1");
                //测试(最新测试)
                String Intret = connectBT();
                PrintUtils.setOutputStream(outputStream);
                for (int a = 0; a < pCount; a++) {
                    PrintUtils.selectCommand(PrintUtils.RESET);
                    PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
                    PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
                    PrintUtils.selectCommand(PrintUtils.BOLD);
                    PrintUtils.printText("\n");
                    PrintUtils.printText("\n");//公司
                    PrintUtils.printText("整改单\n");
                    PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
//                    PrintUtils.selectCommand(PrintUtils.NORMAL);
//                    PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
                    PrintUtils.selectCommand(PrintUtils.NORMAL);
                    PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
//					PrintUtils.printText(PrintUtils.printTwoData("整改单", Sale.getSaleID() + "\n"));
                    PrintUtils.printText(PrintUtils.printTwoData("整改用户", UserName + "\n"));
                    PrintUtils.printText(DeliverAddress + "\n");
                    PrintUtils.printText("--------------------------------\n");
                    if (!lastInspection.toString().equals("")) {
                        PrintUtils.printText("上次巡检不合格项:" + "\n");
                        PrintUtils.printText(lastInspection.toString());
                    }
                    PrintUtils.printText("--------------------------------\n");
                    if (!rectifyInfo.toString().equals("")) {
                        PrintUtils.printText("本次安检结果:未整改" + "\n");
                        PrintUtils.printText("本次巡检不合格项:" + "\n");
                        PrintUtils.printText(rectifyInfo.toString() + "\n");
                    } else {
                        PrintUtils.printText("本次安检结果:已整改" + "\n");
                    }
                    PrintUtils.printText("--------------------------------\n");
                    if (Remark != null) {
                        if (!Remark.equals("")) {
                            PrintUtils.printText(PrintUtils.printTwoData("整改人:", RectifyName + "\n"));
                            PrintUtils.printText(PrintUtils.printTwoData("整改日期:", userRectifyInfo.getInspectionDate() + "\n"));
                            PrintUtils.printText(PrintUtils.printTwoData("备注:", Remark + "\n"));
                        } else {
                            PrintUtils.printText(PrintUtils.printTwoData("整改人:", RectifyName + "\n"));
                            PrintUtils.printText(PrintUtils.printTwoData("整改日期:", userRectifyInfo.getInspectionDate() + "\n"));
                        }
                    } else {
                        PrintUtils.printText(PrintUtils.printTwoData("整改人:", RectifyName + "\n"));
                        PrintUtils.printText(PrintUtils.printTwoData("整改日期:", userRectifyInfo.getInspectionDate() + "\n"));
                    }

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
}
