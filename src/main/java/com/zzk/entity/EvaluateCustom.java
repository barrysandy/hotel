package com.zzk.entity;

import java.util.Date;
import java.util.List;

public class EvaluateCustom extends Evaluate {
	
	/**
	 * 回复内容
	 */
    private List<EvaluateCustom> evaluateCustoms;  
    /**
     * 用户ID
     */
    private  String userId;
	/**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像
     */
    private String headIMG;
    /**
     * 入住时间
     */
    private Date comeTime;
    /**
     * 离店时间
     */
    private Date leaveTime;
    /**
     * 商品名称
     */
    private String goodsName;
	/**
	 * 图片数组
	 * @return
	 */
    private String[] arrayImgs;
    
    public String[] getArrayImgs() {
		return arrayImgs;
	}
	public void setArrayImgs(String[] arrayImgs) {
		this.arrayImgs = arrayImgs;
	}
	public String getUserId() {
    	return userId;
    }
    public void setUserId(String userId) {
    	this.userId = userId;
    }
	public List<EvaluateCustom> getEvaluateCustoms() {
		return evaluateCustoms;
	}
	public void setEvaluateCustoms(List<EvaluateCustom> evaluateCustoms) {
		this.evaluateCustoms = evaluateCustoms;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadIMG() {
		return headIMG;
	}
	public void setHeadIMG(String headIMG) {
		this.headIMG = headIMG;
	}
	public Date getComeTime() {
		return comeTime;
	}
	public void setComeTime(Date comeTime) {
		this.comeTime = comeTime;
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}   
	/**
	 * 平均数量
	 */
	
	private String alldata="0";
	private String noreply="0";
	private String noimg="0";
	private String goodcount="0";
	private String midcount="0";
	private String badcount="0";
	private String avghealth="0.0";
	private String avgevn="0.0";
	private String avgservice="0.0";
	private String avgfacility="0.0";
	private double avgscore=0.0;



	public String getAlldata() {
		return alldata;
	}
	public void setAlldata(String alldata) {
		this.alldata = alldata;
	}
	public String getNoreply() {
		return noreply;
	}
	public void setNoreply(String noreply) {
		this.noreply = noreply;
	}
	public String getNoimg() {
		return noimg;
	}
	public void setNoimg(String noimg) {
		this.noimg = noimg;
	}
	public String getGoodcount() {
		return goodcount;
	}
	public void setGoodcount(String goodcount) {
		this.goodcount = goodcount;
	}
	public String getMidcount() {
		return midcount;
	}
	public void setMidcount(String midcount) {
		this.midcount = midcount;
	}
	public String getBadcount() {
		return badcount;
	}
	public void setBadcount(String badcount) {
		this.badcount = badcount;
	}
	public String getAvghealth() {
		return avghealth;
	}
	public void setAvghealth(String avghealth) {
		this.avghealth = avghealth;
	}
	public String getAvgevn() {
		return avgevn;
	}
	public void setAvgevn(String avgevn) {
		this.avgevn = avgevn;
	}
	public String getAvgservice() {
		return avgservice;
	}
	public void setAvgservice(String avgservice) {
		this.avgservice = avgservice;
	}
	public String getAvgfacility() {
		return avgfacility;
	}
	public void setAvgfacility(String avgfacility) {
		this.avgfacility = avgfacility;
	}
	public double getAvgscore() {
		return avgscore;
	}
	public void setAvgscore(double avgscore) {
		this.avgscore = avgscore;
	}

	
}
