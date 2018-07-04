package com.zzk.dao;

import com.zzk.entity.AuditInfo;

import java.util.*;
public interface AuditInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-03-06 10:14
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
    int insert(AuditInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
    int insertSelective(AuditInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return AuditInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
    AuditInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
    int updateByPrimaryKeySelective(AuditInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
    int updateByPrimaryKey(AuditInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
    List<AuditInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
    int selectCount(Map record);

    /**
     * 通过审核的载体ID查询审核信息
     * @param objectId 载体ID
     * @return AuditInfo bean
     * @author kun
     * @date 15:42 2018/3/8
     */
    AuditInfo getByObjectId(String objectId);
}