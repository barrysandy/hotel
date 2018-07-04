package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.RoomType;
/**
 * 
* 房型信息
* @author：sty
* @date：2017-11-02 10:37
 */
public interface RoomTypeService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：sty
    * @date：2017-11-02 10:37
	 */
	List<RoomType> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：sty
    * @date：2017-11-02 10:37
	 */
	RoomType selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：sty
    * @date：2017-11-02 10:37
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:37
	 */
	public int update(RoomType bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:37
	 */
	public int insert(RoomType bean);
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
	* 查询房型
    * @param hotelId
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:37
	 */
	List<RoomType> selectByHotelId(String hotelId);
	/**
	 * 
	* 获取酒店下房型列表
    * @param hotelId
	* @return
    * @author：huashuwen
    * @date：2017-12-04 10:37
	 */
	List<RoomType> getRoomtypeList(String hotelId);
	/**
	 * 
	* 根据goodsId查房型
    * @param goodsId
	* @return
    * @author：huashuwen
    * @date：2017-12-04 10:37
	 */
	RoomType selectByGoodsId(String goodsId);
	
	
	/**
	 * 
	* 查询房型集合
    * @param hotelId
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:37
	 */
	List<RoomType> selectByHotelsId(List<String> idList);
}
