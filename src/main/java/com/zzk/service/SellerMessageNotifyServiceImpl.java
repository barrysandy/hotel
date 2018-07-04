package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zzk.dao.SellerMessageNotifyMapper;
import com.zzk.entity.SellerMessageNotify;
import com.zzk.util.DateUtils;
import com.zzk.util.Result;
import com.zzk.vo.SellerMessageNotifyVo;

/**
 * 提现信息表的实体类
* @author: wangpeng
* @date: 2018-05-09 14:27
 */
@Service("sellerMessageNotifyService")
public class SellerMessageNotifyServiceImpl implements SellerMessageNotifyService {

	@Resource
	private SellerMessageNotifyMapper sellerMessageNotifyMapper;

	@Override
	public Result<Object> selectBySellerId(String sellerId) {
		List<SellerMessageNotify> list = sellerMessageNotifyMapper.selectBySellerId(sellerId);
		if(list == null){
			return new Result<Object>(0, "error","无该商户记录", null);
		}
		SellerMessageNotifyVo messageVo = new SellerMessageNotifyVo();
		//通知类型通知类型1新订单2退款 3差评4取单5财务6满团
		for (int i = 0; i < list.size(); i++) {
			SellerMessageNotify messageNotify = list.get(i);
			if(messageNotify.getType()== 1){
				messageVo.setNewOrderNumber(messageNotify.getMessageNumber());
			}
			if(messageNotify.getType()== 2){
				messageVo.setRefundNumber(messageNotify.getMessageNumber());
			}
			if(messageNotify.getType()== 3){
				messageVo.setCancelOrderNumber(messageNotify.getMessageNumber());
			}
			if(messageNotify.getType()== 4){
				messageVo.setBadCommentNumber(messageNotify.getMessageNumber());
			}
			if(messageNotify.getType()== 5){
				messageVo.setFinanceNumber(messageNotify.getMessageNumber());
			}
			if(messageNotify.getType()== 6){
				messageVo.setFullNumber(messageNotify.getMessageNumber());
			}
			
		}
		return new Result<Object>(1, "success","请求成功", messageVo);
	}
	
	public int updateSellerMessageNotify(SellerMessageNotify message){
		return sellerMessageNotifyMapper.updateByPrimaryKeySelective(message);
	}

	@Override
	public SellerMessageNotify selectMessageByTypeAndSellerId(String sellerId, int type) {
		return sellerMessageNotifyMapper.selectMessageByTypeAndSellerId(sellerId, type);
	}
	/**
	 * 新增
	 */
	@Override
	public int insert(SellerMessageNotify bean) {
		return sellerMessageNotifyMapper.insertSelective(bean);
	}

	@Override
	public Result<Object> clearMessage(String sellerId, int type) {
		SellerMessageNotify notify = sellerMessageNotifyMapper.selectMessageByTypeAndSellerId(sellerId, type);
		notify.setUpdateDate(DateUtils.getCurrentDate());
		notify.setMessageNumber(0);
		int result = sellerMessageNotifyMapper.updateByPrimaryKeySelective(notify);
		if(result>0){
			return new Result<>(1, "success", "修改成功");
		}
		return new Result<>(0, "error", "修改失败");
	}
}
