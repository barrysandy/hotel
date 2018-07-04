package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zzk.dao.PlatDealRecordMapper;
import com.zzk.entity.PlatDealRecord;

/**
 * 交易流水记录
* @author: wangpeng
* @date: 2018-03-16 12:05
 */
@Service("platDealRecordService")
public class PlatDealRecordServiceImpl implements PlatDealRecordService {

	@Resource
	private PlatDealRecordMapper platDealRecordMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<PlatDealRecord> selectByPage(Map map) {
		return platDealRecordMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return platDealRecordMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public PlatDealRecord selectByPrimaryKey(String id) {
		return platDealRecordMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(PlatDealRecord bean) {
		return platDealRecordMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(PlatDealRecord bean) {
		return platDealRecordMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		PlatDealRecord bean = platDealRecordMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return platDealRecordMapper.updateByPrimaryKey(bean);
	}

}
