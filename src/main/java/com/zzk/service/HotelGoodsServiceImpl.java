package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.HotelGoodsMapper;
import com.zzk.entity.HotelGoods;
import com.zzk.service.HotelGoodsService;

/**
 * 
 * 酒店商品信息
* @author：sty
* @date：2017-11-02 10:44
 */
@Service("hotelGoodsService")
public class HotelGoodsServiceImpl implements HotelGoodsService {
	@Resource
	private HotelGoodsMapper hotelGoodsMapper;
	
	/**
	 * 分页查询
	 * 
	 */
	@Override
	public List<HotelGoods> selectByPage(Map map) {
		return hotelGoodsMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return hotelGoodsMapper.selectCount(record);
	}
	 
	/**
	 * 主键查询
	 */
	@Override
	public HotelGoods selectByPrimaryKey(String id) {
		return hotelGoodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(HotelGoods bean) {
		return hotelGoodsMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	@Override
	public int insert(HotelGoods bean) {
		return hotelGoodsMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	@Override
	public int delete(String id) {
		HotelGoods bean = hotelGoodsMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return hotelGoodsMapper.updateByPrimaryKey(bean);
	}

	@Override
	public List<String> selectGoodIds(String hotelId) {
		
		return hotelGoodsMapper.selectGoodIds(hotelId);
	}

	@Override
	public Map<String, Object> selectStockandPricebyId(String id, String date,
			String dayOfWeek) {
		return hotelGoodsMapper.selectStockandPricebyId(id, date, dayOfWeek);
	}
	/**
	 * 查询商品
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:44
	 */
	@Override
	public List<HotelGoods> selectHotelGoods(String hotelId) {
		
		return hotelGoodsMapper.selectHotelGoods(hotelId);
	}

	@Override
	public HotelGoods selectInfoByPrimaryKey(String id) {
		return hotelGoodsMapper.selectInfoByPrimaryKey(id);
	}
	
	/**
	 * 根据酒店集合查询出所有酒店商品列表
	* @param hotelIds
	* @return
    * @author：sty
    * @date：2017-11-02 10:44
	 */
	@Override
	public List<HotelGoods> selectHotelGoodsByHotelIds(List<String> hotelIds){
		return hotelGoodsMapper.selectHotelGoodsByHotelIds(hotelIds);
	}
	/**
	 * 查询所有商品包含已删除的
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-02 10:44
	 */
	@Override
	public int countHotelGoods(String hotelId) {
		return hotelGoodsMapper.countHotelGoods(hotelId);
	}

}
