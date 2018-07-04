package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 商品图片表的实体类<br/>
* @author: Kun
* @date: 2018-03-06 14:26
 */
public class ProductImages{
	
	/**
	 * 
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 有些图片和商品关联
	 */
	private String productId;
	public String getProductId(){
		return productId;
	}
	public void setProductId(String productId){
		this.productId=productId== null ? null : productId.trim();
	}
	
	
	/**
	 * 有些图片和单品关联
	 */
	private String skuId;
	public String getSkuId(){
		return skuId;
	}
	public void setSkuId(String skuId){
		this.skuId=skuId== null ? null : skuId.trim();
	}
	
	
	/**
	 * 图片访问地址
	 */
	private String url;
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url=url== null ? null : url.trim();
	}
	
	
	/**
	 * 
	 */
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	
	/**
	 * 
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}
	
	
	/**
	 * 1=普通图片 2=封面图片 3=大图 ...
	 */
	private int type;
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type=type;
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
	 * 
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
}