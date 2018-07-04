package com.zzk.service;

import java.util.List;

import com.zzk.entity.HotelAccount;

public interface HotelAccountService {
	
    int deleteByPrimaryKey(String id);

    int insert(HotelAccount record);

    int insertSelective(HotelAccount record);

    HotelAccount selectByPrimaryKey(String id);
    
    HotelAccount selectByHotelPayMode(String shopId,int payMode);
    
    List<HotelAccount> selectByShopId(String shopId);

    int updateByPrimaryKeySelective(HotelAccount record);

    int updateByPrimaryKey(HotelAccount record);

}
