package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.QualificationInfoMapper;
import com.zzk.entity.QualificationInfo;
import com.zzk.util.MethodAnnotation;

/**
 * 用户表
* @author: wangpeng
* @date: 2018-03-12 18:22
 */
@Service("qualificationInfoService")
public class QualificationInfoServiceImpl implements QualificationInfoService {

	@Resource
	private QualificationInfoMapper qualificationInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<QualificationInfo> selectByPage(Map map) {
		return qualificationInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return qualificationInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public QualificationInfo selectByPrimaryKey(String id) {
		return qualificationInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(QualificationInfo bean) {
		return qualificationInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(QualificationInfo bean) {
		return qualificationInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		QualificationInfo bean = qualificationInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return qualificationInfoMapper.updateByPrimaryKey(bean);
	}
	@MethodAnnotation(remark="查询")
	@Override
	public QualificationInfo selectByMerchantId(String merchantId) {
		return qualificationInfoMapper.selectByMerchantId(merchantId);
	}

}
