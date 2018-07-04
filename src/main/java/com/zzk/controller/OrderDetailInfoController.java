package com.zzk.controller;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.entity.OrderDetailInfo;
import com.zzk.service.OrderDetailInfoService;
import com.zzk.util.Result;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;

/**
 * 订单详情表
 * @name: OrderDetailInfoController
 * @author: Kun
 * @date: 2018-03-06 11:01
 */
@RequestMapping(value = "/orderDetailInfo")
@RestController
@EnableAutoConfiguration
public class OrderDetailInfoController extends BaseController {

	@Resource
	private OrderDetailInfoService orderDetailInfoService;
	
	/**
	 * 订单详情表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-03-06 11:01
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(OrderDetailInfo bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					OrderDetailInfo rule = new OrderDetailInfo();
					if(StringUtils.isNotBlank(id)){
						rule = orderDetailInfoService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					orderDetailInfoService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					orderDetailInfoService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 订单详情表删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-03-06 11:01
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		try{
			orderDetailInfoService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<>(0,"fail","内部异常",e);
		}
		return new Result(1,"success","操作成功");
	}
	
}
