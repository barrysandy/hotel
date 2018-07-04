package com.zzk.dao;

import com.zzk.entity.RoomType;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RoomTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(RoomType record);

    int insertSelective(RoomType record);

    RoomType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoomType record);

    int updateByPrimaryKey(RoomType record);
    
    List<RoomType> selectByPage(Map map);
	
    int selectCount(Map record);
    
    List<RoomType> selectByHotelId(String hotelId);
    
    List<RoomType> getRoomtypeList(String hotelId);
    
    RoomType selectByGoodsId(String goodsId);
    
    List<RoomType> selectByHotelsId(@Param("idList")List<String> idList);
}