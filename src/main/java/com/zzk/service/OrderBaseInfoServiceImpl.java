package com.zzk.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Sets.SetView;
import com.zzk.Constant;
import com.zzk.common.OrderConstact;
import com.zzk.common.OrderConstants;
import com.zzk.dao.BusinessInfoMapper;
import com.zzk.dao.CooperationInfoMapper;
import com.zzk.dao.FinanceInfoMapper;
import com.zzk.dao.GoodsSkuInfoMapper;
import com.zzk.dao.OrderBaseInfoMapper;
import com.zzk.dao.OrderDetailInfoMapper;
import com.zzk.dao.OrderStatusInfoMapper;
import com.zzk.dao.PlanInfoMapper;
import com.zzk.dao.PlatDealRecordMapper;
import com.zzk.dao.ProductStockInfoMapper;
import com.zzk.dao.RefundInfoMapper;
import com.zzk.dao.UserMapper;
import com.zzk.dao.ProductBaseInfoMapper;
import com.zzk.dao.ProductImagesMapper;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.CooperationInfo;
import com.zzk.entity.GoodsSkuInfo;
import com.zzk.entity.Hotel;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderBaseInfoCustom;
import com.zzk.entity.OrderCustom;
import com.zzk.entity.OrderDelay;
import com.zzk.entity.OrderDetailInfo;
import com.zzk.entity.OrderStatusInfo;
import com.zzk.entity.ProductBaseInfo;
import com.zzk.entity.ProductStockInfo;
import com.zzk.entity.RefundInfo;
import com.zzk.entity.User;
import com.zzk.link.IndustrySMS;
import com.zzk.link.Template;
import com.zzk.util.DateUtils;
import com.zzk.util.Exceptions;
import com.zzk.util.GenerateSequenceUtil;
import com.zzk.util.JsonUtils;
import com.zzk.util.MethodAnnotation;
import com.zzk.util.Page;
import com.zzk.util.PageView;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.zzk.vo.OrderBaseInfoVo;
import com.zzk.vo.OrderDetailInfoVo;
import com.zzk.vo.OrderStatusInfoVo;
import com.zzk.vo.RefundInfoVo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.omg.CORBA.SetOverrideType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zshlpay.common.constant.PayConstant;


/**
 * 订单信息表
 *
 * @author: Kun
 * @date: 2018-03-06 10:45
 */
@Service("orderBaseInfoService")
public class OrderBaseInfoServiceImpl implements OrderBaseInfoService {
    @Resource
    private OrderBaseInfoMapper orderBaseInfoMapper;
    @Resource
    private OrderDetailInfoMapper orderDetailInfoMapper;
    @Resource
    private GoodsSkuInfoMapper goodsSkuInfoMapper;
    @Resource
    private ProductBaseInfoMapper productBaseInfoMapper;
    @Resource
    private ProductStockInfoMapper productStockInfoMapper;
    @Resource
    private PlanInfoMapper planInfoMapper;
    @Resource
    private OrderStatusInfoMapper orderStatusInfoMapper;
    @Resource
    private OrderStatusInfoService orderStatusInfoService;
    @Resource
    private FinanceInfoService financeInfoService;
    @Resource
    private FinanceInfoMapper financeInfoMapper;
    @Resource
    private BusinessInfoMapper businessInfoMapper;
    @Resource
    private PlatDealRecordMapper platDealRecordMapper;
    @Resource
    private PayService payService;
    @Resource
    private ProductImagesMapper productImagesMapper;
    @Resource
    private RefundInfoMapper refundInfoMapper;
    @Resource
    private DelayService delayService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CooperationInfoMapper cooperationInfoMapper;
    @Resource
    private SellerReceiveConfigService sellerReceiveConfigService;
    @Resource
    private OrderService orderService;
    

    /**
     * 分页查询
     */
    @Override
    public List<OrderBaseInfo> selectByPage(Map map) {
        return orderBaseInfoMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return orderBaseInfoMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public OrderBaseInfo selectByPrimaryKey(String id) {
        return orderBaseInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(OrderBaseInfo bean) {
        return orderBaseInfoMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(OrderBaseInfo bean) {
        return orderBaseInfoMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        OrderBaseInfo bean = orderBaseInfoMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return orderBaseInfoMapper.updateByPrimaryKey(bean);
    }

    @Override
    public List<Map<String, Object>> listLineOrderInfo(Map map) {
        List<Map<String, Object>> resultList = orderBaseInfoMapper.listLineOrderInfo(map);
        return resultList;
    }

    @Override
    public List<Map<String, Object>> listLineOrderUserInfo(String groupId, String sort) {
		Map<String,Object> paraMap = new HashMap<>(4);
		paraMap.put("groupId",groupId);
		paraMap.put("sort",sort);
        List<Map<String, Object>> resultList = orderBaseInfoMapper.listLineOrderUserInfo(paraMap);
        if (CollectionUtils.isNotEmpty(resultList)){
            for (Map<String, Object> stringObjectMap : resultList) {
                String payState = MapUtils.getString(stringObjectMap,"PAY_STATE");
                switch (payState) {
                    case "1" : stringObjectMap.put("PAY_STATE","待支付");break;
                    case "2" : stringObjectMap.put("PAY_STATE","已支付");break;
                    case "3" : stringObjectMap.put("PAY_STATE","退款成功");break;
                    case "4" : stringObjectMap.put("PAY_STATE","退款失败");break;
                    default:
                }
            }
        }
        return resultList;
    }

    @Override
    public String selectOrderDetail(String orderNo) {
        Map<String, Object> map = orderBaseInfoMapper.selectOrderDetail(orderNo);
        if (map != null) {
            return JsonUtils.lineJsonData(1, "success", "请求成功", map);
        }
        return JsonUtils.lineJsonData(0, "error", "数据信息不存在", null);
    }

    @Override
    public String createOrder(OrderBaseInfo order, String stockGoodsId, Integer adult, Integer child) {
        //从单品中找到该单品
        ProductStockInfo productStockInfo = productStockInfoMapper.selectByPrimaryKey(stockGoodsId);
        if (productStockInfo == null) {
            return JsonUtils.lineJsonData(0, "error", "商品信息不存在", null);
        }
        String skuId = productStockInfo.getSkuId();
        GoodsSkuInfo goodsSkuInfo = goodsSkuInfoMapper.selectByPrimaryKey(skuId);
        if (goodsSkuInfo != null) {
            if (goodsSkuInfo.getStatus() == -1 || goodsSkuInfo.getState() == 2) {
                return JsonUtils.lineJsonData(0, "error", "改单品状态为不可用", null);
            }
        } else {
            return JsonUtils.lineJsonData(0, "error", "该单品不存在", null);
        }
        String productId = goodsSkuInfo.getProductId();
        ProductBaseInfo productBaseInfo = productBaseInfoMapper.selectByPrimaryKey(productId);
        if (productBaseInfo != null) {
            if (productBaseInfo.getStatus() == -1 || productBaseInfo.getShelfState() == 2 || productBaseInfo.getAuditState() != 1) {
                return JsonUtils.lineJsonData(0, "error", "产品状态为不可用", null);
            }
        } else {
            return JsonUtils.lineJsonData(0, "error", "该线路信息不存在", null);
        }
        BusinessInfo businessInfo = businessInfoMapper.selectByPrimaryKey(productBaseInfo.getSellerId());
        if (businessInfo == null) {
            return JsonUtils.lineJsonData(0, "error", "卖家信息错误", null);
        }
        order.setSellerId(businessInfo.getId());
        //计算库存 用总库存 减去 成人票已售库存和将要购买的库存
        int adultNumber = productStockInfo.getAdultNumber();
		int adultSaledNumber = productStockInfo.getAdultSaledNumber();
		int adultPreemptedNumber = productStockInfo.getAdultPreemptedNumber();
		int adultSurplusStock = adultNumber-adultSaledNumber-adultPreemptedNumber-adult;
        if (adultSurplusStock < 0) {
            return JsonUtils.lineJsonData(0, "error", "成人库存不足", null);
        }
        //计算儿童库存
        //计算库存 用儿童总库存 减去 儿童票已售库存和将要购买的库存
        int childNumber = productStockInfo.getChildNumber();
		int childSaledNumber = productStockInfo.getChildSaledNumber();
		int childPreemptedNumber = productStockInfo.getChildPreemptedNumber();
		int childSurplusStock = childNumber-childSaledNumber-childPreemptedNumber-child;
        if (childSurplusStock < 0) {
            return JsonUtils.lineJsonData(0, "error", "儿童库存不足", null);
        }
        //计算这个订单的总价
        BigDecimal payment = getOrderTotalPrice(productStockInfo, adult, child);
        if (!(payment.compareTo(order.getOrderMoney()) == 0)) {
            return JsonUtils.lineJsonData(0, "error", "价格错误", null);
        }
        //生成订单
        OrderBaseInfo orderBase = assembleOrder(order, productStockInfo, payment, businessInfo.getBusinName());
        if (orderBase == null) {
            return JsonUtils.lineJsonData(0, "error", "创建订单错误", null);
        }
        //满团提醒 只判断成人 儿童不允许单独购买
        if(adultSurplusStock == 0){
        	sellerReceiveConfigService.sendMessageToSeller(orderBase.getSellerId(), 6);
        }
        final String orderNo = orderBase.getOrderNo();
        OrderDetailInfo orderDetail = assembleOrderDetail(productStockInfo, productBaseInfo, goodsSkuInfo, orderNo, adult, child, payment);
        if (orderDetail == null) {
            return JsonUtils.lineJsonData(0, "error", "创建订单详情错误", null);
        }
        //修改预占库存
        productStockInfo.setAdultPreemptedNumber(productStockInfo.getAdultPreemptedNumber()+adult);
		productStockInfo.setChildPreemptedNumber(productStockInfo.getChildPreemptedNumber()+child);
        productStockInfo.setUpdateTime(new Date());
        int resultCode = productStockInfoMapper.updateByPrimaryKeySelective(productStockInfo);
        if (resultCode > 0) {
            OrderBaseInfoVo orderVo = assembleOrderVO(orderBase, orderDetail);
            orderStatusInfoService.changeStatus(orderNo, orderBase.getOrderState());
            //插入延时队列
            		long no = Long.valueOf(orderNo);

                    OrderDelay orderDelay = new OrderDelay(no, Constant.EXPTIME);
                    delayService.add(orderDelay);
                    delayService.addPrepare(no);
           

            return JsonUtils.lineJsonData(1, "success", "创建订单成功", orderVo);
        }
        return JsonUtils.lineJsonData(0, "error", "创建订单错误", null);
    }

    /**
     * 封装生成订单后给C端返回的orderVO
     */
    private OrderBaseInfoVo assembleOrderVO(OrderBaseInfo orderBase, OrderDetailInfo orderDetail) {
        //生成orderbaseinfoVo
        OrderBaseInfoVo orderVo = new OrderBaseInfoVo();
        orderVo.setId(orderBase.getId());
        orderVo.setOrderNo(orderBase.getOrderNo());
        orderVo.setOrderState(orderBase.getOrderState());
        orderVo.setSellerName(orderBase.getSellerName());
        orderVo.setContactPerson(orderBase.getContactPerson());
        orderVo.setContactPhone(orderBase.getContactPhone());
        orderVo.setOrderMoney(orderBase.getOrderMoney());
        orderVo.setActualMoney(orderBase.getActualMoney());
        orderVo.setPayMode(orderBase.getPayMode());
        orderVo.setPayState(orderBase.getPayState());
        orderVo.setTripTime(DateUtils.changeDateToStr(orderBase.getTripTime(), DateUtils.DEFAULT_DATE_FORMAT_YMD));
        orderVo.setOrderTime(DateUtils.changeDateToStr(orderBase.getOrderTime(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS));
        //生成orderdetailvo
        OrderDetailInfoVo orderDetailVo = new OrderDetailInfoVo();
        orderDetailVo.setId(orderDetail.getId());
        orderDetailVo.setAdultNumber(orderDetail.getAdultNumber());
        orderDetailVo.setChildNumber(orderDetail.getChildNumber());
        orderDetailVo.setCurrentAdultPrice(orderDetail.getCurrentAdultPrice());
        orderDetailVo.setSkuName(orderDetail.getSkuName());
        orderDetailVo.setProductName(orderDetail.getProductName());
        orderDetailVo.setProductSubTitle(orderDetail.getProductSubtitle());
        orderDetailVo.setTotalPrice(orderDetail.getTotalPrice());
        orderDetailVo.setImage(orderDetail.getImage());
        orderDetailVo.setCurrentChildPrice(orderDetail.getCurrentChildPrice());
        orderVo.setOrderDetailInfoVo(orderDetailVo);
        return orderVo;
    }

    /**
     * 创建订单详情数据
     */
    private OrderDetailInfo assembleOrderDetail(ProductStockInfo productStockInfo, ProductBaseInfo productBaseInfo, GoodsSkuInfo goodsSkuInfo, String orderNo, int adultNumber, int childNumber, BigDecimal payment) {
        OrderDetailInfo orderDetail = new OrderDetailInfo();
        String productId = productBaseInfo.getId();
        String coverImage = productImagesMapper.selectCoverImageUrlByProductId(productId);
        orderDetail.setId(UUID.randomUUID().toString());
        orderDetail.setChildNumber(childNumber);
        orderDetail.setAdultNumber(adultNumber);
        orderDetail.setCurrentAdultPrice(productStockInfo.getAdultSellPrice());
        orderDetail.setCurrentChildPrice(productStockInfo.getChildSellPrice());
        orderDetail.setProductId(productId);
        orderDetail.setProductName(productBaseInfo.getProductName());
        orderDetail.setImage(coverImage);
        orderDetail.setProductSubtitle(productBaseInfo.getProductSubtitle());
        orderDetail.setOrderNo(orderNo);
        orderDetail.setTotalPrice(payment);
        orderDetail.setStatus(1);
        orderDetail.setSkuName(goodsSkuInfo.getSkuName());
        orderDetail.setSkuId(goodsSkuInfo.getId());
        orderDetail.setCreateTime(new Date());
        orderDetail.setUpdateTime(new Date());
        orderDetail.setProductStockId(productStockInfo.getId());
        int resultCode = orderDetailInfoMapper.insert(orderDetail);
        return resultCode > 0 ? orderDetail : null;
    }

    private OrderBaseInfo assembleOrder(OrderBaseInfo order, ProductStockInfo productStockInfo, BigDecimal payment, String businessInfoName) {
        CooperationInfo cooperation = cooperationInfoMapper.selectByBusinessId(order.getSellerId());
        BigDecimal commRate = new BigDecimal(cooperation.getCommRate() / 100);
        OrderBaseInfo baseOrderInfo = new OrderBaseInfo();
        //ID
        baseOrderInfo.setId(UUID.randomUUID().toString());
        //订单号
        baseOrderInfo.setOrderNo(GenerateSequenceUtil.generateSequenceNo());
        //实付金额
        baseOrderInfo.setActualMoney(payment);
        //订单金额
        baseOrderInfo.setOrderMoney(payment);
        //买家ID
        baseOrderInfo.setBuyerId(order.getBuyerId());
        //卖家ID
        baseOrderInfo.setSellerId(order.getSellerId());
        //商家名称
        baseOrderInfo.setSellerName(businessInfoName);
        //联系人
        baseOrderInfo.setContactPerson(order.getContactPerson());
        //联系电话
        baseOrderInfo.setContactPhone(order.getContactPhone());
        //发团时间
        baseOrderInfo.setTripTime(productStockInfo.getStartTime());
        //创建日期
        baseOrderInfo.setCreateTime(new Date());
        //更新日期
        baseOrderInfo.setUpdateTime(new Date());
        //数据状态-1删除 1正常
        baseOrderInfo.setStatus(1);
        //订单生产时间
        baseOrderInfo.setOrderTime(new Date());
        //未付款
        baseOrderInfo.setPayState(OrderConstact.OrderPayStatusEnum.UNPAID.getCode());
        //待付款
        baseOrderInfo.setOrderState(OrderConstact.OrderStatusEnum.WAITPAY.getCode());
        //佣金金额
        baseOrderInfo.setOrderType(1);
        baseOrderInfo.setCommission(payment.multiply(commRate));
        int resultCode = orderBaseInfoMapper.insert(baseOrderInfo);
        if (resultCode > 0) {
            return baseOrderInfo;
        }
        return null;
    }

    private BigDecimal getOrderTotalPrice(ProductStockInfo productStockInfo, Integer adult,
                                          Integer child) {
        if (child == null) {
            child = 0;
        }
        BigDecimal payment = new BigDecimal("0");
        BigDecimal adultPrice = productStockInfo.getAdultSellPrice();
        BigDecimal childPrice = productStockInfo.getChildSellPrice();
        BigDecimal adultTotalPrice = adultPrice.multiply(new BigDecimal(adult));
        BigDecimal childTotalPrice = childPrice.multiply(new BigDecimal(child));
        payment = payment.add(adultTotalPrice);
        payment = payment.add(childTotalPrice);
        return payment;
    }
//	@Test
//	public void testaa(){
//	    BigDecimal payment = new BigDecimal("0");
//	    BigDecimal adultPrice = new BigDecimal("1.1");
//	    BigDecimal childPrice = new BigDecimal("1.0");
//	    BigDecimal adultTotalPrice= adultPrice.multiply(new BigDecimal(2));
//	    System.out.println(adultTotalPrice.doubleValue());
//	    BigDecimal childTotalPrice= childPrice.multiply(new BigDecimal(1));
//	    System.out.println(childTotalPrice.doubleValue());
//	    payment = payment.add(adultTotalPrice);
//	    payment = payment.add(childTotalPrice);
//	    System.out.println(payment.doubleValue());
//	}

    /**
     * 买家取消
     */
    @Override
    public Result<Object> buyerCancel(String orderNo, HttpServletRequest request, String userId) {
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "订单不存在", null);
        }
        int status = orderBaseInfo.getOrderState();
        if (status == OrderConstact.OrderStatusEnum.WAITPAY.getCode()
                && orderBaseInfo.getPayState() == OrderConstact.OrderPayStatusEnum.UNPAID.getCode()) {

            orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.USERCANCELED.getCode());
            orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
            int resultCode = this.update(orderBaseInfo);
            if (resultCode > 0) {
            	//cancelOrderAddStock(orderNo);
                orderStatusInfoService.changeStatus(orderNo, orderBaseInfo.getOrderState());
                sellerReceiveConfigService.sendMessageToSeller(orderBaseInfo.getSellerId(), 3);
                //如果延时队列中还有该订单号
                final String no =  orderNo;
                 
    	            	long num = Long.valueOf(no);
    	            	delayService.remove(num);
    	            	boolean b=delayService.removePrepare(num);
    	            	if(b){
    	            		cancelOrderAddStock(no);
    	            	}
    	        
                return new Result<>(1, "success", "取消成功", null);
            }
        } else if (status == OrderConstact.OrderStatusEnum.WAITCONFIRM.getCode() && orderBaseInfo.getPayState() == OrderConstact.OrderPayStatusEnum.ALREADYPAID.getCode()) {
            //退款逻辑
            RefundInfo refund = new RefundInfo();
            refund.setId(StringUtils.getUUID());
            refund.setCreateTime(DateUtils.getCurrentDate());
            refund.setUpdateTime(DateUtils.getCurrentDate());
            refund.setMchRefundNo(orderNo);
            refund.setPayMode(orderBaseInfo.getPayMode());
            refund.setRefundAmount(orderBaseInfo.getOrderMoney());
            refund.setRefundReason("未确认自动退款");
            refund.setStatus(1);
            refund.setRefundStatus(OrderConstact.OrderRefund.ALLREFUND.getCode());
            int resultCode = refundInfoMapper.insert(refund);
            if (resultCode > 0) {
                User user = userMapper.selectByPrimaryKey(userId);
                if (user == null) {
                    return new Result<>(0, "error", "用户不存在");
                }
                return payService.doRefundOrder(request, orderBaseInfo, refund.getRefundAmount(),"","");
            }
        }
        return new Result<>(0, "error", "取消失败", null);
    }
    /**
     * 取消订单恢复库存
     */
    @Override
    public int cancelOrderAddStock(String orderNo){
    	OrderDetailInfo  orderDetailInfo = orderDetailInfoMapper.selectByOrderNo(orderNo);
    	ProductStockInfo productStockInfo = productStockInfoMapper.selectByPrimaryKey(orderDetailInfo.getProductStockId());
    	if(productStockInfo.getAdultPreemptedNumber() != 0){
			productStockInfo.setAdultPreemptedNumber(productStockInfo.getAdultPreemptedNumber()-orderDetailInfo.getAdultNumber());		
		}
		if(productStockInfo.getChildPreemptedNumber() != 0){
			productStockInfo.setChildPreemptedNumber(productStockInfo.getChildPreemptedNumber()-orderDetailInfo.getChildNumber());
		}
    	productStockInfo.setUpdateTime(DateUtils.getCurrentDate());
    	return  productStockInfoMapper.updateByPrimaryKeySelective(productStockInfo);
    }
    /**
     * 退款申请
     */
    @Override
    public Result<Object> refundApply(String orderNo, String userId, String reason, HttpServletRequest request) {
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "订单不存在");
        }
        //需要存储OPENID确认是该用户
        if (!StringUtils.equals(orderBaseInfo.getBuyerId(), userId)) {
            return new Result<>(0, "error", "微信ID不一致");
        }
        orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.REFUNDAPPLY.getCode());
        orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
        int result = orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
        if (result > 0) {
        	RefundInfo refundInfo = refundInfoMapper.selectByOrderNo(orderNo);
        	if(refundInfo != null){
        		return new Result<>(0,"error","改订单已申请退款或已退款");
        	}
        	sellerReceiveConfigService.sendMessageToSeller(orderBaseInfo.getSellerId(), 2);
        	orderStatusInfoService.changeStatusToCancel(orderNo, orderBaseInfo.getOrderState(), "", reason);
            RefundInfo refund = new RefundInfo();
            refund.setId(StringUtils.getUUID());
            refund.setCreateTime(DateUtils.getCurrentDate());
            refund.setUpdateTime(DateUtils.getCurrentDate());
            refund.setMchRefundNo(orderNo);
            refund.setRefundAmount(orderBaseInfo.getOrderMoney());
            refund.setRefundReason(reason);
            refundInfoMapper.insert(refund);
            return new Result<>(1, "success", "退款申请成功");
        }
        return new Result<>(0, "error", "系统错误！");
//      return  payService.doRefundOrder(request, orderBaseInfo, openId);
    }

    /**
     * 商家取消
     */
    @Override
    public Result<Object> sellerCancel(String orderNo,String feedback,String remarks, HttpServletRequest request) {
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "订单不存在", null);
        }
        int status = orderBaseInfo.getOrderState();
        if (status == OrderConstact.OrderStatusEnum.WAITPAY.getCode() && orderBaseInfo.getPayState() == OrderConstact.OrderPayStatusEnum.UNPAID.getCode()) {
            orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.SELLERCANCELED.getCode());
            orderBaseInfo.setUpdateTime(new Date());
            int resultCode = this.update(orderBaseInfo);
            if (resultCode > 0) {
            	sellerReceiveConfigService.sendMessageToBuyer(orderBaseInfo, 4);
            	orderStatusInfoService.changeStatusToCancel(orderNo, orderBaseInfo.getOrderState(), feedback,remarks);
            	final String no =  orderNo;
            	long num = Long.valueOf(no);
            	delayService.remove(num);
            	boolean b=delayService.removePrepare(num);
            	if(b){
            		cancelOrderAddStock(no);
            	}
    	       return new Result<>(1, "success", "取消成功", null);
            }
        } else if (status == OrderConstact.OrderStatusEnum.WAITCONFIRM.getCode() && orderBaseInfo.getPayState() == OrderConstact.OrderPayStatusEnum.ALREADYPAID.getCode()) {
            BigDecimal orderMoney = orderBaseInfo.getOrderMoney();
            Result<Object> result = payService.doRefundOrder(request, orderBaseInfo, orderMoney,feedback,remarks);
            orderBaseInfo.setUpdateTime(new Date());
            orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.REFUNDING.getCode());
            if(result.getState()!=1){
            	orderBaseInfo.setPayState(OrderConstact.OrderPayStatusEnum.REFUNDFAILD.getCode());
            }
            sellerReceiveConfigService.sendMessageToBuyer(orderBaseInfo, 4);
            this.update(orderBaseInfo);
            return  result;

        }
        return new Result<>(0, "error", "取消失败", null);
    }

    /**
     * 商家确认
     */
    @Override
    public Result<Object> sellerConfirm(String orderNo) {
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "订单信息不存在");
        }
        int status = orderBaseInfo.getOrderState();
        int payStatus = orderBaseInfo.getPayState();
        if (status == OrderConstact.OrderStatusEnum.WAITCONFIRM.getCode() && payStatus == OrderConstact.OrderPayStatusEnum.ALREADYPAID.getCode()) {
            orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.WAITCONSUME.getCode());
            orderBaseInfo.setUpdateTime(new Date());
            int resultCode = this.update(orderBaseInfo);
            if (resultCode > 0) {
                /*记录表插入数据*/
                orderStatusInfoService.changeStatus(orderBaseInfo.getOrderNo(), orderBaseInfo.getOrderState());
                /*给用户发送订单已确认短信*/
                  sellerReceiveConfigService.sendMessageToBuyer(orderBaseInfo, 2);
//                String phoneNum = orderBaseInfo.getContactPhone();
//                Date tripTime = orderBaseInfo.getTripTime();
//                Map<String,Object> responseMap = orderDetailInfoMapper.getOrderConfirmResponse(orderNo);
//                String startAddress = MapUtils.getString(responseMap,"startAddress","联系导游确认");
//                String days = MapUtils.getString(responseMap,"days","联系导游确认");
//                String value = MapUtils.getString(responseMap,"value","");
//                IndustrySMS.link(phoneNum, Template.CONFIRM_ORDER,"",new String[]{orderNo,DateUtils.changeDateToStr(tripTime,null),startAddress,days,value});
                return new Result<>(1, "success", "确认成功");
            }
        }
        return new Result<>(0, "error", "确认失败");
    }

    @Override
    public Result<Object> wxpay(String orderNo, HttpServletRequest request,String channelId) {
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        OrderDetailInfo detailInfo = orderDetailInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null || detailInfo == null) {
            return new Result<Object>(0, "error", "商品支付下单失败，订单不存在", orderNo);

        }
        String buyerId = orderBaseInfo.getBuyerId();
        User user = userMapper.selectByPrimaryKey(buyerId);
        if (user == null) {
            return new Result<>(0, "error", "用户不存在");
        }
        String openId = user.getOpenId();
        if (StringUtils.isBlank(openId)&&channelId.equalsIgnoreCase("WX_JSAPI")) {
            System.out.println("=======openId为空=====");
            return new Result<>(0, "error", "openId为空");
        }
        ProductStockInfo productStockInfo = productStockInfoMapper.selectByPrimaryKey(detailInfo.getProductStockId());
        if(productStockInfo == null){
        	return new Result<>(0,"error","库存信息不存在");
        }else{
        	int adult = detailInfo.getAdultNumber();
        	int child = detailInfo.getChildNumber();
            int adultNumber = productStockInfo.getAdultNumber();
    		int adultSaledNumber = productStockInfo.getAdultSaledNumber();
    		int adultSurplusStock = adultNumber-adultSaledNumber-adult;
            if (adultSurplusStock < 0) {
            	orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.SYSTEMCANCELED.getCode());
            	orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
            	orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
                return new Result<>(0, "error", "成人库存不足", null);
            }
            //计算儿童库存
            //计算库存 用儿童总库存 减去 儿童票已售库存和将要购买的库存
            int childNumber = productStockInfo.getChildNumber();
    		int childSaledNumber = productStockInfo.getChildSaledNumber();
    		int childSurplusStock = childNumber-childSaledNumber-child;
            if (childSurplusStock < 0) {
            	orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.SYSTEMCANCELED.getCode());
            	orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
            	orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
                return new Result<>(0, "error", "儿童库存不足", null);
            }
        }
        int status = orderBaseInfo.getPayState();
        if (status != OrderConstact.OrderPayStatusEnum.UNPAID.getCode()) {
            return new Result<>(0, "error", "订单已支付");
        }

        //自定义回调参数--特定渠道发起时额外参数
        String extra = "{}";
        Map retMap = payService.doPay(orderBaseInfo, channelId, extra, openId, request);
        if (retMap == null) {
            return new Result<>(0, "error", "支付中心下单验签失败");
        }
        if (PayConstant.PAY_CHANNEL_ALIPAY_MOBILE.equalsIgnoreCase(channelId)) {
            return new Result<Object>(1, "success", "alipay", retMap.get("payParams"));
        }
        if (PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId)) {
            String payOrderId = retMap.get("payOrderId").toString();
            orderBaseInfo.setPayOrderId(payOrderId);
            orderBaseInfo.setOpenid(openId);
            orderBaseInfo.setPayMode(1);
            int resultCode = this.update(orderBaseInfo);
            if (resultCode > 0) {
                return new Result<Object>(1, "success", "支付中心下单验签成功", retMap.get("payParams"));
            }
            return new Result<>(0, "error", "存储支付信息失败");
        }
        if (PayConstant.PAY_CHANNEL_WX_MWEB.equalsIgnoreCase(channelId)) {
        	String payOrderId = retMap.get("payOrderId").toString();
        	orderBaseInfo.setPayOrderId(payOrderId);
        	orderBaseInfo.setPayMode(1);
        	int resultCode = this.update(orderBaseInfo);
        	if (resultCode > 0) {
        		Map<String,Object> resultMap = new HashMap<String,Object>();
                resultMap.put("redirectUrl", retMap.get("payUrl"));
        		return new Result<Object>(1, "success", "h5支付中心下单验签成功", resultMap);
        	}
        	return new Result<>(0, "error", "存储支付信息失败");
        }
        return new Result<Object>(0, "error", "支付方式Error", null);
    }

    @Override
    public void payCallBack(HttpServletRequest request, HttpServletResponse response) {
        try {
            payService.payNotify(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void refundCallBack(HttpServletRequest request, HttpServletResponse response) {
        try {
            payService.refundNotify(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "支付回调异常");
            e.printStackTrace();
        }

    }

    @Override
    public Result<Object> selectByBuyerId(String userId, String orderState, Page pager) {
    	if (pager == null) {
            pager = new Page<>();
        }
        try {
        	//获取线路订单
            Integer orderStateInt = Integer.parseInt(orderState);
            if (orderStateInt < 0 || orderStateInt > 11) {
                return new Result<>(0, "error", "无效的订单状态");
            }
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
            List<Object> listAll = new ArrayList<Object>();
            listAll.addAll(voData.getList());
            //获取酒店订单
            List<String> searchCondition = assembleBuyHotelOrderState(orderStateInt);
            Map<String,Object> searchMap = new HashMap<>();
            searchMap.put("buyerId", userId);
            if(orderStateInt==4){
            	searchMap.put("payStateArr", searchCondition);
            }else{
            	searchMap.put("orderStateArr", searchCondition);
            }
            PageView<OrderBaseInfo> view = orderService.selectOrderByBuyerIdNew(searchMap,pager.getPageNo(),pager.getpageSize());
            List<OrderBaseInfo> list = view.getList();
            List<Order> resultList = new ArrayList<>();
            if(list.size()>0){
            	for (OrderBaseInfo bean:list) {
	            	resultList.add(orderService.orderBaseInfo2Order(bean));
	            }
	    		changeToOrder(resultList);
	    		for(Order order: resultList){
	    			try{
	    				order.setOrderStateStr(this.getOrderStateStr(order.getOrderState()));
	    				order.setOrderObject(null);
	    				order.setOrderState(changeOrderState(order.getOrderState()));
	    				order.setOrderNo(order.getOrderNum());
	    				int night = DateUtils.daysBetween(order.getComeTime(), order.getLeaveTime());
	    				order.setNight(night);
	    				OrderDetailInfoVo orderDetailInfoVo = new OrderDetailInfoVo();
	    				orderDetailInfoVo.setProductId(order.getGoodsId());
	    				order.setOrderDetailInfoVo(orderDetailInfoVo);
	    				order.setTripTime(DateUtils.changeDateToStr(order.getComeTime(), DateUtils.DEFAULT_DATE_FORMAT_YMD));
	    				if(OrderConstact.OrderStatusEnum.WAITCONSUME.getCode()==order.getOrderState()){
	    					order.setAbleCancel(orderService.ableCancel(order));
	    					if(order.getAbleCancel()==0){
	    						order.setOrderState(13);
	    					}
	    				}else{
	    					order.setAbleCancel(0);
	    				}
	    			}catch(Exception e){
	    				System.err.println(e.toString());
	    				return new Result<Object>(0, "error", "请求错误", e.toString());
	    			}
	    		}
	    		listAll.addAll(resultList);
	    		
	    		Collections.sort(listAll,new Comparator<Object>(){
					public int compare(Object o1, Object o2) {
						long d1;
						long d2;
						if(o1 instanceof Order){
							d1 = ((Order)o1).getOrderTime().getTime();
						}else{
							d1 = DateUtils.changeStrToDate(((OrderBaseInfoVo)o1).getOrderTime()).getTime();
						}
						if(o2 instanceof Order){
							d2 = ((Order)o2).getOrderTime().getTime();
						}else{
							d2 = DateUtils.changeStrToDate(((OrderBaseInfoVo)o2).getOrderTime()).getTime();
						}
			            if(d1<d2){  
		                    return 1;  
		                }  
		                if(d1==d2){  
		                    return 0;  
		                }  
		                return -1; 
		            }
				});
            }
            
            Map<String,Object> resultMap = new HashMap<String, Object>();
            resultMap.put("list", listAll);
            resultMap.put("total", view.getTotal()+voData.getTotal());
            return new Result<Object>(1, "success", "请求成功", resultMap);
        } catch (Exception e) {
            return new Result<Object>(0, "error", "请求错误", e);
        }
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
    
    private List<String> assembleBuyHotelOrderState(int state){
    	ArrayList<String> list = new ArrayList<>();
    	if(state== 1){
    		list.add(String.valueOf(OrderConstants.WAIT_TO_PAY));
    		return list;
    	}
    	if(state == 2){
    		list.add(String.valueOf(OrderConstants.CONFIRM));
    		return list;
    	}
    	if(state== 3){
    		list.add(String.valueOf(OrderConstants.HAVE_EVALUATE));
    		list.add(String.valueOf(OrderConstants.DEFAULT_END));
    		return list;
    	}
    	if(state == 4){
    		list.add(String.valueOf(OrderConstants.PAY_REFUND));
    		list.add(String.valueOf(OrderConstants.PAY_REFUND));
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
            orderVo.setOrderType(order.getOrderType());
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
    public Result<Object> selectOrderItemByOrderNo(String userId, String orderNo) {
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "找不到该订单");
        }
        //OrderDetailInfo orderDetailInfo = orderDetailInfoMapper.selectByOrderNo(orderNo);
        String buyerId = orderBaseInfo.getBuyerId();
        if (!StringUtils.equals(userId, buyerId)) {
            return new Result<>(0, "error", "无权限访问");
        }
        if(orderBaseInfo.getOrderType()==2){
        	Order order = orderService.selectByOrderNum(orderNo);
        	if(order!=null){
        		OrderCustom rule = orderService.selectOrderDetail(order.getOrderId());
        		if(rule!=null){
            		rule.getHotelGoods().setCancelStr(CacheService.getLabel("cancel", String.valueOf(rule.getHotelGoods().getCancel())));
            		OrderStatusInfoVo statusVO = new OrderStatusInfoVo();
            		List<OrderStatusInfoVo> statusVOList = new ArrayList<OrderStatusInfoVo> ();
            		statusVO.setOrderState(changeOrderState(rule.getOrderState()));
                    statusVO.setOrderStateStr(OrderConstact.OrderStatusEnum.codeOf(statusVO.getOrderState()).getValue());
                    statusVO.setPayState(OrderConstact.OrderPayStatusEnum.codeOf(rule.getPayState()).getValue());
                    statusVO.setCreateTime(DateUtils.changeDateToStr(rule.getUpdateTime(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS));
                    statusVO.setRemark(rule.getRemark());
                    statusVOList.add(statusVO);
                    rule.setOrderStatusInfo(statusVOList);
                    rule.setOrderState(changeOrderState(rule.getOrderState()));
                    if(rule.getOrderState()==OrderConstact.OrderStatusEnum.WAITCONSUME.getCode()){
                    	rule.setAbleCancel(orderService.ableCancel(rule));
                    	if(rule.getAbleCancel()==0){
                    		rule.setOrderState(13);
    					}
                    }else{
                    	rule.setAbleCancel(0);
                    }
                    rule.setOrderTimeStr(DateUtils.changeDateToStr(rule.getOrderTime(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS));
                    rule.setOrderNo(rule.getOrderNum());
                    OrderDetailInfoVo orderDetailInfoVo = new OrderDetailInfoVo();
                    orderDetailInfoVo.setProductId(rule.getGoodsId());
                    rule.setOrderDetailInfoVo(orderDetailInfoVo);
                    int night = DateUtils.daysBetween(rule.getComeTime(), rule.getLeaveTime());
                    rule.setNight(night);
                    rule.setOrderType(2);
                    return new Result<Object>(1, "success", "请求成功", rule);
            	}
        	}
        }else{
	        OrderBaseInfoCustom orderCustom = orderBaseInfoMapper.selectOrderItemMobileByOrderNo(orderNo);
	        List<OrderStatusInfo> orderStatusInfoList = orderStatusInfoMapper.selectByOrderNo(orderNo);
	        OrderBaseInfoVo orderVO = assembleMobileOrderItemAndStatus(orderCustom, orderStatusInfoList);
	        orderVO.setOrderType(1);
	        if (orderVO != null) {
	            return new Result<Object>(1, "success", "请求成功", orderVO);
	        }
        }
        return new Result<Object>(0, "error", "订单信息不存在", null);
    }

    private OrderBaseInfoVo assembleMobileOrderItemAndStatus(OrderBaseInfoCustom orderCustom, List<OrderStatusInfo> orderStatusInfoList) {
        if (orderCustom == null || orderStatusInfoList == null) {
            return null;
        }
        OrderBaseInfoVo orderVo = new OrderBaseInfoVo();
        //修改订单状态改变时间
        List<OrderStatusInfoVo> statusListVo = new ArrayList<>();
        for (OrderStatusInfo orderStatusInfo : orderStatusInfoList) {
            OrderStatusInfoVo statusVO = new OrderStatusInfoVo();
            statusVO.setOrderState(orderStatusInfo.getOrderState());
            statusVO.setOrderStateStr(OrderConstact.OrderStatusEnum.codeOf(orderStatusInfo.getOrderState()).getValue());
            statusVO.setPayState(OrderConstact.OrderPayStatusEnum.codeOf(orderStatusInfo.getPayState()).getValue());
            statusVO.setCreateTime(DateUtils.changeDateToStr(orderStatusInfo.getCreateTime(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS));
            statusVO.setCreaterName(orderStatusInfo.getCreaterName());
            statusVO.setRefundFeedback(orderStatusInfo.getRefundFeedback());
            statusVO.setRemark(orderStatusInfo.getRemark());
            statusListVo.add(statusVO);
        }
        OrderDetailInfo orderDetail = orderCustom.getOrderDetailInfo();
        OrderBaseInfoVo orderBaseInfoVo = new OrderBaseInfoVo();
        OrderDetailInfoVo detailVo = new OrderDetailInfoVo();
        //将转换好的订单时间LIST放入orderbaseinfOVO
        orderBaseInfoVo.setOrderStatusInfo(statusListVo);

        orderBaseInfoVo.setId(orderCustom.getId());
        orderBaseInfoVo.setOrderNo(orderCustom.getOrderNo());
        orderBaseInfoVo.setActualMoney(orderCustom.getActualMoney());
        orderBaseInfoVo.setOrderMoney(orderCustom.getOrderMoney());
        orderBaseInfoVo.setSellerName(orderCustom.getSellerName());
        orderBaseInfoVo.setOrderState(orderCustom.getOrderState());
        orderBaseInfoVo.setTripTime(DateUtils.changeDateToStr(orderCustom.getTripTime(), DateUtils.DEFAULT_DATE_FORMAT_YMD));
        orderBaseInfoVo.setContactPerson(orderCustom.getContactPerson());
        orderBaseInfoVo.setContactPhone(orderCustom.getContactPhone());
        orderBaseInfoVo.setPayMode(orderCustom.getPayMode());
        orderBaseInfoVo.setPayState(orderCustom.getPayState());
        orderBaseInfoVo.setOrderTime(DateUtils.changeDateToStr(orderCustom.getOrderTime(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS));

        detailVo.setChildNumber(orderDetail.getChildNumber());
        detailVo.setAdultNumber(orderDetail.getAdultNumber());
        detailVo.setSkuId(orderDetail.getSkuId());
        detailVo.setSkuName(orderDetail.getSkuName());
        detailVo.setProductId(orderDetail.getProductId());
        detailVo.setProductName(orderDetail.getProductName());
        detailVo.setImage(orderDetail.getImage());
        detailVo.setProductSubTitle(orderDetail.getProductSubtitle());
        orderVo.setOrderDetailInfoVo(detailVo);
        orderBaseInfoVo.setOrderDetailInfoVo(detailVo);

        return orderBaseInfoVo;
    }

    /**
     * 同意退款
     */
    @Override
    public Result<Object> agreeRefund(HttpServletRequest request, String orderNo, String feedback, String amount,
                                      String remarks) {
    	if(feedback == null){
    		feedback = "";
    	}
    	if(remarks == null){
    		remarks = "";
    	}
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "订单不存在");
        }
        if (!(orderBaseInfo.getOrderState() == OrderConstact.OrderStatusEnum.REFUNDAPPLY.getCode())) {
            return new Result<>(0, "error", "订单状态错误");
        }
        if (orderBaseInfo.getPayState() == OrderConstact.OrderPayStatusEnum.UNPAID.getCode() || orderBaseInfo.getPayState() == OrderConstact.OrderPayStatusEnum.REFUNDSUCCESS.getCode()) {
            return new Result<>(0, "error", "未支付或已退款");
        }
        if (new BigDecimal(amount).compareTo(orderBaseInfo.getOrderMoney()) > 0) {
            return new Result<>(0, "error", "退款金额错误");
        }
        RefundInfo refundInfo = refundInfoMapper.selectByOrderNo(orderNo);
        if (refundInfo == null) {
            return new Result<>(0, "error", "退款申请不存在");
        }
        refundInfo.setRefundFeedback(feedback);
        refundInfo.setRemark(remarks);
        refundInfo.setRefundAmount(new BigDecimal(amount));
        refundInfo.setUpdateTime(DateUtils.getCurrentDate());
        int resultCode = refundInfoMapper.updateByPrimaryKeySelective(refundInfo);
        orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.REFUNDING.getCode());
        orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
        orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
        orderStatusInfoService.changeStatusToCancel(orderNo, orderBaseInfo.getOrderState(), feedback, remarks);
        if (resultCode > 0) {
            return payService.doRefundOrder(request, orderBaseInfo, new BigDecimal(amount),feedback,remarks);
        }
        return new Result<>(0, "error", "退款信息异常");
    }

    /**
     * 不同意退款
     */
    @Override
    public Result<Object> refusedRefund(String orderNo, String feedback, String remarks) {
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "订单不存在");
        }

        RefundInfo refundInfo = refundInfoMapper.selectByOrderNo(orderNo);
        if (refundInfo == null) {
            return new Result<>(0, "error", "退款申请不存在");
        }
        refundInfo.setRefundFeedback(feedback);
        refundInfo.setRemark(remarks);
        refundInfo.setUpdateTime(DateUtils.getCurrentDate());
        int resultCode = refundInfoMapper.updateByPrimaryKeySelective(refundInfo);
        if (resultCode > 0) {
        	orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.SELLERCANCELED.getCode());
        	orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
        	int result = orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
        	if(result>0){
        		orderStatusInfoService.changeStatusToCancel(orderNo, orderBaseInfo.getOrderState(), feedback, remarks);
        		return new Result<>(1, "success", "提交成功");
        	}
        }
        return new Result<>(0, "error", "退款信息异常");
    }

    /**
     * 商户获取订单列表
     */
    @Override
    @MethodAnnotation(remark = "查询订单")
    public PageView<OrderBaseInfoVo> listOrder(Map<String, Object> paraMap, Integer pageNumber, Integer pageSize) {
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<OrderBaseInfoCustom> orderList = orderBaseInfoMapper.sellerFetchOrderList(paraMap);
            PageView<OrderBaseInfoVo> voData = assembleMobileClientOrderList(new PageView<>(orderList));
            return voData;
        } catch (Exception e) {
        	return new PageView<>(null);
        }
    }

    @Override
    public Result<Object> selectOrderDetailByOrderNo(String orderNo) {
        OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        if (orderBaseInfo == null) {
            return new Result<>(0, "error", "找不到该订单");
        }
        if(orderBaseInfo.getOrderType()==2){
        	Order order = orderService.selectByOrderNum(orderNo);
        	if(order!=null){
        		OrderCustom rule = orderService.selectOrderDetail(order.getOrderId());
        		if(rule!=null){
            		rule.getHotelGoods().setCancelStr(CacheService.getLabel("cancel", String.valueOf(rule.getHotelGoods().getCancel())));
            		return new Result<Object>(1, "success", "请求成功", rule);
            	}
        	}
        }else{
        	OrderBaseInfoCustom orderCustom = orderBaseInfoMapper.selectOrderItemMobileByOrderNo(orderNo);
            List<OrderStatusInfo> orderStatusInfoList = orderStatusInfoMapper.selectByOrderNo(orderNo);
            OrderBaseInfoVo orderVO = assembleMobileOrderItemAndStatus(orderCustom, orderStatusInfoList);
            if (orderCustom.getOrderState() == OrderConstact.OrderStatusEnum.REFUNDAPPLY.getCode()) {
                RefundInfo refundInfo = refundInfoMapper.selectByOrderNo(orderNo);
                if (refundInfo != null) {
                    RefundInfoVo refundInfoVo = assembleRefundInfoVo(refundInfo);
                    orderVO.setRefundInfoVo(refundInfoVo);
                }
            }
            if (orderVO != null) {
                return new Result<Object>(1, "success", "请求成功", orderVO);
            }
        }
        return new Result<Object>(0, "error", "订单信息不存在", null);
    }

    private RefundInfoVo assembleRefundInfoVo(RefundInfo refundInfo) {
        RefundInfoVo refundVo = new RefundInfoVo();
        refundVo.setPayMode(refundInfo.getPayMode());
        refundVo.setProcessResult(refundInfo.getProcessResult());
        refundVo.setApplyTime(DateUtils.changeDateToStr(refundInfo.getCreateTime(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS));
        refundVo.setRefundReason(refundInfo.getRefundReason());
        refundVo.setRemark(refundInfo.getRemark());
        refundVo.setRefundSuccessTime(DateUtils.changeDateToStr(refundInfo.getRefundSuccessTime(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS));
        refundVo.setRefundFeedback(refundInfo.getRefundFeedback());
        refundVo.setRefundStatus(refundInfo.getRefundStatus());
        return refundVo;
    }

    /**
     * 账单对应的订单
     */
    @Override
    public Result<Object> selectByBillNo(String billId, Page page) {
        try {
            PageHelper.startPage(page.getPageNumber(), page.getpageSize());
            List<OrderBaseInfoCustom> list = orderBaseInfoMapper.selectByBillNo(billId);
            PageView<OrderBaseInfoCustom> viewList = new PageView<>(list);
            PageView<OrderBaseInfoVo> listVo = assembleMobileClientOrderList(viewList);
            return new Result(1, "success", "请求完成", listVo);
        } catch (Exception e) {
            return new Result(0, "error", "数据异常", e);
        }
    }

    @Override
    public Map<String, Object> countHomePageData(String sellerId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        Date startTime = calendar.getTime();
        System.err.println(startTime.toString());
        int dayCount = orderBaseInfoMapper.selectOrderTotal(sellerId, startTime, new Date());
        BigDecimal dayMoneySum = orderBaseInfoMapper.selectTotalMoney(sellerId, startTime, new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        startTime = calendar.getTime();
        System.err.println(startTime.toString());
        int monthCount = orderBaseInfoMapper.selectOrderTotal(sellerId, startTime, new Date());
        BigDecimal monthMoneySum = orderBaseInfoMapper.selectTotalMoney(sellerId, startTime, new Date());
        dayMoneySum = dayMoneySum == null ? new BigDecimal(0) : dayMoneySum;
        monthMoneySum = monthMoneySum == null ? new BigDecimal(0) : monthMoneySum;
        resultMap.put("dayCount", dayCount);
        resultMap.put("dayMoneySum", dayMoneySum.toString());
        resultMap.put("monthCount", monthCount);
        resultMap.put("monthMoneySum", monthMoneySum.toString());
        return resultMap;
    }


    @Override
    public List<Double> selectDailyCountByShopId(Map map, List numList) throws Exception {
        List numList2 = new ArrayList();
        List<Map<String, Object>> listMaps = orderBaseInfoMapper.selectDailyCountByShopId(map);
        List<Double> percentList = new ArrayList<Double>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();
        List<String> dateList = new ArrayList<String>();
        String date = "";
        String startDate = DateUtils.getAfterDay(sdf.format(currentTime), -6, "yyyy-MM-dd");
        for (int i = 0; i <= 6; i++) {
            date = DateUtils.getAfterDay(startDate, i, "yyyy-MM-dd");
            dateList.add(date);
        }

        loop:
        for (String d : dateList) {
            for (Map<String, Object> m : listMaps) {
                if (d.equals(m.get("createDate"))) {
                    numList2.add(m.get("orderAmount"));
                    continue loop;

                }
            }
            numList2.add(0);
        }
        for (int i = 0; i < 7; ) {
            if (Integer.parseInt(numList.get(i).toString()) != 0 && Integer.parseInt(numList2.get(i).toString()) != 0) {
                double percent = (Double.parseDouble(numList2.get(i).toString()) / (Double.parseDouble(numList.get(i).toString())));
                BigDecimal bd = new BigDecimal(percent * 100);
                bd = bd.setScale(1, RoundingMode.HALF_UP);
                percentList.add(bd.doubleValue());
                i++;
            } else {
                percentList.add(0.0);
                i++;
            }
        }
        return percentList;
    }
    /**
     * 将开团时间到了的订单状态改变为待评价状态
     */
    @Scheduled(cron="0 0/5 * * * ? ")
    @Override
    public void orderToStart(){
    	try{
    		List<String> orderNos = orderBaseInfoMapper.selectWaitEvaluteOrderNo();
    		if(orderNos != null && orderNos.size() >0){
	    		int result = orderBaseInfoMapper.orderToStart(orderNos);
	    		System.out.println("开团数人数"+result);
    		}else{
    			System.out.println("无等待订单");
    		}
    		for (String orderNo : orderNos) {
				orderStatusInfoService.changeStatus(orderNo, 4);
				financeInfoService.generatorBill(orderNo);
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    /**
     * 将开团时间到了的订单状态改变为待评价状态
     */
    @Scheduled(cron="0 0 0/1 * * ?  ")
    public void orderToFinished(){
    	try{
    		List<String> orderNos = orderBaseInfoMapper.selectCommentedOrderNo();
    		if(orderNos != null && orderNos.size() >0){
    			int result = orderBaseInfoMapper.orderToFinished(orderNos);
    			System.out.println("开团数人数"+result);
    		}else{
    			System.out.println("无等待订单");
    		}
    		for (String orderNo : orderNos) {
    			orderStatusInfoService.changeStatus(orderNo, OrderConstact.OrderStatusEnum.COMPLETED.getCode());    			
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
  
    @Override
    public OrderBaseInfo getOrderByOrderNo(String orderNo) {
        return orderBaseInfoMapper.getOrderByOrderNo(orderNo);
    }

	@Override
	public Result<Object> urgeOrder(String orderNo) {
		OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
		if(orderBaseInfo == null){
			return new Result<>(0,"error","订单不存在");
		}
		int result =sellerReceiveConfigService.sendMessageToSeller(orderBaseInfo.getSellerId(), 1);
		if(result>0){
			return new Result<>(1,"success","催单成功，商家会尽快处理。");
		}
		return new Result<>(1,"success","请耐心待定商户会尽快处理");
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
		}
		
		return stateStr;
	}
	int changeOrderState(int orderState){
		int newState = 0;
		switch(orderState){
		case 101:
			newState = 1;
			break;
		case 201:
			newState = 2;
			break;
		case 202:
			newState = 3;
			break;
		case 405:
			newState = 11;
			break;
		case 406:
			newState = 9;
			break;
		case 305:
			newState = 9;
			break;
		case 302:
			newState = 10;
			break;
		case 303:
			newState = 10;
			break;
		case 304:
			newState = 10;
			break;
		case 407:
			newState = 10;
			break;
		case 301:
			newState = 4;
			break;
		case 401:
			newState = 12;
			break;
		case 402:
			newState = 5;
			break;
		case 6:
			newState = 6;
			break;
		}
		
		return newState;
	}
}
