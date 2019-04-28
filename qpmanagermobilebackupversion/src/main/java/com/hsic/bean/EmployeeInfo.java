package com.hsic.bean;

public class EmployeeInfo {
	// վ��Ա����Ϣ EmployeeInfo��

	public int ID;// Ա�����
	public String UserID;// �˺�
	public String Password;// ���� MD5����
	public String UserName;// �û�����
	public String UserCardID;// Ա������ �����ã���Ϊ��
	public String Telphone;// ��ϵ��ʽ
	public String Station;// ����վ��
	public int UserType;// Ա������ 0������Ա����1������Ա����2��վ�����Ա��9����������Ա
	public String CheckMan;// �����
	public String CheckTime;// ���ʱ��
	public int iState; // ״̬��ʶλ 0��δ��ˣ�1������ˣ�7����˲�ͨ����9��ͣ��
	public String PrePassword;// �û���ԭ��
	//������ѯ�����ֶ�
	public String SearchDate;
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserCardID() {
		return UserCardID;
	}

	public void setUserCardID(String userCardID) {
		UserCardID = userCardID;
	}

	public String getTelphone() {
		return Telphone;
	}

	public void setTelphone(String telphone) {
		Telphone = telphone;
	}

	public String getStation() {
		return Station;
	}

	public void setStation(String station) {
		Station = station;
	}

	public int getUserType() {
		return UserType;
	}

	public void setUserType(int userType) {
		UserType = userType;
	}

	public String getCheckMan() {
		return CheckMan;
	}

	public void setCheckMan(String checkMan) {
		CheckMan = checkMan;
	}

	public String getCheckTime() {
		return CheckTime;
	}

	public void setCheckTime(String checkTime) {
		CheckTime = checkTime;
	}

	public int getiState() {
		return iState;
	}

	public void setiState(int iState) {
		this.iState = iState;
	}

	public String getLogtime() {
		return Logtime;
	}

	public void setLogtime(String logtime) {
		Logtime = logtime;
	}

	public String Logtime;// ע������

	public String getPrePassword() {
		return PrePassword;
	}

	public void setPrePassword(String prePassword) {
		PrePassword = prePassword;
	}

	public String getSearchDate() {
		return SearchDate;
	}

	public void setSearchDate(String searchDate) {
		SearchDate = searchDate;
	}
	
}
