package com.zzk.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 订单详情VO
 * @Description:
 * @author John
 * @date： 2018年3月14日 上午11:25:26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailInfoVo{
	
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
	 * 产品ID
	 */
	private String productId;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 商品标题
	 */
	private String  productSubTitle;
	
	
	public String getProductSubTitle() {
		return productSubTitle;
	}
	public void setProductSubTitle(String productSubTitle) {
		this.productSubTitle = productSubTitle;
	}
	/**
	 * 商品名称
	 */
	private String productName;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	private String createTime;
	public String getCreateTime(){
		return createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
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
	
}