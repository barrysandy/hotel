package com.zzk.service;

import java.util.*;
import com.zzk.entity.SellerAccount;
/**
 * 商家账户表
 * @author: wangpeng
 * @date: 2018-03-10 11:10
 */
public interface SellerAccountService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
	List<SellerAccount> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 商家账户表实体类
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
	SellerAccount selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
	 int update(SellerAccount bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-10 11:10
	 */
	 int insert(SellerAccount bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-10 11:10
	 */
	 int delete(String id);
	 
	 /**
	  * 通过sellerID和type查询
	  * @param sellerId
	  * @param type
	  * @return
	  * @author John
	  * @date： 2018年3月10日 上午11:39:01
	  */
	 SellerAccount selectBySellerId(String sellerId,Integer type);
	 /**
	  * 保存或更新账户
	  * @param account
	  * @return
	  * @author John
	  * @date： 2018年3月10日 上午11:48:13
	  */
	 String insertOrUpdate(SellerAccount account);
	
}
