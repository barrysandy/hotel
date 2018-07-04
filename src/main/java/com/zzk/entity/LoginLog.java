package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/>
 * 用途: 登陆日志的实体类<br/>
 *
 * @author：huashuwen
 * @date：2018-03-08 10:19
 */
public class LoginLog {
    /**
     * id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 日期
     */
    private Date loginDate;

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 时间
     */
    private Date loginTime;

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 日期
     */
    private String loginDateStr;

    public String getLoginDateStr() {
        return loginDateStr;
    }

    public void setLoginDateStr(String loginDateStr) {
        this.loginDateStr = loginDateStr;
    }

    /**
     * 时间
     */
    private String loginTimeStr;

    public String getLoginTimeStr() {
        return loginTimeStr;
    }

    public void setLoginTimeStr(String loginTimeStr) {
        this.loginTimeStr = loginTimeStr;
    }

    /**
     * 登陆状态
     */
    private String loginStatus;

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus == null ? null : loginStatus.trim();
    }

    /**
     * 商家id
     */
    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    /**
     * ip
     */
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 登陆地点
     */
    private String loginPlace;

    public String getLoginPlace() {
        return loginPlace;
    }

    public void setLoginPlace(String loginPlace) {
        this.loginPlace = loginPlace == null ? null : loginPlace.trim();
    }

    /**
     * 设备
     */
    private String facility;

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility == null ? null : facility.trim();
    }

    /**
     * 记录状态
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * 商家电话
     */
    private String sellerPhone;

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }
}