package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 库存表的实体类<br/>
* @author: Kun
* @date: 2018-03-06 15:34
 */
public class ProductStockInfo{

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
	 * 单品ID
	 */
	private String skuId;
	public String getSkuId(){
		return skuId;
	}
	public void setSkuId(String skuId){
		this.skuId=skuId== null ? null : skuId.trim();
	}


	/**
	 * 成人票库存
	 */
	private int adultNumber;
	public int getAdultNumber(){
		return adultNumber;
	}
	public void setAdultNumber(int adultNumber){
		this.adultNumber=adultNumber;
	}


	/**
	 * 成人票已售库存
	 */
	private int adultSaledNumber;
	public int getAdultSaledNumber(){
		return adultSaledNumber;
	}
	public void setAdultSaledNumber(int adultSaledNumber){
		this.adultSaledNumber=adultSaledNumber;
	}


	/**
	 * 成人票预占库存（订单操作过程）
	 */
	private int adultPreemptedNumber;
	public int getAdultPreemptedNumber(){
		return adultPreemptedNumber;
	}
	public void setAdultPreemptedNumber(int adultPreemptedNumber){
		this.adultPreemptedNumber=adultPreemptedNumber;
	}


	/**
	 * 儿童票已售库存
	 */
	private int childSaledNumber;
	public int getChildSaledNumber(){
		return childSaledNumber;
	}
	public void setChildSaledNumber(int childSaledNumber){
		this.childSaledNumber=childSaledNumber;
	}


	/**
	 * 儿童票预占库存
	 */
	private int childPreemptedNumber;
	public int getChildPreemptedNumber(){
		return childPreemptedNumber;
	}
	public void setChildPreemptedNumber(int childPreemptedNumber){
		this.childPreemptedNumber=childPreemptedNumber;
	}


	/**
	 * 儿童票总库存
	 */
	private int childNumber;
	public int getChildNumber(){
		return childNumber;
	}
	public void setChildNumber(int childNumber){
		this.childNumber=childNumber;
	}


	/**
	 * 成人票销售价
	 */
	private BigDecimal adultSellPrice;
	public BigDecimal getAdultSellPrice(){
		return adultSellPrice;
	}
	public void setAdultSellPrice(BigDecimal adultSellPrice){
		this.adultSellPrice=adultSellPrice;
	}


	/**
	 * 儿童票销售价
	 */
	private BigDecimal childSellPrice;
	public BigDecimal getChildSellPrice(){
		return childSellPrice;
	}
	public void setChildSellPrice(BigDecimal childSellPrice){
		this.childSellPrice=childSellPrice;
	}


	/**
	 * 原价
	 */
	private BigDecimal originalPrice;
	public BigDecimal getOriginalPrice(){
		return originalPrice;
	}
	public void setOriginalPrice(BigDecimal originalPrice){
		this.originalPrice=originalPrice;
	}


	/**
	 * 发团日期
	 */
	private Date startTime;
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime=startTime;
	}


	/**
	 * 结束日期(预留字段)
	 */
	private Date endTime;
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime=endTime;
	}


	/**
	 * -1=已删除 1=未删除
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}


	/**
	 * 创建日期
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}


	/**
	 * 更新日期
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}


}