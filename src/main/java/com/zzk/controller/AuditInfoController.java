package com.zzk.controller;

import java.util.*;

import com.zzk.entity.AuditInfo;
import com.zzk.service.AuditInfoService;
import javax.annotation.Resource;
import com.zzk.util.JsonUtils;
import com.zzk.util.StringUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审核信息表
 * @name: AuditInfoController
 * @author: Kun
 * @date: 2018-03-06 10:14
 */
@RequestMapping(value = "/auditInfo")
@RestController
@EnableAutoConfiguration
public class AuditInfoController extends BaseController {

	@Resource
	private AuditInfoService auditInfoService;

	
	/**
	 * 审核信息表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-03-06 10:14
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(AuditInfo bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					AuditInfo rule = new AuditInfo();
					if(StringUtils.isNotBlank(id)){
						rule = auditInfoService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					auditInfoService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					auditInfoService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 审核信息表删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-03-06 10:14
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			auditInfoService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
}
