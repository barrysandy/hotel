package com.zzk.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.MessageContentMapper;
import com.zzk.entity.MessageContent;
import com.zzk.service.MessageContentService;
@Service("messageContentService")
public class MessageContentServiceImpl implements MessageContentService {
    @Resource
	private MessageContentMapper messageContentMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return messageContentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MessageContent record) {
		// TODO Auto-generated method stub
		return messageContentMapper.insert(record);
	}

	@Override
	public int insertSelective(MessageContent record) {
		// TODO Auto-generated method stub
		return messageContentMapper.insertSelective(record);
	}

	@Override
	public MessageContent selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return messageContentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MessageContent record) {
		// TODO Auto-generated method stub
		return messageContentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MessageContent record) {
		return messageContentMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<MessageContent> selectByShopId(String shopId) {
		// TODO Auto-generated method stub
		return messageContentMapper.selectByShopId(shopId);
	}

	@Override
	public int updateByprimarykeysStatus(String[] ids) {
		return messageContentMapper.updateByIds(ids);
	}

	@Override
	public int updateByType(String shopId, int type) {
		return messageContentMapper.updateByType(shopId, type);
	}



}
