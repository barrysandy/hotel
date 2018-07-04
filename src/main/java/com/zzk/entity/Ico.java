package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 图标的实体类<br/>
* @author：huashuwen
* @date：2017-11-28 11:53
 */
public class Ico{
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
	 * 
	 */
	private String type;
	public String getType(){
		return type;
	}
	
	
	public void setType(String type){
		this.type=type== null ? null : type.trim();
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
	private String img;
	public String getImg(){
		return img;
	}
	
	
	public void setImg(String img){
		this.img=img== null ? null : img.trim();
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
	/**
	 * 
	 */
	private String creator;
	public String getCreator(){
		return creator;
	}
	
	
	public void setCreator(String creator){
		this.creator=creator== null ? null : creator.trim();
	}
	/**
	 * 
	 */
	private String updator;
	public String getUpdator(){
		return updator;
	}
	
	
	public void setUpdator(String updator){
		this.updator=updator== null ? null : updator.trim();
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
}