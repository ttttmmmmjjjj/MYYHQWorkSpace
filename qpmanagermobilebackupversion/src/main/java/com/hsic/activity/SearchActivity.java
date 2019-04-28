package com.hsic.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.Toast;

import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.bean.ApplicationHelper;
import com.hsic.bean.UserXJInfo;
import com.hsic.bll.GetBasicInfo;
import com.hsic.bll.GetOperationTime;
import com.hsic.bll.PathUtil;
import com.hsic.picture.SavePic;
import com.hsic.qpmanager.util.json.JSONUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    UserXJInfo userXJInfo;
    ApplicationHelper applicationHelper;
    String deviceid = "";
    private TabHost mTabHost;
    private CheckBox StopSupplyType1, StopSupplyType2, StopSupplyType3,
            StopSupplyType4, StopSupplyType5, StopSupplyType6, StopSupplyType7,StopSupplyType8,
            UnInstallType1, UnInstallType2, UnInstallType3, UnInstallType4,
            UnInstallType5,UnInstallType6, UnInstallType7,
            UnInstallType9,  UnInstallType11, UnInstallType12;// S:停止供气；N:不予接装
    private List<CheckBox> StopSupply = new ArrayList<CheckBox>();
    private List<CheckBox> UnInstall = new ArrayList<CheckBox>();
    private boolean isChecked = false;
    private String TAG = "SearchingActivity";
    private boolean isImage = true;
    private boolean isImageN = false;
    public static String filePath;
    String stop = "";
    String userId = "";
    String saleId = "";
    String inspecstatus = "";
    private CheckBox  IsInstall;
    GetBasicInfo getBasicInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        userXJInfo=new UserXJInfo();
        userXJInfo.setInspectionStatus(null);
        applicationHelper=(ApplicationHelper)getApplication();
        userXJInfo=applicationHelper.getUserXJInfo();
        getBasicInfo=new GetBasicInfo(SearchActivity.this);
        deviceid=getBasicInfo.getDeviceID();
        saleId=userXJInfo.getSaleid();
        userId=userXJInfo.getUserid();
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

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int photoesCount = initData();
            String temp = "";
            temp = userXJInfo.getInspectionStatus();
            if (temp != null) {
                if (!(inspecstatus.equals("0") && temp.equals("0"))) {
                    if (photoesCount <= 0) {
                        Dialog("必须拍照！", SearchActivity.this);
                        return true;
                    }
//                    //强制年度安检
//                    if(isYearInspect!=null){
//                        if(isYearInspect.equals("1")){
//                            if(userXJInfo.getIsYearInspect()==null){
//                                Dialog("必须“年度安检”!", SearchActivity.this);
//                                return true;
//                            }
//                        }
//                    }
//                    //强制是否接装到位
//                    String IsInstall="";
//                    IsInstall=applicationHelper.getUserXJInfo().getIsInstall();
//                    if(IsInstall==null){
//                        Dialog("必须确定“是否接装到位”!", SearchActivity.this);
//                        return true;
//                    }
                } else {
                    return super.onKeyDown(keyCode, event);
                }
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
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
    public void click3(View view) {
        try{
            String inspectedDate = GetOperationTime.getCurrentTime();//获取巡检日期
            int photoesCount = initData();
            saveState();//保存巡检状态
            String temp = "";
            temp = userXJInfo.getInspectionStatus();
            if (!(inspecstatus.equals("0") && temp.equals("0"))) {
                if (photoesCount <= 0) {
                    Toast.makeText(getApplicationContext(), "请拍照",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
//            if(isYearInspect!=null){
//                if(isYearInspect.equals("1")){
//                    if(userXJInfo.getIsYearInspect()==null){
//                        Toast.makeText(getApplicationContext(), "必须“年度安检”!",
//                                Toast.LENGTH_SHORT).show();
//                        return ;
//                    }
//                }
//            }

            if (userXJInfo.getInspectionStatus().equals("2")) {
                userXJInfo.setStopYY(stop());
            }

//            if(RefuseInspection!=null){
//                if (RefuseInspection.isChecked()) {
//                    userXJInfo.setRefuseInspection("1");
//                }else{
//                    userXJInfo.setRefuseInspection("0");
//                }
//            }
//            if(ErrorAddress!=null){
//                if (ErrorAddress.isChecked()) {
//                    userXJInfo.setErrorAddress("1");
//                }else{
//                    userXJInfo.setErrorAddress("0");
//                }
//            }
//            if(ErrorNature!=null){
//                if (ErrorNature.isChecked()) {
//                    userXJInfo.setErrorNature("1");
//                }else{
//                    userXJInfo.setErrorNature("0");
//                }
//            }
//            if(GasItem!=null){
//                if (GasItem.isChecked()) {
//                    userXJInfo.setGasItem("1");
//                }else{
//                    userXJInfo.setGasItem("0");
//                }
//            }
//            if(LiquidItem!=null){
//                if (LiquidItem.isChecked()) {
//                    userXJInfo.setLiquidItem("1");
//                }else{
//                    userXJInfo.setLiquidItem("0");
//                }
//            }
            userXJInfo.setErrorAddress("0");
            userXJInfo.setErrorNature("0");
            userXJInfo.setRefuseInspection("0");
            userXJInfo.setNoAlarm("0");
            userXJInfo.setInspectionDate(inspectedDate);
            userXJInfo.setInspectionMan(applicationHelper.getStaffID());
            applicationHelper.setUserXJInfo(userXJInfo);
//            if(userXJInfo.getInspectionStatus().equals("2")){
////                SaleIsGONO();
//            }else{
////                String IsInstall="";
////                IsInstall=applicationHelper.getUserXJInfo().getIsInstall();
////                if(IsInstall==null){
////                    Toast.makeText(getApplicationContext(), "必须确定“是否接装到位”!",
////                            Toast.LENGTH_SHORT).show();
////                    return ;
////                }
//                SearchActivity.this.finish();
//            }
            SearchActivity.this.finish();
        }catch(Exception ex){
            ex.toString();
//            MyLog.e("保存巡检出现异常", ex.toString());

        }


    }
     /* 把文件路径插入数据库
	 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                SavePic.getImagePath(filePath,deviceid,"",saleId, userId);
            }
        }

    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        applicationHelper.setUserXJInfo(userXJInfo);
    }
    @SuppressLint("SimpleDateFormat")
    public void takePhotoes(String user, String checkSaleid) {
        isImage = false;
        isImageN = false;

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
    public void preview() {
        int flag = 0;
        String filePath = PathUtil.getImagePath();
        File file = new File(filePath);
        String[] paths = file.list();
        if (paths != null && paths.length > 0) {
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (path.contains(deviceid + "s" + saleId )) {
                    flag++;
                }
            }
            if (flag == 0) {
                Toast.makeText(getApplicationContext(), "请先拍照后在预览",
                        Toast.LENGTH_SHORT).show();
                return;
            } else {
                Intent intent = new Intent(this, PreviewActivity.class);
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
        String relationID = "";
        relationID = deviceid + "s" + saleId;
        userXJInfo.setRelationID(relationID);
//        userXJInfo.setInspectionMan();
        if (StopStatus) {
            userXJInfo.setInspectionStatus("2");
            /**
             * 9月27改
             */
//            OperatorLog.i("巡检结果", "停止供气");
//            OperatorLog.i("巡检时间", com.hsic.qpmanager.util.TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));

            userXJInfo.setIsInspected("1");// 本地标识：做过巡检
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
            userXJInfo.setPrintInfo(stop());
            applicationHelper.setUserXJInfo(userXJInfo);
            return;

        } else if (UNStatus) {
            userXJInfo.setInspectionStatus("1");
            /**
             * 9月27改
             */
//            OperatorLog.i("巡检结果", "不予接装");
//            OperatorLog.i("巡检时间", com.hsic.qpmanager.util.TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));

            userXJInfo.setIsInspected("1");
            userXJInfo.setStopYY("");
            userXJInfo.setStopSupplyType1("0");
            userXJInfo.setStopSupplyType2("0");
            userXJInfo.setStopSupplyType3("0");
            userXJInfo.setStopSupplyType4("0");
            userXJInfo.setStopSupplyType5("0");
            userXJInfo.setStopSupplyType6("0");
            userXJInfo.setStopSupplyType7("0");
            userXJInfo.setStopSupplyType8("0");
            userXJInfo.setPrintInfo(unistYY());
            applicationHelper.setUserXJInfo(userXJInfo);

            return;
        } else {
            userXJInfo.setInspectionStatus("0");
            /**
             * 9月27改
             */
//            OperatorLog.i("巡检结果", "合格");
//            OperatorLog.i("巡检时间", com.hsic.qpmanager.util.TimeUtils.getTime("yyyy-MM-dd HH:mm:ss"));

            userXJInfo.setIsInspected("1");
            userXJInfo.setStopYY("");
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
            applicationHelper.setUserXJInfo(userXJInfo);
            return;
        }
    }
    private int initData() {
        ArrayList<String> mList = new ArrayList<String>();
        String photoesPattern = deviceid + "s" + saleId ;
        String filePath = PathUtil.getImagePath();
        File file = new File(filePath);
        String[] paths = file.list();
        if (paths != null && paths.length > 0) {
            for (int i = 0; i < paths.length; i++) {
                String path = filePath + paths[i];
                if (path.contains(photoesPattern)) {
                    mList.add(path);
                }
            }

        }
        return mList.size();
    }
    public String stop() {
        StringBuffer sb = new StringBuffer();
        int j = 1;
        for (CheckBox box : StopSupply) {
            if (box.isChecked()) {
                if (sb.toString().length() == 0) {
                    sb.append(getStopitem(j));
                } else {
                    sb.append("," + getStopitem(j));
                }
            }
            j++;
        }
        return sb.toString();

    }
    private String getStopitem(int i) {
        String item = "";
        switch (i) {
            case 1:
                item = getResources().getString(R.string.StopSupplyType1);
                break;
            case 2:
                item = getResources().getString(R.string.StopSupplyType2);
                break;
            case 3:
                item = getResources().getString(R.string.StopSupplyType3);
                break;
            case 4:
                item = getResources().getString(R.string.StopSupplyType4);
                break;
            case 5:
                item = getResources().getString(R.string.StopSupplyType5);
                break;
            case 6:
                item = getResources().getString(R.string.StopSupplyType6);
                break;
            case 7:
                item = getResources().getString(R.string.StopSupplyType7);
                break;
            case 8:
                item = getResources().getString(R.string.StopSupplyType8);
                break;
            default:
                break;
        }
        return item;
    }
    /**
     * 交易是否继续
     */

    protected void SaleIsGONO() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
        builder.setMessage("巡检结果为停止供气，请选择是否继续进行交易");
        builder.setTitle("提示");
        builder.setIcon(R.drawable.sy_dialog);
        builder.setPositiveButton("交易继续",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
//                        /**
//                         * 提醒强制安检
//                         */
//                        String IsInstall="";
//                        IsInstall=applicationHelper.getUserXJInfo().getIsInstall();
//                        if(IsInstall==null){
//                            Toast.makeText(getApplicationContext(), "必须确定“是否接装到位”!",
//                                    Toast.LENGTH_SHORT).show();
//                            return ;
//                        }
                        userXJInfo.setStopYY("");
                        SearchActivity.this.finish();

                    }
                });

        builder.setNegativeButton("交易作废",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        // 弹出验证码
                        Intent i = new Intent(SearchActivity.this,
                                CodeActivity.class);
                        startActivityForResult(i, 12);
                    }
                });
        builder.create().show();
    }
    public void Dialog(String title, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context); // 先得到构造器
        builder.setTitle("警告"); // 设置标题
        builder.setMessage(title); // 设置内容
        builder.setIcon(R.drawable.sy_dialog);// 设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { // 设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
    private String unistYY() {
        StringBuffer sb = new StringBuffer();
        String item = "";
        if(UnInstallType1.isChecked()){
            item = getResources().getString(R.string.UnInstallType1);
            sb.append(item+",");
        }
        if(UnInstallType2.isChecked()){
            item = getResources().getString(R.string.UnInstallType2);
            sb.append(item+",");
        }
        if(UnInstallType3.isChecked()){
            item = getResources().getString(R.string.UnInstallType3);
            sb.append(item+",");
        }
        if(UnInstallType4.isChecked()){
            item = getResources().getString(R.string.UnInstallType4);
            sb.append(item+",");
        }
        if(UnInstallType5.isChecked()){
            item = getResources().getString(R.string.UnInstallType5);
            sb.append(item+",");
        }
        if(UnInstallType6.isChecked()){
            item = getResources().getString(R.string.UnInstallType6);
            sb.append(item+",");
        }
        if(UnInstallType7.isChecked()){
            item = getResources().getString(R.string.UnInstallType7);
            sb.append(item+",");
        }
        if(UnInstallType9.isChecked()){
            item = getResources().getString(R.string.UnInstallType9);
            sb.append(item+",");
        }
        if(UnInstallType11.isChecked()){
            item = getResources().getString(R.string.UnInstallType11);
            sb.append(item+",");
        }
        if(UnInstallType12.isChecked()){
            item = getResources().getString(R.string.UnInstallType12);
            sb.append(item+",");
        }
        int length=sb.toString().length();
        item=sb.toString().substring(0, length-1);
        return item;
    }
}
