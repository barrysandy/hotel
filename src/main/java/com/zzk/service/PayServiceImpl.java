package com.zzk.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zshlpay.common.constant.Constant;
import org.zshlpay.common.util.AmountUtil;
import org.zshlpay.common.util.ClientInfo;
import org.zshlpay.common.util.PayDigestUtil;
import org.zshlpay.common.util.PayUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzk.common.OrderConstact;
import com.zzk.dao.OrderBaseInfoMapper;
import com.zzk.dao.OrderDetailInfoMapper;
import com.zzk.dao.PlatDealRecordMapper;
import com.zzk.dao.ProductBaseInfoMapper;
import com.zzk.dao.ProductStockInfoMapper;
import com.zzk.dao.RefundInfoMapper;
import com.zzk.dao.UserMapper;
import com.zzk.entity.OrderBaseInfo;

import com.zzk.entity.OrderDelay;

import com.zzk.entity.OrderDetailInfo;

import com.zzk.entity.PlatDealRecord;
import com.zzk.entity.ProductBaseInfo;
import com.zzk.entity.ProductStockInfo;
import com.zzk.entity.RefundInfo;
import com.zzk.util.DateUtils;
import com.zzk.util.Result;
@Service("payServiceImpl")
public class PayServiceImpl implements PayService {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private OrderBaseInfoMapper orderBaseInfoMapper;
	@Resource
	private PlatDealRecordMapper platDealRecordMapper;
	@Resource  
	private RefundInfoMapper refundInfoMapper;
	@Resource
	private OrderStatusInfoService orderStatusInfoService;
    @Resource
    private ProductStockInfoMapper productStockInfoMapper;
	@Resource
	private DelayService delayService;

	@Resource
	private UserMapper userMapper;
	@Resource
	private OrderDetailInfoMapper orderDetailInfoMapper;
	@Resource
	private ProductBaseInfoMapper productBaseInfoMapper;
	@Resource
	private SellerReceiveConfigService sellerReceiveConfigService;

    /**
     * 微信appId
     */
    @Value("${wx.appId}")
    private String appId;
    /**
     * 微信密锁
     */
    @Value("${wx.appSecret}")
    private String appSecret;
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
    @Value("${pay.notifyUrl}")
    private String notifyUrl;
    /**
     * 支付基础URL
     */
    @Value("${pay.url}")
    private String baseUrl;
    /**
     * 退款回调URL
     */
    @Value("${refundOrder.notifyUrl}")
    private String refundOrderNotifyUrl;
	@Override
	public boolean verifySign(Map<String, Object> map) {
	        String resMchId = (String) map.get("mchId");
	        if (!mchId.equals(resMchId)) {
	            return false;
	        }
	        String localSign = PayDigestUtil.getSign(map, resKey, "sign");
	        String sign = (String) map.get("sign");
	        return localSign.equalsIgnoreCase(sign);
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
	            log.warn("Params error. mchId={}", mchId);
	            return false;
	        }
	        if (StringUtils.isEmpty(refundOrderId)) {
	            log.warn("Params error. payOrderId={}", refundOrderId);
	            return false;
	        }
	        if (StringUtils.isEmpty(amount) || !NumberUtils.isNumber(amount)) {
	            log.warn("Params error. amount={}", amount);
	            return false;
	        }
	        if (StringUtils.isEmpty(sign)) {
	            log.warn("Params error. sign={}", sign);
	            return false;
	        }
	        // 验证签名
	        if (!verifySign(map)) {
	            log.warn("verify params sign failed. refundOrderId={}", refundOrderId);
	            return false;
	        }

	        // 根据payOrderId查询业务订单,验证订单是否存在
	        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(mchOrderNo);
	        if (orderBaseInfo == null) {
	            log.warn("业务订单不存在,mchOrderNo={}", mchOrderNo);
	            System.out.println("业务订单不存在,mchOrderNo={"+mchOrderNo+"}");
	            return false;
	        }
	        // 核对金额
	        long orderMoney = mul(orderBaseInfo.getOrderMoney().doubleValue(), new Double("100").doubleValue()).longValue();
	        if (orderMoney < Long.parseLong(amount)) {
	             log.warn("退款金额大于订单金额,dbPayPrice={},payPrice={}", orderMoney, amount);
	            System.out.println("支付金额不一致,dbPayPrice={"+orderMoney+"},payPrice={"+amount+"}");
	            return false;
	        }
	        return true;
	}
	@Override
	public boolean verifyPayResponse(Map<String, Object> map) {
		 String mchId = (String) map.get("mchId");
	        String payOrderId = (String) map.get("payOrderId");
	        String mchOrderNo = (String) map.get("mchOrderNo");
	        String[] str = mchOrderNo.split("-");
	        mchOrderNo = str[0];
	        String amount = (String) map.get("amount");
	        String sign = (String) map.get("sign");

	        if (StringUtils.isEmpty(mchId)) {
	            log.warn("Params error. mchId={}", mchId);
	            return false;
	        }
	        if (StringUtils.isEmpty(payOrderId)) {
	            log.warn("Params error. payOrderId={}", payOrderId);
	            return false;
	        }
	        if (StringUtils.isEmpty(amount) || !NumberUtils.isNumber(amount)) {
	            log.warn("Params error. amount={}", amount);
	            return false;
	        }
	        if (StringUtils.isEmpty(sign)) {
	            log.warn("Params error. sign={}", sign);
	            return false;
	        }
	        // 验证签名
	        if (!verifySign(map)) {
	            log.warn("verify params sign failed. payOrderId={}", payOrderId);
	            return false;
	        }

	        // 根据payOrderId查询业务订单,验证订单是否存在
	        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(mchOrderNo);
	        if (orderBaseInfo == null) {
	            log.warn("业务订单不存在,payOrderId={},mchOrderNo={}", payOrderId, mchOrderNo);
	            System.out.println("业务订单不存在,payOrderId={"+payOrderId+"},mchOrderNo={"+mchOrderNo+"}");
	            return false;
	        }
	        // 核对金额
	        long orderMoney = mul(orderBaseInfo.getOrderMoney().doubleValue(), new Double("100").doubleValue()).longValue();
	        if (orderMoney != Long.parseLong(amount)) {
	             log.warn("支付金额不一致,dbPayPrice={},payPrice={}", orderMoney, amount);
	            System.out.println("支付金额不一致,dbPayPrice={"+orderMoney+"},payPrice={"+amount+"}");
	            return false;
	        }
	        return true;
	}

	@Override
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
	public void outResult(HttpServletResponse response, String content) {
	     response.setContentType("text/html");
	        PrintWriter pw;
	        try {
	            pw = response.getWriter();
	            pw.print(content);
	            log.error("response zshlpay complete.");
	        } catch (IOException e) {
	            log.error("response zshlpay write exception.");
	        }

	}

	@Override
	public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 log.info("====== 开始处理支付中心通知 ======");
	        Map<String, Object> paramMap = request2payResponseMap(request, new String[]{"payOrderId", "mchId", "mchOrderNo", "channelId", "amount", "currency", "status", "clientIp", "device", "subject", "channelOrderNo", "param1", "param2", "paySuccTime", "backType", "sign"});
	        String payChannel=  (String)paramMap.get("channelId");
	        String payTime = (String) paramMap.get("paySuccTime");
	        String amount = (String) paramMap.get("amount");
	        String status = (String) paramMap.get("status");
	        log.info("amount======"+amount+"util="+AmountUtil.convertCent2DollarShort(amount));
	        BigDecimal orderMoney = new BigDecimal(AmountUtil.convertCent2DollarShort(amount));
	        int payMode = 0;
	        if("JSAPI".equalsIgnoreCase(payChannel)){
	        	payMode = 2;
	        }
	        System.out.println("paramMap:"+paramMap);
	        log.info("支付中心通知请求参数,paramMap={}", paramMap);
	        if (!verifyPayResponse(paramMap)) {
	            String errorMessage = "verify request param failed.";
	            log.warn(errorMessage);
	            outResult(response, "fail");
	            return;
	        }
	        String payOrderId = (String) paramMap.get("payOrderId");
	        String mchOrderNo = (String) paramMap.get("mchOrderNo");
	        String[] str = mchOrderNo.split("-");
	        mchOrderNo = str[0];
	       String resStr;
	       OrderBaseInfo orderBaseInfo = null;
	        try {
	            orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(mchOrderNo);
	            if (orderBaseInfo != null &&  orderBaseInfo.getOrderMoney().equals(orderMoney)) {
	            //    outResult(response, "success");
	                // 执行业务逻辑
	            	if(!"3".equals(status)){
	            		if(orderBaseInfo.getPayState() != OrderConstact.OrderPayStatusEnum.ALREADYPAID.getCode()){
	            			paySuccessStock(mchOrderNo);
	            			orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.WAITCONFIRM.getCode());
			                orderBaseInfo.setPayState(OrderConstact.OrderPayStatusEnum.ALREADYPAID.getCode());
			                orderBaseInfo.setPayOrderId(payOrderId);
			                orderBaseInfo.setPayTime(DateUtils.longToDate(Long.parseLong(payTime)));
			                orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
			                orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
			                insertProductPurchaseNumber(orderBaseInfo);
			                sellerReceiveConfigService.sendMessageToBuyer(orderBaseInfo, 1);
			                sellerReceiveConfigService.sendMessageToSeller(orderBaseInfo.getSellerId(), 1);
			                sellerReceiveConfigService.sendMessageToAgent(1, orderBaseInfo, null);
			                orderStatusInfoService.changeStatus(orderBaseInfo.getOrderNo(), orderBaseInfo.getOrderState());
	            		}
		                resStr = "success";
	            	}else{
	            		resStr="fail";
	            	}
	                System.out.println("执行支付回调结果====="+paramMap);
	            } else {
	                // 执行业务逻辑
//	                goodsOrder.setPayDocSn(payOrderId);
//	                goodsOrder.setDocSn(mchOrderNo);
//	                goodsOrder.setPayStatus(2);
//	                ticketOrderService.update(goodsOrder);
	                resStr = "fail";
	            }
	        } catch (Exception e) {
	            resStr = "fail";
	            log.error("执行业务异常,payOrderId=%s.mchOrderNo=%s", payOrderId, mchOrderNo);
	        }
	        insertDealRecord(payMode, orderMoney, orderBaseInfo, 3);
	        log.info("响应支付中心通知结果:{},payOrderId={},mchOrderNo={}", resStr, payOrderId, mchOrderNo);
	        outResult(response, resStr);
	        final String no = mchOrderNo;
	        //从未支付队列中删除
	       
	            	long num = Long.valueOf(no);
	            	delayService.remove(num);
	            	boolean b=delayService.removePrepare(num);
	        
	        log.info("====== 支付中心通知处理完成 ======");

	}
    private void paySuccessStock(String orderNo){
    	OrderDetailInfo orderDetailInfo = orderDetailInfoMapper.selectByOrderNo(orderNo);
		ProductStockInfo productStockInfo = productStockInfoMapper.selectByPrimaryKey(orderDetailInfo.getProductStockId());		
		productStockInfo.setAdultSaledNumber(productStockInfo.getAdultSaledNumber()+orderDetailInfo.getAdultNumber());
		productStockInfo.setChildSaledNumber(productStockInfo.getChildSaledNumber()+orderDetailInfo.getChildNumber());
		if(productStockInfo.getAdultPreemptedNumber()-orderDetailInfo.getAdultNumber() >= 0){
			productStockInfo.setAdultPreemptedNumber(productStockInfo.getAdultPreemptedNumber()-orderDetailInfo.getAdultNumber());		
		}
		if(productStockInfo.getChildPreemptedNumber()-orderDetailInfo.getChildNumber() >= 0){
			productStockInfo.setChildPreemptedNumber(productStockInfo.getChildPreemptedNumber()-orderDetailInfo.getChildNumber());
		}
		productStockInfo.setUpdateTime(DateUtils.getCurrentDate());
		productStockInfoMapper.updateByPrimaryKeySelective(productStockInfo);		
    }	
	private void insertProductPurchaseNumber(OrderBaseInfo orderBaseInfo){
		OrderDetailInfo detail = orderDetailInfoMapper.selectByOrderNo(orderBaseInfo.getOrderNo());
		ProductBaseInfo productBaseInfo = productBaseInfoMapper.selectByPrimaryKey(detail.getProductId());
		productBaseInfo.setPurchasesNumber(productBaseInfo.getPurchasesNumber()+1);
		productBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
		productBaseInfoMapper.updateByPrimaryKeySelective(productBaseInfo);
	}
	
	@Override
	public Map doPay(OrderBaseInfo orderBaseInfo, String channelId, String extra, String openId,
			HttpServletRequest request) {
			String productName = "智胜西行-订单号:"+orderBaseInfo.getOrderNo();
			String productTitle = "daxi51.com";

		 try {
			 	String currentTime = System.currentTimeMillis()+"";
	            ClientInfo clientInfo = new ClientInfo(request.getHeader("user-agent"));
	            JSONObject paramMap = new JSONObject();
	            // 商户ID
	            paramMap.put("mchId", mchId);
	            // 商户订单号
	            paramMap.put("mchOrderNo", orderBaseInfo.getOrderNo()+"-"+currentTime);
	            // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
	            paramMap.put("channelId", channelId);
	            // 支付金额,单位分
	            String orderMoney = mul(orderBaseInfo.getOrderMoney().doubleValue(), new Double("100").doubleValue()).longValue()+"";
	            paramMap.put("amount", orderMoney);
	            // 币种, cny-人民币
	            paramMap.put("currency", "cny");  
	            //用户地址,IP或手机号
	            String ip = request.getRemoteAddr();
	            ip= ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	            paramMap.put("clientIp", ip);
	            // 设备--操作系统
	            paramMap.put("device", clientInfo.getOSName());
	            paramMap.put("subject", productName);
	            paramMap.put("body", productTitle);
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
	          //  extraJson.put("productId", goodsOrder.getProductId());
	              extraJson.put("openId", openId);
	            // 附加参数
	           if(StringUtils.equals("WX_MWEB",channelId)){
	                  JSONObject sceneInfo = new JSONObject();
	                  JSONObject h5_info = new JSONObject();
	                  h5_info.put("type","Wap");
	                  h5_info.put("wap_url","www.daxi51.com");
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
	            log.info("请求支付中心下单接口,请求数据:" + reqData);
	            String url = baseUrl + "/pay/create_order?";
	            String result = PayUtil.call4Post(url + reqDataEncode);
	            log.info("请求支付中心下单接口,响应数据:{}" + result);
	            Map retMap = JSON.parseObject(result);
	            if (StringUtils.equals(Constant.SUCCESS, MapUtils.getString(retMap, "retCode"))) {
	                // 验签
	                String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
	                String retSign = (String) retMap.get("sign");
	                if (checkSign.equals(retSign)) {
	                	OrderBaseInfo baseInfo = orderBaseInfoMapper.selectByOrderNo(orderBaseInfo.getOrderNo());
	                	baseInfo.setCurrentTimeLong(currentTime);
	                	orderBaseInfoMapper.updateByPrimaryKeySelective(baseInfo);
	                    log.info("=========支付中心下单验签成功=========");
	                    return retMap;
	                } else {
	                    log.info("=========支付中心下单验签失败=========");
	                    return null;
	                }
	            }
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	}

	@Override
	public Result<Object> doRefundOrder(HttpServletRequest request, OrderBaseInfo orderBaseInfo,BigDecimal refundAmount,String feedback,String remarks) {
		try {
			String openId = orderBaseInfo.getOpenid();
			if(openId == null){
				openId = "";
			}
            ClientInfo clientInfo = new ClientInfo(request.getHeader("user-agent"));
            JSONObject paramMap = new JSONObject();
            paramMap.put("mchId", mchId);                               // 商户ID
            paramMap.put("mchRefundNo", orderBaseInfo.getOrderNo()+"-"+orderBaseInfo.getCurrentTimeLong());     // 商户订单号
            // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
            paramMap.put("channelId", "WX_MWEB");
            String refundMoney = mul(refundAmount.doubleValue(), new Double("100").doubleValue()).longValue()+"";
            paramMap.put("amount", refundMoney);  // 退款金额
            paramMap.put("currency", "cny");                            // 币种, cny-人民币
            String ip = request.getRemoteAddr();
            ip= ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
            paramMap.put("clientIp", ip);                 // 用户地址,微信H5支付时要真实的
            paramMap.put("device", clientInfo.getOSName());                              // 设备
            paramMap.put("subject",orderBaseInfo.getSellerName()+"-退款");
            paramMap.put("body", orderBaseInfo.getSellerName()+"-退款");
            // 回调URL
            paramMap.put("notifyUrl", refundOrderNotifyUrl);
            // 扩展参数1
            paramMap.put("param1", feedback);
            // 扩展参数2
            paramMap.put("param2", remarks);
            // 微信openId:oIkQuwhPgPUgl-TvQ48_UUpZUwMs
            paramMap.put("channelUser", openId);
            paramMap.put("payOrderId", orderBaseInfo.getPayOrderId());
            String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
            System.out.println(paramMap);
            // 签名
            paramMap.put("sign", reqSign);
            String reqDataencode = "params=" + URLEncoder.encode(paramMap.toJSONString());
            String reqData = "params=" + paramMap.toJSONString();
            System.out.println("请求支付中心退款接口,请求数据:" + reqData);
          //String url = payUrl + "/refund/create_order?";
            String url = baseUrl + "/refund/create_order?";
            String result = PayUtil.call4Post(url + reqDataencode);
            System.out.println("请求支付中心退款接口,响应数据:" + result);
            Map retMap = JSON.parseObject(result);
            if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
                // 验签
                System.out.println(retMap);
                String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
                String retSign = (String) retMap.get("sign");
                if(checkSign.equals(retSign)) {
                    return new Result<>(1, "success", "支付中心退款验签成功");
                }else {
                    System.err.println("=========支付中心退款验签失败=========");
                    return new Result<>(0, "error", "支付中心退款验签失败");
                }
            }else{
                return new Result(0, "error", "支付中心退款失败",retMap);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(0, "error","退款失败"+e.getMessage());
        }
	}

	@Override
	public void refundNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> paramMap = request2payResponseMap(request, new String[]{"refundOrderId", "mchId", "mchOrderNo", "channelId", "refundAmount", "currency", "status","result", "clientIp", "device", "channelOrderNo", "param1", "param2", "refundSuccTime", "backType", "sign"});
		String payChannel=  (String)paramMap.get("channelId");
	    String payTime = (String) paramMap.get("refundSuccTime");
	    String amount = (String) paramMap.get("refundAmount");
	    int refundStatus = Integer.parseInt((String)paramMap.get("status"));
	    String feedback = (String)paramMap.get("param1");
	    String remarks = (String)paramMap.get("param2");
	    BigDecimal refundMoney = new BigDecimal(AmountUtil.convertCent2DollarShort(amount));
	        int payMode = 0;
	        if("JSAPI".equalsIgnoreCase(payChannel)){
	        	payMode = 2;
	        }
	        System.out.println("paramMap:"+paramMap);
	        log.info("退款中心通知请求参数,paramMap={}", paramMap);
	        if (!verifyRefundResponse(paramMap)) {
	            String errorMessage = "verify request param failed.";
	            log.warn(errorMessage);
	            outResult(response, "fail");
	            return;
	        }
	        String mchOrderNo = (String) paramMap.get("mchOrderNo");
	        mchOrderNo = mchOrderNo.split("-")[0];
	        String resStr;
	       OrderBaseInfo orderBaseInfo = null;
	        try {
	            orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(mchOrderNo);
	            if (orderBaseInfo != null) {
	            	if(refundStatus!=3){
	            		if(orderBaseInfo.getOrderState() != OrderConstact.OrderStatusEnum.REFUNDSUCCESS.getCode()){
	            			refundSuccessStoct(mchOrderNo);
			                orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.REFUNDSUCCESS.getCode());
			                orderBaseInfo.setPayState(OrderConstact.OrderPayStatusEnum.REFUNDSUCCESS.getCode());
			                orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
			                orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
			                log.info("orderstate=="+orderBaseInfo.getOrderState());
			                orderStatusInfoService.changeStatusToCancel(orderBaseInfo.getOrderNo(), orderBaseInfo.getOrderState(),feedback,remarks);
			                RefundInfo refundInfo = refundInfoMapper.selectByOrderNo(orderBaseInfo.getOrderNo());
			                if(refundInfo != null){
			                	sellerReceiveConfigService.sendMessageToAgent(2, null, refundInfo);
			                	sellerReceiveConfigService.sendMessageToBuyer(orderBaseInfo, 6);
			                }
			               // sellerReceiveConfigService.sendMessageToSeller(orderBaseInfo.getSellerId(), 2);
			                insertRefundInfo(paramMap);
	            		}
		               
	            	}else{
		                orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.REFUNDING.getCode());
		                orderBaseInfo.setPayState(OrderConstact.OrderPayStatusEnum.REFUNDFAILD.getCode());
		                orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
		                orderStatusInfoService.changeStatus(orderBaseInfo.getOrderNo(), orderBaseInfo.getOrderState());
		                orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
		                insertRefundInfo(paramMap);
	            	}
	                resStr = "success";
	                System.out.println("执行回调结果====="+paramMap);
	            } else {
	            	 RefundInfo refundInfo = new RefundInfo();
		                refundInfo.setId(UUID.randomUUID().toString());
		                refundInfo.setCreateTime(DateUtils.getCurrentDate());
		                refundInfo.setMchRefundNo(mchOrderNo);
		                refundInfo.setUpdateTime(DateUtils.getCurrentDate());
		                refundInfo.setStatus(1);
		                refundInfo.setRefundSuccessTime(DateUtils.getCurrentDate());
		                refundInfo.setRefundAmount(refundMoney);
		                refundInfo.setPayMode(1);
		                refundInfo.setRefundStatus(refundStatus);
		                refundInfo.setRemark("该订单不存在或者其他错误");
		                refundInfoMapper.insert(refundInfo);
		                sellerReceiveConfigService.sendMessageToAgent(3, null, refundInfo);
		                
	                resStr = "success";
	            }
	        } catch (Exception e) {
	            resStr = "fail";
	            log.error("执行业务异常,payOrderId=%s.mchOrderNo=%s", mchOrderNo+""+e);
	        }
	        insertDealRecord(payMode, refundMoney, orderBaseInfo, 6);
	        log.info("响应支付中心通知结果:{},mchOrderNo={}", resStr, mchOrderNo);
	        outResult(response, resStr);
	        log.info("====== 退款中心通知处理完成 ======");
	}
	private void refundSuccessStoct(String orderNo){
		OrderDetailInfo orderDetailInfo = orderDetailInfoMapper.selectByOrderNo(orderNo);
		ProductStockInfo productStockInfo = productStockInfoMapper.selectByPrimaryKey(orderDetailInfo.getProductStockId());		
		productStockInfo.setAdultSaledNumber(productStockInfo.getAdultSaledNumber()-orderDetailInfo.getAdultNumber());
		productStockInfo.setChildSaledNumber(productStockInfo.getChildSaledNumber()-orderDetailInfo.getChildNumber());
		productStockInfo.setUpdateTime(DateUtils.getCurrentDate());
		productStockInfoMapper.updateByPrimaryKeySelective(productStockInfo);		
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
         	refundInfo.setCreateTime(DateUtils.getCurrentDate());
         	refundInfo.setMchRefundNo(mchOrderNo);
         	refundInfo.setUpdateTime(DateUtils.getCurrentDate());
         	refundInfo.setStatus(1);
         	refundInfo.setRefundSuccessTime(DateUtils.getCurrentDate());
         	refundInfo.setRefundAmount(refundMoney);
         	refundInfo.setPayMode(1);
         	refundInfo.setRefundData(JSON.toJSONString(paramMap));
         	refundInfo.setRefundStatus(refundStatus);
         	refundInfo.setRemark("退款状态异常");     
         	refundInfoMapper.insert(refundInfo);
         }else{
         	refundInfo.setRefundSuccessTime(DateUtils.getCurrentDate());
         	refundInfo.setRefundAmount(refundMoney);
         	refundInfo.setPayMode(1);
         	refundInfo.setRefundStatus(refundStatus);
         	refundInfo.setRemark("退款状态异常"); 
         	refundInfo.setRefundData(JSON.toJSONString(paramMap));
         	refundInfoMapper.updateByPrimaryKeySelective(refundInfo);
         }
	}
	private  BigDecimal mul(double v1,double v2){
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));
	        return b1.multiply(b2);
	}
	private int insertDealRecord(int payChannel, BigDecimal actulMoney, OrderBaseInfo orderBaseInfo,int type) {
        PlatDealRecord dealRecord = new PlatDealRecord();
        Date now = new Date();
        dealRecord.setId(UUID.randomUUID().toString());
        dealRecord.setCreateDate(now);
        dealRecord.setOrderId(orderBaseInfo.getId());
        dealRecord.setPayChanel(payChannel);
        dealRecord.setActualMoney(actulMoney);
        dealRecord.setPayPrice(orderBaseInfo.getOrderMoney());
        dealRecord.setSourceId(orderBaseInfo.getBuyerId());
        dealRecord.setDestId(orderBaseInfo.getSellerId());
        dealRecord.setPayState(1);
        dealRecord.setSuccessDate(now);
        dealRecord.setPayTitle("订单");
        dealRecord.setStreamType(type);
        return    platDealRecordMapper.insert(dealRecord);
    }
	   public static String getIpAddr(HttpServletRequest request) {
	        ClientInfo clientInfo = new ClientInfo(request.getHeader("user-agent"));
	        String ip = request.getHeader("Cdn-Src-Ip");    // 网宿cdn的真实ip
	        if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("HTTP_CLIENT_IP");   // 蓝讯cdn的真实ip
	        }
	        if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("X-Forwarded-For");  // 获取代理ip
	        }
	        if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("Proxy-Client-IP"); // 获取代理ip
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("WL-Proxy-Client-IP"); // 获取代理ip
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getRemoteAddr(); // 获取真实ip
	        }
	        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	    }
	   public Map<String,Object> payTest(String orderNo) {
	        JSONObject paramMap = new JSONObject();
	        paramMap.put("mchId", mchId);                               // 商户ID
	        paramMap.put("mchOrderNo", orderNo);     // 商户订单号
//	        paramMap.put("mchOrderNo", System.currentTimeMillis());     // 商户订单号
	        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
	        paramMap.put("channelId", "WX_NATIVE");
	        paramMap.put("amount", 1);                                  // 支付金额,单位分
	        paramMap.put("currency", "cny");                            // 币种, cny-人民币
	        paramMap.put("clientIp", "120.55.49.68");                 // 用户地址,微信H5支付时要真实的
	        paramMap.put("device", "WEB");                              // 设备
	        paramMap.put("subject", "zshlpay支付测试");
	        paramMap.put("body", "zshlpay支付测试");
	        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
	        paramMap.put("param1", "");                                 // 扩展参数1
	        paramMap.put("param2", "");                                 // 扩展参数2
	        paramMap.put("extra", "{\n" +
	                "  \"productId\": \"120989823\",\n" +
	                "  \"openId\": \"o08dF1MYEQSOMA7A76wmaQjFOVUU\",\n" +
	                "  \"sceneInfo\": {\n" +
	                "    \"h5_info\": {\n" +
	                "      \"type\": \"Wap\",\n" +
	                "      \"wap_url\": \"https://pay.qq.com\",\n" +
	                "      \"wap_name\": \"zshlpay充值\"\n" +
	                "    }\n" +
	                "  }\n" +
	                " ,\"discountable_amount\":\"0.00\"," + //面对面支付扫码参数：可打折金额 可打折金额+不可打折金额=总金额
	                "  \"undiscountable_amount\":\"0.00\"," + //面对面支付扫码参数：不可打折金额
	                "}");  // 附加参数

	        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

	        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
	        paramMap.put("sign", reqSign);                              // 签名
	        String reqData = "params=" + paramMap.toJSONString();
	        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
	        String url = baseUrl + "/pay/create_order?";
	        String result = PayUtil.call4Post(url + reqData);
	        System.out.println("请求支付中心下单接口,响应数据:" + result);
	        Map<String,Object> retMap = JSON.parseObject(result);
	        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
	            // 验签
	            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
	            String retSign = (String) retMap.get("sign");
	            if(checkSign.equals(retSign)) {
	                System.out.println("=========支付中心下单验签成功=========");
	                return retMap;
	            }else {
	                System.err.println("=========支付中心下单验签失败=========");
	                return null;
	            }
	        }
//	        return retMap.get("payOrderId")+"";
	        return retMap;
	    }
	   
//	   public String getOpenId(HttpServletRequest request) throws IOException {
//	        log.info("进入获取用户openID页面");
//	        String code = request.getParameter("code");
//	        String openId = "";
//	        if (!StringUtils.isBlank(code)) {//如果request中包括code，则是微信回调
//	            try {
//	                openId = WxApiClient.getOAuthOpenId(appId, appSecret, code);
//	                log.info("调用微信返回openId={}", openId);
//	            } catch (Exception e) {
//	                log.error("调用微信查询openId异常",e);
//	            }
//	            return openId;
//	        	}
//	        return null;
//	        }
}
