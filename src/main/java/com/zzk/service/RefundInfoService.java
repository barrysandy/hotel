package com.zzk.service;

import java.util.*;
import com.zzk.entity.RefundInfo;
/**
 * 退款信息表
 * @author: wangpeng
 * @date: 2018-03-21 19:11
 */
public interface RefundInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
	List<RefundInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 退款信息表实体类
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
	RefundInfo selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
	 int update(RefundInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
	 int insert(RefundInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-21 19:11
	 */
	 int delete(String id);
	
}
