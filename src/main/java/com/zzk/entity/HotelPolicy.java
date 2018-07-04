package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 酒店政策信息的实体类<br/>
* @author：huashuwen
* @date：2017-11-17 11:46
 */
public class HotelPolicy{
	/**
	 * id
	 */
	private String id;
	public String getId(){
		return id;
	}
	
	
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	/**
	 * 酒店id
	 */
	private String hotelId;
	public String getHotelId(){
		return hotelId;
	}
	
	
	public void setHotelId(String hotelId){
		this.hotelId=hotelId== null ? null : hotelId.trim();
	}
	/**
	 * 最早入住时间
	 */
	private String earliestCheckinTime;
	public String getEarliestCheckinTime(){
		return earliestCheckinTime;
	}
	
	
	public void setEarliestCheckinTime(String earliestCheckinTime){
		this.earliestCheckinTime=earliestCheckinTime;
	}
	/**
	 * 最晚离店时间
	 */
	private String lastestLeaveTime;
	public String getLastestLeaveTime(){
		return lastestLeaveTime;
	}
	
	
	public void setLastestLeaveTime(String lastestLeaveTime){
		this.lastestLeaveTime=lastestLeaveTime;
	}
	/**
	 * 早餐: 1自助早餐 2点餐消费 3不提供早餐
	 */
	private int breakfast;
	public int getBreakfast(){
		return breakfast;
	}
	
	
	public void setBreakfast(int breakfast){
		this.breakfast=breakfast;
	}
	
	/**
	 * 早餐: 1自助早餐 2点餐消费 3不提供早餐
	 */
	private String breakfastStr;
	public String getBreakfastStr(){
		return breakfastStr;
	}
	public void setBreakfastStr(String breakfastStr){
		this.breakfastStr=breakfastStr;
	}
	
	/**
	 * 自助早餐费用
	 */
	private String breakfastFee;
	public String getBreakfastFee(){
		return breakfastFee;
	}
	
	
	public void setBreakfastFee(String breakfastFee){
		this.breakfastFee=breakfastFee== null ? null : breakfastFee.trim();
	}
	/**
	 * 宠物携带： 1需收费 2免费 3不可携带宠物
	 */
	private int petBring;
	public int getPetBring(){
		return petBring;
	}
	
	
	public void setPetBring(int petBring){
		this.petBring=petBring;
	}
	
	private String petBringStr;
	public String getPetBringStr(){
		return petBringStr;
	}
	
	
	public void setPetBringStr(String petBringStr){
		this.petBringStr=petBringStr;
	}
	/**
	 * 携带宠物费用
	 */
	private String petFee;
	public String getPetFee(){
		return petFee;
	}
	
	
	public void setPetFee(String petFee){
		this.petFee=petFee== null ? null : petFee.trim();
	}
	/**
	 * 信用卡：1中国银联 2其他 3不支持信用卡
	 */
	private int creditCard;
	public int getCreditCard(){
		return creditCard;
	}
	
	
	public void setCreditCard(int creditCard){
		this.creditCard=creditCard;
	}
	
	/**
	 * 信用卡：1中国银联 2其他 3不支持信用卡
	 */
	private String creditCardStr;
	public String getCreditCardStr(){
		return creditCardStr;
	}
	
	
	public void setCreditCardStr(String creditCardStr){
		this.creditCardStr=creditCardStr;
	}
	
	/**
	 * 其他信用卡
	 */
	private String otherCreditCard;
	public String getOtherCreditCard(){
		return otherCreditCard;
	}
	
	
	public void setOtherCreditCard(String otherCreditCard){
		this.otherCreditCard=otherCreditCard== null ? null : otherCreditCard.trim();
	}
	/**
	 * 其他政策
	 */
	private String otherPolicy;
	public String getOtherPolicy(){
		return otherPolicy;
	}
	
	
	public void setOtherPolicy(String otherPolicy){
		this.otherPolicy=otherPolicy== null ? null : otherPolicy.trim();
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
	/**
	 * 创建者
	 */
	private String creator;
	public String getCreator(){
		return creator;
	}
	
	
	public void setCreator(String creator){
		this.creator=creator== null ? null : creator.trim();
	}
	/**
	 * 创建时间
	 */
	private Date createDate;
	public Date getCreateDate(){
		return createDate;
	}
	
	
	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}
	/**
	 * 更新者
	 */
	private String updater;
	public String getUpdater(){
		return updater;
	}
	
	
	public void setUpdater(String updater){
		this.updater=updater== null ? null : updater.trim();
	}
	/**
	 * 更新时间
	 */
	private Date updateDate;
	public Date getUpdateDate(){
		return updateDate;
	}
	
	
	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}
}