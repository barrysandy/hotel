package com.zzk.dao;

import com.zzk.entity.MessageConfig;

public interface MessageConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(MessageConfig record);

    int insertSelective(MessageConfig record);

    MessageConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MessageConfig record);

    int updateByPrimaryKey(MessageConfig record);
    
    MessageConfig selectByShopId(String shopId);
}