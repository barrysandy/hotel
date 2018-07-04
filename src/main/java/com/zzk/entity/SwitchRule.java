package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 房型开关的实体类<br/>
* @author：huashuwen
* @date：2018-01-05 10:03
 */
public class SwitchRule{
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
	 * 房型ID
	 */
	private String roomtypeId;
	public String getRoomtypeId(){
		return roomtypeId;
	}
	
	public void setRoomtypeId(String roomtypeId){
		this.roomtypeId=roomtypeId== null ? null : roomtypeId.trim();
	}
	
	/**
	 * 类型
	 */
	private int type;
	public int getType(){
		return type;
	}
	
	public void setType(int type){
		this.type=type;
	}
	
	/**
	 * 规则描述
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}
	
	/**
	 * 开关 1开 2关
	 */
	private int isOpen;
	public int getIsOpen(){
		return isOpen;
	}
	
	public void setIsOpen(int isOpen){
		this.isOpen=isOpen;
	}
	
	/**
	 * 起始时间
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
	private Date endTime;
	public Date getEndTime(){
		return endTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime=endTime;
	}
	
	/**
	 * 创建记录
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	/**
	 * 创建人
	 */
	private String creator;
	public String getCreator(){
		return creator;
	}
	
	public void setCreator(String creator){
		this.creator=creator== null ? null : creator.trim();
	}
	
	/**
	 * 更新记录
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
	/**
	 * 更新者
	 */
	private String updateMan;
	public String getUpdateMan(){
		return updateMan;
	}
	
	public void setUpdateMan(String updateMan){
		this.updateMan=updateMan== null ? null : updateMan.trim();
	}
	
	/**
	 * 状态
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int status){
		this.status=status;
	}
	
	/**
	 * 开始星期
	 */
	private int weekStart;
	public int getWeekStart(){
		return weekStart;
	}
	
	public void setWeekStart(int weekStart){
		this.weekStart=weekStart;
	}
	
}