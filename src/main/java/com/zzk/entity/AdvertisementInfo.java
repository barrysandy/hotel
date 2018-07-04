package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 广告信息表的实体类<br/>
* @author：huashuwen
* @date：2018-03-09 15:29
 */
public class AdvertisementInfo{
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
	private String advertisementPositionId;
	public String getAdvertisementPositionId(){
		return advertisementPositionId;
	}
	
	public void setAdvertisementPositionId(String advertisementPositionId){
		this.advertisementPositionId=advertisementPositionId== null ? null : advertisementPositionId.trim();
	}
	
	/**
	 * 
	 */
	private String advertisementPositionName;
	public String getAdvertisementPositionName(){
		return advertisementPositionName;
	}
	
	public void setAdvertisementPositionName(String advertisementPositionName){
		this.advertisementPositionName=advertisementPositionName== null ? null : advertisementPositionName.trim();
	}
	
	/**
	 * 
	 */
	private String content;
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content=content== null ? null : content.trim();
	}
	
	/**
	 * 
	 */
	private int time;
	public int getTime(){
		return time;
	}
	
	public void setTime(int time){
		this.time=time;
	}
	
	/**
	 * 
	 */
	private Date startTime;
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime=startTime;
	}
	
	/**
	 * 
	 */
	private Date endTime;
	public Date getEndTime(){
		return endTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime=endTime;
	}
	
	/**
	 * 
	 */
	private int state;
	public int getState(){
		return state;
	}
	
	public void setState(int state){
		this.state=state;
	}
	
	/**
	 * 
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
	private int count;
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		this.count=count;
	}
	
	/**
	 * 
	 */
	private String remark;
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark=remark== null ? null : remark.trim();
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
	private int status;
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int status){
		this.status=status;
	}
	
}