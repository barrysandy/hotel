package com.zzk.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzk.entity.OrderBaseInfo;
import com.zzk.service.HotelOrderBaseInfoService;
import com.zzk.service.OrderBaseInfoService;
import com.zzk.service.OrderService;
import com.zzk.service.PayService;
import com.zzk.util.HotelResult;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.PageView;
import com.zzk.util.RedisUtils;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;



import com.zzk.vo.OrderBaseInfoVo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单信息表
 * @name: OrderBaseInfoController
 * @author: Kun
 * @date: 2018-03-06 10:45
 */
@RequestMapping(value = "/orderBaseInfo")
@RestController
@EnableAutoConfiguration
public class OrderBaseInfoController extends BaseController {

	@Resource
	private OrderBaseInfoService orderBaseInfoService;
	@Resource
	private PayService payService;
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private HotelOrderBaseInfoService hotelOrderBaseInfoService;
	@Resource
	private OrderService orderService;
	/**
	 * 商家获取订单列表接口
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param orderState
	 * @param search
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @author John
	 * @date： 2018年3月27日 下午5:46:11
	 */
	@RequestMapping("/listOrder")
    @ResponseBody
	public Result<Object> listOrder(String sellerId,String startTime,String endTime,String orderState,String search,Integer pageSize,
			Integer pageNumber){
		try{
			if (StringUtils.isBlank(sellerId)){
				return new Result<>(0,"error","sellerId不能为空",null);
			}else{
				ArrayList<String> orderStates = assembleOrderStateArr(Integer.parseInt(orderState));
				Map<String,Object> paraMap = new HashMap<>(8);
				paraMap.put("sellerId", sellerId);
				paraMap.put("startDate",startTime);
				paraMap.put("endDate",endTime);
				paraMap.put("orderStateArr",orderStates);
				paraMap.put("search",search);
				if (pageNumber == null){
					pageNumber = 1;
				}
				if (pageSize == null){
					pageSize = 10;
				}
				PageView<OrderBaseInfoVo> vo= orderBaseInfoService.listOrder(paraMap,pageNumber,pageSize);
				return new Result<Object>(1,"success","查询成功",vo);
			}
		}catch (Exception e){
			e.printStackTrace();
			return new Result<>(0,"error","内部异常");
		}
	}
	
	private ArrayList<String> assembleOrderStateArr(int state){
		ArrayList<String>  strArray = new ArrayList<String>();
		if(state == 0 ){
			return null;
		}
		if(state==1 || state==2 || state==3 || state==4){
			strArray.add(state+"");
			return strArray;
		}
		if(state==5){
			strArray.add("9");
			strArray.add("10");
			strArray.add("11");
			return strArray;
		}
		if(state ==6 ){
			strArray.add("6");
			strArray.add("7");
			strArray.add("8");
			return strArray;
		}
		//待处理订单状态
		if(state ==12 ){
			strArray.add("1");
			strArray.add("2");
			strArray.add("6");
			return strArray;
		}
		return null;
	}
	
	/**
	 * 查询订单详情
	 * @param orderNo
	 * @return
	 * @author John
	 * @date： 2018年3月13日 下午12:58:54
	 */
	@RequestMapping("/orderItem")
	@ResponseBody
	public Result<Object> orderItemDetail(String orderNo){
		if(StringUtils.isBlank(orderNo)){
			return new Result<>(0, "error", "订单号为空", null);
		}
		return orderBaseInfoService.selectOrderDetailByOrderNo(orderNo);
	}
	/**
	 * 生成订单
	 * @param order
	 * @return
	 * @author John
	 * @date： 2018年3月13日 下午3:16:38
	 */
	@RequestMapping("/createOrder")
	@ResponseBody
	public String saveOrder(OrderBaseInfo order,String stockGoodsId,Integer adult,Integer child){
		if(order == null){
			return JsonUtils.lineJsonData(0, "error", "参数错误", null);
		}
		if(StringUtils.isBlank(order.getBuyerId())){
			return JsonUtils.lineJsonData(0, "error", "用户信息为空", null);
		}
		if(order.getOrderMoney() == null){
			return JsonUtils.lineJsonData(0, "error", "订单金额不能为空", null);
		}
		if(StringUtils.isBlank(order.getContactPerson())|| StringUtils.isBlank(order.getContactPhone())){
			return JsonUtils.lineJsonData(0, "error", "联系人或电话不能为空", null);
		}
		if(StringUtils.isBlank(stockGoodsId)){
			return JsonUtils.lineJsonData(0, "error", "商品ID不能为空", null);
		}
		if(adult==null || adult==0){
			return JsonUtils.lineJsonData(0, "error", "参旅人员为空", null);
		}
		if(child == null){
			child = 0;
		}
		return orderBaseInfoService.createOrder(order,stockGoodsId,adult,child);
	}
	/**
	 * 获取买家的所有订单
	 * @param userId
	 * @return
	 * @author John
	 * @date： 2018年3月20日 上午9:34:54
	 */
	@RequestMapping("/fetchBuyerOrder")
	@ResponseBody
	public Result<Object> fetchBuyerOrder(String userId,String orderState,Page pager){
		 if(StringUtils.isBlank(userId)){
			 return new Result<>(0, "error", "用户ID不能为空");
		 }
		 return orderBaseInfoService.selectByBuyerId(userId,orderState,pager);
		
	}
	/**
	 * 获取买家
	 * @param userId
	 * @return
	 * @author John
	 * @date： 2018年3月20日 上午9:34:54
	 */
	@RequestMapping("/fetchBuyerOrderItem")
	@ResponseBody
	public Result<Object> fetchBuyerOrderItem(String userId,String orderNo){
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(orderNo)){
			return new Result<>(0, "error", "参数错误");
		}
		return orderBaseInfoService.selectOrderItemByOrderNo(userId,orderNo);
	}
	
	/**
	 * 1 待付款 2 待确认 3待消费 4 待评价 5 已完成 6 退款申请 7 退款中 8 退款成功 
	 * 9 已取消（商家） 10 已取消（用户） 11 已取消（系统）12 已评论
	 * 
	 */
	/**
	 * 商家确认
	 * @param orderNo
	 * @return
	 * @author John
	 * @date： 2018年3月14日 下午4:14:40
	 */
	@RequestMapping("/sellerConfirm")
	@ResponseBody
	public Result<Object> sellerConfirm(String orderNo){
		return orderBaseInfoService.sellerConfirm(orderNo);
	}
	/**
	 * 商家取消
	 * @param orderNo
	 * @return
	 * @author John
	 * @date： 2018年3月14日 下午4:12:48
	 */
	@RequestMapping("/sellerCancel")
	@ResponseBody
	public Result<Object> sellerCancel(String orderNo,String feedback,String remarks, HttpServletRequest request){
		return orderBaseInfoService.sellerCancel(orderNo, feedback, remarks, request);
	}
	/**
	 * 申请退款
	 * @param orderNo
	 * @return
	 * @author John
	 * @date： 2018年3月14日 下午2:57:17
	 */
	@RequestMapping("/refundApply")
	@ResponseBody
	public Result<Object> refundApply(String orderNo,String userId,String reason,HttpServletRequest request){
		if(StringUtils.isBlank(orderNo)){
			new Result<>(0, "error", "订单号不能为空");
		}
		OrderBaseInfo orderBaseInfo = orderBaseInfoService.getOrderByOrderNo(orderNo);
		if(orderBaseInfo.getOrderType()==2){
			return orderService.refundApply(orderNo,userId,reason);
		}else{
			return orderBaseInfoService.refundApply(orderNo,userId,reason,request);
		}
	}
	
	/**
	 * 买家取消
	 * @param orderNo
	 * @return
	 * @author John
	 * @date： 2018年3月14日 下午2:17:46
	 */
	@RequestMapping("/buyerCancel")
	@ResponseBody
	public Result<Object> buyerCancel(String orderNo,HttpServletRequest request,String userId){
		if(StringUtils.isBlank(orderNo)){
			return new Result<>(0,"error","订单号不能为空");
		}
		OrderBaseInfo orderBaseInfo = orderBaseInfoService.getOrderByOrderNo(orderNo);
		if(orderBaseInfo==null){
			return new Result<Object>(0, "error", "订单不存在", orderNo);
		}
		if(orderBaseInfo.getOrderType()==2){
			return orderService.buyerCancel(orderNo,request,userId);
		}else{
			return orderBaseInfoService.buyerCancel(orderNo,request,userId);
		}
		
	}
	/**
	 * B端同意退款申请
	 * @param request
	 * @param orderNo
	 * @param feekBack
	 * @param amount
	 * @param remark
	 * @return
	 * @author John
	 * @date： 2018年3月22日 下午5:10:03
	 */
	@RequestMapping("/agreeRefund")
	@ResponseBody
	public Result<Object> agreeRefund(HttpServletRequest request,String orderNo,String feekback,String amount,String remarks){
		if(StringUtils.isBlank(orderNo)){
			return new Result<>(0,"error","订单号不能为空");
		}
		if(StringUtils.isBlank(amount)){
			return new Result<>(0,"error","退款金额不能为空");
		}
		return  orderBaseInfoService.agreeRefund(request,orderNo,feekback,amount,remarks);
	}
	/**
	 * B端不同意退款申请
	 * @param orderNo
	 * @param feekBack
	 * @param remark
	 * @return
	 * @author John
	 * @date： 2018年3月22日 下午5:10:03
	 */
	@RequestMapping("/refusedRefund")
	@ResponseBody
	public Result<Object> refusedRefund(String orderNo,String feedback,String remarks){
		if(StringUtils.isBlank(orderNo)){
			return new Result<>(0,"error","订单号不能为空");
		}

		return  orderBaseInfoService.refusedRefund(orderNo,feedback,remarks);
	}
	
	/***
	 * 支付回调
	 * @param request
	 * @return
	 * @author John
	 * @date： 2018年3月14日 下午5:53:38
	 */
	@RequestMapping("/payCallBack")
	public void payCallBack(HttpServletRequest request, HttpServletResponse response){
		System.out.println("开始处理回调函数");
		orderBaseInfoService.payCallBack(request,response);
	}
	/**
	 * 退款回调
	 * @param request
	 * @param response
	 * @author John
	 * @date： 2018年3月16日 下午3:03:41
	 */
	@RequestMapping("/refundCallBack")
	public void refundCallBack(HttpServletRequest request, HttpServletResponse response){
		orderBaseInfoService.refundCallBack(request,response);
	}
	/**
	 * 订单的支付
	 * @param orderNo
	 * @param request
	 * @return
	 * @author John
	 * @date： 2018年3月14日 下午6:12:49
	 */
    @RequestMapping("/pay")
    @ResponseBody
    public Result<Object> pay(String orderNo, String openId,HttpServletRequest request){
    	String channelId = "WX_JSAPI";
    	OrderBaseInfo orderBaseInfo = orderBaseInfoService.getOrderByOrderNo(orderNo);
    	if(orderBaseInfo==null){
    		return new Result<Object>(0, "error", "商品支付下单失败，订单不存在", orderNo);
    	}
    	if(orderBaseInfo.getOrderType()==2){
    		return hotelOrderBaseInfoService.wxpay(orderNo,request,channelId);
    	}else{
    		return orderBaseInfoService.wxpay(orderNo,request,channelId);
    	}
    	
    }
    /**
     * 订单的支付
     * @param orderNo
     * @param request
     * @return
     * @author John
     * @date： 2018年3月14日 下午6:12:49
     */
    @RequestMapping("/h5Pay")
    @ResponseBody
    public Result<Object> h5Pay(String orderNo,HttpServletRequest request){
    	String channelId = "WX_MWEB";
    	OrderBaseInfo orderBaseInfo = orderBaseInfoService.getOrderByOrderNo(orderNo);
    	if(orderBaseInfo==null){
    		return new Result<Object>(0, "error", "商品支付下单失败，订单不存在", orderNo);
    	}
    	if(orderBaseInfo.getOrderType()==2){
    		return hotelOrderBaseInfoService.wxpay(orderNo,request,channelId);
    	}else{
    		return orderBaseInfoService.wxpay(orderNo,request,channelId);
    	}
    }
    /**
     * 根据账单号  查询对应订单
     * @return
     * @author John
     * @date： 2018年4月3日 下午2:46:38
     */
    @RequestMapping("/findBillOrder")
    @ResponseBody
    public Result<Object> findBillOrder(String billId, Page page){
    	if(StringUtils.isBlank(billId)){
    		new Result<>(0,"error","账单号不能为空");
    	}
    	if(page == null){
    		page = new Page();
    	}
    	return orderBaseInfoService.selectByBillNo(billId,page);
    }
	/**
	 * 二维码测试
	 * @return
	 * @author John
	 * @date： 2018年3月20日 上午9:23:45
	 */
	@RequestMapping("/testPay")
	@ResponseBody
	public Map<String,Object> ts(String orderNo){
		return payService.payTest(orderNo);
	}
	/**
	 * 首页统计订单接口
	 * @param sellerId
	 * @return String
	 * @author hua
	 * @date 2018-03-06 10:45
	 */
	@RequestMapping("/getHomePageData")
	@ResponseBody
	public Result<Map<String,Object>> getHomePageData(String sellerId){
		Map<String,Object> resultMap = orderBaseInfoService.countHomePageData(sellerId);
		return new Result<Map<String,Object>>(1,"success","查询成功",resultMap);
	}
	/**
	 * 催单接口
	 * @param orderNo
	 * @return
	 * @author John
	 * @date： 2018年5月3日 上午11:24:57
	 */
	@RequestMapping("/urgeOrder")
	@ResponseBody
	public Result<Object> urgeOrder(String orderNo){
		if(StringUtils.isBlank(orderNo)){
			return new Result<>(0, "error", "订单号不能为空");
		}
		
		return orderBaseInfoService.urgeOrder(orderNo);
	}
}
