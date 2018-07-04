package com.zzk.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.zzk.entity.FinanceInfo;
public interface FinanceInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-10 15:34
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
    int insert(FinanceInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
    int insertSelective(FinanceInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return FinanceInfo 实体类
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
    FinanceInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
    int updateByPrimaryKeySelective(FinanceInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
    int updateByPrimaryKey(FinanceInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
    List<FinanceInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
    int selectCount(Map record);
    /**
     * 根据条件分页查询
     * @param map
     * @return
     * @author John
     * @date： 2018年3月10日 下午4:03:56
     */
    List<FinanceInfo> selectByMap(Map map);
    /**
     * 根据账单号和账户Id查询账单
     * @param billNo
     * @param sellerId
     * @return
     * @author John
     * @date： 2018年3月10日 下午5:30:52
     */
    FinanceInfo selectByBillNo(@Param(value="billNo") String billNo,@Param(value="sellerId")String sellerId);
    /**
     * 批量增加账单，每月初创建
     * @param financeList
     * @return
     * @author John
     * @date： 2018年3月30日 上午9:45:36
     */
    int batchInsert(List<FinanceInfo> financeList);
    /**
     * 生成账单
     * @return
     * @author John
     * @date： 2018年4月3日 上午11:41:36
     */
	int updateStatusToWaitCash();
	/**
	 * 查询出未出账单的数量
	 * @return
	 * @author John
	 * @date： 2018年4月3日 上午11:44:34
	 */
	int selectNobillCount();
	/**
	 * 获取商户可提现金额和带开票金额
	 * @param sellerId
	 * @return
	 * @author John
	 * @date： 2018年4月11日 下午5:27:43
	 */
	Map<String, Object> selectDisPosableValue(String sellerId);
	/**
	 * 查询未生成的订单
	 * @param sellerId
	 * @return
	 * @author John
	 * @date： 2018年4月24日 下午3:37:49
	 */
	FinanceInfo selectNotGeneratedBySellerId(String sellerId);
}