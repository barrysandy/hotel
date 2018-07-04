package com.zzk.controller;


import java.io.File;
import java.util.*;
import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzk.entity.SellerAccount;
import com.zzk.service.SellerAccountService;

/**
 * 商家账户表
 * @name: SellerAccountController
 * @author: wangpeng
 * @date: 2018-03-10 10:30
 */
@Controller
@RequestMapping(value = "/sellerAccount")
public class SellerAccountController extends BaseController {

	@Resource
	private SellerAccountService sellerAccountService;
	

	/**
	 * 获取用户的账户类型
	 * @param account
	 * @param type
	 * @return
	 * @author John
	 * @date： 2018年3月10日 上午11:17:56
	 */
	@RequestMapping("/saveOrUpdateAccount")
	public String saveOrUpdateAccount(SellerAccount account){
		Integer type = account.getType();
		if(type==null || type<100 ||type>104){
			return "forward:/sellerAccount/errorType";
		}
		System.out.println(account+"------");
		if(type==100){
		  return "forward:/sellerAccount/saveOrUpdatePublic";
		}else if(type==101){
		  return "forward:/sellerAccount/saveOrUpdatePrivate";
		}else{
		 return "forward:/sellerAccount/saveOrUpdateAlWX";
		}
	}
	/**
	 * 保存或更新对公账户
	 * @param account
	 * @return
	 * @author John
	 * @date： 2018年3月10日 上午11:21:55
	 */
	@RequestMapping("/saveOrUpdatePublic")
	@ResponseBody
	public String saveOrUpdatePublic(SellerAccount account){
		if(account == null){
			return JsonUtils.lineJsonData(0, "error", "参数错误", null);
		}
		if(StringUtils.isBlank(account.getSellerId())){
			return JsonUtils.lineJsonData(0, "error", "商户ID不能为空", null);
		}
		if(StringUtils.isBlank(account.getAccountName())){
			return JsonUtils.lineJsonData(0, "error", "账户名能为空", null);
		}
		if(StringUtils.isBlank(account.getAccountNum())){
			return JsonUtils.lineJsonData(0, "error", "账号不能为空", null);
		}
		if(account.getDepositBank()==0){
			return JsonUtils.lineJsonData(0, "error", "无效的开户行代码", null);
		}
		if(StringUtils.isBlank(account.getSubBank())){
			return JsonUtils.lineJsonData(0, "error", "支行不能为空", null);
		}
		if(StringUtils.isBlank(account.getBankCode())){
			return JsonUtils.lineJsonData(0, "error", "银行行号不能为空", null);
		}
		return sellerAccountService.insertOrUpdate(account);
	}
	/**
	 * 保存或更新私人账户
	 * @param account
	 * @return
	 * @author John
	 * @date： 2018年3月10日 下午12:52:04
	 */
	@RequestMapping("/saveOrUpdatePrivate")
	@ResponseBody
	public String saveOrUpdatePrivate(SellerAccount account){
		if(account == null){
			return JsonUtils.lineJsonData(0, "error", "参数错误", null);
		}
		if(StringUtils.isBlank(account.getSellerId())){
			return JsonUtils.lineJsonData(0, "error", "商户ID不能为空", null);
		}
		if(StringUtils.isBlank(account.getName())){
			return JsonUtils.lineJsonData(0, "error", "姓名不能为空", null);
		}
		if(StringUtils.isBlank(account.getAccountNum())){
			return JsonUtils.lineJsonData(0, "error", "银行账号不能为空", null);
		}
		if(account.getDepositBank()==null){
			return JsonUtils.lineJsonData(0, "error", "开户行不能为空", null);
		}
		return sellerAccountService.insertOrUpdate(account);
	}
	/**
	 * 保存或更新微信或支付宝账户
	 * @param account
	 * @return
	 * @author John
	 * @date： 2018年3月10日 下午12:52:04
	 */
	@RequestMapping("/saveOrUpdateAlWX")
	@ResponseBody
	public String saveOrUpdateAlWX(SellerAccount account){
		if(account == null){
			return JsonUtils.lineJsonData(0, "error", "参数错误", null);
		}
		if(StringUtils.isBlank(account.getSellerId())){
			return JsonUtils.lineJsonData(0, "error", "商户ID不能为空", null);
		}
		if(StringUtils.isBlank(account.getName())){
			return JsonUtils.lineJsonData(0, "error", "姓名不能为空", null);
		}
		if(StringUtils.isBlank(account.getAccountNum())){
			return JsonUtils.lineJsonData(0, "error", "账号不能为空", null);
		}
		if(StringUtils.isBlank(account.getPayQr())){
			return JsonUtils.lineJsonData(0, "error", "二维码不能为空", null);
		}
		return sellerAccountService.insertOrUpdate(account);
	}
	/**
	 * 获取账户信息
	 * @param sellerId
	 * @param type
	 * @return
	 * @author John
	 * @date： 2018年3月10日 下午1:40:32
	 */
	 @RequestMapping("/query")
	 @ResponseBody
	 public String query(String sellerId,Integer type){
		 SellerAccount account = null;
		if(StringUtils.isBlank(sellerId)){
			return JsonUtils.lineJsonData(0, "error", "商户ID不能为空", null);
		}
		if(type ==null || type>104 || type<100){
			return JsonUtils.lineJsonData(0, "error", "无效的账户类型", null);
		}
		account = sellerAccountService.selectBySellerId(sellerId, type);
		if(account != null){
			return JsonUtils.lineJsonData(1, "success", "获取成功", account);
		}
		return JsonUtils.lineJsonData(0, "error", "账户不存在", null);
	 }
	
	/**
	 *验证账户类型
	 * @return
	 * @author John
	 * @date： 2018年3月10日 上午11:00:12
	 */
	@RequestMapping("/errorType")
	@ResponseBody
	public String  errorTypeResult(){
		return JsonUtils.lineJsonData(0, "error", "无效的账户类型", null);
	}
}
