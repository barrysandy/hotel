package com.zzk.service;

import java.util.*;

import com.zzk.entity.SinglePlan;

/**
 * 单天行程安排表
 *
 * @author: Kun
 * @date: 2018-03-06 14:38
 */
public interface SinglePlanService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    List<SinglePlan> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 单天行程安排表实体类
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    SinglePlan selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    int update(SinglePlan bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:38
     */
    int insert(SinglePlan bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 14:38
     */
    int delete(String id);

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
     * 通过planId查询单天行程
     * @param planId
     * @return list
     * @author kun
     * @date 15:42 2018/3/21
     */
    List<Map<String,Object>> getSinglePlan(String planId);
}
