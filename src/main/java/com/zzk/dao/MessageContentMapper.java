package com.zzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zzk.entity.MessageContent;

public interface MessageContentMapper {
    int deleteByPrimaryKey(String id);

    int insert(MessageContent record);

    int insertSelective(MessageContent record);

    MessageContent selectByPrimaryKey(String id);
    
    List<MessageContent> selectByShopId(String shopId);

    int updateByPrimaryKeySelective(MessageContent record);

    int updateByPrimaryKey(MessageContent record);
    
    int updateByIds(String[] id);
    
    int updateByType(@Param("shopId")String shopId,@Param("type")int type);
}