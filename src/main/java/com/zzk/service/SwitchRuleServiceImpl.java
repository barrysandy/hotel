package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.SwitchRuleMapper;
import com.zzk.entity.SwitchRule;
import com.zzk.service.SwitchRuleService;

/**
 * 
 * 房型开关
* @author：huashuwen
* @date：2018-01-05 10:03
 */
@Service("switchRuleService")
public class SwitchRuleServiceImpl implements SwitchRuleService {
	@Resource
	private SwitchRuleMapper switchRuleMapper;
	
	
	/**
	 * 分页查询
	 */
	@Override
	public List<SwitchRule> selectByPage(Map map) {
		return switchRuleMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return switchRuleMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public SwitchRule selectByPrimaryKey(String id) {
		return switchRuleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(SwitchRule bean) {
		return switchRuleMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-01-05 10:03
	 */
	@Override
	public int insert(SwitchRule bean) {
		int result = switchRuleMapper.insert(bean);
		
		return result;
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-01-05 10:03
	 */
	@Override
	public int delete(String id) {
		SwitchRule bean = switchRuleMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return switchRuleMapper.updateByPrimaryKey(bean);
	}
	
	@Override
	public List<SwitchRule> selectByHotelId(String hotelId, String startTime) {
		
		return switchRuleMapper.selectByHotelId(hotelId,startTime);
	}

	@Override
	public List<SwitchRule> selectByGoodsId(String goodsId, String startTime) {
		
		return switchRuleMapper.selectByGoodsId(goodsId,startTime);
	}

	@Override
	public List<SwitchRule> selectByHotelsId(List<String> idList,
			String startTime, String endTime) {
		
		return switchRuleMapper.selectByHotelsId(idList, startTime,endTime);
	}

}
