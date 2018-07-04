package com.zzk.service;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zzk.common.Constact;
import com.zzk.common.FinanceConstact;
import com.zzk.dao.BusinessInfoMapper;
import com.zzk.dao.FinanceInfoMapper;
import com.zzk.dao.OrderBaseInfoMapper;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.FinanceInfo;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderBaseInfoCustom;
import com.zzk.util.DateUtils;
import com.zzk.util.GenerateSequenceUtil;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.PageView;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.zzk.vo.FinanceInfoVo;

/**
 * 商家财务信息表
* @author: wangpeng
* @date: 2018-03-10 15:34
 */
@Service("financeInfoService")
public class FinanceInfoServiceImpl implements FinanceInfoService {

	@Resource
	private FinanceInfoMapper financeInfoMapper;
	@Resource
	private BusinessInfoMapper businessInfoMapper;
	@Resource
	private OrderBaseInfoMapper orderBaseInfoMapper;
	@Resource
	private SellerReceiveConfigService sellerReceiveConfigService;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<FinanceInfo> selectByPage(Map map) {
		return financeInfoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return financeInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public FinanceInfo selectByPrimaryKey(String id) {
		return financeInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(FinanceInfo bean) {
		return financeInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(FinanceInfo bean) {
		return financeInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		FinanceInfo bean = financeInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return financeInfoMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 条件分页查询
	 */
	@Override
	public Result<Object> selectByMap(Page page, Map map) {
		if(page == null){
			page = new Page();
		}
		try{
			PageHelper.startPage(page.getPageNumber(), page.getpageSize());
			List<FinanceInfo> listFinance= financeInfoMapper.selectByMap(map);
			PageView<FinanceInfo> view = new PageView<>(listFinance);
			PageView<FinanceInfoVo> listToVo = assembleFinanceListToVo(view);
			return new Result(1, "success", "请求成功", listToVo);
		}catch(Exception e){
			return new Result<>(0,"error",e.toString());
		}
	}
	/**
	 * 账单Id和sellerID查询
	 */
	@Override
	public String selectByBillNo(String billNo,String sellerId) {
		FinanceInfo vo = financeInfoMapper.selectByBillNo(billNo,sellerId);
		return JsonUtils.lineJsonData(1, "success", "请求成功", vo);
	}

	private PageView<FinanceInfoVo> assembleFinanceListToVo(PageView<FinanceInfo> view){
		if(view.getList() == null){
			return null;
		}
		List<FinanceInfoVo> financeInfoVoList = new ArrayList<FinanceInfoVo>();
		List<FinanceInfo> financeInfoList = view.getList();
		for (FinanceInfo financeInfo : financeInfoList) {
			FinanceInfoVo financeVo = new FinanceInfoVo();
			financeVo.setOrderBaseInfVo(null);
			financeVo.setId(financeInfo.getId());
			financeVo.setSellerId(financeInfo.getSellerId());
			financeVo.setBillNo(financeInfo.getBillNo());
			financeVo.setBillStatus(financeInfo.getBillStatus());
			financeVo.setCommMoney(financeInfo.getCommMoney()==null ? new BigDecimal("0.00") : financeInfo.getCommMoney());
			financeVo.setOrderMoney(financeInfo.getOrderMoney()==null ? new BigDecimal("0.00"):financeInfo.getOrderMoney());
			financeVo.setActualMoney(financeInfo.getActualMoney()==null ? new BigDecimal("0.00"):financeInfo.getActualMoney());
			financeVo.setInvStatus(financeInfo.getInvStatus());
			financeVo.setInvMoney(financeInfo.getInvMoney()==null ? new BigDecimal("0.00") : financeInfo.getInvMoney());
			financeVo.setStartTime(DateUtils.changeDateToStr(financeInfo.getStartTime(), DateUtils.DEFAULT_DATE_FORMAT_YMD));
			financeVo.setEndTime(DateUtils.changeDateToStr(financeInfo.getEndTime(), DateUtils.DEFAULT_DATE_FORMAT_YMD));
			financeVo.setOrderNum(financeInfo.getOrderCount());
			financeInfoVoList.add(financeVo);
		}
		PageView<FinanceInfoVo> listView = new PageView<>(financeInfoVoList);
		listView.setList(financeInfoVoList);
		listView.setPageNum(view.getPageNum());
		listView.setPageSize(view.getPageSize());
		listView.setPages(view.getPages());
		listView.setTotal(view.getTotal());
		listView.setSize(view.getSize());
		return listView;
	}
	/**
	 * 生成可以提取现金的账单
	 * 
	 * @author John
	 * @date： 2018年4月25日 上午11:23:33
	 */
	@Scheduled(cron="50 0 0 1 * ?  ")
	public void generatorWithdrawCashBill(){
		financeInfoMapper.updateStatusToWaitCash();
	}
	/**
	 * 每月初生成订单
	 * 
	 * @author John
	 * @date： 2018年3月30日 上午11:50:46
	 */
	@Scheduled(cron="50 1 0 1 * ?  ")
	public void createNewBill(){
		System.out.println("======开始创建账单=====");
		List<String> ids = businessInfoMapper.selectAllBusinessId();
		if(ids == null || ids.size() == 0){
			return;
		}
		List<FinanceInfo> financeList = new ArrayList<FinanceInfo>();
		while(true){
			int count = financeInfoMapper.selectNobillCount();
			if(count ==0){
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (String businId : ids) {
			FinanceInfo  fina = new FinanceInfo();
			fina.setId(StringUtils.getUUID());
			fina.setBillNo(GenerateSequenceUtil.generateBillNo());
			fina.setBillStatus(FinanceConstact.StatusEnum.NO_BILL.getCode());
			fina.setStartTime(DateUtils.getCurrentMonthFirstDay());
			fina.setEndTime(DateUtils.getCurrentMonthLastDay());
			fina.setStatus(1);
			fina.setCreateTime(DateUtils.getCurrentDate());
			fina.setPlat(1);
			fina.setSellerId(businId);
			financeList.add(fina);
		}
		financeInfoMapper.batchInsert(financeList);
	}
	@Scheduled(cron="1 1 0 1 * ?   ")
	public void generatorBill(){
		financeInfoMapper.updateStatusToWaitCash();
	}

	@Override
	public Result<Object> selectDisposableValue(String sellerId) {
		Map<String,Object> map = financeInfoMapper.selectDisPosableValue(sellerId);
		return new Result<Object>(1,"success","请求成功",map);
	}

	@Override
	public int generatorBill(String orderNo) {
		OrderBaseInfo orderBaseInfo = orderBaseInfoMapper.selectByOrderNo(orderNo);
		FinanceInfo financeInfo = financeInfoMapper.selectNotGeneratedBySellerId(orderBaseInfo.getSellerId());
		sellerReceiveConfigService.sendMessageToSeller(orderBaseInfo.getSellerId(), 5);
		if(financeInfo == null){
			FinanceInfo finance = new FinanceInfo();
			finance.setId(StringUtils.getUUID());
			finance.setBillNo(GenerateSequenceUtil.generateBillNo());
			finance.setOrderMoney(orderBaseInfo.getOrderMoney());
			finance.setCommMoney(orderBaseInfo.getCommission());
			finance.setActualMoney(orderBaseInfo.getOrderMoney().subtract(orderBaseInfo.getCommission()));
			finance.setBillStatus(FinanceConstact.StatusEnum.NO_BILL.getCode());
			finance.setOrderCount(1);
			finance.setStatus(1);
			finance.setPlat(Constact.PlatformEnum.LINE.getCode());
			finance.setSellerId(orderBaseInfo.getSellerId());
			finance.setStartTime(DateUtils.getCurrentDate());
			finance.setStartTime(DateUtils.getCurrentDate());
			finance.setEndTime(DateUtils.getCurrentMonthLastDay());
			finance.setCreateTime(DateUtils.getCurrentDate());
			finance.setUpdateTime(DateUtils.getCurrentDate());
			int result= financeInfoMapper.insert(finance);
			if(result>0){
				orderBaseInfo.setBillId(finance.getId());
				return orderBaseInfoMapper.updateByPrimaryKeySelective(orderBaseInfo);
			}
			return -1;
		}else{
			financeInfo.setOrderMoney(financeInfo.getOrderMoney().add(orderBaseInfo.getOrderMoney()));
			financeInfo.setCommMoney(financeInfo.getCommMoney().add(orderBaseInfo.getCommission()));
			financeInfo.setActualMoney(financeInfo.getActualMoney().add(orderBaseInfo.getOrderMoney().subtract(orderBaseInfo.getCommission())));
			financeInfo.setOrderCount(financeInfo.getOrderCount()+1);
			financeInfo.setUpdateTime(DateUtils.getCurrentDate());
			return financeInfoMapper.updateByPrimaryKeySelective(financeInfo);
		}
		
	}


}
