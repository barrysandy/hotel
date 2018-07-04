package com.zzk.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.http.HttpRequest;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzk.util.DateUtils;
import com.zzk.util.HotelResult;
import com.zzk.common.OrderConstact;
import com.zzk.common.OrderConstants;
import com.zzk.common.OrderLabel;
import com.zzk.util.PageView;
import com.zzk.common.PlatConstants;
import com.zzk.common.FormModel;
import com.zzk.util.Exceptions;
import com.zzk.util.Page;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.zzk.util.RedisUtils;
import com.zzk.vo.OrderBaseInfoVo;
import com.zzk.entity.Dict;
import com.zzk.entity.Hotel;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderCondition;
import com.zzk.entity.OrderCustom;
import com.zzk.entity.OrderCustomDo;
import com.zzk.entity.PriceRule;
import com.zzk.entity.RoomType;
import com.zzk.entity.StockRule;
import com.zzk.entity.SwitchRule;
import com.zzk.service.CacheService;
import com.zzk.service.DictService;
import com.zzk.service.FinanceService;
import com.zzk.service.HotelGoodsService;
import com.zzk.service.HotelOrderBaseInfoService;
import com.zzk.service.HotelService;
import com.zzk.service.OrderBaseInfoService;
import com.zzk.service.OrderService;
import com.zzk.service.OrderServiceImpl;
import com.zzk.service.PriceRuleService;
import com.zzk.service.RoomTypeService;
import com.zzk.service.SellerReceiveConfigService;
import com.zzk.service.StockRuleService;
import com.zzk.service.SwitchRuleService;
import com.zzk.controller.BaseController;

/**
 * <p>description：订单信息</p>
 * @name：OrderController
 * @author：sty
 * @date：2017-11-02 10:39
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private StockRuleService stockRuleService;
	@Autowired
	private PriceRuleService priceRuleService;
	@Autowired
	private HotelGoodsService hotelGoodsService;
 	@Autowired
	private RedisUtils redisUtils;
 	@Autowired
 	private HotelService hotelService;
 	@Autowired
 	private RoomTypeService roomTypeService;
 	@Autowired
 	private SwitchRuleService switchRuleService;
 	@Autowired
 	private OrderBaseInfoService orderBaseInfoService;
 	@Autowired
 	private HotelOrderBaseInfoService hotelOrderBaseInfoService;
 	@Autowired
	private DictService dictService;
 	@Autowired
 	private SellerReceiveConfigService sellerReceiveConfigService;
 	@Autowired
 	private FinanceService financeService;
	private static List<Dict> dictList;
 	
	
	/**
	 * 买家查询订单列表
	 * @param page
	 * @param buyerId
	 * @return
	 * @author huashuwen
	 * @date 2017-11-15 10:39
	 * （列表需要改）
	 */
	@RequestMapping("/listByBuyerId")
	@ResponseBody
	public String listByBuyerId(@FormModel("pager")Page pager,String buyerId,String orderState){
		HotelResult result = new HotelResult();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> stateList = new ArrayList<>();
		if(StringUtils.isNotBlank(orderState)){
			stateList.add(orderState);
			map.put("orderStateArr", stateList);
		}
		map.put("buyerId", buyerId);
		PageView<OrderBaseInfo> view = orderService.selectOrderByBuyerIdNew(map,pager.getPageNo(),pager.getpageSize());
		//PageView<OrderBaseInfoVo> view1 = orderService.selectByBuyerId(buyerId, "0", pager);
		List<OrderBaseInfo> list = view.getList();
		//List<OrderBaseInfoVo> lineOrders = view1.getList();
		List<Order> resultList = new ArrayList<>();
		
		for (OrderBaseInfo bean:list) {
        	resultList.add(orderService.orderBaseInfo2Order(bean));
        }
		
		changeToOrder(resultList);
		for(Order order: resultList){
			try{
				order.setOrderStateStr(CacheService.getLabel("orderState", String.valueOf(order.getOrderState())));
				order.setOrderStateStr(this.getOrderStateStr(order.getOrderState()));
				order.setOrderObject(null);
			}catch(Exception e){
				System.err.println(e.toString());
				result.setMessage("有无效订单");
				Exceptions.getStackTraceAsString(e);//统一的异常处理
			}
		}
		List<Object> allOrder = new ArrayList<Object>();
		allOrder.addAll(resultList);
		//allOrder.addAll(lineOrders);
		result.setData(allOrder);
		result.setPageSize(view.getPageSize());
		result.setPageIndex(view.getPageNum());
		result.setTotalNum((int)view.getTotal());
		result.setPageCount(view.getPages());
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	/**
	 * 卖家查询订单列表
	* @param search
	* @param page
	* @param buyerId
	* @return
	* @author huashuwen
	* @date 2017-11-15 10:39
	*（列表需要改）
	 */
	@RequestMapping("/listByShopId")
	@ResponseBody
	public String listByShopId(@FormModel("pager")Page pager,Order bean,String startTime,String endTime){
		HotelResult result = new HotelResult();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("shopId", bean.getShopId());
		map.put("startTime", startTime);
		map.put("contactMobile", bean.getContactMobile());
		map.put("checkinPerson", bean.getCheckinPerson());
		map.put("handleMan", bean.getHandleMan());
		map.put("search", bean.getOrderNum());
		map.put("orderState", bean.getOrderState());
		try {
			map.put("endTime", DateUtils.getAfterDay(endTime, 1, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Order> list = orderService.selectOrderByShopId(map);
		changeToOrder(list);
		int totalNum = orderService.selectCountByShopId(map);
		int pageCount = totalNum%pager.getpageSize()==0?totalNum/pager.getpageSize():totalNum/pager.getpageSize()+1;
		result.setData(list);
		result.setPageSize(pager.getpageSize());
		result.setPageIndex(pager.getPageNo());
		result.setTotalNum(totalNum);
		result.setPageCount(pageCount);
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 商户确认订单
	* @param bean
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-20
	 */
	@RequestMapping("/confirmOrder")
	@ResponseBody
	public String confirmOrder(Order bean){
		HotelResult result = new HotelResult();
		String orderId = bean.getOrderId();
		Order order = orderService.selectByPrimaryKey(orderId);
		int orderState = order.getOrderState();
		if(orderState==OrderConstants.WAIT_TO_CONFIRM){
			order.setOrderState(OrderConstants.CONFIRM);
			order.setHandleMan(bean.getHandleMan());
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("确认失败");
			return JSON.toJSONString(result);
		}
		order.setUpdateTime(new Date());
		orderService.update(order);
		result.setState(1);
		result.setMsg("确认成功");
		result.setMessage("确认成功");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 商户确认入住
	* @param bean
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-15 
	 */
	@RequestMapping("/confirmCheckIn")
	@ResponseBody
	public String confirmCheckIn(Order bean){
		HotelResult result = new HotelResult();
		String orderId = bean.getOrderId();
		Order order = orderService.selectByPrimaryKey(orderId);
		int orderState = order.getOrderState();
		if(orderState==OrderConstants.WAIT_TO_CONFIRM){
			order.setOrderState(OrderConstants.CONFIRM);
			order.setArriveTime(new Date());
			order.setHandleMan(order.getHandleMan());
			OrderBaseInfo orderBaseInfo = orderService.order2orderBaseInfo(order);
			order.setUpdateTime(new Date());
			orderService.update(order);
			sellerReceiveConfigService.sendHotelMessageToBuyer(orderBaseInfo,2);
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("确认失败");
			return JSON.toJSONString(result);
		}
		result.setState(1);
		result.setMsg("success");
		result.setMessage("确认成功");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 商户取消订单
	* @param bean
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-15 
	 */
	@RequestMapping("/hotelCancel")
	@ResponseBody
	public String hotelCancel(Order bean,HttpServletRequest request,HttpServletResponse response){
		HotelResult result = new HotelResult();
		String orderId = bean.getOrderId();
		Order order = orderService.selectByPrimaryKey(orderId);
		int orderState = order.getOrderState();
		if(orderState==OrderConstants.WAIT_TO_PAY){
//			return "forward:/order/hotelCancelNotPay";
			order.setOrderState(OrderConstants.NOT_PAY_CANCEL_BY_SELLER);
			orderService.update(order);
			result.setState(1);
			result.setMsg("success");
			result.setMessage("拒单成功");
			return JSON.toJSONString(result);
		} else if(orderState==OrderConstants.WAIT_TO_CONFIRM){
			
			result= hotelOrderBaseInfoService.doRefundOrder(request ,order.getOrderId(),"");
			order.setOrderState(OrderConstants.CANCEL_BY_SELLER);
			OrderBaseInfo orderBaseInfo = orderService.order2orderBaseInfo(order);
			sellerReceiveConfigService.sendHotelMessageToBuyer(orderBaseInfo,4);
			orderService.update(order);
			return JSON.toJSONString(result);
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("取消失败");
			return JSON.toJSONString(result);
		}
//		order.setHandleMan(bean.getHandleMan());
//		order.setUpdateTime(new Date());
//		orderService.update(order);
//		result.setState(1);
//		result.setMsg("success");
//		return JSON.toJSONString(result);
	}
	@RequestMapping("/hotelCancelNotPay")
	@ResponseBody
	public String hotelCancelNotPay(Order bean){
		HotelResult result = new HotelResult();
		String orderId = bean.getOrderId();
		Order order = orderService.selectByPrimaryKey(orderId);
		int orderState = order.getOrderState();
		if(orderState==OrderConstants.WAIT_TO_PAY){
			order.setOrderState(OrderConstants.NOT_PAY_CANCEL_BY_SELLER);
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("取消失败");
			return JSON.toJSONString(result);
		}
		order.setHandleMan(bean.getHandleMan());
		order.setUpdateTime(new Date());
		orderService.update(order);
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 未付款买家取消订单
	* @param bean
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-15 
	 */
	@RequestMapping("/buyerCancelNotPay")
	@ResponseBody
	public String buyerCancelNotPay(Order bean){
		HotelResult result = new HotelResult();
		String orderId = bean.getOrderId();
		Order order = orderService.selectByPrimaryKey(orderId);
		int orderState = order.getOrderState();
		if(orderState==OrderConstants.WAIT_TO_PAY){
			order.setOrderState(OrderConstants.NOT_PAY_CANCEL_BY_SELLER);
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("取消失败");
			return JSON.toJSONString(result);
		}
		order.setUpdateTime(new Date());
		orderService.update(order);
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 买家取消订单
	* @param bean
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-15 
	 */
	@RequestMapping("/buyerCancel")
	@ResponseBody
	public String buyerCancel(Order bean){
		HotelResult result = new HotelResult();
		String orderId = bean.getOrderId();
		Order order = orderService.selectByPrimaryKey(orderId);
		if(StringUtils.isNotBlank(orderId)){
			HotelGoods hotelGoods = hotelGoodsService.selectByPrimaryKey(order.getGoodsId());
			int cancel = hotelGoods.getCancel();
			if(order.getOrderState()==OrderConstants.WAIT_TO_PAY){
				if(cancel == 0){
					//TODO 不可取消
					//order.setOrderState(OrderConstants.CANCEL_BY_BUYER_OVERTIME);
				}else if(cancel == 1){
					//TODO 退款
					order.setOrderState(OrderConstants.CANCEL_BY_BUYER_ANYTIME);
				}else{
					if((new Date()).getTime()-order.getOrderTime().getTime() <= cancel*3600000){
						//TODO 限时内取消
						order.setOrderState(OrderConstants.CANCEL_BY_BUYER);
					}else{
						//TODO 超时取消
						//order.setOrderState(OrderConstants.CANCEL_BY_BUYER_OVERTIME);
					}
				}
			}else{
				result.setState(0);
				result.setMsg("error");
				result.setMessage("取消失败");
				return JSON.toJSONString(result);
			}
		}
		order.setUpdateTime(new Date());
		orderService.update(order);
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 未用
	 * 买家取消订单
	* @param bean
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-15 
	 */
	@RequestMapping("/refund")
	@ResponseBody
	public String refund(Order bean,HttpServletRequest request){
		HotelResult result = new HotelResult();
		String orderId = bean.getOrderId();
		Order order = orderService.selectByPrimaryKey(orderId);
		if(StringUtils.isNotBlank(orderId)){
			if((order.getOrderState()==OrderConstants.REFUND_APLLY&&
					order.getPayState()==OrderConstants.PAY_PAY)||order.getPayState()==OrderConstants.REFUND_FAIL){
				HotelResult result2 = hotelOrderBaseInfoService.doRefundOrder(request, orderId, null);
				if(result2.getState()==1){
					order.setOrderState(OrderConstants.CANCEL_BY_BUYER);
					order.setUpdateTime(new Date());
					orderService.update(order);
					result.setState(1);
					result.setMsg("success");
					return JSON.toJSONString(result);
				}else{
					result.setState(0);
					result.setMsg("error");
					result.setMessage("退款失败");
					return JSON.toJSONString(result);
				}
			}else{
				result.setState(0);
				result.setMsg("error");
				result.setMessage("该订单不可退款");
				return JSON.toJSONString(result);
			}
			
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("参数错误");
			return JSON.toJSONString(result);
		}
	}
	
	/**
	 * 订单信息编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:39
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			Order rule = new Order();
			if(StringUtils.isNotBlank(id)){
				rule = orderService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("order/order_edit");
	}
	/**
	 * 订单信息预览
	* @param orderId
	* @return
	* @author huashuwen
	* @date 2017-11-15 10:39
	 */
	@RequestMapping("/toView")
	@ResponseBody
	public String toView(String orderId){
		HotelResult<Map> result = new HotelResult<Map>();
		Order rule = new Order();
		if(StringUtils.isNotBlank(orderId)){
			rule = orderService.selectByPrimaryKey(orderId);
			if(!StringUtils.isEmpty(rule)){
				
				Hotel hotel = hotelService.selectByPrimaryKey(rule.getShopId());
				String city = hotelService.selecCity(hotel.getCityId());
				String province = hotelService.selecProvince(hotel.getCityId().substring(0, 2));
				hotel.setAreaName(province+city);
				HotelGoods hg = hotelGoodsService.selectByPrimaryKey(rule.getGoodsId());
				String breakfastStr = CacheService.getLabel("goodsBreakfast",String.valueOf(hg.getBreakfast()));
				String roomTypeId = hg.getRoomtypeId();
				RoomType  roomType= roomTypeService.selectByPrimaryKey(roomTypeId);
				
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("roomType", roomType);
				m.put("hotel", hotel);
				m.put("order", rule);
				m.put("breakfastStr", breakfastStr);
				result.setData(m);
				result.setMsg("success");
				result.setState(1);
			}else{
				
				result.setMsg("error");
				result.setState(0);
			}
		}else{
			result.setMsg("error");
			result.setState(0);
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 订单信息预览
	* @param orderId
	* @return
	* @author huashuwen
	* @date 2017-11-15 10:39
	 */
	@RequestMapping("/toViewOrder")
	@ResponseBody
	public String toViewOrder(String orderId){
		HotelResult<OrderCustom> result = new HotelResult<OrderCustom>();
		if(dictList==null){
			dictList=CacheService.getAllDict();
		}
		OrderCustom rule = new OrderCustom();
		if(StringUtils.isNotBlank(orderId)){
			rule = orderService.selectOrderDetail(orderId);
			rule.getHotelGoods().setCancelStr(CacheService.getLabel("cancel", String.valueOf(rule.getHotelGoods().getCancel())));
			result.setData(rule);
			result.setMsg("success");
			result.setState(1);
		}else{
			result.setMsg("error");
			result.setState(0);
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 订单修改
	* @param bean
	* @return
	* @author huashuwen
	* @date 2017-11-15 10:39
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(Order bean){
		HotelResult<Order> result=new HotelResult<Order>();
		String orderId = bean.getOrderId();
		Order rule = new Order();
		if(StringUtils.isNotBlank(orderId)){
			rule = orderService.selectByPrimaryKey(orderId);
			if(StringUtils.isEmpty(rule)){
				result.setMsg("error");
				result.setMessage("订单id不存在");
				result.setState(0);
			}else{
				bean.setCreateTime(rule.getCreateTime());
				bean.setUpdateTime(new Date());
				bean.setStatus(1);
				bean.setOrderMoney(rule.getOrderMoney());
				bean.setOrderTime(rule.getOrderTime());
				bean.setOrderState(rule.getOrderState());
				bean.setOrderObject(rule.getOrderObject());
				orderService.update(bean);
				result.setMsg("success");
				result.setState(1);
			}
		}else{
			result.setMsg("error");
			result.setState(0);
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 订单信息保存操作
	* @param bean
	* @return
	* @author huashuwen
	* @date 2017-11-15 10:39
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(Order bean){
		HotelResult<String> result=new HotelResult<>();
		String buyerId = bean.getBuyerId();
		String shopId = bean.getShopId();
		String goodsId = bean.getGoodsId();
		String checkinPerson = bean.getCheckinPerson();
		String contactMobile = bean.getContactMobile();
		Date leaveTime = bean.getLeaveTime();
		Date comeTime = bean.getComeTime();
		if(StringUtils.isEmpty(buyerId)||
				StringUtils.isEmpty(shopId)||
				StringUtils.isEmpty(goodsId)||
				StringUtils.isEmpty(checkinPerson)||
				StringUtils.isEmpty(contactMobile)||
				StringUtils.isEmpty(leaveTime)||
				StringUtils.isEmpty(comeTime)){
			result.setState(0);
			result.setMsg("error");
			result.setMessage("参数为空，提交失败");
			return JSON.toJSONString(result);
		}
		//try {
			if (bean != null) {
				
				List<String> dateList = new ArrayList<String>();
				List<String> dateListTemp = new ArrayList<String>();
				SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
				Calendar calendar = null;
				Map<String,Integer> stockMap = new HashMap<String,Integer>();
				List<BigDecimal> priceList = new ArrayList<BigDecimal>();
				List<BigDecimal> basicPriceList = new ArrayList<BigDecimal>();
				calendar = Calendar.getInstance();
				String stock = "";
				String price = "";
				String basicPrice = "";
				String date = "";
				int isOpen = 0;
				int dayOfWeek1 = 0;
				HotelGoods goods =hotelGoodsService.selectInfoByPrimaryKey(goodsId);
				int goodsNum = "".equals(bean.getGoodsNum())
						|| bean.getGoodsNum() == null ? 1 : Integer
						.parseInt(bean.getGoodsNum());
				int days = DateUtils.daysBetween(comeTime, leaveTime);
				for (int i = 0; i < days; i++) {
					try {
						date = DateUtils.getAfterDay(sdf.format(comeTime), i,
								DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dateListTemp.add(date);
				}
				List<StockRule> stockRuleList = stockRuleService
						.selectByGoodsId(goodsId, sdf.format(comeTime));
				List<PriceRule> priceRuleList = priceRuleService
						.selectByGoodsId(goodsId, sdf.format(comeTime));
				List<SwitchRule> swithchRuleList = switchRuleService
						.selectByGoodsId(goodsId, sdf.format(comeTime));
				Hotel hotel = hotelService.selectByPrimaryKey(bean.getShopId());
				dateList.addAll(dateListTemp);
				
				
				for(SwitchRule switchRule:swithchRuleList){
					isOpen = switchRule.getIsOpen();
					Iterator<String> it = dateList.iterator(); 
					 while (it.hasNext()) { 
						 String date1  = it.next();  
						     try {
								if(DateUtils.compareDate(switchRule.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(switchRule.getEndTime(), sdf.parse(date1))>=0){
									 if(switchRule.getType()==2){
										 calendar.setTime(sdf.parse(date1));
										 dayOfWeek1 = calendar.get(7)-1;
										if(dayOfWeek1==0){
											dayOfWeek1=7;
										}
										if(String.valueOf(switchRule.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
											if(isOpen == 2){
												result.setState(0);
												result.setMsg("error");
												result.setMessage("所选商品未设定库存");
												return JSON.toJSONString(result);
											}
											it.remove();
											continue;
										}
									 }else{
										 if(isOpen == 2){
											 	result.setState(0);
												result.setMsg("error");
												result.setMessage("所选商品未设定库存");
												return JSON.toJSONString(result);
											}
											it.remove();
								     }
								 }
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				}
				
				
				dateList.clear();
				dateList.addAll(dateListTemp);
				// 计算 日期库存
				if (StringUtils.isEmpty(stockRuleList)) {
					result.setState(0);
					result.setMsg("error");
					result.setMessage("所选商品未设定库存");
					return JSON.toJSONString(result);
				}
				for (StockRule stockRule : stockRuleList) {
					stock = stockRule.getStock();
					Iterator<String> it = dateList.iterator();
					while (it.hasNext()) {
						String date1 = it.next();
						try {
							if (DateUtils.compareDate(stockRule.getStartTime(),
									sdf.parse(date1)) <= 0
									&& DateUtils.compareDate(stockRule.getEndTime(),
											sdf.parse(date1)) >= 0) {
								if (stockRule.getType() == 2) {
									calendar.setTime(sdf.parse(date1));
									dayOfWeek1 = calendar.get(7) - 1;
									if (dayOfWeek1 == 0) {
										dayOfWeek1 = 7;
									}
									if (String.valueOf(stockRule.getWeekStart())
											.indexOf(String.valueOf(dayOfWeek1)) >= 0) {
										stockMap.put(date1, Integer.parseInt(stock));
										it.remove();
										continue;
									}
								} else {
									stockMap.put(date1, Integer.parseInt(stock));
									it.remove();
								}
							}
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if (dateList.size() > 0) {
					result.setState(0);
					result.setMsg("error");
					result.setMessage("所选商品未设定库存");
					return JSON.toJSONString(result);
				}
				if (StringUtils.isEmpty(priceRuleList)) {
					result.setState(0);
					result.setMsg("error");
					result.setMessage("所选商品未设定价格，不能预定");
					return JSON.toJSONString(result);
				}
				
				
				// 计算单房售价
				dateList.addAll(dateListTemp);
				for (PriceRule priceRule : priceRuleList) {
					price = priceRule.getPrice();
					basicPrice = priceRule.getBasicPrice();
					if(StringUtils.isBlank(basicPrice)){
						basicPrice="0";
					}
					Iterator<String> it = dateList.iterator();
					while (it.hasNext()) {
						String date1 = it.next();
						try {
							if (DateUtils.compareDate(priceRule.getStartTime(),
									sdf.parse(date1)) <= 0
									&& DateUtils.compareDate(priceRule.getEndTime(),
											sdf.parse(date1)) >= 0) {
								if (priceRule.getType() == 2) {
									calendar.setTime(sdf.parse(date1));
									dayOfWeek1 = calendar.get(7) - 1;
									if (dayOfWeek1 == 0) {
										dayOfWeek1 = 7;
									}
									if (String.valueOf(priceRule.getWeekStart())
											.indexOf(String.valueOf(dayOfWeek1)) >= 0) {
										priceList.add(new BigDecimal(price));
										basicPriceList.add(new BigDecimal(basicPrice));
										it.remove();
										continue;
									}
								} else {
									priceList.add(new BigDecimal(price));
									basicPriceList.add(new BigDecimal(basicPrice));
									it.remove();
								}
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (dateList.size() > 0) {
					result.setState(0);
					result.setMsg("error");
					result.setMessage("所选商品未设定价格，不能预定");
					return JSON.toJSONString(result);
				}
				
				
				// 统计订单
				dateList.addAll(dateListTemp);
				List<Order> orderList = orderService.selectOrderInRange(bean);
				try {
				for (String date2 : dateList) {
					Date date3;
					
						date3 = sdf.parse(date2);
					
					int goodNum = 0;
					for (Order order : orderList) {
						if (DateUtils.compareDate(date3, order.getLeaveTime()) < 0
								&& DateUtils.compareDate(date3,
										order.getComeTime()) >= 0) {
							goodNum += Integer.parseInt(order.getGoodsNum());
						}
					}
					// 规则库存-已卖数量-预订数量
					if (stockMap.get(date2) - goodNum - goodsNum < 0) {
						result.setState(0);
						result.setMsg("error");
						result.setMessage("房间库存不足");
						return JSON.toJSONString(result);
					}
				}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 计算总价
				BigDecimal totalPrice = new BigDecimal("0");
				BigDecimal basicMoney = new BigDecimal("0");
				BigDecimal num = new BigDecimal(goodsNum);
				for(BigDecimal price1:priceList){
					totalPrice = totalPrice.add(price1);
				}
				for(BigDecimal price2:basicPriceList){
					basicMoney = basicMoney.add(price2);
				}
				totalPrice = totalPrice.multiply(num);
				basicMoney = basicMoney.multiply(num);
				totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
				basicMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
				UUID u = UUID.randomUUID();
				bean.setStatus(1);
				bean.setOrderId(u.toString());
				bean.setCreateTime(new Date());
				bean.setOrderTime(new Date());
				bean.setOrderState(OrderConstants.WAIT_TO_PAY);
				bean.setOrderName(hotel.getName()+"-"+goods.getGoodsName());
				bean.setOrderNum(createOrderNum());
				bean.setPayState(PlatConstants.PAY_NO);
				bean.setOrderMoney(totalPrice);
				bean.setBasicMoney(basicMoney);
				bean.setBuyerId(buyerId);
				bean.setOrderType(2);
				bean.setGoodsNum(String.valueOf(goodsNum));
				RoomType roomType = roomTypeService.selectByGoodsId(goodsId);
				String roomTypeName = roomType.getApartmentName();
				int cancel =goods.getCancel();
				String cancelTime = goods.getCancelTime();
				String confirmStr = goods.getConfirmStr();
				String hotelName = hotel.getName();
				String coverImg = hotel.getCoverImg();
				JSONObject json = new JSONObject();
				json.put("roomTypeName", roomTypeName);
				json.put("cancel", cancel);
				json.put("cancelTime", cancelTime);
				json.put("hotelName", hotelName);
				json.put("confirmStr", confirmStr);
				json.put("coverImg", coverImg);
				json.put("goodsName", goods.getGoodsName());
				String orderJson = json.toJSONString();
				bean.setOrderObject(orderJson);
				bean.setPayState(OrderConstact.OrderPayStatusEnum.UNPAID.getCode());
				orderService.insert(bean);
				result.setData(bean.getOrderId());
			}
//		} catch (Exception e) {
//			Exceptions.getStackTraceAsString(e);// 统一的异常处理
//			System.err.println(e.toString());
//		}
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	/**
	 * 计算订单价格
	* @param 
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-14 
	 */
	@RequestMapping("/computePrice")
	@ResponseBody
	public String computePrice(Order bean){
		HotelResult result=new HotelResult();
		List<String> dateList = new ArrayList<String>();
		List<String> dateListTemp = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
		Calendar calendar = null;
		Map<String,Integer> stockMap = new HashMap<String,Integer>();
		List<BigDecimal> priceList = new ArrayList<BigDecimal>();
		calendar = Calendar.getInstance();
		Date leaveTime = bean.getLeaveTime();
		Date comeTime = bean.getComeTime();
		String stock = "";
		String price = "";
		String date = "";
		int minStock = 0;
		int isOpen = 0;
		int dayOfWeek1=0;
		String goodsId = bean.getGoodsId();
		int goodsNum = "".equals(bean.getGoodsNum())||bean.getGoodsNum()==null?1:Integer.parseInt(bean.getGoodsNum());
		int days=DateUtils.daysBetween(comeTime,leaveTime);
		//try{
			for(int i=0;i<days;i++){
				try {
					date = DateUtils.getAfterDay(sdf.format(comeTime), i, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dateListTemp.add(date);
			}
			List<StockRule> stockRuleList = stockRuleService.selectByGoodsId(goodsId,sdf.format(comeTime));
			List<PriceRule> priceRuleList = priceRuleService.selectByGoodsId(goodsId, sdf.format(comeTime));
			List<SwitchRule> swithchRuleList = switchRuleService.selectByGoodsId(goodsId, sdf.format(comeTime));
			if(StringUtils.isEmpty(stockRuleList)){
				result.setState(0);
				result.setMsg("error");
				result.setMessage("所选商品未设定库存");
				return JSON.toJSONString(result);
			}
			dateList.addAll(dateListTemp);
			for(SwitchRule switchRule:swithchRuleList){
				isOpen = switchRule.getIsOpen();
				Iterator<String> it = dateList.iterator(); 
				 while (it.hasNext()) { 
					 String date1  = it.next();  
					     try {
							if(DateUtils.compareDate(switchRule.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(switchRule.getEndTime(), sdf.parse(date1))>=0){
								 if(switchRule.getType()==2){
									 calendar.setTime(sdf.parse(date1));
									 dayOfWeek1 = calendar.get(7)-1;
									if(dayOfWeek1==0){
										dayOfWeek1=7;
									}
									if(String.valueOf(switchRule.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
										if(isOpen == 2){
											result.setState(0);
											result.setMsg("error");
											result.setMessage("所选商品无库存");
											return JSON.toJSONString(result);
										}
										it.remove();
										continue;
									}
								 }else{
									 if(isOpen == 2){
										 	result.setState(0);
											result.setMsg("error");
											result.setMessage("所选商品无库存");
											return JSON.toJSONString(result);
										}
										it.remove();
							     }
							 }
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}
			dateList.clear();
			dateList.addAll(dateListTemp);
			for(StockRule stockRule:stockRuleList){
				stock = stockRule.getStock();
				Iterator<String> it = dateList.iterator(); 
				 while (it.hasNext()) { 
					 String date1  = it.next();  
					     try {
							if(DateUtils.compareDate(stockRule.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(stockRule.getEndTime(), sdf.parse(date1))>=0){
								 if(stockRule.getType()==2){
									 calendar.setTime(sdf.parse(date1));
									 dayOfWeek1 = calendar.get(7)-1;
									if(dayOfWeek1==0){
										dayOfWeek1=7;
									}
									if(String.valueOf(stockRule.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
										stockMap.put(date1, Integer.parseInt(stock));
										it.remove();
										continue;
									}
								 }else{
							    	 stockMap.put(date1, Integer.parseInt(stock));
										it.remove();
							     }
							 }
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}
			if(dateList.size()>0){
				 result.setState(0);
				 result.setMsg("error");
				 result.setMessage("所选商品未设定库存");
				 return JSON.toJSONString(result);
			 }
			if(StringUtils.isEmpty(priceRuleList)){
				result.setState(0);
				result.setMsg("error");
				result.setMessage("所选商品未设定价格，不能预定");
				return JSON.toJSONString(result);
			}
			dateList.addAll(dateListTemp);
			for(PriceRule priceRule:priceRuleList){
				price = priceRule.getPrice();
				Iterator<String> it = dateList.iterator(); 
				 while (it.hasNext()) { 
					 String date1  = it.next();  
					     try {
							if(DateUtils.compareDate(priceRule.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(priceRule.getEndTime(), sdf.parse(date1))>=0){
								 if(priceRule.getType()==2){
									 calendar.setTime(sdf.parse(date1));
									 dayOfWeek1 = calendar.get(7)-1;
									if(dayOfWeek1==0){
										dayOfWeek1=7;
									}
									if(String.valueOf(priceRule.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
										priceList.add(new BigDecimal(price));
										it.remove();
										continue;
									}
								 }else{
									 priceList.add(new BigDecimal(price));
										it.remove();
							     }
							 }
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} 
			}
			 if(dateList.size()>0){
				 result.setState(0);
				 result.setMsg("error");
				 result.setMessage("所选商品未设定价格，不能预定");
				 return JSON.toJSONString(result);
			 }
			//统计订单
			dateList.addAll(dateListTemp);
			List<Order> orderList = orderService.selectOrderInRange(bean);
			List<Integer> stockList = new ArrayList<Integer>();
			for(String date2:dateList){
				Date date3;
				try {
					date3 = sdf.parse(date2);
				
				int goodNum = 0;
				for(Order order :orderList){
					if(DateUtils.compareDate(date3,order.getLeaveTime())<0&&DateUtils.compareDate(date3,order.getComeTime())>=0){
						goodNum+=Integer.parseInt(order.getGoodsNum());
					}
				}
				//goodNumMap.put(date2,goodNum);
				//规则库存-已卖数量-预订数量
				if(stockMap.get(date2) - goodNum - goodsNum < 0){
					 result.setState(0);
					 result.setMsg("error");
					 result.setMessage("房间库存不足");
					 return JSON.toJSONString(result);
				}else{
					stockList.add(stockMap.get(date2) - goodNum);
				}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Collections.sort(stockList);
			minStock = stockList.get(0);
		/*}catch(Exception e){
			System.err.println(e.toString());
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return JSON.toJSONString(new HotelResult<Order>(2,"error"));
		}*/
		BigDecimal totalPrice = new BigDecimal("0");
		for(BigDecimal price1:priceList){
			totalPrice = totalPrice.add(price1);
		}
		totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("price", totalPrice.toString());
		dataMap.put("minStock", minStock);
		result.setState(1);
		result.setMsg("success");
		result.setData(dataMap);
		return JSON.toJSONString(result);
	}
	/**
	 * 查询支付状态
	* @param orderNum
	* @return
	* @return
	* @author huashuwen
	* @date 2018-2-07 10:39
	 */	
	@RequestMapping("/queryPayState")
	@ResponseBody
	public String queryPayState(String orderNum){
		HotelResult<Object> result = new HotelResult<Object>();
		Order order = orderService.selectByOrderNum(orderNum);
		int payState = order.getPayState();
		int payStatus =0;
		if(payState==1){
			payStatus =1;
		}
		Map dataMap=new HashMap();
		dataMap.put("payStatus", payStatus);
		result.setState(1);
		result.setMsg("success");
		result.setData(dataMap);
		return JSON.toJSONString(result);
	}
	
	/**
	 * 订单信息删除
	* @param id
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:39
	 */
	@RequestMapping("/del")
	@ResponseBody
	public HotelResult<Order> del(String id){
		
		try{
			orderService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<Order>(2,"error");
		}
		return new HotelResult<Order>(1,"success");
	}
	public String createOrderNum(){
		String orderNum = String.valueOf(System.nanoTime());
		orderNum = orderNum.substring(orderNum.length()-10, orderNum.length());
		Order order = orderService.selectByOrderNum(orderNum);
		if(StringUtils.isEmpty(order)){
			return orderNum;
		}else{
			return createOrderNum();
		}
	}
	
	/**
	 * 获取当天酒店的订单信息
	 * @param shopId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dailylOrder")
	public String dailyOrder(String shopId){
		HotelResult<Map<String,String>> result = new HotelResult<Map<String,String>>();
		try{
		Map<String,String> map = orderService.selectDailyByshopId(shopId);
		if(map != null){
			result.setData(map);
			result.setState(1);
			result.setMsg("success");
			return JSON.toJSONString(result);
		}else{
			map = new HashMap<>();
			map.put("ACTUALMONEY", "0");
			map.put("CANCELCOUNT", "0");
			map.put("DATACOUNT", "0");
			result.setData(map);
			result.setState(1);
			result.setMsg("success");
			return JSON.toJSONString(result);
		}
		}catch(Exception e){
			e.printStackTrace();
			result.setState(0);
			result.setMsg("error");
			return JSON.toJSONString(result);
		}
	}
	/**
	 * 订单列表查询
	 * @param condition
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * * （列表需要改）
	 */
	@ResponseBody
	@RequestMapping("/orderList")
	public String queryOrder(OrderCondition condition, 
			@RequestParam(defaultValue="1") String pageNum,
			@RequestParam(defaultValue="1")	String pageSize){
			//try{
			String time = condition.getEndTime();
			String endTime = null;
			if(!StringUtils.isBlank(time)){
				SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS);
				Date dateEnd = DateUtils.parseDate(time,DateUtils.DEFAULT_DATE_FORMAT_YMD);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateEnd);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				endTime = sdf.format(calendar.getTime());
			}
			Map<String,Object> map = new HashMap<>();
			map.put("shopId", condition.getShopId());
			map.put("pageNum",pageNum);
			map.put("pageSize",pageSize);
			map.put("orderNum", condition.getOrderNum());
			map.put("contactMobile", condition.getContactMobile());
			map.put("orderState", condition.getOrderState());
			map.put("handleMan", condition.getHandleMan());
			map.put("startTime", condition.getStartTime());
			map.put("endTime", endTime);
			map.put("checkinPerson", condition.getCheckinPerson());
			PageView<OrderCustomDo> view = orderService.selectOrderByPage(map);
			System.err.println("3:"+view.getList().size());
			List<OrderCustom> resultList = new ArrayList<OrderCustom>();
			List<OrderCustomDo> list = view.getList();
			for (int i = 0; i < list.size(); i++) {
	        	OrderCustomDo orderCustomDo = list.get(i);
	        	OrderCustom orderCustom = orderService.orderCustomDo2orderCustom(orderCustomDo);
	        	HotelGoods goods = hotelGoodsService.selectByPrimaryKey(orderCustom.getGoodsId());
	            Date date =orderCustom.getComeTime();
	            String strTime = goods.getCancelTime();
	            if(StringUtils.isBlank(strTime)){
	                strTime = "0";
	            }
	            int time1 = Integer.parseInt(strTime);
	            Date cancelTime =DateUtils.getAddDate(date, time1*60*60*1000);
	            orderCustom.setLastestCancel(cancelTime);
	            resultList.add(orderCustom);
	        }
			System.err.println("1:"+resultList.size());
			changeToOrderCustom(resultList);
			System.err.println("2:"+resultList.size());
			Integer PageNum=view.getPageNum();
			Integer PageSize=view.getPageSize();
			Integer total=(int)view.getTotal();
			return JSON.toJSONString(new HotelResult<>(resultList, PageNum, PageSize, total,1,"查询成功"));
//		}catch(Exception e){
//			HotelResult result = new HotelResult<>();
//			result.setMsg("error");
//			result.setState(0);
//			return JSON.toJSONString(result);
//		}
	}
	/** 
	 * 待处理的订单列表
	 * @param userId
	 * @return
	 * （列表需要改）
	 */
	
	@ResponseBody
	@RequestMapping(value="/susOrder")
	public String suspendingOrder(String shopId){
		HotelResult<List<Object>> result  = new HotelResult<>();
		List<OrderCustom> orders= orderService.selectSuspOrder(shopId);
		Map<String,Object> paraMap = new HashMap<>(8);
		paraMap.put("sellerId", shopId);
		ArrayList<String> orderStates =new ArrayList<>();
		orderStates.add("1");
		orderStates.add("2");
		orderStates.add("6");
		paraMap.put("orderStateArr",orderStates);
		PageView<OrderBaseInfoVo> lineOrder = orderBaseInfoService.listOrder(paraMap,1,20);
		List<OrderBaseInfoVo> lineOrders = lineOrder.getList();
		List<Object> list = new ArrayList<>();
		changeToOrderCustom(orders);
		if(orders.size()>0){
			for(Object o: orders){
				list.add(o);
			}
		}
		//TODO
		/*if(lineOrders!=null){
			for(Object o: lineOrders){
				list.add(o);
			}
		}*/
		Collections.sort(list,new Comparator<Object>(){
			public int compare(Object o1, Object o2) {  
	            if(o1.hashCode() > o2.hashCode()){  
                    return 1;  
                }  
                if(o1.hashCode() > o2.hashCode()){  
                    return 0;  
                }  
                return -1; 
            }
		});
		result.setMsg("success");
		result.setData(list);
		result.setState(1);
		return JSON.toJSONString(result);
	}
	/**
	 * 获得 订单所有的状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/orderLabel")
	public String  getOrderLabel(){
		HotelResult<List<OrderLabel>> result = new HotelResult<>();
		List<OrderLabel> list = OrderLabel.getLabellist();
		result.setMsg("success");
		result.setData(list);
		result.setState(1);
		return JSON.toJSONString(result);
		
	}

	/**
	 * 处理订单
	 * @param orderNum 订单号
	 * @param state 订单状态
	 * @return 处理后的订单
	 */
	@ResponseBody
	@RequestMapping(value="/orderState")
	public String dealOrder(String orderNum,int state){
		HotelResult<Order> result = new HotelResult<>();
		Order order = null;
		boolean flag = false;
		int[] payStates= {1001,1002,1003};
		int[] states= ArrayUtils.addAll(OrderLabel.getLableArray(), payStates);
		for (int i : states) {
			if(i==state){
				flag =true;
			} 
		}
		 if(!flag){
			result.setMsg("error");
			result.setState(0);
			result.setMessage("无效的订单状态");
			return JSON.toJSONString(result);
		 }
		if(StringUtils.isNotBlank(orderNum)){
			order = orderService.selectByOrderNum(orderNum);
		}else{
			 result.setMsg("error");
			 result.setState(0);
			 result.setMessage("订单号为空");
			 return JSON.toJSONString(result);
		}
		 if(order == null){
			 result.setMsg("error");
			 result.setState(0);
			 result.setMessage("订单不存在");
			 return JSON.toJSONString(result);
		 }
		 if(state == 1001 || state==1002 || state ==1003){
			 order.setPayState(state);
		 }else{
			 order.setOrderState(state);
		 }
		 order.setUpdateTime(new Date());
		 if(state==301){
			 order.setArriveTime(new Date());
		 }
		 int num = orderService.update(order);
		 if(num>0){
			 result.setData(order);
			 result.setMsg("success");
			 result.setState(1);
			 return JSON.toJSONString(result);
		 }else{
			 result.setMsg("error");
			 result.setState(0);
			 result.setMessage("提交失败");
			 return JSON.toJSONString(result);
		 }	  
		 
	}
	
	private void changeToOrder(List<Order> list){
		for (Order order : list) {
			String json = order.getOrderObject();
			JSONObject obj= JSON.parseObject(json);
			String roomTypeName=obj.getString("roomTypeName");
			int cancel =obj.getInteger("cancel");
			String cancelTime = obj.getString("cancelTime");
			String hotelName = obj.getString("hotelName");
			String coverImg = obj.getString("coverImg");
			Hotel hotel = order.getHotel();
			if(hotel==null){
				hotel=new Hotel();
				hotel.setId(order.getShopId());
				hotel.setCoverImg(coverImg);
				hotel.setName(hotelName);
			}
			order.setHotel(hotel);
			order.setRoomTypeName(roomTypeName);
		} 
	}
	
	private void changeToOrderCustom(List<OrderCustom> list){
		for (OrderCustom orderCustom : list) {
			String json = orderCustom.getOrderObject();
			if(!StringUtils.isBlank(json)){
			JSONObject obj= JSON.parseObject(json);
			String roomTypeName=obj.getString("roomTypeName");
			int cancel =obj.getInteger("cancel");
			String confirmStr = obj.getString("confirmStr");
			String cancelTime = obj.getString("cancelTime");
			String hotelName = obj.getString("hotelName");
			orderCustom.setHotelName(hotelName);
			orderCustom.setRoomTypeName(roomTypeName);
			HotelGoods goods = orderCustom.getHotelGoods();
			goods.setCancel(cancel);
			goods.setCancelTime(cancelTime);
			goods.setConfirmStr(confirmStr);
			}
		} 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/initData")
	public String initData(String shop){
		List<Order> order = null;
		if("02d218ba969641ea8aec3a9195821a62initorderdata".equals(shop)){
		   order =	orderService.selectAll();
		   for (Order bean : order) {
			   JSONObject json = new JSONObject();
			    String goodsId= bean.getGoodsId();
			    String shopId =bean.getShopId();
			    String roomTypeName =  roomTypeService.selectByGoodsId(goodsId).getApartmentName();
			    HotelGoods goods = hotelGoodsService.selectByPrimaryKey(goodsId);
			    int cancel=goods.getCancel();
			    String confirmStr = goods.getConfirmStr();
			    String cancelTime= goods.getCancelTime();
			    Hotel hotel = hotelService.selectByPrimaryKey(shopId);
			    String hotelName = hotel.getName();
			    String coverImg = hotel.getCoverImg();
				json.put("roomTypeName", roomTypeName);
				json.put("cancel", cancel);
				json.put("cancelTime", cancelTime);
				json.put("hotelName", hotelName);
				json.put("confirmStr", confirmStr);
				json.put("coverImg", coverImg);
				String orderJson = json.toJSONString();
				bean.setOrderObject(orderJson);
				orderService.update(bean);
		   }
		}
		return JSON.toJSONString(order);
	}
	String getOrderStateStr(int orderState){
		String stateStr = "";
		switch(orderState){
		case 101:
			stateStr = "待付款";
			break;
		case 201:
			stateStr = "已付款";
			break;
		case 202:
			stateStr = "已确认";
			break;
		case 405:
			stateStr = "已取消";
			break;
		case 406:
			stateStr = "已取消";
			break;
		case 305:
			stateStr = "已取消";
			break;
		case 302:
			stateStr = "已取消";
			break;
		case 303:
			stateStr = "已取消";
			break;
		case 304:
			stateStr = "已取消";
			break;
		case 407:
			stateStr = "已取消";
			break;
		case 301:
			stateStr = "已入住";
			break;
		case 401:
			stateStr = "已评价";
			break;
		case 402:
			stateStr = "已关闭";
			break;
		case 6:
			stateStr = "退款申请";
			break;
		}
		
		return stateStr;
	}
	@ResponseBody
	@RequestMapping(value="/testMes")
	public String testMes(String orderId,HttpServletRequest request){
		//Order o1= orderService.selectByOrderNum(orderNo);
		//OrderBaseInfo o = orderService.order2orderBaseInfo(o1);
		//sellerReceiveConfigService.sendHotelMessageToBuyer(o,1);
		//sellerReceiveConfigService.sendHotelMessageToBuyer(o,3);
		//sellerReceiveConfigService.sendHotelMessageToBuyer(o,3);
		//sellerReceiveConfigService.sendHotelMessageToBuyer(o,4);
		//sellerReceiveConfigService.sendHotelMessageToBuyer(o,5);
		//sellerReceiveConfigService.sendHotelMessageToBuyer(o,6);
		/*Date date = new Date();
		financeService.createBill();
		financeService.generatedBills();*/
		return "";
	}
}
