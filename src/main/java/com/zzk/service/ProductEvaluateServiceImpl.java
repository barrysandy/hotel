package com.zzk.service;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

import com.zzk.util.DateUtils;
import com.zzk.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.zzk.common.OrderConstact;
import com.zzk.dao.OrderBaseInfoMapper;
import com.zzk.dao.ProductEvaluateMapper;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.ProductEvaluate;

/**
 * 商品评论表
 *
 * @author: Kun
 * @date: 2018-03-06 11:45
 */
@Service("productEvaluateService")
public class ProductEvaluateServiceImpl implements ProductEvaluateService {

    @Resource
    private ProductEvaluateMapper productEvaluateMapper;
    @Resource
    private OrderBaseInfoMapper orderBaseInfoMapper;

    /**
     * 分页查询
     */
    @Override
    public List<ProductEvaluate> selectByPage(Map map) {
        return productEvaluateMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return productEvaluateMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public ProductEvaluate selectByPrimaryKey(String id) {
        return productEvaluateMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProductEvaluate bean) {
        return productEvaluateMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(ProductEvaluate bean) {
        Integer healthScore = bean.getHealthScore();
        Integer securityScore = bean.getSecurityScore();
        Integer serviceScore = bean.getServiceScore();
        Integer featuresScore = bean.getFeaturesScore();
        Double score = (double) (healthScore + securityScore + serviceScore + featuresScore) / 4;
        BigDecimal scoreBigDecimal = new BigDecimal(score);
        /*setScale(a,b) : a=1保留一位小数 b=四舍五入 */
        score = scoreBigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        String evaluateType;
        if (score < 2.0) {
            evaluateType = "差评";
        } else if (score >= 4.0) {
            evaluateType = "好评";
        } else {
            evaluateType = "中评";
        }
        bean.setScore(score);
        bean.setEvaluateType(evaluateType);
        String orderNo = bean.getOrderNo();
        if(StringUtils.isBlank(orderNo)){
        	return -1;
        }
        int result= productEvaluateMapper.insertSelective(bean);
        if(result> 0){        	
        	OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
        	if(orderBaseInfo != null && orderBaseInfo.getOrderState() == OrderConstact.OrderStatusEnum.WAITEVALUATE.getCode()){
	        	orderBaseInfo.setOrderState(OrderConstact.OrderStatusEnum.COMMENTED.getCode());
	        	orderBaseInfo.setUpdateTime(DateUtils.getCurrentDate());
	        	return orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
        	}
        }
        return -1;
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        ProductEvaluate bean = productEvaluateMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return productEvaluateMapper.updateByPrimaryKey(bean);
    }

    @Override
    public Map<String, Object> getSellerAllScoreInfo(String sellerId) {
        Map<String,Object> resultMap = productEvaluateMapper.getSellerAllScoreInfo(sellerId);
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> getEvaluateList(Map<String, Object> map) {
        String evaluateType = MapUtils.getString(map,"evaluateType");
        if (StringUtils.isNotBlank(evaluateType)){
            switch (evaluateType){
                case "haveImg" :
                    map.put("haveImg","haveImg");
                    map.remove("evaluateType");
                    break;
                case "noReply" :
                    map.put("noReply","noReply");
                    map.remove("evaluateType");
                    break;
                default :
            }
        }
        List<Map<String,Object>> resultList = productEvaluateMapper.getEvaluateList(map);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map<String, Object> evaluate : resultList) {
                String images = MapUtils.getString(evaluate,"IMAGES");
                if (StringUtils.isNotBlank(images)) {
                    String[] args = images.split(",");
                    List<String> imageList = new ArrayList<>();
                    CollectionUtils.addAll(imageList,args);
                    evaluate.put("IMAGES",imageList);
                }
            }
        }
        return resultList;
    }

    @Override
    public int deleteReply(String evaluateId) {
        ProductEvaluate productEvaluate = productEvaluateMapper.selectByPrimaryKey(evaluateId);
        if (productEvaluate == null){
            return 0;
        } else {
            productEvaluate.setReplayState(0);
            productEvaluate.setReplayContent(null);
            productEvaluate.setReplayTime(null);
            productEvaluate.setUpdateTime(new Date());
            productEvaluateMapper.updateByPrimaryKey(productEvaluate);
            return 1;
        }
    }

}
