package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.AdvertisementPositionInfo;

/**
 * 
* 广告位信息表
* @author：huashuwen
* @date：2018-03-09 15:30
 */
public interface AdvertisementPositionInfoService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:30
	 */
	List<AdvertisementPositionInfo> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：huashuwen
    * @date：2018-03-09 15:30
	 */
	AdvertisementPositionInfo selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:30
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:30
	 */
	public int update(AdvertisementPositionInfo bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:30
	 */
	public int insert(AdvertisementPositionInfo bean);
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
