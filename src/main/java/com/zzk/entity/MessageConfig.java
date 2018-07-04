package com.zzk.entity;

public class MessageConfig {
    private String id;

    private String shopId;

    private String newOrderMsg;

    private String refundMsg;

    private String cancelOrderMsg;

    private String badCommentMsg;

    private String financialMsg;

    private String fullRoomMsg;

    private String messageEmail;

    private String messagePhone;

    private String messageTel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getNewOrderMsg() {
        return newOrderMsg;
    }

    public void setNewOrderMsg(String newOrderMsg) {
        this.newOrderMsg = newOrderMsg == null ? null : newOrderMsg.trim();
    }

    public String getRefundMsg() {
        return refundMsg;
    }

    public void setRefundMsg(String refundMsg) {
        this.refundMsg = refundMsg == null ? null : refundMsg.trim();
    }

    public String getCancelOrderMsg() {
        return cancelOrderMsg;
    }

    public void setCancelOrderMsg(String cancelOrderMsg) {
        this.cancelOrderMsg = cancelOrderMsg == null ? null : cancelOrderMsg.trim();
    }

    public String getBadCommentMsg() {
        return badCommentMsg;
    }

    public void setBadCommentMsg(String badCommentMsg) {
        this.badCommentMsg = badCommentMsg == null ? null : badCommentMsg.trim();
    }

    public String getFinancialMsg() {
        return financialMsg;
    }

    public void setFinancialMsg(String financialMsg) {
        this.financialMsg = financialMsg == null ? null : financialMsg.trim();
    }

    public String getFullRoomMsg() {
        return fullRoomMsg;
    }

    public void setFullRoomMsg(String fullRoomMsg) {
        this.fullRoomMsg = fullRoomMsg == null ? null : fullRoomMsg.trim();
    }

    public String getMessageEmail() {
        return messageEmail;
    }

    public void setMessageEmail(String messageEmail) {
        this.messageEmail = messageEmail == null ? null : messageEmail.trim();
    }

    public String getMessagePhone() {
    	
        return messagePhone;
    }

    public void setMessagePhone(String messagePhone) {
        this.messagePhone = messagePhone == null ? null : messagePhone.trim();
    }

    public String getMessageTel() {
        return messageTel;
    }

    public void setMessageTel(String messageTel) {
        this.messageTel = messageTel == null ? null : messageTel.trim();
    }
}