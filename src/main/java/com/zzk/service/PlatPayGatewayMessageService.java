package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.entity.PlatPayGatewayMessage;
/**
 * 
* 网关报文
* @author：zhou.zhengkun
* @date：2017-03-24 17:16
 */
public interface PlatPayGatewayMessageService {
	/**
	 * 
	* 分頁查詢
	* @return
    * @author：zhou.zhengkun
    * @date：2017-03-24 17:16
	 */
	List<PlatPayGatewayMessage> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：zhou.zhengkun
    * @date：2017-03-24 17:16
	 */
	PlatPayGatewayMessage selectByPrimaryKey(String id);

	/**
	 * 订单ID查询报文
	 * @param orderId
	 * @return
	 */
	List<PlatPayGatewayMessage> selectByOrderId(String orderId);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：zhou.zhengkun
    * @date：2017-03-24 17:16
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：zhou.zhengkun
    * @date：2017-03-24 17:16
	 */
	public int update(PlatPayGatewayMessage bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：zhou.zhengkun
    * @date：2017-03-24 17:16
	 */
	public int insert(PlatPayGatewayMessage bean);
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
	 * @Description:  
	 * @author sty
	 */
	int updateByPrimaryKeySelective(PlatPayGatewayMessage bean);


	
}
