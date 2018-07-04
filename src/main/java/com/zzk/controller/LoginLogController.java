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
import com.zzk.entity.LoginLog;
import com.zzk.service.LoginLogService;

/**
 * 待完善旅游要素
 * @name: LoginLogController
 * @author: huashuwen
 * @date: 2018-04-02 14:11
 */
@Controller
@RequestMapping(value = "/loginLog")
public class LoginLogController extends BaseController {

	@Resource
	private LoginLogService loginLogService;
	
	/**
	 * 待完善旅游要素分页查询
	 * @param pager 分页
	 * @param model model
	 * @param search 搜索关键词
	 * @return view
	 * @author huashuwen
	 * @date 2018-04-02 14:11
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(Integer pageSize,Integer pageNumber,String shopId) {
		Map<String,Object> map = new HashMap<>(4);
		map.put("startRow", (pageNumber - 1) * pageSize);
		map.put("pageSize", pageSize);
		map.put("shopId", shopId);
		List<LoginLog> list = loginLogService.selectByPage(map);
		
		map.put("startRow", 0);
		map.put("pageSize", 2);
		map.put("shopId", shopId);
		List<LoginLog> twoLoginList = loginLogService.selectByPage(map);
		System.err.println(twoLoginList.size());
		int totalNum = loginLogService.selectCount(map);
		Map<String,Object> resultMap =new HashMap<String,Object>();
		resultMap.put("totalNum", totalNum);
		resultMap.put("list", list);
		if(twoLoginList.size()>=1){
			resultMap.put("thisTimeLogin", twoLoginList.get(0));
		}
		if(twoLoginList.size()>=2){
			resultMap.put("lastLogin", twoLoginList.get(1));
		}
		return new Result(1,"success","查询成功",resultMap);
	}
	
	/**
	 * 待完善旅游要素编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author huashuwen
	 * @date 2018-04-02 14:11
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			LoginLog rule = new LoginLog();
			if(StringUtils.isNotBlank(id)){
				rule = loginLogService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("loginLog/loginLog_edit");
	}
	
	/**
	 * 待完善旅游要素预览
	 * @param id
	 * @param model
	 * @return view
	 * @author huashuwen
	 * @date 2018-04-02 14:11
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		LoginLog rule = new LoginLog();
		if(StringUtils.isNotBlank(id)){
			rule = loginLogService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("loginLog/loginLog_view");
	}
	
	/**
	 * 待完善旅游要素保存操作
	 * @param bean 实体类
	 * @return String
	 * @author huashuwen
	 * @date 2018-04-02 14:11
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(LoginLog bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					LoginLog rule = new LoginLog();
					if(StringUtils.isNotBlank(id)){
						rule = loginLogService.selectByPrimaryKey(id);
					}
					bean.setStatus(1);
					loginLogService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					loginLogService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 待完善旅游要素删除
	 * @param id 主键ID
	 * @return String
	 * @author huashuwen
	 * @date 2018-04-02 14:11
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			loginLogService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
}
