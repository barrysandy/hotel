package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 房型信息的实体类<br/>
* @author：sty
* @date：2017-11-02 10:37
 */
public class RoomType{
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
	 * 房型
	 */
	private String apartmentName;
	public String getApartmentName(){
		return apartmentName;
	}
	
	
	public void setApartmentName(String apartmentName){
		this.apartmentName=apartmentName== null ? null : apartmentName.trim();
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
	 * 面积
	 */
	private String area;
	public String getArea(){
		return area;
	}
	
	
	public void setArea(String area){
		this.area=area== null ? null : area.trim();
	}
	/**
	 * 楼层
	 */
	private String floor;
	public String getFloor(){
		return floor;
	}
	
	
	public void setFloor(String floor){
		this.floor=floor== null ? null : floor.trim();
	}
	/**
	 * 床宽
	 */
	private String bed;
	public String getBed(){
		return bed;
	}
	
	
	public void setBed(String bed){
		this.bed=bed== null ? null : bed.trim();
	}
	/**
	 * 入住人数
	 */
	private String people;
	public String getPeople(){
		return people;
	}
	
	
	public void setPeople(String people){
		this.people=people== null ? null : people.trim();
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
	 * 无烟信息
	 */
	private String smoke;
	public String getSmoke(){
		return smoke;
	}
	
	
	public void setSmoke(String smoke){
		this.smoke=smoke== null ? null : smoke.trim();
	}
	/**
	 * 宽带
	 */
	private String wideband;
	public String getWideband(){
		return wideband;
	}
	
	
	public void setWideband(String wideband){
		this.wideband=wideband== null ? null : wideband.trim();
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
	 * 
	 */
	private String coverImg;
	public String getCoverImg(){
		return coverImg;
	}
	
	
	public void setCoverImg(String coverImg){
		this.coverImg=coverImg== null ? null : coverImg.trim();
	}
	/**
	 * 
	 */
	private String imgs;
	public String getImgs(){
		return imgs;
	}
	
	
	public void setImgs(String imgs){
		this.imgs=imgs== null ? null : imgs.trim();
	}
	/**
	 * 
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	
	
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}
	/**
	 * 床型
	 */
	public String bedType;
	public String getBedType(){
		return bedType;
	}
	public void setBedType(String bedType){
		this.bedType=bedType== null ? null : bedType.trim();
	}
	/**
	 * 加床信息
	 */
	public String addBed;
	public String getAddBed(){
		return addBed;
	}
	public void setAddBed(String addBed){
		this.addBed=addBed== null ? null : addBed.trim();
	}
	/**
	 * 房型状态
	 */
	public int states;
	public int getStates(){
		return states;
	}
	public void setStates(int states){
		this.states=states;
	}
	/**
	 * 房型状态
	 */
	public String statesStr;
	public String getStatesStr(){
		return statesStr;
	}
	public void setStatesStr(String statesStr){
		this.statesStr=statesStr;
	}
	/**
	 * 可住儿童数量
	 */
	public String kidNum;
	public String getKidNum(){
		return kidNum;
	}
	public void setKidNum(String kidNum){
		this.kidNum=kidNum== null ? null : kidNum.trim();
	}
	/**
	 * 独立卫浴
	 */
	public String bathroom;
	public String getBathroom(){
		return bathroom;
	}
	public void setBathroom(String bathroom){
		this.bathroom=bathroom== null ? null : bathroom.trim();
	}
	/**
	 * wifi
	 */
	public String freeWifi;
	public String getFreeWifi(){
		return freeWifi;
	}
	public void setFreeWifi(String freeWifi){
		this.freeWifi=freeWifi== null ? null : freeWifi.trim();
	}
	/**
	 * 加床费
	 */
	public String bedFee;
	public String getBedFee(){
		return bedFee;
	}
	public void setBedFee(String bedFee){
		this.bedFee=bedFee== null ? null : bedFee.trim();
	}
	/**
	 * 是否有窗
	 */
	public String isWindow;
	public String getsWindow(){
		return isWindow;
	}
	public void setIsWindow(String isWindow){
		this.isWindow=isWindow== null ? null : isWindow.trim();
	}
	/**
	 * 创建人
	 */
	public String creator;
	public String getCreator(){
		return creator;
	}
	public void setCreator(String creator){
		this.creator=creator== null ? null : creator.trim();
	}
	/**
	 * 更新人
	 */
	public String updater;
	public String getUpdater(){
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater == null ? null : updater.trim();
	}
	
	/**
	 * 合作信息
	 */
	public int cooperateType;
	public int getCooperateType(){
		return cooperateType;
	}

	public void setCooperateType(int cooperateType) {
		this.cooperateType = cooperateType;
	}
	/**
	 * 佣金
	 */
	public String brokerage;
	public String getBrokerage(){
		return brokerage;
	}

	public void setBrokerage(String brokerage) {
		this.brokerage = brokerage == null ? null : brokerage.trim();
	}
	/**
	 * 佣金率
	 */
	public String brokerageProportion;
	public String getBrokerageProportion(){
		return brokerageProportion;
	}

	public void setBrokerageProportion(String brokerageProportion) {
		this.brokerageProportion = brokerageProportion == null ? null : brokerageProportion.trim();
	}
	/**
	 * 初始房量
	 */
	public String initStock;
	public String getInitStock(){
		return initStock;
	}

	public void setInitStock(String initStock) {
		this.initStock = initStock == null ? null : initStock.trim();
	}
	/**
	 * 总房量
	 */
	public String totalStock;
	public String getTotalStock(){
		return initStock;
	}

	public void setTotalStock(String totalStock) {
		this.totalStock = totalStock == null ? null : totalStock.trim();
	}
	// ---------------------------------------------
	/**
	 * 用于接收房量规则
	 */
	public Map<String,Object> map;

	public Map<String,Object> getMap() {
		return map;
	}

	public void setMap(Map<String,Object> map) {
		this.map = map == null ? null : map;
	}
}