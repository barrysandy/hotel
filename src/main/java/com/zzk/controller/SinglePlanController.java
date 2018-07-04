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
import com.zzk.entity.SinglePlan;
import com.zzk.service.SinglePlanService;

/**
 * 单天行程安排表
 * @name: SinglePlanController
 * @author: Kun
 * @date: 2018-03-06 14:38
 */
@RequestMapping(value = "/singlePlan")
@RestController
@EnableAutoConfiguration
public class SinglePlanController extends BaseController {

	@Resource
	private SinglePlanService singlePlanService;

}
