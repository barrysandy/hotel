package com.zzk.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zzk.util.DateUtils;
import com.zzk.util.HotelResult;
import com.zzk.util.Exceptions;
import com.zzk.util.StringUtils;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.Order;
import com.zzk.entity.RoomType;
import com.zzk.entity.StockRule;
import com.zzk.entity.SwitchRule;
import com.zzk.service.HotelGoodsService;
import com.zzk.service.OrderService;
import com.zzk.service.RoomTypeService;
import com.zzk.service.StockRuleService;
import com.zzk.service.SwitchRuleService;
import com.zzk.controller.BaseController;

/**
 * <p>description：库存规则信息</p>
 * @name：StockRuleController
 * @author：sty
 * @date：2017-11-02 10:41
 */
@Controller
@RequestMapping(value = "/stockRule")
public class StockRuleController extends BaseController {

	@Autowired
	private StockRuleService stockRuleService;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private HotelGoodsService hotelGoodsService ;
	@Autowired
	private OrderService orderService;
	@Autowired
	private SwitchRuleService switchRuleService;
	/**
	 * 库存规则信息分页查询
	 * @param page
	 * @param model
	 * @param id
	 * @return
	 * @author sty
	 * @date 2017-11-02 10:41
	 */
	/*@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<StockRule> list = stockRuleService.selectByPage(map);
		int totalNum = stockRuleService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("stockRule/stockRule_list");
	}*/
	
	/**
	 * 库存规则信息编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:41
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			StockRule rule = new StockRule();
			if(StringUtils.isNotBlank(id)){
				rule = stockRuleService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("stockRule/stockRule_edit");
	}
	/**
	 * 库存规则信息预览
	* @param id
	* @param model
	* @return
	* @author sty
	* @date 2017-11-02 10:41
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		StockRule rule = new StockRule();
		if(StringUtils.isNotBlank(id)){
			rule = stockRuleService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("stockRule/stockRule_view");
	}
	/**
	 * 库存规则信息保存操作
	* @param bean
	* @return
	* @author sty
	* @date 2017-11-02 10:41
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public HotelResult<StockRule> saveOrUpdate(StockRule bean){
		try{
			if("".equals(bean.getStock())){
				return new HotelResult<StockRule>(0,"库存不能为空");
			}
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					StockRule rule = new StockRule();
					if(StringUtils.isNotBlank(id)){
						rule = stockRuleService.selectByPrimaryKey(id);
					}
					bean.setCreator(rule.getCreator());
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					stockRuleService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					Date startTime = bean.getStartTime();
					if(startTime!=null){
						bean.setEndTime(new Date(startTime.getTime()+86399000));
					}
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					bean.setUpdateTime(new Date());
					stockRuleService.insert(bean);
				}
			}
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<StockRule>(2,"error");
		}
		return new HotelResult<StockRule>(1,"success");
	}
	/**
	 * 按照选择时间段房型，算出周一至周日最小库存量
	* @param 
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-02 10:41 
	 *//*
	@RequestMapping("/getWeekMinStock")
	@ResponseBody
	public String getWeekMinStock(@RequestBody Map<String,Object> map){
		List<Map<String,String>> times = (List<Map<String,String>>)map.get("times");
		List<String>  roomtypeIdList = (List<String>)map.get("roomtypeIdList");
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> resultList= new ArrayList<Map<String,Object>>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD);
		Set<String> set = new HashSet<String>();
		Date date3 ;
		int goodNum=0;
		for(Map<String,String> time:times){
			String beginDate = time.get("beginDate");
			String endDate = time.get("endDate");
			try {
				List<HashMap<String,Object>> orderList = orderService.selectByRoomtypeList(roomtypeIdList,sdf.parse(beginDate),sdf.parse(DateUtils.getAfterDay(endDate, 1, DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD)));
				calendar.setTime(sdf.parse(beginDate));
				int dayOfWeek = calendar.get(7)-1;
				int days=DateUtil.daysBetween(DateUtil.parseDate(beginDate, DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD),DateUtil.parseDate(endDate, DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD));
				List<String> dateList = new ArrayList<String>();
				int dayOfWeek1=0;
				String date;
				String stock= "";
				for(String roomtypeId:roomtypeIdList){
					Map<String,Integer> saleNumMap= new HashMap<String,Integer> ();
					List<StockRule> stockRuleList = stockRuleService.selectByRoomtype(roomtypeId, beginDate);
					for(int i=0;i<=days;i++){
						date = DateUtils.getAfterDay(beginDate, i, DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD);
						dateList.add(date);
					}
					Map<String,String> stockMap = new HashMap<String,String>();
					if(stockRuleList.size() == 0){
						for(String d :dateList){
							stockMap.put(d,"");
						}
					}
					for(StockRule stockRule:stockRuleList){
						Iterator<String> it = dateList.iterator();
						if(roomtypeId.equals(stockRule.getRoomtypeId())){
							stock = stockRule.getStock();
							while (it.hasNext()) { 
								 String date1  = it.next();  
								 	if(DateUtil.compareDate(stockRule.getStartTime(), sdf.parse(date1))<=0&&DateUtil.compareDate(stockRule.getEndTime(), sdf.parse(date1))>=0){
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
					if(dateList.size()!=0){
						 for(String d:dateList){
							 stockMap.put(d, "");
						 }
					}
					dateList.clear();
					for(int i=0;i<=days;i++){
						date = DateUtils.getAfterDay(beginDate, i, DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD);
						dateList.add(date);
					}
					for(String date1:dateList){
						date3 = sdf.parse(date1);
						goodNum = 0;
						for(Map m:orderList){
							if(roomtypeId.equals((String)m.get("roomtypeId"))){
								if(DateUtil.compareDate(date3,(Date)m.get("LEAVE_TIME"))<0&&DateUtil.compareDate(date3,(Date)m.get("COME_TIME"))>=0){
									goodNum+=Integer.parseInt((String)m.get("GOODS_NUM"));
								}
							}
						}
						saleNumMap.put(date1, goodNum);
					}
					dateList.clear();
					Map<String,Object> map2= new HashMap<String,Object>();
					for(int w=1;w<=7;w++){
						List<Integer> list = new ArrayList<Integer>();
						for(int i=0;i<=days;i++){
							date = DateUtils.getAfterDay(beginDate, i, DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD);
							
							if(w!=7){
								if((dayOfWeek+i)%7==w){
									String stock1 = stockMap.get(date);
									if(!StringUtil.isEmpty(stock1)){
										list.add(Integer.parseInt(stock1)-saleNumMap.get(date));
									}
									
								}
							}else{
								if((dayOfWeek+i)%7==0){
									String stock1 = stockMap.get(date);
									if(!StringUtil.isEmpty(stock1)){
										list.add(Integer.parseInt(stock1)-saleNumMap.get(date));
									}
								}
							}
						}
						list.removeAll(Collections.singleton(null));
						list.removeAll(Collections.singleton(""));
						if(list.size()>0){
							map2.put(String.valueOf(w),Collections.min(list));
						}else{
							map2.put(String.valueOf(w),"");
						}
					}
					
					map2.put("id",roomtypeId);
					resultList.add(map2);
				}
				dateList.clear();
				for(int i=0;i<=days;i++){
					date = DateUtils.getAfterDay(beginDate, i, DateUtil.NOSYMBOL_DATETIME_FORMAT_YMD);
					dateList.add(date);
				}
				for(String date1:dateList){
					calendar.setTime(sdf.parse(date1));
					dayOfWeek = calendar.get(7)-1;
					if(dayOfWeek==0){
						dayOfWeek=7;
					}
					switch (dayOfWeek) {
					case 1:
						set.add("周一");
						break;
					case 2:
						set.add("周二");
						break;
					case 3:
						set.add("周三");
						break;
					case 4:
						set.add("周四");
						break;
					case 5:
						set.add("周五");
						break;
					case 6:
						set.add("周六");
						break;
					case 7:
						set.add("周日");
						break;	
					default:
						break;
					}
				}
				resultMap.put("resultList", resultList);
				resultMap.put("selectableWeek", set);
				result.setData(resultMap);	
				result.setState(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return JSON.toJSONString(result);
	}
	*/
	/**
	 * 库存规则信息删除
	* @param id
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:41
	 */
	@RequestMapping("/del")
	@ResponseBody
	public HotelResult<StockRule> del(String id){
		try{
			stockRuleService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<StockRule>(2,"error");
		}
		return new HotelResult<StockRule>(1,"success");
	}
	@RequestMapping("/getStockInfo")
	@ResponseBody
	public String getStockInfo(String time,String hotelId) {
		HotelResult<List<Map<String,Object>>> result= new HotelResult<List<Map<String,Object>>>();
		List<Map<String,Object>> stockMapList = new ArrayList<Map<String,Object>>();
		Order bean = new Order();
		String endTime="";
		String startTime ="";
		try {
			Date parseDate = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD).parse(time);
			 endTime = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD).format(DateUtils.getNextWeekMonday(parseDate));
			 startTime = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD).format(DateUtils.getThisWeekMonday(parseDate));
			
			SimpleDateFormat sdf=new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD);
			
			
			bean.setComeTime(sdf.parse(startTime));
			bean.setLeaveTime(sdf.parse(endTime));
			bean.setShopId(hotelId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setState(0);
			result.setMsg("获取数据失败");
			return JSON.toJSONString(result);
		}
		List<RoomType> roomTypeList = roomTypeService.selectByHotelId(hotelId);
		
		List<StockRule> stockRuleList = stockRuleService.selectByHotelId(hotelId,startTime);
			List<HashMap<String,Object>> orderList = orderService.selectByHotelId(bean);
			
			
			
			List<SwitchRule> switchRuleList = switchRuleService.selectByHotelId(hotelId,startTime);
			
			try {
				stockMapList =stockRuleService.getStockInfo(orderList,stockRuleList,roomTypeList,switchRuleList,startTime,endTime);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
		
		result.setData(stockMapList);
		result.setState(1);
		result.setMsg("查询成功");
		return JSON.toJSONString(result);
	}
	@RequestMapping("/getInfoTree")
	@ResponseBody
	public String getInfoTree(String id){
		HotelResult result= new HotelResult();
		List resList = new ArrayList();
		List<HotelGoods> hotelGoodsList = hotelGoodsService.selectHotelGoods(id);
		List<RoomType> roomTypeList = roomTypeService.selectByHotelId(id);
		Map<String,String> initStockMap = new HashMap<String,String> ();
		for(int i=0;i<roomTypeList.size();i++){
			Map room= new HashMap();
			room.put("id", i+1);
			String roomtypeId = roomTypeList.get(i).getId();
			room.put("roomtypeId", roomtypeId);
			room.put("initStockMap", initStockMap);
			room.put("label", roomTypeList.get(i).getApartmentName());
			resList.add(room);
		}
		result.setData(resList);
		result.setMsg("获取信息成功");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	@RequestMapping("/batchEditStock")
	@ResponseBody
	public String batchEditStock(@RequestBody Map<String,Object> map) {
		List<Map<String,Object>> list = (List<Map<String,Object>>)map.get("list");
		String rType = (String)map.get("rtype");
		String weeks = (String)map.get("weeks");
		String creator = (String)map.get("creator");
		String isOpen = (String)map.get("isOpen");
		String isChange = (String)map.get("isChange");
		String addStock = (String)map.get("addStock");
		String redStock = (String)map.get("redStock");	
		List<StockRule> addStockRuleList = new ArrayList<StockRule>();
		List<String> dates= new ArrayList<String>();
		List<String> dateList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		if(StringUtils.isEmpty(weeks)){
			return JSON.toJSONString(new HotelResult<StockRule>(0,"请选择星期"));
		}
		int small=0;
		String date;
		int count=0;
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
		List<List<String>> dateList1 = new ArrayList<List<String>>();
		//把选择的日期变成datelist去重
		try {
			if("1".equals(rType)){
				String startDate =  sdf.format(new Date());
				for(int i = 0;i<90;i++){
					date = DateUtils.getAfterDay(startDate, i, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
					dates.add(date);	
				}
				dateList1.add(dates);
				if(!"0".equals(isOpen)){
					for (Map<String, Object> m : list){
						SwitchRule switchRule = new SwitchRule();
						switchRule.setId(UUID.randomUUID().toString());
						switchRule.setCreateTime(new Date());
						switchRule.setRoomtypeId((String)m.get("roomtypeId"));
						switchRule.setUpdateTime(new Date());
						switchRule.setCreator(creator);
						switchRule.setStatus(1);
						switchRule.setId(UUID.randomUUID().toString());
						switchRule.setWeekStart(Integer.parseInt(weeks));
						switchRule.setType(2);
						switchRule.setIsOpen(Integer.parseInt(isOpen));
						switchRule.setStartTime(DateUtils.getStartTime());
						Calendar c = Calendar.getInstance();
						c.add(Calendar.YEAR,10);
						switchRule.setEndTime(c.getTime());
						switchRuleService.insert(switchRule);
						count ++;
					}
				}
			}else if("3".equals(rType)){
				
				List<Map<String,Long>> times= (List<Map<String,Long>>)map.get("times");
				if(times.size()<=0){
					return JSON.toJSONString(new HotelResult<StockRule>(0,"请选择时间段"));
				}
				for(Map<String,Long> timeMap :times){
					long start = timeMap.get("start");
					long end =timeMap.get("end");
					int days=DateUtils.daysBetween(new Date(start),new Date(end));
					String startDate =  sdf.format(new Date(start));
					for(int i = 0;i<=days;i++){
						date = DateUtils.getAfterDay(startDate, i, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
						dates.add(date);	
					}
					dateList1.add(dates);
					if(!"0".equals(isOpen)){
						for (Map<String, Object> m : list){
							SwitchRule switchRule = new SwitchRule();
							switchRule.setId(UUID.randomUUID().toString());
							switchRule.setCreateTime(new Date());
							switchRule.setRoomtypeId((String)m.get("roomtypeId"));
							switchRule.setUpdateTime(new Date());
							switchRule.setCreator(creator);
							switchRule.setStatus(1);
							switchRule.setId(UUID.randomUUID().toString());
							switchRule.setWeekStart(Integer.parseInt(weeks));
							switchRule.setType(2);
							switchRule.setIsOpen(Integer.parseInt(isOpen));
							switchRule.setStartTime(new Date(start));
							switchRule.setEndTime(new Date(end));
							switchRuleService.insert(switchRule);
							count ++;
						}
					}
				}
			}else{
				return JSON.toJSONString(new HotelResult<StockRule>(0,"参数错误"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new HotelResult<StockRule>(0,"参数错误"));
		}
		
		if(!"0".equals(isChange)){
			//去重
			List<String> listWithoutDup = new ArrayList<String>(new LinkedHashSet<String>(dates));
			List<String>  roomTypeIdList = new ArrayList<String>();
			for(Map a :list){
				roomTypeIdList.add((String)a.get("roomtypeId"));
			}
			List<StockRule> stockRuleList = stockRuleService.selectByRoomtypeList(roomTypeIdList, listWithoutDup.get(0));
			int dayOfWeek1=0;
			String stock= "";
			if (list.size() < 1) {
				return JSON.toJSONString(new HotelResult<StockRule>(0, "请输入修改规则"));
			}
			for(List<String> ddaa: dateList1){
				for (Map<String, Object> m : list) {
					dateList.addAll(ddaa);
					Map<String,String> stockMap = new TreeMap<String, String>(new Comparator<String>(){  
						public int compare(String o1, String o2) {  
			            	//指定排序器按照升序排列  
			                return o1.compareTo(o2);  
			            }     
			        }); 
					if(stockRuleList.size() == 0){
						for(String d :dateList){
							stockMap.put(d,"");
						}
					}
					for(StockRule stockRule:stockRuleList){
						Iterator<String> it = dateList.iterator();
						if(m.get("roomtypeId").equals(stockRule.getRoomtypeId())){
							stock = stockRule.getStock();
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
													if("1".equals(isChange)){
														stockMap.put(date1, String.valueOf(Integer.parseInt(stock)+Integer.parseInt(addStock)));
													}else if("2".equals(isChange)){
														stockMap.put(date1, String.valueOf(Integer.parseInt(stock)-Integer.parseInt(redStock)));
													}
													it.remove();
													continue;
												}
											 }else{
												 if("1".equals(isChange)){
														stockMap.put(date1, String.valueOf(Integer.parseInt(stock)+Integer.parseInt(addStock)));
													}else if("2".equals(isChange)){
														stockMap.put(date1, String.valueOf(Integer.parseInt(stock)-Integer.parseInt(redStock)));
													}
												 it.remove();
										     }
										 }
									} catch (NumberFormatException e) {
										e.printStackTrace();
										return JSON.toJSONString(new HotelResult<StockRule>(0, "数字类型错误"));
									} catch (ParseException e) {
										e.printStackTrace();
										return JSON.toJSONString(new HotelResult<StockRule>(0, "时间转化错误"));
									}
								} 
						}
					}
					if(dateList.size()!=0){
						 for(String d:dateList){
							 stockMap.put(d, "");
						 }
					}
					dateList.clear();
					String temp = "";
					String startDate1 = "";
					String endDate1 = "";
					boolean flag =false;
					Set<Entry<String,String>> set = stockMap.entrySet();
					for (Entry<String, String> me : set){
						if(!StringUtils.isEmpty(me.getValue())){
							if(small>Integer.parseInt(me.getValue())){
								small = Integer.parseInt(me.getValue());
							}
						}
					}
					int length = set.size();
					int index = 0;
					
					try {
						for (Entry<String, String> me : set) {
							//只有一条数据的处理
							if(length == 1){
								addStockRuleList.add(newObj((String) m.get("roomtypeId"), creator,
										Integer.parseInt(weeks), me.getValue(),
										sdf.parse(me.getKey()), sdf.parse(me.getKey())));
								break;
							}
							// 是否最后一次，最后一次开始时间取上一次不一样库存的时间
							if (length - index == 1) {
								if (temp.equals(me.getValue())) {
									endDate1 = me.getKey();
									addStockRuleList.add(newObj((String) m.get("roomtypeId"), creator,
											Integer.parseInt(weeks), me.getValue(),
											sdf.parse(startDate1), sdf.parse(endDate1)));
								} else {
									// 不相等先插入上次不等的数据
									addStockRuleList.add(newObj((String) m.get("roomtypeId"), creator,
											Integer.parseInt(weeks), temp,
											sdf.parse(startDate1), sdf.parse(endDate1)));
									// 再插入最后一次的数据.最后一次库存不同情况，开始日期结束日期一样
									endDate1 = me.getKey();
									addStockRuleList.add(newObj((String) m.get("roomtypeId"), creator,
											Integer.parseInt(weeks), me.getValue(),
											sdf.parse(endDate1), sdf.parse(endDate1)));
									
								}
							} else {
								if (temp.equals(me.getValue())) {
									// 记录时间（下次不相等时取此时间）
									endDate1 = me.getKey();
									temp = me.getValue();
									flag = true;
								} else {
									if (flag) {
										addStockRuleList.add(newObj((String) m.get("roomtypeId"), creator,
													Integer.parseInt(weeks), temp,
													sdf.parse(startDate1),
													sdf.parse(endDate1)));
									}
									startDate1 = me.getKey();
									endDate1 = me.getKey();
									temp = me.getValue();
									flag = true;
								}
								index++;
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
						return JSON.toJSONString(new HotelResult<StockRule>(0, "时间转化错误"));
					}
				}
			}
		}
		if(small<0){
			String msg = "不能大于"+(Integer.parseInt(redStock)+small);
			return JSON.toJSONString(new HotelResult<StockRule>(0, msg));
		}else{
			for(StockRule bean :addStockRuleList){
				if(!StringUtils.isEmpty(bean.getStock())){
					stockRuleService.insert(bean);
					count++;
				}
			}
		}
		if(count>0){
			return JSON.toJSONString(new HotelResult<StockRule>(1, "修改成功"));
		}else{
			return JSON.toJSONString(new HotelResult<StockRule>(0, "修改失败"));
		}
	}
	
	@RequestMapping("/EditStock")
	@ResponseBody
	public HotelResult<StockRule> EditStock(@RequestBody Map<String,Object> map){
		List<Map<String,Object>> list= (List<Map<String,Object>>)map.get("stockRuleList");
		if(list.size()<1){
			return new HotelResult<StockRule>(0,"请输入修改规则");
		}
		for (Map<String, Object> m : list) {
			UUID uid = UUID.randomUUID();
			StockRule stockRule = new StockRule();
			stockRule.setId(uid.toString());
			stockRule.setType(3);
			stockRule.setStatus(1);
			stockRule.setIsInit(0);
			stockRule.setCreateTime(new Date());
			stockRule.setCreator((String) m.get("creator"));
			stockRule.setUpdateTime(new Date());
			stockRule.setStartTime(new Date((Long)m.get("startDate")));
			stockRule.setEndTime(new Date((Long)m.get("endDate")));
			stockRule.setRoomtypeId((String)m.get("roomtypeId"));
			if(StringUtils.isEmpty((String) m.get("stock"))){
				return new HotelResult<StockRule>(0, "房量不能为空");
			}
			stockRule.setStock((String)m.get("stock"));
			stockRuleService.insert(stockRule);
		}
		return new HotelResult<StockRule>(1, "修改成功");
	}
	
	private  StockRule newObj(String id,String creator, int weeks,String stock,Date startTime,Date endTime){
		StockRule stockRule = new StockRule();
		stockRule.setCreateTime(new Date());
		stockRule.setRoomtypeId(id);
		// 更新时间相当于最新操作时间，计算价格时必取
		stockRule.setUpdateTime(new Date());
		stockRule.setCreator(creator);
		stockRule.setStatus(1);
		stockRule.setIsInit(0);
		stockRule.setId(UUID.randomUUID().toString());
		stockRule.setWeekStart(weeks);
		stockRule.setType(2);
		stockRule.setStock(stock);
		stockRule.setStartTime(startTime);
		stockRule.setEndTime(endTime);
		return stockRule;
	}
	
}
