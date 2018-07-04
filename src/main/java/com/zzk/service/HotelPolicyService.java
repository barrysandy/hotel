package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.HotelPolicy;
/**
 * 
* 酒店政策信息
* @author：huashuwen
* @date：2017-11-17 11:46
 */
public interface HotelPolicyService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：huashuwen
    * @date：2017-11-17 11:46
	 */
	List<HotelPolicy> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：huashuwen
    * @date：2017-11-17 11:46
	 */
	HotelPolicy selectByPrimaryKey(String id);
	/**
	 * 
	* 根据hotelId查政策
    * @author：huashuwen
    * @date：2017-11-17 11:46
	 */
	HotelPolicy selectByHotelId(String hotelId);
	
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：huashuwen
    * @date：2017-11-17 11:46
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-17 11:46
	 */
	public int update(HotelPolicy bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-17 11:46
	 */
	public int insert(HotelPolicy bean);
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
