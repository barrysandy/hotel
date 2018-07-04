package com.zzk.controller;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zzk.util.HotelResult;
import com.zzk.util.Exceptions;
import com.zzk.entity.SwitchRule;
import com.zzk.service.SwitchRuleService;
import com.zzk.controller.BaseController;

/**
 * <p>description：房型开关</p>
 * @name：SwitchRuleController
 * @author：huashuwen
 * @date：2018-01-05 10:03
 */
@Controller
@RequestMapping(value = "/switchRule")
public class SwitchRuleController extends BaseController {

	@Autowired
	private SwitchRuleService switchRuleService;
	
	/**
	 * 房型开关分页查询
	 * @param page
	 * @param model
	 * @param id
	 * @return
	 * @author huashuwen
	 * @date 2018-01-05 10:03
	 */
	/*@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<SwitchRule> list = switchRuleService.selectByPage(map);
		int totalNum = switchRuleService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("switchRule/switchRule_list");
	}*/
	
	/**
	 * 房型开关编辑页面
	* @param id
	* @param model
	* @return
	* @return
	* @author huashuwen
	* @date 2018-01-05 10:03
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			SwitchRule rule = new SwitchRule();
			if(StringUtils.isNotBlank(id)){
				rule = switchRuleService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
		}
		return toVm("switchRule/switchRule_edit");
	}
	/**
	 * 房型开关预览
	* @param id
	* @param model
	* @return
	* @author huashuwen
	* @date 2018-01-05 10:03
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		SwitchRule rule = new SwitchRule();
		if(StringUtils.isNotBlank(id)){
			rule = switchRuleService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("switchRule/switchRule_view");
	}
	/**
	 * 房型开关保存操作
	* @param bean
	* @return
	* @author huashuwen
	* @date 2018-01-05 10:03
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public HotelResult<SwitchRule> saveOrUpdate(SwitchRule bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					SwitchRule rule = new SwitchRule();
					if(StringUtils.isNotBlank(id)){
						rule = switchRuleService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					switchRuleService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					switchRuleService.insert(bean);
				}
			}
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<SwitchRule>(2,"error");
		}
		return new HotelResult<SwitchRule>(1,"success");
	}
	/**
	 * 房型开关删除
	* @param id
	* @return
	* @return
	* @author huashuwen
	* @date 2018-01-05 10:03
	 */
	@RequestMapping("/del")
	@ResponseBody
	public HotelResult<SwitchRule> del(String id){
		try{
			switchRuleService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<SwitchRule>(2,"error");
		}
		return new HotelResult<SwitchRule>(1,"success");
	}
	
}
