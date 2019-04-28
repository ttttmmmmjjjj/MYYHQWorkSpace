package com.hsic.sy.search.web;
import com.hsic.sy.supplystationmanager.R;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

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
		String endPointDefaultString = context.getResources().getString(
				R.string.station_default);// IPĬ��ֵ
		ip = PreferenceManager.getDefaultSharedPreferences(context).getString(
				"StationServer", endPointDefaultString);
		String portDefaultString = context.getResources().getString(
				R.string.stationport_default);// �˿ں�Ĭ��ֵ
		port = PreferenceManager.getDefaultSharedPreferences(context)
				.getString("StationPort", portDefaultString);
		this.endPoint = "http://" + ip + ":" + port
				+ "/SHLPGPhoneWs/SHLPGPhone.asmx";
		//���ڰ�
//		this.endPoint = "http://" + ip + ":" + port
//				+ "/SHLPGPhoneWs/SHLPGPhone8ST.asmx";
		String oTime = context.getResources()
				.getString(R.string.value_web_time);
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
		String soapAction = nameSpace + methodName;
		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject request = new SoapObject(this.nameSpace, methodName);
		// ������Ҫ�����������Ĳ���
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
