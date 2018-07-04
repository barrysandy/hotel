package com.zzk.service;

import java.util.*;
import com.zzk.entity.PlatDealRecord;
/**
 * 交易流水记录
 * @author: wangpeng
 * @date: 2018-03-16 12:05
 */
public interface PlatDealRecordService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
	List<PlatDealRecord> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 交易流水记录实体类
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
	PlatDealRecord selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
	 int update(PlatDealRecord bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-16 12:05
	 */
	 int insert(PlatDealRecord bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-16 12:05
	 */
	 int delete(String id);
	
}
