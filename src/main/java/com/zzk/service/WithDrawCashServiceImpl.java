package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zzk.common.WithDrawConstact;
import com.zzk.dao.WithDrawCashMapper;
import com.zzk.entity.WithDrawCash;
import com.zzk.util.DateUtils;
import com.zzk.util.Page;
import com.zzk.util.PageView;
import com.zzk.util.Result;
import com.zzk.vo.WithDrawCashVo;

/**
 * 提现信息表的实体类
* @author: wangpeng
* @date: 2018-04-12 10:41
 */
@Service("withDrawCashService")
public class WithDrawCashServiceImpl implements WithDrawCashService {

	@Resource
	private WithDrawCashMapper withDrawCashMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public Result<Object> selectByPage(Page<Object> pager,Map<String,Object> map) {
		try{
			PageHelper.startPage(pager.getPageNumber(), pager.getpageSize());
			List<WithDrawCash> list = withDrawCashMapper.selectByPage(map);
			PageView<WithDrawCash> view = new PageView<>(list);
			PageView<WithDrawCashVo> cashVo = assembleWithDrawCashVo(view);
			return new Result<Object>(1, "success", "请求成功", cashVo);
		}catch(Exception e){
			return new Result<Object>(0,"error","数据异常",e);
		}
	}
	
	private PageView<WithDrawCashVo> assembleWithDrawCashVo(PageView<WithDrawCash> view){
		List<WithDrawCashVo> listVo = new ArrayList<WithDrawCashVo>();
		List<WithDrawCash> list = view.getList();
		for (WithDrawCash withDrawCash : list) {
			WithDrawCashVo drawVo = new WithDrawCashVo();
			drawVo.setId(withDrawCash.getId());
			drawVo.setApplyTime(DateUtils.changeDateToStr(withDrawCash.getApplyTime(), DateUtils.DEFAULT_DATE_FORMAT_YMD));
			drawVo.setBillState(WithDrawConstact.BillStateEnum.codeOf(withDrawCash.getBillState()).getValue());
			drawVo.setDrawMode(WithDrawConstact.PayMode.codeOf(withDrawCash.getPayMode()).getValue()+":"+withDrawCash.getAccountNum());
			drawVo.setCashMoney(withDrawCash.getCashMoney());
			drawVo.setPayMode(WithDrawConstact.PayMode.codeOf(withDrawCash.getPayMode()).getValue());
			drawVo.setPayTime(DateUtils.changeDateToStr(withDrawCash.getPayTime(), DateUtils.DEFAULT_DATE_FORMAT_YMD));
			drawVo.setElecReceipt(withDrawCash.getElecReceipt());
			drawVo.setSerialNo(withDrawCash.getSerialNo());
			listVo.add(drawVo);
		}
		PageView<WithDrawCashVo> pageVo = new PageView<>(listVo);
		pageVo.setList(listVo);
		pageVo.setPageNum(view.getPageNum());
		pageVo.setPages(view.getPages());
		pageVo.setPages(view.getPages());
		pageVo.setPageSize(view.getPageSize());
		pageVo.setTotal(view.getTotal());
		return pageVo;
	}
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return withDrawCashMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public WithDrawCash selectByPrimaryKey(String id) {
		return withDrawCashMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(WithDrawCash bean) {
		return withDrawCashMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(WithDrawCash bean) {
		return withDrawCashMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		WithDrawCash bean = withDrawCashMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return withDrawCashMapper.updateByPrimaryKey(bean);
	}

}
