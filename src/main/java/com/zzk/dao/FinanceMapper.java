package com.zzk.dao;

import java.util.List;
import java.util.Map;

import com.zzk.entity.Finance;
import com.zzk.entity.FinanceInfo;

public interface FinanceMapper {
    int deleteByPrimaryKey(String id);

    int insert(FinanceInfo record);

    int insertSelective(FinanceInfo record);

    FinanceInfo selectByPrimaryKey(String id);
    
    List<FinanceInfo> selectByPage(Map map);
    
    int updateByPrimaryKeySelective(FinanceInfo record);

    int updateByPrimaryKey(FinanceInfo record);
    
    int updateByShopList(List list);
    
    Map selectAbleCashTotal(Map map);
    
    FinanceInfo selectNotGenerateFin(String shopId);
}