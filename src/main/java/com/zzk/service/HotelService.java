package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.BusinessInfo;
import com.zzk.entity.Hotel;
import com.zzk.entity.PriceRule;
/**
 * 
* 酒店信息
* @author：sty
* @date：2017-11-02 10:26
 */
public interface HotelService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：sty
    * @date：2017-11-02 10:26
	 */
	List<Hotel> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：sty
    * @date：2017-11-02 10:26
	 */
	Hotel selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：sty
    * @date：2017-11-02 10:26
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:26
	 */
	public int update(Hotel bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:26
	 */
	public int insert(Hotel bean);
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
	 * 
	* 根据名称查询
	* @param name
	* @return
	* @author huashuwen
	* @date 2017-11-03
	 */
	public Hotel selectByName(String name);
	
	public List<Hotel> selectList();
	/**
	 * 
	* 查询城市
	* @param cityId
	* @return
	* @author huashuwen
	* @date 2017-11-03
	 */
	public String selecCity(String cityId);
	/**
	 * 
	* 查询省份
	* @param provinceId
	* @return
	* @author huashuwen
	* @date 2017-11-03
	 */
	public String selecProvince(String provinceId);
	/**
	 * 
	* 查询地区
	* @param areaId
	* @return
	* @author huashuwen
	* @date 2017-11-03
	 */
	public String selectArea(String areaId);
	/**
	 * 
	* 获取省份列表
	* @return
	* @author huashuwen
	* @date 2017-11-03
	 */
	public List<Map<String,Object>> getProvinceList();
	/**
	 * 
	* 根据省获取城市列表
	* @param provinceId
	* @return
	* @author huashuwen
	* @date 2017-11-03
	 */
	public List<Map<String,Object>> getCityList(String provinceId);
	
	/**
	 * 
	* 根据城市获取地区列表
	* @param cityId
	* @return
	* @author huashuwen
	* @date 2017-11-03
	 */
	public List<Map<String,Object>> getAreaList(String cityId);
	/**
	 * 
	* 查询ownerId下的酒店
	* @param 
	* @return
	* @author huashuwen
	* @date 2017-12-01
	 */
	public List<Hotel> checkOwnerId(String id);
	/**
	 * 查询是否所有房间都已经预定
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getRoomState(String userId);
	
	
	/**
	 * 查询
	 */
	String selectOp(String id);
	
	/**
	 * 将线路商家信息转化成酒店商家信息
	 */
	public Hotel business2Hotel(BusinessInfo bean);
	/**
	 * 将酒店商家信息转化成线路商家信息
	 */
	public BusinessInfo hotel2Business(Hotel bean);
	public int update(BusinessInfo bean);
	public int updateHotel(BusinessInfo bean);
}
