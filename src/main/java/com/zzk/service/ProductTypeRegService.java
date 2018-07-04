package com.zzk.service;

import java.util.*;
import com.zzk.entity.ProductTypeReg;
/**
 * 商品分类信息表
 * @author: Kun
 * @date: 2018-03-07 15:04
 */
public interface ProductTypeRegService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: Kun
     * @date: 2018-03-07 15:04
	 */
	List<ProductTypeReg> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 商品分类信息表实体类
     * @author: Kun
     * @date: 2018-03-07 15:04
	 */
	ProductTypeReg selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: Kun
     * @date: 2018-03-07 15:04
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-07 15:04
	 */
	 int update(ProductTypeReg bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-07 15:04
	 */
	 int insert(ProductTypeReg bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-03-07 15:04
	 */
	 int delete(String id);

    List<Map<String,Object>> listProductTypeByParentId(String parentId);
	
}
