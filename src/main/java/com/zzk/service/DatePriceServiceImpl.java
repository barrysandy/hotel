package com.zzk.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzk.util.DateUtils;
import com.zzk.dao.DatePriceMapper;
import com.zzk.entity.DatePrice;
import com.zzk.entity.Hotel;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.PriceRule;
import com.zzk.service.DatePriceService;
import com.zzk.service.HotelGoodsService;
import com.zzk.service.HotelService;
import com.zzk.service.PriceRuleService;

/**
 * 
 * 酒店时间价格二维表
* @author：huashuwen
* @date：2018-01-29 09:49
 */
@Service("datePriceService")
public class DatePriceServiceImpl implements DatePriceService {
	@Resource
	private DatePriceMapper datePriceMapper;
	@Resource
	private HotelGoodsService hotelGoodsService;
	@Resource
	private PriceRuleService priceRuleService;
	@Resource
	private HotelService hotelService;
	/**
	 * 分页查询
	 */
	@Override
	public List<DatePrice> selectByPage(Map map) {
		return datePriceMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return datePriceMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public DatePrice selectByPrimaryKey(String id) {
		return datePriceMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(DatePrice bean) {
		return datePriceMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-01-29 09:49
	 */
	@Override
	public int insert(DatePrice bean) {
		return datePriceMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-01-29 09:49
	 */
	@Override
	public int delete(String id) {
		DatePrice bean = datePriceMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return datePriceMapper.updateByPrimaryKey(bean);
	}

	@Override
	@Transactional
	public List<Map<String,Object>> insertPirceInfo(String hotelId) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
		double price =0 ;
		String date = "";
		double max = 0;
		double min = 0;
		String datePrice = "";
		Calendar calendar = Calendar.getInstance();
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		String startTime = sdf.format(new Date());
		int dayOfWeek1=0;
		List<HotelGoods> hotelGoodsList = hotelGoodsService.selectHotelGoods(hotelId);
		if(hotelGoodsList.size()==0){
			return null;
		}
		List<PriceRule> priceRuleList = priceRuleService.selectByHotelId(hotelId,startTime);
		for (int i = 0; i < 120; i++) {
			date = DateUtils.getAfterDay(startTime, i,
					DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
			hotelGoodsLoop:for (HotelGoods hotelGoods : hotelGoodsList) {
				Map<String,Object> map =new HashMap<String,Object>();
				for (PriceRule priceRule : priceRuleList) {
					if (hotelGoods.getId().equals(priceRule.getGoodId())) {
						price = Double.parseDouble(priceRule.getPrice());
						if (DateUtils.compareDate(priceRule.getStartTime(),
								sdf.parse(date)) <= 0
								&& DateUtils.compareDate(priceRule.getEndTime(),
										sdf.parse(date)) >= 0) {
							if (priceRule.getType() == 2) {
								calendar.setTime(sdf.parse(date));
								dayOfWeek1 = calendar.get(7) - 1;
								if (dayOfWeek1 == 0) {
									dayOfWeek1 = 7;
								}
								if (String.valueOf(priceRule.getWeekStart())
										.indexOf(String.valueOf(dayOfWeek1)) >= 0) {
									map.put("price", price);
									map.put("goodsId",hotelGoods.getId());
									map.put("id",UUID.randomUUID().toString());
									map.put("executeDate", date);
									map.put("status",1);
									mapList.add(map);
									continue hotelGoodsLoop;
								}
							} else {
								map.put("price", price);
								map.put("goodsId",hotelGoods.getId());
								map.put("id",UUID.randomUUID().toString());
								map.put("executeDate", date);
								map.put("status",1);
								mapList.add(map);
								continue hotelGoodsLoop;
							}
						}
					}
				}
				map.put("price",null);
				map.put("goodsId",hotelGoods.getId());
				map.put("id",UUID.randomUUID().toString());
				map.put("executeDate", date);
				map.put("status",1);
				mapList.add(map);
			}
		}
		
		/*for(Map m:mapList){
			datePrice += "//"+m.get("executeDate")+":"+m.get("minPrice")+","+m.get("maxPrice");
		}*/
		Hotel hotel = hotelService.selectByPrimaryKey(hotelId);
		hotel.setDatePrice(datePrice);
		hotelService.update(hotel);
		datePriceMapper.deleteByHotelId(hotelId);
		datePriceMapper.batchInsert(hotelId, mapList);
		return mapList;
	}

	
	
}
