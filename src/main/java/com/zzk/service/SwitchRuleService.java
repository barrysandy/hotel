package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.SwitchRule;
/**
 * 
* 房型开关
* @author：huashuwen
* @date：2018-01-05 10:03
 */
public interface SwitchRuleService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：huashuwen
    * @date：2018-01-05 10:03
	 */
	List<SwitchRule> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：huashuwen
    * @date：2018-01-05 10:03
	 */
	SwitchRule selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：huashuwen
    * @date：2018-01-05 10:03
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-01-05 10:03
	 */
	public int update(SwitchRule bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-01-05 10:03
	 */
	public int insert(SwitchRule bean);
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
	* 根据酒店id查询开关规则
	* @param hotelId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-8
	 */
	public List<SwitchRule> selectByHotelId(String hotelId,String startTime);
	/**
	 * 
	* 根据商品id查询开关规则
	* @param goodsId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-8
	 */
	public List<SwitchRule> selectByGoodsId(String goodsId, String startTime);
	
	/**
	 * 
	* 根据酒店集合查询开关规则
	* @param list
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-8
	 */
	public List<SwitchRule> selectByHotelsId(List<String> idList, String startTime,String endTime);
	
}
