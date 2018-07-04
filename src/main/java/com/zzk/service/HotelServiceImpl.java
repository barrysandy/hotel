package com.zzk.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.util.DateUtils;
import com.zzk.util.StringUtils;
import com.zzk.dao.BusinessInfoMapper;
import com.zzk.dao.HotelMapper;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.Hotel;
import com.zzk.entity.LineUser;
import com.zzk.entity.Order;
import com.zzk.entity.PriceRule;
import com.zzk.entity.RoomType;
import com.zzk.entity.StockRule;
import com.zzk.service.HotelService;
import com.zzk.service.OrderService;
import com.zzk.service.PriceRuleService;
import com.zzk.service.RoomTypeService;
import com.zzk.service.StockRuleService;

/**
 * 
 * 酒店信息
* @author：sty
* @date：2017-11-02 10:26
 */
@Service("hotelService")
public class HotelServiceImpl implements HotelService {
	@Resource
	private HotelMapper hotelMapper;
	@Resource
	private StockRuleService stockRuleService;
	@Resource
	private OrderService orderService;
	@Resource
	private RoomTypeService roomTypeService;
	@Resource
	private PriceRuleService priceRuleService;
	@Resource
	private LineUserService lineUserService;
	@Resource
	private BusinessInfoMapper businessInfoMapper;
	/**
	 * 分页查询
	 */
	@Override
	public List<Hotel> selectByPage(Map map) {
		return hotelMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return hotelMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public Hotel selectByPrimaryKey(String id) {
		return hotelMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(Hotel bean) {
		String merchantId = bean.getId();
		String userId = bean.getOwnerId();
		
		LineUser user = lineUserService.selectByPrimaryKey(userId);
		if(user!=null){
			if(StringUtils.isBlank(user.getMerchatId())){
				user.setMerchatId(merchantId);
				lineUserService.update(user);
			}
		}
		return hotelMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:26
	 */
	@Override
	public int insert(Hotel bean) {
		String merchantId = bean.getId();
		String userId = bean.getOwnerId();
		LineUser user = lineUserService.selectByPrimaryKey(userId);
		user.setMerchatId(merchantId);
		lineUserService.update(user);
		return hotelMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：sty
    * @date：2017-11-02 10:26
	 */
	@Override
	public int delete(String id) {
		Hotel bean = hotelMapper.selectByPrimaryKey(id);
		bean.setRecordStatus(-1);
		return hotelMapper.updateByPrimaryKey(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param name
	* @return
    * @author：huashuwen
    * @date：2017-11-03 
	 */
	@Override
	public Hotel selectByName(String name) {
		return hotelMapper.selectByName(name);
	}

	@Override
	public List<Hotel> selectList() {
		return hotelMapper.selectList();
	}

	@Override
	public String selecCity(String cityId) {
		
		return hotelMapper.selectCity(cityId);
	}
	
	
	@Override
	public String selecProvince(String provinceId) {
		return hotelMapper.selectProvince(provinceId);
	}

	@Override
	public List<Map<String,Object>> getProvinceList() {
		return hotelMapper.getProvinceList();
	}

	@Override
	public List<Map<String, Object>> getCityList(String provinceId) {
		return hotelMapper.getCityList(provinceId);
	}

	@Override
	public List<Map<String, Object>> getAreaList(String cityId) {
		return hotelMapper.getAreaList(cityId);
	}

	@Override
	public String selectArea(String areaId) {
		return hotelMapper.selectArea(areaId);
	}

	@Override
	public List<Hotel> checkOwnerId(String id) {
		
		return hotelMapper.checkOwnerId(id);
	}

	@Override
	public Map<String,Object> getRoomState(String shopId)  {
		Map<String,Object> map = new HashMap<String,Object>();
		int result = 0;
		try {
			result =  stockRuleService.selectByHotelIdFull(shopId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(result > 0){
			map.put("status", 1);
			map.put("time", "--:--:--");
			return map;
		}else{
			Order order= orderService.selectRecendOrderByUserId(shopId);
			if(order ==null){
				return null;
			}
			String time = null;
			try {
				time = DateUtils.formatTime(order.getOrderTime());
				map.put("status", 0);
				map.put("time", time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
		}
	}
	
	
	@Override
	public String selectOp(String id) {
		return hotelMapper.selectById(id);
	}
	@Override
	public Hotel business2Hotel(BusinessInfo bean){
		Hotel hotel = new Hotel();
		hotel.setOwnerId(bean.getUserId());
		hotel.setId(bean.getId());
		hotel.setName(bean.getBusinName());
		hotel.setAddress(bean.getAddress());
		hotel.setAreaId(bean.getAreaId());
		hotel.setCityId(bean.getCityId());
		hotel.setContactEmail(bean.getEmail());
		hotel.setContacts(bean.getPersonName());
		hotel.setContactPhone(bean.getPhone());
		hotel.setReceptionPhone(bean.getTel());
		hotel.setLon(bean.getLon());
		hotel.setLat(bean.getLat());
		return hotel;
	}
	
	@Override
	public BusinessInfo hotel2Business(Hotel bean){
		BusinessInfo businessInfo = new BusinessInfo();
		businessInfo.setId(bean.getId());
		businessInfo.setUserId(bean.getOwnerId());
		businessInfo.setBusinName(bean.getName());
		businessInfo.setAddress(bean.getAddress());
		businessInfo.setAreaId(bean.getAreaId());
		businessInfo.setCityId(bean.getCityId());
		businessInfo.setEmail(bean.getContactEmail());
		businessInfo.setPersonName(bean.getContacts());
		businessInfo.setPhone(bean.getContactPhone());
		businessInfo.setTel(bean.getReceptionPhone());
		businessInfo.setLon(bean.getLon());
		businessInfo.setLat(bean.getLat());
		return businessInfo;
	}
	
	@Override
	public int update(BusinessInfo bean) {
		
		BusinessInfo rule = businessInfoMapper.selectByPrimaryKey(bean.getId());
		if(rule!=null){
			rule.setBusinName(bean.getBusinName());
			rule.setLon(bean.getLon());
			rule.setLat(bean.getLat());
			rule.setAreaId(bean.getAreaId());
			rule.setCityId(bean.getCityId());
			rule.setTel(bean.getTel());
			rule.setAddress(bean.getAddress());
			businessInfoMapper.updateByPrimaryKeySelective(rule);
		}
		return 1;
		
	}
	@Override
	public int updateHotel(BusinessInfo bean) {
		Hotel rule = selectByPrimaryKey(bean.getId());
		if(rule!=null){
			rule.setName(bean.getBusinName());
			rule.setLon(bean.getLon());
			rule.setLat(bean.getLat());
			rule.setAreaId(bean.getAreaId());
			rule.setCityId(bean.getCityId());
			rule.setReceptionPhone(bean.getTel());
			rule.setAddress(bean.getAddress());
			rule.setUpdateDate(new Date());
			update(rule);
		}
		return 1;
	}
}
