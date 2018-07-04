package com.zzk.service;

import com.zzk.dao.ProductAttributeInfoMapper;
import com.zzk.entity.ProductAttributeInfo;
import com.zzk.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品扩展属性表
* @author: Kun
* @date: 2018-03-06 11:36
 */
@Service("productAttributeInfoService")
public class ProductAttributeInfoServiceImpl implements ProductAttributeInfoService {

	@Resource
	private ProductAttributeInfoMapper productAttributeInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<ProductAttributeInfo> selectByPage(Map map) {
		return productAttributeInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return productAttributeInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public ProductAttributeInfo selectByPrimaryKey(String id) {
		return productAttributeInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProductAttributeInfo bean) {
		return productAttributeInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(ProductAttributeInfo bean) {
		return productAttributeInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		ProductAttributeInfo bean = productAttributeInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return productAttributeInfoMapper.updateByPrimaryKey(bean);
	}

	@Override
	public List<Map<String, Object>> listProductAttrByCategoryId(String categoryId) {
        List<Map<String, Object>> resultList = productAttributeInfoMapper.listProductAttrByCategoryId(categoryId);
        if (CollectionUtils.isNotEmpty(resultList)){
            for (Map<String, Object> stringObjectMap : resultList) {
                Date createTime = (Date) stringObjectMap.get("CREATE_TIME");
                Date updateTime = (Date) stringObjectMap.get("UPDATE_TIME");
                if (createTime != null){
                    stringObjectMap.put("CREATE_TIME", DateUtils.changeDateToStr(createTime,null));
                }
                if (updateTime != null){
                    stringObjectMap.put("UPDATE_TIME", DateUtils.changeDateToStr(updateTime,null));
                }
            }
        }
		return resultList;
	}

}
