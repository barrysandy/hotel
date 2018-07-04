package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.DatePrice;
/**
 * 
* 酒店时间价格二维表
* @author：huashuwen
* @date：2018-01-29 09:49
 */
public interface DatePriceService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：huashuwen
    * @date：2018-01-29 09:49
	 */
	List<DatePrice> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：huashuwen
    * @date：2018-01-29 09:49
	 */
	DatePrice selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：huashuwen
    * @date：2018-01-29 09:49
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-01-29 09:49
	 */
	public int update(DatePrice bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-01-29 09:49
	 */
	public int insert(DatePrice bean);
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
	 * 插入酒店的每天最高最低价格
	* @param hotelId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-3-16
	 */
	public List<Map<String,Object>> insertPirceInfo(String hotelId) throws Exception ;
	
	 
}
