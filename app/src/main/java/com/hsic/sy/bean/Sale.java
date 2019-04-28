package com.hsic.sy.bean;

import java.sql.Date;
import java.util.List;

public class Sale {
	/// <summary>
    /// 
    /// </summary>
    public List<SaleDetail> Saledetail;
    /// <summary>
    /// 
    /// </summary>
    public String totalprice;  
    /// <summary>
    /// ��Ʒ����
    /// </summary>
    public String goodscount;//
 
    /// <summary>
    /// �ֻ���
    /// </summary>
    public String handphone;  //
    /// <summary>
    /// �绰
    /// </summary>
    public String telephone;//
 
	/// <summary>
	/// ������
	/// </summary>
	public String saleid;//
    /// <summary>
    /// �û�����
    /// </summary>
    public String username;
	/// <summary>
	/// վ���
	/// </summary>
	public String stationcode;
	/// <summary>
	/// ֧����ʽ
	/// </summary>
	public String paymode;
	/// <summary>
	/// �û�������
	/// </summary>
	public String userid;	
	/// <summary>
	/// �û���ID
	/// </summary>
	public String cardid;
	/// <summary>
	/// �Ƿ�����
	/// </summary>
	public String deliveryn;
	/// <summary>
	/// ��������
	/// </summary>
	public String transdate;
	/// <summary>
	/// �ܼ�
	/// </summary>
	public String tradesum;	
	/// <summary>
	/// �Ƿ���ʾ������ȡ��
	/// </summary>
	public String revokeyn;	
	/// <summary>
	/// 
	/// </summary>
	public String bkserial;
	/// <summary>
	/// 
	/// </summary>
	public String retcode;
	/// <summary>
	/// ������ʽ
	/// </summary>
	public String accept;
	/// <summary>
	/// ������
	/// </summary>
	public String Operator;
	/// <summary>
	/// ��������
	/// </summary>
	public String deliver_time;	
	/// <summary>
	/// �˷�
	/// </summary>
	public String deliverprice;	
	/// <summary>
	/// ������ַ
	/// </summary>
	public String deliveraddress;//
	/// <summary>
	/// 
	/// </summary>
	public String deliver_time_property;
	/// <summary>
	/// 
	/// </summary>
	public String decreaseprice;
	
	/// <summary>
	/// 
	/// </summary>
	public String deliver_method;
	/// <summary>
	/// �Ƿ񿪷�Ʊ
	/// </summary>
	public String invoice;
	/// <summary>
	/// �û�Ҫ��
	/// </summary>
	public String comment;
	/// <summary>
	/// ԭ��ƿ��
	/// </summary>
	public String bottleno;
	/// <summary>
	/// 
	/// </summary>
	public String xdry;
	/// <summary>
	/// 
	/// </summary>
	public String xdrq;
	/// <summary>
	/// 
	/// </summary>
	public String MoveDate;
	/// <summary>
	/// ���κ�
	/// </summary>
	public String truck_id;

	public List<SaleDetail> getSaledetail() {
		return Saledetail;
	}

	public void setSaledetail(List<SaleDetail> saledetail) {
		Saledetail = saledetail;
	}

	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
	public String getGoodscount() {
		return goodscount;
	}
	public void setGoodscount(String goodscount) {
		this.goodscount = goodscount;
	}
	public String getHandphone() {
		return handphone;
	}
	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSaleid() {
		return saleid;
	}
	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStationcode() {
		return stationcode;
	}
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getDeliveryn() {
		return deliveryn;
	}
	public void setDeliveryn(String deliveryn) {
		this.deliveryn = deliveryn;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getTradesum() {
		return tradesum;
	}
	public void setTradesum(String tradesum) {
		this.tradesum = tradesum;
	}
	public String getRevokeyn() {
		return revokeyn;
	}
	public void setRevokeyn(String revokeyn) {
		this.revokeyn = revokeyn;
	}
	public String getBkserial() {
		return bkserial;
	}
	public void setBkserial(String bkserial) {
		this.bkserial = bkserial;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getOperator() {
		return Operator;
	}
	public void setOperator(String operator) {
		Operator = operator;
	}
	public String getDeliver_time() {
		return deliver_time;
	}
	public void setDeliver_time(String deliver_time) {
		this.deliver_time = deliver_time;
	}
	public String getDeliverprice() {
		return deliverprice;
	}
	public void setDeliverprice(String deliverprice) {
		this.deliverprice = deliverprice;
	}
	public String getDeliveraddress() {
		return deliveraddress;
	}
	public void setDeliveraddress(String deliveraddress) {
		this.deliveraddress = deliveraddress;
	}
	public String getDeliver_time_property() {
		return deliver_time_property;
	}
	public void setDeliver_time_property(String deliver_time_property) {
		this.deliver_time_property = deliver_time_property;
	}
	public String getDecreaseprice() {
		return decreaseprice;
	}
	public void setDecreaseprice(String decreaseprice) {
		this.decreaseprice = decreaseprice;
	}
	public String getDeliver_method() {
		return deliver_method;
	}
	public void setDeliver_method(String deliver_method) {
		this.deliver_method = deliver_method;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getBottleno() {
		return bottleno;
	}
	public void setBottleno(String bottleno) {
		this.bottleno = bottleno;
	}
	public String getXdry() {
		return xdry;
	}
	public void setXdry(String xdry) {
		this.xdry = xdry;
	}
	public String getXdrq() {
		return xdrq;
	}
	public void setXdrq(String xdrq) {
		this.xdrq = xdrq;
	}
	public String getMoveDate() {
		return MoveDate;
	}
	public void setMoveDate(String moveDate) {
		MoveDate = moveDate;
	}
	public String getTruck_id() {
		return truck_id;
	}
	public void setTruck_id(String truck_id) {
		this.truck_id = truck_id;
	}
	
  
	
}
