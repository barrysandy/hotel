package com.zzk.service;

import java.util.*;
import com.zzk.entity.CooperationInfo;
/**
 * 商户合作信息表
 * @author: wangpeng
 * @date: 2018-03-12 18:19
 */
public interface CooperationInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
	List<CooperationInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 用户表实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
	CooperationInfo selectByPrimaryKey(String id);
	
	/**
 	 * 通过外键ID查询
 	 * @param id 主键ID
	 * @return 用户表实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
	CooperationInfo selectByBusinessId(String businessId);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
	 int update(CooperationInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:19
	 */
	 int insert(CooperationInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-12 18:19
	 */
	 int delete(String id);
	
}
