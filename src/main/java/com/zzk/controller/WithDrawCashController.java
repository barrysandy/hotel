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
import com.zzk.entity.WithDrawCash;
import com.zzk.service.WithDrawCashService;

/**
 * 提现信息表的实体类
 * @name: WithDrawCashController
 * @author: wangpeng
 * @date: 2018-04-12 10:41
 */
@Controller
@RequestMapping(value="/withDrawCash")
public class WithDrawCashController extends BaseController {

	@Resource
	private WithDrawCashService withDrawCashService;
	
	/**
	 * 提现信息表的实体类分页查询
	 * @param pager 分页
	 * @param model model
	 * @param search 搜索关键词
	 * @return view
	 * @author wangpeng
	 * @date 2018-04-12 10:41
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result<Object> list(String sellerId,Page<Object> pager,String startTime,String endTime, String billState) {
		if(StringUtils.isBlank(sellerId)){
			return new Result<>(0,"error","商户标志不能为空");
		}
		if(pager == null){
			pager = new Page<>();
		}
		Map<String,Object> map = new HashMap<>();
		map.put("sellerId", sellerId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("billState", billState);
		return withDrawCashService.selectByPage(pager,map);
	}
	
	/**
	 * 提现信息表的实体类编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author wangpeng
	 * @date 2018-04-12 10:41
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			WithDrawCash rule = new WithDrawCash();
			if(StringUtils.isNotBlank(id)){
				rule = withDrawCashService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("withDrawCash/withDrawCash_edit");
	}
	
	/**
	 * 提现信息表的实体类预览
	 * @param id
	 * @param model
	 * @return view
	 * @author wangpeng
	 * @date 2018-04-12 10:41
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		WithDrawCash rule = new WithDrawCash();
		if(StringUtils.isNotBlank(id)){
			rule = withDrawCashService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("withDrawCash/withDrawCash_view");
	}
	
	/**
	 * 提现信息表的实体类保存操作
	 * @param bean 实体类
	 * @return String
	 * @author wangpeng
	 * @date 2018-04-12 10:41
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(WithDrawCash bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					WithDrawCash rule = new WithDrawCash();
					if(StringUtils.isNotBlank(id)){
						rule = withDrawCashService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					withDrawCashService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					withDrawCashService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 提现信息表的实体类删除
	 * @param id 主键ID
	 * @return String
	 * @author wangpeng
	 * @date 2018-04-12 10:41
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			withDrawCashService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
}
