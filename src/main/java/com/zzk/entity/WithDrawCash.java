package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 提现信息表的实体类的实体类<br/>
* @author: wangpeng
* @date: 2018-04-12 10:41
 */
public class WithDrawCash{
	
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
	private Date applyTime;
	public Date getApplyTime(){
		return applyTime;
	}
	public void setApplyTime(Date applyTime){
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
	
	
	/**
	 * 支付方式
	 */
	private int payMode;
	public int getPayMode(){
		return payMode;
	}
	public void setPayMode(int payMode){
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
	private Date payTime;
	public Date getPayTime(){
		return payTime;
	}
	public void setPayTime(Date payTime){
		this.payTime=payTime;
	}
	
	
	/**
	 * 提现状态
	 */
	private int payStatus;
	public int getPayStatus(){
		return payStatus;
	}
	public void setPayStatus(int payStatus){
		this.payStatus=payStatus;
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
	/**
	 * 账单状态
	 */
	private int billState;
	public int getBillState(){
		return billState;
	}
	public void setBillState(int billState){
		this.billState=billState;
	}
}