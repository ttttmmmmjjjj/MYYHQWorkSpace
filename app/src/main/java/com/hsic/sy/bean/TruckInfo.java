package com.hsic.sy.bean;

import java.util.List;

public class TruckInfo {
	private int ID;

    /// <summary>
    /// ���κ�
    /// </summary>
	private String TruckNoID;


    /// <summary>
    /// ���α��
    /// </summary>
	private String TruckNo;

    /// <summary>
    /// ����С���
    /// </summary>
	private String TruckID;

    /// <summary>
    /// ���ƺ�
    /// </summary>
	private String license;

    /// <summary>
    /// վ����
    /// </summary>
	private String stationid;

    /// <summary>
    /// վ������
    /// </summary>
	private String stationname;

    /// <summary>
    /// ����
    /// </summary>
	private int bottleNum;


    /// <summary>
    /// ����Ա
    /// </summary>
	private String SaleMan;

    /// <summary>
    /// ��ʻԱ
    /// </summary>
	private String driver;

    /// <summary>
    /// �ֳֻ����
    /// </summary>
	private String HandsetNo;

    /// <summary>
    /// ��ƿ��ǩ
    /// </summary>
	private String UseRegCode;

    /// <summary>
    /// ����״̬
    /// </summary>
	private String State;

    /// <summary>
    /// ����ʱ��
    /// </summary>
	private String CreateTime;

    /// <summary>
    /// ����ʱ��
    /// </summary>
	private String StartTime;

    /// <summary>
    /// ����ʱ��
    /// </summary>
	private String endTime;
    /// <summary>
    /// gps
    /// </summary>
	private String gps_id;

    /// <summary>
    /// �����ƿվ��
    /// </summary>
	private String getQPStationid;


    /// <summary>
    /// TmpID
    /// </summary>
	private String TmpID;

    /// <summary>
    /// IsGet
    /// </summary>
	private String IsGet;

    /// <summary>
    /// Driver
    /// </summary>
	private String Driver1;

    /// <summary>
    /// HandSetNo
    /// </summary>
	private String HandSetNo;

    /// <summary>
    /// tag_id
    /// </summary>
	private String tag_id;

    /// <summary>
    /// memo
    /// </summary>
	private String memo;

    /// <summary>
    /// ���۵�
    /// </summary>
	private List<Sale> Sale_tmp;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTruckNoID() {
		return TruckNoID;
	}

	public void setTruckNoID(String truckNoID) {
		TruckNoID = truckNoID;
	}

	public String getTruckNo() {
		return TruckNo;
	}

	public void setTruckNo(String truckNo) {
		TruckNo = truckNo;
	}

	public String getTruckID() {
		return TruckID;
	}

	public void setTruckID(String truckID) {
		TruckID = truckID;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	public int getBottleNum() {
		return bottleNum;
	}

	public void setBottleNum(int bottleNum) {
		this.bottleNum = bottleNum;
	}

	public String getSaleMan() {
		return SaleMan;
	}

	public void setSaleMan(String saleMan) {
		SaleMan = saleMan;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getHandsetNo() {
		return HandsetNo;
	}

	public void setHandsetNo(String handsetNo) {
		HandsetNo = handsetNo;
	}

	public String getUseRegCode() {
		return UseRegCode;
	}

	public void setUseRegCode(String useRegCode) {
		UseRegCode = useRegCode;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGps_id() {
		return gps_id;
	}

	public void setGps_id(String gps_id) {
		this.gps_id = gps_id;
	}

	public String getGetQPStationid() {
		return getQPStationid;
	}

	public void setGetQPStationid(String getQPStationid) {
		this.getQPStationid = getQPStationid;
	}

	public String getTmpID() {
		return TmpID;
	}

	public void setTmpID(String tmpID) {
		TmpID = tmpID;
	}

	public String getIsGet() {
		return IsGet;
	}

	public void setIsGet(String isGet) {
		IsGet = isGet;
	}
	public String getHandSetNo() {
		return HandSetNo;
	}

	public void setHandSetNo(String handSetNo) {
		HandSetNo = handSetNo;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<Sale> getSale_tmp() {
		return Sale_tmp;
	}

	public void setSale_tmp(List<Sale> sale_tmp) {
		Sale_tmp = sale_tmp;
	}

	public String getDriver1() {
		return Driver1;
	}

	public void setDriver1(String driver1) {
		Driver1 = driver1;
	}
	

	
}
