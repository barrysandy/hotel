package com.zzk.dao;

import java.util.*;
import com.zzk.entity.WithDrawCash;
public interface WithDrawCashMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-04-12 10:41
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
    int insert(WithDrawCash record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
    int insertSelective(WithDrawCash record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return WithDrawCash 实体类
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
    WithDrawCash selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
    int updateByPrimaryKeySelective(WithDrawCash record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
    int updateByPrimaryKey(WithDrawCash record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
    List<WithDrawCash> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-04-12 10:41
	 */
    int selectCount(Map record);
}