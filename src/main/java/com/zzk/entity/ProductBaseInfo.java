package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 商品基本信息表的实体类<br/>
 * @author: Kun
 * @date: 2018-03-17 15:07
 */
public class ProductBaseInfo{

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
	 * 商品名
	 */
	private String productName;
	public String getProductName(){
		return productName;
	}
	public void setProductName(String productName){
		this.productName=productName== null ? null : productName.trim();
	}


	/**
	 * 商品副标题
	 */
	private String productSubtitle;
	public String getProductSubtitle(){
		return productSubtitle;
	}
	public void setProductSubtitle(String productSubtitle){
		this.productSubtitle=productSubtitle== null ? null : productSubtitle.trim();
	}


	/**
	 * 商品描述
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}


	/**
	 * 商家ID
	 */
	private String sellerId;
	public String getSellerId(){
		return sellerId;
	}
	public void setSellerId(String sellerId){
		this.sellerId=sellerId== null ? null : sellerId.trim();
	}


	/**
	 * 商品所属类别ID
	 */
	private String categoryId;
	public String getCategoryId(){
		return categoryId;
	}
	public void setCategoryId(String categoryId){
		this.categoryId=categoryId== null ? null : categoryId.trim();
	}


	/**
	 * 1=上架  2=下架
	 */
	private int shelfState;
	public int getShelfState(){
		return shelfState;
	}
	public void setShelfState(int shelfState){
		this.shelfState=shelfState;
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
	 * 0=未审核 1=审核通过 2=审核不通过
	 */
	private int auditState;
	public int getAuditState(){
		return auditState;
	}
	public void setAuditState(int auditState){
		this.auditState=auditState;
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
	 * 修改日期
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}


	/**
	 * 商品展示的标签(小方格的广告词类似)
	 */
	private String label;
	public String getLabel(){
		return label;
	}
	public void setLabel(String label){
		this.label=label== null ? null : label.trim();
	}


	/**
	 * 用于排序的字段
	 */
	private int sort;
	public int getSort(){
		return sort;
	}
	public void setSort(int sort){
		this.sort=sort;
	}


	/**
	 * 1=是推荐商品2=不是推荐商品
	 */
	private int recommended;
	public int getRecommended(){
		return recommended;
	}
	public void setRecommended(int recommended){
		this.recommended=recommended;
	}


	/**
	 * 最近一次上架时间
	 */
	private Date shelveUpTime;
	public Date getShelveUpTime(){
		return shelveUpTime;
	}
	public void setShelveUpTime(Date shelveUpTime){
		this.shelveUpTime=shelveUpTime;
	}


	/**
	 * 最近一次下架时间
	 */
	private Date shelveDownTime;
	public Date getShelveDownTime(){
		return shelveDownTime;
	}
	public void setShelveDownTime(Date shelveDownTime){
		this.shelveDownTime=shelveDownTime;
	}
	/**
	 * 被购买次数
	 */
	private int purchasesNumber;
	public int getPurchasesNumber(){
		return purchasesNumber;
	}
	public void setPurchasesNumber(int purchasesNumber){
		this.purchasesNumber=purchasesNumber;
	}
}