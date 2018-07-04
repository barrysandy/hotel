package com.zzk.controller;

import java.util.*;
import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.service.OrderBaseInfoService;
import com.zzk.service.OrderStatusInfoService;
import com.zzk.util.*;
import net.sf.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.entity.ProductEvaluate;
import com.zzk.entity.SellerReceiveConfig;
import com.zzk.service.ProductEvaluateService;
import com.zzk.service.SellerReceiveConfigService;

/**
 * 商品评论表
 *
 * @name: ProductEvaluateController
 * @author: Kun
 * @date: 2018-03-06 11:45
 */
@RequestMapping(value = "/productEvaluate")
@RestController
@EnableAutoConfiguration
public class ProductEvaluateController extends BaseController {

    @Resource
    private ProductEvaluateService productEvaluateService;
    @Resource
    private OrderBaseInfoService orderBaseInfoService;
    @Resource
    private OrderStatusInfoService orderStatusInfoService;
    @Resource
    private SellerReceiveConfigService sellerReceiveConfigService;


    /**********************************************C端评论接口***********************************************/

    /**
     * 添加评论信息
     *
     * @param evaluate 评论实体类
     * @return result
     * @author kun
     * @date 10:35 2018/3/15
     */
    @RequestMapping("insertEvaluate")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result insertEvaluate(@RequestBody String evaluate) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(evaluate);
            /*订单编号*/
            String orderNo = jsonObject.optString("orderNo");
            /*评论人ID*/
            String userId = jsonObject.optString("userId");
            /*新增评论*/
            if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(userId)) {
                return new Result(0, "fail", "订单编号或者评论人ID不能为空");
            }
            ProductEvaluate bean = new ProductEvaluate();
            bean.setId(StringUtils.getUUID());
            bean.setStatus(1);
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());
            bean.setFeaturesScore(jsonObject.optInt("featuresScore"));
            bean.setServiceScore(jsonObject.optInt("serviceScore"));
            bean.setSecurityScore(jsonObject.optInt("securityScore"));
            bean.setHealthScore(jsonObject.optInt("healthScore"));
            bean.setUserId(userId);
            bean.setOrderNo(orderNo);
            // 0=未回复 1=已回复
            bean.setReplayState(0);
            bean.setContent(jsonObject.optString("content"));
            bean.setImages(jsonObject.optString("images"));
            int code = productEvaluateService.insert(bean);
            if (code > 0) {
            	double score = (double)(bean.getHealthScore()+bean.getFeaturesScore()+bean.getServiceScore()+bean.getSecurityScore())/4;
            	double midScore = 3.0;
                OrderBaseInfo order = orderBaseInfoService.getOrderByOrderNo(orderNo);
                if(score<midScore){
                	sellerReceiveConfigService.sendMessageToSeller(order.getSellerId(), 4);
                }
                if (order != null) {
                    order.setUpdateTime(new Date());
                    //12 = 已评价
                    order.setOrderState(12);
                    
                    orderBaseInfoService.update(order);
                    orderStatusInfoService.changeStatus(orderNo, 12);
                }
                return new Result(1, "success", "添加评论成功");
            } else {
                return new Result(0, "fail", "操作失败,请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常 " + e.getMessage());
        }
    }

    /**
     * 删除评论
     *
     * @param evaluateId
     * @return result
     * @author kun
     * @date 10:51 2018/3/15
     */
    @RequestMapping("deleteEvaluateById")
    @ResponseBody
    public Result deleteEvaluateById(String evaluateId) {
        try {
            if (StringUtils.isBlank(evaluateId)) {
                return new Result(0, "fail", "参数不能位空");
            } else {
                productEvaluateService.delete(evaluateId);
                return new Result(1, "success", "操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常 " + e.getMessage());
        }
    }

    /**
     * 回复评论接口
     *
     * @param evaluateId 评论ID
     * @param content    回复内容
     * @return result
     * @author kun
     * @date 10:55 2018/3/15
     */
    @RequestMapping("saveEvaluateReply")
    @ResponseBody
    public Result saveEvaluateReply(String evaluateId, String content) {
        try {
            if (StringUtils.isBlank(evaluateId)) {
                return new Result(0, "fail", "参数不能为空");
            } else {
                ProductEvaluate evaluate = productEvaluateService.selectByPrimaryKey(evaluateId);
                /*避免异常在这里加一层为空判断,业务逻辑上讲,这里不可能位空*/
                if (evaluate != null) {
                    evaluate.setReplayContent(content);
                    /*0=未回复 1=已回复*/
                    evaluate.setReplayState(1);
                    evaluate.setReplayTime(new Date());
                    evaluate.setUpdateTime(new Date());
                    productEvaluateService.update(evaluate);
                    return new Result(1, "success", "操作成功");
                } else {
                    return new Result(0, "fail", "评论信息有误");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常 " + e.getMessage());
        }
    }

    /**********************************************B端评论接口***********************************************/

    /**
     * 获取商家各项评分信息
     * @param sellerId 商家Id
     * @return result
     * @author kun
     * @date 11:54 2018/3/15
     */
    @RequestMapping("getSellerAllScoreInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result getSellerAllScoreInfo(String sellerId) {
        try {
        	if (StringUtils.isBlank(sellerId)){
        	    return new Result(0,"fail","参数错误");
            } else {
        	    Map<String,Object> resultList = productEvaluateService.getSellerAllScoreInfo(sellerId);
        	    return new Result(1,"success","查询到商家评分信息",resultList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常 " + e.getMessage());
        }
    }

    /**
     * 查询评论列表
     * @param sellerId 商家ID
     * @param evaluateType 评论类型 好评,中评,差评,没有回复,有图
     * @param pageSize 每页多少评论
     * @param pageNum 第几页
     * @return result
     * @author kun
     * @date 15:12 2018/3/15
     */
    @RequestMapping("getEvaluateList")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result getEvaluateList(String sellerId,String evaluateType,Integer pageSize,Integer pageNum){
        try {
            if (StringUtils.isBlank(sellerId)){
                return new Result(0,"fail","参数错误");
            } else{
                Map<String,Object> paraMap = new HashMap<>(4);
                paraMap.put("sellerId",sellerId);
                paraMap.put("evaluateType",evaluateType);
                pageNum = (pageNum == null ? 1 : pageNum);
                pageSize = (pageSize == null ? 20 : pageSize);
                PageHelper.startPage(pageNum,pageSize);
                List<Map<String,Object>> resultList = productEvaluateService.getEvaluateList(paraMap);
                return new Result(1,"success","查询到评论列表",new PageView<>(resultList));
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常 " + e.getMessage());
        }
    }

    /**
     * 删除评论回复接口
     * @param evaluateId 评论ID
     * @return result
     * @author kun
     * @date 16:52 2018/3/28
     */
    @RequestMapping("deleteReply")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result deleteReply(String evaluateId){
        try {
            if (StringUtils.isBlank(evaluateId)){
                return new Result(0,"fail","评论ID不能位空");
            } else {
                int result = productEvaluateService.deleteReply(evaluateId);
                if (result >0 ){
                    return new Result(1,"success","操作成功");
                } else {
                    return new Result(0,"fail","查询不到评论信息");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常 " + e.getMessage());
        }
    }

}
