package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.zzk.dao.SinglePlanMapper;
import com.zzk.entity.SinglePlan;

/**
 * 单天行程安排表
 *
 * @author: Kun
 * @date: 2018-03-06 14:38
 */
@Service("singlePlanService")
public class SinglePlanServiceImpl implements SinglePlanService {

    @Resource
    private SinglePlanMapper singlePlanMapper;

    /**
     * 分页查询
     */
    @Override
    public List<SinglePlan> selectByPage(Map map) {
        return singlePlanMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return singlePlanMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public SinglePlan selectByPrimaryKey(String id) {
        return singlePlanMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(SinglePlan bean) {
        return singlePlanMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(SinglePlan bean) {
        return singlePlanMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        SinglePlan bean = singlePlanMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return singlePlanMapper.updateByPrimaryKey(bean);
    }

    @Override
    public List<SinglePlan> listSinglePlanByPlanId(String planId) {
        return singlePlanMapper.listSinglePlanByPlanId(planId);
    }

    @Override
    public List<Map<String, Object>> getSinglePlan(String planId) {
        List<Map<String, Object>> resultMap = singlePlanMapper.getSinglePlan(planId);
        return resultMap;
    }

}
