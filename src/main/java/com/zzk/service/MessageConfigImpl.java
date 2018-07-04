package com.zzk.service;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.zzk.dao.MessageConfigMapper;
import com.zzk.entity.MessageConfig;
import com.zzk.entity.MessageConfigCustom;
import com.zzk.service.MessageConfigService;
/**
 * 
 * @Description:消息提醒设置
 * @author John
 * @date： 2017年12月5日 下午3:20:50
 */

@Service("messageConfigService")
public class MessageConfigImpl implements MessageConfigService {

	@Resource
	private MessageConfigMapper messageConfigMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 */
	@Override
	public int insert(MessageConfig record) {
		// TODO Auto-generated method stub
		record.setId(UUID.randomUUID().toString());
		return messageConfigMapper.insert(record);
	}

	@Override
	public int insertSelective(MessageConfig record) {
		// TODO Auto-generated method stub
		record.setId(UUID.randomUUID().toString());
		return messageConfigMapper.insertSelective(record);
	}

	@Override
	public MessageConfig selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return messageConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MessageConfig record) {
		// TODO Auto-generated method stub
		return messageConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MessageConfig record) {
		// TODO Auto-generated method stub
		return messageConfigMapper.updateByPrimaryKey(record);
	}

	@Override
	public MessageConfigCustom selectByShopId(String shopId) {
		// TODO Auto-generated method stub
		MessageConfig config = messageConfigMapper.selectByShopId(shopId);
		if(config == null){
			return null;
		}
		MessageConfigCustom custom = new MessageConfigCustom();
		BeanUtils.copyProperties(config, custom);
		String newOrderMsg = config.getNewOrderMsg();
		if(newOrderMsg!=null){
			custom.setNewOrderMsgs(newOrderMsg.split(","));
		}
		String refundMsg = config.getRefundMsg();
		if(refundMsg != null){
			custom.setRefundMsgs(refundMsg.split(","));
		}
		String cancelOrderMsg = config.getCancelOrderMsg();
		if(cancelOrderMsg != null){
			custom.setCancelOrderMsgs(cancelOrderMsg.split(","));
		}
		String badConmentMsg = config.getBadCommentMsg();
		if(badConmentMsg != null){
			custom.setBadCommentMsgs(badConmentMsg.split(","));
		}
		String financialMsg = config.getFinancialMsg();
		if(financialMsg != null){
			custom.setFinancialMsgs(financialMsg.split(","));
		}
		String fullRoomMsg = config.getFullRoomMsg();
		if(fullRoomMsg != null){
			custom.setFullRoomMsgs(fullRoomMsg.split(","));
		}
		
		return custom;
	}

}
