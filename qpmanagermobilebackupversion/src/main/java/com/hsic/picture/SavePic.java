package com.hsic.picture;

import android.os.Environment;

import com.hsic.bll.PathUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2019/1/7.
 */

public class SavePic {
    public static void  getImagePath(String filePath,String deviceid,String TruckNoId, String checkSaleid, String user) {
        if (checkSaleid != null) {
            File file = new File(filePath);
            if (file != null && file.exists()) {
                String path = PathUtil.getImagePath();
                File file1 = new File(path);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                Date date = new Date();
                String pattern = "yyyyMMddHHmmss";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                        Locale.getDefault());
                String format = sdf.format(date);
                File file2 = new File(file1.getPath(), deviceid + "s"
                        + checkSaleid +  "_" + format + "_"
                        + user + ".jpg");
                PictureHelper.compressPicture(file.getAbsolutePath(),
                        file2.getAbsolutePath(), 720, 1280);
                if (file.exists()) {
                    file.delete();
                }
                File fileDir = new File(
                        Environment.getExternalStorageDirectory(), "/photoes/");
                if (fileDir.exists()) {
                    fileDir.delete();
                }
                /***
                 * 20180507 将照片信息保存到数据表中
                 */
//                T_B_XJAssociaton XJAssociaton_T_B=new T_B_XJAssociaton(this);
//                String RelationID=deviceid + "s"+ checkSaleid;
//                String ImageFileName=deviceid + "s"+ checkSaleid + "t" + TruckNoId + "_" + format + "_"
//                        + user +"sign"+ ".jpg";
//                String FileName=PathUtil.getFilePath();
//                XJAssociaton_T_B.Insert(TruckNoId, checkSaleid, ImageFileName, RelationID,FileName);//将照片信息插入到数据表中
            }
        }
    }
}
