package webservice;

import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.hsic.qpmanager.util.json.JSONUtils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

public class GetWebResultRQC {
	private static String nameSpaceDefaultString = "http://tempuri.org/";

	private static String header = "http://";

	private static String end = "/SHGASInfo.asmx";

	private String nameSpace; // �����ռ�

	private String endPoint; // EndPoint ��������

	private int overTime;// �ӿڳ�ʱʱ��
	String ip,port;

	public GetWebResultRQC(Context context) {// ���캯����ΪwebService�ĵ����������Բ���

		this.nameSpace = nameSpaceDefaultString;
		ip = PreferenceManager.getDefaultSharedPreferences(context).getString(
				"ip", "192.168.1.26");
		port = PreferenceManager.getDefaultSharedPreferences(context)
				.getString("port", "80");
		this.endPoint = header + ip+":"+port + end;//���õ�ַ
//		String outTime = context.getResources().getString(R.string.outTime);
		String outTime="30";
		try {

			overTime = Integer.parseInt(outTime);

		} catch (Exception e) {

			overTime = 30;
		}
	}
	
	public Object recevieData(String methodName,List<Map<String, Object>> propertyList,  Boolean isSimpleRet) {
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
