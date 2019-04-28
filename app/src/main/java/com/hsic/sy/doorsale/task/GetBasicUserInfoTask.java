package com.hsic.sy.doorsale.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.doorsale.web.WebServiceHelper;
import com.hsic.sy.doorsale.lisenter.ImplGetBasicUserInfoTask;

/**
 * Created by Administrator on 2018/8/8.
 */

public class GetBasicUserInfoTask extends AsyncTask<Void,Void,HsicMessage> {
    private Context context;
    private ImplGetBasicUserInfoTask l;
    private ProgressDialog dialog;
    WebServiceHelper webHelper;
    private String requestStr,deviceIDStr,stationID;
    public GetBasicUserInfoTask(Context context, ImplGetBasicUserInfoTask l, String deviceIDStr, String requestStr){
        this.context=context;
        this.l=l;
        dialog=new ProgressDialog(context);
        this.deviceIDStr=deviceIDStr;
        this.requestStr=requestStr;
        GetBasicInfo s=new GetBasicInfo(context);
        stationID=s.getStationID();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog.setMessage("正在下载信息");
            dialog.setCancelable(false);
            dialog.show();
    }
    @Override
    protected HsicMessage doInBackground(Void... voids) {
        HsicMessage hsicMess=new HsicMessage();
        webHelper=new WebServiceHelper(context);
        hsicMess= webHelper.getBasicUserInfo(requestStr, deviceIDStr,stationID);
        return hsicMess;
    }
    @Override
    protected void onPostExecute(HsicMessage hsicMessage) {
        super.onPostExecute(hsicMessage);
        dialog.setCancelable(true);
        dialog.dismiss();
        l.GetBasicUserInfoTaskListenerEnd(hsicMessage);
    }


}
