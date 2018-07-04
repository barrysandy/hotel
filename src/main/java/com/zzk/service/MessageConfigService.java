package com.zzk.service;


import com.zzk.entity.MessageConfig;
import com.zzk.entity.MessageConfigCustom;

public interface MessageConfigService {
	/**
	 * 根据MG ID 删除
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(String id);
	/**
	 * 新增消息设置
	 * @param id
	 * @return
	 */
    int insert(MessageConfig record);
	/**
	 * 根据条件查询固定消息设置
	 * @param id
	 * @return
	 */
    int insertSelective(MessageConfig record);
	/**
	 * 根据ID查询固定消息的设备信息
	 * @param id
	 * @return
	 */   
    MessageConfig selectByPrimaryKey(String id);
    /**
     * 根据shopId查询
     * @param userId
     * @return
     */
    MessageConfigCustom selectByShopId(String shopId);
	/** 
	 * 更新新消息设置 提交更新非空的字段
	 * @param id
	 * @return
	 */
    int updateByPrimaryKeySelective(MessageConfig record);
     /**
      * 更新MessageConfig中所有属性到table中取，不会进行非空检查
      * @param record
      * @return
      */
    int updateByPrimaryKey(MessageConfig record);
}