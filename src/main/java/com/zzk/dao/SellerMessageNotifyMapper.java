package com.zzk.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.zzk.entity.SellerMessageNotify;
public interface SellerMessageNotifyMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-05-09 14:27
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-05-09 14:27
	 */
    int insert(SellerMessageNotify record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-05-09 14:27
	 */
    int insertSelective(SellerMessageNotify record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return SellerMessageNotify 实体类
     * @author: wangpeng
     * @date: 2018-05-09 14:27
	 */
    SellerMessageNotify selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-05-09 14:27
	 */
    int updateByPrimaryKeySelective(SellerMessageNotify record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-05-09 14:27
	 */
    int updateByPrimaryKey(SellerMessageNotify record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-05-09 14:27
	 */
    List<SellerMessageNotify> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-05-09 14:27
	 */
    int selectCount(Map record);
    /**
     * 查询商户消息情况
     * @param sellerId
     * @return
     * @author John
     * @date： 2018年5月9日 下午2:45:09
     */
	List<SellerMessageNotify> selectBySellerId(String sellerId);
	/**
	 * 
	 * @param sellerId
	 * @param type
	 * @return
	 * @author John
	 * @date： 2018年5月9日 下午3:15:53
	 */
	SellerMessageNotify selectMessageByTypeAndSellerId(@Param("sellerId")String sellerId, @Param("type")int type);
}