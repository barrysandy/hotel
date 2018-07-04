package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.HotelServe;
/**
 * 
* 酒店服务设施信息
* @author：huashuwen
* @date：2017-11-17 14:21
 */
public interface HotelServeService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：huashuwen
    * @date：2017-11-17 14:21
	 */
	List<HotelServe> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：huashuwen
    * @date：2017-11-17 14:21
	 */
	HotelServe selectByPrimaryKey(String id);
	/**
	 * 
	* 酒店Id查服务
    * @author：huashuwen
    * @date：2017-11-17 14:21
	 */
	HotelServe selectByHotelId(String hotelId);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：huashuwen
    * @date：2017-11-17 14:21
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-17 14:21
	 */
	public int update(HotelServe bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-17 14:21
	 */
	public int insert(HotelServe bean);
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
	
	/**
	 * 根据酒店id集合查询所有的酒店服务
	 * @param userId
	 * @return
	 */
    List<HotelServe> selectByHotelIds(List<String> hotelIds);
	
}
