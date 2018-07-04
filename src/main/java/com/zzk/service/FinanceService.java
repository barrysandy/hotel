package com.zzk.service;

import java.util.Map;

import com.zzk.util.PageView;
import com.zzk.entity.FinanceInfo;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;

public interface FinanceService {
	
    int deleteByPrimaryKey(String id);

    int insert(FinanceInfo record);

    int insertSelective(FinanceInfo record);

    FinanceInfo selectByPrimaryKey(String id);
    
    PageView selectByPage(FinanceInfo finance,int pageNum,int PageSize);

    int updateByPrimaryKeySelective(FinanceInfo record);

    int updateByPrimaryKey(FinanceInfo record);
    
    Map selectAbleCashTotal(String shopId);
    
    void createBill();
    
    int generatedBills();
    
    PageView<OrderBaseInfo> billDetail(String id, int pageNum, int pageSize);
    
    int insertSingleFinance(Order order);
    
    FinanceInfo selectNotGenerateFin(String shopId);

}
