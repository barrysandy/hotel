package com.zzk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zzk.util.StringUtils;
import com.zzk.util.HotelResult;
//import com.wisesoft.wisdom.xixing.common.utils.MapUtils;
import com.zzk.entity.MessageConfigCustom;
import com.zzk.entity.MessageContent;
import com.zzk.service.MessageConfigService;
import com.zzk.service.MessageContentService;
import com.zzk.service.MessageService;


@Controller
@RequestMapping(value = "/message")
public class HotelMessageController extends BaseController {
	@Autowired
	private MessageConfigService messageConfigService;
	@Autowired
	private MessageContentService messageContentService;
	@Autowired
	private MessageService messageService;

	/*
	 * 消息设置的查询
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public String getHotelMessage(@RequestParam(required = true) String shopId) {
		HotelResult<MessageConfigCustom> result = new HotelResult<MessageConfigCustom>();
		MessageConfigCustom configs = messageConfigService.selectByShopId(shopId);
		if (configs == null) {
			configs = new MessageConfigCustom();
			configs.setShopId(shopId);
		}
		result.setData(configs);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);

	}

	/**
	 * 设置消息提醒配置
	 * 
	 * @param newConfig
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(MessageConfigCustom newConfig) {
		HotelResult<Object> result = new HotelResult<Object>();
		String shopId = newConfig.getShopId();
		if (StringUtils.isEmpty(shopId)) {
			result.setMsg("error");
			result.setState(0);
			result.setMessage("酒店ID为空");
			return JSON.toJSONString(result);
		}
		MessageConfigCustom configs = messageConfigService.selectByShopId(shopId);
		newConfig.setBadCommentMsg(StringUtils.join(newConfig.getBadCommentMsgs(), ","));
		newConfig.setCancelOrderMsg(StringUtils.join(newConfig.getCancelOrderMsgs(), ","));
		newConfig.setFinancialMsg(StringUtils.join(newConfig.getFinancialMsgs(), ","));
		newConfig.setFullRoomMsg(StringUtils.join(newConfig.getFullRoomMsgs(), ","));
		newConfig.setNewOrderMsg(StringUtils.join(newConfig.getNewOrderMsgs(), ","));
		newConfig.setRefundMsg(StringUtils.join(newConfig.getRefundMsgs(), ","));
		if (configs != null) {
			if (newConfig.getId() == null) {
				newConfig.setId(configs.getId());
			}
			messageConfigService.updateByPrimaryKey(newConfig);
			result.setMsg("success");
			result.setState(1);
			return JSON.toJSONString(result);

		} else {
			messageConfigService.insertSelective(newConfig);
			result.setMsg("success");
			result.setState(1);
			return JSON.toJSONString(result);

		}
	}
	/**
	 * 站内获取新消息
	 * @param shopId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/newMsg")
	public String findNewMessage(String shopId) {
		HotelResult<Map<String,Object>> result = new HotelResult<>();
		if(StringUtils.isBlank(shopId)){
			result.setState(0);
			result.setMsg("error");
			return JSON.toJSONString(result);
		}
		List<MessageContent> messageList = messageContentService.selectByShopId(shopId);
		List<MessageContent> newOrder = new ArrayList<>();
		List<MessageContent> refund = new ArrayList<>();
		List<MessageContent> cancel = new ArrayList<>();
		List<MessageContent> badComm = new ArrayList<>();
		List<MessageContent> finance = new ArrayList<>();
		List<MessageContent> full = new ArrayList<>();
		for (MessageContent messageContent : messageList) {
			//消息类型1、新订单提醒 2、退款提醒3、取单提醒4、差评提醒5、财务提醒6、满房提醒
			if(messageContent.getMsgType()==1){
				newOrder.add(messageContent);
			}
			if(messageContent.getMsgType()==2){
				refund.add(messageContent);
			}
			if(messageContent.getMsgType()==3){
				cancel.add(messageContent);
			}
			if(messageContent.getMsgType()==4){
				badComm.add(messageContent);
			}
			if(messageContent.getMsgType()==5){
				finance.add(messageContent);
			}
			if(messageContent.getMsgType()==6){
				full.add(messageContent);
			}
		}
		Map<String,Object> maps = new HashMap<>();
		maps.put("newOder", newOrder.size());
		maps.put("refund", refund.size());
		maps.put("cancel", cancel.size());
		maps.put("badComm", badComm.size());
		maps.put("finance", finance.size());
		maps.put("full", full.size());
		result.setData(maps);
		result.setMsg("success");
		result.setState(1);
		result.setTotalNum(messageList.size());
		return JSON.toJSONString(result);
	}
	/***
	 * 批量修改消息状态
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/readMsg")
	public String changeStatus(String[] ids){
		HotelResult<String> result = new HotelResult<String>();
		int code = messageContentService.updateByprimarykeysStatus(ids);
		if(code>0){
			result.setState(1);
			result.setMsg("success");
			return JSON.toJSONString(result);
		}
		result.setState(0);
		result.setMsg("error");
		return JSON.toJSONString(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/readMessage")
	public String readMessage(String shopId,Integer type){
		HotelResult<String> result = new HotelResult<String>();
		if(StringUtils.isBlank(shopId)||type==null){
			result.setState(0);
			result.setMsg("error");
			result.setMessage("酒店ID或消息类型不能为空");
			return JSON.toJSONString(result);
		}
		try{
		   messageContentService.updateByType(shopId,type);
	 		result.setState(1);
			result.setMsg("success");
			return JSON.toJSONString(result);
		}catch(Exception e){
			result.setState(0);
			result.setMsg("error");
			return JSON.toJSONString(result);
		}
	}
}
