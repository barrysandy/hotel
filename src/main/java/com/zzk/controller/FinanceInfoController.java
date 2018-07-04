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
import com.zzk.entity.FinanceInfo;
import com.zzk.service.FinanceInfoService;

/**
 * 商家账户表
 * @name: FinanceInfoController
 * @author: wangpeng
 * @date: 2018-03-10 15:34
 */
@Controller
@RequestMapping(value = "/financeInfo")
public class FinanceInfoController extends BaseController {

	@Resource
	private FinanceInfoService financeInfoService;

	/**
	 * 根据条件分页查询
	 * @param page
	 * @param info
	 * @return
	 * @author John
	 * @date： 2018年3月10日 下午3:45:13
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Result<Object> queryByMap(Page page ,String sellerId,String billNo,String billStatus,String startTime,String  endTime){
		Map<String,Object> map = new HashMap<>();
		if(StringUtils.isBlank(sellerId)){
			return new Result<>(0, "error", "商户ID不能为空", null);
		}
		map.put("sellerId", sellerId);
		map.put("billNo", billNo);
		map.put("billStatus", billStatus);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return financeInfoService.selectByMap(page,map);
	}
	/**
	 * 账单详情查询
	 * @param sellerId
	 * @param billNo
	 * @return
	 * @author John
	 * @date： 2018年3月10日 下午5:02:01
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public String detailFinance(String sellerId,String billNo){
		if(StringUtils.isBlank(sellerId)|| StringUtils.isBlank(billNo)){
			return JsonUtils.lineJsonData(0, "error", "请求参数错误", null);
		}
		
		return "";
	}
	/**
	 * 获取商户可提现金额和待开增值税金额
	 * @param sellerId
	 * @return
	 * @author John
	 * @date： 2018年4月11日 下午5:18:40
	 */
	@RequestMapping("/fetchInvoiceValue")
	@ResponseBody
	public Result<Object> fetchInvoiceValue(String sellerId){
		if(StringUtils.isBlank(sellerId)){
			return new Result<>(0,"error","商户ID不能为空");
		}
		 return financeInfoService.selectDisposableValue(sellerId);
	}
	
}
