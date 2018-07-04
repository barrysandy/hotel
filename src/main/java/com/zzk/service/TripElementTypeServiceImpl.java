package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.TripElementTypeMapper;
import com.zzk.entity.TripElementType;

/**
 * 旅游要素类型
* @author: huashuwen
* @date: 2018-03-10 11:01
 */
@Service("tripElementTypeService")
public class TripElementTypeServiceImpl implements TripElementTypeService {

	@Resource
	private TripElementTypeMapper tripElementTypeMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<TripElementType> selectByPage(Map map) {
		return tripElementTypeMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return tripElementTypeMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public TripElementType selectByPrimaryKey(String id) {
		return tripElementTypeMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(TripElementType bean) {
		return tripElementTypeMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(TripElementType bean) {
		return tripElementTypeMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		TripElementType bean = tripElementTypeMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return tripElementTypeMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 查询类型
	 */
	@Override
	public List<TripElementType> selectType() {
		List<TripElementType> typeList= tripElementTypeMapper.selectAll();
		return typeList;
	}

}
