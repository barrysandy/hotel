package com.zzk.controller;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.service.ProductStockInfoService;
import com.zzk.util.Result;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.entity.GoodsSkuInfo;
import com.zzk.service.GoodsSkuInfoService;

/**
 * 单品信息表
 *
 * @name: GoodsSkuInfoController
 * @author: Kun
 * @date: 2018-03-06 15:16
 */
@RequestMapping(value = "/goodsSkuInfo")
@RestController
@EnableAutoConfiguration
public class GoodsSkuInfoController extends BaseController {

    @Resource
    private GoodsSkuInfoService goodsSkuInfoService;

    @Resource
    private ProductStockInfoService productStockInfoService;

    /******************************************C端接口*************************************************************/

    /**
     * C端 - 选择出行日期方案 - 获取商品的单品接口
     * @param productId 商品ID
     * @return skuName skuId
     * @author kun
     * @date 13:57 2018/3/20
     */
    @RequestMapping("listSku")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result listSku(String productId){
        try {
            if (StringUtils.isBlank(productId)){
                return new Result(0, "fail", "商品ID不能为空");
            } else {
                List<Map<String,Object>> resultList = goodsSkuInfoService.listSku(productId);
                return new Result(1, "success", "查询到出行方案列表", resultList);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());

        }
    }

    /**
     * 通过skuId查询发团信息 (C端日历数据)
     * @param skuId
     * @return result
     * @author kun
     * @date 15:49 2018/3/20
     */
    @RequestMapping("getSkuStockInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result getSkuStockInfo(String skuId){
        try {
            if (StringUtils.isBlank(skuId)){
                return new Result(0, "fail", "单品ID不能为空");
            } else {
                List<Map<String,Object>> resultMap = goodsSkuInfoService.getSkuStockInfo(skuId);
                return new Result(1, "success", "获取到日历信息", resultMap);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());

        }
    }


    /******************************************B端接口*************************************************************/

    /**
     * 商品发布 - 价格信息 - 状态修改接口
     * @param skuId 单品Id
     * @param state 状态 1=启用 2=不启用
     * @return result
     * @author kun
     * @date 19:31 2018/3/28
     */
    @RequestMapping("updateSkuState")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result updateSkuState(String skuId,Integer state){
        try {
            if (StringUtils.isBlank(skuId) || state == null) {
                return new Result(0, "fail", "单品ID及状态不能为空");
            } else {
                goodsSkuInfoService.updateSkuState(skuId,state);
                return new Result(1,"success","操作成功");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());
        }
    }

    /**
     * 商品发布 - 单品信息 删除
     * @param skuId 主键ID
     * @return String
     * @author Kun
     * @date 2018-03-06 15:16
     */
    @RequestMapping("/deleteSku")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result deleteSku(String skuId) {
        try {
            goodsSkuInfoService.delete(skuId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());
        }
        return new Result(1,"success","操作成功");
    }

    /**
     * 商品发布 - 价格信息 - 修改价格信息的数据获取接口
     * @param skuId
     * @return result
     * @author kun
     * @date 9:39 2018/3/28
     */
    @RequestMapping("toSkuUpdatePage")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result toSkuUpdatePage(String skuId){
        try {
            if (StringUtils.isBlank(skuId)){
                return new Result(0, "fail", "单品ID不能位空");
            }else{
                Map<String,Object> resultMap = goodsSkuInfoService.toSkuUpdatePage(skuId);
                return new Result(1,"success","查询到sku信息",resultMap);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());
        }
    }

    /**
     * 商品发布 - 价格信息 - 添加价格接口
     * @param skuData 单品的基本信息,以及发团列表基本信息
     * @return result
     * @author kun
     * @date 9:35 2018/3/28
     */
    @RequestMapping("saveOrUpdateSkuInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result saveOrUpdateSkuInfo(@RequestBody String skuData){
        try{
            if (StringUtils.isBlank(skuData)){
                return new Result(0, "fail", "参数错误");
            }else{
                goodsSkuInfoService.saveOrUpdateSkuInfo(skuData);
                return new Result(1,"success","操作成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());
        }
    }

}
