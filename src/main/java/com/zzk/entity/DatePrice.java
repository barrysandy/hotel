package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 酒店时间价格二维表的实体类<br/>
* @author：huashuwen
* @date：2018-01-29 09:49
 */
public class DatePrice{
	/**
	 * 
	 */
	private String id;
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	/**
	 * 酒店id
	 */
	private String hotelId;
	public String getHotelId(){
		return hotelId;
	}
	
	public void setHotelId(String hotelId){
		this.hotelId=hotelId== null ? null : hotelId.trim();
	}
	
	/**
	 * 执行时间
	 */
	private String executeDate;
	public String getExecuteDate(){
		return executeDate;
	}
	
	public void setExecuteDate(String executeDate){
		this.executeDate=executeDate== null ? null : executeDate.trim();
	}
	
	/**
	 * 最低价格
	 */
	private double price;
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double price){
		this.price=price;
	}
	
	
	/**
	 * 状态
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int status){
		this.status=status;
	}
	
	/**
	 * 商品id
	 */
	private String goodsId;
	public String getGoodsId(){
		return goodsId;
	}
	
	public void setGoodsId(String goodsId){
		this.goodsId=goodsId== null ? null : goodsId.trim();
	}
}