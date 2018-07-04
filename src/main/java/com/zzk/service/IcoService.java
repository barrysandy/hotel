package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.Ico;
/**
 * 
* 图标
* @author：huashuwen
* @date：2017-11-28 11:53
 */
public interface IcoService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：huashuwen
    * @date：2017-11-28 11:53
	 */
	List<Ico> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：huashuwen
    * @date：2017-11-28 11:53
	 */
	Ico selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：huashuwen
    * @date：2017-11-28 11:53
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-28 11:53
	 */
	public int update(Ico bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-28 11:53
	 */
	public int insert(Ico bean);
	
	/**
	 * 
	* 查询所有
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-28 11:53
	 */
	public List<Ico> selectIco();
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
