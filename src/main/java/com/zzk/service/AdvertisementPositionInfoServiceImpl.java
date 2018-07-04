package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.AdvertisementPositionInfoMapper;
import com.zzk.entity.AdvertisementPositionInfo;


/**
 * 
 * 广告位信息表
* @author：huashuwen
* @date：2018-03-09 15:30
 */
@Service("advertisementPositionInfoService")
public class AdvertisementPositionInfoServiceImpl implements AdvertisementPositionInfoService {
	@Resource
	private AdvertisementPositionInfoMapper advertisementPositionInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<AdvertisementPositionInfo> selectByPage(Map map) {
		return advertisementPositionInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return advertisementPositionInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public AdvertisementPositionInfo selectByPrimaryKey(String id) {
		return advertisementPositionInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(AdvertisementPositionInfo bean) {
		return advertisementPositionInfoMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:30
	 */
	@Override
	public int insert(AdvertisementPositionInfo bean) {
		return advertisementPositionInfoMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:30
	 */
	@Override
	public int delete(String id) {
		AdvertisementPositionInfo bean = advertisementPositionInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return advertisementPositionInfoMapper.updateByPrimaryKey(bean);
	}

}
