package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zzk.dao.ProductStockInfoMapper;
import com.zzk.entity.ProductStockInfo;

/**
 * 库存表
* @author: Kun
* @date: 2018-03-06 15:34
 */
@Service("productStockInfoService")
public class ProductStockInfoServiceImpl implements ProductStockInfoService {

	@Resource
	private ProductStockInfoMapper productStockInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<ProductStockInfo> selectByPage(Map map) {
		return productStockInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return productStockInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public ProductStockInfo selectByPrimaryKey(String id) {
		return productStockInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProductStockInfo bean) {
		return productStockInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(ProductStockInfo bean) {
		return productStockInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		ProductStockInfo bean = productStockInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return productStockInfoMapper.updateByPrimaryKey(bean);
	}

}
