package com.zzk.service;

import com.zzk.entity.ProductAttributeInfo;

import java.util.List;
import java.util.Map;

/**
 * 商品扩展属性表
 * @author: Kun
 * @date: 2018-03-06 11:36
 */
public interface ProductAttributeInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: Kun
     * @date: 2018-03-06 11:36
	 */
	List<ProductAttributeInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 商品扩展属性表实体类
     * @author: Kun
     * @date: 2018-03-06 11:36
	 */
	ProductAttributeInfo selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 11:36
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:36
	 */
	 int update(ProductAttributeInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:36
	 */
	 int insert(ProductAttributeInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-03-06 11:36
	 */
	 int delete(String id);
	 
	 /**
	  * 通过商品类型ID查询平台配置的扩展属性
	  * @param categoryId 分类ID
	  * @return
	  * @author kun
	  * @date 17:34 2018/3/7
	  */
	 List<Map<String,Object>> listProductAttrByCategoryId(String categoryId);
	
}
