package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

import com.zzk.vo.OrderDetailInfoVo;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 订单信息的实体类<br/>
* @author：sty
* @date：2017-11-02 10:39
 */
public class Order{
	/**
	 * 订单ID
	 */
	private String orderId;
	public String getOrderId(){
		return orderId;
	}
	
	
	public void setOrderId(String orderId){
		this.orderId=orderId== null ? null : orderId.trim();
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
	 * 平台ID
	 */
	private String platId;
	public String getPlatId(){
		return platId;
	}
	
	
	public void setPlatId(String platId){
		this.platId=platId== null ? null : platId.trim();
	}
	/**
	 * 卖家ID
	 */
	private String sellId;
	public String getSellId(){
		return sellId;
	}
	
	
	public void setSellId(String sellId){
		this.sellId=sellId== null ? null : sellId.trim();
	}
	/**
	 * 店铺ID
	 */
	private String shopId;
	public String getShopId(){
		return shopId;
	}
	
	
	public void setShopId(String shopId){
		this.shopId=shopId== null ? null : shopId.trim();
	}
	/**
	 * 商品ID
	 */
	private String goodsId;
	public String getGoodsId(){
		return goodsId;
	}
	
	
	public void setGoodsId(String goodsId){
		this.goodsId=goodsId== null ? null : goodsId.trim();
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
	 * 
	 */
	private String goodsNum;
	public String getGoodsNum(){
		return goodsNum;
	}
	
	
	public void setGoodsNum(String goodsNum){
		this.goodsNum=goodsNum== null ? null : goodsNum.trim();
	}
	/**
	 * 订单总金额
	 */
	private BigDecimal orderMoney;
	public BigDecimal getOrderMoney(){
		return orderMoney;
	}
	
	
	public void setOrderMoney(BigDecimal orderMoney){
		this.orderMoney=orderMoney;
	}
	/**
	 * 优惠额
	 */
	private BigDecimal discountAmount;
	public BigDecimal getDiscountAmount(){
		return discountAmount;
	}
	
	
	public void setDiscountAmount(BigDecimal discountAmount){
		this.discountAmount=discountAmount;
	}
	/**
	 * 运费
	 */
	private BigDecimal transportFee;
	public BigDecimal getTransportFee(){
		return transportFee;
	}
	
	
	public void setTransportFee(BigDecimal transportFee){
		this.transportFee=transportFee;
	}
	/**
	 * 实付额
	 */
	private BigDecimal actualMoney;
	public BigDecimal getActualMoney(){
		return actualMoney;
	}
	
	
	public void setActualMoney(BigDecimal actualMoney){
		this.actualMoney=actualMoney;
	}
	
	/**
	 * 底价金额
	 */
	private BigDecimal basicMoney;
	public BigDecimal getBasicMoney(){
		return basicMoney;
	}
	public void setBasicMoney(BigDecimal basicMoney){
		this.basicMoney=basicMoney;
	}
	
	/**
	 * 订单详情页
	 */
	private String orderDetail;
	public String getOrderDetail(){
		return orderDetail;
	}
	
	
	public void setOrderDetail(String orderDetail){
		this.orderDetail=orderDetail== null ? null : orderDetail.trim();
	}
	/**
	 * 订单生成时间
	 */
	private Date orderTime;
	public Date getOrderTime(){
		return orderTime;
	}
	
	
	public void setOrderTime(Date orderTime){
		this.orderTime=orderTime;
	}
	/**
	 * 订单支付类型1：预订；2：在线支付 ;3 充值
	 */
	private int payType;
	public int getPayType(){
		return payType;
	}
	
	
	public void setPayType(int payType){
		this.payType=payType;
	}
	/**
	 * 订单支付方式1：支付宝；2：微信；3：网银；
	 */
	private int payMode;
	public int getPayMode(){
		return payMode;
	}
	
	
	public void setPayMode(int payMode){
		this.payMode=payMode;
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
	 * 保险ID
	 */
	private String safeId;
	public String getSafeId(){
		return safeId;
	}
	
	
	public void setSafeId(String safeId){
		this.safeId=safeId== null ? null : safeId.trim();
	}
	/**
	 * 入住人
	 */
	private String checkinPerson;
	public String getCheckinPerson(){
		return checkinPerson;
	}
	
	
	public void setCheckinPerson(String checkinPerson){
		this.checkinPerson=checkinPerson== null ? null : checkinPerson.trim();
	}
	/**
	 * 联系人手机
	 */
	private String contactMobile;
	public String getContactMobile(){
		return contactMobile;
	}
	
	
	public void setContactMobile(String contactMobile){
		this.contactMobile=contactMobile== null ? null : contactMobile.trim();
	}
	/**
	 * 状态 -1 删除 1正常
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
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	
	
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	/**
	 * 更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	
	
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	/**
	 * 支付状态0 未支付，1支付
	 */
	private int payState;
	public int getPayState(){
		return payState;
	}
	
	
	public void setPayState(int payState){
		this.payState=payState;
	}
	/**
	 * 对账记录类型0 默认 1 对账数据
	 */
	private int checkType;
	public int getCheckType(){
		return checkType;
	}
	
	
	public void setCheckType(int checkType){
		this.checkType=checkType;
	}
	/**
	 * 对账状态 0待对账 1已对账 -3对账异常 -4 退款
	 */
	private int checkState;
	public int getCheckState(){
		return checkState;
	}
	
	
	public void setCheckState(int checkState){
		this.checkState=checkState;
	}
	/**
	 * 异常处理说明
	 */
	private String exceptionDesc;
	public String getExceptionDesc(){
		return exceptionDesc;
	}
	
	
	public void setExceptionDesc(String exceptionDesc){
		this.exceptionDesc=exceptionDesc== null ? null : exceptionDesc.trim();
	}
	/**
	 * 是否结算0 未结算，1已结算
	 */
	private int isAccount;
	public int getIsAccount(){
		return isAccount;
	}
	
	
	public void setIsAccount(int isAccount){
		this.isAccount=isAccount;
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
	 * 订单号
	 */
	private String orderNum;
	public String getOrderNum(){
		return orderNum;
	}
	
	public void setOrderNum(String orderNum){
		this.orderNum=orderNum== null ? null : orderNum.trim();
	}
	/**
	 * 经手人
	 */
	private String handleMan;
	public String getHandleMan(){
		return handleMan;
	}
	
	public void setHandleMan(String handleMan){
		this.handleMan=handleMan== null ? null : handleMan.trim();
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
	
	/**
	 * 订单状态
	 */
	private String orderStateStr;
	public String getOrderStateStr(){
		return orderStateStr;
	}
	
	public void setOrderStateStr(String orderStateStr){
		this.orderStateStr=orderStateStr== null ? null : orderStateStr.trim();
	}
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	public Date getArriveTime() {
		return arriveTime;
	}


	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	private String orderObject;
	
	/**
	 * order对象数据
	 * @return
	 */
	public String getOrderObject() {
		return orderObject;
	}


	public void setOrderObject(String orderObject) {
		this.orderObject = orderObject;
	}
	/**
	 * 账单ID
	 */
	private String billId;
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId == null ? null : billId.trim();
    }
	//_________
	/**
	 * hotel对象
	 */
	private Hotel hotel;
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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
	 * 支付订单号
	 */
	
	private String payNum;
	public String getPayNum() {
		return payNum;
	}


	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}
	
	/**
	 * openId
	 */
	
	private String openId;
	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 支付订单号
	 */
	
	private String payId;
	public String getPayId() {
		return payId;
	}


	public void setPayId(String payId) {
		this.payId = payId;
	}
	
	/**
	 * orderType
	 */
	private int orderType;
	public int getOrderType(){
		return orderType;
	}
	public void setOrderType(int orderType){
		this.orderType = orderType;
	}
	/**
	 * tripTime
	 */
	private String tripTime;
	public String getTripTime(){
		return tripTime;
	}
	public void setTripTime(String tripTime){
		this.tripTime = tripTime;
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
	 * 天数
	 */
	private int night;
	public int getNight(){
		return night;
	}
	
	public void setNight(int night){
		this.night=night;
	}
	/**
	 * 创建时间
	 */
	private String orderTimeStr;
	public String getOrderTimeStr(){
		return orderTimeStr;
	}
	
	public void setOrderTimeStr(String orderTimeStr){
		this.orderTimeStr=orderTimeStr== null ? null : orderTimeStr.trim();
	}
	/**
	 * 返回前端可否取消标记  0不能取消 1可以去取消
	 */
	private int ableCancel;
	public int getAbleCancel(){
		return ableCancel;
	}
	public void setAbleCancel(int ableCancel){
		this.ableCancel=ableCancel;
	}
	private OrderDetailInfoVo orderDetailInfoVo;
	public OrderDetailInfoVo getOrderDetailInfoVo(){
		return orderDetailInfoVo;
	}
	public void setOrderDetailInfoVo(OrderDetailInfoVo orderDetailInfoVo){
		this.orderDetailInfoVo=orderDetailInfoVo;
	}
}