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
import com.zzk.util.StringUtils;
import com.zzk.entity.SellerChooseAttributeInfo;
import com.zzk.service.SellerChooseAttributeInfoService;

/**
 * 商家选择的扩展属性信息表
 * @name: SellerChooseAttributeInfoController
 * @author: Kun
 * @date: 2018-03-07 16:05
 */
@Controller
@RequestMapping(value = "/sellerChooseAttributeInfo")
public class SellerChooseAttributeInfoController extends BaseController {

	@Resource
	private SellerChooseAttributeInfoService sellerChooseAttributeInfoService;
	
}
