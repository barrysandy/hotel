package com.zzk.entity;

import java.util.*;


/**
 * 所属类别:实体类 <br/> 
 * 用途: 酒店商品信息的实体类<br/>
* @author：sty
* @date：2017-11-02 10:44
 */
public class HotelGoods{
	/**
	 * 商品ID
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
	 * 标签
	 */
	private String label;
	public String getLabel(){
		return label;
	}
	
	
	public void setLabel(String label){
		this.label=label== null ? null : label.trim();
	}
	/**
	 * 商品名称
	 */
	private String goodsName;
	public String getGoodsName(){
		return goodsName;
	}
	
	
	public void setGoodsName(String goodsName){
		this.goodsName=goodsName== null ? null : goodsName.trim();
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
	 * 商品状态
	 */
	private int state;
	public int getState(){
		return state;
	}
	
	
	public void setState(int state){
		this.state=state;
	}
	/**
	 * 价钱
	 */
	private String price;
	public String getPrice(){
		return price;
	}
	
	
	public void setPrice(String price){
		this.price=price== null ? null : price.trim();
	}
	/**
	 * 商品描述
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	
	
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}
	/**
	 * freeWifi 
	 */
	private int freeWifi;
	public int getFreeWifi(){
		return freeWifi;
	}
	public void setFreeWifi(int freeWifi){
		this.freeWifi=freeWifi;
	}
	/**
	 * wifi 0无1有
	 */
	private String wifiStr;
	public String getWifiStr(){
		return wifiStr;
	}
	public void setWifiStr(String wifiStr){
		this.wifiStr=wifiStr;
	}
	
	/**
	 * 早餐 0无1单早2双早
	 */
	private int breakfast;
	public int getBreakfast(){
		return breakfast;
	}
	public void setBreakfast(int breakfast){
		this.breakfast=breakfast;
	}
	/**
	 * 早餐 0无1单早2双早
	 */
	private String breakfastStr;
	public String getBreakfastStr(){
		return breakfastStr;
	}
	public void setBreakfastStr(String breakfastStr){
		this.breakfastStr=breakfastStr;
	}
	
	
	/**
	 * 窗户 0无1有窗
	 */
	private int isWindow;
	public int getIsWindow(){
		return isWindow;
	}
	public void setIsWindow(int isWindow){
		this.isWindow=isWindow;
	}
	
	private String isWindowStr;
	public String getIsWindowStr(){
		return isWindowStr;
	}
	public void setIsWindowStr(String isWindowStr){
		this.isWindowStr=isWindowStr;
	}
	
	/**
	 * 取消 0不可取消 1可取消 2限时取消
	 */
	private int cancel;
	public int getCancel(){
		return cancel;
	}
	public void setCancel(int cancel){
		this.cancel=cancel;
	}
	/**
	 * 取消 0不可取消 1可取消 2限时取消
	 */
	private String cancelStr;
	public String getCancelStr(){
		return cancelStr;
	}
	public void setCancelStr(String cancelStr){
		this.cancelStr = cancelStr;
	}
	/**
	 * 确认时间
	 */
	private int confirm;
	public int getConfirm(){
		return confirm;
	}
	
	
	public void setConfirm(int confirm){
		this.confirm=confirm;
	}
	
	/**
	 * 确认时间
	 */
	private String confirmStr;
	public String getConfirmStr(){
		return confirmStr;
	}
	
	
	public void setConfirmStr(String confirmStr){
		this.confirmStr=confirmStr;
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
	 * 最小库存数
	 */
	private String minStock;
	public String getMinStock(){
		return minStock;
	}
	public void setMinStock(String minStock){
		this.minStock=minStock== null ? null : minStock.trim();
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
	 * 可住人数
	 */
	private String people;
	public String getPeople(){
		return people;
	}
	public void setPeople(String people){
		this.people=people== null ? null : people.trim();
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
	 * 加床信息
	 */
	private String addBed;
	public String getAddBed(){
		return addBed;
	}
	public void setAddBed(String addBed){
		this.addBed=addBed== null ? null : addBed.trim();
	}
	/**
	 * 床型
	 */
	private String bedType;
	public String getBedType(){
		return bedType;
	}
	public void setBedType(String bedType){
		this.bedType=bedType== null ? null : bedType.trim();
	}
	/**
	 * 吸烟信息
	 */
	private String smoke;
	public String getSmoke(){
		return smoke;
	}
	public void setSmoke(String smoke){
		this.smoke=smoke== null ? null : smoke.trim();
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
	 * 加床收费
	 */
	private String bedFee;
	public String getBedFee(){
		return bedFee;
	}
	public void setBedFee(String bedFee){
		this.bedFee=bedFee== null ? null : bedFee.trim();
	}
	/**
	 * 取消时间
	 */
	private String cancelTime;
	public String getCancelTime(){
		return cancelTime;
	}
	public void setCancelTime(String cancelTime){
		this.cancelTime=cancelTime== null ? null : cancelTime.trim();
	}
	/**
	 * 确认时间
	 */
	private String confirmTime;
	public String getConfirmTime(){
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime){
		this.confirmTime=confirmTime== null ? null : confirmTime.trim();
	}
	/**
	 * 封面图片
	 */
	private String coverImg;
	public String getCoverImg(){
		return coverImg;
	}
	public void setCoverImg(String coverImg){
		this.coverImg=coverImg== null ? null : coverImg.trim();
	}
	/**
	 * 图标
	 */
	private List<Map<String,String>> icoList;
	public List<Map<String,String>> getIcoList(){
		return icoList;
	}
	public void setIcoList(List<Map<String,String>> icoList){
		this.icoList=icoList== null ? null : icoList;
	}
	
	/**
	 * 独立卫浴
	 */
	private String bathroom;
	public String getBathroom(){
		return bathroom;
	}
	public void setBathroom(String bathroom){
		this.bathroom=bathroom== null ? null : bathroom;
	}
	/**
	 * 宽带上网
	 */
	
	private String wideband;
	public String getWideband(){
		return wideband;
	}
	public void setWideband(String wideband){
		this.wideband=wideband== null ? null : wideband.trim();
	}
	
	private List<PriceRule> priceruleList;
	public List<PriceRule> getPriceruleList() {
		return priceruleList;
	}


	public void setPriceruleList(List<PriceRule> priceruleList) {
		this.priceruleList = priceruleList;
	}
	/**
	 * hotelId
	 */
	private String hotelId ;
	public String getHotelId(){
		return hotelId;
	}
	public void setHotelId(String hotelId){
		this.hotelId=hotelId== null ? null : hotelId;
	}
//---------------------------------------------
	/**
	 * 用于接收商品规则
	 */
	public Map<String,Object> map;
	public Map<String,Object> getMap() {
		return map;
	}

	public void setMap(Map<String,Object> map) {
		this.map = map == null ? null : map;
	}
	
	/**
	 * 房型名称
	 */
	public String apartmentName;
	public String getApartmentName(){
		return apartmentName;
	}
	public void setApartmentName(String apartmentName){
		this.apartmentName=apartmentName== null ? null : apartmentName;
	}
	
}