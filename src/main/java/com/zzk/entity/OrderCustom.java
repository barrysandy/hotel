package com.zzk.entity;

import java.util.Date;
import java.util.List;

import com.zzk.vo.OrderStatusInfoVo;

public class OrderCustom extends Order{
	
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
	
	private List<OrderStatusInfoVo> orderStatusInfoVo;
	public List<OrderStatusInfoVo> getOrderStatusInfo() {
		return orderStatusInfoVo;
	}
	public void setOrderStatusInfo(List<OrderStatusInfoVo> orderStatusInfoVo) {
		this.orderStatusInfoVo = orderStatusInfoVo;
	}

}

