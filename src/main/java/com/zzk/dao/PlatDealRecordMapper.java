package com.zzk.dao;

import java.util.*;
import com.zzk.entity.PlatDealRecord;
public interface PlatDealRecordMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-16 12:05
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
    int insert(PlatDealRecord record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
    int insertSelective(PlatDealRecord record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return PlatDealRecord 实体类
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
    PlatDealRecord selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
    int updateByPrimaryKeySelective(PlatDealRecord record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
    int updateByPrimaryKey(PlatDealRecord record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
    List<PlatDealRecord> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
    int selectCount(Map record);
}