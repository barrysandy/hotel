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
import com.zzk.entity.UnfinishedTripElement;
import com.zzk.service.UnfinishedTripElementService;

/**
 * 待完善旅游要素
 * @name: UnfinishedTripElementController
 * @author: huashuwen
 * @date: 2018-03-10 11:02
 */
@Controller
@RequestMapping(value = "/unfinishedTripElement")
public class UnfinishedTripElementController extends BaseController {

	@Resource
	private UnfinishedTripElementService unfinishedTripElementService;
	
	/**
	 * 待完善旅游要素删除
	 * @param id 主键ID
	 * @return String
	 * @author huashuwen
	 * @date 2018-03-10 11:02
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		unfinishedTripElementService.reletion("bed44dbe-2d8d-42cf-ab7b-719b3cd2846d", "10003");
		/*try{
			unfinishedTripElementService.delete(id);
			
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}*/
		return JsonUtils.turnJson(true,"success",null);
	}
	
}
