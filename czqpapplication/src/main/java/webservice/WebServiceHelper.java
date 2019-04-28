package webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hsic.qpmanager.util.json.JSONUtils;

import android.content.Context;
import android.util.Log;

import bean.HsicMessage;

public class WebServiceHelper {
	private Context mContext;

	public WebServiceHelper(Context context) {//ʵ�ֹ��캯��
		super();
		mContext = context;
	}
	public HsicMessage GetBasicInfo(String requestData) {
		//			�����ĺ��壬��һ�ǲ��������֣�
		//			����ǵ���webService�ķ�������
		//			�����ǵ���webService�ķ���Ҫ����Ĳ���

		HsicMessage mess = new HsicMessage();//����HsicMessage

		mess.setRespCode(-1);//����RespCode����

		NetWorkHelper mHelper = new NetWorkHelper(mContext);//�ж������Ƿ����

		try {

			if (!mHelper.checkNetworkStatus()) {

				mess.setRespMsg("��������ʧ��");

				mess.setRespCode(-2);

				return mess;
			}
		} catch (Exception e1) {

			e1.printStackTrace();

			mess.setRespMsg("������ʧ��");

			mess.setRespCode(-3);

			return mess;
		}

		GetWebResultRQC orderdeclData = new GetWebResultRQC(mContext);
		//�ڵ���webServiceʱ�Ļ����������ã�������ڹ���GetData����ʱ��ɵ�
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//׼������
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("propertyName", "QPDJCODE");// ��������
		map.put("propertyValue",requestData);
		propertyList.add(map);
		try {
			Object object = orderdeclData.recevieData("getQPCZInfo", propertyList,true);//��Ӳ�������recevieData�������ؽ�� 
//			
			mess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);		
//			Log.e("���ý��", JSONUtils.toJsonWithGson(mess));
		} catch (Exception e) {
			e.printStackTrace();
			mess.setRespCode(-4);
			mess.setRespMsg("�����쳣");
		}
		return mess;
	}	
	public HsicMessage GetSaleInfo(String cqdw,String qpcode) {
		//			�����ĺ��壬��һ�ǲ��������֣�
		//			����ǵ���webService�ķ�������
		//			�����ǵ���webService�ķ���Ҫ����Ĳ���

		HsicMessage mess = new HsicMessage();//����HsicMessage

		mess.setRespCode(-1);//����RespCode����

		NetWorkHelper mHelper = new NetWorkHelper(mContext);//�ж������Ƿ����

		try {

			if (!mHelper.checkNetworkStatus()) {
				mess.setRespMsg("��������ʧ��");
				mess.setRespCode(-2);
				return mess;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			mess.setRespMsg("������ʧ��");
			mess.setRespCode(-3);
			return mess;
		}

		GetWebResultRQC orderdeclData = new GetWebResultRQC(mContext);
		//�ڵ���webServiceʱ�Ļ����������ã�������ڹ���GetData����ʱ��ɵ�
		List<Map<String, Object>> propertyList = new ArrayList<Map<String, Object>>();//׼������
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("propertyName", "CQDW");// ��������
		map1.put("propertyValue",cqdw);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("propertyName", "QP");// ��������
		map2.put("propertyValue",qpcode);

		propertyList.add(map1);
		propertyList.add(map2);
		try {
			Object object = orderdeclData.recevieData("getQPSInfo", propertyList,true);//��Ӳ�������recevieData�������ؽ�� 
			Log.e("object", object.toString());
			mess = (HsicMessage) JSONUtils.toObjectWithGson(object.toString(), HsicMessage.class);			
		} catch (Exception e) {
			e.printStackTrace();
			mess.setRespCode(-4);
			mess.setRespMsg("�����쳣!"+e.toString());
		}
		return mess;
	}	
}
