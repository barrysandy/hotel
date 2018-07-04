package com.zzk.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.util.DateUtils;
import com.zzk.util.UuidUtil;
import com.zzk.dao.HotelAccountMapper;
import com.zzk.entity.HotelAccount;
import com.zzk.service.HotelAccountService;


@Service("hotelAccountService")
public class HotelAccountServiceImpl implements HotelAccountService {
	@Resource
	private HotelAccountMapper hotelAccountMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return hotelAccountMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(HotelAccount record) {
		record.setId(UuidUtil.uuidStr());
		record.setCreateDate(DateUtils.getDate());
		return hotelAccountMapper.insert(record);
	}

	@Override
	public int insertSelective(HotelAccount record) {
		return hotelAccountMapper.insertSelective(record);
	}

	@Override
	public HotelAccount selectByPrimaryKey(String id) {
		return hotelAccountMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(HotelAccount record) {
		return hotelAccountMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(HotelAccount record) {
		return hotelAccountMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<HotelAccount> selectByShopId(String shopId) {
		return hotelAccountMapper.selectByShopId(shopId);
	}

	@Override
	public HotelAccount selectByHotelPayMode(String shopId, int payMode) {
		return hotelAccountMapper.selectByHotelPayMode(shopId, payMode);
	}

}
