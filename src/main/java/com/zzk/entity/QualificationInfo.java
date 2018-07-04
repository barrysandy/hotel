package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 商户资质信息表的实体类<br/>
* @author: wangpeng
* @date: 2018-03-15 10:12
 */
public class QualificationInfo{
	
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
	 * 商户ID
	 */
	private String merchantId;
	public String getMerchantId(){
		return merchantId;
	}
	public void setMerchantId(String merchantId){
		this.merchantId=merchantId== null ? null : merchantId.trim();
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
	 * 创建时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
}