package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.LoginLog;
/**
 * 
* 登陆日志
* @author：huashuwen
* @date：2018-03-08 10:19
 */
public interface LoginLogService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：huashuwen
    * @date：2018-03-08 10:19
	 */
	List<LoginLog> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：huashuwen
    * @date：2018-03-08 10:19
	 */
	LoginLog selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：huashuwen
    * @date：2018-03-08 10:19
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-08 10:19
	 */
	public int update(LoginLog bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-08 10:19
	 */
	public int insert(LoginLog bean);
	/**
	 * 
	* 删除
	* @param id
	* @return
	* @return
	* @author lishiqiang
	* @date 2017-3-16
	* modify history
	 */
	public int delete(String id);
	
}
