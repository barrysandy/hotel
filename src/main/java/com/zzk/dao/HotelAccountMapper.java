package com.zzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zzk.entity.HotelAccount;

public interface HotelAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(HotelAccount record);

    int insertSelective(HotelAccount record);

    HotelAccount selectByPrimaryKey(String id);
    
    HotelAccount selectByHotelPayMode(@Param("shopId") String shopId,@Param("payMode") int payMode);
    
    List<HotelAccount> selectByShopId(String shopId);

    int updateByPrimaryKeySelective(HotelAccount record);

    int updateByPrimaryKey(HotelAccount record);
}