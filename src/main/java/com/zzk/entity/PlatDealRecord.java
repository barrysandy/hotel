package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 交易流水记录的实体类<br/>
* @author: wangpeng
* @date: 2018-03-16 12:05
 */
public class PlatDealRecord{
	
	/**
	 * 流水号（一般为前多少为数字+类别字符）
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 订单号
	 */
	private String orderId;
	public String getOrderId(){
		return orderId;
	}
	public void setOrderId(String orderId){
		this.orderId=orderId== null ? null : orderId.trim();
	}
	
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}
	
	
	/**
	 * 成功时间
	 */
	private Date successDate;
	public Date getSuccessDate(){
		return successDate;
	}
	public void setSuccessDate(Date successDate){
		this.successDate=successDate;
	}
	
	
	/**
	 * 来源账号ID
	 */
	private String sourceId;
	public String getSourceId(){
		return sourceId;
	}
	public void setSourceId(String sourceId){
		this.sourceId=sourceId== null ? null : sourceId.trim();
	}
	
	
	/**
	 * 来源账号用户类型
	 */
	private int sourceType;
	public int getSourceType(){
		return sourceType;
	}
	public void setSourceType(int sourceType){
		this.sourceType=sourceType;
	}
	
	
	/**
	 * 来源账号户名
	 */
	private String sourceName;
	public String getSourceName(){
		return sourceName;
	}
	public void setSourceName(String sourceName){
		this.sourceName=sourceName== null ? null : sourceName.trim();
	}
	
	
	/**
	 * 来源银行名称
	 */
	private String sourceBank;
	public String getSourceBank(){
		return sourceBank;
	}
	public void setSourceBank(String sourceBank){
		this.sourceBank=sourceBank== null ? null : sourceBank.trim();
	}
	
	
	/**
	 * 目标账号ID
	 */
	private String destId;
	public String getDestId(){
		return destId;
	}
	public void setDestId(String destId){
		this.destId=destId== null ? null : destId.trim();
	}
	
	
	/**
	 * 目标账号用户类型
	 */
	private int destType;
	public int getDestType(){
		return destType;
	}
	public void setDestType(int destType){
		this.destType=destType;
	}
	
	
	/**
	 * 目标账号户名
	 */
	private String destName;
	public String getDestName(){
		return destName;
	}
	public void setDestName(String destName){
		this.destName=destName== null ? null : destName.trim();
	}
	
	
	/**
	 * 目标账户银行
	 */
	private String destBank;
	public String getDestBank(){
		return destBank;
	}
	public void setDestBank(String destBank){
		this.destBank=destBank== null ? null : destBank.trim();
	}
	
	
	/**
	 * 流水类型，1 打赏 2 转账 3 订单 4 提现 5 充值 6 退款 7充值保证金 8授信还款
	 */
	private int streamType;
	public int getStreamType(){
		return streamType;
	}
	public void setStreamType(int streamType){
		this.streamType=streamType;
	}
	
	
	/**
	 * 支付标题，打赏，购买支付，平台给商家转账，提现等
	 */
	private String payTitle;
	public String getPayTitle(){
		return payTitle;
	}
	public void setPayTitle(String payTitle){
		this.payTitle=payTitle== null ? null : payTitle.trim();
	}
	
	
	/**
	 * 支付金额
	 */
	private BigDecimal payPrice;
	public BigDecimal getPayPrice(){
		return payPrice;
	}
	public void setPayPrice(BigDecimal payPrice){
		this.payPrice=payPrice;
	}
	
	
	/**
	 * 支付状态，0 失败，1 成功 2 异常
	 */
	private int payState;
	public int getPayState(){
		return payState;
	}
	public void setPayState(int payState){
		this.payState=payState;
	}
	
	
	/**
	 * 支付手续费
	 */
	private BigDecimal payFee;
	public BigDecimal getPayFee(){
		return payFee;
	}
	public void setPayFee(BigDecimal payFee){
		this.payFee=payFee;
	}
	
	
	/**
	 * 支付手续费比例
	 */
	private BigDecimal payRatio;
	public BigDecimal getPayRatio(){
		return payRatio;
	}
	public void setPayRatio(BigDecimal payRatio){
		this.payRatio=payRatio;
	}
	
	
	/**
	 * 实收金额
	 */
	private BigDecimal actualMoney;
	public BigDecimal getActualMoney(){
		return actualMoney;
	}
	public void setActualMoney(BigDecimal actualMoney){
		this.actualMoney=actualMoney;
	}
	
	
	/**
	 * 数据状态
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
	
	/**
	 * 支付渠道 1 支付宝，2 微信，3 银联 4余额 5授信额度
	 */
	private int payChanel;
	public int getPayChanel(){
		return payChanel;
	}
	public void setPayChanel(int payChanel){
		this.payChanel=payChanel;
	}
	
	
	/**
	 * 异常说明
	 */
	private String exceptionDesc;
	public String getExceptionDesc(){
		return exceptionDesc;
	}
	public void setExceptionDesc(String exceptionDesc){
		this.exceptionDesc=exceptionDesc== null ? null : exceptionDesc.trim();
	}
	
	
	/**
	 * 是否对账
	 */
	private int checkState;
	public int getCheckState(){
		return checkState;
	}
	public void setCheckState(int checkState){
		this.checkState=checkState;
	}
	
	
	/**
	 * 是否结算
	 */
	private int isAccount;
	public int getIsAccount(){
		return isAccount;
	}
	public void setIsAccount(int isAccount){
		this.isAccount=isAccount;
	}
	
	
	/**
	 * 修改人id
	 */
	private String modifyUser;
	public String getModifyUser(){
		return modifyUser;
	}
	public void setModifyUser(String modifyUser){
		this.modifyUser=modifyUser== null ? null : modifyUser.trim();
	}
	
	
	/**
	 * 修改时间
	 */
	private Date modifyDate;
	public Date getModifyDate(){
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate){
		this.modifyDate=modifyDate;
	}
	
}