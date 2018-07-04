	package com.zzk.vo;
	
	import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
	
	/**
	 * 给前台返回的VO对象
	 * @Description:
	 * @author John
	 * @date： 2018年3月13日 下午7:58:16
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	public class OrderBaseInfoVo{
		/**
		 * 订单状态改变表
		 */
	private List<OrderStatusInfoVo> orderStatusInfoVo;
	public List<OrderStatusInfoVo> getOrderStatusInfo() {
		return orderStatusInfoVo;
	}
	public void setOrderStatusInfo(List<OrderStatusInfoVo> orderStatusInfoVo) {
		this.orderStatusInfoVo = orderStatusInfoVo;
	}
	
	/**
	 * 订单详情	
	 */
	private OrderDetailInfoVo orderDetailInfoVo;
	public OrderDetailInfoVo getOrderDetailInfoVo() {
		return orderDetailInfoVo;
	}
	public void setOrderDetailInfoVo(OrderDetailInfoVo orderDetailInfoVo) {
		this.orderDetailInfoVo = orderDetailInfoVo;
	}
	/**
	 * 退款详情
	 */
	private RefundInfoVo refundInfoVo;
	

	public RefundInfoVo getRefundInfoVo() {
		return refundInfoVo;
	}
	public void setRefundInfoVo(RefundInfoVo refundInfoVo) {
		this.refundInfoVo = refundInfoVo;
	}

	/**
	 * 主键ID
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
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
	 * 订单名称
	 */
	private String orderName;
	public String getOrderName(){
		return orderName;
	}
	public void setOrderName(String orderName){
		this.orderName=orderName== null ? null : orderName.trim();
	}
	/**
	 * 商家名称
	 */
	private String sellerName;
	
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/**
	 * 账单ID
	 */
	private String billId;
	public String getBillId(){
		return billId;
	}
	public void setBillId(String billId){
		this.billId=billId== null ? null : billId.trim();
	}
	
	
	/**
	 * 订单金额
	 */
	private BigDecimal orderMoney;
	public BigDecimal getOrderMoney(){
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney){
		this.orderMoney=orderMoney;
	}
	
	
	/**
	 * 实付金额
	 */
	private BigDecimal actualMoney;
	public BigDecimal getActualMoney(){
		return actualMoney;
	}
	public void setActualMoney(BigDecimal actualMoney){
		this.actualMoney=actualMoney;
	}
	
	
	/**
	 * 0=待支付 1=已支付
	 */
	private int payState;
	public int getPayState(){
		return payState;
	}
	public void setPayState(int payState){
		this.payState=payState;
	}
	/**
	 * 1：支付宝 2：微信
	 */
	private int payMode;
	public int getPayMode(){
		return payMode;
	}
	public void setPayMode(int payMode){
		this.payMode=payMode;
	}
	/**
	 * 支付时间
	 */
	private String payTime;
	public String getPayTime(){
		return payTime;
	}
	public void setPayTime(String payTime){
		this.payTime=payTime;
	}
	/**
	 * 1 待付款 2 待确认 3 待评价 4 已完成 5 退款申请 6 退款中 7 退款成功 8 已取消（商家） 9 已取消（用户） 10 已取消（系统）
	 */
	private int orderState;
	public int getOrderState(){
		return orderState;
	}
	public void setOrderState(int orderState){
		this.orderState=orderState;
	}
	/**
	 * 下订单的时间
	 */
	private String orderTime;
	public String getOrderTime(){
		return orderTime;
	}
	public void setOrderTime(String orderTime){
		this.orderTime=orderTime;
	}
	/**
	 * 出行日期（可理解为订单的发团时间）
	 */
	private String tripTime;
	public String getTripTime(){
		return tripTime;
	}
	public void setTripTime(String tripTime){
		this.tripTime=tripTime;
	}
	
	/**
	 * 联系人
	 */
	private String contactPerson;
	public String getContactPerson(){
		return contactPerson;
	}
	public void setContactPerson(String contactPerson){
		this.contactPerson=contactPerson== null ? null : contactPerson.trim();
	}
	/**
	 * 联系人电话
	 */
	private String contactPhone;
	public String getContactPhone(){
		return contactPhone;
	}
	public void setContactPhone(String contactPhone){
		this.contactPhone=contactPhone== null ? null : contactPhone.trim();
	}
	/**
	 * 最后一次修改时间
	 */
	private String updateTime;

	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 订单类型
	 */
	private int orderType;

	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
}
