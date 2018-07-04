package com.zzk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.zzk.entity.RoomType;
import com.zzk.entity.StockRule;
import com.zzk.entity.SwitchRule;
/**
 * 
* 库存规则信息
* @author：sty
* @date：2017-11-02 10:41
 */
public interface StockRuleService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：sty
    * @date：2017-11-02 10:41
	 */
	List<StockRule> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：sty
    * @date：2017-11-02 10:41
	 */
	StockRule selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：sty
    * @date：2017-11-02 10:41
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:41
	 */
	public int update(StockRule bean);
	
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:41
	 */
	public int insert(StockRule bean);
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
	* 根据房型查询库存规则
	* @param roomtypeId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-8
	 */
	public List<StockRule> selectByRoomtype(String roomtypeId,String startTime);
	/**
	 * 
	* 根据酒店id查询库存规则
	* @param hotelId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-8
	 */
	public List<StockRule> selectByHotelId(String hotelId,String startTime);
	/**
	 * 
	* 根据房型id集合查询库存规则
	* @param hotelId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-8
	 */
	public List<StockRule> selectByRoomtypeList(List<String> list,String startTime);
	/**
	 * 
	* 根据商品查询库存规则
	* @param roomtypeId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-14
	 */
	public List<StockRule> selectByGoodsId(String goodsId,String startTime);
  	/**
	 * 
	* 根据hotelID查询是否满房
	* @param roomtypeId
	* @return 1 未满 -1 满房
  	 * @throws Exception 
	 */
	public int selectByHotelIdFull(String hotelId) throws Exception;
	/**
	 * 
	* 计算库存详情
	* @param
	* @return 
	 */
	public List<Map<String,Object>> getStockInfo(List<HashMap<String,Object>> orderList,List<StockRule> stockRuleList
			,List<RoomType> roomTypeList,List<SwitchRule> switchRuleList,String startTime,String endTime) throws Exception;
	
	
}
