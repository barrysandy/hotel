package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 单天行程安排表的实体类<br/>
* @author: Kun
* @date: 2018-03-06 14:38
 */
public class SinglePlan{

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
	 * 关联的行程安排ID
	 */
	private String planId;
	public String getPlanId(){
		return planId;
	}
	public void setPlanId(String planId){
		this.planId=planId== null ? null : planId.trim();
	}


	/**
	 * 行程的第几天
	 */
	private String daySort;
	public String getDaySort(){
		return daySort;
	}
	public void setDaySort(String daySort){
		this.daySort=daySort== null ? null : daySort.trim();
	}


	/**
	 * 开始时间
	 */
	private Date startTime;
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime=startTime;
	}


	/**
	 * 结束时间
	 */
	private Date endTie;
	public Date getEndTie(){
		return endTie;
	}
	public void setEndTie(Date endTie){
		this.endTie=endTie;
	}


	/**
	 * 行程(目的地),用逗号隔开
	 */
	private String destination;
	public String getDestination(){
		return destination;
	}
	public void setDestination(String destination){
		this.destination=destination== null ? null : destination.trim();
	}


	/**
	 * 目的地经度
	 */
	private BigDecimal lon;
	public BigDecimal getLon(){
		return lon;
	}
	public void setLon(BigDecimal lon){
		this.lon=lon;
	}


	/**
	 * 目的地纬度
	 */
	private BigDecimal lat;
	public BigDecimal getLat(){
		return lat;
	}
	public void setLat(BigDecimal lat){
		this.lat=lat;
	}


	/**
	 * 1=吃2=住3=行4=游5=购6=娱
	 */
	private int nodeType;
	public int getNodeType(){
		return nodeType;
	}
	public void setNodeType(int nodeType){
		this.nodeType=nodeType;
	}


	/**
	 * 亮点
	 */
	private String brightSpot;
	public String getBrightSpot(){
		return brightSpot;
	}
	public void setBrightSpot(String brightSpot){
		this.brightSpot=brightSpot== null ? null : brightSpot.trim();
	}


	/**
	 * 介绍
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}


	/**
	 * 里程
	 */
	private Double mileage;
	public Double getMileage(){
		return mileage;
	}
	public void setMileage(Double mileage){
		this.mileage=mileage;
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