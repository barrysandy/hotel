package com.zzk.service;

import com.zzk.entity.Order;
import com.zzk.util.HotelResult;
import com.zzk.util.Result;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 订单信息表
 * @author: Kun
 * @date: 2018-03-06 10:45
 */
public interface HotelOrderBaseInfoService {

	
      /**
       * 退款申请
       * @param orderNo
       * @return
       * @author John
       * @date： 2018年3月14日 下午2:43:28
       */
      String refundApply(String orderNo);
      
    /**
     * 支付
     * @param orderNo
     * @param extra
     * @param request
     * @param redirect
     * @param model
     * @return
     * @author John
     * @date： 2018年3月15日 上午11:04:14
     */
      HotelResult<Object> wxpay(String orderNo, String openId,HttpServletRequest request,String channelId);
      /**
       * 支付
       * @param orderNo
       * @param extra
       * @param request
       * @param redirect
       * @param model
       * @return
       * @author John
       * @date： 2018年3月15日 上午11:04:14
       */
        Result<Object> wxpay(String orderNo,HttpServletRequest request,String channelId);
	/**
	 * 支付回调函数
	 * @param request
	 * @param response
	 * @author John
	 * @date： 2018年3月16日 下午12:10:17
	 */
	String payCallBack(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 退款回调函数
	 * @param request
	 * @param response
	 * @author John
	 * @date： 2018年3月16日 下午3:04:36
	 */
	void refundCallBack(HttpServletRequest request, HttpServletResponse response);
	
	HotelResult doRefundOrder(HttpServletRequest request,String goodsorderId, String openId);
	
	HotelResult refundH5BC(Order order, String handleMan, HotelResult result,HttpServletResponse response) throws Exception;
}
