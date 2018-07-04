package com.zzk.controller;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.entity.TripElementType;
import com.zzk.service.TripElementTypeService;

/**
 * 旅游要素类型
 * @name: TripElementTypeController
 * @author: huashuwen
 * @date: 2018-03-10 11:01
 */
@Controller
@RequestMapping(value = "/tripElementType")
public class TripElementTypeController extends BaseController {

	@Resource
	private TripElementTypeService tripElementTypeService;
	
	/**
	 * 旅游要素类型下拉选
	 * @param 
	 * @return list
	 * @author huashuwen
	 * @date 2018-03-10 11:01
	 */
	@RequestMapping("/getType")
	@ResponseBody
	public String getType(){
		List<TripElementType> typeList= tripElementTypeService.selectType();
		
		return JsonUtils.turnJson(true,"success",typeList);
	}
	
}
