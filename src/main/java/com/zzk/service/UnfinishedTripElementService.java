package com.zzk.service;

import java.util.*;

import com.zzk.entity.UnfinishedTripElement;
/**
 * 待完善旅游要素
 * @author: huashuwen
 * @date: 2018-03-10 11:02
 */
public interface UnfinishedTripElementService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
	List<UnfinishedTripElement> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 待完善旅游要素实体类
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
	UnfinishedTripElement selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
	 int update(UnfinishedTripElement bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 11:02
	 */
	 int insert(UnfinishedTripElement bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author huashuwen
	 * @date  2018-03-10 11:02
	 */
	 int delete(String id);
	 
	 /**
		 * 插入旅游要素
		 * @param nameList 要素名称集合
		 * @return int 修改行数
		 * @author huashuwen
		 * @date  2018-03-10 10:57
	*/
	 int batchInsert(List<String> nameList);
	 
	 /**
	  * 关联
	  * @param id，tripElementId
	  * @return int 修改行数
	  * @author huashuwen
	  * @date  2018-03-10 10:57
	  */
	 int reletion(String id, String tripElementId);
}
