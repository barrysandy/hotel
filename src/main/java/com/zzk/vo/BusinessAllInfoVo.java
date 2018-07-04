package com.zzk.vo;

import java.math.BigDecimal;
import java.util.Date;

public class BusinessAllInfoVo {
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
	 * LAN
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
	 * 合作方式101佣金率102面议
	 */
	private int commMode;
	public int getCommMode(){
		return commMode;
	}
	public void setCommMode(int commMode){
		this.commMode=commMode;
	}
	
	
	/**
	 * 佣金率
	 */
	private Double commRate;
	public Double getCommRate(){
		return commRate;
	}
	public void setCommRate(Double commRate){
		this.commRate=commRate;
	}
	
	
	/**
	 * 联系人
	 */
	private String conPerson;
	public String getConPerson(){
		return conPerson;
	}
	public void setConPerson(String conPerson){
		this.conPerson=conPerson== null ? null : conPerson.trim();
	}
	
	
	/**
	 * 联系电话
	 */
	private String conPhone;
	public String getConPhone(){
		return conPhone;
	}
	public void setConPhone(String conPhone){
		this.conPhone=conPhone== null ? null : conPhone.trim();
	}
	
	
	/**
	 * 备注
	 */
	private String remark;
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark== null ? null : remark.trim();
	}
	
	/**
	 * 企业法人
	 */
	private String legalPerson;
	public String getLegalPerson(){
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson){
		this.legalPerson=legalPerson== null ? null : legalPerson.trim();
	}
	
	
	/**
	 * 身份证
	 */
	private String idCard;
	public String getIdCard(){
		return idCard;
	}
	public void setIdCard(String idCard){
		this.idCard=idCard== null ? null : idCard.trim();
	}
	
	
	/**
	 * 身份证照片
	 */
	private String idCardImg;
	public String getIdCardImg(){
		return idCardImg;
	}
	public void setIdCardImg(String idCardImg){
		this.idCardImg=idCardImg== null ? null : idCardImg.trim();
	}
	
	
	/**
	 * 企业注册时间
	 */
	private Date registeredTime;
	public Date getRegisteredTime(){
		return registeredTime;
	}
	public void setRegisteredTime(Date registeredTime){
		this.registeredTime=registeredTime;
	}
	
	
	/**
	 * 注册资金
	 */
	private BigDecimal registeredCapitel;
	public BigDecimal getRegisteredCapitel(){
		return registeredCapitel;
	}
	public void setRegisteredCapitel(BigDecimal registeredCapitel){
		this.registeredCapitel=registeredCapitel;
	}
	
	
	/**
	 * 信用代码
	 */
	private String creditCode;
	public String getCreditCode(){
		return creditCode;
	}
	public void setCreditCode(String creditCode){
		this.creditCode=creditCode== null ? null : creditCode.trim();
	}
	
	
	/**
	 * 营业执照
	 */
	private String license;
	public String getLicense(){
		return license;
	}
	public void setLicense(String license){
		this.license=license== null ? null : license.trim();
	}
	
	
	/**
	 * 营业执照IMG
	 */
	private String licenseImg;
	public String getLicenseImg(){
		return licenseImg;
	}
	public void setLicenseImg(String licenseImg){
		this.licenseImg=licenseImg== null ? null : licenseImg.trim();
	}
	
	/**
	 * token 
	 */
	private String token;
	public String getToken(){
		return token;
	}
	public void setToken(String token){
		this.token=token== null ? null : token.trim();
	}
}
