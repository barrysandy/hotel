package com.zzk.service;

import java.util.*;
import com.zzk.entity.SellerMessageNotify;
import com.zzk.util.Result;
/**
 * 提现信息表的实体类
 * @author: wangpeng
 * @date: 2018-05-09 14:27
 */
public interface SellerMessageNotifyService {
	/**
	 * 通过商户ID查询商户的消息情况
	 * @param sellerId
	 * @return
	 * @author John
	 * @date： 2018年5月9日 下午2:43:02
	 */
	Result<Object> selectBySellerId(String sellerId);
	/**
	 * 更新消息信息
	 * @param message
	 * @return
	 * @author John
	 * @date： 2018年5月9日 下午3:12:09
	 */
	int updateSellerMessageNotify(SellerMessageNotify message);
	/**
	 * 通过商户ID和消息类型查询
	 * @param sellerId
	 * @param type
	 * @return
	 * @author John
	 * @date： 2018年5月9日 下午3:13:40
	 */
	SellerMessageNotify selectMessageByTypeAndSellerId(String sellerId,int type);
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-05-09 14:27
	 */
	 int insert(SellerMessageNotify bean);
	 /**
	  * 将消息数量清零
	  * @param sellerId
	  * @param type
	  * @return
	  * @author John
	  * @date： 2018年5月10日 上午11:50:09
	  */
	Result<Object> clearMessage(String sellerId, int type);
	
}
