package com.zzk.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Finance {
    private String id;

    private String shopId;

    private String billNo;

    private Date startTime;

    private Date endTime;

    private Date createDate;

    private Integer type;

    private Integer orderNum;

    private BigDecimal orderMoney;

    private BigDecimal commMoney;

    private BigDecimal actualAmounts;

    private Integer payMode;

    private Integer status;

    private Integer financeStatus;

    private String elecReceipt;

    private String remark;

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

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getCommMoney() {
        return commMoney;
    }

    public void setCommMoney(BigDecimal commMoney) {
        this.commMoney = commMoney;
    }

    public BigDecimal getActualAmounts() {
        return actualAmounts;
    }

    public void setActualAmounts(BigDecimal actualAmounts) {
        this.actualAmounts = actualAmounts;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(Integer financeStatus) {
        this.financeStatus = financeStatus;
    }

    public String getElecReceipt() {
        return elecReceipt;
    }

    public void setElecReceipt(String elecReceipt) {
        this.elecReceipt = elecReceipt == null ? null : elecReceipt.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}