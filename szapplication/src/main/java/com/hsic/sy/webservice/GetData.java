package com.hsic.sy.webservice;

import android.content.Context;
import android.util.Log;

import com.hsic.qpmanager.util.json.JSONUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;
import java.util.Map;

public class GetData {
	private static String nameSpaceDefaultString = "http://tempuri.org/";
	private String nameSpace; // �����ռ�
	private String endPoint; // EndPoint
	private int overTime;// �ӿڳ�ʱʱ��
	private String port;
	private String ip;
	public GetData(Context context) {
		super();
		this.nameSpace = nameSpaceDefaultString;
//		ip = PreferenceManager.getDefaultSharedPreferences(context).getString(
//				"GPServer", endPointDefaultString);
//		String portDefaultString = context.getResources().getString(
//				R.string.gpport_default);// �˿ں�Ĭ��ֵ
//		port = PreferenceManager.getDefaultSharedPreferences(context)
//				.getString("GPPort", portDefaultString);
		//http://192.168.1.58:86/NetTest.asmx
		this.endPoint = "http://106.14.9.217:3392/NetTest.asmx";
		String oTime ="30";
		try {
			overTime = Integer.parseInt(oTime);
		} catch (Exception e) {
			overTime = 30;
		}
	}
	@SuppressWarnings("rawtypes")
	public Object recevieData(String methodName,
			List<Map<String, Object>> propertyList, List<String> mappingName,
			List<Map<String, Class>> mappingList, Boolean isSimpleRet) {
		// ���صĲ�ѯ���
//		Log.e("endPoint", endPoint);
		String soapAction = nameSpace + methodName;
		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject request = new SoapObject(this.nameSpace, methodName);
		// ������Ҫ�����������Ĳ���
//		Log.e("����Ĳ���", JSONUtils.toJsonWithGson(propertyList));
		for (Map<String, Object> map : propertyList) {
			request.addProperty(map.get("propertyName").toString(),
					map.get("propertyValue"));
		}
		// ����soap�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// �����Ƿ���õ���dotNet������
		envelope.dotNet = true;
		// �ȼ���envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(endPoint,
				overTime * 1000);
		// web service����
		try {
			// ����WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		// �õ����ؽ��
		try {
			if (!isSimpleRet) {
				SoapObject o = (SoapObject) envelope.bodyIn;
				if (o != null) {
					return o;
				}
				
			} else {
				return envelope.getResponse();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public String recevieData2(String methodName,
			List<Map<String, Object>> propertyList, List<String> mappingName,
			List<Map<String, Class>> mappingList, Boolean isSimpleRet) {
		String name="";
		// ���صĲ�ѯ���	
		
		String soapAction = nameSpace + methodName;
		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject request = new SoapObject(this.nameSpace, methodName);
		// ������Ҫ�����������Ĳ���
//		Log.e("����Ĳ���", JSONUtils.toJsonWithGson(propertyList));
		for (Map<String, Object> map : propertyList) {
			request.addProperty(map.get("propertyName").toString(),
					map.get("propertyValue"));
		}
		// ����soap�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// �����Ƿ���õ���dotNet������
		envelope.dotNet = true;
		// �ȼ���envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(endPoint,
				overTime * 1000);

//		HttpsTransportSE t=new HttpsTransportSE();
		// web service����
		try {
			// ����WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			e.printStackTrace();
			return "5";
		}
		
		// �õ����ؽ��
		try {
			SoapObject result = (SoapObject) envelope.bodyIn;
			if(envelope.getResponse()!=null){
				name=result.getProperty(0).toString();
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "5";
		}
		 return name;
	}

	@SuppressWarnings("rawtypes")
	public KvmSerializable parseToObject(SoapObject soapObject,
			Class objectClass) throws InstantiationException,
			IllegalAccessException {

		KvmSerializable result = (KvmSerializable) objectClass.newInstance();

		int numOfAttr = result.getPropertyCount();

		for (int i = 0; i < numOfAttr; i++) {
			PropertyInfo info = new PropertyInfo();
			result.getPropertyInfo(i, null, info);
			// ����property�����ڵ����
			try {
				Object object = soapObject.getProperty(info.name);
				result.setProperty(i, object);
			} catch (Exception e) {
				continue;
			}
		}
		return result;
	}
}
