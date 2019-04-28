package com.hsic.sy.version;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;

import com.hsic.sy.ftp.MySFTP;
import com.jcraft.jsch.ChannelSftp;

import java.io.File;

public class VersionHelper {
    public Context context;
    public VersionHelper(Context context){
    	this.context=context;
    }
    /**
     * 
     * @param context
     * @return
     * ��ȡӦ�ð汾��
     */
    public  int getVersionCode(Context context) {  
        //��ȡpackagemanager��ʵ��   
        PackageManager packageManager =context. getPackageManager();  
        //getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ  
        PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}  
        return packInfo.versionCode;   
    }
    
    /**
     * ����apk
     * @return
     */
    public boolean downLoadApk(){
    	boolean flag=false;
    	MySFTP mysftp = new MySFTP(context);
		ChannelSftp connect = mysftp.connect();
		if(connect==null){
			return flag;
		}else {
			File saveFile=new File(Environment.getExternalStorageDirectory()+"/downLoad");
			if(!saveFile.exists()){
				saveFile.mkdirs();
			}
			File file=new File(saveFile.getAbsolutePath()+"/boilerandroid.apk");
			if(file.exists()){
				file.delete();
			}
			boolean download = mysftp.download("/APP/", "SupplyStationManager.apk", file.getAbsolutePath(), connect);
			if(download){
				flag=true;
			}
		}
		return flag;
    	
    }
    /**
     *��װApk
     */
    public void installApk(){
    	  Intent intent = new Intent(Intent.ACTION_VIEW);
    	  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	  intent.setDataAndType(Uri.fromFile(new File(Environment
    	        .getExternalStorageDirectory(), "/downLoad/SupplyStationManager.apk")),
    	        "application/vnd.android.package-archive");
    	   context. startActivity(intent);

    }
    /**
     * ж��Apk
     */
    public void uninstallApk(){
    	Uri packageURI = Uri.parse("com.hsic.sy.supplystationmanager");
    	Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);   
    	context.startActivity(uninstallIntent);
    }
    
}
