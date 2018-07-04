package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zzk.Constant;
import com.zzk.dao.*;
import com.zzk.entity.*;
import com.zzk.util.DateUtils;
import com.zzk.util.MethodAnnotation;
import com.zzk.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

/**
 * 商品基本信息表
 *
 * @author: Kun
 * @date: 2018-03-06 14:32
 */
@Service("productBaseInfoService")
public class ProductBaseInfoServiceImpl implements ProductBaseInfoService {

    @Resource
    private ProductBaseInfoMapper productBaseInfoMapper;
    @Resource
    private ProductImagesMapper productImagesMapper;
    @Resource
    private SellerChooseAttributeInfoMapper sellerChooseAttributeInfoMapper;
    @Resource
    private AuditInfoMapper auditInfoMapper;
    @Resource
    private ProductAttributeInfoMapper productAttributeInfoMapper;
    @Resource
    private ProductTypeRegMapper productTypeRegMapper;
    @Resource
    private ProductEvaluateMapper productEvaluateMapper;
    @Resource
    private PlanInfoMapper planInfoMapper;
    @Resource
    private SinglePlanMapper singlePlanMapper;
    @Resource
    private TripElementService tripElementService;
    @Resource
    private OperateLogService operateLogService;

    /**
     * 分页查询
     */
    @Override
    public List<ProductBaseInfo> selectByPage(Map map) {
        return productBaseInfoMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return productBaseInfoMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public ProductBaseInfo selectByPrimaryKey(String id) {
        return productBaseInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProductBaseInfo bean) {
        return productBaseInfoMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(ProductBaseInfo bean) {
        return productBaseInfoMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    @MethodAnnotation(remark = "删除商品")
    public int delete(String productId) {
        ProductBaseInfo bean = productBaseInfoMapper.selectByPrimaryKey(productId);
        bean.setStatus(-1);
        return productBaseInfoMapper.updateByPrimaryKey(bean);
    }

    @Override
    public int physicalDeleteProduct(String productId) {
        return productBaseInfoMapper.physicalDeleteProduct(productId);
    }

    @Override
    public void saveProductPublishBaseInfo(String baseInfo,String productId) {

        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String productName = jsonObject.optString("productName");
        String subtitle = jsonObject.optString("subtitle");
        String label = jsonObject.optString("label");
        String descM = jsonObject.optString("description");
        String sellerId = jsonObject.optString("sellerId");

        ProductBaseInfo productBaseInfo = new ProductBaseInfo();
        productBaseInfo.setId(productId);
        productBaseInfo.setProductName(productName);
        productBaseInfo.setProductSubtitle(subtitle);
        productBaseInfo.setDescription(descM);
        productBaseInfo.setCategoryId(Constant.LINE_ID);
        productBaseInfo.setSellerId(sellerId);
        //todo 这里将在后面管理平台开发完成之后 修改审核状态以及上下架状态的逻辑
        /*初始设为未上架*/
        productBaseInfo.setShelfState(1);
        /*0=待审核 1=审核通过, 这里先设置为1 让她默认审核通过*/
        productBaseInfo.setAuditState(1);
        productBaseInfo.setStatus(1);
        productBaseInfo.setCreateTime(new Date());
        productBaseInfo.setUpdateTime(new Date());
        productBaseInfo.setShelveUpTime(new Date());
        productBaseInfo.setLabel(label);
        productBaseInfoMapper.insertSelective(productBaseInfo);
        operateLogService.recordLog("新增商品表信息，商品名称："+productName, productId, 1);
    }

    @Override
    public void updateProductPublishBaseInfo(String baseInfo) {
        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String productName = jsonObject.optString("productName");
        String productId = jsonObject.optString("productId");
        String subtitle = jsonObject.optString("subtitle");
        String label = jsonObject.optString("label");
        ProductBaseInfo productBaseInfo = productBaseInfoMapper.selectByPrimaryKey(productId);
        if (productBaseInfo != null) {
            productBaseInfo.setProductName(productName);
            productBaseInfo.setProductSubtitle(subtitle);
            productBaseInfo.setLabel(label);
            productBaseInfo.setUpdateTime(new Date());
            productBaseInfo.setAuditState(1);
            productBaseInfo.setShelfState(1);
            productBaseInfo.setShelveUpTime(new Date());
            productBaseInfoMapper.updateByPrimaryKeySelective(productBaseInfo);
            operateLogService.recordLog("修改商品表信息，商品名称："+productName, productId, 2);
        }
    }

    @Override
    public List<Map<String, Object>> listDeleteProduct(String sellerId) {
        List<Map<String, Object>> resultList = productBaseInfoMapper.listDeleteProduct(sellerId);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map<String, Object> map : resultList) {
                Date date = (Date) map.get("DELETE_DATE");
                map.put("DELETE_DATE", DateUtils.changeDateToStr(date, null));
            }
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> listProductForUser(Map<String, Object> map) {
        String days = (String) map.get("days");
        if (StringUtils.isNotBlank(days)) {
            switch (days) {
                case "1":
                    map.put("days", " AND t5.DAYS = 1");
                    break;
                case "2":
                    map.put("days", " AND t5.DAYS = 2");
                    break;
                case "3":
                    map.put("days", " AND t5.DAYS = 3");
                    break;
                case "4":
                    map.put("days", " AND t5.DAYS in (4,5,6,7)");
                    break;
                case "5":
                    map.put("days", " AND t5.DAYS > 7");
                    break;
                default:
            }
        }
        String theme = (String) map.get("theme");
        if (StringUtils.isNotBlank(theme)) {
            switch (theme) {
                case "爱心之旅":
                    map.put("theme", "%{\"name\":\"爱心之旅\",\"value\":1,\"checked\":true}%");
                    break;
                case "佛系之旅":
                    map.put("theme", "%{\"name\":\"佛系之旅\",\"value\":2,\"checked\":true}%");
                    break;
                case "红色之旅":
                    map.put("theme", "%{\"name\":\"红色之旅\",\"value\":3,\"checked\":true}%");
                    break;
                case "文化之旅":
                    map.put("theme", "%{\"name\":\"文化之旅\",\"value\":4,\"checked\":true}%");
                    break;
                case "赏花之旅":
                    map.put("theme", "%{\"name\":\"赏花之旅\",\"value\":5,\"checked\":true}%");
                    break;
                case "摄影之旅":
                    map.put("theme", "%{\"name\":\"摄影之旅\",\"value\":6,\"checked\":true}%");
                    break;
                case "探秘之旅":
                    map.put("theme", "%{\"name\":\"探秘之旅\",\"value\":7,\"checked\":true}%");
                    break;
                default:
            }
        }
        String orderBy = (String) map.get("orderBy");
        String orderDesc = (String) map.get("orderDesc");
        if (StringUtils.isBlank(orderDesc)) {
            orderDesc = "DESC";
            map.put("orderDesc", orderDesc);
        }
        if (StringUtils.isNotBlank(orderBy)) {
            switch (orderBy) {
                case "price":
                    map.put("orderBy", "MIN(t3.ADULT_SELL_PRICE)");
                    break;
                case "views":
                    map.put("orderBy", "t1.PURCHASES_NUMBER");
                    break;
                case "comments":
                    map.put("comments", "t1.COMMENTS");
                    map.put("orderBy","evaluate.SCORE");
                    break;
                case "all":
                    map.put("orderBy","t1.CREATE_TIME");break;
                default: map.put("orderBy","t1.UPDATE_TIME");
            }
        } else {
            map.put("orderBy","t1.UPDATE_TIME");
        }
        List<Map<String, Object>> resultList = productBaseInfoMapper.listProductForUser(map);
        return resultList;
    }

    @Override
    public Map<String, Object> getProductDetail(String productId) {
	    /*基本信息 : 商品名,商品价格,商品标签,商家姓名,商家Id*/
        Map<String, Object> resultMap = productBaseInfoMapper.getProductDetail(productId);
        if (resultMap == null){
            return null;
        }
        String label = (String) resultMap.get("label");
	    /*查询商品图片*/
        List<String> imgList = productImagesMapper.listImageUrlByProductId(productId);
        if (CollectionUtils.isNotEmpty(imgList)) {
            resultMap.put("imgList", imgList);
        }
        /*查询扩展属性*/
        List<Map<String, Object>> attrList = sellerChooseAttributeInfoMapper.listChooseProductAttrByProductId(productId);
        if (CollectionUtils.isNotEmpty(attrList)) {
            List<Map<String,Object>> attrs = new ArrayList<>();
            for (Map<String, Object> attr : attrList) {

                String attrId = (String) attr.get("ATTR_ID");
                String content = (String) attr.get("CONTENT");
                Integer type = (Integer) attr.get("TYPE");
                String name = (String) attr.get("NAME");
                String value = (String) attr.get("VALUE");

                //通过扩展属性ID查询扩展属性 :线路标签(特色)
                if (StringUtils.isEquals(attrId, "123daf133c6547ce80f96b526f347a20")) {
                    List<String> attrLabel = handleLabel(content);
                    if (StringUtils.isNotBlank(label)) {
                        JSONArray labelArray = JSONArray.fromObject(label);
                        JSONObject labelObject ;
                        for (int i = 0 ; i < labelArray.size() ; i++){
                            labelObject = labelArray.getJSONObject(i);
                            String labelItem = labelObject.optString("labelItem");
                            Collections.addAll(attrLabel, labelItem);
                        }
                    }
                    resultMap.put("label", attrLabel);
                }

                //小莹姐提的需求 : 将扩展属性中电话号码提出来
                if (StringUtils.isEquals(attrId, "38e90929d41a43738aead9a795cedcc4")) {
                    resultMap.put("phone",value);
                }

                //获取其他扩展属性 1=文本框2=单选3=复选4=html编辑器5=文本域
                Map<String,Object> attrMap = new HashMap<>(4);
                switch (type){
                    case 1 :
                        attrMap.put("name",name);
                        attrMap.put("attrId",attrId);
                        attrMap.put("content",value);
                        break;
                    case 2:
                        attrMap.put("name",name);
                        attrMap.put("attrId",attrId);
                        attrMap.put("content",handleLabel(content));
                        break;
                    case 3:
                        attrMap.put("name",name);
                        attrMap.put("attrId",attrId);
                        attrMap.put("content",handleLabel(content));
                        break;
                    case 4 :
                        attrMap.put("name",name);
                        attrMap.put("attrId",attrId);
                        attrMap.put("content",value);
                        break;
                    case 5 :
                        if (StringUtils.isNotBlank(value) && !StringUtils.isEquals(value,"[]")) {
                            attrMap.put("name",name);
                            attrMap.put("attrId",attrId);
                            attrMap.put("content",value);
                        }
                        break;
                    default:
                }
                //把线路标签(特色)剔除去,因为线路标签特色 会组装为label返回
                if (!StringUtils.isEquals(attrId, "123daf133c6547ce80f96b526f347a20") &&
                        !StringUtils.isEquals(attrId, "38e90929d41a43738aead9a795cedcc4") && attrMap.size()>0){
                    attrs.add(attrMap);
                }
            }
            resultMap.put("attributeList",attrs);
        } else {
            List<String> labelList = new ArrayList<>();
            if (StringUtils.isNotBlank(label)) {
                JSONArray labelArray = JSONArray.fromObject(label);
                JSONObject labelObject ;
                for (int i = 0 ; i < labelArray.size() ; i++){
                    labelObject = labelArray.getJSONObject(i);
                    String labelItem = labelObject.optString("labelItem");
                    Collections.addAll(labelList, labelItem);
                }
            }
            resultMap.put("label", labelList);
        }
        /*查询行程安排*/
        PlanInfo planBaseInfo = planInfoMapper.getDetailModePlanInfo(productId);
        if (planBaseInfo != null){
            resultMap.put("startAddress",planBaseInfo.getStartAddress());
            resultMap.put("days",planBaseInfo.getDays());
            resultMap.put("destination",planBaseInfo.getDestination());
            String planId = planBaseInfo.getId();
            List<Map<String,Object>> singlePlan = singlePlanMapper.getSinglePlan(planId);
            List<String> destinationList = new ArrayList<>();
            // 为地图数据处理行程
            if (CollectionUtils.isNotEmpty(singlePlan)){
                resultMap.put("planMode","detail");
                for (Map<String, Object> plan : singlePlan) {
                    List<Map<String,Object>> elementsList = new ArrayList<>();
                    String destination = MapUtils.getString(plan,"destination");
                    JSONArray jsonArray = JSONArray.fromObject(destination);
                    JSONObject jsonObject;
                    for (int i = 0; i<jsonArray.size(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String address = jsonObject.optString("address");
                        Map<String,Object> resultElement = tripElementService.selectOneByName(address);
                        if (resultElement!=null){
                            //将用户填写的地名 替换查询出来的匹配地名 如: 填写温江,查询出温江区,但是返回前台还是修改为温江
                            resultElement.put("address",address);
                            elementsList.add(resultElement);
                            destinationList.add(address);
                        }
                    }
                    plan.put("destination",elementsList);
                }
                //使用set去重
                Set<String> set = new HashSet<>();
                List<String> result = new ArrayList<>();
                for (String item : destinationList) {
                    if (set.add(item)) {
                        result.add(item);
                    }
                }
                resultMap.put("destinationList",result);
                resultMap.put("singlePlan",singlePlan);
            } else {
                resultMap.put("planMode","concise");
                PlanInfo concise = planInfoMapper.getConciseModePlanInfo(productId);
                List<Map<String,Object>> elementsList = new ArrayList<>();
                if (concise != null){
                    String destination = concise.getDestination();
                    JSONArray jsonArray = JSONArray.fromObject(destination);
                    JSONObject jsonObject;
                    for (int i = 0; i<jsonArray.size(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String address = jsonObject.optString("address");
                        Map<String,Object> resultElement = tripElementService.selectOneByName(address);
                        if (resultElement!=null){
                            //将用户填写的地名 替换查询出来的匹配地名 如: 填写温江,查询出温江区,但是返回前台还是修改为温江
                            resultElement.put("address",address);
                            elementsList.add(resultElement);
                            destinationList.add(address);
                        }
                    }
                    resultMap.put("conciseDestination",elementsList);
                    //使用set去重
                    Set<String> set = new HashSet<>();
                    List<String> result = new ArrayList<>();
                    for (String item : destinationList) {
                        if (set.add(item)) {
                            result.add(item);
                        }
                    }
                    resultMap.put("destinationList",result);
                }
            }
        }

        /*查询评论列表*/
        List<Map<String,Object>> evaluateList = productEvaluateMapper.listProductEvaluate(productId);
        if (CollectionUtils.isNotEmpty(evaluateList)) {
            Map<String,Object> evaluate = evaluateList.get(0);
            Double score = MapUtils.getDouble(evaluate,"score");
            if (score >= 4.5){
                evaluate.put("scoreText","非常值得去");
            } else if (score >= 4 && score < 4.5) {
                evaluate.put("scoreText","体验很棒");
            } else if (score < 4 && score >3) {
                evaluate.put("scoreText","体验不错");
            } else if (score <=3 ) {
                evaluate.put("scoreText","一般");
            }
        }
        String productScore = productEvaluateMapper.getOneProductScore(productId);
        resultMap.put("evaluateList",evaluateList);
        resultMap.put("productScore",productScore);

        /*新需求: 查询现在的剩余库存*/
        Integer remainingNumber = productBaseInfoMapper.getRemainingNumber(productId);
        resultMap.put("remainingNumber",remainingNumber);
        return resultMap;
    }

    @Override
    public int reductionProduct(String productId) {
        ProductBaseInfo productBaseInfo = productBaseInfoMapper.selectByPrimaryKey(productId);
        if (productBaseInfo != null) {
            productBaseInfo.setStatus(1);
            productBaseInfo.setAuditState(0);
            productBaseInfo.setUpdateTime(new Date());
            productBaseInfoMapper.updateByPrimaryKeySelective(productBaseInfo);

            /*审核表数据*/
            AuditInfo auditInfo = auditInfoMapper.getByObjectId(productId);
            if (auditInfo != null) {
                /*新增商品的时候会添加审核表,所以这里逻辑上是不能位空的*/
                auditInfo.setUpdateTime(new Date());
                auditInfo.setAuditState(0);
                auditInfoMapper.updateByPrimaryKeySelective(auditInfo);
            }
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> listProductForSeller(Map<String, Object> map) {
        String days = (String) map.get("days");
        if (StringUtils.isNotBlank(days)) {
            switch (days) {
                case "1":
                    map.put("days", " AND t5.DAYS = 1");
                    break;
                case "2":
                    map.put("days", " AND t5.DAYS = 2");
                    break;
                case "3":
                    map.put("days", " AND t5.DAYS = 3");
                    break;
                case "4":
                    map.put("days", " AND t5.DAYS in (4,5,6,7)");
                    break;
                case "5":
                    map.put("days", " AND t5.DAYS > 7");
                    break;
                default:
            }
        }
        String productState = (String) map.get("productState");
        if (StringUtils.isNotBlank(productState)){
            switch (productState) {
                case "已上架":
                    map.put("shelfState", 1);
                    break;
                case "已下架":
                    map.put("shelfState", 2);
                    break;
                case "审核中":
                    map.put("auditState", 0);
                    break;
                case "审核失败":
                    map.put("auditState", 2);
                    break;
                default:
            }
        }
        String theme = (String) map.get("theme");
        if (StringUtils.isNotBlank(theme)) {
            switch (theme) {
                case "爱心之旅":
                    map.put("theme", "%{\"name\":\"爱心之旅\",\"value\":1,\"checked\":true}%");
                    break;
                case "佛系之旅":
                    map.put("theme", "%{\"name\":\"佛系之旅\",\"value\":2,\"checked\":true}%");
                    break;
                case "红色之旅":
                    map.put("theme", "%{\"name\":\"红色之旅\",\"value\":3,\"checked\":true}%");
                    break;
                case "文化之旅":
                    map.put("theme", "%{\"name\":\"文化之旅\",\"value\":4,\"checked\":true}%");
                    break;
                case "赏花之旅":
                    map.put("theme", "%{\"name\":\"赏花之旅\",\"value\":5,\"checked\":true}%");
                    break;
                case "摄影之旅":
                    map.put("theme", "%{\"name\":\"摄影之旅\",\"value\":6,\"checked\":true}%");
                    break;
                case "探秘之旅":
                    map.put("theme", "%{\"name\":\"探秘之旅\",\"value\":7,\"checked\":true}%");
                    break;
                default:
            }
        }
        List<Map<String, Object>> resultList = productBaseInfoMapper.listProductForSeller(map);
        if (CollectionUtils.isNotEmpty(resultList)){
            for (Map<String, Object> stringObjectMap : resultList) {
                Integer auditState = (Integer) stringObjectMap.get("AUDIT_STATE");
                Integer shelfState = (Integer) stringObjectMap.get("SHELF_STATE");
                // 0=待审核 1=审核通过 2=审核不通过 3=系统自动审核通过
                switch (auditState){
                    case 0 : stringObjectMap.put("AUDIT_STATE","待审核");break;
                    case 1 : stringObjectMap.put("AUDIT_STATE","审核通过");break;
                    case 2 : stringObjectMap.put("AUDIT_STATE","审核不通过");break;
                    // 3=系统自动审核通过 这里传前端显示审核通过
                    case 3 : stringObjectMap.put("AUDIT_STATE","审核通过");break;
                    default:
                }
                switch (shelfState){
                    case 1 : stringObjectMap.put("SHELF_STATE","已上架");break;
                    case 2 : stringObjectMap.put("SHELF_STATE","已下架");break;
                    default:
                }
            }
        }
        return resultList;
    }

    @Override
    public Map<String, Object> getProductListSearchParam() {
        /*线路主题*/
        List<String> themeList = new ArrayList<>();
        String content = productAttributeInfoMapper.getThemeAttribute();
        if (StringUtils.isNotBlank(content) && content.startsWith("[")) {
            JSONArray jsonArray = JSONArray.fromObject(content);
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String theme = jsonObject.getString("name");
                themeList.add(theme);
            }
        }

        /*游玩天数*/
        String[] args = new String[]{"1天","2天","3天","4-7天","7天以上"};
        List<String> daysList = new ArrayList<>();
        Collections.addAll(daysList, args);

        /*出行方式*/
        List<Map<String,Object>> typeList = productTypeRegMapper.getLineTypeList();

        Map<String,Object> resultMap = new HashMap<>(4);
        resultMap.put("typeList",typeList);
        resultMap.put("daysList",daysList);
        resultMap.put("themeList",themeList);
        return resultMap;
    }

    @Override
    public int deleteOldSchedu(String productId) {
        /*清空简洁模式*/
        PlanInfo concise = planInfoMapper.getConciseModePlanInfo(productId);
        if (concise != null){
            planInfoMapper.deleteByPrimaryKey(concise.getId());
        }
        /*清空单天的行程安排*/
        PlanInfo planInfo = planInfoMapper.getDetailModePlanInfo(productId);
        if (planInfo != null) {
            String planId = planInfo.getId();
            return singlePlanMapper.deleteByPlanId(planId);
        }
        return 0;
    }

    @Override
    public int changeShelfState(String productId, Integer state) {
        Map<String,Object> paraMap = new HashMap<>(2);
        paraMap.put("productId",productId);
        paraMap.put("state",state);
        ProductBaseInfo productBaseInfo = productBaseInfoMapper.selectByPrimaryKey(productId);
        if (productBaseInfo != null){
            productBaseInfo.setShelfState(state);
            switch (state) {
                case 1 : productBaseInfo.setShelveUpTime(new Date());break;
                case 2 : productBaseInfo.setShelveDownTime(new Date());break;
                default:
            }
        }
        operateLogService.recordLog(state==1?"上架了商品":"下架了商品", productId, 2);
        return productBaseInfoMapper.updateByPrimaryKeySelective(productBaseInfo);
    }

    @Override
    public List<Map<String, Object>> listProductBySellerId(String sellerId) {
        return productBaseInfoMapper.listProductBySellerId(sellerId);
    }

    @Override
    public String getSellerPhone(String sellerId) {
        return productBaseInfoMapper.getSellerPhone(sellerId);
    }

    private List<String> handleLabel(String content) {
        if (StringUtils.isNotBlank(content) && content.startsWith("[")) {
            JSONArray jsonArray = JSONArray.fromObject(content);
            JSONObject jsonObject;
            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String checked = jsonObject.optString("checked");
                if (StringUtils.isNotBlank(checked)) {
                    resultList.add(name);
                }
            }
            return resultList;
        } else {
            return new ArrayList<>();
        }
    }

}
