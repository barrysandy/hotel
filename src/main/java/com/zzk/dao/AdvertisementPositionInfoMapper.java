package com.zzk.dao;

import com.zzk.entity.AdvertisementPositionInfo;
import java.util.List;
import java.util.Map;

public interface AdvertisementPositionInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdvertisementPositionInfo record);

    int insertSelective(AdvertisementPositionInfo record);

    AdvertisementPositionInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdvertisementPositionInfo record);

    int updateByPrimaryKey(AdvertisementPositionInfo record);
    
    List<AdvertisementPositionInfo> selectByPage(Map map);
	
    int selectCount(Map record);
}