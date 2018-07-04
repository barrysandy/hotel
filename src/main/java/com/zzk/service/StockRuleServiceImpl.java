package com.zzk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.util.DateUtils;
import com.zzk.util.StringUtils;
import com.zzk.dao.StockRuleMapper;
import com.zzk.entity.Order;
import com.zzk.entity.RoomType;
import com.zzk.entity.StockRule;
import com.zzk.entity.SwitchRule;
import com.zzk.service.HotelGoodsService;
import com.zzk.service.OrderService;
import com.zzk.service.RoomTypeService;
import com.zzk.service.StockRuleService;
import com.zzk.service.SwitchRuleService;

/**
 * 
 * 库存规则信息
* @author：sty
* @date：2017-11-02 10:41
 */
@Service("stockRuleService")
public class StockRuleServiceImpl implements StockRuleService {
	@Resource
	private StockRuleMapper stockRuleMapper;
	@Resource
	private OrderService orderService;
	@Resource
	private HotelGoodsService hotelGoodsService;
	@Resource
	private RoomTypeService roomTypeService;
	@Resource
	private SwitchRuleService switchRuleService;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<StockRule> selectByPage(Map map) {
		return stockRuleMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return stockRuleMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public StockRule selectByPrimaryKey(String id) {
		return stockRuleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(StockRule bean) {
		return stockRuleMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:41
	 */
	@Override
	public int insert(StockRule bean) {
		return stockRuleMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:41
	 */
	@Override
	public int delete(String id) {
		StockRule bean = stockRuleMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return stockRuleMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 逻辑删除
	* @param roomtypeId
	* @return
    * @author：huashuwen
    * @date：2017-11-08 
	 */
	@Override
	public List<StockRule> selectByRoomtype(String roomtypeId,String startTime){
		
		return stockRuleMapper.selectByRoomtypeId(roomtypeId,startTime);
	}

	@Override
	public List<StockRule> selectByGoodsId(String goodsId, String startTime) {
		return stockRuleMapper.selectByGoodsId(goodsId,startTime);
	}

	@Override
	public List<StockRule> selectByHotelId(String hotelId, String startTime) {
		
		return stockRuleMapper.selectByHotelId(hotelId,startTime);
	}
   /**
    * 查询是否满房 满房
    * @param userId
    * @return -1 满房 1 正常
 * @throws Exception 
    */
	@Override
	public int  selectByHotelIdFull(String shopId) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD);
		Date date = new Date();
		String startTime= format.format(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		String endTime = format.format(calendar.getTime());
		List<StockRule> stockRuleList =stockRuleMapper.selectByHotelId(shopId, startTime);
		List<SwitchRule> switchRuleList = switchRuleService.selectByHotelId(shopId, startTime);
		List<RoomType> roomTypeList = roomTypeService.selectByHotelId(shopId);
		List<HashMap<String, Object>> orderList = orderStockNum(shopId);
		List<Map<String, Object>> mapsList= getStockInfo(orderList, stockRuleList, roomTypeList, switchRuleList, startTime, endTime);
		int code = 0;
		for (Map<String, Object> map2 : mapsList) {
			Map<String,Object> map3 = (Map)map2.get(startTime);
			if("1".equals(map3.get("isOpen").toString())){
			 int i= Integer.parseInt(map3.get("stock").toString());
			 code+=i;
			}
		}

		return code>0?1:0;

		}
	
	
	public List<HashMap<String, Object>> orderStockNum(String shopId){
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS);
		Order bean = new Order();
		bean.setShopId(shopId);
		int size = 0;
		Calendar calendar = Calendar.getInstance();
		bean.setComeTime(calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		bean.setLeaveTime(calendar.getTime());
	    return  orderService.selectByHotelId(bean);
	}

	@Override
	public List<StockRule> selectByRoomtypeList(List<String> list, String startTime) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("list", list);
		map.put("startTime", startTime);
		return stockRuleMapper.selectByRoomtypeList(map);
	}

	@Override
	public List<Map<String, Object>> getStockInfo(
			List<HashMap<String, Object>> orderList,
			List<StockRule> stockRuleList, List<RoomType> roomTypeList,
			List<SwitchRule> switchRuleList, String startTime, String endTime) throws Exception {
		
		
		List<Map<String,Object>> stockMapList = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD);
		int days=DateUtils.daysBetween(DateUtils.parseDate(startTime, DateUtils.DEFAULT_DATE_FORMAT_YMD),DateUtils.parseDate(endTime, DateUtils.DEFAULT_DATE_FORMAT_YMD));
		Calendar calendar = Calendar.getInstance();
		List<String> dateList = new ArrayList<String>();
		List<String> dateListTemp = new ArrayList<String>();
		int dayOfWeek1=0;
		String date;
		String stock= "";
		int isOpen = 0;
		Date date3 ;
		int goodNum=0;
		
		for(RoomType roomType : roomTypeList){
			
			
			Map<String,Integer> saleNumMap= new HashMap<String,Integer> ();
			Map<String,String> stockMap = new HashMap<String,String>();
			Map<String,String> stockMap1 = new HashMap<String,String>();
			for(int i=0;i<days;i++){
				date = DateUtils.getAfterDay(startTime, i, DateUtils.DEFAULT_DATE_FORMAT_YMD);
				dateListTemp.add(date);
			}
			dateList.addAll(dateListTemp);
			if(stockRuleList.size() == 0){
				for(String d :dateList){
					stockMap1.put(d,"");
				}
			}
			for(StockRule stockRule:stockRuleList){
				Iterator<String> it = dateList.iterator();
				if(roomType.getId().equals(stockRule.getRoomtypeId())){
					stock = stockRule.getStock();
					while (it.hasNext()) { 
						 String date1  = it.next();  
						 	if(DateUtils.compareDate(stockRule.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(stockRule.getEndTime(), sdf.parse(date1))>=0){
						    	 if(stockRule.getType()==2){
						    		 calendar.setTime(sdf.parse(date1));
						    		 dayOfWeek1 = calendar.get(7)-1;
									if(dayOfWeek1==0){
										dayOfWeek1=7;
									}
									if(String.valueOf(stockRule.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
										stockMap1.put(date1, stock);
										it.remove();
										continue;
									}
						    	 }else{
						    		 stockMap1.put(date1, stock);
										it.remove();
							     }
						     }
						} 
				}
			}
			if(dateList.size()!=0){
				 for(String d:dateList){
					 stockMap1.put(d, "");
				 }
			}
			dateList.clear();
			dateList.addAll(dateListTemp);
			for(String date1:dateList){
				date3 = sdf.parse(date1);
				goodNum = 0;
				for(Map m:orderList){
					if(roomType.getId().equals((String)m.get("roomtypeId"))){
						if(DateUtils.compareDate(date3,(Date)m.get("LEAVE_TIME"))<0&&DateUtils.compareDate(date3,(Date)m.get("COME_TIME"))>=0){
							goodNum+=Integer.parseInt((String)m.get("GOODS_NUM"));
						}
					}
				}
				saleNumMap.put(date1, goodNum);
			}
			dateList.clear();
			for(Entry<String,String> me :stockMap1.entrySet()){
				if(!StringUtils.isEmpty(me.getValue())){
					stockMap.put(me.getKey(), String.valueOf(Integer.parseInt(me.getValue())-saleNumMap.get(me.getKey())));
				}else{
					stockMap.put(me.getKey(), "");
				}
			}
			
			stockMap.put("initStock", roomType.getInitStock());
			Map<String,Integer> switchMap = new HashMap<String,Integer>();
			dateList.addAll(dateListTemp);
			if(switchRuleList.size() == 0){
				for(String d :dateList){
					switchMap.put(d,1);
				}
			}
			for(SwitchRule switchRule:switchRuleList){
				Iterator<String> it = dateList.iterator();
				if(roomType.getId().equals(switchRule.getRoomtypeId())){
					isOpen = switchRule.getIsOpen();
					while (it.hasNext()) { 
						 String date1  = it.next();  
						 	if(DateUtils.compareDate(switchRule.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(switchRule.getEndTime(), sdf.parse(date1))>=0){
						    	 if(switchRule.getType()==2){
						    		 calendar.setTime(sdf.parse(date1));
						    		 dayOfWeek1 = calendar.get(7)-1;
									if(dayOfWeek1==0){
										dayOfWeek1=7;
									}
									if(String.valueOf(switchRule.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
										switchMap.put(date1, isOpen);
										it.remove();
										continue;
									}
						    	 }else{
						    		 switchMap.put(date1, isOpen);
										it.remove();
							     }
						     }
						} 
				}
			}
			if(dateList.size()!=0){
				 for(String d:dateList){
					 switchMap.put(d, 1);
				 }
			}
			dateList.clear();
			dateList.addAll(dateListTemp);
			Map<String,Object> xMap = new HashMap(); 
			for(String date2:dateList){
				Map<String,Object> m = new HashMap(); 
				m.put("isOpen", switchMap.get(date2));
				m.put("stock", stockMap.get(date2));
				xMap.put(date2, m);
			}
			xMap.put("name", roomType.getApartmentName());
			xMap.put("id", roomType.getId());
			stockMapList.add(xMap);
		}
		return stockMapList;
	}
	

}
	
	
