package com.zzk.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzk.entity.OrderBaseInfo;
import com.zzk.util.Result;

public interface PayService {
	/**
	 * 验证签名是否正确	
	 * @param map
	 * @return
	 * @author John
	 * @date： 2018年3月16日 下午10:11:44
	 */
	 public boolean verifySign(Map<String, Object> map);
	 /**
	  * 验证支付回签
	  * @param map
	  * @return
	  * @author John
	  * @date： 2018年3月16日 下午10:12:47
	  */
	 public boolean verifyPayResponse(Map<String, Object> map);
	 /**
	  *
	  * @param request
	  * @param paramArray
	  * @return
	  * @author John
	  * @date： 2018年3月16日 下午10:14:04
	  */
	 public Map<String, Object> request2payResponseMap(HttpServletRequest request, String[] paramArray);
	 /**
	  * 输出
	  * @param response
	  * @param content
	  * @author John
	  * @date： 2018年3月16日 下午10:15:00
	  */
	 void outResult(HttpServletResponse response, String content);
	 /**
	  * 接收支付回调
	  * @param request
	  * @param response
	  * @throws Exception
	  * @author John
	  * @date： 2018年3月16日 下午10:15:48
	  */
	 public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;
	 /**
	  * 支付处理
	  * @param goodsOrder
	  * @param channelId
	  * @param extra
	  * @param openId
	  * @param request
	  * @return
	  * @author John
	  * @date： 2018年3月16日 下午10:16:31
	  */
	 Map doPay(OrderBaseInfo orderBaseInfo, String channelId, String extra, String openId, HttpServletRequest request);
	 /**
	  * 支付
	  * @param goodsOrderId
	  * @param openId
	  * @param request
	  * @return
	  * @author John
	  * @date： 2018年3月16日 下午10:18:24
	  * DSC 暂时注释预留
	  */
	// public String pay(String goodsOrderId, String openId, HttpServletRequest request);
	 /**
	  * 退款
	  * @param request
	  * @param goodsOrderId
	  * @param openId
	  * @return
	  * @author John
	  * @date： 2018年3月16日 下午10:19:00
	  */
	 public Result<Object> doRefundOrder(HttpServletRequest request,OrderBaseInfo orderBaseInfo,BigDecimal refundAmount,String feedback,String remarks);
	 /**
	  * 接收退款回调
	  * @param request
	  * @param response
	  * @throws Exception
	  * @author John
	  * @date： 2018年3月16日 下午10:15:48
	  */
	 public void refundNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 测试 
	 * @return
	 * @author John
	 * @date： 2018年3月19日 上午10:27:26
	 */
	 Map<String,Object> payTest(String orderNo);




}
