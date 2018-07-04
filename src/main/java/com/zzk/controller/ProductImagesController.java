package com.zzk.controller;

import java.util.*;
import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.entity.ProductImages;
import com.zzk.service.ProductImagesService;

/**
 * 商品图片表
 * @name: ProductImagesController
 * @author: Kun
 * @date: 2018-03-06 14:26
 */
@RequestMapping(value = "/productImages")
@RestController
@EnableAutoConfiguration
public class ProductImagesController extends BaseController {

	@Resource
	private ProductImagesService productImagesService;
	

}
