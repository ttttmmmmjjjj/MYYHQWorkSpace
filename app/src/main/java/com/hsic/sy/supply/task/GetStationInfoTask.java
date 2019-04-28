package com.hsic.sy.supply.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.GetExpListListener;
import com.hsic.sy.supply.listener.GetStationInfoListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;

/**
 * Created by Administrator on 2018/12/5.
 */

public class GetStationInfoTask extends AsyncTask<String, Void, HsicMessage> {
    private Context context;
    private ProgressDialog dialog;
    GetStationInfoListener l;
    WebServiceCallHelper wb;
    GetBasicInfo getBasicInfo;//20181019
    public GetStationInfoTask(Context context,GetStationInfoListener l){
        this.context = context;
        dialog = new ProgressDialog(context);
        getBasicInfo=new GetBasicInfo(context);
        this.l=l;
    }
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        dialog.setMessage("加载信息...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected HsicMessage doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        wb=new WebServiceCallHelper(context);
        HsicMessage hsicMess=new HsicMessage();
        hsicMess=wb.GetStationInfo(getBasicInfo.getDeviceID());
        return hsicMess;
    }

    @Override
    protected void onPostExecute(HsicMessage result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        dialog.setCancelable(true);
        dialog.dismiss();
        l.GetStationInfoListenerEnd(result);
    }
}
