package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.HotelServeMapper;
import com.zzk.entity.HotelServe;
import com.zzk.service.HotelServeService;

/**
 * 
 * 酒店服务设施信息
* @author：huashuwen
* @date：2017-11-17 14:21
 */
@Service("hotelServeService")
public class HotelServeServiceImpl implements HotelServeService {
	@Resource
	private HotelServeMapper hotelServeMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<HotelServe> selectByPage(Map map) {
		return hotelServeMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return hotelServeMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public HotelServe selectByPrimaryKey(String id) {
		return hotelServeMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(HotelServe bean) {
		return hotelServeMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-17 14:21
	 */
	@Override
	public int insert(HotelServe bean) {
		return hotelServeMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-17 14:21
	 */
	@Override
	public int delete(String id) {
		HotelServe bean = hotelServeMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return hotelServeMapper.updateByPrimaryKey(bean);
	}

	@Override
	public HotelServe selectByHotelId(String hotelId) {
		return hotelServeMapper.selectByHotelId(hotelId);
	}

	@Override
	public List<HotelServe> selectByHotelIds(List<String> hotelIds) {
		// TODO Auto-generated method stub
		return hotelServeMapper.selectByHotelIds(hotelIds);
	}

}
