package com.zzk.controller;

import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.zzk.util.StringUtils;
import com.zzk.entity.PlanInfo;
import com.zzk.service.PlanInfoService;

/**
 * 行程安排总表
 * @name: PlanInfoController
 * @author: Kun
 * @date: 2018-03-06 11:09
 */
@RequestMapping(value = "/planInfo")
@RestController
@EnableAutoConfiguration
public class PlanInfoController extends BaseController {

	@Resource
	private PlanInfoService planInfoService;
	

}
