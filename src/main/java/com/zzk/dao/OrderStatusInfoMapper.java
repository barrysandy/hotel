package com.zzk.dao;

import java.util.*;

import com.zzk.entity.OrderBaseInfoCustom;
import com.zzk.entity.OrderStatusInfo;
public interface OrderStatusInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-14 15:52
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
    int insert(OrderStatusInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
    int insertSelective(OrderStatusInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return OrderStatusInfo 实体类
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
    OrderStatusInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
    int updateByPrimaryKeySelective(OrderStatusInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
    int updateByPrimaryKey(OrderStatusInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
    List<OrderStatusInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-14 15:52
	 */
    int selectCount(Map record);
    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月20日 下午12:00:50
     */
    List<OrderStatusInfo> selectByOrderNo(String orderNo);

}