package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 字典信息表的实体类的实体类<br/>
* @author: wangpeng
* @date: 2018-04-11 17:55
 */
public class FinanceInfo{
	
	/**
	 * ID
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
	 * 账单ID
	 */
	private String billNo;
	public String getBillNo(){
		return billNo;
	}
	public void setBillNo(String billNo){
		this.billNo=billNo== null ? null : billNo.trim();
	}
	
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime=startTime;
	}
	
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime=endTime;
	}
	
	
	/**
	 * 订单数量
	 */
	private int orderCount;
	public int getOrderCount(){
		return orderCount;
	}
	public void setOrderCount(int orderCount){
		this.orderCount=orderCount;
	}
	
	
	/**
	 * 订单金额
	 */
	private BigDecimal orderMoney;
	public BigDecimal getOrderMoney(){
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney){
		this.orderMoney=orderMoney;
	}
	
	
	/**
	 * 佣金
	 */
	private BigDecimal commMoney;
	public BigDecimal getCommMoney(){
		return commMoney;
	}
	public void setCommMoney(BigDecimal commMoney){
		this.commMoney=commMoney;
	}
	
	
	/**
	 * 实结金额
	 */
	private BigDecimal actualMoney;
	public BigDecimal getActualMoney(){
		return actualMoney;
	}
	public void setActualMoney(BigDecimal actualMoney){
		this.actualMoney=actualMoney;
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
	 * 平台
	 */
	private int plat;
	public int getPlat(){
		return plat;
	}
	public void setPlat(int plat){
		this.plat=plat;
	}
	
	
	/**
	 * 账单状态财务状态1，未出账单 2，待提现3，审核中，4，支付中，5，支付成功，支付失败
	 */
	private int billStatus;
	public int getBillStatus(){
		return billStatus;
	}
	public void setBillStatus(int billStatus){
		this.billStatus=billStatus;
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
	 * 支付方式  101对公账户，102个人账户，103支付宝，104微信
	 */
	private int payMode;
	public int getPayMode(){
		return payMode;
	}
	public void setPayMode(int payMode){
		this.payMode=payMode;
	}
	
	/**
	 * 发票状态
	 */
	private int invStatus;
	public int getInvStatus(){
		return invStatus;
	}
	public void setInvStatus(int invStatus){
		this.invStatus=invStatus;
	}
	
	
	/**
	 * 发票金额
	 */
	private BigDecimal invMoney;
	public BigDecimal getInvMoney(){
		return invMoney;
	}
	public void setInvMoney(BigDecimal invMoney){
		this.invMoney=invMoney;
	}
	
	
	/**
	 * 数据生成时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 数据更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
}