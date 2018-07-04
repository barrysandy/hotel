package com.zzk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zzk.entity.PriceRule;

public interface PriceRuleMapper {
    int deleteByPrimaryKey(String id);

    int insert(PriceRule record);

    int insertSelective(PriceRule record);

    PriceRule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PriceRule record);

    int updateByPrimaryKey(PriceRule record);
    
    List<PriceRule> selectByPage(Map map);
	
    int selectCount(Map record);
    
    List<PriceRule> selectByGoodsId(@Param("goodId")String goodId,@Param("startTime")String startTime);
    
    List<PriceRule> selectByHotelId(@Param("hotelId")String hotelId,@Param("startTime")String startTime);
    
    List<PriceRule> selectRuleByHotelList(Map map);
    
    List<PriceRule> selectRuleByGoodsList(List<String> list);
}