package com.hsic.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class LeaseQPInfo implements Serializable{
	public int ID ;// ���
	public String LeaseID;// ��ƿ����
	public String QPType;// ��ƿ����
	public int QPNum;// ��ƿ����
	public String CustomerID;// �����ͻ��� CustomerInfo�е�CustomerID
	public int AddressID;// ��ַ��� CustomerAddressInfo�е�ID
	public String SaleID;// ���۵��� Sale�е�SaleID
	public String Station;// ����վ���
	public int RecUserType;// ���վ��������� 0������Ա����ӣ�1������Ա�����
	public String RecUserID ;// ���վ����� EmployeeInfo�е�UserID
	public String RecTime ; // ����ʱ��
	public int SendUserType;// ���⾭�������� 0������Ա����ӣ�1������Ա�����
	public String SendUserID;// ���⾭����  EmployeeInfo�е�UserID
	public String SendTime;// ����ʱ�� Ĭ��ʱ��
	public int iState;// ״̬��ʶλ 5�������У�6���ѻ��գ�9������
	public BigDecimal QPPrice; //��ƿ�۸�
	public String Name ;// ��ƿ����������
	public String CustomerName;//�ͻ�����
	public String LeaseQP ;// ��ƿ��
	public int LeaseType;//0���⣬1������2����
	public int LeaseNum;///��ƿ����
	/**
	 * @return the leaseType
	 */
	public int getLeaseType() {
		return LeaseType;
	}
	/**
	 * @param leaseType the leaseType to set
	 */
	public void setLeaseType(int leaseType) {
		LeaseType = leaseType;
	}
	public BigDecimal getQPPrice() {
		return QPPrice;
	}
	public void setQPPrice(BigDecimal qPPrice) {
		QPPrice = qPPrice;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getLeaseID() {
		return LeaseID;
	}
	public void setLeaseID(String leaseID) {
		LeaseID = leaseID;
	}
	public String getQPType() {
		return QPType;
	}
	public void setQPType(String qPType) {
		QPType = qPType;
	}
	public int getQPNum() {
		return QPNum;
	}
	public void setQPNum(int qPNum) {
		QPNum = qPNum;
	}
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	public int getAddressID() {
		return AddressID;
	}
	public void setAddressID(int addressID) {
		AddressID = addressID;
	}
	public String getSaleID() {
		return SaleID;
	}
	public void setSaleID(String saleID) {
		SaleID = saleID;
	}
	public String getStation() {
		return Station;
	}
	public void setStation(String station) {
		Station = station;
	}
	public int getRecUserType() {
		return RecUserType;
	}
	public void setRecUserType(int recUserType) {
		RecUserType = recUserType;
	}
	public String getRecUserID() {
		return RecUserID;
	}
	public void setRecUserID(String recUserID) {
		RecUserID = recUserID;
	}
	public String getRecTime() {
		return RecTime;
	}
	public void setRecTime(String recTime) {
		RecTime = recTime;
	}
	public int getSendUserType() {
		return SendUserType;
	}
	public void setSendUserType(int sendUserType) {
		SendUserType = sendUserType;
	}
	public String getSendUserID() {
		return SendUserID;
	}
	public void setSendUserID(String sendUserID) {
		SendUserID = sendUserID;
	}
	public String getSendTime() {
		return SendTime;
	}
	public void setSendTime(String sendTime) {
		SendTime = sendTime;
	}
	public int getiState() {
		return iState;
	}
	public void setiState(int iState) {
		this.iState = iState;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getLeaseQP() {
		return LeaseQP;
	}
	public void setLeaseQP(String leaseQP) {
		LeaseQP = leaseQP;
	}
	/**
	 * @return the leaseNum
	 */
	 public int getLeaseNum() {
		return LeaseNum;
	}
	/**
	 * @param leaseNum the leaseNum to set
	 */
	 public void setLeaseNum(int leaseNum) {
		LeaseNum = leaseNum;
	}

}
