package com.zzk.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;






import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zzk.util.PageView;
import com.zzk.util.DateUtils;
import com.zzk.util.StringUtils;
import com.zzk.dao.EvaluateMapper;
import com.zzk.dao.ProductEvaluateMapper;
import com.zzk.entity.Evaluate;
import com.zzk.entity.EvaluateCustom;
import com.zzk.entity.Hotel;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.ProductEvaluate;
import com.zzk.service.EvaluateService;
import com.zzk.service.HotelService;
import com.zzk.service.OrderService;

@Service("evaluateService")
public class EvaluateServiceImpl implements EvaluateService {
	
	@Resource 
	private HotelService hotelService;
	
	@Resource
	private OrderService orderService;
	
	@Resource  
	private EvaluateMapper evaluateMapper;
	@Resource  
	private ProductEvaluateMapper productEvaluateMapper;
	
	@Resource
	private MessageService messageService;
	@Resource
	private OrderBaseInfoService orderBaseInfoService;
	
	public List<Map<String,Object>> selectAllEvaluate(Map<String,Object> map){
		String evaluateType = MapUtils.getString(map,"evaluateType");
		if (StringUtils.isNotBlank(evaluateType)){
            switch (evaluateType){
                case "haveImg" :
                    map.put("haveImg","haveImg");
                    map.remove("evaluateType");
                    break;
                case "noReply" :
                    map.put("noReply","noReply");
                    map.remove("evaluateType");
                    break;
                default :
            }
        }
		List<Map<String,Object>> list=selectByPage(map);
		
		
		
		return list;
	}

	@Override
	public List<Map<String,Object>> selectByPage(Map map) {
		List<Map<String,Object>> list = evaluateMapper.selectByPage(map);
		return list;
	}

	@Override
	public int insertEvaluate(Evaluate record) {
		Order order = orderService.selectByPrimaryKey(record.getOrderId());
		String orderNo= order.getOrderNum();
		String shopId  = record.getShopId();
		String roomTypeId = record.getRoomtypeId();
		String goodsId = record.getGoodsId();
		if(order ==null){
			return -1;
		}
		if(shopId == null){
			shopId= order.getShopId();
		}
		if(goodsId==null){
			goodsId = order.getGoodsId();
		}
		if(record.getCreaterId()==null){
			record.setCreaterId(order.getBuyerId());
		}		
		double score = (double)(record.getHealthScore()+record.getEvnScore()+record.getServiceScore()+record.getFacilityScore())/4;
		BigDecimal scoreBigDecimal = new BigDecimal(score);
        /*setScale(a,b) : a=1保留一位小数 b=四舍五入 */
        score = scoreBigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        String evaluateType;
        if (score < 2.0) {
            evaluateType = "差评";
        } else if (score >= 4.0) {
            evaluateType = "好评";
        } else {
            evaluateType = "中评";
        }
        double mid = 2.0;
		record.setScore(score);
		if(score<mid){
			messageService.sendMessage(record.getShopId(), MessageService.BADCOMMENT_SENDTYPE);
		}
		//vo组装Do
		ProductEvaluate bean = new ProductEvaluate();
		bean.setId(StringUtils.getUUID());
        bean.setStatus(1);
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        bean.setFeaturesScore(record.getFacilityScore());
        bean.setServiceScore(record.getServiceScore());
        bean.setSecurityScore(record.getEvnScore());
        bean.setHealthScore(record.getHealthScore());
        bean.setUserId(record.getCreaterId());
        bean.setOrderNo(order.getOrderNum());
        bean.setScore(record.getScore());
        // 0=未回复 1=已回复
        bean.setReplayState(0);
        bean.setContent(record.getContent());
        bean.setImages(record.getImgs());
        bean.setProductId(record.getRoomtypeId());
        bean.setSkuId(record.getGoodsId());
        bean.setType(2);
        bean.setEvaluateType(evaluateType);
        int code =  insert(bean);
		if(code>0){
			order.setOrderState(401);
			orderService.update(order);
			//有问题
			EvaluateCustom custom =selectCountByshopId(shopId);
			String count = custom.getAlldata();
			int intAcount = 0;
			if(count!=null){
				intAcount = Integer.parseInt(count);
			}
			DecimalFormat df = new DecimalFormat("#0.0");  
			double avscore = custom.getAvgscore();
			avscore = Double.parseDouble(df.format(avscore));
			Hotel hotel = hotelService.selectByPrimaryKey(shopId);
			hotel.setCommentScore(avscore);
			hotel.setCommentNum(intAcount);
			hotelService.update(hotel);
			return code;
		}else{
			return -1;
		}
	}
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return evaluateMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProductEvaluate record) {
		
		return productEvaluateMapper.insert(record);
	}

	

	@Override
	public Evaluate selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Evaluate record) {
		// TODO Auto-generated method stub
		return evaluateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Evaluate record) {
		// TODO Auto-generated method stub
		return evaluateMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(Evaluate record) {
		record.setUpdateTime(DateUtils.getDate());
		return evaluateMapper.updateByPrimaryKeySelective(record);
	}
		
	
	@Override
	public List<EvaluateCustom> selectScore(Map map) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectScore(map);
	}
	

	@Override
	public int insertReplyEvaluate(Evaluate record) {
		String id = record.getParentId();
		Evaluate evaluate = selectByPrimaryKey(id);
		if(evaluate != null){
			String roomtypeId = evaluate.getRoomtypeId();
			String shopId = evaluate.getShopId();
			String goodsId =evaluate.getGoodsId();
			String orderId = evaluate.getOrderId();
			record.setId(UUID.randomUUID().toString());
			record.setCreateTime(DateUtils.getDate());
			record.setStatus(1);
			record.setType(2);
			record.setRoomtypeId(roomtypeId);
			record.setShopId(shopId);
			record.setGoodsId(goodsId);
			record.setOrderId(orderId);
			int status =evaluateMapper.insert(record);
			if(status > 0){
				evaluate.setReplyStatus(1);
				return	evaluateMapper.updateByPrimaryKey(evaluate);
			}else{
				return -1;
			}
		}else{
			return -1;
		}
	}

	@Override
	public List<EvaluateCustom> selectByPageReply(Map map) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectByPageReply(map);
	}
	/**
	 * 删除回复
	 */
	@Override
	public int delete(String id) {
		try{
			ProductEvaluate productEvaluate = productEvaluateMapper.selectByPrimaryKey(id);
	        if (productEvaluate == null){
	            return 0;
	        } else {
	            productEvaluate.setReplayState(0);
	            productEvaluate.setReplayContent(null);
	            productEvaluate.setReplayTime(null);
	            productEvaluate.setUpdateTime(new Date());
	            productEvaluateMapper.updateByPrimaryKey(productEvaluate);
	            return 1;
	        }
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public int updateEvaluate(Evaluate evaluate) {
		String id = evaluate.getId();
		Evaluate eva =evaluateMapper.selectByPrimaryKey(id);
		int health = evaluate.getHealthScore();
		int service=evaluate.getServiceScore();
		int evn =evaluate.getEvnScore();
		int facility = evaluate.getFacilityScore();
		double score = (double)(health+service+evn+facility)/4;
		eva.setContent(evaluate.getContent());
		eva.setHealthScore(health);
		eva.setServiceScore(service);
		eva.setEvnScore(evn);
		eva.setFacilityScore(facility);
		eva.setScore(score);
		eva.setImgs(evaluate.getImgs());
		return evaluateMapper.updateByPrimaryKeySelective(eva);
	}

	@Override
	public EvaluateCustom selectCountByshopId(String shopId) {
		EvaluateCustom custom=evaluateMapper.selectCountByshopId(shopId);
		if(custom != null){
		 double heal = Double.parseDouble(custom.getAvghealth());
		 double evn = Double.parseDouble(custom.getAvgevn());
		 double service = Double.parseDouble(custom.getAvgservice());
		 double facility = Double.parseDouble(custom.getAvgfacility());
		 double avgScore = (heal+evn+service+facility)/4;
		 custom.setAvgscore(avgScore);	
		}
		return custom;
	}

	
}
