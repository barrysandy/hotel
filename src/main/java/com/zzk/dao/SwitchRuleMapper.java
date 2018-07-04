package com.zzk.dao;

import com.zzk.entity.SwitchRule;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SwitchRuleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SwitchRule record);

    int insertSelective(SwitchRule record);

    SwitchRule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SwitchRule record);

    int updateByPrimaryKey(SwitchRule record);
    
    List<SwitchRule> selectByPage(Map map);
	
    int selectCount(Map record);
    
    List<SwitchRule> selectByHotelId(@Param("hotelId")String hotelId,@Param("startTime")String startTime);
    
    List<SwitchRule> selectByGoodsId(@Param("goodsId")String hotelId,@Param("startTime")String startTime);
    
    List<SwitchRule> selectByHotelsId(@Param("idList")List<String> idList,@Param("startTime")String startTime,@Param("endTime")String endTime);
}