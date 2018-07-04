package com.zzk.vo;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 商户信息表的实体类<br/>
* @author: wangpeng
* @date: 2018-03-15 10:10
 */
public class BusinessInfoVo{
	
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
	 * 用户ID
	 */
	private String userId;
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId=userId== null ? null : userId.trim();
	}
	
	
	/**
	 * 商家类型
	 */
	private int type;
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type=type;
	}
	
	
	/**
	 * 供应商名称
	 */
	private String businName;
	public String getBusinName(){
		return businName;
	}
	public void setBusinName(String businName){
		this.businName=businName== null ? null : businName.trim();
	}
	
	
	/**
	 * 简称
	 */
	private String simpleName;
	public String getSimpleName(){
		return simpleName;
	}
	public void setSimpleName(String simpleName){
		this.simpleName=simpleName== null ? null : simpleName.trim();
	}
	
	
	/**
	 * 主营业务
	 */
	private String businMain;
	public String getBusinMain(){
		return businMain;
	}
	public void setBusinMain(String businMain){
		this.businMain=businMain== null ? null : businMain.trim();
	}
	
	
	/**
	 * 联系人姓名
	 */
	private String personName;
	public String getPersonName(){
		return personName;
	}
	public void setPersonName(String personName){
		this.personName=personName== null ? null : personName.trim();
	}
	
	
	/**
	 * 联系人手机
	 */
	private String phone;
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone=phone== null ? null : phone.trim();
	}
	
	
	/**
	 * 联系人邮箱
	 */
	private String email;
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email== null ? null : email.trim();
	}
	
	
	/**
	 * 咨询电话
	 */
	private String tel;
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel=tel== null ? null : tel.trim();
	}
	
	
	/**
	 * CITYID
	 */
	private String cityId;
	public String getCityId(){
		return cityId;
	}
	public void setCityId(String cityId){
		this.cityId=cityId== null ? null : cityId.trim();
	}
	
	
	/**
	 * AREA_ID
	 */
	private String areaId;
	public String getAreaId(){
		return areaId;
	}
	public void setAreaId(String areaId){
		this.areaId=areaId== null ? null : areaId.trim();
	}
	
	
	/**
	 * ADDRESS
	 */
	private String address;
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address=address== null ? null : address.trim();
	}
	
	
	/**
	 * LAT
	 */
	private Double lat;
	public Double getLat(){
		return lat;
	}
	public void setLat(Double lat){
		this.lat=lat;
	}
	
	
	/**
	 * LON
	 */
	private Double lon;
	public Double getLon(){
		return lon;
	}
	public void setLon(Double lon){
		this.lon=lon;
	}
	
	
	/**
	 * 封面
	 */
	private String coverImg;
	public String getCoverImg(){
		return coverImg;
	}
	public void setCoverImg(String coverImg){
		this.coverImg=coverImg== null ? null : coverImg.trim();
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
	 * 评分
	 */
	private Double commScore;
	public Double getCommScore(){
		return commScore;
	}
	public void setCommScore(Double commScore){
		this.commScore=commScore;
	}
	
	
	/**
	 * 详情图片
	 */
	private String imgDetail;
	public String getImgDetail(){
		return imgDetail;
	}
	public void setImgDetail(String imgDetail){
		this.imgDetail=imgDetail== null ? null : imgDetail.trim();
	}
	
	
	/**
	 * 评论次数
	 */
	private int commNum;
	public int getCommNum(){
		return commNum;
	}
	public void setCommNum(int commNum){
		this.commNum=commNum;
	}
	
	
	/**
	 * 数据状态
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
	
	/**
	 * 商户状态
	 */
	private int businStatus;
	public int getBusinStatus(){
		return businStatus;
	}
	public void setBusinStatus(int businStatus){
		this.businStatus=businStatus;
	}
	
	
	/**
	 * 总线路数
	 */
	private int lineCount;
	public int getLineCount(){
		return lineCount;
	}
	public void setLineCount(int lineCount){
		this.lineCount=lineCount;
	}
	
	
	/**
	 * 创建日期
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 更新日期
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
	
	
}