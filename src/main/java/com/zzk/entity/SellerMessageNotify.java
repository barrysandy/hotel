package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 提现信息表的实体类的实体类<br/>
* @author: wangpeng
* @date: 2018-05-09 14:27
 */
public class SellerMessageNotify{
	
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
	 * 商户ID
	 */
	private String sellerId;
	public String getSellerId(){
		return sellerId;
	}
	public void setSellerId(String sellerId){
		this.sellerId=sellerId== null ? null : sellerId.trim();
	}
	
	
	/**
	 * 通知类型1新订单2，财务3满团4差评5退款6取单
	 */
	private int type;
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type=type;
	}
	
	
	/**
	 * 消息数量
	 */
	private int messageNumber;
	public int getMessageNumber(){
		return messageNumber;
	}
	public void setMessageNumber(int messageNumber){
		this.messageNumber=messageNumber;
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