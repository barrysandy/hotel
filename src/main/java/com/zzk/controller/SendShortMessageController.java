package com.zzk.controller;

import java.util.Map;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzk.link.IndustrySMS;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;

/**
 * sendSMS
 * @Description:专用于对外暴露的短信发送接口
 * @author John
 * @date： 2018年3月22日 上午9:33:25
 */
@RequestMapping(value = "/message")
@RestController
@EnableAutoConfiguration
public class SendShortMessageController extends  ControllerContext{
	
	@RequestMapping("/sendMessage")
	public Result<Object> sendMessage(String phoneNumber,String content,String time){
		if(StringUtils.isBlank(phoneNumber)){
			return new Result<>(0, "error", "手机号码不能为空");
		}
		if(StringUtils.isBlank(content)){
			return new Result<>(0, "error", "信息内容不能为空");
		}
		if(StringUtils.isBlank(time)){
			time="";
		}
		if(!StringUtils.isChinaPhoneLegal(phoneNumber)){
			return new Result<>(0, "error", "非法手机号码");
		}
		Map<String,Object> resultMap= IndustrySMS.link(phoneNumber, content, time, null);
		String status = (String) resultMap.get("status");
        Integer statusInt = Integer.parseInt(status);
        if(statusInt==1){
     	   return new Result<Object>(1, "success", "发送成功", null);
        }else{
     	   String msg = (String) resultMap.get("msg");
     	   return new Result<Object>(0, "error", msg, null);
        }
	}

}
