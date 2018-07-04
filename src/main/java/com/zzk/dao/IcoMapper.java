package com.zzk.dao;

import com.zzk.entity.Ico;

import java.util.List;
import java.util.Map;

public interface IcoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Ico record);

    int insertSelective(Ico record);

    Ico selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Ico record);

    int updateByPrimaryKey(Ico record);
    
    List<Ico> selectByPage(Map map);
	
    int selectCount(Map record);
    
    List<Ico> selectIco();
}