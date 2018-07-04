package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.util.StringUtils;
import org.springframework.stereotype.Service;
import com.zzk.dao.ProductTypeRegMapper;
import com.zzk.entity.ProductTypeReg;

/**
 * 商品分类信息表
* @author: Kun
* @date: 2018-03-07 15:04
 */
@Service("productTypeRegService")
public class ProductTypeRegServiceImpl implements ProductTypeRegService {

	@Resource
	private ProductTypeRegMapper productTypeRegMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<ProductTypeReg> selectByPage(Map map) {
		return productTypeRegMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return productTypeRegMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public ProductTypeReg selectByPrimaryKey(String id) {
		return productTypeRegMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProductTypeReg bean) {
		return productTypeRegMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(ProductTypeReg bean) {
		return productTypeRegMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		ProductTypeReg bean = productTypeRegMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return productTypeRegMapper.updateByPrimaryKey(bean);
	}

	@Override
	public List<Map<String, Object>> listProductTypeByParentId(String parentId) {
		if (StringUtils.isBlank(parentId)){

			return null;
		}else{
			return productTypeRegMapper.listProductTypeByParentId(parentId);
		}
	}

}
