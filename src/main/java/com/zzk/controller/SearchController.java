/**
* @Description:  
* @author sty   
* @date 2017年3月23日 上午10:00:38 
* @version V1.0   
*/
package com.zzk.controller;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zzk.util.DateUtils;
import com.zzk.util.HotelResult;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.common.FormModel;
import com.zzk.entity.RoomType;
import com.zzk.service.CreateIndexService;
import com.zzk.service.SearchService;


@Controller
@RequestMapping(value = "/search")
public class SearchController  extends BaseController{
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private CreateIndexService createIndexService;
	 
	
	 /**
	  * <p>description:初始化搜索引擎，创建索引和数据结构，一般手动调用</p>
	  * @param 
	  * @return String
	  * @date 2017-7-6上午10:54:54
	  */
	@RequestMapping(value="/initSearchEngine",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String initSearchEngine(Model model) {
		try {
			createIndexService.initSearchEngine();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
		
	}
	/**
	 * <p>description:导入所有商品数据</p>
	 * @param 
	 * @return String
	 * @date 2017-7-6上午11:14:24
	 */
	@RequestMapping(value="/createAllHotelIndex",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createAllIndex(Model model) {
	   
		try {
			createIndexService.createAllHotelIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
		
	}
	/**
	 * <p>description:保存一条商品数据</p>
	 * @param productId 商品ID
	 * @return String
	 * @date 2017-7-6上午11:46:22
	 
	@RequestMapping(value="/createOrUpdateIndexByProductId",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createOrUpdateIndexByProductId(String productId){
		try {
			createIndexService.createIndexByProductId(productId);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	/**
	 * 
	 * <p>description:通过店铺ID处理该店铺下的数据在搜索引擎中的状态</p>
	 * @param 
	 * @return String
	 * @author Wen Yugang
	 * @date 2017-8-2下午4:20:24
	 *//*
	@RequestMapping(value="/createOrUpdateProductByShopId",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createOrUpdateProductByShopId(String shopId){
		try{
			createIndexService.createOrUpdateProductByShopId(shopId);
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	*//**
	 * <p>description:创建一条活动索引数据</p>
	 * @param activityId 活动ID
	 * @return String
	 * @date 2017-7-6上午11:48:09
	 *//*
	@RequestMapping(value="/createOrUpdateActivityIndexById",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createOrUpdateActivityIndexById(String activityId){
		
		try {
			createIndexService.createActivityIndexById(activityId);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
		
	}
	*//**
	 * <p>description:创建所有活动数据</p>
	 * @param 
	 * @return String
	 * @date 2017-7-6下午2:02:41
	 *//*
	@RequestMapping(value="/createAllActivityIndex",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createAllActivityIndex(){
		try {
			createIndexService.createAllActivityIndex();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
		
	}
	*//**
	 * <p>description:创建游记内容索引</p>
	 * @param contentId 游记ID
	 * @return String
	 * @date 2017-7-6下午2:07:31
	 *//*
	@RequestMapping(value="/createContentIndexById",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createContentIndexById(String contentId){
		try {
			createIndexService.createContentIndexById(contentId);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
		
	}
	*//**
	 * <p>description:创建所有内容索引</p>
	 * @param 
	 * @return String
	 * @date 2017-7-6下午2:10:12
	 *//*
	@RequestMapping(value="/createAllContentIndex",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createAllContentIndex(String contentId){
		try {
			createIndexService.createAllContentIndex();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	*//**
	 * <p>description:创建目的地数据</p>
	 * @param 
	 * @return String
	 * @author Wen Yugang
	 * @date 2017-8-1上午10:06:48
	 *//*
	@RequestMapping(value="/createAllDestinationIndex",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createAllDestinationIndex(){
		try{
			createIndexService.createAllDestinationIndex();
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
		
	}
	*//**
	 * <p>description:搜索所有</p>
	 * @param keyword 关键字
	 * @return String
	 * @date 2017-7-6下午2:14:25
	 *//*
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/searchAll")
	public String searchAll(String keyword,Model model) throws UnknownHostException {
		Page pager = new Page();
		pager.setPageNumber(0);
		pager.setPageSize(5);
		int start = (pager.getPageNo() - 1) * pager.getPageSize();
		int pageSize = pager.getPageSize();
		
		Map<String,String> categories = new HashMap<String,String>();
		
		Properties p =PropertiesUtil.getProperties(ResourceUtil.getResourceAsStream(EIGHT_GOODSTYPE));
		
		String line = p.getProperty(" goodsType.route", "line");//线路
		String hotel = p.getProperty("goodsType.hotel", "hotel");//酒店
		String car = p.getProperty("goodsType.car", "car");//租车
		String food = p.getProperty("goodsType.food", "food");//美食
		String scene = p.getProperty("goodsType.sight", "scene");//景点
		String natives = p.getProperty("goodsType.local", "native");//特产
		String entertainment = p.getProperty("goodsType.entertain", "entertainment");//娱乐
		
		String line_id = dataService.getRegIdByCode(line);//线路类型ID
		String hotel_id = dataService.getRegIdByCode(hotel);//酒店类型ID
		String car_id = dataService.getRegIdByCode(car);//租车类型ID
		String food_id = dataService.getRegIdByCode(food);//美食类型ID
		String scene_id = dataService.getRegIdByCode(scene);//景点类型ID
		String native_id = dataService.getRegIdByCode(natives);//特产类型ID
		String entertain_id = dataService.getRegIdByCode(entertainment);//娱乐类型ID
		
		categories.put(line_id, line);
		categories.put(hotel_id, hotel);
		categories.put(car_id, car);
		categories.put(food_id, food);
		categories.put(scene_id, scene);
		categories.put(native_id, "natives");
		categories.put(entertain_id, entertainment);
         
		Map<String,Object> data = new HashMap<String,Object>();
		for (Map.Entry<String,String> entry : categories.entrySet()) {
			String categoryId = (String) entry.getKey();
			Map<String,Object> parameters = new HashMap<String,Object>();
			Map<String,Object> attributes = new HashMap<String,Object>();
			if(!StringUtil.isEmpty(keyword)){
				parameters.put("name", keyword);
				parameters.put("descM", keyword);
				parameters.put("shopName", keyword);
			}
			String sortFiled = "";
			int sortType = 0;
			searchService.search2("products",categoryId, parameters, attributes, start, pageSize,pager,sortFiled,sortType);
			List list = pager.getList();
			data.put(entry.getValue()+"Num", pager.getFullListSize());
			data.put(entry.getValue(), list);
		}
		//搜索游记
		Map<String,Object> parameters = new HashMap<String,Object>();
		Map<String,Object> attributes = new HashMap<String,Object>();
		String sortFiled = "";
		int sortType = 0;
		if(!StringUtil.isEmpty(keyword)){
			parameters.put("title", keyword);
			parameters.put("publishContent", keyword);
		}
		searchService.search2("contents",null, parameters, attributes, start, pageSize,pager,sortFiled,sortType);
		List list = pager.getList();
		data.put("contents", list);
		data.put("contentsNum", pager.getFullListSize());
		//活动
		Map<String,Object> parametersActive = new HashMap<String,Object>();
		Map<String,Object> attributesActive = new HashMap<String,Object>();
		String sortFiledActive = "";
		int sortTypeActive = 0;
		if(!StringUtil.isEmpty(keyword)){
			parametersActive.put("name", keyword);
			parametersActive.put("originatorName", keyword);
			parametersActive.put("activeName", keyword);
			parametersActive.put("activeAddress", keyword);
			parametersActive.put("activeDesc", keyword);
		}
		searchService.search2("activities",null, parametersActive, attributesActive, start, pageSize,pager,sortFiledActive,sortTypeActive);
		List activities = pager.getList();
		data.put("activities", activities);
		data.put("activitiesNum", pager.getFullListSize());
		//目的地
		Map<String,Object> parametersDestination = new HashMap<String,Object>();
		Map<String,Object> attributesDestination = new HashMap<String,Object>();
		String sortFiledDestination = "";
		int sortTypeDestination = 0;
		if(!StringUtil.isEmpty(keyword)){
			parametersDestination.put("name", keyword);
			parametersDestination.put("descM", keyword);
			parametersDestination.put("siteInfomation", keyword);
		}
		searchService.search2("destination",null, parametersDestination, attributesDestination, start, pageSize,pager,sortFiledDestination,sortTypeDestination);
		List destinations = pager.getList();
		data.put("destinations", destinations);
		data.put("destinationsNum", pager.getFullListSize());
		
		
		//System.out.println("  data    "+data);
		model.addAttribute("data", data);
		model.addAttribute("keyword", keyword);
		
		return "search/search_index";
	}
	
	*//**
	 * <p>description:搜索某一类型的数据</p>
	 * @param category 分类ID
	 * @param keyword 关键字
	 * @return ModelAndView
	 * @date 2017-7-6下午4:53:40
	 *//*
	@RequestMapping("/searchNomal")
	public ModelAndView searchNomal(Page pager,String categoryCode,String keyword,Model model) throws UnknownHostException {
		//获取分类ID
		int start = (pager.getPageNo() - 1) * pager.getPageSize();
		int pageSize = pager.getPageSize();
		
		Map<String,String> categories = new HashMap<String,String>();
		Properties p =PropertiesUtil.getProperties(ResourceUtil.getResourceAsStream(EIGHT_GOODSTYPE));
		
		String line = p.getProperty(" goodsType.route", "line");//线路
		String hotel = p.getProperty("goodsType.hotel", "hotel");//酒店
		String car = p.getProperty("goodsType.car", "car");//租车
		String food = p.getProperty("goodsType.food", "food");//美食
		String scene = p.getProperty("goodsType.sight", "scene");//景点
		String natives = p.getProperty("goodsType.local", "native");//特产
		String entertainment = p.getProperty("goodsType.entertain", "entertainment");//娱乐
		
		String line_id = dataService.getRegIdByCode(line);//线路类型ID
		String hotel_id = dataService.getRegIdByCode(hotel);//酒店类型ID
		String car_id = dataService.getRegIdByCode(car);//租车类型ID
		String food_id = dataService.getRegIdByCode(food);//美食类型ID
		String scene_id = dataService.getRegIdByCode(scene);//景点类型ID
		String native_id = dataService.getRegIdByCode(natives);//特产类型ID
		String entertain_id = dataService.getRegIdByCode(entertainment);//娱乐类型ID
		 
		
         
		if(StringUtils.equalsIgnoreCase("route", categoryCode)){
			categories.put(line_id, line);
		}else if(StringUtils.equalsIgnoreCase("sight", categoryCode)){
			categories.put(scene_id, scene);
		}else if(StringUtils.equalsIgnoreCase("hotel", categoryCode)){
			categories.put(hotel_id, hotel);
		}else if(StringUtils.equalsIgnoreCase("local", categoryCode)){
			categories.put(native_id, "natives");
		}else if(StringUtils.equalsIgnoreCase("food", categoryCode)){
			categories.put(food_id, food);
		}else if(StringUtils.equalsIgnoreCase("car", categoryCode)){
			categories.put(car_id, car);
		}else if(StringUtils.equalsIgnoreCase("entertain", categoryCode)){
			categories.put(entertain_id, entertainment);
		}else{
			categories.put(line_id, line);
			categories.put(hotel_id, hotel);
			categories.put(car_id, car);
			categories.put(food_id, food);
			categories.put(scene_id, scene);
			categories.put(native_id, "natives");
			categories.put(entertain_id, entertainment);
		}
		
		Map<String,Object> data = new HashMap<String,Object>();
		for (Map.Entry<String,String> entry : categories.entrySet()) {
			String categoryId = (String) entry.getKey();
			Map<String,Object> parameters = new HashMap<String,Object>();
			Map<String,Object> attributes = new HashMap<String,Object>();
			if(!StringUtil.isEmpty(keyword)){
				parameters.put("name", keyword);
				parameters.put("descM", keyword);
				parameters.put("shopName", keyword);
			}
			String sortFiled = "";
			int sortType = 0;
			searchService.search2("products",categoryId, parameters, attributes, start, pageSize,pager,sortFiled,sortType);
			List list = pager.getList();
			data.put(entry.getValue()+"Num", pager.getFullListSize());
			data.put(entry.getValue(), list);
		}
		
		model.addAttribute("data", data);
		model.addAttribute("keyword", keyword);
		model.addAttribute("menu", categoryCode);
		return toVm("mall/food/food_searchResult");
		
		
	}
	*//**
	 * <p>description:搜索特产</p>
	 * @param category 分类ID
	 * @param keyword 关键字
	 * @return ModelAndView
	 * @date 2017-7-6下午4:54:53
	 *//*
	@RequestMapping("/searchLocal")
	public ModelAndView searchLocal(Page pager,String category,String keyword,Model model) throws UnknownHostException {
		//获取分类ID
		String categoryName = "goodsType."+category;
		String type_id = PropertiesUtil.getValueByPropertyName(ResourceUtil.getResourceAsStream(EIGHT_GOODSTYPE),categoryName);
		Map<String,Object> parameters = new HashMap<String,Object>();
		Map<String,Object> attributes = new HashMap<String,Object>();
		int start = (pager.getPageNo() - 1) * pager.getPageSize();
		int pageSize = pager.getPageSize();
		if(StringUtil.isNotBlank(keyword)){
			parameters.put("name", keyword);
			parameters.put("descM", keyword);
		}
		
		String sortFiled = "";
		int sortType = 0;
		searchService.search2("products",category, parameters, attributes, start, pageSize,pager,sortFiled,sortType);
		model.addAttribute("pager", pager);
		model.addAttribute("keyword", keyword);
		model.addAttribute("menu", category);
		getDesitinations(model);
		return toVm("mall/local/local_searchResult");
		
		
	}
	
	*//**
	 * <p>description:按扩展属性查询</p>
	 * @param categoryId 分类ID
	 * @param keyword 关键字
	 * @param queryAtt 属性
	 * @param sortFiled 排序字段
	 * @param sortType 排序类型
	 * @return String
	 * @date 2017-7-6下午4:55:34
	 *//*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/searchWithAtt",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String searchWithAtt(@FormModel("pager")Page pager,String categoryId,String keyword,String queryAtt,String sortFiled,int sortType) throws UnknownHostException {
		String categoryName = "googsType."+categoryId;
		String type_id = PropertiesUtil.getValueByPropertyName(ResourceUtil.getResourceAsStream(EIGHT_GOODSTYPE),categoryName);
		int start = (pager.getPageNo() - 1) * pager.getPageSize();
		int pageSize = pager.getPageSize();
		Map attributesMap = (Map)JSON.parse(queryAtt);  
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(keyword)){
			parameters.put("name", keyword);
			parameters.put("descM", keyword);
		}
		searchService.search2("products",type_id, parameters, attributesMap, start, pageSize,pager,sortFiled,sortType);
		return JSON.toJSONString(pager.getList());
		
	}
	
	*//**
	 * <p>description:搜索游记数据</p>
	 * @param keyword 关键字
	 * @return ModelAndView
	 * @date 2017-7-6下午4:57:21
	 *//*
	@RequestMapping(value="/searchContent",produces="text/html;charset=UTF-8")
	public ModelAndView searchContent(@FormModel("pager")Page pager,String keyword,Model model,String orderType) throws UnknownHostException {
		Map<String,Object> parameters = new HashMap<String,Object>();
		Map<String,Object> attributes = new HashMap<String,Object>();
		int start = (pager.getPageNo() - 1) * pager.getPageSize();
		int pageSize = pager.getPageSize();
		
		if(!StringUtil.isEmpty(keyword)){
			parameters.put("title", keyword);
			parameters.put("publishContent", keyword);
		}
		String sortFiled = "";
		int sortType = 2;
		if("hot".equals(orderType)){
			sortFiled = "browsingNum";
		}else if("new".equals(orderType)){
			sortFiled = "publishedTime";
		}else if("playTour".equals(orderType)){
			sortFiled = "forwardNum";
		}
		searchService.search2("contents",null, parameters, attributes, start, pageSize,pager,sortFiled,sortType);
		model.addAttribute("pager", pager);
		model.addAttribute("keyword", keyword);
		return toVm("main/travel/travel_searchResult");
		
		
	}
	*//**
	 * <p>description:搜索活动</p>
	 * @param keyword 关键字
	 * @date 2017-7-6下午4:58:38
	 *//*
	@RequestMapping(value="/searchActivity")
	public ModelAndView searchActivity(@FormModel("pager")Page pager,String keyword,Model model) throws UnknownHostException {
		Map<String,Object> parameters = new HashMap<String,Object>();
		Map<String,Object> attributes = new HashMap<String,Object>();
		int start = (pager.getPageNo() - 1) * pager.getPageSize();
		int pageSize = pager.getPageSize();
		if(!StringUtil.isEmpty(keyword)){
		parameters.put("name", keyword);
		parameters.put("activeName", keyword);
		parameters.put("routing", keyword);
		}
		String sortFiled = "";
		int sortType = 0;
		searchService.search2("activity",null, parameters, attributes, start, pageSize,pager,sortFiled,sortType);
		System.out.println("   data:  "+JSON.toJSONString(pager.getList()));
		model.addAttribute("pager", pager);
		model.addAttribute("keyword", keyword);
		return toVm("mall/hotel/hotel_search");
		
		
	}
	
	*//**
	 * <p>description:向页面 放入 地区查询数据</p>
	 * @param 
	 * @return void
	 * @author Wen Yugang
	 * @date 2017-4-15下午12:40:01
	 *//*
	@SuppressWarnings("rawtypes")
	public void getDesitinations(Model model){
		List<Map<String,Object>> destinations = destinationService.selectDistinations(new HashMap());
		model.addAttribute("destinations", destinations);
	}
*/
	
	/**
	 * <p>description:按扩展属性查询</p>
	 * @param categoryId 分类ID
	 * @param keyword 关键字
	 * @param queryAtt 属性
	 * @param sortFiled 排序字段
	 * @param sortType 排序类型
	 * @return String
	 * @date 2017-7-6下午4:55:34
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/search",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String search(@FormModel("pager")Page pager,String keyword,String starLevel,Double lat,Double lon,Date checkinDate,Date leaveDate,Integer miniPrice,Integer maxPrice) throws UnknownHostException {
		HotelResult result = new HotelResult();
		if(lat==null||lon==null){
			System.out.println("lat="+lat+"lon="+lon);
			result.setData(pager.getList());
			result.setMsg("定位错误");
			result.setState(0);
			return JSON.toJSONString(result);
		}
		String type_id = "hotels";
		int start = (pager.getPageNo() - 1) * pager.getPageSize();
		int pageSize = pager.getPageSize();
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(keyword)){
			parameters.put("name", keyword);
			parameters.put("address", keyword);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
		String checkinTime = sdf.format(checkinDate);
		String leaveTime = sdf.format(leaveDate);
			//es搜索
			//searchService.search(type_id, parameters, starLevel,lat,lon, start, pageSize,pager,miniPrice,maxPrice,checkinTime,leaveTime);
		
			//mysql查询
		try {
			searchService.search1(parameters, starLevel,lat,lon, start, pageSize,pager,miniPrice,maxPrice,checkinDate,leaveDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.setData(pager.getList());
		result.setMsg("success");
		result.setState(1);
		result.setPageIndex(pager.getPageNumber());
		result.setPageSize(pager.getPageSize());
		result.setPageSize(pageSize);
		return JSON.toJSONString(result);
		
	}
}
