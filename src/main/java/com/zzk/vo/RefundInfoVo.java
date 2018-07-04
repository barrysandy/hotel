package com.zzk.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class RefundInfoVo {
	
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
		private String refundSuccessTime;
		public String getRefundSuccessTime(){
			return refundSuccessTime;
		}
		public void setRefundSuccessTime(String refundSuccessTime){
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
		private Integer status;
		public Integer getStatus(){
			return status;
		}
		public void setStatus(Integer status){
			this.status=status;
		}
		
		
		/**
		 * 申请时间
		 */
		private String ApplyTime;
	
		public String getApplyTime() {
			return ApplyTime;
		}
		public void setApplyTime(String applyTime) {
			ApplyTime = applyTime;
		}


		/**
		 * 数据更新时间
		 */
		private String updateTime;
		public String getUpdateTime(){
			return updateTime;
		}
		public void setUpdateTime(String updateTime){
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
