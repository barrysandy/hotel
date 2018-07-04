package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 旅游要素类型的实体类<br/>
* @author: huashuwen
* @date: 2018-03-10 11:01
 */
public class TripElementType{
	
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
	 * 图片
	 */
	private String ico;
	public String getIco(){
		return ico;
	}
	public void setIco(String ico){
		this.ico=ico== null ? null : ico.trim();
	}
	
	
	/**
	 * 分类
	 */
	private String type;
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type=type== null ? null : type.trim();
	}
	
	
	/**
	 * 描述
	 */
	private String describption;
	public String getDescribption(){
		return describption;
	}
	public void setDescribption(String describption){
		this.describption=describption== null ? null : describption.trim();
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
	 * 有效标记 -1无效 1有效
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
}