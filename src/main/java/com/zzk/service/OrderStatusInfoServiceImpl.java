package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.zzk.dao.BusinessInfoMapper;
import com.zzk.dao.OrderBaseInfoMapper;
import com.zzk.dao.OrderStatusInfoMapper;
import com.zzk.dao.UserMapper;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderStatusInfo;
import com.zzk.entity.User;

/**
 * 用户表
* @author: wangpeng
* @date: 2018-03-14 15:52
 */
@Service("orderStatusInfoService")
public class OrderStatusInfoServiceImpl implements OrderStatusInfoService {

	@Resource
	private OrderStatusInfoMapper orderStatusInfoMapper;
	@Resource
	private OrderBaseInfoMapper orderBaseInfoMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private BusinessInfoMapper businessInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<OrderStatusInfo> selectByPage(Map map) {
		return orderStatusInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return orderStatusInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public OrderStatusInfo selectByPrimaryKey(String id) {
		return orderStatusInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(OrderStatusInfo bean) {
		return orderStatusInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(OrderStatusInfo bean) {
		return orderStatusInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		OrderStatusInfo bean = orderStatusInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return orderStatusInfoMapper.updateByPrimaryKey(bean);
	}

	@Override
	public int changeStatus(String orderNo, int orderStatus) {
		OrderBaseInfo baseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
		String name = getCreateName(baseInfo,orderStatus);
		OrderStatusInfo orderStatusInfo = new OrderStatusInfo();
		orderStatusInfo.setId(UUID.randomUUID().toString());
		orderStatusInfo.setCreateTime(new Date());
		orderStatusInfo.setOrderNo(orderNo);
		orderStatusInfo.setOrderState(orderStatus);
		orderStatusInfo.setPayState(baseInfo.getPayState());
		orderStatusInfo.setCreaterName(name);
		orderStatusInfo.setStatus(1);
		return orderStatusInfoMapper.insert(orderStatusInfo);
	}
	@Override
	public int changeStatusToCancel(String orderNo, int orderStatus,String feekback,String remarks) {
		OrderBaseInfo baseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
		String name = getCreateName(baseInfo,orderStatus);
		OrderStatusInfo orderStatusInfo = new OrderStatusInfo();
		orderStatusInfo.setId(UUID.randomUUID().toString());
		orderStatusInfo.setCreateTime(new Date());
		orderStatusInfo.setOrderNo(orderNo);
		orderStatusInfo.setOrderState(orderStatus);
		orderStatusInfo.setPayState(baseInfo.getPayState());
		orderStatusInfo.setRefundFeedback(feekback);
		orderStatusInfo.setRemark(remarks);
		orderStatusInfo.setCreaterName(name);
		orderStatusInfo.setStatus(1);
		return orderStatusInfoMapper.insert(orderStatusInfo);
	}
	
	private String getCreateName(OrderBaseInfo orderBaseInfo,int orderStatus){
		String name = "";
		if(orderStatus == 1 || orderStatus ==2 || orderStatus == 6 || orderStatus == 10){
			name = getBuyerName(orderBaseInfo.getBuyerId());
		}else if(orderStatus == 3 || orderStatus == 7 || orderStatus== 9) {
			name = getSellerName(orderBaseInfo.getSellerId());
		}else{
			name = "系统";
		}
		
		return name;
	}
	
	public String getBuyerName(String id){
		User user = userMapper.selectByPrimaryKey(id);
		if(user != null){
			String nickName = filterEmoji(user.getNickname());
			if(nickName != null){
				return nickName;
			}
		}
		return "";
	}
	public static String filterEmoji(String source) {
		if(source == null){
			return "";
		}
		int len = source.length(); 
		StringBuilder buf = new StringBuilder(len); 
		for (int i = 0; i < len; i++){ 
			char codePoint = source.charAt(i); 
			if (isNotEmojiCharacter(codePoint)) 
		{ 
		buf.append(codePoint); 
		} else{
	
		buf.append("*");
	
		}
		} 
		return buf.toString(); 
		}
	private static boolean isNotEmojiCharacter(char codePoint) 
	{ 
	return (codePoint == 0x0) || 
		(codePoint == 0x9) || 
		(codePoint == 0xA) || 
		(codePoint == 0xD) || 
		((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || 
		((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || 
		((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)); 
	} 
	public String getSellerName(String id){
		BusinessInfo businessInfo = businessInfoMapper.selectByPrimaryKey(id);
		if(businessInfo != null){
			User user = userMapper.selectByPrimaryKey(businessInfo.getUserId());
			if(user != null){
				String account = user.getPhoneNum();
				if(account != null){
					return account;
				}
			}
		}
		return "";
	}
}
