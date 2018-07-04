package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 订单基础信息表的实体类<br/>
* @author: wangpeng
* @date: 2018-03-21 09:27
 */
public class OrderBaseInfo{
	
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
	 * 商家ID
	 */
	private String sellerId;
	public String getSellerId(){
		return sellerId;
	}
	public void setSellerId(String sellerId){
		this.sellerId=sellerId== null ? null : sellerId.trim();
	}
	
	
	/**
	 * 商家名称
	 */
	private String sellerName;
	public String getSellerName(){
		return sellerName;
	}
	public void setSellerName(String sellerName){
		this.sellerName=sellerName== null ? null : sellerName.trim();
	}
	
	
	/**
	 * 买家ID
	 */
	private String buyerId;
	public String getBuyerId(){
		return buyerId;
	}
	public void setBuyerId(String buyerId){
		this.buyerId=buyerId== null ? null : buyerId.trim();
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
	 * 1=待支付 2=已支付3已退款4退款失败
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
	private Date payTime;
	public Date getPayTime(){
		return payTime;
	}
	public void setPayTime(Date payTime){
		this.payTime=payTime;
	}
	
	
	/**
	 *  1 待付款 2 待确认 3待消费 4 待评价 5 已完成 6 退款申请 7 退款中 8 退款成功 9 已取消（商家） 10 已取消（用户） 11 已取消（系统）12 已评价
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
	private Date orderTime;
	public Date getOrderTime(){
		return orderTime;
	}
	public void setOrderTime(Date orderTime){
		this.orderTime=orderTime;
	}
	
	
	/**
	 * 出行日期（可理解为订单的发团时间）
	 */
	private Date tripTime;
	public Date getTripTime(){
		return tripTime;
	}
	public void setTripTime(Date tripTime){
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
	 * 该条数据生成时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 该条数据更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
	
	/**
	 * -1 已删除 1=未删除
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
	
	/**
	 * 公众号支付凭证
	 */
	private String openid;
	public String getOpenid(){
		return openid;
	}
	public void setOpenid(String openid){
		this.openid=openid== null ? null : openid.trim();
	}
	
	
	/**
	 * 支付平台支付ID
	 */
	private String payOrderId;
	public String getPayOrderId(){
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId){
		this.payOrderId=payOrderId== null ? null : payOrderId.trim();
	}
	/**
	 * 用于处理支付下单问题
	 */
	private String currentTimeLong;
	public String getCurrentTimeLong(){
		return currentTimeLong;
	}
	public void setCurrentTimeLong(String currentTimeLong){
		this.currentTimeLong=currentTimeLong== null ? null : currentTimeLong.trim();
	}
	
	/**
	 * 订单佣金
	 */
	private BigDecimal commission;
	public BigDecimal getCommission(){
		return commission;
	}
	public void setCommission(BigDecimal commission){
		this.commission=commission;
	}

	
	/**
	 * 入住人姓名
	 */
	private String checkinPerson;
	public String getCheckinPerson(){
		return checkinPerson;
	}
	public void setCheckinPerson(String checkinPerson){
		this.checkinPerson=checkinPerson== null ? null : checkinPerson.trim();
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
	 * 入住时间
	 */
	private Date comeTime;
	public Date getComeTime(){
		return comeTime;
	}
	public void setComeTime(Date comeTime){
		this.comeTime=comeTime;
	}
	
	
	
	/**
	 * 离店时间
	 */
	private Date leaveTime;
	public Date getLeaveTime(){
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime){
		this.leaveTime=leaveTime;
	}
	/**
	 * 到店时间
	 */
	private Date arriveTime;
	public Date getArriveTime(){
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime){
		this.arriveTime=arriveTime;
	}
	/**
	 * 酒店商品id
	 */
	private String goodsId;
	public String getGoodsId(){
		return goodsId;
	}
	public void setGoodsId(String goodsId){
		this.goodsId=goodsId;
	}
	/**
	 * 预定人
	 */
	
	private String nickName;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 数量
	 */
	private String goodsNum;
	public String getGoodsNum(){
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum){
		this.goodsNum=goodsNum;
	}

	
	/**
	 * 订单类型  1线路   2酒店
	 */
	private int orderType;
	public int getOrderType(){
		return orderType;
	}
	public void setOrderType(int orderType){
		this.orderType=orderType;
	}

}