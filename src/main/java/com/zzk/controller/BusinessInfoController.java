package com.zzk.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zzk.util.DateUtils;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.zzk.vo.BusinessInfoVo;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.CooperationInfo;
import com.zzk.entity.QualificationInfo;
import com.zzk.service.AccessService;
import com.zzk.service.BusinessInfoService;
import com.zzk.service.CooperationInfoService;
import com.zzk.service.OrderBaseInfoService;
import com.zzk.service.QualificationInfoService;

/**
 * 商户信息
 *
 * @name: BusinessInfoController
 * @author: wangpeng
 * @date: 2018-03-12 18:16
 */
@Controller
@RequestMapping(value = "/businessInfo")
public class BusinessInfoController extends BaseController {

    @Resource
    private BusinessInfoService businessInfoService;
    @Resource
    private QualificationInfoService qualificationInfoService;
    @Resource
    private CooperationInfoService cooperationInfoService;
    @Resource
    private AccessService accessService;
    @Resource
    private OrderBaseInfoService orderBaseInfoService;

    /**
     * 商户信息
     *
     * @param id
     * @return view
     * @author hua
     * @date 2018-03-12 18:16
     */
    @RequestMapping("/toView")
    @ResponseBody
    public Result<BusinessInfo> toView(String id) {
        BusinessInfo rule = new BusinessInfo();
        if (StringUtils.isNotBlank(id)) {
            rule = businessInfoService.selectByPrimaryKey(id);
            if (rule != null) {
                String areaId = rule.getAreaId();
                String cityId = rule.getCityId();
                String shortProvinceId = "";
                if (StringUtils.isNotBlank(cityId)) {
                    shortProvinceId = cityId.substring(0, 2);
                    rule.setProvinceId(shortProvinceId + "0000");
                }
                String cityName = businessInfoService.selecCity(cityId);
                String areaName = businessInfoService.selectArea(areaId);
                String provinceName = businessInfoService.selecProvince(shortProvinceId);

                rule.setAreaName(areaName);
                rule.setCityName(cityName);
                rule.setProvinceName(provinceName);

                // B端 - 设置 - 商家信息 用到了这个接口, 增加两个返回的东西
                String businessMain = rule.getBusinMain();
                if (StringUtils.isNotBlank(businessMain)) {
                    businessMain = businessMain.replaceAll("，", ",");
                    String[] args = businessMain.split(",");
                    rule.setBusinessType(args);
                }
                String tel = rule.getTel();
                if (StringUtils.isNotBlank(tel)) {
                    tel = tel.replaceAll("，", ",");
                    String[] tels = tel.split(",");
                    rule.setTels(tels);
                }
                return new Result<>(1, "success", "获取成功", rule);
            }
        }
        return new Result<>(0, "error", "获取失败", null);
    }

    /**
     * 资质信息
     *
     * @param merchantId 商家ID
     * @return view
     * @author hua
     * @date 2018-03-12 18:16
     */
    @RequestMapping("/getQualificationInfo")
    @ResponseBody
    public Result<QualificationInfo> getQualificationInfo(String merchantId) {
        QualificationInfo rule = new QualificationInfo();
        if (StringUtils.isNotBlank(merchantId)) {
            rule = qualificationInfoService.selectByMerchantId(merchantId);
            if (rule != null) {
                return new Result<>(1, "success", "获取成功", rule);
            } else {
                return new Result<>(0, "error", "无记录", null);
            }
        } else {
            return new Result<>(0, "error", "merchantId为空", null);
        }

    }

    /**
     * 用户表保存操作
     *
     * @param bean 实体类
     * @return String
     * @author wangpeng
     * @date 2018-03-12 18:16
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(BusinessInfo bean) {
        try {
            if (bean != null) {
                String id = bean.getId();
                if (StringUtils.isNotBlank(id)) {
                    BusinessInfo rule = new BusinessInfo();
                    if (StringUtils.isNotBlank(id)) {
                        rule = businessInfoService.selectByPrimaryKey(id);
                    }
                    bean.setCreateTime(rule.getCreateTime());
                    bean.setUpdateTime(new Date());
                    bean.setStatus(1);
                    businessInfoService.update(bean);
                } else {
                    UUID u = UUID.randomUUID();
                    bean.setStatus(1);
                    bean.setId(u.toString());
                    bean.setCreateTime(new Date());
                    businessInfoService.insert(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.turnJson(false, "error" + e.getMessage(), e);
        }
        return JsonUtils.turnJson(true, "success", null);
    }

    /**
     * 用户表删除
     *
     * @param id 主键ID
     * @return String
     * @author wangpeng
     * @date 2018-03-12 18:16
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id) {
        try {
            businessInfoService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.turnJson(false, "error" + e.getMessage(), e);
        }
        return JsonUtils.turnJson(true, "success", null);
    }

    /**
     * 获取商户信息
     *
     * @param id
     * @return
     * @author John
     * @date： 2018年3月12日 下午6:54:28
     */
    @RequestMapping("/queryInfo")
    @ResponseBody
    public String queryItem(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonUtils.lineJsonData(0, "error", "id不能为空", null);
        }
        return businessInfoService.selectByPrimaryKeyInfo(id);
    }

    /**
     * 修改商户信息
     *
     * @param business
     * @return
     * @author John
     * @date： 2018年3月12日 下午7:26:01
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(BusinessInfo business) {
        if (business == null || StringUtils.isBlank(business.getId())) {
            return JsonUtils.lineJsonData(0, "error", "参数错误", null);
        }
        return businessInfoService.updateBusinessInfo(business);

    }

    /**
     * 修改供应商信息
     *
     * @param bean
     * @return
     * @author hua
     * @date： 2018年3月12日 下午7:26:01
     */
    @RequestMapping("/updateSupplier")
    @ResponseBody
    public Result<Object> updateSupplier(@RequestBody BusinessInfo bean) {
        String id = bean.getId();
        if (!StringUtils.isEmpty(id)) {
            businessInfoService.update(bean);
        } else {
            return new Result<>(0, "error", "id为空");
        }
        return new Result<>(1, "success", "保存成功");
    }

    /**
     * 修改供应商信息
     *
     * @param bean
     * @return
     * @author hua
     * @date： 2018年3月12日 下午7:26:01
     */
    @RequestMapping("/updateBusinessInfo")
    @ResponseBody
    public Result<Object> updateBusinessInfo(@RequestBody String bean) {
        try {
            if (StringUtils.isBlank(bean) || !bean.startsWith("{")) {
                return new Result<>(0, "fail", "参数错误");
            } else {
                JSONObject jsonObject = JSONObject.fromObject(bean);
                String id = jsonObject.optString("id");
                BusinessInfo rule = businessInfoService.selectByPrimaryKey(id);
                if (rule == null) {
                    return new Result<>(0,"fail","此商家信息有误,检查ID");
                }
                rule.setBusinName(jsonObject.optString("businessName"));
                rule.setSimpleName(jsonObject.optString("simpleName"));
                JSONArray businessType = jsonObject.getJSONArray("type");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < businessType.size(); i++) {
                    String item = businessType.getString(i);
                    if (sb.length() < 1) {
                        sb.append(item);
                    } else {
                        sb.append(",");
                        sb.append(item);
                    }
                }
                rule.setBusinMain(sb.toString());
                rule.setCoverImg(jsonObject.optString("coverImg"));
                rule.setAlbum(jsonObject.optString("album"));
                rule.setStatus(1);
                rule.setUpdateTime(new Date());
                rule.setDescription(jsonObject.optString("description"));
                rule.setAddress(jsonObject.optString("address"));
                rule.setCityId(jsonObject.optString("cityId"));
                rule.setCityName(jsonObject.optString("cityName"));
                rule.setProvinceId(jsonObject.optString("provinceId"));
                rule.setProvinceName(jsonObject.optString("provinceName"));
                rule.setAreaId(jsonObject.optString("areaId"));
                rule.setAreaName(jsonObject.optString("areaName"));
                JSONArray telArray = jsonObject.getJSONArray("tels");
                StringBuilder telStr = new StringBuilder();
                for (int i = 0; i < telArray.size(); i++) {
                    String item = telArray.getString(i);
                    if (telStr.length() < 1) {
                        telStr.append(item);
                    } else {
                        telStr.append(",");
                        telStr.append(item);
                    }
                }
                rule.setTel(telStr.toString());
                businessInfoService.update(rule);
                return new Result<>(1,"success","操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<Object>(0, "error", "内部异常",e);
        }
    }



    /**
     * 修改资质信息
     *
     * @param bean
     * @return
     * @author hua
     * @date： 2018年3月12日 下午7:26:01
     */
    @RequestMapping("/updateDocument")
    @ResponseBody
    public Result<Object> updateDocument(@RequestBody QualificationInfo bean) {
        String merchantId = bean.getMerchantId();
        if (!StringUtils.isEmpty(merchantId)) {
            QualificationInfo rule = qualificationInfoService.selectByMerchantId(merchantId);
            if (rule == null) {
                bean.setCreateTime(new Date());
                bean.setStatus(1);
                bean.setId(UUID.randomUUID().toString());
                qualificationInfoService.insert(bean);
            } else {
                bean.setUpdateTime(new Date());
                bean.setUpdateTime(new Date());
                bean.setId(rule.getId());
                qualificationInfoService.update(bean);
            }
        } else {
            return new Result<>(0, "error", "merchantId为空");
        }
        return new Result<>(1, "success", "保存成功");
    }

    /**
     * 修改合作信息
     *
     * @param bean
     * @return
     * @author hua
     * @date： 2018年3月12日 下午7:26:01
     */
    @RequestMapping("/updateCooperate")
    @ResponseBody
    public Result<Object> updateCooperate(@RequestBody CooperationInfo bean) {
        String businnessId = bean.getBusinnessId();
        if (!StringUtils.isEmpty(businnessId)) {
            CooperationInfo rule = cooperationInfoService.selectByBusinessId(businnessId);
            if (rule == null) {
            	if(bean.getCommRate()==null){
            		bean.setCommRate(new Double(0));
            	}
                bean.setCreateTime(new Date());
                bean.setStatus(1);
                bean.setId(UUID.randomUUID().toString());
                cooperationInfoService.insert(bean);
            } else {
            	if(bean.getCommRate()==null){
            		bean.setCommRate(new Double(0));
            	}
                bean.setUpdateTime(new Date());
                bean.setUpdateTime(new Date());
                bean.setId(rule.getId());
                cooperationInfoService.update(bean);
            }

        } else {
            return new Result<>(0, "error", "businnessId为空");
        }
        return new Result<>(1, "success", "保存成功");
    }

    @RequestMapping("/getProvinceList")
    @ResponseBody
    public Result<List<Map<String, Object>>> getProvinceList() {
        Result<List<Map<String, Object>>> result = new Result<List<Map<String, Object>>>();
        List<Map<String, Object>> list = businessInfoService.getProvinceList();
        result.setData(list);
        result.setMsg("success");
        result.setState(1);
        return result;
    }

    @RequestMapping("/getCityList")
    @ResponseBody
    public Result<List<Map<String, Object>>> getCityList(String provinceId) {
        Result<List<Map<String, Object>>> result = new Result<List<Map<String, Object>>>();
        List<Map<String, Object>> list = businessInfoService.getCityList(provinceId);
        result.setData(list);
        result.setMsg("success");
        result.setState(1);
        return result;
    }

    @RequestMapping("/getAreaList")
    @ResponseBody
    public Result<List<Map<String, Object>>> getAreaList(String cityId) {
        Result<List<Map<String, Object>>> result = new Result<List<Map<String, Object>>>();
        List<Map<String, Object>> list = businessInfoService.getAreaList(cityId);
        result.setData(list);
        result.setMsg("success");
        result.setState(1);
        return result;
    }
    
    @RequestMapping("/selectTotalAccess")
	@ResponseBody
	public Result selectTotalAccess(int resourceType,String sellerId){
		
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime = new Date();
		List<String> dateList = new ArrayList<String>();
		String date = "";
		String startDate;
		
		
	    Result<Map<String,Object>> result = new Result<Map<String,Object>>();
	    Map<String,Object> resultMap = new HashMap<String,Object>();
	    
	    List<Double> percentList = new ArrayList<Double>();
	    Map map = new HashMap<>();
	    Map map2 = new HashMap<>();
	    map.put("sellerId", sellerId);
	    map.put("resourceType", resourceType);
		map.put("accessTime", currentTime); 
		map2.put("createTime", currentTime);
		map2.put("sellerId", sellerId);
		List numList = new ArrayList();
		try {
			numList = accessService.selectTotalNumber(map);
			percentList = orderBaseInfoService.selectDailyCountByShopId(map2,numList);
			
			startDate = DateUtils.getAfterDay(sdf.format(currentTime), -6, "yyyy-MM-dd");
			for(int i=0;i<=6;i++){
				date = DateUtils.getAfterDay(startDate, i, "yyyy-MM-dd");
				dateList.add(date);
			}
		} catch (Exception e1) {
			return new Result(0,"error",e1.toString(),null);
		}
		
		resultMap.put("num", numList);
		resultMap.put("date", dateList);
		resultMap.put("percent", percentList);
		result.setData(resultMap);
		result.setMsg("success");
		result.setState(1); 
		return result;
	}

}
