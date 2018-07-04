package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zzk.dao.SinglePlanMapper;
import com.zzk.entity.SinglePlan;
import com.zzk.util.MethodAnnotation;
import com.zzk.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.zzk.dao.PlanInfoMapper;
import com.zzk.entity.PlanInfo;

/**
 * 行程安排总表
 *
 * @author: Kun
 * @date: 2018-03-06 11:09
 */
@Service("planInfoService")
public class PlanInfoServiceImpl implements PlanInfoService {

    @Resource
    private PlanInfoMapper planInfoMapper;
    @Resource
    private SinglePlanMapper singlePlanMapper;
    @Resource
    private OperateLogService operateLogService;

    /**
     * 分页查询
     */
    @Override
    public List<PlanInfo> selectByPage(Map map) {
        return planInfoMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return planInfoMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public PlanInfo selectByPrimaryKey(String id) {
        return planInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(PlanInfo bean) {
        return planInfoMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(PlanInfo bean) {
        return planInfoMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        PlanInfo bean = planInfoMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return planInfoMapper.updateByPrimaryKey(bean);
    }


    @Override
    public String saveProductPublishPlanInfo(String baseInfo,String productId) {
        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String days = jsonObject.optString("days");
        String startAddress = jsonObject.optString("startAddress");
        String destination = jsonObject.optString("destination");
        String daysLimit = jsonObject.optString("daysLimit");
        String hoursLimit = jsonObject.optString("hoursLimit");
        PlanInfo planInfo = new PlanInfo();
        String planId = StringUtils.getUUID();
        planInfo.setId(planId);
        planInfo.setProductId(productId);
        planInfo.setDays(Integer.valueOf(days));
        planInfo.setDaysLimit(daysLimit);
        planInfo.setHoursLimit(hoursLimit);
        planInfo.setStartAddress(startAddress);
        planInfo.setDestination(destination);
        //1=简洁模式(html编辑器编辑的) 2=详细模式(有单天行程的)
        planInfo.setType(2);
        planInfo.setStatus(1);
        planInfo.setCreateTime(new Date());
        planInfoMapper.insertSelective(planInfo);
        operateLogService.recordLog("新增行程安排", productId, 1);
        return planId;
    }

    @Override
    public void updateProductPublishPlanInfo(String baseInfo) {
        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String productId = jsonObject.optString("productId");
        String days = jsonObject.optString("days");
        String startAddress = jsonObject.optString("startAddress");
        String destination = jsonObject.optString("destination");
        String daysLimit = jsonObject.optString("daysLimit");
        String hoursLimit = jsonObject.optString("hoursLimit");

        PlanInfo planInfo = planInfoMapper.getDetailModePlanInfo(productId);
        planInfo.setProductId(productId);
        planInfo.setDays(Integer.valueOf(days));
        planInfo.setDaysLimit(daysLimit);
        planInfo.setHoursLimit(hoursLimit);
        planInfo.setStartAddress(startAddress);
        planInfo.setDestination(destination);
        planInfo.setUpdateTime(new Date());
        planInfoMapper.updateByPrimaryKeySelective(planInfo);
        operateLogService.recordLog("更新行程安排", productId, 2);
    }

    @Override
    public PlanInfo getDetailModePlanInfo(String productId) {
        return planInfoMapper.getDetailModePlanInfo(productId);
    }

    @Override
    public PlanInfo getConciseModePlanInfo(String productId) {
        return planInfoMapper.getConciseModePlanInfo(productId);
    }

    @Override
    public void saveConciseModePlanInfo(String productId, JSONArray destination, String content) {

		/*通过productId查询是否已有简洁模式的数据(商品在填写基本信息的时候会自动增加一条类型为详细模式的数据)*/
        PlanInfo planInfo = planInfoMapper.getConciseModePlanInfo(productId);
        if (planInfo == null) {
            /*表示第一次添加*/
            planInfo = new PlanInfo();
            planInfo.setId(StringUtils.getUUID());
            planInfo.setContent(content);
            planInfo.setDestination(destination.toString());
            planInfo.setStatus(1);
            planInfo.setProductId(productId);
            planInfo.setCreateTime(new Date());
            planInfo.setType(1);
            planInfoMapper.insert(planInfo);
        } else {
		    /*表示修改*/
            planInfo.setContent(content);
            planInfo.setDestination(destination.toString());
            planInfo.setUpdateTime(new Date());
            planInfoMapper.updateByPrimaryKeySelective(planInfo);
        }

    }

    @Override
    public void saveDetailModePlanInfo(List<Map<String, Object>> list, String productId) {

        PlanInfo planInfo = planInfoMapper.getDetailModePlanInfo(productId);
        /*详细模式的数据是在商品添加基本信息的时候就会有录入,所以这里从正常逻辑上讲不会有为空的情况*/
        /*这里进行一次为空判断是为了增加代码逻辑严谨性,在业务层面上来讲不应该为空*/
        if (planInfo != null) {
            String planId = planInfo.getId();
            List<SinglePlan> oldPlans = singlePlanMapper.listSinglePlanByPlanId(planId);
            List<SinglePlan> singlePlans = new ArrayList<>();
            /*若已有安排删除之前的安排计划添加新的(这种可以避免以前有4天,新的有3天,进行天数对比更新之后第四天的数据仍然存在,简化代码操作)*/
            if (CollectionUtils.isNotEmpty(oldPlans)) {
                singlePlanMapper.deleteByPlanId(planId);
            }

            for (Map<String, Object> map : list) {
                SinglePlan singlePlan = new SinglePlan();
                JSONArray destination = (JSONArray) map.get("destination");
                String day = MapUtils.getString(map,"day");
                String description = MapUtils.getString(map,"description");
                String brightSpot = MapUtils.getString(map,"brightSpot");
                String mileage = MapUtils.getString(map,"mileage");
                singlePlan.setId(StringUtils.getUUID());
                singlePlan.setPlanId(planId);
                singlePlan.setDescription(description);
                singlePlan.setDestination(destination.toString());
                singlePlan.setDaySort(day+"");
                singlePlan.setBrightSpot(brightSpot);
                if (StringUtils.isNotBlank(mileage)){
                    singlePlan.setMileage(Double.valueOf(mileage));
                }
                singlePlan.setStatus(1);
                singlePlan.setCreateTime(new Date());
                singlePlans.add(singlePlan);
            }
            singlePlanMapper.insertBatch(singlePlans);
        }

    }

}
