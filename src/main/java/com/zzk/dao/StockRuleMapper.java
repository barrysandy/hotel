package com.zzk.dao;

import com.zzk.entity.StockRule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StockRuleMapper {
    int deleteByPrimaryKey(String id);

    int insert(StockRule record);

    int insertSelective(StockRule record);

    StockRule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StockRule record);

    int updateByPrimaryKey(StockRule record);
    
    List<StockRule> selectByPage(Map map);
    
    List<StockRule> selectByUserId(Map map);
	
    int selectCount(Map record);
    
    List<StockRule> selectByRoomtypeId(@Param("roomtypeId")String roomtypeId,@Param("startTime")String startTime);
    
    List<StockRule> selectByGoodsId(@Param("goodsId")String goodsId,@Param("startTime")String startTime);
    
    List<StockRule> selectByHotelId(@Param("hotelId")String hotelId,@Param("startTime")String startTime);

	List<StockRule> selectByRoomtypeList(Map <String,Object> map);
	
}