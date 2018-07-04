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
import com.zzk.entity.SellerChooseMessageReminding;
import com.zzk.service.SellerChooseMessageRemindingService;

/**
 * 商家选择消息提醒表
 * @name: SellerChooseMessageRemindingController
 * @author: Kun
 * @date: 2018-04-03 14:50
 */
@Controller
@RequestMapping(value = "/sellerChooseMessageReminding")
public class SellerChooseMessageRemindingController extends BaseController {

	@Resource
	private SellerChooseMessageRemindingService sellerChooseMessageRemindingService;
	
}
