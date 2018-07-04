package com.zzk.dao;

import java.util.*;
import com.zzk.entity.RefundInfo;
public interface RefundInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-21 19:11
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
    int insert(RefundInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
    int insertSelective(RefundInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return RefundInfo 实体类
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
    RefundInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
    int updateByPrimaryKeySelective(RefundInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
    int updateByPrimaryKey(RefundInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
    List<RefundInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-21 19:11
	 */
    int selectCount(Map record);
    /**
     * 通过订单号查询退款单
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月22日 下午4:18:47
     */
    RefundInfo selectByOrderNo(String orderNo);
}