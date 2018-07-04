package com.zzk.service;

import java.util.*;

import com.zzk.entity.OperateLog;
/**
 * 操作日志
 * @author: huashuwen
 * @date: 2018-04-02 16:31
 */
public interface OperateLogService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: huashuwen
     * @date: 2018-04-02 16:31
	 */
	List<OperateLog> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 操作日志实体类
     * @author: huashuwen
     * @date: 2018-04-02 16:31
	 */
	OperateLog selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: huashuwen
     * @date: 2018-04-02 16:31
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-04-02 16:31
	 */
	 int update(OperateLog bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-04-02 16:31
	 */
	 int insert(OperateLog bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author huashuwen
	 * @date  2018-04-02 16:31
	 */
	 int delete(String id);
	 /**
	 * 新增日志
	 * @param content 日志内容, id被操作对象id, type操作类型1新增，2修改，3删除，4其他
	 * @return int 修改行数
	 * @author huashuwen
	 * @date  2018-06-05 16:31
	 */
	 int recordLog(String content,String id,int type);
}
