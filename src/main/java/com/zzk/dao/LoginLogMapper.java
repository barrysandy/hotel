package com.zzk.dao;

import com.zzk.entity.LoginLog;
import java.util.List;
import java.util.Map;

public interface LoginLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoginLog record);

    int insertSelective(LoginLog record);

    LoginLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoginLog record);

    int updateByPrimaryKey(LoginLog record);
    
    List<LoginLog> selectByPage(Map map);
	
    int selectCount(Map record);
}