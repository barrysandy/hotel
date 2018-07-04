package com.zzk.dao;

import java.util.*;
import com.zzk.entity.CooperationInfo;
public interface CooperationInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-12 18:19
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
    int insert(CooperationInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
    int insertSelective(CooperationInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return CooperationInfo 实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
    CooperationInfo selectByBusinessId(String businnessId);
    
    /**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return CooperationInfo 实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
    CooperationInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
    int updateByPrimaryKeySelective(CooperationInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
    int updateByPrimaryKey(CooperationInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
    List<CooperationInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
    int selectCount(Map record);
}