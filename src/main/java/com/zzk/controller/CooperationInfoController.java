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
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.zzk.entity.CooperationInfo;
import com.zzk.service.CooperationInfoService;

/**
 * 合作详情
 * @name: CooperationInfoController
 * @author: wangpeng
 * @date: 2018-03-12 18:19
 */
@Controller
@RequestMapping(value = "/cooperationInfo")
public class CooperationInfoController extends BaseController {

	@Resource
	private CooperationInfoService cooperationInfoService;
	

	/**
	 * 用户表预览
	 * @param businessId 商家ID
	 * @return view
	 * @author wangpeng
	 * @date 2018-03-12 18:19
	 */
	@RequestMapping("/toViewByBusinessId")
	@ResponseBody
	public Result<CooperationInfo> toViewByBusinessId(String businessId){
		CooperationInfo rule = new CooperationInfo();
		if(StringUtils.isNotBlank(businessId)){
			rule = cooperationInfoService.selectByBusinessId(businessId);
			if(rule!=null){
				return new Result<CooperationInfo>(1,"success","获取成功",rule);
			}
		}
		
		return new Result<CooperationInfo>(0,"error","获取失败",null);
	}
	
	/**
	 * 用户表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author wangpeng
	 * @date 2018-03-12 18:19
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(CooperationInfo bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					CooperationInfo rule = new CooperationInfo();
					if(StringUtils.isNotBlank(id)){
						rule = cooperationInfoService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					if(bean.getCommRate()==null){
						bean.setCommRate(new Double(0));
					}
					bean.setStatus(1);
					cooperationInfoService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					if(bean.getCommRate()==null){
						bean.setCommRate(new Double(0));
					}
					cooperationInfoService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 用户表删除
	 * @param id 主键ID
	 * @return String
	 * @author wangpeng
	 * @date 2018-03-12 18:19
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			cooperationInfoService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
}
