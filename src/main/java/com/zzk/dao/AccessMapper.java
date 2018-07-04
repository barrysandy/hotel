package com.zzk.dao;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zzk.entity.Access;

public interface AccessMapper {
	
    Access selectByPrimaryKey(String id);
	
	int insertSelective(Access record);
	
	List<Map<String,Object>> selectTotalNumber(Map map);	

}
