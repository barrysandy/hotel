package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 商品分类信息表的实体类<br/>
* @author: Kun
* @date: 2018-03-07 15:04
 */
public class ProductTypeReg{
	
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
	 * 线路,租车,门票 等等等
	 */
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	
	/**
	 * 分类的CODE
	 */
	private String code;
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code=code== null ? null : code.trim();
	}
	
	
	/**
	 * 描述,简介等
	 */
	private String descM;
	public String getDescM(){
		return descM;
	}
	public void setDescM(String descM){
		this.descM=descM== null ? null : descM.trim();
	}
	
	
	/**
	 * 归属类别ID
	 */
	private String parentId;
	public String getParentId(){
		return parentId;
	}
	public void setParentId(String parentId){
		this.parentId=parentId== null ? null : parentId.trim();
	}
	
	
	/**
	 * 排序
	 */
	private int sort;
	public int getSort(){
		return sort;
	}
	public void setSort(int sort){
		this.sort=sort;
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