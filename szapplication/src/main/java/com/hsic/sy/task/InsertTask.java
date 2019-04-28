package com.hsic.sy.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.hsic.sy.utils.HsicMessage;
import com.hsic.sy.webservice.WebServiceCallHelper;

/**
 * Created by Administrator on 2018/12/20.
 */

public class InsertTask extends AsyncTask<Void,Void,HsicMessage> {
    private Context context;
    private ProgressDialog dialog;
    WebServiceCallHelper wb;
    Impltask l;
    public InsertTask(Context context,Impltask l){
        this.context=context;
        dialog = new ProgressDialog(context);
        this.l=l;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setMessage("正在访问服务...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onPostExecute(HsicMessage hsicMessage) {
        super.onPostExecute(hsicMessage);
        dialog.setCancelable(true);
        dialog.dismiss();
        l.ImpltaskEnd(hsicMessage);
    }

    @Override
    protected HsicMessage doInBackground(Void... voids) {
        HsicMessage hsicMessage=new HsicMessage();
        try{
            wb=new WebServiceCallHelper(context);
            hsicMessage=wb.NetTest("");
        }catch (Exception ex){

        }

        return hsicMessage;
    }
}
