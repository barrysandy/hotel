package com.zzk.dao;

import com.zzk.entity.HotelServe;
import java.util.List;
import java.util.Map;

public interface HotelServeMapper {
    int deleteByPrimaryKey(String id);

    int insert(HotelServe record);

    int insertSelective(HotelServe record);

    HotelServe selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HotelServe record);

    int updateByPrimaryKey(HotelServe record);
    
    List<HotelServe> selectByPage(Map map);
	
    int selectCount(Map record);
    
    HotelServe selectByHotelId(String hotelId);
    
    List<HotelServe> selectByHotelIds(List<String> hotelIds);
}