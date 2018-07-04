package com.zzk.service;

import java.util.*;
import com.zzk.entity.WithDrawCash;
import com.zzk.util.Page;
import com.zzk.util.Result;
/**
 * 提现信息表的实体类
 * @author: wangpeng
 * @date: 2018-04-12 10:41
 */
public interface WithDrawCashService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
	 * @param pager 
     * @date: 2018-04-12 10:41
	 */
	Result<Object> selectByPage(Page<Object> pager, Map<String,Object> map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 提现信息表的实体类实体类
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
	WithDrawCash selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
	 int update(WithDrawCash bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
	 int insert(WithDrawCash bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-04-12 10:41
	 */
	 int delete(String id);
	
}
