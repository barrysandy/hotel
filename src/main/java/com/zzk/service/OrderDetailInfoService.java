package com.zzk.service;

import com.zzk.entity.OrderDetailInfo;

import java.util.*;

/**
 * 订单详情表
 * @author: Kun
 * @date: 2018-03-06 11:01
 */
public interface OrderDetailInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: Kun
     * @date: 2018-03-06 11:01
	 */
	List<OrderDetailInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 订单详情表实体类
     * @author: Kun
     * @date: 2018-03-06 11:01
	 */
	OrderDetailInfo selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 11:01
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:01
	 */
	 int update(OrderDetailInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:01
	 */
	 int insert(OrderDetailInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-03-06 11:01
	 */
	 int delete(String id);
	
}
