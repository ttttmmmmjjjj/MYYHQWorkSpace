package com.hsic.sy.doorsale.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hsic.sy.baidulocation.Const;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.doorsale.web.WebServiceHelper;
import com.hsic.sy.doorsale.lisenter.ImplEmployeeLoginTask;
/**
 * Created by Administrator on 2018/8/15.
 */

public class EmployeeLoginTask extends AsyncTask<Void,Void,HsicMessage> {
    ImplEmployeeLoginTask l;
    private Context context;
    private ProgressDialog dialog;
    WebServiceHelper webHelper;
    private String requestStr;
    public EmployeeLoginTask(Context context, ImplEmployeeLoginTask l,String requestStr) {
        this.context = context;
        this.l = l;
        dialog = new ProgressDialog(context);
        this.requestStr = requestStr;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("正在登录");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected HsicMessage doInBackground(Void... voids) {
        HsicMessage hsicMess = new HsicMessage();
        webHelper = new WebServiceHelper(context);
        hsicMess = webHelper.EmployeeLogin(requestStr, Const.DeviceID,Const.APPVsersion);
        return hsicMess;
    }

    @Override
    protected void onPostExecute(HsicMessage hsicMessage) {
        super.onPostExecute(hsicMessage);
        dialog.setCancelable(true);
        dialog.dismiss();
        l.EmployeeLoginTaskEnd(hsicMessage);
    }

}
