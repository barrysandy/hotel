package com.zzk.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Access {

	private String id;
	public String getId(){
		return id;
	}
	
	
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	/**
	 * 所有者ID
	 */
	private String ownerId;
	public String getOwnerId(){
		return ownerId;
	}
	
	
	public void setOwnerId(String ownerId){
		this.ownerId=ownerId== null ? null : ownerId.trim();
	}
	/**
	 * 资源ID
	 */
	
	private String resourceId;
	public String getResourceId(){
		return resourceId;
	}
	
	
	public void setResourceId(String resourceId){
		this.resourceId=resourceId== null ? null : resourceId.trim();
	}
	/**
	 * 资源类型
	 */
	
	private int resourceType;
	public int getResourceType(){
		return resourceType;
	}
	
	
	public void setResourceType(int resourceType){
		this.resourceType=resourceType;
	}
	
	/**
	 * 资源名称
	 */
	
	private String resourceName;
	public String getResourceName(){
		return resourceName;
	}
	
	
	public void setResourceName(String resourceName){
		this.resourceName=resourceName== null ? null : resourceName.trim();
	}
	
	/**
	 * 访问者ID
	 */
	
	private String sellerId;
	public String getSellerId(){
		return sellerId;
	}
	public void setSellerId(String sellerId){
		this.sellerId=sellerId== null ? null : sellerId.trim();
	}
	
	/**
	 * 商家ID
	 */
	
	private String accessId;
	public String getAccessId(){
		return accessId;
	}
	
	
	public void setAccessId(String accessId){
		this.accessId=accessId== null ? null : accessId.trim();
	}
	
	/**
	 * 访问时间
	 */
	
	private Date accessTime;
	public Date getAccessTime(){
		return accessTime;
	}
	
	
	public void setAccessTime(Date accessTime){
		this.accessTime = accessTime;
	}
	
	
	
	//------------------------------------------------------------------------
	
	
	
}

