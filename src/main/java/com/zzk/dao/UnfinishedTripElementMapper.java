package com.zzk.dao;

import java.util.*;
import com.zzk.entity.UnfinishedTripElement;
public interface UnfinishedTripElementMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author huashuwen
	 * @date  2018-03-10 11:02
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    int insert(UnfinishedTripElement record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    int insertSelective(UnfinishedTripElement record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return UnfinishedTripElement 实体类
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    UnfinishedTripElement selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    int updateByPrimaryKeySelective(UnfinishedTripElement record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    int updateByPrimaryKey(UnfinishedTripElement record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    List<UnfinishedTripElement> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    int selectCount(Map record);
    
    /**
	 * 根据状态查名称
	 * @return List
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    List<String> selectByStatus(int status);
    /**
	 * 根据状态查对象
	 * @return List
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    List<UnfinishedTripElement> selectObjByStatus(int status);
    
    /**
	 * 批量插入
	 * @return int 总条数
	 * @param list 
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    int batchInsert(List<UnfinishedTripElement> list);
    
    /**
	 * 批量更新
	 * @return int 总条数
	 * @param list 
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
    int batchUpdate(List<UnfinishedTripElement> list);
    
    
}