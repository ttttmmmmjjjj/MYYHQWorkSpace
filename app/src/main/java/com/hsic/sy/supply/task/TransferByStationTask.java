package com.hsic.sy.supply.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.GetBasicInfo;
import com.hsic.sy.supply.listener.GetStationInfoListener;
import com.hsic.sy.supply.listener.TransferByStationListener;
import com.hsic.sy.supply.web.WebServiceCallHelper;

/**
 * Created by Administrator on 2018/12/5.
 */

public class TransferByStationTask extends AsyncTask<String, Void, HsicMessage> {
    private Context context;
    private ProgressDialog dialog;
    TransferByStationListener l;
    WebServiceCallHelper wb;
    GetBasicInfo getBasicInfo;//20181019
    public TransferByStationTask(Context context,TransferByStationListener l){
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
        hsicMess=wb.TransferByStation(getBasicInfo.getDeviceID(),arg0[0],arg0[1],arg0[2],arg0[3],arg0[4]);
        return hsicMess;
    }

    @Override
    protected void onPostExecute(HsicMessage result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        dialog.setCancelable(true);
        dialog.dismiss();
        l.TransferByStationListenerEnd(result);
    }
}
