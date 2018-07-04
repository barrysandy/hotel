package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 审核信息表的实体类<br/>
* @author: Kun
* @date: 2018-03-06 10:14
 */
public class AuditInfo{
	
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
	 * 审核对象的ID
	 */
	private String objectId;
	public String getObjectId(){
		return objectId;
	}
	public void setObjectId(String objectId){
		this.objectId=objectId== null ? null : objectId.trim();
	}
	
	
	/**
	 * 1=商品  后续扩展
	 */
	private int objectType;
	public int getObjectType(){
		return objectType;
	}
	public void setObjectType(int objectType){
		this.objectType=objectType;
	}
	
	
	/**
	 * 0=待审核 1=审核通过 2=审核不通过 3=系统自动审核通过
	 */
	private int auditState;
	public int getAuditState(){
		return auditState;
	}
	public void setAuditState(int auditState){
		this.auditState=auditState;
	}
	
	
	/**
	 * 审核意见
	 */
	private String auditContent;
	public String getAuditContent(){
		return auditContent;
	}
	public void setAuditContent(String auditContent){
		this.auditContent=auditContent== null ? null : auditContent.trim();
	}
	
	
	/**
	 * 审核时间
	 */
	private Date auditTime;
	public Date getAuditTime(){
		return auditTime;
	}
	public void setAuditTime(Date auditTime){
		this.auditTime=auditTime;
	}
	
	
	/**
	 * 0=无通知 1=内部消息 2=短信通知 3=邮件通知
	 */
	private int informType;
	public int getInformType(){
		return informType;
	}
	public void setInformType(int informType){
		this.informType=informType;
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