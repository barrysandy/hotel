package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.dao.OrderDetailInfoMapper;
import com.zzk.entity.OrderDetailInfo;
import org.springframework.stereotype.Service;


/**
 * 订单详情表
* @author: Kun
* @date: 2018-03-06 11:01
 */
@Service("orderDetailInfoService")
public class OrderDetailInfoServiceImpl implements OrderDetailInfoService {

	@Resource
	private OrderDetailInfoMapper orderDetailInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<OrderDetailInfo> selectByPage(Map map) {
		return orderDetailInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return orderDetailInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public OrderDetailInfo selectByPrimaryKey(String id) {
		return orderDetailInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(OrderDetailInfo bean) {
		return orderDetailInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(OrderDetailInfo bean) {
		return orderDetailInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		OrderDetailInfo bean = orderDetailInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return orderDetailInfoMapper.updateByPrimaryKey(bean);
	}

}
