package com.zzk.dao;

import java.util.*;

import com.zzk.entity.TripElementType;
public interface TripElementTypeMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author huashuwen
	 * @date  2018-03-10 11:01
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
    int insert(TripElementType record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
    int insertSelective(TripElementType record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return TripElementType 实体类
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
    TripElementType selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
    int updateByPrimaryKeySelective(TripElementType record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
    int updateByPrimaryKey(TripElementType record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
    List<TripElementType> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
    int selectCount(Map record);
    /**
	 * 查询总类型
	 * @return int 
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
    List<TripElementType> selectAll();
}