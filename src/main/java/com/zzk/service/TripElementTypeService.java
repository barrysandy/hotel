package com.zzk.service;

import java.util.*;
import com.zzk.entity.TripElementType;
/**
 * 旅游要素类型
 * @author: huashuwen
 * @date: 2018-03-10 11:01
 */
public interface TripElementTypeService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
	List<TripElementType> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 旅游要素类型实体类
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
	TripElementType selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
	 int update(TripElementType bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
	 int insert(TripElementType bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author huashuwen
	 * @date  2018-03-10 11:01
	 */
	 int delete(String id);
 	/**
	 * 查询出所有类型
	 * @return list
     * @author: huashuwen
     * @date: 2018-03-10 11:01
	 */
	List<TripElementType> selectType();
	
}
