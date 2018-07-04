package com.zzk.entity;

import java.util.List;
/**
 * 订单信息扩展表
 * @Description:
 * @author John
 * @date： 2018年3月21日 下午12:05:45
 */
public class OrderBaseInfoCustom extends OrderBaseInfo{
	/**
	 * 订单明细
	 */
	private OrderDetailInfo orderDetailInfo;
	public OrderDetailInfo getOrderDetailInfo() {
		return orderDetailInfo;
	}
	public void setOrderDetailInfo(OrderDetailInfo orderDetailInfo) {
		this.orderDetailInfo = orderDetailInfo;
	}
	/**
	 * 订单状态明细
	 */
	private List<OrderStatusInfo> orderStatusInfo;
	public List<OrderStatusInfo> getOrderStatusInfo() {
		return orderStatusInfo;
	}
	public void setOrderStatusInfo(List<OrderStatusInfo> orderStatusInfo) {
		this.orderStatusInfo = orderStatusInfo;
	}
}
