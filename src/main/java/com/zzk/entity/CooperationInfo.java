package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 商户合作信息表的实体类<br/>
* @author: wangpeng
* @date: 2018-03-15 10:11
 */
public class CooperationInfo{
	
	/**
	 * ID
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 商户ID
	 */
	private String businnessId;
	public String getBusinnessId(){
		return businnessId;
	}
	public void setBusinnessId(String businnessId){
		this.businnessId=businnessId== null ? null : businnessId.trim();
	}
	
	
	/**
	 * 合作方式101佣金率102面议
	 */
	private int commMode;
	public int getCommMode(){
		return commMode;
	}
	public void setCommMode(int commMode){
		this.commMode=commMode;
	}
	
	
	/**
	 * 佣金率
	 */
	private Double commRate;
	public Double getCommRate(){
		return commRate;
	}
	public void setCommRate(Double commRate){
		this.commRate=commRate;
	}
	
	
	/**
	 * 联系人
	 */
	private String conPerson;
	public String getConPerson(){
		return conPerson;
	}
	public void setConPerson(String conPerson){
		this.conPerson=conPerson== null ? null : conPerson.trim();
	}
	
	
	/**
	 * 联系电话
	 */
	private String conPhone;
	public String getConPhone(){
		return conPhone;
	}
	public void setConPhone(String conPhone){
		this.conPhone=conPhone== null ? null : conPhone.trim();
	}
	
	
	/**
	 * 数据状态
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
	
	/**
	 * 备注
	 */
	private String remark;
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark== null ? null : remark.trim();
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
	
}