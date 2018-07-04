package com.zzk.controller;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zzk.util.HotelResult;
import com.zzk.common.FormModel;
import com.zzk.util.DateUtils;
import com.zzk.util.Exceptions;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.PriceRule;
import com.zzk.entity.RoomType;
import com.zzk.service.HotelService;
import com.zzk.service.CreateIndexService;
import com.zzk.service.DatePriceService;
import com.zzk.service.HotelGoodsService;
import com.zzk.service.PriceRuleService;
import com.zzk.service.RoomTypeService;
import com.zzk.controller.BaseController;

/**
 * <p>description：价格规则信息</p>
 * @name：PriceRuleController
 * @author：sty
 * @date：2017-11-02 10:40
 */
@Controller
@RequestMapping(value = "/priceRule")
public class PriceRuleController extends BaseController {

	@Autowired
	private PriceRuleService priceRuleService;
	@Autowired
	private HotelGoodsService hotelGoodsService ;
	@Autowired 
	private RoomTypeService roomTypeService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private CreateIndexService createIndexService;
	@Autowired
	private DatePriceService datePriceService;
	/**
	 * 价格规则信息分页查询
	 * @param page
	 * @param model
	 * @param id
	 * @return
	 * @author sty
	 * @date 2017-11-02 10:40
	 */
	@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<PriceRule> list = priceRuleService.selectByPage(map);
		int totalNum = priceRuleService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("priceRule/priceRule_list");
	}
	
	/**
	 * 价格规则信息编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:40
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			PriceRule rule = new PriceRule();
			if(StringUtils.isNotBlank(id)){
				rule = priceRuleService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("priceRule/priceRule_edit");
	}
	/**
	 * 价格规则信息预览
	* @param id
	* @param model
	* @return
	* @author sty
	* @date 2017-11-02 10:40
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		PriceRule rule = new PriceRule();
		if(StringUtils.isNotBlank(id)){
			rule = priceRuleService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("priceRule/priceRule_view");
	}
	/**
	 * 价格规则信息保存操作
	* @param bean
	* @return
	* @author sty
	* @date 2017-11-02 10:40
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public HotelResult<PriceRule> saveOrUpdate(PriceRule bean){
		try{
			if("".equals(bean.getPrice())){
				return new HotelResult<PriceRule>(0,"价格不能为空");
			}
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					PriceRule rule = new PriceRule();
					rule = priceRuleService.selectByPrimaryKey(id);
					bean.setCreator(rule.getCreator());
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					HotelGoods hotelGoods = hotelGoodsService.selectByPrimaryKey(bean.getGoodId());
					String hotelId = hotelGoods.getHotelId();
					priceRuleService.update(bean);
					datePriceService.insertPirceInfo(hotelId);
					createIndexService.createIndexByHotelId(hotelId);
					//createIndexService.createAllHotelIndex();
				}else{
					UUID u = UUID.randomUUID();
					HotelGoods hotelGoods = hotelGoodsService.selectByPrimaryKey(bean.getGoodId());
					bean.setRoomtypeId(hotelGoods.getRoomtypeId());
					RoomType roomtype = roomTypeService.selectByPrimaryKey(bean.getRoomtypeId());
					BigDecimal price = new BigDecimal(0);
					BigDecimal basicPrice = new BigDecimal(0);
					BigDecimal brokerage = new BigDecimal(0);
					BigDecimal brokerageProportion = new BigDecimal(0);
					price = new BigDecimal(bean.getPrice());
					if(roomtype.getCooperateType()==0){
						brokerage = new BigDecimal(roomtype.getBrokerage());
						basicPrice = price.subtract(brokerage);
						bean.setBasicPrice(basicPrice.toString());
					}else if(roomtype.getCooperateType()==1){
						brokerageProportion = new BigDecimal(roomtype.getBrokerageProportion());
						basicPrice = price.divide(new BigDecimal(1).add(brokerageProportion),2,BigDecimal.ROUND_HALF_UP);
						bean.setBasicPrice(basicPrice.toString());
					}else{
						return new HotelResult<PriceRule>(0,"该房型未设置合作方式");
					}
					Date startTime = bean.getStartTime();
					if(startTime!=null){
						bean.setEndTime(new Date(startTime.getTime()+86399000));
					}
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					bean.setUpdateTime(new Date());
					priceRuleService.insert(bean);
					datePriceService.insertPirceInfo(hotelGoods.getHotelId());
					//createIndexService.createIndexByHotelId(hotelGoods.getHotelId());
					//createIndexService.createAllHotelIndex();
				}
//				String roomtypeId = bean.getRoomtypeId();
//				RoomType roomType =roomTypeService.selectByPrimaryKey(roomtypeId);
//				String shopId =roomType.getHotelId();
//				Hotel hotel =hotelService.selectByPrimaryKey(shopId);
//				Double miniPrice = hotel.getMiniPrice();
//				Double maxPrice  = hotel.getMaxPrice();
//				double price =0.0;
//				List<PriceRule> list = priceRuleService.selectByHotelId(shopId, DateUtils.getYMDHMS());
//				for(int i=0;i<list.size();i++){
//					PriceRule rule = list.get(i);
//					price =Double.parseDouble(rule.getPrice());
//					if(price>maxPrice){
//						maxPrice = price;
//					}
//					if(price<miniPrice){
//						miniPrice = price;
//					}
//					
//				}
//				if(miniPrice.equals(0.0) || maxPrice.equals(0.0)){
//					maxPrice = price;
//					miniPrice = price;
//				}
//					hotel.setMaxPrice(maxPrice);
//					hotel.setMiniPrice(miniPrice);
//				hotelService.update(hotel);
			  }
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<PriceRule>(2,"修改失败");
		}
		return new HotelResult<PriceRule>(1,"修改成功");
	}
	/**
	 * 价格规则信息删除
	* @param id
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:40
	 */
	@RequestMapping("/del")
	@ResponseBody
	public HotelResult<PriceRule> del(String id){
		try{
			priceRuleService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<PriceRule>(2,"error");
		}
		return new HotelResult<PriceRule>(1,"success");
	}
	@RequestMapping("/getPriceInfo")
	@ResponseBody
	public String getStockInfo(String time,String hotelId) {
		HotelResult<List<Map<String,Object>>> result= new HotelResult<List<Map<String,Object>>>();
		//System.err.println(DateUtils.getNextWeekMonday());
		List<Map<String,Object>> priceMapList = new ArrayList<Map<String,Object>>();
		try {
			Date parseDate = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD).parse(time);
			String endTime = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD).format(DateUtils.getNextWeekMonday(parseDate));
			String startTime = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD).format(DateUtils.getThisWeekMonday(parseDate));
			int days=DateUtils.daysBetween(DateUtils.parseDate(startTime, DateUtils.DEFAULT_DATE_FORMAT_YMD),DateUtils.parseDate(endTime, DateUtils.DEFAULT_DATE_FORMAT_YMD));
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT_YMD);
			List<HotelGoods> hotelGoodsList = hotelGoodsService.selectHotelGoods(hotelId);
			List<String> dateList = new ArrayList<String>();
			List<PriceRule> priceRuleList = priceRuleService.selectByHotelId(hotelId,startTime);
			int dayOfWeek1=0;
			String date;
			String basePrice="";
			String price="";
			
			for(HotelGoods hotelGoods : hotelGoodsList){
				for(int i=0;i<days;i++){
					date = DateUtils.getAfterDay(startTime, i, DateUtils.DEFAULT_DATE_FORMAT_YMD);
					dateList.add(date);
				}
				Map<String,Object> priceMap = new HashMap<String,Object>();
				if(priceRuleList.size() == 0){
					for(String d :dateList){
						Map<String,String> p = new HashMap<String,String> ();
						p.put("price", "");
						p.put("basePrice", "");
						priceMap.put(d,p);
					}
				}
				for(PriceRule pirceRule:priceRuleList){
					Iterator<String> it = dateList.iterator();
					if(hotelGoods.getId().equals(pirceRule.getGoodId())){
						price = pirceRule.getPrice();
						//basePrice = price;
						basePrice = pirceRule.getBasicPrice();
						while (it.hasNext()) { 
							 Map<String,String> p = new HashMap<String,String> ();
							 p.put("price", price);
							 p.put("basePrice", basePrice);
							 String date1  = it.next();  
							 	if(DateUtils.compareDate(pirceRule.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(pirceRule.getEndTime(), sdf.parse(date1))>=0){
							    	 if(pirceRule.getType()==2){
							    		 calendar.setTime(sdf.parse(date1));
							    		 dayOfWeek1 = calendar.get(7)-1;
										if(dayOfWeek1==0){
											dayOfWeek1=7;
										}
										if(String.valueOf(pirceRule.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
											
											priceMap.put(date1, p);
											it.remove();
											continue;
										}
							    	 }else{
								    	 priceMap.put(date1, p);
											it.remove();
								     }
							     }
							} 
					}
				}
				if(dateList.size()!=0){
					 for(String d:dateList){
						 Map<String,String> p = new HashMap<String,String> ();
						 p.put("price", "");
						 p.put("basePrice", "");
						 priceMap.put(d, p);
					 }
				}
				dateList.clear();
				priceMap.put("name", hotelGoods.getGoodsName());
				priceMap.put("id", hotelGoods.getId());
				priceMapList.add(priceMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setState(0);
			result.setMsg("获取数据失败");
			return JSON.toJSONString(result);
		}
		result.setData(priceMapList);
		result.setState(1);
		result.setMsg("查询成功");
		return JSON.toJSONString(result);
	}
	@RequestMapping("/getInfoTree")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String getInfoTree(String id){
		HotelResult result= new HotelResult();
		List resList = new ArrayList();
		List<HotelGoods> hotelGoodsList = hotelGoodsService.selectHotelGoods(id);
		List<RoomType> roomTypeList = roomTypeService.selectByHotelId(id);
		for(int i=0;i<roomTypeList.size();i++){
			Map room= new HashMap();
			room.put("id", i+1);
			room.put("roomtypeId", roomTypeList.get(i).getId());
			room.put("brokerage", roomTypeList.get(i).getBrokerage());
			room.put("initStock", roomTypeList.get(i).getInitStock());
			room.put("brokerageProportion", roomTypeList.get(i).getBrokerageProportion());
			room.put("cooperateType", roomTypeList.get(i).getCooperateType());
			room.put("label", roomTypeList.get(i).getApartmentName());
			List goodsList = new ArrayList();
			for(int j=0;j<hotelGoodsList.size();j++){
				if(roomTypeList.get(i).getId().equals(hotelGoodsList.get(j).getRoomtypeId())){
					Map a = new HashMap();
					a.put("id", j+1);
					a.put("goodsId", hotelGoodsList.get(j).getId());
					a.put("label", hotelGoodsList.get(j).getGoodsName());
					a.put("cooperateType", roomTypeList.get(i).getCooperateType());
					a.put("brokerage", roomTypeList.get(i).getBrokerage());
					a.put("brokerageProportion", roomTypeList.get(i).getBrokerageProportion());
					a.put("roomtypeId", roomTypeList.get(i).getId());
					a.put("week", new ArrayList());
					goodsList.add(a);
				}
			}
			room.put("children", goodsList);
			resList.add(room);
		}
		result.setData(resList);
		result.setMsg("获取信息成功");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	@RequestMapping("/batchEditPrice")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	public HotelResult<PriceRule> batchEditPrice(@RequestBody Map<String,Object> map){
		List<Map<String,Object>> list= (List<Map<String,Object>>)map.get("list");
		String rType = (String)map.get("rtype");
		String weeks = (String)map.get("weeks");
		if(StringUtils.isEmpty(weeks)){
			return new HotelResult<PriceRule>(0,"请选择星期");
		}
		if(list.size()<1){
			return new HotelResult<PriceRule>(0,"请输入");
		}
		String goodsId=(String)list.get(0).get("goodsId");
		String hotelId = hotelGoodsService.selectByPrimaryKey(goodsId).getHotelId();
		List<RoomType> roomTypes = roomTypeService.selectByHotelId(hotelId);
		String creator = (String)map.get("creator");
		for(Map<String,Object> m:list){
			String pr = (String)m.get("price");
			if(!StringUtils.isEmpty(pr)){
				if( !pr.matches("-?[0-9]+.*[0-9]*")){
					return new HotelResult<PriceRule>(0,"价格为非法字符");
				}
				if(Double.parseDouble(pr)<=0.0){
					return new HotelResult<PriceRule>(0,"价格必须大于0");
				}
			}
		}
		for(Map<String,Object> m:list){
			String pr = (String)m.get("price");
			if(!StringUtils.isEmpty(pr)){
				for(RoomType roomType:roomTypes){
					if(roomType.getId().equals((String)m.get("roomtypeId"))){
						PriceRule priceRule = new PriceRule();
						priceRule.setCreateTime(new Date());
						priceRule.setRoomtypeId(roomType.getId());
						//更新时间相当于最新操作时间，计算价格时必取
						priceRule.setUpdateTime(new Date());
						//选择所有日期或选择按星期时，生效时间默认近3个月
						priceRule.setCreator(creator);
						priceRule.setStatus(1);
						BigDecimal price = new BigDecimal(pr);
						BigDecimal basicPrice = new BigDecimal(0);
						BigDecimal brokerage = new BigDecimal(0);
						BigDecimal brokerageProportion = new BigDecimal(0);
						priceRule.setPrice(pr);
						priceRule.setGoodId((String)m.get("goodsId"));
						priceRule.setType(2);
						priceRule.setWeekStart(Integer.parseInt(weeks));
						if(roomType.getCooperateType()==0){
							if(StringUtils.isEmpty(roomType.getBrokerage())){
								return new HotelResult<PriceRule>(0,roomType.getApartmentName()+"未设置佣金");
							}
							brokerage = new BigDecimal(roomType.getBrokerage());
							basicPrice = price.subtract(brokerage);
							priceRule.setBasicPrice(basicPrice.toString());
						}else if(roomType.getCooperateType()==1){
							if(StringUtils.isEmpty(roomType.getBrokerageProportion())){
								return new HotelResult<PriceRule>(0,roomType.getApartmentName()+"未设置佣金率");
							}
							brokerageProportion = new BigDecimal(roomType.getBrokerageProportion());
							basicPrice = price.divide(new BigDecimal(1).add(brokerageProportion),2,BigDecimal.ROUND_HALF_UP);
							priceRule.setBasicPrice(basicPrice.toString());
						}else{
							return new HotelResult<PriceRule>(0,roomType.getApartmentName()+"未设置合作方式");
						}
						if("1".equals(rType)){
							UUID uid = UUID.randomUUID();
							priceRule.setId(uid.toString());
							priceRule.setStartTime(DateUtils.getStartTime());
							Calendar c = Calendar.getInstance();
							c.add(Calendar.YEAR,10);
							priceRule.setEndTime(c.getTime());
							priceRuleService.insert(priceRule);
						}else if("3".equals(rType)){
							List<Map<String,Long>> times = (List<Map<String,Long>>)map.get("times");
							for(Map<String,Long> timeMap :  times){
								UUID uid = UUID.randomUUID();
								priceRule.setId(uid.toString());
								long start = (Long)timeMap.get("start");
								long end = (Long)timeMap.get("end");
								priceRule.setStartTime(new Date(start));
								priceRule.setEndTime(new Date(end));
								priceRuleService.insert(priceRule);
							}
						}else{
							return new HotelResult<PriceRule>(0,"参数错误");
						}
					}
				}
			}
		}
		try {
			datePriceService.insertPirceInfo(hotelId);
			//createIndexService.createAllHotelIndex();
			//createIndexService.createIndexByHotelId(hotelId);
		} catch (UnknownHostException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HotelResult<PriceRule>(1,"修改成功");
	}
	@RequestMapping("/EditPrice")
	@ResponseBody
	public HotelResult<PriceRule> EditPrice(@RequestBody Map<String,Object> map){
		List<Map<String,Object>> list= (List<Map<String,Object>>)map.get("priceRuleList");
		if(list.size()<1){
			return new HotelResult<PriceRule>(0,"请输入修改规则");
		}
		HotelGoods hotelGoods = hotelGoodsService.selectByPrimaryKey((String)list.get(0).get("goodsId"));
		String hotelId = hotelGoods.getHotelId();
		for (Map<String, Object> m : list) {
			String goodsId = (String)m.get("goodsId");
			RoomType roomType = roomTypeService.selectByGoodsId(goodsId);
			UUID uid = UUID.randomUUID();
			PriceRule priceRule = new PriceRule();
			priceRule.setId(uid.toString());
			priceRule.setType(2);
			priceRule.setWeekStart(1234567);
			priceRule.setStatus(1);
			priceRule.setCreateTime(new Date());
			priceRule.setUpdateTime(new Date());
			long startDate =(Long)m.get("startDate");
			long endDate = (Long)m.get("endDate");
			priceRule.setStartTime(new Date(startDate));
			priceRule.setEndTime(new Date(endDate));
			priceRule.setRoomtypeId(roomType.getId());
			if(StringUtils.isEmpty((String)m.get("price"))){
				return new HotelResult<PriceRule>(2, "价格不能为空");
			}
			priceRule.setPrice((String) m.get("price"));
			priceRule.setGoodId((String) m.get("goodsId"));
			BigDecimal price = new BigDecimal((String) m.get("price"));
			BigDecimal basicPrice = new BigDecimal(0);
			BigDecimal brokerage = new BigDecimal(0);
			BigDecimal brokerageProportion = new BigDecimal(0);
			if (roomType.getCooperateType() == 0) {
				brokerage = new BigDecimal(roomType.getBrokerage());
				basicPrice = price.subtract(brokerage);
				priceRule.setBasicPrice(basicPrice.toString());
			} else if (roomType.getCooperateType() == 1) {
				brokerageProportion = new BigDecimal(
						roomType.getBrokerageProportion());
				basicPrice = price.divide(
						new BigDecimal(1).add(brokerageProportion), 2,
						BigDecimal.ROUND_HALF_UP);
				priceRule.setBasicPrice(basicPrice.toString());
			} else {
				return new HotelResult<PriceRule>(0, roomType.getApartmentName()
						+ "未设置合作方式");
			}
			priceRuleService.insert(priceRule);
		}
		
		try {
			datePriceService.insertPirceInfo(hotelId);
			//createIndexService.createIndexByHotelId(hotelId);
			//createIndexService.createAllHotelIndex();
		} catch (UnknownHostException e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return new HotelResult<PriceRule>(0, "修改失败:"+e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new HotelResult<PriceRule>(0, "修改失败:"+e.toString());
		}
		return new HotelResult<PriceRule>(1, "修改成功");
	}
	@RequestMapping("/getSelectableWeek")
	@ResponseBody
	public String getSelectableWeek(@RequestBody List<Map<String,String>> list){
		HotelResult<Set<String>> result = new HotelResult<Set<String>>();
		Set<String> set = new HashSet<String>();
		for(Map m:list){
			String beginDate = (String)m.get("beginDate");
			String endDate = (String)m.get("endDate");
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
			Calendar calendar = Calendar.getInstance();
			int dayOfWeek = 0;
			String date;
			try {
				int days=DateUtils.daysBetween(sdf.parse(beginDate),sdf.parse(endDate));
				List<String> dateList2 = new ArrayList<String>();
				for(int i=0;i<=days;i++){
					date = DateUtils.getAfterDay(beginDate, i, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
					dateList2.add(date);
				}
				for(String date1:dateList2){
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
			} catch (Exception e) {
				e.printStackTrace();
				result.setState(0);
				result.setMsg("日期格式有误");
				return JSON.toJSONString(result);
			}
		}
		result.setState(1);
		result.setData(set);
		result.setMsg("获取成功");
		return JSON.toJSONString(result);
	}

}
