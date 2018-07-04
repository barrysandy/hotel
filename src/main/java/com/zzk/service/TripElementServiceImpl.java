package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zzk.dao.TripElementMapper;
import com.zzk.dao.UnfinishedTripElementMapper;
import com.zzk.entity.TripElement;

/**
 * 旅游要素
* @author: huashuwen
* @date: 2018-03-10 10:57
 */
@Service("tripElementService")
public class TripElementServiceImpl implements TripElementService {

	@Resource
	private TripElementMapper tripElementMapper;
	@Resource
	private UnfinishedTripElementMapper unfinishedTripElementMapper;
	/**
	 * 分页查询
	 */
	@Override
	public List<TripElement> selectByPage(Map map) {
		return tripElementMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return tripElementMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public TripElement selectByPrimaryKey(String id) {
		return tripElementMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(TripElement bean) {
		return tripElementMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(TripElement bean) {
		return tripElementMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		TripElement bean = tripElementMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return tripElementMapper.updateByPrimaryKey(bean);
	}
	
	
	/**
	 * 查询旅游要素信息
	 */
	@Override
	public TripElement selectByName(String name) {
		TripElement bean = tripElementMapper.selectByName(name);
		return bean;
	}
	
	/**
	 * 查询旅游要素信息集合
	 */
	@Override
	public List<Map<String,Object>> selectByList(List<String> list) {
		List<Map<String,Object>> beanList= tripElementMapper.selectByList(list);
		return beanList;
	}

	@Override
	public Map<String, Object> selectOneByName(String name) {
		return tripElementMapper.selectOneByName(name);
	}
}
