package com.zzk.service;

import com.zzk.entity.AuditInfo;

import java.util.*;

/**
 * 审核信息表
 * @author: Kun
 * @date: 2018-03-06 10:14
 */
public interface AuditInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
	List<AuditInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 审核信息表实体类
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
	AuditInfo selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
	 int update(AuditInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 10:14
	 */
	 int insert(AuditInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-03-06 10:14
	 */
	 int delete(String id);

	 /**
	  * 商品发布-基本信息-审核表数据插入
	  * @param productId 商品ID
	  * @author kun
	  * @date 11:21 2018/3/8
	  */
	 void saveProductPublishAuditInfo(String productId);

    /**
     * 商品发布-基本信息-审核表数据更新
     * @param productId 商品ID
     * @author kun
     * @date 11:21 2018/3/8
     */
	 void updateProductPublishAuditInfo(String productId);
}
