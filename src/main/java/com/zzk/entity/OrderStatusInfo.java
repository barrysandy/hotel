package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 提现信息表的实体类的实体类<br/>
* @author: wangpeng
* @date: 2018-04-12 17:52
 */
public class OrderStatusInfo{
	
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
	 * 订单号
	 */
	private String orderNo;
	public String getOrderNo(){
		return orderNo;
	}
	public void setOrderNo(String orderNo){
		this.orderNo=orderNo== null ? null : orderNo.trim();
	}
	
	
	/**
	 * 订单状态
	 */
	private int orderState;
	public int getOrderState(){
		return orderState;
	}
	public void setOrderState(int orderState){
		this.orderState=orderState;
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
	 * 反馈
	 */
	private String refundFeedback;
	public String getRefundFeedback(){
		return refundFeedback;
	}
	public void setRefundFeedback(String refundFeedback){
		this.refundFeedback=refundFeedback== null ? null : refundFeedback.trim();
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
	 * 操作者
	 */
	private String createrName;
	public String getCreaterName(){
		return createrName;
	}
	public void setCreaterName(String createrName){
		this.createrName=createrName== null ? null : createrName.trim();
	}
	
	
	/**
	 * 支付状态
	 */
	private int payState;
	public int getPayState(){
		return payState;
	}
	public void setPayState(int payState){
		this.payState=payState;
	}
	
}