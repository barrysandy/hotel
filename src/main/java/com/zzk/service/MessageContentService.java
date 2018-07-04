package com.zzk.service;

import java.util.List;

import com.zzk.entity.MessageContent;

public interface MessageContentService {
	
    int deleteByPrimaryKey(String id);

    int insert(MessageContent record);

    int insertSelective(MessageContent record);

    MessageContent selectByPrimaryKey(String id);
    
    List<MessageContent> selectByShopId(String shopId);

    int updateByPrimaryKeySelective(MessageContent record);

    int updateByPrimaryKey(MessageContent record);
    
    int updateByprimarykeysStatus(String[] ids);
    
    int updateByType(String shopId,int type);

}
