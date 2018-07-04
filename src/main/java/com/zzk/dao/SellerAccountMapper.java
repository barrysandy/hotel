package com.zzk.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.zzk.entity.SellerAccount;
public interface SellerAccountMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-10 11:10
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
    int insert(SellerAccount record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
    int insertSelective(SellerAccount record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return SellerAccount 实体类
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
    SellerAccount selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
    int updateByPrimaryKeySelective(SellerAccount record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
    int updateByPrimaryKey(SellerAccount record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
    List<SellerAccount> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
    int selectCount(Map record);
  
    /**
    * 通过sellerID和type查询 
    * @param sellerId
    * @param type
    * @return
    * @author John
    * @date： 2018年3月10日 上午11:43:26
    */
    SellerAccount selectBySellerId(@Param(value="sellerId") String sellerId,@Param(value="type")Integer type);
    
}