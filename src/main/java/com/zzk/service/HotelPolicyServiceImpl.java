package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.HotelPolicyMapper;
import com.zzk.entity.HotelPolicy;
import com.zzk.service.HotelPolicyService;

/**
 * 
 * 酒店政策信息
* @author：huashuwen
* @date：2017-11-17 11:46
 */
@Service("hotelPolicyService")
public class HotelPolicyServiceImpl implements HotelPolicyService {
	@Resource
	private HotelPolicyMapper hotelPolicyMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<HotelPolicy> selectByPage(Map map) {
		return hotelPolicyMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return hotelPolicyMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public HotelPolicy selectByPrimaryKey(String id) {
		return hotelPolicyMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(HotelPolicy bean) {
		return hotelPolicyMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-17 11:46
	 */
	@Override
	public int insert(HotelPolicy bean) {
		return hotelPolicyMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-17 11:46
	 */
	@Override
	public int delete(String id) {
		HotelPolicy bean = hotelPolicyMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return hotelPolicyMapper.updateByPrimaryKey(bean);
	}

	@Override
	public HotelPolicy selectByHotelId(String hotelId) {
		return hotelPolicyMapper.selectByHotelId(hotelId);
	}

}
