package com.zzk.controller;

import com.zzk.entity.ProductAttributeInfo;
import com.zzk.service.ProductAttributeInfoService;
import com.zzk.util.StringUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 商品扩展属性表
 * @name: ProductAttributeInfoController
 * @author: Kun
 * @date: 2018-03-06 11:36
 */
@RequestMapping(value = "/productAttributeInfo")
@RestController
@EnableAutoConfiguration
public class ProductAttributeInfoController extends BaseController {

	@Resource
	private ProductAttributeInfoService productAttributeInfoService;
	
}
