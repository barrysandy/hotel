package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.CooperationInfoMapper;
import com.zzk.entity.CooperationInfo;

/**
 * 商户合作信息表
* @author: wangpeng
* @date: 2018-03-12 18:19
 */
@Service("cooperationInfoService")
public class CooperationInfoServiceImpl implements CooperationInfoService {

	@Resource
	private CooperationInfoMapper cooperationInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<CooperationInfo> selectByPage(Map map) {
		return cooperationInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return cooperationInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public CooperationInfo selectByPrimaryKey(String id) {
		return cooperationInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(CooperationInfo bean) {
		return cooperationInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(CooperationInfo bean) {
		return cooperationInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		CooperationInfo bean = cooperationInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return cooperationInfoMapper.updateByPrimaryKey(bean);
	}

	@Override
	public CooperationInfo selectByBusinessId(String businnessId) {
		
		return cooperationInfoMapper.selectByBusinessId(businnessId);
	}

}
