package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;

import com.zzk.dao.OrderDetailInfoMapper;
import com.zzk.dao.PlanInfoMapper;
import com.zzk.dao.ProductStockInfoMapper;
import com.zzk.dao.RefundInfoMapper;
import com.zzk.dao.SellerChooseAttributeInfoMapper;
import com.zzk.dao.SellerChooseMessageRemindingMapper;
import com.zzk.dao.SellerMessageNotifyMapper;
import com.zzk.entity.Hotel;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderDetailInfo;
import com.zzk.entity.PlanInfo;
import com.zzk.entity.ProductStockInfo;
import com.zzk.entity.RefundInfo;
import com.zzk.entity.SellerChooseMessageReminding;
import com.zzk.entity.SellerMessageNotify;
import com.zzk.util.DateUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.zzk.dao.SellerReceiveConfigMapper;
import com.zzk.entity.SellerReceiveConfig;
import com.zzk.link.IndustrySMS;
import com.zzk.link.Template;

/**
 * 商家消息接收设置表
 *
 * @author: Kun
 * @date: 2018-04-03 15:12
 */
@Service("sellerReceiveConfigService")
public class SellerReceiveConfigServiceImpl implements SellerReceiveConfigService {
	@Value("${companyPhone}")
	private  String phones;
    @Resource
    private SellerReceiveConfigMapper sellerReceiveConfigMapper;
    @Resource
    private SellerChooseMessageRemindingMapper sellerChooseMessageRemindingMapper;
    @Resource
    private SellerChooseAttributeInfoMapper sellerChooseAttributeInfoMapper;
    @Resource
    private OrderDetailInfoMapper orderDetailInfoMapper;
    @Resource
    private ProductStockInfoMapper productStockInfoMapper;
    @Resource
    private PlanInfoMapper planInfoMapper;
    @Resource
    private RefundInfoMapper refundInfoMapper;
    @Resource
    private SellerMessageNotifyMapper sellerMessageNotifyMapper;
    @Resource
    private HotelService hotelService;
    @Resource
    private SearchService searchService;
    @Resource
    private HotelGoodsService hotelGoodsService;
    /**
     * 分页查询
     */
    @Override
    public List<SellerReceiveConfig> selectByPage(Map map) {
        return sellerReceiveConfigMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return sellerReceiveConfigMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public SellerReceiveConfig selectByPrimaryKey(String id) {
        return sellerReceiveConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(SellerReceiveConfig bean) {
        return sellerReceiveConfigMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(SellerReceiveConfig bean) {
        return sellerReceiveConfigMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        SellerReceiveConfig bean = sellerReceiveConfigMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return sellerReceiveConfigMapper.updateByPrimaryKey(bean);
    }

    @Override
    public Map<String, Object> selectBySellerId(String sellerId) {
        return sellerReceiveConfigMapper.selectBySellerId(sellerId);
    }

    @Override
    public int saveReceiveConfig(String data) {
        JSONObject jsonData = JSONObject.fromObject(data);
        String phone = jsonData.optString("phone");
        String email = jsonData.optString("email");
        String servicePhone = jsonData.optString("servicePhone");
        String sellerId = jsonData.optString("sellerId");
        JSONObject newOrderMessage = jsonData.getJSONObject("newOrderMessage");
        JSONObject refundMessage = jsonData.getJSONObject("refundMessage");
        JSONObject badEvaluateMessage = jsonData.getJSONObject("badEvaluateMessage");
        JSONObject cancelOrderMessage = jsonData.getJSONObject("cancelOrderMessage");
        JSONObject financeMessage = jsonData.getJSONObject("financeMessage");
        JSONObject fullGroupMessage = jsonData.getJSONObject("fullGroupMessage");

        //消息接受设置主键ID
        String receiveConfigId = StringUtils.getUUID();

        //保存或者新增消息接受设置
        SellerReceiveConfig sellerReceiveConfig = sellerReceiveConfigMapper.getBySellerId(sellerId);
        int receiveConfigResult = 0;
        if (sellerReceiveConfig != null) {
            //更新消息接受设置
            sellerReceiveConfig.setUpdateTime(new Date());
            //手机号
            sellerReceiveConfig.setSellerPhone(phone);
            //前台电话
            sellerReceiveConfig.setServicePhone(servicePhone);
            sellerReceiveConfig.setEmail(email);
            receiveConfigResult = sellerReceiveConfigMapper.updateByPrimaryKey(sellerReceiveConfig);
        } else {
            //新增消息接受设置
            sellerReceiveConfig = new SellerReceiveConfig();
            sellerReceiveConfig.setId(receiveConfigId);
            //手机号
            sellerReceiveConfig.setSellerPhone(phone);
            //前台电话
            sellerReceiveConfig.setServicePhone(servicePhone);
            sellerReceiveConfig.setEmail(email);
            sellerReceiveConfig.setStatus(1);
            sellerReceiveConfig.setCreateTime(new Date());
            sellerReceiveConfig.setUpdateTime(new Date());
            sellerReceiveConfig.setSellerId(sellerId);
            receiveConfigResult = sellerReceiveConfigMapper.insertSelective(sellerReceiveConfig);
        }

        //消息提醒设置新增
        sellerChooseMessageRemindingMapper.deleteOldBySellerId(sellerId);
        List<SellerChooseMessageReminding> list = new ArrayList<>();
        SellerChooseMessageReminding bean = handelChooseMessageReminding(newOrderMessage,receiveConfigId,sellerId);
        list.add(bean);
        bean = handelChooseMessageReminding(refundMessage,receiveConfigId,sellerId);
        list.add(bean);
        bean = handelChooseMessageReminding(badEvaluateMessage,receiveConfigId,sellerId);
        list.add(bean);
        bean = handelChooseMessageReminding(cancelOrderMessage,receiveConfigId,sellerId);
        list.add(bean);
        bean = handelChooseMessageReminding(financeMessage,receiveConfigId,sellerId);
        list.add(bean);
        bean = handelChooseMessageReminding(fullGroupMessage,receiveConfigId,sellerId);
        list.add(bean);
        int chooseMessageResult = sellerChooseMessageRemindingMapper.insertBatch(list);

        if (chooseMessageResult >0 && receiveConfigResult > 0){
            return 1;
        } else {
            return 0;
        }
    }

    private SellerChooseMessageReminding handelChooseMessageReminding (JSONObject jsonObject,String receiveId,String sellerId){
        SellerChooseMessageReminding bean = new SellerChooseMessageReminding();
        bean.setId(StringUtils.getUUID());
        bean.setReceiveId(receiveId);
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        bean.setStatus(1);
        bean.setSellerId(sellerId);
        bean.setMessageId(jsonObject.optString("id"));
        bean.setMessageName(jsonObject.optString("name"));
        JSONArray content = jsonObject.getJSONArray("content");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<content.size(); i++){
            String item = content.getString(i);
            if ( sb.length() < 1 ){
                sb.append(item);
            } else {
                sb.append(",");
                sb.append(item);
            }
        }
        bean.setMessageContent(sb.toString());
        return bean;
    }
    @Override
    public int sendMessageToSeller(String sellerId,int type){
    	//type 1 新订单  2 退款订单  3取消订单 4 差评提醒 5 财务提醒 6 满团提醒
    	//获取商家的消息设置配置
    	String messageName  =  "";
    	String SMSContent = "";
    	SellerReceiveConfig config = sellerReceiveConfigMapper.getBySellerId(sellerId);
    	switch (type) {
		case 1:
			SMSContent = Template.NEW_ORDER;
			messageName = "新订单提醒";
			break;
		case 2:
			SMSContent = Template.REFUND_ORDER;
			messageName = "退款提醒";	
				break;
		case 3:
			SMSContent  = Template.CANCEL_ORDER;
			messageName = "取单提醒";
			break;
		case 4:
			SMSContent = Template.BAD_EVALUATE;
			messageName = "差评提醒";
			break;
		case 5:
			SMSContent = Template.FINANCE_MESSAGE;
			messageName = "财务提醒";
			break;
		case 6:
			SMSContent = Template.FULL_GROUP;
			messageName = "满团提醒";
			break;

		default:
			messageName = null;
			break;
		}
    	if(StringUtils.isBlank(messageName)){
    		return -1;
    	}
    		SellerChooseMessageReminding reminding = sellerChooseMessageRemindingMapper.selectBySellerIdAndMessaegName(sellerId, messageName);
    		if(reminding == null){
    			return -1;
    		}
    		String messageContent = reminding.getMessageContent();
    		if(StringUtils.isBlank(messageContent)){
    			return -1;
    		}
    		if(messageContent.indexOf("站内提醒") != -1){
    			addInnerMessage(sellerId,type);
    		}
    		if(messageContent.indexOf("短信通知") != -1){
    			String phoneNumber = config.getSellerPhone();
    			if(!StringUtils.isBlank(phoneNumber)){
    				HashMap<String, Object> link = IndustrySMS.link(phoneNumber, SMSContent, "", null);
    				String status=	(String)link.get("status");
    				return Integer.parseInt(status);
    			}
    		}
    		if(messageContent.indexOf("邮件通知") != -1){
    			
    		}
    		if(messageContent.indexOf("电话通知") != -1){
    			
    		}
    	
    	return -1;
    }
    
    private int addInnerMessage(String sellerId,int type){
    	SellerMessageNotify messageNotify = sellerMessageNotifyMapper.selectMessageByTypeAndSellerId(sellerId, type);
    	if(messageNotify == null){
    		SellerMessageNotify notify = new SellerMessageNotify();
    		notify.setId(StringUtils.getUUID());
    		notify.setSellerId(sellerId);
    		notify.setType(type);
    		notify.setStatus(1);
    		notify.setMessageNumber(1);
    		notify.setCreateDate(DateUtils.getCurrentDate());
    		notify.setUpdateDate(DateUtils.getCurrentDate());
    	   return	sellerMessageNotifyMapper.insert(notify);
    	}
    	messageNotify.setMessageNumber(messageNotify.getMessageNumber()+1);
    	messageNotify.setUpdateDate(DateUtils.getCurrentDate());
    	return sellerMessageNotifyMapper.updateByPrimaryKeySelective(messageNotify);
    }
    
    @Override
    public int sendMessageToBuyer(OrderBaseInfo order,int type){
    	//type类型 1预订（非立即确认的预订）2，确认 3，预订（立即确认的预订）4，商家拒单5，系统拒单6，退款申请 7 系统取消
    	String SMSContent = "";
    	String[] param = null;
    	if(type == 1){
    		SMSContent = Template.CREATE_ORDER;
    		Date date=DateUtils.getBeforeOrAfterDateByHour(order.getOrderTime(), 1);
    		String dateTime = DateUtils.changeDateToStr(date, DateUtils.CHINAESE_DATETIME_FORMAT_YMDHM);
    		param = new String[]{dateTime};
    	}
    	if(type == 2){
    		SMSContent = Template.CONFIRM_ORDER;
    		OrderDetailInfo detailInfo = orderDetailInfoMapper.selectByOrderNo(order.getOrderNo());
    		String phone=sellerChooseAttributeInfoMapper.selectPhoneNumberByProductId(detailInfo.getProductId());
    		PlanInfo planInfo = planInfoMapper.getDetailModePlanInfo(detailInfo.getProductId());    		
    		String days = String.valueOf(planInfo.getDays());
    		String startAddress = planInfo.getStartAddress();
    		String tripTime = DateUtils.changeDateToStr(order.getTripTime(), DateUtils.CHINESE_DATE_FORAMT_YMD);
    		param = new String[]{order.getOrderNo(),tripTime,startAddress,days,phone};
    	}
    	if(type == 3){
    		
    	}
    	if(type == 4){
    		SMSContent = Template.REFUSE_ORDER;
    		OrderDetailInfo detailInfo = orderDetailInfoMapper.selectByOrderNo(order.getOrderNo());
    		PlanInfo planInfo = planInfoMapper.getDetailModePlanInfo(detailInfo.getProductId()); 
    		String surname = order.getContactPerson().substring(0, 1);
    		String tripTime = DateUtils.changeDateToStr(order.getTripTime(), DateUtils.CHINESE_DATE_FORAMT_YMD);
    		String startAddress = planInfo.getStartAddress();
    		String days = String.valueOf(planInfo.getDays());
    		String url = "www.daxi51.com";
    		param= new String[]{surname,tripTime,startAddress,days,url};
    	}
    	if(type == 6){
    		SMSContent = Template.REFUND_APPLY;
    		OrderDetailInfo detailInfo = orderDetailInfoMapper.selectByOrderNo(order.getOrderNo());
    		PlanInfo planInfo = planInfoMapper.getDetailModePlanInfo(detailInfo.getProductId());    		
    		RefundInfo refundInfo = refundInfoMapper.selectByOrderNo(detailInfo.getOrderNo());
    		String tripTime = DateUtils.changeDateToStr(order.getTripTime(), DateUtils.CHINESE_DATE_FORAMT_YMD);
    		String startAddress = planInfo.getStartAddress();
    		String days = String.valueOf(planInfo.getDays());
    		param = new String[]{tripTime,startAddress,days,detailInfo.getOrderNo(),refundInfo.getRefundAmount().toString()};
    	}
    	if(type == 7){
    		SMSContent = Template.SYSTEM_REFUSE_ORDER;
    		String orderNo = order.getOrderNo();
    		String productId = orderDetailInfoMapper.getProductIdByOrderNo(orderNo);
    		String surname = order.getContactPerson().substring(0, 1);
    		String url = "www.daxi51.com/#/details" + productId;
    		param= new String[]{surname,url};
    	}
    	 HashMap<String, Object> link=IndustrySMS.link(order.getContactPhone(), SMSContent, "", param);
    	 String status=	(String)link.get("status");
		 return Integer.parseInt(status);
    	
    }

    @Override
    public int sendHotelMessageToBuyer(OrderBaseInfo order,int type){
    	//type类型 1预订（非立即确认的预订）2，确认 3，预订（立即确认的预订）4，商家拒单5，系统拒单6，退款申请 7 系统取消
    	String SMSContent = "";
    	String[] param = null;
    	if(type == 1){
    		SMSContent = Template.CREATE_ORDER;
    		HotelGoods bean = hotelGoodsService.selectByPrimaryKey(order.getGoodsId());
    		Date date;
    		if(bean.getCancel()==0){
    			String cancelTime = bean.getCancelTime();
    			date=DateUtils.getBeforeOrAfterDateByHour(order.getOrderTime(), Integer.valueOf(cancelTime));
    		}else{
    			date=DateUtils.getBeforeOrAfterDateByMin(order.getOrderTime(), 30);
    		}
    		String dateTime = DateUtils.changeDateToStr(date, DateUtils.CHINAESE_DATETIME_FORMAT_YMDHM);
    		param = new String[]{dateTime};
    	}
    	if(type == 2){
    		SMSContent = Template.CONFIRM_ORDER_HOTEL;
    		OrderDetailInfo detailInfo = orderDetailInfoMapper.selectByOrderNo(order.getOrderNo());
    		String sellerId = order.getSellerId();
    		Hotel seller = hotelService.selectByPrimaryKey(sellerId);
    		String comeTime = DateUtils.changeDateToStr(order.getComeTime(), DateUtils.CHINESE_DATE_FORAMT_YMD);
    		String hotelName = seller.getName();
    		String skuName = detailInfo.getSkuName();  
    		String goodsNum = order.getGoodsNum();
    		String days = String.valueOf(DateUtils.daysBetween(order.getComeTime(), order.getLeaveTime()));
    		String price = String.valueOf(order.getActualMoney());
    		String address = seller.getAddress();
    		String provinceName="";
    		String cityId = seller.getCityId();
			String areaId = seller.getAreaId();
			if(cityId!=null){
				String shortProvinceId = cityId.substring(0, 2);
				provinceName = hotelService.selecProvince(shortProvinceId);
    		}
			String cityName = hotelService.selecCity(cityId);
			String areaName = hotelService.selectArea(areaId);
			address=provinceName+cityName+areaName+address;
    		String phone = seller.getReceptionPhone();
    		param = new String[]{order.getOrderNo(),comeTime,hotelName+"/"+skuName,goodsNum,days,price,address,phone};
    	}
    	if(type == 3){
    		SMSContent = Template.CREATE_CONFIRM_ORDER_HOTEL;
    		OrderDetailInfo detailInfo = orderDetailInfoMapper.selectByOrderNo(order.getOrderNo());
    		String sellerId = order.getSellerId();
    		Hotel seller = hotelService.selectByPrimaryKey(sellerId);
    		String comeTime = DateUtils.changeDateToStr(order.getComeTime(), DateUtils.CHINESE_DATE_FORAMT_YMD);
    		String hotelName = seller.getName();
    		String skuName = detailInfo.getSkuName();  
    		String goodsNum = order.getGoodsNum();
    		String days = String.valueOf(DateUtils.daysBetween(order.getComeTime(), order.getLeaveTime()));
    		String price = String.valueOf(order.getActualMoney());
    		String address = seller.getAddress();
    		String provinceName="";
    		String cityId = seller.getCityId();
			String areaId = seller.getAreaId();
			if(cityId!=null){
				String shortProvinceId = cityId.substring(0, 2);
				provinceName = hotelService.selecProvince(shortProvinceId);
    		}
			String cityName = hotelService.selecCity(cityId);
			String areaName = hotelService.selectArea(areaId);
			address=provinceName+cityName+areaName+address;
    		String phone = seller.getReceptionPhone();
    		param = new String[]{order.getOrderNo(),comeTime,hotelName+"/"+skuName,goodsNum,days,price,address,phone};
    	}
    	if(type == 4){
    		SMSContent = Template.REFUSE_ORDER_HOTEL;
    		String surname = order.getCheckinPerson().substring(0, 1);
    		String sellerName = order.getSellerName();
    		Hotel seller = hotelService.selectByPrimaryKey(order.getSellerId());
    		Map<String,Object> parameters = new HashMap<String,Object>();
    		Page<Hotel> pager = new Page<Hotel>();
    		Double lat = seller.getLat();
    		Double lon = seller.getLon();
    		Date checkinDate = order.getComeTime();
    		Date leaveDate =order.getLeaveTime();
    		String url="";
    		String hotelName = "";
    		try {
				searchService.search1(parameters, null,lat,lon, 1, 10,pager,0,100000,checkinDate,leaveDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		List<Hotel> list= pager.getList();
    		if(list.size()>=2){
    			Hotel bean = list.get(1);
    			hotelName = bean.getName();
    			url = "http://www.daxi51.com/hotel/#/hotelMainDetail?hotelId="+bean.getId()+"&intoDate="+DateUtils.changeDateToStr(checkinDate, DateUtils.DEFAULT_DATE_FORMAT_YMD)+"&leaveDate="+DateUtils.changeDateToStr(leaveDate, DateUtils.DEFAULT_DATE_FORMAT_YMD);
    		}else{
    			url = "http://www.daxi51.com/hotel/#/";
    		}
    		param= new String[]{surname,sellerName,hotelName,url};
    	}
    	if(type == 5){
    		SMSContent = Template.REFUSE_ORDER_HOTEL_SYSTEM;
    		String surname = order.getCheckinPerson().substring(0, 1);
    		String sellerName = order.getSellerName();
    		Hotel seller = hotelService.selectByPrimaryKey(order.getSellerId());
    		Map<String,Object> parameters = new HashMap<String,Object>();
    		Page<Hotel> pager = new Page<Hotel>();
    		Double lat = seller.getLat();
    		Double lon = seller.getLon();
    		Date checkinDate = order.getComeTime();
    		Date leaveDate =order.getLeaveTime();
    		String url="";
    		String hotelName = "";
    		try {
				searchService.search1(parameters, null,lat,lon, 1, 10,pager,0,100000,checkinDate,leaveDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		List<Hotel> list= pager.getList();
    		if(list.size()>=2){
    			Hotel bean = list.get(1);
    			hotelName = bean.getName();
    			url = "http://www.daxi51.com/hotel/#/hotelMainDetail?hotelId="+bean.getId()+"&intoDate="+DateUtils.changeDateToStr(checkinDate, DateUtils.DEFAULT_DATE_FORMAT_YMD)+"&leaveDate="+DateUtils.changeDateToStr(leaveDate, DateUtils.DEFAULT_DATE_FORMAT_YMD);
    		}else{
    			url = "http://www.daxi51.com/hotel/#/";
    		}
    		param= new String[]{surname,sellerName,hotelName,url};
    	}
    	if(type == 6){
    		SMSContent = Template.REFUND_APPLY_HOTEL;
    		String comeTime = DateUtils.changeDateToStr(order.getComeTime(), DateUtils.CHINESE_DATE_FORAMT_YMD);
    		String sellerName = order.getSellerName();
    		String orderNo = order.getOrderNo();
    		param= new String[]{comeTime,sellerName,orderNo};
    	}
    	 HashMap<String, Object> link=IndustrySMS.link(order.getContactPhone(), SMSContent, "", param);
    	 String status=	(String)link.get("status");
		 return Integer.parseInt(status);
    }

    @Override
    public  void sendMessageToAgent(int type,OrderBaseInfo order,RefundInfo refund){
    	if(type == 1){
    		IndustrySMS.link(phones, Template.AGENT_ORDER, "", new String[]{order.getOrderNo(),order.getSellerName(),order.getOrderMoney().toString()});  		
    	}
    	if(type == 2){
    		IndustrySMS.link(phones, Template.AGENT_REFUND_ORDER, "", new String[]{refund.getMchRefundNo(),refund.getRefundAmount().toString()});
    	}
    	if(type ==3){
    		IndustrySMS.link(phones, Template.AGENT_REFUND_FAILD, "", new String[]{order.getOrderNo()});
    	}
    }

}
