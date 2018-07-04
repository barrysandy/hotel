package com.zzk.dao;

import com.zzk.entity.PlatPayGatewayMessage;
import java.util.List;
import java.util.Map;

public interface PlatPayGatewayMessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlatPayGatewayMessage record);

    int insertSelective(PlatPayGatewayMessage record);

    PlatPayGatewayMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PlatPayGatewayMessage record);

    int updateByPrimaryKey(PlatPayGatewayMessage record);
    
    List<PlatPayGatewayMessage> selectByPage(Map map);
	
    int selectCount(Map record);

    List<PlatPayGatewayMessage> selectByOrderId(String orderId);
}