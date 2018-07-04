package com.zzk.entity;

import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 广告位信息表的实体类<br/>
* @author：huashuwen
* @date：2018-03-09 15:30
 */
public class AdvertisementPositionInfo{
	/**
	 * id
	 */
	private String id;
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	/**
	 * 名称
	 */
	private String name;
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	/**
	 * 标识符
	 */
	private String identification;
	public String getIdentification(){
		return identification;
	}
	
	public void setIdentification(String identification){
		this.identification=identification== null ? null : identification.trim();
	}
	
	/**
	 * 页面
	 */
	private String page;
	public String getPage(){
		return page;
	}
	
	public void setPage(String page){
		this.page=page== null ? null : page.trim();
	}
	
	/**
	 * 开关状态
	 */
	private int states;
	public int getStates(){
		return states;
	}
	
	public void setStates(int states){
		this.states=states;
	}
	
	/**
	 * 格式 1单图 2多图 3视频 4文字
	 */
	private int format;
	public int getFormat(){
		return format;
	}
	
	public void setFormat(int format){
		this.format=format;
	}
	
	/**
	 * 备注
	 */
	private String remark;
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark=remark== null ? null : remark.trim();
	}
	
	/**
	 * 截图
	 */
	private String cutImage;
	public String getCutImage(){
		return cutImage;
	}
	
	public void setCutImage(String cutImage){
		this.cutImage=cutImage== null ? null : cutImage.trim();
	}
	
	/**
	 * 排序
	 */
	private int sort;
	public int getSort(){
		return sort;
	}
	
	public void setSort(int sort){
		this.sort=sort;
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
	 * 状态
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int status){
		this.status=status;
	}
	
}