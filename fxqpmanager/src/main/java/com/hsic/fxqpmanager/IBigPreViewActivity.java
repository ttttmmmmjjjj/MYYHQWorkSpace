package com.hsic.fxqpmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hsic.bll.GetBasicInfo;
import com.hsic.db.DeliveryDB;

import java.io.File;
import java.util.ArrayList;

/**
 * 巡检照片
 */
public class IBigPreViewActivity extends AppCompatActivity {
    private ImageView mImageview;
    private Bitmap decodeFile;
    String path="";
    String fileName="";
    ArrayList<String> mList = new ArrayList<String>();
    String userId = "";
    String saleId = "";
    GetBasicInfo getBasicInfo;
    DeliveryDB dbData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pre_view);
        mImageview = (ImageView) findViewById(R.id.bigpreview);
        getBasicInfo=new GetBasicInfo(this);
        dbData=new DeliveryDB(this);
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Intent intent = getIntent();
        userId=intent.getStringExtra("UserID");
        saleId=intent.getStringExtra("SaleID");
        path = intent.getStringExtra("pathName");
        mList=intent.getStringArrayListExtra("List");
        fileName=path.substring(path.lastIndexOf("/")+1);
        Bitmap big = big(path);
        mImageview.setImageBitmap(big);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (decodeFile != null && !decodeFile.isRecycled()) {
            decodeFile.recycle();
            decodeFile = null;

        }

        System.gc();
    }

    /**
     *
     * @param path
     * @return
     */
    public Bitmap big(String path) {
        Bitmap resizeBmp = BitmapFactory.decodeFile(path);
        return resizeBmp;
    }

    /**
     *
     * @param v
     *
     */
    public void click(View v) {
        Intent in = new Intent(this, InsepctionPreviewActivity.class);
        in.putExtra("SaleID",saleId);
        in.putExtra("UserID",userId);
        startActivity(in);
        this.finish();
    }

    /**
     *
     * @param v
     */
    public void click1(View v) {
        if(mList.size()==1){
            File file=new File(path);
            if(file.exists()){
                file.delete();
                dbData.DeleteXJAssociation(fileName,getBasicInfo.getOperationID(),saleId);
            }
            this.finish();
        }else{
            File file=new File(path);
            if(file.exists()){
                file.delete();
                dbData.DeleteXJAssociation(fileName,getBasicInfo.getOperationID(),saleId);
            }
            Intent in = new Intent(this, InsepctionPreviewActivity.class);
            in.putExtra("SaleID",saleId);
            in.putExtra("UserID",userId);
            startActivity(in);
            this.finish();
        }

    }
}
