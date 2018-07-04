package com.zzk.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zzk.entity.Access;

public interface AccessService {
	
	Access selectByPrimaryKey(String id);
	
	int insertSelective(Access record);
	
	List selectTotalNumber(Map map) throws Exception; 	

}
