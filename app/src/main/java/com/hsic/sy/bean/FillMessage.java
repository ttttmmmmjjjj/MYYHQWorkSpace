package com.hsic.sy.bean;

public class FillMessage {
	int Result;	//��ʾִ�н������ֵ�ͣ�0-�ɹ���1-ʧ�ܣ�
	String CountTime;//��ʾͳ��ʱ�䣬�ַ��ͣ���ʽ:yyyy-MM-dd HH:mm����ȷ���֣�ִ��ʧ��ʱ�����أ�
	String BigNum;//��ʾ��ƿ��װ������ֵ�ͣ�ִ��ʧ��ʱ�����أ�
	String SmallNum;//��ʾСƿ��װ������ֵ�ͣ�ִ��ʧ��ʱ�����أ�
	String Message;//��ʾִ��ʧ��ʱ��������Ϣ��ִ�гɹ�ʱ�����ء�
	public int getResult() {
		return Result;
	}
	public void setResult(int result) {
		Result = result;
	}
	public String getCountTime() {
		return CountTime;
	}
	public void setCountTime(String countTime) {
		CountTime = countTime;
	}
	public String getBigNum() {
		return BigNum;
	}
	public void setBigNum(String bigNum) {
		BigNum = bigNum;
	}
	public String getSmallNum() {
		return SmallNum;
	}
	public void setSmallNum(String smallNum) {
		SmallNum = smallNum;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}


}
