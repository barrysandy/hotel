package com.zzk.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class OrderStatusInfoVo {
	/**
	 * 订单状态
	 */
	private int orderState;

	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	/**
	 * 订单状态str
	 */
	private String orderStateStr;
	
	public String getOrderStateStr() {
		return orderStateStr;
	}
	public void setOrderStateStr(String orderStateStr) {
		this.orderStateStr = orderStateStr;
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
	private String payState;
	public String getPayState(){
		return payState;
	}
	public void setPayState(String payState){
		this.payState=payState;
	}
	/**
	 * 创建时间
	 */
	private String createTime;
	public String getCreateTime(){
		return createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
}
