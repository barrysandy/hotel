package com.zzk.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.cluster.ClusterState.Custom;
import org.hibernate.dialect.identity.GetGeneratedKeysDelegate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zzk.util.HotelResult;
import com.zzk.util.Page;
import com.zzk.util.PageView;
import com.zzk.util.DateUtils;
import com.zzk.util.Result;
import com.zzk.vo.OrderBaseInfoVo;
import com.zzk.vo.OrderDetailInfoVo;
import com.zzk.common.OrderConstact;
import com.zzk.common.OrderConstants;
import com.zzk.dao.HotelPolicyMapper;
import com.zzk.dao.OrderBaseInfoMapper;
import com.zzk.dao.OrderDetailInfoMapper;
import com.zzk.dao.OrderMapper;
import com.zzk.dao.RefundInfoMapper;
import com.zzk.entity.Finance;
import com.zzk.entity.FinanceInfo;
import com.zzk.entity.Hotel;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.HotelPolicy;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderBaseInfoCustom;
import com.zzk.entity.OrderCustom;
import com.zzk.entity.OrderCustomDo;
import com.zzk.entity.OrderDetailInfo;
import com.zzk.entity.RefundInfo;
import com.zzk.service.FinanceService;
import com.zzk.service.OrderService;
import com.zzk.service.StockRuleService;

/**
 *
 * 订单信息
 * @author：sty
 * @date：2017-11-02 10:39
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private FinanceService financeService;
    @Resource
    private MessageService messageService;
    @Resource
    private StockRuleService stockRuleService;
    @Resource
    private OrderDetailInfoService orderDetailInfoService;
    @Resource
    private HotelGoodsService hotelGoodsService;
    @Resource
    private OrderDetailInfoMapper orderDetailInfoMapper;
    @Resource
    private HotelService hotelService;
    @Resource
    private OrderBaseInfoMapper orderBaseInfoMapper;
    @Resource
    private HotelOrderBaseInfoService hotelOrderBaseInfoService;
    @Resource
    private HotelPolicyMapper hotelPolicyMapper;
    @Resource
    private RefundInfoMapper refundInfoMapper;
    
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);;

   /**
     * 分页条件查询
     */
    @Override
    public PageView<OrderCustomDo> selectOrderByPage(Map map) {
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        List<OrderCustomDo> list = orderMapper.selectOrderByPage(map);
        
        return new PageView<OrderCustomDo>(list);
    }
    
    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return orderMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public Order selectByPrimaryKey(String orderId) {
    	OrderBaseInfo orderBaseInfo = orderMapper.selectByPrimaryKey(orderId);
    	return orderBaseInfo2Order(orderBaseInfo);
    }
    
    /**
     * 更新
     */
    @Override
    public int update(Order bean) {
        int orderState = bean.getOrderState();
        if(orderState==301){
            updateFinanceByOrder(bean);
        }
        OrderBaseInfo orderBaseInfo = order2orderBaseInfo(bean);
        return orderMapper.updateByPrimaryKey(orderBaseInfo);
    }
    /**
     *
     * 新增
     * @param bean
     * @return
     * @author：sty
     * @date：2017-11-02 10:39
     */
    @Override
    public int insert(Order bean) {
    	
    	String json = bean.getOrderObject();
    	JSONObject jo = JSONObject.parseObject(json);
    	String hotelName = (String)jo.get("hotelName");
    	int cancel = jo.getIntValue("cancel");
    	String cancelTime = jo.getString("cancelTime");
    	String coverImg = jo.getString("coverImg");
    	String roomTypeName = jo.getString("roomTypeName");
    	String goodsName = jo.getString("goodsName");
    	OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
    	orderDetailInfo.setOrderNo(bean.getOrderNum());
    	orderDetailInfo.setId(UUID.randomUUID().toString());
    	orderDetailInfo.setSkuName(goodsName);
    	orderDetailInfo.setImage(coverImg);
    	orderDetailInfo.setProductName(roomTypeName);
    	orderDetailInfo.setProductId(bean.getGoodsId());
    	orderDetailInfo.setTotalPrice(bean.getOrderMoney());
    	orderDetailInfo.setCreateTime(new Date());
    	orderDetailInfo.setStatus(1);
    	orderDetailInfo.setCancel(cancel);
    	orderDetailInfo.setCancelTime(cancelTime);
    	orderDetailInfoService.insert(orderDetailInfo);
    	
    	OrderBaseInfo orderBaseInfo = order2orderBaseInfo(bean);
    	orderBaseInfo.setSellerName(hotelName);
    	int code =	orderMapper.insert(orderBaseInfo);
        final String shopId = bean.getShopId();
        if(code>0){
        	executorService.execute(new Runnable(){  
                public void run(){  
                	messageService.sendMessage(shopId, MessageService.NEWORDER_SENDTYPE);
                }  
            });
        }
        return code;
    }

    /**
     *
     * 逻辑删除
     * @param bean
     * @return
     * @author：sty
     * @date：2017-11-02 10:39
     */
    @Override
    public int delete(String id) {
        OrderBaseInfo bean = orderMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return orderMapper.updateByPrimaryKey(bean);
    }

    @Override
    public List<Order> selectOrderInRange(Order bean) {
    	OrderBaseInfo orderDo =  order2orderBaseInfo(bean);
    	List<OrderBaseInfo> list = orderMapper.selectOrderInRange(orderDo);
    	List<Order> resultList = new ArrayList<>();
    	for (OrderBaseInfo baseInfo: list) {
    		resultList.add(orderBaseInfo2Order(baseInfo));
    	}
        return resultList;
    }
    /**
     * 通过买家Id查询  暂时废弃
     */
    @Override
    public List<Order> selectOrderByBuyerId(Map map) {
        return orderMapper.selectOrderByBuyerId(map);
    }
    /**
     * 通过买家ID分页查询
     */
    @Override
    public PageView<OrderBaseInfo> selectOrderByBuyerIdNew(Map map,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderBaseInfo> list= orderMapper.selectOrderByBuyerIdNew(map);
        return new PageView<>(list);
    }
    /**
     * 根据酒店ID查询订单列表
     */
    @Override
    public List<Order> selectOrderByShopId(Map map) {
    	 List<OrderBaseInfo> list= orderMapper.selectOrderByShopId(map);
         List<Order> resultList = new ArrayList<>();
         for (OrderBaseInfo bean:list) {
         	resultList.add(orderBaseInfo2Order(bean));
         }
    	
        return resultList;
    }
    /**
     * 根据酒店ID查询订单总条数
     */
    @Override
    public int selectCountByShopId(Map map) {
        return orderMapper.selectCountByShopId(map);
    }
    /**
     * 根据酒店ID查询返回MAP
     */
    @Override
    public List<HashMap<String,Object>> selectByHotelId(Order bean) {
    	OrderBaseInfo orderDo =  order2orderBaseInfo(bean);
        return orderMapper.selectOrderByHotelId(orderDo);
    }
  //更新订单未付款超时取消
    @Override
    @Scheduled(cron="0 * * * * ?")
    public void updateOrderNotPay() {
    	Date date = new Date();
        orderMapper.updateOrderNotPay(date);
    }
    //更新订单未评价默认结束
    @Override
    @Scheduled(cron="0 1 * * * ?")
    public void updateOrderDefaultEnd() {
    	Date date = new Date();
        orderMapper.updateOrderDefaultEnd(date);
    }
    //每日更新已入住
    @Override
    @Scheduled(cron="5 0 0 * * ?")
    public void updateOrderCheckIn() {
    	orderMapper.updateOrderCheckIn();
    }
  //每2分钟查询未确认的超时订单
    @Override
    @Scheduled(cron="0 0/5 * * * ?")
    public void dealNotConfirm() {
    	List<OrderCustomDo> orders = orderMapper.selectByOrderState(OrderConstants.WAIT_TO_CONFIRM);
    	if(orders.size()>0){
    		for(OrderCustomDo order:orders){
    			HotelGoods goods = order.getHotelGoods();
    			if(goods!=null){
    				long time=0L;
    				if(goods.getConfirm()==2){
    					 time = order.getOrderTime().getTime()+30*60L;
    				}else if(goods.getConfirm()==0){
    					if(com.zzk.util.StringUtils.isNotBlank(goods.getConfirmTime())){
    						time = order.getOrderTime().getTime()+ (long)(Double.valueOf(goods.getConfirmTime())*3600L);
    					}
    				}
    				if(time>System.currentTimeMillis()){
    					refundApply(order.getOrderNo(),order.getBuyerId(),"");
    				}
    			}
    		}
    	}
    }
    /**
     * 根据订单号查询
     */
    @Override
    public Order selectByOrderNum(String orderNum) {
    	OrderBaseInfo baseInfo= orderMapper.selectByOrderNum(orderNum);
    	return orderBaseInfo2Order(baseInfo);
    }
    @Override
    public Order selectRecendOrderByUserId(String shopId) {
    	OrderBaseInfo baseInfo= orderMapper.selectRecentOrderByUserId(shopId);
    	return orderBaseInfo2Order(baseInfo);
    }
    /*---标记----*/
    @Override
    public OrderCustom selectOrderDetail(String orderId) {
        OrderCustomDo customDo = orderMapper.selectOrderDetail(orderId);
        OrderDetailInfo detailInfo = customDo.getOrderDetailInfo();
        
        if(detailInfo!=null){
        	String productId = detailInfo.getProductId();
        	if(StringUtils.isNotBlank(productId)){
        		HotelGoods goods = hotelGoodsService.selectByPrimaryKey(productId);
        		Date date =customDo.getComeTime();
        		
                String strTime = goods.getCancelTime();
                if(StringUtils.isBlank(strTime)){
                    strTime = "0";
                }
                int time = Integer.parseInt(strTime);
                Date cancelTime =DateUtils.getAddDate(date, time*60*60*1000);
                customDo.setLastestCancel(cancelTime);
            }
        }
        OrderCustom custom =orderCustomDo2orderCustom(customDo) ;
        System.err.println(JSON.toJSONString(custom));
        return custom;
    }
    @Override
    public List<OrderCustom> selectSuspOrder(String shopId) {
    	List<OrderCustomDo> list = orderMapper.selectSuspOrder(shopId);
    	List<OrderCustom> resultList =  new ArrayList<>();
    	for(OrderCustomDo orderCustomDo:list){
    		resultList.add(orderCustomDo2orderCustom(orderCustomDo));
    	}
    	return resultList;
    }
    @Override
    public Map<String, String> selectDailyByshopId(String shopId) {
        Map<String,String> map = new HashMap<>();
        map.put("shopId", shopId);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS);
        Date date = DateUtils.getDate();
        String endTime = dateFormat.format(date);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        String startTime= dateFormat.format(date);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return orderMapper.selectDailyByshopId(map);
    }
    /**
     * 通过ID更新财务添加订单的财务状态
     * @param bean
     * @return
     */
    public int updateFinanceByOrder(Order bean){
    	FinanceInfo finance = null;
        finance  = financeService.selectNotGenerateFin(bean.getShopId());
        if(finance==null){
            return	financeService.insertSingleFinance(bean);
        }else{
            String billId= finance.getId();
            bean.setBillId(billId);
            BigDecimal actualMoney = bean.getOrderMoney();
            BigDecimal basicMoney = bean.getBasicMoney();
            if(actualMoney==null){
                actualMoney= new BigDecimal("0");
            }
            if(basicMoney==null){
                basicMoney= new BigDecimal("0");
            }
            BigDecimal commMoney = actualMoney.subtract(basicMoney);
            finance.setOrderMoney(finance.getOrderMoney().add(actualMoney));
            finance.setActualMoney(finance.getActualMoney().add(basicMoney));
            finance.setCommMoney(finance.getCommMoney().add(commMoney));
            finance.setOrderCount(finance.getOrderCount()+1);
            return financeService.updateByPrimaryKey(finance);
        }
    }

    @Override
    public List<HashMap<String, Object>> selectByRoomtypeList(List<String> roomtypeList,Date beginDate,Date endDate) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("comeTime", beginDate);
        map.put("leaveTime", endDate);
        map.put("list", roomtypeList);
        return orderMapper.selectByRoomtypeList(map);
    }
    /**
     * 根据酒店ID查询订单
     */
    @Override
    public List<HashMap<String, Object>> selectSaleNumMap(String hotelId) {
        return orderMapper.selectSaleNumMap(hotelId);
    }
    /**
     * 根据账ID分页查询订单
     */
    @Override
    public PageView<OrderBaseInfo> selectByBillId(String billId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderBaseInfo> list = orderMapper.selectByBillId(billId);
        
        return new PageView<OrderBaseInfo>(list);
    }
    /**
     * 查询每日订单详情
     */
    @Override
    public List<Map<String, Object>> selectDailyCountByShopId(Map map) {
        return orderMapper.selectDailyCountByShopId(map);
    }
    /**
     * 查询所有订单
     */
    @Override
    public List<Order> selectAll() {
    	List<OrderBaseInfo> orderBaseInfos= orderMapper.selectAll();
    	List<Order> orders= new ArrayList<Order>();
    	for(OrderBaseInfo orderBaseInfo: orderBaseInfos){
    		orders.add(orderBaseInfo2Order(orderBaseInfo));
    	}
    	return orders;
    }
    @Override
    public Order orderBaseInfo2Order(OrderBaseInfo a){
    	if(a!=null){
    		Order b = new Order();
        	b.setOrderId(a.getId());
        	b.setOrderName(a.getOrderName());
        	b.setShopId(a.getSellerId());
        	b.setBuyerId(a.getBuyerId());
        	b.setOrderMoney(a.getOrderMoney());
        	b.setActualMoney(a.getActualMoney());
        	b.setOrderTime(a.getOrderTime());
        	b.setPayMode(a.getPayMode());
        	b.setOrderState(a.getOrderState());
        	b.setContactMobile(a.getContactPhone());
        	b.setStatus(a.getStatus());
        	b.setCreateTime(a.getCreateTime());
        	b.setUpdateTime(a.getUpdateTime());
        	b.setPayState(a.getPayState());
        	b.setOrderNum(a.getOrderNo());
        	b.setBillId(a.getBillId());
        	b.setOpenId(a.getOpenid());
        	if(StringUtils.isNotBlank(a.getCurrentTimeLong())){
        		b.setPayNum(a.getOrderNo()+"/"+a.getCurrentTimeLong());
        	}
        	b.setPayId(a.getPayOrderId());
        	if(a.getCommission()!=null){
        		b.setBasicMoney(a.getOrderMoney().subtract(a.getCommission()));
        	}
        	b.setGoodsId(a.getGoodsId());
        	b.setGoodsNum(a.getGoodsNum());
        	b.setCheckinPerson(a.getCheckinPerson());
        	b.setRemark(a.getRemark());
        	b.setComeTime(a.getComeTime());
        	b.setLeaveTime(a.getLeaveTime());
        	b.setArriveTime(a.getArriveTime());
        	b.setOrderType(a.getOrderType());
        	b.setNickName(a.getNickName());
        	OrderDetailInfo info= orderDetailInfoMapper.selectByOrderNo(a.getOrderNo());
        	JSONObject json = new JSONObject();
        	json.put("roomTypeName", info.getProductName());
    		json.put("cancel", info.getCancel());
    		json.put("cancelTime", info.getCancelTime());
    		json.put("hotelName", a.getSellerName());
    		json.put("coverImg", info.getImage());
        	b.setOrderObject(json.toJSONString());
        	return b;
    	}else{
    		return null;
    	}
    	
    }
    @Override
    public OrderBaseInfo order2orderBaseInfo(Order a){
    	if(a!=null){
    		OrderBaseInfo b = new OrderBaseInfo();
        	b.setId(a.getOrderId());
        	b.setOrderName(a.getOrderName());
        	b.setSellerId(a.getShopId());
        	b.setBuyerId(a.getBuyerId());
        	b.setOrderMoney(a.getOrderMoney());
        	b.setActualMoney(a.getActualMoney());
        	b.setOrderTime(a.getOrderTime());
        	b.setPayMode(a.getPayMode());
        	b.setOrderState(a.getOrderState());
        	b.setContactPhone(a.getContactMobile());
        	b.setStatus(a.getStatus());
        	b.setCreateTime(a.getCreateTime());
        	b.setUpdateTime(a.getUpdateTime());
        	b.setPayState(a.getPayState());
        	b.setOrderNo(a.getOrderNum());
        	b.setBillId(a.getBillId());
        	b.setOpenid(a.getOpenId());
        	if(StringUtils.isNotBlank(a.getPayNum())){
        		String payNum = a.getPayNum();
        		b.setCurrentTimeLong(payNum.split("/")[1]);
        	}
        	b.setPayOrderId(a.getPayId());
        	if(a.getBasicMoney()!=null){
        		b.setCommission(a.getOrderMoney().subtract(a.getBasicMoney()));
        	}
        	b.setGoodsId(a.getGoodsId());
        	b.setGoodsNum(a.getGoodsNum());
        	b.setCheckinPerson(a.getCheckinPerson());
        	b.setRemark(a.getRemark());
        	b.setComeTime(a.getComeTime());
        	b.setLeaveTime(a.getLeaveTime());
        	b.setArriveTime(a.getArriveTime());
        	b.setOrderType(a.getOrderType());
        	b.setNickName(a.getNickName());
        	if(a.getOrderObject()!=null){
        		String json = a.getOrderObject();
            	JSONObject object = JSONObject.parseObject(json);
            	String hotelName = object.getString("hotelName");
            	b.setSellerName(hotelName);
            	
        	}else{
        		Hotel hotel = hotelService.selectByPrimaryKey(b.getSellerId());
        		if(hotel!=null){
        			b.setSellerName(hotel.getName());
        		}
            	
        	}
        	return b;
    	}else{
    		return null;
    	}
    	
    }
    @Override
    public OrderCustom orderCustomDo2orderCustom(OrderCustomDo a){
    	if(a!=null){
	    	OrderCustom b = new OrderCustom();
	    	b.setOrderId(a.getId());
	    	b.setOrderName(a.getOrderName());
	    	b.setShopId(a.getSellerId());
	    	b.setBuyerId(a.getBuyerId());
	    	b.setOrderMoney(a.getOrderMoney());
	    	b.setActualMoney(a.getActualMoney());
	    	b.setOrderTime(a.getOrderTime());
	    	b.setPayMode(a.getPayMode());
	    	b.setOrderState(a.getOrderState());
	    	b.setContactMobile(a.getContactPhone());
	    	b.setStatus(a.getStatus());
	    	b.setCreateTime(a.getCreateTime());
	    	b.setUpdateTime(a.getUpdateTime());
	    	b.setPayState(a.getPayState());
	    	b.setOrderNum(a.getOrderNo());
	    	b.setBillId(a.getBillId());
	    	b.setOpenId(a.getOpenid());
	    	b.setOrderType(a.getOrderType());
	    	if(StringUtils.isNotBlank(a.getCurrentTimeLong())){
	    		b.setPayNum(a.getOrderNo()+"/"+a.getCurrentTimeLong());
	    	}
	    	b.setPayId(a.getPayOrderId());
	    	if(a.getCommission()!=null){
	    		b.setBasicMoney(a.getOrderMoney().subtract(a.getCommission()));
	    	}
	    	
	    	b.setGoodsId(a.getGoodsId());
	    	b.setGoodsNum(a.getGoodsNum());
	    	b.setCheckinPerson(a.getCheckinPerson());
	    	b.setRemark(a.getRemark());
	    	b.setComeTime(a.getComeTime());
	    	b.setLeaveTime(a.getLeaveTime());
	    	b.setArriveTime(a.getArriveTime());
	    	b.setHotelGoods(a.getHotelGoods());
	    	b.setHotelName(a.getHotelName());
	    	b.setRoomTypeName(a.getRoomTypeName());
	    	b.setHotelGoods(a.getHotelGoods());
	    	b.setLastestCancel(a.getLastestCancel());
	    	b.setNickName(a.getNickName());
	    	OrderDetailInfo info= orderDetailInfoMapper.selectByOrderNo(a.getOrderNo());
	    	JSONObject json = new JSONObject();
	    	json.put("roomTypeName", info.getProductName());
			json.put("cancel", info.getCancel());
			json.put("cancelTime", info.getCancelTime());
			json.put("hotelName", a.getSellerName());
			json.put("coverImg", info.getImage());
	    	b.setOrderObject(json.toJSONString());
	    	return b;
    	}else{
    		return null;
    	}
    }
    
    @Override
    public PageView<OrderBaseInfoVo> selectByBuyerId(String userId, String orderState, Page pager) {
    	if (pager == null) {
            pager = new Page<>();
        }
       
            Integer orderStateInt = Integer.parseInt(orderState);
            
            List<String> orderStateArr = assembleBuyOrderState(orderStateInt);
            System.out.println("userId="+userId+":orderState:"+orderStateArr+"pageSize="+pager.getpageSize()+":"+pager.getPageNumber());
            Map<String,Object> map = new HashMap<>();
            map.put("buyerId", userId);
            map.put("orderStateArr", orderStateArr);
            PageHelper.startPage(pager.getPageNumber(), pager.getpageSize());
            List<OrderBaseInfoCustom> orderCustom = orderBaseInfoMapper.selectByBuyerId(map);
            PageView<OrderBaseInfoCustom> data = new PageView<>(orderCustom);
            PageView<OrderBaseInfoVo> voData = assembleMobileClientOrderList(data);
            System.out.println("pageVo:"+voData.getList().size());
            return voData;
        
    }
    
    private List<String> assembleBuyOrderState(int state){
    	ArrayList<String> list = new ArrayList<>();
    	if(state== 1){
    		list.add(String.valueOf(OrderConstact.OrderStatusEnum.WAITPAY.getCode()));
    		return list;
    	}
    	if(state == 2){
    		list.add(String.valueOf(OrderConstact.OrderStatusEnum.WAITCONSUME.getCode()));
    		return list;
    	}
    	if(state== 3){
    		list.add(String.valueOf(OrderConstact.OrderStatusEnum.WAITEVALUATE.getCode()));
    		list.add(String.valueOf(OrderConstact.OrderStatusEnum.COMMENTED.getCode()));
    		list.add(String.valueOf(OrderConstact.OrderStatusEnum.COMPLETED.getCode()));
    		return list;
    	}
    	if(state == 4){
    		list.add(String.valueOf(OrderConstact.OrderStatusEnum.REFUNDAPPLY.getCode()));
    		list.add(String.valueOf(OrderConstact.OrderStatusEnum.REFUNDING.getCode()));
    		list.add(String.valueOf(OrderConstact.OrderStatusEnum.REFUNDSUCCESS.getCode()));
    		return list;
    	}
    	return null;
    }

    private PageView<OrderBaseInfoVo> assembleMobileClientOrderList(PageView<OrderBaseInfoCustom> orderList) {
        List<OrderBaseInfoCustom> orderBaseInfoCustomList = orderList.getList();
        List<OrderBaseInfoVo> orderBaseInfoVoList = new ArrayList<>();
        for (int i = 0; i < orderBaseInfoCustomList.size(); i++) {
            OrderBaseInfoCustom order = orderBaseInfoCustomList.get(i);
            OrderDetailInfo orderDetail = order.getOrderDetailInfo();
            OrderBaseInfoVo orderVo = new OrderBaseInfoVo();
            OrderDetailInfoVo detailVo = new OrderDetailInfoVo();
            orderVo.setOrderStatusInfo(null);
            orderVo.setId(order.getId());
            orderVo.setPayState(order.getPayState());
            orderVo.setOrderNo(order.getOrderNo());
            orderVo.setActualMoney(order.getActualMoney());
            orderVo.setOrderMoney(order.getOrderMoney());
            orderVo.setContactPerson(order.getContactPerson());
            orderVo.setOrderTime(DateUtils.changeDateToStr(order.getOrderTime(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS));
            orderVo.setContactPhone(order.getContactPhone());
            orderVo.setSellerName(order.getSellerName());
            orderVo.setOrderState(order.getOrderState());
            orderVo.setTripTime(DateUtils.changeDateToStr(order.getTripTime(), DateUtils.DEFAULT_DATE_FORMAT_YMD));
            detailVo.setChildNumber(orderDetail.getChildNumber());
            detailVo.setAdultNumber(orderDetail.getAdultNumber());
            detailVo.setSkuId(orderDetail.getSkuId());
            detailVo.setSkuName(orderDetail.getSkuName());
            detailVo.setImage(orderDetail.getImage());
            detailVo.setProductId(orderDetail.getProductId());
            detailVo.setProductName(orderDetail.getProductName());
            detailVo.setProductSubTitle(orderDetail.getProductSubtitle());
            orderVo.setOrderDetailInfoVo(detailVo);
            orderBaseInfoVoList.add(orderVo);
        }
        PageView<OrderBaseInfoVo> pageVo = new PageView<>(orderBaseInfoVoList);
        pageVo.setList(orderBaseInfoVoList);
        pageVo.setPageNum(orderList.getPageNum());
        pageVo.setPages(orderList.getPages());
        pageVo.setPageSize(orderList.getPageSize());
        pageVo.setSize(orderList.getSize());
        pageVo.setTotal(orderList.getTotal());
        return pageVo;
    }

	@Override
	public Result<Object> buyerCancel(String orderNo,HttpServletRequest request, String userId) {
		OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.getOrderByOrderNo(orderNo);
		
		if(orderBaseInfo.getOrderState()==OrderConstants.WAIT_TO_PAY){
			orderBaseInfo.setOrderState(OrderConstants.CANCEL_BY_BUYER);
			orderBaseInfo.setUpdateTime(new Date());
			orderBaseInfoMapper.updateByPrimaryKey(orderBaseInfo);
			return new Result<>(1,"success","取消成功");
		}else if(orderBaseInfo.getOrderState()==OrderConstants.WAIT_TO_CONFIRM){
			//退款
			hotelOrderBaseInfoService.doRefundOrder(request ,orderBaseInfo.getId(),"");
			orderBaseInfo.setOrderState(OrderConstants.CANCEL_BY_BUYER);
			orderBaseInfo.setUpdateTime(new Date());
			orderBaseInfoMapper.updateByPrimaryKey(orderBaseInfo);
			return new Result<>(1,"success","取消成功");
		}/*else if(orderBaseInfo.getOrderState()==OrderConstants.CONFIRM){
			if(hotelGoods.getCancel()==1){
				//随时取消
				orderBaseInfo.setOrderState(OrderConstants.CANCEL_BY_BUYER_ANYTIME);
				orderBaseInfo.setPayState(OrderConstants.PAY_REFUNDING);
				orderBaseInfo.setUpdateTime(new Date());
				orderBaseInfoMapper.updateByPrimaryKey(orderBaseInfo);
				return new Result<>(1,"success","取消成功");
			}else if(hotelGoods.getCancel()==2){
				//限时取消
				double cancelTime = Double.valueOf(hotelGoods.getCancelTime());
				long cometime = orderBaseInfo.getComeTime().getTime();
				long cancelPoint = 0;
				String shopId = orderBaseInfo.getSellerId();
				HotelPolicy hotelPolicy = hotelPolicyMapper.selectByHotelId(shopId);
				if(hotelPolicy==null){
					cancelPoint = cometime+12*60*60*1000L-(long)(cancelTime*60*60*1000L);
				}else{
					String ect = hotelPolicy.getEarliestCheckinTime();
					if(ect.equals("不限")){
						cancelPoint = cometime+12*60*60*1000L-(long)(cancelTime*60*60*1000L);
					}else{
						int minute = Integer.valueOf(ect.substring(ect.indexOf(":")+1,ect.length()));
						int hour = Integer.valueOf(ect.substring(0, ect.indexOf(":")));
						cancelPoint = cometime+hour*60*60*1000L+minute*60*1000L-(long)(cancelTime*60*60*1000L);
					}
				}
				if(cancelPoint>new Date().getTime()){
					return new Result<>(0,"error","该订单不可取消");
				}else{
					orderBaseInfo.setOrderState(OrderConstants.CANCEL_BY_BUYER);
					orderBaseInfo.setPayState(OrderConstants.PAY_REFUNDING);
					orderBaseInfo.setUpdateTime(new Date());
					orderBaseInfoMapper.updateByPrimaryKey(orderBaseInfo);
					return new Result<>(1,"success","取消成功");
				}
			}else{
				return new Result<>(0,"error","该订单不可取消");
			}
		}*/else{
			return new Result<>(0,"error","该订单不可取消");
		}
	}
	
	@Override
	public int ableCancel(Order order){
		String goodsId = order.getGoodsId();
		HotelGoods hotelGoods = hotelGoodsService.selectByPrimaryKey(goodsId);
		if(hotelGoods==null){
			return 0;
		}
		int cancel=hotelGoods.getCancel();
		if(cancel==1){
			return 1;
		}else if(cancel==2){
			double cancelTime = Double.valueOf(hotelGoods.getCancelTime());
			long cometime = order.getComeTime().getTime();
			long cancelPoint = 0;
			String shopId = order.getShopId();
			HotelPolicy hotelPolicy = hotelPolicyMapper.selectByHotelId(shopId);
			if(hotelPolicy==null){
				cancelPoint = cometime+12*60*60*1000L-(long)(cancelTime*60*60*1000L);
			}else{
				String ect = hotelPolicy.getEarliestCheckinTime();
				if(ect.equals("不限")){
					cancelPoint = cometime+12*60*60*1000L-(long)(cancelTime*60*60*1000L);
				}else{
					int minute = Integer.valueOf(ect.substring(ect.indexOf(":")+1,ect.length()));
					int hour = Integer.valueOf(ect.substring(0, ect.indexOf(":")));
					cancelPoint = cometime+hour*60*60*1000L+minute*60*1000L-(long)(cancelTime*60*60*1000L);
				}
			}
			if(cancelPoint<new Date().getTime()){
				//不能申请退款
				return 0;
			}else{
				//可以申请退款
				return 1;
			}
		}
		return 0;
	}
	/**
     * 退款申请
     */
    @Override
    public Result<Object> refundApply(String orderNo, String userId, String reason){
    	OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.getOrderByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "订单不存在");
        }
        if (orderBaseInfo.getOrderState() == OrderConstact.OrderStatusEnum.REFUNDAPPLY.getCode()) {
            return new Result<>(1, "error", "订单已申请退款，不可重复申请");
        }
        if (orderBaseInfo.getOrderState() != OrderConstants.CONFIRM||orderBaseInfo.getPayState() != OrderConstants.PAY_PAY) {
            return new Result<>(1, "error", "订单不可退款");
        }
        //需要存储OPENID确认是该用户
        if (!StringUtils.equals(orderBaseInfo.getBuyerId(), userId)) {
            return new Result<>(0, "error", "微信ID不一致");
        }
        OrderDetailInfo orderDetailInfo = orderDetailInfoMapper.selectByOrderNo(orderNo);
    	String goodsId = orderDetailInfo.getProductId();
		HotelGoods hotelGoods = hotelGoodsService.selectByPrimaryKey(goodsId);
    	if(hotelGoods.getCancel()==1){
			//随时取消
			orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.REFUNDAPPLY.getCode());
			orderBaseInfo.setUpdateTime(new Date());
			orderBaseInfoMapper.updateByPrimaryKey(orderBaseInfo);
			return new Result<>(1,"success","退款申请成功");
		}else if(hotelGoods.getCancel()==2){
			//限时取消
			double cancelTime = Double.valueOf(hotelGoods.getCancelTime());
			long cometime = orderBaseInfo.getComeTime().getTime();
			long cancelPoint = 0;
			String shopId = orderBaseInfo.getSellerId();
			HotelPolicy hotelPolicy = hotelPolicyMapper.selectByHotelId(shopId);
			if(hotelPolicy==null){
				cancelPoint = cometime+12*60*60*1000L-(long)(cancelTime*60*60*1000L);
			}else{
				String ect = hotelPolicy.getEarliestCheckinTime();
				if(ect.equals("不限")){
					cancelPoint = cometime+12*60*60*1000L-(long)(cancelTime*60*60*1000L);
				}else{
					int minute = Integer.valueOf(ect.substring(ect.indexOf(":")+1,ect.length()));
					int hour = Integer.valueOf(ect.substring(0, ect.indexOf(":")));
					cancelPoint = cometime+hour*60*60*1000L+minute*60*1000L-(long)(cancelTime*60*60*1000L);
				}
			}
			long now = new Date().getTime();
			if(cancelPoint<now){
				return new Result<>(1,"error","该订单不可取消");
			}else{
				orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.REFUNDAPPLY.getCode());
				orderBaseInfo.setUpdateTime(new Date());
				orderBaseInfoMapper.updateByPrimaryKey(orderBaseInfo);
				RefundInfo refund = new RefundInfo();
	            refund.setId(com.zzk.util.StringUtils.getUUID());
	            refund.setCreateTime(DateUtils.getCurrentDate());
	            refund.setUpdateTime(DateUtils.getCurrentDate());
	            refund.setMchRefundNo(orderNo);
	            refund.setRefundAmount(orderBaseInfo.getOrderMoney());
	            refund.setRefundReason(reason);
	            refundInfoMapper.insert(refund);
				return new Result<>(1,"success","退款申请成功");
			}
		}else{
			return new Result<>(1,"error","该订单不可取消");
		}
            
    }
}
