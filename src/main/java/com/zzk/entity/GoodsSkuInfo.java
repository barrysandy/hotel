package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 单品信息表的实体类<br/>
* @author: Kun
* @date: 2018-03-06 15:16
 */
public class GoodsSkuInfo{

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
	 * 关联商品Id
	 */
	private String productId;
	public String getProductId(){
		return productId;
	}
	public void setProductId(String productId){
		this.productId=productId== null ? null : productId.trim();
	}


	/**
	 * 单品名字
	 */
	private String skuName;
	public String getSkuName(){
		return skuName;
	}
	public void setSkuName(String skuName){
		this.skuName=skuName== null ? null : skuName.trim();
	}


	/**
	 * 描述
	 */
	private String skuDesc;
	public String getSkuDesc(){
		return skuDesc;
	}
	public void setSkuDesc(String skuDesc){
		this.skuDesc=skuDesc== null ? null : skuDesc.trim();
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
	 * 创建时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}


	/**
	 * 更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}


	/**
	 * 1=启用 2=未启用
	 */
	private int state;
	public int getState(){
		return state;
	}
	public void setState(int state){
		this.state=state;
	}
	
}