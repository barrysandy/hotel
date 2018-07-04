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
import com.zzk.entity.ProductTypeReg;
import com.zzk.service.ProductTypeRegService;

/**
 * 商品分类信息表
 * @name: ProductTypeRegController
 * @author: Kun
 * @date: 2018-03-07 15:04
 */
@Controller
@RequestMapping(value = "/productTypeReg")
public class ProductTypeRegController extends BaseController {

	@Resource
	private ProductTypeRegService productTypeRegService;
	
}
