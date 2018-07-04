package com.zzk.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 网关报文的实体类<br/>
* @author：zhou.zhengkun
* @date：2017-03-24 17:16
 */
public class PlatPayGatewayMessage{
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
	 * 网关类型 1 支付宝，2 微信，3 银联
	 */
	private int gatewayType;
	public int getGatewayType(){
		return gatewayType;
	}
	
	
	public void setGatewayType(int gatewayType){
		this.gatewayType=gatewayType;
	}
	/**
	 * 订单id
	 */
	private String orderId;
	public String getOrderId(){
		return orderId;
	}
	
	
	public void setOrderId(String orderId){
		this.orderId=orderId== null ? null : orderId.trim();
	}
	/**
	 * 支付人id
	 */
	private String payUserId;
	public String getPayUserId(){
		return payUserId;
	}
	
	
	public void setPayUserId(String payUserId){
		this.payUserId=payUserId== null ? null : payUserId.trim();
	}
	/**
	 * 收款人id
	 */
	private String payeeUserId;
	public String getPayeeUserId(){
		return payeeUserId;
	}
	
	
	public void setPayeeUserId(String payeeUserId){
		this.payeeUserId=payeeUserId== null ? null : payeeUserId.trim();
	}
	/**
	 * 店铺ID，个人可无店铺id
	 */
	private String shopId;
	public String getShopId(){
		return shopId;
	}
	
	
	public void setShopId(String shopId){
		this.shopId=shopId== null ? null : shopId.trim();
	}
	/**
	 * 收款
	 */
	private BigDecimal money;
	public BigDecimal getMoney(){
		return money;
	}
	
	
	public void setMoney(BigDecimal money){
		this.money=money;
	}
	/**
	 * 实际收款
	 */
	private BigDecimal actMoney;
	public BigDecimal getActMoney(){
		return actMoney;
	}
	
	
	public void setActMoney(BigDecimal actMoney){
		this.actMoney=actMoney;
	}
	/**
	 * 发送报文
	 */
	private String sendContent;
	public String getSendContent(){
		return sendContent;
	}
	
	
	public void setSendContent(String sendContent){
		this.sendContent=sendContent== null ? null : sendContent.trim();
	}
	/**
	 * 发送报文时间
	 */
	private Date sendTime;
	public Date getSendTime(){
		return sendTime;
	}
	
	
	public void setSendTime(Date sendTime){
		this.sendTime=sendTime;
	}
	/**
	 * 接受报文
	 */
	private String receiveContent;
	public String getReceiveContent(){
		return receiveContent;
	}
	
	
	public void setReceiveContent(String receiveContent){
		this.receiveContent=receiveContent== null ? null : receiveContent.trim();
	}
	/**
	 * 接受报文时间
	 */
	private Date receiveTime;
	public Date getReceiveTime(){
		return receiveTime;
	}
	
	
	public void setReceiveTime(Date receiveTime){
		this.receiveTime=receiveTime;
	}
	/**
	 * 状态 1 支付成功  ，-1 支付失败
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
	 * 修改时间
	 */
	private Date modifyDate;
	public Date getModifyDate(){
		return modifyDate;
	}
	
	
	public void setModifyDate(Date modifyDate){
		this.modifyDate=modifyDate;
	}

	public String getStatusShow(){
		String result = "";
		if(status == 1 ){
			result = "支付成功";
		}else if(status == -1){
			result = "支付失败";
		}
		return result;
	}

	public String getGatewayTypeShow(){
		String result = "";
		if(status == 1 ){
			result = "支付宝";
		}else if(status == 2){
			result = "微信";
		}else if(status == 3){
			result = "银联";
		}
		return result;
	}
}