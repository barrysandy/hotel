package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 商家账户表的实体类<br/>
* @author: wangpeng
* @date: 2018-03-10 11:10
 */
public class SellerAccount{
	
	/**
	 * 订单详情主键ID
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
	 * 账户类型 100 对公账户  101私人账户 102 支付宝103 微信104
	 */
	private Integer type;
	public Integer getType(){
		return type;
	}
	public void setType(Integer type){
		this.type=type;
	}
	
	
	/**
	 * 账户者姓名
	 */
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	
	/**
	 * 账户名
	 */
	private String accountName;
	public String getAccountName(){
		return accountName;
	}
	public void setAccountName(String accountName){
		this.accountName=accountName== null ? null : accountName.trim();
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
	 * 开户行
	 */
	private Integer depositBank;
	public Integer getDepositBank(){
		return depositBank;
	}
	public void setDepositBank(Integer depositBank){
		this.depositBank=depositBank;
	}
	
	
	/**
	 * 支行
	 */
	private String subBank;
	public String getSubBank(){
		return subBank;
	}
	public void setSubBank(String subBank){
		this.subBank=subBank== null ? null : subBank.trim();
	}
	
	
	/**
	 * 银行行号
	 */
	private String bankCode;
	public String getBankCode(){
		return bankCode;
	}
	public void setBankCode(String bankCode){
		this.bankCode=bankCode== null ? null : bankCode.trim();
	}
	
	
	/**
	 * 二维码
	 */
	private String payQr;
	public String getPayQr(){
		return payQr;
	}
	public void setPayQr(String payQr){
		this.payQr=payQr== null ? null : payQr.trim();
	}
	
	
	/**
	 * 账号状态
	 */
	private Integer accountStatus;
	public Integer getAccountStatus(){
		return accountStatus;
	}
	public void setAccountStatus(Integer accountStatus){
		this.accountStatus=accountStatus;
	}
	
	
	/**
	 * -1 已删除 1未删除
	 */
	private Integer status;
	public Integer getStatus(){
		return status;
	}
	public void setStatus(Integer status){
		this.status=status;
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