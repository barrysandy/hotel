package com.zzk.dao;

import com.zzk.entity.AdvertisementInfo;
import java.util.List;
import java.util.Map;

public interface AdvertisementInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdvertisementInfo record);

    int insertSelective(AdvertisementInfo record);

    AdvertisementInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdvertisementInfo record);

    int updateByPrimaryKey(AdvertisementInfo record);
    
    List<AdvertisementInfo> selectByPage(Map map);
	
    int selectCount(Map record);
}