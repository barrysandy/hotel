package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.HotelGoods;
/**
 * 
* 酒店商品信息
* @author：sty
* @date：2017-11-02 10:44
 */
public interface HotelGoodsService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	List<HotelGoods> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	HotelGoods selectByPrimaryKey(String id);
	
	/**
	 * 
	* 详情
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	HotelGoods selectInfoByPrimaryKey(String id);
	
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	public int update(HotelGoods bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	public int insert(HotelGoods bean);
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
	 * 查询商品ID集
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:44
	 */
	public List<String> selectGoodIds(String hotelId);
	/**
	 * 查询商品
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:44
	 */
	public List<HotelGoods> selectHotelGoods(String hotelId);
	/**
	 * 查询所有商品包含已删除的
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:44
	 */
	public int countHotelGoods(String hotelId);
	
	/**
	 * 根据商品id查询库存和价格
	* @param id
	* @param date
	* @param week
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:44
	 */
	public Map<String,Object> selectStockandPricebyId(String id,String date,String dayOfWeek);
	
	
	/**
	 * 根据酒店集合查询出所有酒店商品列表
	* @param hotelIds
	* @return
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	List<HotelGoods> selectHotelGoodsByHotelIds(List<String> hotelIds);
	/**
	 * 
	* @param 
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:44
	 */
}
