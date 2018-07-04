package com.zzk.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zzk.util.DateUtils;
import com.zzk.util.Exceptions;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.util.HotelResult;
import com.zzk.common.PlatConstants;
import com.zzk.entity.Dict;
import com.zzk.entity.Hotel;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.HotelPolicy;
import com.zzk.entity.Ico;
import com.zzk.entity.Order;
import com.zzk.entity.PriceRule;
import com.zzk.entity.RoomType;
import com.zzk.entity.StockRule;
import com.zzk.entity.SwitchRule;
import com.zzk.service.CacheService;
import com.zzk.service.CreateIndexService;
import com.zzk.service.DatePriceService;
import com.zzk.service.DictService;
import com.zzk.service.HotelGoodsService;
import com.zzk.service.HotelPolicyService;
import com.zzk.service.HotelService;
import com.zzk.service.IcoService;
import com.zzk.service.OrderService;
import com.zzk.service.PriceRuleService;
import com.zzk.service.RoomTypeService;
import com.zzk.service.StockRuleService;
import com.zzk.service.SwitchRuleService;


/**
 * <p>description：酒店商品信息</p>
 * @name：HotelGoodsController
 * @author：sty
 * @date：2017-11-02 10:44
 */
@Controller
@RequestMapping(value = "/hotelGoods")
public class HotelGoodsController extends BaseController {

	@Autowired
	private HotelGoodsService hotelGoodsService;
	@Autowired
	private StockRuleService stockRuleService;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private PriceRuleService priceRuleService;
	@Autowired
	private SwitchRuleService switchRuleService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private DictService dictService;
	@Autowired
	private IcoService icoService;
	@Autowired
	private HotelPolicyService hotelPolicyService;
	@Autowired
	private CreateIndexService createIndexService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private DatePriceService datePriceService;
	private static List<Dict> dictList;
	private static List<Ico> icoList;
	/**
	 * 酒店商品信息分页查询
	 * @param page
	 * @param model
	 * @param id
	 * @return
	 * @author sty
	 * @date 2017-11-02 10:44
	 */
	/*@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<HotelGoods> list = hotelGoodsService.selectByPage(map);
		int totalNum = hotelGoodsService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("hotelGoods/hotelGoods_list");
	}*/
	
	@RequestMapping("/getGoodsList")
	@ResponseBody
	public String getGoodsList(String hotelId,String startTime,String endTime){
		
		HotelResult result=new HotelResult();
		if(StringUtils.isBlank(hotelId)||
				StringUtils.isBlank(startTime)||
				StringUtils.isBlank(endTime)){
			result.setMsg("error");
			result.setMessage("参数错误");
			result.setState(0);
			return JSON.toJSONString(result);
		}
		Map<String,Object> resultMap=new HashMap<String,Object>(); 
		SimpleDateFormat sdf=new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
		int d = DateUtils.daysBetween(new Date(),DateUtils.parseDate(endTime, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD));
		if(d>90){
			resultMap.put("hotelGoodsSet", new ArrayList());
			result.setData(resultMap);
			result.setMsg("success");
			result.setState(1);
		}
		if(dictList==null){
			dictList=CacheService.getAllDict();
		}
		List<Map<String,String>> stockMapList = new ArrayList<Map<String,String>>();
		List<Map<String,String>> priceMapList = new ArrayList<Map<String,String>>();
		//List<HotelGoods> goodsList = new ArrayList<HotelGoods>();
		//List<HotelGoods> noStockGoodsList = new ArrayList<HotelGoods>();
		//Map<String,Object> map=new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		BigDecimal avgPrice = new BigDecimal(0);
		List<String> dateList = new ArrayList<String>();
		List<String> dateListTemp = new ArrayList<String>();
		List<Integer> stockList = new ArrayList<Integer>();
		int dayOfWeek1=0;
		String date;
		String stock="";
		String price="";
		int isOpen = 0;
		int days=DateUtils.daysBetween(DateUtils.parseDate(startTime, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD),DateUtils.parseDate(endTime, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD));
		try {
			Order bean = new Order();
			bean.setComeTime(sdf.parse(startTime));
			bean.setLeaveTime(sdf.parse(endTime));
			bean.setShopId(hotelId);
			List<HashMap<String,Object>> saleNumMapList = orderService.selectSaleNumMap(hotelId);
			List<HashMap<String,Object>> orderList = orderService.selectByHotelId(bean);
			List<HotelGoods> hotelGoodsList = hotelGoodsService.selectHotelGoods(hotelId);
			List<RoomType> roomTypeList = roomTypeService.selectByHotelId(hotelId);
			List<StockRule> stockRuleList = stockRuleService.selectByHotelId(hotelId,startTime);
			List<PriceRule> priceRuleList = priceRuleService.selectByHotelId(hotelId,startTime);
			List<SwitchRule> switchRuleList = switchRuleService.selectByHotelId(hotelId,startTime);
			List<RoomType> closeRoomTypeList = new ArrayList<RoomType>();
			List<RoomType> closeRoomTypeList1 = new ArrayList<RoomType>();
			Date date3 ;
			int goodNum = 0;
			//判断总房量
			for(RoomType rt : roomTypeList){
				String totalStock= rt.getTotalStock();
				if(!StringUtils.isEmpty(rt.getTotalStock())&&StringUtils.isNumeric(totalStock)){
					for(Map<String,Object> saleNumMap:saleNumMapList){
						if(rt.getId().equals(saleNumMap.get("id"))){
							int saleNum = (Integer)saleNumMap.get("count");
							int x = Integer.parseInt(rt.getTotalStock())-saleNum;
							if(x<=0){
								closeRoomTypeList1.add(rt);
							}
						}
					}
				}
			}
			for(int i=0;i<days;i++){
				date = DateUtils.getAfterDay(startTime, i, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
				dateListTemp.add(date);
			}
			loop1:for(RoomType rt : roomTypeList){
				dateList.addAll(dateListTemp);
				for(SwitchRule switchRule:switchRuleList){
					if(rt.getId().equals(switchRule.getRoomtypeId())){
						isOpen = switchRule.getIsOpen();
						Iterator<String> it = dateList.iterator(); 
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
											if(isOpen == 2){
												closeRoomTypeList.add(rt);
											}
											it.remove();
											continue loop1;
										}
							    	 }else{
							    		if(isOpen == 2){
											closeRoomTypeList.add(rt);
										}
										it.remove();
										continue loop1;
								     }
							     }
							} 
					}
				}
				dateList.clear();
			}
			
			roomTypeList.removeAll(closeRoomTypeList);
			roomTypeList.removeAll(closeRoomTypeList1);
			for(RoomType roomType : roomTypeList){
				dateList.addAll(dateListTemp);
				Map<String,String> stockMap = new HashMap<String,String>();
				for(StockRule stockRule:stockRuleList){
					if(roomType.getId().equals(stockRule.getRoomtypeId())){
						stock = stockRule.getStock();
						Iterator<String> it = dateList.iterator(); 
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
											stockMap.put(date1, stock);
											it.remove();
											continue;
										}
							    	 }else{
								    	 stockMap.put(date1, stock);
											it.remove();
								     }
							     }
							} 
					}
				}
				if(dateList.size() > 0){
					dateList.clear();
					continue;
				}
				dateList.addAll(dateListTemp);
				boolean haveRest = true;
				for(String date2:dateList){
					date3 = sdf.parse(date2);
					goodNum = 0;
					for(Map m:orderList){
						if(roomType.getId().equals((String)m.get("roomtypeId"))){
							if(DateUtils.compareDate(date3,(Date)m.get("LEAVE_TIME"))<0&&DateUtils.compareDate(date3,(Date)m.get("COME_TIME"))>=0){
								goodNum+=Integer.parseInt((String)m.get("GOODS_NUM"));
							}
						}
						if(Integer.parseInt(stockMap.get(date2)) - goodNum - 1 < 0){
							haveRest = false;
							break;
						}
					}
					stockList.add(Integer.parseInt(stockMap.get(date2)) - goodNum);
					if(!haveRest){
						break;
					}
				}
				Collections.sort(stockList);
				Integer minStock = stockList.get(0);
				
				
				dateList.clear();
				/*if(!haveRest){
					continue;
				}*/
				stockMap.put("roomtypeId", roomType.getId());
				stockMap.put("minStock", String.valueOf(minStock));
				stockMapList.add(stockMap);
			}
			for(HotelGoods hotelGoods : hotelGoodsList){
				dateList.addAll(dateListTemp);
				Map<String,String> priceMap = new HashMap<String,String>();
				for(PriceRule priceRule:priceRuleList){
					if(hotelGoods.getId().equals(priceRule.getGoodId())){
						price = priceRule.getPrice();
						Iterator<String> it = dateList.iterator(); 
						 while (it.hasNext()) { 
							 String date1  = it.next();  
							     if(DateUtils.compareDate(priceRule.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(priceRule.getEndTime(), sdf.parse(date1))>=0){
							    	 if(priceRule.getType()==2){
							    		 calendar.setTime(sdf.parse(date1));
							    		 dayOfWeek1 = calendar.get(7)-1;
										if(dayOfWeek1==0){
											dayOfWeek1=7;
										}
										if(String.valueOf(priceRule.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
											priceMap.put(date1, price);
											it.remove();
											continue;
										}
							    	 }else{
							    		 priceMap.put(date1, price);
											it.remove();
								     }
							     }
							} 
					}
				}
				if(dateList.size() > 0){
					dateList.clear();
					continue;
				}
				avgPrice = new BigDecimal(0);
				for(int i=0;i<days;i++){
					date = DateUtils.getAfterDay(startTime, i, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
					avgPrice = avgPrice.add(new BigDecimal(priceMap.get(date)));
				}
				avgPrice=avgPrice.divide(new BigDecimal(days),2,BigDecimal.ROUND_HALF_UP);
				hotelGoods.setPrice(avgPrice.toString());
				/*BigDecimal minPrice = new BigDecimal(0);
				BigDecimal checkPrice;
				for(int i=0;i<days;i++){
					date = DateUtils.getAfterDay(startTime, i, DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD);
					checkPrice = new BigDecimal(priceMap.get(date));
					if(i==0){
						minPrice = checkPrice ;
					}
					if(minPrice.compareTo(checkPrice)==1){
						minPrice = checkPrice;
					}
				}
				hotelGoods.setPrice(minPrice.toString());*/
				
				priceMap.put("goodsId", hotelGoods.getId());
				priceMapList.add(priceMap);
			}
			Set<HotelGoods> hotelGoodsSet = new HashSet<HotelGoods>();
			Set<HotelGoods> fullHotelGoodsSet = new HashSet<HotelGoods>();
			for(HotelGoods hotelGoods :hotelGoodsList){
				for(Map stockMap:stockMapList){
					if(hotelGoods.getRoomtypeId().equals(stockMap.get("roomtypeId"))){
						for(Map priceMap:priceMapList){
							if(hotelGoods.getId().equals(priceMap.get("goodsId"))){
								hotelGoods.setMinStock((String)stockMap.get("minStock"));
								hotelGoods.setBedType(CacheService.getLabel("bedType",hotelGoods.getBedType()));
								String addBed=CacheService.getLabel("addBed",hotelGoods.getAddBed());
								hotelGoods.setAddBed("1".equals(hotelGoods.getAddBed())?"加床需收费"+hotelGoods.getBedFee()+"元":addBed);
								hotelGoods.setSmoke(CacheService.getLabel("smoke",hotelGoods.getSmoke()));
								hotelGoods.setBreakfastStr(CacheService.getLabel("goodsBreakfast",String.valueOf(hotelGoods.getBreakfast())));
								hotelGoods.setIsWindowStr(CacheService.getLabel("isWindow",String.valueOf(hotelGoods.getIsWindow())));
								hotelGoods.setWifiStr(CacheService.getLabel("wideband",String.valueOf(hotelGoods.getFreeWifi())));
								hotelGoods.setCancelStr(CacheService.getLabel("cancel",String.valueOf(hotelGoods.getCancel())));
								String confirmStr=CacheService.getLabel("confirm",String.valueOf(hotelGoods.getConfirm()));
								hotelGoods.setConfirmStr(PlatConstants.CONFIRM_TIME==hotelGoods.getConfirm()?String.valueOf(hotelGoods.getConfirmTime())+"小时内确认":confirmStr);
								if(Integer.valueOf(hotelGoods.getMinStock())>0){
									hotelGoodsSet.add(hotelGoods);
								}else{
									fullHotelGoodsSet.add(hotelGoods);
								}
							}
						}
					}
				}
			}
			List<HotelGoods> list = new ArrayList<>(hotelGoodsSet);
			Collections.sort(list,new Comparator<HotelGoods>(){
				public int compare(HotelGoods o1, HotelGoods o2) {  
		            if(Double.parseDouble(o1.getPrice()) > Double.parseDouble(o2.getPrice())){  
	                    return 1;  
	                }  
	                if(Double.parseDouble(o1.getPrice()) == Double.parseDouble(o2.getPrice())){  
	                    return 0;  
	                }  
	                return -1; 
	            }
			});
			resultMap.put("hotelGoodsSet", list);
			resultMap.put("fullHotelGoodsSet", fullHotelGoodsSet);
		}catch( Exception e){
			System.err.println(e.toString());
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			result.setMsg("error");
			result.setState(0);
			return JSON.toJSONString(result);
		}
		result.setData(resultMap);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	
	
	
	/**
	 * 酒店商品信息编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:44
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			HotelGoods rule = new HotelGoods();
			if(StringUtils.isNotBlank(id)){
				rule = hotelGoodsService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("hotelGoods/hotelGoods_edit");
	}
	/**
	 * 酒店商品信息预览
	* @param id
	* @param model
	* @return
	* @author huashuwen
	* @date 2017-11-02 10:44
	 */
	@RequestMapping("/toView")
	@ResponseBody
	public String toView(String id){
		HotelResult<HotelGoods> result = new HotelResult<HotelGoods>();
		HotelGoods rule = new HotelGoods();
		RoomType room = new RoomType();
		Map<String,String> icoMap=null;
		List<Map<String,String>> icoMapList = new ArrayList<Map<String,String>>();
		
		if(StringUtils.isNotBlank(id)){
			if(dictList==null){
				dictList=CacheService.getAllDict();
			}
			if(icoList==null){
				icoList=icoService.selectIco();
			}
			rule = hotelGoodsService.selectInfoByPrimaryKey(id);
			room = roomTypeService.selectByPrimaryKey(rule.getRoomtypeId());
			rule.setApartmentName(room.getApartmentName());
			rule.setBedType(CacheService.getLabel("bedType",rule.getBedType()));
			String addBed=CacheService.getLabel("addBed",rule.getAddBed());
			rule.setAddBed("1".equals(rule.getAddBed())?"加床需收费"+rule.getBedFee()+"元":addBed);
			rule.setSmoke(CacheService.getLabel("smoke",rule.getSmoke()));
			rule.setBreakfastStr(CacheService.getLabel("goodsBreakfast",String.valueOf(rule.getBreakfast())));
			rule.setIsWindowStr(CacheService.getLabel("isWindow",String.valueOf(rule.getIsWindow())));
			rule.setWifiStr(CacheService.getShotName("freeWifi",String.valueOf(rule.getFreeWifi())));
			rule.setBathroom(CacheService.getShotName("bathroom",String.valueOf(room.getBathroom())));
			rule.setWideband(CacheService.getLabel("wideband",String.valueOf(room.getWideband())));
			rule.setCancelStr(CacheService.getLabel("cancel",String.valueOf(rule.getCancel())));
			String confirmStr=CacheService.getLabel("confirm",String.valueOf(rule.getConfirm()));
			rule.setConfirmStr(PlatConstants.CONFIRM_TIME==rule.getConfirm()?String.valueOf(rule.getConfirmTime())+"小时内确认":confirmStr);
			if(StringUtils.isNotBlank(rule.getFloor())){
				icoMap = new HashMap<String,String>();
				icoMap.put("str", rule.getFloor()+"层");
				icoMap.put("url",getIcoUrl("roomType", "floor"));
				icoMapList.add(icoMap);
			}
			if(StringUtils.isNotBlank(rule.getArea())){
				icoMap = new HashMap<String,String>();
				icoMap.put("str", rule.getArea()+"㎡");
				icoMap.put("url",getIcoUrl("roomType", "area"));
				icoMapList.add(icoMap);
			}
			if(StringUtils.isNotBlank(rule.getBedType())){
				icoMap = new HashMap<String,String>();
				icoMap.put("str",rule.getBedType());
				icoMap.put("url",getIcoUrl("roomType", "bedType"));
				icoMapList.add(icoMap);
			}
			if(StringUtils.isNotBlank(rule.getSmoke())){
				icoMap = new HashMap<String,String>();
				icoMap.put("str", rule.getSmoke());
				icoMap.put("url",getIcoUrl("roomType", "smoke"));
				icoMapList.add(icoMap);
			}
			if(rule.getFreeWifi()==1){
				icoMap = new HashMap<String,String>();
				icoMap.put("str", rule.getWifiStr());
				icoMap.put("url",getIcoUrl("roomType", "wifi"));
				icoMapList.add(icoMap);
			}
			rule.setDescription(room.getDescription());
			rule.setIcoList(icoMapList);
			result.setData(rule);
			result.setMsg("success");
			result.setState(1);
		}else{
			result.setMsg("获取信息失败");
			result.setState(0);
			result.setMessage("Id为空");
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 酒店商品信息预览
	* @param id
	* @param model
	* @return
	* @author huashuwen
	* @date 2017-11-02 10:44
	 */
	@RequestMapping("/toView1")
	@ResponseBody
	public String toView1(String id,String hotelId,String intoDate){
		System.err.println(intoDate);
		
		HotelResult<Map<String,Object>> result = new HotelResult<Map<String,Object>>();
		Map<String,Object> m= new HashMap<String,Object>();
		HotelGoods rule = new HotelGoods();
		
		if(StringUtils.isNotBlank(id)){
			if(dictList==null){
				dictList=CacheService.getAllDict();
			}
			if(icoList==null){
				icoList=icoService.selectIco();
			}
			rule = hotelGoodsService.selectInfoByPrimaryKey(id);
			rule.setBedType(CacheService.getLabel("bedType",rule.getBedType()));
			String addBed=CacheService.getLabel("addBed",rule.getAddBed());
			rule.setAddBed("1".equals(rule.getAddBed())?"加床需收费"+rule.getBedFee()+"元":addBed);
			rule.setSmoke(CacheService.getLabel("smoke",rule.getSmoke()));
			rule.setBreakfastStr(CacheService.getLabel("goodsBreakfast",String.valueOf(rule.getBreakfast())));
			rule.setIsWindowStr(CacheService.getLabel("isWindow",String.valueOf(rule.getIsWindow())));
			rule.setWifiStr(CacheService.getLabel("wideband",String.valueOf(rule.getFreeWifi())));
			rule.setCancelStr(CacheService.getLabel("cancel",String.valueOf(rule.getCancel())));
			String confirmStr=CacheService.getLabel("confirm",String.valueOf(rule.getConfirm()));
			rule.setConfirmStr(PlatConstants.CONFIRM_TIME==rule.getConfirm()?String.valueOf(rule.getConfirmTime())+"小时内确认":confirmStr);
			HotelPolicy hotelPolicy = hotelPolicyService.selectByHotelId(hotelId);
			String prompt="";
			m.put("hotelGoods",rule );
			if(rule.getCancel()==0){
				prompt="当前房型不可取消订单，若未入住将收取您全额房费。";
			}else{
				prompt="前可免费取消，若未入住将收取您全额房费。我们会根据您的付款方式进行预授权或扣除房费，如订单不确认将解除预授权或全额退款至您的付款账户。";
			}
			String ect="";
			String ectEnd="";
			String cancelTime = StringUtils.isEmpty(rule.getCancelTime())?"0":rule.getCancelTime();
			if(!StringUtils.isEmpty(hotelPolicy)){
				if(!StringUtils.isEmpty(hotelPolicy.getEarliestCheckinTime())){
					if(!"不限".equals(hotelPolicy.getEarliestCheckinTime())){
						ect = StringUtils.isEmpty(hotelPolicy.getEarliestCheckinTime())?"12:00":hotelPolicy.getEarliestCheckinTime();;
						ectEnd = ect.substring(ect.indexOf(":")+1,ect.length());
						ect = ect.substring(0, ect.indexOf(":"));
					}else{
						ect = "12";
						ectEnd= "00";
					}
				}else{
					ect = "12";
					ectEnd= "00";
				}
			}else{
				ect = "12";
				ectEnd= "00";
			}
			Integer timeNum = Integer.parseInt(ect) - Integer.parseInt(cancelTime);
			Integer ectEnd1 = Integer.parseInt(ectEnd);
			Date comeDate = DateUtils.changeStrToDate(intoDate);
			long time =comeDate.getTime()+timeNum*60*60*1000L+ectEnd1*60*1000L;
			
			ect = DateUtils.changeDateToStr(new Date(time), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHM);
			m.put("time",ect);
			m.put("prompt", prompt);
			result.setData(m);
			result.setMsg("success");
			result.setState(1);
		}else{
			result.setMsg("获取信息失败");
			result.setState(0);
			result.setMessage("Id为空");
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 酒店商品信息保存操作
	* @param bean
	* @return
	* @author sty
	* @date 2017-11-02 10:44
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public HotelResult<HotelGoods> saveOrUpdate(@RequestBody HotelGoods bean){
		Map<String,Object> map =bean.getMap();
		
		String rtype = (String)map.get("rtype");
		String weeks = (String)map.get("weeks");
		List<Map<String,Long>> times = (List<Map<String,Long>>)map.get("times");
		String priceA = (String)map.get("price");
		if(!StringUtils.isEmpty(priceA)){
			if( !priceA.matches("-?[0-9]+.*[0-9]*")){
				return new HotelResult<HotelGoods>(0,"价格为非法字符");
			}
			if(Double.parseDouble(priceA)<=0.0){
				return new HotelResult<HotelGoods>(0,"价格必须大于0");
			}
		}
//		try{
			if(bean!=null){
				String id = bean.getId();
				String hotelId = bean.getHotelId();
				if(StringUtils.isEmpty(hotelId)){
					return new HotelResult<HotelGoods>(0,"参数错误");
				}
				if(StringUtils.isNotBlank(id)){
					HotelGoods rule = new HotelGoods();
					if(StringUtils.isNotBlank(id)){
						rule = hotelGoodsService.selectByPrimaryKey(id);
					}
					bean.setCreator(rule.getCreator());
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setState(rule.getState());
					bean.setStatus(1);
					hotelGoodsService.update(bean);
					try {
						datePriceService.insertPirceInfo(hotelId);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//createIndexService.createAllHotelIndex();
				}else{
					//新增商品	
					UUID u = UUID.randomUUID();
					RoomType rt = roomTypeService.selectByPrimaryKey(bean.getRoomtypeId());
					String name = rt.getApartmentName() + CacheService.getLabel("goodsBreakfast", String.valueOf(bean.getBreakfast()));
					bean.setGoodsName(name);
					bean.setStatus(1);
					bean.setState(PlatConstants.HOTELGOODS_SALE);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					bean.setHotelId(hotelId);
					hotelGoodsService.insert(bean);
					//新增售卖规则
					BigDecimal basicPrice = new BigDecimal(0);
					BigDecimal brokerage = new BigDecimal(0);
					BigDecimal brokerageProportion = new BigDecimal(0);
					BigDecimal price = new BigDecimal(priceA);
					PriceRule pricerule = new PriceRule();
					pricerule.setCreateTime(new Date());
					pricerule.setUpdateTime(new Date());
					pricerule.setPrice(priceA);
					RoomType roomtype = roomTypeService.selectByPrimaryKey(bean.getRoomtypeId());
					if(roomtype.getCooperateType()==0){
						brokerage = new BigDecimal(roomtype.getBrokerage());
						basicPrice = price.subtract(brokerage);
						pricerule.setBasicPrice(basicPrice.toString());
					}else if(roomtype.getCooperateType()==1){
						brokerageProportion = new BigDecimal(roomtype.getBrokerageProportion());
						basicPrice = price.divide(new BigDecimal(1).add(brokerageProportion),2,BigDecimal.ROUND_HALF_UP);
						pricerule.setBasicPrice(basicPrice.toString());
					}else{
						return new HotelResult<HotelGoods>(0,"该房型未设置合作方式");
					}
					pricerule.setType(2);
					pricerule.setCreator(bean.getCreator());
					pricerule.setStatus(1);
					pricerule.setGoodId(bean.getId());
					pricerule.setRoomtypeId(bean.getRoomtypeId());
					if(!StringUtils.isEmpty(weeks)){
						pricerule.setWeekStart(Integer.parseInt(weeks));
					}
					if("1".equals(rtype)){
						pricerule.setStartTime(DateUtils.getStartTime());
						Calendar c = Calendar.getInstance();
						c.add(Calendar.YEAR,10);
						pricerule.setEndTime(c.getTime());
						pricerule.setId(UUID.randomUUID().toString());
						
						priceRuleService.insert(pricerule);
					}else if("3".equals(rtype)){
						for(Map<String, Long> time :times){
							pricerule.setStartTime(new Date(time.get("start")));
							pricerule.setEndTime(new Date(time.get("end")));
							pricerule.setId(UUID.randomUUID().toString());
							priceRuleService.insert(pricerule);
						}
					}
//					hotel.setMaxPrice(maxPrice);
//					hotel.setMiniPrice(miniPrice);
//					hotelService.update(hotel);
					try {
						datePriceService.insertPirceInfo(hotelId);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//createIndexService.createAllHotelIndex();
				}
			}
		/*}catch(Exception e){
			System.err.println(e.toString());
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<HotelGoods>(2,"error");
		}*/
		return new HotelResult<HotelGoods>(1,"success");
	}
	/**
	 * 酒店商品信息删除
	* @param id
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:44
	 */
	@RequestMapping("/del")
	@ResponseBody
	public HotelResult<HotelGoods> del(String id){
		try{
			hotelGoodsService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<HotelGoods>(2,"error");
		}
		return new HotelResult<HotelGoods>(1,"success");
	}
	@RequestMapping("/getSelectList")
	@ResponseBody
	public String getSelectList(){
		HotelResult<Map<String,Object>> result = new HotelResult<Map<String,Object>>();
		List<Dict> breakfastList = new ArrayList<Dict>();
		List<Dict> cancelList= new ArrayList<Dict>();
		List<Dict> confirmList = new ArrayList<Dict>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String type= "";
		if(dictList==null){
			dictList = CacheService.getAllDict();
		}
		for(Dict dict:dictList){
			type = dict.getType();
			if("goodsBreakfast".equals(type)){
				breakfastList.add(dict);
			}
			if("cancel".equals(type)){
				cancelList.add(dict);
			}
			if("confirm".equals(type)){
				confirmList.add(dict);
			}
		}
		resultMap.put("breakfastList", breakfastList);
		resultMap.put("cancelList", cancelList);
		resultMap.put("confirmList", confirmList);
		result.setData(resultMap);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	@RequestMapping("/getRoomtypeList")
	@ResponseBody
	public String getRoomtypeList(String hotelId){
		HotelResult<List<RoomType>> result = new HotelResult<List<RoomType>>();
		List<RoomType> list = roomTypeService.getRoomtypeList(hotelId);
		result.setData(list);
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	
	
	
	public String getIcoUrl(String type,String name){
		for(Ico ico:icoList){
			if(type.equals(ico.getType())&&name.equals(ico.getName())){
				return ico.getImg();
			}
		}
		return "";
	}
	
	@RequestMapping("/all")
	@ResponseBody
	public String all(){
		List<Hotel> a =hotelService.selectList();
		for(Hotel a1 :a){
			try {
				datePriceService.insertPirceInfo(a1.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "success";
	}
}
