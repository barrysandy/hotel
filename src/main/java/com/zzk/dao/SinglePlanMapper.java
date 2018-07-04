package com.zzk.dao;

import java.util.*;

import com.zzk.entity.SinglePlan;

public interface SinglePlanMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 14:38
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    int insert(SinglePlan record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    int insertSelective(SinglePlan record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return SinglePlan 实体类
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    SinglePlan selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    int updateByPrimaryKeySelective(SinglePlan record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    int updateByPrimaryKey(SinglePlan record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    List<SinglePlan> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    int selectCount(Map record);

    /**
     * 通过总行程Id 查询单天行程LIST
     *
     * @param planId
     * @return List
     * @author kun
     * @date 16:26 2018/3/8
     */
    List<SinglePlan> listSinglePlanByPlanId(String planId);

    /**
     * 通过总行程Id 查询单天行程LIST (提供给C端接口的方法)
     *
     * @param planId
     * @return List
     * @author kun
     * @date 16:26 2018/3/8
     */
    List<Map<String,Object>> getSinglePlan(String planId);

    /**
     * 通过planId删除所有的单天安排计划
     *
     * @param planId 总行程计划ID
     * @author kun
     * @date 15:16 2018/3/9
     */
    int deleteByPlanId(String planId);

    /**
     * 批量新增
     *
     * @param list 实体类的list
     * @author kun
     * @date 15:19 2018/3/9
     */
    int insertBatch(List<SinglePlan> list);
}