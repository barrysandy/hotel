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
import com.zzk.entity.SellerMessageNotify;
import com.zzk.service.SellerMessageNotifyService;

/**
 * 提现信息表的实体类
 * @name: SellerMessageNotifyController
 * @author: wangpeng
 * @date: 2018-05-09 14:27
 */
@Controller
@RequestMapping(value = "/sellerMessageNotify")
public class SellerMessageNotifyController extends BaseController {

	@Resource
	private SellerMessageNotifyService sellerMessageNotifyService;
	
	/**
	 * 查询信息消息
	 * @param pager
	 * @param model
	 * @param search
	 * @return
	 * @author John
	 * @date： 2018年5月9日 下午2:39:34
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result<Object> list(String sellerId) {
		return sellerMessageNotifyService.selectBySellerId(sellerId);
	}
	@RequestMapping("/clear")
	@ResponseBody
	public Result<Object> clear(String sellerId,int type){
		return sellerMessageNotifyService.clearMessage(sellerId,type);
	}
	
}
