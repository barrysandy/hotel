package com.zzk.dao;

import com.zzk.entity.ProductTypeInfo;

import java.util.*;

public interface ProductTypeInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-03-06 14:35
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:35
	 */
    int insert(ProductTypeInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:35
	 */
    int insertSelective(ProductTypeInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return ProductTypeInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 14:35
	 */
    ProductTypeInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:35
	 */
    int updateByPrimaryKeySelective(ProductTypeInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:35
	 */
    int updateByPrimaryKey(ProductTypeInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-03-06 14:35
	 */
    List<ProductTypeInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 14:35
	 */
    int selectCount(Map record);

    /**
     * @param productId 商品ID
     * @return ProductTypeInfo bean
     * @author kun
     * @date 15:23 2018/3/7
     */
    ProductTypeInfo getByProductId(String productId);

}