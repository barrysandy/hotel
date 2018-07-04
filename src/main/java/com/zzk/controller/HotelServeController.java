package com.zzk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zzk.util.HotelResult;
import com.zzk.util.Exceptions;
import com.zzk.util.StringUtils;
import com.zzk.entity.Dict;
import com.zzk.entity.HotelServe;
import com.zzk.entity.Ico;
import com.zzk.service.CacheService;
import com.zzk.service.DictService;
import com.zzk.service.HotelServeService;
import com.zzk.service.IcoService;
import com.zzk.controller.BaseController;

/**
 * <p>description：酒店服务设施信息</p>
 * @name：HotelServeController
 * @author：huashuwen
 * @date：2017-11-17 14:21
 */
@Controller
@RequestMapping(value = "/hotelServe")
public class HotelServeController extends BaseController {

	@Autowired
	private HotelServeService hotelServeService;
	@Autowired
	private DictService dictService;
	@Autowired
	private IcoService icoService;
	private static List<Ico> icoList;
	
	/**
	 * 酒店服务设施信息分页查询
	 * @param page
	 * @param model
	 * @param id
	 * @return
	 * @author huashuwen
	 * @date 2017-11-17 14:21
	 */
	/*@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<HotelServe> list = hotelServeService.selectByPage(map);
		int totalNum = hotelServeService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("hotelServe/hotelServe_list");
	}*/
	
	/**
	 * 酒店服务设施信息编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-17 14:21
	 */
	/*@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			HotelServe rule = new HotelServe();
			if(StringUtils.isNotBlank(id)){
				rule = hotelServeService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("hotelServe/hotelServe_edit");
	}*/
	/**
	 * 酒店服务设施信息预览
	* @param id
	* @param model
	* @return
	* @author huashuwen
	* @date 2017-11-17 14:21
	 */
	@RequestMapping("/toView")
	@ResponseBody
	public String toView(String hotelId){
		HotelResult<Object> result = new HotelResult<Object>();
		HotelServe hotelServe = new HotelServe();
		List<String> icoList = new ArrayList<String>();
		List<String> serviceList = new ArrayList<String>();
		if(StringUtils.isNotBlank(hotelId)){
			hotelServe = hotelServeService.selectByHotelId(hotelId);
		}
		if(StringUtils.isEmpty(hotelServe)){
			result.setMsg("error");
			result.setMessage("获取失败");
			result.setState(0);
			return JSON.toJSONString(result);
		}
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
            hotelServe.setServiceList(serviceList);
        }
		result.setData(hotelServe);
		result.setMsg("success");
		result.setMessage("获取成功");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	/**
	 * 酒店服务设施信息保存操作
	* @param bean
	* @return
	* @author huashuwen
	* @date 2017-11-17 14:21
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(@RequestBody HotelServe bean){
		HotelResult<Map<String,List<String>>> result = new HotelResult<Map<String,List<String>>>();
		Map<String,List<String>> resultMap= new HashMap<String,List<String>>();
		List<String> facilityList = new ArrayList<String>();
		
		try {
			if (bean != null) {
				String hotelId = bean.getHotelId();
				HotelServe rule = new HotelServe();
				if (StringUtils.isNotBlank(hotelId)) {
					rule = hotelServeService.selectByHotelId(hotelId);
				
				String hotelServeIcos = "";
					bean.setId(rule.getId());
					bean.setCreator(rule.getCreator());
					bean.setCreateDate(rule.getCreateDate());
					bean.setUpdateDate(new Date());
					bean.setStatus(1);
					if(bean.getHotWater()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "hotWater")+",";
					}
					if(bean.getPark()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "park")+",";
					}
					if(bean.getFreeWifi()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "freeWifi")+",";
					}
					if(bean.getAirConditioner()!=0){
						hotelServeIcos +=getIcoUrl("hotelServe", "airConditioner")+",";
					}
					if(hotelServeIcos.length()>0){
						hotelServeIcos = hotelServeIcos.substring(0, hotelServeIcos.length()-1);
					}
					bean.setIcos(hotelServeIcos);
					String service = "";
					//把服务数组转化为数字字符串
					if(!StringUtils.isEmpty(bean.getServiceList())){
						for(String str:bean.getServiceList()){
							service += CacheService.getValue("hotelServe",str)+",";
						}
						service = service.substring(0, service.length()-1);
					}
					bean.setService(service);
					hotelServeService.update(bean);
					facilityList.add(CacheService.getLabel("adsl",String.valueOf(bean.getAdsl())));
					facilityList.add(CacheService.getLabel("airConditioner",String.valueOf(bean.getAirConditioner())));
					facilityList.add(CacheService.getLabel("bathroom",String.valueOf(bean.getBathroom())));
					facilityList.add(CacheService.getLabel("park",String.valueOf(bean.getPark())));
					facilityList.add(CacheService.getLabel("elevator",String.valueOf(bean.getElevator())));
					facilityList.add(CacheService.getLabel("freeDrinkingWater",String.valueOf(bean.getFreeDrinkingWater())));
					facilityList.add(CacheService.getLabel("freeWifi",String.valueOf(bean.getFreeWifi())));
					facilityList.add(CacheService.getLabel("hotWater",String.valueOf(bean.getHotWater())));
					resultMap.put("facilityList",facilityList );
					resultMap.put("serviceList",bean.getServiceList());
				}
			}
		} catch (Exception e) {
			result.setState(0);
			result.setMsg("error");
			System.err.println(e.toString());
			Exceptions.getStackTraceAsString(e);// 统一的异常处理
			return JSON.toJSONString(result);
		}
		result.setData(resultMap);
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 将服务设施转为字符串
	* @param bean
	* @return
	* @author huashuwen
	* @date 2017-11-17 14:21
	 */
	@RequestMapping("/parseStr")
	@ResponseBody
	public String parseStr(@RequestBody HotelServe bean){
		HotelResult<Map<String,List<String>>> result = new HotelResult<Map<String,List<String>>>();
		Map<String,List<String>> resultMap= new HashMap<String,List<String>>();
		List<String> facilityList = new ArrayList<String>();
		
		try {
			if (bean != null) {
				String hotelServeIcos = "";

				bean.setUpdateDate(new Date());
				bean.setStatus(1);
				if (bean.getHotWater() != 0) {
					hotelServeIcos += getIcoUrl("hotelServe", "hotWater") + ",";
				}
				if (bean.getPark() != 0) {
					hotelServeIcos += getIcoUrl("hotelServe", "park") + ",";
				}
				if (bean.getFreeWifi() != 0) {
					hotelServeIcos += getIcoUrl("hotelServe", "freeWifi") + ",";
				}
				if (bean.getAirConditioner() != 0) {
					hotelServeIcos += getIcoUrl("hotelServe", "airConditioner")
							+ ",";
				}
				if (hotelServeIcos.length() > 0) {
					hotelServeIcos = hotelServeIcos.substring(0,
							hotelServeIcos.length() - 1);
				}
				bean.setIcos(hotelServeIcos);
				String service = "";
				// 把服务数组转化为数字字符串
				if (!StringUtils.isEmpty(bean.getServiceList())) {
					for (String str : bean.getServiceList()) {
						service += CacheService.getValue("hotelServe", str) + ",";
					}
					if(bean.getServiceList().size()>0){
						service = service.substring(0, service.length() - 1);
					}
				}
				bean.setService(service);
				facilityList.add(CacheService.getLabel("adsl",
						String.valueOf(bean.getAdsl())));
				facilityList.add(CacheService.getLabel("airConditioner",
						String.valueOf(bean.getAirConditioner())));
				facilityList.add(CacheService.getLabel("bathroom",
						String.valueOf(bean.getBathroom())));
				facilityList.add(CacheService.getLabel("park",
						String.valueOf(bean.getPark())));
				facilityList.add(CacheService.getLabel("elevator",
						String.valueOf(bean.getElevator())));
				facilityList.add(CacheService.getLabel("freeDrinkingWater",
						String.valueOf(bean.getFreeDrinkingWater())));
				facilityList.add(CacheService.getLabel("freeWifi",
						String.valueOf(bean.getFreeWifi())));
				facilityList.add(CacheService.getLabel("hotWater",
						String.valueOf(bean.getHotWater())));
				resultMap.put("facilityList", facilityList);
				resultMap.put("serviceList", bean.getServiceList());
			}
		} catch (Exception e) {
			result.setState(0);
			result.setMsg("error");
			System.err.println(e.toString());
			Exceptions.getStackTraceAsString(e);// 统一的异常处理
			return JSON.toJSONString(result);
		}
		result.setData(resultMap);
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 酒店服务设施信息删除
	* @param id
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-17 14:21
	 */
	@RequestMapping("/del")
	@ResponseBody
	public HotelResult<HotelServe> del(String id){
		try{
			hotelServeService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<HotelServe>(2,"error");
		}
		return new HotelResult<HotelServe>(1,"success");
	}
	/**
	 * 获取设施服务页面选项
	* @param 
	* @return
	* @author huashuwen
	* @date 2017-11-17 14:21
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public String getList(){
		HotelResult<Map<String,Object>> result = new HotelResult<Map<String,Object>>();
		List<Dict> parkList = new ArrayList<Dict>();
		List<Dict> airConditionerList = new ArrayList<Dict>();
		List<Dict> freeWifiList = new ArrayList<Dict>();
		List<Dict> elevatorList = new ArrayList<Dict>();
		List<Dict> hotWaterList = new ArrayList<Dict>();
		List<Dict> bathroomList = new ArrayList<Dict>();
		List<Dict> freeDrinkingWaterList = new ArrayList<Dict>();
		List<Dict> adslList = new ArrayList<Dict>();
		List<String> serviceList = new ArrayList<String>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String type= "";
		List<Dict> list = CacheService.getAllDict();
		for(Dict dict:list){
			type = dict.getType();
			if("park".equals(type)){
				parkList.add(dict);
			}
			if("airConditioner".equals(type)){
				airConditionerList.add(dict);
			}
			if("freeWifi".equals(type)){
				freeWifiList.add(dict);
			}
			if("elevator".equals(type)){
				elevatorList.add(dict);
			}
			if("hotWater".equals(type)){
				hotWaterList.add(dict);
			}
			if("bathroom".equals(type)){
				bathroomList.add(dict);
			}
			if("freeDrinkingWater".equals(type)){
				freeDrinkingWaterList.add(dict);
			}
			if("adsl".equals(type)){
				adslList.add(dict);
			}
			if("hotelServe".equals(type)){
				serviceList.add(dict.getLabel());
			}
		}
		resultMap.put("parkList", parkList);
		resultMap.put("airConditioner", airConditionerList);
		resultMap.put("freeWifi", freeWifiList);
		resultMap.put("elevator", elevatorList);
		resultMap.put("hotWater", hotWaterList);
		resultMap.put("bathroom", bathroomList);
		resultMap.put("freeDrinkingWater", freeDrinkingWaterList);
		resultMap.put("adslList", adslList);
		resultMap.put("serviceList", serviceList);
		result.setData(resultMap);
		result.setState(1);
		return JSON.toJSONString(result);
	}
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
	
	
}
