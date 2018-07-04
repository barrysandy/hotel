package com.zzk.controller;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.entity.MessageRemindingConfig;
import com.zzk.service.MessageRemindingConfigService;
import com.zzk.service.SellerChooseMessageRemindingService;
import com.zzk.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.entity.SellerReceiveConfig;
import com.zzk.service.SellerReceiveConfigService;

/**
 * 商家消息接收设置表
 *
 * @name: SellerReceiveConfigController
 * @author: Kun
 * @date: 2018-04-03 15:12
 */
@Controller
@RequestMapping(value = "/sellerReceiveConfig")
public class SellerReceiveConfigController extends BaseController {

    @Resource
    private SellerReceiveConfigService sellerReceiveConfigService;
    @Resource
    private MessageRemindingConfigService messageRemindingConfigService;
    @Resource
    private SellerChooseMessageRemindingService sellerChooseMessageRemindingService;

    /** 新订单提醒*/
    private static final String NEW_ORDER_MESSAGE = "8e42a4c7a126427a9876d246828d831d";
    /** 退款提醒*/
    private static final String REFUND_MESSAGE = "8d73yf9115d126427a9876d246828d831d";
    /** 取单提醒*/
    private static final String CANCEL_ORDER_MESSAGE = "ft242ga126427a9876d246828d831d";
    /** 差评提醒*/
    private static final String BAD_EVALUATE_MESSAGE = "os9271d7a126427a9876d246828d831d";
    /** 财务提醒*/
    private static final String FINANCE_MESSAGE = "9u1udbq7a126427a9876d246828d831d";
    /** 满团提醒*/
    private static final String FULL_GROUP_MESSAGE = "mdi8012tfa126427a9876d246828d831d";

    /************************************* 设置 - 消息提醒相关接口 ****************************************/

    /**
     * 消息提醒页面接口
     * @param sellerId 商家Id
     * @return result
     * @author kun
     * @date 15:23 2018/4/3
     */
    @RequestMapping("toMessageRemindPage")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result toMessageRemindPage(String sellerId){
        try {
            if (StringUtils.isBlank(sellerId)){
                return new Result(0,"fail","参数不能为空");
            } else {
                //消息接受设置信息
                Map<String,Object> resultMap = sellerReceiveConfigService.selectBySellerId(sellerId);
                if (resultMap == null) {
                    resultMap = new HashMap<>(4);
                }
                //查询商家选择消息提醒
                List<Map<String,Object>> chooseMessage =sellerChooseMessageRemindingService.listChooseMessageBySellerId(sellerId);
                if (CollectionUtils.isNotEmpty(chooseMessage)){
                    for (Map<String, Object> message : chooseMessage) {
                        String messageId = MapUtils.getString(message,"MESSAGE_ID");
                        String content = MapUtils.getString(message,"MESSAGE_CONTENT");
                        content = content.replaceAll("，",",");
                        String[] args = content.split(",");
                        List<String> contentList = new ArrayList<>();
                        CollectionUtils.addAll(contentList,args);
                        switch (messageId) {
                            case NEW_ORDER_MESSAGE : resultMap.put("checkOrderList",contentList);break;
                            case REFUND_MESSAGE : resultMap.put("checkRefundList",contentList);break;
                            case CANCEL_ORDER_MESSAGE : resultMap.put("checkCancelList",contentList);break;
                            case BAD_EVALUATE_MESSAGE : resultMap.put("checkBadList",contentList);break;
                            case FINANCE_MESSAGE : resultMap.put("checkFinanceList",contentList);break;
                            case FULL_GROUP_MESSAGE : resultMap.put("checkFullList",contentList);break;
                            default:break;
                        }
                    }
                }
                return new Result(1,"success","查询到信息",resultMap);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0,"fail","内部异常" ,e);
        }
    }

    /**
     * 消息接受设置 , 消息提醒设置保存
     *
     * @param data 参数
     * @return String
     * @author Kun
     * @date 2018-04-03 15:12
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result saveOrUpdate(@RequestBody String data) {
        try {
            if (data == null || !data.startsWith("{")){
                return new Result(0,"fail","参数错误");
            } else {
                int result = sellerReceiveConfigService.saveReceiveConfig(data);
                if (result < 0 ) {
                    return new Result(0,"fail","未知错误导致操作不成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0,"fail","内部异常" ,e);
        }
        return new Result(1,"success","操作成功");
    }


}
