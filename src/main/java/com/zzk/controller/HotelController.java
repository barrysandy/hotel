package com.zzk.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.text.SimpleDateFormat;  

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzk.util.HotelResult;
import com.zzk.common.PlatConstants;
//import com.wisesoft.wisdom.xixing.common.mvc.FormModel;
import com.zzk.util.DateUtils;
import com.zzk.util.Exceptions;
import com.zzk.util.HttpUtils;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.zzk.entity.Access;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.Dict;
import com.zzk.entity.Hotel;
import com.zzk.entity.HotelPolicy;
import com.zzk.entity.HotelServe;
import com.zzk.entity.Ico;
import com.zzk.entity.LineUser;
import com.zzk.service.AccessService;
import com.zzk.service.BusinessInfoService;
import com.zzk.service.CacheService;
import com.zzk.service.DatePriceService;
import com.zzk.service.DelayService;
import com.zzk.service.DictService;
import com.zzk.service.HotelGoodsService;
import com.zzk.service.HotelPolicyService;
import com.zzk.service.HotelServeService;
import com.zzk.service.HotelService;
import com.zzk.service.IcoService;
import com.zzk.service.LineUserService;
import com.zzk.service.OrderBaseInfoService;
import com.zzk.service.OrderService;
import com.zzk.service.PriceRuleService;
import com.zzk.controller.BaseController;

/**
 * <p>description：酒店信息</p>
 * @name：HotelController
 * @author：sty
 * @date：2017-11-02 10:26
 */
@Controller
@RequestMapping(value = "/hotel") 
public class HotelController extends BaseController {

	@Autowired
	private HotelService hotelService;
	@Autowired
	private HotelPolicyService hotelPolicyService;
	@Autowired
	private HotelServeService hotelServeService;
	@Autowired
	private HotelGoodsService hotelGoodsService;
	@Autowired
	private DictService dictService;
	@Autowired
	private IcoService icoService;
	@Autowired
	private PriceRuleService priceRuleService;
	@Autowired
	private AccessService accessService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderBaseInfoService orderBaseInfoService;
	@Autowired
	private DatePriceService datePriceService;
	@Autowired
	private DelayService delayService;
	@Autowired
	private LineUserService lineUserService;
	@Autowired
	private BusinessInfoService businessInfoService;
	
	private static List<Dict> dictList;
	private static List<Ico> icoList;
	private static String host = "http://geo.market.alicloudapi.com";
	private static String path = "/v3/geocode/geo";
	private static String method = "GET";
	private static String appcode = "e85d2d16958643168e7628ff5618192a";
	
	
	/**
	 * 酒店信息分页查询
	 * @param page
	 * @param model
	 * @param id
	 * @return
	 * @author sty
	 * @date 2017-11-02 10:26
	 *//*
	@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<Hotel> list = hotelService.selectByPage(map);
		int totalNum = hotelService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("hotel/hotel_list");
	}*/
	
	/**
	 * 酒店信息编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:26
	 *//*
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			Hotel rule = new Hotel();
			if(StringUtils.isNotBlank(id)){
				rule = hotelService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("hotel/hotel_edit");
	}*/
	/**
	 * 酒店信息预览
	* @param id
	* @param model
	* @return
	* @author huashuwen
	* @date 2017-11-02 10:26
	 */
	@RequestMapping("/toView")
	@ResponseBody
	public String toView(String id,String i){
		HotelResult<Object> result = new HotelResult<Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		Hotel rule = new Hotel();
		Access access = new Access();
		HotelPolicy hotelPolicy = new HotelPolicy();
		HotelServe hotelServe = new HotelServe();
		List<String> icoList = new ArrayList<String>();
		List<String> serviceList = new ArrayList<String>();
		if(StringUtils.isNotBlank(id)){
			rule = hotelService.selectByPrimaryKey(id);
			System.err.println(JSON.toJSONString(rule));
			String cityId = rule.getCityId();
			String areaId = rule.getAreaId();
			String shortProvinceId = cityId.substring(0, 2);
			
			String cityName = hotelService.selecCity(cityId);
			String areaName = hotelService.selectArea(areaId);
			String provinceName = hotelService.selecProvince(shortProvinceId);
			
			rule.setCityName(cityName);
			rule.setAreaName(areaName);
			rule.setProvinceName(provinceName);
			rule.setProvinceId(shortProvinceId+"0000");
			System.err.println(JSON.toJSON(rule));
			hotelPolicy = hotelPolicyService.selectByHotelId(id);
			hotelServe = hotelServeService.selectByHotelId(id);
			
			if (StringUtils.isEmpty(rule)) {
	            result.setState(0);
	            result.setMsg("error");
	            result.setMessage("获取失败");
	        } else {
	            result.setState(1);
	            //酒店政策
	            if(!StringUtils.isEmpty(hotelPolicy)){
	            	hotelPolicy.setBreakfastStr(CacheService.getLabel("breakfast",String.valueOf(hotelPolicy.getBreakfast())));
		            hotelPolicy.setCreditCardStr(CacheService.getLabel("creditCard",String.valueOf(hotelPolicy.getCreditCard())));
		            if(hotelPolicy.getPetBring()==1){
		            	hotelPolicy.setPetBringStr("携带宠物"+CacheService.getLabel("petBring",String.valueOf(hotelPolicy.getPetBring()))+hotelPolicy.getPetFee()+"元");
		            }else{
		            	hotelPolicy.setPetBringStr(CacheService.getLabel("petBring",String.valueOf(hotelPolicy.getPetBring())));
		            }
		            dataMap.put("hotelPolicy",hotelPolicy);
	            }
	            //酒店服务设
	            if(!StringUtils.isEmpty(hotelServe)){
	            	String icos= hotelServe.getIcos();
		            if(StringUtils.isNotBlank(icos)){
		            	String[] aIcos= icos.split(",");
		            	for(String ico:aIcos){
		            		icoList.add(ico);
		            	}
		            }
		            hotelServe.setIcoList(icoList);
		            hotelServe.setAdslStr(CacheService.getLabel("adsl",String.valueOf(hotelServe.getAdsl())));
		            hotelServe.setAirConditionerStr(CacheService.getLabel("airConditioner",String.valueOf(hotelServe.getAirConditioner())));
		            hotelServe.setBathroomStr(CacheService.getLabel("bathroom",String.valueOf(hotelServe.getBathroom())));
		            hotelServe.setElevatorStr(CacheService.getLabel("elevator",String.valueOf(hotelServe.getElevator())));
		            hotelServe.setFreeDrinkingWaterStr(CacheService.getLabel("freeDrinkingWater",String.valueOf(hotelServe.getFreeDrinkingWater())));
		            hotelServe.setFreeWifiStr(CacheService.getLabel("freeWifi",String.valueOf(hotelServe.getFreeWifi())));
		            hotelServe.setHotWaterStr(CacheService.getLabel("hotWater",String.valueOf(hotelServe.getHotWater())));
		            hotelServe.setParkStr(CacheService.getLabel("park",String.valueOf(hotelServe.getPark())));
		            if(!StringUtils.isEmpty(hotelServe.getService())){
		            	String[] aService= hotelServe.getService().split(",");
			            for(String str:aService){
			            	str = CacheService.getLabel("hotelServe",str);
			            	serviceList.add(str);
			            }
			        }
		            hotelServe.setServiceList(serviceList);
		            dataMap.put("hotelServe", hotelServe);
	            }
	            dataMap.put("hotel", rule);
	            if(i==null){
	            	access.setId(UUID.randomUUID().toString());
		    		access.setAccessTime(DateUtils.getDate());
		    		access.setResourceId(rule.getId());
		    		access.setResourceName(rule.getName());
		    		access.setOwnerId(rule.getOwnerId());
		    		access.setSellerId(rule.getId());
		    		access.setResourceType(1);
		    		accessService.insertSelective(access);
	            }
	            result.setMsg("success");
	            result.setMessage("获取成功");
	            result.setData(dataMap);
	    	}
		}else{
			result.setState(0);
            result.setMsg("error");
            result.setMessage("id不能为空");
            
		}
		
		
		return JSON.toJSONString(result);
	}
	
	/**
	 * 酒店信息保存操作
	* @param bean
	* @return
	* @author huashuwen
	* @date 2017-11-02 10:26
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(@RequestBody Hotel hotel){
		HotelResult<Object> result = new HotelResult<Object>();
		HotelServe hotelServe = hotel.getHotelServe();
		
		String hotelServeIcos = "";
		String hotelIcos = "";
		if(hotel.getRenovationDateLong()!=0){
			hotel.setRenovationDate(new Date(hotel.getRenovationDateLong()));
		}
		if(hotel.getOpenTimeLong()!=0){
			hotel.setOpenTime(new Date(hotel.getOpenTimeLong()));
		}
		System.err.println(JSON.toJSON(hotel));
		//try{
			if(hotel!=null){
				String id = hotel.getId();
				if(StringUtils.isNotBlank(id)){
					Hotel bean = hotelService.selectByPrimaryKey(id);
					HotelServe bean1 = hotelServeService.selectByHotelId(id);
					hotel.setAddress(hotel.getAddress());
					hotel.setCreateDate(bean.getCreateDate());
					hotel.setUpdateDate(new Date());
					hotel.setCreator(bean.getCreator());
					if(hotelServe.getHotWater()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "hotWater")+",";
						hotelIcos += getIcoUrl("hotel", "hotWater")+",";
					}
					if(hotelServe.getPark()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "park")+",";
						hotelIcos += getIcoUrl("hotel", "park")+",";
					}
					if(hotelServe.getFreeWifi()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "freeWifi")+",";
						hotelIcos += getIcoUrl("hotel", "freeWifi")+",";
					}
					if(hotelServe.getAirConditioner()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "airConditioner")+",";
						hotelIcos += getIcoUrl("hotel", "airConditioner")+",";
					}
					String service = "";
					if(!StringUtils.isEmpty(hotelServe.getServiceList())){
						for(String str:hotelServe.getServiceList()){
							service += CacheService.getValue("hotelServe",str)+",";
						}
						if(service.length()>0){
							service = service.substring(0, service.length()-1);
						}
					}
					String[] serveCodes =new String[]{"1","6","9","8","10","14"};
					for(String a :serveCodes){
						if(service.indexOf(a)>=0){
							String c =CacheService.getLabel("hotelServe", a);
							if(!StringUtils.isEmpty(getIcoUrl("hotel", c))){
								hotelIcos +=getIcoUrl("hotel", c)+",";
							}
							if(!StringUtils.isEmpty(getIcoUrl("hotelServe", c))){
								hotelServeIcos +=getIcoUrl("hotelServe", c)+","; 
							}
						}
					}
					if(hotelIcos.length()>0){
						hotelIcos = hotelIcos.substring(0, hotelIcos.length()-1);
					}
					if(hotelServeIcos.length()>0){
						hotelServeIcos = hotelServeIcos.substring(0, hotelServeIcos.length()-1);
					}
					hotel.setHotelIcos(hotelIcos);
					hotel.setRecordStatus(1);
					hotel.setState(bean.getState());
					BusinessInfo info = hotelService.hotel2Business(hotel);
					hotelService.update(info);
					hotelService.update(hotel);
					
					hotelServe.setUpdateDate(new Date());
					hotelServe.setCreator(bean1.getCreator());
					hotelServe.setCreateDate(bean1.getCreateDate());
					hotelServe.setUpdater(hotel.getUpdater());
					hotelServe.setIcos(hotelServeIcos);
					hotelServe.setService(service);
					hotelServeService.update(hotelServe);
				}else{
					UUID u = UUID.randomUUID();
					UUID uuid = UUID.randomUUID();
					
					hotel.setAddress(hotel.getAddress());
					hotel.setRecordStatus(1);
					hotel.setId(u.toString());
					hotel.setState(4);
					hotel.setCreateDate(new Date());
					hotel.setMiniPrice(0.00);
					hotel.setMaxPrice(0.00);
					hotel.setCommentScore(5.0);
					//TODO
					hotel.setState(PlatConstants.HOTEL_NORMAL);
					if(hotelServe.getHotWater()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "hotWater")+",";
						hotelIcos += getIcoUrl("hotel", "hotWater")+",";
					}
					if(hotelServe.getPark()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "park")+",";
						hotelIcos += getIcoUrl("hotel", "park")+",";
					}
					if(hotelServe.getFreeWifi()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "freeWifi")+",";
						hotelIcos += getIcoUrl("hotel", "freeWifi")+",";
					}
					if(hotelServe.getAirConditioner()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "airConditioner")+",";
						hotelIcos += getIcoUrl("hotel", "airConditioner")+",";
					}
					String service = "";
					if(!StringUtils.isEmpty(hotelServe.getServiceList())){
						for(String str:hotelServe.getServiceList()){
							service += CacheService.getValue("hotelServe",str)+",";
						}
						if(service.length()>0){
							service = service.substring(0, service.length()-1);
						}
						
					}
					
					String[] serveCodes =new String[]{"1","6","9","8","10","14"};
					for(String a :serveCodes){
						if(service.indexOf(a)>=0){
							String c =CacheService.getLabel("hotelServe", a);
							if(!StringUtils.isEmpty(getIcoUrl("hotel", c))){
								hotelIcos +=getIcoUrl("hotel", c)+",";
							}
							if(!StringUtils.isEmpty(getIcoUrl("hotelServe", c))){
								hotelServeIcos +=getIcoUrl("hotelServe", c)+","; 
							}
						}
					}
					if(hotelServeIcos.length()>0){
						hotelServeIcos = hotelServeIcos.substring(0, hotelServeIcos.length()-1);
					}
					if(hotelIcos.length()>0){
						hotelIcos = hotelIcos.substring(0, hotelIcos.length()-1);
					}
					hotel.setHotelIcos(hotelIcos);
					hotelService.insert(hotel);
					hotelServe.setStatus(1);
					hotelServe.setHotelId(u.toString());
					hotelServe.setId(uuid.toString());
					hotelServe.setCreateDate(new Date());
					hotelServe.setCreator(hotel.getCreator());
					hotelServe.setStatus(1);
					hotelServe.setIcos(hotelServeIcos);
					hotelServe.setService(service);
					hotelServeService.insert(hotelServe);
					
				}
				result.setState(1);
				result.setMsg("已保存");
				result.setData(hotel.getId());
			}
		/*}catch(Exception e){
			System.err.println(e.toString());
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			result.setState(2);
			result.setMsg("error");
		}*/
		return JSON.toJSONString(result);
	}
	/**
	 * 判断ownerId下是否绑定酒店
	* @param id
	* @return
	* @author huashuwen
	* @date 2017-11-03 
	 */
	@RequestMapping("/checkOwnerId")
	@ResponseBody
	public String checkOwnerId(String ownerId){
		HotelResult<Object> result = new HotelResult<Object>();
		List<Hotel> hotels = hotelService.checkOwnerId(ownerId);
		if(hotels.size()!=0){
			Hotel hotel = hotels.get(0);
			String hotelId = hotel.getId();
			String hotelName = hotel.getName();
			int count= hotelGoodsService.countHotelGoods(hotelId);
			Map<String,Object> m = new HashMap<String,Object> ();
			m.put("hotelId", hotelId);
			m.put("hotelName", hotelName);
			m.put("count", count);
			result.setData(m);
			result.setState(1);
		}else {
			LineUser bean = lineUserService.selectByPrimaryKey(ownerId);
			String merchantId = bean.getMerchatId();
			if(StringUtils.isBlank(merchantId)){
				result.setState(0);
			}else{
				BusinessInfo businessInfo = businessInfoService.selectByPrimaryKey(merchantId);
				if(StringUtils.isEmpty(businessInfo)){
					result.setState(0);
				}else{
					Hotel hotel = hotelService.business2Hotel(businessInfo);
					hotel.setRecordStatus(1);
					hotel.setState(1);
					hotel.setCreateDate(new Date());
					hotelService.insert(hotel);
					HotelServe hotelServe = new HotelServe();
					hotelServe.setId(UUID.randomUUID().toString());
					hotelServe.setHotelId(merchantId);
					hotelServe.setStatus(1);
					hotelServe.setCreateDate(new Date());
					hotelServeService.insert(hotelServe);
					Map<String,Object> m = new HashMap<String,Object> ();
					m.put("hotelId", merchantId);
					m.put("hotelName", businessInfo.getBusinName());
					m.put("count", 0);
					result.setData(m);
					result.setState(1);
				}
			}
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 修改酒店状态
	* @param id
	* @return
	* @author huashuwen
	* @date 2017-11-03 
	 */
	@RequestMapping("/updateState")
	@ResponseBody
	public String updateState(HttpServletRequest request){
		String id = request.getParameter("id");
		String state = request.getParameter("state");
		Hotel hotel = hotelService.selectByPrimaryKey(id); 
		if(StringUtils.isEmpty(hotel)){
			return JSON.toJSONString(new HotelResult<Hotel>(0,"error"));
		}else{
			hotel.setState(Integer.parseInt(state));
			hotelService.update(hotel);
			return JSON.toJSONString(new HotelResult<Hotel>(1,"success"));
		}
	}
	
	
	/**
	 * 酒店搜索
	* @param bean
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-23 10:26
	 */
	@RequestMapping("/search")
	@ResponseBody
	public String search(Hotel bean){
		HotelResult<List<Hotel>> result = new HotelResult<List<Hotel>>();
		List<Hotel> list =new ArrayList<Hotel>();
		list = hotelService.selectList();
		
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	
	/*@RequestMapping("/search1")
	@ResponseBody
	public String search1(@FormModel("pager")Page pager,Date comeDate,Date leaveDate,String searchKey,String starLevel,String destination){
		Map<String, String> map = new HashMap<String, String>();  
       
		return " ";
	}*/
	@RequestMapping("/saveOrUpdateDescription")
	@ResponseBody
	public String saveOrUpdateDescription(@RequestBody Hotel bean){
		HotelResult result = new HotelResult();
		String id = bean.getId();
		if(StringUtils.isNotBlank(id)){
			Hotel rule = hotelService.selectByPrimaryKey(id);
			rule.setDescription(bean.getDescription());
			rule.setSellingPoint(bean.getSellingPoint());
			rule.setUpdateDate(new Date());
			rule.setUpdater(bean.getUpdater());
			hotelService.update(rule);
			result.setState(1);
			result.setMsg("修改成功");
		}else{
			result.setState(0);
			result.setMsg("获取酒店信息失败");
			result.setMessage("获取酒店信息失败");
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 酒店信息删除
	* @param id
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:26
	 */
	@RequestMapping("/del")
	@ResponseBody
	public HotelResult<Hotel> del(String id){
		try{
			hotelService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<Hotel>(2,"error");
		}
		return new HotelResult<Hotel>(1,"success");
	}
	@RequestMapping("/getProvinceList")
	@ResponseBody
	public String getProvinceList(){
		HotelResult<List<Map<String,Object>>> result = new HotelResult<List<Map<String,Object>>>();
		List<Map<String,Object>> list = hotelService.getProvinceList();
		result.setData(list);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	@RequestMapping("/getCityList")
	@ResponseBody
	public String getCityList(String provinceId){
		HotelResult<List<Map<String,Object>>> result = new HotelResult<List<Map<String,Object>>>();
		List<Map<String,Object>> list = hotelService.getCityList(provinceId);
		result.setData(list);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	@RequestMapping("/getAreaList")
	@ResponseBody
	public String getAreaList(String cityId){
		HotelResult<List<Map<String,Object>>> result = new HotelResult<List<Map<String,Object>>>();
		List<Map<String,Object>> list = hotelService.getAreaList(cityId);
		result.setData(list);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	
	/*public String getDictName(String type,String code){
		if(dictList==null){
			dictList=dictService.selectDict();
		}
		for(Dict dict:dictList){
			if(type.equals(dict.getDictType())&&code.equals(dict.getDictCode())){
				return dict.getDictName();
			}
		}
		return "";
	}*/
	
	public String getIcoUrl(String type,String name){
		if(icoList==null){
			icoList=icoService.selectIco();
		}
		for(Ico ico:icoList){
			if(type.equals(ico.getType())&&name.equals(ico.getName())){
				return ico.getImg();
			}
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/isFullRoom")
	public String isFullRoom(String shopId){
		HotelResult<Map<String,Object>> result = new HotelResult<Map<String,Object>>();
		Map<String,Object> map = hotelService.getRoomState(shopId);
		if(map !=null){
		if(map.get("time") == null){
			result.setMsg("error");
			result.setState(0);
		}
		result.setData(map);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
		}else{
			result.setMessage("无房间记录");
			result.setMsg("success");
			result.setState(1);
			return JSON.toJSONString(result);
		}
	}
	/**
	 * 设置列表封面
	 * @param hotelId
	 * @param coverImg
	 * @return
	 */
	@RequestMapping("/editCoverImg")
	@ResponseBody
	public String editCoverImg(String hotelId,String coverImg,String updater){
		HotelResult result = new HotelResult<>();
		Hotel bean = hotelService.selectByPrimaryKey(hotelId);
		if(!StringUtils.isEmpty(bean)){
			bean.setCoverImg(coverImg);
			bean.setUpdater(updater);
			bean.setUpdateDate(new Date());
			hotelService.update(bean);
			result.setState(1);
			result.setMsg("seccuss");
			result.setMessage("修改成功");
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("修改成功");
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 设置详情页封面
	 * @param hotelId
	 * @param detailImg
	 * @return
	 */
	@RequestMapping("/editDetailImg")
	@ResponseBody
	public String editDetailImg(String hotelId,String detailImg,String updater){
		HotelResult result = new HotelResult<>();
		Hotel bean = hotelService.selectByPrimaryKey(hotelId);
		if(!StringUtils.isEmpty(bean)){
			bean.setDetailImg(detailImg);
			bean.setUpdater(updater);
			bean.setUpdateDate(new Date());
			hotelService.update(bean);
			result.setState(1);
			result.setMsg("seccuss");
			result.setMessage("修改成功");
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("修改失败");
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 编辑相册
	 * @param hotelId
	 * @param detailImg
	 * @return
	 */
	@RequestMapping("/editAlbum")
	@ResponseBody
	public String editAlbum(String hotelId,String album,String updater){
		HotelResult result = new HotelResult<>();
		Hotel bean = hotelService.selectByPrimaryKey(hotelId);
		if(!StringUtils.isEmpty(bean)){
			bean.setAlbum(album);
			bean.setUpdater(updater);
			bean.setUpdateDate(new Date());
			hotelService.update(bean);
			result.setState(1);
			result.setMsg("seccuss");
			result.setMessage("修改成功");
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("修改失败");
		}
		return JSON.toJSONString(result);
	}
	@RequestMapping("/parseAddress")
	@ResponseBody
	public String parseAddress(String city,String address){
		HotelResult result = new HotelResult<>();
		Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    Map<String, Double> resultMap = new HashMap<String, Double>();
	    querys.put("address", address);
	    querys.put("batch", "false");
	    querys.put("city", city);
	    querys.put("output", "JSON");
	    
	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	if(response.getStatusLine().getStatusCode()==200){
	    		String jsonStr = EntityUtils.toString(response.getEntity());
	    		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
	    		String count = (String)jsonObject.get("count");
	    		if(!"0".equals(count)){
	    			JSONArray geocodes = (JSONArray)jsonObject.get("geocodes");
		    		JSONObject a = (JSONObject)geocodes.get(0);
		    		String location = (String)a.get("location");
		    		String[] aLocation= location.split(",");
		    		double lon = Double.parseDouble(aLocation[0]);
		    		double lat = Double.parseDouble(aLocation[1]);
		    		resultMap.put("lat", lat);
		    		resultMap.put("lon", lon);
		    		result.setData(resultMap);
		    		result.setState(1);
		    		result.setMsg("success");
		    		result.setMessage("定位成功");
	    		}else{
	    			result.setState(0);
	    			result.setMsg("error");
		    		result.setMessage("定位失败");
	    		}
	    	}else{
	    		result.setState(0);
	    		result.setMsg("error");
	    		result.setMessage("定位失败");
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	result.setState(0);
	    	result.setMsg("error");
    		result.setMessage("定位失败");
	    }
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/selectTotalAccess")
	@ResponseBody
	public String selectTotalAccess(String resourceId,int resourceType,String shopId){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime = new Date();
		List<String> dateList = new ArrayList<String>();
		String date = "";
		String startDate;
		
		
	    Result<Map<String,Object>> result = new Result<Map<String,Object>>();
	    Map<String,Object> resultMap = new HashMap<String,Object>();
	    
	    List<Double> percentList = new ArrayList<Double>();
	    Map map = new HashMap<>();
	    Map map2 = new HashMap<>();
	    map.put("sellerId", resourceId);
	    map.put("resourceType", resourceType);
		map.put("accessTime", currentTime); 
		map2.put("createTime", currentTime);
		map2.put("sellerId", shopId);
		List numList = new ArrayList();
		try {
			numList = accessService.selectTotalNumber(map);
			percentList = orderBaseInfoService.selectDailyCountByShopId(map2,numList);
			
			startDate = DateUtils.getAfterDay(sdf.format(currentTime), -6, "yyyy-MM-dd");
			for(int i=0;i<=6;i++){
				date = DateUtils.getAfterDay(startDate, i, "yyyy-MM-dd");
				dateList.add(date);
			}
		} catch (Exception e1) {
			return JSON.toJSONString(new Result(0,"error",e1.toString(),null));
		}
		
		resultMap.put("num", numList);
		resultMap.put("date", dateList);
		resultMap.put("percent", percentList);
		result.setData(resultMap);
		result.setMsg("success");
		result.setState(1); 
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/refreshCache")
	@ResponseBody
	public HotelResult refreshCache(){
		CacheService.update();
		return new HotelResult(1,"success");
	}
	
	@RequestMapping("/addHotel")
	@ResponseBody
	public HotelResult addHotel(String ownerId,String name){
		Hotel hotel =new Hotel();
		hotel.setOwnerId(ownerId);
		hotel.setName(name);
		hotel.setState(1);
		hotel.setRecordStatus(1);
		hotel.setId(UUID.randomUUID().toString());
		hotelService.insert(hotel);
		return new HotelResult(1,"success");
	}
	
	@RequestMapping("/orderDelay")
	@ResponseBody
	public int orderDelay(long orderNo){
		delayService.addPrepare(orderNo);
		return 1;
	}
	@RequestMapping("/deleteOrderDelay")
	@ResponseBody
	public boolean deleteOrderDelay(long orderNo){
		boolean b=delayService.removePrepare(orderNo);
		return b;
	}
	
	
}
