package com.zzk.service;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

import com.zzk.dao.ProductStockInfoMapper;
import com.zzk.entity.ProductStockInfo;
import com.zzk.util.DateUtils;
import com.zzk.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import com.zzk.dao.GoodsSkuInfoMapper;
import com.zzk.entity.GoodsSkuInfo;

/**
 * 单品信息表
 *
 * @author: Kun
 * @date: 2018-03-06 15:16
 */
@Service("goodsSkuInfoService")
public class GoodsSkuInfoServiceImpl implements GoodsSkuInfoService {

    @Resource
    private GoodsSkuInfoMapper goodsSkuInfoMapper;
    @Resource
    private ProductStockInfoMapper productStockInfoMapper;
    @Resource
    private OperateLogService operateLogService;
    /**
     * 分页查询
     */
    @Override
    public List<GoodsSkuInfo> selectByPage(Map map) {
        return goodsSkuInfoMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return goodsSkuInfoMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public GoodsSkuInfo selectByPrimaryKey(String id) {
        return goodsSkuInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(GoodsSkuInfo bean) {
        return goodsSkuInfoMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(GoodsSkuInfo bean) {
        return goodsSkuInfoMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        GoodsSkuInfo bean = goodsSkuInfoMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return goodsSkuInfoMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public List<Map<String, Object>> listGoodsListByProductId(String productId) {
        List<Map<String, Object>> resultList = goodsSkuInfoMapper.listGoodsListByProductId(productId);
        for (Map<String, Object> stringObjectMap : resultList) {
            Date updateTime = (Date) stringObjectMap.get("UPDATE_TIME");
            if (updateTime != null) {
                String updateTimeStr = DateUtils.changeDateToStr(updateTime, null);
                stringObjectMap.put("UPDATE_TIME", updateTimeStr);
            }
        }
        return resultList;
    }

    @Override
    public void insertSkuInfo(String skuId, String productId, String skuName, List<ProductStockInfo> list) {
        /*新增单品信息*/
        GoodsSkuInfo goodsSkuInfo = new GoodsSkuInfo();
        goodsSkuInfo.setId(skuId);
        goodsSkuInfo.setProductId(productId);
        goodsSkuInfo.setSkuName(skuName);
        goodsSkuInfo.setStatus(1);
        goodsSkuInfo.setState(1);
        goodsSkuInfo.setCreateTime(new Date());
        goodsSkuInfo.setUpdateTime(new Date());
        goodsSkuInfoMapper.insertSelective(goodsSkuInfo);
        for (ProductStockInfo productStockInfo : list) {
            productStockInfo.setSkuId(skuId);
        }
        
        productStockInfoMapper.insertBatch(list);
        operateLogService.recordLog("新增了单品信息，单品名称："+skuName, skuId, 1);
    }

    @Override
    public void updateSkuInfo(String skuId, String skuName, List<ProductStockInfo> list) {

        GoodsSkuInfo goodsSkuInfo = goodsSkuInfoMapper.selectByPrimaryKey(skuId);
        goodsSkuInfo.setSkuName(skuName);
        goodsSkuInfo.setUpdateTime(new Date());
        goodsSkuInfoMapper.updateByPrimaryKeySelective(goodsSkuInfo);
        /*删除旧的*/
        productStockInfoMapper.deleteOldInfoBySkuId(skuId);
        /*插入新的*/
        if (CollectionUtils.isNotEmpty(list)) {
            for (ProductStockInfo productStockInfo : list) {
                productStockInfo.setSkuId(skuId);
            }
        }
        productStockInfoMapper.insertBatch(list);
        operateLogService.recordLog("修改单品信息，单品名称："+skuName, skuId, 2);
    }

    @Override
    public List<Map<String, Object>> listLineGroupInfo(Map map) {
        List<Map<String, Object>> resultList = productStockInfoMapper.listLineGroupInfo(map);
        return resultList;
    }

    @Override
    public List<Map<String, Object>> listSku(String productId) {
        List<Map<String,Object>> resultList = goodsSkuInfoMapper.listSku(productId);
        if (CollectionUtils.isNotEmpty(resultList)){
            for (Map<String, Object> map : resultList) {
                String skuId = (String) map.get("skuId");
                Map<String,Object> dayAndHours = goodsSkuInfoMapper.getLimitTimeBySkuId(skuId);
                Integer days = MapUtils.getInteger(dayAndHours,"days",0);
                Integer hours = MapUtils.getInteger(dayAndHours,"hours",0);
                Map<String,Object> paraMap = new HashMap<>(2);
                paraMap.put("skuId",skuId);
                paraMap.put("hour",days*24 + hours);
                List<Map<String,Object>> stockInfo = productStockInfoMapper.getSkuStockInfo(paraMap);
                if (CollectionUtils.isNotEmpty(stockInfo)){
                    for (Map<String, Object> stringObjectMap : stockInfo) {
                        String groupTime = MapUtils.getString(stringObjectMap,"groupTime");
                        stringObjectMap.put("groupTime",groupTime);
                    }
                }
                map.put("calendarInfo",stockInfo);
            }
        }
        return resultList;
    }

    @Override
    public List<Map<String,Object>> getSkuStockInfo(String skuId) {
        Map<String,Object> dayAndHours = goodsSkuInfoMapper.getLimitTimeBySkuId(skuId);
        Integer days = MapUtils.getInteger(dayAndHours,"days");
        Integer hours = MapUtils.getInteger(dayAndHours,"hours");
        Map<String,Object> paraMap = new HashMap<>(2);
        paraMap.put("skuId",skuId);
        paraMap.put("hour",days*24 + hours);
        List<Map<String,Object>> resultList = productStockInfoMapper.getSkuStockInfo(paraMap);
        /*if (resultList != null) {
            for (int i = 0; i< resultList.size(); i++) {
                Map<String,Object> stockInfo = resultList.get(i);
                String groupTime = MapUtils.getString(stockInfo,"groupTime");
                Date nowDate = new Date();
                String now = DateUtils.changeDateToStr(nowDate,"yyyy-MM-dd");
                // 现在发团时间只有年月日
                if (StringUtils.equals(groupTime,now)) {
                    Map<String,Object> dayAndHours = goodsSkuInfoMapper.getLimitTimeBySkuId(skuId);
                    Integer days = MapUtils.getInteger(dayAndHours,"days");
                    Integer hours = MapUtils.getInteger(dayAndHours,"hours");
                    Date startTime = DateUtils.changeStrToDate(groupTime);
                    Long diff = startTime.getTime() - nowDate.getTime();
                    // 计算出今天距发团日期还有多少小时
                    long hour = diff % (1000*24*60*60) / (1000 * 60 *60);
                    if (hour < days*24 + hours ) {
                        resultList.remove(i);
                    }
                }
            }
        }*/
        return resultList;
    }

    @Override
    public Map<String, Object> toSkuUpdatePage(String skuId) {
        Map<String,Object> resultMap = new HashMap<>(2);
        GoodsSkuInfo goodsSkuInfo = goodsSkuInfoMapper.selectByPrimaryKey(skuId);
        if (goodsSkuInfo != null){
            String skuName = goodsSkuInfo.getSkuName();
            resultMap.put("skuName",skuName);
            resultMap.put("productId",goodsSkuInfo.getProductId());
        }
        List<Map<String,Object>> stockInfo = productStockInfoMapper.getSkuStockInfoOld(skuId);
        resultMap.put("calendarInfo",stockInfo);
        return resultMap;
    }

    @Override
    public int saveOrUpdateSkuInfo(String data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        String skuName = jsonObject.optString("skuName");
        JSONArray jsonArray = jsonObject.getJSONArray("skuData");
        String skuId = jsonObject.optString("skuId","");
        String productId = jsonObject.optString("productId");
        Integer days = jsonObject.optInt("days");
        if (StringUtils.isBlank(skuId)){
            //新增
            GoodsSkuInfo goodsSkuInfo = new GoodsSkuInfo();
            skuId = StringUtils.getUUID();
            goodsSkuInfo.setId(skuId);
            goodsSkuInfo.setSkuName(skuName);
            goodsSkuInfo.setProductId(productId);
            goodsSkuInfo.setStatus(1);
            goodsSkuInfo.setState(1);
            goodsSkuInfo.setCreateTime(new Date());
            goodsSkuInfo.setUpdateTime(new Date());
            goodsSkuInfoMapper.insertSelective(goodsSkuInfo);

            JSONObject object;
            List<ProductStockInfo> productStockInfoList = new ArrayList<>();
            for (int i=0 ; i < jsonArray.size() ; i++){
                object = jsonArray.getJSONObject(i);
                ProductStockInfo productStockInfo = new ProductStockInfo();
                productStockInfo.setId(StringUtils.getUUID());
                productStockInfo.setSkuId(skuId);
                productStockInfo.setAdultNumber(object.optInt("adultNumber"));
                productStockInfo.setChildNumber(object.optInt("childNumber"));
                productStockInfo.setOriginalPrice(new BigDecimal(object.optDouble("originalPrice",-1)));
                productStockInfo.setAdultSellPrice(new BigDecimal(object.optDouble("adultSellPrice")));
                productStockInfo.setChildSellPrice(new BigDecimal(object.optDouble("childSellPrice")));
                /*发团日期,结束日期*/
                String startTime = object.optString("groupTime");
                if (StringUtils.isNotBlank(startTime)){
                    Date startDate = DateUtils.changeStrToDate(startTime);
                    Date endDate = DateUtils.getBeforeOrAfterDate(startDate,days);
                    productStockInfo.setStartTime(startDate);
                    productStockInfo.setEndTime(endDate);
                }
                productStockInfo.setStatus(1);
                productStockInfo.setCreateTime(new Date());
                productStockInfoList.add(productStockInfo);
            }
            productStockInfoMapper.insertBatch(productStockInfoList);
            operateLogService.recordLog("新增发团信息，单品名称："+skuName, skuId, 1);
        } else {
            //修改
            GoodsSkuInfo goodsSkuInfo = goodsSkuInfoMapper.selectByPrimaryKey(skuId);
            goodsSkuInfo.setSkuName(skuName);
            goodsSkuInfo.setUpdateTime(new Date());
            goodsSkuInfoMapper.updateByPrimaryKeySelective(goodsSkuInfo);
            //删除旧的发团信息
            productStockInfoMapper.deleteOldInfoBySkuId(skuId);

            JSONObject object;
            List<ProductStockInfo> productStockInfoList = new ArrayList<>();
            for (int i=0 ; i < jsonArray.size() ; i++){
                object = jsonArray.getJSONObject(i);
                ProductStockInfo productStockInfo = new ProductStockInfo();
                productStockInfo.setId(StringUtils.getUUID());
                productStockInfo.setSkuId(skuId);
                productStockInfo.setAdultNumber(object.optInt("adultNumber"));
                productStockInfo.setChildNumber(object.optInt("childNumber"));
                productStockInfo.setOriginalPrice(new BigDecimal(object.optDouble("originalPrice",-1)));
                productStockInfo.setAdultSellPrice(new BigDecimal(object.optDouble("adultSellPrice")));
                productStockInfo.setChildSellPrice(new BigDecimal(object.optDouble("childSellPrice")));
                String startTime = object.optString("groupTime");
                if (StringUtils.isNotBlank(startTime)){
                    Date startDate = DateUtils.changeStrToDate(startTime);
                    Date endDate = DateUtils.getBeforeOrAfterDate(startDate,days);
                    productStockInfo.setStartTime(startDate);
                    productStockInfo.setEndTime(endDate);
                }
                productStockInfo.setStatus(1);
                productStockInfo.setCreateTime(new Date());
                productStockInfoList.add(productStockInfo);
            }
            productStockInfoMapper.insertBatch(productStockInfoList);
            operateLogService.recordLog("修改发团信息，单品名称："+skuName, skuId, 2);
        }
        return 0;
    }

    @Override
    public int updateSkuState(String skuId, Integer state) {
        GoodsSkuInfo goodsSkuInfo = goodsSkuInfoMapper.selectByPrimaryKey(skuId);
        goodsSkuInfo.setState(state);
        goodsSkuInfo.setUpdateTime(new Date());
        return goodsSkuInfoMapper.updateByPrimaryKeySelective(goodsSkuInfo);
    }

}
