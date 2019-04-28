package com.hsic.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hsic.bean.HsicMessage;
import com.hsic.bll.GetBasicInfo;
import com.hsic.bll.WebServiceHelper;
import com.hsic.listener.ImplUpUserInspection;

/**
 * Created by Administrator on 2019/1/7.
 */

public class UpUserInspectionTask extends AsyncTask<String, Void, HsicMessage> {
    private Context context;
    private ProgressDialog dialog;
    WebServiceHelper web;
    GetBasicInfo getBasicInfo;
    ImplUpUserInspection l;

    public UpUserInspectionTask(Context context,ImplUpUserInspection l){
        this.context=context;
        dialog = new ProgressDialog(context);
        this.l=l;
        getBasicInfo=new GetBasicInfo(context);
    }
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        dialog.setMessage("上传巡检信息...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected HsicMessage doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        dialog.dismiss();
        web=new WebServiceHelper(context);
        HsicMessage hsicMess=new HsicMessage();
        hsicMess=web.UpUserInspection(getBasicInfo.getDeviceID(),arg0[0]);
        return hsicMess;
    }

    @Override
    protected void onPostExecute(HsicMessage result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        dialog.setCancelable(true);
        dialog.dismiss();
        l.UpUserInspectionTaskEnd(result);
    }
}
