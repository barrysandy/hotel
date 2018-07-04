package com.zzk.entity;
/**
 * message扩展类，小心方式数组化
 * @author John
 *
 */
public class MessageConfigCustom extends MessageConfig {
	private String[] newOrderMsgs={};
	private String[] refundMsgs={};
	private String[] cancelOrderMsgs={};
	private String[] badCommentMsgs={};
	private String[] financialMsgs={};
	private String[] fullRoomMsgs={};
	
	
	public String[] getNewOrderMsgs() {
		return newOrderMsgs;
	}
	public void setNewOrderMsgs(String[] newOrderMsgs) {
		this.newOrderMsgs = newOrderMsgs;
	}
	public String[] getRefundMsgs() {
		return refundMsgs;
	}
	public void setRefundMsgs(String[] refundMsgs) {
		this.refundMsgs = refundMsgs;
	}
	public String[] getCancelOrderMsgs() {
		return cancelOrderMsgs;
	}
	public void setCancelOrderMsgs(String[] cancelOrderMsgs) {
		this.cancelOrderMsgs = cancelOrderMsgs;
	}
	public String[] getBadCommentMsgs() {
		return badCommentMsgs;
	}
	public void setBadCommentMsgs(String[] badCommentMsgs) {
		this.badCommentMsgs = badCommentMsgs;
	}
	public String[] getFinancialMsgs() {
		return financialMsgs;
	}
	public void setFinancialMsgs(String[] financialMsgs) {
		this.financialMsgs = financialMsgs;
	}
	public String[] getFullRoomMsgs() {
		return fullRoomMsgs;
	}
	public void setFullRoomMsgs(String[] fullRoomMsgs) {
		this.fullRoomMsgs = fullRoomMsgs;
	}
	
	
}
