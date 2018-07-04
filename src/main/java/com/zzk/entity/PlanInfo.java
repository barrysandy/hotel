package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 行程安排总表的实体类<br/>
* @author: Kun
* @date: 2018-03-06 11:09
 */
public class PlanInfo{
	
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
	 * 需要后面确定和单品还是商品关联
	 */
	private String productId;
	public String getProductId(){
		return productId;
	}
	public void setProductId(String productId){
		this.productId=productId== null ? null : productId.trim();
	}
	
	
	/**
	 * 需要后面确定和单品还是商品关联
	 */
	private String skuId;
	public String getSkuId(){
		return skuId;
	}
	public void setSkuId(String skuId){
		this.skuId=skuId== null ? null : skuId.trim();
	}
	
	
	/**
	 * 行程总共几天
	 */
	private int days;
	public int getDays(){
		return days;
	}
	public void setDays(int days){
		this.days=days;
	}
	
	
	/**
	 * 行程总共几晚(几晚可能不会用)
	 */
	private int nights;
	public int getNights(){
		return nights;
	}
	public void setNights(int nights){
		this.nights=nights;
	}
	
	
	/**
	 * 行程的描述,简介,或者注意事项放这里
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}
	
	
	/**
	 * 当选择简洁模式进行编辑的时候,这里存储行程所有内容
	 */
	private String content;
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content=content== null ? null : content.trim();
	}
	
	
	/**
	 * 1=简洁模式(html编辑器编辑的)2=详细模式(有单天行程的)
	 */
	private int type;
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type=type;
	}
	
	
	/**
	 * 报名截至天数(页面列表展示的时候应该计算这个时间)
	 */
	private String daysLimit;
	public String getDaysLimit(){
		return daysLimit;
	}
	public void setDaysLimit(String daysLimit){
		this.daysLimit=daysLimit== null ? null : daysLimit.trim();
	}
	
	
	/**
	 * 报名截至小时(这里将天数和小时分开是为了后台好存储并且不需通过小时来计算)
	 */
	private String hoursLimit;
	public String getHoursLimit(){
		return hoursLimit;
	}
	public void setHoursLimit(String hoursLimit){
		this.hoursLimit=hoursLimit== null ? null : hoursLimit.trim();
	}
	
	
	/**
	 * 行程开始地址
	 */
	private String startAddress;
	public String getStartAddress(){
		return startAddress;
	}
	public void setStartAddress(String startAddress){
		this.startAddress=startAddress== null ? null : startAddress.trim();
	}
	
	
	/**
	 * 行程结束地址
	 */
	private String endAddress;
	public String getEndAddress(){
		return endAddress;
	}
	public void setEndAddress(String endAddress){
		this.endAddress=endAddress== null ? null : endAddress.trim();
	}

	/**
	 * 目的地
	 */
	private String destination;
	public String getDestination(){
		return destination;
	}
	public void setDestination(String destination){
		this.destination=destination== null ? null : destination.trim();
	}



	/**
	 * 行程开始日期
	 */
	private Date startTime;
	public Date getStartTime(){
		return startTime;
	}
	public void setStartTime(Date startTime){
		this.startTime=startTime;
	}
	
	
	/**
	 * 行程结束日期
	 */
	private Date endTime;
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime=endTime;
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
	 * 该条数据的创建时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 该条数据的更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
}