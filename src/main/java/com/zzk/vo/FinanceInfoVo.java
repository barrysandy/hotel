package com.zzk.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 账单VO类
 * @Description:
 * @author John
 * @date： 2018年3月10日 下午5:12:13
 */
public class FinanceInfoVo {
	/**
	 * 订单信息
	 */
	private OrderBaseInfoVo orderBaseInfVo;
		
	
	public OrderBaseInfoVo getOrderBaseInfVo() {
		return orderBaseInfVo;
	}
	public void setOrderBaseInfVo(OrderBaseInfoVo orderBaseInfVo) {
		this.orderBaseInfVo = orderBaseInfVo;
	}


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
	 * 商户ID
	 */
	private String sellerId;
	public String getSellerId(){
		return sellerId;
	}
	public void setSellerId(String sellerId){
		this.sellerId=sellerId== null ? null : sellerId.trim();
	}
	
	
	/**
	 * 账单ID
	 */
	private String billNo;
	public String getBillNo(){
		return billNo;
	}
	public void setBillNo(String billNo){
		this.billNo=billNo== null ? null : billNo.trim();
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
	 * 佣金
	 */
	private BigDecimal commMoney;
	public BigDecimal getCommMoney(){
		return commMoney;
	}
	public void setCommMoney(BigDecimal commMoney){
		this.commMoney=commMoney;
	}
		
	/**
	 * 账单状态
	 */
	private int billStatus;
	public int getBillStatus(){
		return billStatus;
	}
	public void setBillStatus(int billStatus){
		this.billStatus=billStatus;
	}
	/**
	 * 实结金额
	 */
	private BigDecimal actualMoney;
	public BigDecimal getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(BigDecimal actualMoney) {
		this.actualMoney = actualMoney;
	}
	/**
	 * 开始时间
	 */
	private String startTime;

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 结束时间
	 */
	private String endTime;


	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 订单数量
	 */
	private Integer orderNum;


	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	/**
	 * 发票状态
	 */
	private Integer invStatus;
	public Integer getInvStatus(){
		return invStatus;
	}
	public void setInvStatus(Integer invStatus){
		this.invStatus=invStatus;
	}
	
	
	/**
	 * 发票金额
	 */
	private BigDecimal invMoney;
	public BigDecimal getInvMoney(){
		return invMoney;
	}
	public void setInvMoney(BigDecimal invMoney){
		this.invMoney=invMoney;
	}
}
