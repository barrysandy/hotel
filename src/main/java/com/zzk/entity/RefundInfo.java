package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 退款信息表的实体类<br/>
* @author: wangpeng
* @date: 2018-03-22 14:13
 */
public class RefundInfo{
	
	/**
	 * 退款ID
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 退款订单号
	 */
	private String refundOrderId;
	public String getRefundOrderId(){
		return refundOrderId;
	}
	public void setRefundOrderId(String refundOrderId){
		this.refundOrderId=refundOrderId== null ? null : refundOrderId.trim();
	}
	
	
	/**
	 * 商户订单号
	 */
	private String mchRefundNo;
	public String getMchRefundNo(){
		return mchRefundNo;
	}
	public void setMchRefundNo(String mchRefundNo){
		this.mchRefundNo=mchRefundNo== null ? null : mchRefundNo.trim();
	}
	
	
	/**
	 * 退款结果
	 */
	private int refundStatus;
	public int getRefundStatus(){
		return refundStatus;
	}
	public void setRefundStatus(int refundStatus){
		this.refundStatus=refundStatus;
	}
	
	
	/**
	 * 退款成功时间
	 */
	private Date refundSuccessTime;
	public Date getRefundSuccessTime(){
		return refundSuccessTime;
	}
	public void setRefundSuccessTime(Date refundSuccessTime){
		this.refundSuccessTime=refundSuccessTime;
	}
	
	
	/**
	 * 支付方式
	 */
	private int payMode;
	public int getPayMode(){
		return payMode;
	}
	public void setPayMode(int payMode){
		this.payMode=payMode;
	}
	
	
	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount;
	public BigDecimal getRefundAmount(){
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount){
		this.refundAmount=refundAmount;
	}
	
	
	/**
	 * 数据状态(-1 删除 1正常)
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
	
	/**
	 * 数据生成时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 数据更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
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
	 * 退款原因
	 */
	private String refundReason;
	public String getRefundReason(){
		return refundReason;
	}
	public void setRefundReason(String refundReason){
		this.refundReason=refundReason== null ? null : refundReason.trim();
	}
	
	
	/**
	 * 处理结果 1 同意 2不同意 3部分退款
	 */
	private int processResult;
	public int getProcessResult(){
		return processResult;
	}
	public void setProcessResult(int processResult){
		this.processResult=processResult;
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
	 * 退款返回数据
	 */
	private String refundData;
	public String getRefundData(){
		return refundData;
	}
	public void setRefundData(String refundData){
		this.refundData=refundData== null ? null : refundData.trim();
	}
	
}