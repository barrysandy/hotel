package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 酒店信息的实体类<br/>
* @author：sty
* @date：2017-11-02 10:26
 */
public class Hotel{
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
	 * 酒店名称
	 */
	private String name;
	public String getName(){
		return name;
	}
	
	
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	/**
	 * 所有者ID
	 */
	private String ownerId;
	public String getOwnerId(){
		return ownerId;
	}
	
	
	public void setOwnerId(String ownerId){
		this.ownerId=ownerId== null ? null : ownerId.trim();
	}
	/**
	 * 联系人姓名
	 */
	private String contacts;
	public String getContacts(){
		return contacts;
	}
	
	
	public void setContacts(String contacts){
		this.contacts=contacts== null ? null : contacts.trim();
	}
	/**
	 * 联系人电话
	 */
	private String contactPhone;
	public String getContactPhone(){
		return contactPhone;
	}
	
	
	public void setContactPhone(String contactPhone){
		this.contactPhone=contactPhone== null ? null : contactPhone.trim();
	}
	/**
	 * 联系人EMAIL
	 */
	private String contactEmail;
	public String getContactEmail(){
		return contactEmail;
	}
	
	
	public void setContactEmail(String contactEmail){
		this.contactEmail=contactEmail== null ? null : contactEmail.trim();
	}
	/**
	 * 前台电话
	 */
	private String receptionPhone;
	public String getReceptionPhone(){
		return receptionPhone;
	}
	
	
	public void setReceptionPhone(String receptionPhone){
		this.receptionPhone=receptionPhone== null ? null : receptionPhone.trim();
	}
	/**
	 * 地址
	 */
	private String address;
	public String getAddress(){
		return address;
	}
	
	
	public void setAddress(String address){
		this.address=address== null ? null : address.trim();
	}
	/**
	 * 维度
	 */
	private Double lat;
	public Double getLat(){
		return lat;
	}
	
	
	public void setLat(Double lat){
		this.lat=lat;
	}
	/**
	 * 经度
	 */
	private Double lon;
	public Double getLon(){
		return lon;
	}
	
	
	public void setLon(Double lon){
		this.lon=lon;
	}
	/**
	 * 星级
	 */
	private String starLevel;
	public String getStarLevel(){
		return starLevel;
	}
	
	
	public void setStarLevel(String starLevel){
		this.starLevel=starLevel== null ? null : starLevel.trim();
	}
	/**
	 * 总客房数
	 */
	private int roomNumber;
	public int getRoomNumber(){
		return roomNumber;
	}
	
	
	public void setRoomNumber(int roomNumber){
		this.roomNumber=roomNumber;
	}
	/**
	 * 连锁品牌
	 */
	private String chainBrand;
	public String getChainBrand(){
		return chainBrand;
	}
	
	
	public void setChainBrand(String chainBrand){
		this.chainBrand=chainBrand== null ? null : chainBrand.trim();
	}
	/**
	 * 开业时间
	 */
	private Date openTime;
	public Date getOpenTime(){
		return openTime;
	}
	
	
	public void setOpenTime(Date openTime){
		this.openTime=openTime;
	}
	/**
	 * 最后装修时间
	 */
	private Date renovationDate;
	public Date getRenovationDate(){
		return renovationDate;
	}
	
	
	public void setRenovationDate(Date renovationDate){
		this.renovationDate=renovationDate;
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
	private int recordStatus;
	public int getRecordStatus(){
		return recordStatus;
	}
	
	
	public void setRecordStatus(int recordStatus){
		this.recordStatus=recordStatus;
	}
	/**
	 * 酒店状态  1正常开业 2关店 3暂停营业 4审核中 5审核不通过
	 */
	private int state;
	public int getState(){
		return state;
	}
	public void setState(int state){
		this.state=state;
	}
	/**
	 * 酒店描述
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	
	
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}
	/**
	 * 
	 */
	private String note;
	public String getNote(){
		return note;
	}
	
	
	public void setNote(String note){
		this.note=note== null ? null : note.trim();
	}
	/**
	 * 
	 */
	private String provinceId;
	public String getProvinceId(){
		return provinceId;
	}
	public void setProvinceId(String provinceId){
		this.provinceId=provinceId== null ? null : provinceId.trim();
	}
	/**
	 * 
	 */
	private String cityId;
	public String getCityId(){
		return cityId;
	}
	
	
	public void setCityId(String cityId){
		this.cityId=cityId== null ? null : cityId.trim();
	}
	/**
	 * 
	 */
	private String areaId;
	public String getAreaId(){
		return areaId;
	}
	
	
	public void setAreaId(String areaId){
		this.areaId=areaId== null ? null : areaId.trim();
	}
	/**
	 * 
	 */
	private String areaName;
	public String getAreaName(){
		return areaName;
	}
	public void setAreaName(String areaName){
		this.areaName=areaName== null ? null : areaName.trim();
	}
	/**
	 * 
	 */
	private String cityName;
	public String getCityName(){
		return cityName;
	}
	public void setCityName(String cityName){
		this.cityName=cityName== null ? null : cityName.trim();
	}
	/**
	 * 
	 */
	private String provinceName;
	public String getProvinceName(){
		return provinceName;
	}
	public void setProvinceName(String provinceName){
		this.provinceName=provinceName== null ? null : provinceName.trim();
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
	public String hotelIcos;
	public String getHotelIcos(){
		return hotelIcos;
	}
	public void setHotelIcos(String hotelIcos){
		this.hotelIcos=hotelIcos== null ? null : hotelIcos.trim();
	}
	/**
	 * 卖点
	 */
	public String sellingPoint;
	public String getSellingPoint(){
		return sellingPoint;
	}
	public void setSellingPoint(String sellingPoint){
		this.sellingPoint=sellingPoint== null ? null : sellingPoint.trim();
	}
	/**
	 * 详情封面
	 */
	private String detailImg;
	public String getDetailImg(){
		return detailImg;
	}
	public void setDetailImg(String detailImg){
		this.detailImg=detailImg== null ? null : detailImg.trim();
	}
	/**
	 * 相册
	 */
	private String album;
	public String getAlbum(){
		return album;
	}
	public void setAlbum(String album){
		this.album=album== null ? null : album.trim();
	}
	
	
	//--------------------------------------------------------------------------
	public HotelServe hotelServe;
	public HotelServe getHotelServe(){
		return hotelServe;
	}
	public void setHotelServe(HotelServe hotelServe){
		this.hotelServe=hotelServe== null ? null : hotelServe;
	}
	
	private List<HotelGoods> goodsList;
	public List<HotelGoods> getGoodsList() {
		return goodsList;
	}


	public void setGoodsList(List<HotelGoods> goodsList) {
		this.goodsList = goodsList;
	}
	
	private BigDecimal distance;
	public BigDecimal getDistance() {
		return distance;
	}


	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}
	
	private int commentNum;
	
	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	private Double commentScore;
	public Double getCommentScore() {
		return commentScore;
	}


	public void setCommentScore(Double commentScore) {
		this.commentScore = commentScore;
	}
	
	private Double miniPrice;
	

	public Double getMiniPrice() {
		return miniPrice;
	}


	public void setMiniPrice(Double miniPrice) {
		this.miniPrice = miniPrice;
	}
	
	private Double avgmin;
	

	public Double getAvgmin() {
		return avgmin;
	}


	public void setAvgmin(Double avgmin) {
		this.avgmin = avgmin;
	}

	
	private Double maxPrice;

	public Double getMaxPrice() {
		return maxPrice;
	}


	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}
	/**
	 * 开业时间
	 */
	private long openTimeLong;
	public long getOpenTimeLong(){
		return openTimeLong;
	}
	public void setOpenTimeLong(long openTimeLong){
		this.openTimeLong=openTimeLong;
	}
	/**
	 * 最后装修时间
	 */
	private long renovationDateLong;
	public long getRenovationDateLong(){
		return renovationDateLong;
	}
	public void setRenovationDateLong(long renovationDateLong){
		this.renovationDateLong=renovationDateLong;
	}
	/**
	 * 90天内最高最低价格
	 */
	private String datePrice;
	public String getDatePrice(){
		return datePrice;
	}
	public void setDatePrice(String datePrice){
		this.datePrice=datePrice;
	}
	
}