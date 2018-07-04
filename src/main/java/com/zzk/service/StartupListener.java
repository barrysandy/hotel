package com.zzk.service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zzk.Constant;
import com.zzk.common.OrderConstact;
import com.zzk.dao.OrderBaseInfoMapper;
import com.zzk.dao.OrderDetailInfoMapper;
import com.zzk.dao.ProductStockInfoMapper;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderDelay;
import com.zzk.entity.OrderDetailInfo;
import com.zzk.entity.ProductStockInfo;
import com.zzk.util.StringUtils;
import com.zzk.util.DateUtils;


@Service 
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {  
      
  
    @Autowired  
    DelayService delayService;  
    @Autowired
    OrderBaseInfoMapper baseInfoMapper;

    @Autowired
    OrderDetailInfoMapper detailInfoMapper;
    @Autowired
    ProductStockInfoMapper productStockInfoMapper;
    

    @Autowired
    private OrderStatusInfoService orderStatusInfoService;
    @Autowired
    private OrderBaseInfoService orderBaseInfoService;
    @Autowired
    private SellerReceiveConfigService sellerReceiveConfigService;
    
    private  ExecutorService fixedThreadPool;
    @Override  
    public void onApplicationEvent(ContextRefreshedEvent evt) {  
    	System.err.println("-----------执行启动方法——————————————————————————————--");
    	if(this.fixedThreadPool==null){
    		fixedThreadPool = Executors.newFixedThreadPool(6);
    	}
    	/*if (evt.getApplicationContext().getParent() == null) {  
            return;  
        }*/  
        //自动改变未支付超时订单状态
        delayService.start(new OnDelayedListener(){  
            @Override  
            public void onDelayedArrived(final OrderDelay order) {  
                //异步来做  
            	fixedThreadPool.execute(new Runnable(){  
                    public void run(){  
                        long orderNo = order.getOrderNo();  
                        OrderBaseInfo baseOrder = baseInfoMapper.selectByOrderNo(String.valueOf(orderNo));
                        if(baseOrder!=null && baseOrder.getOrderState() == OrderConstact.OrderStatusEnum.WAITPAY.getCode()){
	                        baseOrder.setOrderState(OrderConstact.OrderStatusEnum.SYSTEMCANCELED.getCode());
	                        baseOrder.setUpdateTime(DateUtils.getCurrentDate());
	                        int result= baseInfoMapper.updateByPrimaryKeySelective(baseOrder);
	                        if(result> 0){
	                        	sellerReceiveConfigService.sendMessageToBuyer(baseOrder, 7);
//	                        	orderBaseInfoService.cancelOrderAddStock(String.valueOf(orderNo));
	                        }
	                        orderStatusInfoService.changeStatus(String.valueOf(orderNo), OrderConstact.OrderStatusEnum.SYSTEMCANCELED.getCode());
	                    }  
                    }
                });  
            } 
            @Override  
            public void onDelayedPrepareStockArrived(final OrderDelay order) { 
                //异步来做  
            	fixedThreadPool.execute(new Runnable(){  
                    public void run(){  
                        long orderNo = order.getOrderNo();  
                        OrderBaseInfo baseOrder = baseInfoMapper.selectByOrderNo(String.valueOf(orderNo));
                        if(baseOrder!=null && baseOrder.getOrderState() == OrderConstact.OrderStatusEnum.WAITPAY.getCode()){
	                    //预占库存-订单人数
                        	orderBaseInfoService.cancelOrderAddStock(String.valueOf(orderNo));
                        }  
                    }
                });  
            } 
        });  
        //查找需要入队的订单  
        fixedThreadPool.execute(new Runnable(){  
            @Override  
            public void run() {  
            	List<OrderBaseInfo> list=  baseInfoMapper.selectByOrderState(1);
            	for(OrderBaseInfo bean:list){
            		long no = Long.valueOf(bean.getOrderNo());
            		OrderDelay od = new OrderDelay(no,Constant.EXPTIME ,bean.getCreateTime());
            		delayService.add(od);
            		System.err.println(od.toString());
            	}
            	//项目启动时，先把预占库存全部清零
            	productStockInfoMapper.updateInit();
            }  
        });   
    }  
} 
