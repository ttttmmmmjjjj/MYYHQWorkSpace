package com.hsic.sy.picture;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bll.MyLog;
import com.hsic.sy.bll.PathUtil;
import com.hsic.sy.bll.TimeUtils;
import com.hsic.sy.ftp.MySFTP;
import com.hsic.sy.search.task.UpLoadData;
import com.jcraft.jsch.ChannelSftp;
import android.content.Context;
import android.util.Log;

//relationId ��������device+"u"+"userid"
public class UpLoadPicture {
	public HsicMessage upPicture(Context context, String deviceid) {
		int flag = 0;
		HsicMessage hm = new HsicMessage();
		hm.setRespCode(8);
		try {
			MySFTP mysftp = new MySFTP(context);
			ChannelSftp connect = mysftp.connect();
			UpLoadData upLoadData = new UpLoadData();
			if (connect == null) {
				hm.setRespCode(2);
			} else {
				//创建文件夹
				List<String> dirList;
				Date date = new Date();
				String pattern = "yyyyMMdd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
						Locale.getDefault());
				String FileName = simpleDateFormat.format(date);// 获取巡检日期

				dirList = new ArrayList<String>();
				dirList.add(FileName);
				mysftp.addDirs2("/PIC/",dirList,context,connect);

				String filePath = "";
				filePath = PathUtil.getImagePath();
				File file = new File(filePath);
				String[] path = file.list();
				int length = path.length;
				if (path != null && length <= 0) {
					hm.setRespCode(0);
				} else if (path != null && length > 0) {
					String ImageFile = "";
					for (int i = 0; i < path.length; i++) {
						ImageFile = path[i];
						int index = path[i].indexOf("_");
						String relationId = "";
						relationId = path[i].substring(0, index);
						/**
						 * 20170824 修改
						 * 只上传三天前的照片
						 * 修改人:tmj
						 */
						SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
						String[] pictureName=path[i].split("_");
						String pictureTime=pictureName[1];//照片拍摄时间
						String NowString = TimeUtils.getTime("yyyyMMddHHmmss");	//当前时间
						long days = TimeUtils.getDaySub(sdf,pictureTime,NowString); //时间差
//					    MyLog.e("时间差", String.valueOf(days));
						if((int)days>3){
							//本地删除照片
//					    	MyLog.e("执行照片删除", pictureTime);
							flag++;
							File file1 = new File(file, path[i]);
							if (file1.exists()) {
								file1.delete();
							}
						}else{
							//上传
							//“/PIC/20171110/”


							boolean upload = mysftp.upload("/PIC/"+FileName+"/", file.getPath()
									+ "/" + path[i], connect);
							Log.e("upload", JSONUtils.toJsonWithGson(upload));
							if (upload) {
								hm = upLoadData.UpLoadA(FileName+"/"+ImageFile, relationId,
										deviceid, context);
								if (hm.getRespCode() == 0) {
									flag++;
									File file1 = new File(file, path[i]);
									if (file1.exists()) {
										file1.delete();
									}
								}
							}
						}

					}
					if (flag < path.length) {
						hm.setRespCode(12);
					}
				}
			}
		} catch (Exception ex) {
			hm.setRespCode(9);
			MyLog.e("上传照片出现异常!", ex.toString());
		}

		return hm;
	}

	/**
	 * 上传灶位勘探照片
	 * @param context
	 * @param deviceid
	 */
	public HsicMessage UpEcpPicture(Context context, String deviceid) {
		int flag = 0;
		HsicMessage hm = new HsicMessage();
		hm.setRespCode(8);
		try {
			MySFTP mysftp = new MySFTP(context);
			ChannelSftp connect = mysftp.connect();
			com.hsic.sy.supply.task.UpLoadData upLoadData = new com.hsic.sy.supply.task.UpLoadData();
			if (connect == null) {
				hm.setRespCode(2);
				hm.setRespMsg("FTP连接失败");
			} else {
				//创建文件夹
				String filePath = "";
				filePath = PathUtil.getExpInfoImagePath();
				File file = new File(filePath);
				String[] path = file.list();
				int length = path.length;
				if (path != null && length <= 0) {
					hm.setRespCode(0);
				} else if (path != null && length > 0) {
					String ImageFile = "";
					for (int i = 0; i < path.length; i++) {
						ImageFile = path[i];
						int index = path[i].indexOf("_");
						String relationId = "";
						relationId = path[i].substring(0, index);
//						Log.e("relationId",JSONUtils.toJsonWithGson(relationId));
						/**
						 * 20170824 修改
						 * 只上传三天前的照片
						 * 修改人:tmj
						 */
						SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
						String[] pictureName=path[i].split("_");
						String pictureTime=pictureName[1];//照片拍摄时间
						String NowString = TimeUtils.getTime("yyyyMMddHHmmss");	//当前时间
						long days = TimeUtils.getDaySub(sdf,pictureTime,NowString); //时间差
//					    MyLog.e("时间差", String.valueOf(days));
						if((int)days>3){
							//本地删除照片
//					    	MyLog.e("执行照片删除", pictureTime);
							flag++;
							File file1 = new File(file, path[i]);
							if (file1.exists()) {
								file1.delete();
							}
						}else{
							//上传
							//“/PIC/20171110/”
							List<String> dirList;
							Date date = new Date();
							String pattern = "yyyyMMdd";
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
									Locale.getDefault());
							String FileName = simpleDateFormat.format(date);// 获取巡检日期

							dirList = new ArrayList<String>();
							dirList.add(FileName);
							mysftp.addDirs2("/PIC/",dirList,context,connect);
							boolean upload = mysftp.upload("/PIC/"+FileName+"/", file.getPath()
									+ "/" + path[i], connect);
							if (upload) {
								hm = upLoadData.UpFileReletionInfo(FileName+"/"+ImageFile, relationId,
										deviceid, context);
								if (hm.getRespCode() == 0) {
									flag++;
									File file1 = new File(file, path[i]);
									if (file1.exists()) {
										file1.delete();
									}
								}
							}
						}

					}
					if (flag < path.length) {
						hm.setRespCode(12);

					}
				}
			}
		} catch (Exception ex) {
			hm.setRespCode(9);
			hm.setRespMsg("上传照片出现异常:"+ex.toString());
		}

		return hm;
	}
}
