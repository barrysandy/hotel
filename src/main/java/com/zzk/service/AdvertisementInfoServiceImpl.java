package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.AdvertisementInfoMapper;
import com.zzk.entity.AdvertisementInfo;
import com.zzk.service.AdvertisementInfoService;

/**
 * 
 * 广告信息表
* @author：huashuwen
* @date：2018-03-09 15:29
 */
@Service("advertisementInfoService")
public class AdvertisementInfoServiceImpl implements AdvertisementInfoService {
	@Resource
	private AdvertisementInfoMapper advertisementInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<AdvertisementInfo> selectByPage(Map map) {
		return advertisementInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return advertisementInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public AdvertisementInfo selectByPrimaryKey(String id) {
		return advertisementInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(AdvertisementInfo bean) {
		return advertisementInfoMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:29
	 */
	@Override
	public int insert(AdvertisementInfo bean) {
		return advertisementInfoMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-09 15:29
	 */
	@Override
	public int delete(String id) {
		AdvertisementInfo bean = advertisementInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return advertisementInfoMapper.updateByPrimaryKey(bean);
	}

}
