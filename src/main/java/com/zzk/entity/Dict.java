package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 字典信息表的实体类的实体类<br/>
* @author: wangpeng
* @date: 2018-04-10 11:46
 */
public class Dict{
	
	/**
	 * id编号
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 数据值
	 */
	private String value;
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value=value== null ? null : value.trim();
	}
	
	
	/**
	 * 标签名
	 */
	private String label;
	public String getLabel(){
		return label;
	}
	public void setLabel(String label){
		this.label=label== null ? null : label.trim();
	}
	
	
	/**
	 * 类型
	 */
	private String type;
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type=type== null ? null : type.trim();
	}
	
	
	/**
	 * 描述
	 */
	private String description;
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description=description== null ? null : description.trim();
	}
	
	
	/**
	 * 排序
	 */
	private BigDecimal sort;
	public BigDecimal getSort(){
		return sort;
	}
	public void setSort(BigDecimal sort){
		this.sort=sort;
	}
	
	
	/**
	 * 父级编号
	 */
	private String parentId;
	public String getParentId(){
		return parentId;
	}
	public void setParentId(String parentId){
		this.parentId=parentId== null ? null : parentId.trim();
	}
	
	
	/**
	 * 创建者
	 */
	private String createBy;
	public String getCreateBy(){
		return createBy;
	}
	public void setCreateBy(String createBy){
		this.createBy=createBy== null ? null : createBy.trim();
	}
	
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}
	
	
	/**
	 * 更新者
	 */
	private String updateBy;
	public String getUpdateBy(){
		return updateBy;
	}
	public void setUpdateBy(String updateBy){
		this.updateBy=updateBy== null ? null : updateBy.trim();
	}
	
	
	/**
	 * 更新时间
	 */
	private Date updateDate;
	public Date getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}
	
	
	/**
	 * 备注信息
	 */
	private String remarks;
	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks=remarks== null ? null : remarks.trim();
	}
	
	
	/**
	 * 删除标记
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
}