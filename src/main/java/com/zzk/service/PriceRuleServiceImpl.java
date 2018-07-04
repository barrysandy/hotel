package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.PriceRuleMapper;
import com.zzk.entity.PriceRule;
import com.zzk.service.HotelService;
import com.zzk.service.PriceRuleService;

/**
 * 
 * 价格规则信息
* @author：sty
* @date：2017-11-02 10:40
 */
@Service("priceRuleService")
public class PriceRuleServiceImpl implements PriceRuleService {
	@Resource
	private PriceRuleMapper priceRuleMapper;
	@Resource
	private HotelService hotelService;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<PriceRule> selectByPage(Map map) {
		return priceRuleMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return priceRuleMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public PriceRule selectByPrimaryKey(String id) {
		return priceRuleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(PriceRule bean) {
		int state = priceRuleMapper.updateByPrimaryKey(bean);
		
		return state;
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:40
	 */
	@Override
	public int insert(PriceRule bean) {
		int state = priceRuleMapper.insert(bean);
		
		return state;
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:40
	 */
	@Override
	public int delete(String id) {
		PriceRule bean = priceRuleMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		int state = priceRuleMapper.updateByPrimaryKey(bean);
		
		return state;
	}
	/**
	 * 
	* 根据hotelId查询
	* @param goodId
	* @return
	* @return
	* @author huashuwen
	* @date 2017-11-9
	 */
	@Override
	public List<PriceRule> selectByGoodsId(String goodsId,String startTime) {
		return priceRuleMapper.selectByGoodsId(goodsId,startTime);
	}

	@Override
	public List<PriceRule> selectByHotelId(String hotelId, String startTime) {
		
		return priceRuleMapper.selectByHotelId(hotelId,startTime);
	}

	@Override
	public List<PriceRule> selectRuleByHotelList(Map map) {
		return priceRuleMapper.selectRuleByHotelList(map);
	}
	
	@Override
	public List<PriceRule> selectRuleByGoodsList(List list){
		return priceRuleMapper.selectRuleByGoodsList(list);
	}

}
