package com.zzk.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zzk.service.MessageRemindingConfigService;

/**
 * 消息提醒配置表
 *
 * @name: MessageRemindingConfigController
 * @author: Kun
 * @date: 2018-04-03 14:45
 */
@Controller
@RequestMapping(value = "/messageRemindingConfig")
public class MessageRemindingConfigController extends BaseController {

    @Resource
    private MessageRemindingConfigService messageRemindingConfigService;


}
