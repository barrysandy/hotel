package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.AdvertisementInfo;
/**
 * 
* 广告信息表
* @author：huashuwen
* @date：2018-03-09 15:29
 */
public interface AdvertisementInfoService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:29
	 */
	List<AdvertisementInfo> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：huashuwen
    * @date：2018-03-09 15:29
	 */
	AdvertisementInfo selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:29
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:29
	 */
	public int update(AdvertisementInfo bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:29
	 */
	public int insert(AdvertisementInfo bean);
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
