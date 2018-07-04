package com.zzk.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.zzk.entity.validation.ValidationGroupBase;
import com.zzk.entity.validation.ValidationGroupCustom;

public class HotelAccount {
  
	
	@NotBlank(groups={ValidationGroupCustom.class})
    private String id;
	@NotBlank(groups={ValidationGroupBase.class},message="{account.id.error}")
    private String shopId;
	@NotBlank
	private String name;
	
    private Integer payMode;
   // @NotBlank(groups={ValidationGroupBase.class,ValidationGroupCustom.class})
    private String accountName;
    //@NotBlank(groups={ValidationGroupBase.class,ValidationGroupCustom.class})
    private String accountNum;
    //@NotNull(groups={ValidationGroupBase.class,ValidationGroupCustom.class})
    private Integer depositBank;
    //@NotBlank(groups={ValidationGroupBase.class,ValidationGroupCustom.class})
    private String subBank;
    //@NotBlank(groups={ValidationGroupBase.class,ValidationGroupCustom.class})
    private String bankCode;
  //  @NotBlank(groups={ValidationGroupBase.class})
    private String creatorId;

    private Date createDate;
  //  @NotBlank(groups={ValidationGroupCustom.class})
    private String updateId;

    private Date updateDate;

    private String payImg;

    private Integer type;

    private Integer accountStatus;

    private Integer status;

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

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum == null ? null : accountNum.trim();
    }

    public Integer getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(Integer depositBank) {
        this.depositBank = depositBank;
    }

    public String getSubBank() {
        return subBank;
    }

    public void setSubBank(String subBank) {
        this.subBank = subBank == null ? null : subBank.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPayImg() {
        return payImg;
    }

    public void setPayImg(String payImg) {
        this.payImg = payImg == null ? null : payImg.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 用于前端显示
     */
    private String depositBankStr;

	public String getDepositBankStr() {
		return depositBankStr;
	}

	public void setDepositBankStr(String depositBankStr) {
		this.depositBankStr = depositBankStr;
	}
}