package com.zzk.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;

import cn.beecloud.BCPay;
import cn.beecloud.BCUtil;
import cn.beecloud.bean.BCRefund;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzk.util.HotelResult;
import com.zzk.util.Result;
import com.zzk.common.OrderConstants;
import com.zzk.common.PlatConstants;
import com.zzk.dao.HotelGoodsMapper;
import com.zzk.dao.OrderBaseInfoMapper;
import com.zzk.dao.OrderMapper;
import com.zzk.dao.PlatDealRecordMapper;
import com.zzk.dao.RefundInfoMapper;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.PlatDealRecord;
import com.zzk.service.OrderBaseInfoService;
import com.zzk.service.OrderService;
import com.zzk.entity.RefundInfo;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
//import org.slf4j.logger;
//import org.slf4j.loggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zshlpay.common.constant.Constant;
import org.zshlpay.common.constant.PayConstant;
import org.zshlpay.common.util.AmountUtil;
import org.zshlpay.common.util.ClientInfo;
import org.zshlpay.common.util.JsonUtil;
import org.zshlpay.common.util.PayDigestUtil;
import org.zshlpay.common.util.PayUtil;
import org.zshlpay.common.util.PropertiesUtil;
import org.zshlpay.common.util.SecurityHelper;


/**
 * 订单信息表
* @author: Kun
* @date: 2018-03-06 10:45
 */
@Service("HotelOrderBaseInfoService")
public class HotelOrderBaseInfoServiceImpl implements HotelOrderBaseInfoService {
	    /**
	     * 加签key
	     */
	    @Value("${pay.reqKey}")
	    private String reqKey;
	    /**
	     * 验签key
	     */
	    @Value("${pay.resKey}")
	    private String resKey;
	    /**
	     * 支付平台商户ID
	     */
	    @Value("${pay.mchId}")
	    private String mchId;
	    /**
	     * 支付平台==>业务平台响应地址
	     */
	    @Value("${pay.hotel.notifyUrl}")
	    private String notifyUrl;
	    @Value("${refundOrder.hotel.notifyUrl}")
	    private String refundUrl;
	    @Value("${pay.url}")
	    private String localUrl;
	    @Resource
	    private OrderMapper orderMapper;
	    @Resource
	    private PlatDealRecordMapper platDealRecordMapper;
	    @Resource
	    private RefundInfoMapper refundInfoMapper;
	    @Resource
	    private HotelGoodsMapper hotelGoodsMapper;
	    @Resource 
	    private OrderService orderService;
	    @Resource
	    private OrderBaseInfoService orderBaseInfoService;
	    @Resource
	    private OrderBaseInfoMapper orderBaseInfoMapper;
	    @Resource
	    private SellerReceiveConfigService sellerReceiveConfigService;
	    @Resource
	    private HotelService hotelService;

    /**
     * 退款申请
     */
	/*@Override
	public String refundApply(String orderNo) {
		order order = orderMapper.selectByorderNo(orderNo);
		if(order==null){
			return JsonUtils.lineJsonData(0, "error", "订单不存在", null);
		}
		int status =order.getorderState();
		if(status == orderConstact.orderStatusEnum.WAITCONFIRM.getCode()&& order.getPayState()== orderConstact.orderPayStatusEnum.ALREADYPAID.getCode()){
			//待确认退款逻辑
		}
		if(status == orderConstact.orderStatusEnum.WAITCONSUME.getCode()&& order.getPayState()== orderConstact.orderPayStatusEnum.ALREADYPAID.getCode()){
			//待消费逻辑
		}
		return JsonUtils.lineJsonData(0, "error", "退款中", null);
	}*/
	    
	@Override
	public HotelResult doRefundOrder(HttpServletRequest request,String goodsorderId, String openId) {
		HotelResult results= new HotelResult<>();
		
		
            Order goodsOrder = orderService.selectByPrimaryKey(goodsorderId);
            
            if(goodsOrder==null){
            	results.setState(0);
            	results.setMsg("error");
            	results.setMessage("查询失败goodsOrder is null");
            	results.setData(goodsorderId);
                return results;
            }
            /*if(!StringUtils.equals(goodsOrder.getOpenId(),openId)){
                return JsonUtil.toJson(false, "微信ID不一致","openId="+openId+";goodsOpenId="+goodsOrder.getOpenId());
            }*/
            try {
            ClientInfo clientInfo = new ClientInfo(request.getHeader("user-agent"));
            JSONObject paramMap = new JSONObject();
            paramMap.put("mchId", mchId);                               // 商户ID
            paramMap.put("mchRefundNo", goodsOrder.getOrderNum()+"-"+System.currentTimeMillis());     // 商户订单号
            // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
            paramMap.put("channelId", "WX_NATIVE");
            String amount = mul(goodsOrder.getActualMoney().doubleValue(), new Double("100").doubleValue()).longValue()+"";
            paramMap.put("amount", amount);  // 退款金额
            paramMap.put("currency", "cny");                            // 币种, cny-人民币
          //  paramMap.put("clientIp", getIpAddr(request));                 // 用户地址,微信H5支付时要真实的
            paramMap.put("device", clientInfo.getOSName());                              // 设备
            paramMap.put("subject",goodsOrder.getOrderName()+"-退款");
            paramMap.put("body", goodsOrder.getOrderName()+"-退款");
            // 回调URL
            paramMap.put("notifyUrl", refundUrl);
            // 扩展参数1
            paramMap.put("param1", "");
            // 扩展参数2
            paramMap.put("param2", "");
            // 微信openId:oIkQuwhPgPUgl-TvQ48_UUpZUwMs
            paramMap.put("channelUser", goodsOrder.getOpenId());
            paramMap.put("payOrderId", goodsOrder.getPayId());
            String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
            System.out.println(paramMap);
            // 签名
            paramMap.put("sign", reqSign);
            String reqDataencode = "params=" + URLEncoder.encode(paramMap.toJSONString());
            String reqData = "params=" + paramMap.toJSONString();
            System.out.println("请求支付中心退款接口,请求数据:" + reqData);
            String url = localUrl + "/refund/create_order?";
            String result = PayUtil.call4Post(url + reqDataencode);
            System.out.println("请求支付中心退款接口,响应数据:" + result);
            Map retMap = JSON.parseObject(result);
            if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
                // 验签
                System.out.println(retMap);
                String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
                String retSign = (String) retMap.get("sign");
                if(checkSign.equals(retSign)) {
                	goodsOrder.setPayState(PlatConstants.PAY_REFUNDING);
                	orderService.update(goodsOrder);
                	System.out.println("=========支付中心退款验签成功=========");
                    results.setState(1);
                    results.setMsg("success");
                    results.setMessage("支付中心退款验签成功");
                    return results;
                }else {
                	goodsOrder.setPayState(PlatConstants.PAY_REFUND_FALSE);
                	orderService.update(goodsOrder);
                    System.err.println("=========支付中心退款验签失败=========");
                    results.setState(0);
                    results.setMsg("error");
                    results.setMessage("支付中心退款验签失败");
                    return results;
                }
            }else{
            	goodsOrder.setPayState(PlatConstants.PAY_REFUND_FALSE);
            	orderService.update(goodsOrder);
            	results.setState(0);
            	results.setMsg("error");
                results.setMessage("支付中心退款验签失败");
                return results;
            }
        }catch (Exception e){
        	goodsOrder.setPayState(PlatConstants.PAY_REFUND_FALSE);
        	orderService.update(goodsOrder);
        	e.printStackTrace();
        	results.setState(0);
            results.setMsg("error");
            results.setMessage("支付中心退款验签失败"+e.getMessage().toString());
            return results;
        }
    }
	
    
	/**
	 * 支付回调
	 */
	@Override
	public String payCallBack(HttpServletRequest request, HttpServletResponse response) {
		 Map<String, Object> paramMap = request2payResponseMap(request, new String[]{"payOrderId", "mchId", "mchOrderNo", "channelId", "amount", "currency", "status", "clientIp", "device", "subject", "channelOrderNo", "param1", "param2", "paySuccTime", "backType", "sign"});
	        System.out.println("paramMap:"+paramMap);
	        String resStr="";
	        //log.info("支付中心通知请求参数,paramMap={}", paramMap);
	        if (!verifyPayResponse(paramMap)) {
	            String errorMessage = "verify request param failed.";
	            ////log.warn(errorMessage);
	            System.err.println(errorMessage);
	            //outResult(response, "fail");
	            resStr="fail";
	            return resStr;
	        }
	        String payOrderId = (String) paramMap.get("payOrderId");
	        String mchOrderNo = (String) paramMap.get("mchOrderNo");
	        
	        
	        try {
	            OrderBaseInfo orderBaseInfo = orderMapper.selectByPayNum(mchOrderNo);
	            Order order = orderService.orderBaseInfo2Order(orderBaseInfo);
	            if (order != null) {
	                //outResult(response, "success");
	                // 执行业务逻辑
	                System.err.println("bigin"+JSON.toJSON(order));
	                
	                order.setPayId(payOrderId);
	                order.setPayState(OrderConstants.PAY_PAY);
	                order.setPayMode(2);
	                order.setActualMoney(order.getOrderMoney());
	                System.err.println("end"+JSON.toJSON(order));
	                OrderBaseInfo obi= orderService.order2orderBaseInfo(order);
	                HotelGoods hg= hotelGoodsMapper.selectByPrimaryKey(order.getGoodsId());
	                if(hg!=null){
	                	if(hg.getConfirm()==1){
	                		order.setOrderState(OrderConstants.CONFIRM);
		                	sellerReceiveConfigService.sendHotelMessageToBuyer(obi,3);
		                }else{
		                	order.setOrderState(OrderConstants.WAIT_TO_CONFIRM);
		                	sellerReceiveConfigService.sendHotelMessageToBuyer(obi,1);
		                	
		                }
	                }
	                orderService.update(order);
	                insertDealRecord(2,order.getOrderMoney() , order,3);
	                resStr = "success";
	            } else {
	                // 执行业务逻辑
	                resStr = "fail";
	            }
	        } catch (Exception e) {
	            resStr = "fail";
	            System.out.println("执行业务异常,"+payOrderId+","+mchOrderNo);
	        }finally{
	        	//log.info("响应支付中心通知结果:{},payOrderId={},mchOrderNo={}", resStr, payOrderId, mchOrderNo);
	        //outResult(response, resStr);
	        System.out.println("====== 支付中心通知处理完成 ======");
	        return resStr;
	        }
	       
	}
	private int insertDealRecord(int payChannel, BigDecimal actulMoney, Order order,int type) {
	        PlatDealRecord dealRecord = new PlatDealRecord();
	        Date now = new Date();
	        dealRecord.setId(UUID.randomUUID().toString());
	        dealRecord.setCreateDate(now);
	        dealRecord.setOrderId(order.getOrderNum());
	        dealRecord.setPayChanel(payChannel);
	        dealRecord.setActualMoney(actulMoney);
	        dealRecord.setPayPrice(order.getOrderMoney());
	        dealRecord.setSourceId(order.getBuyerId());
	        dealRecord.setDestId(order.getSellId());
	        dealRecord.setPayState(1);
	        dealRecord.setSuccessDate(now);
	        dealRecord.setPayTitle("订单");
	        dealRecord.setStreamType(type);
	        return   platDealRecordMapper.insert(dealRecord);
	    }
	 
	  void outResult(HttpServletResponse response, String content) {
	        response.setContentType("text/html");
	        PrintWriter pw;
	        try {
	            pw = response.getWriter();
	            pw.print(content);
	            //log.error("response zshlpay complete.");
	        } catch (IOException e) {
	            //log.error("response zshlpay write exception.");
	        }
	    }
    public boolean verifyPayResponse(Map<String, Object> map) {
        String mchId = (String) map.get("mchId");
        String payOrderId = (String) map.get("payOrderId");
        String mchOrderNo = (String) map.get("mchOrderNo");
        String amount = (String) map.get("amount");
        String sign = (String) map.get("sign");
        System.err.println("errorr//mchOrderNo:"+mchOrderNo);
        if (StringUtils.isEmpty(mchId)) {
            ////log.warn("Params error. mchId={}", mchId);
        	System.err.println("Params error. mchId={"+mchId+"}");
            return false;
        }
        if (StringUtils.isEmpty(payOrderId)) {
            ////log.warn("Params error. payOrderId={}", payOrderId);
        	System.err.println("Params error. payOrderId={"+payOrderId+"}");
            return false;
        }
        if (StringUtils.isEmpty(amount) || !NumberUtils.isNumber(amount)) {
            ////log.warn("Params error. amount={}", amount);
        	System.err.println("Params error. amount={"+amount+"}");
            return false;
        }
        if (StringUtils.isEmpty(sign)) {
        	System.err.println("Params error. sign={"+sign+"}");
            return false;
        }
        // 验证签名
        if (!verifySign(map)) {
            ////log.warn("verify params sign failed. payOrderId={}", payOrderId);
        	System.err.println("verify params sign failed. payOrderId={"+payOrderId+"}");
            return false;
        }
    	
        // 根据payOrderId查询业务订单,验证订单是否存在
        OrderBaseInfo orderBaseInfo = orderMapper.selectByPayNum(mchOrderNo);
        Order order = orderService.orderBaseInfo2Order(orderBaseInfo);
        System.err.println(JSON.toJSON(order));
        if (order == null) {
            //log.warn("业务订单不存在,payOrderId={},mchOrderNo={}", payOrderId, mchOrderNo);
            System.out.println("业务订单不存在,payOrderId={"+payOrderId+"},mchOrderNo={"+mchOrderNo+"}");
            return false;
        }
        // 核对金额
        if (mul(order.getOrderMoney().doubleValue(), new Double("100").doubleValue()).longValue() != Long.parseLong(amount)) {
            ////log.warn("支付金额不一致,dbPayPrice={},payPrice={}", order.getOrderMoney(), amount);
        	System.out.println("支付金额不一致"+order.getOrderMoney()+","+ amount);
            return false;
        }
        return true;
    }
    public boolean verifySign(Map<String, Object> map) {
        String resMchId = (String) map.get("mchId");
        if (!mchId.equals(resMchId)) {
            return false;
        }
        String localSign = PayDigestUtil.getSign(map, resKey, "sign");
        String sign = (String) map.get("sign");
        return localSign.equalsIgnoreCase(sign);
    }
	   public Map<String, Object> request2payResponseMap(HttpServletRequest request, String[] paramArray) {
	        Map<String, Object> responseMap = new HashMap<>(16);
	        for (int i = 0; i < paramArray.length; i++) {
	            String key = paramArray[i];
	            String v = request.getParameter(key);
	            if (v != null) {
	                responseMap.put(key, v);
	            }
	        }
	        return responseMap;
	    }

	@Override
	public HotelResult<Object> wxpay(String orderNo, String openId,HttpServletRequest request,String channelId) {
		  Order order = orderService.selectByOrderNum(orderNo);
		  OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
	        if (order == null) {
	            return new HotelResult(0, "error", "下单失败","订单不存在");
	        }
	        int status = order.getOrderState();
	        if (status != 101) {
	            return new HotelResult(0, "error", "下单失败","订单状态错误");
	        }
	        //微信js支付
	        //String channelId = "WX_JSAPI";
	        //自定义回调参数--特定渠道发起时额外参数
	        String extra = "{}";
	        Map resultMap = doPay(order, channelId, extra, openId, request);
	        if (resultMap == null) {
	        	return new HotelResult<Object>(0, "error", "支付中心下单验签失败", null);
	        }
	        if (PayConstant.PAY_CHANNEL_ALIPAY_MOBILE.equalsIgnoreCase(channelId)) {
	        	return new HotelResult(1, "success", "", resultMap.get("payParams").toString());
	        	//	            return retMap.get("payParams").toString();
	        }
	        if (PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId)) {
//	          
//	            //当时就是因为ionic签单url链接中
//	            json.put("payUrl",payUrl);
	        	JSONObject json = (JSONObject) resultMap.get("payParams");
//	        	JSONObject json =JSONObject.parseObject(jsonSr);
	        	Map<String,Object> resultMaps = new HashMap<String, Object>();
	        	resultMaps.put("jsapiAppid", json.get("appId"));
	        	resultMaps.put("timeStamp", json.get("timeStamp"));
	        	resultMaps.put("nonceStr", json.get("nonceStr"));
	        	resultMaps.put("jsapipackage", json.get("package"));
	        	resultMaps.put("signType", json.get("signType"));
	        	resultMaps.put("paySign", json.get("paySign"));
	        	resultMaps.put("payParams", resultMap.get("payParams"));
	            return new HotelResult<Object>(1, "success", "success", resultMaps);
	        }
	        if (PayConstant.PAY_CHANNEL_WX_MWEB.equalsIgnoreCase(channelId)) {
	        	String payOrderId = resultMap.get("payOrderId").toString();
	        	orderBaseInfo.setPayOrderId(payOrderId);
	        	orderBaseInfo.setPayMode(1);
	        	int resultCode = orderBaseInfoService.update(orderBaseInfo);
	        	if (resultCode > 0) {
	        		Map<String,Object> resultMaps = new HashMap<String, Object>();
	        		resultMaps.put("redirectUrl", resultMap.get("payUrl"));
	        		return new HotelResult<Object>(1, "success", "h5支付中心下单验签成功", resultMaps);
	        	}
	        	return new HotelResult<Object>(0, "error", "存储支付信息失败",null);
	        }
	        return new HotelResult<Object>(0, "error", "支付中心下单验签失败", null);
//	        return retMap.get("payUrl").toString();
	}
	
	@Override
	public Result<Object> wxpay(String orderNo,HttpServletRequest request,String channelId) {
			Order order = orderService.selectByOrderNum(orderNo);
			OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
			if (order == null) {
	            return new Result(0, "error", "下单失败","订单不存在");
	        }
			String buyerId = order.getBuyerId();
			System.out.println(buyerId);
	        String openId = hotelService.selectOp(order.getBuyerId());
	        if(StringUtils.isBlank(openId)&&channelId.equalsIgnoreCase("WX_JSAPI")){
	        	System.out.println("=======openId为空=====");
	            return new Result<>(0, "error", "openId为空");
	        }
	        int status = order.getOrderState();
	        if (status != 101) {
	            return new Result(0, "error", "下单失败","订单状态错误");
	        }
	        //微信js支付
	        //String channelId = "WX_JSAPI";
	        //自定义回调参数--特定渠道发起时额外参数
	        String extra = "{}";
	        Map resultMap = doPay(order, channelId, extra, openId, request);
	        if (resultMap == null) {
	        	return new Result<Object>(0, "error", "支付中心下单验签失败", null);
	        }
	        if (PayConstant.PAY_CHANNEL_ALIPAY_MOBILE.equalsIgnoreCase(channelId)) {
	        	return new Result(1, "success", "", resultMap.get("payParams").toString());
	        	//	            return retMap.get("payParams").toString();
	        }
	        if (PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId)) {
//	          
//	            //当时就是因为ionic签单url链接中
//	            json.put("payUrl",payUrl);
	        	JSONObject json = (JSONObject) resultMap.get("payParams");
//	        	JSONObject json =JSONObject.parseObject(jsonSr);
	        	return new Result<Object>(1, "success", "success",  resultMap.get("payParams"));
	        }
	        if (PayConstant.PAY_CHANNEL_WX_MWEB.equalsIgnoreCase(channelId)) {
	        	String payOrderId = resultMap.get("payOrderId").toString();
	        	orderBaseInfo.setPayOrderId(payOrderId);
	        	orderBaseInfo.setPayMode(1);
	        	int resultCode = orderBaseInfoService.update(orderBaseInfo);
	        	if (resultCode > 0) {
	        		Map<String,Object> resultMaps = new HashMap<String, Object>();
	        		resultMaps.put("redirectUrl", resultMap.get("payUrl"));
	        		return new Result<Object>(1, "success", "h5支付中心下单验签成功", resultMaps);
	        	}
	        	return new Result<Object>(0, "error", "存储支付信息失败",null);
	        }
	        return new Result<Object>(0, "error", "支付中心下单验签失败", null);
    }
	
	
	/**
     * 支付处理
     *
     * @param goodsorder
     * @param channelId
     * @param extra
     * @param openId
     * @param request
     * @return
     */
    Map doPay(Order goodsOrder, String channelId, String extra, String openId, HttpServletRequest request) {
        try {
            ClientInfo clientInfo = new ClientInfo(request.getHeader("user-agent"));
            JSONObject paramMap = new JSONObject();
            // 商户ID
            paramMap.put("mchId", mchId);
            // 商户订单号
            String payNum=goodsOrder.getOrderNum()+"/"+System.currentTimeMillis();
            paramMap.put("mchOrderNo", payNum);
            System.out.println(payNum);
            goodsOrder.setPayNum(payNum);
            goodsOrder.setOpenId(openId);
            orderService.update(goodsOrder);
            // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
            paramMap.put("channelId", channelId);
            // 支付金额,单位分
            long amount = mul(goodsOrder.getOrderMoney().doubleValue(), new Double("100").doubleValue()).longValue();
            paramMap.put("amount",amount);
            // 币种, cny-人民币
            paramMap.put("currency", "cny");
            //Ip用户地址,IP或手机号
            String ip = request.getRemoteAddr();
             ip= ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
            paramMap.put("clientIp", ip);
            // 设备--操作系统
            paramMap.put("device", clientInfo.getOSName());
            paramMap.put("subject", "subject");
            paramMap.put("body", "body");
            // 回调URL
            paramMap.put("notifyUrl", notifyUrl);
            // 扩展参数1
            paramMap.put("param1", "");
            // 扩展参数2
            paramMap.put("param2", "");
            if (extra == null) {
                extra = "{}";
            }
            JSONObject extraJson = JSON.parseObject(extra);
//            extraJson.put("productId", goodsorder.getId());
            extraJson.put("openId", openId);
            // 附加参数
            if(StringUtils.equals("WX_MWEB",channelId)){
                JSONObject sceneInfo = new JSONObject();
                JSONObject h5_info = new JSONObject();
                h5_info.put("type","Wap");
                h5_info.put("wap_url","http://www.lishiqiang.cn/");
                h5_info.put("wap_name","支付");
                sceneInfo.put("h5_info",h5_info);
                extraJson.put("sceneInfo",sceneInfo);
            }
            
            paramMap.put("extra", extraJson.toJSONString());
            String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
            // 签名
            paramMap.put("sign", reqSign);
            String reqDataEncode = "params=" + URLEncoder.encode(paramMap.toJSONString(), "UTF-8");
            String reqData = "params=" + paramMap.toJSONString();
            	//log.info("请求支付中心下单接口,请求数据:" + reqData);
            String url = localUrl + "/pay/create_order?";
            String result = PayUtil.call4Post(url + reqDataEncode);
            	//log.info("请求支付中心下单接口,响应数据:{}" + result);
            Map retMap = JSON.parseObject(result);
            if (StringUtils.equals(Constant.SUCCESS, MapUtils.getString(retMap, "retCode"))) {
                // 验签
                String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
                String retSign = (String) retMap.get("sign");
                if (checkSign.equals(retSign)) {
                    //log.info("=========支付中心下单验签成功=========");
                } else {
                    //log.info("=========支付中心下单验签失败=========");
                    return null;
                }
            }
//            String payOrderId = retMap.get("payOrderId").toString();
//            TicketorderVO ticketorderVO = new TicketorderVO();
//            ticketorderVO.setDocSn(goodsorder.getDocSn());
//            ticketorderVO.setPayDocSn(payOrderId);
//            ticketorderService.update(ticketorderVO);
              //log.info("修改商品订单,返回:{}");
            return retMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private  BigDecimal mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }
    
	@Override
	public void refundCallBack(HttpServletRequest request, HttpServletResponse response) {
		//退款回调
		try {
			Map<String, Object> paramMap = request2payResponseMap(request, new String[]{"refundOrderId", "mchId", "mchOrderNo", "channelId", "refundAmount", "currency", "status","result", "clientIp", "device", "channelOrderNo", "param1", "param2", "refundSuccTime", "backType", "sign"});
			String payChannel=  (String)paramMap.get("channelId");
		    String payTime = (String) paramMap.get("refundSuccTime");
		    String amount = (String) paramMap.get("refundAmount");
		    int refundStatus = Integer.parseInt((String)paramMap.get("status"));
		    BigDecimal refundMoney = new BigDecimal(AmountUtil.convertCent2DollarShort(amount));
		        int payMode = 0;
		        if("JSAPI".equalsIgnoreCase(payChannel)){
		        	payMode = 2;
		        }
		        System.out.println("paramMap:"+paramMap);
		        if (!verifyRefundResponse(paramMap)) {
		            String errorMessage = "verify request param failed.";
		            //outResult(response, "fail");
		            return;
		        }
		        String mchOrderNo = (String) paramMap.get("mchOrderNo");
		        mchOrderNo = mchOrderNo.split("-")[0];
		        String resStr;
		       Order order = null;
		        try {
		            order = orderService.selectByOrderNum(mchOrderNo);
		            if (order != null) {
		            	if(refundStatus!=3){
		            		if(order.getPayState() != PlatConstants.PAY_REFUND_SUCCESS){
		            			//order.setOrderState(OrderConstants.CANCEL_BY_SELLER);
		            			order.setPayState(PlatConstants.PAY_REFUND_SUCCESS);
				                order.setUpdateTime(new Date());
				                orderService.update(order);
				                insertRefundInfo(paramMap);
		            		}
			            }else{
			            	//order.setOrderState(OrderConstants.CANCEL_BY_SELLER);
			                order.setPayState(OrderConstants.PAY_REFUND_FAIL);
			                order.setUpdateTime(new Date());
			                orderService.update(order);
			                insertRefundInfo(paramMap);
		            	}
		                resStr = "success";
		                System.out.println("执行回调结果====="+paramMap);
		            } else {
		            	 RefundInfo refundInfo = new RefundInfo();
			                refundInfo.setId(UUID.randomUUID().toString());
			                refundInfo.setCreateTime(new Date());
			                refundInfo.setMchRefundNo(mchOrderNo);
			                refundInfo.setUpdateTime(new Date());
			                refundInfo.setStatus(1);
			                refundInfo.setRefundSuccessTime(new Date());
			                refundInfo.setRefundAmount(refundMoney);
			                refundInfo.setPayMode(1);
			                refundInfo.setRefundStatus(refundStatus);
			                refundInfo.setRemark("该订单不存在或者其他错误");
			                refundInfoMapper.insert(refundInfo);
		                resStr = "success";
		            }
		        } catch (Exception e) {
		            resStr = "fail";
		        }
		        insertDealRecord(payMode, refundMoney, order, 6);
		        //outResult(response, resStr);
		} catch (Exception e) {
			System.out.println(e.getMessage()+"支付回调异常");
			e.printStackTrace();
		}
	}
	
	private boolean verifyRefundResponse(Map<String, Object> map) {
		String mchId = (String) map.get("mchId");
	 	String refundOrderId = (String) map.get("refundOrderId");
        String mchOrderNo = (String) map.get("mchOrderNo");
        String[] str = mchOrderNo.split("-");
        mchOrderNo = str[0];
        String amount = (String) map.get("refundAmount");
        String sign = (String) map.get("sign");

        if (StringUtils.isEmpty(mchId)) {
            return false;
        }
        if (StringUtils.isEmpty(refundOrderId)) {
            return false;
        }
        if (StringUtils.isEmpty(amount) || !NumberUtils.isNumber(amount)) {
            return false;
        }
        if (StringUtils.isEmpty(sign)) {
            return false;
        }
        // 验证签名
        if (!verifySign(map)) {
            return false;
        }

        // 根据payOrderId查询业务订单,验证订单是否存在
        Order orderBaseInfo = orderService.selectByOrderNum(mchOrderNo);
        if (orderBaseInfo == null) {
            System.out.println("业务订单不存在,mchOrderNo={"+mchOrderNo+"}");
            return false;
        }
        // 核对金额
        long orderMoney = mul(orderBaseInfo.getOrderMoney().doubleValue(), new Double("100").doubleValue()).longValue();
        if (orderMoney < Long.parseLong(amount)) {
            System.out.println("支付金额不一致,dbPayPrice={"+orderMoney+"},payPrice={"+amount+"}");
            return false;
        }
        return true;
}

	@Override
	public String refundApply(String orderNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void insertRefundInfo(Map paramMap){
		String payChannel=  (String)paramMap.get("channelId");
	    String payTime = (String) paramMap.get("refundSuccTime");
	    String amount = (String) paramMap.get("refundAmount");
	    int refundStatus = Integer.parseInt((String)paramMap.get("status"));
	    String mchOrderNo = (String) paramMap.get("mchOrderNo");
        mchOrderNo = mchOrderNo.split("-")[0];
	    BigDecimal refundMoney = new BigDecimal(AmountUtil.convertCent2DollarShort(amount));
		 RefundInfo refundInfo = refundInfoMapper.selectByOrderNo(mchOrderNo);
         if(refundInfo == null){
         	refundInfo = new RefundInfo();
         	refundInfo.setId(UUID.randomUUID().toString());
         	refundInfo.setCreateTime(new Date());
         	refundInfo.setMchRefundNo(mchOrderNo);
         	refundInfo.setUpdateTime(new Date());
         	refundInfo.setStatus(1);
         	refundInfo.setRefundSuccessTime(new Date());
         	refundInfo.setRefundAmount(refundMoney);
         	refundInfo.setPayMode(1);
         	refundInfo.setRefundData(JSON.toJSONString(paramMap));
         	refundInfo.setRefundStatus(refundStatus);
         	refundInfo.setRemark("退款状态异常");     
         	refundInfoMapper.insert(refundInfo);
         }else{
         	refundInfo.setRefundSuccessTime(new Date());
         	refundInfo.setRefundAmount(refundMoney);
         	refundInfo.setPayMode(1);
         	refundInfo.setRefundStatus(refundStatus);
         	refundInfo.setRemark("退款状态异常"); 
         	refundInfo.setRefundData(JSON.toJSONString(paramMap));
         	refundInfoMapper.updateByPrimaryKeySelective(refundInfo);
         }
	}
	@Override
	public HotelResult refundH5BC(Order order, String handleMan,HotelResult result,HttpServletResponse response) throws Exception {
		if(order == null){
			result.setState(0);
			result.setMsg("error");
			result.setMessage("订单不存在");
			return result;
		}
		
		String orderNum = order.getOrderNum();
		String refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date())
	            + BCUtil.generateNumberWith3to24digitals();
		 BCRefund param = new BCRefund();
		 BigDecimal orderMoney= order.getActualMoney();
		 BigDecimal base  = new BigDecimal(100);
		 Map<String,Object> optional = new HashMap<>();
		 optional.put("orderNum", orderNum);
		 optional.put("refundNo", refundNo);
		 int refundFee = 0;
		 if(orderMoney != null){
		 refundFee =  orderMoney.multiply(base).intValue();
		 }
		 param.setBillNo(orderNum);
		 param.setRefundNo(refundNo);
		 param.setRefundFee(refundFee);
		 param.setTitle("退款");
		 param.setOptional(optional);
		 //   param.setOptional(optional);//optional 可选业务参数
		 
		   
        BCRefund refund = BCPay.startBCRefund(param);
        if(refund.getAliRefundUrl() != null){
        	response.sendRedirect(refund.getAliRefundUrl());		        	
        }else{
        	order.setPayState(PlatConstants.PAY_REFUNDING);
        	order.setHandleMan(handleMan);
        	order.setUpdateTime(new Date());
        	order.setOrderState(OrderConstants.CANCEL_BY_SELLER);
        	orderService.update(order);
        	result.setMessage("退款成功");
        	return result;
        }
		result.setState(0);
        result.setMsg("error");
        result.setMessage("退款失败");
        return result;
	}
}
