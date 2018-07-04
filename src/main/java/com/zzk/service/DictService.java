package com.zzk.service;

import java.util.*;
import com.zzk.entity.Dict;
import com.zzk.util.Result;
/**
 * 字典信息表的实体类
 * @author: wangpeng
 * @date: 2018-04-10 11:46
 */
public interface DictService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
	List<Dict> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 字典信息表的实体类实体类
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
	Dict selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
	 int update(Dict bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-04-10 11:46
	 */
	 int insert(Dict bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-04-10 11:46
	 */
	 int delete(String id);
   /**
    * 根据条件类型或者描述查询list
    * @param dict
    * @return
    * @author John
    * @date： 2018年4月10日 下午3:46:51
    */
	Result<Object> selectList(Dict dict);
	
}
