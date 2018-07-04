package com.zzk.dao;

import com.zzk.entity.OperateLog;
import java.util.List;
import java.util.Map;

public interface OperateLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    OperateLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
    
    List<OperateLog> selectByPage(Map map);
	
    int selectCount(Map record);
}