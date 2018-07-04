package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 酒店服务设施信息的实体类<br/>
* @author：huashuwen
* @date：2017-11-17 14:21
 */
public class HotelServe{
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
	 * 停车场
	 */
	private int park;
	public int getPark(){
		return park;
	}
	
	
	public void setPark(int park){
		this.park=park;
	}
	/**
	 * 停车场
	 */
	private String parkStr;
	public String getParkStr(){
		return parkStr;
	}
	
	
	public void setParkStr(String parkStr){
		this.parkStr=parkStr;
	}
	
	/**
	 * 独立卫浴
	 */
	private int bathroom;
	public int getBathroom(){
		return bathroom;
	}
	
	
	public void setBathroom(int bathroom){
		this.bathroom=bathroom;
	}
	/**
	 * 独立卫浴
	 */
	private String bathroomStr;
	public String getBathroomStr(){
		return bathroomStr;
	}
	
	
	public void setBathroomStr(String bathroomStr){
		this.bathroomStr=bathroomStr;
	}
	/**
	 * 空调
	 */
	private int airConditioner;
	public int getAirConditioner(){
		return airConditioner;
	}
	
	
	public void setAirConditioner(int airConditioner){
		this.airConditioner=airConditioner;
	}
	/**
	 * 空调
	 */
	private String airConditionerStr;
	public String getAirConditionerStr(){
		return airConditionerStr;
	}
	
	
	public void setAirConditionerStr(String airConditionerStr){
		this.airConditionerStr=airConditionerStr;
	}
	/**
	 * 免费wifi
	 */
	private int freeWifi;
	public int getFreeWifi(){
		return freeWifi;
	}
	
	
	public void setFreeWifi(int freeWifi){
		this.freeWifi=freeWifi;
	}
	/**
	 * 免费wifi
	 */
	private String freeWifiStr;
	public String getFreeWifiStr(){
		return freeWifiStr;
	}
	
	
	public void setFreeWifiStr(String freeWifiStr){
		this.freeWifiStr=freeWifiStr;
	}
	/**
	 * 电梯
	 */
	private int elevator;
	public int getElevator(){
		return elevator;
	}
	
	
	public void setElevator(int elevator){
		this.elevator=elevator;
	}
	/**
	 * 电梯
	 */
	private String elevatorStr;
	public String getElevatorStr(){
		return elevatorStr;
	}
	
	
	public void setElevatorStr(String elevatorStr){
		this.elevatorStr=elevatorStr;
	}
	/**
	 * 24小时热水
	 */
	private int hotWater;
	public int getHotWater(){
		return hotWater;
	}
	
	
	public void setHotWater(int hotWater){
		this.hotWater=hotWater;
	}
	/**
	 * 24小时热水
	 */
	private String hotWaterStr;
	public String getHotWaterStr(){
		return hotWaterStr;
	}
	
	
	public void setHotWaterStr(String hotWaterStr){
		this.hotWaterStr=hotWaterStr;
	}
	/**
	 * 免费饮用水
	 */
	private int freeDrinkingWater;
	public int getFreeDrinkingWater(){
		return freeDrinkingWater;
	}
	
	
	public void setFreeDrinkingWater(int freeDrinkingWater){
		this.freeDrinkingWater=freeDrinkingWater;
	}
	/**
	 * 免费饮用水
	 */
	private String freeDrinkingWaterStr;
	public String getFreeDrinkingWaterStr(){
		return freeDrinkingWaterStr;
	}
	
	
	public void setFreeDrinkingWaterStr(String freeDrinkingWaterStr){
		this.freeDrinkingWaterStr=freeDrinkingWaterStr;
	}
	/**
	 * 宽带上网
	 */
	private int adsl;
	public int getAdsl(){
		return adsl;
	}
	
	
	public void setAdsl(int adsl){
		this.adsl=adsl;
	}
	/**
	 * 宽带上网
	 */
	private String adslStr;
	public String getAdslStr(){
		return adslStr;
	}
	
	
	public void setAdslStr(String adslStr){
		this.adslStr=adslStr;
	}
	
	/**
	 * 服务
	 */
	private String service;
	public String getService(){
		return service;
	}
	
	
	public void setService(String service){
		this.service=service== null ? null : service.trim();
	}
	/**
	 * 服务
	 */
	private List<String> serviceList;
	public List<String> getServiceList(){
		return serviceList;
	}
	
	
	public void setServiceList(List<String> serviceList){
		this.serviceList=serviceList;
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
	/**
	 * 记录状态  1正常  2删除
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	
	
	public void setStatus(int status){
		this.status=status;
	}
	/**
	 * 图标路径
	 */
	private String icos;
	public String getIcos(){
		return icos;
	}
	
	public void setIcos(String icos){
		this.icos=icos;
	}
	/**
	 * 图标路径集合
	 */
	private List<String> icoList;
	public List<String> getIcoList(){
		return icoList;
	}
	
	public void setIcoList(List<String> icoList){
		this.icoList=icoList;
	}
	
	
}