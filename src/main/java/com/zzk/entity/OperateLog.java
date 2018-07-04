package com.zzk.entity;

import java.io.Serializable;
import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 操作日志的实体类<br/>
* @author：huashuwen
* @date：2018-03-08 09:49
 */
public class OperateLog implements Serializable{
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
	 * 
	 */
	private String user;
	public String getUser(){
		return user;
	}
	
	public void setUser(String user){
		this.user=user== null ? null : user.trim();
	}
	
	/**
	 * 
	 */
	private Date date;
	public Date getDate(){
		return date;
	}
	
	public void setDate(Date date){
		this.date=date;
	}
	
	/**
	 * 
	 */
	private String dateStr;
	public String getDateStr(){
		return dateStr;
	}
	
	public void setDateStr(String dateStr){
		this.dateStr=dateStr;
	}
	
	/**
	 * 
	 */
	private String ip;
	public String getIp(){
		return ip;
	}
	
	public void setIp(String ip){
		this.ip=ip== null ? null : ip.trim();
	}
	
	/**
	 * 
	 */
	private String content;
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content=content== null ? null : content.trim();
	}
	
	/**
	 * 
	 */
	private String shopId;
	public String getShopId(){
		return shopId;
	}
	
	public void setShopId(String shopId){
		this.shopId=shopId== null ? null : shopId.trim();
	}
	
	/**
	 * 
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int status){
		this.status=status;
	}
	
	
	/**
	 * 日志类型  1新增 2修改 3删除 4其他
	 */
	private int type;
	public int getType(){
		return type;
	}
	
	public void setType(int type){
		this.type=type;
	}
	
	/**
	 * 被操作对象ID
	 */
	private String objId;
	public String getObjId(){
		return objId;
	}
	
	public void setObjId(String objId){
		this.objId=objId== null ? null : objId.trim();
	}
}