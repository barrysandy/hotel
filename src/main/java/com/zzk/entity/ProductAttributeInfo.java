package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/>
 * 用途: 商品扩展属性配置表的实体类<br/>
 * @author: Kun
 * @date: 2018-03-07 18:58
 */
public class ProductAttributeInfo{

    /**
     * 主键ID
     */
    private String id;
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id== null ? null : id.trim();
    }


    /**
     * 商品类型ID
     */
    private String categoryId;
    public String getCategoryId(){
        return categoryId;
    }
    public void setCategoryId(String categoryId){
        this.categoryId=categoryId== null ? null : categoryId.trim();
    }


    /**
     * 1=商品扩展 2=价格扩展
     */
    private int attrType;
    public int getAttrType(){
        return attrType;
    }
    public void setAttrType(int attrType){
        this.attrType=attrType;
    }


    /**
     *
     */
    private String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name== null ? null : name.trim();
    }


    /**
     * 1=文本框2=单选3=复选4=html编辑器
     */
    private int type;
    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type=type;
    }


    /**
     * 扩展属性内容 （单选，复选才会有这个）
     */
    private String content;
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content== null ? null : content.trim();
    }


    /**
     * 预留 ，以后可能会通过code进行查询
     */
    private String code;
    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code=code== null ? null : code.trim();
    }


    /**
     * 属性排序
     */
    private int sort;
    public int getSort(){
        return sort;
    }
    public void setSort(int sort){
        this.sort=sort;
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
     *
     */
    private Date createTime;
    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }


    /**
     *
     */
    private Date updateTime;
    public Date getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }

}