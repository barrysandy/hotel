package com.zzk.entity;

public class OrderCondition {
	private String shopId;
	//用户ID
	private String userId;
	//订单状态 详细信息OrderConstants
	private Integer orderState;
	//开始时间
	private String startTime;
	//截止时间
	private String endTime;
	//订单编号
	private String orderNum;
	//手机号码
	private String contactMobile;
	//经手人
	private String handleMan;
	//姓名
	private String checkinPerson;

	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId  = userId;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getHandleMan() {
		return handleMan;
	}
	public void setHandleMan(String handleMan) {
		this.handleMan = handleMan;
	}
	public String getCheckinPerson() {
		return checkinPerson;
	}
	public void setCheckinPerson(String checkinPerson) {
		this.checkinPerson = checkinPerson;
	}

	
	
}
