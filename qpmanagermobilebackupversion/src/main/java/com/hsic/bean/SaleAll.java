package com.hsic.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaleAll  implements  Serializable{

	public Sale Sale;//
	public List<SaleDetail> SaleDetail = new ArrayList<SaleDetail>();
	public CustomerInfo CustomerInfo;//
	public List<LeaseQPInfo> LeaseQPInfo = new ArrayList<LeaseQPInfo>();// 租瓶信息
	public boolean isUpdate=false;
	public List<ScanHistory> ScanHistory; // 发瓶收瓶列表

	public List<com.hsic.bean.SaleDetail> getSaleDetail() {
		return SaleDetail;
	}

	public void setSaleDetail(List<com.hsic.bean.SaleDetail> saleDetail) {
		SaleDetail = saleDetail;
	}

	public com.hsic.bean.CustomerInfo getCustomerInfo() {
		return CustomerInfo;
	}

	public void setCustomerInfo(com.hsic.bean.CustomerInfo customerInfo) {
		CustomerInfo = customerInfo;
	}

	public List<com.hsic.bean.LeaseQPInfo> getLeaseQPInfo() {
		return LeaseQPInfo;
	}

	public void setLeaseQPInfo(List<com.hsic.bean.LeaseQPInfo> leaseQPInfo) {
		LeaseQPInfo = leaseQPInfo;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean update) {
		isUpdate = update;
	}

	public List<com.hsic.bean.ScanHistory> getScanHistory() {
		return ScanHistory;
	}

	public void setScanHistory(List<com.hsic.bean.ScanHistory> scanHistory) {
		ScanHistory = scanHistory;
	}

	public com.hsic.bean.Sale getSale() {
		return Sale;
	}

	public void setSale(com.hsic.bean.Sale sale) {
		Sale = sale;
	}
}
