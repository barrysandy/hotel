package com.zzk.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;
import com.zzk.util.Result;
import com.zzk.common.PlatConstants;
import com.zzk.common.FormModel;
import com.zzk.util.DateUtils;
import com.zzk.util.Exceptions;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
//import com.zzk.util.UploadUtil;
import com.zzk.entity.Dict;
import com.zzk.entity.Hotel;
import com.zzk.entity.PriceRule;
import com.zzk.entity.RoomType;
import com.zzk.entity.StockRule;
import com.zzk.service.CacheService;
import com.zzk.service.DictService;
import com.zzk.service.RoomTypeService;
import com.zzk.service.StockRuleService;
import com.zzk.controller.BaseController;

/**
 * <p>description：房型信息</p>
 * @name：RoomTypeController
 * @author：sty
 * @date：2017-11-02 10:37
 */
@Controller
@RequestMapping(value = "/roomType")
public class RoomTypeController extends BaseController {

	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private StockRuleService stockRuleService;
	@Autowired
	private DictService dictService;
	private List<Dict> dictList;
	
	/**
	 * 房型信息分页查询
	 * @param page
	 * @param model
	 * @param id
	 * @return
	 * @author sty
	 * @date 2017-11-02 10:37
	 */
	/*@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<RoomType> list = roomTypeService.selectByPage(map);
		int totalNum = roomTypeService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("roomType/roomType_list");
	}*/
	
	/**
	 * 房型信息编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:37
	 */
	/*@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			RoomType rule = new RoomType();
			if(StringUtils.isNotBlank(id)){
				rule = roomTypeService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("roomType/roomType_edit");
	}*/
	/**
	 * 房型信息预览
	* @param id
	* @param model
	* @return
	* @author sty
	* @date 2017-11-02 10:37
	 */
	@RequestMapping("/toView")
	@ResponseBody
	public String toView(String id){
		Result<RoomType> result = new Result<RoomType>();
		if(StringUtils.isNotBlank(id)){
			RoomType rule = roomTypeService.selectByPrimaryKey(id);
			result.setData(rule);
			result.setState(1);
			result.setMsg("success");
		}else{
			result.setState(0);
			result.setMsg("error");
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 房型信息保存操作
	* @param bean
	* @return
	* @author sty
	* @date 2017-11-02 10:37
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	public Result<RoomType> saveOrUpdate(@RequestBody RoomType bean){
		
		
		Map<String,Object> map =  bean.getMap();
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					RoomType rule = new RoomType();
					rule = roomTypeService.selectByPrimaryKey(id);
					bean.setHotelId(rule.getHotelId());
					bean.setCreator(rule.getCreator());
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStates(rule.getStates());
					bean.setStatus(1);
					roomTypeService.update(bean);
				}else{
					String firstStock = (String)map.get("firstStock");
					String rtype = (String)map.get("rtype");
					String weeks = (String)map.get("weeks");
					List<Map<String,Double>> times = (List<Map<String,Double>>)map.get("times");
					String stock = (String)map.get("stock");
					String totalStock = (String)map.get("totalStock");
					boolean isShow = (boolean)map.get("isShow");
					if(StringUtils.isBlank(bean.getHotelId())){
						return new Result<RoomType>(0,"hotelId不可为空");
					}
					if(StringUtils.isEmpty(firstStock)){
						return new Result<RoomType>(0,"预留房量不能为空");
					}
					if(!StringUtils.isNumeric(firstStock)||Integer.parseInt(firstStock)<0){
						return new Result<RoomType>(0,"预留房量为非法字符");
					}
					if(!StringUtils.isEmpty(totalStock)){
						if(!StringUtils.isNumeric(totalStock)||Integer.parseInt(totalStock)<0){
							return new Result<RoomType>(0,"总房量为非法字符");
						}
					}
					if(isShow){
						if(StringUtils.isEmpty(stock)){
							return new Result<RoomType>(0,"预留房量不能为空");
						}
						if(!StringUtils.isNumeric(stock)||Integer.parseInt(stock)<0){
							return new Result<RoomType>(0,"预留房量为非法字符");
						}
						if(StringUtils.isEmpty(weeks)){
							return new Result<RoomType>(0,"星期不能为空");
						}
						if("3".equals(rtype)){
							if(times.size()<=0){
								return new Result<RoomType>(0,"请选择时间");
							}
						}
					}
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setStates(PlatConstants.ROOMTYPE_NORMAL);
					//bean.setStates(PlatConstants.ROOMTYPE_REVIEW);
					bean.setId(u.toString());
					bean.setInitStock(firstStock);
					bean.setTotalStock(totalStock);
					bean.setCreateTime(new Date());
					if(bean.getCooperateType()==PlatConstants.COOPERATE_BROKERAGE){
						if(StringUtils.isEmpty(bean.getBrokerage())){
							return new Result<RoomType>(0,"请输入佣金");
						}else{
							bean.setBrokerageProportion("");
						}
					}else if(bean.getCooperateType()==PlatConstants.COOPERATE_PROPORTION){
						if(StringUtils.isEmpty(bean.getBrokerageProportion())){
							return new Result<RoomType>(0,"请输入佣金率");
						}else{
							BigDecimal bd = new BigDecimal(bean.getBrokerageProportion()).divide(new BigDecimal(100));
							bean.setBrokerageProportion(bd.toString());
							bean.setBrokerage("");
						}
					}
					roomTypeService.insert(bean);
					//新增初始库存规则
					StockRule stockRule = new StockRule();
					Calendar c = Calendar.getInstance();
					//-10秒，查询时与高级设置区分开
					c.add(Calendar.SECOND,-10);
					stockRule.setCreateTime(c.getTime());
					stockRule.setUpdateTime(c.getTime());
					stockRule.setCreator(bean.getCreator());
					stockRule.setStatus(1);
					stockRule.setType(2);
					stockRule.setIsInit(1);
					stockRule.setWeekStart(1234567);
					stockRule.setStock(firstStock);
					stockRule.setId(UUID.randomUUID().toString());
					stockRule.setRoomtypeId(bean.getId());
					stockRule.setStartTime(DateUtils.getStartTime());
					c = Calendar.getInstance();
					c.add(Calendar.YEAR,10);
					stockRule.setEndTime(c.getTime());
					stockRuleService.insert(stockRule);
					//新增高级规则
					if(isShow){
						StockRule stockRule1 = new StockRule();
						stockRule1.setCreateTime(new Date());
						stockRule1.setUpdateTime(new Date());
						stockRule1.setCreator(bean.getCreator());
						stockRule1.setStatus(1);
						stockRule1.setType(2);
						stockRule1.setStock(stock);
						stockRule1.setWeekStart(Integer.parseInt(weeks));
						stockRule1.setRoomtypeId(bean.getId());
						if("1".equals(rtype)){
							stockRule1.setStartTime(DateUtils.getStartTime());
							c = Calendar.getInstance();
							c.add(Calendar.YEAR,10);
							stockRule1.setEndTime(c.getTime());
							stockRule1.setId(UUID.randomUUID().toString());
							stockRuleService.insert(stockRule1);
						}else if("3".equals(rtype)){
							for(Map<String, Double> time :times){
								stockRule1.setStartTime(new Date(Math.round(time.get("start"))));
								stockRule1.setEndTime(new Date(Math.round(time.get("end"))));
								stockRule1.setId(UUID.randomUUID().toString());
								stockRuleService.insert(stockRule1);
							}
						}else{
							return new Result<RoomType>(0,"参数有误失败");
						}
					}	
				}
				
			}
		}catch(Exception e){
			System.err.println(e.toString());
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new Result<RoomType>(2,"保存失败");
		}
		return new Result<RoomType>(1,"保存成功");
	}
	/**
	 * 房型信息删除
	* @param id
	* @return
	* @return
	* @author sty
	* @date 2017-11-02 10:37
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Result<RoomType> del(String id){
		try{
			roomTypeService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new Result<RoomType>(2,"删除失败");
		}
		return new Result<RoomType>(1,"删除成功");
	}
	/**
	 * 获取同一酒店下所有房型
	* @param hotelId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-09 10:37
	 */
	@RequestMapping("/getRoomType")
	@ResponseBody
	public String getRoomType(String hotelId){
		Result<List<RoomType>> result = new Result<List<RoomType>>();
		List<RoomType> roomTypeList = new ArrayList<RoomType>();
		List<RoomType> list = roomTypeService.selectByHotelId(hotelId);
		for(RoomType bean:list){
			bean.setAddBed(CacheService.getLabel("addBed",bean.getAddBed()));
			bean.setBedType(CacheService.getLabel("bedType",bean.getBedType()));
			bean.setStatesStr(CacheService.getLabel("roomTypeState",String.valueOf(bean.getStates())));
			roomTypeList.add(bean);
		}
		result.setData(roomTypeList);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	/**
	 * 获房型下选项列表
	* @param 
	* @return
	* @return
	* @author huashuwen
	* @date 2017-12-01 10:37
	 */
	@RequestMapping("/editAlbum")
	@ResponseBody
	public String editAlbum(String roomTypeId,String imgs,String updater){
		Result result = new Result<>();
		RoomType bean = roomTypeService.selectByPrimaryKey(roomTypeId);
		if(!StringUtils.isEmpty(bean)){
			bean.setImgs(imgs);
			bean.setUpdater(updater);
			bean.setUpdateTime(new Date());
			roomTypeService.update(bean);
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
	 * 修改封面
	* @param 
	* @return
	* @return
	* @author huashuwen
	* @date 2017-12-01 10:37
	 */
	@RequestMapping("/changeCover")
	@ResponseBody
	public Result changeCover(String id,String url){
		RoomType roomType = roomTypeService.selectByPrimaryKey(id);
		if(!StringUtils.isEmpty(roomType)){
			roomType.setUpdateTime(new Date());
			roomType.setCoverImg(url);
			roomTypeService.update(roomType);
		}else{
			return new Result(0,"修改失败");
		}
		return new Result(1,"修改成功");
	}
	/**
	 * 删除图片
	* @param 
	* @return
	* @return
	* @author huashuwen
	* @date 2017-12-01 10:37
	 */
	@RequestMapping("/delPicture")
	@ResponseBody
	public Result delPicture(String id,String url){
		RoomType roomType = roomTypeService.selectByPrimaryKey(id);
		String imgs = "";
		if(!StringUtils.isEmpty(roomType)){
			imgs = roomType.getImgs();
			imgs = imgs.replace(","+url,"");
			imgs = imgs.replace(url+",","");
			imgs = imgs.replace(url,"");
			roomType.setUpdateTime(new Date());
			roomType.setImgs(imgs);
			roomTypeService.update(roomType);
			//UploadUtils.removeFileById(url);
		}else{
			return new Result(0,"删除失败");
		}
		Result result = new Result(1,"删除成功");
		result.setData(imgs);
		return result;
	}
	
	/**
	 * 获房型下选项列表
	* @param 
	* @return
	* @return
	* @author huashuwen
	* @date 2017-12-01 10:37
	 */
	@RequestMapping("/getSelectList")
	@ResponseBody
	public String getSelectList(){
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		List<Dict> widebandList = new ArrayList<Dict>();
		List<Dict> freeWifiList= new ArrayList<Dict>();
		List<Dict> bathroomList = new ArrayList<Dict>();
		List<Dict> isWindowList = new ArrayList<Dict>();
		List<Dict> bedTypeList = new ArrayList<Dict>();
		List<Dict> roomTypeStateTypeList = new ArrayList<Dict>();
		List<Dict> addBedList = new ArrayList<Dict>();
		List<Dict> smokeList = new ArrayList<Dict>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String type= "";
		if(dictList==null){
			dictList = CacheService.getAllDict();
		}
		for(Dict dict:dictList){
			type = dict.getType();
			if("wideband".equals(type)){
				widebandList.add(dict);
			}
			if("freeWifi".equals(type)){
				freeWifiList.add(dict);
			}
			if("bathroom".equals(type)){
				bathroomList.add(dict);
			}
			if("isWindow".equals(type)){
				isWindowList.add(dict);
			}
			if("bedType".equals(type)){
				bedTypeList.add(dict);
			}
			if("addBed".equals(type)){
				addBedList.add(dict);
			}
			if("smoke".equals(type)){
				smokeList.add(dict);
			}
			if("roomTypeState".equals(type)){
				roomTypeStateTypeList.add(dict);
			}
		}
		resultMap.put("widebandList", widebandList);
		resultMap.put("freeWifiList", freeWifiList);
		resultMap.put("bathroomList", bathroomList);
		resultMap.put("isWindowList", isWindowList);
		resultMap.put("bedTypeList", bedTypeList);
		resultMap.put("addBedList", addBedList);
		resultMap.put("smokeList", smokeList);
		resultMap.put("roomTypeStateTypeList", roomTypeStateTypeList);
		System.err.println(JSON.toJSONString(resultMap));
		result.setData(resultMap);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	
}
