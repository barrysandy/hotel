package com.zzk.controller;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.entity.ProductTypeInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.service.ProductTypeInfoService;

/**
 * 商品分类信息表
 * @name: ProductTypeInfoController
 * @author: Kun
 * @date: 2018-03-06 14:35
 */
@RequestMapping(value = "/productTypeInfo")
@RestController
@EnableAutoConfiguration
public class ProductTypeInfoController extends BaseController {

	@Resource
	private ProductTypeInfoService productTypeInfoService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return StringUtils.getUUID();
    }

}
