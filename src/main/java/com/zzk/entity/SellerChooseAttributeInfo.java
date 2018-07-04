package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 商家选择的扩展属性信息表的实体类<br/>
* @author: Kun
* @date: 2018-03-07 16:05
 */
public class SellerChooseAttributeInfo{
	
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
	 * 表是这是商品的扩展属性
	 */
	private String productId;
	public String getProductId(){
		return productId;
	}
	public void setProductId(String productId){
		this.productId=productId== null ? null : productId.trim();
	}
	
	
	/**
	 * 表是这是单品的扩展属性
	 */
	private String skuId;
	public String getSkuId(){
		return skuId;
	}
	public void setSkuId(String skuId){
		this.skuId=skuId== null ? null : skuId.trim();
	}
	
	
	/**
	 * 平台设置的扩展属性ID
	 */
	private String attrId;
	public String getAttrId(){
		return attrId;
	}
	public void setAttrId(String attrId){
		this.attrId=attrId== null ? null : attrId.trim();
	}
	
	
	/**
	 * 1=商品扩展属性 2=单品扩展属性
	 */
	private String attrType;
	public String getAttrType(){
		return attrType;
	}
	public void setAttrType(String attrType){
		this.attrType=attrType== null ? null : attrType.trim();
	}
	
	
	/**
	 * 属性名称
	 */
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	
	/**
	 * 1=文本框2=单选3=复选4=html编辑器
	 */
	private int type;
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type=type;
	}
	
	
	/**
	 * 需要处理的json字符串(单选或者复选)
	 */
	private String content;
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content=content== null ? null : content.trim();
	}
	
	
	/**
	 * 直接是字符串的值(文本框,html编辑器)
	 */
	private String value;
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value=value== null ? null : value.trim();
	}
	
	
	/**
	 * 属性排序
	 */
	private int sort;
	public int getSort(){
		return sort;
	}
	public void setSort(int sort){
		this.sort=sort;
	}
	
	
	/**
	 * 
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