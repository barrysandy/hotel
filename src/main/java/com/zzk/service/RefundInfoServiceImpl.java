package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zzk.dao.RefundInfoMapper;
import com.zzk.entity.RefundInfo;

/**
 * 退款信息表
* @author: wangpeng
* @date: 2018-03-21 19:11
 */
@Service("refundInfoService")
public class RefundInfoServiceImpl implements RefundInfoService {

	@Resource
	private RefundInfoMapper refundInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<RefundInfo> selectByPage(Map map) {
		return refundInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return refundInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public RefundInfo selectByPrimaryKey(String id) {
		return refundInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(RefundInfo bean) {
		return refundInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(RefundInfo bean) {
		return refundInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		RefundInfo bean = refundInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return refundInfoMapper.updateByPrimaryKey(bean);
	}

}
