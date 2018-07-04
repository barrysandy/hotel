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
import com.zzk.entity.Dict;
import com.zzk.service.DictService;

/**
 * 字典信息表的实体类
 * @name: DictController
 * @author: wangpeng
 * @date: 2018-04-10 11:46
 */
@Controller
@RequestMapping(value = "/dict")
public class DictController extends BaseController {

	@Resource
	private DictService dictService;
	
	/**
	 * 字典信息表的实体类分页查询
	 * @param pager 分页
	 * @param model model
	 * @param search 搜索关键词
	 * @return view
	 * @author wangpeng
	 * @date 2018-04-10 11:46
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page pager, Model model, String search) {
		Map<String,Object> map = new HashMap<>(4);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<Dict> list = dictService.selectByPage(map);
		int totalNum = dictService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("dict/dict_list");
	}
	
	/**
	 * 字典信息表的实体类编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author wangpeng
	 * @date 2018-04-10 11:46
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			Dict rule = new Dict();
			if(StringUtils.isNotBlank(id)){
				rule = dictService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("dict/dict_edit");
	}
	
	/**
	 * 字典信息表的实体类预览
	 * @param id
	 * @param model
	 * @return view
	 * @author wangpeng
	 * @date 2018-04-10 11:46
	 */
	@RequestMapping("/toView")
	@ResponseBody
	public Result<Object> toView(String id){
		Dict rule = new Dict();
		if(StringUtils.isNotBlank(id)){
			rule = dictService.selectByPrimaryKey(id);
		}
		return new Result<Object>(1, "success", "请求成功", rule);
	}
	
	/**
	 * 字典信息表的实体类保存操作
	 * @param bean 实体类
	 * @return String
	 * @author wangpeng
	 * @date 2018-04-10 11:46
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(Dict bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					Dict rule = new Dict();
					if(StringUtils.isNotBlank(id)){
						rule = dictService.selectByPrimaryKey(id);
					}
					bean.setCreateDate(rule.getCreateDate());
					bean.setUpdateDate(new Date());
					bean.setStatus(1);
					dictService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateDate(new Date());
					dictService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 字典信息表的实体类删除
	 * @param id 主键ID
	 * @return String
	 * @author wangpeng
	 * @date 2018-04-10 11:46
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			dictService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 
	 * @param dict
	 * @return
	 * @author John
	 * @date： 2018年4月10日 下午3:40:56
	 */
	@ResponseBody
	@RequestMapping("/findList")
	public Result<Object> findListDict(Dict dict){
		if(dict == null){
			return new Result<>(0,"error","无效参数");
		}
		if(StringUtils.isBlank(dict.getType()) && StringUtils.isBlank(dict.getDescription())){
		return new Result<>(0,"error","无效参数");
		}
		return dictService.selectList(dict);
	}
	
}
