package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zzk.dao.SellerAccountMapper;
import com.zzk.entity.SellerAccount;
import com.zzk.util.JsonUtils;

/**
 * 商家账户表
* @author: wangpeng
* @date: 2018-03-10 11:10
 */
@Service("sellerAccountService")
public class SellerAccountServiceImpl implements SellerAccountService {

	@Resource
	private SellerAccountMapper sellerAccountMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<SellerAccount> selectByPage(Map map) {
		return sellerAccountMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return sellerAccountMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public SellerAccount selectByPrimaryKey(String id) {
		return sellerAccountMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(SellerAccount bean) {
		return sellerAccountMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(SellerAccount bean) {
		return sellerAccountMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		SellerAccount bean = sellerAccountMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return sellerAccountMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 通过sellerID和type查询
	 */
	@Override
	public SellerAccount selectBySellerId(String sellerId, Integer type) {
		
		return sellerAccountMapper.selectBySellerId(sellerId, type);
	}
	
   /**
    * 更新或新增账户信息
    */
	@Override
	public String insertOrUpdate(SellerAccount sellerAccount) {
		SellerAccount account = selectBySellerId(sellerAccount.getSellerId(),sellerAccount.getType());
		int resultCode = 0;
		if(account != null){
			sellerAccount.setId(account.getId());
			sellerAccount.setUpdateTime(new Date());
			resultCode=	this.update(sellerAccount);
			if(resultCode > 0){
				return JsonUtils.lineJsonData(1, "sucess", "更新成功",sellerAccount);
			}
		}else{
			sellerAccount.setId(UUID.randomUUID().toString());
			sellerAccount.setStatus(1);
			sellerAccount.setAccountStatus(1);
			sellerAccount.setCreateTime(new Date());
			sellerAccount.setUpdateTime(new Date());
			resultCode=this.insert(sellerAccount);
			if(resultCode > 0){
				return JsonUtils.lineJsonData(1, "sucess", "保存成功",sellerAccount);
			}
		}
		return JsonUtils.lineJsonData(0, "error", "操作失败", null);
	}

}
