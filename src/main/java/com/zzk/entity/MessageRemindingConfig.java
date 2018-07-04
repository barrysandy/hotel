package com.zzk.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/>
 * 用途: 消息提醒配置表的实体类<br/>
 *
 * @author: Kun
 * @date: 2018-04-03 14:45
 */
public class MessageRemindingConfig {

    /**
     * ID
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    /**
     * 消息提醒名称
     */
    private String messageName;

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName == null ? null : messageName.trim();
    }


    /**
     * 多选框的值,用逗号隔开
     */
    private String messageContent;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }


    /**
     * 创建时间
     */
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    /**
     * 更新时间
     */
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * 数据状态
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}