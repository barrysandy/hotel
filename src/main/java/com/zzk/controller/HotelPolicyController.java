package com.zzk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zzk.util.HotelResult;
import com.zzk.util.Exceptions;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.entity.Dict;
import com.zzk.entity.HotelPolicy;
import com.zzk.service.CacheService;
import com.zzk.service.DictService;
import com.zzk.service.HotelPolicyService;
import com.zzk.controller.BaseController;

/**
 * <p>description：酒店政策信息</p>
 * @name：HotelPolicyController
 * @author：huashuwen
 * @date：2017-11-17 11:46
 */
@Controller
@RequestMapping(value = "/hotelPolicy")
public class HotelPolicyController extends BaseController {

	@Autowired
	private HotelPolicyService hotelPolicyService;
	@Autowired
	private DictService dictService;
	private static List<Dict> dictList;
	
	/**
	 * 酒店政策信息分页查询
	 * @param page
	 * @param model
	 * @param id
	 * @return
	 * @author huashuwen
	 * @date 2017-11-17 11:46
	 */
	/*@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<HotelPolicy> list = hotelPolicyService.selectByPage(map);
		int totalNum = hotelPolicyService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("hotelPolicy/hotelPolicy_list");
	}*/
	
	/**
	 * 酒店政策信息编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-17 11:46
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			HotelPolicy rule = new HotelPolicy();
			if(StringUtils.isNotBlank(id)){
				rule = hotelPolicyService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("hotelPolicy/hotelPolicy_edit");
	}
	/**
	 * 酒店政策信息预览
	* @param id
	* @param model
	* @return
	* @author huashuwen
	* @date 2017-11-17 11:46
	 */
	@RequestMapping("/toView")
	@ResponseBody
	public String toView(String hotelId){
		HotelResult<HotelPolicy> result = new HotelResult<HotelPolicy>();
		HotelPolicy rule = new HotelPolicy();
		if(StringUtils.isNotBlank(hotelId)){
			rule = hotelPolicyService.selectByHotelId(hotelId);
		}
		if(StringUtils.isEmpty(rule)){
			result.setMsg("error");
			result.setMessage("获取失败");
			result.setState(0);
			return JSON.toJSONString(result);
		}
		result.setData(rule);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	/**
	 * 酒店政策信息保存操作
	* @param bean
	* @return
	* @author huashuwen
	* @date 2017-11-17 11:46
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public HotelResult<HotelPolicy> saveOrUpdate(@RequestBody HotelPolicy bean){
		try{
			if(bean!=null){
				String hotelId = bean.getHotelId();
				if(StringUtils.isNotBlank(hotelId)){
					HotelPolicy rule = new HotelPolicy();
					rule = hotelPolicyService.selectByHotelId(hotelId);
					if(StringUtils.isEmpty(rule)){
						UUID u = UUID.randomUUID();
						bean.setStatus(1);
						bean.setId(u.toString());
						bean.setCreateDate(new Date());
						hotelPolicyService.insert(bean);
					}else{
						bean.setId(rule.getId());
						bean.setCreator(rule.getCreator());
						bean.setCreateDate(rule.getCreateDate());
						bean.setUpdateDate(new Date());
						bean.setStatus(1);
						hotelPolicyService.update(bean);
					}
				}
			}
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<HotelPolicy>(2,"error");
		}
		return new HotelResult<HotelPolicy>(1,"success");
	}
	/**
	 * 酒店政策信息删除
	* @param id
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-17 11:46
	 */
	@RequestMapping("/del")
	@ResponseBody
	public HotelResult<HotelPolicy> del(String id){
		try{
			hotelPolicyService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<HotelPolicy>(2,"error");
		}
		return new HotelResult<HotelPolicy>(1,"success");
	}
	/**
	 * 获取酒店政策选项
	* @param 
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-17 11:46
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public String getList(){
		HotelResult<Map<String,Object>> result = new HotelResult<Map<String,Object>>();
		List<Dict> breakfastList = new ArrayList<Dict>();
		List<Dict> petBringList= new ArrayList<Dict>();
		List<Dict> creditCardList = new ArrayList<Dict>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String type= "";
		if(dictList==null){
			dictList = CacheService.getAllDict();
		}
		for(Dict dict:dictList){
			type = dict.getType();
			if("breakfast".equals(type)){
				breakfastList.add(dict);
			}
			if("petBring".equals(type)){
				petBringList.add(dict);
			}
			if("creditCard".equals(type)){
				creditCardList.add(dict);
			}
		}
		resultMap.put("breakfastList", breakfastList);
		resultMap.put("petBringList", petBringList);
		resultMap.put("creditCardList", creditCardList);
		result.setData(resultMap);
		result.setMsg("success");
		result.setState(1);
		return JSON.toJSONString(result);
	}
	
	
	
}
