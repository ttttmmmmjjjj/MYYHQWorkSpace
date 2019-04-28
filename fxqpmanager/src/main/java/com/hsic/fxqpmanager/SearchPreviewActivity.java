package com.hsic.fxqpmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hsic.bll.GetBasicInfo;
import com.hsic.picture.ImageAdapter;
import com.hsic.utils.PathUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/14.
 */

public class SearchPreviewActivity extends AppCompatActivity {
    private ArrayList<String> mList;
    private GridView gridView;
    private ImageAdapter imageAdapter;
    String saleId = "", deviceid = "", userID = "";
    GetBasicInfo getBasicInfo;
    String photoesPattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        getBasicInfo = new GetBasicInfo(SearchPreviewActivity.this);
        deviceid = getBasicInfo.getDeviceID();

        Intent i=getIntent();
        userID=i.getStringExtra("UserID");
        photoesPattern=deviceid + "e"+getBasicInfo.getOperationID()+"s" + saleId;
        gridView = (GridView) findViewById(R.id.gridView1);//
        initData();
        imageAdapter = new ImageAdapter(this, mList);//
        gridView.setAdapter(imageAdapter);//
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {// ����¼�
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent in = new Intent(SearchPreviewActivity.this, SBigPreViewActivity.class);
                in.putExtra("pathName", mList.get(position));
                in.putExtra("UserID",userID);
                in.putStringArrayListExtra("List", mList);
                startActivityForResult(in, 1);//
                SearchPreviewActivity.this.finish();
            }
        });

    }
    /**
     *
     */
    private void initData() {
        mList = new ArrayList<String>();
        String filePath= PathUtil.getImagePath();
        File file = new File(filePath);
        String[] paths = file.list();
        if (paths != null && paths.length > 0) {
            for (int i = 0; i < paths.length; i++) {
                String path = filePath+ paths[i];
                if (path.contains(photoesPattern)) {
                    mList.add(path);
                }
            }

        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
