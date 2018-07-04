package com.zzk.controller;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.zzk.entity.OperateLog;
import com.zzk.service.OperateLogService;

/**
 * 操作日志
 * @name: OperateLogController
 * @author: huashuwen
 * @date: 2018-04-02 16:31
 */
@Controller
@RequestMapping(value = "/operateLog")
public class OperateLogController extends BaseController {

	@Resource
	private OperateLogService operateLogService;
	
	/**
	 * 操作日志分页查询
	 * @param pager 分页
	 * @param model model
	 * @param search 搜索关键词
	 * @return view
	 * @author huashuwen
	 * @date 2018-04-02 16:31
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(String shopId,Integer pageSize,Integer pageNumber) {
		Map<String,Object> map = new HashMap<>();
		map.put("startRow", (pageNumber-1) * pageSize);
		map.put("pageSize", pageSize);
		map.put("shopId", shopId);
		List<OperateLog> list = operateLogService.selectByPage(map);
		int totalNum = operateLogService.selectCount(map);
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("list", list);
		resultMap.put("totalNum", totalNum);
		return new Result(1,"success","查询成功",resultMap);
	}
	
	/**
	 * 操作日志编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author huashuwen
	 * @date 2018-04-02 16:31
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			OperateLog rule = new OperateLog();
			if(StringUtils.isNotBlank(id)){
				rule = operateLogService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("operateLog/operateLog_edit");
	}
	
	/**
	 * 操作日志预览
	 * @param id
	 * @param model
	 * @return view
	 * @author huashuwen
	 * @date 2018-04-02 16:31
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		OperateLog rule = new OperateLog();
		if(StringUtils.isNotBlank(id)){
			rule = operateLogService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("operateLog/operateLog_view");
	}
	
	/**
	 * 操作日志保存操作
	 * @param bean 实体类
	 * @return String
	 * @author huashuwen
	 * @date 2018-04-02 16:31
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(OperateLog bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					OperateLog rule = new OperateLog();
					if(StringUtils.isNotBlank(id)){
						rule = operateLogService.selectByPrimaryKey(id);
					}
					bean.setStatus(1);
					operateLogService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					operateLogService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 操作日志删除
	 * @param id 主键ID
	 * @return String
	 * @author huashuwen
	 * @date 2018-04-02 16:31
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			operateLogService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
}
