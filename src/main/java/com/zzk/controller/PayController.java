/**
 * @Description:
 * @author sty
 * @date 2017年3月16日 下午2:06:05
 * @version V1.0
 */
package com.zzk.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.beecloud.BCCache;
import cn.beecloud.BCPay;
import cn.beecloud.BCUtil;
import cn.beecloud.BeeCloud;
import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCOrder;
import cn.beecloud.bean.BCRefund;

import com.alibaba.fastjson.JSON;
import com.zzk.util.HotelResult;
import com.zzk.common.OrderConstants;
import com.zzk.common.PlatConstants;
import com.zzk.common.log.LogTypeEnum;
import com.zzk.util.StringUtils;
import com.zzk.util.UuidUtil;
import com.zzk.util.RedisUtil;
import com.zzk.util.RedisUtils;
import com.zzk.entity.Order;
import com.zzk.entity.PlatDealRecord;
import com.zzk.entity.PlatPayGatewayMessage;
import com.zzk.service.HotelOrderBaseInfoService;
import com.zzk.service.HotelService;
import com.zzk.service.OrderBaseInfoService;
import com.zzk.service.OrderService;
import com.zzk.service.PlatDealRecordService;
import com.zzk.service.PlatPayGatewayMessageService;


@Controller
@RequestMapping(value = "/pay")
public class PayController extends BaseController {

    private Logger log = Logger.getLogger(BaseController.class.getName());

    private static final String NOTICE_CONTENT = "您有新的订单,请注意查收";
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    HotelService hotelService;
    
    @Autowired
    PlatPayGatewayMessageService platPayGatewayMessageService;
    
    @Autowired
    PlatDealRecordService platDealRecordService;
    
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private HotelOrderBaseInfoService hotelOrderBaseInfoService;
    
    @Autowired
    private RedisUtil redisUtil;
   
    

    @Value("${pay.appID}")
    private String appID;

    @Value("${pay.testSecret}")
    private String testSecret;

    @Value("${pay.appSecret}")
    private String appSecret;

    @Value("${pay.masterSecret}")
    private String masterSecret;

    @Value("${pay.testMode}")
    private boolean testMode;
    
    private static String wxJSAPIRedirectUrlPrefix = "http://www.daxi51.com/hotel-web/pay/wxJSAPIPay";
    
    private static String redirectUrlPrefix = "https://open.weixin.qq.com/connect/oauth2/authorize";
    
    private static String accessTokenPrefix = "https://api.weixin.qq.com/sns/oauth2/access_token";
    
    private static String getAccessTokenOrefix = "https://api.weixin.qq.com/cgi-bin/token";
    @PostConstruct
    public void init() {
        BeeCloud.registerApp(appID, testSecret, appSecret, masterSecret);
        BeeCloud.setSandbox(testMode);
        System.out.println(" PayController init:   resitered beeCloud"+testMode);
    }

    /**
     * <p>description:微信支付跳转</p>
     *
     * @param
     * @return String
     * @author 
     * @date 2
     *//*
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/wxForward1")
    @ResponseBody
    public String wxForward(Model model, String service, Order orderInfo, HttpServletRequest request) {
        //验证交易基本信息
    	HotelResult<Object> result = new HotelResult<Object>();
        result.setState(0);
        if (StringUtils.isEmpty(orderInfo.getOrderId())) {
            result.setMsg("orderId 不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(orderInfo.getOrderNum())) {
            result.setMsg("orderNum 不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(service)) {
            result.setMsg("service 不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(orderInfo.getOrderName())) {
            result.setMsg("orderName 不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(orderInfo.getOrderMoney())) {
            result.setMsg("orderMoney 不能为空");
            return JSON.toJSONString(result);
        }
        String ip = getIp(request);
        
        //ip = ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
        
        System.out.println("  ***********   ip    **********************   "+ip);
        
        String return_url = "http://www.daxi51.com/hotel/#/orderDetail?payId="+orderInfo.getOrderId();
        String billNo = orderInfo.getOrderNum();
        String title = orderInfo.getOrderName();
        Map<String, Object> optional = new HashMap<String, Object>();
        PAY_CHANNEL channel;
        try {
            channel = PAY_CHANNEL.BC_WX_WAP;channel = PAY_CHANNEL.valueOf("WX_WAP");
        } catch (Exception e) {
            channel = null;
            e.printStackTrace();
            LogTypeEnum.DEFAULT.error("PayController.wxForward 微信渠道赋值转化异常。", e);
            result.setMsg("微信渠道赋值转化异常");
            return JSON.toJSONString(result);
        }
        int totalFee = orderInfo.getOrderMoney().multiply(new BigDecimal(100)).intValue();
        BCOrder bcOrder = new BCOrder(channel, totalFee, billNo, title);
        bcOrder.setBillTimeout(360);
        //以下是WX_JSAPI（公众号内支付）用到的返回参数，需要在页面的js用到
        PlatPayGatewayMessage gateway = new PlatPayGatewayMessage();
        Date now = new Date();
        gateway.setId(UuidUtil.uuidStr());
        gateway.setOrderId(orderInfo.getOrderId());
        gateway.setPayUserId(orderInfo.getBuyerId());
        gateway.setPayeeUserId(orderInfo.getSellId());
        gateway.setShopId(orderInfo.getShopId());
        gateway.setGatewayType(1);
        gateway.setMoney(orderInfo.getOrderMoney());
        gateway.setSendContent(JSON.toJSONString(bcOrder));
        gateway.setCreateDate(now);
        gateway.setSendTime(now);
        optional.put("gatewayId", gateway.getId());
        
        Map analysis = new HashMap<>();
        bcOrder.setOptional(optional);
        bcOrder.setReturnUrl(return_url);
        analysis.put("ip", ip);
        System.err.println("H5支付ip:"+ip);
        bcOrder.setAnalysis(analysis);
        String redirectUrl = "";
        try {
                     bcOrder.setNotifyUrl(aliReturnUrl);
            bcOrder = BCPay.startBCPay(bcOrder);
            System.out.println(bcOrder.getObjectId());
            Thread.sleep(3000);
            redirectUrl = bcOrder.getUrl();
            
            System.err.println("H5支付redirectUrl:"+redirectUrl);
           
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.err.println("支付异常:"+e.toString()+":"+e.getMessage());
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("redirectUrl", redirectUrl);
        result.setMessage("success");
        result.setState(1);
        result.setData(resultMap);
        
        return JSON.toJSONString(result);
    }
    */
    /**
     * <p>description:慧旅第三方h5支付</p>
     *
     * @param
     * @return String
     * @author 
     * @date 2
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/wxForward")
    @ResponseBody
    public String wxForward1(Model model, String service, Order orderInfo, HttpServletRequest request) {
        //验证交易基本信息
    	HotelResult<Object> result = new HotelResult<Object>();
        result.setState(0);
        if (StringUtils.isEmpty(orderInfo.getOrderId())) {
            result.setMsg("orderId 不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(orderInfo.getOrderNum())) {
            result.setMsg("orderNum 不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(service)) {
            result.setMsg("service 不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(orderInfo.getOrderName())) {
            result.setMsg("orderName 不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(orderInfo.getOrderMoney())) {
            result.setMsg("orderMoney 不能为空");
            return JSON.toJSONString(result);
        }
        String ip = getIp(request);
        System.out.println("  ***********   ip    **********************   "+ip);
        String channelId = "WX_MWEB";
        result = hotelOrderBaseInfoService.wxpay(orderInfo.getOrderNum(), null, request,channelId);
        return JSON.toJSONString(result);
    }
    
    
    
    
     /**
     * <p>description:微信公众号支付</p>
     *
     * @param
     * @return String
     * @author 
     * @throws Exception 
     * @date 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/wxJSAPIPay")
    @ResponseBody
    public String wxJSAPIPay(Order orderInfo, HttpServletRequest request, HttpServletResponse response)throws Exception {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	HotelResult result = new HotelResult<>();
    	String openId = "";
    	if (StringUtils.isEmpty(orderInfo.getOrderId())) {
            result.setMsg("orderId 不能为空");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(orderInfo.getOrderNum())) {
            result.setMsg("orderNum 不能为空");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        if (request.getParameter("userId") == null || request.getParameter("userId").toString().equals("")) {
        	result.setMessage("无法获取openId");
            result.setState(0);
            return JSON.toJSONString(result);
        }else{
        	String userId = request.getParameter("userId");
               	openId = hotelService.selectOp(userId);
                if(StringUtils.isBlank(openId)){
                	result.setMessage("无法获取openId");
                    result.setState(0);
                    return JSON.toJSONString(result);
                }
        	}	
        String channelId = "WX_JSAPI";
        result = hotelOrderBaseInfoService.wxpay(orderInfo.getOrderNum(), openId, request, channelId);
        
        
    	return JSON.toJSONString(result);
    }
    
    /*@SuppressWarnings("unchecked")
    @RequestMapping(value = "/wxJSAPIPay1")
    @ResponseBody
    public String wxJSAPIPay1(Model model, String service, Order orderInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	 Map<String,Object> resultMap = new HashMap<String,Object>();
    	 HotelResult<Object> result = new HotelResult<Object>();
    	 
         result.setState(0);
         if (StringUtils.isEmpty(orderInfo.getOrderId())) {
             result.setMsg("orderId 不能为空");
             return JSON.toJSONString(result);
         }
         if (StringUtils.isEmpty(orderInfo.getOrderNum())) {
             result.setMsg("orderNum 不能为空");
             return JSON.toJSONString(result);
         }
         if (StringUtils.isEmpty(service)) {
             result.setMsg("service 不能为空");
             return JSON.toJSONString(result);
         }
         if (StringUtils.isEmpty(orderInfo.getOrderName())) {
             result.setMsg("orderName 不能为空");
             return JSON.toJSONString(result);
         }

         if (StringUtils.isEmpty(orderInfo.getOrderMoney())) {
             result.setMsg("orderMoney 不能为空");
             return JSON.toJSONString(result);
         }
         String ip = request.getRemoteAddr();
         
         ip = ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
         
         System.out.println("  ***********   ip    **********************   "+ip);
         String return_url = "www.baidu.com";
         String billNo = orderInfo.getOrderNum();
         String title = orderInfo.getOrderName();
         Map<String, Object> optional = new HashMap<String, Object>();
         PAY_CHANNEL channel;
         try {
             channel = PAY_CHANNEL.BC_WX_WAP;channel = PAY_CHANNEL.valueOf("WX_JSAPI");
         } catch (Exception e) {
             channel = null;
             e.printStackTrace();
             LogTypeEnum.DEFAULT.error("PayController.wxForward 微信渠道赋值转化异常。", e);
             result.setMsg("微信渠道赋值转化异常");
             return JSON.toJSONString(result);
         }
         int totalFee = orderInfo.getOrderMoney().multiply(new BigDecimal(100)).intValue();
         BCOrder bcOrder = new BCOrder(channel, totalFee, billNo, title);
         
         Map analysis = new HashMap<>();
         analysis.put("ip", ip);
         bcOrder.setAnalysis(analysis);
        String wxJSAPIAppId = "wx93a4e8ff7be8feb9";
        String wxJSAPISecret = "eacce4048a253e99fa2ea649cf11b928";
        String wxJSAPIRedirectUrl = wxJSAPIRedirectUrlPrefix+"?service="+service+"&orderId="+orderInfo.getOrderId()+"&orderNum="+orderInfo.getOrderNum()+"&orderName="+orderInfo.getOrderName()+"&orderMoney="+orderInfo.getOrderMoney() ;
        String encodedWSJSAPIRedirectUrl = URLEncoder.encode(wxJSAPIRedirectUrl);
        
        if (request.getParameter("userId") == null || request.getParameter("userId").toString().equals("")) {
        	String redirectUrl = redirectUrlPrefix+"?appid=" + wxJSAPIAppId + "&redirect_uri=" + encodedWSJSAPIRedirectUrl + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
            System.out.println("wx jsapi redirct url:" + redirectUrl);
            resultMap.put("redirectUrl", redirectUrl);
            response.sendRedirect(redirectUrl);
            
            return JSON.toJSONString(result);
        } else {
            String code = request.getParameter("code");
            String resultStr = "";
			try {
				resultStr = sendGet(accessTokenPrefix+"?appid=" + wxJSAPIAppId + "&secret=" + wxJSAPISecret + "&code=" + code + "&grant_type=authorization_code");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("code  :  "+code+"    result:" + resultStr);
            JSONObject resultObject = JSONObject.fromObject(resultStr);
            if (resultObject.containsKey("errcode")) {
            	result.setMessage("获取access_token出错！错误信息为：" + resultObject.get("errmsg").toString());
            	
                result.setMessage("获取access_token出错");
                result.setState(0);
                result.setData(resultMap);
            } else {
        
                String openId = resultObject.get("openid").toString();
        	result.setMessage("获取access_token出错！错误信息为：" + resultObject.get("errmsg").toString());
        	
            result.setMessage("获取openid出错");
            result.setState(0);
            result.setData(resultMap);
        }else{
        	String userId = request.getParameter("userId");
                String openId = hotelService.selectOp(userId);
                if(StringUtils.isBlank(openId)){
                	result.setMessage("获取openid出错");
                    result.setState(0);
                    return JSON.toJSONString(result);
                }
                System.err.println(openId);
                bcOrder.setOpenId(openId);
                String redirectUrl2 = "http://www.daxi51.com/hotel/blank?";
                try {
                    bcOrder = BCPay.startBCPay(bcOrder);
                    out.println(bcOrder.getObjectId());
                    Thread.sleep(3000);
                    System.out.print(bcOrder.getObjectId());
                    Map<String, String> map = bcOrder.getWxJSAPIMap();
                    System.out.print(" bcOrder     map    "+map);
                    String jsapiAppid = map.get("appId").toString();
                    String timeStamp = map.get("timeStamp").toString();
                    String nonceStr = map.get("nonceStr").toString();
                    String jsapipackage = map.get("package").toString();
                    String signType = map.get("signType").toString();
                    String paySign = map.get("paySign").toString();
                    
                    resultMap.put("jsapiAppid", jsapiAppid);
                    resultMap.put("timeStamp", timeStamp);
                    resultMap.put("nonceStr", nonceStr);
                    resultMap.put("jsapipackage", jsapipackage);
                    resultMap.put("signType", signType);
                    resultMap.put("paySign", paySign);
                    redirectUrl2 = redirectUrl2 + "jsapiAppid="+jsapiAppid+"&timeStamp="+timeStamp+"&nonceStr="+nonceStr+"&jsapipackage="+jsapipackage+"&signType="+signType+"&paySign="+paySign;
                    result.setMessage("success");
                    result.setState(1);
                    result.setData(resultMap);
                    response.sendRedirect(redirectUrl2);
                    
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    //out.println(e.getMessage());
                }
            }
 //       }

        return JSON.toJSONString(result);
    }*/
    
    
	@ResponseBody
	@RequestMapping(value="/refund")
	public String refund(HttpServletResponse response,Order bean){
		HotelResult result = new HotelResult<>();
		String orderId = bean.getOrderId();
		String handleMan = bean.getHandleMan();
		if(StringUtils.isBlank(orderId)){
			result.setState(0);
			result.setMsg("error");
			result.setMessage("订单ID不能为空");
			return JSON.toJSONString(result);
		}
		Order order = orderService.selectByPrimaryKey(orderId);
		try {
			result = hotelOrderBaseInfoService.refundH5BC(order, handleMan, result, response);
		} catch (Exception e) {
			System.err.println(e.toString());
			System.err.println(e.getMessage());
			result.setState(0);
			result.setMsg("error");
			result.setMessage("退款失败");
			return JSON.toJSONString(result);
		}
		return JSON.toJSONString(result); 
	}


	/**
     * <p>description:秒支付</p>
     *
     * @param jsonObj 秒支付返回内容
     * @return String
     * @author Wen Yugang
     * @date 2017-6-26下午3:02:27
     *//*
    @SuppressWarnings("finally")
    @Transactional
    private String processPayResult(JSONObject jsonObj, HttpServletRequest request) {
        try {
            //签名
            String signature = jsonObj.getString("signature");
            //交易ID
            String transactionId = jsonObj.getString("transaction_id");
            //交易类型 
            String transactionType = jsonObj.getString("transaction_type");
            //交易渠道(支付宝或微信)
            String channelType = jsonObj.getString("channelType");
            //交易金额（单位：分）
            long transactionFee = jsonObj.getLong("transaction_fee");
            BigDecimal b1 = new BigDecimal(Double.toString(transactionFee));
            BigDecimal b2 = new BigDecimal(Double.toString(100));
            //计算实际交易金额 （将分转换成元，保留两位小数到分）
            double actulMoney = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();

            //签名= appID+交易ID+交易类型+交易渠道+交易金额
            StringBuffer toSign = new StringBuffer();
            toSign.append(BCCache.getAppID()).append(transactionId).append(transactionType).append(channelType).append(transactionFee);
            //验证签名
            boolean status = verifySign(toSign.toString(), masterSecret, signature);
            Date now = new Date();
            //传递参数
            Map<String, Object> optional = jsonObj.getJSONObject("optional");
            System.out.println("  ******* jsonObj " + jsonObj);
            if (status) {
                int payChannel = 4;//余额
                if ("WX".equals(channelType)) {
                    payChannel = 2;//微信
                } else if ("ALI".equals(channelType)) {
                    payChannel = 1;//支付宝
                }
                    //更新网关报文（用于对账）
                    PlatPayGatewayMessage gateway = new PlatPayGatewayMessage();
                    gateway.setId(optional.get("gatewayId").toString());
                    gateway.setReceiveContent(optional.toString());
                    gateway.setReceiveTime(now);
                    gateway.setModifyDate(now);
                    gateway.setActMoney(BigDecimal.valueOf(actulMoney));
                    gateway.setStatus(1);
                    platPayGatewayMessageService.updateByPrimaryKeySelective(gateway);
                
                   if("PAY".equalsIgnoreCase(transactionType)){
                	   orderCallBack(transactionId, actulMoney, payChannel, request);//处理订单业务
                   }else if("REFUND".equalsIgnoreCase(transactionType)){
                	   refundOrderCallBack(optional,transactionId, actulMoney, payChannel, request);
                   }
                    
                }
            } else {
                if (optional != null && !StringUtils.isEmpty(optional.get("gatewayId"))) {
                    PlatPayGatewayMessage gateway = new PlatPayGatewayMessage();
                    gateway.setId(optional.get("gatewayId").toString());
                    gateway.setReceiveContent(optional.toString());
                    gateway.setReceiveTime(now);
                    gateway.setModifyDate(now);
                    gateway.setStatus(-1);
                    platPayGatewayMessageService.updateByPrimaryKeySelective(gateway);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogTypeEnum.DEFAULT.error("PayController 支付回调方法异常。", e);
            throw e;
        } finally {
            return "success";
        }
    }

    *//**
     * <p>description:秒支付账号配置的回调接口地址</p>
     *
     * @param
     * @return String
     * @author Wen Yugang
     * @date 2017-6-26下午3:11:07
     *//*
    @RequestMapping(value = "/webHookReceiver", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String webHookReceiver(HttpServletRequest request) {
        StringBuffer json = new StringBuffer();
        String line = null;
        try {
            request.setCharacterEncoding("utf-8");
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("  ******* json " + json+"  "+json.toString());
        JSONObject jsonObj = JSONObject.fromObject(json.toString());
        System.out.println("  ******* webHookReceiver " + jsonObj);
        return processPayResult(jsonObj, request);
    }*/
    
    
    //慧旅支付回调接口
    @RequestMapping(value = "/payCallBack", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String payCallBack(HttpServletRequest request,HttpServletResponse response) {
    	return hotelOrderBaseInfoService.payCallBack(request, response);
    }
    
    //慧旅退款回调接口
    @RequestMapping(value = "/refundCallBack", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void refundCallBack(HttpServletRequest request,HttpServletResponse response) {
    	hotelOrderBaseInfoService.refundCallBack(request, response);
    }
    

    /**
     * <p>description:验证签名</p>
     *
     * @param text      我们拼接的签名
     * @param masterKey 秘钥
     * @param signature 秒支付提供的签名
     * @return boolean
     * @author Wen Yugang
     * @date 2017-6-26下午3:07:02
     */
    boolean verifySign(String text, String masterKey, String signature) {
        boolean isVerified = verify(text, signature, masterKey, "UTF-8");
        if (!isVerified) {
            return false;
        }
        return true;
    }

    /**
     * <p>description:验证签名</p>
     *
     * @param text         我们拼接的签名
     * @param sign         秒支付提供的签名
     * @param key          秘钥
     * @param inputCharset 字符集编码
     * @return boolean
     * @date 2017-6-26下午3:04:57
     */
    boolean verify(String text, String sign, String key, String inputCharset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, inputCharset));
        return mysign.equals(sign);
    }

    /**
     * <p>description:获取指定字符集的签名</p>
     *
     * @param
     * @return byte[]
     * @author Wen Yugang
     * @date 2017-6-26下午3:06:30
     */
    byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

  


    /**
     * 交易记录
     *
     * @param payChannel
     * @param actulMoney
     * @param orderInfo
     * @return
     */
    int insertDealRecord(int payChannel, BigDecimal actulMoney, Order orderInfo) {
        PlatDealRecord dealRecord = new PlatDealRecord();
        Date now = new Date();
        dealRecord.setId(UuidUtil.uuidStr());
        dealRecord.setCreateDate(now);
        dealRecord.setOrderId(orderInfo.getOrderId());
        dealRecord.setPayChanel(payChannel);
        dealRecord.setActualMoney(actulMoney);
        dealRecord.setPayPrice(orderInfo.getOrderMoney());
        dealRecord.setSourceId(orderInfo.getBuyerId());
        dealRecord.setDestId(orderInfo.getSellId());
        dealRecord.setPayState(1);
        dealRecord.setSuccessDate(now);
        dealRecord.setPayTitle("订单");
        dealRecord.setStreamType(PlatConstants.STREAM_DINGDAN);
        platDealRecordService.insert(dealRecord);
        return 0;
    }
    private int insertRefundDealRecord(int payChannel, BigDecimal actulMoney,Order order) {
        PlatDealRecord dealRecord = new PlatDealRecord();
        Date now = new Date();
        dealRecord.setId(UuidUtil.uuidStr());
        dealRecord.setCreateDate(now);
        dealRecord.setOrderId(order.getOrderId());
        dealRecord.setPayChanel(payChannel);
        dealRecord.setActualMoney(order.getActualMoney());
        dealRecord.setPayPrice(order.getActualMoney());
        dealRecord.setSourceId(order.getSellId());
        dealRecord.setDestId(order.getBuyerId());
        dealRecord.setPayState(1);
        dealRecord.setSuccessDate(now);
        dealRecord.setPayTitle("退款");
        dealRecord.setStreamType(PlatConstants.STREAM_TUIKUAN);
        platDealRecordService.insert(dealRecord);
        return 0;
		
	}

    /**
     * <p>description:订单回调方法</p>
     *
     * @param
     * @return void
     * @author Wen Yugang
     * @date 2017-6-26下午4:12:51
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void orderCallBack(String transactionId, double actulMoney, int payChannel, HttpServletRequest request) {
        try {
            Date now = new Date();
            //查询相关订单信息
            Order orderInfo = orderService.selectByOrderNum(transactionId);
            if (actulMoney == orderInfo.getOrderMoney().doubleValue()) {
                if (orderInfo.getPayState() != 1) {
                	orderInfo.setActualMoney(new BigDecimal(actulMoney));
                	orderInfo.setOrderState(OrderConstants.WAIT_TO_CONFIRM);
                	orderInfo.setPayMode(payChannel);
                	orderInfo.setPayState(OrderConstants.PAY_PAY);
                	orderService.update(orderInfo);
                    //交易记录
                    insertDealRecord(payChannel, new BigDecimal(actulMoney), orderInfo);
                    
                    
                }
            } else {
                //支付金额异常处理
                LogTypeEnum.DEFAULT.error("PayController.orderCallBack 支付金额异常。", new Exception("支付金额异常"));
                throw new RuntimeException("支付金额异常,订单金额和hook传入的支付金额不等。订单金额为：" + actulMoney + ",订单金额为=" + orderInfo.getOrderMoney().doubleValue());
            }
        } catch (Exception e) {
            LogTypeEnum.DEFAULT.error("PayController.orderCallBack 订单处理回调方法异常。", e);
            throw e;
        }
    }
    /**
     * <p>description:退款订单回调方法</p>
     *
     * @param
     * @return void
     * @author Wen Yugang
     * @date 2017-6-26下午4:12:51
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void refundOrderCallBack(Map<String,Object> optional,String transactionId, double actulMoney, int payChannel, HttpServletRequest request) {
    	try {
    		Date now = new Date();
    		String orderNum = optional.get("orderNum").toString();
    		//查询相关订单信息
    		Order orderInfo = orderService.selectByOrderNum(orderNum);
    		if (actulMoney == orderInfo.getOrderMoney().doubleValue()) {
    			if (orderInfo.getPayState() != OrderConstants.PAY_REFUND) {
    				orderInfo.setActualMoney(new BigDecimal(actulMoney));
    				orderInfo.setOrderState(OrderConstants.CANCEL_BY_SELLER);
    				orderInfo.setPayMode(payChannel);
    				orderInfo.setPayState(OrderConstants.PAY_REFUND);
    				orderService.update(orderInfo);
    				//交易记录
    				insertRefundDealRecord(payChannel, new BigDecimal(actulMoney), orderInfo);
    				
    				
    			}
    		} else {
    			//支付金额异常处理
    			LogTypeEnum.DEFAULT.error("PayController.orderCallBack 支付金额异常。", new Exception("支付金额异常"));
    			throw new RuntimeException("支付金额异常,订单金额和hook传入的支付金额不等。订单金额为：" + actulMoney + ",订单金额为=" + orderInfo.getOrderMoney().doubleValue());
    		}
    	} catch (Exception e) {
    		LogTypeEnum.DEFAULT.error("PayController.orderCallBack 订单处理回调方法异常。", e);
    		throw e;
    	}
    }
    
    public String sendGet(String url) throws Exception {
        String result = "";
        BufferedReader in = null;
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
        // 设置通用的请求属性
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }
    
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip))  
            try {  
                ip = InetAddress.getLocalHost().getHostAddress();  
            }  
            catch (UnknownHostException unknownhostexception) {  
            }
		return ip;

	}
}
