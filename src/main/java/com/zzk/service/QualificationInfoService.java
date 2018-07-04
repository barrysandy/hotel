package com.zzk.service;

import java.util.*;
import com.zzk.entity.QualificationInfo;
/**
 * 用户表
 * @author: wangpeng
 * @date: 2018-03-12 18:22
 */
public interface QualificationInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
	List<QualificationInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 用户表实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
	QualificationInfo selectByPrimaryKey(String id);
	
	/**
 	 * 通过外键merchantId查询
 	 * @param id 主键ID
	 * @return 用户表实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
	QualificationInfo selectByMerchantId(String merchantId);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
	 int update(QualificationInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
	 int insert(QualificationInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-12 18:22
	 */
	 int delete(String id);
	
}
