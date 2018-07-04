package com.zzk.entity;

import java.util.Date;
/**
 * 评论实体类
 * @Description:
 * @author John
 * @date： 2017年12月14日 上午9:54:34
 */
public class Evaluate {
	/**
	 * 评论或回复ID
	 */
    private String id;
    /**
     * 房型ID
     */
    private String roomtypeId;
    /**
     * 综合评分
     */
    private Double score;
    /**
     * 内容
     */
    private String content;
    /**
     * 数据的创建时间
     */
    private Date createTime;
    /**
     * 数据状态
     */
    private Integer status;
    /**
     * 酒店ID
     */
    private String shopId;
     /**
      * 商品ID
      */
    private String goodsId;
     /**
      * 创建者ID
      */
    private String createrId;
    /**
     * 酒店卫生评分
     */
    private Integer healthScore;
     /**
      * 酒店环境评分
      */
    private Integer evnScore;
      /**
       * 酒店的服务评分
       */
    private Integer serviceScore;
      /**
       * 酒店的设施评分
       */
    private Integer facilityScore;
     /**
      * 评论的回复对象ID
      */

    private String parentId;
     /**
      * 数据的类型    1评论 2 回复
      */
    private Integer type;
      /**
       * 订单ID
       */
    private String orderId;
     /**
      * 评论回复状态
      */
    private Integer replyStatus;
      /**
       * 更新者ID
       */
    private String updateId;
     /**
      * 更新的时间
      */
    private Date updateTime;
     /**
      * 图片的路径
      */
    private String imgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoomtypeId() {
        return roomtypeId;
    }

    public void setRoomtypeId(String roomtypeId) {
        this.roomtypeId = roomtypeId == null ? null : roomtypeId.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public Integer getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(Integer healthScore) {
        this.healthScore = healthScore;
    }

    public Integer getEvnScore() {
        return evnScore;
    }

    public void setEvnScore(Integer evnScore) {
        this.evnScore = evnScore;
    }

    public Integer getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
    }

    public Integer getFacilityScore() {
        return facilityScore;
    }

    public void setFacilityScore(Integer facilityScore) {
        this.facilityScore = facilityScore;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs == null ? null : imgs.trim();
    }
}