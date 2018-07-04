package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 订单详情信息表的实体类<br/>
* @author: wangpeng
* @date: 2018-03-21 11:55
 */
public class OrderDetailInfo{
	
	/**
	 * 订单详情主键ID
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 关联订单的订单号
	 */
	private String orderNo;
	public String getOrderNo(){
		return orderNo;
	}
	public void setOrderNo(String orderNo){
		this.orderNo=orderNo== null ? null : orderNo.trim();
	}
	
	
	/**
	 * 关联单品的ID
	 */
	private String skuId;
	public String getSkuId(){
		return skuId;
	}
	public void setSkuId(String skuId){
		this.skuId=skuId== null ? null : skuId.trim();
	}
	
	
	/**
	 * 关联单品的名字
	 */
	private String skuName;
	public String getSkuName(){
		return skuName;
	}
	public void setSkuName(String skuName){
		this.skuName=skuName== null ? null : skuName.trim();
	}
	
	
	/**
	 * 商品ID
	 */
	private String productId;
	public String getProductId(){
		return productId;
	}
	public void setProductId(String productId){
		this.productId=productId== null ? null : productId.trim();
	}
	
	
	/**
	 * 商品名称
	 */
	private String productName;
	public String getProductName(){
		return productName;
	}
	public void setProductName(String productName){
		this.productName=productName== null ? null : productName.trim();
	}
	
	
	/**
	 * 商品标题
	 */
	private String productSubtitle;
	public String getProductSubtitle(){
		return productSubtitle;
	}
	public void setProductSubtitle(String productSubtitle){
		this.productSubtitle=productSubtitle== null ? null : productSubtitle.trim();
	}
	
	
	/**
	 * 商品图片
	 */
	private String image;
	public String getImage(){
		return image;
	}
	public void setImage(String image){
		this.image=image== null ? null : image.trim();
	}
	
	
	/**
	 * 购买时的成人票单价
	 */
	private BigDecimal currentAdultPrice;
	public BigDecimal getCurrentAdultPrice(){
		return currentAdultPrice;
	}
	public void setCurrentAdultPrice(BigDecimal currentAdultPrice){
		this.currentAdultPrice=currentAdultPrice;
	}
	
	
	/**
	 * 购买时的儿童票单价
	 */
	private BigDecimal currentChildPrice;
	public BigDecimal getCurrentChildPrice(){
		return currentChildPrice;
	}
	public void setCurrentChildPrice(BigDecimal currentChildPrice){
		this.currentChildPrice=currentChildPrice;
	}
	
	
	/**
	 * 总价（应与订单价格是一致）
	 */
	private BigDecimal totalPrice;
	public BigDecimal getTotalPrice(){
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice){
		this.totalPrice=totalPrice;
	}
	
	
	/**
	 * 成人票数量
	 */
	private int adultNumber;
	public int getAdultNumber(){
		return adultNumber;
	}
	public void setAdultNumber(int adultNumber){
		this.adultNumber=adultNumber;
	}
	
	
	/**
	 * 儿童票数量
	 */
	private int childNumber;
	public int getChildNumber(){
		return childNumber;
	}
	public void setChildNumber(int childNumber){
		this.childNumber=childNumber;
	}
	
	
	/**
	 * -1 已删除 1未删除
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
	 * 产品库存表ID
	 */
	private String productStockId;
	public String getProductStockId(){
		return productStockId;
	}
	public void setProductStockId(String productStockId){
		this.productStockId=productStockId== null ? null : productStockId.trim();
	}
	
	/**
	 * 酒店取消
	 */
	private int cancel;
	public int getCancel(){
		return cancel;
	}
	public void setCancel(int cancel){
		this.cancel=cancel;
	}
	/**
	 * 取消时间
	 */
	private String cancelTime;
	public String getCancelTime(){
		return cancelTime;
	}
	public void setCancelTime(String cancelTime){
		this.cancelTime=cancelTime== null ? null : cancelTime.trim();
	}
	
}