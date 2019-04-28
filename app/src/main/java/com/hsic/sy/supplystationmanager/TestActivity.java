package com.hsic.sy.supplystationmanager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.hsic.sy.bll.MatrixUtil;

public class TestActivity extends AppCompatActivity {
    ImageView imageView;
    MatrixUtil m;
    Button button;
    String s="";
    private static final int BARCODE_WIDTH = 500;
    private static final int BARCODE_HEIGHT = 80;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        imageView =this.findViewById(R.id.imageView);
//        button=this.findViewById(R.id.button);
//        m=new MatrixUtil();
//        try{
//            imageView.setImageBitmap(m.CreateOneDCode("123456789"));
//
//            s= BitmapAndStringUtils.bitmapToBase64(m.CreateOneDCode("123456789"));
//        }catch(Exception WriterException){
//
//        }

//        button.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
////                new PrintTask(TestActivity.this,s).execute();
////                Print p;
////                p=new Print(TestActivity.this);
//                BDLocationUtils bdLocationUtils = new BDLocationUtils(TestActivity.this);
//                bdLocationUtils.doLocation();//开启定位
//                bdLocationUtils.mLocationClient.start();//开始定位
//                Toast.makeText(getBaseContext(),"经度:"+Const.LONGITUDE+" "+"纬度:"+ Const.LATITUDE+" "+
//                        "地址:"+Const.ADDRESS,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
