package com.zzk.dao;

import com.zzk.entity.DatePrice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



public interface DatePriceMapper {
    int deleteByPrimaryKey(String id);

    int insert(DatePrice record);

    int insertSelective(DatePrice record);

    DatePrice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DatePrice record);

    int updateByPrimaryKey(DatePrice record);
    
    List<DatePrice> selectByPage(Map map);
	
    int selectCount(Map record);
    
    int batchInsert(@Param("hotelId")String hotelId,@Param("list") List list);
    
    int deleteByHotelId(String hotelId);
}