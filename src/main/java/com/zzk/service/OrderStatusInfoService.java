package com.zzk.service;

import java.util.*;
import com.zzk.entity.OrderStatusInfo;
/**
 * 用户表
 * @author: wangpeng
 * @date: 2018-03-14 15:52
 */
public interface OrderStatusInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
	List<OrderStatusInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 用户表实体类
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
	OrderStatusInfo selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
	 int update(OrderStatusInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
	 int insert(OrderStatusInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-14 15:52
	 */
	 int delete(String id);
	 /**
	  * 增加状态信息
	  * @param orderNo
	  * @param orderStatus
	  * @return
	  * @author John
	  * @date： 2018年3月14日 下午4:31:39
	  */
	 int changeStatus(String orderNo,int orderStatus);
	 /**
	  * 新增状态信息待反馈和备注
	  * @param orderNo
	  * @param orderStatus
	  * @param feekback
	  * @param remarks
	  * @return
	  * @author John
	  * @date： 2018年4月25日 下午3:22:55
	  */
	 int changeStatusToCancel(String orderNo,int orderStatus,String feekback,String remarks);
	
}
