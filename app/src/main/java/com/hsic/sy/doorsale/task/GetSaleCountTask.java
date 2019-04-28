package com.hsic.sy.doorsale.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.doorsale.web.WebServiceHelper;
import com.hsic.sy.doorsale.lisenter.ImplGetSaleCountTask;

/**
 * Created by Administrator on 2018/9/6.
 */

public class GetSaleCountTask extends AsyncTask<Void,Void,HsicMessage> {
    ImplGetSaleCountTask l;
    private Context context;
    WebServiceHelper webHelper;
    private String DeviceID,StationID;
    private ProgressDialog dialog;
    public GetSaleCountTask(Context context,ImplGetSaleCountTask l){
        this.l=l;
        this.context=context;
        dialog=new ProgressDialog(context);
        GetBasicInfo s=new GetBasicInfo(context);
        DeviceID=s.getDeviceID();
        StationID=s.getStationID();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("正在统计销售数量");
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    protected HsicMessage doInBackground(Void... voids) {
        HsicMessage hsicMess=new HsicMessage();
        webHelper=new WebServiceHelper(context);
        hsicMess= webHelper.getSaleCount(DeviceID,StationID);
        return hsicMess;
    }
    @Override
    protected void onPostExecute(HsicMessage hsicMessage) {
        super.onPostExecute(hsicMessage);
        dialog.setCancelable(true);
        dialog.dismiss();
        l.ImplGetSaleCount(hsicMessage);
    }
}
