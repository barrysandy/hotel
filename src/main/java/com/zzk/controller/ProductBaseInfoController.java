package com.zzk.controller;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzk.Constant;
import com.zzk.dao.SinglePlanMapper;
import com.zzk.entity.*;
import com.zzk.service.*;
import com.zzk.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 商品基本信息表
 *
 * @name: ProductBaseInfoController
 * @author: Kun
 * @date: 2018-03-06 14:32
 */
@RequestMapping(value = "/productBaseInfo")
@RestController
@EnableAutoConfiguration
public class ProductBaseInfoController extends BaseController {

    @Resource
    private ProductBaseInfoService productBaseInfoService;
    @Resource
    private ProductTypeInfoService productTypeInfoService;
    @Resource
    private ProductTypeRegService productTypeRegService;
    @Resource
    private SellerChooseAttributeInfoService sellerChooseAttributeInfoService;
    @Resource
    private ProductAttributeInfoService productAttributeInfoService;
    @Resource
    private PlanInfoService planInfoService;
    @Resource
    private AuditInfoService auditInfoService;
    @Resource
    private ProductImagesService productImagesService;
    @Resource
    private SinglePlanService singlePlanService;
    @Resource
    private GoodsSkuInfoService goodsSkuInfoService;
    @Resource
    private OrderBaseInfoService orderBaseInfoService;
    @Resource
    private ProductStockInfoService productStockInfoService;
    @Resource
    private SinglePlanMapper singlePlanMapper;



    /**
     * 商品基本信息表删除
     *
     * @param productId 主键ID
     * @return String
     * @author Kun
     * @date 2018-03-06 14:32
     */
    @RequestMapping("/deleteProductById")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result deleteProductById(String productId) {
        try {
            productBaseInfoService.delete(productId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0,"fail","内部异常",e.getMessage());
        }
        return new Result(1,"success","操作成功");
    }

    /**
     * 回收站 彻底删除商品
     * @param productId
     * @return result
     * @author kun
     * @date 11:57 2018/3/29
     */
    @RequestMapping("physicalDeleteProduct")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result physicalDeleteProduct(String productId){
        try {
            if (StringUtils.isBlank(productId)){
                return new Result(0,"fail","商品ID不能位空");
            } else{
                productBaseInfoService.physicalDeleteProduct(productId);
                return new Result(1,"success","操作成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0,"fail","内部异常",e.getMessage());
        }
    }

    /********************************************B端商品发布相关接口*********************************************/

    /**
     * 跳转商品发布基本信息页接口
     *
     * @param productId 商品Id
     * @author kun
     * @date 15:49 2018/3/8
     */
    @RequestMapping("toProductBaseInfoPage")
    @ResponseBody
    public String toProductBaseInfoPage(String productId,String sellerId) {
        try {
            /*定义接口返回DATA*/
            Map<String, Object> resultMap = new HashMap<>(8);
            /*查询系统分类配置信息*/
            String parentId = Constant.LINE_ID;
            List<Map<String, Object>> regList = productTypeRegService.listProductTypeByParentId(parentId);
            resultMap.put("productTypeList", regList);
            /*查询商家的联系电话*/
            String phoneNum = productBaseInfoService.getSellerPhone(sellerId);
            resultMap.put("sellerPhone",phoneNum);
            /*查询系统扩展属性配置信息*/
            List<Map<String, Object>> attrList = productAttributeInfoService.listProductAttrByCategoryId(parentId);

            if (StringUtils.isNotBlank(productId)) {

                /*查询商品基本信息*/
                ProductBaseInfo productBaseInfo = productBaseInfoService.selectByPrimaryKey(productId);
                if (productBaseInfo == null) {
                    return JsonUtils.lineJsonData(2, "fail", "无法查询到此商品信息，检查ID", "传入的productId:" + productId);
                } else {
                    String productName = productBaseInfo.getProductName();
                    String subtitle = productBaseInfo.getProductSubtitle();
                    String label = productBaseInfo.getLabel();
                    resultMap.put("label", label);
                    resultMap.put("productName", productName);
                    resultMap.put("subtitle", subtitle);
                }

                /*查询图片信息*/
                List<ProductImages> images = productImagesService.listImageByProductId(productId);
                StringBuilder imageList = new StringBuilder();
                if (CollectionUtils.isNotEmpty(images)){
                    for (ProductImages image : images) {
                        String url = image.getUrl();
                        Integer type = image.getType();
                        if (type == 2) {
                            resultMap.put("coverUrl",url);
                        } else if (type == 1){
                            imageList.append(",");
                            imageList.append(url);
                        }
                    }
                    if (imageList.length()>1){
                        resultMap.put("imgList",imageList.substring(1));
                    }
                }

                /*查询该商品商家选择的分类*/
                ProductTypeInfo productTypeInfo = productTypeInfoService.getByProductId(productId);
                if (productTypeInfo != null) {
                    String typeInfoId = productTypeInfo.getParentId();
                    String typeName = productTypeInfo.getName();
                    resultMap.put("typeId", typeInfoId);
                    resultMap.put("typeName", typeName);
                }

                /*查询商品选择的扩展属性*/
                List<Map<String, Object>> sellerChooseAttribute = sellerChooseAttributeInfoService.listChooseProductAttrByProductId(productId);
                for (Map<String, Object> chooseAttr : sellerChooseAttribute) {
                    String attrId = (String) chooseAttr.get("ATTR_ID");
                    String content = (String) chooseAttr.get("CONTENT");
                    String value = (String) chooseAttr.get("VALUE");
                    for (Map<String, Object> platAttr : attrList) {
                        String platAttrId = (String) platAttr.get("ID");
                        if (StringUtils.isEquals(attrId, platAttrId)) {
                            platAttr.put("CONTENT", content);
                            platAttr.put("VALUE", value);
                        }
                    }
                }
                Map<String,Object> themAndLable =  handleAttrListForPage(attrList);
                resultMap.put("theme",themAndLable.get("theme"));
                resultMap.put("featuresLabel",themAndLable.get("featuresLabel"));
                resultMap.put("attrList", attrList);

                /*查询行程安排计划*/
                PlanInfo planInfo = planInfoService.getDetailModePlanInfo(productId);
                if (planInfo != null) {
                    /*行程天数*/
                    Integer days = planInfo.getDays();
                    /*出发地*/
                    String startAddress = planInfo.getStartAddress();
                    /*目的地*/
                    String destination = planInfo.getDestination();
                    /*提前几天预定*/
                    String daysLimit = planInfo.getDaysLimit();
                    /*提前几小时预定*/
                    String hoursLimit = planInfo.getHoursLimit();
                    resultMap.put("days", days);
                    resultMap.put("startAddress", startAddress);
                    resultMap.put("destination", destination);
                    resultMap.put("daysLimit", daysLimit);
                    resultMap.put("hoursLimit", hoursLimit);
                }
                return JsonUtils.lineJsonData(1, "success", "success", resultMap);

            } else {
                resultMap.put("attrList", attrList);
                return JsonUtils.lineJsonData(1, "success", "success", resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.lineJsonData(0, "fail", "内部异常: " + e.getMessage(), e);
        }
    }

    /**
     * 为前端的扩展属性组装数据
     * @param attrList
     * @return Map
     * @author kun
     * @date 16:46 2018/3/27
     */
    private Map<String,Object> handleAttrListForPage(List<Map<String,Object>> attrList){
        Map<String,Object> resultMap = new HashMap<>(2);
        JSONArray theme = new JSONArray();
        JSONArray featuresLabel = new JSONArray();
        if (CollectionUtils.isNotEmpty(attrList)){
            for (Map<String, Object> attr : attrList) {
                String name = MapUtils.getString(attr,"NAME");
                String content = MapUtils.getString(attr,"CONTENT");
                Integer type = MapUtils.getInteger(attr,"TYPE");
                //3=多选框
                if (type == 3){
                    JSONArray jsonArray = JSONArray.fromObject(content);
                    JSONObject jsonObject;
                    for (int i = 0 ; i<jsonArray.size() ; i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        String key = jsonObject.getString("name");
                        boolean checked = jsonObject.optBoolean("checked",false);
                        if (checked){
                            switch (name){
                                case "主题" : theme.add(key);break;
                                case "线路标签(特色)" : featuresLabel.add(key);break;
                                default:
                            }
                        }

                    }
                }
            }
            resultMap.put("theme",theme);
            resultMap.put("featuresLabel",featuresLabel);
        }
        return resultMap;
    }

    /**
     * 商品发布基本信息的保存接口
     *
     * @param baseInfo json字符串
     * @author kun
     * @date 15:49 2018/3/8
     */
    @RequestMapping(value = "saveBaseInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result saveBaseInfo(@RequestBody String baseInfo) {
        try {
            if (StringUtils.isBlank(baseInfo)) {
                return new Result(0, "fail", "商家ID不能为空");
            }
            JSONObject jsonObject = JSONObject.fromObject(baseInfo);
            String productId = jsonObject.optString("productId");
            if (StringUtils.isBlank(productId)) {
                /*新增*/
                productId = StringUtils.getUUID();
                /*保存商品表信息*/
                productBaseInfoService.saveProductPublishBaseInfo(baseInfo, productId);
                /*保存分类信息*/
                productTypeInfoService.saveProductPublishSellerChooseType(baseInfo, productId);
                /*审核表添加数据*/
                auditInfoService.saveProductPublishAuditInfo(productId);
                /*保存图片表信息*/
                productImagesService.saveProductPublishProductImg(baseInfo, productId);
                /*保存行程总安排*/
                String planId = planInfoService.saveProductPublishPlanInfo(baseInfo, productId);
                /*扩展属性的新增*/
                sellerChooseAttributeInfoService.saveProductPublishSellerAttr(baseInfo, productId);

                return new Result(1, "success", "操作成功", productId);
            } else {
                /*更新*/
                /*更新商品表信息*/
                productBaseInfoService.updateProductPublishBaseInfo(baseInfo);
                /*更新分类信息*/
                productTypeInfoService.updateProductPublishSellerChooseType(baseInfo);
                /*更新图片表信息*/
                productImagesService.updateProductPublishProductImg(baseInfo);
                /*更新行程安排*/
                planInfoService.updateProductPublishPlanInfo(baseInfo);
                /*更新审核表数据*/
                auditInfoService.updateProductPublishAuditInfo(productId);
                /*扩展属性的修改*/
                sellerChooseAttributeInfoService.updateProductPublishSellerAttr(baseInfo);

                return new Result(1, "success", "保存成功",productId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());
        }
    }


    /**
     * 跳转行程安排页面接口
     *
     * @author kun
     * @date 15:53 2018/3/8
     */
    @RequestMapping("/toPlanInfoPage")
    @ResponseBody
    public String toPlanInfoPage(String productId) {
        try {

            Map<String, Object> resultMap = new HashMap<>(4);

            /*查询详细模式的数据*/
            PlanInfo detailPlan = planInfoService.getDetailModePlanInfo(productId);
            Map<String, Object> detailPlanMap = new HashMap<>(4);
            if (detailPlan != null) {
                String detailPlanId = detailPlan.getId();
                Integer days = detailPlan.getDays();
                resultMap.put("days", days);
                List<Map<String, Object>> singlePlans = singlePlanService.getSinglePlan(detailPlanId);
                detailPlanMap.put("singlePlanList", singlePlans);
            }

            /*查询简介模式的数据*/
            PlanInfo concisePlan = planInfoService.getConciseModePlanInfo(productId);
            Map<String, Object> concisePlanMap = new HashMap<>(4);
            if (concisePlan != null) {
                /*内容*/
                String content = concisePlan.getContent();
                /*途经*/
                String destination = concisePlan.getDestination();
                concisePlanMap.put("introduce", content);
                concisePlanMap.put("destination", destination);
            }

            resultMap.put("detail", detailPlanMap);
            resultMap.put("concise", concisePlanMap);
            return JsonUtils.lineJsonData(1,"success","操作成功",resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.lineJsonData(0,"fail","内部异常",e);
        }
    }

    /**
     * 行程安排保存接口
     *
     * @param responseData json参数
     * @return String
     * @author kun
     * @date 16:42 2018/3/8
     */
    @RequestMapping("savePlanInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result savePlanInfo(@RequestBody String responseData) {
        try {
            if (StringUtils.isBlank(responseData)) {
                return new Result(0, "fail", "参数错误");
            } else {
                JSONObject jsonObject = JSONObject.fromObject(responseData);
                Integer type = jsonObject.optInt("type");
                String productId = jsonObject.optString("productId");
                if (type == 1) {
                    /*简介模式,保存content和途径*/
                    JSONObject simpleSchedu = jsonObject.getJSONObject("simpleSchedu");
                    JSONArray destination = simpleSchedu.getJSONArray("destination");
                    String content = simpleSchedu.getString("introduce");
                    planInfoService.saveConciseModePlanInfo(productId, destination, content);
                    /*填写的简洁模式,清除所有的单天行程安排*/
                    PlanInfo planInfo = planInfoService.getDetailModePlanInfo(productId);
                    if (planInfo != null) {
                        String planId = planInfo.getId();
                        singlePlanMapper.deleteByPlanId(planId);
                    }
                } else if (type == 2) {
                    /*详细模式保存数据至单天行程安排表中*/
                    JSONArray detailSched = jsonObject.getJSONArray("detailSchedu");
                    JSONObject detailItem;
                    List<Map<String, Object>> list = new ArrayList<>();
                    for (int i = 0; i < detailSched.size(); i++) {
                        detailItem = detailSched.getJSONObject(i);
                        Map<String, Object> dataMap = new HashMap<>(8);
                        dataMap.put("destination", detailItem.get("destination"));
                        dataMap.put("day", detailItem.optString("day"));
                        dataMap.put("description", detailItem.optString("introduce"));
                        dataMap.put("brightSpot", detailItem.optString("brightSpot"));
                        dataMap.put("mileage", detailItem.optString("mileage"));
                        list.add(dataMap);
                    }
                    planInfoService.saveDetailModePlanInfo(list, productId);
                    /*填写的详细模式,删除简介模式数据*/
                    PlanInfo concise = planInfoService.getConciseModePlanInfo(productId);
                    if (concise != null){
                        planInfoService.delete(concise.getId());
                    }
                }
                return new Result(1,"success","操作成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0,"fail","内部异常:"+e.getMessage(),e.getMessage());
        }
    }

    /**
     * 行程天数变更删除旧的行程安排接口
     * @param productId
     * @return result
     * @author kun
     * @date 10:54 2018/3/30
     */
    @RequestMapping("deleteOldSchedu")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result deleteOldSchedu(String productId){
        try {
            if (StringUtils.isBlank(productId)){
                return new Result(0,"error","商品ID不能位空");
            } else {
                productBaseInfoService.deleteOldSchedu(productId);
                return new Result(1,"success","操作成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());
        }
    }


    /**
     * B端展品列表 上方搜索条件接口
     *
     * @return result
     * @author kun
     * @date 10:38 2018/3/19
     */
    @RequestMapping("getProductListScreenParam")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result getProductListSearchParam() {
        try {
            Map<String, Object> resultList = productBaseInfoService.getProductListSearchParam();
            return new Result(1, "success", "操作成功", resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());
        }
    }

    /**
     * B端产品列表接口
     *
     * @param sellerId     商家ID
     * @param theme        主题筛选 (查询扩展属性表)
     * @param days         旅游天数筛选
     * @param productState 状态筛选 已上架,已下架,审核中,审核失败
     * @param type         出行类型筛选 (查询分类表)
     * @return Result
     * @author kun
     * @date 11:34 2018/3/17
     */
    @RequestMapping("getProductList")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result getProductList(String sellerId, String theme, String days, String productState,
                                 String type, Integer pageSize, Integer pageNum) {
        try {
            if (StringUtils.isBlank(sellerId)) {
                return new Result(0, "fail", "商家ID不能为空");
            } else {
                pageSize = (pageSize == null ? 20 : pageSize);
                pageNum = (pageNum == null ? 1 : pageNum);
                Map<String, Object> paraMap = new HashMap<>(8);
                paraMap.put("theme", theme);
                paraMap.put("days", days);
                paraMap.put("productState", productState);
                paraMap.put("type", type);
                paraMap.put("sellerId", sellerId);
                PageHelper.startPage(pageNum, pageSize);
                List<Map<String, Object>> productList = productBaseInfoService.listProductForSeller(paraMap);
                return new Result(1, "success", "查询到商品列表", new PageView<>(productList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage());
        }
    }

    /**
     * 修改商品上下架状态接口
     * @param productId 商品Id
     * @param state 状态 1=上架 2=下架
     * @return result
     * @author kun
     * @date 15:52 2018/4/2
     */
    @RequestMapping("changeProductShelfState")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result changeProductShelfState(String productId,Integer state){
        try {
            if (StringUtils.isBlank(productId) || state == null) {
                return new Result(0, "fail", "参数错误");
            } else {
                int result = productBaseInfoService.changeShelfState(productId,state);
                if (result > 0 ) {
                    return new Result(1, "success", "操作成功!");
                } else {
                    return new Result(0, "fail", "操作失败!");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage(),e);
        }
    }

    /**
     * 获取单品列表接口 (和下面的listGoodsSku重复了 ..)
     *
     * @author kun
     * @date 16:15 2018/3/9
     */
    @RequestMapping("getGoodsList")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result getGoodsList(String productId) {
        try {
            if (StringUtils.isBlank(productId)) {
                return new Result(0,"fail","参数不能为空");
            } else {
                List<Map<String, Object>> resultList = goodsSkuInfoService.listGoodsListByProductId(productId);
                return new Result(1,"success","查询到单品列表",resultList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage(),e);
        }
    }

    /**
     * 单品删除接口
     *
     * @param skuId
     * @return String
     * @author kun
     * @date 10:41 2018/3/10
     */
    @RequestMapping("deleteOneSku")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result deleteOneSku(String skuId) {
        try {
            if (StringUtils.isBlank(skuId)) {
                return new Result(0,"fail","参数不能为空");
            } else {
                int result = goodsSkuInfoService.delete(skuId);
                if (result > 0) {
                    return new Result(1,"success","操作成功!");
                } else {
                    return new Result(0,"fail","未知错误导致操作失败!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage(),e);
        }
    }

    /**
     * 商品发布-单品信息保存
     *
     * @param skuId        单品ID,当为更新的时候需传递
     * @param skuName      价格名称(单品名)
     * @param calendarData json数据的日历数据
     * @return String
     * @author kun
     * @date 10:48 2018/3/10
     */
    @RequestMapping("saveSkuInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result saveSkuInfo(String skuId, String skuName, String productId, String calendarData) {
        try {
            if (StringUtils.isBlank(skuName) || StringUtils.isBlank(calendarData)) {
                return new Result(0, "fail", "参数不能位空" );
            } else if (!calendarData.startsWith("[")) {
                return new Result(0, "fail", "参数格式错误" );
            } else {
                JSONArray jsonArray = JSONArray.fromObject(calendarData);
                List<ProductStockInfo> list = new ArrayList<>();
                JSONObject jsonObject;
                for (int i = 0; i < jsonArray.size(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    ProductStockInfo productStockInfo = new ProductStockInfo();
                    productStockInfo.setAdultNumber(jsonObject.getInt("adultNumber"));
                    productStockInfo.setChildNumber(jsonObject.getInt("childNumber"));
                    String adultSellPrice = jsonObject.getString("adultSellPrice");
                    String childSellPrice = jsonObject.getString("childSellPrice");
                    String originalPrice = jsonObject.getString("originalPrice");
                    String startTime = jsonObject.getString("startTime");
                    /*避免转换异常,这里加一层BUG过滤*/
                    if (StringUtils.isNotBlank(adultSellPrice)) {
                        productStockInfo.setAdultSellPrice(new BigDecimal(adultSellPrice));
                    }
                    if (StringUtils.isNotBlank(childSellPrice)) {
                        productStockInfo.setChildSellPrice(new BigDecimal(childSellPrice));
                    }
                    if (StringUtils.isNotBlank(originalPrice)) {
                        productStockInfo.setOriginalPrice(new BigDecimal(originalPrice));
                    }
                    if (StringUtils.isNotBlank(startTime)) {
                        productStockInfo.setStartTime(DateUtils.changeStrToDate(startTime));
                    }
                    productStockInfo.setId(StringUtils.getUUID());
                    productStockInfo.setStatus(1);
                    productStockInfo.setCreateTime(new Date());
                    productStockInfo.setUpdateTime(new Date());
                    list.add(productStockInfo);
                }
                if (StringUtils.isBlank(skuId)) {
                    skuId = StringUtils.getUUID();
                    goodsSkuInfoService.insertSkuInfo(skuId, productId, skuName, list);
                } else {
                    goodsSkuInfoService.updateSkuInfo(skuId, skuName, list);
                }
                return new Result(1,"success","操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常" + e.getMessage(),e);
        }
    }

    /**
     * 回收站 - 已删除商品列表
     *
     * @param sellerId 商家ID
     * @author kun
     * @date 19:06 2018/3/13
     */
    @RequestMapping("listDeleteProduct")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result listDeleteProduct(Integer pageSize, Integer pageNumber, String sellerId) {
        try {
            pageNumber = (pageNumber == null ? 1 : pageNumber);
            pageSize = (pageSize == null ? 10 : pageSize);
            PageHelper.startPage(pageNumber, pageSize);
            List<Map<String, Object>> resultList = productBaseInfoService.listDeleteProduct(sellerId);
            return new Result(1, "success", "查询到商品列表", new PageView<>(resultList));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常:" + e.getMessage());
        }
    }

    /**
     * 回收站 - 还原已删除商品
     *
     * @param productId
     * @return result
     * @author kun
     * @date 11:42 2018/3/16
     */
    @RequestMapping("reductionProduct")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result reductionProduct(String productId) {
        try {
            if (StringUtils.isBlank(productId)) {
                return new Result(0, "fail", "参数不能为空");
            } else {
                productBaseInfoService.reductionProduct(productId);
                return new Result(1, "success", "操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常:" + e.getMessage());
        }
    }

    /******************************************B端线路发团相关接口*************************************************/

    /**
     * 通过productId查询单品列表
     *
     * @param productId
     * @return list
     * @author kun
     * @date 15:20 2018/3/10
     */
    @RequestMapping("listGoodsSku")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result listGoodsSku(String productId) {
        try {
            if (StringUtils.isBlank(productId)) {
                return new Result(0, "fail", "参数不能位空" );
            } else {
                List<Map<String, Object>> resultList = goodsSkuInfoService.listGoodsListByProductId(productId);
                return new Result(1,"success","查询到单品列表",resultList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常",e);

        }
    }

    /**
     * 发团管理 - 选择产品 接口
     * @param sellerId 商家ID
     * @return result
     * @author kun
     * @date 15:09 2018/4/10
     */
    @RequestMapping("listProductBySellerId")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result listProductBySellerId(String sellerId){
        try {
            if (StringUtils.isBlank(sellerId)){
                return new Result(0,"fail","参数不能为空");
            } else {
                List<Map<String,Object>> resultList = productBaseInfoService.listProductBySellerId(sellerId);
                return new Result(1,"success","查询到商品LIST",resultList);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0,"fail","内部异常" ,e);
        }
    }

    /**
     * 查询发团列表
     *
     * @author kun
     * @date 15:08 2018/3/10
     */
    @RequestMapping("listLineGroup")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result listLineGroup(String startTime, String endTime, String productId, Integer pageSize, Integer pageNumber,
                                String sellerId , String sort) {
        try {
            if (StringUtils.isBlank(sellerId)) {
                return new Result(0, "fail", "参数不能为空", null);
            } else {
                pageNumber = (pageNumber == null ? Constant.PAGE_NUMBER : pageNumber);
                pageSize = (pageSize == null ? Constant.PAGE_SIZE : pageSize);

                Map<String, Object> paraMap = new HashMap<>(8);
                paraMap.put("startDate", startTime);
                paraMap.put("endDate", endTime);
                paraMap.put("productId", productId);
                paraMap.put("sellerId", sellerId);
                paraMap.put("sort",sort);
                PageHelper.startPage(pageNumber, pageSize);
                List<Map<String, Object>> resultList = goodsSkuInfoService.listLineGroupInfo(paraMap);
                return new Result(1, "success", "查询发团列表成功", new PageView<>(resultList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常:" + e.getMessage(), e);
        }
    }

    /**
     * 查询参团人员详情
     *
     * @param groupId
     * @return
     * @author kun
     * @date 10:23 2018/3/11
     */
    @RequestMapping("listLineOrderUserInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result listLineOrderUserInfo(String groupId, Integer pageSize, Integer pageNumber,String sort) {
        try {
            if (StringUtils.isBlank(groupId)) {
                return new Result(0, "fail", "参数不能为空" );
            } else {
                pageNumber = (pageNumber == null ? Constant.PAGE_NUMBER : pageNumber);
                pageSize = (pageSize == null ? Constant.PAGE_SIZE : pageSize);
                PageHelper.startPage(pageNumber, pageSize);
                List<Map<String, Object>> resultList = orderBaseInfoService.listLineOrderUserInfo(groupId,sort);
                return new Result(1, "success", "查询到参团人员详情", new PageView<>(resultList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常",e );
        }
    }

    /*******************************************C 端接口*********************************************/

    /**
     * C端线路列表接口(B端的列表接口更完善推荐使用B端的列表接口)
     *
     * @param days     游玩天数
     * @param orderBy  排序规则
     * @param orderDesc 正序或者倒叙
     * @return list
     * @author kun
     * @date 17:51 2018/3/14
     */
    @RequestMapping("listProductInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result listProductInfo(Integer pageSize, Integer pageNum, String days,String type,String theme, String orderBy, String orderDesc) {
        try {

            pageNum = (pageNum == null ? Constant.PAGE_NUMBER : pageNum);
            pageSize = (pageSize == null ? Constant.PAGE_SIZE : pageSize);
            Map<String, Object> paraMap = new HashMap<>(4);
            paraMap.put("days", days);
            paraMap.put("type", type);
            paraMap.put("theme", theme);
            paraMap.put("orderBy", orderBy);
            paraMap.put("orderDesc", orderDesc);
            PageHelper.startPage(pageNum, pageSize);
            List<Map<String, Object>> resultList = productBaseInfoService.listProductForUser(paraMap);
            return new Result(1, "success", "查询到商品列表", new PageView<>(resultList));

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常:" + e.getMessage(), e);
        }
    }

    /**
     * C端商品详情接口
     *
     * @param productId 商品ID
     * @return result
     * @author kun
     * @date 19:02 2018/3/19
     */
    @RequestMapping("getProductDetail")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result getProductDetail(String productId) {
        try {
            if (StringUtils.isBlank(productId)) {
                return new Result(0, "fail", "参数不能位空");
            } else {
                Map<String, Object> resultList = productBaseInfoService.getProductDetail(productId);
                if (resultList == null) {
                    return new Result(0,"error","商品信息有误,请检查商品的单品以及库存!");
                }
                return new Result(1, "success", "查询到商品详情", resultList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0, "fail", "内部异常:" + e.getMessage(), e);
        }
    }
}
