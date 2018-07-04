package com.zzk.controller;

import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.entity.ProductStockInfo;
import com.zzk.service.ProductStockInfoService;

/**
 * 库存表
 * @name: ProductStockInfoController
 * @author: Kun
 * @date: 2018-03-06 15:34
 */
@RequestMapping(value = "/productStockInfo")
@RestController
@EnableAutoConfiguration
public class ProductStockInfoController extends BaseController {

	@Resource
	private ProductStockInfoService productStockInfoService;
	
}
