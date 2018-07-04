package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 旅游要素的实体类<br/>
* @author: huashuwen
* @date: 2018-03-10 10:57
 */
public class TripElement{
	
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
	 * 类别ID
	 */
	private String typeId;
	public String getTypeId(){
		return typeId;
	}
	public void setTypeId(String typeId){
		this.typeId=typeId== null ? null : typeId.trim();
	}
	
	
	/**
	 * 标题
	 */
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	
	/**
	 * 别名
	 */
	private String otherName;
	public String getOtherName(){
		return otherName;
	}
	public void setOtherName(String otherName){
		this.otherName=otherName== null ? null : otherName.trim();
	}
	
	
	/**
	 * 图片
	 */
	private String image;
	public String getImage(){
		return image;
	}
	public void setImage(String image){
		this.image=image== null ? null : image.trim();
	}
	
	
	/**
	 * 视频
	 */
	private String video;
	public String getVideo(){
		return video;
	}
	public void setVideo(String video){
		this.video=video== null ? null : video.trim();
	}
	
	
	/**
	 * 封面
	 */
	private String coverImage;
	public String getCoverImage(){
		return coverImage;
	}
	public void setCoverImage(String coverImage){
		this.coverImage=coverImage== null ? null : coverImage.trim();
	}
	
	
	/**
	 * 内容
	 */
	private String content;
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content=content== null ? null : content.trim();
	}
	
	
	/**
	 * 推荐
	 */
	private String recommend;
	public String getRecommend(){
		return recommend;
	}
	public void setRecommend(String recommend){
		this.recommend=recommend== null ? null : recommend.trim();
	}
	
	
	/**
	 * 经度
	 */
	private BigDecimal lon;
	public BigDecimal getLon(){
		return lon;
	}
	public void setLon(BigDecimal lon){
		this.lon=lon;
	}
	
	
	/**
	 * 纬度
	 */
	private BigDecimal lat;
	public BigDecimal getLat(){
		return lat;
	}
	public void setLat(BigDecimal lat){
		this.lat=lat;
	}
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
	
	/**
	 * 有效标记 -1无效 1有效
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
}