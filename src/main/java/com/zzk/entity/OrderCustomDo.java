package com.zzk.entity;

import java.util.Date;

public class OrderCustomDo extends OrderBaseInfo{
	
	private HotelGoods hotelGoods;
	
	/**
	 * 最晚取消订单时间
	 * @param confirmTime
	 */
	private Date lastestCancel;
	
	public Date getLastestCancel() {
		return lastestCancel;
	}

	public void setLastestCancel(Date lastestCancel) {
		this.lastestCancel = lastestCancel;
	}


	public HotelGoods getHotelGoods() {
		return hotelGoods;
	}

	public void setHotelGoods(HotelGoods hotelGoods) {
		this.hotelGoods = hotelGoods;
	}
	
	public OrderDetailInfo orderDetailInfo;
	public OrderDetailInfo getOrderDetailInfo() {
		return orderDetailInfo;
	}
	public void setOrderDetailInfo(OrderDetailInfo orderDetailInfo) {
		this.orderDetailInfo = orderDetailInfo;
	}
	/**
	 * 酒店名称
	 */
	private String hotelName;

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	/**
	 * 房型名称
	 */
	private String roomTypeName;
	public String getRoomTypeName(){
		return roomTypeName;
	}
	
	public void setRoomTypeName(String roomTypeName){
		this.roomTypeName=roomTypeName== null ? null : roomTypeName.trim();
	}
}
