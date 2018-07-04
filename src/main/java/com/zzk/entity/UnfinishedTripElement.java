package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 待完善旅游要素的实体类<br/>
* @author: huashuwen
* @date: 2018-03-10 11:02
 */
public class UnfinishedTripElement{
	
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
	 * 标题
	 */
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	
	/**
	 * 分类
	 */
	private String source;
	public String getSource(){
		return source;
	}
	public void setSource(String source){
		this.source=source== null ? null : source.trim();
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
	 * 有效标记 -1无效 1有效
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
	
	/**
	 * 关联ID
	 */
	private String tripElementId;
	public String getTripElementId(){
		return tripElementId;
	}
	public void setTripElementId(String tripElementId){
		this.tripElementId=tripElementId== null ? null : tripElementId.trim();
	}
	
	
	/**
	 * 次数
	 */
	private int frequency;
	public int getFrequency(){
		return frequency;
	}
	public void setFrequency(int frequency){
		this.frequency=frequency;
	}
	
}