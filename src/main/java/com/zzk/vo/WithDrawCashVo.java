package com.zzk.vo;

import java.math.BigDecimal;

public class WithDrawCashVo {
	/**
	 * 主键ID
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 商户ID
	 */
	private String sellerId;
	public String getSellerId(){
		return sellerId;
	}
	public void setSellerId(String sellerId){
		this.sellerId=sellerId== null ? null : sellerId.trim();
	}
	
	
	/**
	 * 申请时间
	 */
	private String applyTime;
	public String getApplyTime(){
		return applyTime;
	}
	public void setApplyTime(String applyTime){
		this.applyTime=applyTime;
	}
	
	
	/**
	 * 提现金额
	 */
	private BigDecimal cashMoney;
	public BigDecimal getCashMoney(){
		return cashMoney;
	}
	public void setCashMoney(BigDecimal cashMoney){
		this.cashMoney=cashMoney;
	}
	
	private String drawMode;
	
	
	public String getDrawMode() {
		return drawMode;
	}
	public void setDrawMode(String drawMode) {
		this.drawMode = drawMode;
	}


	/**
	 * 支付方式
	 */
	private String payMode;
	public String getPayMode(){
		return payMode;
	}
	public void setPayMode(String payMode){
		this.payMode=payMode;
	}
	
	
	/**
	 * 账号
	 */
	private String accountNum;
	public String getAccountNum(){
		return accountNum;
	}
	public void setAccountNum(String accountNum){
		this.accountNum=accountNum== null ? null : accountNum.trim();
	}
	
	
	/**
	 * 支付时间
	 */
	private String payTime;
	public String getPayTime(){
		return payTime;
	}
	public void setPayTime(String payTime){
		this.payTime=payTime;
	}
	
	
	/**
	 * 提现状态
	 */
	private String payStatus;
	public String getPayStatus(){
		return payStatus;
	}
	public void setPayStatus(String payStatus){
		this.payStatus=payStatus;
	}
	
	/**
	 * 电子回单URL
	 */
	private String elecReceipt;
	public String getElecReceipt(){
		return elecReceipt;
	}
	public void setElecReceipt(String elecReceipt){
		this.elecReceipt=elecReceipt== null ? null : elecReceipt.trim();
	}
	
	
	/**
	 * 流水号
	 */
	private String serialNo;
	public String getSerialNo(){
		return serialNo;
	}
	public void setSerialNo(String serialNo){
		this.serialNo=serialNo== null ? null : serialNo.trim();
	}
	
	/**
	 * 账单状态
	 */
	private String billState;
	public String getBillState(){
		return billState;
	}
	public void setBillState(String billState){
		this.billState=billState;
	}
}
