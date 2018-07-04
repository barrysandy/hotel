package com.zzk.dao;

import com.zzk.entity.HotelGoods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HotelGoodsMapper {
    int deleteByPrimaryKey(String id);

    int insert(HotelGoods record);

    int insertSelective(HotelGoods record);

    HotelGoods selectByPrimaryKey(String id);
    
    HotelGoods selectInfoByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HotelGoods record);

    int updateByPrimaryKey(HotelGoods record);
    
    List<HotelGoods> selectByPage(Map map);
	
    int selectCount(Map record);
    
    List<String> selectGoodIds(@Param("hotelId")String hotelId);
    
    Map<String,Object> selectStockandPricebyId(@Param("id")String id,@Param("date")String date,@Param("dayOfWeek")String dayOfWeek);
    
    List<HotelGoods> selectHotelGoods(String hotelId);
    
    int countHotelGoods(String hotelId);
    
    List<HotelGoods> selectHotelGoodsByHotelIds(List<String> hotelIds);
    
    List<HotelGoods> selectByRoomtypeId(String roomtypeId);
}