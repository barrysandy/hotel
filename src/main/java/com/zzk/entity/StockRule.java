package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 库存规则信息的实体类<br/>
* @author：sty
* @date：2017-11-02 10:41
 */
public class StockRule{
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
	 * 商品ID
	 */
	private String goodId;
	public String getGoodId(){
		return goodId;
	}
	
	
	public void setGoodId(String goodId){
		this.goodId=goodId== null ? null : goodId.trim();
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
	 * 库存
	 */
	private String stock;
	public String getStock(){
		return stock;
	}
	
	
	public void setStock(String stock){
		this.stock=stock== null ? null : stock.trim();
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
	 * 起始时间（接受json格式传输日期）
	 */
	private long startTimeLong;
	public long getStartTimeLong(){
		return startTimeLong;
	}
	public void setStartTimeLong(long startTimeLong){
		this.startTimeLong=startTimeLong;
	}
	/**
	 * 结束时间（接受json格式传输日期）
	 */
	private long endTimeLong;
	public long getEndTimeLong(){
		return endTimeLong;
	}
	public void setEndTimeLong(long endTimeLong){
		this.endTimeLong=endTimeLong;
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
	 * 
	 */
	private int weekStart;
	public int getWeekStart(){
		return weekStart;
	}
	
	
	public void setWeekStart(int weekStart){
		this.weekStart=weekStart;
	}
	/**
	 * 
	 */
	private int weekEnd;
	public int getWeekEnd(){
		return weekEnd;
	}
	
	public void setWeekEnd(int weekEnd) {
		this.weekEnd = weekEnd;
	}
	/**
	 * 预留房量标记
	 */
	private int isInit;
	public int getIsInit(){
		return isInit;
	}
	
	public void setIsInit(int isInit) {
		this.isInit = isInit;
	}
	
}