package com.zzk.dao;

import java.util.List;
import java.util.Map;

import com.zzk.entity.Evaluate;
import com.zzk.entity.EvaluateCustom;

public interface EvaluateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Evaluate record);

    int insertSelective(Evaluate record);
    
    EvaluateCustom selectCountByshopId(String shopId);

    Evaluate selectByPrimaryKey(String id);
    
    List<Map<String, Object>> selectByPage(Map map);
    
    List<EvaluateCustom> selectByPageReply(Map map);
    
    List<EvaluateCustom> selectByParentId(String parentId);

    int updateByPrimaryKeySelective(Evaluate record);

    int updateByPrimaryKeyWithBLOBs(Evaluate record);

    int updateByPrimaryKey(Evaluate record);
    
    int updateByParentId(String parentId);
    
    List<EvaluateCustom> selectScore(Map map);
    
}