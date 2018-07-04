package com.zzk.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.zzk.util.PageView;
import com.zzk.util.DateUtils;
import com.zzk.util.GenerateSequenceUtil;
import com.zzk.util.UuidUtil;
import com.zzk.common.Constact;
import com.zzk.dao.FinanceMapper;
import com.zzk.dao.OrderMapper;
import com.zzk.entity.FinanceInfo;
import com.zzk.entity.Hotel;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderCustom;
import com.zzk.service.FinanceService;
import com.zzk.service.HotelService;
import com.zzk.service.OrderService;

@Service("financeService")
public class FinanceServiceImpl implements FinanceService {
	@Resource
	private FinanceMapper financeMapper;
	@Resource
	private HotelService hotelService;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private OrderService orderService;

	@Override
	public int deleteByPrimaryKey(String id) {
		return financeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FinanceInfo record) {
		return financeMapper.insert(record);
	}

	@Override
	public int insertSelective(FinanceInfo record) {
		return financeMapper.insertSelective(record);
	}

	@Override
	public FinanceInfo selectByPrimaryKey(String id) {
		return financeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FinanceInfo record) {
		return financeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FinanceInfo record) {
		return financeMapper.updateByPrimaryKey(record);
	}

	@Override
	@Scheduled(cron="5 0 0 1 * ?")
	public void createBill() {
		List<Hotel> hotelList = hotelService.selectList();
		Calendar calendar = Calendar.getInstance();
		Date startTime = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date endTime = calendar.getTime();
		for (int i = 0; i < hotelList.size(); i++) {
			Hotel hotel = hotelList.get(i);
			String shopId = hotel.getId();
			FinanceInfo finance = new FinanceInfo();
			finance.setId(UuidUtil.uuidStr());
			finance.setBillNo(GenerateSequenceUtil.generateSequenceNo());
			finance.setCreateTime(new Date());
			finance.setActualMoney(new BigDecimal(0.00));
			finance.setOrderMoney(new BigDecimal(0.00));
			finance.setCommMoney(new BigDecimal(0.00));
			finance.setStartTime(startTime);
			finance.setEndTime(endTime);
			finance.setBillStatus(6);
			finance.setPlat(Constact.PlatformEnum.HOTEL.getCode());
			finance.setSellerId(shopId);
			finance.setStatus(1);
			finance.setOrderCount(0);
			financeMapper.insert(finance);
		}
	}
	@Override
	public int insertSingleFinance(Order order) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS);
		Calendar calendar = Calendar.getInstance();
		String startTime = dateFormat.format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		String endTime = dateFormat.format(calendar.getTime());
		String shopId = order.getShopId();
		FinanceInfo finance = new FinanceInfo();
		String id = UuidUtil.uuidStr();
		finance.setId(id);
		order.setBillId(id);
		finance.setBillNo(GenerateSequenceUtil.generateSequenceNo());
		BigDecimal bdOrder = order.getOrderMoney();
		BigDecimal bdBasic = order.getBasicMoney();
		BigDecimal bdComm = bdOrder.subtract(bdBasic);
		finance.setCreateTime(new Date());
		finance.setStartTime(DateUtils.getDate(startTime));
		finance.setEndTime(DateUtils.getDate(endTime));
		finance.setBillStatus(6);
		finance.setOrderMoney(bdOrder);
		finance.setCommMoney(bdComm);
		finance.setPlat(Constact.PlatformEnum.HOTEL.getCode());
		finance.setActualMoney(bdBasic);
		finance.setSellerId(shopId);
		finance.setStatus(1);
		finance.setOrderCount(1);
		return financeMapper.insert(finance);
	}

	@Override
	public PageView selectByPage(FinanceInfo finance, int pageNum, int PageSize) {
		Map<String, Object> map = new HashMap<>();
		Date date1 = finance.getStartTime();
		Date date2 = finance.getEndTime();
		String startTime = null;
		String endTime = null;
		if(date1 !=null && date2 !=null){
				date2.setHours(23);
				date2.setMinutes(59);
				date2.setSeconds(59);
				startTime = DateUtils.getDateTime(date1);
				endTime = DateUtils.getDateTime(date2);
		}
		map.put("shopId", finance.getSellerId());
		map.put("financeStatus", finance.getBillStatus());
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("billNo", finance.getBillNo());
		PageHelper.startPage(pageNum, PageSize);
		List<FinanceInfo> list = financeMapper.selectByPage(map);
		return new PageView(list);
	}

	@Override
	public Map selectAbleCashTotal(String shopId) {
		Map<String, String> map = new HashMap<>();
		map.put("shopId", shopId);
		return financeMapper.selectAbleCashTotal(map);
	}

	@Override
	public PageView<OrderBaseInfo> billDetail(String id, int pageNum, int pageSize) {
		
		return orderService.selectByBillId(id, pageNum, pageSize);
	}

	@Override
	@Scheduled(cron="0 0 0 1 * ?")
	public int generatedBills() {
		List<Hotel> hotelList = hotelService.selectList();
		return financeMapper.updateByShopList(hotelList);
	}

	@Override
	public FinanceInfo selectNotGenerateFin(String shopId) {
		return financeMapper.selectNotGenerateFin(shopId);
	}
}
