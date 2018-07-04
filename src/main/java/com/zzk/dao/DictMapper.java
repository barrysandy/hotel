package com.zzk.dao;

import java.util.*;
import com.zzk.entity.Dict;
public interface DictMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-04-10 11:46
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
    int insert(Dict record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
    int insertSelective(Dict record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return Dict 实体类
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
    Dict selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
    int updateByPrimaryKeySelective(Dict record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
    int updateByPrimaryKey(Dict record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
    List<Dict> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
    int selectCount(Map record);
    /**
     * 通过条件查询
     * @param dict
     * @return
     * @author John
     * @date： 2018年4月10日 下午3:38:23
     */
    List<Dict> findList(Dict dict);
    /**
     * 查询全部
     * @param dict
     * @return
     * @author huashuwen
     * @date： 2018年4月10日 下午3:38:23
     */
    List<Dict> findAllDict();
}