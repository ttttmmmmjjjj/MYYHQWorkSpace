package com.hsic.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import com.hsic.bean.HsicMessage;
import com.hsic.bll.GetBasicInfo;
import com.hsic.db.DeliveryDB;
import com.hsic.listener.ImplUploadSale;

/**
 * Created by Administrator on 2019/3/18.
 */

public class UploadSaleTask extends AsyncTask<String, Void, HsicMessage> {
    DeliveryDB deliveryDB;
    ImplUploadSale l;
    GetBasicInfo getBasicInfo;
    private Context context;
    private ProgressDialog dialog;
    public UploadSaleTask(Context context, ImplUploadSale l){
        this.context=context;
        this.l=l;
        getBasicInfo=new GetBasicInfo(context);
        dialog = new ProgressDialog(context);
        deliveryDB=new DeliveryDB(context);
    }
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        dialog.setMessage("上传销售信息...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected HsicMessage doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        dialog.dismiss();
        HsicMessage hsicMess=new HsicMessage();
        hsicMess=deliveryDB.upLoadSaleInfo(context,getBasicInfo.getDeviceID(),getBasicInfo.getOperationID(),getBasicInfo.getStationID(),arg0[0],arg0[1]);
        return hsicMess;
    }

    @Override
    protected void onPostExecute(HsicMessage result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        dialog.setCancelable(true);
        dialog.dismiss();
        l.UpLoadSaleTaskEnd(result);
    }
}
