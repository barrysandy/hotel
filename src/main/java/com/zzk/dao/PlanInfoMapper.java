package com.zzk.dao;

import java.util.*;

import com.zzk.entity.PlanInfo;
public interface PlanInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-03-06 11:09
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:09
	 */
    int insert(PlanInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:09
	 */
    int insertSelective(PlanInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return PlanInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 11:09
	 */
    PlanInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:09
	 */
    int updateByPrimaryKeySelective(PlanInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:09
	 */
    int updateByPrimaryKey(PlanInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-03-06 11:09
	 */
    List<PlanInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 11:09
	 */
    int selectCount(Map record);

	/**
	 * 通过商品ID 查询详细模式的行程安排
	 * @param productId 商品ID
	 * @return PlanInfo
	 * @author kun
	 * @date 19:12 2018/3/7
	 */
    PlanInfo getDetailModePlanInfo(String productId);

    /**
     * 通过商品ID 查询简洁模式的行程安排
     * @param productId 商品ID
     * @return PlanInfo
     * @author kun
     * @date 19:12 2018/3/7
     */
    PlanInfo getConciseModePlanInfo(String productId);
}