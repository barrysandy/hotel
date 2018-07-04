package com.zzk.dao;

import com.zzk.entity.HotelPolicy;
import java.util.List;
import java.util.Map;

public interface HotelPolicyMapper {
    int deleteByPrimaryKey(String id);

    int insert(HotelPolicy record);

    int insertSelective(HotelPolicy record);

    HotelPolicy selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HotelPolicy record);

    int updateByPrimaryKey(HotelPolicy record);
    
    List<HotelPolicy> selectByPage(Map map);
	
    int selectCount(Map record);
    
    HotelPolicy selectByHotelId(String hotelId);
}