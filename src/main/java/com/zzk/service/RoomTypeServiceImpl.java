package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzk.dao.HotelGoodsMapper;
import com.zzk.dao.RoomTypeMapper;
import com.zzk.entity.Dict;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.RoomType;
import com.zzk.service.DictService;
import com.zzk.service.RoomTypeService;

/**
 * 
 * 房型信息
* @author：sty
* @date：2017-11-02 10:37
 */
@Service("roomTypeService")
public class RoomTypeServiceImpl implements RoomTypeService {
	@Resource
	private RoomTypeMapper roomTypeMapper;
	@Resource
	private HotelGoodsMapper hotelGoodsMapper;
	@Autowired
	private DictService dictService;
	private static List<Dict> dictList;
	/**
	 * 分页查询
	 */
	@Override
	public List<RoomType> selectByPage(Map map) {
		return roomTypeMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return roomTypeMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public RoomType selectByPrimaryKey(String id) {
		return roomTypeMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(RoomType bean) {
		
		String roomtypeId = bean.getId();
		List<HotelGoods> HotelGoods= hotelGoodsMapper.selectByRoomtypeId(roomtypeId);
		for(HotelGoods hotelGood:HotelGoods){
			String name = bean.getApartmentName() + CacheService.getLabel("goodsBreakfast", String.valueOf(hotelGood.getBreakfast()));
			hotelGood.setGoodsName(name);
			hotelGoodsMapper.updateByPrimaryKey(hotelGood);
		}
		return roomTypeMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:37
	 */
	@Override
	public int insert(RoomType bean) {
		return roomTypeMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:37
	 */
	@Override
	public int delete(String id) {
		RoomType bean = roomTypeMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return roomTypeMapper.updateByPrimaryKey(bean);
	}

	@Override
	public List<RoomType> selectByHotelId(String hotelId) {
		
		return roomTypeMapper.selectByHotelId(hotelId);
	}

	@Override
	public List<RoomType> getRoomtypeList(String hotelId) {
		
		return roomTypeMapper.getRoomtypeList(hotelId);
	}

	@Override
	public RoomType selectByGoodsId(String goodsId) {
		
		return roomTypeMapper.selectByGoodsId(goodsId);
	}

	@Override
	public List<RoomType> selectByHotelsId(List<String> idList) {
		return roomTypeMapper.selectByHotelsId(idList);
	}
	
	
}
