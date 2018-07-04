package com.zzk.service;

import java.util.*;
import com.zzk.entity.FinanceInfo;
import com.zzk.util.Page;
import com.zzk.util.Result;
/**
 * 商家账户表
 * @author: wangpeng
 * @date: 2018-03-10 15:34
 */
public interface FinanceInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
	List<FinanceInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 商家账户表实体类
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
	FinanceInfo selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
	 int update(FinanceInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-10 15:34
	 */
	 int insert(FinanceInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-10 15:34
	 */
	 int delete(String id);
	 /**
	  * 根据条件分页查询
	  * @param page
	  * @param info
	  * @return
	  * @author John
	  * @date： 2018年3月10日 下午3:57:59
	  */
	 Result<Object> selectByMap(Page page, Map map); 
	 /**
	  * 根据账单号查询账单
	  * @param billNo
	  * @return
	  * @author John
	  * @date： 2018年3月10日 下午5:33:30
	  */
	 String selectByBillNo(String billNo,String sellerId);
	 /**
	  * 查询可提现金额和贷款发票金额
	  * @param sellerId
	  * @return
	  * @author John
	  * @date： 2018年4月11日 下午5:24:46
	  */
	Result<Object> selectDisposableValue(String sellerId);
	/**
	 * 订单完成后生成账单
	 * @param orderNo
	 * @return
	 * @author John
	 * @date： 2018年4月24日 下午2:16:21
	 */
	int generatorBill(String orderNo);
}
