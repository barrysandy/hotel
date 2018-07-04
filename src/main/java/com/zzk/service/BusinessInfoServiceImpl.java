package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.BusinessInfoMapper;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.Hotel;
import com.zzk.util.JsonUtils;

/**
 * 用户表
* @author: wangpeng
* @date: 2018-03-12 18:16
 */
@Service("businessInfoService")
public class BusinessInfoServiceImpl implements BusinessInfoService {

	@Resource
	private BusinessInfoMapper businessInfoMapper;
	@Resource
	private HotelService hotelService;
	/**
	 * 分页查询
	 */
	@Override
	public List<BusinessInfo> selectByPage(Map map) {
		return businessInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return businessInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public BusinessInfo selectByPrimaryKey(String id) {
		return businessInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(BusinessInfo bean) {
		BusinessInfo rule = businessInfoMapper.selectByPrimaryKey(bean.getId());
		rule.setBusinName(bean.getBusinName());
		rule.setUpdateTime(new Date());
        rule.setPersonName(bean.getPersonName());
        rule.setPhone(bean.getPhone());
        rule.setEmail(bean.getEmail());
        rule.setTel(bean.getTel());
        rule.setCoverImg(bean.getCoverImg());
        rule.setAlbum(bean.getAlbum());
        rule.setDescription(bean.getDescription());
        rule.setCityId(bean.getCityId());
        rule.setAreaId(bean.getAreaId());
        rule.setLat(bean.getLat());
        rule.setLon(bean.getLon());
        rule.setAddress(bean.getAddress());
        hotelService.updateHotel(rule);
		return businessInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(BusinessInfo bean) {
		return businessInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		BusinessInfo bean = businessInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return businessInfoMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 获取商户信息
	 */
	@Override
	public String selectByPrimaryKeyInfo(String id) {
		BusinessInfo bean = businessInfoMapper.selectByPrimaryKey(id);
		if(bean != null){
			return JsonUtils.lineJsonData(1, "success", "获取成功", bean);
		}
		return JsonUtils.lineJsonData(0, "error", "信息不存在", null);
	}
	/**
	 * 修改商户信息
	 */
	@Override
	public String updateBusinessInfo(BusinessInfo business) {
		String id = business.getId();
		BusinessInfo bean = businessInfoMapper.selectByPrimaryKey(id);
		if(bean == null){
			JsonUtils.lineJsonData(0, "error", "账户信息不存在", null);
		}
		BusinessInfo info = new BusinessInfo();
		info.setId(bean.getId());
		info.setBusinName(business.getBusinName());
		info.setSimpleName(business.getSimpleName());
		info.setBusinName(business.getBusinMain());
		info.setAddress(business.getAddress());
		info.setCoverImg(business.getCoverImg());
		info.setAlbum(business.getAlbum());
		info.setDescription(business.getDescription());
		info.setUpdateTime(new Date());
		info.setLat(business.getLat());
		info.setLon(business.getLon());
		info.setCityId(business.getCityId());
		info.setAreaId(business.getAreaId());
		hotelService.updateHotel(business);
		int resultCode=	businessInfoMapper.updateByPrimaryKeySelective(info);
		
		if(resultCode>0){
			BusinessInfo businessInfo = businessInfoMapper.selectByPrimaryKey(info.getId());
			if(businessInfo == null){
				JsonUtils.lineJsonData(1, "error", "内部错误", null);
			}
			return JsonUtils.lineJsonData(1, "success", "修改成功", businessInfo);
		}
	return JsonUtils.lineJsonData(1, "error", "账户信息不存在", null);
	}
	
	@Override
	public List<Map<String,Object>> getProvinceList() {
		return businessInfoMapper.getProvinceList();
	}

	@Override
	public List<Map<String, Object>> getCityList(String provinceId) {
		return businessInfoMapper.getCityList(provinceId);
	}

	@Override
	public List<Map<String, Object>> getAreaList(String cityId) {
		return businessInfoMapper.getAreaList(cityId);
	}
	@Override
	public String selecCity(String cityId) {
		
		return businessInfoMapper.selectCity(cityId);
	}
	@Override
	public String selecProvince(String provinceId) {
		return businessInfoMapper.selectProvince(provinceId);
	}
	@Override
	public String selectArea(String areaId) {
		return businessInfoMapper.selectArea(areaId);
	}
}
