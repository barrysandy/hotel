package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 评论信息表的实体类<br/>
 * @author: Kun
 * @date: 2018-03-15 10:29
 */
public class ProductEvaluate{

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
	 * 商品
	 */
	private String productId;
	public String getProductId(){
		return productId;
	}
	public void setProductId(String productId){
		this.productId=productId== null ? null : productId.trim();
	}


	/**
	 * 单品
	 */
	private String skuId;
	public String getSkuId(){
		return skuId;
	}
	public void setSkuId(String skuId){
		this.skuId=skuId== null ? null : skuId.trim();
	}

	/**
	 * 评论者ID
	 */
	private String userId;
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId=userId== null ? null : userId.trim();
	}



	/**
	 * 订单编号
	 */
	private String orderNo;
	public String getOrderNo(){
		return orderNo;
	}
	public void setOrderNo(String orderNo){
		this.orderNo=orderNo== null ? null : orderNo.trim();
	}


	/**
	 * 评论内容
	 */
	private String content;
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content=content== null ? null : content.trim();
	}


	/**
	 * 各个维度算平均值
	 */
	private Double score;
	public Double getScore(){
		return score;
	}
	public void setScore(Double score){
		this.score=score;
	}


	/**
	 * 多个图片用逗号隔开
	 */
	private String images;
	public String getImages(){
		return images;
	}
	public void setImages(String images){
		this.images=images== null ? null : images.trim();
	}


	/**
	 * 特色评分
	 */
	private int featuresScore;
	public int getFeaturesScore(){
		return featuresScore;
	}
	public void setFeaturesScore(int featuresScore){
		this.featuresScore=featuresScore;
	}


	/**
	 * 服务评分
	 */
	private int serviceScore;
	public int getServiceScore(){
		return serviceScore;
	}
	public void setServiceScore(int serviceScore){
		this.serviceScore=serviceScore;
	}


	/**
	 * 安全评分
	 */
	private int securityScore;
	public int getSecurityScore(){
		return securityScore;
	}
	public void setSecurityScore(int securityScore){
		this.securityScore=securityScore;
	}


	/**
	 * 卫生评分
	 */
	private int healthScore;
	public int getHealthScore(){
		return healthScore;
	}
	public void setHealthScore(int healthScore){
		this.healthScore=healthScore;
	}


	/**
	 * 1 好评（4分以上） 2 中评（2到4分） 3差评（2分以下）
	 */
	private String evaluateType;
	public String getEvaluateType(){
		return evaluateType;
	}
	public void setEvaluateType(String evaluateType){
		this.evaluateType=evaluateType== null ? null : evaluateType.trim();
	}


	/**
	 * 1 已回复 0 未回复
	 */
	private int replayState;
	public int getReplayState(){
		return replayState;
	}
	public void setReplayState(int replayState){
		this.replayState=replayState;
	}


	/**
	 * 回复内容
	 */
	private String replayContent;
	public String getReplayContent(){
		return replayContent;
	}
	public void setReplayContent(String replayContent){
		this.replayContent=replayContent== null ? null : replayContent.trim();
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
	 * 回复时间
	 */
	private Date replayTime;
	public Date getReplayTime(){
		return replayTime;
	}
	public void setReplayTime(Date replayTime){
		this.replayTime=replayTime;
	}


	/**
	 * 评论时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}


	/**
	 * s更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
	/**
	 * 评论类型 1线路 2酒店
	 * 
	 */
	private int type;
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type=type;
	}
}