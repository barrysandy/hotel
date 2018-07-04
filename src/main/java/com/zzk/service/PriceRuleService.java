package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.PriceRule;
/**
 * 
* 价格规则信息
* @author：sty
* @date：2017-11-02 10:40
 */
public interface PriceRuleService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：sty
    * @date：2017-11-02 10:40
	 */
	List<PriceRule> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：sty
    * @date：2017-11-02 10:40
	 */
	PriceRule selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：sty
    * @date：2017-11-02 10:40
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:40
	 */
	public int update(PriceRule bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:40
	 */
	public int insert(PriceRule bean);
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
	* 根据goodId查询
	* @param goodId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-9
	 */
	public List<PriceRule> selectByGoodsId(String goodsId,String startTime);
	/**
	 * 
	* 根据hotelId查询
	* @param hotelId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-9
	 */
	public List<PriceRule> selectByHotelId(String hotelId,String startTime);
	
	/**
	 * 
	* 根据hotelId集合 查价格规则
	* @param hotelIdList
	* @param comeDate
	* @param leaveDate
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-27
	 */
	public List<PriceRule> selectRuleByHotelList(Map map);
	
	List<PriceRule> selectRuleByGoodsList(List list);
	
}
