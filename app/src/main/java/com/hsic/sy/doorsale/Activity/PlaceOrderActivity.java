package com.hsic.sy.doorsale.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.spinner.AbstractSpinerAdapter;
import com.hsic.sy.spinner.SpinerPopWindow;
import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.dialoglibrary.OnItemClickListener;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.doorsale.bean.UserInfoDoorSale;
import com.hsic.sy.doorsale.dialog.SaleCodeDialog;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.bll.SaleCode;
import com.hsic.sy.doorsale.lisenter.ImplGetBasicUserInfoTask;
import com.hsic.sy.doorsale.lisenter.ImplGetDevicesTask;
import com.hsic.sy.doorsale.lisenter.ImplSubscriberOrderTask;
import com.hsic.sy.doorsale.adapter.AddGoodsListAdapter;
import com.hsic.sy.doorsale.task.GetBasicUserInfoTask;
import com.hsic.sy.doorsale.task.GetDevicesTask;
import com.hsic.sy.doorsale.task.SubscriberOrderTask;
import com.hsic.sy.supplystationmanager.R;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 下订单
 */
public class PlaceOrderActivity extends AppCompatActivity implements ImplGetBasicUserInfoTask,
        AbstractSpinerAdapter.IOnItemSelectListener,
        ImplSubscriberOrderTask,ImplGetDevicesTask,OnDismissListener, OnItemClickListener {
    private SearchView mSearchView;//搜索框
    private ListView listView;
    UserInfoDoorSale userInfoDoorSale;
    private String requestStr,deviceIDStr;
    private TextView txt_userID,txt_userAddress,txt_goods,txt_username,txt_EmptyNo,
            txt_stationName,txt_phoneNum;
    private Dialog mGoodsDialog;//
    private LinearLayout root;
    private TextView mtvGoodsName;
    private EditText edt_GoodsCounts;
    private Button btn_upGoods,btn_upAccessory,btn_scanEmpty,btn_submit;
    private String upGoodsType;//1.气瓶，2.配件
    private String goodsName,goodsCode;
    List<UserInfoDoorSale.GoodsInfo> goodsInfo_List;//下载到的商品信息
    private String edt_Input;
    List<UserInfoDoorSale.GoodsInfo> goodsInfo_List_add;//订单新增商品信息 (新增)
    List<UserInfoDoorSale.GoodsInfo> goodsInfo_List_Accessary;//订单新增商品信息 (下拉列表信息来源)
    List<UserInfoDoorSale.GoodsInfo> goodsInfo_List_Goods;//订单新增商品信息 (下拉列表信息来源)
    private List<UserInfoDoorSale.GoodsInfo> nameList;//可购买商品信息列表
    private SpinerPopWindow mSpinerPopWindow;
    Toolbar toolbar;
    GetBasicInfo basicInfo;
    private String EmptyNO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subsorderlayout);
        basicInfo=new GetBasicInfo(PlaceOrderActivity.this);
        deviceIDStr= basicInfo.getDeviceID();
        txt_stationName=this.findViewById(R.id.txt_stationName);
        txt_userID=this.findViewById(R.id.txt_userID);
        txt_userAddress=this.findViewById(R.id.txt_userAddress);
        txt_goods=this.findViewById(R.id.txt_goods);
        txt_username=this.findViewById(R.id.txt_username);
        txt_phoneNum=this.findViewById(R.id.txt_phoneNum);
        txt_EmptyNo=this.findViewById(R.id.txt_EmptyNo);
        txt_EmptyNo.setText("");
        listView=this.findViewById(R.id.listView);
        btn_upGoods=this.findViewById(R.id.btn_upGoods);
        btn_upAccessory=this.findViewById(R.id.btn_upAccessory);
        btn_scanEmpty=this.findViewById(R.id.btn_scanEmpty);
        btn_submit=this.findViewById(R.id.btn_submit);
        mSearchView=this.findViewById(R.id.search);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();
        /**
         * 设置提示框字体颜色
         * SearchView去掉默认的下划线
         */
        if(mSearchView == null) { return;}
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        textView.setTextColor(Color.BLACK);//字体颜色
        textView.setTextSize(18);//字体、提示字体大小
        textView.setHintTextColor(Color.GRAY);//提示字体颜色
        if (mSearchView != null) {
            try {        //--拿到字节码
                Class<?> argClass = mSearchView.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(mSearchView);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        goodsInfo_List_add=new ArrayList<UserInfoDoorSale.GoodsInfo>();
        goodsInfo_List=new ArrayList<UserInfoDoorSale.GoodsInfo>();
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                edt_Input=mSearchView.getQuery().toString();
                if(TextUtils.isEmpty(edt_Input)){
                    Toast.makeText(getBaseContext(),"查询条件不能为空",Toast.LENGTH_SHORT).show();
                    return false;
                }
                edt_Input=addZero(edt_Input,8);
                userInfoDoorSale=new UserInfoDoorSale();
                userInfoDoorSale.setUserID(edt_Input);
                requestStr= JSONUtils.toJsonWithGson(userInfoDoorSale);
                GetBasicUserInfoTask getTask=new GetBasicUserInfoTask(PlaceOrderActivity.this,PlaceOrderActivity.this,deviceIDStr,requestStr);
                getTask.execute();
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                }else{
                }
                return false;
            }
        });
        userInfoDoorSale=new UserInfoDoorSale();
        EmptyNO="";
        InnitGoodsDialog();
        /**
         *
         */
        btn_upGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=txt_userID.getText().toString();
                if(TextUtils.isEmpty(userid)){
                    Toast.makeText(getBaseContext(),"请先查询用户信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                setmSpinerPopWindow(goodsInfo_List_Goods);
                upGoodsType="1";
                mtvGoodsName.setText("");
                edt_GoodsCounts.setText("");
                mGoodsDialog.show();
            }
        });
        btn_upAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=txt_userID.getText().toString();
                if(TextUtils.isEmpty(userid)){
                    Toast.makeText(getBaseContext(),"请先查询用户信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                upGoodsType="2";
                mtvGoodsName.setText("");
                edt_GoodsCounts.setText("");
                new GetDevicesTask(PlaceOrderActivity.this,PlaceOrderActivity.this).execute();
                mGoodsDialog.show();
            }
        });
        btn_scanEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=txt_userID.getText().toString();
                if(TextUtils.isEmpty(userid)){
                    Toast.makeText(getBaseContext(),"请先查询用户信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent scan=new Intent(PlaceOrderActivity.this,ScanQPTagActivity.class);
                scan.putExtra("QPNO",EmptyNO);
                startActivityForResult(scan,1);

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交订单
                String userid=txt_userID.getText().toString();
                if(TextUtils.isEmpty(userid)){
                    Toast.makeText(getBaseContext(),"请先查询用户信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                String empty=(userInfoDoorSale.getEmptyNO()==null? "" :userInfoDoorSale.getEmptyNO());

                if(TextUtils.isEmpty(empty)){
                    Toast.makeText(getBaseContext(),"请先扫空瓶",Toast.LENGTH_SHORT).show();
                    return;
                }
                EmptyNO=userInfoDoorSale.getEmptyNO();
                int size=goodsInfo_List_add.size();
                if(size>0){
                    userInfoDoorSale.setPayMode("C");
                    userInfoDoorSale.setGoodsInfo(goodsInfo_List_add);
                    requestStr= JSONUtils.toJsonWithGson(userInfoDoorSale);
                    SubscriberOrderTask task=new SubscriberOrderTask(PlaceOrderActivity.this,PlaceOrderActivity.this,deviceIDStr,requestStr);
                    task.execute();
                }else{
                    Toast.makeText(getBaseContext(),"无可提交的商品",Toast.LENGTH_SHORT).show();
                    return;
                }
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
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.inflateMenu(R.menu.pickgoods_toolbar_menu);
//        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        toolbar.setTitle(basicInfo.getStationName());
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if(menuItemId==R.id.action_search){
//                    Toast.makeText(getBaseContext(),"用户卡二维码",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }
    // 是否退出程序
    private static Boolean isExit = false;
    // 定时触发器
    private static Timer tExit = null;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (isExit == false) {
//                isExit = true;
//                if (tExit != null) {
//                    tExit.cancel(); // 将原任务从队列中移除
//                }
//                // 重新实例一个定时器
//                tExit = new Timer();
//                TimerTask task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        isExit = false;
//                    }
//                };
//                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                // 延时两秒触发task任务
//                tExit.schedule(task, 2000);
//            } else {
//                finish();
//                System.exit(0);
//            }
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            Bundle b=data.getExtras(); //data为B中回传的Intent
            String userRegionCode=b.getString("userReginCode");
            userInfoDoorSale.setEmptyNO(userRegionCode);
            txt_EmptyNo.setText("空瓶号:"+userRegionCode);
            EmptyNO=userRegionCode;
        }
    }


    private void showSpinWindow() {
        mSpinerPopWindow.setWidth(mtvGoodsName.getWidth());
        mSpinerPopWindow.showAsDropDown(mtvGoodsName);
        mtvGoodsName.setFocusable(false);
    }
    /**
     *
     */
    private void InnitGoodsDialog(){
        mGoodsDialog = new Dialog(this, R.style.my_dialog);
        root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.goods_layout, null);
        root.findViewById(R.id.btn_cansel).setOnClickListener(btnListener);
        root.findViewById(R.id.btn_sure).setOnClickListener(btnListener);
        mtvGoodsName = (EditText) root.findViewById(R.id.edt_brand);
        mtvGoodsName.setText("");
        edt_GoodsCounts= (EditText) root.findViewById(R.id.edt_quailty);
        edt_GoodsCounts.setText("");
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
    }
    /**
     *
     */
    private View.OnClickListener btnListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_cansel:
                    mGoodsDialog.dismiss();
                    break;
                case R.id.btn_sure://保存数据
                    //数量，品牌都不能为空
                    String goodsName="",goodsCount="";
                    goodsName=mtvGoodsName.getText().toString();
                    goodsCount=edt_GoodsCounts.getText().toString();
                    int goodsCount_I=0;
                    if(TextUtils.isEmpty(goodsName)){
                        Toast.makeText(getBaseContext(),"商品名称不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(goodsCount)){
                        Toast.makeText(getBaseContext(),"商品数量不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        goodsCount_I=Integer.parseInt(goodsCount);
                        if(goodsCount_I<=0){
                            Toast.makeText(getBaseContext(),"商品数量必须大于0",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //用户购买的商品数不能超过最大限度
                        int maxGoodsCount=0;
                        if(upGoodsType.equals("1")){
                            for(int i=0;i<goodsInfo_List.size();i++){
                                if(goodsCode.equals(goodsInfo_List.get(i).getGoodsCode())){
                                    String  temp=goodsInfo_List.get(i).getGoodsCount();
                                    maxGoodsCount=Integer.parseInt(temp);
                                    break;

                                }
                            }
                            if(goodsCount_I>maxGoodsCount){
                                Toast.makeText(getBaseContext(),"超过该商品购买最大数量",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    UserInfoDoorSale.GoodsInfo goodsInfo;
                    goodsInfo=userInfoDoorSale.new GoodsInfo();
                    goodsInfo.setGoodsName(goodsName);
                    goodsInfo.setGoodsCount(goodsCount);
                    goodsInfo.setGoodsCode(goodsCode);
                    if(upGoodsType.equals("1")){
                        goodsInfo.setGoodsType("1");
                    }else if(upGoodsType.equals("2")){
                        goodsInfo.setGoodsType("2");
                    }
                    int size=goodsInfo_List_add.size();
                    if(size>0){
                        for(int i=0;i<size;i++){
                            String temp=goodsInfo_List_add.get(i).getGoodsCode();
                            if(goodsCode.equals(temp)){
                                UserInfoDoorSale.GoodsInfo goodsInfo2;
                                goodsInfo2=goodsInfo_List_add.get(i);
                                goodsInfo_List_add.remove(goodsInfo2);
                                break;
                            }
                        }
                        goodsInfo_List_add.add(goodsInfo);

                    }else{
                        goodsInfo_List_add.add(goodsInfo);
                    }
                    setAdapter(false);
                    mGoodsDialog.dismiss();
                    break;
            }
        }
    };

    /**
     * 获取用户基本信息
     * @param tag
     */
    @Override
    public void GetBasicUserInfoTaskListenerEnd(HsicMessage tag) {
        hintKeyBoard();
        if(tag.getRespCode()==0){
            /**
             * 用户基本信息查询完成以后，初始化
             */
            goodsInfo_List_Goods=new ArrayList<UserInfoDoorSale.GoodsInfo>();
            goodsInfo_List_add=new ArrayList<UserInfoDoorSale.GoodsInfo>();
            goodsInfo_List=new ArrayList<UserInfoDoorSale.GoodsInfo>();
            setAdapter(false);
            Type typeOfT = new TypeToken<List<UserInfoDoorSale>>() {
            }.getType();
            List<UserInfoDoorSale> UserInfo_LIST = new ArrayList<UserInfoDoorSale>();
            UserInfo_LIST = JSONUtils.toListWithGson(tag.getRespMsg(), typeOfT);
            userInfoDoorSale=new UserInfoDoorSale();
            userInfoDoorSale=UserInfo_LIST.get(0);
            txt_stationName.setText("用户站点:"+userInfoDoorSale.getStationName());
            txt_userID.setText("用户编号:"+userInfoDoorSale.getUserID());
            txt_username.setText("用户姓名:"+userInfoDoorSale.getUserName()+"["+userInfoDoorSale.getUserType()+"]");
            txt_userAddress.setText("地址:"+userInfoDoorSale.getAddress());
            txt_phoneNum.setText("电话:"+userInfoDoorSale.getPhoneNumber());
            goodsInfo_List=userInfoDoorSale.getGoodsInfo();
            goodsInfo_List_Goods=userInfoDoorSale.getGoodsInfo();
            int size=goodsInfo_List.size();
            String goods="";
            if(size>0){
                for(int i=0;i<size;i++){
                    goods+=goodsInfo_List.get(i).getGoodsName()+"/"+goodsInfo_List.get(i).getGoodsCount()+",";
                }
                if(goods.length()>1){
                    goods=goods.substring(0,goods.length()-1);
                }
            }

            txt_goods.setText("可购商品:"+goods);
            setmSpinerPopWindow(goodsInfo_List_Goods);
        }else{
            shownDialog(tag.getRespMsg());
        }
    }

    @Override
    public void onItemClick(int pos) {
        goodsCode="";
        goodsName="";
        if (pos >= 0 && pos <= nameList.size()) {
            String value = nameList.get(pos).getGoodsName();
            String key=nameList.get(pos).getGoodsCode();
//            Log.e("key="+key,"value="+value);
            goodsCode=key;
            goodsName=value;
            mtvGoodsName.setText(value);
            mSpinerPopWindow.dismiss();
        }
    }

    /**
     * 设置可购买商品信息列表
     * @param goodsInfo
     */
    private void setmSpinerPopWindow(List<UserInfoDoorSale.GoodsInfo> goodsInfo){
        nameList = new ArrayList<UserInfoDoorSale.GoodsInfo>();
        int size=goodsInfo.size();
        for(int i=0;i<size;i++){
            nameList.add(goodsInfo.get(i));
        }
        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.refreshData(nameList, 0);
        mSpinerPopWindow.setItemListener(this);
        mtvGoodsName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mtvGoodsName.getWindowToken(),0);
                mSpinerPopWindow.refreshData(nameList, 0);
                showSpinWindow();
            }
        });
    }

    /**
     * 下订单
     * @param tag
     */
    @Override
    public void SubscriberOrderTaskListenerEnd(HsicMessage tag) {
        hintKeyBoard();
        try{
            if(tag.getRespCode()==0){
                EmptyNO="";
                UserInfoDoorSale userInfo=new UserInfoDoorSale();
                List<UserInfoDoorSale.GoodsInfo> goodsInfo_List2=new ArrayList<UserInfoDoorSale.GoodsInfo>();
                userInfo=JSONUtils.toObjectWithGson(tag.getRespMsg(),UserInfoDoorSale.class);
                goodsInfo_List2=userInfo.getGoodsInfo();
                int size=goodsInfo_List2.size();
                StringBuffer goodsInfo=new StringBuffer();
                for(int i=0;i<size;i++){
                    goodsInfo.append(goodsInfo_List2.get(i).getGoodsName()+"\t\t\t\t"+goodsInfo_List2.get(i).getGoodsCount()+
                            "个"+",");
                }
                //显示订单信息并且打印
                Toast.makeText(getBaseContext(),"订单下单成功",Toast.LENGTH_SHORT).show();
                StringBuffer msg=new StringBuffer();
                msg.append("\n\n");
                msg.append("用户站点:"+userInfo.getStationName()+"\n");
                msg.append("订单编号:"+userInfo.getSaleID()+"\n");
                msg.append("用户编号:"+userInfo.getUserID()+"\n");
                msg.append("用户姓名:"+userInfo.getUserName()+"\n");
                msg.append("电话:"+userInfo.getPhoneNumber()+"\n");
                msg.append("用户地址:"+userInfo.getAddress()+"\n");
                String goodsTemp=(goodsInfo.substring(0,goodsInfo.length()-1)).toString();
                msg.append("商品详情:"+"\n");
                if(goodsTemp.contains(",")){
                    String[] g=goodsTemp.split(",");
                    int i=g.length;
                    for(int j=0;j<i;j++){
                        msg.append("\t\t\t\t\t\t\t\t"+g[j]+"\n");
                    }
                }else{

                    msg.append("\t\t\t\t\t\t\t\t"+goodsTemp+"\n");
                }
                msg.append("归还空瓶:"+userInfo.getEmptyNO()+"\n");
                msg.append("总价:"+userInfo.getTotalPrice()+"\n");
                msg.append("完成时间:"+userInfo.getOperationTime()+"\n");
                msg.append("操作人:"+basicInfo.getOperationName()+"\n");
                msg.append("操作站点:"+basicInfo.getStationName()+"\n");
                userInfo.setFullNO("");
                SaleCode sleCode=new SaleCode(userInfo.getSaleID());
                byte[] s=sleCode.getSaleCode();
                SaleCodeDialog t=new SaleCodeDialog(PlaceOrderActivity.this,msg.toString(),s,userInfo);
                t.shown();
            }else{
                shownDialog(tag.getRespMsg());
//                Toast.makeText(getBaseContext(),tag.getRespMsg(),Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
            ex.toString();
        }

    }

    @Override
    public void GetDevicesTaskEnd(HsicMessage tag) {
    try{
        if(tag.getRespCode()==0){
             goodsInfo_List_Accessary=new ArrayList<UserInfoDoorSale.GoodsInfo>();
            UserInfoDoorSale userInfo=new UserInfoDoorSale();
            userInfo=JSONUtils.toObjectWithGson(tag.getRespMsg(),UserInfoDoorSale.class);
            goodsInfo_List_Accessary=userInfo.getGoodsInfo();
            setmSpinerPopWindow(goodsInfo_List_Accessary);
        }else{
            shownDialog(tag.getRespMsg());
//            Toast.makeText(getBaseContext(),tag.getRespMsg(),Toast.LENGTH_SHORT).show();
        }
    }catch (Exception ex){

    }
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
    }

    public interface ShownDialogListener {
        void onCheckedChanged(boolean b, String GoodsName, String GoodsCount,
                              String GoodsCode);
    }

    /**
     * 订单新增商品信息ListView
     * @param isClick
     */
    private void setAdapter(boolean isClick){
        listView.setAdapter(new AddGoodsListAdapter(this,goodsInfo_List_add,isClick,
                new ShownDialogListener() {
                    @Override
                    public void onCheckedChanged(boolean b, String GoodsName, String GoodsCount, String GoodsCode) {
                        goodsCode=GoodsCode;
                        mtvGoodsName.setText(GoodsName);
                        mGoodsDialog.show();
                    }
                }));
    }

    /**
     * 关闭软键盘
     */
    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 用户编号补0
     * @param str
     * @param strLength
     * @return
     */
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
    private void shownDialog(String msg){
        new AlertView("提示", msg, null, new String[] { "确定" },
                null,this, AlertView.Style.Alert, this)
                .show();
    }
}
